package com.zkteco.android.employeemgmt.face;

import java.io.Serializable;

public class EnrollVLFaceInfo implements Cloneable, Serializable {
    private int height;
    private byte[] nv21RawData;
    private byte[] template;
    private int width;

    public EnrollVLFaceInfo() {
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

    public byte[] getNV21RawData() {
        return this.nv21RawData;
    }

    public void setNV21RawData(byte[] bArr) {
        if (this.nv21RawData == null && bArr != null) {
            this.nv21RawData = new byte[bArr.length];
        }
        if (bArr != null) {
            System.arraycopy(bArr, 0, this.nv21RawData, 0, bArr.length);
        } else {
            this.nv21RawData = null;
        }
    }

    public byte[] getTemplate() {
        return this.template;
    }

    public void setTemplate(byte[] bArr) {
        if (this.template == null && bArr != null) {
            this.template = new byte[bArr.length];
        }
        if (bArr != null) {
            System.arraycopy(bArr, 0, this.template, 0, bArr.length);
        } else {
            this.template = null;
        }
    }

    private EnrollVLFaceInfo(EnrollVLFaceInfo enrollVLFaceInfo) throws IllegalStateException {
        setHeight(enrollVLFaceInfo.getHeight());
        setWidth(enrollVLFaceInfo.getWidth());
        setNV21RawData(enrollVLFaceInfo.getNV21RawData());
        setTemplate(enrollVLFaceInfo.getTemplate());
    }

    public EnrollVLFaceInfo clone() throws CloneNotSupportedException {
        try {
            return new EnrollVLFaceInfo(this);
        } catch (IllegalStateException e) {
            throw new CloneNotSupportedException(e.getMessage());
        }
    }
}
