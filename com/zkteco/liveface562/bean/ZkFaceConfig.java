package com.zkteco.liveface562.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.zkteco.liveface562.util.ZKFaceUtil;
import java.io.FileNotFoundException;

public class ZkFaceConfig implements Parcelable {
    public static final Parcelable.Creator<ZkFaceConfig> CREATOR = new Parcelable.Creator<ZkFaceConfig>() {
        public ZkFaceConfig createFromParcel(Parcel parcel) {
            return new ZkFaceConfig(parcel);
        }

        public ZkFaceConfig[] newArray(int i) {
            return new ZkFaceConfig[i];
        }
    };
    private boolean ageGenderEnabled;
    private byte[] ageGenderModel;
    private float blurThreshold;
    private float brightnessSTDThreshold;
    private int detectMode;
    private byte[] detectModel;
    private byte[] detectRectModel;
    private byte[] eyeModel;
    private boolean eyeOCEnabled;
    private int faceMinThreshold;
    private String fileRootPath;
    private String gpuCacheName;
    private float highBrightnessThreshold;
    private byte[] landmarkModel;
    private boolean livenessEnabled;
    private byte[] livenessGPUCache;
    private boolean livenessGPUEnabled;
    private byte[] livenessModel;
    private float livenessThreshold;
    private float lowBrightnessThreshold;
    private boolean maxFaceEnabled;
    private byte[] occlusionFilterModel;
    private byte[] poseBlurModel;
    private FacePose poseThreshold;
    private int rcAttributeAndOcclusionMode;
    private byte[] rcAttributeModel;
    private int retryCount;
    private boolean rgbIrLivenessEnabled;
    private byte[] rgbIrLivenessModel;
    private String rgbIrLivenessModelName;
    private byte[] searchModel;
    private float searchThreshold;
    private boolean smileEnabled;
    private byte[] smileModel;

    public int describeContents() {
        return 0;
    }

    public int getRcAttributeAndOcclusionMode() {
        return this.rcAttributeAndOcclusionMode;
    }

    public ZkFaceConfig setRcAttributeAndOcclusionMode(int i) {
        this.rcAttributeAndOcclusionMode = i;
        return this;
    }

    public int getDetectMode() {
        return this.detectMode;
    }

    public ZkFaceConfig setDetectMode(int i) {
        this.detectMode = i;
        return this;
    }

    public String getRgbIrLivenessModelName() {
        return this.rgbIrLivenessModelName;
    }

    public ZkFaceConfig setRgbIrLivenessModelName(String str) {
        this.rgbIrLivenessModelName = str;
        return this;
    }

    public String getGpuCacheName() {
        return this.gpuCacheName;
    }

    public ZkFaceConfig setGpuCacheName(String str) {
        this.gpuCacheName = str;
        return this;
    }

    public byte[] getRcAttributeModel() {
        return this.rcAttributeModel;
    }

    public ZkFaceConfig setRcAttributeModel(byte[] bArr) {
        this.rcAttributeModel = bArr;
        return this;
    }

    public ZkFaceConfig setSearchThreshold(float f) {
        this.searchThreshold = f;
        return this;
    }

    public ZkFaceConfig setLivenessThreshold(float f) {
        this.livenessThreshold = f;
        return this;
    }

    public ZkFaceConfig setLivenessEnabled(boolean z) {
        this.livenessEnabled = z;
        return this;
    }

    public ZkFaceConfig setRgbIrLivenessEnabled(boolean z) {
        this.rgbIrLivenessEnabled = z;
        return this;
    }

    public ZkFaceConfig setSmileEnabled(boolean z) {
        this.smileEnabled = z;
        return this;
    }

    public ZkFaceConfig setAgeGenderEnabled(boolean z) {
        this.ageGenderEnabled = z;
        return this;
    }

    public ZkFaceConfig setLivenessGPUEnabled(boolean z) {
        this.livenessGPUEnabled = z;
        return this;
    }

    public ZkFaceConfig setMaxFaceEnabled(boolean z) {
        this.maxFaceEnabled = z;
        return this;
    }

    public ZkFaceConfig setFaceMinThreshold(int i) {
        this.faceMinThreshold = i;
        return this;
    }

    public ZkFaceConfig setPoseThreshold(FacePose facePose) {
        this.poseThreshold = facePose;
        return this;
    }

    public ZkFaceConfig setBlurThreshold(float f) {
        this.blurThreshold = f;
        return this;
    }

    public ZkFaceConfig setLowBrightnessThreshold(float f) {
        this.lowBrightnessThreshold = f;
        return this;
    }

    public ZkFaceConfig setHighBrightnessThreshold(float f) {
        this.highBrightnessThreshold = f;
        return this;
    }

    public ZkFaceConfig setBrightnessSTDThreshold(float f) {
        this.brightnessSTDThreshold = f;
        return this;
    }

    public ZkFaceConfig setRetryCount(int i) {
        this.retryCount = i;
        return this;
    }

    public ZkFaceConfig setFileRootPath(String str) {
        this.fileRootPath = str;
        return this;
    }

    public ZkFaceConfig setPoseBlurModel(byte[] bArr) {
        this.poseBlurModel = bArr;
        return this;
    }

    public ZkFaceConfig setLivenessModel(byte[] bArr) {
        this.livenessModel = bArr;
        return this;
    }

    public ZkFaceConfig setRgbIrLivenessModel(byte[] bArr) {
        this.rgbIrLivenessModel = bArr;
        return this;
    }

    public ZkFaceConfig setSearchModel(byte[] bArr) {
        this.searchModel = bArr;
        return this;
    }

    public ZkFaceConfig setDetectModel(byte[] bArr) {
        this.detectModel = bArr;
        return this;
    }

    public ZkFaceConfig setDetectRectModel(byte[] bArr) {
        this.detectRectModel = bArr;
        return this;
    }

    public ZkFaceConfig setLandmarkModel(byte[] bArr) {
        this.landmarkModel = bArr;
        return this;
    }

    public ZkFaceConfig setSmileModel(byte[] bArr) {
        this.smileModel = bArr;
        return this;
    }

    public ZkFaceConfig setAgeGenderModel(byte[] bArr) {
        this.ageGenderModel = bArr;
        return this;
    }

    public ZkFaceConfig setLivenessGPUCache(byte[] bArr) {
        this.livenessGPUCache = bArr;
        return this;
    }

    public ZkFaceConfig setOcclusionFilterModel(byte[] bArr) {
        this.occlusionFilterModel = bArr;
        return this;
    }

    public ZkFaceConfig setEyeModel(byte[] bArr) {
        this.eyeModel = bArr;
        return this;
    }

    public ZkFaceConfig setEyeOCEnabled(boolean z) {
        this.eyeOCEnabled = z;
        return this;
    }

    public float getSearchThreshold() {
        return this.searchThreshold;
    }

    public float getLivenessThreshold() {
        return this.livenessThreshold;
    }

    public boolean isLivenessEnabled() {
        return this.livenessEnabled;
    }

    public boolean isRgbIrLivenessEnabled() {
        return this.rgbIrLivenessEnabled;
    }

    public boolean isSmileEnabled() {
        return this.smileEnabled;
    }

    public boolean isAgeGenderEnabled() {
        return this.ageGenderEnabled;
    }

    public boolean isLivenessGPUEnabled() {
        return this.livenessGPUEnabled;
    }

    public boolean isMaxFaceEnabled() {
        return this.maxFaceEnabled;
    }

    public int getFaceMinThreshold() {
        return this.faceMinThreshold;
    }

    public FacePose getPoseThreshold() {
        return this.poseThreshold;
    }

    public float getBlurThreshold() {
        return this.blurThreshold;
    }

    public float getLowBrightnessThreshold() {
        return this.lowBrightnessThreshold;
    }

    public float getHighBrightnessThreshold() {
        return this.highBrightnessThreshold;
    }

    public float getBrightnessSTDThreshold() {
        return this.brightnessSTDThreshold;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public String getFileRootPath() {
        return this.fileRootPath;
    }

    public byte[] getPoseBlurModel() {
        return this.poseBlurModel;
    }

    public byte[] getLivenessModel() {
        return this.livenessModel;
    }

    public byte[] getRgbIrLivenessModel() {
        return this.rgbIrLivenessModel;
    }

    public byte[] getSearchModel() {
        return this.searchModel;
    }

    public byte[] getDetectModel() {
        return this.detectModel;
    }

    public byte[] getDetectRectModel() {
        return this.detectRectModel;
    }

    public byte[] getLandmarkModel() {
        return this.landmarkModel;
    }

    public byte[] getSmileModel() {
        return this.smileModel;
    }

    public byte[] getAgeGenderModel() {
        return this.ageGenderModel;
    }

    public byte[] getLivenessGPUCache() {
        return this.livenessGPUCache;
    }

    public byte[] getOcclusionFilterModel() {
        return this.occlusionFilterModel;
    }

    public boolean isEyeOCEnabled() {
        return this.eyeOCEnabled;
    }

    public byte[] getEyeModel() {
        return this.eyeModel;
    }

    public ZkFaceConfig build() throws FileNotFoundException {
        if (this.poseBlurModel != null) {
            if (this.poseThreshold == null) {
                this.poseThreshold = new FacePose(30.0f, 30.0f, 30.0f);
            }
            if (this.livenessEnabled && this.livenessModel == null) {
                throw new FileNotFoundException("Liveness model is null");
            } else if (this.livenessGPUEnabled && this.livenessGPUCache == null) {
                throw new FileNotFoundException("GPU Cache model is null");
            } else if (this.rgbIrLivenessEnabled && this.rgbIrLivenessModel == null) {
                throw new FileNotFoundException("RGB IR live model is null");
            } else if (this.searchModel == null) {
                throw new FileNotFoundException("search model is null");
            } else if (this.detectModel == null) {
                throw new FileNotFoundException("detect model is null");
            } else if (this.detectRectModel == null) {
                throw new FileNotFoundException("detect manual model is null");
            } else if (this.landmarkModel == null) {
                throw new FileNotFoundException("landmark  model is null");
            } else if (this.ageGenderEnabled && this.ageGenderModel == null) {
                throw new FileNotFoundException("age and gender  model is null");
            } else if (this.smileEnabled && this.smileModel == null) {
                throw new FileNotFoundException("smile model is null");
            } else if (!this.eyeOCEnabled || this.eyeModel != null) {
                return this;
            } else {
                throw new FileNotFoundException("eye oc model is null");
            }
        } else {
            throw new FileNotFoundException("PoseBlur model is null");
        }
    }

    public static ZkFaceConfig getDefaultConfig(Context context) throws FileNotFoundException {
        return new ZkFaceConfig().setPoseBlurModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.POSE_BLUR_MODEL)).setBlurThreshold(0.7f).setPoseThreshold(new FacePose(35.0f, 35.0f, 35.0f)).setLivenessModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.LIVENESS_CPU_RGB_MODEL)).setLivenessThreshold(65.0f).setLivenessEnabled(true).setLivenessGPUCache((byte[]) null).setLivenessGPUEnabled(false).setRgbIrLivenessModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.LIVENESS_CPU_IR_RGB_MODEL)).setRgbIrLivenessModelName(ModelManager.LIVENESS_CPU_IR_RGB_MODEL).setRgbIrLivenessEnabled(false).setSearchModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.RECOGNIZE_MODEL)).setSearchThreshold(65.0f).setDetectModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.DETECT_FOR_INTO_GROUP_MODEL)).setDetectRectModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.DETECT_FOR_RECOGNIZE_MODEL)).setLandmarkModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.LAND_MARK_MODEL)).setAgeGenderModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.AGE_AND_GENDER_MODEL)).setAgeGenderEnabled(false).setSmileModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.SMILE_MODEL)).setSmileEnabled(false).setRcAttributeModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.RC_ATTRIBUTE_MODEL)).setOcclusionFilterModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.OCCLUSION_FILTER_MODEL)).setEyeModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.EYE_OC_MODEL)).setEyeOCEnabled(true).setRcAttributeAndOcclusionMode(1).setLowBrightnessThreshold(70.0f).setHighBrightnessThreshold(220.0f).setBrightnessSTDThreshold(60.0f).setRetryCount(3).setMaxFaceEnabled(false).setFaceMinThreshold(100).setFileRootPath(context.getCacheDir().getAbsolutePath()).build();
    }

    public static ZkFaceConfig getMiddleEastConfig(Context context) throws FileNotFoundException {
        return new ZkFaceConfig().setPoseBlurModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.POSE_BLUR_MODEL)).setBlurThreshold(0.7f).setPoseThreshold(new FacePose(35.0f, 35.0f, 35.0f)).setLivenessModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.LIVENESS_CPU_RGB_MODEL)).setLivenessThreshold(65.0f).setLivenessEnabled(true).setLivenessGPUCache((byte[]) null).setLivenessGPUEnabled(false).setRgbIrLivenessModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.LIVENESS_CPU_IR_RGB_MODEL)).setRgbIrLivenessModelName(ModelManager.LIVENESS_CPU_IR_RGB_MODEL).setRgbIrLivenessEnabled(false).setSearchModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.RECOGNIZE_MODEL_MIDDLE_EAST_5W)).setSearchThreshold(72.0f).setDetectModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.DETECT_FOR_INTO_GROUP_MODEL)).setDetectRectModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.DETECT_FOR_RECOGNIZE_MODEL)).setLandmarkModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.LAND_MARK_MODEL)).setAgeGenderModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.AGE_AND_GENDER_MODEL)).setAgeGenderEnabled(false).setSmileModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.SMILE_MODEL)).setSmileEnabled(false).setRcAttributeModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.RC_ATTRIBUTE_MODEL)).setOcclusionFilterModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.OCCLUSION_FILTER_MODEL)).setEyeModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.EYE_OC_MODEL)).setEyeOCEnabled(true).setRcAttributeAndOcclusionMode(1).setLowBrightnessThreshold(70.0f).setHighBrightnessThreshold(220.0f).setBrightnessSTDThreshold(60.0f).setRetryCount(3).setMaxFaceEnabled(false).setFaceMinThreshold(100).setFileRootPath(context.getCacheDir().getAbsolutePath()).build();
    }

    public static ZkFaceConfig getGPUConfig(Context context) throws FileNotFoundException {
        ZkFaceConfig zkFaceConfig = new ZkFaceConfig();
        return zkFaceConfig.setPoseBlurModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.POSE_BLUR_MODEL)).setBlurThreshold(0.8f).setPoseThreshold(new FacePose(30.0f, 30.0f, 30.0f)).setLivenessModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.LIVENESS_CPU_RGB_MODEL)).setLivenessEnabled(false).setLivenessGPUCache(ZKFaceUtil.readModel(context.getAssets(), ModelManager.GPU_RGB_IR_CACHE_MODEL)).setGpuCacheName(ModelManager.GPU_RGB_IR_CACHE_MODEL).setLivenessGPUEnabled(true).setRgbIrLivenessModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.LIVENESS_GPU_IR_RGB_MODEL)).setRgbIrLivenessModelName(ModelManager.LIVENESS_GPU_IR_RGB_MODEL).setRgbIrLivenessEnabled(true).setSearchModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.RECOGNIZE_MODEL)).setDetectModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.DETECT_FOR_INTO_GROUP_MODEL)).setDetectRectModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.DETECT_FOR_RECOGNIZE_MODEL)).setLandmarkModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.LAND_MARK_MODEL)).setAgeGenderModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.AGE_AND_GENDER_MODEL)).setAgeGenderEnabled(false).setSmileModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.SMILE_MODEL)).setSmileEnabled(false).setRcAttributeModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.RC_ATTRIBUTE_MODEL)).setOcclusionFilterModel(ZKFaceUtil.readModel(context.getAssets(), ModelManager.OCCLUSION_FILTER_MODEL)).setRcAttributeAndOcclusionMode(1).setLowBrightnessThreshold(70.0f).setHighBrightnessThreshold(210.0f).setBrightnessSTDThreshold(80.0f).setLivenessThreshold(0.0f).setSearchThreshold(65.0f).setRetryCount(3).setMaxFaceEnabled(false).setFaceMinThreshold(100).setDetectMode(0).setFileRootPath(context.getCacheDir().getAbsolutePath()).build();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.searchThreshold);
        parcel.writeFloat(this.livenessThreshold);
        parcel.writeByte(this.livenessEnabled ? (byte) 1 : 0);
        parcel.writeByte(this.rgbIrLivenessEnabled ? (byte) 1 : 0);
        parcel.writeByte(this.smileEnabled ? (byte) 1 : 0);
        parcel.writeByte(this.ageGenderEnabled ? (byte) 1 : 0);
        parcel.writeByte(this.livenessGPUEnabled ? (byte) 1 : 0);
        parcel.writeByte(this.maxFaceEnabled ? (byte) 1 : 0);
        parcel.writeInt(this.faceMinThreshold);
        parcel.writeParcelable(this.poseThreshold, i);
        parcel.writeFloat(this.blurThreshold);
        parcel.writeFloat(this.lowBrightnessThreshold);
        parcel.writeFloat(this.highBrightnessThreshold);
        parcel.writeFloat(this.brightnessSTDThreshold);
        parcel.writeInt(this.retryCount);
        parcel.writeString(this.fileRootPath);
        parcel.writeByteArray(this.poseBlurModel);
        parcel.writeByteArray(this.livenessModel);
        parcel.writeByteArray(this.rgbIrLivenessModel);
        parcel.writeByteArray(this.searchModel);
        parcel.writeByteArray(this.detectModel);
        parcel.writeByteArray(this.detectRectModel);
        parcel.writeByteArray(this.landmarkModel);
        parcel.writeByteArray(this.smileModel);
        parcel.writeByteArray(this.ageGenderModel);
        parcel.writeByteArray(this.livenessGPUCache);
        parcel.writeByteArray(this.occlusionFilterModel);
        parcel.writeByteArray(this.rcAttributeModel);
        parcel.writeString(this.gpuCacheName);
        parcel.writeString(this.rgbIrLivenessModelName);
        parcel.writeInt(this.detectMode);
        parcel.writeInt(this.rcAttributeAndOcclusionMode);
    }

    public void readFromParcel(Parcel parcel) {
        this.searchThreshold = parcel.readFloat();
        this.livenessThreshold = parcel.readFloat();
        boolean z = true;
        this.livenessEnabled = parcel.readByte() != 0;
        this.rgbIrLivenessEnabled = parcel.readByte() != 0;
        this.smileEnabled = parcel.readByte() != 0;
        this.ageGenderEnabled = parcel.readByte() != 0;
        this.livenessGPUEnabled = parcel.readByte() != 0;
        if (parcel.readByte() == 0) {
            z = false;
        }
        this.maxFaceEnabled = z;
        this.faceMinThreshold = parcel.readInt();
        this.poseThreshold = (FacePose) parcel.readParcelable(FacePose.class.getClassLoader());
        this.blurThreshold = parcel.readFloat();
        this.lowBrightnessThreshold = parcel.readFloat();
        this.highBrightnessThreshold = parcel.readFloat();
        this.brightnessSTDThreshold = parcel.readFloat();
        this.retryCount = parcel.readInt();
        this.fileRootPath = parcel.readString();
        this.poseBlurModel = parcel.createByteArray();
        this.livenessModel = parcel.createByteArray();
        this.rgbIrLivenessModel = parcel.createByteArray();
        this.searchModel = parcel.createByteArray();
        this.detectModel = parcel.createByteArray();
        this.detectRectModel = parcel.createByteArray();
        this.landmarkModel = parcel.createByteArray();
        this.smileModel = parcel.createByteArray();
        this.ageGenderModel = parcel.createByteArray();
        this.livenessGPUCache = parcel.createByteArray();
        this.occlusionFilterModel = parcel.createByteArray();
        this.rcAttributeModel = parcel.createByteArray();
        this.gpuCacheName = parcel.readString();
        this.rgbIrLivenessModelName = parcel.readString();
        this.detectMode = parcel.readInt();
        this.rcAttributeAndOcclusionMode = parcel.readInt();
    }

    public ZkFaceConfig() {
        this.searchThreshold = -1.0f;
        this.livenessThreshold = -1.0f;
        this.faceMinThreshold = -1;
        this.blurThreshold = -1.0f;
        this.lowBrightnessThreshold = -1.0f;
        this.highBrightnessThreshold = -1.0f;
        this.brightnessSTDThreshold = -1.0f;
        this.eyeOCEnabled = false;
        this.retryCount = -1;
        this.detectMode = 0;
        this.rcAttributeAndOcclusionMode = 0;
    }

    protected ZkFaceConfig(Parcel parcel) {
        this.searchThreshold = -1.0f;
        this.livenessThreshold = -1.0f;
        this.faceMinThreshold = -1;
        this.blurThreshold = -1.0f;
        this.lowBrightnessThreshold = -1.0f;
        this.highBrightnessThreshold = -1.0f;
        this.brightnessSTDThreshold = -1.0f;
        boolean z = false;
        this.eyeOCEnabled = false;
        this.retryCount = -1;
        this.detectMode = 0;
        this.rcAttributeAndOcclusionMode = 0;
        this.searchThreshold = parcel.readFloat();
        this.livenessThreshold = parcel.readFloat();
        this.livenessEnabled = parcel.readByte() != 0;
        this.rgbIrLivenessEnabled = parcel.readByte() != 0;
        this.smileEnabled = parcel.readByte() != 0;
        this.ageGenderEnabled = parcel.readByte() != 0;
        this.livenessGPUEnabled = parcel.readByte() != 0;
        this.maxFaceEnabled = parcel.readByte() != 0 ? true : z;
        this.faceMinThreshold = parcel.readInt();
        this.poseThreshold = (FacePose) parcel.readParcelable(FacePose.class.getClassLoader());
        this.blurThreshold = parcel.readFloat();
        this.lowBrightnessThreshold = parcel.readFloat();
        this.highBrightnessThreshold = parcel.readFloat();
        this.brightnessSTDThreshold = parcel.readFloat();
        this.retryCount = parcel.readInt();
        this.fileRootPath = parcel.readString();
        this.poseBlurModel = parcel.createByteArray();
        this.livenessModel = parcel.createByteArray();
        this.rgbIrLivenessModel = parcel.createByteArray();
        this.searchModel = parcel.createByteArray();
        this.detectModel = parcel.createByteArray();
        this.detectRectModel = parcel.createByteArray();
        this.landmarkModel = parcel.createByteArray();
        this.smileModel = parcel.createByteArray();
        this.ageGenderModel = parcel.createByteArray();
        this.livenessGPUCache = parcel.createByteArray();
        this.occlusionFilterModel = parcel.createByteArray();
        this.rcAttributeModel = parcel.createByteArray();
        this.gpuCacheName = parcel.readString();
        this.rgbIrLivenessModelName = parcel.readString();
        this.detectMode = parcel.readInt();
        this.rcAttributeAndOcclusionMode = parcel.readInt();
    }
}
