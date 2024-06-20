package com.zkteco.biometric;

public class ZKBioTemplateService {
    public static native int mergePalmTemplate(byte[][] bArr, byte[] bArr2);

    public static native int splitPalmTemplate(byte[] bArr, byte[][] bArr2, int[] iArr);

    static {
        System.loadLibrary("zkbiotemplate");
    }
}
