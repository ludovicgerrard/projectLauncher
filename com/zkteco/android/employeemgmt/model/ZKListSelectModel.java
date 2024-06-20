package com.zkteco.android.employeemgmt.model;

public class ZKListSelectModel {
    private String content;
    private boolean selectStatus;
    private int value;

    public ZKListSelectModel(String str, boolean z) {
        this.content = str;
        this.selectStatus = z;
    }

    public ZKListSelectModel(String str) {
        this.content = str;
    }

    public ZKListSelectModel(String str, int i) {
        this.content = str;
        this.value = i;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public boolean isSelectStatus() {
        return this.selectStatus;
    }

    public ZKListSelectModel setSelectStatus(boolean z) {
        this.selectStatus = z;
        return this;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }
}
