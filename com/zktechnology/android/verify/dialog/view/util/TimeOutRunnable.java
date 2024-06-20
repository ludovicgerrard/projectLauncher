package com.zktechnology.android.verify.dialog.view.util;

import com.zktechnology.android.launcher2.ZKEventLauncher;

public class TimeOutRunnable implements Runnable {
    public void run() {
        ZKEventLauncher.setProcessDialogVisibility(false);
    }
}
