package com.zkteco.edk.qrcode;

public interface ZkQrCodeStateCode {
    public static final int DEVICE_CLOSE = -3;
    public static final int DEVICE_OPEN = 3;
    public static final int FAILED_ENCRYPT = -1001;
    public static final int INIT_FAILED = -1;
    public static final int INIT_FAILED_NOT_USB_PERMISSION = -1002;
    public static final int INIT_START = 0;
    public static final int INIT_SUCCESS = 1;
    public static final int USB_CONNECT = 2;
    public static final int USB_DISCONNECT = -2;
}
