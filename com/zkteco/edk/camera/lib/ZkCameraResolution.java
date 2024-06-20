package com.zkteco.edk.camera.lib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ZkCameraResolution {
    public static final int R1080P = 4;
    public static final int R240P = 1;
    public static final int R480P = 2;
    public static final int R720P = 3;
    public static final int R960P = 5;
}
