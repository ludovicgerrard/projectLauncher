package com.zkteco.edk.hardware.encrypt;

import android.content.Context;
import android.content.Intent;
import com.zkteco.zkliveface.ver56.auth.FaceAuthNative;

public final class ZkHardwareUtils {
    private static boolean mIsDebugMode = true;
    private static boolean mIsForceStopApp = true;

    public String getVersion() {
        return "1.0.0";
    }

    public static boolean isHardwareActive() {
        return ZkBackgroundService.getHardwareState();
    }

    public static void setDebug(boolean z) {
        mIsDebugMode = z;
    }

    public static boolean isDebug() {
        return mIsDebugMode;
    }

    public static boolean isIsForceStopApp() {
        return mIsForceStopApp;
    }

    public static void setForceStopApp(boolean z) {
        mIsForceStopApp = z;
    }

    public static void retryCheckActivation(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, ZkBackgroundService.class);
            intent.putExtra(ZkBackgroundService.INTENT_KEY_RETRY_CHECK_BOOL, true);
            context.startService(intent);
        }
    }

    public static boolean checkHardwareActivation() {
        return FaceAuthNative.getChipAuthStatus();
    }

    public static boolean checkSoftwareActivation() {
        return ZkOfflineActivationUtils.activateLicense();
    }
}
