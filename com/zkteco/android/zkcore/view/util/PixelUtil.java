package com.zkteco.android.zkcore.view.util;

import android.content.Context;
import android.content.res.Resources;

public class PixelUtil {
    public static int dp2px(float f, Context context) {
        return (int) ((f * (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f)) + 0.5f);
    }

    public static int px2dp(float f, Context context) {
        return (int) (((f * 160.0f) / ((float) context.getResources().getDisplayMetrics().densityDpi)) + 0.5f);
    }

    public static int sp2px(float f, Context context) {
        Resources resources;
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        return (int) ((f * resources.getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int px2sp(float f, Context context) {
        return (int) ((f / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }
}
