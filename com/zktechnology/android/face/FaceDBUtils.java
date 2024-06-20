package com.zktechnology.android.face;

import android.database.Cursor;
import android.util.Log;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.PersBiotemplatedata;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.liveface562.ZkFaceManager;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmService12;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FaceDBUtils {
    private static final boolean DEBUG = true;
    public static final int TEMPLATE_TYPE_FACE_VL = 9;

    public static void updateTemplateByUserPin(String str) {
        Log.i("LiveFace", "=================update Template start ==============");
        HashMap hashMap = new HashMap();
        Cursor queryBySql = DBManager.getInstance().queryBySql("select p2.id, p1.user_pin, p2.template_data from pers_biotemplate p1, pers_biotemplatedata p2 where p1.bio_type = 9 and p1.major_ver = 5 and p1.minor_ver = 622 and p1.template_id = p2.id and p2.id > 0 and p1.user_pin = '" + str + "'");
        if (queryBySql != null) {
            try {
                if (queryBySql.moveToFirst()) {
                    while (!queryBySql.isAfterLast()) {
                        String string = queryBySql.getString(1);
                        byte[] blob = queryBySql.getBlob(2);
                        Set set = (Set) hashMap.get(string);
                        if (set == null) {
                            set = new HashSet();
                        }
                        set.add(blob);
                        hashMap.put(string, set);
                        queryBySql.moveToNext();
                    }
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
        Log.i("LiveFace", "step : delete " + str + " ret=" + (ZkFaceManager.getInstance().dbDel(str) == 0));
        if (!hashMap.isEmpty()) {
            for (Map.Entry entry : hashMap.entrySet()) {
                String str2 = (String) entry.getKey();
                for (byte[] bArr : (Set) entry.getValue()) {
                    if (bArr != null && bArr.length > 0) {
                        Log.d("LiveFace", "step insert:  userId= " + str2 + " ,ret=" + (ZkFaceManager.getInstance().dbAdd(str, bArr) == 0));
                    }
                }
            }
        }
        Log.i("LiveFace", "=================update Template end ==============");
    }

    public static void deleteUserTemplate(String str) {
        Log.i("LiveFace", "=================delete Template start ==============");
        ZkFaceManager.getInstance().dbDel(str);
        deleteFaceTemplate(str);
        Log.i("LiveFace", "=================delete Template end ==============");
    }

    public static void deleteFaceTemplate(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            List query = new PersBiotemplate().getQueryBuilder().where().eq("user_pin", str).and().eq("bio_type", 9).query();
            if (query.size() > 0) {
                for (int i = 0; i < query.size(); i++) {
                    arrayList.add(Integer.valueOf(((PersBiotemplate) query.get(i)).getTemplate_id()));
                    ((PersBiotemplate) query.get(i)).delete();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    List query2 = new PersBiotemplatedata().getQueryBuilder().where().eq("id", (Integer) it.next()).query();
                    if (query2.size() > 0) {
                        for (int i2 = 0; i2 < query2.size(); i2++) {
                            ((PersBiotemplatedata) query2.get(i2)).delete();
                        }
                    }
                }
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public static boolean isFaceAdd(String str) {
        UserInfo userInfo;
        try {
            userInfo = (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", str).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            userInfo = null;
        }
        if (userInfo != null) {
            return true;
        }
        try {
            UserInfo userInfo2 = new UserInfo();
            userInfo2.setName("onlineTest");
            userInfo2.setUser_PIN(str);
            userInfo2.setPrivilege(0);
            userInfo2.create();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public static void saveTemplateVL(String str, byte[] bArr, int i, int i2, boolean z) {
        DataManager instance = DBManager.getInstance();
        if (!z) {
            insertTemplate(instance, str, bArr, i, i2);
        } else {
            updateTemplate(instance, str, bArr, i, i2);
        }
    }

    public static void updateTemplate(DataManager dataManager, String str, byte[] bArr, int i, int i2) {
        if (bArr != null) {
            Cursor cursor = null;
            int i3 = -1;
            try {
                cursor = dataManager.queryBySql("select template_id from Pers_BioTemplate where user_pin='" + str + "' and major_ver= " + i + " and minor_ver=" + i2 + " and bio_type=" + 9);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        i3 = cursor.getInt(0);
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
            if (i3 > 0) {
                dataManager.executeSql("ZKDB.db", "update Pers_BioTemplateData set template_data = ? where id = ?", new Object[]{bArr, Integer.valueOf(i3)});
                return;
            }
            insertTemplate(dataManager, str, bArr, i, i2);
        }
    }

    public static void insertTemplate(DataManager dataManager, String str, byte[] bArr, int i, int i2) {
        if (bArr != null) {
            dataManager.executeSql("ZKDB.db", "insert into Pers_BioTemplateData(template_data, CREATE_ID, MODIFY_TIME, SEND_FLAG) values(?,?,?,?)", new Object[]{bArr, 0, 0, 0});
            Cursor cursor = null;
            int i3 = -1;
            try {
                cursor = dataManager.queryBySql("select max(id) from Pers_BioTemplateData");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        i3 = cursor.getInt(0);
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
            if (i3 > 0) {
                dataManager.executeSql("ZKDB.db", "insert into Pers_BioTemplate(user_pin, template_no_index,template_id, bio_type, major_ver, minor_ver, send_flag, template_no) values(?,?,?,?,?,?,?,?)", new Object[]{str, 0, Integer.valueOf(i3), 9, Integer.valueOf(i), Integer.valueOf(i2), 0, 0});
            }
        }
    }

    public static void deleteUserTemplateInFaceAnalyzer(String str) {
        Log.i("LiveFace", "=================delete Template start ==============");
        ZkFaceManager.getInstance().dbDel(str);
        deleteFaceTemplate(str);
        Log.i("LiveFace", "=================delete Template end ==============");
    }

    public static List<PersBiotemplate> getBiotemplateByUserId(String str, int i, int i2, int i3) {
        try {
            return new PersBiotemplate().getQueryBuilder().where().eq("user_pin", str).and().eq("bio_type", Integer.valueOf(i)).and().eq("major_ver", Integer.valueOf(i2)).and().eq("minor_ver", Integer.valueOf(i3)).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void clearBiotemplateData() {
        int dbClear = ZkFaceManager.getInstance().dbClear();
        int dbClear2 = ZKPalmService12.dbClear();
        Log.i("LiveFace", "clear Template :" + dbClear);
        Log.i("Palm", "clear Template :" + dbClear2);
    }
}
