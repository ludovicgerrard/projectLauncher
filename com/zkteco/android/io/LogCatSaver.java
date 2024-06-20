package com.zkteco.android.io;

import android.app.Activity;

public class LogCatSaver implements Runnable {
    private static final String TAG = "com.zkteco.android.io.LogCatSaver";
    private final Activity activity;

    public LogCatSaver(Activity activity2) {
        this.activity = activity2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0048, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        android.util.Log.e(TAG, "Error reading logcat");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r3.close();
        r4.close();
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0065, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0066, code lost:
        android.util.Log.e(TAG, "IOException was caught", r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006b, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x004a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r6 = this;
            java.lang.String r0 = "IOException was caught"
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch:{ IOException -> 0x0074 }
            java.lang.String r2 = "logcat"
            java.lang.Process r1 = r1.exec(r2)     // Catch:{ IOException -> 0x0074 }
            android.app.Activity r2 = r6.activity     // Catch:{ FileNotFoundException -> 0x006c }
            java.lang.String r3 = "logcat_backup"
            r4 = 0
            java.io.FileOutputStream r2 = r2.openFileOutput(r3, r4)     // Catch:{ FileNotFoundException -> 0x006c }
            java.io.BufferedWriter r3 = new java.io.BufferedWriter
            java.io.OutputStreamWriter r4 = new java.io.OutputStreamWriter
            r4.<init>(r2)
            r3.<init>(r4)
            java.io.BufferedReader r4 = new java.io.BufferedReader
            java.io.InputStreamReader r5 = new java.io.InputStreamReader
            java.io.InputStream r1 = r1.getInputStream()
            r5.<init>(r1)
            r4.<init>(r5)
        L_0x002d:
            java.lang.String r1 = r4.readLine()     // Catch:{ IOException -> 0x004a }
            if (r1 == 0) goto L_0x0037
            r3.write(r1)     // Catch:{ IOException -> 0x004a }
            goto L_0x002d
        L_0x0037:
            r3.close()     // Catch:{ IOException -> 0x0041 }
            r4.close()     // Catch:{ IOException -> 0x0041 }
            r2.close()     // Catch:{ IOException -> 0x0041 }
            goto L_0x005a
        L_0x0041:
            r1 = move-exception
            java.lang.String r2 = TAG
            android.util.Log.e(r2, r0, r1)
            goto L_0x005a
        L_0x0048:
            r1 = move-exception
            goto L_0x005b
        L_0x004a:
            java.lang.String r1 = TAG     // Catch:{ all -> 0x0048 }
            java.lang.String r5 = "Error reading logcat"
            android.util.Log.e(r1, r5)     // Catch:{ all -> 0x0048 }
            r3.close()     // Catch:{ IOException -> 0x0041 }
            r4.close()     // Catch:{ IOException -> 0x0041 }
            r2.close()     // Catch:{ IOException -> 0x0041 }
        L_0x005a:
            return
        L_0x005b:
            r3.close()     // Catch:{ IOException -> 0x0065 }
            r4.close()     // Catch:{ IOException -> 0x0065 }
            r2.close()     // Catch:{ IOException -> 0x0065 }
            goto L_0x006b
        L_0x0065:
            r2 = move-exception
            java.lang.String r3 = TAG
            android.util.Log.e(r3, r0, r2)
        L_0x006b:
            throw r1
        L_0x006c:
            java.lang.String r0 = TAG
            java.lang.String r1 = "Could not create file"
            android.util.Log.e(r0, r1)
            return
        L_0x0074:
            java.lang.String r0 = TAG
            java.lang.String r1 = "Could not execute logcat -d"
            android.util.Log.e(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.io.LogCatSaver.run():void");
    }
}
