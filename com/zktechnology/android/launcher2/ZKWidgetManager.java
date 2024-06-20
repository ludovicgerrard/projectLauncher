package com.zktechnology.android.launcher2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ZKWidgetManager {
    public static final String APPWIDGET_UPDATE = "com.zktechnology.android.APPWIDGET_UPDATE";
    public static final String BUNDLE = "info";
    private static final String TAG = "com.zktechnology.android.launcher2.ZKWidgetManager";

    public static void updateMyWidgets(Activity activity) {
        String str = TAG;
        Log.e(str, "--updateMyWidgets--");
        Intent intent = new Intent();
        intent.setAction(APPWIDGET_UPDATE);
        intent.putExtra(BUNDLE, new Bundle());
        if (activity == null) {
            Log.e(str, "Activity is null! (while updating widgets)");
        } else {
            activity.sendBroadcast(intent);
        }
    }
}
