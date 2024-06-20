package com.zktechnology.android.verify.dao;

import android.database.Cursor;
import androidx.exifinterface.media.ExifInterface;
import com.zktechnology.android.utils.StrUtil;
import com.zktechnology.android.verify.bean.ZKPersonMsg;
import com.zkteco.android.db.orm.manager.DataManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ZKPersonMsgDao {
    private DataManager db;

    public ZKPersonMsgDao(DataManager dataManager) {
        this.db = dataManager;
    }

    public List<ZKPersonMsg> queryPersonMsg(String str) {
        ArrayList arrayList = new ArrayList();
        Cursor queryBySql = this.db.queryBySql("SELECT si.Content, si.Start_Time, si.Valid_Time FROM USER_SMS us LEFT JOIN SMS_INFO si ON us.Sms_ID = si.ID WHERE us.User_PIN = '" + str + "' ORDER BY si.Start_Time DESC");
        if (queryBySql.moveToFirst()) {
            do {
                String string = queryBySql.getString(queryBySql.getColumnIndex("Start_Time"));
                int i = queryBySql.getInt(queryBySql.getColumnIndex("Valid_Time"));
                String string2 = queryBySql.getString(queryBySql.getColumnIndex("Content"));
                if (expires(string, i)) {
                    arrayList.add(new ZKPersonMsg(string.replace(ExifInterface.GPS_DIRECTION_TRUE, " "), i, string2));
                }
            } while (queryBySql.moveToNext());
        }
        queryBySql.close();
        return arrayList;
    }

    private boolean expires(String str, int i) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            if (StrUtil.isEmpty(str) || !str.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                return false;
            }
            Date date = new Date();
            Date parse = simpleDateFormat.parse(str.replace(ExifInterface.GPS_DIRECTION_TRUE, " "));
            Date date2 = new Date(parse.getTime() + ((long) (i * 1000 * 60)));
            if (i == 0) {
                if (date.getTime() >= parse.getTime()) {
                    return true;
                }
                return false;
            } else if (date.getTime() < parse.getTime() || date.getTime() > date2.getTime()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
