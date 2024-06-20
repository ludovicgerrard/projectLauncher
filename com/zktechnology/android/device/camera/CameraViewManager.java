package com.zktechnology.android.device.camera;

import android.content.Context;
import com.zktechnology.android.device.DeviceManager;
import com.zkteco.edk.camera.lib.ZkCameraView;
import java.util.List;

public class CameraViewManager {
    public static ZkCameraView createDefault(Context context) {
        ZkCameraView zkCameraView = new ZkCameraView(context);
        List<CameraParam> params = DeviceManager.getDefault().getCameraConfig().getParams();
        if (params != null && !params.isEmpty()) {
            CameraParam cameraParam = params.get(0);
            zkCameraView.setCameraAngle(cameraParam.getCameraAngle());
            zkCameraView.setCameraIndex(cameraParam.getCameraIndex());
            zkCameraView.setCameraMirror(cameraParam.getCameraMirror());
            zkCameraView.setCameraResolution(cameraParam.getCameraResolution());
        }
        return zkCameraView;
    }

    public static ZkCameraView createSecondary(Context context) {
        ZkCameraView zkCameraView = new ZkCameraView(context);
        List<CameraParam> params = DeviceManager.getDefault().getCameraConfig().getParams();
        if (params == null || params.size() < 2) {
            return null;
        }
        CameraParam cameraParam = params.get(1);
        zkCameraView.setCameraAngle(cameraParam.getCameraAngle());
        zkCameraView.setCameraIndex(cameraParam.getCameraIndex());
        zkCameraView.setCameraMirror(cameraParam.getCameraMirror());
        zkCameraView.setCameraResolution(cameraParam.getCameraResolution());
        return zkCameraView;
    }

    public static void initDefaultCameraView(ZkCameraView zkCameraView) {
        List<CameraParam> params;
        if (zkCameraView != null && (params = DeviceManager.getDefault().getCameraConfig().getParams()) != null && !params.isEmpty()) {
            CameraParam cameraParam = params.get(0);
            zkCameraView.setCameraAngle(cameraParam.getCameraAngle());
            zkCameraView.setCameraIndex(cameraParam.getCameraIndex());
            zkCameraView.setCameraMirror(cameraParam.getCameraMirror());
            zkCameraView.setCameraResolution(cameraParam.getCameraResolution());
        }
    }
}
