package com.zktechnology.android.verify.dao;

import android.content.Context;
import android.database.Cursor;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zkteco.android.db.orm.manager.DataManager;

public class ZKErrorLogDao {
    private static final String TAG = "Access--ZKErrorLogDao";
    private static ZKErrorLogDao zKErrorLogDaoInstance;
    private Context mContext;
    private DataManager mDb = DBManager.getInstance();

    private ZKErrorLogDao(Context context) {
    }

    public static synchronized ZKErrorLogDao getInstance(Context context) {
        ZKErrorLogDao zKErrorLogDao;
        synchronized (ZKErrorLogDao.class) {
            if (zKErrorLogDaoInstance == null) {
                zKErrorLogDaoInstance = new ZKErrorLogDao(context);
            }
            zKErrorLogDao = zKErrorLogDaoInstance;
        }
        return zKErrorLogDao;
    }

    public int getErrorLogCount() {
        int i = 0;
        Cursor cursor = null;
        try {
            Cursor queryBySql = this.mDb.queryBySql("SELECT COUNT(*) as count FROM ERROR_LOG");
            if (queryBySql.moveToFirst()) {
                i = queryBySql.getInt(queryBySql.getColumnIndex("count"));
            }
            if (queryBySql != null && !queryBySql.isClosed()) {
                queryBySql.close();
            }
            return i;
        } catch (Exception e) {
            LogUtils.e(TAG, "Exc: " + e.getMessage());
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return 0;
        } catch (Throwable th) {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
    }

    public void deleteErrorLog(int i) {
        try {
            Cursor queryBySql = this.mDb.queryBySql("DELETE FROM ERROR_LOG WHERE id IN(SELECT id FROM ERROR_LOG ORDER BY id ASC LIMIT " + i + ")");
            if (queryBySql != null && !queryBySql.isClosed()) {
                queryBySql.close();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "Exc: " + e.getMessage());
        }
    }
}
