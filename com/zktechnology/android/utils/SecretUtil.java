package com.zktechnology.android.utils;

import android.os.Environment;

public class SecretUtil {
    public static final String path = (Environment.getExternalStorageDirectory().toString() + "/.secret.dat");

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0072 A[SYNTHETIC, Splitter:B:34:0x0072] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x007f A[SYNTHETIC, Splitter:B:41:0x007f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] getPublicKey() {
        /*
            java.io.File r0 = new java.io.File
            java.lang.String r1 = path
            r0.<init>(r1)
            boolean r1 = r0.exists()
            r2 = 0
            if (r1 == 0) goto L_0x0088
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0068, all -> 0x0066 }
            r1.<init>(r0)     // Catch:{ Exception -> 0x0068, all -> 0x0066 }
            r3 = 80
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0064 }
            int r4 = r1.read(r3)     // Catch:{ Exception -> 0x0064 }
            r5 = -1
            if (r4 != r5) goto L_0x0022
            r1.close()     // Catch:{ IOException -> 0x0076 }
            goto L_0x0088
        L_0x0022:
            r4 = 16
            byte[] r5 = new byte[r4]     // Catch:{ Exception -> 0x0064 }
            r6 = 32
            byte[] r7 = new byte[r6]     // Catch:{ Exception -> 0x0064 }
            byte[] r8 = new byte[r4]     // Catch:{ Exception -> 0x0064 }
            byte[] r9 = new byte[r4]     // Catch:{ Exception -> 0x0064 }
            r10 = 0
            java.lang.System.arraycopy(r3, r10, r5, r10, r4)     // Catch:{ Exception -> 0x0064 }
            java.lang.System.arraycopy(r3, r4, r7, r10, r6)     // Catch:{ Exception -> 0x0064 }
            r6 = 48
            java.lang.System.arraycopy(r3, r6, r8, r10, r4)     // Catch:{ Exception -> 0x0064 }
            r6 = 64
            java.lang.System.arraycopy(r3, r6, r9, r10, r4)     // Catch:{ Exception -> 0x0064 }
            java.lang.String r3 = "MD5"
            java.security.MessageDigest r3 = java.security.MessageDigest.getInstance(r3)     // Catch:{ Exception -> 0x0064 }
            byte[] r3 = r3.digest(r7)     // Catch:{ Exception -> 0x0064 }
            boolean r3 = java.util.Arrays.equals(r5, r3)     // Catch:{ Exception -> 0x0064 }
            if (r3 == 0) goto L_0x0058
            r1.close()     // Catch:{ IOException -> 0x0053 }
            goto L_0x0057
        L_0x0053:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0057:
            return r7
        L_0x0058:
            r0.delete()     // Catch:{ Exception -> 0x0064 }
            r1.close()     // Catch:{ IOException -> 0x005f }
            goto L_0x0063
        L_0x005f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0063:
            return r2
        L_0x0064:
            r3 = move-exception
            goto L_0x006a
        L_0x0066:
            r0 = move-exception
            goto L_0x007d
        L_0x0068:
            r3 = move-exception
            r1 = r2
        L_0x006a:
            r3.getStackTrace()     // Catch:{ all -> 0x007b }
            r0.delete()     // Catch:{ all -> 0x007b }
            if (r1 == 0) goto L_0x0088
            r1.close()     // Catch:{ IOException -> 0x0076 }
            goto L_0x0088
        L_0x0076:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0088
        L_0x007b:
            r0 = move-exception
            r2 = r1
        L_0x007d:
            if (r2 == 0) goto L_0x0087
            r2.close()     // Catch:{ IOException -> 0x0083 }
            goto L_0x0087
        L_0x0083:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0087:
            throw r0
        L_0x0088:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.SecretUtil.getPublicKey():byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x006f A[SYNTHETIC, Splitter:B:34:0x006f] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x007c A[SYNTHETIC, Splitter:B:41:0x007c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] getIv() {
        /*
            java.io.File r0 = new java.io.File
            java.lang.String r1 = path
            r0.<init>(r1)
            boolean r1 = r0.exists()
            r2 = 0
            if (r1 == 0) goto L_0x0085
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0068, all -> 0x0066 }
            r1.<init>(r0)     // Catch:{ Exception -> 0x0068, all -> 0x0066 }
            r3 = 80
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0064 }
            int r4 = r1.read(r3)     // Catch:{ Exception -> 0x0064 }
            r5 = -1
            if (r4 != r5) goto L_0x0022
            r1.close()     // Catch:{ IOException -> 0x0073 }
            goto L_0x0085
        L_0x0022:
            r4 = 16
            byte[] r5 = new byte[r4]     // Catch:{ Exception -> 0x0064 }
            r6 = 32
            byte[] r7 = new byte[r6]     // Catch:{ Exception -> 0x0064 }
            byte[] r8 = new byte[r4]     // Catch:{ Exception -> 0x0064 }
            byte[] r9 = new byte[r4]     // Catch:{ Exception -> 0x0064 }
            r10 = 0
            java.lang.System.arraycopy(r3, r10, r5, r10, r4)     // Catch:{ Exception -> 0x0064 }
            java.lang.System.arraycopy(r3, r4, r7, r10, r6)     // Catch:{ Exception -> 0x0064 }
            r5 = 48
            java.lang.System.arraycopy(r3, r5, r8, r10, r4)     // Catch:{ Exception -> 0x0064 }
            r5 = 64
            java.lang.System.arraycopy(r3, r5, r9, r10, r4)     // Catch:{ Exception -> 0x0064 }
            java.lang.String r3 = "MD5"
            java.security.MessageDigest r3 = java.security.MessageDigest.getInstance(r3)     // Catch:{ Exception -> 0x0064 }
            byte[] r3 = r3.digest(r9)     // Catch:{ Exception -> 0x0064 }
            boolean r3 = java.util.Arrays.equals(r8, r3)     // Catch:{ Exception -> 0x0064 }
            if (r3 == 0) goto L_0x0058
            r1.close()     // Catch:{ IOException -> 0x0053 }
            goto L_0x0057
        L_0x0053:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0057:
            return r9
        L_0x0058:
            r0.delete()     // Catch:{ Exception -> 0x0064 }
            r1.close()     // Catch:{ IOException -> 0x005f }
            goto L_0x0063
        L_0x005f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0063:
            return r2
        L_0x0064:
            r0 = move-exception
            goto L_0x006a
        L_0x0066:
            r0 = move-exception
            goto L_0x007a
        L_0x0068:
            r0 = move-exception
            r1 = r2
        L_0x006a:
            r0.getStackTrace()     // Catch:{ all -> 0x0078 }
            if (r1 == 0) goto L_0x0085
            r1.close()     // Catch:{ IOException -> 0x0073 }
            goto L_0x0085
        L_0x0073:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0085
        L_0x0078:
            r0 = move-exception
            r2 = r1
        L_0x007a:
            if (r2 == 0) goto L_0x0084
            r2.close()     // Catch:{ IOException -> 0x0080 }
            goto L_0x0084
        L_0x0080:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0084:
            throw r0
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.SecretUtil.getIv():byte[]");
    }
}
