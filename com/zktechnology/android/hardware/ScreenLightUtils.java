package com.zktechnology.android.hardware;

import android.app.KeyguardManager;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import com.zktechnology.android.launcher2.LauncherApplication;

public class ScreenLightUtils {
    public static final int SAVE_POWER_BRIGHT = 10;
    private static final String TAG = "ScreenLightUtils";
    private static ScreenLightUtils instance;
    private final int API_TYPE = 0;
    private ComponentName adminReceiver;
    /* access modifiers changed from: private */
    public CountDownTimer closeScreenCountDownTimer = new CountDownTimer(10000, 1000) {
        public void onTick(long j) {
        }

        public void onFinish() {
            ScreenLightUtils screenLightUtils = ScreenLightUtils.this;
            screenLightUtils.setScreenBrightness(screenLightUtils.mNonWorkModeBrightness, "closeScreenCountDown time out,0");
            ScreenLightUtils.this.closeScreenCountDownTimer.cancel();
        }
    };
    /* access modifiers changed from: private */
    public int mNonWorkModeBrightness = 0;
    private DevicePolicyManager policyManager;
    private PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    private ScreenLightUtils() {
        try {
            PowerManager powerManager2 = (PowerManager) LauncherApplication.getApplication().getSystemService("power");
            this.powerManager = powerManager2;
            this.wakeLock = powerManager2.newWakeLock(805306394, ScreenLightUtils.class.getCanonicalName());
            this.policyManager = (DevicePolicyManager) LauncherApplication.getApplication().getSystemService("device_policy");
            this.adminReceiver = new ComponentName(LauncherApplication.getApplication(), MyAdminReceiver.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized ScreenLightUtils getInstance() {
        ScreenLightUtils screenLightUtils;
        synchronized (ScreenLightUtils.class) {
            if (instance == null) {
                instance = new ScreenLightUtils();
            }
            screenLightUtils = instance;
        }
        return screenLightUtils;
    }

    private void setScreenManualMode() {
        ContentResolver contentResolver = LauncherApplication.getApplication().getContentResolver();
        try {
            if (Settings.System.getInt(contentResolver, "screen_brightness_mode") == 1) {
                Settings.System.putInt(contentResolver, "screen_brightness_mode", 0);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void acquireWakeLock() {
        try {
            this.wakeLock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void releaseWakeLock() {
        try {
            if (this.wakeLock.isHeld()) {
                this.wakeLock.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setScreenBrightness(int i, String str) {
        Log.i(TAG, "setScreenBrightness:>>>>>>>>>>" + str);
        setScreenManualMode();
        Settings.System.putInt(LauncherApplication.getApplication().getContentResolver(), "screen_brightness", i);
    }

    public void setNonWorkModeBrightness(int i) {
        this.mNonWorkModeBrightness = i;
    }

    public void turnOnScreen() {
        try {
            this.wakeLock.acquire(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isScreenOn() {
        return this.powerManager.isScreenOn();
    }

    public boolean isAdminActivity() {
        return this.policyManager.isAdminActive(this.adminReceiver);
    }

    public void turnOffScreen() {
        if (this.policyManager.isAdminActive(this.adminReceiver)) {
            this.policyManager.lockNow();
        }
    }

    public void setLockScreenTimeOut(long j) {
        if (this.policyManager.isAdminActive(this.adminReceiver)) {
            this.policyManager.setMaximumTimeToLock(this.adminReceiver, j);
        }
    }

    public void unlockScreen() {
        ((KeyguardManager) LauncherApplication.getApplication().getSystemService("keyguard")).newKeyguardLock("unLock").disableKeyguard();
    }

    public static class MyAdminReceiver extends DeviceAdminReceiver {
        public void onEnabled(Context context, Intent intent) {
            super.onEnabled(context, intent);
        }

        public void onDisabled(Context context, Intent intent) {
            super.onDisabled(context, intent);
        }
    }
}
