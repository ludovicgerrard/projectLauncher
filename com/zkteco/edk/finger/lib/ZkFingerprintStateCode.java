package com.zkteco.edk.finger.lib;

public interface ZkFingerprintStateCode {
    public static final int DEVICE_ATTACH = 1;
    public static final int DEVICE_CLOSED = -3;
    public static final int DEVICE_DETACH = -1;
    public static final int DEVICE_OPEN_FAILED = -2;
    public static final int DEVICE_OPEN_SUCCESS = 2;
    public static final int DEVICE_REBOOT = -4;
}
