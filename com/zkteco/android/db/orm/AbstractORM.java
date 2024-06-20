package com.zkteco.android.db.orm;

import android.os.Environment;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zkteco.android.db.utils.FileLogUtils;
import com.zkteco.util.YAMLHelper;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractORM<K> implements LongId, Serializable {
    private static final Map<String, ConnectionSource> sourceMap = new HashMap();
    @DatabaseField(generatedId = true)
    protected long ID;
    private Class<K> clazz;
    private Dao<K, Long> dao;

    public String getDatabaseId() {
        return "";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "";
    }

    public AbstractORM(Class<K> cls) {
        this.clazz = cls;
    }

    public long getID() {
        return this.ID;
    }

    public static void addSource(String str, ConnectionSource connectionSource) throws SQLException {
        Map<String, ConnectionSource> map = sourceMap;
        if (!map.containsKey(str)) {
            map.put(str, connectionSource);
        }
    }

    private Dao<K, Long> getDao() throws SQLException {
        ConnectionSource connectionSource = sourceMap.get(getDatabaseId());
        if (connectionSource != null) {
            DaoManager.clearCache();
            Dao<K, Long> createDao = DaoManager.createDao(connectionSource, this.clazz);
            this.dao = createDao;
            return createDao;
        }
        throw new SQLException("Please make sure you set connectionSource before using ORM features!");
    }

    public K createTable() throws SQLException {
        TableUtils.createTable(getDao().getConnectionSource(), this.clazz);
        return this;
    }

    public K createTableIfNotExists() throws SQLException {
        TableUtils.createTableIfNotExists(getDao().getConnectionSource(), this.clazz);
        return this;
    }

    public K deleteTable() throws SQLException {
        TableUtils.dropTable(getDao().getConnectionSource(), this.clazz, true);
        return this;
    }

    public K clearTable() throws SQLException {
        TableUtils.clearTable(getDao().getConnectionSource(), this.clazz);
        return this;
    }

    public K create() throws SQLException {
        getDao().create(this);
        return this;
    }

    public K createIfNotExists() throws SQLException {
        getDao().createIfNotExists(this);
        return this;
    }

    public K createOrUpdate() throws SQLException {
        getDao().createOrUpdate(this);
        return this;
    }

    public K update() throws SQLException {
        getDao().update(this);
        return this;
    }

    public K delete() throws SQLException {
        getDao().delete(this);
        return this;
    }

    public List<K> queryForAll() throws SQLException {
        return getDao().queryForAll();
    }

    public K queryForId(Long l) throws SQLException {
        return getDao().queryForId(l);
    }

    public QueryBuilder<K, Long> getQueryBuilder() throws SQLException {
        return getDao().queryBuilder();
    }

    public long countOf() throws SQLException {
        return getDao().countOf();
    }

    public void createTableWithDefaults() {
        try {
            createTable();
            List<AbstractORM> defaultValues = getDefaultValues();
            if (defaultValues != null) {
                for (AbstractORM create : defaultValues) {
                    create.create();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeDatabaseLog(this.clazz.getCanonicalName() + " createTableWithDefaults error:\n" + e.getMessage());
        }
    }

    public List<AbstractORM<K>> getDefaultValues() {
        try {
            return (List) YAMLHelper.getInstanceFromFile((Environment.getExternalStorageDirectory().getPath() + "/config/orm/") + getYAMLFilePathForDefaultValues());
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeDatabaseLog(this.clazz.getCanonicalName() + " getDefaultValues error:\n" + e.getMessage());
            return null;
        }
    }
}
