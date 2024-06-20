package com.zkteco.android.core.interfaces;

import android.database.Cursor;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public interface DatabaseInterface {
    public static final String ACTION_ON_TABLE_MODIFIED = "com.zkteco.android.core.db.TABLE_MODIFIED";
    public static final String DB_ID = "id";
    public static final String EXECSQL = "db-execsql";
    public static final String GET_DATABASE_VERSION = "db-databases-version";
    public static final String INSERT = "db-insert";
    public static final String OPEN = "db-open";
    public static final String QUERY = "db-query";
    public static final String SYSTEM_FREE = "system-free";
    public static final String SYSTEM_GETINT = "system-getint";
    public static final String SYSTEM_GETSTR = "system-getstr";
    public static final String SYSTEM_INIT = "system-init";
    public static final String SYSTEM_INIT_OPTIONTABLE = "system-init-optiontable";
    public static final String SYSTEM_SETINT = "system-setint";
    public static final String SYSTEM_SETSTR = "system-setstr";
    public static final String TABLE_LOG = "database_log";
    public static final String TABLE_NAME = "table-name";
    public static final String TABLE_SETTING = "database_setting";
    public static final String TRANSACTION = "db-transaction";
    public static final String UPDATE = "db-update";

    public enum Event {
        INSERT,
        UPDATE,
        DELETE
    }

    Number executeInsert(String str, SQLCommand sQLCommand);

    Cursor executeQuery(String str, SQLCommand sQLCommand);

    void executeSql(String str, String str2, Object[] objArr);

    boolean executeTransaction(String str, List<SQLCommand> list);

    void executeUpdate(String str, SQLCommand sQLCommand);

    int getDBVersion();

    int getIntOption(String str, int i);

    String getStrOption(String str, String str2);

    void open(String str, Integer num);

    int setIntOption(String str, int i);

    int setStrOption(String str, String str2);

    int systemFree();

    void systemInit();

    void systemInitOptionTable();

    public static class SQLCommand implements Serializable {
        private String[] arguments;
        private String sql;

        public String getSql() {
            return this.sql;
        }

        public void setSql(String str) {
            this.sql = str;
        }

        public String[] getArguments() {
            return this.arguments;
        }

        public void setArguments(String[] strArr) {
            this.arguments = strArr;
        }

        public String toString() {
            return "SQLCommand{sql='" + this.sql + '\'' + ", arguments=" + Arrays.toString(this.arguments) + '}';
        }
    }

    public static class SQLCommand_Object implements Serializable {
        private Object[] arguments;
        private String sql;

        public String getSql() {
            return this.sql;
        }

        public void setSql(String str) {
            this.sql = str;
        }

        public Object[] getArguments() {
            return this.arguments;
        }

        public void setArguments(Object[] objArr) {
            this.arguments = objArr;
        }
    }
}
