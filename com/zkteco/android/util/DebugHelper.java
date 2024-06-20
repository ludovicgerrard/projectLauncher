package com.zkteco.android.util;

import android.util.Log;
import com.zkteco.util.FailedExecutionException;

public class DebugHelper {
    public static void logStackTrace(String str, String str2) {
        try {
            throw new FailedExecutionException(str2);
        } catch (FailedExecutionException e) {
            Log.d(str, Log.getStackTraceString(e));
        }
    }
}
