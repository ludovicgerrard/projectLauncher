package com.zktechnology.android.verify.bean.process;

public class ZKCaptureBean {
    private byte[] data;
    private String path;

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }
}
