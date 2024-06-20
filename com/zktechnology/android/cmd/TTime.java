package com.zktechnology.android.cmd;

public class TTime {
    long tm_gmtoff;
    private int tm_hour;
    private int tm_isdst;
    private int tm_mday;
    private int tm_min;
    private int tm_mon;
    private int tm_sec;
    private int tm_wday;
    private int tm_yday;
    private int tm_year;
    String tm_zone;

    public TTime() {
    }

    public TTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, long j, String str) {
        this.tm_sec = i;
        this.tm_min = i2;
        this.tm_hour = i3;
        this.tm_mday = i4;
        this.tm_mon = i5;
        this.tm_year = i6;
        this.tm_wday = i7;
        this.tm_yday = i8;
        this.tm_isdst = i9;
        this.tm_gmtoff = j;
        this.tm_zone = str;
    }

    public TTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, long j) {
        this.tm_sec = i;
        this.tm_min = i2;
        this.tm_hour = i3;
        this.tm_mday = i4;
        this.tm_mon = i5;
        this.tm_year = i6;
        this.tm_wday = i7;
        this.tm_yday = i8;
        this.tm_isdst = i9;
        this.tm_gmtoff = j;
    }

    public int getTm_sec() {
        return this.tm_sec;
    }

    public void setTm_sec(int i) {
        this.tm_sec = i;
    }

    public int getTm_min() {
        return this.tm_min;
    }

    public void setTm_min(int i) {
        this.tm_min = i;
    }

    public int getTm_hour() {
        return this.tm_hour;
    }

    public void setTm_hour(int i) {
        this.tm_hour = i;
    }

    public int getTm_mday() {
        return this.tm_mday;
    }

    public void setTm_mday(int i) {
        this.tm_mday = i;
    }

    public int getTm_mon() {
        return this.tm_mon;
    }

    public void setTm_mon(int i) {
        this.tm_mon = i;
    }

    public int getTm_year() {
        return this.tm_year;
    }

    public void setTm_year(int i) {
        this.tm_year = i;
    }

    public int getTm_wday() {
        return this.tm_wday;
    }

    public void setTm_wday(int i) {
        this.tm_wday = i;
    }

    public int getTm_yday() {
        return this.tm_yday;
    }

    public void setTm_yday(int i) {
        this.tm_yday = i;
    }

    public int getTm_isdst() {
        return this.tm_isdst;
    }

    public void setTm_isdst(int i) {
        this.tm_isdst = i;
    }

    public long getTm_gmtoff() {
        return this.tm_gmtoff;
    }

    public void setTm_gmtoff(long j) {
        this.tm_gmtoff = j;
    }

    public String getTm_zone() {
        return this.tm_zone;
    }

    public void setTm_zone(String str) {
        this.tm_zone = str;
    }

    public String toString() {
        return "TTime{tm_sec=" + this.tm_sec + ", tm_min=" + this.tm_min + ", tm_hour=" + this.tm_hour + ", tm_mday=" + this.tm_mday + ", tm_mon=" + this.tm_mon + ", tm_year=" + this.tm_year + ", tm_wday=" + this.tm_wday + ", tm_yday=" + this.tm_yday + ", tm_isdst=" + this.tm_isdst + ", tm_gmtoff=" + this.tm_gmtoff + ", tm_zone='" + this.tm_zone + '\'' + '}';
    }
}
