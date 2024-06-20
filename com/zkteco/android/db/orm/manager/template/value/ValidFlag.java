package com.zkteco.android.db.orm.manager.template.value;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ValidFlag {
    public static final int IN_VALID = 0;
    public static final int VALID = 1;
}
