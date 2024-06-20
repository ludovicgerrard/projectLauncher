package com.zkteco.liveface562.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.zkteco.liveface562.bean.AgeAndGender;
import com.zkteco.liveface562.bean.CompareResult;
import com.zkteco.liveface562.bean.EyeState;
import com.zkteco.liveface562.bean.Face;
import com.zkteco.liveface562.bean.FaceOccStatus;
import com.zkteco.liveface562.bean.FacePose;
import com.zkteco.liveface562.bean.IdentifyInfo;
import com.zkteco.liveface562.bean.LivenessResult;
import com.zkteco.liveface562.bean.TopKIdentifyInfo;
import com.zkteco.liveface562.bean.ZkDetectInfo;
import com.zkteco.liveface562.bean.ZkExtractResult;
import com.zkteco.liveface562.bean.ZkFaceConfig;
import com.zkteco.liveface562.bean.ZkFaceException;
import com.zkteco.liveface562.util.ZkBitmapUtils;
import com.zkteco.liveface562.util.ZkLogUtil;
import java.util.ArrayList;
import java.util.List;
import mcv.facepass.FacePassException;
import mcv.facepass.FacePassHandler;
import mcv.facepass.FacePassLicenseManagerNative;
import mcv.facepass.types.FacePassAgeGenderResult;
import mcv.facepass.types.FacePassCompareResult;
import mcv.facepass.types.FacePassConfig;
import mcv.facepass.types.FacePassDetectionResult;
import mcv.facepass.types.FacePassExtractFeatureResult;
import mcv.facepass.types.FacePassEyeClosed;
import mcv.facepass.types.FacePassFace;
import mcv.facepass.types.FacePassFeature;
import mcv.facepass.types.FacePassImage;
import mcv.facepass.types.FacePassLivenessResult;
import mcv.facepass.types.FacePassLmkOccStatus;
import mcv.facepass.types.FacePassModel;
import mcv.facepass.types.FacePassPose;
import mcv.facepass.types.FacePassRCAttribute;
import mcv.facepass.types.FacePassRecognitionDetail;
import mcv.facepass.types.FacePassRecognitionResult;
import mcv.facepass.types.FacePassRect;

public class ZkLiveFaceImpl implements ZKLiveFaceInterface {
    private static final String BITMAP_INVALID = "bitmap invalid";
    private static final String GROUP_NAME = "zkfacegroup";
    private static final String TAG = "ZkLiveFace";
    public boolean isDebug = false;
    private boolean isInit = false;
    private volatile boolean isLocalGroupExist = false;
    private FacePassHandler mFacePassHandler;

    public int authorize(String str) {
        return -1;
    }

    public int generateFingerprint() {
        return -1;
    }

    public String getModelVersion() {
        return "2.2";
    }

    public boolean isAuthorize() {
        return FacePassHandler.isAuthorized();
    }

    public boolean getChipsState() {
        return FacePassHandler.isAuthorized();
    }

    public void setDebug(boolean z) {
        this.isDebug = z;
        ZkLogUtil.setDebug(z);
    }

    public int init(Context context, ZkFaceConfig zkFaceConfig) throws ZkFaceException {
        boolean z = true;
        if (this.isInit) {
            return 1;
        }
        FacePassHandler.initSDK(context);
        FacePassHandler.authPrepare(context);
        ZkLogUtil.iFormat("[Version]%s", FacePassHandler.getVersion());
        int i = 0;
        while (i < 30) {
            boolean isAvailable = FacePassHandler.isAvailable();
            ZkLogUtil.iFormat("[CheckChipAuth]:retry=%d,isAvailable = %b", Integer.valueOf(i), Boolean.valueOf(isAvailable));
            if (!isAvailable) {
                i++;
            }
        }
        try {
            FacePassConfig facePassConfig = new FacePassConfig();
            facePassConfig.poseBlurModel = new FacePassModel(zkFaceConfig.getPoseBlurModel());
            facePassConfig.livenessModel = new FacePassModel(zkFaceConfig.getLivenessModel());
            facePassConfig.livenessEnabled = zkFaceConfig.isLivenessEnabled();
            facePassConfig.searchModel = new FacePassModel(zkFaceConfig.getSearchModel());
            facePassConfig.detectModel = new FacePassModel(zkFaceConfig.getDetectModel());
            facePassConfig.detectRectModel = new FacePassModel(zkFaceConfig.getDetectRectModel());
            facePassConfig.landmarkModel = new FacePassModel(zkFaceConfig.getLandmarkModel());
            facePassConfig.rcAttributeModel = new FacePassModel(zkFaceConfig.getRcAttributeModel());
            facePassConfig.occlusionFilterModel = new FacePassModel(zkFaceConfig.getOcclusionFilterModel());
            if (zkFaceConfig.isSmileEnabled()) {
                facePassConfig.smileModel = new FacePassModel(zkFaceConfig.getSmileModel());
                facePassConfig.smileEnabled = zkFaceConfig.isSmileEnabled();
            }
            if (zkFaceConfig.isAgeGenderEnabled()) {
                facePassConfig.ageGenderModel = new FacePassModel(zkFaceConfig.getAgeGenderModel());
                facePassConfig.ageGenderEnabled = zkFaceConfig.isAgeGenderEnabled();
            }
            facePassConfig.eyeocEnabled = zkFaceConfig.isEyeOCEnabled();
            facePassConfig.eyeocModel = new FacePassModel(zkFaceConfig.getEyeModel());
            facePassConfig.rgbIrLivenessModel = new FacePassModel(zkFaceConfig.getRgbIrLivenessModel(), zkFaceConfig.getRgbIrLivenessModelName());
            facePassConfig.rgbIrLivenessEnabled = zkFaceConfig.isRgbIrLivenessEnabled();
            if (zkFaceConfig.isLivenessGPUEnabled()) {
                if (zkFaceConfig.getLivenessGPUCache() == null) {
                    return ErrorCode.GPU_CACHE_CAN_NOT_BE_NULL;
                }
                facePassConfig.livenessGPUCache = new FacePassModel(zkFaceConfig.getLivenessGPUCache(), zkFaceConfig.getGpuCacheName());
            }
            facePassConfig.rcAttributeAndOcclusionMode = zkFaceConfig.getRcAttributeAndOcclusionMode();
            facePassConfig.detectMode = zkFaceConfig.getDetectMode();
            facePassConfig.searchThreshold = zkFaceConfig.getSearchThreshold();
            facePassConfig.livenessThreshold = zkFaceConfig.getLivenessThreshold();
            facePassConfig.livenessEnabled = zkFaceConfig.isLivenessEnabled();
            facePassConfig.ageGenderEnabled = zkFaceConfig.isAgeGenderEnabled();
            facePassConfig.faceMinThreshold = zkFaceConfig.getFaceMinThreshold();
            FacePose poseThreshold = zkFaceConfig.getPoseThreshold();
            facePassConfig.poseThreshold = new FacePassPose(poseThreshold.roll, poseThreshold.pitch, poseThreshold.yaw);
            facePassConfig.blurThreshold = zkFaceConfig.getBlurThreshold();
            facePassConfig.lowBrightnessThreshold = zkFaceConfig.getLowBrightnessThreshold();
            facePassConfig.highBrightnessThreshold = zkFaceConfig.getHighBrightnessThreshold();
            facePassConfig.brightnessSTDThreshold = zkFaceConfig.getBrightnessSTDThreshold();
            facePassConfig.retryCount = zkFaceConfig.getRetryCount();
            facePassConfig.maxFaceEnabled = zkFaceConfig.isMaxFaceEnabled();
            facePassConfig.fileRootPath = zkFaceConfig.getFileRootPath();
            FacePassHandler facePassHandler = new FacePassHandler(facePassConfig);
            this.mFacePassHandler = facePassHandler;
            FacePassConfig addFaceConfig = facePassHandler.getAddFaceConfig();
            addFaceConfig.blurThreshold = zkFaceConfig.getBlurThreshold();
            this.mFacePassHandler.setAddFaceConfig(addFaceConfig);
            int checkGroup = checkGroup();
            if (checkGroup != 0) {
                z = false;
            }
            this.isInit = z;
            return checkGroup;
        } catch (FacePassException e) {
            throw new ZkFaceException(e.getMessage());
        }
    }

    public int setConfig(ZkFaceConfig zkFaceConfig) throws ZkFaceException {
        checkAvailable();
        try {
            FacePassConfig config = this.mFacePassHandler.getConfig();
            FacePose poseThreshold = zkFaceConfig.getPoseThreshold();
            if (poseThreshold != null) {
                config.poseThreshold = new FacePassPose(poseThreshold.roll, poseThreshold.pitch, poseThreshold.yaw);
            }
            if (zkFaceConfig.getBrightnessSTDThreshold() > 0.0f) {
                config.brightnessSTDThreshold = zkFaceConfig.getBrightnessSTDThreshold();
            }
            if (zkFaceConfig.getBlurThreshold() > 0.0f) {
                config.blurThreshold = zkFaceConfig.getBlurThreshold();
            }
            if (zkFaceConfig.getHighBrightnessThreshold() > 0.0f) {
                config.highBrightnessThreshold = zkFaceConfig.getHighBrightnessThreshold();
            }
            if (zkFaceConfig.getLowBrightnessThreshold() > 0.0f) {
                config.lowBrightnessThreshold = zkFaceConfig.getLowBrightnessThreshold();
            }
            if (zkFaceConfig.getFaceMinThreshold() > 0) {
                config.faceMinThreshold = zkFaceConfig.getFaceMinThreshold();
            }
            if (zkFaceConfig.getLivenessThreshold() > 0.0f) {
                config.livenessThreshold = zkFaceConfig.getLivenessThreshold();
            }
            if (zkFaceConfig.getRetryCount() > 0) {
                config.retryCount = zkFaceConfig.getRetryCount();
            }
            if (config.isInvalidThresholdParams()) {
                return ErrorCode.CONFIG_THRESHOLD_PARAM_INVALID;
            }
            config.maxFaceEnabled = zkFaceConfig.isMaxFaceEnabled();
            config.ageGenderEnabled = zkFaceConfig.isAgeGenderEnabled();
            config.livenessEnabled = zkFaceConfig.isLivenessEnabled();
            config.rgbIrLivenessEnabled = zkFaceConfig.isRgbIrLivenessEnabled();
            config.smileEnabled = zkFaceConfig.isSmileEnabled();
            config.detectMode = zkFaceConfig.getDetectMode();
            config.rcAttributeAndOcclusionMode = zkFaceConfig.getRcAttributeAndOcclusionMode();
            config.eyeocEnabled = zkFaceConfig.isEyeOCEnabled();
            if (this.mFacePassHandler.setConfig(config)) {
                return 0;
            }
            return ErrorCode.SET_CONFIG_FAILED;
        } catch (FacePassException e) {
            throw new ZkFaceException(e.getMessage());
        }
    }

    public int getChipState(Context context) {
        FacePassHandler.initSDK(context);
        return FacePassLicenseManagerNative.CheckChipAuth();
    }

    public String getAuthContext(Context context) {
        FacePassHandler.initSDK(context);
        return FacePassLicenseManagerNative.getLicenseContext();
    }

    public int setLicense(Context context, String str) {
        FacePassHandler.initSDK(context);
        return FacePassLicenseManagerNative.setLicense(str);
    }

    public ZkFaceConfig getConfig() throws ZkFaceException {
        checkAvailable();
        try {
            FacePassConfig config = this.mFacePassHandler.getConfig();
            ZkFaceConfig zkFaceConfig = new ZkFaceConfig();
            FacePassPose facePassPose = config.poseThreshold;
            zkFaceConfig.setPoseThreshold(new FacePose(facePassPose.roll, facePassPose.pitch, facePassPose.yaw));
            zkFaceConfig.setBrightnessSTDThreshold(config.brightnessSTDThreshold);
            zkFaceConfig.setBlurThreshold(config.blurThreshold);
            zkFaceConfig.setHighBrightnessThreshold(config.highBrightnessThreshold);
            zkFaceConfig.setLowBrightnessThreshold(config.lowBrightnessThreshold);
            zkFaceConfig.setFaceMinThreshold(config.faceMinThreshold);
            zkFaceConfig.setLivenessThreshold(config.livenessThreshold);
            zkFaceConfig.setRetryCount(config.retryCount);
            zkFaceConfig.setRcAttributeAndOcclusionMode(config.rcAttributeAndOcclusionMode);
            zkFaceConfig.setDetectMode(config.detectMode);
            zkFaceConfig.setMaxFaceEnabled(config.maxFaceEnabled);
            zkFaceConfig.setAgeGenderEnabled(config.ageGenderEnabled);
            zkFaceConfig.setLivenessEnabled(config.livenessEnabled);
            zkFaceConfig.setRgbIrLivenessEnabled(config.rgbIrLivenessEnabled);
            zkFaceConfig.setSmileEnabled(config.smileEnabled);
            return zkFaceConfig;
        } catch (FacePassException e) {
            throw new ZkFaceException(e.getMessage());
        }
    }

    public boolean isInit() {
        return this.isInit;
    }

    private int checkGroup() throws ZkFaceException {
        FacePassHandler facePassHandler = this.mFacePassHandler;
        if (facePassHandler == null) {
            return -4;
        }
        try {
            String[] localGroups = facePassHandler.getLocalGroups();
            if (localGroups == null) {
                return -23;
            }
            int length = localGroups.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (GROUP_NAME.equals(localGroups[i])) {
                    this.isLocalGroupExist = true;
                    if (this.isDebug) {
                        Log.i(TAG, "[checkGroup]: group exists");
                    }
                } else {
                    i++;
                }
            }
            if (!this.isLocalGroupExist) {
                try {
                    boolean createLocalGroup = this.mFacePassHandler.createLocalGroup(GROUP_NAME);
                    if (this.isDebug) {
                        Log.i(TAG, "[checkGroup]: create group" + createLocalGroup);
                    }
                    try {
                        String[] localGroups2 = this.mFacePassHandler.getLocalGroups();
                        int length2 = localGroups2.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length2) {
                                break;
                            } else if (GROUP_NAME.equals(localGroups2[i2])) {
                                if (this.isDebug) {
                                    Log.i(TAG, "[checkGroup]: group exists");
                                }
                                this.isLocalGroupExist = true;
                            } else {
                                i2++;
                            }
                        }
                    } catch (FacePassException e) {
                        throw new ZkFaceException(e.getMessage());
                    }
                } catch (FacePassException e2) {
                    throw new ZkFaceException(e2.getMessage());
                }
            }
            if (!this.isLocalGroupExist) {
                return -21;
            }
            return 0;
        } catch (FacePassException e3) {
            throw new ZkFaceException(e3.getMessage());
        }
    }

    public ZkDetectInfo detectFacesFromNV21(byte[] bArr, int i, int i2) throws ZkFaceException {
        checkAvailable();
        if (this.mFacePassHandler != null) {
            try {
                try {
                    FacePassDetectionResult feedFrame = this.mFacePassHandler.feedFrame(new FacePassImage(bArr, i, i2, 0, 0));
                    if (feedFrame != null) {
                        FacePassFace[] facePassFaceArr = feedFrame.faceList;
                        if (facePassFaceArr.length <= 0) {
                            return null;
                        }
                        Face[] faceArr = new Face[facePassFaceArr.length];
                        for (int i3 = 0; i3 < facePassFaceArr.length; i3++) {
                            FacePassFace facePassFace = facePassFaceArr[i3];
                            Face face = new Face();
                            fillFace(face, facePassFace);
                            faceArr[i3] = face;
                        }
                        return new ZkDetectInfo(faceArr, feedFrame.message);
                    }
                    throw new ZkFaceException("Detection Result is null,license may be broken");
                } catch (FacePassException e) {
                    throw new ZkFaceException(e.getMessage());
                }
            } catch (FacePassException e2) {
                throw new ZkFaceException(e2.getMessage());
            }
        } else {
            throw new ZkFaceException("Zk Live Face not ready!");
        }
    }

    public ZkDetectInfo detectFacesFromNV21Ex(byte[] bArr, int i, int i2, int i3) throws ZkFaceException {
        checkAvailable();
        if (this.mFacePassHandler != null) {
            try {
                try {
                    FacePassDetectionResult feedFrame = this.mFacePassHandler.feedFrame(new FacePassImage(bArr, i, i2, i3, 0));
                    if (feedFrame != null) {
                        FacePassFace[] facePassFaceArr = feedFrame.faceList;
                        if (facePassFaceArr.length <= 0) {
                            return null;
                        }
                        Face[] faceArr = new Face[facePassFaceArr.length];
                        for (int i4 = 0; i4 < facePassFaceArr.length; i4++) {
                            FacePassFace facePassFace = facePassFaceArr[i4];
                            Face face = new Face();
                            fillFace(face, facePassFace);
                            faceArr[i4] = face;
                        }
                        return new ZkDetectInfo(faceArr, feedFrame.message);
                    }
                    throw new ZkFaceException("Detection Result is null,license may be broken");
                } catch (FacePassException e) {
                    throw new ZkFaceException(e.getMessage());
                }
            } catch (FacePassException e2) {
                throw new ZkFaceException(e2.getMessage());
            }
        } else {
            throw new ZkFaceException("Zk Live Face not ready!");
        }
    }

    public ZkDetectInfo detectFacesFromRGBIR(byte[] bArr, byte[] bArr2, int i, int i2) throws ZkFaceException {
        checkAvailable();
        if (this.mFacePassHandler != null) {
            try {
                try {
                    try {
                        FacePassDetectionResult feedFrameRGBIR = this.mFacePassHandler.feedFrameRGBIR(new FacePassImage(bArr, i, i2, 0, 0), new FacePassImage(bArr2, i, i2, 0, 0));
                        if (feedFrameRGBIR != null) {
                            FacePassFace[] facePassFaceArr = feedFrameRGBIR.faceList;
                            if (facePassFaceArr.length <= 0) {
                                return null;
                            }
                            Face[] faceArr = new Face[facePassFaceArr.length];
                            for (int i3 = 0; i3 < facePassFaceArr.length; i3++) {
                                FacePassFace facePassFace = facePassFaceArr[i3];
                                Face face = new Face();
                                fillFace(face, facePassFace);
                                faceArr[i3] = face;
                            }
                            return new ZkDetectInfo(faceArr, feedFrameRGBIR.message);
                        }
                        throw new ZkFaceException("Detection Result is null,license may be broken");
                    } catch (FacePassException e) {
                        throw new ZkFaceException(e.getMessage());
                    }
                } catch (FacePassException e2) {
                    throw new ZkFaceException(e2.getMessage());
                }
            } catch (FacePassException e3) {
                throw new ZkFaceException(e3.getMessage());
            }
        } else {
            throw new ZkFaceException("Zk Live Face not ready!");
        }
    }

    public ZkDetectInfo detectFacesFromRGBIREx(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4) throws ZkFaceException {
        checkAvailable();
        if (this.mFacePassHandler != null) {
            try {
                try {
                    try {
                        FacePassDetectionResult feedFrameRGBIR = this.mFacePassHandler.feedFrameRGBIR(new FacePassImage(bArr, i, i2, i3, 0), new FacePassImage(bArr2, i, i2, i4, 0));
                        if (feedFrameRGBIR != null) {
                            FacePassFace[] facePassFaceArr = feedFrameRGBIR.faceList;
                            if (facePassFaceArr.length <= 0) {
                                return null;
                            }
                            Face[] faceArr = new Face[facePassFaceArr.length];
                            for (int i5 = 0; i5 < facePassFaceArr.length; i5++) {
                                FacePassFace facePassFace = facePassFaceArr[i5];
                                Face face = new Face();
                                fillFace(face, facePassFace);
                                faceArr[i5] = face;
                            }
                            return new ZkDetectInfo(faceArr, feedFrameRGBIR.message);
                        }
                        throw new ZkFaceException("Detection Result is null,license may be broken");
                    } catch (FacePassException e) {
                        throw new ZkFaceException(e.getMessage());
                    }
                } catch (FacePassException e2) {
                    throw new ZkFaceException(e2.getMessage());
                }
            } catch (FacePassException e3) {
                throw new ZkFaceException(e3.getMessage());
            }
        } else {
            throw new ZkFaceException("Zk Live Face not ready!");
        }
    }

    public ZkDetectInfo detectFacesFromBitmap(Bitmap bitmap, int i) throws ZkFaceException {
        FacePassDetectionResult facePassDetectionResult;
        checkAvailable();
        if (bitmap == null || bitmap.isRecycled()) {
            throw new ZkFaceException(BITMAP_INVALID);
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        try {
            try {
                facePassDetectionResult = this.mFacePassHandler.feedFrame(new FacePassImage(ZkBitmapUtils.getNV21(width, height, bitmap), width, height, 0, 0));
            } catch (FacePassException e) {
                e.printStackTrace();
                facePassDetectionResult = null;
            }
            if (facePassDetectionResult != null) {
                FacePassFace[] facePassFaceArr = facePassDetectionResult.faceList;
                if (facePassFaceArr.length <= 0) {
                    return null;
                }
                Face[] faceArr = new Face[facePassFaceArr.length];
                for (int i2 = 0; i2 < facePassFaceArr.length; i2++) {
                    FacePassFace facePassFace = facePassFaceArr[i2];
                    Face face = new Face();
                    fillFace(face, facePassFace);
                    faceArr[i2] = face;
                }
                return new ZkDetectInfo(faceArr, facePassDetectionResult.message);
            }
            throw new ZkFaceException("Detection Result is null,license may be broken");
        } catch (FacePassException e2) {
            throw new ZkFaceException(e2.getMessage());
        }
    }

    public ZkExtractResult extractFromBitmap(Bitmap bitmap, boolean z) throws ZkFaceException {
        checkAvailable();
        try {
            FacePassConfig addFaceConfig = this.mFacePassHandler.getAddFaceConfig();
            addFaceConfig.faceMinThreshold = z ? 10 : 100;
            addFaceConfig.blurThreshold = z ? 1.0f : 0.7f;
            float f = 255.0f;
            addFaceConfig.brightnessSTDThreshold = z ? 255.0f : 80.0f;
            addFaceConfig.lowBrightnessThreshold = z ? 0.0f : 70.0f;
            if (!z) {
                f = 210.0f;
            }
            addFaceConfig.highBrightnessThreshold = f;
            float f2 = 90.0f;
            float f3 = z ? 90.0f : 20.0f;
            float f4 = z ? 90.0f : 20.0f;
            if (!z) {
                f2 = 20.0f;
            }
            addFaceConfig.poseThreshold = new FacePassPose(f3, f4, f2);
            this.mFacePassHandler.setAddFaceConfig(addFaceConfig);
            addFaceConfig.maxFaceEnabled = true;
            try {
                FacePassExtractFeatureResult extractFeature = this.mFacePassHandler.extractFeature(bitmap);
                if (extractFeature == null) {
                    return null;
                }
                ZkExtractResult zkExtractResult = new ZkExtractResult();
                zkExtractResult.result = extractFeature.result;
                zkExtractResult.feature = extractFeature.featureData;
                zkExtractResult.face = new Face();
                zkExtractResult.face.blur = extractFeature.blur;
                zkExtractResult.face.brightness = extractFeature.brightness;
                zkExtractResult.face.deviation = extractFeature.deviation;
                FacePassRect facePassRect = extractFeature.rect;
                zkExtractResult.face.rect = new Rect(facePassRect.left, facePassRect.top, facePassRect.left, facePassRect.bottom);
                FacePassPose facePassPose = extractFeature.pose;
                zkExtractResult.face.pose = new FacePose(facePassPose.roll, facePassPose.pitch, facePassPose.yaw);
                return zkExtractResult;
            } catch (FacePassException e) {
                throw new ZkFaceException(e.getMessage());
            }
        } catch (FacePassException e2) {
            throw new ZkFaceException(e2.getMessage());
        }
    }

    public CompareResult compare(Bitmap bitmap, Bitmap bitmap2, boolean z) throws ZkFaceException {
        checkAvailable();
        try {
            FacePassCompareResult compare = this.mFacePassHandler.compare(bitmap, bitmap2, z);
            if (compare != null) {
                return new CompareResult(compare.result, compare.score, compare.compareThreshold, compare.livenessThreshold, compare.livenessScore1, compare.livenessScore2);
            }
            return null;
        } catch (FacePassException e) {
            throw new ZkFaceException(e.getMessage());
        }
    }

    public List<IdentifyInfo> dbIdentify(byte[] bArr) throws ZkFaceException {
        checkAvailable();
        ArrayList arrayList = new ArrayList();
        if (this.isLocalGroupExist) {
            try {
                FacePassRecognitionResult[] recognize = this.mFacePassHandler.recognize(GROUP_NAME, bArr);
                if (recognize != null && recognize.length > 0) {
                    if (this.isDebug) {
                        Log.i(TAG, "dbIdentify: length=" + recognize.length);
                        for (FacePassRecognitionResult facePassRecognitionResult : recognize) {
                            Log.i(TAG, "dbIdentify: faceToken=[" + new String(facePassRecognitionResult.faceToken) + "]\nscore=[" + facePassRecognitionResult.detail.searchScore + "]\ntrackId=" + facePassRecognitionResult.trackId);
                        }
                    }
                    for (FacePassRecognitionResult facePassRecognitionResult2 : recognize) {
                        IdentifyInfo identifyInfo = new IdentifyInfo();
                        fillIdentifyInfo(identifyInfo, facePassRecognitionResult2);
                        if (this.isDebug) {
                            Log.i(TAG, String.format("[dbIdentify]: [faceToken=%s]", new Object[]{new String(facePassRecognitionResult2.faceToken)}));
                        }
                        arrayList.add(identifyInfo);
                    }
                }
                return arrayList;
            } catch (FacePassException e) {
                throw new ZkFaceException(e.getMessage());
            }
        } else {
            throw new ZkFaceException("group is null");
        }
    }

    public LivenessResult[] livenessClassify(byte[] bArr) throws ZkFaceException {
        FacePassLivenessResult[] facePassLivenessResultArr;
        checkAvailable();
        LivenessResult[] livenessResultArr = null;
        try {
            facePassLivenessResultArr = this.mFacePassHandler.livenessClassify(bArr);
        } catch (FacePassException e) {
            e.printStackTrace();
            facePassLivenessResultArr = null;
        }
        if (facePassLivenessResultArr != null) {
            livenessResultArr = new LivenessResult[facePassLivenessResultArr.length];
            for (int i = 0; i < facePassLivenessResultArr.length; i++) {
                FacePassLivenessResult facePassLivenessResult = facePassLivenessResultArr[i];
                LivenessResult livenessResult = new LivenessResult();
                livenessResult.livenessScore = facePassLivenessResult.livenessScore;
                livenessResult.trackId = facePassLivenessResult.trackId;
                livenessResultArr[i] = livenessResult;
            }
        }
        return livenessResultArr;
    }

    public AgeAndGender[] getAgeAndGender(byte[] bArr) throws ZkFaceException {
        checkAvailable();
        try {
            FacePassAgeGenderResult[] ageGender = this.mFacePassHandler.getAgeGender(bArr);
            if (ageGender == null) {
                return null;
            }
            AgeAndGender[] ageAndGenderArr = new AgeAndGender[ageGender.length];
            for (int i = 0; i < ageGender.length; i++) {
                FacePassAgeGenderResult facePassAgeGenderResult = ageGender[i];
                AgeAndGender ageAndGender = new AgeAndGender();
                ageAndGender.age = facePassAgeGenderResult.age;
                ageAndGender.gender = facePassAgeGenderResult.gender;
                ageAndGender.trackId = facePassAgeGenderResult.trackId;
                ageAndGenderArr[i] = ageAndGender;
            }
            return ageAndGenderArr;
        } catch (FacePassException e) {
            throw new ZkFaceException(e.getMessage());
        }
    }

    public ArrayList<IdentifyInfo> dbIdentify(byte[] bArr, byte[] bArr2) throws ZkFaceException {
        checkAvailable();
        ArrayList<IdentifyInfo> arrayList = new ArrayList<>();
        if (this.isLocalGroupExist) {
            try {
                FacePassRecognitionResult[][] recognize = this.mFacePassHandler.recognize(new FacePassFeature[]{new FacePassFeature(bArr)}, bArr2);
                if (recognize != null && recognize.length > 0) {
                    for (FacePassRecognitionResult[] facePassRecognitionResultArr : recognize) {
                        for (FacePassRecognitionResult fillIdentifyInfo : recognize[r1]) {
                            IdentifyInfo identifyInfo = new IdentifyInfo();
                            fillIdentifyInfo(identifyInfo, fillIdentifyInfo);
                            arrayList.add(identifyInfo);
                        }
                    }
                }
                return arrayList;
            } catch (FacePassException e) {
                throw new ZkFaceException(e.getMessage());
            }
        } else {
            throw new ZkFaceException("group is null");
        }
    }

    public ArrayList<TopKIdentifyInfo> dbIdentify(byte[] bArr, int i) throws ZkFaceException {
        checkAvailable();
        ArrayList<TopKIdentifyInfo> arrayList = null;
        FacePassRecognitionResult[][] facePassRecognitionResultArr = null;
        if (this.isLocalGroupExist) {
            try {
                facePassRecognitionResultArr = this.mFacePassHandler.recognize(GROUP_NAME, bArr, i);
            } catch (FacePassException e) {
                if (this.isDebug) {
                    e.printStackTrace();
                    throw new ZkFaceException("recognize failed");
                }
            }
            if (facePassRecognitionResultArr != null && facePassRecognitionResultArr.length > 0) {
                arrayList = new ArrayList<>(facePassRecognitionResultArr.length);
                for (FacePassRecognitionResult[] facePassRecognitionResultArr2 : facePassRecognitionResultArr) {
                    if (facePassRecognitionResultArr2 != null && facePassRecognitionResultArr2.length > 0) {
                        IdentifyInfo[] identifyInfoArr = new IdentifyInfo[facePassRecognitionResultArr2.length];
                        TopKIdentifyInfo topKIdentifyInfo = new TopKIdentifyInfo();
                        for (int i2 = 0; i2 < facePassRecognitionResultArr2.length; i2++) {
                            FacePassRecognitionResult facePassRecognitionResult = facePassRecognitionResultArr2[i2];
                            IdentifyInfo identifyInfo = new IdentifyInfo();
                            fillIdentifyInfo(identifyInfo, facePassRecognitionResult);
                            identifyInfoArr[i2] = identifyInfo;
                        }
                        topKIdentifyInfo.setIdentifyInfos(identifyInfoArr);
                        arrayList.add(topKIdentifyInfo);
                    }
                }
            }
            return arrayList;
        }
        throw new ZkFaceException("group is null");
    }

    public void resetFaceMessage(long j) throws ZkFaceException {
        checkAvailable();
        this.mFacePassHandler.setMessage(j, 0);
    }

    public void closeFace(Face face) throws ZkFaceException {
        checkAvailable();
        this.mFacePassHandler.setMessage(face.trackId, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00a6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean dbAdd(java.lang.String r7, byte[] r8) throws com.zkteco.liveface562.bean.ZkFaceException {
        /*
            r6 = this;
            java.lang.String r0 = "ZkLiveFace"
            r6.checkAvailable()
            boolean r1 = android.text.TextUtils.isEmpty(r7)
            if (r1 != 0) goto L_0x00b3
            if (r8 == 0) goto L_0x00ab
            int r1 = r8.length
            if (r1 <= 0) goto L_0x00ab
            int r1 = r7.length()
            r2 = 24
            r3 = 0
            if (r1 >= r2) goto L_0x0024
            byte[] r1 = new byte[r2]
            byte[] r2 = r7.getBytes()
            int r4 = r2.length
            java.lang.System.arraycopy(r2, r3, r1, r3, r4)
            goto L_0x0028
        L_0x0024:
            byte[] r1 = r7.getBytes()
        L_0x0028:
            java.lang.String r1 = android.util.Base64.encodeToString(r1, r3)
            mcv.facepass.types.FacePassFeatureAppendInfo r2 = new mcv.facepass.types.FacePassFeatureAppendInfo     // Catch:{ FacePassException -> 0x0087 }
            r2.<init>()     // Catch:{ FacePassException -> 0x0087 }
            r2.faceToken = r1     // Catch:{ FacePassException -> 0x0087 }
            mcv.facepass.FacePassHandler r1 = r6.mFacePassHandler     // Catch:{ FacePassException -> 0x0087 }
            java.lang.String r8 = r1.insertFeature(r8, r2)     // Catch:{ FacePassException -> 0x0087 }
            boolean r1 = r6.isDebug     // Catch:{ FacePassException -> 0x0087 }
            if (r1 == 0) goto L_0x0053
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ FacePassException -> 0x0087 }
            r1.<init>()     // Catch:{ FacePassException -> 0x0087 }
            java.lang.String r2 = "dbAdd: insert feature ,token = "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ FacePassException -> 0x0087 }
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ FacePassException -> 0x0087 }
            java.lang.String r1 = r1.toString()     // Catch:{ FacePassException -> 0x0087 }
            android.util.Log.i(r0, r1)     // Catch:{ FacePassException -> 0x0087 }
        L_0x0053:
            if (r8 != 0) goto L_0x005b
            java.lang.String r7 = "dbAdd: insert feature failed"
            android.util.Log.e(r0, r7)     // Catch:{ FacePassException -> 0x0087 }
            return r3
        L_0x005b:
            mcv.facepass.FacePassHandler r1 = r6.mFacePassHandler     // Catch:{ FacePassException -> 0x0087 }
            java.lang.String r2 = "zkfacegroup"
            byte[] r4 = r8.getBytes()     // Catch:{ FacePassException -> 0x0087 }
            boolean r1 = r1.bindGroup(r2, r4)     // Catch:{ FacePassException -> 0x0087 }
            boolean r2 = r6.isDebug     // Catch:{ FacePassException -> 0x0084 }
            if (r2 == 0) goto L_0x00aa
            java.lang.String r2 = "[dbAdd]: bindGroup ,[ret = %s][userPin = %s][token = %s]"
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ FacePassException -> 0x0084 }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r1)     // Catch:{ FacePassException -> 0x0084 }
            r4[r3] = r5     // Catch:{ FacePassException -> 0x0084 }
            r3 = 1
            r4[r3] = r7     // Catch:{ FacePassException -> 0x0084 }
            r7 = 2
            r4[r7] = r8     // Catch:{ FacePassException -> 0x0084 }
            java.lang.String r7 = java.lang.String.format(r2, r4)     // Catch:{ FacePassException -> 0x0084 }
            android.util.Log.i(r0, r7)     // Catch:{ FacePassException -> 0x0084 }
            goto L_0x00aa
        L_0x0084:
            r7 = move-exception
            r3 = r1
            goto L_0x0088
        L_0x0087:
            r7 = move-exception
        L_0x0088:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r1 = "[dbAdd]: db add failed,"
            java.lang.StringBuilder r8 = r8.append(r1)
            java.lang.String r1 = r7.getMessage()
            java.lang.StringBuilder r8 = r8.append(r1)
            java.lang.String r8 = r8.toString()
            android.util.Log.e(r0, r8)
            boolean r8 = r6.isDebug
            if (r8 == 0) goto L_0x00a9
            r7.printStackTrace()
        L_0x00a9:
            r1 = r3
        L_0x00aa:
            return r1
        L_0x00ab:
            com.zkteco.liveface562.bean.ZkFaceException r7 = new com.zkteco.liveface562.bean.ZkFaceException
            java.lang.String r8 = "feature is invalid"
            r7.<init>((java.lang.String) r8)
            throw r7
        L_0x00b3:
            com.zkteco.liveface562.bean.ZkFaceException r7 = new com.zkteco.liveface562.bean.ZkFaceException
            java.lang.String r8 = "pin is invalid"
            r7.<init>((java.lang.String) r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.liveface562.common.ZkLiveFaceImpl.dbAdd(java.lang.String, byte[]):boolean");
    }

    public boolean dbDel(String str) throws ZkFaceException {
        checkAvailable();
        String tokenBase64String = getTokenBase64String(str);
        if (this.isDebug) {
            Log.i(TAG, "[dbDel]: tokenBase64String=" + tokenBase64String);
        }
        boolean z = false;
        if (TextUtils.isEmpty(tokenBase64String)) {
            Log.e(TAG, "[dbDel]: invalid pin");
            return false;
        }
        try {
            z = this.mFacePassHandler.deleteFace(tokenBase64String.getBytes());
            if (this.isDebug) {
                Log.i(TAG, "[dbDel]: deleteFace=" + z);
            }
        } catch (FacePassException e) {
            if (this.isDebug) {
                e.printStackTrace();
            }
            Log.e(TAG, "[dbDel]: failed," + e.getMessage());
        }
        return z;
    }

    public boolean dbClear() throws ZkFaceException {
        checkAvailable();
        try {
            boolean deleteLocalGroup = this.mFacePassHandler.deleteLocalGroup(GROUP_NAME);
            if (this.isDebug) {
                Log.i(TAG, "[dbClear]: deleteLocalGroup = " + deleteLocalGroup);
            }
            boolean clearAllGroupsAndFaces = this.mFacePassHandler.clearAllGroupsAndFaces();
            this.isLocalGroupExist = !clearAllGroupsAndFaces;
            if (this.isDebug) {
                Log.i(TAG, "[dbClear]: clearAllGroupsAndFaces = " + clearAllGroupsAndFaces);
            }
            checkGroup();
            return clearAllGroupsAndFaces;
        } catch (FacePassException e) {
            if (this.isDebug) {
                e.printStackTrace();
            }
            Log.e(TAG, "[dbClear]: failed," + e.getMessage());
            return false;
        }
    }

    public int dbCount() throws ZkFaceException {
        checkAvailable();
        try {
            return this.mFacePassHandler.getLocalGroupFaceNum(GROUP_NAME);
        } catch (FacePassException e) {
            if (this.isDebug) {
                e.printStackTrace();
            }
            Log.e(TAG, "[dbCount]: failed," + e.getMessage());
            return ErrorCode.ALGORITHM_NOT_INIT;
        }
    }

    public int loadTemplateFromAlgorithmDatabase() throws ZkFaceException {
        checkAvailable();
        try {
            int initLocalGroup = this.mFacePassHandler.initLocalGroup(GROUP_NAME);
            if (initLocalGroup == -1) {
                return ErrorCode.GROUP_NOT_EXIST;
            }
            return initLocalGroup == -2 ? ErrorCode.GROUP_INIT_FAILED : initLocalGroup;
        } catch (FacePassException e) {
            e.printStackTrace();
            throw new ZkFaceException(e.getMessage());
        }
    }

    public void reset() throws ZkFaceException {
        checkAvailable();
        this.mFacePassHandler.reset();
    }

    public void release() {
        FacePassHandler facePassHandler = this.mFacePassHandler;
        if (facePassHandler != null) {
            facePassHandler.release();
        }
        this.isInit = false;
    }

    public String getFaceEngineSdkVersion() {
        return FacePassHandler.getVersion();
    }

    private String getPinByFaceToken(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[] decode = Base64.decode(bArr, 0);
        return new String(decode, 0, getValidLength(decode));
    }

    private String getTokenBase64String(String str) {
        byte[] bArr = new byte[24];
        System.arraycopy(str.getBytes(), 0, bArr, 0, str.getBytes().length);
        return Base64.encodeToString(bArr, 0);
    }

    private void checkAvailable() throws ZkFaceException {
        if (!this.isInit) {
            throw new ZkFaceException("LiveFace is not ready");
        }
    }

    private void fillFace(Face face, FacePassFace facePassFace) {
        FacePassPose facePassPose = facePassFace.pose;
        FacePassRect facePassRect = facePassFace.rect;
        face.trackId = facePassFace.trackId;
        face.rect = new Rect(facePassRect.left, facePassRect.top, facePassRect.right, facePassRect.bottom);
        face.pose = new FacePose(facePassPose.roll, facePassPose.pitch, facePassPose.yaw);
        FacePassRCAttribute facePassRCAttribute = facePassFace.rcAttr;
        face.hair = facePassRCAttribute.hairType.ordinal();
        face.beard = facePassRCAttribute.beardType.ordinal();
        face.respirator = facePassRCAttribute.respiratorType.ordinal();
        face.glasses = facePassRCAttribute.glassesType.ordinal();
        face.skinColor = facePassRCAttribute.skinColorType.ordinal();
        face.hat = facePassRCAttribute.hatType.ordinal();
        face.blur = facePassFace.blur;
        face.smile = facePassFace.smile;
        FacePassEyeClosed facePassEyeClosed = facePassFace.eyeclosed;
        face.eyeState = new EyeState(facePassEyeClosed.valid, facePassEyeClosed.left_eye, facePassEyeClosed.right_eye);
        FacePassLmkOccStatus facePassLmkOccStatus = facePassFace.lmkoccsta;
        if (facePassLmkOccStatus != null) {
            face.faceOccStatus = new FaceOccStatus(facePassLmkOccStatus.valid, facePassLmkOccStatus.eyeocc, facePassLmkOccStatus.noseocc, facePassLmkOccStatus.mouthocc);
        }
    }

    private void fillIdentifyInfo(IdentifyInfo identifyInfo, FacePassRecognitionResult facePassRecognitionResult) {
        FacePassRecognitionDetail facePassRecognitionDetail = facePassRecognitionResult.detail;
        identifyInfo.trackId = facePassRecognitionResult.trackId;
        identifyInfo.glasses = facePassRecognitionDetail.rcAttr.glassesType.ordinal();
        identifyInfo.hairType = facePassRecognitionDetail.rcAttr.hairType.ordinal();
        identifyInfo.beardType = facePassRecognitionDetail.rcAttr.beardType.ordinal();
        identifyInfo.hatType = facePassRecognitionDetail.rcAttr.hatType.ordinal();
        identifyInfo.skinColor = facePassRecognitionDetail.rcAttr.skinColorType.ordinal();
        identifyInfo.identifyScore = facePassRecognitionDetail.searchScore;
        identifyInfo.livenessScore = facePassRecognitionDetail.livenessScore;
        identifyInfo.respirator = facePassRecognitionDetail.rcAttr.respiratorType.ordinal();
        identifyInfo.pin = getPinByFaceToken(facePassRecognitionResult.faceToken);
    }

    private int getValidLength(byte[] bArr) {
        int length = bArr.length;
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] == 0) {
                return i;
            }
        }
        return length;
    }
}
