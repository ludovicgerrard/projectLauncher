package com.zkteco.android.zkcore.wiegand.enmutype;

public enum WiegandSiteCode {
    MIN(0),
    MAX(256),
    DEF(-1);
    
    private int value;

    private WiegandSiteCode(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
