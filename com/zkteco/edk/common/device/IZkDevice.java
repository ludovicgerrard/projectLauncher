package com.zkteco.edk.common.device;

public interface IZkDevice {
    boolean deviceClose();

    boolean deviceDestroy();

    boolean deviceExist();

    String deviceName();

    boolean deviceOpen();

    int deviceState();
}
