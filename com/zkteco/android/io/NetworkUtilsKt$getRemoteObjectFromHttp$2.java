package com.zkteco.android.io;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "T", "error", "", "invoke"}, k = 3, mv = {1, 1, 9})
/* compiled from: NetworkUtils.kt */
final class NetworkUtilsKt$getRemoteObjectFromHttp$2 extends Lambda implements Function1<String, Unit> {
    public static final NetworkUtilsKt$getRemoteObjectFromHttp$2 INSTANCE = new NetworkUtilsKt$getRemoteObjectFromHttp$2();

    NetworkUtilsKt$getRemoteObjectFromHttp$2() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((String) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(String str) {
        LogUtilsKt.log$default((Object) "Error obtaining file: " + str, (String) null, 1, (Object) null);
    }
}
