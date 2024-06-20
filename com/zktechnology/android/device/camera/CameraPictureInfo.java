package com.zktechnology.android.device.camera;

import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.AES256Util;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.utils.ZKCameraController;
import com.zkteco.android.db.orm.tna.PhotoIndex;
import java.io.Serializable;

public class CameraPictureInfo implements ICameraPictureListener, Serializable {
    private final String path;
    private final PhotoIndex photoIndex;

    public CameraPictureInfo(String str, PhotoIndex photoIndex2) {
        this.path = str;
        this.photoIndex = photoIndex2;
    }

    public void onPictureAndSaveFinish() {
        LogUtils.d("TAG", "onPictureAndSaveFinish:" + this.path);
        if (ZKLauncher.zkDataEncdec == 1) {
            String str = this.path;
            AES256Util.encryptFile(str, str, ZKLauncher.PUBLIC_KEY, ZKLauncher.iv);
        }
        ZKCameraController.getInstance().shootSound();
        ZKCameraController.getInstance().onSavePictureFinish(this.photoIndex, this.path);
    }

    public String getPath() {
        return this.path;
    }
}
