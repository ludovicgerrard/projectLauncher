package com.zktechnology.android.utils;

import android.graphics.Bitmap;
import com.google.common.base.Ascii;

public class ZkImageUtils {
    public static Bitmap renderCroppedGreyScaleBitmap(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        int[] iArr = new int[(i * i2)];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = i4 * i;
            for (int i6 = 0; i6 < i; i6++) {
                iArr[i5 + i6] = ((bArr[i3 + i6] & 255) * Ascii.SOH) | -16777216;
            }
            i3 += i;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.RGB_565);
        createBitmap.setPixels(iArr, 0, i, 0, 0, i, i2);
        return createBitmap;
    }
}
