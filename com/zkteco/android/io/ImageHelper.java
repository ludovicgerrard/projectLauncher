package com.zkteco.android.io;

import android.graphics.Bitmap;
import com.google.common.base.Ascii;

public final class ImageHelper {
    private static final String TAG = "ImageHelper";

    private ImageHelper() {
    }

    public static Bitmap renderCroppedGreyscaleBitmap(byte[] bArr, int i, int i2) {
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

    public static byte[] copyImage(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x003d A[SYNTHETIC, Splitter:B:18:0x003d] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0043 A[SYNTHETIC, Splitter:B:21:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void dumpBitmapToFile(android.graphics.Bitmap r3, java.lang.String r4) {
        /*
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x001e }
            r1.<init>(r4)     // Catch:{ FileNotFoundException -> 0x001e }
            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ FileNotFoundException -> 0x0019, all -> 0x0016 }
            r0 = 100
            r3.compress(r4, r0, r1)     // Catch:{ FileNotFoundException -> 0x0019, all -> 0x0016 }
            r1.close()     // Catch:{ IOException -> 0x0011 }
            goto L_0x0040
        L_0x0011:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0040
        L_0x0016:
            r3 = move-exception
            r0 = r1
            goto L_0x0041
        L_0x0019:
            r3 = move-exception
            r0 = r1
            goto L_0x001f
        L_0x001c:
            r3 = move-exception
            goto L_0x0041
        L_0x001e:
            r3 = move-exception
        L_0x001f:
            java.lang.String r4 = TAG     // Catch:{ all -> 0x001c }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x001c }
            r1.<init>()     // Catch:{ all -> 0x001c }
            java.lang.String r2 = "Could not save image to file\n"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x001c }
            java.lang.String r3 = android.util.Log.getStackTraceString(r3)     // Catch:{ all -> 0x001c }
            java.lang.StringBuilder r3 = r1.append(r3)     // Catch:{ all -> 0x001c }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x001c }
            android.util.Log.e(r4, r3)     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x0040
            r0.close()     // Catch:{ IOException -> 0x0011 }
        L_0x0040:
            return
        L_0x0041:
            if (r0 == 0) goto L_0x004b
            r0.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x004b
        L_0x0047:
            r4 = move-exception
            r4.printStackTrace()
        L_0x004b:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.io.ImageHelper.dumpBitmapToFile(android.graphics.Bitmap, java.lang.String):void");
    }

    public static void dumpBitmapToFile(byte[] bArr, int i, int i2, String str) {
        dumpBitmapToFile(renderCroppedGreyscaleBitmap(bArr, i, i2), str);
    }
}
