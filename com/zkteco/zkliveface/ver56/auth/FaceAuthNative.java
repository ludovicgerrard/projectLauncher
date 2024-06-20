package com.zkteco.zkliveface.ver56.auth;

public class FaceAuthNative {
    public static final int a = 0;
    public static final int b = -1;

    /* renamed from: c  reason: collision with root package name */
    public static final int f133c = -2;
    public static final int d = -3;
    public static final int e = -4;
    public static final int f = -5;
    public static final int g = -6;
    public static final int h = -7;
    public static final int i = -8;
    public static final int j = -9;
    public static final int k = -10;
    public static final int l = -11;
    public static final int m = -12;
    public static final int n = -13;
    public static final int o = -14;
    public static final int p = -15;
    public static final int q = -16;
    public static final int r = -17;
    public static final int s = -18;
    public static final int t = -19;
    public static final int u = -20;

    public static native boolean getChipAuthStatus();

    public static native String getDeviceFingerprint();

    public static native long getExpireTime();

    public static native String getHardwareID();

    public static native String getLasterror();

    public static native int getLasterrorCode();

    public static native int init(boolean z);

    public static native int setLicense(String str);

    private FaceAuthNative() {
    }

    static {
        System.loadLibrary("LicenseManager-0.3.1");
        System.loadLibrary("zkfaceauth-1.0");
    }
}
