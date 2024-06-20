package com.zkteco.android.zkcore.wiegand.enmutype;

public enum WiegandStatus {
    YES(1),
    NO(0);
    
    private int value;

    private WiegandStatus(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
