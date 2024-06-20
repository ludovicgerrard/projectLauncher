package com.zkteco.adk.core.task;

import android.text.TextUtils;
import java.util.Map;

public class ZkTaskParamUtils {
    public static int getIntParam(Map<String, Object> map, String str) {
        Object obj;
        if (TextUtils.isEmpty(str) || map == null || (obj = map.get(str)) == null) {
            return -1;
        }
        return ((Integer) obj).intValue();
    }

    public static int getIntParam(Map<String, Object> map, String str, int i) {
        Object obj;
        if (TextUtils.isEmpty(str) || map == null || (obj = map.get(str)) == null) {
            return i;
        }
        return ((Integer) obj).intValue();
    }

    public static long getLongParam(Map<String, Object> map, String str) {
        return getLongParam(map, str, 0);
    }

    public static long getLongParam(Map<String, Object> map, String str, long j) {
        Object obj;
        if (TextUtils.isEmpty(str) || map == null || (obj = map.get(str)) == null) {
            return j;
        }
        return ((Long) obj).longValue();
    }

    public static float getFloatParam(Map<String, Object> map, String str) {
        Object obj;
        if (TextUtils.isEmpty(str) || map == null || (obj = map.get(str)) == null) {
            return 0.0f;
        }
        return ((Float) obj).floatValue();
    }

    public static float getFloatParam(Map<String, Object> map, String str, float f) {
        Object obj;
        if (TextUtils.isEmpty(str) || map == null || (obj = map.get(str)) == null) {
            return f;
        }
        return ((Float) obj).floatValue();
    }

    public static boolean getBoolParam(Map<String, Object> map, String str) {
        Object obj;
        if (TextUtils.isEmpty(str) || map == null || (obj = map.get(str)) == null) {
            return false;
        }
        return ((Boolean) obj).booleanValue();
    }

    public static boolean getBoolParam(Map<String, Object> map, String str, boolean z) {
        Object obj;
        if (TextUtils.isEmpty(str) || map == null || (obj = map.get(str)) == null) {
            return z;
        }
        return ((Boolean) obj).booleanValue();
    }

    public static String getStringParam(Map<String, Object> map, String str) {
        Object obj;
        if (TextUtils.isEmpty(str) || map == null || (obj = map.get(str)) == null) {
            return null;
        }
        return (String) obj;
    }

    public static byte[] getByteArrayParam(Map<String, Object> map, String str) {
        Object obj;
        if (TextUtils.isEmpty(str) || map == null || (obj = map.get(str)) == null) {
            return null;
        }
        return (byte[]) obj;
    }
}
