package com.zktechnology.android.utils;

public final class ThreadFactory {
    public static final String TAG = "com.zktechnology.android.utils.ThreadFactory";
    private static int curThreadId = Integer.MAX_VALUE;

    private ThreadFactory() {
    }

    public static void startNewThread(String str, Runnable runnable) {
        startNewThread(str, runnable, 5);
    }

    private static synchronized void startNewThread(String str, Runnable runnable, int i) {
        synchronized (ThreadFactory.class) {
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
