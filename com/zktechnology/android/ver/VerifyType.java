package com.zktechnology.android.ver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface VerifyType {
    public static final int CARD = 4;
    public static final int FACE = 5;
    public static final int FINGER = 1;
    public static final int INVALID = -1;
    public static final int PASSWORD = 3;
    public static final int PIN = 2;
}
