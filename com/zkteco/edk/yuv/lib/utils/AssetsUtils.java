package com.zkteco.edk.yuv.lib.utils;

public class AssetsUtils {
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0053 A[SYNTHETIC, Splitter:B:32:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x005d A[SYNTHETIC, Splitter:B:37:0x005d] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x006b A[SYNTHETIC, Splitter:B:43:0x006b] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0075 A[SYNTHETIC, Splitter:B:48:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getFromAssets(android.content.Context r3, java.lang.String r4) {
        /*
            r0 = 0
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x004c, all -> 0x0049 }
            android.content.res.Resources r3 = r3.getResources()     // Catch:{ Exception -> 0x004c, all -> 0x0049 }
            android.content.res.AssetManager r3 = r3.getAssets()     // Catch:{ Exception -> 0x004c, all -> 0x0049 }
            java.io.InputStream r3 = r3.open(r4)     // Catch:{ Exception -> 0x004c, all -> 0x0049 }
            r1.<init>(r3)     // Catch:{ Exception -> 0x004c, all -> 0x0049 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0047 }
            r3.<init>(r1)     // Catch:{ Exception -> 0x0047 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            r4.<init>()     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
        L_0x001c:
            java.lang.String r0 = r3.readLine()     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            if (r0 == 0) goto L_0x002c
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            java.lang.String r2 = "\n"
            r0.append(r2)     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            goto L_0x001c
        L_0x002c:
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            r3.close()     // Catch:{ IOException -> 0x0034 }
            goto L_0x0038
        L_0x0034:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0038:
            r1.close()     // Catch:{ IOException -> 0x003c }
            goto L_0x0040
        L_0x003c:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0040:
            return r4
        L_0x0041:
            r4 = move-exception
            r0 = r3
            goto L_0x0069
        L_0x0044:
            r4 = move-exception
            r0 = r3
            goto L_0x004e
        L_0x0047:
            r4 = move-exception
            goto L_0x004e
        L_0x0049:
            r4 = move-exception
            r1 = r0
            goto L_0x0069
        L_0x004c:
            r4 = move-exception
            r1 = r0
        L_0x004e:
            r4.printStackTrace()     // Catch:{ all -> 0x0068 }
            if (r0 == 0) goto L_0x005b
            r0.close()     // Catch:{ IOException -> 0x0057 }
            goto L_0x005b
        L_0x0057:
            r3 = move-exception
            r3.printStackTrace()
        L_0x005b:
            if (r1 == 0) goto L_0x0065
            r1.close()     // Catch:{ IOException -> 0x0061 }
            goto L_0x0065
        L_0x0061:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0065:
            java.lang.String r3 = ""
            return r3
        L_0x0068:
            r4 = move-exception
        L_0x0069:
            if (r0 == 0) goto L_0x0073
            r0.close()     // Catch:{ IOException -> 0x006f }
            goto L_0x0073
        L_0x006f:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0073:
            if (r1 == 0) goto L_0x007d
            r1.close()     // Catch:{ IOException -> 0x0079 }
            goto L_0x007d
        L_0x0079:
            r3 = move-exception
            r3.printStackTrace()
        L_0x007d:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.edk.yuv.lib.utils.AssetsUtils.getFromAssets(android.content.Context, java.lang.String):java.lang.String");
    }
}
