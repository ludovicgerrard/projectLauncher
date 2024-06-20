package com.zktechnology.android.utils;

public class Size {
    private int height;
    private int width;

    public Size() {
    }

    public Size(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public String toString() {
        return String.format("Width[%s] Height[%s]", new Object[]{Integer.valueOf(this.width), Integer.valueOf(this.height)});
    }
}
