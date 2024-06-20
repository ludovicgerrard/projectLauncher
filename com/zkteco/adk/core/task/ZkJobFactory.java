package com.zkteco.adk.core.task;

import android.text.TextUtils;

public class ZkJobFactory {
    private static volatile ZkJobFactory mInstance;
    private static volatile IZkJobFactory mJobFactory;

    private ZkJobFactory() {
    }

    public static ZkJobFactory getInstance() {
        if (mInstance == null) {
            synchronized (ZkJobFactory.class) {
                if (mInstance == null) {
                    mInstance = new ZkJobFactory();
                    mJobFactory = new IZkJobFactory();
                }
            }
        }
        return mInstance;
    }

    public IZkJob create(String str) {
        if (TextUtils.isEmpty(str)) {
            return new ZkBasicJob();
        }
        return mJobFactory.create(str);
    }
}
