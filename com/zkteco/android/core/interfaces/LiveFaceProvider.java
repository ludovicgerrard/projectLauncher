package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class LiveFaceProvider extends AbstractProvider implements LiveFaceInterface {
    public LiveFaceProvider(Provider provider) {
        super(provider);
    }

    public int init(long[] jArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.INIT, jArr)).intValue();
    }

    public int bayerToBGR24(byte[] bArr, int i, int i2, byte[] bArr2) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.BAYERTOBGR24, bArr, Integer.valueOf(i), Integer.valueOf(i2), bArr2)).intValue();
    }

    public int closeFaceContext(long j) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.CLOSEFACECONTEXT, Long.valueOf(j))).intValue();
    }

    public int dbAdd(long j, String str, byte[] bArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.DBADD, bArr)).intValue();
    }

    public int dbClear(long j) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.DBCLEAR, Long.valueOf(j))).intValue();
    }

    public int dbCount(long j, int[] iArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.DBCOUNT, Long.valueOf(j), iArr)).intValue();
    }

    public int dbDel(long j, String str) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.DBDEL, Long.valueOf(j), str)).intValue();
    }

    public int dbIdentify(long j, byte[] bArr, byte[] bArr2, int[] iArr, int[] iArr2, int i, int i2) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.DBIDENTIFY, bArr, bArr2, iArr, iArr2, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
    }

    public int detectFaces(long j, byte[] bArr, int i, int i2, int[] iArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.DETECTFACES, Long.valueOf(j), bArr, Integer.valueOf(i), Integer.valueOf(i2), iArr)).intValue();
    }

    public int detectFacesExt(long j, byte[] bArr, int i, int i2, int[] iArr, int i3) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.DETECTFACESEXT, bArr, Integer.valueOf(i), Integer.valueOf(i2), iArr, Integer.valueOf(i3))).intValue();
    }

    public int extractTemplate(long j, byte[] bArr, int[] iArr, int[] iArr2) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.EXTRACTTEMPLATE, Long.valueOf(j), bArr, iArr, iArr2)).intValue();
    }

    public int getFaceFeature(long j, int i, int[] iArr, int[] iArr2, int[] iArr3) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.GETFACEFEATURE, Long.valueOf(j), Integer.valueOf(i), iArr, iArr2, iArr3)).intValue();
    }

    public int getLastError(long j, byte[] bArr, int[] iArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.GETLASTERROR, bArr, iArr)).intValue();
    }

    public int getHardwareId(byte[] bArr, int[] iArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.GETHARDWAREID, bArr, iArr)).intValue();
    }

    public int version(byte[] bArr, int[] iArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.VERSION, bArr, iArr)).intValue();
    }

    public int loadImage(String str, byte[] bArr, int[] iArr, int[] iArr2, int[] iArr3) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.LOADIMAGE, str, bArr, iArr, iArr2, iArr3)).intValue();
    }

    public int setParameter(long j, int i, byte[] bArr, int i2) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.SETPARAMETER, Long.valueOf(j), Integer.valueOf(i), bArr, Integer.valueOf(i2))).intValue();
    }

    public int getParameter(long j, int i, byte[] bArr, int[] iArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.GETPARAMETE, Long.valueOf(j), Integer.valueOf(i), bArr, iArr)).intValue();
    }

    public int terminate(long j) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.TERMINATE, Long.valueOf(j))).intValue();
    }

    public int getFaceContext(long j, int i, long[] jArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.GETFACECONTEXT, Long.valueOf(j), Integer.valueOf(i), jArr)).intValue();
    }

    public int getCropImageData(long j, int[] iArr, int[] iArr2, int[] iArr3, byte[] bArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.GETCROPIMAGEDATA, Long.valueOf(j), iArr, iArr2, iArr3, bArr)).intValue();
    }

    public int getFaceICaoFeature(long j, int i, int[] iArr) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.GETFACEICAOFEATURE, Long.valueOf(j), Integer.valueOf(i), iArr)).intValue();
    }

    public int getFaceRect(long j, int[] iArr, int i) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.GETFACERECT, Long.valueOf(j), iArr, Integer.valueOf(i))).intValue();
    }

    public int verify(long j, byte[] bArr, byte[] bArr2, int[] iArr) {
        return ((Integer) getProvider().invoke("fp-verify", Long.valueOf(j), bArr, bArr2, iArr)).intValue();
    }

    public int loadImageFromMemoryExt(byte[] bArr, int i, byte[] bArr2, int[] iArr, int[] iArr2, int[] iArr3) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.LOADIMAGEFROMMEMORYEXT, bArr, bArr2, iArr, iArr2, iArr3)).intValue();
    }

    public int setThumbnailParameter(long j, int i, int i2, float f, float f2) {
        return ((Integer) getProvider().invoke(LiveFaceInterface.SETTHUMBNAILPARAMETER, Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2), Float.valueOf(f), Float.valueOf(f2))).intValue();
    }
}
