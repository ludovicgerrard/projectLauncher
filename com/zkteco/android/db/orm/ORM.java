package com.zkteco.android.db.orm;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ORM<K, V> {
    private static final Map<String, ConnectionSource> sourceMap = new HashMap();
    private final Class<K> clazz;

    public String getColumnOrderByPurge() {
        return null;
    }

    public String getTableName() {
        return null;
    }

    public ORM(Class<K> cls) {
        this.clazz = cls;
    }

    public final void createTable(String str) throws SQLException {
        TableUtils.createTable(getDao(str).getConnectionSource(), this.clazz);
    }

    public final void createTableIfNotExists(String str) throws SQLException {
        TableUtils.createTableIfNotExists(getDao(str).getConnectionSource(), this.clazz);
    }

    public final void deleteTable(String str) throws SQLException {
        TableUtils.dropTable(getDao(str).getConnectionSource(), this.clazz, true);
    }

    public final void clearTable(String str) throws SQLException {
        TableUtils.clearTable(getDao(str).getConnectionSource(), this.clazz);
    }

    public final Dao<K, V> getDao(String str) throws SQLException {
        ConnectionSource connectionSource = sourceMap.get(str);
        if (connectionSource != null) {
            return DaoManager.createDao(connectionSource, this.clazz);
        }
        throw new IllegalStateException(String.format("There is no defined connection source for %s database!", new Object[]{str}));
    }

    public static void addSource(String str, ConnectionSource connectionSource) throws SQLException {
        sourceMap.put(str, connectionSource);
    }

    public final void create(String str) throws SQLException {
        getDao(str).create(this);
    }

    public final K createIfNotExists(String str) throws SQLException {
        return getDao(str).createIfNotExists(this);
    }

    public final void createOrUpdate(String str) throws SQLException {
        getDao(str).createOrUpdate(this);
    }

    public final void update(String str) throws SQLException {
        getDao(str).update(this);
    }

    public final void delete(String str) throws SQLException {
        getDao(str).delete(this);
    }

    public List<K> queryForAll(String str) throws SQLException {
        return getDao(str).queryForAll();
    }

    public K queryForId(String str, V v) throws SQLException {
        return getDao(str).queryForId(v);
    }

    public long countOf(String str) throws SQLException {
        return getDao(str).countOf();
    }

    public final void purge(String str, long j) throws SQLException {
        String columnOrderByPurge = getColumnOrderByPurge();
        if (columnOrderByPurge != null) {
            Dao dao = getDao(str);
            long countOf = dao.countOf();
            if (countOf > j) {
                dao.delete(dao.queryBuilder().orderBy(columnOrderByPurge, true).limit(Long.valueOf(countOf - j)).query());
                return;
            }
            return;
        }
        throw new SQLException("No column to purge define, override \"getColumnOrderByPurge\"");
    }
}
