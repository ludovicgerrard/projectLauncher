package com.zkteco.android.io;

import com.zkteco.util.YAMLHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\f\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a\u001d\u0010\u0000\u001a\n \u0002*\u0004\u0018\u0001H\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0003¢\u0006\u0002\u0010\u0004\u001a\u001d\u0010\u0005\u001a\n \u0002*\u0004\u0018\u00010\u00030\u0003\"\u0004\b\u0000\u0010\u0001*\u0002H\u0001¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"toInstanceFromYAML", "T", "kotlin.jvm.PlatformType", "", "(Ljava/lang/String;)Ljava/lang/Object;", "toYAML", "(Ljava/lang/Object;)Ljava/lang/String;", "HelpersAndroidIO_release"}, k = 2, mv = {1, 1, 9})
/* compiled from: YAMLUtils.kt */
public final class YAMLUtilsKt {
    public static final <T> String toYAML(T t) {
        return YAMLHelper.getStringFromInstance(t);
    }

    public static final <T> T toInstanceFromYAML(String str) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        return YAMLHelper.getInstanceFromString(str);
    }
}
