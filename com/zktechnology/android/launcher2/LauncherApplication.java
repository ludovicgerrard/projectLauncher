package com.zktechnology.android.launcher2;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import com.zktechnology.android.att.ZkAttDoorManager;
import com.zktechnology.android.door.ZkDoorManager;
import com.zktechnology.android.face.AlgorithmActivateReceiver;
import com.zktechnology.android.face.UpdateFaceTemplateReceiver;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.receiver.LogDebugReceiver;
import com.zktechnology.android.rs232.ZKRS232EncryptManager;
import com.zktechnology.android.service.IPCServerService;
import com.zktechnology.android.strategy.BackgroundThreadExecutor;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.FileUtils;
import com.zktechnology.android.utils.SharedPreferencesUtils;
import com.zktechnology.android.wiegand.ZKWiegandManager;
import com.zktechnology.android.work.ZkClearPhotoWork;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LauncherApplication extends Application {
    public static final boolean DEBUG = true;
    private static final String RESTART_COUNT = "Launcher_start_count";
    private static final String TAG = "LauncherApplication";
    private static Application mApplication = null;
    private static Context mInstance = null;
    private static boolean sIsScreenLarge = false;
    private static int sLongPressTimeout = 300;
    private static float sScreenDensity = 0.0f;
    private static final String sSharedPreferencesKey = "com.android.launcher2.prefs";
    public static final int version = 1;
    private LogDebugReceiver logDebugReceiver;
    private AlgorithmActivateReceiver mAlgorithmActivateReceiver;
    private int mState;
    private UpdateFaceTemplateReceiver mUpdateFaceTemplateReceiver;

    public static String getSharedPreferencesKey() {
        return sSharedPreferencesKey;
    }

    public static Context getLauncherApplicationContext() {
        return mInstance;
    }

    public static Application getApplication() {
        return mApplication;
    }

    public static void setApplication(Application application) {
        mApplication = application;
    }

    public void onCreate() {
        super.onCreate();
        setApplication(this);
        BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable() {
            public final void run() {
                LauncherApplication.this.lambda$onCreate$0$LauncherApplication();
            }
        });
        mInstance = getApplicationContext();
        sIsScreenLarge = getResources().getBoolean(R.bool.is_large_screen);
        sScreenDensity = getResources().getDisplayMetrics().density;
        this.logDebugReceiver = new LogDebugReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LogDebugReceiver.DEBUG_SWITCH_ON);
        intentFilter.addAction(LogDebugReceiver.DEBUG_SWITCH_OFF);
        intentFilter.addAction(LogDebugReceiver.KILL_CAMERA_PROCESS);
        registerReceiver(this.logDebugReceiver, intentFilter);
        openCrash();
        ZkDoorManager.getInstance(getApplicationContext());
        ZkAttDoorManager.getInstance(getApplicationContext());
        ZKWiegandManager.newInstance(getApplicationContext());
        ZKRS232EncryptManager.newInstance(getApplicationContext());
        Intent intent = new Intent();
        intent.setClass(this, IPCServerService.class);
        startService(intent);
        if (Build.VERSION.SDK_INT >= 25) {
            this.mUpdateFaceTemplateReceiver = new UpdateFaceTemplateReceiver();
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("com.zkteco.android.employeemgmt.action.UPDATE_FACE_TEMPLATE");
            registerReceiver(this.mUpdateFaceTemplateReceiver, intentFilter2);
            this.mAlgorithmActivateReceiver = new AlgorithmActivateReceiver();
            IntentFilter intentFilter3 = new IntentFilter();
            intentFilter3.addAction(AlgorithmActivateReceiver.ACTIVATE_ALGORITHM_ACTION);
            intentFilter3.addAction(AlgorithmActivateReceiver.ACTIVATE_ALGORITHM_ONLINE_ACTION);
            intentFilter3.addAction(AlgorithmActivateReceiver.ACTIVATE_DEVICEFINGERPRINT_ACTION);
            registerReceiver(this.mAlgorithmActivateReceiver, intentFilter3);
        }
        hideBottomUIMenu();
        hookWebView();
        ZkClearPhotoWork.startClearPhotoWork(this);
    }

    public /* synthetic */ void lambda$onCreate$0$LauncherApplication() {
        saveBootRecord();
        SharedPreferences sharedPreferences = getSharedPreferences("config_setting", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (sharedPreferences.getInt("version", -1) < 1) {
            FileUtils.deleteFile(new File("sdcard/config/"));
            FileUtils.copyAssets(getApplicationContext(), "config", "sdcard/config/");
            FileUtils.deleteFile(new File("sdcard/ZKTeco/"));
            FileUtils.copyAssets(getApplicationContext(), "ZKTeco", "sdcard/ZKTeco/");
            edit.putInt("version", 1);
            edit.apply();
        }
        try {
            AppUtils.execCommands("echo " + ((Integer) SharedPreferencesUtils.get(this, "cpu_max_freq", 1200000)).intValue() + " > /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void hideBottomUIMenu() {
        Intent intent = new Intent();
        intent.setAction("HIDE_NAVIGATION");
        sendBroadcast(intent);
    }

    public void openCrash() {
        CrashHandler.getInstance().init(getApplicationContext());
    }

    public void onTerminate() {
        super.onTerminate();
        LogDebugReceiver logDebugReceiver2 = this.logDebugReceiver;
        if (logDebugReceiver2 != null) {
            unregisterReceiver(logDebugReceiver2);
        }
        stopService(new Intent(this, IPCServerService.class));
        if (Build.VERSION.SDK_INT >= 25) {
            unregisterReceiver(this.mUpdateFaceTemplateReceiver);
            unregisterReceiver(this.mAlgorithmActivateReceiver);
        }
    }

    public static boolean isScreenLarge() {
        return sIsScreenLarge;
    }

    public static boolean isScreenLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    public static float getScreenDensity() {
        return sScreenDensity;
    }

    public static int getLongPressTimeout() {
        return sLongPressTimeout;
    }

    private void saveBootRecord() {
        int intValue = ((Integer) SharedPreferencesUtils.get(this, RESTART_COUNT, 0)).intValue() + 1;
        SharedPreferencesUtils.set(this, RESTART_COUNT, Integer.valueOf(intValue));
        FileLogUtils.writeLauncherInitRecord("Launcher boot -> StartCount: " + intValue);
    }

    public static void hookWebView() {
        Method method;
        Object obj;
        int i = Build.VERSION.SDK_INT;
        try {
            Class<?> cls = Class.forName("android.webkit.WebViewFactory");
            Field declaredField = cls.getDeclaredField("sProviderInstance");
            declaredField.setAccessible(true);
            if (declaredField.get((Object) null) != null) {
                Log.i(TAG, "sProviderInstance isn't null");
                return;
            }
            if (i > 22) {
                method = cls.getDeclaredMethod("getProviderClass", new Class[0]);
            } else if (i == 22) {
                method = cls.getDeclaredMethod("getFactoryClass", new Class[0]);
            } else {
                Log.i(TAG, "Don't need to Hook WebView");
                return;
            }
            method.setAccessible(true);
            Class cls2 = (Class) method.invoke(cls, new Object[0]);
            Class<?> cls3 = Class.forName("android.webkit.WebViewDelegate");
            Constructor<?> declaredConstructor = cls3.getDeclaredConstructor(new Class[0]);
            declaredConstructor.setAccessible(true);
            if (i < 26) {
                Constructor constructor = cls2.getConstructor(new Class[]{cls3});
                constructor.setAccessible(true);
                obj = constructor.newInstance(new Object[]{declaredConstructor.newInstance(new Object[0])});
            } else {
                Field declaredField2 = cls.getDeclaredField("CHROMIUM_WEBVIEW_FACTORY_METHOD");
                declaredField2.setAccessible(true);
                String str = (String) declaredField2.get((Object) null);
                if (str == null) {
                    str = "create";
                }
                obj = cls2.getMethod(str, new Class[]{cls3}).invoke((Object) null, new Object[]{declaredConstructor.newInstance(new Object[0])});
            }
            if (obj != null) {
                declaredField.set("sProviderInstance", obj);
                Log.i(TAG, "Hook success!");
                return;
            }
            Log.i(TAG, "Hook failed!");
        } catch (Throwable th) {
            Log.w(TAG, th);
        }
    }

    public synchronized int getPushState() {
        return this.mState;
    }

    public synchronized void setPushState(int i) {
        this.mState = i;
    }
}
