package com.zkteco.android.db.orm.manager.template.value;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface DataFormat {
    public static final int ANSI = 2;
    public static final int ISO = 1;
    public static final int ZK = 0;
}
