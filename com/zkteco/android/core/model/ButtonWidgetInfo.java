package com.zkteco.android.core.model;

import java.io.Serializable;

public class ButtonWidgetInfo implements Serializable {
    private ActionModel action;
    private int iconNormalRes;
    private int iconSelectRes;
    private String isDefault;
    private String name;
    private int nameResId;
    private int position;
    private boolean selectState = false;
    private int widgetId;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getWidgetId() {
        return this.widgetId;
    }

    public void setWidgetId(int i) {
        this.widgetId = i;
    }

    public int getIconNormalRes() {
        return this.iconNormalRes;
    }

    public void setIconNormalRes(int i) {
        this.iconNormalRes = i;
    }

    public int getIconSelectRes() {
        return this.iconSelectRes;
    }

    public void setIconSelectRes(int i) {
        this.iconSelectRes = i;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public ActionModel getAction() {
        return this.action;
    }

    public void setAction(ActionModel actionModel) {
        this.action = actionModel;
    }

    public boolean isSelectState() {
        return this.selectState;
    }

    public void setSelectState(boolean z) {
        this.selectState = z;
    }

    public String getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(String str) {
        this.isDefault = str;
    }

    public int getNameResId() {
        return this.nameResId;
    }

    public void setNameResId(int i) {
        this.nameResId = i;
    }
}
