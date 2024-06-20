package com.zkteco.android.util;

import android.os.Process;

public final class AppKillerHelper {
    private AppKillerHelper() {
    }

    public static void killApp(boolean z) {
        if (z) {
            System.runFinalizersOnExit(true);
            System.exit(0);
            return;
        }
        Process.killProcess(Process.myPid());
    }
}
