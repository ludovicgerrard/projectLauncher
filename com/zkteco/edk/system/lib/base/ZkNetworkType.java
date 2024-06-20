package com.zkteco.edk.system.lib.base;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ZkNetworkType {
    public static final int ETHERNET = 9;
    public static final int MOBILE = 0;
    public static final int NONE = -1;
    public static final int WIFI = 1;
}
