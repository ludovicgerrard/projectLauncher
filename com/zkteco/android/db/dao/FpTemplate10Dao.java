package com.zkteco.android.db.dao;

import android.text.TextUtils;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import java.util.ArrayList;
import java.util.List;

public class FpTemplate10Dao {
    public static List<FpTemplate10> getTemplates(String str) {
        return getTemplates(str, -1, -1, -1);
    }

    public static List<FpTemplate10> getTemplates(String str, int i, long j, long j2) {
        boolean z;
        List<FpTemplate10> queryForAll;
        ArrayList arrayList = new ArrayList();
        try {
            QueryBuilder queryBuilder = new FpTemplate10().getQueryBuilder();
            boolean z2 = true;
            if (j >= 0) {
                queryBuilder = queryBuilder.limit(Long.valueOf(j));
                z = true;
            } else {
                z = false;
            }
            if (j2 >= 0) {
                queryBuilder = queryBuilder.offset(Long.valueOf(j2));
            } else {
                z2 = z;
            }
            Where where = queryBuilder.where();
            if (!TextUtils.isEmpty(str)) {
                where = where.eq("pin", "'" + str + "'");
            }
            if (i != -1) {
                where = where.and().eq("fingerid", Integer.valueOf(i));
            }
            if (where != null) {
                queryForAll = where.query();
            } else if (z2) {
                queryForAll = queryBuilder.query();
            } else {
                queryForAll = new FpTemplate10().queryForAll();
            }
            return queryForAll;
        } catch (Exception e) {
            e.getStackTrace();
            return arrayList;
        }
    }
}
