package com.zktechnology.android.att;

import com.zktechnology.android.acc.DoorSensorType;
import com.zktechnology.android.utils.DBManager;

public class AttParameter {
    public static AttParameter mInstance;
    private boolean AAFO = false;
    private boolean APBFO = false;
    private boolean DU11 = false;
    private boolean DU1N = false;
    private int DUAD = 0;
    private boolean DUPWD = false;
    private int ERRTimes = 0;
    private int ErrTimeInterval = 8;
    private int alwayClose = 0;
    private int alwaysOpen = 0;
    private boolean auxInFunOn = false;
    private int auxInKeepTime = 0;
    private int auxInOption = 0;
    private int doorSensorAlarmDelay = 0;
    private int doorSensorDelay = 0;
    private DoorSensorType doorSensorType = DoorSensorType.NONE;
    private boolean extAlarm = false;
    private boolean gateMode = false;
    private boolean isHolidayValid = false;
    private boolean localAlarm = false;
    private double lockDriveTime = 0.0d;

    public static AttParameter getInstance() {
        if (mInstance == null) {
            synchronized (AttParameter.class) {
                if (mInstance == null) {
                    mInstance = new AttParameter();
                }
            }
        }
        return mInstance;
    }

    public static AttParameter getmInstance() {
        return mInstance;
    }

    public static void setmInstance(AttParameter attParameter) {
        mInstance = attParameter;
    }

    public double getLockDriveTime() {
        return this.lockDriveTime;
    }

    public void setLockDriveTime(double d) {
        this.lockDriveTime = d;
    }

    public boolean isGateMode() {
        return this.gateMode;
    }

    public void setGateMode(boolean z) {
        this.gateMode = z;
    }

    public int getDoorSensorDelay() {
        return this.doorSensorDelay;
    }

    public void setDoorSensorDelay(int i) {
        this.doorSensorDelay = i;
    }

    public DoorSensorType getDoorSensorType() {
        return this.doorSensorType;
    }

    public void setDoorSensorType(DoorSensorType doorSensorType2) {
        this.doorSensorType = doorSensorType2;
    }

    public int getDoorSensorAlarmDelay() {
        return this.doorSensorAlarmDelay;
    }

    public void setDoorSensorAlarmDelay(int i) {
        this.doorSensorAlarmDelay = i;
    }

    public boolean isAAFO() {
        return this.AAFO;
    }

    public void setAAFO(boolean z) {
        this.AAFO = z;
    }

    public boolean isLocalAlarm() {
        return this.localAlarm;
    }

    public void setLocalAlarm(boolean z) {
        this.localAlarm = z;
    }

    public boolean isExtAlarm() {
        return this.extAlarm;
    }

    public void setExtAlarm(boolean z) {
        this.extAlarm = z;
    }

    public int getAlwaysOpen() {
        return this.alwaysOpen;
    }

    public void setAlwaysOpen(int i) {
        this.alwaysOpen = i;
    }

    public int getAlwayClose() {
        return this.alwayClose;
    }

    public void setAlwayClose(int i) {
        this.alwayClose = i;
    }

    public boolean isHolidayValid() {
        return this.isHolidayValid;
    }

    public void setHolidayValid(boolean z) {
        this.isHolidayValid = z;
    }

    public int getERRTimes() {
        return this.ERRTimes;
    }

    public void setERRTimes(int i) {
        this.ERRTimes = i;
    }

    public int getErrTimeInterval() {
        return this.ErrTimeInterval;
    }

    public void setErrTimeInterval(int i) {
        this.ErrTimeInterval = i;
    }

    public boolean isAuxInFunOn() {
        return this.auxInFunOn;
    }

    public void setAuxInFunOn(boolean z) {
        this.auxInFunOn = z;
    }

    public int getAuxInOption() {
        return this.auxInOption;
    }

    public void setAuxInOption(int i) {
        this.auxInOption = i;
    }

    public int getAuxInKeepTime() {
        return this.auxInKeepTime;
    }

    public void setAuxInKeepTime(int i) {
        this.auxInKeepTime = i;
    }

    public boolean isDUPWD() {
        boolean equals = DBManager.getInstance().getStrOption("DUPWD", "0").equals("1");
        this.DUPWD = equals;
        return equals;
    }

    public void setDUPWD(boolean z) {
        this.DUPWD = z;
    }

    public boolean isDU11() {
        boolean equals = DBManager.getInstance().getStrOption("DU11", "0").equals("1");
        this.DU11 = equals;
        return equals;
    }

    public void setDU11(boolean z) {
        this.DU11 = z;
    }

    public boolean isDU1N() {
        boolean equals = DBManager.getInstance().getStrOption("DU1N", "0").equals("1");
        this.DU1N = equals;
        return equals;
    }

    public void setDU1N(boolean z) {
        this.DU1N = z;
    }

    public int getDUAD() {
        int intOption = DBManager.getInstance().getIntOption("DUAD", 0);
        this.DUAD = intOption;
        return intOption;
    }

    public void setDUAD(int i) {
        this.DUAD = i;
    }

    public boolean isAPBFO() {
        return this.APBFO;
    }

    public void setAPBFO(boolean z) {
        this.APBFO = z;
    }
}
