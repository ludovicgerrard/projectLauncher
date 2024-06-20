package com.zkteco.liveface562.bean;

public class ZkEnrollResult {
    public byte[] faceToken;
    public boolean result = false;

    public ZkEnrollResult(boolean z, byte[] bArr) {
        this.result = z;
        this.faceToken = bArr;
    }
}
