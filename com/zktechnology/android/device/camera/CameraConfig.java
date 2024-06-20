package com.zktechnology.android.device.camera;

import java.io.Serializable;
import java.util.List;

public class CameraConfig implements Serializable {
    private int cameraType;
    private List<CameraParam> params;
    private int previewHeight = 1280;
    private int previewWidth = 720;

    public CameraConfig() {
    }

    public CameraConfig(int i) {
        this.cameraType = i;
    }

    public int getCameraType() {
        return this.cameraType;
    }

    public void setCameraType(int i) {
        this.cameraType = i;
    }

    public List<CameraParam> getParams() {
        return this.params;
    }

    public void setParams(List<CameraParam> list) {
        this.params = list;
    }

    public int getPreviewWidth() {
        return this.previewWidth;
    }

    public void setPreviewWidth(int i) {
        this.previewWidth = i;
    }

    public int getPreviewHeight() {
        return this.previewHeight;
    }

    public void setPreviewHeight(int i) {
        this.previewHeight = i;
    }

    public String toString() {
        return "CameraConfig{cameraType=" + this.cameraType + ", previewWidth=" + this.previewWidth + ", previewHeight=" + this.previewHeight + ", params=" + this.params + '}';
    }
}
