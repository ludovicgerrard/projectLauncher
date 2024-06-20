package com.zkteco.android.db.dao;

import com.zkteco.android.core.interfaces.DatabaseInterface;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.AccTimeZone;
import com.zkteco.android.db.utils.FileLogUtils;
import java.util.ArrayList;

public class AccTimeZoneDao {
    public static void resetAccTimeZoneTable(DataManager dataManager) {
        if (dataManager != null) {
            try {
                FileLogUtils.writeAccResetLog("start resetAccTimeZoneTable.");
                new AccTimeZone().clearTable();
                ArrayList arrayList = new ArrayList();
                for (int i = 1; i <= 1500; i++) {
                    String[] strArr = {"0", "2359", "0", String.valueOf(i)};
                    DatabaseInterface.SQLCommand sQLCommand = new DatabaseInterface.SQLCommand();
                    sQLCommand.setArguments(strArr);
                    sQLCommand.setSql("insert into ACC_TIME_ZONE(Start_Time ,End_Time, SEND_FLAG, ID) values(?,?,?,?) ");
                    arrayList.add(sQLCommand);
                    if (arrayList.size() >= 100) {
                        dataManager.executeTransaction("ZKDB.db", arrayList);
                        arrayList.clear();
                    }
                }
                if (arrayList.size() > 0) {
                    dataManager.executeTransaction("ZKDB.db", arrayList);
                    arrayList.clear();
                }
                FileLogUtils.writeAccResetLog("finish resetAccTimeZoneTable.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("resetAccTimeZoneTable failed.\n" + e.getMessage());
            }
        }
    }
}
