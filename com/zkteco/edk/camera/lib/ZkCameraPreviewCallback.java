package com.zkteco.edk.camera.lib;

import android.hardware.Camera;

public interface ZkCameraPreviewCallback {
    void onPreviewFrame(byte[] bArr, Camera camera, int i, int i2);
}
