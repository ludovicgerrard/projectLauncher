package com.zktechnology.android.verify.dao;

import android.database.Cursor;
import androidx.exifinterface.media.ExifInterface;
import com.zktechnology.android.utils.StrUtil;
import com.zktechnology.android.verify.bean.ZKAttRecord;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;
import java.util.ArrayList;
import java.util.List;

public class ZKAttRecordDao {
    private DataManager db;

    public ZKAttRecordDao(DataManager dataManager) {
        this.db = dataManager;
    }

    public List<ZKAttRecord> queryAttRecord(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        Cursor queryBySql = this.db.queryBySql("SELECT al.Verify_Time,al.Status,al.Work_Code_ID, ss.State_No,ss.State_Name,ss.Description, wc.ID,wc.Work_Code_Num,wc.Work_Code_Name  FROM ATT_LOG al LEFT JOIN SHORT_STATE ss ON al.Status = ss.State_No  LEFT JOIN WORK_CODE wc ON al.Work_Code_ID = wc.ID  WHERE al.User_PIN = '" + str + "' AND Verify_Time  BETWEEN datetime('now','start of day')  AND datetime('now','start of day','+1 day' )  ORDER BY al.Verify_Time DESC");
        if (queryBySql.moveToFirst()) {
            do {
                String string = queryBySql.getString(queryBySql.getColumnIndex("Verify_Time"));
                int i = queryBySql.getInt(queryBySql.getColumnIndex(WiegandConfig.COLUMN_STATUS));
                int i2 = queryBySql.getInt(queryBySql.getColumnIndex("Work_Code_ID"));
                int i3 = queryBySql.getInt(queryBySql.getColumnIndex("State_No"));
                String string2 = queryBySql.getString(queryBySql.getColumnIndex("State_Name"));
                String string3 = queryBySql.getString(queryBySql.getColumnIndex("Description"));
                int i4 = queryBySql.getInt(queryBySql.getColumnIndex("id"));
                String string4 = queryBySql.getString(queryBySql.getColumnIndex("Work_Code_Num"));
                String string5 = queryBySql.getString(queryBySql.getColumnIndex("Work_Code_Name"));
                ZKAttRecord zKAttRecord = new ZKAttRecord();
                zKAttRecord.setName(StrUtil.parseEmpty(str2));
                zKAttRecord.setUserPin(StrUtil.parseEmpty(str));
                zKAttRecord.setVerify_Time(StrUtil.parseEmpty(string.replace(ExifInterface.GPS_DIRECTION_TRUE, " ")));
                zKAttRecord.setStatus(i);
                zKAttRecord.setWork_Code_ID(i2);
                zKAttRecord.setState_No(i3);
                zKAttRecord.setState_Name(StrUtil.parseEmpty(string2));
                zKAttRecord.setDescription(string3);
                zKAttRecord.setId(i4);
                zKAttRecord.setWork_Code_Num(StrUtil.parseEmpty(string4));
                zKAttRecord.setWork_Code_Name(StrUtil.parseEmpty(string5));
                arrayList.add(zKAttRecord);
            } while (queryBySql.moveToNext());
        }
        queryBySql.close();
        return arrayList;
    }
}
