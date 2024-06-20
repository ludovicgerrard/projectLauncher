package com.zktechnology.android.att.types;

public enum AttAlarmType {
    NONE(-1),
    AUXALARM(0),
    DURESSALARM(1),
    TAMPER(2),
    SENSORDELAY(3),
    SENSORALARMDELAY(4),
    ACCIDENTALLOPEN(5),
    VERIFYFAILALARM(6),
    HIGHTEMPERATUREALARM(7),
    NOMASK(8);
    
    private int value;

    private AttAlarmType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public static AttAlarmType fromInteger(int i) {
        if (i == -1) {
            return NONE;
        }
        if (i == 0) {
            return AUXALARM;
        }
        if (i == 1) {
            return DURESSALARM;
        }
        if (i == 2) {
            return TAMPER;
        }
        if (i == 3) {
            return SENSORDELAY;
        }
        if (i == 4) {
            return SENSORALARMDELAY;
        }
        if (i == 5) {
            return ACCIDENTALLOPEN;
        }
        if (i == 6) {
            return VERIFYFAILALARM;
        }
        if (i == 7) {
            return HIGHTEMPERATUREALARM;
        }
        if (i == 8) {
            return NOMASK;
        }
        return null;
    }
}
