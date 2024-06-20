package com.zktechnology.android.helper;

public interface OnFingerprintScanListener {
    void onFingerprintCapture(byte[] bArr, int i, int i2);

    void onFingerprintExtract(byte[] bArr);
}
