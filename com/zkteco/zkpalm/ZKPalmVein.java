package com.zkteco.zkpalm;

public class ZKPalmVein {
    private static volatile ZKPalmVein zkPalmVein;

    public native int ZKGetLicense(Object obj, int i, byte[] bArr);

    public native int ZKPalmDetectForEnroll(byte[] bArr, byte[] bArr2, int[] iArr, int[] iArr2);

    public native int ZKPalmDetectForMatch(byte[] bArr, byte[] bArr2, int[] iArr);

    public native int ZKPalmVeinDBCount(byte[] bArr);

    public native int ZKPalmVeinDBCountByID(byte[] bArr, String str);

    public native int ZKPalmVeinDBDel(byte[] bArr, String str);

    public native int ZKPalmVeinDBGet(byte[] bArr, String str, byte[] bArr2, int i);

    public native int ZKPalmVeinDBIdentify(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, int i2);

    public native int ZKPalmVeinDBReset(byte[] bArr);

    public native int ZKPalmVeinDBSet(byte[] bArr, String str, byte[] bArr2, int i);

    public native int ZKPalmVeinDBVerify(byte[] bArr, byte[] bArr2, String str);

    public native int ZKPalmVeinEnroll(byte[] bArr, byte[] bArr2, byte[] bArr3);

    public native int ZKPalmVeinExtract(byte[] bArr, byte[] bArr2, int[] iArr, byte[] bArr3);

    public native int ZKPalmVeinExtractFromROI(byte[] bArr, int[] iArr, byte[] bArr2);

    public native int ZKPalmVeinExtractRawEnrollFeature(byte[] bArr, byte[] bArr2, int[] iArr, byte[] bArr3, byte[] bArr4, int[] iArr2);

    public native int ZKPalmVeinExtractRawEnrollFeatureFromROI(byte[] bArr, int[] iArr, byte[] bArr2, byte[] bArr3);

    public native void ZKPalmVeinFinal(byte[] bArr);

    public native int ZKPalmVeinGetParam(byte[] bArr, int i);

    public native int ZKPalmVeinGetTemplateSize(byte[] bArr, byte b);

    public native int ZKPalmVeinInit(int i, int i2, boolean z);

    public native int ZKPalmVeinSetParam(byte[] bArr, int i, int i2);

    public native int ZKPalmVeinVerify(byte[] bArr, byte[] bArr2, byte[] bArr3);

    public native int ZKPalmVeinVersion(int[] iArr, int[] iArr2, int[] iArr3);

    public static ZKPalmVein getInstance() {
        if (zkPalmVein == null) {
            synchronized (ZKPalmVein.class) {
                if (zkPalmVein == null) {
                    zkPalmVein = new ZKPalmVein();
                }
            }
        }
        return zkPalmVein;
    }

    static {
        System.loadLibrary("zkpalm");
    }
}
