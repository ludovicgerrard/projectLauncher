package com.zkteco.edk.yuv.lib.glsl;

import java.io.Serializable;

public class ZkArgbData implements Serializable {
    private int mDisplayOrientation;
    private boolean mFlipX = false;
    private boolean mFlipY = false;
    private int mHeight;
    private byte[] mImageData;
    private int mWidth;

    public int getImageLength() {
        return this.mWidth * this.mHeight * 4;
    }

    public byte[] getImageData() {
        return this.mImageData;
    }

    public void setImageData(byte[] bArr) {
        this.mImageData = bArr;
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
