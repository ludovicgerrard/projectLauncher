package com.zkteco.android.zkcore.wiegand.enmutype;

public enum WiegandFailedID {
    MIN(0),
    MAX(65535),
    DEF(-1);
    
    private int value;

    private WiegandFailedID(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
