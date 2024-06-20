package com.zkteco.edk.yuv.lib.glsl;

import java.io.Serializable;

public class ZkYuvData implements Serializable {
    private int mDisplayOrientation;
    private boolean mFlipX = false;
    private boolean mFlipY = false;
    private int mHeight;
    private int mWidth;
    private byte[] mYuvData;

    public ZkYuvData(byte[] bArr, int i, int i2) {
        this.mYuvData = bArr;
        this.mWidth = i;
        this.mHeight = i2;
    }

    public ZkYuvData(byte[] bArr, int i, int i2, int i3) {
        this.mYuvData = bArr;
        this.mWidth = i;
        this.mHeight = i2;
        this.mDisplayOrientation = i3;
    }

    public ZkYuvData(byte[] bArr, int i, int i2, int i3, boolean z, boolean z2) {
        this.mYuvData = bArr;
        this.mWidth = i;
        this.mHeight = i2;
        this.mDisplayOrientation = i3;
        this.mFlipX = z;
        this.mFlipY = z2;
    }

    public int getYLength() {
        return this.mWidth * this.mHeight;
    }

    public int getUVLength() {
        return (this.mWidth * this.mHeight) / 2;
    }

    public byte[] getYuv() {
        return this.mYuvData;
    }

    public void setYuv(byte[] bArr) {
        this.mYuvData = bArr;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int i) {
        this.mWidth = i;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public int getDisplayOrientation() {
        return this.mDisplayOrientation;
    }

    public void setDisplayOrientation(int i) {
        this.mDisplayOrientation = i;
    }

    public boolean isFlipX() {
        return this.mFlipX;
    }

    public void setFlipX(boolean z) {
        this.mFlipX = z;
    }

    public boolean isFlipY() {
        return this.mFlipY;
    }

    public void setFlipY(boolean z) {
        this.mFlipY = z;
    }
}
