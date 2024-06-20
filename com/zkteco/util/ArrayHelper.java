package com.zkteco.util;

import java.util.Iterator;
import java.util.List;

public final class ArrayHelper {
    private ArrayHelper() {
    }

    public static byte[] copy(byte[] bArr, byte[] bArr2, int i) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr2[i2 + i] = bArr[i2];
        }
        return bArr2;
    }

    public static <E> String concatenateList(List<E> list, E e) {
        StringBuilder sb = new StringBuilder();
        Iterator<E> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(e);
            }
        }
        return sb.toString();
    }
}
