package com.zkteco.android.util;

import android.util.Log;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TestHelper {
    private static final String TAG = "com.zkteco.android.util.TestHelper";

    private TestHelper() {
    }

    private static boolean checkTestCaller() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int length = stackTrace.length;
        int i = 0;
        boolean z = false;
        while (i < length) {
            StackTraceElement stackTraceElement = stackTrace[i];
            try {
                z = isAnyTestCaller(getAllMethodsByName(Arrays.asList(Class.forName(stackTraceElement.getClassName()).getDeclaredMethods()), stackTraceElement.getMethodName()));
                if (z) {
                    break;
                }
                i++;
            } catch (ClassNotFoundException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                return false;
            }
        }
        return z;
    }

    private static List<Method> getAllMethodsByName(List<Method> list, String str) {
        return getAllMethodsByName(list, str, 0);
    }

    private static List<Method> getAllMethodsByName(List<Method> list, String str, int i) {
        ArrayList arrayList = new ArrayList(3);
        if (i >= list.size()) {
            return arrayList;
        }
        while (i < list.size()) {
            if (list.get(i).getName().equals(str)) {
                arrayList.add(list.get(i));
            }
            i++;
        }
        return arrayList;
    }

    private static boolean isAnyTestCaller(List<Method> list) {
        boolean z = false;
        int i = -1;
        while (true) {
            i++;
            if (i >= list.size() || z) {
                return z;
            }
            z = list.get(i).isAnnotationPresent(ZKTest.class);
        }
        return z;
    }

    public static void isTestCaller() throws RuntimeException {
        if (!checkTestCaller()) {
            throw new RuntimeException("Test method not called from test code");
        }
    }
}
