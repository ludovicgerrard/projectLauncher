package com.zkteco.android.employeemgmt.model;

import com.zkteco.android.db.orm.tna.UserInfo;
import java.io.Serializable;

public class ZKStaffBean implements Serializable, Cloneable {
    private String iconPath;
    private boolean isCardLight;
    private boolean isCkSelected;
    private boolean isFaceLight;
    private boolean isFingerLight;
    private boolean isPalmPrintLight;
    private boolean isPdLight;
    private UserInfo userInfo;

    public boolean isPalmPrintLight() {
        return this.isPalmPrintLight;
    }

    public void setPalmPrintLight(boolean z) {
        this.isPalmPrintLight = z;
    }

    public ZKStaffBean(boolean z, UserInfo userInfo2, String str, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.isCkSelected = z;
        this.userInfo = userInfo2;
        this.iconPath = str;
        this.isFingerLight = z2;
        this.isCardLight = z3;
        this.isPdLight = z4;
        this.isFaceLight = z5;
        this.isPalmPrintLight = z6;
    }

    public boolean isCkSelected() {
        return this.isCkSelected;
    }

    public void setCkSelected(boolean z) {
        this.isCkSelected = z;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo userInfo2) {
        this.userInfo = userInfo2;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public void setIconPath(String str) {
        this.iconPath = str;
    }

    public boolean isFingerLight() {
        return this.isFingerLight;
    }

    public void setFingerLight(boolean z) {
        this.isFingerLight = z;
    }

    public boolean isCardLight() {
        return this.isCardLight;
    }

    public void setCardLight(boolean z) {
        this.isCardLight = z;
    }

    public boolean isPdLight() {
        return this.isPdLight;
    }

    public void setPdLight(boolean z) {
        this.isPdLight = z;
    }

    public boolean isFaceLight() {
        return this.isFaceLight;
    }

    public void setFaceLight(boolean z) {
        this.isFaceLight = z;
    }

    /* access modifiers changed from: protected */
    public Object clone() throws CloneNotSupportedException {
        return (ZKStaffBean) super.clone();
    }
}
