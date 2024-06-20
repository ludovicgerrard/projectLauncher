package com.zkteco.util;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a*\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006\u001a\u001b\u0010\u0007\u001a\u00020\u0001*\u00020\b2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\b\u001a\u001b\u0010\t\u001a\u00020\u0001*\u00020\b2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\b\u001a\u0017\u0010\n\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u000b\u001a\u0017\u0010\f\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u000b\u001a!\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u0002¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"forEachNotNull", "", "T", "", "", "block", "Lkotlin/Function0;", "ifFalse", "", "ifTrue", "isNotNull", "(Ljava/lang/Object;)Z", "isNull", "tag", "", "kotlin.jvm.PlatformType", "(Ljava/lang/Object;)Ljava/lang/String;", "HelpersUtils"}, k = 2, mv = {1, 1, 9})
/* compiled from: Utils.kt */
public final class UtilsKt {
    public static final <T> boolean isNull(T t) {
        return t == null;
    }

    public static final <T> String tag(T t) {
        Intrinsics.checkParameterIsNotNull(t, "$receiver");
        return t.getClass().getSimpleName();
    }

    public static final void ifTrue(boolean z, Function0<Unit> function0) {
        Intrinsics.checkParameterIsNotNull(function0, "block");
        if (z) {
            function0.invoke();
        }
    }

    public static final void ifFalse(boolean z, Function0<Unit> function0) {
        Intrinsics.checkParameterIsNotNull(function0, "block");
        if (!z) {
            function0.invoke();
        }
    }

    public static final <T> boolean isNotNull(T t) {
        return !isNull(t);
    }

    public static final <T> void forEachNotNull(Iterable<? extends T> iterable, Function0<Unit> function0) {
        Intrinsics.checkParameterIsNotNull(iterable, "$receiver");
        Intrinsics.checkParameterIsNotNull(function0, "block");
        Iterator it = CollectionsKt.filterNotNull(iterable).iterator();
        while (it.hasNext()) {
            it.next();
            function0.invoke();
        }
    }
}
