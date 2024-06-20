package com.zkteco.android.zkcore.wiegand.enmutype;

public enum WiegandType {
    PIN(0),
    CARD_NUM(1);
    
    private int value;

    private WiegandType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static WiegandType getEnum(int i) {
        for (WiegandType wiegandType : values()) {
            if (wiegandType.getValue() == i) {
                return wiegandType;
            }
        }
        return CARD_NUM;
    }
}
