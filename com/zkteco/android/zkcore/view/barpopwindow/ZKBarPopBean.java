package com.zkteco.android.zkcore.view.barpopwindow;

import java.io.Serializable;

public class ZKBarPopBean implements Serializable {
    private String name;
    private boolean state;

    public ZKBarPopBean() {
    }

    public ZKBarPopBean(String str, boolean z) {
        this.name = str;
        this.state = z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public boolean isState() {
        return this.state;
    }

    public void setState(boolean z) {
        this.state = z;
    }
}
