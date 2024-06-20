package com.zkteco.android.db.orm;

import android.util.Log;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseResults;
import com.zkteco.android.core.interfaces.DatabaseInterface;
import com.zkteco.android.core.interfaces.DatabaseProvider;
import com.zkteco.android.core.library.Provider;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

class SQLiteInterfaceCompiledStatement implements CompiledStatement {
    private static final String TAG = "SQLiteInterfaceCompiledStatement";
    private final String databaseId;
    private final boolean isUpdate;
    private final Provider provider;
    private DatabaseResults result;
    private final DatabaseInterface.SQLCommand sqlCommand;

    public void setMaxRows(int i) throws SQLException {
    }

    SQLiteInterfaceCompiledStatement(Provider provider2, String str, boolean z, String str2, int i) {
        this.provider = provider2;
        this.databaseId = str;
        this.isUpdate = z;
        DatabaseInterface.SQLCommand sQLCommand = new DatabaseInterface.SQLCommand();
        this.sqlCommand = sQLCommand;
        sQLCommand.setSql(str2);
        sQLCommand.setArguments(new String[i]);
    }

    public int getColumnCount() throws SQLException {
        throw new SQLException("Unsupported operation getColumnCount()");
    }

    public String getColumnName(int i) throws SQLException {
        throw new SQLException("Unsupported operation getColumnName()");
    }

    public int runUpdate() throws SQLException {
        if (this.isUpdate) {
            DatabaseProvider.getInstance(this.provider).executeUpdate(this.databaseId, this.sqlCommand);
            return 0;
        }
        throw new SQLException("Not an update");
    }

    public DatabaseResults runQuery(ObjectCache objectCache) throws SQLException {
        if (!this.isUpdate) {
            SQLiteInterfaceDatabaseResults sQLiteInterfaceDatabaseResults = new SQLiteInterfaceDatabaseResults(DatabaseProvider.getInstance(this.provider).executeQuery(this.databaseId, this.sqlCommand), objectCache);
            this.result = sQLiteInterfaceDatabaseResults;
            return sQLiteInterfaceDatabaseResults;
        }
        throw new SQLException("Trying to execute update as query");
    }

    public int runExecute() throws SQLException {
        if (this.isUpdate) {
            DatabaseProvider.getInstance(this.provider).executeUpdate(this.databaseId, this.sqlCommand);
            return 0;
        }
        throw new SQLException("Not an update");
    }

    public void closeQuietly() {
        try {
            close();
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public void cancel() throws SQLException {
        closeQuietly();
    }

    /* renamed from: com.zkteco.android.db.orm.SQLiteInterfaceCompiledStatement$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$j256$ormlite$field$SqlType;

        /* JADX WARNING: Can't wrap try/catch for region: R(26:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|(3:25|26|28)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0078 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0084 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0090 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.j256.ormlite.field.SqlType[] r0 = com.j256.ormlite.field.SqlType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$j256$ormlite$field$SqlType = r0
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.DOUBLE     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.INTEGER     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.FLOAT     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.BIG_DECIMAL     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.BOOLEAN     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.CHAR     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.LONG_STRING     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.SHORT     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x006c }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.STRING     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x0078 }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.UUID     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x0084 }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.BYTE     // Catch:{ NoSuchFieldError -> 0x0084 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0084 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0084 }
            L_0x0084:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x0090 }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.LONG     // Catch:{ NoSuchFieldError -> 0x0090 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0090 }
                r2 = 12
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0090 }
            L_0x0090:
                int[] r0 = $SwitchMap$com$j256$ormlite$field$SqlType     // Catch:{ NoSuchFieldError -> 0x009c }
                com.j256.ormlite.field.SqlType r1 = com.j256.ormlite.field.SqlType.DATE     // Catch:{ NoSuchFieldError -> 0x009c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x009c }
                r2 = 13
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x009c }
            L_0x009c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.db.orm.SQLiteInterfaceCompiledStatement.AnonymousClass1.<clinit>():void");
        }
    }

    public void setObject(int i, Object obj, SqlType sqlType) throws SQLException {
        String str;
        if (obj != null) {
            switch (AnonymousClass1.$SwitchMap$com$j256$ormlite$field$SqlType[sqlType.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    str = String.valueOf(obj);
                    break;
                case 13:
                    str = String.valueOf(((Date) obj).getTime());
                    break;
                default:
                    throw new SQLException("Unsupported SQL type " + sqlType);
            }
        } else {
            str = null;
        }
        this.sqlCommand.getArguments()[i] = str;
    }

    public void setQueryTimeout(long j) throws SQLException {
        throw new SQLException("Unsupported operation setQueryTimeout()");
    }

    public void close() throws IOException {
        DatabaseResults databaseResults = this.result;
        if (databaseResults != null) {
            databaseResults.close();
        }
    }

    /* access modifiers changed from: package-private */
    public DatabaseInterface.SQLCommand getSQLCommand() {
        return this.sqlCommand;
    }
}
