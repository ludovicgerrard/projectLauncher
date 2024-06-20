package com.zkteco.edk.common.device;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ZkDeviceState {
    public static final int OFFLINE = 1;
    public static final int ONLINE = 0;
}
