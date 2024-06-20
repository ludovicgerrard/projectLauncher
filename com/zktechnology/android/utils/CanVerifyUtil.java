package com.zktechnology.android.utils;

public class CanVerifyUtil {
    private static CanVerifyUtil mInstance;
    private boolean isCanVerify = true;

    public static CanVerifyUtil getInstance() {
        if (mInstance == null) {
            synchronized (CanVerifyUtil.class) {
                if (mInstance == null) {
                    mInstance = new CanVerifyUtil();
                }
            }
        }
        return mInstance;
    }

    public boolean isCanVerify() {
        return this.isCanVerify;
    }

    public void setCanVerify(boolean z) {
        this.isCanVerify = z;
    }
}
