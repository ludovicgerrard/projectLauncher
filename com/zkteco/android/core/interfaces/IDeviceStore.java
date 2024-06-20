package com.zkteco.android.core.interfaces;

public interface IDeviceStore {
    String getProp(String str);

    boolean setProp(String str, String str2);
}
