package com.zkteco.liveface562.util;

import android.content.Context;

public class ZKFaceUtil {
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0027 A[SYNTHETIC, Splitter:B:20:0x0027] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0032 A[SYNTHETIC, Splitter:B:26:0x0032] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] readModel(android.content.res.AssetManager r1, java.lang.String r2) {
        /*
            r0 = 0
            java.io.InputStream r1 = r1.open(r2)     // Catch:{ IOException -> 0x0020, all -> 0x001e }
            int r2 = r1.available()     // Catch:{ IOException -> 0x001c }
            byte[] r2 = new byte[r2]     // Catch:{ IOException -> 0x001c }
            r1.read(r2)     // Catch:{ IOException -> 0x001c }
            if (r1 == 0) goto L_0x0018
            r1.close()     // Catch:{ IOException -> 0x0014 }
            goto L_0x0018
        L_0x0014:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0018:
            return r2
        L_0x0019:
            r2 = move-exception
            r0 = r1
            goto L_0x0030
        L_0x001c:
            r2 = move-exception
            goto L_0x0022
        L_0x001e:
            r2 = move-exception
            goto L_0x0030
        L_0x0020:
            r2 = move-exception
            r1 = r0
        L_0x0022:
            r2.printStackTrace()     // Catch:{ all -> 0x0019 }
            if (r1 == 0) goto L_0x002f
            r1.close()     // Catch:{ IOException -> 0x002b }
            goto L_0x002f
        L_0x002b:
            r1 = move-exception
            r1.printStackTrace()
        L_0x002f:
            return r0
        L_0x0030:
            if (r0 == 0) goto L_0x003a
            r0.close()     // Catch:{ IOException -> 0x0036 }
            goto L_0x003a
        L_0x0036:
            r1 = move-exception
            r1.printStackTrace()
        L_0x003a:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.liveface562.util.ZKFaceUtil.readModel(android.content.res.AssetManager, java.lang.String):byte[]");
    }

    public static byte[] readModel(Class<?> cls, Context context, String str) {
        return readFile(copyModuleIfNotExists(cls, context, str));
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0086 A[SYNTHETIC, Splitter:B:39:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0090 A[SYNTHETIC, Splitter:B:44:0x0090] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x009d A[SYNTHETIC, Splitter:B:52:0x009d] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00a7 A[SYNTHETIC, Splitter:B:57:0x00a7] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String copyModuleIfNotExists(java.lang.Class<?> r6, android.content.Context r7, java.lang.String r8) {
        /*
            java.io.File r0 = r7.getFileStreamPath(r8)
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x0019
            long r1 = r0.length()
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L_0x0019
            java.lang.String r6 = r0.getAbsolutePath()
            return r6
        L_0x0019:
            r1 = 0
            r0.delete()     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            r4.<init>()     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            java.lang.String r5 = "/"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            java.lang.StringBuilder r4 = r4.append(r8)     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            java.io.InputStream r6 = r6.getResourceAsStream(r4)     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            r3.<init>(r6)     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            r2.<init>(r3)     // Catch:{ IOException -> 0x007e, all -> 0x007b }
            java.io.DataOutputStream r6 = new java.io.DataOutputStream     // Catch:{ IOException -> 0x0078, all -> 0x0075 }
            java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0078, all -> 0x0075 }
            r4 = 0
            java.io.FileOutputStream r7 = r7.openFileOutput(r8, r4)     // Catch:{ IOException -> 0x0078, all -> 0x0075 }
            r3.<init>(r7)     // Catch:{ IOException -> 0x0078, all -> 0x0075 }
            r6.<init>(r3)     // Catch:{ IOException -> 0x0078, all -> 0x0075 }
            r7 = 4096(0x1000, float:5.74E-42)
            byte[] r7 = new byte[r7]     // Catch:{ IOException -> 0x0073 }
        L_0x0051:
            int r8 = r2.read(r7)     // Catch:{ IOException -> 0x0073 }
            if (r8 <= 0) goto L_0x005b
            r6.write(r7, r4, r8)     // Catch:{ IOException -> 0x0073 }
            goto L_0x0051
        L_0x005b:
            r6.flush()     // Catch:{ IOException -> 0x0073 }
            java.lang.String r7 = r0.getAbsolutePath()     // Catch:{ IOException -> 0x0073 }
            r2.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x006a
        L_0x0066:
            r8 = move-exception
            r8.printStackTrace()
        L_0x006a:
            r6.close()     // Catch:{ IOException -> 0x006e }
            goto L_0x0072
        L_0x006e:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0072:
            return r7
        L_0x0073:
            r7 = move-exception
            goto L_0x0081
        L_0x0075:
            r7 = move-exception
            r6 = r1
            goto L_0x009a
        L_0x0078:
            r7 = move-exception
            r6 = r1
            goto L_0x0081
        L_0x007b:
            r7 = move-exception
            r6 = r1
            goto L_0x009b
        L_0x007e:
            r7 = move-exception
            r6 = r1
            r2 = r6
        L_0x0081:
            r7.printStackTrace()     // Catch:{ all -> 0x0099 }
            if (r2 == 0) goto L_0x008e
            r2.close()     // Catch:{ IOException -> 0x008a }
            goto L_0x008e
        L_0x008a:
            r7 = move-exception
            r7.printStackTrace()
        L_0x008e:
            if (r6 == 0) goto L_0x0098
            r6.close()     // Catch:{ IOException -> 0x0094 }
            goto L_0x0098
        L_0x0094:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0098:
            return r1
        L_0x0099:
            r7 = move-exception
        L_0x009a:
            r1 = r2
        L_0x009b:
            if (r1 == 0) goto L_0x00a5
            r1.close()     // Catch:{ IOException -> 0x00a1 }
            goto L_0x00a5
        L_0x00a1:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00a5:
            if (r6 == 0) goto L_0x00af
            r6.close()     // Catch:{ IOException -> 0x00ab }
            goto L_0x00af
        L_0x00ab:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00af:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.liveface562.util.ZKFaceUtil.copyModuleIfNotExists(java.lang.Class, android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0062 A[SYNTHETIC, Splitter:B:41:0x0062] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x006c A[SYNTHETIC, Splitter:B:46:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0079 A[SYNTHETIC, Splitter:B:54:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0083 A[SYNTHETIC, Splitter:B:59:0x0083] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] readFile(java.lang.String r7) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            java.io.DataInputStream r0 = new java.io.DataInputStream     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            r3.<init>(r7)     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            r0.<init>(r2)     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            r7 = 4096(0x1000, float:5.74E-42)
            byte[] r2 = new byte[r7]     // Catch:{ IOException -> 0x0054, all -> 0x0052 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0054, all -> 0x0052 }
            r3.<init>()     // Catch:{ IOException -> 0x0054, all -> 0x0052 }
            r4 = 0
            r5 = r4
        L_0x0022:
            int r6 = r0.read(r2, r4, r7)     // Catch:{ IOException -> 0x0050 }
            if (r6 <= 0) goto L_0x002d
            int r5 = r5 + r6
            r3.write(r2, r4, r6)     // Catch:{ IOException -> 0x0050 }
            goto L_0x0022
        L_0x002d:
            if (r5 <= 0) goto L_0x0044
            byte[] r7 = r3.toByteArray()     // Catch:{ IOException -> 0x0050 }
            r3.close()     // Catch:{ IOException -> 0x0037 }
            goto L_0x003b
        L_0x0037:
            r1 = move-exception
            r1.printStackTrace()
        L_0x003b:
            r0.close()     // Catch:{ IOException -> 0x003f }
            goto L_0x0043
        L_0x003f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0043:
            return r7
        L_0x0044:
            r3.close()     // Catch:{ IOException -> 0x0048 }
            goto L_0x004c
        L_0x0048:
            r7 = move-exception
            r7.printStackTrace()
        L_0x004c:
            r0.close()     // Catch:{ IOException -> 0x0070 }
            goto L_0x0074
        L_0x0050:
            r7 = move-exception
            goto L_0x005d
        L_0x0052:
            r7 = move-exception
            goto L_0x0077
        L_0x0054:
            r7 = move-exception
            r3 = r1
            goto L_0x005d
        L_0x0057:
            r7 = move-exception
            r0 = r1
            goto L_0x0077
        L_0x005a:
            r7 = move-exception
            r0 = r1
            r3 = r0
        L_0x005d:
            r7.printStackTrace()     // Catch:{ all -> 0x0075 }
            if (r3 == 0) goto L_0x006a
            r3.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x006a
        L_0x0066:
            r7 = move-exception
            r7.printStackTrace()
        L_0x006a:
            if (r0 == 0) goto L_0x0074
            r0.close()     // Catch:{ IOException -> 0x0070 }
            goto L_0x0074
        L_0x0070:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0074:
            return r1
        L_0x0075:
            r7 = move-exception
            r1 = r3
        L_0x0077:
            if (r1 == 0) goto L_0x0081
            r1.close()     // Catch:{ IOException -> 0x007d }
            goto L_0x0081
        L_0x007d:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0081:
            if (r0 == 0) goto L_0x008b
            r0.close()     // Catch:{ IOException -> 0x0087 }
            goto L_0x008b
        L_0x0087:
            r0 = move-exception
            r0.printStackTrace()
        L_0x008b:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.liveface562.util.ZKFaceUtil.readFile(java.lang.String):byte[]");
    }
}
