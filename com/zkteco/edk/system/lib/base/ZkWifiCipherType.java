package com.zkteco.edk.system.lib.base;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ZkWifiCipherType {
    public static final int INVALID = 3;
    public static final int NO_PASS = 2;
    public static final int WEP = 0;
    public static final int WPA = 1;
}
