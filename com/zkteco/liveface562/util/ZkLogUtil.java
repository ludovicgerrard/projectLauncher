package com.zkteco.liveface562.util;

import android.util.Log;
import java.util.Locale;

public class ZkLogUtil {
    private static final String TAG = "ZkLiveFace";
    private static boolean debug = true;

    public static void setDebug(boolean z) {
        debug = z;
    }

    public static void d(String str) {
        if (debug) {
            Log.d(TAG, str);
        }
    }

    public static void w(String str) {
        if (debug) {
            Log.w(TAG, str);
        }
    }

    public static void e(String str) {
        if (debug) {
            Log.e(TAG, str);
        }
    }

    public static void i(String str) {
        if (debug) {
            Log.i(TAG, str);
        }
    }

    public static void iFormat(String str, Object... objArr) {
        if (debug) {
            Log.i(TAG, String.format(Locale.US, str, objArr));
        }
    }
}
