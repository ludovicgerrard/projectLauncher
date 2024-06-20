package com.zktechnology.android.utils;

import android.os.SystemClock;
import android.view.View;

public class ClickUtils {
    public static boolean executeClick(View view, long j) {
        Object tag = view.getTag();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (tag != null && Math.abs(elapsedRealtime - ((Long) tag).longValue()) < j) {
            return false;
        }
        view.setTag(Long.valueOf(elapsedRealtime));
        return true;
    }
}
