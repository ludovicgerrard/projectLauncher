package com.zkteco.android.db.orm;

import android.database.Cursor;
import android.util.Log;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.support.DatabaseResults;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

class SQLiteInterfaceDatabaseResults implements DatabaseResults {
    private static final String TAG = "SQLiteInterfaceDatabaseResults";
    private final ObjectCache cache;
    private final Cursor cursor;

    SQLiteInterfaceDatabaseResults(Cursor cursor2, ObjectCache objectCache) {
        this.cursor = cursor2;
        this.cache = objectCache;
    }

    public int getColumnCount() throws SQLException {
        return this.cursor.getColumnCount();
    }

    public String[] getColumnNames() throws SQLException {
        return this.cursor.getColumnNames();
    }

    public boolean first() throws SQLException {
        return this.cursor.moveToFirst();
    }

    public boolean previous() throws SQLException {
        return this.cursor.moveToPrevious();
    }

    public boolean next() throws SQLException {
        return this.cursor.moveToNext();
    }

    public boolean last() throws SQLException {
        return this.cursor.moveToLast();
    }

    public boolean moveRelative(int i) throws SQLException {
        return this.cursor.move(i);
    }

    public boolean moveAbsolute(int i) throws SQLException {
        return this.cursor.moveToPosition(i);
    }

    public int findColumn(String str) throws SQLException {
        return this.cursor.getColumnIndex(str);
    }

    public String getString(int i) throws SQLException {
        return this.cursor.getString(i);
    }

    public boolean getBoolean(int i) throws SQLException {
        return this.cursor.getInt(i) == 1;
    }

    public char getChar(int i) throws SQLException {
        return this.cursor.getString(i).toCharArray()[0];
    }

    public byte getByte(int i) throws SQLException {
        return (byte) this.cursor.getInt(i);
    }

    public byte[] getBytes(int i) throws SQLException {
        return this.cursor.getBlob(i);
    }

    public short getShort(int i) throws SQLException {
        return this.cursor.getShort(i);
    }

    public int getInt(int i) throws SQLException {
        return this.cursor.getInt(i);
    }

    public long getLong(int i) throws SQLException {
        return this.cursor.getLong(i);
    }

    public float getFloat(int i) throws SQLException {
        return this.cursor.getFloat(i);
    }

    public double getDouble(int i) throws SQLException {
        return this.cursor.getDouble(i);
    }

    public Timestamp getTimestamp(int i) throws SQLException {
        return new Timestamp(this.cursor.getLong(i));
    }

    public InputStream getBlobStream(int i) throws SQLException {
        throw new SQLException("Unsupported operation getBlobStream()");
    }

    public BigDecimal getBigDecimal(int i) throws SQLException {
        return new BigDecimal(this.cursor.getString(i));
    }

    public Object getObject(int i) throws SQLException {
        return this.cursor.getString(i);
    }

    public boolean wasNull(int i) throws SQLException {
        return this.cursor.isNull(i);
    }

    public ObjectCache getObjectCacheForRetrieve() {
        return this.cache;
    }

    public ObjectCache getObjectCacheForStore() {
        return this.cache;
    }

    public void closeQuietly() {
        try {
            close();
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public void close() throws IOException {
        Cursor cursor2 = this.cursor;
        if (cursor2 != null && !cursor2.isClosed()) {
            this.cursor.close();
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
