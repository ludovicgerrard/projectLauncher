package com.zkteco.android.core.device;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface PlatformType {
    public static final String HORUS_H1 = "Horus_H1";
    public static final String UNKNOWN = "Unknown";
    public static final String ZIM200 = "ZIM200";
    public static final String ZIM205 = "ZIM205";
}
