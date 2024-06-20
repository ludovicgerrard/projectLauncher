package com.zkteco.liveface562.common;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ZkLicenseType {
    public static final int HARDWARE_LICENSE = 1;
    public static final int SOFTWARE_LICENSE = 2;
}
