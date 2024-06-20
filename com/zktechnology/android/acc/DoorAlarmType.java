package com.zktechnology.android.acc;

public enum DoorAlarmType {
    NONE(0),
    UNSAFE_OPEN(1),
    ANTI_TAMPER(2),
    STRESS_PASSWORD(4),
    STRESS_FINGER(8),
    SENSOR_TIMEOUT(16);
    
    private int value;

    private DoorAlarmType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public static DoorAlarmType fromInteger(int i) {
        if (i == 0) {
            return NONE;
        }
        if (i == 1) {
            return UNSAFE_OPEN;
        }
        if (i == 2) {
            return ANTI_TAMPER;
        }
        if (i == 4) {
            return STRESS_PASSWORD;
        }
        if (i == 8) {
            return STRESS_FINGER;
        }
        if (i == 16) {
            return SENSOR_TIMEOUT;
        }
        return null;
    }
}
