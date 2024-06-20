package com.zktechnology.android.acc;

public enum DoorSensorType {
    NONE(0),
    ALWAYS_OPEN(1),
    ALWAYS_CLOSE(2);
    
    private int value;

    private DoorSensorType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public static DoorSensorType fromInteger(int i) {
        if (i == 0) {
            return NONE;
        }
        if (i == 1) {
            return ALWAYS_OPEN;
        }
        if (i == 2) {
            return ALWAYS_CLOSE;
        }
        return null;
    }
}
