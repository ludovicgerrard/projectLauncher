package com.zkteco.edk.finger.lib;

public interface IFingerprintVerifyListener {
    void onFingerprintCaptureError(String str);

    void onFingerprintCaptureSuccess(byte[] bArr, int i, int i2);

    void onFingerprintExtractFail(int i);

    void onFingerprintSensorExtractSuccess(byte[] bArr);

    void onFingerprintStateCallback(int i);
}
