package com.zktechnology.android.att.types;

public enum AttDoorType {
    NONE(-1),
    AUXOPENDOOR(0),
    ALWAYSOPENDOOR(1),
    ALWAYSCLOSEDOOR(2),
    DURESSOPENDOOR(3),
    VERIFYOPENDOOR(4),
    SUPERPASSWORDOPENDOOR(5),
    EXIT_BUTTON(6);
    
    private int value;

    private AttDoorType(int i) {
        this.value = i;
    }

    public static AttDoorType fromInteger(int i) {
        if (i == -1) {
            return NONE;
        }
        if (i == 0) {
            return AUXOPENDOOR;
        }
        if (i == 1) {
            return ALWAYSOPENDOOR;
        }
        if (i == 2) {
            return ALWAYSCLOSEDOOR;
        }
        if (i == 3) {
            return DURESSOPENDOOR;
        }
        if (i == 4) {
            return VERIFYOPENDOOR;
        }
        if (i == 5) {
            return SUPERPASSWORDOPENDOOR;
        }
        if (i == 6) {
            return EXIT_BUTTON;
        }
        return null;
    }
}
