package com.zkteco.edk.hardware.encrypt;

import android.os.Environment;
import android.text.TextUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

final class ZkFileLogUtils {
    private static final String SD_CARD_PATH;
    private static String mLogSavePath;

    ZkFileLogUtils() {
    }

    static {
        String file = Environment.getExternalStorageDirectory().toString();
        SD_CARD_PATH = file;
        mLogSavePath = file + "/ZKTeco/trace/";
    }

    public static void setLogSavePath(String str) {
        if (!TextUtils.isEmpty(str)) {
            File file = new File(str);
            if (file.exists() && file.isDirectory()) {
                mLogSavePath = str + "/trace/";
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a2 A[SYNTHETIC, Splitter:B:24:0x00a2] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00a8 A[Catch:{ Exception -> 0x00ac }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void saveLogRecord(java.lang.String r5, java.lang.String r6) {
        /*
            java.io.File r0 = new java.io.File
            java.lang.String r1 = mLogSavePath
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = ""
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r6 = r2.append(r6)
            java.lang.String r2 = ".txt"
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r6 = r6.toString()
            r0.<init>(r1, r6)
            boolean r6 = r0.exists()     // Catch:{ Exception -> 0x00ac }
            if (r6 != 0) goto L_0x0035
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x00ac }
            java.lang.String r1 = r0.getParent()     // Catch:{ Exception -> 0x00ac }
            r6.<init>(r1)     // Catch:{ Exception -> 0x00ac }
            r6.mkdirs()     // Catch:{ Exception -> 0x00ac }
            r0.createNewFile()     // Catch:{ Exception -> 0x00ac }
        L_0x0035:
            long r1 = r0.length()     // Catch:{ Exception -> 0x00ac }
            r3 = 3145728(0x300000, double:1.554196E-317)
            int r6 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r6 <= 0) goto L_0x0046
            r0.delete()     // Catch:{ Exception -> 0x00ac }
            r0.createNewFile()     // Catch:{ Exception -> 0x00ac }
        L_0x0046:
            r6 = 0
            java.util.Date r1 = new java.util.Date     // Catch:{ IOException -> 0x009c }
            r1.<init>()     // Catch:{ IOException -> 0x009c }
            java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat     // Catch:{ IOException -> 0x009c }
            java.lang.String r3 = "yyyy-MM-dd HH:mm:ss"
            r2.<init>(r3)     // Catch:{ IOException -> 0x009c }
            java.lang.String r1 = r2.format(r1)     // Catch:{ IOException -> 0x009c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x009c }
            r2.<init>()     // Catch:{ IOException -> 0x009c }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ IOException -> 0x009c }
            java.lang.String r2 = " "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ IOException -> 0x009c }
            java.lang.StringBuilder r5 = r1.append(r5)     // Catch:{ IOException -> 0x009c }
            java.lang.String r1 = "    "
            java.lang.StringBuilder r5 = r5.append(r1)     // Catch:{ IOException -> 0x009c }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x009c }
            java.io.FileWriter r1 = new java.io.FileWriter     // Catch:{ IOException -> 0x009c }
            r2 = 1
            r1.<init>(r0, r2)     // Catch:{ IOException -> 0x009c }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0097, all -> 0x0094 }
            r6.<init>()     // Catch:{ IOException -> 0x0097, all -> 0x0094 }
            java.lang.StringBuilder r5 = r6.append(r5)     // Catch:{ IOException -> 0x0097, all -> 0x0094 }
            java.lang.String r6 = "\n"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x0097, all -> 0x0094 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0097, all -> 0x0094 }
            r1.write(r5)     // Catch:{ IOException -> 0x0097, all -> 0x0094 }
            r1.close()     // Catch:{ Exception -> 0x00ac }
            goto L_0x00b0
        L_0x0094:
            r5 = move-exception
            r6 = r1
            goto L_0x00a6
        L_0x0097:
            r5 = move-exception
            r6 = r1
            goto L_0x009d
        L_0x009a:
            r5 = move-exception
            goto L_0x00a6
        L_0x009c:
            r5 = move-exception
        L_0x009d:
            r5.printStackTrace()     // Catch:{ all -> 0x009a }
            if (r6 == 0) goto L_0x00b0
            r6.close()     // Catch:{ Exception -> 0x00ac }
            goto L_0x00b0
        L_0x00a6:
            if (r6 == 0) goto L_0x00ab
            r6.close()     // Catch:{ Exception -> 0x00ac }
        L_0x00ab:
            throw r5     // Catch:{ Exception -> 0x00ac }
        L_0x00ac:
            r5 = move-exception
            r5.printStackTrace()
        L_0x00b0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.edk.hardware.encrypt.ZkFileLogUtils.saveLogRecord(java.lang.String, java.lang.String):void");
    }

    private static void methodBufferedWriter(File file, String str) {
        BufferedWriter bufferedWriter = null;
        try {
            BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            try {
                bufferedWriter2.write(str);
                try {
                    bufferedWriter2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e = e2;
                bufferedWriter = bufferedWriter2;
                try {
                    e.printStackTrace();
                    bufferedWriter.close();
                } catch (Throwable th) {
                    th = th;
                    try {
                        bufferedWriter.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedWriter = bufferedWriter2;
                bufferedWriter.close();
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            bufferedWriter.close();
        }
    }
}
