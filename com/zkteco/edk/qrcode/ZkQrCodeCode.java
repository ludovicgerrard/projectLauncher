package com.zkteco.edk.qrcode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ZkQrCodeCode {
    public static final int ERROR = -1000;
    public static final int ERR_BIND_SERVICE_FAILED = -1001;
    public static final int ERR_DATA_ERROR = -1004;
    public static final int ERR_EXEC_REMOTE_FUN_FAILED = -1002;
    public static final int ERR_INACTIVE = -1006;
    public static final int ERR_NOT_INIT = -1005;
    public static final int SUCCESS = 0;
}
