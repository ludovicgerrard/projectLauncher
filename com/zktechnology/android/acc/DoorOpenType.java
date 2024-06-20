package com.zktechnology.android.acc;

public enum DoorOpenType {
    NONE(-1),
    LOCAL_OPEN(0),
    LOCAL_OPEN_ALWAYS(1),
    LOCAL_CLOSE(2),
    REMOTE_OPEN(3),
    REMOTE_CLOSE(4),
    REMOTE_OPEN_EXIT_BUTTON(5),
    REMOTE_OPEN_ALWAYS(6),
    AUX_IN_OPEN_ALWAYS(7),
    UNSAFE_OPEN(8),
    TAMPER(9),
    FIRST_OPEN_ALWAYS(10),
    ADMIN_OPEN_ALWAYS(11),
    ADMIN_CANCEL_OPEN_ALWAYS(12),
    AUX_IN_REMOTE_OPEN(13);
    
    private int value;

    private DoorOpenType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public static DoorOpenType fromInteger(int i) {
        if (i == -1) {
            return NONE;
        }
        if (i == 0) {
            return LOCAL_OPEN;
        }
        if (i == 1) {
            return LOCAL_OPEN_ALWAYS;
        }
        if (i == 2) {
            return LOCAL_CLOSE;
        }
        if (i == 3) {
            return REMOTE_OPEN;
        }
        if (i == 4) {
            return REMOTE_CLOSE;
        }
        if (i == 5) {
            return REMOTE_OPEN_EXIT_BUTTON;
        }
        if (i == 6) {
            return REMOTE_OPEN_ALWAYS;
        }
        if (i == 7) {
            return AUX_IN_OPEN_ALWAYS;
        }
        if (i == 8) {
            return UNSAFE_OPEN;
        }
        if (i == 9) {
            return TAMPER;
        }
        if (i == 10) {
            return FIRST_OPEN_ALWAYS;
        }
        if (i == 11) {
            return ADMIN_OPEN_ALWAYS;
        }
        if (i == 12) {
            return ADMIN_CANCEL_OPEN_ALWAYS;
        }
        if (i == 13) {
            return AUX_IN_REMOTE_OPEN;
        }
        return null;
    }
}
