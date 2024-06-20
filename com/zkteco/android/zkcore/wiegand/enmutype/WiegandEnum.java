package com.zkteco.android.zkcore.wiegand.enmutype;

public enum WiegandEnum {
    CARD_BIT_26_DEF("26"),
    CARD_BIT_34("34"),
    CARD_BIT_36("36"),
    CARD_BIT_37("37"),
    CARD_BIT_50("50");
    
    private String value;

    private WiegandEnum(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }

    public static WiegandEnum getEnum(String str) {
        for (WiegandEnum wiegandEnum : values()) {
            if (wiegandEnum.getValue().equals(str)) {
                return wiegandEnum;
            }
        }
        return CARD_BIT_26_DEF;
    }
}
