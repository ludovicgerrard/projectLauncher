package com.zktechnology.android.att;

import com.zktechnology.android.att.types.AttAlarmType;
import com.zktechnology.android.att.types.AttDoorType;

public class AttResponse {
    private AttAlarmType attAlarmType;
    private AttDoorType attDoorType;
    private boolean canProceed;
    private String errorMessage;
    private int eventCode;
    private boolean openDoor;
    private boolean tempHigh;
    private double temperature;
    private int wearMask;

    public boolean isCanProceed() {
        return this.canProceed;
    }

    public void setCanProceed(boolean z) {
        this.canProceed = z;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String str) {
        this.errorMessage = str;
    }

    public int getEventCode() {
        return this.eventCode;
    }

    public void setEventCode(int i) {
        this.eventCode = i;
    }

    public AttAlarmType getAttAlarmType() {
        return this.attAlarmType;
    }

    public void setAttAlarmType(AttAlarmType attAlarmType2) {
        this.attAlarmType = attAlarmType2;
    }

    public AttDoorType getAttDoorType() {
        return this.attDoorType;
    }

    public void setAttDoorType(AttDoorType attDoorType2) {
        this.attDoorType = attDoorType2;
    }

    public boolean isOpenDoor() {
        return this.openDoor;
    }

    public void setOpenDoor(boolean z) {
        this.openDoor = z;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double d) {
        this.temperature = d;
    }

    public boolean isTempHigh() {
        return this.tempHigh;
    }

    public void setTempHigh(boolean z) {
        this.tempHigh = z;
    }

    public int getWearMask() {
        return this.wearMask;
    }

    public void setWearMask(int i) {
        this.wearMask = i;
    }
}
