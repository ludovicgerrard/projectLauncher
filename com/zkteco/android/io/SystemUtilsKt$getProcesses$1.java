package com.zkteco.android.io;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 1, 9})
/* compiled from: SystemUtils.kt */
final class SystemUtilsKt$getProcesses$1 extends Lambda implements Function1<String, Boolean> {
    public static final SystemUtilsKt$getProcesses$1 INSTANCE = new SystemUtilsKt$getProcesses$1();

    SystemUtilsKt$getProcesses$1() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return Boolean.valueOf(invoke((String) obj));
    }

    public final boolean invoke(String str) {
        Intrinsics.checkParameterIsNotNull(str, "it");
        return true;
    }
}
