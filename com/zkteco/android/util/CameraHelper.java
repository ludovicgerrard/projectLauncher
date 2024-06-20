package com.zkteco.android.util;

import android.content.Context;

public class CameraHelper {
    public static boolean hasCameraSupport(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.camera");
    }
}
