package com.zkteco.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class FileHelper {
    private FileHelper() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0030  */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copy(java.io.File r3, java.io.File r4) throws java.io.IOException {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ all -> 0x0027 }
            r1.<init>(r3)     // Catch:{ all -> 0x0027 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ all -> 0x0025 }
            r3.<init>(r4)     // Catch:{ all -> 0x0025 }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x0022 }
        L_0x000f:
            int r0 = r1.read(r4)     // Catch:{ all -> 0x0022 }
            if (r0 <= 0) goto L_0x001a
            r2 = 0
            r3.write(r4, r2, r0)     // Catch:{ all -> 0x0022 }
            goto L_0x000f
        L_0x001a:
            r4 = 1
            r3.close()
            r1.close()
            return r4
        L_0x0022:
            r4 = move-exception
            r0 = r3
            goto L_0x0029
        L_0x0025:
            r4 = move-exception
            goto L_0x0029
        L_0x0027:
            r4 = move-exception
            r1 = r0
        L_0x0029:
            if (r0 == 0) goto L_0x002e
            r0.close()
        L_0x002e:
            if (r1 == 0) goto L_0x0033
            r1.close()
        L_0x0033:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.util.FileHelper.copy(java.io.File, java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0033  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyFromJar(java.lang.String r3, java.io.File r4) throws java.io.IOException {
        /*
            r0 = 0
            java.lang.Class<com.zkteco.util.FileHelper> r1 = com.zkteco.util.FileHelper.class
            java.io.InputStream r3 = r1.getResourceAsStream(r3)     // Catch:{ all -> 0x002a }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0028 }
            r1.<init>(r4)     // Catch:{ all -> 0x0028 }
            r4 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x0025 }
        L_0x0010:
            int r0 = r3.read(r4)     // Catch:{ all -> 0x0025 }
            if (r0 <= 0) goto L_0x001b
            r2 = 0
            r1.write(r4, r2, r0)     // Catch:{ all -> 0x0025 }
            goto L_0x0010
        L_0x001b:
            r4 = 1
            r1.close()
            if (r3 == 0) goto L_0x0024
            r3.close()
        L_0x0024:
            return r4
        L_0x0025:
            r4 = move-exception
            r0 = r1
            goto L_0x002c
        L_0x0028:
            r4 = move-exception
            goto L_0x002c
        L_0x002a:
            r4 = move-exception
            r3 = r0
        L_0x002c:
            if (r0 == 0) goto L_0x0031
            r0.close()
        L_0x0031:
            if (r3 == 0) goto L_0x0036
            r3.close()
        L_0x0036:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.util.FileHelper.copyFromJar(java.lang.String, java.io.File):boolean");
    }

    @Deprecated
    public static boolean delete(File file) {
        return file.delete();
    }

    @Deprecated
    public static String getFileAsString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder(1000);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] cArr = new char[1024];
        while (true) {
            int read = bufferedReader.read(cArr);
            if (read != -1) {
                sb.append(String.valueOf(cArr, 0, read));
            } else {
                bufferedReader.close();
                return sb.toString();
            }
        }
    }

    @Deprecated
    public static String getFileAsString(String str) throws IOException {
        StringBuilder sb = new StringBuilder(1000);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
        char[] cArr = new char[1024];
        while (true) {
            int read = bufferedReader.read(cArr);
            if (read != -1) {
                sb.append(String.valueOf(cArr, 0, read));
            } else {
                bufferedReader.close();
                return sb.toString();
            }
        }
    }

    public static boolean isPathBelow(File file, File file2) {
        return file.compareTo(file2) <= 0;
    }
}
