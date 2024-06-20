package com.zktechnology.android.utils;

import android.util.Log;
import com.zktechnology.android.launcher2.ZKLauncher;

public class LogUtils {
    private static final int D = 2;
    private static boolean DEBUG = false;
    private static final int E = 3;
    private static final int I = 0;
    public static final String TAG_ACC = "Access-";
    public static final String TAG_VERIFY = "Verify";
    public static final int V = 1;
    private static final int W = 4;

    private static String formatTag(String str) {
        return str;
    }

    public static void e(String str, String str2) {
        print(3, str, str2);
    }

    public static void e(String str, String str2, Object... objArr) {
        print(3, str, String.format(str2, objArr));
    }

    public static void d(String str, String str2) {
        print(2, str, str2);
    }

    public static void d(String str, String str2, Object... objArr) {
        print(2, str, String.format(str2, objArr));
    }

    public static void i(String str, String str2) {
        print(0, str, str2);
    }

    public static void i(String str, String str2, Object... objArr) {
        print(0, str, String.format(str2, objArr));
    }

    public static void v(String str, String str2) {
        print(1, str, str2);
    }

    public static void v(String str, String str2, Object... objArr) {
        print(1, str, String.format(str2, objArr));
    }

    public static void w(String str, String str2) {
        print(4, str, str2);
    }

    public static void w(String str, String str2, Object... objArr) {
        print(4, str, String.format(str2, objArr));
    }

    private static void print(int i, String str, String str2) {
        if (DEBUG) {
            if (i == 0) {
                Log.i(formatTag(str), formatValue(str2));
            } else if (i == 1) {
                Log.v(formatTag(str), formatValue(str2));
            } else if (i == 2) {
                Log.d(formatTag(str), formatValue(str2));
            } else if (i == 3) {
                Log.e(formatTag(str), formatValue(str2));
            } else if (i == 4) {
                Log.w(formatTag(str), formatValue(str2));
            }
        }
    }

    private static String formatValue(String str) {
        return str + getLineNum();
    }

    private static String getSimpleClassName() {
        String className = Thread.currentThread().getStackTrace()[6].getClassName();
        return className.substring(className.lastIndexOf(".") + 1);
    }

    private static String getMethodName() {
        return Thread.currentThread().getStackTrace()[6].getMethodName();
    }

    private static String getLineNum() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[6];
        return ".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
    }

    public static void log(String str) {
        if (DEBUG) {
            print(0, ZKLauncher.TAG, str);
        }
    }

    public static void verifyLog(String str) {
        if (DEBUG) {
            print(0, TAG_VERIFY, str);
        }
    }

    public static void verifyFormatLog(String str, Object... objArr) {
        if (DEBUG) {
            print(0, TAG_VERIFY, String.format(str, objArr));
        }
    }

    public static void setDebug(boolean z) {
        DEBUG = z;
    }
}
