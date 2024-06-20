package com.zkteco.android.db.sqlite;

public enum SQLiteError {
    UNKNOWN(""),
    ERROR("0"),
    INTERNAL("1"),
    PERMISSION("2"),
    BUSY("5"),
    READ_ONLY("8"),
    IO_ERROR("10"),
    CORRUPT("11"),
    CANT_OPEN("14");
    
    private final String code;

    private SQLiteError(String str) {
        this.code = str;
    }

    public static SQLiteError getInstance(String str) {
        for (SQLiteError sQLiteError : values()) {
            if (sQLiteError.code.equals(str)) {
                return sQLiteError;
            }
        }
        return UNKNOWN;
    }
}
