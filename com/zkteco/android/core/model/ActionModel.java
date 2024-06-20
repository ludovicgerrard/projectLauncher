package com.zkteco.android.core.model;

import java.io.Serializable;

public class ActionModel implements Serializable {
    private String actionName;
    private String actionParams;
    private int actionResId;
    private int actionType;
    private long attenId = 0;
    private int iconPosition = 0;
    private String isDefault;

    public int getIconPosition() {
        return this.iconPosition;
    }

    public void setIconPosition(int i) {
        this.iconPosition = i;
    }

    public int getActionType() {
        return this.actionType;
    }

    public void setActionType(int i) {
        this.actionType = i;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String str) {
        this.actionName = str;
    }

    public String getActionParams() {
        return this.actionParams;
    }

    public void setActionParams(String str) {
        this.actionParams = str;
    }

    public long getAttenId() {
        return this.attenId;
    }

    public void setAttenId(long j) {
        this.attenId = j;
    }

    public String getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(String str) {
        this.isDefault = str;
    }

    public int getActionResId() {
        return this.actionResId;
    }

    public void setActionResId(int i) {
        this.actionResId = i;
    }
}
