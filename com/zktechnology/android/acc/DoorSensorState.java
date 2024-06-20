package com.zktechnology.android.acc;

public enum DoorSensorState {
    NONE(-1),
    CLOSE(0),
    OPEN(1);
    
    private int value;

    private DoorSensorState(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public static DoorSensorState fromInteger(int i) {
        if (i == -1) {
            return NONE;
        }
        if (i == 0) {
            return CLOSE;
        }
        if (i == 1) {
            return OPEN;
        }
        return null;
    }
}
