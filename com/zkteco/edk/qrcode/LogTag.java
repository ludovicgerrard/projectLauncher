package com.zkteco.edk.qrcode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface LogTag {
    public static final String QR_CODE_DEMO = "EDK-QR-DEMO";
    public static final String QR_CODE_LIB = "EDK-QR-LIB";
    public static final String QR_CODE_SERVICE = "EDK-QR-SER";
}
