package com.zkteco.util;

public final class ClassLoaderHelper {
    private static ClassLoader classLoader;

    public static ClassLoader getClassLoader() {
        return classLoader;
    }

    public static void setClassLoader(ClassLoader classLoader2) {
        classLoader = classLoader2;
    }

    private ClassLoaderHelper() {
    }
}
