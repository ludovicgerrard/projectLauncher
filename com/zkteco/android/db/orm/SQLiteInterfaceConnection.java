package com.zkteco.android.db.orm;

import android.database.Cursor;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.GeneratedKeyHolder;
import com.zkteco.android.core.interfaces.DatabaseInterface;
import com.zkteco.android.core.interfaces.DatabaseProvider;
import com.zkteco.android.core.library.Provider;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

class SQLiteInterfaceConnection implements DatabaseConnection {
    private static final String SQL_CHECK_TABLE = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
    private List<DatabaseInterface.SQLCommand> currentTransaction = new ArrayList();
    private final String databaseId;
    private boolean isAutoCommit = false;
    private boolean isClosed = true;
    private final Provider provider;

    public boolean isAutoCommitSupported() throws SQLException {
        return true;
    }

    public Savepoint setSavePoint(String str) throws SQLException {
        return null;
    }

    SQLiteInterfaceConnection(Provider provider2, String str, int i) {
        this.provider = provider2;
        this.databaseId = str;
        this.isClosed = false;
    }

    public boolean isAutoCommit() throws SQLException {
        return this.isAutoCommit;
    }

    public void setAutoCommit(boolean z) throws SQLException {
        this.isAutoCommit = z;
    }

    public void commit(Savepoint savepoint) throws SQLException {
        DatabaseProvider.getInstance(this.provider).executeTransaction(this.databaseId, this.currentTransaction);
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        this.currentTransaction.clear();
    }

    public int executeStatement(String str, int i) throws SQLException {
        DatabaseInterface.SQLCommand sQLCommand = new DatabaseInterface.SQLCommand();
        sQLCommand.setSql(str);
        if (this.isAutoCommit) {
            this.currentTransaction.add(sQLCommand);
            return 0;
        }
        DatabaseProvider.getInstance(this.provider).executeUpdate(this.databaseId, sQLCommand);
        return 0;
    }

    /* renamed from: com.zkteco.android.db.orm.SQLiteInterfaceConnection$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$j256$ormlite$stmt$StatementBuilder$StatementType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.j256.ormlite.stmt.StatementBuilder$StatementType[] r0 = com.j256.ormlite.stmt.StatementBuilder.StatementType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$j256$ormlite$stmt$StatementBuilder$StatementType = r0
                com.j256.ormlite.stmt.StatementBuilder$StatementType r1 = com.j256.ormlite.stmt.StatementBuilder.StatementType.DELETE     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$j256$ormlite$stmt$StatementBuilder$StatementType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.j256.ormlite.stmt.StatementBuilder$StatementType r1 = com.j256.ormlite.stmt.StatementBuilder.StatementType.EXECUTE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$j256$ormlite$stmt$StatementBuilder$StatementType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.j256.ormlite.stmt.StatementBuilder$StatementType r1 = com.j256.ormlite.stmt.StatementBuilder.StatementType.UPDATE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.db.orm.SQLiteInterfaceConnection.AnonymousClass1.<clinit>():void");
        }
    }

    public CompiledStatement compileStatement(String str, StatementBuilder.StatementType statementType, FieldType[] fieldTypeArr, int i, boolean z) throws SQLException {
        int i2 = AnonymousClass1.$SwitchMap$com$j256$ormlite$stmt$StatementBuilder$StatementType[statementType.ordinal()];
        boolean z2 = true;
        if (!(i2 == 1 || i2 == 2 || i2 == 3)) {
            z2 = false;
        }
        return new SQLiteInterfaceCompiledStatement(this.provider, this.databaseId, z2, str, fieldTypeArr.length);
    }

    private SQLiteInterfaceCompiledStatement getCompiledStatement(boolean z, String str, Object[] objArr, FieldType[] fieldTypeArr) throws SQLException {
        if (objArr.length == fieldTypeArr.length) {
            SQLiteInterfaceCompiledStatement sQLiteInterfaceCompiledStatement = new SQLiteInterfaceCompiledStatement(this.provider, this.databaseId, z, str, fieldTypeArr.length);
            for (int i = 0; i < objArr.length; i++) {
                sQLiteInterfaceCompiledStatement.setObject(i, objArr[i], fieldTypeArr[i].getSqlType());
            }
            return sQLiteInterfaceCompiledStatement;
        }
        throw new SQLException("Values and types are not same size");
    }

    public int insert(String str, Object[] objArr, FieldType[] fieldTypeArr, GeneratedKeyHolder generatedKeyHolder) throws SQLException {
        Number executeInsert = DatabaseProvider.getInstance(this.provider).executeInsert(this.databaseId, getCompiledStatement(true, str, objArr, fieldTypeArr).getSQLCommand());
        if (generatedKeyHolder != null) {
            generatedKeyHolder.addKey(executeInsert);
        }
        return 1;
    }

    public int update(String str, Object[] objArr, FieldType[] fieldTypeArr) throws SQLException {
        DatabaseProvider.getInstance(this.provider).executeUpdate(this.databaseId, getCompiledStatement(true, str, objArr, fieldTypeArr).getSQLCommand());
        return 0;
    }

    public int delete(String str, Object[] objArr, FieldType[] fieldTypeArr) throws SQLException {
        DatabaseProvider.getInstance(this.provider).executeUpdate(this.databaseId, getCompiledStatement(true, str, objArr, fieldTypeArr).getSQLCommand());
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0040  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.lang.Object queryForOne(java.lang.String r5, java.lang.Object[] r6, com.j256.ormlite.field.FieldType[] r7, com.j256.ormlite.stmt.GenericRowMapper<T> r8, com.j256.ormlite.dao.ObjectCache r9) throws java.sql.SQLException {
        /*
            r4 = this;
            r0 = 0
            com.zkteco.android.core.library.Provider r1 = r4.provider     // Catch:{ all -> 0x003d }
            com.zkteco.android.core.interfaces.DatabaseInterface r1 = com.zkteco.android.core.interfaces.DatabaseProvider.getInstance(r1)     // Catch:{ all -> 0x003d }
            java.lang.String r2 = r4.databaseId     // Catch:{ all -> 0x003d }
            r3 = 0
            com.zkteco.android.db.orm.SQLiteInterfaceCompiledStatement r5 = r4.getCompiledStatement(r3, r5, r6, r7)     // Catch:{ all -> 0x003d }
            com.zkteco.android.core.interfaces.DatabaseInterface$SQLCommand r5 = r5.getSQLCommand()     // Catch:{ all -> 0x003d }
            android.database.Cursor r5 = r1.executeQuery(r2, r5)     // Catch:{ all -> 0x003d }
            com.zkteco.android.db.orm.SQLiteInterfaceDatabaseResults r6 = new com.zkteco.android.db.orm.SQLiteInterfaceDatabaseResults     // Catch:{ all -> 0x003a }
            r6.<init>(r5, r9)     // Catch:{ all -> 0x003a }
            boolean r7 = r6.first()     // Catch:{ all -> 0x003a }
            if (r7 == 0) goto L_0x0034
            java.lang.Object r0 = r8.mapRow(r6)     // Catch:{ all -> 0x003a }
            boolean r6 = r6.next()     // Catch:{ all -> 0x003a }
            if (r6 != 0) goto L_0x002c
            goto L_0x0034
        L_0x002c:
            java.sql.SQLException r6 = new java.sql.SQLException     // Catch:{ all -> 0x003a }
            java.lang.String r7 = "There are more rows than one!"
            r6.<init>(r7)     // Catch:{ all -> 0x003a }
            throw r6     // Catch:{ all -> 0x003a }
        L_0x0034:
            if (r5 == 0) goto L_0x0039
            r5.close()
        L_0x0039:
            return r0
        L_0x003a:
            r6 = move-exception
            r0 = r5
            goto L_0x003e
        L_0x003d:
            r6 = move-exception
        L_0x003e:
            if (r0 == 0) goto L_0x0043
            r0.close()
        L_0x0043:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.db.orm.SQLiteInterfaceConnection.queryForOne(java.lang.String, java.lang.Object[], com.j256.ormlite.field.FieldType[], com.j256.ormlite.stmt.GenericRowMapper, com.j256.ormlite.dao.ObjectCache):java.lang.Object");
    }

    private long queryForLong(DatabaseInterface.SQLCommand sQLCommand) {
        long j = -1;
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(this.provider).executeQuery(this.databaseId, sQLCommand);
            if (cursor.moveToNext()) {
                j = cursor.getLong(0);
            }
            return j;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public long queryForLong(String str) throws SQLException {
        DatabaseInterface.SQLCommand sQLCommand = new DatabaseInterface.SQLCommand();
        sQLCommand.setSql(str);
        return queryForLong(sQLCommand);
    }

    public long queryForLong(String str, Object[] objArr, FieldType[] fieldTypeArr) throws SQLException {
        return queryForLong(getCompiledStatement(false, str, objArr, fieldTypeArr).getSQLCommand());
    }

    public void closeQuietly() {
        this.isClosed = true;
    }

    public boolean isClosed() throws SQLException {
        return this.isClosed;
    }

    public boolean isTableExists(String str) throws SQLException {
        DatabaseInterface.SQLCommand sQLCommand = new DatabaseInterface.SQLCommand();
        sQLCommand.setSql(SQL_CHECK_TABLE);
        sQLCommand.setArguments(new String[]{str});
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(this.provider).executeQuery(this.databaseId, sQLCommand);
            return cursor.moveToNext();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void close() throws IOException {
        closeQuietly();
    }
}
