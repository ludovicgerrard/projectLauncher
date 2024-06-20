package com.zktechnology.android.acc;

public enum DoorAccessDirection {
    EXIT(0),
    ENTER(1);
    
    private int value;

    private DoorAccessDirection(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public static DoorAccessDirection fromInteger(int i) {
        if (i == 0) {
            return EXIT;
        }
        if (i == 1) {
            return ENTER;
        }
        return null;
    }
}
