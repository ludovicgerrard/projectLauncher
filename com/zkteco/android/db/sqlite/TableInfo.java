package com.zkteco.android.db.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableInfo {
    private static final String TABLE_INFO = "pragma table_info('%s')";
    private static final String TAG = "TableInfo";
    private final Integer cid;
    private final String defaultValue;
    private final String name;
    private final Boolean notNull;
    private final Boolean pk;
    private final String type;

    private TableInfo(String... strArr) {
        Log.d(TAG, Arrays.toString(strArr));
        this.cid = Integer.valueOf(strArr[0]);
        this.name = strArr[1];
        this.type = strArr[2];
        this.notNull = Boolean.valueOf(strArr[3].equals("1"));
        this.defaultValue = strArr[4];
        this.pk = Boolean.valueOf(strArr[5].equals("1"));
    }

    private static TableInfo getFromRow(Cursor cursor) {
        return new TableInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.String[], android.database.Cursor] */
    public static List<TableInfo> getInfo(SQLiteDatabase sQLiteDatabase, String str) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = 0;
        try {
            cursor = sQLiteDatabase.rawQuery(String.format(TABLE_INFO, new Object[]{str}), cursor);
            while (cursor.moveToNext()) {
                arrayList.add(getFromRow(cursor));
            }
            return arrayList;
        } finally {
            if (cursor != 0) {
                cursor.close();
            }
        }
    }

    public Integer getCid() {
        return this.cid;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public Boolean isNotNull() {
        return this.notNull;
    }

    public Boolean isPk() {
        return this.pk;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }
}
