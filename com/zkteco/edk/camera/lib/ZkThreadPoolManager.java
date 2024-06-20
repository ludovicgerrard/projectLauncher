package com.zkteco.edk.camera.lib;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ZkThreadPoolManager {
    private static final ZkThreadPoolManager INSTANCE = new ZkThreadPoolManager();
    private final ScheduledThreadPoolExecutor mExecutor = new ScheduledThreadPoolExecutor((Runtime.getRuntime().availableProcessors() * 2) + 1, new ZkThreadFactory());

    public static ZkThreadPoolManager getInstance() {
        return INSTANCE;
    }

    private ZkThreadPoolManager() {
    }

    public Future<?> submit(Runnable runnable) {
        if (runnable == null) {
            return null;
        }
        return this.mExecutor.submit(runnable);
    }

    public void cancel(Future<?> future) {
        if (future != null) {
            future.cancel(true);
        }
    }

    public void execute(Runnable runnable) {
        if (runnable != null) {
            this.mExecutor.execute(runnable);
        }
    }

    public boolean remove(Runnable runnable) {
        if (runnable == null) {
            return false;
        }
        return this.mExecutor.remove(runnable);
    }

    public void shutdown() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.mExecutor;
        if (scheduledThreadPoolExecutor != null) {
            scheduledThreadPoolExecutor.shutdown();
        }
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.mExecutor;
        if (scheduledThreadPoolExecutor == null) {
            return null;
        }
        return scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, j, j2, timeUnit);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.mExecutor;
        if (scheduledThreadPoolExecutor == null) {
            return null;
        }
        return scheduledThreadPoolExecutor.schedule(runnable, j, timeUnit);
    }

    public void finishFuture(ScheduledFuture<?> scheduledFuture) {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }
}
