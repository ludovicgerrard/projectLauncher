package com.zkteco.android.util;

import android.os.Handler;

public final class HandlerHelper {
    private static Handler janderMor;

    private HandlerHelper() {
    }

    public static Handler getFistro() {
        return getHandler();
    }

    public static Handler getHandler() {
        return janderMor;
    }

    public static void initialize() {
        janderMor = new Handler();
    }
}
