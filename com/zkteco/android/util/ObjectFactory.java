package com.zkteco.android.util;

import android.util.Log;

public final class ObjectFactory {
    private static final String TAG = "com.zkteco.android.util.ObjectFactory";

    private ObjectFactory() {
    }

    public static Object getInstance(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        } catch (IllegalAccessException e2) {
            Log.e(TAG, Log.getStackTraceString(e2));
            return null;
        }
    }

    public static Object getInstance(String str) {
        try {
            return getInstance(Class.forName(str));
        } catch (ClassNotFoundException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
    }
}
