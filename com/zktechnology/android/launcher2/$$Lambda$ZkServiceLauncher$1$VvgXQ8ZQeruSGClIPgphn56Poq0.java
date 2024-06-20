package com.zktechnology.android.launcher2;

import com.zktechnology.android.helper.FingerprintServiceHelper;

/* renamed from: com.zktechnology.android.launcher2.-$$Lambda$ZkServiceLauncher$1$VvgXQ8ZQeruSGClIPgphn56Poq0  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ZkServiceLauncher$1$VvgXQ8ZQeruSGClIPgphn56Poq0 implements Runnable {
    public static final /* synthetic */ $$Lambda$ZkServiceLauncher$1$VvgXQ8ZQeruSGClIPgphn56Poq0 INSTANCE = new $$Lambda$ZkServiceLauncher$1$VvgXQ8ZQeruSGClIPgphn56Poq0();

    private /* synthetic */ $$Lambda$ZkServiceLauncher$1$VvgXQ8ZQeruSGClIPgphn56Poq0() {
    }

    public final void run() {
        FingerprintServiceHelper.getInstance().init();
    }
}
