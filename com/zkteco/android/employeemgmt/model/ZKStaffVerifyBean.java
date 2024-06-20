package com.zkteco.android.employeemgmt.model;

public class ZKStaffVerifyBean {
    public boolean isShow;
    public String string;
    private int value;

    public ZKStaffVerifyBean(boolean z, String str) {
        this.string = str;
    }

    public ZKStaffVerifyBean(String str, int i) {
        this.string = str;
        this.value = i;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String str) {
        this.string = str;
    }

    public boolean getShow() {
        return this.isShow;
    }

    public void setShow(boolean z) {
        this.isShow = z;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }
}
