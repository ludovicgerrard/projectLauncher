package com.zktechnology.android.verify.bean.process;

public class ZKVerifyHistory {
    private long lastVerifyTime;
    private int totalVerifyCount;
    private String userPin;
    private int verifyCount;

    public String getUserPin() {
        return this.userPin;
    }

    public void setUserPin(String str) {
        this.userPin = str;
    }

    public int getVerifyCount() {
        return this.verifyCount;
    }

    public void setVerifyCount(int i) {
        this.verifyCount = i;
    }

    public int getTotalVerifyCount() {
        return this.totalVerifyCount;
    }

    public void setTotalVerifyCount(int i) {
        this.totalVerifyCount = i;
    }

    public long getLastVerifyTime() {
        return this.lastVerifyTime;
    }

    public void setLastVerifyTime(long j) {
        this.lastVerifyTime = j;
    }

    public String toString() {
        return "ZKVerifyHistory{userPin='" + this.userPin + '\'' + ", verifyCount=" + this.verifyCount + ", totalVerifyCount=" + this.totalVerifyCount + ", lastVerifyTime=" + this.lastVerifyTime + '}';
    }
}
