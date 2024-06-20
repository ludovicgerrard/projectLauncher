package com.zkteco.android.zkcore.utils;

public class ZKJpegUtil {
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00c9 A[LOOP:0: B:15:0x00c0->B:17:0x00c9, LOOP_END] */
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
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG
            r2 = 30
            r0.compress(r1, r2, r7)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "总内存:"
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()
            long r3 = r3.maxMemory()
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = "\t已用内存:"
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()
            long r3 = r3.totalMemory()
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = "\t剩余内存:"
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()
            long r3 = r3.maxMemory()
            java.lang.Runtime r5 = java.lang.Runtime.getRuntime()
            long r5 = r5.totalMemory()
            long r3 = r3 - r5
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.String r3 = "OOM"
            android.util.Log.d(r3, r1)
            r1 = r2
        L_0x00c0:
            byte[] r3 = r7.toByteArray()
            int r3 = r3.length
            int r3 = r3 / 1024
            if (r3 <= r2) goto L_0x00d4
            r7.reset()
            int r1 = r1 + -5
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.JPEG
            r0.compress(r3, r1, r7)
            goto L_0x00c0
        L_0x00d4:
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00ec }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00ec }
            r2.<init>(r8)     // Catch:{ Exception -> 0x00ec }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00ec }
            byte[] r8 = r7.toByteArray()     // Catch:{ Exception -> 0x00ec }
            r1.write(r8)     // Catch:{ Exception -> 0x00ec }
            r1.flush()     // Catch:{ Exception -> 0x00ec }
            r1.close()     // Catch:{ Exception -> 0x00ec }
            goto L_0x00f0
        L_0x00ec:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00f0:
            r7.close()     // Catch:{ IOException -> 0x00f4 }
            goto L_0x00f8
        L_0x00f4:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00f8:
            r0.recycle()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.zkcore.utils.ZKJpegUtil.compressPhoto(android.graphics.Bitmap, java.lang.String):void");
    }
}
