package com.zkteco.android.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import dalvik.system.DexClassLoader;

public class APKHelper {
    private static String getAPKPath(Context context, String str) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(str, 0).sourceDir;
    }

    public static ClassLoader loadAPK(Context context, String str) throws PackageManager.NameNotFoundException {
        return new DexClassLoader(getAPKPath(context, str), context.getDir("tmp", 0).getAbsolutePath(), (String) null, context.getClassLoader());
    }

    public static Resources getResourcesForApplication(Context context, String str) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getResourcesForApplication(str);
    }
}
