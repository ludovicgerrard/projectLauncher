package com.zkteco.android.db.sqlite;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLiteHelper {
    private static final Pattern ERROR_CODE_PATTERN = Pattern.compile(".*\\(code (\\d+)\\).*");
    private static final List<String> EXCLUDED_TABLES = Arrays.asList(new String[]{"android_metadata", "sqlite_sequence"});
    private static final String SQLITE_GET_TABLES = "SELECT tbl_name FROM sqlite_master WHERE type = 'table'";
    private static final String SQLITE_GET_TRIGGERS_FOR_TABLE = "SELECT count(*) AS trigger_count FROM sqlite_master where type = 'trigger' AND tbl_name = ?";
    private static final String SQLITE_TABLE_CREATED_QUERY = "SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '%s'";
    private static final Pattern SYSTEM_TABLE_PATTERN = Pattern.compile("^sqlite_.*");
    private static final String TAG = "com.zkteco.android.db.sqlite.SQLiteHelper";

    public static SQLiteError getErrorCodeFromException(SQLException sQLException) {
        SQLiteError sQLiteError = SQLiteError.UNKNOWN;
        Matcher matcher = ERROR_CODE_PATTERN.matcher(sQLException.getMessage());
        return matcher.matches() ? SQLiteError.getInstance(matcher.group(1)) : sQLiteError;
    }

    public static void removeSystemTables(Set<String> set) {
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            if (SYSTEM_TABLE_PATTERN.matcher(it.next()).matches()) {
                it.remove();
            }
        }
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.String[], android.database.Cursor] */
    public static Set<String> getTables(SQLiteDatabase sQLiteDatabase) {
        HashSet hashSet = new HashSet();
        Cursor cursor = 0;
        try {
            cursor = sQLiteDatabase.rawQuery(SQLITE_GET_TABLES, cursor);
            while (cursor.moveToNext()) {
                hashSet.add(cursor.getString(0));
            }
            return hashSet;
        } finally {
            if (cursor != 0) {
                cursor.close();
            }
        }
    }
}
