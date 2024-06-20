package com.zkteco.android.core.interfaces;

public interface LiveFaceInterface {
    public static final String BAYERTOBGR24 = "lf-bayerToBGR24";
    public static final String CLOSEFACECONTEXT = "lf-closeFaceContext";
    public static final String DBADD = "lf-dbAdd";
    public static final String DBCLEAR = "lf-dbClear";
    public static final String DBCOUNT = "lf-dbCount";
    public static final String DBDEL = "lf-dbDel";
    public static final String DBIDENTIFY = "lf-dbIdentify";
    public static final String DETECTFACES = "lf-detectFaces";
    public static final String DETECTFACESEXT = "lf-detectFacesExt";
    public static final String EXTRACTTEMPLATE = "lf-extractTemplate";
    public static final String GETCROPIMAGEDATA = "fp-getCropImageData";
    public static final String GETFACECONTEXT = "fp-getFaceContext";
    public static final String GETFACEFEATURE = "fp-getFaceFeature";
    public static final String GETFACEICAOFEATURE = "fp-getFaceICaoFeature";
    public static final String GETFACERECT = "fp-getFaceRect";
    public static final String GETHARDWAREID = "fp-getHardwareId";
    public static final String GETLASTERROR = "fp-getLastError";
    public static final String GETPARAMETE = "fp-getParameter";
    public static final String INIT = "lf-init";
    public static final String LOADIMAGE = "fp-loadImage";
    public static final String LOADIMAGEFROMMEMORYEXT = "fp-loadImageFromMemoryExt";
    public static final String SETPARAMETER = "fp-setParameter";
    public static final String SETTHUMBNAILPARAMETER = "fp-setThumbnailParameter";
    public static final String TERMINATE = "fp-terminate";
    public static final String VERIFY = "fp-verify";
    public static final String VERSION = "fp-version";

    int bayerToBGR24(byte[] bArr, int i, int i2, byte[] bArr2);

    int closeFaceContext(long j);

    int dbAdd(long j, String str, byte[] bArr);

    int dbClear(long j);

    int dbCount(long j, int[] iArr);

    int dbDel(long j, String str);

    int dbIdentify(long j, byte[] bArr, byte[] bArr2, int[] iArr, int[] iArr2, int i, int i2);

    int detectFaces(long j, byte[] bArr, int i, int i2, int[] iArr);

    int detectFacesExt(long j, byte[] bArr, int i, int i2, int[] iArr, int i3);

    int extractTemplate(long j, byte[] bArr, int[] iArr, int[] iArr2);

    int getCropImageData(long j, int[] iArr, int[] iArr2, int[] iArr3, byte[] bArr);

    int getFaceContext(long j, int i, long[] jArr);

    int getFaceFeature(long j, int i, int[] iArr, int[] iArr2, int[] iArr3);

    int getFaceICaoFeature(long j, int i, int[] iArr);

    int getFaceRect(long j, int[] iArr, int i);

    int getHardwareId(byte[] bArr, int[] iArr);

    int getLastError(long j, byte[] bArr, int[] iArr);

    int getParameter(long j, int i, byte[] bArr, int[] iArr);

    int init(long[] jArr);

    int loadImage(String str, byte[] bArr, int[] iArr, int[] iArr2, int[] iArr3);

    int loadImageFromMemoryExt(byte[] bArr, int i, byte[] bArr2, int[] iArr, int[] iArr2, int[] iArr3);

    int setParameter(long j, int i, byte[] bArr, int i2);

    int setThumbnailParameter(long j, int i, int i2, float f, float f2);

    int terminate(long j);

    int verify(long j, byte[] bArr, byte[] bArr2, int[] iArr);

    int version(byte[] bArr, int[] iArr);
}
