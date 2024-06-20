package com.zktechnology.android.bean;

import android.os.SystemClock;

public class ExtUserBean {
    private String cmdId;
    private int result = -1;
    private long timeStamp = SystemClock.elapsedRealtime();
    private String userPin;

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public String getUserPin() {
        return this.userPin;
    }

    public void setUserPin(String str) {
        this.userPin = str;
    }

    public String getCmdId() {
        return this.cmdId;
    }

    public void setCmdId(String str) {
        this.cmdId = str;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int i) {
        this.result = i;
    }

    public String toString() {
        return "userPin=" + this.userPin + ";cmdId=" + this.cmdId + ";result=" + this.result;
    }

    public int hashCode() {
        return this.userPin.hashCode();
    }

    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }
}
