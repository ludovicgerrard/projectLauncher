package com.zktechnology.android.verify.bean.process;

public class ZKMarkTypeBean {
    private boolean state = false;
    private int type;

    public ZKMarkTypeBean() {
    }

    public ZKMarkTypeBean(int i) {
        this.type = i;
    }

    public ZKMarkTypeBean(int i, boolean z) {
        this.type = i;
        this.state = z;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public boolean isState() {
        return this.state;
    }

    public void setState(boolean z) {
        this.state = z;
    }
}
