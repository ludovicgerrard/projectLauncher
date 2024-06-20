package com.zkteco.adk.core.task;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ZkJobType {
    public static final String BASIC = "ZkBasicJob";
    public static final String LOOP = "ZkLoopJob";
}
