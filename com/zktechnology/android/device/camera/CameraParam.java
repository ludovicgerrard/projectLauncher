package com.zktechnology.android.device.camera;

import java.io.Serializable;

public class CameraParam implements Serializable {
    private int cameraAngle;
    private int cameraIndex;
    private int cameraMirror;
    private int cameraResolution;

    public CameraParam() {
    }

    public CameraParam(int i, int i2, int i3, int i4) {
        this.cameraAngle = i;
        this.cameraIndex = i2;
        this.cameraMirror = i3;
        this.cameraResolution = i4;
    }

    public int getCameraAngle() {
        return this.cameraAngle;
    }

    public void setCameraAngle(int i) {
        this.cameraAngle = i;
    }

    public int getCameraIndex() {
        return this.cameraIndex;
    }

    public void setCameraIndex(int i) {
        this.cameraIndex = i;
    }

    public int getCameraMirror() {
        return this.cameraMirror;
    }

    public void setCameraMirror(int i) {
        this.cameraMirror = i;
    }

    public int getCameraResolution() {
        return this.cameraResolution;
    }

    public void setCameraResolution(int i) {
        this.cameraResolution = i;
    }

    public String toString() {
        return "CameraParam{cameraAngle=" + this.cameraAngle + ", cameraIndex=" + this.cameraIndex + ", cameraMirror=" + this.cameraMirror + ", cameraResolution=" + this.cameraResolution + '}';
    }
}
