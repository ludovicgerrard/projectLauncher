package com.zktechnology.android.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

public class BitmapHelper {
    public static byte[] floatArrayToByteArray(float[] fArr) {
        if (fArr == null) {
            return null;
        }
        byte[] bArr = new byte[(fArr.length * 4)];
        ByteBuffer.wrap(bArr).asFloatBuffer().put(fArr);
        return bArr;
    }

    public static float[] byteArrayToFloatArray(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        float[] fArr = new float[(bArr.length / 4)];
        ByteBuffer.wrap(bArr).asFloatBuffer().get(fArr);
        return fArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00ca A[LOOP:0: B:15:0x00bf->B:17:0x00ca, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void compressPhoto(android.graphics.Bitmap r7, java.lang.String r8) {
        /*
            int r0 = r7.getWidth()
            int r1 = r7.getHeight()
            r2 = 1
            if (r0 <= r1) goto L_0x001e
            int r0 = r7.getWidth()
            float r0 = (float) r0
            r1 = 1156579328(0x44f00000, float:1920.0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x001e
            int r0 = r7.getWidth()
        L_0x001a:
            float r0 = (float) r0
            float r0 = r0 / r1
            int r0 = (int) r0
            goto L_0x0039
        L_0x001e:
            int r0 = r7.getWidth()
            int r1 = r7.getHeight()
            if (r0 >= r1) goto L_0x0038
            int r0 = r7.getHeight()
            float r0 = (float) r0
            r1 = 1149698048(0x44870000, float:1080.0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x0038
            int r0 = r7.getHeight()
            goto L_0x001a
        L_0x0038:
            r0 = r2
        L_0x0039:
            if (r0 > 0) goto L_0x003c
            goto L_0x003d
        L_0x003c:
            r2 = r0
        L_0x003d:
            int r0 = r7.getWidth()
            int r0 = r0 / r2
            int r1 = r7.getHeight()
            int r1 = r1 / r2
            android.graphics.Bitmap$Config r3 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r0, r1, r3)
            android.graphics.Canvas r1 = new android.graphics.Canvas
            r1.<init>(r0)
            android.graphics.Rect r3 = new android.graphics.Rect
            int r4 = r7.getWidth()
            int r4 = r4 / r2
            int r5 = r7.getHeight()
            int r5 = r5 / r2
            r2 = 0
            r3.<init>(r2, r2, r4, r5)
            r2 = 0
            r1.drawBitmap(r7, r2, r3, r2)
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream
            r7.<init>()
            r1 = 65
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG
            r0.compress(r2, r1, r7)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "总内存:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()
            long r3 = r3.maxMemory()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = "\t已用内存:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()
            long r3 = r3.totalMemory()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = "\t剩余内存:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()
            long r3 = r3.maxMemory()
            java.lang.Runtime r5 = java.lang.Runtime.getRuntime()
            long r5 = r5.totalMemory()
            long r3 = r3 - r5
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "OOM"
            android.util.Log.d(r3, r2)
        L_0x00bf:
            byte[] r2 = r7.toByteArray()
            int r2 = r2.length
            int r2 = r2 / 1024
            r3 = 255(0xff, float:3.57E-43)
            if (r2 <= r3) goto L_0x00d5
            r7.reset()
            int r1 = r1 + -5
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG
            r0.compress(r2, r1, r7)
            goto L_0x00bf
        L_0x00d5:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00ed }
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x00ed }
            r1.<init>(r8)     // Catch:{ Exception -> 0x00ed }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00ed }
            byte[] r8 = r7.toByteArray()     // Catch:{ Exception -> 0x00ed }
            r0.write(r8)     // Catch:{ Exception -> 0x00ed }
            r0.flush()     // Catch:{ Exception -> 0x00ed }
            r0.close()     // Catch:{ Exception -> 0x00ed }
            goto L_0x00f1
        L_0x00ed:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00f1:
            r7.close()     // Catch:{ IOException -> 0x00f5 }
            goto L_0x00f9
        L_0x00f5:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00f9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.BitmapHelper.compressPhoto(android.graphics.Bitmap, java.lang.String):void");
    }

    public static Bitmap getBitmap(byte[] bArr) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inSampleSize = 1;
        options.inPurgeable = true;
        options.inInputShareable = true;
        if (bArr == null) {
            return null;
        }
        try {
            return BitmapFactory.decodeStream(new ByteArrayInputStream(bArr), (Rect) null, options);
        } catch (OutOfMemoryError e) {
            Log.e("ContentValues", Log.getStackTraceString(e));
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0038 A[SYNTHETIC, Splitter:B:25:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0042 A[SYNTHETIC, Splitter:B:30:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x004e A[SYNTHETIC, Splitter:B:36:0x004e] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0058 A[SYNTHETIC, Splitter:B:41:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeBitmap(java.lang.String r2, android.graphics.Bitmap r3, int r4) {
        /*
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x002c }
            r2.<init>(r1)     // Catch:{ Exception -> 0x002c }
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x0029, all -> 0x0026 }
            r3.compress(r0, r4, r2)     // Catch:{ Exception -> 0x0029, all -> 0x0026 }
            r2.flush()     // Catch:{ Exception -> 0x0029, all -> 0x0026 }
            java.io.FileDescriptor r3 = r1.getFD()     // Catch:{ Exception -> 0x0029, all -> 0x0026 }
            r3.sync()     // Catch:{ Exception -> 0x0029, all -> 0x0026 }
            r2.close()     // Catch:{ IOException -> 0x001e }
            goto L_0x0022
        L_0x001e:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0022:
            r1.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x004a
        L_0x0026:
            r3 = move-exception
            r0 = r2
            goto L_0x004c
        L_0x0029:
            r3 = move-exception
            r0 = r2
            goto L_0x0033
        L_0x002c:
            r3 = move-exception
            goto L_0x0033
        L_0x002e:
            r3 = move-exception
            r1 = r0
            goto L_0x004c
        L_0x0031:
            r3 = move-exception
            r1 = r0
        L_0x0033:
            r3.printStackTrace()     // Catch:{ all -> 0x004b }
            if (r0 == 0) goto L_0x0040
            r0.close()     // Catch:{ IOException -> 0x003c }
            goto L_0x0040
        L_0x003c:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0040:
            if (r1 == 0) goto L_0x004a
            r1.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x004a
        L_0x0046:
            r2 = move-exception
            r2.printStackTrace()
        L_0x004a:
            return
        L_0x004b:
            r3 = move-exception
        L_0x004c:
            if (r0 == 0) goto L_0x0056
            r0.close()     // Catch:{ IOException -> 0x0052 }
            goto L_0x0056
        L_0x0052:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0056:
            if (r1 == 0) goto L_0x0060
            r1.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x0060
        L_0x005c:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0060:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.BitmapHelper.writeBitmap(java.lang.String, android.graphics.Bitmap, int):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.graphics.Bitmap} */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0049 A[SYNTHETIC, Splitter:B:20:0x0049] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0051 A[SYNTHETIC, Splitter:B:26:0x0051] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap nv21ToBitmap(byte[] r8, int r9, int r10) {
        /*
            r0 = 0
            if (r8 == 0) goto L_0x005a
            int r1 = r8.length
            if (r1 > 0) goto L_0x0007
            goto L_0x005a
        L_0x0007:
            android.graphics.YuvImage r1 = new android.graphics.YuvImage     // Catch:{ Exception -> 0x003c, all -> 0x003a }
            r4 = 17
            r7 = 0
            r2 = r1
            r3 = r8
            r5 = r9
            r6 = r10
            r2.<init>(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x003c, all -> 0x003a }
            java.io.ByteArrayOutputStream r8 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x003c, all -> 0x003a }
            r8.<init>()     // Catch:{ Exception -> 0x003c, all -> 0x003a }
            android.graphics.Rect r2 = new android.graphics.Rect     // Catch:{ Exception -> 0x0038 }
            r3 = 0
            r2.<init>(r3, r3, r9, r10)     // Catch:{ Exception -> 0x0038 }
            r9 = 80
            r1.compressToJpeg(r2, r9, r8)     // Catch:{ Exception -> 0x0038 }
            byte[] r9 = r8.toByteArray()     // Catch:{ Exception -> 0x0038 }
            int r10 = r8.size()     // Catch:{ Exception -> 0x0038 }
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeByteArray(r9, r3, r10)     // Catch:{ Exception -> 0x0038 }
            r8.close()     // Catch:{ IOException -> 0x0033 }
            goto L_0x004c
        L_0x0033:
            r8 = move-exception
            r8.printStackTrace()
            goto L_0x004c
        L_0x0038:
            r9 = move-exception
            goto L_0x003e
        L_0x003a:
            r9 = move-exception
            goto L_0x004f
        L_0x003c:
            r9 = move-exception
            r8 = r0
        L_0x003e:
            java.lang.String r10 = "ContentValues"
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x004d }
            android.util.Log.e(r10, r9)     // Catch:{ all -> 0x004d }
            if (r8 == 0) goto L_0x004c
            r8.close()     // Catch:{ IOException -> 0x0033 }
        L_0x004c:
            return r0
        L_0x004d:
            r9 = move-exception
            r0 = r8
        L_0x004f:
            if (r0 == 0) goto L_0x0059
            r0.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x0059
        L_0x0055:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0059:
            throw r9
        L_0x005a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.BitmapHelper.nv21ToBitmap(byte[], int, int):android.graphics.Bitmap");
    }
}
