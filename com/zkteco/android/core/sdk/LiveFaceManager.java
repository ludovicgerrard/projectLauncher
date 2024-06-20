package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.LiveFaceInterface;
import com.zkteco.android.core.interfaces.LiveFaceProvider;
import com.zkteco.android.core.library.CoreProvider;

public class LiveFaceManager implements LiveFaceInterface {
    private LiveFaceProvider provider;

    public LiveFaceManager(Context context) {
        this.provider = new LiveFaceProvider(new CoreProvider(context));
    }

    public int init(long[] jArr) {
        return this.provider.init(jArr);
    }

    public int bayerToBGR24(byte[] bArr, int i, int i2, byte[] bArr2) {
        return this.provider.bayerToBGR24(bArr, i, i2, bArr2);
    }

    public int closeFaceContext(long j) {
        return this.provider.closeFaceContext(j);
    }

    public int dbAdd(long j, String str, byte[] bArr) {
        return this.provider.dbAdd(j, str, bArr);
    }

    public int dbClear(long j) {
        return this.provider.dbClear(j);
    }

    public int dbCount(long j, int[] iArr) {
        return this.provider.dbCount(j, iArr);
    }

    public int dbDel(long j, String str) {
        return this.provider.dbDel(j, str);
    }

    public int dbIdentify(long j, byte[] bArr, byte[] bArr2, int[] iArr, int[] iArr2, int i, int i2) {
        return this.provider.dbIdentify(j, bArr, bArr2, iArr, iArr2, i, i2);
    }

    public int detectFaces(long j, byte[] bArr, int i, int i2, int[] iArr) {
        return this.provider.detectFaces(j, bArr, i, i2, iArr);
    }

    public int detectFacesExt(long j, byte[] bArr, int i, int i2, int[] iArr, int i3) {
        return this.provider.detectFacesExt(j, bArr, i, i2, iArr, i3);
    }

    public int extractTemplate(long j, byte[] bArr, int[] iArr, int[] iArr2) {
        return this.provider.extractTemplate(j, bArr, iArr, iArr2);
    }

    public int getFaceFeature(long j, int i, int[] iArr, int[] iArr2, int[] iArr3) {
        return this.provider.getFaceFeature(j, i, iArr, iArr2, iArr3);
    }

    public int getLastError(long j, byte[] bArr, int[] iArr) {
        return this.provider.getLastError(j, bArr, iArr);
    }

    public int getHardwareId(byte[] bArr, int[] iArr) {
        return this.provider.getHardwareId(bArr, iArr);
    }

    public int version(byte[] bArr, int[] iArr) {
        return this.provider.version(bArr, iArr);
    }

    public int loadImage(String str, byte[] bArr, int[] iArr, int[] iArr2, int[] iArr3) {
        return this.provider.loadImage(str, bArr, iArr, iArr2, iArr3);
    }

    public int setParameter(long j, int i, byte[] bArr, int i2) {
        return this.provider.setParameter(j, i, bArr, i2);
    }

    public int getParameter(long j, int i, byte[] bArr, int[] iArr) {
        return this.provider.getParameter(j, i, bArr, iArr);
    }

    public int terminate(long j) {
        return this.provider.terminate(j);
    }

    public int getFaceContext(long j, int i, long[] jArr) {
        return this.provider.getFaceContext(j, i, jArr);
    }

    public int getCropImageData(long j, int[] iArr, int[] iArr2, int[] iArr3, byte[] bArr) {
        return this.provider.getCropImageData(j, iArr, iArr2, iArr3, bArr);
    }

    public int getFaceICaoFeature(long j, int i, int[] iArr) {
        return this.provider.getFaceICaoFeature(j, i, iArr);
    }

    public int getFaceRect(long j, int[] iArr, int i) {
        return this.provider.getFaceRect(j, iArr, i);
    }

    public int verify(long j, byte[] bArr, byte[] bArr2, int[] iArr) {
        return this.provider.verify(j, bArr, bArr2, iArr);
    }

    public int loadImageFromMemoryExt(byte[] bArr, int i, byte[] bArr2, int[] iArr, int[] iArr2, int[] iArr3) {
        return this.provider.loadImageFromMemoryExt(bArr, i, bArr2, iArr, iArr2, iArr3);
    }

    public int setThumbnailParameter(long j, int i, int i2, float f, float f2) {
        return this.provider.setThumbnailParameter(j, i, i2, f, f2);
    }
}
