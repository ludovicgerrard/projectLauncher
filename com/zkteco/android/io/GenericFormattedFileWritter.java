package com.zkteco.android.io;

import android.content.Context;
import android.database.Cursor;
import com.zkteco.android.util.StringHelper;

public class GenericFormattedFileWritter {
    public static final int AUTO_FLUSH_AFTER_LINES = 10;
    public static final String TAG = "com.zkteco.android.io.GenericFormattedFileWritter";

    public String getFormatHeader() {
        return null;
    }

    public char getSeparator() {
        return ' ';
    }

    private String getStringFromCursor(Context context, Cursor cursor, int i) {
        String string = cursor.getString(i);
        if (string == null || string.equals("null")) {
            string = " ";
        }
        return StringHelper.getStringByFullIdentifier(context, string);
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean writeFromCursor(android.content.Context r8, java.lang.String r9, android.database.Cursor r10) {
        /*
            r7 = this;
            java.lang.String r0 = "\""
            r1 = 0
            r2 = 1
            r3 = 0
            java.io.PrintWriter r4 = new java.io.PrintWriter     // Catch:{ Exception -> 0x00a7 }
            java.lang.String r5 = "UTF-8"
            r4.<init>(r9, r5)     // Catch:{ Exception -> 0x00a7 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r9.<init>()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            java.lang.String r3 = r7.getFormatHeader()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            if (r3 == 0) goto L_0x001a
            r4.print(r3)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
        L_0x001a:
            if (r10 == 0) goto L_0x0092
            r3 = r1
        L_0x001d:
            int r5 = r10.getColumnCount()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            if (r3 >= r5) goto L_0x004f
            int r5 = r9.length()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            if (r5 <= 0) goto L_0x0030
            char r5 = r7.getSeparator()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r9.append(r5)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
        L_0x0030:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r5.<init>()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            java.lang.String r6 = r10.getColumnName(r3)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r9.append(r5)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            int r3 = r3 + 1
            goto L_0x001d
        L_0x004f:
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r4.println(r9)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r9 = r2
        L_0x0057:
            boolean r0 = r10.moveToNext()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            if (r0 == 0) goto L_0x0092
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r0.<init>()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r3 = r1
        L_0x0063:
            int r5 = r10.getColumnCount()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            if (r3 >= r5) goto L_0x0080
            int r5 = r0.length()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            if (r5 <= 0) goto L_0x0076
            char r5 = r7.getSeparator()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r0.append(r5)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
        L_0x0076:
            java.lang.String r5 = r7.getStringFromCursor(r8, r10, r3)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r0.append(r5)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            int r3 = r3 + 1
            goto L_0x0063
        L_0x0080:
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r4.println(r0)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r0 = 10
            if (r9 != r0) goto L_0x008f
            r4.flush()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            goto L_0x0057
        L_0x008f:
            int r9 = r9 + 1
            goto L_0x0057
        L_0x0092:
            if (r10 == 0) goto L_0x0097
            r10.close()
        L_0x0097:
            r4.flush()
            r4.close()
            r1 = r2
            goto L_0x00c3
        L_0x009f:
            r8 = move-exception
            r3 = r4
            goto L_0x00c4
        L_0x00a2:
            r8 = move-exception
            r3 = r4
            goto L_0x00a8
        L_0x00a5:
            r8 = move-exception
            goto L_0x00c4
        L_0x00a7:
            r8 = move-exception
        L_0x00a8:
            java.lang.String r9 = TAG     // Catch:{ all -> 0x00a5 }
            java.lang.String r0 = "There was an error reading the cursor and exporting it"
            android.util.Log.e(r9, r0)     // Catch:{ all -> 0x00a5 }
            java.lang.String r8 = android.util.Log.getStackTraceString(r8)     // Catch:{ all -> 0x00a5 }
            android.util.Log.e(r9, r8)     // Catch:{ all -> 0x00a5 }
            if (r10 == 0) goto L_0x00bb
            r10.close()
        L_0x00bb:
            if (r3 == 0) goto L_0x00c3
            r3.flush()
            r3.close()
        L_0x00c3:
            return r1
        L_0x00c4:
            if (r10 == 0) goto L_0x00c9
            r10.close()
        L_0x00c9:
            if (r3 == 0) goto L_0x00d1
            r3.flush()
            r3.close()
        L_0x00d1:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.io.GenericFormattedFileWritter.writeFromCursor(android.content.Context, java.lang.String, android.database.Cursor):boolean");
    }
}
