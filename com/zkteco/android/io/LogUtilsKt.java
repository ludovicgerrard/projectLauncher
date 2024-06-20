package com.zkteco.android.io;

import android.util.Log;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0010\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0001\u001a0\u0010\u0005\u001a\u0004\u0018\u0001H\u0006\"\u0004\b\u0000\u0010\u00062\b\b\u0002\u0010\u0004\u001a\u00020\u00012\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00060\bH\b¢\u0006\u0002\u0010\t\u001a\u001f\u0010\n\u001a\n \u000b*\u0004\u0018\u00010\u00010\u0001\"\u0004\b\u0000\u0010\u0006*\u0002H\u0006H\u0002¢\u0006\u0002\u0010\f\u001a\u001f\u0010\r\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006*\u0002H\u00062\b\b\u0002\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u000e\u001a'\u0010\r\u001a\u0002H\u0006\"\f\b\u0000\u0010\u0006*\u00060\u000fj\u0002`\u0010*\u0002H\u00062\b\b\u0002\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0011\u001a\u001f\u0010\u0012\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006*\u0002H\u00062\b\b\u0002\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u000e\u001a\u001f\u0010\u0013\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006*\u0002H\u00062\b\b\u0002\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u000e\u001a\u001f\u0010\u0014\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006*\u0002H\u00062\b\b\u0002\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u000e\u001a\u001f\u0010\u0015\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006*\u0002H\u00062\b\b\u0002\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u000e\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"TAG", "", "logCallTrace", "", "tag", "logExecutionTime", "T", "block", "Lkotlin/Function0;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "asString", "kotlin.jvm.PlatformType", "(Ljava/lang/Object;)Ljava/lang/String;", "log", "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "(Ljava/lang/Exception;Ljava/lang/String;)Ljava/lang/Exception;", "logDebug", "logError", "logInfo", "logWarning", "HelpersAndroidIO_release"}, k = 2, mv = {1, 1, 9})
/* compiled from: LogUtils.kt */
public final class LogUtilsKt {
    private static final String TAG = "LogUtils";

    private static final <T> String asString(T t) {
        if (t instanceof Object[]) {
            return Arrays.toString((Object[]) t);
        }
        if (t instanceof byte[]) {
            return Arrays.toString((byte[]) t);
        }
        if (t instanceof short[]) {
            return Arrays.toString((short[]) t);
        }
        if (t instanceof int[]) {
            return Arrays.toString((int[]) t);
        }
        if (t instanceof long[]) {
            return Arrays.toString((long[]) t);
        }
        return String.valueOf(t);
    }

    public static /* bridge */ /* synthetic */ Object logDebug$default(Object obj, String str, int i, Object obj2) {
        if ((i & 1) != 0) {
            str = TAG;
        }
        return logDebug(obj, str);
    }

    public static final <T> T logDebug(T t, String str) {
        Intrinsics.checkParameterIsNotNull(str, "tag");
        Log.d(str, asString(t));
        return t;
    }

    public static final <T> T log(T t, String str) {
        Intrinsics.checkParameterIsNotNull(str, "tag");
        return logDebug(t, str);
    }

    public static /* bridge */ /* synthetic */ Object log$default(Object obj, String str, int i, Object obj2) {
        if ((i & 1) != 0) {
            str = TAG;
        }
        return log(obj, str);
    }

    public static /* bridge */ /* synthetic */ Object logInfo$default(Object obj, String str, int i, Object obj2) {
        if ((i & 1) != 0) {
            str = TAG;
        }
        return logInfo(obj, str);
    }

    public static final <T> T logInfo(T t, String str) {
        Intrinsics.checkParameterIsNotNull(str, "tag");
        Log.i(str, asString(t));
        return t;
    }

    public static /* bridge */ /* synthetic */ Object logWarning$default(Object obj, String str, int i, Object obj2) {
        if ((i & 1) != 0) {
            str = TAG;
        }
        return logWarning(obj, str);
    }

    public static final <T> T logWarning(T t, String str) {
        Intrinsics.checkParameterIsNotNull(str, "tag");
        Log.w(str, asString(t));
        return t;
    }

    public static /* bridge */ /* synthetic */ Object logError$default(Object obj, String str, int i, Object obj2) {
        if ((i & 1) != 0) {
            str = TAG;
        }
        return logError(obj, str);
    }

    public static final <T> T logError(T t, String str) {
        Intrinsics.checkParameterIsNotNull(str, "tag");
        Log.e(str, asString(t));
        return t;
    }

    public static /* bridge */ /* synthetic */ Exception log$default(Exception exc, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = TAG;
        }
        return log(exc, str);
    }

    public static final <T extends Exception> T log(T t, String str) {
        Intrinsics.checkParameterIsNotNull(t, "$receiver");
        Intrinsics.checkParameterIsNotNull(str, "tag");
        logError(Log.getStackTraceString((Throwable) t), str);
        return t;
    }

    public static /* bridge */ /* synthetic */ Object logExecutionTime$default(String str, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            str = TAG;
        }
        Intrinsics.checkParameterIsNotNull(str, "tag");
        Intrinsics.checkParameterIsNotNull(function0, "block");
        long currentTimeMillis = System.currentTimeMillis();
        Object invoke = function0.invoke();
        logDebug("" + str + ": " + (System.currentTimeMillis() - currentTimeMillis), str);
        return invoke;
    }

    public static /* bridge */ /* synthetic */ void logCallTrace$default(String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = TAG;
        }
        logCallTrace(str);
    }

    public static final void logCallTrace(String str) {
        Intrinsics.checkParameterIsNotNull(str, "tag");
        try {
            throw new IllegalStateException();
        } catch (IllegalStateException e) {
            logDebug(Log.getStackTraceString(e), str);
        }
    }

    public static final <T> T logExecutionTime(String str, Function0<? extends T> function0) {
        Intrinsics.checkParameterIsNotNull(str, "tag");
        Intrinsics.checkParameterIsNotNull(function0, "block");
        long currentTimeMillis = System.currentTimeMillis();
        T invoke = function0.invoke();
        logDebug("" + str + ": " + (System.currentTimeMillis() - currentTimeMillis), str);
        return invoke;
    }
}
