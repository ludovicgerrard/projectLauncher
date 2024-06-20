package com.zkteco.android.util;

import android.database.Cursor;
import android.database.MatrixCursor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\n\u0010\u0005\u001a\u00020\u0004*\u00020\u0006\u001a\n\u0010\u0007\u001a\u00020\u0006*\u00020\u0004¨\u0006\b"}, d2 = {"getRowList", "", "", "cursor", "Landroid/database/Cursor;", "unwrapCursor", "Lcom/zkteco/android/util/CursorHolder;", "wrapCursor", "HelpersAndroidUtils_release"}, k = 2, mv = {1, 1, 9})
/* compiled from: DatabaseUtils.kt */
public final class DatabaseUtilsKt {
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0091, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0092, code lost:
        kotlin.io.CloseableKt.closeFinally(r1, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0095, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final com.zkteco.android.util.CursorHolder wrapCursor(android.database.Cursor r7) {
        /*
            java.lang.String r0 = "$receiver"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r0 = (java.util.List) r0
            r1 = r7
            java.io.Closeable r1 = (java.io.Closeable) r1
            r2 = 0
            r3 = r2
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            r4 = r1
            android.database.Cursor r4 = (android.database.Cursor) r4     // Catch:{ all -> 0x008f }
            java.util.Map r2 = (java.util.Map) r2     // Catch:{ all -> 0x008f }
            boolean r4 = r7.moveToFirst()     // Catch:{ all -> 0x008f }
            if (r4 == 0) goto L_0x0081
            java.lang.String[] r2 = r7.getColumnNames()     // Catch:{ all -> 0x008f }
            java.lang.String r4 = "this.columnNames"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r2, r4)     // Catch:{ all -> 0x008f }
            java.lang.Object[] r2 = (java.lang.Object[]) r2     // Catch:{ all -> 0x008f }
            java.util.List r2 = kotlin.collections.ArraysKt.asList((T[]) r2)     // Catch:{ all -> 0x008f }
            java.lang.Iterable r2 = (java.lang.Iterable) r2     // Catch:{ all -> 0x008f }
            r4 = 10
            int r4 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r2, r4)     // Catch:{ all -> 0x008f }
            int r4 = kotlin.collections.MapsKt.mapCapacity(r4)     // Catch:{ all -> 0x008f }
            r5 = 16
            int r4 = kotlin.ranges.RangesKt.coerceAtLeast((int) r4, (int) r5)     // Catch:{ all -> 0x008f }
            java.util.LinkedHashMap r5 = new java.util.LinkedHashMap     // Catch:{ all -> 0x008f }
            r5.<init>(r4)     // Catch:{ all -> 0x008f }
            r4 = r5
            java.util.Map r4 = (java.util.Map) r4     // Catch:{ all -> 0x008f }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x008f }
        L_0x004b:
            boolean r5 = r2.hasNext()     // Catch:{ all -> 0x008f }
            if (r5 == 0) goto L_0x0073
            java.lang.Object r5 = r2.next()     // Catch:{ all -> 0x008f }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x008f }
            int r6 = r7.getColumnIndex(r5)     // Catch:{ all -> 0x008f }
            int r6 = r7.getType(r6)     // Catch:{ all -> 0x008f }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x008f }
            kotlin.Pair r5 = kotlin.TuplesKt.to(r5, r6)     // Catch:{ all -> 0x008f }
            java.lang.Object r6 = r5.getFirst()     // Catch:{ all -> 0x008f }
            java.lang.Object r5 = r5.getSecond()     // Catch:{ all -> 0x008f }
            r4.put(r6, r5)     // Catch:{ all -> 0x008f }
            goto L_0x004b
        L_0x0073:
            java.util.List r2 = getRowList(r7)     // Catch:{ all -> 0x008f }
            r0.add(r2)     // Catch:{ all -> 0x008f }
            boolean r2 = r7.moveToNext()     // Catch:{ all -> 0x008f }
            if (r2 != 0) goto L_0x0073
            r2 = r4
        L_0x0081:
            com.zkteco.android.util.CursorHolder r7 = new com.zkteco.android.util.CursorHolder     // Catch:{ all -> 0x008f }
            if (r2 != 0) goto L_0x0088
            kotlin.jvm.internal.Intrinsics.throwNpe()     // Catch:{ all -> 0x008f }
        L_0x0088:
            r7.<init>(r2, r0)     // Catch:{ all -> 0x008f }
            kotlin.io.CloseableKt.closeFinally(r1, r3)
            return r7
        L_0x008f:
            r7 = move-exception
            throw r7     // Catch:{ all -> 0x0091 }
        L_0x0091:
            r0 = move-exception
            kotlin.io.CloseableKt.closeFinally(r1, r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.util.DatabaseUtilsKt.wrapCursor(android.database.Cursor):com.zkteco.android.util.CursorHolder");
    }

    private static final List<String> getRowList(Cursor cursor) {
        List<String> arrayList = new ArrayList<>();
        String[] columnNames = cursor.getColumnNames();
        Intrinsics.checkExpressionValueIsNotNull(columnNames, "cursor.columnNames");
        for (String columnIndex : ArraysKt.asList((T[]) (Object[]) columnNames)) {
            int columnIndex2 = cursor.getColumnIndex(columnIndex);
            if (columnIndex2 > -1) {
                int type = cursor.getType(columnIndex2);
                if (type == 0) {
                    arrayList.add((Object) null);
                } else if (type == 1) {
                    arrayList.add(String.valueOf(cursor.getLong(columnIndex2)));
                } else if (type == 2) {
                    arrayList.add(String.valueOf(cursor.getFloat(columnIndex2)));
                } else if (type == 3) {
                    arrayList.add(cursor.getString(columnIndex2));
                }
            }
        }
        return arrayList;
    }

    public static final Cursor unwrapCursor(CursorHolder cursorHolder) {
        Intrinsics.checkParameterIsNotNull(cursorHolder, "$receiver");
        Collection keySet = cursorHolder.getColumnInfo().keySet();
        if (keySet != null) {
            Object[] array = keySet.toArray(new String[0]);
            if (array != null) {
                MatrixCursor matrixCursor = new MatrixCursor((String[]) array);
                List<T> mutableList = CollectionsKt.toMutableList(cursorHolder.getColumnInfo().values());
                for (List<String> it : cursorHolder.getData()) {
                    List arrayList = new ArrayList();
                    int i = 0;
                    for (String str : it) {
                        int i2 = i + 1;
                        int intValue = ((Number) mutableList.get(i)).intValue();
                        if (intValue == 0) {
                            arrayList.add((Object) null);
                        } else if (intValue == 1) {
                            if (str == null) {
                                Intrinsics.throwNpe();
                            }
                            arrayList.add(Long.valueOf(Long.parseLong(str)));
                        } else if (intValue == 2) {
                            if (str == null) {
                                Intrinsics.throwNpe();
                            }
                            arrayList.add(Float.valueOf(Float.parseFloat(str)));
                        } else if (intValue == 3) {
                            arrayList.add(str);
                        }
                        i = i2;
                    }
                    matrixCursor.addRow(arrayList);
                }
                return matrixCursor;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        throw new TypeCastException("null cannot be cast to non-null type java.util.Collection<T>");
    }
}
