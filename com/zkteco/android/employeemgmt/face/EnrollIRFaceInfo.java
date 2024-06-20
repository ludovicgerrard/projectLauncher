package com.zkteco.android.employeemgmt.face;

import java.io.Serializable;

public class EnrollIRFaceInfo implements Cloneable, Serializable {
    private byte[] data;
    private int height;
    private String template;
    private int width;

    public EnrollIRFaceInfo() {
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

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] bArr) {
        if (this.data == null && bArr != null) {
            this.data = new byte[bArr.length];
        }
        if (bArr != null) {
            System.arraycopy(bArr, 0, this.data, 0, bArr.length);
        } else {
            this.data = null;
        }
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String str) {
        if (str != null) {
            this.template = str;
        } else {
            this.template = null;
        }
    }

    private EnrollIRFaceInfo(EnrollIRFaceInfo enrollIRFaceInfo) throws IllegalStateException {
        setHeight(enrollIRFaceInfo.getHeight());
        setWidth(enrollIRFaceInfo.getWidth());
        setData(enrollIRFaceInfo.getData());
        setTemplate(enrollIRFaceInfo.getTemplate());
    }

    public EnrollIRFaceInfo clone() throws CloneNotSupportedException {
        try {
            return new EnrollIRFaceInfo(this);
        } catch (IllegalStateException e) {
            throw new CloneNotSupportedException(e.getMessage());
        }
    }
}
