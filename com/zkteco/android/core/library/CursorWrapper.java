package com.zkteco.android.core.library;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;
import com.zkteco.android.util.serialization.Serializer;
import com.zkteco.android.util.serialization.SerializerFactory;

public class CursorWrapper {
    private static final Serializer SERIALIZER = SerializerFactory.INSTANCE.getDefaultSerializer();
    private static final String WRAPPER_COLUMN_DATA = "return_data";
    private static final String WRAPPER_COLUMN_EXCEPTION = "exception_message";

    private CursorWrapper() {
    }

    private static Object unwrapClass(Cursor cursor) {
        return SERIALIZER.getInstanceFromString(cursor.getString(cursor.getColumnIndex(WRAPPER_COLUMN_DATA)));
    }

    private static void unwrapException(Cursor cursor, int i) {
        throw new CoreServiceException(cursor.getString(i));
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0040  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.Object unwrapFromCursor(android.database.Cursor r5) {
        /*
            if (r5 != 0) goto L_0x0004
            r5 = 0
            return r5
        L_0x0004:
            r0 = 0
            r1 = 1
            r2 = -1
            boolean r3 = r5.moveToNext()     // Catch:{ all -> 0x0036 }
            if (r3 == 0) goto L_0x002b
            java.lang.String r3 = "return_data"
            int r3 = r5.getColumnIndex(r3)     // Catch:{ all -> 0x0036 }
            if (r3 <= r2) goto L_0x001f
            java.lang.Object r0 = unwrapClass(r5)     // Catch:{ all -> 0x001d }
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x002c
        L_0x001d:
            r0 = move-exception
            goto L_0x003a
        L_0x001f:
            java.lang.String r3 = "exception_message"
            int r3 = r5.getColumnIndex(r3)     // Catch:{ all -> 0x0036 }
            if (r3 <= r2) goto L_0x002b
            unwrapException(r5, r3)     // Catch:{ all -> 0x001d }
            r0 = r1
        L_0x002b:
            r1 = r5
        L_0x002c:
            if (r0 == 0) goto L_0x0032
            r5.close()
            goto L_0x0035
        L_0x0032:
            r5.moveToPosition(r2)
        L_0x0035:
            return r1
        L_0x0036:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
        L_0x003a:
            if (r1 == 0) goto L_0x0040
            r5.close()
            goto L_0x0043
        L_0x0040:
            r5.moveToPosition(r2)
        L_0x0043:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.core.library.CursorWrapper.unwrapFromCursor(android.database.Cursor):java.lang.Object");
    }

    public static Cursor wrapInCursor(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Cursor) {
            return (Cursor) obj;
        }
        if (obj instanceof Exception) {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{WRAPPER_COLUMN_EXCEPTION});
            matrixCursor.addRow(new String[]{Log.getStackTraceString((Exception) obj)});
            return matrixCursor;
        }
        MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{WRAPPER_COLUMN_DATA});
        matrixCursor2.addRow(new Object[]{SERIALIZER.getStringFromInstance(obj)});
        return matrixCursor2;
    }
}
