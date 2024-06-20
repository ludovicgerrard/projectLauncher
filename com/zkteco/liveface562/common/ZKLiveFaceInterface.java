package com.zkteco.liveface562.common;

import android.content.Context;
import android.graphics.Bitmap;
import com.zkteco.liveface562.bean.AgeAndGender;
import com.zkteco.liveface562.bean.CompareResult;
import com.zkteco.liveface562.bean.Face;
import com.zkteco.liveface562.bean.IdentifyInfo;
import com.zkteco.liveface562.bean.LivenessResult;
import com.zkteco.liveface562.bean.TopKIdentifyInfo;
import com.zkteco.liveface562.bean.ZkDetectInfo;
import com.zkteco.liveface562.bean.ZkExtractResult;
import com.zkteco.liveface562.bean.ZkFaceConfig;
import com.zkteco.liveface562.bean.ZkFaceException;
import java.util.List;

public interface ZKLiveFaceInterface {
    public static final int ERROR_AUTH_CREATE_GROUP_FAIL = -21;
    public static final int ERROR_CREATE_GROUP_FAILED = -23;
    public static final int ERR_ALREADY_INIT = 1;
    public static final int ERR_AUTH_INIT_FAIL = -4;
    public static final int ERR_AUTH_INVALID_PARAM = -17;
    public static final int ERR_OK = 0;
    public static final int ERR_OPERATE_FAIL = -1;

    int authorize(String str);

    void closeFace(Face face) throws ZkFaceException;

    CompareResult compare(Bitmap bitmap, Bitmap bitmap2, boolean z) throws ZkFaceException;

    boolean dbAdd(String str, byte[] bArr) throws ZkFaceException;

    boolean dbClear() throws ZkFaceException;

    int dbCount() throws ZkFaceException;

    boolean dbDel(String str) throws ZkFaceException;

    List<IdentifyInfo> dbIdentify(byte[] bArr) throws ZkFaceException;

    List<TopKIdentifyInfo> dbIdentify(byte[] bArr, int i) throws ZkFaceException;

    List<IdentifyInfo> dbIdentify(byte[] bArr, byte[] bArr2) throws ZkFaceException;

    ZkDetectInfo detectFacesFromBitmap(Bitmap bitmap, int i) throws ZkFaceException;

    ZkDetectInfo detectFacesFromNV21(byte[] bArr, int i, int i2) throws ZkFaceException;

    ZkDetectInfo detectFacesFromNV21Ex(byte[] bArr, int i, int i2, int i3) throws ZkFaceException;

    ZkDetectInfo detectFacesFromRGBIR(byte[] bArr, byte[] bArr2, int i, int i2) throws ZkFaceException;

    ZkDetectInfo detectFacesFromRGBIREx(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4) throws ZkFaceException;

    ZkExtractResult extractFromBitmap(Bitmap bitmap, boolean z) throws ZkFaceException;

    int generateFingerprint();

    AgeAndGender[] getAgeAndGender(byte[] bArr) throws ZkFaceException;

    String getAuthContext(Context context);

    int getChipState(Context context);

    boolean getChipsState();

    ZkFaceConfig getConfig() throws ZkFaceException;

    String getFaceEngineSdkVersion();

    String getModelVersion();

    int init(Context context, ZkFaceConfig zkFaceConfig) throws ZkFaceException;

    boolean isAuthorize();

    boolean isInit();

    LivenessResult[] livenessClassify(byte[] bArr) throws ZkFaceException;

    int loadTemplateFromAlgorithmDatabase() throws ZkFaceException;

    void release();

    void reset() throws ZkFaceException;

    void resetFaceMessage(long j) throws ZkFaceException;

    int setConfig(ZkFaceConfig zkFaceConfig) throws ZkFaceException;

    void setDebug(boolean z);

    int setLicense(Context context, String str);
}
