package com.zkteco.android.employeemgmt.util;

import android.database.Cursor;
import com.zktechnology.android.utils.GlobalConfig;
import com.zkteco.android.db.orm.contants.BiometricCommuCMD;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.PersBiotemplatedata;
import com.zkteco.android.employeemgmt.face.EnrollVLFaceInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SQLiteFaceUtils {
    public static final int TEMPLATE_TYPE_FACE_VL = 9;

    public static boolean isFaceAlreadyInDB(DataManager dataManager, String str, int i, int i2, int i3) {
        Cursor queryBySql = dataManager.queryBySql("select p2.template_data from pers_biotemplate p1, pers_biotemplatedata p2 where p1.bio_type = " + i + " and p1.user_pin = '" + str + "' and p1.major_ver = " + i2 + " and p1.minor_ver = " + i3 + " and p1.template_id = p2.id and p2.id > 0");
        boolean z = false;
        if (queryBySql != null) {
            try {
                if (queryBySql.moveToFirst()) {
                    boolean z2 = false;
                    while (!queryBySql.isAfterLast()) {
                        byte[] blob = queryBySql.getBlob(0);
                        if (blob != null && blob.length > 0) {
                            z2 = true;
                        }
                        queryBySql.moveToNext();
                    }
                    z = z2;
                }
            } catch (Throwable th) {
                if (queryBySql != null) {
                    queryBySql.close();
                }
                throw th;
            }
        }
        if (queryBySql != null) {
            queryBySql.close();
        }
        return z;
    }

    public static void saveTemplateVL(DataManager dataManager, String str, ArrayList<EnrollVLFaceInfo> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getTemplate() != null) {
                dataManager.executeSql("ZKDB.db", "insert into Pers_BioTemplateData(template_data, CREATE_ID, MODIFY_TIME, SEND_FLAG) values(?,?,?,?)", new Object[]{arrayList.get(i).getTemplate(), 0, 0, 0});
                Cursor cursor = null;
                int i2 = -1;
                try {
                    cursor = dataManager.queryBySql("select max(id) from pers_biotemplatedata");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cursor != null) {
                    try {
                        if (cursor.moveToFirst()) {
                            i2 = cursor.getInt(0);
                        }
                    } catch (Throwable th) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
                if (i2 > 0) {
                    dataManager.executeSql("ZKDB.db", "insert into Pers_BioTemplate(user_pin, template_no_index,template_id, bio_type, major_ver, minor_ver, send_flag, template_no) values(?,?,?,?,?,?,?,?)", new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2), 9, 5, Integer.valueOf(GlobalConfig.FACE_ALGO_MINOR_VER), 1, 0});
                }
            }
        }
    }

    public static void updateTemplateVL(DataManager dataManager, String str, ArrayList<EnrollVLFaceInfo> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        try {
            List query = new PersBiotemplate().getQueryBuilder().where().eq("user_pin", str).and().eq("bio_type", Integer.toString(9)).and().eq("major_ver", Integer.toString(5)).and().eq("minor_ver", Integer.toString(GlobalConfig.FACE_ALGO_MINOR_VER)).query();
            if (query.size() > 0) {
                for (int i = 0; i < query.size(); i++) {
                    arrayList2.add(Integer.valueOf(((PersBiotemplate) query.get(i)).getTemplate_id()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!arrayList2.isEmpty()) {
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                if (((Integer) arrayList2.get(i2)).intValue() > 0) {
                    if (i2 != arrayList.size()) {
                        dataManager.executeSql("ZKDB.db", "update pers_biotemplate set send_flag = ? where template_id = ? and bio_type = ? and major_ver = ? and minor_ver = ?", new Object[]{1, arrayList2.get(i2), 9, 5, Integer.valueOf(GlobalConfig.FACE_ALGO_MINOR_VER)});
                        dataManager.executeSql("ZKDB.db", "update Pers_BioTemplateData set template_data = ?, CREATE_ID = ?, MODIFY_TIME = ?, SEND_FLAG = ? where ID = ?", new Object[]{arrayList.get(i2).getTemplate(), 0, 0, 0, arrayList2.get(i2)});
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public static void deleteFaceOrPalmTemplate(String str, int i) {
        ArrayList arrayList = new ArrayList();
        try {
            List query = new PersBiotemplate().getQueryBuilder().where().eq("user_pin", str).and().eq("bio_type", Integer.valueOf(i)).query();
            if (query.size() > 0) {
                for (int i2 = 0; i2 < query.size(); i2++) {
                    arrayList.add(Integer.valueOf(((PersBiotemplate) query.get(i2)).getTemplate_id()));
                    ((PersBiotemplate) query.get(i2)).delete();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    List query2 = new PersBiotemplatedata().getQueryBuilder().where().eq(BiometricCommuCMD.FIELD_DESC_TMP_ID, (Integer) it.next()).query();
                    if (query2.size() > 0) {
                        for (int i3 = 0; i3 < query2.size(); i3++) {
                            ((PersBiotemplatedata) query2.get(i3)).delete();
                        }
                    }
                }
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}
