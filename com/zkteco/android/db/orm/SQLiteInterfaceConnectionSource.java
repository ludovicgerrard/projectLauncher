package com.zkteco.android.db.orm;

import android.util.Log;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.zkteco.android.core.interfaces.DatabaseProvider;
import com.zkteco.android.core.library.Provider;
import java.io.IOException;
import java.sql.SQLException;

public class SQLiteInterfaceConnectionSource implements ConnectionSource {
    private static final String TAG = "SQLiteInterfaceConnectionSource";
    private SQLiteInterfaceConnection connection = null;
    private final String databaseId;
    private final Provider provider;
    private final int version;

    public boolean isSingleConnection(String str) {
        return true;
    }

    public boolean saveSpecialConnection(DatabaseConnection databaseConnection) throws SQLException {
        return true;
    }

    public SQLiteInterfaceConnectionSource(Provider provider2, String str, int i) {
        this.provider = provider2;
        this.databaseId = str;
        this.version = i;
        DatabaseProvider.getInstance(provider2).open(str, Integer.valueOf(i));
    }

    public DatabaseConnection getReadOnlyConnection(String str) throws SQLException {
        return getReadWriteConnection(str);
    }

    public DatabaseConnection getReadWriteConnection(String str) throws SQLException {
        if (this.connection == null) {
            this.connection = new SQLiteInterfaceConnection(this.provider, this.databaseId, this.version);
        }
        return this.connection;
    }

    public void releaseConnection(DatabaseConnection databaseConnection) throws SQLException {
        try {
            databaseConnection.close();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    public void clearSpecialConnection(DatabaseConnection databaseConnection) {
        try {
            releaseConnection(databaseConnection);
        } catch (SQLException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public DatabaseConnection getSpecialConnection(String str) {
        return this.connection;
    }

    public void closeQuietly() {
        SQLiteInterfaceConnection sQLiteInterfaceConnection = this.connection;
        if (sQLiteInterfaceConnection != null) {
            try {
                sQLiteInterfaceConnection.close();
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }

    public DatabaseType getDatabaseType() {
        return new SQLiteInterfaceDatabaseType();
    }

    public boolean isOpen(String str) {
        try {
            return !this.connection.isClosed();
        } catch (SQLException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }

    public void close() throws IOException {
        closeQuietly();
    }
}
