package com.zkteco.android.employeemgmt.activity.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.zktechnology.android.helper.FingerprintServiceHelper;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.receiver.OpLogReceiver;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.AccAttLog;
import com.zkteco.android.db.orm.tna.ExtUser;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.controller.StaffHomeController;
import com.zkteco.android.employeemgmt.model.ZKStaffBean;
import com.zkteco.android.employeemgmt.util.HasFaceUtils;
import com.zkteco.android.employeemgmt.util.SQLiteFaceUtils;
import com.zkteco.android.zkcore.utils.FileUtil;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import com.zkteco.edk.camera.lib.ZkThreadPoolManager;
import com.zkteco.liveface562.ZkFaceManager;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmService12;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StaffHomeController {
    private static final String DB_NAME = "ZKDB.db";
    private static final String TAG = "StaffHomeController";
    private int accessRuleType;
    private boolean fingerFunOn;
    private boolean hasFingerModule;
    private Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            super.handleMessage(message);
        }
    };
    private HubProtocolManager mHubProtocolManager;
    private OnListChangeListener mListener;
    private TemplateManager mTemplateManager;
    private int maxUserCount;
    private int sLockFunOn;

    public interface OnListChangeListener {
        void deleteFinish();

        void queryFinish(int i, List<ZKStaffBean> list);

        void startDelete();

        void userUpdate(int i);
    }

    private static void deletePhotos(UserInfo userInfo) {
    }

    public StaffHomeController(Context context, OnListChangeListener onListChangeListener) {
        this.mContext = context;
        this.mListener = onListChangeListener;
        init();
    }

    private void init() {
        this.mTemplateManager = new TemplateManager(this.mContext);
        this.mHubProtocolManager = new HubProtocolManager(this.mContext);
        boolean z = false;
        this.accessRuleType = DBManager.getInstance().getIntOption("AccessRuleType", 0);
        this.sLockFunOn = DBManager.getInstance().getIntOption("~LockFunOn", 0);
        this.fingerFunOn = DBManager.getInstance().getIntOption("FingerFunOn", 0) == 1;
        if (DBManager.getInstance().getIntOption("hasFingerModule", 0) == 1) {
            z = true;
        }
        this.hasFingerModule = z;
        this.maxUserCount = DBManager.getInstance().getIntOption("~MaxUserCount", 100) * 100;
        ZKLauncher.sPhotoFunOn = DBManager.getInstance().getStrOption("PhotoFunOn", "0");
        ZKLauncher.sSaveBioPhotoFunOn = DBManager.getInstance().getStrOption("SaveBioPhotoFunOn", "1");
    }

    public void deleteItems(List<ZKStaffBean> list, boolean z, Activity activity) {
        OnListChangeListener onListChangeListener = this.mListener;
        if (onListChangeListener != null) {
            ZkThreadPoolManager.getInstance().execute(new Runnable(onListChangeListener, list, z, activity) {
                public final /* synthetic */ StaffHomeController.OnListChangeListener f$1;
                public final /* synthetic */ List f$2;
                public final /* synthetic */ boolean f$3;
                public final /* synthetic */ Activity f$4;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                    this.f$4 = r5;
                }

                public final void run() {
                    StaffHomeController.this.lambda$deleteItems$0$StaffHomeController(this.f$1, this.f$2, this.f$3, this.f$4);
                }
            });
        }
    }

    public /* synthetic */ void lambda$deleteItems$0$StaffHomeController(OnListChangeListener onListChangeListener, List list, boolean z, Activity activity) {
        Handler handler = this.mHandler;
        Objects.requireNonNull(onListChangeListener);
        handler.post(new Runnable() {
            public final void run() {
                StaffHomeController.OnListChangeListener.this.startDelete();
            }
        });
        ArrayList<UserInfo> arrayList = new ArrayList<>();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ZKStaffBean zKStaffBean = (ZKStaffBean) it.next();
            if (zKStaffBean.isCkSelected()) {
                arrayList.add(zKStaffBean.getUserInfo());
            }
        }
        int i = 0;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        for (UserInfo deleteUser : arrayList) {
            i++;
            deleteUser(deleteUser, z, activity);
        }
        Log.e(TAG, "deleteItems: 删除" + i + "个用户结束,共耗时: " + (SystemClock.elapsedRealtime() - elapsedRealtime));
        Handler handler2 = this.mHandler;
        Objects.requireNonNull(onListChangeListener);
        handler2.post(new Runnable() {
            public final void run() {
                StaffHomeController.OnListChangeListener.this.deleteFinish();
            }
        });
    }

    public boolean isAccess() {
        return this.accessRuleType == 1;
    }

    public long getMaxUserCount() {
        return (long) this.maxUserCount;
    }

    public static void deleteSingleUser(UserInfo userInfo, TemplateManager templateManager) throws SQLException {
        deleteFingerprint(userInfo, templateManager);
        deletePhotos(userInfo);
        deleteTemplates(userInfo);
        deleteUserInfo(userInfo);
    }

    private static void deleteUserInfo(UserInfo userInfo) {
        deleteExtUserByPIN(userInfo.getUser_PIN(), DBManager.getInstance());
        deleteUserByPIN(userInfo.getUser_PIN(), DBManager.getInstance());
    }

    private static void deleteTemplates(UserInfo userInfo) {
        SQLiteFaceUtils.deleteFaceOrPalmTemplate(userInfo.getUser_PIN(), 9);
        SQLiteFaceUtils.deleteFaceOrPalmTemplate(userInfo.getUser_PIN(), 8);
        ZKPalmService12.dbDel(userInfo.getUser_PIN());
        ZkFaceManager.getInstance().dbDel(userInfo.getUser_PIN());
    }

    private static void deleteFingerprint(UserInfo userInfo, TemplateManager templateManager) throws SQLException {
        if (userInfo != null) {
            for (FpTemplate10 next : templateManager.getFingerTemplateForUserPin(String.valueOf(userInfo.getID()))) {
                next.delete();
                FingerprintServiceHelper.getInstance().deleteTemplate(userInfo.getID() + "_" + next.getFingerid());
            }
        }
    }

    private void deleteUser(UserInfo userInfo, boolean z, Activity activity) {
        try {
            UserInfo userInfo2 = (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", userInfo.getUser_PIN()).queryForFirst();
            if (userInfo2 == null) {
                Log.d("user", "deleteItem: userInfo == null " + userInfo.getUser_PIN());
                return;
            }
            Log.d("user", "deleteItem: userInfo.getID = " + userInfo2.getID());
            if (FileUtil.deleteFile(ZKFilePath.PICTURE_PATH + userInfo2.getUser_PIN() + ZKFilePath.SUFFIX_IMAGE, activity)) {
                deleteMessageQueueByPIN(userInfo2.getUser_PIN(), DBManager.getInstance());
            }
            FileUtil.deleteFile(ZKFilePath.FACE_PATH + userInfo2.getUser_PIN() + ZKFilePath.SUFFIX_IMAGE, activity);
            if (z) {
                deleteAttLogByPIN(userInfo2.getUser_PIN(), DBManager.getInstance());
                deleteFileByUserPin(new File(ZKFilePath.ATT_PHOTO_PATH.substring(0, ZKFilePath.ATT_PHOTO_PATH.length() - 1)), userInfo2.getUser_PIN());
                deletePhotoIndexByPIN(userInfo2.getUser_PIN(), DBManager.getInstance());
            }
            if (z && this.accessRuleType == 1 && this.sLockFunOn == 15) {
                deleteAccLogByPIN(userInfo2.getUser_PIN(), DBManager.getInstance());
            }
            deleteAccUserAuthorizerByPIN(userInfo2.getUser_PIN(), DBManager.getInstance());
            deleteSingleUser(userInfo2, this.mTemplateManager);
            this.mContext.sendBroadcast(new Intent(OpLogReceiver.ACTION_DELETE_USER).putExtra("OpWho", userInfo2.getUser_PIN()));
            sendHub();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:107:0x00ed A[SYNTHETIC, Splitter:B:107:0x00ed] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x00fd A[SYNTHETIC, Splitter:B:114:0x00fd] */
    /* JADX WARNING: Removed duplicated region for block: B:120:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:121:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x002e A[SYNTHETIC, Splitter:B:20:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0049 A[SYNTHETIC, Splitter:B:30:0x0049] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x005d A[SYNTHETIC, Splitter:B:41:0x005d] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0078 A[SYNTHETIC, Splitter:B:51:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x008c A[SYNTHETIC, Splitter:B:62:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x009c A[SYNTHETIC, Splitter:B:69:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x00ac A[SYNTHETIC, Splitter:B:77:0x00ac] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x00bc A[SYNTHETIC, Splitter:B:85:0x00bc] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x00d9 A[SYNTHETIC, Splitter:B:96:0x00d9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendHub() {
        /*
            r7 = this;
            int r0 = r7.accessRuleType
            r1 = 0
            if (r0 != 0) goto L_0x00c7
            r0 = 19
            com.zkteco.android.core.sdk.HubProtocolManager r3 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x0025, all -> 0x0021 }
            long r3 = r3.convertPushInit()     // Catch:{ Exception -> 0x0025, all -> 0x0021 }
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x001f }
            java.lang.String r6 = "TableName=USER_INFO"
            r5.sendHubAction(r0, r3, r6)     // Catch:{ Exception -> 0x001f }
            int r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r5 == 0) goto L_0x0038
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x0034 }
            r5.convertPushFree(r3)     // Catch:{ Exception -> 0x0034 }
            goto L_0x0038
        L_0x001f:
            r5 = move-exception
            goto L_0x0027
        L_0x0021:
            r0 = move-exception
            r3 = r1
            goto L_0x00b8
        L_0x0025:
            r5 = move-exception
            r3 = r1
        L_0x0027:
            r5.printStackTrace()     // Catch:{ all -> 0x00b7 }
            int r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r5 == 0) goto L_0x0038
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x0034 }
            r5.convertPushFree(r3)     // Catch:{ Exception -> 0x0034 }
            goto L_0x0038
        L_0x0034:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0038:
            com.zkteco.android.core.sdk.HubProtocolManager r3 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x0054, all -> 0x0051 }
            long r3 = r3.convertPushInit()     // Catch:{ Exception -> 0x0054, all -> 0x0051 }
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x004f }
            java.lang.String r6 = "TableName=Pers_BioTemplate"
            r5.sendHubAction(r0, r3, r6)     // Catch:{ Exception -> 0x004f }
            int r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r5 == 0) goto L_0x0067
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x0063 }
            r5.convertPushFree(r3)     // Catch:{ Exception -> 0x0063 }
            goto L_0x0067
        L_0x004f:
            r5 = move-exception
            goto L_0x0056
        L_0x0051:
            r0 = move-exception
            r3 = r1
            goto L_0x00a8
        L_0x0054:
            r5 = move-exception
            r3 = r1
        L_0x0056:
            r5.printStackTrace()     // Catch:{ all -> 0x00a7 }
            int r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r5 == 0) goto L_0x0067
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x0063 }
            r5.convertPushFree(r3)     // Catch:{ Exception -> 0x0063 }
            goto L_0x0067
        L_0x0063:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0067:
            com.zkteco.android.core.sdk.HubProtocolManager r3 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x0083, all -> 0x0080 }
            long r3 = r3.convertPushInit()     // Catch:{ Exception -> 0x0083, all -> 0x0080 }
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x007e }
            java.lang.String r6 = "TableName=Pers_BioTemplateData"
            r5.sendHubAction(r0, r3, r6)     // Catch:{ Exception -> 0x007e }
            int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x00c7
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x0092 }
            r0.convertPushFree(r3)     // Catch:{ Exception -> 0x0092 }
            goto L_0x00c7
        L_0x007e:
            r0 = move-exception
            goto L_0x0085
        L_0x0080:
            r0 = move-exception
            r3 = r1
            goto L_0x0098
        L_0x0083:
            r0 = move-exception
            r3 = r1
        L_0x0085:
            r0.printStackTrace()     // Catch:{ all -> 0x0097 }
            int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x00c7
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x0092 }
            r0.convertPushFree(r3)     // Catch:{ Exception -> 0x0092 }
            goto L_0x00c7
        L_0x0092:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00c7
        L_0x0097:
            r0 = move-exception
        L_0x0098:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x00a6
            com.zkteco.android.core.sdk.HubProtocolManager r1 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x00a2 }
            r1.convertPushFree(r3)     // Catch:{ Exception -> 0x00a2 }
            goto L_0x00a6
        L_0x00a2:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00a6:
            throw r0
        L_0x00a7:
            r0 = move-exception
        L_0x00a8:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x00b6
            com.zkteco.android.core.sdk.HubProtocolManager r1 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x00b2 }
            r1.convertPushFree(r3)     // Catch:{ Exception -> 0x00b2 }
            goto L_0x00b6
        L_0x00b2:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00b6:
            throw r0
        L_0x00b7:
            r0 = move-exception
        L_0x00b8:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x00c6
            com.zkteco.android.core.sdk.HubProtocolManager r1 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x00c2 }
            r1.convertPushFree(r3)     // Catch:{ Exception -> 0x00c2 }
            goto L_0x00c6
        L_0x00c2:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00c6:
            throw r0
        L_0x00c7:
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x00e4, all -> 0x00e1 }
            long r3 = r0.convertPushInit()     // Catch:{ Exception -> 0x00e4, all -> 0x00e1 }
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x00df }
            r5 = 25
            r6 = 0
            r0.sendHubAction(r5, r3, r6)     // Catch:{ Exception -> 0x00df }
            int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x00f7
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x00f3 }
            r0.convertPushFree(r3)     // Catch:{ Exception -> 0x00f3 }
            goto L_0x00f7
        L_0x00df:
            r0 = move-exception
            goto L_0x00e6
        L_0x00e1:
            r0 = move-exception
            r3 = r1
            goto L_0x00f9
        L_0x00e4:
            r0 = move-exception
            r3 = r1
        L_0x00e6:
            r0.printStackTrace()     // Catch:{ all -> 0x00f8 }
            int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x00f7
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x00f3 }
            r0.convertPushFree(r3)     // Catch:{ Exception -> 0x00f3 }
            goto L_0x00f7
        L_0x00f3:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00f7:
            return
        L_0x00f8:
            r0 = move-exception
        L_0x00f9:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0107
            com.zkteco.android.core.sdk.HubProtocolManager r1 = r7.mHubProtocolManager     // Catch:{ Exception -> 0x0103 }
            r1.convertPushFree(r3)     // Catch:{ Exception -> 0x0103 }
            goto L_0x0107
        L_0x0103:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0107:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.controller.StaffHomeController.sendHub():void");
    }

    private static void deleteMessageQueueByPIN(String str, DataManager dataManager) {
        dataManager.executeSql(DB_NAME, "DELETE FROM MESSAGE_QUEUE WHERE TABLE_KEY = ?", new Object[]{"ID=" + str});
    }

    private static void deleteAttLogByPIN(String str, DataManager dataManager) {
        dataManager.executeSql(DB_NAME, "DELETE FROM ATT_LOG WHERE User_PIN = ?", new Object[]{str});
    }

    private static void deletePhotoIndexByPIN(String str, DataManager dataManager) {
        dataManager.executeSql(DB_NAME, "DELETE FROM PHOTO_INDEX WHERE User_PIN = ?", new Object[]{str});
    }

    private static void deleteAccLogByPIN(String str, DataManager dataManager) {
        dataManager.executeSql(DB_NAME, "DELETE FROM ACC_ATT_LOG WHERE UserPIN = ?", new Object[]{str});
    }

    private static void deleteAccUserAuthorizerByPIN(String str, DataManager dataManager) {
        dataManager.executeSql(DB_NAME, "DELETE FROM ACC_USER_AUTHORIZE WHERE UserPIN = ?", new Object[]{str});
    }

    private static void deleteExtUserByPIN(String str, DataManager dataManager) {
        dataManager.executeSql(DB_NAME, "DELETE FROM ExtUser WHERE Pin = ?", new Object[]{str});
    }

    public static void deleteFileByUserPin(File file, String str) {
        for (File file2 : file.listFiles()) {
            if (file2.isDirectory()) {
                deleteFileByUserPin(file2, str);
            } else if (file2.getName().substring(file2.getName().indexOf("-") + 1, file2.getName().indexOf(".")).equals(str)) {
                file2.delete();
            }
        }
    }

    private static void deleteAccLog(String str) {
        try {
            List<AccAttLog> query = new AccAttLog().getQueryBuilder().where().eq("UserPIN", str).query();
            if (query.size() > 0) {
                for (AccAttLog delete : query) {
                    delete.delete();
                }
            }
        } catch (SQLException unused) {
        }
    }

    private static void deleteUserByPIN(String str, DataManager dataManager) {
        dataManager.executeSql(DB_NAME, "DELETE FROM USER_INFO WHERE User_PIN = ?", new Object[]{str});
        Log.d("deleteUser", "deleteUser: " + str);
    }

    public void query(String str, long j, int i) {
        OnListChangeListener onListChangeListener = this.mListener;
        if (onListChangeListener != null) {
            ZkThreadPoolManager.getInstance().execute(new Runnable(str, j, i, onListChangeListener) {
                public final /* synthetic */ String f$1;
                public final /* synthetic */ long f$2;
                public final /* synthetic */ int f$3;
                public final /* synthetic */ StaffHomeController.OnListChangeListener f$4;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r5;
                    this.f$4 = r6;
                }

                public final void run() {
                    StaffHomeController.this.lambda$query$2$StaffHomeController(this.f$1, this.f$2, this.f$3, this.f$4);
                }
            });
        }
    }

    public /* synthetic */ void lambda$query$2$StaffHomeController(String str, long j, int i, OnListChangeListener onListChangeListener) {
        this.mHandler.post(new Runnable(i, queryInternal(str, j, i)) {
            public final /* synthetic */ int f$1;
            public final /* synthetic */ List f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                StaffHomeController.OnListChangeListener.this.queryFinish(this.f$1, this.f$2);
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0408, code lost:
        r15 = r31;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0261, code lost:
        if (r1 != null) goto L_0x0278;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0276, code lost:
        if (r1 == null) goto L_0x027b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0278, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x027b, code lost:
        r1 = r19;
        android.util.Log.e(r1, "queryInternal: 单纯查询用户耗时: " + (android.os.SystemClock.elapsedRealtime() - r20));
        r3 = new java.util.ArrayList();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x02a2, code lost:
        if (r2.isEmpty() != false) goto L_0x0408;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x02a4, code lost:
        r4 = java.util.concurrent.Executors.newFixedThreadPool(3);
        r6 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.zkteco.android.employeemgmt.model.ZKStaffBean> queryInternal(java.lang.String r32, long r33, int r35) {
        /*
            r31 = this;
            r0 = r32
            r1 = r33
            r3 = r35
            java.lang.String r4 = "&"
            java.lang.String r5 = ", "
            java.lang.String r6 = "StaffHomeController"
            long r7 = android.os.SystemClock.elapsedRealtime()
            com.zkteco.android.db.orm.manager.DataManager r9 = com.zktechnology.android.utils.DBManager.getInstance()
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            int r11 = com.zktechnology.android.launcher2.ZKLauncher.longName
            java.lang.String r12 = ""
            r13 = 1
            if (r11 != r13) goto L_0x0064
            int r11 = com.zktechnology.android.launcher2.ZKLauncher.sAccessRuleType
            if (r11 != r13) goto L_0x0064
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r14 = "select * from USER_INFO As us        left join ExtUser As eu on us.User_PIN = eu.Pin            where                us.User_PIN LIKE '%"
            java.lang.StringBuilder r11 = r11.append(r14)
            java.lang.StringBuilder r11 = r11.append(r0)
            java.lang.String r14 = "%'            or                eu.FirstName LIKE '%"
            java.lang.StringBuilder r11 = r11.append(r14)
            java.lang.StringBuilder r11 = r11.append(r0)
            java.lang.String r14 = "%'            or                eu.LastName LIKE '%"
            java.lang.StringBuilder r11 = r11.append(r14)
            java.lang.StringBuilder r0 = r11.append(r0)
            java.lang.String r11 = "%'        order by USER_PIN asc            limit "
            java.lang.StringBuilder r0 = r0.append(r11)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = "            offset "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r12)
            java.lang.String r0 = r0.toString()
            goto L_0x0099
        L_0x0064:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r14 = "select * from USER_INFO    where        USER_INFO.Name LIKE  '%"
            java.lang.StringBuilder r11 = r11.append(r14)
            java.lang.StringBuilder r11 = r11.append(r0)
            java.lang.String r14 = "%'    or        USER_INFO.User_PIN LIKE '%"
            java.lang.StringBuilder r11 = r11.append(r14)
            java.lang.StringBuilder r0 = r11.append(r0)
            java.lang.String r11 = "%'    order by USER_PIN asc        limit "
            java.lang.StringBuilder r0 = r0.append(r11)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = "        offset "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r12)
            java.lang.String r0 = r0.toString()
        L_0x0099:
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0269 }
            r2.<init>()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r3 = "subscribe: "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x0269 }
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0269 }
            android.util.Log.d(r6, r2)     // Catch:{ Exception -> 0x0269 }
            android.database.Cursor r1 = r9.queryBySql(r0)     // Catch:{ Exception -> 0x0269 }
            boolean r0 = r1.moveToFirst()     // Catch:{ Exception -> 0x0269 }
            if (r0 == 0) goto L_0x0258
        L_0x00ba:
            boolean r0 = r1.isAfterLast()     // Catch:{ Exception -> 0x0269 }
            if (r0 != 0) goto L_0x0258
            java.lang.String r0 = "ID"
            int r0 = r1.getColumnIndex(r0)     // Catch:{ Exception -> 0x0269 }
            long r2 = r1.getLong(r0)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r0 = "User_PIN"
            int r0 = r1.getColumnIndex(r0)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r0 = r1.getString(r0)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r9 = "Privilege"
            int r9 = r1.getColumnIndex(r9)     // Catch:{ Exception -> 0x0269 }
            int r9 = r1.getInt(r9)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r11 = "Name"
            int r11 = r1.getColumnIndex(r11)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r11 = r1.getString(r11)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r14 = "Password"
            int r14 = r1.getColumnIndex(r14)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r14 = r1.getString(r14)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r15 = "Face_Group_ID"
            int r15 = r1.getColumnIndex(r15)     // Catch:{ Exception -> 0x0269 }
            int r15 = r1.getInt(r15)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r13 = "Acc_Group_ID"
            int r13 = r1.getColumnIndex(r13)     // Catch:{ Exception -> 0x0269 }
            int r13 = r1.getInt(r13)     // Catch:{ Exception -> 0x0269 }
            r17 = r5
            java.lang.String r5 = "Dept_ID"
            int r5 = r1.getColumnIndex(r5)     // Catch:{ Exception -> 0x0256 }
            int r5 = r1.getInt(r5)     // Catch:{ Exception -> 0x0256 }
            r18 = r12
            java.lang.String r12 = "Is_Group_TZ"
            int r12 = r1.getColumnIndex(r12)     // Catch:{ Exception -> 0x024f }
            int r12 = r1.getInt(r12)     // Catch:{ Exception -> 0x024f }
            r19 = r6
            java.lang.String r6 = "Verify_Type"
            int r6 = r1.getColumnIndex(r6)     // Catch:{ Exception -> 0x024d }
            int r6 = r1.getInt(r6)     // Catch:{ Exception -> 0x024d }
            r20 = r7
            java.lang.String r7 = "Main_Card"
            int r7 = r1.getColumnIndex(r7)     // Catch:{ Exception -> 0x024b }
            java.lang.String r7 = r1.getString(r7)     // Catch:{ Exception -> 0x024b }
            java.lang.String r8 = "Vice_Card"
            int r8 = r1.getColumnIndex(r8)     // Catch:{ Exception -> 0x024b }
            java.lang.String r8 = r1.getString(r8)     // Catch:{ Exception -> 0x024b }
            r22 = r10
            java.lang.String r10 = "Expires"
            int r10 = r1.getColumnIndex(r10)     // Catch:{ Exception -> 0x0247 }
            int r10 = r1.getInt(r10)     // Catch:{ Exception -> 0x0247 }
            r32 = r2
            java.lang.String r2 = "StartDatetime"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Exception -> 0x0247 }
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x0247 }
            java.lang.String r3 = "EndDatetime"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Exception -> 0x0247 }
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Exception -> 0x0247 }
            r34 = r3
            java.lang.String r3 = "VaildCount"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Exception -> 0x0247 }
            int r3 = r1.getInt(r3)     // Catch:{ Exception -> 0x0247 }
            r35 = r3
            java.lang.String r3 = "Timezone1"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Exception -> 0x0247 }
            int r3 = r1.getInt(r3)     // Catch:{ Exception -> 0x0247 }
            r23 = r3
            java.lang.String r3 = "Timezone2"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Exception -> 0x0247 }
            int r3 = r1.getInt(r3)     // Catch:{ Exception -> 0x0247 }
            r24 = r3
            java.lang.String r3 = "Timezone3"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Exception -> 0x0247 }
            int r3 = r1.getInt(r3)     // Catch:{ Exception -> 0x0247 }
            r25 = r3
            com.zkteco.android.db.orm.tna.UserInfo r3 = new com.zkteco.android.db.orm.tna.UserInfo     // Catch:{ Exception -> 0x0247 }
            r3.<init>()     // Catch:{ Exception -> 0x0247 }
            r3.setUser_PIN(r0)     // Catch:{ Exception -> 0x0247 }
            r3.setPrivilege(r9)     // Catch:{ Exception -> 0x0247 }
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sAccessRuleType     // Catch:{ Exception -> 0x0247 }
            java.lang.String r9 = " "
            r26 = r2
            r2 = 1
            if (r0 != r2) goto L_0x01e0
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.longName     // Catch:{ Exception -> 0x0247 }
            if (r0 != r2) goto L_0x01e0
            java.lang.String r0 = "FirstName"
            int r0 = r1.getColumnIndex(r0)     // Catch:{ Exception -> 0x0247 }
            java.lang.String r0 = r1.getString(r0)     // Catch:{ Exception -> 0x0247 }
            java.lang.String r2 = "LastName"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Exception -> 0x0247 }
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x0247 }
            if (r0 == 0) goto L_0x01c3
            goto L_0x01c5
        L_0x01c3:
            r0 = r18
        L_0x01c5:
            if (r2 == 0) goto L_0x01dc
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0247 }
            r11.<init>()     // Catch:{ Exception -> 0x0247 }
            java.lang.StringBuilder r0 = r11.append(r0)     // Catch:{ Exception -> 0x0247 }
            java.lang.StringBuilder r0 = r0.append(r9)     // Catch:{ Exception -> 0x0247 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Exception -> 0x0247 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0247 }
        L_0x01dc:
            r3.setName(r0)     // Catch:{ Exception -> 0x0247 }
            goto L_0x01ef
        L_0x01e0:
            if (r11 == 0) goto L_0x01ec
            boolean r0 = r11.contains(r4)     // Catch:{ Exception -> 0x0247 }
            if (r0 == 0) goto L_0x01ec
            java.lang.String r11 = r11.replace(r4, r9)     // Catch:{ Exception -> 0x0247 }
        L_0x01ec:
            r3.setName(r11)     // Catch:{ Exception -> 0x0247 }
        L_0x01ef:
            r3.setPassword(r14)     // Catch:{ Exception -> 0x0247 }
            r3.setFace_Group_ID(r15)     // Catch:{ Exception -> 0x0247 }
            r3.setAcc_Group_ID(r13)     // Catch:{ Exception -> 0x0247 }
            r3.setDept_ID(r5)     // Catch:{ Exception -> 0x0247 }
            r3.setIs_Group_TZ(r12)     // Catch:{ Exception -> 0x0247 }
            r3.setVerify_Type(r6)     // Catch:{ Exception -> 0x0247 }
            r3.setMain_Card(r7)     // Catch:{ Exception -> 0x0247 }
            r3.setVice_Card(r8)     // Catch:{ Exception -> 0x0247 }
            r3.setExpires(r10)     // Catch:{ Exception -> 0x0247 }
            r0 = r26
            r3.setStartDatetime(r0)     // Catch:{ Exception -> 0x0247 }
            r0 = r34
            r3.setEndDatetime(r0)     // Catch:{ Exception -> 0x0247 }
            r0 = r35
            r3.setVaildCount(r0)     // Catch:{ Exception -> 0x0247 }
            r0 = r23
            r3.setTimezone1(r0)     // Catch:{ Exception -> 0x0247 }
            r0 = r24
            r3.setTimezone2(r0)     // Catch:{ Exception -> 0x0247 }
            r0 = r25
            r3.setTimezone3(r0)     // Catch:{ Exception -> 0x0247 }
            android.util.Pair r0 = new android.util.Pair     // Catch:{ Exception -> 0x0247 }
            java.lang.Long r2 = java.lang.Long.valueOf(r32)     // Catch:{ Exception -> 0x0247 }
            r0.<init>(r2, r3)     // Catch:{ Exception -> 0x0247 }
            r2 = r22
            r2.add(r0)     // Catch:{ Exception -> 0x0245 }
            r1.moveToNext()     // Catch:{ Exception -> 0x0245 }
            r10 = r2
            r5 = r17
            r12 = r18
            r6 = r19
            r7 = r20
            r13 = 1
            goto L_0x00ba
        L_0x0245:
            r0 = move-exception
            goto L_0x0273
        L_0x0247:
            r0 = move-exception
            r2 = r22
            goto L_0x0273
        L_0x024b:
            r0 = move-exception
            goto L_0x0254
        L_0x024d:
            r0 = move-exception
            goto L_0x0252
        L_0x024f:
            r0 = move-exception
            r19 = r6
        L_0x0252:
            r20 = r7
        L_0x0254:
            r2 = r10
            goto L_0x0273
        L_0x0256:
            r0 = move-exception
            goto L_0x026c
        L_0x0258:
            r17 = r5
            r19 = r6
            r20 = r7
            r2 = r10
            r18 = r12
            if (r1 == 0) goto L_0x027b
            goto L_0x0278
        L_0x0264:
            r0 = move-exception
            r15 = r31
            goto L_0x0427
        L_0x0269:
            r0 = move-exception
            r17 = r5
        L_0x026c:
            r19 = r6
            r20 = r7
            r2 = r10
            r18 = r12
        L_0x0273:
            r0.printStackTrace()     // Catch:{ all -> 0x0264 }
            if (r1 == 0) goto L_0x027b
        L_0x0278:
            r1.close()
        L_0x027b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "queryInternal: 单纯查询用户耗时: "
            java.lang.StringBuilder r0 = r0.append(r1)
            long r3 = android.os.SystemClock.elapsedRealtime()
            long r3 = r3 - r20
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r0 = r0.toString()
            r1 = r19
            android.util.Log.e(r1, r0)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            boolean r0 = r2.isEmpty()
            if (r0 != 0) goto L_0x0408
            r0 = 3
            java.util.concurrent.ExecutorService r4 = java.util.concurrent.Executors.newFixedThreadPool(r0)
            r6 = 0
        L_0x02aa:
            int r0 = r2.size()
            if (r6 >= r0) goto L_0x0402
            java.lang.Object r0 = r2.get(r6)
            android.util.Pair r0 = (android.util.Pair) r0
            java.lang.Object r0 = r0.first
            java.lang.Long r0 = (java.lang.Long) r0
            long r7 = r0.longValue()
            java.lang.Object r0 = r2.get(r6)
            android.util.Pair r0 = (android.util.Pair) r0
            java.lang.Object r0 = r0.second
            r9 = r0
            com.zkteco.android.db.orm.tna.UserInfo r9 = (com.zkteco.android.db.orm.tna.UserInfo) r9
            long r10 = android.os.SystemClock.elapsedRealtime()
            com.zkteco.android.employeemgmt.activity.controller.-$$Lambda$StaffHomeController$ZwqOotZrHAKRQ5cUhny8e6QIkpk r0 = new com.zkteco.android.employeemgmt.activity.controller.-$$Lambda$StaffHomeController$ZwqOotZrHAKRQ5cUhny8e6QIkpk
            r0.<init>()
            java.util.concurrent.Future r0 = r4.submit(r0)
            long r12 = android.os.SystemClock.elapsedRealtime()
            com.zkteco.android.employeemgmt.activity.controller.-$$Lambda$StaffHomeController$myr3fFem4DuDXNx77yv9uXVkmkU r14 = new com.zkteco.android.employeemgmt.activity.controller.-$$Lambda$StaffHomeController$myr3fFem4DuDXNx77yv9uXVkmkU
            r15 = r31
            r14.<init>(r7)
            java.util.concurrent.Future r7 = r4.submit(r14)
            long r22 = android.os.SystemClock.elapsedRealtime()
            com.zkteco.android.employeemgmt.activity.controller.-$$Lambda$StaffHomeController$KBa7v-EZrGqJ0wKbhAyNpWfOSKU r8 = new com.zkteco.android.employeemgmt.activity.controller.-$$Lambda$StaffHomeController$KBa7v-EZrGqJ0wKbhAyNpWfOSKU
            r8.<init>()
            java.util.concurrent.Future r8 = r4.submit(r8)
            java.lang.String r14 = r9.getMain_Card()
            if (r14 == 0) goto L_0x030b
            java.lang.String r5 = "0"
            boolean r5 = r5.equals(r14)
            if (r5 != 0) goto L_0x030b
            r5 = r18
            boolean r14 = r5.equals(r14)
            if (r14 != 0) goto L_0x030d
            r27 = 1
            goto L_0x030f
        L_0x030b:
            r5 = r18
        L_0x030d:
            r27 = 0
        L_0x030f:
            java.lang.String r14 = r9.getPassword()
            boolean r14 = android.text.TextUtils.isEmpty(r14)
            r16 = 1
            r28 = r14 ^ 1
            java.lang.Object r0 = r0.get()     // Catch:{ ExecutionException -> 0x03d7, InterruptedException -> 0x03d5 }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ ExecutionException -> 0x03d7, InterruptedException -> 0x03d5 }
            boolean r14 = r0.booleanValue()     // Catch:{ ExecutionException -> 0x03d7, InterruptedException -> 0x03d5 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ExecutionException -> 0x03cd, InterruptedException -> 0x03cb }
            r0.<init>()     // Catch:{ ExecutionException -> 0x03cd, InterruptedException -> 0x03cb }
            r18 = r2
            java.lang.String r2 = "queryInternal: 人脸模板耗时: "
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ ExecutionException -> 0x03c9, InterruptedException -> 0x03c7 }
            long r24 = android.os.SystemClock.elapsedRealtime()     // Catch:{ ExecutionException -> 0x03c9, InterruptedException -> 0x03c7 }
            long r10 = r24 - r10
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ ExecutionException -> 0x03c9, InterruptedException -> 0x03c7 }
            r2 = r17
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ ExecutionException -> 0x03c5, InterruptedException -> 0x03c3 }
            long r10 = android.os.SystemClock.elapsedRealtime()     // Catch:{ ExecutionException -> 0x03c5, InterruptedException -> 0x03c3 }
            long r10 = r10 - r20
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ ExecutionException -> 0x03c5, InterruptedException -> 0x03c3 }
            java.lang.String r0 = r0.toString()     // Catch:{ ExecutionException -> 0x03c5, InterruptedException -> 0x03c3 }
            android.util.Log.e(r1, r0)     // Catch:{ ExecutionException -> 0x03c5, InterruptedException -> 0x03c3 }
            java.lang.Object r0 = r7.get()     // Catch:{ ExecutionException -> 0x03c5, InterruptedException -> 0x03c3 }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ ExecutionException -> 0x03c5, InterruptedException -> 0x03c3 }
            boolean r7 = r0.booleanValue()     // Catch:{ ExecutionException -> 0x03c5, InterruptedException -> 0x03c3 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            r0.<init>()     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            java.lang.String r10 = "queryInternal: 指纹模板耗时: "
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            long r10 = android.os.SystemClock.elapsedRealtime()     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            long r10 = r10 - r12
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            long r10 = android.os.SystemClock.elapsedRealtime()     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            long r10 = r10 - r20
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            java.lang.String r0 = r0.toString()     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            android.util.Log.e(r1, r0)     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            java.lang.Object r0 = r8.get()     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            boolean r8 = r0.booleanValue()     // Catch:{ ExecutionException -> 0x03c1, InterruptedException -> 0x03bf }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ExecutionException -> 0x03bd, InterruptedException -> 0x03bb }
            r0.<init>()     // Catch:{ ExecutionException -> 0x03bd, InterruptedException -> 0x03bb }
            java.lang.String r10 = "queryInternal: 掌纹模板耗时: "
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ ExecutionException -> 0x03bd, InterruptedException -> 0x03bb }
            long r10 = android.os.SystemClock.elapsedRealtime()     // Catch:{ ExecutionException -> 0x03bd, InterruptedException -> 0x03bb }
            long r10 = r10 - r22
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ ExecutionException -> 0x03bd, InterruptedException -> 0x03bb }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ ExecutionException -> 0x03bd, InterruptedException -> 0x03bb }
            long r10 = android.os.SystemClock.elapsedRealtime()     // Catch:{ ExecutionException -> 0x03bd, InterruptedException -> 0x03bb }
            long r10 = r10 - r20
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ ExecutionException -> 0x03bd, InterruptedException -> 0x03bb }
            java.lang.String r0 = r0.toString()     // Catch:{ ExecutionException -> 0x03bd, InterruptedException -> 0x03bb }
            android.util.Log.e(r1, r0)     // Catch:{ ExecutionException -> 0x03bd, InterruptedException -> 0x03bb }
            goto L_0x03e2
        L_0x03bb:
            r0 = move-exception
            goto L_0x03df
        L_0x03bd:
            r0 = move-exception
            goto L_0x03df
        L_0x03bf:
            r0 = move-exception
            goto L_0x03d3
        L_0x03c1:
            r0 = move-exception
            goto L_0x03d3
        L_0x03c3:
            r0 = move-exception
            goto L_0x03d2
        L_0x03c5:
            r0 = move-exception
            goto L_0x03d2
        L_0x03c7:
            r0 = move-exception
            goto L_0x03d0
        L_0x03c9:
            r0 = move-exception
            goto L_0x03d0
        L_0x03cb:
            r0 = move-exception
            goto L_0x03ce
        L_0x03cd:
            r0 = move-exception
        L_0x03ce:
            r18 = r2
        L_0x03d0:
            r2 = r17
        L_0x03d2:
            r7 = 0
        L_0x03d3:
            r8 = 0
            goto L_0x03df
        L_0x03d5:
            r0 = move-exception
            goto L_0x03d8
        L_0x03d7:
            r0 = move-exception
        L_0x03d8:
            r18 = r2
            r2 = r17
            r7 = 0
            r8 = 0
            r14 = 0
        L_0x03df:
            r0.printStackTrace()
        L_0x03e2:
            r26 = r7
            r30 = r8
            r29 = r14
            com.zkteco.android.employeemgmt.model.ZKStaffBean r0 = new com.zkteco.android.employeemgmt.model.ZKStaffBean
            r23 = 0
            r25 = 0
            r22 = r0
            r24 = r9
            r22.<init>(r23, r24, r25, r26, r27, r28, r29, r30)
            r3.add(r0)
            int r6 = r6 + 1
            r17 = r2
            r2 = r18
            r18 = r5
            goto L_0x02aa
        L_0x0402:
            r15 = r31
            r4.shutdown()
            goto L_0x040a
        L_0x0408:
            r15 = r31
        L_0x040a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "queryInternal: 总共耗时: "
            java.lang.StringBuilder r0 = r0.append(r2)
            long r4 = android.os.SystemClock.elapsedRealtime()
            long r4 = r4 - r20
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r0 = r0.toString()
            android.util.Log.e(r1, r0)
            return r3
        L_0x0427:
            if (r1 == 0) goto L_0x042c
            r1.close()
        L_0x042c:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.controller.StaffHomeController.queryInternal(java.lang.String, long, int):java.util.List");
    }

    public /* synthetic */ Boolean lambda$queryInternal$4$StaffHomeController(long j) throws Exception {
        return Boolean.valueOf(isFingerprintLight(String.valueOf(j)));
    }

    private static boolean isPalmLight(String str) throws SQLException {
        if (((PersBiotemplate) new PersBiotemplate().getQueryBuilder().where().eq("user_pin", str).and().eq("bio_type", 8).and().eq("major_ver", 12).and().eq("minor_ver", 0).queryForFirst()) != null) {
            return true;
        }
        return false;
    }

    private boolean isFingerprintLight(String str) {
        return this.fingerFunOn && this.hasFingerModule && this.mTemplateManager.getFingerTemplateForUserPin(str).size() > 0;
    }

    public void updateUser(List<ZKStaffBean> list, int i) {
        OnListChangeListener onListChangeListener = this.mListener;
        if (onListChangeListener != null) {
            ZkThreadPoolManager.getInstance().execute(new Runnable(list, i, onListChangeListener) {
                public final /* synthetic */ List f$1;
                public final /* synthetic */ int f$2;
                public final /* synthetic */ StaffHomeController.OnListChangeListener f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void run() {
                    StaffHomeController.this.lambda$updateUser$7$StaffHomeController(this.f$1, this.f$2, this.f$3);
                }
            });
        }
    }

    public /* synthetic */ void lambda$updateUser$7$StaffHomeController(List list, int i, OnListChangeListener onListChangeListener) {
        updateUserInternal(list, i);
        this.mHandler.post(new Runnable(i) {
            public final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                StaffHomeController.OnListChangeListener.this.userUpdate(this.f$1);
            }
        });
    }

    private void updateUserInternal(List<ZKStaffBean> list, int i) {
        ExecutorService newFixedThreadPool;
        if (list != null && !list.isEmpty() && i < list.size()) {
            ZKStaffBean zKStaffBean = list.get(i);
            try {
                UserInfo userInfo = (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", zKStaffBean.getUserInfo().getUser_PIN()).queryForFirst();
                if (userInfo != null) {
                    long id = userInfo.getID();
                    UserInfo userInfo2 = (UserInfo) new UserInfo().queryForId(Long.valueOf(id));
                    if (userInfo2 != null) {
                        newFixedThreadPool = Executors.newFixedThreadPool(3);
                        Future submit = newFixedThreadPool.submit(new Callable() {
                            public final Object call() {
                                return Boolean.valueOf(HasFaceUtils.isHasFace(UserInfo.this.getUser_PIN()));
                            }
                        });
                        Future submit2 = newFixedThreadPool.submit(new Callable(id) {
                            public final /* synthetic */ long f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final Object call() {
                                return StaffHomeController.this.lambda$updateUserInternal$9$StaffHomeController(this.f$1);
                            }
                        });
                        Future submit3 = newFixedThreadPool.submit(new Callable() {
                            public final Object call() {
                                return Boolean.valueOf(StaffHomeController.isPalmLight(UserInfo.this.getUser_PIN()));
                            }
                        });
                        if (userInfo2.getName() != null) {
                            userInfo2.setName(userInfo2.getName().replace("&", " "));
                        }
                        boolean z = true;
                        if ((ZKLauncher.longName == 1 || TextUtils.isEmpty(userInfo2.getName())) && ZKLauncher.sAccessRuleType == 1) {
                            try {
                                ExtUser extUser = (ExtUser) new ExtUser().getQueryBuilder().where().eq("Pin", userInfo2.getUser_PIN()).queryForFirst();
                                if (extUser != null) {
                                    userInfo2.setName(extUser.getFirstName() + " " + extUser.getLastName());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        String main_Card = userInfo2.getMain_Card();
                        zKStaffBean.setCardLight(main_Card != null && !"".equals(main_Card) && !"0".equals(main_Card));
                        if (TextUtils.isEmpty(userInfo2.getPassword())) {
                            z = false;
                        }
                        zKStaffBean.setPdLight(z);
                        try {
                            zKStaffBean.setFingerLight(((Boolean) submit2.get()).booleanValue());
                            zKStaffBean.setFaceLight(((Boolean) submit.get()).booleanValue());
                            zKStaffBean.setPalmPrintLight(((Boolean) submit3.get()).booleanValue());
                        } catch (InterruptedException | ExecutionException e2) {
                            e2.printStackTrace();
                        }
                        newFixedThreadPool.shutdown();
                        zKStaffBean.setUserInfo(userInfo2);
                    }
                }
            } catch (SQLException e3) {
                e3.printStackTrace();
            } catch (Throwable th) {
                newFixedThreadPool.shutdown();
                throw th;
            }
        }
    }

    public /* synthetic */ Boolean lambda$updateUserInternal$9$StaffHomeController(long j) throws Exception {
        return Boolean.valueOf(isFingerprintLight(String.valueOf(j)));
    }

    public void release() {
        this.mHandler.removeCallbacksAndMessages((Object) null);
        this.mContext = null;
        this.mListener = null;
    }
}
