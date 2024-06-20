package com.zkteco.adk.core.task;

import android.text.TextUtils;
import android.util.Log;

public class ZkTaskLog {
    public static int LEVEL_D = 1;
    public static int LEVEL_E = 4;
    public static int LEVEL_I = 2;
    public static int LEVEL_NONE = 5;
    public static int LEVEL_V = 0;
    public static int LEVEL_W = 3;
    private static int logLevel = 0;
    private static final String tagPrefix = "ADK-TASK";

    public static void setLogLevel(int i) {
        logLevel = i;
    }

    private static String generateTag() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String className = stackTraceElement.getClassName();
        String format = String.format("%s.%s(L:%d)", new Object[]{className.substring(className.lastIndexOf(".") + 1), stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber())});
        return TextUtils.isEmpty("ADK-TASK") ? format : "ADK-TASK:" + format;
    }

    public static void v(String str) {
        if (logLevel <= LEVEL_V) {
            Log.v(generateTag(), str);
        }
    }

    public static void v(String str, Throwable th) {
        if (logLevel <= LEVEL_V) {
            Log.v(generateTag(), str, th);
        }
    }

    public static void d(String str) {
        if (logLevel <= LEVEL_D) {
            Log.d(generateTag(), str);
        }
    }

    public static void d(String str, Throwable th) {
        if (logLevel <= LEVEL_D) {
            Log.d(generateTag(), str, th);
        }
    }

    public static void i(String str) {
        if (logLevel <= LEVEL_I) {
            Log.i(generateTag(), str);
        }
    }

    public static void i(String str, Throwable th) {
        if (logLevel <= LEVEL_I) {
            Log.i(generateTag(), str, th);
        }
    }

    public static void w(String str) {
        if (logLevel <= LEVEL_W) {
            Log.w(generateTag(), str);
        }
    }

    public static void w(String str, Throwable th) {
        if (logLevel <= LEVEL_W) {
            Log.w(generateTag(), str, th);
        }
    }

    public static void e(String str) {
        if (logLevel <= LEVEL_E) {
            Log.e(generateTag(), str);
        }
    }

    public static void e(String str, Throwable th) {
        if (logLevel <= LEVEL_E) {
            Log.e(generateTag(), str, th);
        }
    }
}
