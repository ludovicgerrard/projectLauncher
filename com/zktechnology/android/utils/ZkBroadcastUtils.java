package com.zktechnology.android.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import com.zktechnology.android.launcher2.ZkActions;

public class ZkBroadcastUtils {
    public static void sendCameraErrorBroadcast(Context context) {
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(ZkActions.BR_CAMERA_ERROR);
            if (Build.VERSION.SDK_INT >= 24) {
                context.sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(Process.myUid()));
            } else {
                context.sendBroadcast(intent);
            }
        }
    }
}
