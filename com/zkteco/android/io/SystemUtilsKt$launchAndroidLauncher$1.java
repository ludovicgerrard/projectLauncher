package com.zkteco.android.io;

import android.os.Build;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 1, 9})
/* compiled from: SystemUtils.kt */
final class SystemUtilsKt$launchAndroidLauncher$1 extends Lambda implements Function0<Unit> {
    public static final SystemUtilsKt$launchAndroidLauncher$1 INSTANCE = new SystemUtilsKt$launchAndroidLauncher$1();

    SystemUtilsKt$launchAndroidLauncher$1() {
        super(0);
    }

    public final void invoke() {
        if (Build.VERSION.SDK_INT >= 21) {
            SystemUtilsKt.launchAndroidLauncher3();
        } else {
            SystemUtilsKt.launchAndroidLauncher2();
        }
    }
}
