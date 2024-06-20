package com.zktechnology.android.utils;

public interface IAppInitializeCallback {
    void allInitOver();

    void initDbOver(boolean z);

    void initDeviceOver();

    void initExceptionStateChange(String str);

    void initStateChange(int i);

    void initStateChange(String str);

    void replaceBiotemplateRemainingNum(int i);
}
