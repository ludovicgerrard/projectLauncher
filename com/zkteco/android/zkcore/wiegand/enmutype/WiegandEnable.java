package com.zkteco.android.zkcore.wiegand.enmutype;

public enum WiegandEnable {
    CLOSE_DEF(0),
    OPEN(1);
    
    private int value;

    private WiegandEnable(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static WiegandEnable getEnum(int i) {
        for (WiegandEnable wiegandEnable : values()) {
            if (wiegandEnable.getValue() == i) {
                return wiegandEnable;
            }
        }
        return CLOSE_DEF;
    }
}
