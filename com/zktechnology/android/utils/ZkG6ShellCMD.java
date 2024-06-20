package com.zktechnology.android.utils;

import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.push.util.FileLogUtils;

public class ZkG6ShellCMD {
    private static final String TAG = "ZkG6ShellCMD";

    public static void killCameraProcess() {
        if (DeviceManager.getDefault().isG6()) {
            String str = "G6 killCameraProcess " + ZkShellUtils.execCommand("su 0,0 killall android.hardware.camera.provider@2.4-service", false);
            LogUtils.i(TAG, str);
            FileLogUtils.writeCameraLog(str);
        }
    }
}
