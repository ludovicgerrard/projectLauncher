package com.zktechnology.android.utils;

import android.text.TextUtils;

public abstract class BaseRunnable implements Runnable {
    private String mTaskName;

    public abstract void doWork();

    public BaseRunnable(String str) {
        this.mTaskName = str;
    }

    public void run() {
        if (TextUtils.isEmpty(this.mTaskName)) {
            Thread.currentThread().setName("zk-no-name");
        } else if (!Thread.currentThread().getName().equals(this.mTaskName)) {
            Thread.currentThread().setName(this.mTaskName);
        }
        doWork();
    }
}
