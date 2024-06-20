package com.zkteco.LicenseManager;

public class LicenseManager {
    public native int CheckLicense(byte[] bArr, int[] iArr, byte[] bArr2, int[] iArr2);

    public native int GetHardwareId(byte[] bArr, int[] iArr);

    public native int GetLicSDKVersion(byte[] bArr);

    public native int InitResource();

    public native int SetLicense(String str, int i);

    public native int SetLicensePath(String str);

    static {
        System.loadLibrary("LicenseManager");
    }
}
