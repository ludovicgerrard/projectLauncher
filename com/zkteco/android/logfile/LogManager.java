package com.zkteco.android.logfile;

public class LogManager {
    private static final String logFileName = "ZKLogOutput";

    private static class Holder {
        /* access modifiers changed from: private */
        public static final LogManager instance = new LogManager();

        private Holder() {
        }
    }

    private LogManager() {
    }

    public static final LogManager getInstance() {
        return Holder.instance;
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00eb A[SYNTHETIC, Splitter:B:38:0x00eb] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f5 A[SYNTHETIC, Splitter:B:43:0x00f5] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ff A[SYNTHETIC, Splitter:B:48:0x00ff] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x010f A[SYNTHETIC, Splitter:B:57:0x010f] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0119 A[SYNTHETIC, Splitter:B:62:0x0119] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0123 A[SYNTHETIC, Splitter:B:67:0x0123] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x012f A[SYNTHETIC, Splitter:B:73:0x012f] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0139 A[SYNTHETIC, Splitter:B:78:0x0139] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0143 A[SYNTHETIC, Splitter:B:83:0x0143] */
    /* JADX WARNING: Removed duplicated region for block: B:89:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:90:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void OutputLogAuto(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            java.lang.String r0 = "#$#"
            java.lang.String r1 = "/"
            java.lang.String r2 = "\r"
            boolean r3 = android.text.TextUtils.isEmpty(r9)
            if (r3 != 0) goto L_0x014c
            boolean r3 = android.text.TextUtils.isEmpty(r10)
            if (r3 == 0) goto L_0x0014
            goto L_0x014c
        L_0x0014:
            r3 = 0
            java.lang.String r4 = android.os.Environment.getExternalStorageState()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.String r5 = "mounted"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            if (r4 == 0) goto L_0x00e7
            java.io.File r4 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            if (r5 != 0) goto L_0x00e7
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r5.<init>()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.String r5 = "ZKLogOutput"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r5.<init>(r4)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            boolean r6 = r5.exists()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            if (r6 != 0) goto L_0x0054
            r5.mkdirs()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
        L_0x0054:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r5.<init>()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.StringBuilder r9 = r1.append(r9)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.String r1 = ".txt"
            java.lang.StringBuilder r9 = r9.append(r1)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r1.<init>(r9)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            boolean r4 = r1.exists()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            if (r4 != 0) goto L_0x007d
            r1.createNewFile()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
        L_0x007d:
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.String r4 = "yyyy:MM:dd hh:mm:ss"
            java.util.Locale r5 = java.util.Locale.US     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r1.<init>(r4, r5)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r4.<init>()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.util.Date r5 = new java.util.Date     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r5.<init>()     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.lang.String r1 = r1.format(r5)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r4.append(r1)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r4.append(r2)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r4.append(r0)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r4.append(r2)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r4.append(r10)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r4.append(r2)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r4.append(r0)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r4.append(r2)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            r0 = 1
            r10.<init>(r9, r0)     // Catch:{ Exception -> 0x0107, all -> 0x0103 }
            java.io.OutputStreamWriter r9 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x00e3, all -> 0x00df }
            r9.<init>(r10)     // Catch:{ Exception -> 0x00e3, all -> 0x00df }
            java.io.BufferedWriter r0 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x00d9, all -> 0x00d2 }
            r0.<init>(r9)     // Catch:{ Exception -> 0x00d9, all -> 0x00d2 }
            java.lang.String r1 = r4.toString()     // Catch:{ Exception -> 0x00cc, all -> 0x00c5 }
            r0.write(r1)     // Catch:{ Exception -> 0x00cc, all -> 0x00c5 }
            r3 = r0
            goto L_0x00e9
        L_0x00c5:
            r1 = move-exception
            r3 = r0
            r0 = r10
            r10 = r9
            r9 = r1
            goto L_0x012d
        L_0x00cc:
            r1 = move-exception
            r3 = r0
            r0 = r10
            r10 = r9
            r9 = r1
            goto L_0x010a
        L_0x00d2:
            r0 = move-exception
            r7 = r10
            r10 = r9
            r9 = r0
            r0 = r7
            goto L_0x012d
        L_0x00d9:
            r0 = move-exception
            r7 = r10
            r10 = r9
            r9 = r0
            r0 = r7
            goto L_0x010a
        L_0x00df:
            r9 = move-exception
            r0 = r10
            r10 = r3
            goto L_0x012d
        L_0x00e3:
            r9 = move-exception
            r0 = r10
            r10 = r3
            goto L_0x010a
        L_0x00e7:
            r9 = r3
            r10 = r9
        L_0x00e9:
            if (r3 == 0) goto L_0x00f3
            r3.close()     // Catch:{ Exception -> 0x00ef }
            goto L_0x00f3
        L_0x00ef:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00f3:
            if (r9 == 0) goto L_0x00fd
            r9.close()     // Catch:{ Exception -> 0x00f9 }
            goto L_0x00fd
        L_0x00f9:
            r9 = move-exception
            r9.printStackTrace()
        L_0x00fd:
            if (r10 == 0) goto L_0x012b
            r10.close()     // Catch:{ Exception -> 0x0127 }
            goto L_0x012b
        L_0x0103:
            r9 = move-exception
            r10 = r3
            r0 = r10
            goto L_0x012d
        L_0x0107:
            r9 = move-exception
            r10 = r3
            r0 = r10
        L_0x010a:
            r9.printStackTrace()     // Catch:{ all -> 0x012c }
            if (r3 == 0) goto L_0x0117
            r3.close()     // Catch:{ Exception -> 0x0113 }
            goto L_0x0117
        L_0x0113:
            r9 = move-exception
            r9.printStackTrace()
        L_0x0117:
            if (r10 == 0) goto L_0x0121
            r10.close()     // Catch:{ Exception -> 0x011d }
            goto L_0x0121
        L_0x011d:
            r9 = move-exception
            r9.printStackTrace()
        L_0x0121:
            if (r0 == 0) goto L_0x012b
            r0.close()     // Catch:{ Exception -> 0x0127 }
            goto L_0x012b
        L_0x0127:
            r9 = move-exception
            r9.printStackTrace()
        L_0x012b:
            return
        L_0x012c:
            r9 = move-exception
        L_0x012d:
            if (r3 == 0) goto L_0x0137
            r3.close()     // Catch:{ Exception -> 0x0133 }
            goto L_0x0137
        L_0x0133:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0137:
            if (r10 == 0) goto L_0x0141
            r10.close()     // Catch:{ Exception -> 0x013d }
            goto L_0x0141
        L_0x013d:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0141:
            if (r0 == 0) goto L_0x014b
            r0.close()     // Catch:{ Exception -> 0x0147 }
            goto L_0x014b
        L_0x0147:
            r10 = move-exception
            r10.printStackTrace()
        L_0x014b:
            throw r9
        L_0x014c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.logfile.LogManager.OutputLogAuto(java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0104 A[SYNTHETIC, Splitter:B:37:0x0104] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x010e A[SYNTHETIC, Splitter:B:42:0x010e] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0118 A[SYNTHETIC, Splitter:B:47:0x0118] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0128 A[SYNTHETIC, Splitter:B:56:0x0128] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0132 A[SYNTHETIC, Splitter:B:61:0x0132] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x013c A[SYNTHETIC, Splitter:B:66:0x013c] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0148 A[SYNTHETIC, Splitter:B:72:0x0148] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0152 A[SYNTHETIC, Splitter:B:77:0x0152] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x015c A[SYNTHETIC, Splitter:B:82:0x015c] */
    /* JADX WARNING: Removed duplicated region for block: B:88:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:89:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void OutputLog(java.lang.String r10, java.lang.String r11) {
        /*
            r9 = this;
            java.lang.String r0 = "#$#"
            java.lang.String r1 = "/"
            java.lang.String r2 = "\r"
            boolean r3 = android.text.TextUtils.isEmpty(r10)
            if (r3 != 0) goto L_0x0165
            boolean r3 = android.text.TextUtils.isEmpty(r11)
            if (r3 == 0) goto L_0x0014
            goto L_0x0165
        L_0x0014:
            r3 = 0
            java.lang.String r4 = android.os.Environment.getExternalStorageState()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r5 = "mounted"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            if (r4 == 0) goto L_0x0100
            java.io.File r4 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            if (r5 != 0) goto L_0x0100
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r5.<init>()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r5 = "ZKLogOutput"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r5.<init>(r4)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            boolean r5 = r5.exists()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            if (r5 == 0) goto L_0x0100
            java.text.SimpleDateFormat r5 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r6 = "yyyy_MM_dd"
            java.util.Locale r7 = java.util.Locale.US     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r5.<init>(r6, r7)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r6.<init>()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.StringBuilder r4 = r6.append(r4)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.util.Date r4 = new java.util.Date     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r4.<init>()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r4 = r5.format(r4)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r4 = "_"
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.StringBuilder r10 = r1.append(r10)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r1 = ".txt"
            java.lang.StringBuilder r10 = r10.append(r1)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r1.<init>(r10)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            boolean r4 = r1.exists()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            if (r4 != 0) goto L_0x0096
            r1.createNewFile()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
        L_0x0096:
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r4 = "yyyy:MM:dd hh:mm:ss"
            java.util.Locale r5 = java.util.Locale.US     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r1.<init>(r4, r5)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r4.<init>()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.util.Date r5 = new java.util.Date     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r5.<init>()     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.lang.String r1 = r1.format(r5)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r4.append(r1)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r4.append(r2)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r4.append(r0)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r4.append(r2)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r4.append(r11)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r4.append(r2)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r4.append(r0)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r4.append(r2)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.io.FileOutputStream r11 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            r0 = 1
            r11.<init>(r10, r0)     // Catch:{ Exception -> 0x0120, all -> 0x011c }
            java.io.OutputStreamWriter r10 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x00fc, all -> 0x00f8 }
            r10.<init>(r11)     // Catch:{ Exception -> 0x00fc, all -> 0x00f8 }
            java.io.BufferedWriter r0 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x00f2, all -> 0x00eb }
            r0.<init>(r10)     // Catch:{ Exception -> 0x00f2, all -> 0x00eb }
            java.lang.String r1 = r4.toString()     // Catch:{ Exception -> 0x00e5, all -> 0x00de }
            r0.write(r1)     // Catch:{ Exception -> 0x00e5, all -> 0x00de }
            r3 = r0
            goto L_0x0102
        L_0x00de:
            r1 = move-exception
            r3 = r0
            r0 = r11
            r11 = r10
            r10 = r1
            goto L_0x0146
        L_0x00e5:
            r1 = move-exception
            r3 = r0
            r0 = r11
            r11 = r10
            r10 = r1
            goto L_0x0123
        L_0x00eb:
            r0 = move-exception
            r8 = r11
            r11 = r10
            r10 = r0
            r0 = r8
            goto L_0x0146
        L_0x00f2:
            r0 = move-exception
            r8 = r11
            r11 = r10
            r10 = r0
            r0 = r8
            goto L_0x0123
        L_0x00f8:
            r10 = move-exception
            r0 = r11
            r11 = r3
            goto L_0x0146
        L_0x00fc:
            r10 = move-exception
            r0 = r11
            r11 = r3
            goto L_0x0123
        L_0x0100:
            r10 = r3
            r11 = r10
        L_0x0102:
            if (r3 == 0) goto L_0x010c
            r3.close()     // Catch:{ Exception -> 0x0108 }
            goto L_0x010c
        L_0x0108:
            r0 = move-exception
            r0.printStackTrace()
        L_0x010c:
            if (r10 == 0) goto L_0x0116
            r10.close()     // Catch:{ Exception -> 0x0112 }
            goto L_0x0116
        L_0x0112:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0116:
            if (r11 == 0) goto L_0x0144
            r11.close()     // Catch:{ Exception -> 0x0140 }
            goto L_0x0144
        L_0x011c:
            r10 = move-exception
            r11 = r3
            r0 = r11
            goto L_0x0146
        L_0x0120:
            r10 = move-exception
            r11 = r3
            r0 = r11
        L_0x0123:
            r10.printStackTrace()     // Catch:{ all -> 0x0145 }
            if (r3 == 0) goto L_0x0130
            r3.close()     // Catch:{ Exception -> 0x012c }
            goto L_0x0130
        L_0x012c:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0130:
            if (r11 == 0) goto L_0x013a
            r11.close()     // Catch:{ Exception -> 0x0136 }
            goto L_0x013a
        L_0x0136:
            r10 = move-exception
            r10.printStackTrace()
        L_0x013a:
            if (r0 == 0) goto L_0x0144
            r0.close()     // Catch:{ Exception -> 0x0140 }
            goto L_0x0144
        L_0x0140:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0144:
            return
        L_0x0145:
            r10 = move-exception
        L_0x0146:
            if (r3 == 0) goto L_0x0150
            r3.close()     // Catch:{ Exception -> 0x014c }
            goto L_0x0150
        L_0x014c:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0150:
            if (r11 == 0) goto L_0x015a
            r11.close()     // Catch:{ Exception -> 0x0156 }
            goto L_0x015a
        L_0x0156:
            r11 = move-exception
            r11.printStackTrace()
        L_0x015a:
            if (r0 == 0) goto L_0x0164
            r0.close()     // Catch:{ Exception -> 0x0160 }
            goto L_0x0164
        L_0x0160:
            r11 = move-exception
            r11.printStackTrace()
        L_0x0164:
            throw r10
        L_0x0165:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.logfile.LogManager.OutputLog(java.lang.String, java.lang.String):void");
    }
}
