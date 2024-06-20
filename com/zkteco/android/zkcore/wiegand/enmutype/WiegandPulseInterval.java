package com.zkteco.android.zkcore.wiegand.enmutype;

public enum WiegandPulseInterval {
    MIN(200),
    MAX(20000),
    DEF(1000);
    
    private int value;

    private WiegandPulseInterval(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
