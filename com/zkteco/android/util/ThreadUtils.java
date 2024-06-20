package com.zkteco.android.util;

import android.os.Looper;

public class ThreadUtils {
    private static int curThreadId = Integer.MAX_VALUE;

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void startNewThread(String str, Runnable runnable) {
        startNewThread(str, runnable, 5);
    }

    public static synchronized void startNewThread(String str, Runnable runnable, int i) {
        synchronized (ThreadUtils.class) {
            Thread thread = new Thread(runnable);
            StringBuilder append = new StringBuilder().append(str).append("-");
            int i2 = curThreadId + 1;
            curThreadId = i2;
            thread.setName(append.append(Integer.toHexString(i2)).toString());
            thread.setPriority(i);
            thread.start();
        }
    }
}
