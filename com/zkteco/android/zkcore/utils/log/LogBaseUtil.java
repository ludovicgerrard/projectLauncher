package com.zkteco.android.zkcore.utils.log;

import android.util.Log;

public abstract class LogBaseUtil {
    private static final int D = 2;
    private static final int E = 3;
    private static final int I = 0;
    private static final int V = 1;
    private static final int W = 4;
    protected boolean isLog = false;

    private String formatTag(String str) {
        return str;
    }

    /* access modifiers changed from: protected */
    public abstract boolean isShowLog();

    public abstract void setLog(boolean z);

    public void e(String str, String str2) {
        print(3, str, str2);
    }

    public void e(String str, String str2, Object... objArr) {
        print(3, str, String.format(str2, objArr));
    }

    public void d(String str, String str2) {
        print(2, str, str2);
    }

    public void d(String str, String str2, Object... objArr) {
        print(2, str, String.format(str2, objArr));
    }

    public void i(String str, String str2) {
        print(0, str, str2);
    }

    public void i(String str, String str2, Object... objArr) {
        print(0, str, String.format(str2, objArr));
    }

    public void v(String str, String str2) {
        print(1, str, str2);
    }

    public void v(String str, String str2, Object... objArr) {
        print(1, str, String.format(str2, objArr));
    }

    public void w(String str, String str2) {
        print(4, str, str2);
    }

    public void w(String str, String str2, Object... objArr) {
        print(4, str, String.format(str2, objArr));
    }

    private void print(int i, String str, String str2) {
        if (isShowLog()) {
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

    private String formatValue(String str) {
        return str + getLineNum();
    }

    private String getSimpleClassName() {
        String className = Thread.currentThread().getStackTrace()[6].getClassName();
        return className.substring(className.lastIndexOf(".") + 1);
    }

    private String getMethodName() {
        return Thread.currentThread().getStackTrace()[6].getMethodName();
    }

    private String getLineNum() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[6];
        return ".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
    }
}
