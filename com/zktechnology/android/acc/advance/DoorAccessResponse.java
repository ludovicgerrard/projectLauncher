package com.zktechnology.android.acc.advance;

import com.zktechnology.android.acc.DoorOpenType;

public class DoorAccessResponse {
    private boolean adminContinuousVerify;
    private boolean canProceed;
    private DoorOpenType doorOpenType;
    private int errorCode;
    private String errorMessage;
    private String firstOpenUserPin;
    private boolean isRemoteAlarmOn;
    private boolean isStressFingerAlarmOn;
    private boolean isStressPasswordAlarmOn;
    private int remoteAlarmDelay;
    private boolean tempHigh;
    private double temperature;
    private int wearMask;

    public String getFirstOpenUserPin() {
        return this.firstOpenUserPin;
    }

    public void setFirstOpenUserPin(String str) {
        this.firstOpenUserPin = str;
    }

    public boolean isStressFingerAlarmOn() {
        return this.isStressFingerAlarmOn;
    }

    public void setStressFingerAlarmOn(boolean z) {
        this.isStressFingerAlarmOn = z;
    }

    public boolean isStressPasswordAlarmOn() {
        return this.isStressPasswordAlarmOn;
    }

    public void setStressPasswordAlarmOn(boolean z) {
        this.isStressPasswordAlarmOn = z;
    }

    public void setDoorOpenType(DoorOpenType doorOpenType2) {
        this.doorOpenType = doorOpenType2;
    }

    public DoorOpenType getDoorOpenType() {
        return this.doorOpenType;
    }

    public boolean isRemoteAlarmOn() {
        return this.isRemoteAlarmOn;
    }

    public void setRemoteAlarmOn(boolean z) {
        this.isRemoteAlarmOn = z;
    }

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

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int i) {
        this.errorCode = i;
    }

    public int getRemoteAlarmDelay() {
        return this.remoteAlarmDelay;
    }

    public void setRemoteAlarmDelay(int i) {
        this.remoteAlarmDelay = i;
    }

    public boolean isAdminContinuousVerify() {
        return this.adminContinuousVerify;
    }

    public void setAdminContinuousVerify(boolean z) {
        this.adminContinuousVerify = z;
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

    public String toString() {
        return "DoorAccessResponse{canProceed=" + this.canProceed + ", errorMessage='" + this.errorMessage + '\'' + ", errorCode=" + this.errorCode + ", doorOpenType=" + this.doorOpenType + ", isRemoteAlarmOn=" + this.isRemoteAlarmOn + ", remoteAlarmDelay=" + this.remoteAlarmDelay + ", isStressFingerAlarmOn=" + this.isStressFingerAlarmOn + ", isStressPasswordAlarmOn=" + this.isStressPasswordAlarmOn + ", firstOpenUserPin='" + this.firstOpenUserPin + '\'' + ", adminContinuousVerify=" + this.adminContinuousVerify + ", temperature=" + this.temperature + ", tempHigh=" + this.tempHigh + ", wearMask=" + this.wearMask + '}';
    }
}
