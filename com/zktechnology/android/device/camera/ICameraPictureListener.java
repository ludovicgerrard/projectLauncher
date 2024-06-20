package com.zktechnology.android.device.camera;

public interface ICameraPictureListener {
    String getPath();

    void onPictureAndSaveFinish();
}
