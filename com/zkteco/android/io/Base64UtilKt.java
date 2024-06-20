package com.zkteco.android.io;

import android.util.Base64;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001*\u00020\u0003\u001a\u0012\u0010\u0004\u001a\n \u0002*\u0004\u0018\u00010\u00030\u0003*\u00020\u0001\u001a\u0012\u0010\u0004\u001a\n \u0002*\u0004\u0018\u00010\u00030\u0003*\u00020\u0003¨\u0006\u0005"}, d2 = {"decode", "", "kotlin.jvm.PlatformType", "", "encode", "HelpersAndroidIO_release"}, k = 2, mv = {1, 1, 9})
/* compiled from: Base64Util.kt */
public final class Base64UtilKt {
    public static final byte[] decode(String str) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        return Base64.decode(str, 0);
    }

    public static final String encode(byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "$receiver");
        return Base64.encodeToString(bArr, 0);
    }

    public static final String encode(String str) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        return Base64.encodeToString(bytes, 0);
    }
}
