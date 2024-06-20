package com.zkteco.edk.camera.lib;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

final class ZkThreadFactory implements ThreadFactory {
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private final ThreadGroup mGroup;
    private final String mNamePrefix;
    private final AtomicInteger mThreadNumber = new AtomicInteger(1);

    public ZkThreadFactory() {
        ThreadGroup threadGroup;
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            threadGroup = securityManager.getThreadGroup();
        } else {
            threadGroup = Thread.currentThread().getThreadGroup();
        }
        this.mGroup = threadGroup;
        this.mNamePrefix = "pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.mGroup, runnable, this.mNamePrefix + this.mThreadNumber.getAndIncrement(), 0);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        return thread;
    }
}
