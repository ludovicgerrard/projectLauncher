package com.zktechnology.android.acc.base;

import com.zktechnology.android.acc.DoorAlarmType;
import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.DoorSensorState;
import com.zktechnology.android.acc.DoorSensorType;
import com.zktechnology.android.utils.LogUtils;

public class Request {
    private long antiTamperAlarmTime;
    private long cancelRemoteAlarmTime;
    private DoorSensorState currentDoorSensorState;
    private DoorAlarmType doorAlarmType;
    private DoorOpenType doorOpenType;
    private int doorSensorLocalAlarmDelay;
    private int doorSensorRemoteAlarmDelay;
    private DoorSensorType doorSensorType;
    private int extAlarmSwitch;
    private String firstOpenUserPin;
    private int gateSwitchMode;
    private boolean isAccessVerified;
    private boolean isCheckLocalAlarm;
    private boolean isDoorSensorOpened;
    private boolean isDoorSensorTimeout;
    private boolean isRemoteAlarmOn;
    private boolean isRemoteCancelAlarm;
    private boolean isStressFingerAlarmOn;
    private boolean isStressPasswordAlarmOn;
    private boolean isTampered;
    private boolean isUnsafeOpened;
    private DoorSensorState lastDoorSensorState;
    private int localAlarmSwitch;
    private int lockDriveDurationInSeconds;
    private int remoteLockDriveDurationInSeconds;
    private int tamperState;
    private long unsafeOpenAlarmTime;

    public DoorAlarmType getDoorAlarmType() {
        return this.doorAlarmType;
    }

    public void setDoorAlarmType(DoorAlarmType doorAlarmType2) {
        this.doorAlarmType = doorAlarmType2;
        LogUtils.v("AccDoor", "setDoorAlarmType: " + doorAlarmType2);
    }

    public String getFirstOpenUserPin() {
        return this.firstOpenUserPin;
    }

    public void setFirstOpenUserPin(String str) {
        this.firstOpenUserPin = str;
        LogUtils.v("AccDoor", "setFirstOpenUserPin: " + str);
    }

    public boolean isStressFingerAlarmOn() {
        return this.isStressFingerAlarmOn;
    }

    public void setStressFingerAlarmOn(boolean z) {
        this.isStressFingerAlarmOn = z;
        LogUtils.v("AccDoor", "setStressFingerAlarmOn: " + this.isStressFingerAlarmOn);
    }

    public boolean isStressPasswordAlarmOn() {
        return this.isStressPasswordAlarmOn;
    }

    public void setStressPasswordAlarmOn(boolean z) {
        this.isStressPasswordAlarmOn = z;
        LogUtils.v("AccDoor", "setStressPasswordAlarmOn: " + this.isStressPasswordAlarmOn);
    }

    public boolean isDoorSensorOpened() {
        return this.isDoorSensorOpened;
    }

    public void setDoorSensorOpened(boolean z) {
        this.isDoorSensorOpened = z;
        LogUtils.v("AccDoor", "setDoorSensorOpened: " + this.isDoorSensorOpened);
    }

    public boolean isDoorSensorTimeout() {
        return this.isDoorSensorTimeout;
    }

    public void setDoorSensorTimeout(boolean z) {
        this.isDoorSensorTimeout = z;
        LogUtils.v("AccDoor", "setDoorSensorTimeout: " + this.isDoorSensorTimeout);
    }

    public boolean isCheckLocalAlarm() {
        return this.isCheckLocalAlarm;
    }

    public void setCheckLocalAlarm(boolean z) {
        this.isCheckLocalAlarm = z;
        LogUtils.v("AccDoor", "setCheckLocalAlarm: " + this.isCheckLocalAlarm);
    }

    public void setUnsafeOpened(boolean z) {
        this.isUnsafeOpened = z;
        LogUtils.v("AccDoor", "setUnsafeOpened: " + this.isUnsafeOpened);
    }

    public boolean isUnsafeOpened() {
        return this.isUnsafeOpened;
    }

    public boolean isTampered() {
        return this.isTampered;
    }

    public void setTampered(boolean z) {
        this.isTampered = z;
        LogUtils.v("AccDoor", "setTampered: " + this.isTampered);
    }

    public int getRemoteLockDriveDurationInSeconds() {
        return this.remoteLockDriveDurationInSeconds;
    }

    public void setRemoteLockDriveDurationInSeconds(int i) {
        this.remoteLockDriveDurationInSeconds = i;
        LogUtils.v("AccDoor", "setRemoteLockDriveDurationInSeconds: " + i);
    }

    public boolean isRemoteCancelAlarm() {
        return this.isRemoteCancelAlarm;
    }

    public void setRemoteCancelAlarm(boolean z) {
        this.isRemoteCancelAlarm = z;
        LogUtils.v("AccDoor", "setRemoteCancelAlarm: " + z);
    }

    public boolean isAccessVerified() {
        return this.isAccessVerified;
    }

    public void setAccessVerified(boolean z) {
        this.isAccessVerified = z;
        LogUtils.v("AccDoor", "setAccessVerified: " + this.isAccessVerified);
    }

    public DoorSensorState getCurrentDoorSensorState() {
        return this.currentDoorSensorState;
    }

    public void setCurrentDoorSensorState(DoorSensorState doorSensorState) {
        this.currentDoorSensorState = doorSensorState;
        LogUtils.v("AccDoor", "setCurrentDoorSensorState: " + doorSensorState);
    }

    public DoorSensorState getLastDoorSensorState() {
        return this.lastDoorSensorState;
    }

    public void setLastDoorSensorState(DoorSensorState doorSensorState) {
        this.lastDoorSensorState = doorSensorState;
        LogUtils.v("AccDoor", "setLastDoorSensorState: " + doorSensorState);
    }

    public int getTamperState() {
        return this.tamperState;
    }

    public void setTamperState(int i) {
        this.tamperState = i;
        LogUtils.v("AccDoor", "setTamperState: " + i);
    }

    public DoorOpenType getDoorOpenType() {
        return this.doorOpenType;
    }

    public DoorSensorType getDoorSensorType() {
        return this.doorSensorType;
    }

    public int getGateSwitchMode() {
        return this.gateSwitchMode;
    }

    public int getLocalAlarmSwitch() {
        return this.localAlarmSwitch;
    }

    public int getExtAlarmSwitch() {
        return this.extAlarmSwitch;
    }

    public void setExtAlarmSwitch(int i) {
        this.extAlarmSwitch = i;
        LogUtils.v("AccDoor", "setExtAlarmSwitch: " + i);
    }

    public int getLockDriveDurationInSeconds() {
        return this.lockDriveDurationInSeconds;
    }

    public void setDoorOpenType(DoorOpenType doorOpenType2) {
        this.doorOpenType = doorOpenType2;
        LogUtils.v("AccDoor", "setDoorOpenType: " + doorOpenType2);
    }

    public void setDoorSensorType(DoorSensorType doorSensorType2) {
        this.doorSensorType = doorSensorType2;
        LogUtils.v("AccDoor", "setDoorSensorType: " + doorSensorType2);
    }

    public void setGateSwitchMode(int i) {
        this.gateSwitchMode = i;
        LogUtils.v("AccDoor", "setGateSwitchMode: " + i);
    }

    public void setLocalAlarmSwitch(int i) {
        this.localAlarmSwitch = i;
        LogUtils.v("AccDoor", "setLocalAlarmSwitch: " + i);
    }

    public int getDoorSensorLocalAlarmDelay() {
        return this.doorSensorLocalAlarmDelay;
    }

    public int getDoorSensorRemoteAlarmDelay() {
        return this.doorSensorRemoteAlarmDelay;
    }

    public void setDoorSensorLocalAlarmDelay(int i) {
        this.doorSensorLocalAlarmDelay = i;
        LogUtils.v("AccDoor", "setDoorSensorLocalAlarmDelay: " + i);
    }

    public void setDoorSensorRemoteAlarmDelay(int i) {
        this.doorSensorRemoteAlarmDelay = i;
        LogUtils.v("AccDoor", "setDoorSensorRemoteAlarmDelay: " + i);
    }

    public void setLockDriveDurationInSeconds(int i) {
        this.lockDriveDurationInSeconds = i;
        LogUtils.v("AccDoor", "setLockDriveDurationInSeconds: " + i);
    }

    public boolean isRemoteAlarmOn() {
        return this.isRemoteAlarmOn;
    }

    public void setRemoteAlarmOn(boolean z) {
        this.isRemoteAlarmOn = z;
        LogUtils.v("AccDoor", "setRemoteAlarmOn: " + this.isRemoteAlarmOn);
    }

    public long getAntiTamperAlarmTime() {
        return this.antiTamperAlarmTime;
    }

    public long getUnsafeOpenAlarmTime() {
        return this.unsafeOpenAlarmTime;
    }

    public void setAntiTamperAlarmTime(long j) {
        this.antiTamperAlarmTime = j;
        LogUtils.v("AccDoor", "setAntiTamperAlarmTime: " + j);
    }

    public void setUnsafeOpenAlarmTime(long j) {
        this.unsafeOpenAlarmTime = j;
        LogUtils.v("AccDoor", "setUnsafeOpenAlarmTime: " + j);
    }

    public long getCancelRemoteAlarmTime() {
        return this.cancelRemoteAlarmTime;
    }

    public void setCancelRemoteAlarmTime(long j) {
        this.cancelRemoteAlarmTime = j;
        LogUtils.v("AccDoor", "setCancelRemoteAlarmTime: " + j);
    }
}
