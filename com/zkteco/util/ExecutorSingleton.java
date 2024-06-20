package com.zkteco.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorSingleton {
    private static final Object LOCK = new Object();
    private static ExecutorService cachedPoolExecutor;
    private static ScheduledExecutorService singleScheduledExecutor;
    private static ExecutorService singleThreadExecutor;

    private ExecutorSingleton() {
    }

    public static ExecutorService getCachedPool() {
        synchronized (LOCK) {
            if (cachedPoolExecutor == null) {
                cachedPoolExecutor = Executors.newCachedThreadPool();
            }
        }
        return cachedPoolExecutor;
    }

    public static ExecutorService getSingleThread() {
        synchronized (LOCK) {
            if (singleThreadExecutor == null) {
                singleThreadExecutor = Executors.newSingleThreadExecutor();
            }
        }
        return singleThreadExecutor;
    }

    public static ScheduledExecutorService getScheduled() {
        synchronized (LOCK) {
            if (singleScheduledExecutor == null) {
                singleScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            }
        }
        return singleScheduledExecutor;
    }
}
