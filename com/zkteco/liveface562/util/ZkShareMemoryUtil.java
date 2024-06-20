package com.zkteco.liveface562.util;

import android.os.ParcelFileDescriptor;
import java.io.IOException;

public class ZkShareMemoryUtil {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: android.os.MemoryFile} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.os.ParcelFileDescriptor} */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005f A[SYNTHETIC, Splitter:B:27:0x005f] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006c A[SYNTHETIC, Splitter:B:35:0x006c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.os.ParcelFileDescriptor writeShareMemory(byte[] r5, int r6) {
        /*
            r0 = 0
            android.os.MemoryFile r1 = new android.os.MemoryFile     // Catch:{ IOException -> 0x0052, IllegalAccessException -> 0x0050, NoSuchMethodException -> 0x004e, InvocationTargetException -> 0x004c, all -> 0x0049 }
            java.lang.String r2 = "yuv"
            r1.<init>(r2, r6)     // Catch:{ IOException -> 0x0052, IllegalAccessException -> 0x0050, NoSuchMethodException -> 0x004e, InvocationTargetException -> 0x004c, all -> 0x0049 }
            java.io.OutputStream r6 = r1.getOutputStream()     // Catch:{ IOException -> 0x0046, IllegalAccessException -> 0x0044, NoSuchMethodException -> 0x0042, InvocationTargetException -> 0x0040, all -> 0x003d }
            int r2 = r5.length     // Catch:{ IOException -> 0x003b, IllegalAccessException -> 0x0039, NoSuchMethodException -> 0x0037, InvocationTargetException -> 0x0035 }
            r3 = 0
            r6.write(r5, r3, r2)     // Catch:{ IOException -> 0x003b, IllegalAccessException -> 0x0039, NoSuchMethodException -> 0x0037, InvocationTargetException -> 0x0035 }
            java.lang.Class<android.os.MemoryFile> r5 = android.os.MemoryFile.class
            java.lang.String r2 = "getFileDescriptor"
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ IOException -> 0x003b, IllegalAccessException -> 0x0039, NoSuchMethodException -> 0x0037, InvocationTargetException -> 0x0035 }
            java.lang.reflect.Method r5 = r5.getDeclaredMethod(r2, r4)     // Catch:{ IOException -> 0x003b, IllegalAccessException -> 0x0039, NoSuchMethodException -> 0x0037, InvocationTargetException -> 0x0035 }
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch:{ IOException -> 0x003b, IllegalAccessException -> 0x0039, NoSuchMethodException -> 0x0037, InvocationTargetException -> 0x0035 }
            java.lang.Object r5 = r5.invoke(r1, r2)     // Catch:{ IOException -> 0x003b, IllegalAccessException -> 0x0039, NoSuchMethodException -> 0x0037, InvocationTargetException -> 0x0035 }
            java.io.FileDescriptor r5 = (java.io.FileDescriptor) r5     // Catch:{ IOException -> 0x003b, IllegalAccessException -> 0x0039, NoSuchMethodException -> 0x0037, InvocationTargetException -> 0x0035 }
            android.os.ParcelFileDescriptor r0 = android.os.ParcelFileDescriptor.dup(r5)     // Catch:{ IOException -> 0x003b, IllegalAccessException -> 0x0039, NoSuchMethodException -> 0x0037, InvocationTargetException -> 0x0035 }
            r1.close()
            if (r6 == 0) goto L_0x0062
            r6.close()     // Catch:{ IOException -> 0x0030 }
            goto L_0x0062
        L_0x0030:
            r5 = move-exception
            r5.printStackTrace()
            goto L_0x0062
        L_0x0035:
            r5 = move-exception
            goto L_0x0055
        L_0x0037:
            r5 = move-exception
            goto L_0x0055
        L_0x0039:
            r5 = move-exception
            goto L_0x0055
        L_0x003b:
            r5 = move-exception
            goto L_0x0055
        L_0x003d:
            r5 = move-exception
            r6 = r0
            goto L_0x0064
        L_0x0040:
            r5 = move-exception
            goto L_0x0047
        L_0x0042:
            r5 = move-exception
            goto L_0x0047
        L_0x0044:
            r5 = move-exception
            goto L_0x0047
        L_0x0046:
            r5 = move-exception
        L_0x0047:
            r6 = r0
            goto L_0x0055
        L_0x0049:
            r5 = move-exception
            r6 = r0
            goto L_0x0065
        L_0x004c:
            r5 = move-exception
            goto L_0x0053
        L_0x004e:
            r5 = move-exception
            goto L_0x0053
        L_0x0050:
            r5 = move-exception
            goto L_0x0053
        L_0x0052:
            r5 = move-exception
        L_0x0053:
            r6 = r0
            r1 = r6
        L_0x0055:
            r5.printStackTrace()     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x005d
            r1.close()
        L_0x005d:
            if (r6 == 0) goto L_0x0062
            r6.close()     // Catch:{ IOException -> 0x0030 }
        L_0x0062:
            return r0
        L_0x0063:
            r5 = move-exception
        L_0x0064:
            r0 = r1
        L_0x0065:
            if (r0 == 0) goto L_0x006a
            r0.close()
        L_0x006a:
            if (r6 == 0) goto L_0x0074
            r6.close()     // Catch:{ IOException -> 0x0070 }
            goto L_0x0074
        L_0x0070:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0074:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.liveface562.util.ZkShareMemoryUtil.writeShareMemory(byte[], int):android.os.ParcelFileDescriptor");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0043 A[SYNTHETIC, Splitter:B:21:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0051 A[SYNTHETIC, Splitter:B:28:0x0051] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void readShareMemory(android.os.ParcelFileDescriptor r5, byte[] r6, int r7) {
        /*
            if (r5 != 0) goto L_0x0003
            return
        L_0x0003:
            java.io.FileDescriptor r0 = r5.getFileDescriptor()
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x003d }
            r2.<init>(r0)     // Catch:{ IOException -> 0x003d }
            int r6 = r2.read(r6)     // Catch:{ IOException -> 0x0038, all -> 0x0035 }
            if (r6 == r7) goto L_0x002f
            java.util.Locale r0 = java.util.Locale.US     // Catch:{ IOException -> 0x0038, all -> 0x0035 }
            java.lang.String r1 = "read share memory length not match,should read %d,actual read %d"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ IOException -> 0x0038, all -> 0x0035 }
            r4 = 0
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ IOException -> 0x0038, all -> 0x0035 }
            r3[r4] = r7     // Catch:{ IOException -> 0x0038, all -> 0x0035 }
            r7 = 1
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ IOException -> 0x0038, all -> 0x0035 }
            r3[r7] = r6     // Catch:{ IOException -> 0x0038, all -> 0x0035 }
            java.lang.String r6 = java.lang.String.format(r0, r1, r3)     // Catch:{ IOException -> 0x0038, all -> 0x0035 }
            com.zkteco.liveface562.util.ZkLogUtil.w(r6)     // Catch:{ IOException -> 0x0038, all -> 0x0035 }
        L_0x002f:
            r2.close()     // Catch:{ IOException -> 0x0033 }
            goto L_0x004b
        L_0x0033:
            r6 = move-exception
            goto L_0x0048
        L_0x0035:
            r6 = move-exception
            r1 = r2
            goto L_0x004f
        L_0x0038:
            r6 = move-exception
            r1 = r2
            goto L_0x003e
        L_0x003b:
            r6 = move-exception
            goto L_0x004f
        L_0x003d:
            r6 = move-exception
        L_0x003e:
            r6.printStackTrace()     // Catch:{ all -> 0x003b }
            if (r1 == 0) goto L_0x004b
            r1.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x004b
        L_0x0047:
            r6 = move-exception
        L_0x0048:
            r6.printStackTrace()
        L_0x004b:
            closePfd(r5)
            return
        L_0x004f:
            if (r1 == 0) goto L_0x0059
            r1.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x0059
        L_0x0055:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0059:
            closePfd(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.liveface562.util.ZkShareMemoryUtil.readShareMemory(android.os.ParcelFileDescriptor, byte[], int):void");
    }

    public static void closePfd(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
