package com.zkteco.edk.card.lib;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ZkCardRuleMode {
    public static final String CUSTOM = "custom";
    public static final String DEFAULT = "default";
}
