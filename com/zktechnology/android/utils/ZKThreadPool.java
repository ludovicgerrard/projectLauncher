package com.zktechnology.android.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ZKThreadPool {
    private static ZKThreadPool mZKThreadPool;
    private int corePoolSize;
    private long keepAliveTime;
    volatile ThreadPoolExecutor mThreadPoolExecutor;
    private int maximumPoolSize;

    public static ZKThreadPool getInstance() {
        if (mZKThreadPool == null) {
            synchronized (ZKThreadPool.class) {
                mZKThreadPool = new ZKThreadPool(5, 10, 5000);
            }
        }
        return mZKThreadPool;
    }

    private ZKThreadPool(int i, int i2, long j) {
        this.corePoolSize = i;
        this.maximumPoolSize = i2;
        this.keepAliveTime = j;
    }

    private ThreadPoolExecutor initExecutor() {
        if (this.mThreadPoolExecutor == null) {
            synchronized (ZKThreadPool.class) {
                if (this.mThreadPoolExecutor == null) {
                    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                    ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
                    ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
                    this.mThreadPoolExecutor = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize, this.keepAliveTime, timeUnit, new LinkedBlockingQueue(100), defaultThreadFactory, abortPolicy);
                    this.mThreadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
                }
            }
        }
        return this.mThreadPoolExecutor;
    }

    public void executeTask(Runnable runnable) {
        initExecutor();
        this.mThreadPoolExecutor.execute(runnable);
    }

    public Future<?> commitTask(Runnable runnable) {
        initExecutor();
        return this.mThreadPoolExecutor.submit(runnable);
    }

    public void removeTask(Runnable runnable) {
        initExecutor();
        this.mThreadPoolExecutor.remove(runnable);
    }
}
