package com.zktechnology.android.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import com.guide.guidecore.utils.ShutterHandler;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.helper.CardServiceHelper;
import com.zktechnology.android.helper.FaceServiceHelper;
import com.zktechnology.android.helper.FingerprintServiceHelper;
import com.zktechnology.android.helper.McuServiceHelper;
import com.zktechnology.android.helper.QrcodeServiceHelper;
import com.zktechnology.android.helper.SystemServiceHelper;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.rs232.ZKRS232EncryptManager;
import com.zktechnology.android.service.ZkCheckMultiBioDataCountService;
import com.zktechnology.android.strategy.MainThreadExecutor;
import com.zktechnology.android.task.ZkQueryFingerParamsTask;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zktechnology.android.wiegand.ZKWiegandManager;
import com.zkteco.android.core.sdk.LauncherManager;
import com.zkteco.android.core.sdk.RfidManager;
import com.zkteco.android.core.sdk.SharedPreferencesManager;
import com.zkteco.android.core.sdk.TimeManager;
import com.zkteco.android.core.sdk.ZKDateTimeManager;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.PersBiotemplatedata;
import com.zkteco.android.employeemgmt.util.VerifyTypeUtils;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import com.zkteco.android.zkcore.view.ZKToast;
import com.zkteco.biometric.ZKBioTemplateService;
import com.zkteco.edk.camera.lib.ZkThreadPoolManager;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmService12;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AppInitializeTask implements Runnable {
    private static final String DEFAULT_OPEN_ADB = "0";
    private static final String TAG = "AppInitializeTask";
    private final IAppInitializeCallback callBack;
    private final Context mContext;
    private DataManager mDataManager;
    private SharedPreferencesManager sharedPreferencesManager;

    public AppInitializeTask(Context context, IAppInitializeCallback iAppInitializeCallback) {
        this.mContext = context.getApplicationContext();
        this.callBack = iAppInitializeCallback;
    }

    private void sleep(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Thread.currentThread().setName("app_init_thread");
        try {
            this.callBack.initStateChange((int) R.string.init_copy_config_file);
            copyApplicationConfig();
            this.callBack.initStateChange((int) R.string.init_db_step);
            Log.d(TAG, "run: start initialize db files and tables.");
            initApplicationDatabase();
            Log.d(TAG, "run: start check device type.");
            checkDeviceType();
            Log.d(TAG, "run: start check platform type.");
            checkPlatformType();
            FaceServiceHelper.getInstance().init();
            McuServiceHelper.getInstance().init();
            SystemServiceHelper.getInstance().init();
            Log.d(TAG, "run: start check device serial number.");
            checkDeviceSerialNumber();
            IAppInitializeCallback iAppInitializeCallback = this.callBack;
            Objects.requireNonNull(iAppInitializeCallback);
            runOnMainThread(new Runnable() {
                public final void run() {
                    IAppInitializeCallback.this.initDeviceOver();
                }
            });
            this.callBack.initStateChange((int) R.string.init_create_file);
            createFiles();
            sleep(500);
            try {
                VerifyTypeUtils.init(this.mContext);
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeLauncherInitRecord("Error: VerifyTypeUtils.init -> " + e.getMessage());
            }
            this.callBack.initStateChange((int) R.string.init_swipe_card);
            boolean openRFidDev = openRFidDev(this.mContext);
            sleep(500);
            initHDR();
            this.callBack.initStateChange((int) R.string.init_adb);
            initADB(this.mDataManager);
            sleep(500);
            if (openRFidDev) {
                this.callBack.initStateChange((int) R.string.init_wg);
                initWiegand();
            } else {
                LogUtils.e(TAG, "9: 初始化 韦根功能 失败，RFil.openDev 失败！");
            }
            if (openRFidDev) {
                initRS232();
            } else {
                LogUtils.e(TAG, "12: 初始化 读头 失败，RFil.openDev 失败！");
            }
            sleep(500);
            CardServiceHelper.getInstance().init();
            QrcodeServiceHelper.getInstance().init();
            FingerprintServiceHelper.getInstance().init();
            this.callBack.initStateChange((int) R.string.init_check_capacity);
            isFaceCountAndBioPhotoCountEqual();
            this.callBack.initStateChange((int) R.string.init_time_zone);
            initTimeZone();
            this.callBack.initStateChange((int) R.string.init_time_manager);
            try {
                ZKDateTimeManager.getInstance().init(this.mContext);
                new LauncherManager(this.mContext.getApplicationContext()).setDefaultLauncher(this.mContext.getPackageName(), "com.zktechnology.android.launcher2.ZKLauncher");
            } catch (Exception e2) {
                e2.printStackTrace();
                FileLogUtils.writeLauncherInitRecord("Error: ZKDateTimeManager#init -> " + e2.getMessage());
            }
            tryRebootDevice();
            setTime();
            setIsUpgradeByClouds();
            runInstallScript();
            this.callBack.initStateChange((int) R.string.init_palm_algorithm);
            try {
                if (initPalm()) {
                    setDBPalm();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            try {
                DeviceManager.getDefault().checkFirmwareActivation();
            } catch (Exception e4) {
                e4.printStackTrace();
                FileLogUtils.writeLauncherInitRecord("Error: checkFirmwareActivation -> " + e4.getMessage());
            }
            try {
                startCheckMultiBioDataCountService(this.mContext);
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            try {
                ZkThreadPoolManager.getInstance().execute(new ZkQueryFingerParamsTask());
            } catch (Exception e6) {
                e6.printStackTrace();
            }
            try {
                AppUtils.startUpgradeService(this.mContext);
            } catch (Exception e7) {
                e7.printStackTrace();
            }
            setScreenOffTimeoutNever();
            runOnMainThread(new Runnable() {
                public final void run() {
                    AppInitializeTask.this.lambda$run$0$AppInitializeTask();
                }
            });
        } catch (Exception e8) {
            e8.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: init task error -> " + e8.getMessage());
            runOnMainThread(new Runnable(e8) {
                public final /* synthetic */ Exception f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    AppInitializeTask.this.lambda$run$1$AppInitializeTask(this.f$1);
                }
            });
        }
    }

    public /* synthetic */ void lambda$run$0$AppInitializeTask() {
        this.callBack.initStateChange((int) R.string.init_finish);
        this.callBack.allInitOver();
    }

    public /* synthetic */ void lambda$run$1$AppInitializeTask(Exception exc) {
        this.callBack.initExceptionStateChange(exc.getMessage());
        ZKToast.showToast(this.mContext, exc.getMessage());
    }

    /* access modifiers changed from: protected */
    public void startCheckMultiBioDataCountService(Context context) {
        context.startService(new Intent(context, ZkCheckMultiBioDataCountService.class));
    }

    private void tryRebootDevice() {
        if (SharedPreferencesUtils.getInstance().getBoolean("IsFirstLaunch", true)) {
            SharedPreferencesUtils.getInstance().putBoolean("IsFirstLaunch", false);
            SystemClock.sleep(ShutterHandler.NUC_TIME_MS);
            AppUtils.rebootSystem(this.mContext, "初次安装启动");
        }
    }

    private void checkDeviceSerialNumber() {
        try {
            DeviceManager.getDefault().checkDeviceSerialNumber();
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: checkDeviceSerialNumber -> " + e.getMessage());
        }
    }

    private void checkDeviceType() {
        try {
            DeviceManager.getDefault().initDeviceType();
        } catch (IOException | SignatureException e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: checkDeviceType -> " + e.getMessage());
        }
    }

    private void checkPlatformType() {
        try {
            DeviceManager.getDefault().initPlatformType();
        } catch (IOException | SignatureException e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: checkPlatformType -> " + e.getMessage());
        }
    }

    private void initRS232() {
        try {
            if (this.mDataManager.getIntOption("ExtReader", 0) == 1) {
                LogUtils.e(TAG, "12: 初始化 读头 start");
                this.callBack.initStateChange((int) R.string.init_reader);
                ZKRS232EncryptManager.getInstance().init();
            }
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: initRS232 -> " + e.getMessage());
        }
    }

    private void initApplicationDatabase() {
        try {
            if (!initDb(this.mContext)) {
                Log.e(TAG, "run: db init failed");
                return;
            }
            Log.d(TAG, "run: initialize db files and tables...done");
            this.mDataManager = DBManager.getInstance();
            Log.d(TAG, "run: start querying db init state.");
            int i = 300;
            while (true) {
                if (this.mDataManager.getIntOption("db.init.state", -1) > 0) {
                    Log.d(TAG, "run: query db init state,ready.");
                    break;
                }
                i--;
                if (i == 0) {
                    Log.d(TAG, "run: query db init state,retries exhausted");
                    break;
                } else {
                    Log.d(TAG, "run: query db init state,retries:" + (100 - i));
                    sleep(50);
                }
            }
            LogUtils.d(TAG, "start systemInitOptionTable");
            this.callBack.initStateChange((int) R.string.init_system_option_db);
            this.mDataManager.systemInitOptionTable();
            LogUtils.d(TAG, "end systemInitOptionTable");
            sleep(50);
            LogUtils.d(TAG, "start systemInit");
            this.callBack.initStateChange((int) R.string.init_system);
            this.mDataManager.systemInit();
            LogUtils.d(TAG, "end systemInit");
            sleep(50);
            runOnMainThread(new Runnable() {
                public final void run() {
                    AppInitializeTask.this.lambda$initApplicationDatabase$2$AppInitializeTask();
                }
            });
            if (1 == this.mDataManager.getIntOption("AccessPersonalVerification", 0) && SharedPreferencesUtils.getInstance().getInt("version", 1) != 2) {
                SharedPreferencesUtils.getInstance().putInt("version", 2);
                Cursor cursor = null;
                try {
                    if (1 == this.mDataManager.getIntOption("AccessRuleType", 0)) {
                        cursor = this.mDataManager.queryBySql("update USER_INFO set Verify_Type = '-1' where Verify_Type = '0'");
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: initApplicationDatabase -> " + e2.getMessage());
        }
    }

    public /* synthetic */ void lambda$initApplicationDatabase$2$AppInitializeTask() {
        this.callBack.initDbOver(true);
    }

    private void copyApplicationConfig() {
        try {
            SharedPreferencesManager sharedPreferencesManager2 = new SharedPreferencesManager(this.mContext);
            this.sharedPreferencesManager = sharedPreferencesManager2;
            String str = sharedPreferencesManager2.get("isBDInitOver-cfg", "999");
            String str2 = this.sharedPreferencesManager.get("isBDInitOver-ZKDB.db", "999");
            String str3 = this.sharedPreferencesManager.get("deleteDataComplete", "999");
            try {
                FileLogUtils.writeLauncherInitRecord("AppInitializeTask: isBDInitOver-cfg = " + str + "; isBDInitOver-ZKDB.db = " + str2 + "; deleteDataComplete = " + str3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            copyFiles(this.mContext, str);
            if (str3.equals("1")) {
                deleteRecursive(new File("/sdcard/ZKTeco/data/biophoto"));
                deleteRecursive(new File("/sdcard/ZKTeco/data/capture"));
                deleteRecursive(new File("/sdcard/ZKTeco/data/databases"));
                deleteRecursive(new File("/sdcard/ZKTeco/data/photo"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: copyApplicationConfig -> " + e2.getMessage());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00d0 A[Catch:{ Exception -> 0x00f3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00e7 A[Catch:{ Exception -> 0x00f3 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean initPalm() {
        /*
            r8 = this;
            java.lang.String r0 = "/sdcard/zkpalmveinlic.txt"
            r1 = 0
            com.zktechnology.android.device.DeviceManager r2 = com.zktechnology.android.device.DeviceManager.getDefault()     // Catch:{ Exception -> 0x00f3 }
            boolean r2 = r2.isSupportPalm()     // Catch:{ Exception -> 0x00f3 }
            if (r2 != 0) goto L_0x000e
            return r1
        L_0x000e:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00f3 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x00f3 }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r3 = "palm: set eth0 license failed, ret="
            java.lang.String r4 = "掌纹许可: retLicense == "
            r5 = 1
            java.lang.String r6 = "/sdcard/zkpalmveinlic.dat"
            java.lang.String r7 = "AppInitializeTask"
            if (r2 == 0) goto L_0x0060
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00f3 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x00f3 }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x00f3 }
            if (r2 != 0) goto L_0x0060
            byte[] r0 = r8.readLicense(r0)     // Catch:{ Exception -> 0x00f3 }
            int r0 = com.zkteco.zkinfraredservice.irpalm.ZKPalmService12.setLicenseData(r5, r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r2.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00f3 }
            com.zktechnology.android.utils.LogUtils.log(r2)     // Catch:{ Exception -> 0x00f3 }
            if (r0 == 0) goto L_0x00a9
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r2.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00f3 }
            android.util.Log.d(r7, r0)     // Catch:{ Exception -> 0x00f3 }
            return r1
        L_0x0060:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00f3 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x00f3 }
            boolean r0 = r2.exists()     // Catch:{ Exception -> 0x00f3 }
            if (r0 != 0) goto L_0x00ed
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x00f3 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x00f3 }
            boolean r0 = r0.exists()     // Catch:{ Exception -> 0x00f3 }
            if (r0 == 0) goto L_0x00ed
            byte[] r0 = r8.readLicense(r6)     // Catch:{ Exception -> 0x00f3 }
            int r0 = com.zkteco.zkinfraredservice.irpalm.ZKPalmService12.setLicenseData(r5, r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r2.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00f3 }
            com.zktechnology.android.utils.LogUtils.log(r2)     // Catch:{ Exception -> 0x00f3 }
            if (r0 == 0) goto L_0x00a9
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r2.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00f3 }
            android.util.Log.d(r7, r0)     // Catch:{ Exception -> 0x00f3 }
            return r1
        L_0x00a9:
            java.lang.String r0 = "palm: set eth0 license success"
            android.util.Log.d(r7, r0)     // Catch:{ Exception -> 0x00f3 }
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x00f3 }
            r2 = 720(0x2d0, float:1.009E-42)
            r3 = 1280(0x500, float:1.794E-42)
            int r0 = com.zkteco.zkinfraredservice.irpalm.ZKPalmService12.init(r0, r2, r3)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r2.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r3 = "掌纹许可: retVal == "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00f3 }
            com.zktechnology.android.utils.LogUtils.log(r2)     // Catch:{ Exception -> 0x00f3 }
            if (r0 == 0) goto L_0x00e7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r2.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r3 = "palm: init failed, ret="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00f3 }
            android.util.Log.d(r7, r0)     // Catch:{ Exception -> 0x00f3 }
            return r1
        L_0x00e7:
            java.lang.String r0 = "palm: init success"
            android.util.Log.d(r7, r0)     // Catch:{ Exception -> 0x00f3 }
            return r5
        L_0x00ed:
            java.lang.String r0 = "掌纹许可文件不存在！"
            com.zktechnology.android.utils.LogUtils.log(r0)     // Catch:{ Exception -> 0x00f3 }
            return r1
        L_0x00f3:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Error: initPalm -> "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            com.zktechnology.android.push.util.FileLogUtils.writeLauncherInitRecord(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.AppInitializeTask.initPalm():boolean");
    }

    private byte[] readLicense(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            byte[] bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            fileInputStream.close();
            return bArr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDBPalm() {
        try {
            ZKPalmService12.dbClear();
            List<PersBiotemplate> query = new PersBiotemplate().getQueryBuilder().where().eq("bio_type", 8).query();
            HashMap hashMap = new HashMap();
            for (PersBiotemplate persBiotemplate : query) {
                int[] iArr = hashMap.containsKey(persBiotemplate.getUser_pin()) ? (int[]) hashMap.get(persBiotemplate.getUser_pin()) : new int[5];
                if (iArr != null && iArr.length > 0) {
                    iArr[persBiotemplate.getTemplate_no_index()] = persBiotemplate.getTemplate_id();
                    hashMap.put(persBiotemplate.getUser_pin(), iArr);
                }
            }
            for (String str : hashMap.keySet()) {
                int[] iArr2 = new int[2];
                iArr2[1] = 2048;
                iArr2[0] = 5;
                byte[][] bArr = (byte[][]) Array.newInstance(byte.class, iArr2);
                for (int i = 0; i < ((int[]) hashMap.get(str)).length; i++) {
                    bArr[i] = ((PersBiotemplatedata) new PersBiotemplatedata().queryForId(Long.valueOf((long) ((int[]) hashMap.get(str))[i]))).getTemplate_data();
                }
                byte[] bArr2 = new byte[ZKPalmService12.FIX_REG_TEMPLATE_LEN];
                int mergePalmTemplate = ZKBioTemplateService.mergePalmTemplate(bArr, bArr2);
                if (mergePalmTemplate == 0) {
                    ZKPalmService12.dbAdd(str, bArr2);
                } else {
                    Log.d(TAG, "setDBPlam mergePalmTemplate failed: " + mergePalmTemplate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: setPalmDb -> " + e.getMessage());
        }
    }

    private void setTime() {
        try {
            String strOption = DBManager.getInstance().getStrOption("timeFormat", "1");
            Intent intent = new Intent();
            if (strOption.equals("1")) {
                intent.putExtra("time_24", 24);
            } else {
                intent.putExtra("time_24", 12);
            }
            intent.setAction("com.zktechnology.android.state.time_24_changed");
            this.mContext.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: setTime -> " + e.getMessage());
        }
    }

    private void setIsUpgradeByClouds() {
        try {
            boolean z = false;
            int intOption = this.mDataManager.getIntOption(DBConfig.IS_UPGRADE_BY_CLOUDS, 0);
            Intent intent = new Intent();
            intent.setAction("com.zkteco.android.zkupgrade.UPGRADE_CONNECT_SERVICE");
            if (intOption != 0) {
                z = true;
            }
            intent.putExtra("needConnect", z);
            this.mContext.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: setTime -> " + e.getMessage());
        }
    }

    private void isFaceCountAndBioPhotoCountEqual() {
        try {
            int intOption = this.mDataManager.getIntOption(DBConfig.ABOUT_MAXFACE7COUNT, 0);
            if (this.mDataManager.getIntOption("~MaxBioPhotoCount", 0) != intOption) {
                this.mDataManager.setIntOption("~MaxBioPhotoCount", intOption);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: isFaceCountAndBioPhotoCountEqual -> " + e.getMessage());
        }
    }

    private boolean initDb(Context context) {
        try {
            DataManager dataManager = new DataManager();
            this.mDataManager = dataManager;
            boolean z = dataManager.init(context) == 0;
            if (z) {
                LogUtils.d(TAG, "1：数据库初始化 成功！");
            } else {
                LogUtils.e(TAG, "1: 数据库初始 失败！");
            }
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void copyFiles(Context context, String str) {
        if (!str.equals("1")) {
            this.sharedPreferencesManager.set("isBDInitOver-cfg", "1");
            FileLogUtils.writeLauncherInitRecord("copyFiles: isBDInitOver-cfg set to 1");
            LogUtils.d(TAG, "2：copy config 文件，完成！");
        } else {
            LogUtils.d(TAG, "2：config 文件已拷贝！");
        }
        FileUtils.copyAssets(context, "config/push", "sdcard/config/push");
        StrUtil.copyToFilesystem(context, "sdcard/config/push/tabledesc.xml", ZKFilePath.DATABASE_PATH + "pushconfig", "/tabledesc.xml");
        StrUtil.copyToFilesystem(context, "sdcard/config/push/acc.tabledesc.xml", ZKFilePath.DATABASE_PATH + "pushconfig", "/acc.tabledesc.xml");
        StrUtil.copyToFilesystem(context, "sdcard/config/push/standalonetabledesc.xml", ZKFilePath.DATABASE_PATH, "/standalonetabledesc.xml");
        StrUtil.copyToFilesystem(context, "sdcard/config/push/push.ini", ZKFilePath.DATABASE_PATH + "pushconfig", "/push.ini");
    }

    private void createFiles() {
        try {
            LogUtils.d(TAG, "3: start createFiles");
            checkPath(new File(ZKFilePath.APP_PATH));
            checkPath(new File(ZKFilePath.IMAGE_PATH));
            checkPath(new File(ZKFilePath.PICTURE_PATH));
            checkPath(new File(ZKFilePath.BLACK_LIST_PATH));
            checkPath(new File(ZKFilePath.ATT_PHOTO_PATH));
            checkPath(new File(ZKFilePath.WALLPAPER_PATH));
            checkPath(new File(ZKFilePath.LOGCAT_PATH));
            checkPath(new File(ZKFilePath.FACE_PIC_PATH));
            LogUtils.d(TAG, "3: end createFiles");
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: createFiles -> " + e.getMessage());
        }
    }

    private boolean openRFidDev(Context context) {
        try {
            LogUtils.d(TAG, "6: start init FingerAl");
            RfidManager rfidManager = new RfidManager(context);
            int i = 0;
            while (i < 3) {
                if (rfidManager.openDevice()) {
                    LogUtils.d(TAG, "6: start init FingerAl Success");
                    return true;
                }
                i++;
                sleep(1000);
                LogUtils.e(TAG, "5: 扫卡功能开启重试：%s 次", Integer.valueOf(i));
                if (i == 3) {
                    LogUtils.d(TAG, "6: start init FingerAl Failed");
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: openRFidDev -> " + e.getMessage());
            return false;
        }
    }

    private void initADB(DataManager dataManager) {
        try {
            String strOption = dataManager.getStrOption("AdbDebug", "0");
            if (Integer.parseInt(strOption) != Settings.Global.getInt(this.mContext.getContentResolver(), "adb_enabled", -1)) {
                Settings.Global.putInt(this.mContext.getContentResolver(), "adb_enabled", Integer.parseInt(strOption));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: initADB -> " + e.getMessage());
        }
    }

    private void initWiegand() {
        try {
            LogUtils.d(TAG, "12: start initWiegand");
            ZKWiegandManager.getInstance().init();
            LogUtils.d(TAG, "12: end initWiegand");
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: initWiegand -> " + e.getMessage());
        }
    }

    private void initHDR() {
        try {
            boolean z = true;
            int intOption = DBManager.getInstance().getIntOption(DBConfig.BIOMETRIC_IS_SUPPORT_WDR, 1);
            if (!String.valueOf(intOption).equals(getHDR())) {
                if (intOption != 1) {
                    z = false;
                }
                enabledHDR(z);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: initHDR -> " + e.getMessage());
        }
    }

    private void enabledHDR(boolean z) {
        Log.d(TAG, "enabledHDR: enabled = " + z);
        if (z) {
            try {
                Runtime.getRuntime().exec("setprop persist.camera.hdr 1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Runtime.getRuntime().exec("setprop persist.camera.hdr 0");
        }
    }

    private String getHDR() {
        String str = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop persist.camera.hdr").getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                str = readLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    private void initTimeZone() {
        LogUtils.d(TAG, "13：start initTimeZone");
        try {
            new TimeManager(this.mContext).setTimeZone(DBManager.getInstance().getStrOption("DefaultTimeZone", "Asia/Shanghai"));
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: initTimeZone -> " + e.getMessage());
        }
        LogUtils.d(TAG, "13：end initTimeZone");
    }

    private void checkPath(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void runOnMainThread(Runnable runnable) {
        MainThreadExecutor.getInstance().execute(runnable);
    }

    private void runInstallScript() {
        try {
            String str = Environment.getExternalStorageDirectory().toString() + "/ZKTeco/install";
            if (new File(str + "/script/post-install.sh").exists()) {
                runShellScript(str + "/script/post-install.sh");
            }
            FileUtils.deleteFile(new File(str));
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeLauncherInitRecord("Error: runInstallScript -> " + e.getMessage());
        }
    }

    private boolean runShellScript(String str) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/bin/sh", str});
            process.waitFor();
            if (process != null) {
                process.destroy();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (process != null) {
                process.destroy();
            }
            return false;
        } catch (Throwable unused) {
            if (process != null) {
                process.destroy();
            }
            return false;
        }
    }

    private static void deleteRecursive(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File deleteRecursive : file.listFiles()) {
                    deleteRecursive(deleteRecursive);
                }
            }
            file.delete();
        }
    }

    private void setScreenOffTimeoutNever() {
        if (!DeviceManager.getDefault().isH1()) {
            try {
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_off_timeout", DBManager.getInstance().getIntOption(ZKDBConfig.OPT_SCREEN_OFF_FORBIDDEN, 1) == 0 ? 300000 : Integer.MAX_VALUE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
