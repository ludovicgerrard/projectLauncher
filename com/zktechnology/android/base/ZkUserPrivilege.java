package com.zktechnology.android.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ZkUserPrivilege {
    public static final int NORMAL = 0;
    public static final int SUPER_ADMIN = 14;
}
