package com.zkteco.android.io;

import com.zkteco.util.YAMLHelper;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\f\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u0001H\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "T", "kotlin.jvm.PlatformType", "data", "", "invoke", "(Ljava/lang/String;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 9})
/* compiled from: NetworkUtils.kt */
final class NetworkUtilsKt$getRemoteObjectFromHttp$1 extends Lambda implements Function1<String, T> {
    public static final NetworkUtilsKt$getRemoteObjectFromHttp$1 INSTANCE = new NetworkUtilsKt$getRemoteObjectFromHttp$1();

    NetworkUtilsKt$getRemoteObjectFromHttp$1() {
        super(1);
    }

    public final T invoke(String str) {
        Intrinsics.checkParameterIsNotNull(str, "data");
        return YAMLHelper.getInstanceFromString(str);
    }
}
