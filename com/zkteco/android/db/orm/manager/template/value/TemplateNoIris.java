package com.zkteco.android.db.orm.manager.template.value;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface TemplateNoIris {
    public static final int IRIS_LEFT = 0;
    public static final int IRIS_RIGHT = 1;
}
