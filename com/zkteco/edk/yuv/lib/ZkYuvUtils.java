package com.zkteco.edk.yuv.lib;

public class ZkYuvUtils {
    public static native int getYUV420SPLuminance(byte[] bArr, int i, int i2);

    public static native int motionDetect(byte[] bArr, byte[] bArr2, int i, int i2);

    public static native int nativeARGBToNV21(byte[] bArr, byte[] bArr2, int i, int i2, int i3, boolean z);

    public static native int nativeCropI420(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4, int i5, int i6);

    public static native int nativeI420ToNV21(byte[] bArr, byte[] bArr2, int i, int i2);

    public static native int nativeMirrorI420(byte[] bArr, int i, int i2, byte[] bArr2);

    public static native int nativeNV21ToI420(byte[] bArr, byte[] bArr2, int i, int i2);

    public static native int nativeNV21Transform(byte[] bArr, byte[] bArr2, int i, int i2, int i3, boolean z);

    public static native int nativeRotateI420(byte[] bArr, byte[] bArr2, int i, int i2, int i3);

    public static native int nativeScaleI420(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4, int i5);

    public static native int nativeYUY2ToI420(byte[] bArr, byte[] bArr2, int i, int i2);

    public static native int nativeYUY2ToNV21(byte[] bArr, byte[] bArr2, int i, int i2, int i3, boolean z);

    static {
        System.loadLibrary("ZkYUV");
    }
}
