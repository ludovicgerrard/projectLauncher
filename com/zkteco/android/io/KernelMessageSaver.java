package com.zkteco.android.io;

import android.app.Activity;

public class KernelMessageSaver implements Runnable {
    private static final String TAG = "com.zkteco.android.io.KernelMessageSaver";
    private final Activity activity;

    public KernelMessageSaver(Activity activity2) {
        this.activity = activity2;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:14|15|16|17|29) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0044, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        android.util.Log.e(TAG, "Error reading dmesg");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r2.close();
        r3.close();
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0061, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0062, code lost:
        r1.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0065, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0046 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r5 = this;
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()     // Catch:{ IOException -> 0x006e }
            java.lang.String r1 = "dmesg"
            java.lang.Process r0 = r0.exec(r1)     // Catch:{ IOException -> 0x006e }
            android.app.Activity r1 = r5.activity     // Catch:{ FileNotFoundException -> 0x0066 }
            java.lang.String r2 = "dmesg_backup"
            r3 = 0
            java.io.FileOutputStream r1 = r1.openFileOutput(r2, r3)     // Catch:{ FileNotFoundException -> 0x0066 }
            java.io.BufferedWriter r2 = new java.io.BufferedWriter
            java.io.OutputStreamWriter r3 = new java.io.OutputStreamWriter
            r3.<init>(r1)
            r2.<init>(r3)
            java.io.BufferedReader r3 = new java.io.BufferedReader
            java.io.InputStreamReader r4 = new java.io.InputStreamReader
            java.io.InputStream r0 = r0.getInputStream()
            r4.<init>(r0)
            r3.<init>(r4)
        L_0x002b:
            java.lang.String r0 = r3.readLine()     // Catch:{ IOException -> 0x0046 }
            if (r0 == 0) goto L_0x0035
            r2.write(r0)     // Catch:{ IOException -> 0x0046 }
            goto L_0x002b
        L_0x0035:
            r2.close()     // Catch:{ IOException -> 0x003f }
            r3.close()     // Catch:{ IOException -> 0x003f }
            r1.close()     // Catch:{ IOException -> 0x003f }
            goto L_0x0056
        L_0x003f:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0056
        L_0x0044:
            r0 = move-exception
            goto L_0x0057
        L_0x0046:
            java.lang.String r0 = TAG     // Catch:{ all -> 0x0044 }
            java.lang.String r4 = "Error reading dmesg"
            android.util.Log.e(r0, r4)     // Catch:{ all -> 0x0044 }
            r2.close()     // Catch:{ IOException -> 0x003f }
            r3.close()     // Catch:{ IOException -> 0x003f }
            r1.close()     // Catch:{ IOException -> 0x003f }
        L_0x0056:
            return
        L_0x0057:
            r2.close()     // Catch:{ IOException -> 0x0061 }
            r3.close()     // Catch:{ IOException -> 0x0061 }
            r1.close()     // Catch:{ IOException -> 0x0061 }
            goto L_0x0065
        L_0x0061:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0065:
            throw r0
        L_0x0066:
            java.lang.String r0 = TAG
            java.lang.String r1 = "Could not create file"
            android.util.Log.e(r0, r1)
            return
        L_0x006e:
            java.lang.String r0 = TAG
            java.lang.String r1 = "Could not execute dmesg"
            android.util.Log.e(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.io.KernelMessageSaver.run():void");
    }
}
