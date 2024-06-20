package com.zkteco.edk.system.lib.base;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ZkWifiConnectState {
    public static final int CONNECTED = 0;
    public static final int CONNECTING = 1;
    public static final int DISCONNECT = -1;
    public static final int SAVED = 2;
}
