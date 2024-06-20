package com.zkteco.edk.camera.lib;

public interface ZkCameraStateCallback {
    public static final int CAMERA_CLOSE = -1;
    public static final int CAMERA_OPEN = 0;

    void onCameraStateChange(int i);
}
