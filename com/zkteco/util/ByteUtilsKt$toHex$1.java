package com.zkteco.util;

import com.google.common.base.Ascii;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u0005\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 1, 9})
/* compiled from: ByteUtils.kt */
final class ByteUtilsKt$toHex$1 extends Lambda implements Function1<Byte, CharSequence> {
    public static final ByteUtilsKt$toHex$1 INSTANCE = new ByteUtilsKt$toHex$1();

    ByteUtilsKt$toHex$1() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return invoke(((Number) obj).byteValue());
    }

    public final CharSequence invoke(byte b) {
        String str = "" + ByteUtilsKt.HEX_CHARS[(b & 240) >>> 4] + "" + ByteUtilsKt.HEX_CHARS[b & Ascii.SI];
        if (str.length() == 1) {
            return '0' + str;
        }
        return str;
    }
}
