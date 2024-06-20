package com.zktechnology.android.verify.server;

import com.zktechnology.android.utils.LogUtils;

public abstract class BaseTask implements Runnable {
    private static final String TAG = "BaseTask";

    /* access modifiers changed from: package-private */
    public abstract void doWork();

    public BaseTask(String str) {
        LogUtils.d(TAG, "submitTask: " + str);
    }

    public void run() {
        doWork();
    }
}
