package com.zkteco.android.employeemgmt.model;

public class ZkStaffTypeModel {
    public static final int TYPE_ARROW = 1;
    public static final int TYPE_INPUT = 2;
    public static final int TYPE_RADIO_BUTTON = 3;
    private int id;
    private String leftContent;
    private boolean radioBtnStatus;
    private String rightParams;
    private int type;
    private String value = "";

    public ZkStaffTypeModel(String str) {
        this.leftContent = str;
    }

    public ZkStaffTypeModel(String str, String str2) {
        this.leftContent = str;
        this.value = str2;
    }

    public ZkStaffTypeModel(String str, int i, int i2) {
        this.leftContent = str;
        this.type = i;
        this.id = i2;
    }

    public String getLeftContent() {
        return this.leftContent;
    }

    public void setLeftContent(String str) {
        this.leftContent = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public boolean isRadioBtnStatus() {
        return this.radioBtnStatus;
    }

    public void setRadioBtnStatus(boolean z) {
        this.radioBtnStatus = z;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getRightParams() {
        return this.rightParams;
    }

    public void setRightParams(String str) {
        this.rightParams = str;
    }
}
