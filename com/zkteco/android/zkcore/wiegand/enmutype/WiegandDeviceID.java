package com.zkteco.android.zkcore.wiegand.enmutype;

public enum WiegandDeviceID {
    MIN(1),
    MAX(255);
    
    private int value;

    private WiegandDeviceID(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static WiegandDeviceID getEnum(int i) {
        for (WiegandDeviceID wiegandDeviceID : values()) {
            if (wiegandDeviceID.getValue() == i) {
                return wiegandDeviceID;
            }
        }
        return MIN;
    }
}
