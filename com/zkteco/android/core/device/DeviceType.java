package com.zkteco.android.core.device;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface DeviceType {
    public static final String G4_PRO = "G4 Pro";
    public static final String G5 = "G5";
    public static final String G6 = "G6";
    public static final String HORUS_H1 = "Horus-H1";
    public static final String IRIS = "ProIris";
    public static final String UNKNOWN = "Unknown";
}
