package com.zktechnology.android.verify.bean;

import android.graphics.drawable.Drawable;

public class ZKNotifyBeen {
    private String details;
    private Drawable icon;

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String str) {
        this.details = str;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }
}
