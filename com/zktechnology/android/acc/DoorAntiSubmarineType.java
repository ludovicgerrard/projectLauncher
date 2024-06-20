package com.zktechnology.android.acc;

public enum DoorAntiSubmarineType {
    NONE(0),
    EXIT(1),
    ENTER(2),
    EXIT_ENTER(3);
    
    private int value;

    private DoorAntiSubmarineType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public static DoorAntiSubmarineType fromInteger(int i) {
        if (i == 0) {
            return NONE;
        }
        if (i == 1) {
            return EXIT;
        }
        if (i == 2) {
            return ENTER;
        }
        if (i == 3) {
            return EXIT_ENTER;
        }
        return null;
    }
}
