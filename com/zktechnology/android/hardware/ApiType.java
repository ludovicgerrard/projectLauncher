package com.zktechnology.android.hardware;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ApiType {
    public static final int ANDROID = 0;
    public static final int HORUS = 1;
}
