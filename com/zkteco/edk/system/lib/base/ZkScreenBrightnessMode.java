package com.zkteco.edk.system.lib.base;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ZkScreenBrightnessMode {
    public static final int AUTO = 1;
    public static final int MANUAL = 0;
}
