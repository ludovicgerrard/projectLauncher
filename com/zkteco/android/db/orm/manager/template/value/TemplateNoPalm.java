package com.zkteco.android.db.orm.manager.template.value;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface TemplateNoPalm {
    public static final int PALM_LEFT = 0;
    public static final int PALM_RIGHT = 1;
}
