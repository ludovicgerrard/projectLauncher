package com.zktechnology.android.ver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface VerifySourceType {
    public static final int VS_DEF = 1;
    public static final int VS_READ = 3;
    public static final int VS_WIEGAND = 2;
}
