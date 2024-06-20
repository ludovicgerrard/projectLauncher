package com.zkteco.edk.camera.lib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ZkCameraMirror {
    public static final int NONE = 1;
    public static final int X = 2;
}
