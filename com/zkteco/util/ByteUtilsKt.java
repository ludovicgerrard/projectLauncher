package com.zkteco.util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 2}, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0010\u0011\n\u0002\u0010\u0005\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0012\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003*\u00020\u0003\u001a\u0012\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003*\u00020\u0003\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\n\u001a\n\u0010\u0006\u001a\u00020\u0007*\u00020\u0003\u001a\u0015\u0010\u000b\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\n\u001a\n\u0010\u000b\u001a\u00020\u0007*\u00020\u0003\u001a\u0015\u0010\f\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\n\u001a\n\u0010\f\u001a\u00020\u0007*\u00020\u0003\u001a\n\u0010\r\u001a\u00020\u000e*\u00020\u0003\u001a\u0012\u0010\u000f\u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0007\u001a\u0012\u0010\u0013\u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000e\u001a\u001a\u0010\u0014\u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u000e\u001a\u0012\u0010\u0016\u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000e\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"HEX_CHARS", "", "compress", "", "kotlin.jvm.PlatformType", "decompress", "toHex", "", "", "", "([Ljava/lang/Byte;)Ljava/lang/String;", "toHexBigEndian", "toHexLittleEndian", "withoutNull", "", "writeAs32bitHex", "", "Ljava/io/ByteArrayOutputStream;", "value", "writeAsByte", "writeAsFixedLengthBuffer", "size", "writeAsShort", "HelpersUtils"}, k = 2, mv = {1, 1, 9})
/* compiled from: ByteUtils.kt */
public final class ByteUtilsKt {
    /* access modifiers changed from: private */
    public static final char[] HEX_CHARS;

    static {
        char[] charArray = "0123456789ABCDEF".toCharArray();
        Intrinsics.checkExpressionValueIsNotNull(charArray, "(this as java.lang.String).toCharArray()");
        HEX_CHARS = charArray;
    }

    public static final String toHex(Byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "$receiver");
        return ArraysKt.joinToString$default((Object[]) bArr, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) ByteUtilsKt$toHex$1.INSTANCE, 30, (Object) null);
    }

    public static final String toHexLittleEndian(Byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "$receiver");
        ArraysKt.reverse((T[]) (Object[]) bArr);
        return toHex(bArr);
    }

    public static final String toHexBigEndian(Byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "$receiver");
        return toHex(bArr);
    }

    public static final String toHex(byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "$receiver");
        return toHex(ArraysKt.toTypedArray(bArr));
    }

    public static final String toHexLittleEndian(byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "$receiver");
        return toHexLittleEndian(ArraysKt.toTypedArray(bArr));
    }

    public static final String toHexBigEndian(byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "$receiver");
        return toHex(bArr);
    }

    public static final void writeAsByte(ByteArrayOutputStream byteArrayOutputStream, int i) {
        Intrinsics.checkParameterIsNotNull(byteArrayOutputStream, "$receiver");
        byteArrayOutputStream.write(i & 255);
    }

    public static final void writeAsShort(ByteArrayOutputStream byteArrayOutputStream, int i) {
        Intrinsics.checkParameterIsNotNull(byteArrayOutputStream, "$receiver");
        byteArrayOutputStream.write(i & 255);
        byteArrayOutputStream.write((i >> 8) & 255);
    }

    public static final void writeAs32bitHex(ByteArrayOutputStream byteArrayOutputStream, String str) {
        Intrinsics.checkParameterIsNotNull(byteArrayOutputStream, "$receiver");
        Intrinsics.checkParameterIsNotNull(str, "value");
        if (StringsKt.any(str)) {
            int parseInt = Integer.parseInt(str, CharsKt.checkRadix(16));
            byteArrayOutputStream.write(parseInt & 255);
            byteArrayOutputStream.write((parseInt >> 8) & 255);
            byteArrayOutputStream.write((parseInt >> 16) & 255);
            byteArrayOutputStream.write((parseInt >> 24) & 255);
            return;
        }
        Byte[] bArr = new Byte[4];
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) 0;
        }
        byteArrayOutputStream.write(ArraysKt.toByteArray(bArr));
    }

    public static final byte[] compress(byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "$receiver");
        return CompressionHelper.compress(bArr);
    }

    public static final byte[] decompress(byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "$receiver");
        return CompressionHelper.decompress(bArr);
    }

    public static final int withoutNull(byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "$receiver");
        Collection arrayList = new ArrayList();
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            byte b = bArr[i];
            if (b != 0) {
                arrayList.add(Byte.valueOf(b));
            }
        }
        return ((List) arrayList).size();
    }

    public static final void writeAsFixedLengthBuffer(ByteArrayOutputStream byteArrayOutputStream, String str, int i) {
        Intrinsics.checkParameterIsNotNull(byteArrayOutputStream, "$receiver");
        Intrinsics.checkParameterIsNotNull(str, "value");
        Byte[] bArr = new Byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) 0;
        }
        Iterator it = new IntRange(0, Math.min(str.length() - 1, 8)).iterator();
        while (it.hasNext()) {
            int nextInt = ((IntIterator) it).nextInt();
            bArr[nextInt] = Byte.valueOf((byte) str.charAt(nextInt));
        }
        byteArrayOutputStream.write(ArraysKt.toByteArray(bArr));
    }
}
