package com.zkteco.android.zkcore.wiegand.enmutype;

public enum WiegandFormatType {
    FORMAT_TYPE_OUT(1),
    FORMAT_TYPE_IN(3);
    
    private int value;

    private WiegandFormatType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
