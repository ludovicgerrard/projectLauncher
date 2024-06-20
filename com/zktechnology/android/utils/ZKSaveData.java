package com.zktechnology.android.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class ZKSaveData {
    public static void deleteFile(String str) {
        File file = new File(str);
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0038 A[SYNTHETIC, Splitter:B:21:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0043 A[SYNTHETIC, Splitter:B:27:0x0043] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int writeStringData(java.util.List<java.lang.String> r4, java.lang.String r5) {
        /*
            r0 = 1
            r1 = 0
            java.io.BufferedWriter r2 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x0032 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ IOException -> 0x0032 }
            r3.<init>(r5, r0)     // Catch:{ IOException -> 0x0032 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0032 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ IOException -> 0x002d, all -> 0x002a }
        L_0x0010:
            boolean r5 = r4.hasNext()     // Catch:{ IOException -> 0x002d, all -> 0x002a }
            if (r5 == 0) goto L_0x0020
            java.lang.Object r5 = r4.next()     // Catch:{ IOException -> 0x002d, all -> 0x002a }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ IOException -> 0x002d, all -> 0x002a }
            r2.write(r5)     // Catch:{ IOException -> 0x002d, all -> 0x002a }
            goto L_0x0010
        L_0x0020:
            r2.close()     // Catch:{ IOException -> 0x0024 }
            goto L_0x0028
        L_0x0024:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0028:
            r4 = 0
            return r4
        L_0x002a:
            r4 = move-exception
            r1 = r2
            goto L_0x0041
        L_0x002d:
            r4 = move-exception
            r1 = r2
            goto L_0x0033
        L_0x0030:
            r4 = move-exception
            goto L_0x0041
        L_0x0032:
            r4 = move-exception
        L_0x0033:
            r4.printStackTrace()     // Catch:{ all -> 0x0030 }
            if (r1 == 0) goto L_0x0040
            r1.close()     // Catch:{ IOException -> 0x003c }
            goto L_0x0040
        L_0x003c:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0040:
            return r0
        L_0x0041:
            if (r1 == 0) goto L_0x004b
            r1.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x004b
        L_0x0047:
            r5 = move-exception
            r5.printStackTrace()
        L_0x004b:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.ZKSaveData.writeStringData(java.util.List, java.lang.String):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x003c A[SYNTHETIC, Splitter:B:22:0x003c] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0047 A[SYNTHETIC, Splitter:B:28:0x0047] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int writeByteData(java.util.List<byte[]> r4, java.lang.String r5) {
        /*
            r0 = 0
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0035 }
            java.lang.String r2 = "rw"
            r1.<init>(r5, r2)     // Catch:{ Exception -> 0x0035 }
            long r2 = r1.length()     // Catch:{ Exception -> 0x0030, all -> 0x002d }
            r1.seek(r2)     // Catch:{ Exception -> 0x0030, all -> 0x002d }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ Exception -> 0x0030, all -> 0x002d }
        L_0x0013:
            boolean r5 = r4.hasNext()     // Catch:{ Exception -> 0x0030, all -> 0x002d }
            if (r5 == 0) goto L_0x0023
            java.lang.Object r5 = r4.next()     // Catch:{ Exception -> 0x0030, all -> 0x002d }
            byte[] r5 = (byte[]) r5     // Catch:{ Exception -> 0x0030, all -> 0x002d }
            r1.write(r5)     // Catch:{ Exception -> 0x0030, all -> 0x002d }
            goto L_0x0013
        L_0x0023:
            r1.close()     // Catch:{ Exception -> 0x0027 }
            goto L_0x002b
        L_0x0027:
            r4 = move-exception
            r4.printStackTrace()
        L_0x002b:
            r4 = 0
            return r4
        L_0x002d:
            r4 = move-exception
            r0 = r1
            goto L_0x0045
        L_0x0030:
            r4 = move-exception
            r0 = r1
            goto L_0x0036
        L_0x0033:
            r4 = move-exception
            goto L_0x0045
        L_0x0035:
            r4 = move-exception
        L_0x0036:
            r4.printStackTrace()     // Catch:{ all -> 0x0033 }
            r4 = 1
            if (r0 == 0) goto L_0x0044
            r0.close()     // Catch:{ Exception -> 0x0040 }
            goto L_0x0044
        L_0x0040:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0044:
            return r4
        L_0x0045:
            if (r0 == 0) goto L_0x004f
            r0.close()     // Catch:{ Exception -> 0x004b }
            goto L_0x004f
        L_0x004b:
            r5 = move-exception
            r5.printStackTrace()
        L_0x004f:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.ZKSaveData.writeByteData(java.util.List, java.lang.String):int");
    }

    public static byte[] getWorkCodeTextSta() {
        byte[] bArr = new byte[0];
        try {
            bArr = ("ZK WORK CODE FORMAT " + "1.0.0.0").getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        allocate.put(bArr);
        return allocate.array();
    }
}
