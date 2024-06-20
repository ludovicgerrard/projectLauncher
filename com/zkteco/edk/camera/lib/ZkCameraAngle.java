package com.zkteco.edk.camera.lib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ZkCameraAngle {
    public static final int A0 = 1;
    public static final int A180 = 3;
    public static final int A270 = 4;
    public static final int A90 = 2;
}
