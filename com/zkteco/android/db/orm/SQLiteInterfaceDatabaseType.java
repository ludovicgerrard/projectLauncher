package com.zkteco.android.db.orm;

import com.j256.ormlite.db.BaseSqliteDatabaseType;

class SQLiteInterfaceDatabaseType extends BaseSqliteDatabaseType {
    private static final String DATABASE_NAME = "ZKTeco CoreService SQLiteInterface";

    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    /* access modifiers changed from: protected */
    public String getDriverClassName() {
        return null;
    }

    public boolean isDatabaseUrlThisType(String str, String str2) {
        return false;
    }

    SQLiteInterfaceDatabaseType() {
    }
}
