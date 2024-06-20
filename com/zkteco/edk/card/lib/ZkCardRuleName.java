package com.zkteco.edk.card.lib;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ZkCardRuleName {
    public static final String CARD_LENGTH = "CardLength";
    public static final String CARD_REVERT = "CardRevert";
    public static final String CARD_RULE_MODE = "CardRuleMode";
    public static final String HEX_CARD = "HexCard";
    public static final String START_POSITION = "StartPosition";
}
