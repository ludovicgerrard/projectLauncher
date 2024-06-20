package com.zktechnology.android.helper;

public interface OnMcuReadListener {
    void onRs232Read(byte[] bArr);

    void onRs485Read(byte[] bArr);

    void onWiegandRead(String str);
}
