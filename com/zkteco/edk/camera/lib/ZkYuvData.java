package com.zkteco.edk.camera.lib;

import java.io.Serializable;

public class ZkYuvData implements Serializable {
    int displayOrientation;
    boolean flipX = false;
    boolean flipY = false;
    int height;
    int width;
    byte[] yuv;

    public ZkYuvData(byte[] bArr, int i, int i2) {
        this.yuv = bArr;
        this.width = i;
        this.height = i2;
    }

    public ZkYuvData(byte[] bArr, int i, int i2, int i3) {
        this.yuv = bArr;
        this.width = i;
        this.height = i2;
        this.displayOrientation = i3;
    }

    public ZkYuvData(byte[] bArr, int i, int i2, int i3, boolean z, boolean z2) {
        this.yuv = bArr;
        this.width = i;
        this.height = i2;
        this.displayOrientation = i3;
        this.flipX = z;
        this.flipY = z2;
    }

    public int getYLength() {
        return this.width * this.height;
    }

    public int getUVLength() {
        return (this.width * this.height) / 2;
    }

    public byte[] getYuv() {
        return this.yuv;
    }

    public void setYuv(byte[] bArr) {
        this.yuv = bArr;
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

    public int getDisplayOrientation() {
        return this.displayOrientation;
    }

    public void setDisplayOrientation(int i) {
        this.displayOrientation = i;
    }

    public boolean isFlipX() {
        return this.flipX;
    }

    public void setFlipX(boolean z) {
        this.flipX = z;
    }

    public boolean isFlipY() {
        return this.flipY;
    }

    public void setFlipY(boolean z) {
        this.flipY = z;
    }
}
