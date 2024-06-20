package com.zktechnology.android.acc.base;

import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.utils.LogUtils;

public class Response {
    private DoorOpenType doorOpenType;
    private boolean isCancelRemoteAlarm;
    private boolean isInDoorEffectiveTimePeriod;
    private boolean isLocalAlarmOn;
    private boolean isRemoteAlarmOn;
    private boolean isStartLocalAlarmDelay;
    private int lockDriveDurationInSeconds;
    private boolean openDoorDuplicate;

    public boolean isInDoorEffectiveTimePeriod() {
        return this.isInDoorEffectiveTimePeriod;
    }

    public void setInDoorEffectiveTimePeriod(boolean z) {
        this.isInDoorEffectiveTimePeriod = z;
    }

    public void setCancelRemoteAlarm(boolean z) {
        this.isCancelRemoteAlarm = z;
    }

    public boolean isCancelRemoteAlarm() {
        return this.isCancelRemoteAlarm;
    }

    public boolean isStartLocalAlarmDelay() {
        return this.isStartLocalAlarmDelay;
    }

    public void setStartLocalAlarmDelay(boolean z) {
        this.isStartLocalAlarmDelay = z;
    }

    public void setLockDriveDurationInSeconds(int i) {
        this.lockDriveDurationInSeconds = i;
    }

    public int getLockDriveDurationInSeconds() {
        return this.lockDriveDurationInSeconds;
    }

    public void setDoorOpenType(DoorOpenType doorOpenType2) {
        this.doorOpenType = doorOpenType2;
    }

    public DoorOpenType getDoorOpenType() {
        return this.doorOpenType;
    }

    public boolean isLocalAlarmOn() {
        return this.isLocalAlarmOn;
    }

    public boolean isRemoteAlarmOn() {
        return this.isRemoteAlarmOn;
    }

    public void setLocalAlarmOn(boolean z) {
        this.isLocalAlarmOn = z;
        LogUtils.i("AccDoor", "setLocalAlarmOn: " + this.isLocalAlarmOn);
    }

    public void setRemoteAlarmOn(boolean z) {
        this.isRemoteAlarmOn = z;
    }

    public void setOpenDoorDuplicate(boolean z) {
        this.openDoorDuplicate = z;
    }

    public boolean isOpenDoorDuplicate() {
        return this.openDoorDuplicate;
    }
}
