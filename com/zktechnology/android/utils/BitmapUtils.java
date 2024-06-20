package com.zktechnology.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BitmapUtils {
    public static Bitmap getBitmapByUri(Uri uri, Context context) {
        String filePathFromContentUri = getFilePathFromContentUri(uri, context.getContentResolver());
        if (filePathFromContentUri == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = computeSampleSize(options, -1, 565248);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePathFromContentUri, options);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002c, code lost:
        if (r7 != null) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002e, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0038, code lost:
        if (r7 == null) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003b, code lost:
        return r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getFilePathFromContentUri(android.net.Uri r7, android.content.ContentResolver r8) {
        /*
            java.lang.String r0 = "_data"
            java.lang.String[] r0 = new java.lang.String[]{r0}
            r4 = 0
            r5 = 0
            r6 = 0
            r1 = r8
            r2 = r7
            r3 = r0
            android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)
            r8 = 0
            r7.moveToFirst()     // Catch:{ Exception -> 0x0034 }
            r1 = 0
            boolean r2 = r7.moveToPosition(r1)     // Catch:{ Exception -> 0x0034 }
            r3 = 1
            if (r2 == r3) goto L_0x0022
            if (r7 == 0) goto L_0x0021
            r7.close()
        L_0x0021:
            return r8
        L_0x0022:
            r0 = r0[r1]     // Catch:{ Exception -> 0x0034 }
            int r0 = r7.getColumnIndex(r0)     // Catch:{ Exception -> 0x0034 }
            java.lang.String r8 = r7.getString(r0)     // Catch:{ Exception -> 0x0034 }
            if (r7 == 0) goto L_0x003b
        L_0x002e:
            r7.close()
            goto L_0x003b
        L_0x0032:
            r8 = move-exception
            goto L_0x003c
        L_0x0034:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0032 }
            if (r7 == 0) goto L_0x003b
            goto L_0x002e
        L_0x003b:
            return r8
        L_0x003c:
            if (r7 == 0) goto L_0x0041
            r7.close()
        L_0x0041:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.BitmapUtils.getFilePathFromContentUri(android.net.Uri, android.content.ContentResolver):java.lang.String");
    }

    private static int computeSampleSize(BitmapFactory.Options options, int i, int i2) {
        int computeInitialSampleSize = computeInitialSampleSize(options, i, i2);
        if (computeInitialSampleSize > 8) {
            return 8 * ((computeInitialSampleSize + 7) / 8);
        }
        int i3 = 1;
        while (i3 < computeInitialSampleSize) {
            i3 <<= 1;
        }
        return i3;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3;
        int i4;
        double d = (double) options.outWidth;
        double d2 = (double) options.outHeight;
        if (i2 == -1) {
            i3 = 1;
        } else {
            i3 = (int) Math.ceil(Math.sqrt((d * d2) / ((double) i2)));
        }
        if (i == -1) {
            i4 = 128;
        } else {
            double d3 = (double) i;
            i4 = (int) Math.min(Math.floor(d / d3), Math.floor(d2 / d3));
        }
        if (i4 < i3) {
            return i3;
        }
        if (i2 == -1 && i == -1) {
            return 1;
        }
        return i == -1 ? i3 : i4;
    }

    public static byte[] getUserPhoto(String str) {
        File file = new File(ZKFilePath.PICTURE_PATH + str + ZKFilePath.SUFFIX_IMAGE);
        if (!file.exists()) {
            return null;
        }
        int length = (int) file.length();
        byte[] bArr = new byte[length];
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedInputStream.read(bArr, 0, length);
            bufferedInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return ZKLauncher.zkDataEncdec == 1 ? AES256Util.decrypt(bArr, ZKLauncher.PUBLIC_KEY, ZKLauncher.iv) : bArr;
    }

    public static byte[] getUserTempPhoto(String str) {
        File file = new File(ZKFilePath.IMAGE_PATH + "temp/" + str + ZKFilePath.SUFFIX_IMAGE);
        if (!file.exists()) {
            return null;
        }
        int length = (int) file.length();
        byte[] bArr = new byte[length];
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedInputStream.read(bArr, 0, length);
            bufferedInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bArr;
    }

    public static synchronized Bitmap cropAvatar(Bitmap bitmap, Rect rect) {
        Bitmap bitmap2;
        synchronized (BitmapUtils.class) {
            bitmap2 = null;
            if (!(bitmap == null || rect == null)) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int i = rect.bottom - rect.top;
                int i2 = i / 3;
                rect.bottom = Math.min(height, rect.bottom + i2);
                rect.left = Math.max(0, rect.left - i2);
                rect.right = Math.min(width, rect.right + i2);
                rect.top = Math.max(0, (rect.top - (i / 2)) - i2);
                int i3 = rect.right - rect.left;
                int i4 = rect.bottom - rect.top;
                try {
                    if (!bitmap.isRecycled()) {
                        bitmap2 = Bitmap.createBitmap(bitmap, rect.left, rect.top, i3, i4);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError e2) {
                    e2.printStackTrace();
                }
            }
        }
        return bitmap2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x003c A[SYNTHETIC, Splitter:B:28:0x003c] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0046 A[SYNTHETIC, Splitter:B:33:0x0046] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0052 A[SYNTHETIC, Splitter:B:39:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x005c A[SYNTHETIC, Splitter:B:44:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeBitmap(java.lang.String r2, android.graphics.Bitmap r3, int r4) {
        /*
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0035, all -> 0x0032 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0035, all -> 0x0032 }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x0030 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0030 }
            r0 = 100
            if (r4 == r0) goto L_0x0014
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x002d, all -> 0x002a }
            r3.compress(r0, r4, r2)     // Catch:{ Exception -> 0x002d, all -> 0x002a }
        L_0x0014:
            r2.flush()     // Catch:{ Exception -> 0x002d, all -> 0x002a }
            java.io.FileDescriptor r3 = r1.getFD()     // Catch:{ Exception -> 0x002d, all -> 0x002a }
            r3.sync()     // Catch:{ Exception -> 0x002d, all -> 0x002a }
            r2.close()     // Catch:{ IOException -> 0x0022 }
            goto L_0x0026
        L_0x0022:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0026:
            r1.close()     // Catch:{ IOException -> 0x004a }
            goto L_0x004e
        L_0x002a:
            r3 = move-exception
            r0 = r2
            goto L_0x0050
        L_0x002d:
            r3 = move-exception
            r0 = r2
            goto L_0x0037
        L_0x0030:
            r3 = move-exception
            goto L_0x0037
        L_0x0032:
            r3 = move-exception
            r1 = r0
            goto L_0x0050
        L_0x0035:
            r3 = move-exception
            r1 = r0
        L_0x0037:
            r3.printStackTrace()     // Catch:{ all -> 0x004f }
            if (r0 == 0) goto L_0x0044
            r0.close()     // Catch:{ IOException -> 0x0040 }
            goto L_0x0044
        L_0x0040:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0044:
            if (r1 == 0) goto L_0x004e
            r1.close()     // Catch:{ IOException -> 0x004a }
            goto L_0x004e
        L_0x004a:
            r2 = move-exception
            r2.printStackTrace()
        L_0x004e:
            return
        L_0x004f:
            r3 = move-exception
        L_0x0050:
            if (r0 == 0) goto L_0x005a
            r0.close()     // Catch:{ IOException -> 0x0056 }
            goto L_0x005a
        L_0x0056:
            r2 = move-exception
            r2.printStackTrace()
        L_0x005a:
            if (r1 == 0) goto L_0x0064
            r1.close()     // Catch:{ IOException -> 0x0060 }
            goto L_0x0064
        L_0x0060:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0064:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.BitmapUtils.writeBitmap(java.lang.String, android.graphics.Bitmap, int):void");
    }
}
