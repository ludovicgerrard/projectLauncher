package com.zkteco.android.db.dao;

import com.zkteco.android.core.interfaces.DatabaseInterface;
import com.zkteco.android.db.orm.manager.DataManager;
import java.util.ArrayList;

public class AccTimeZoneWeekDao {
    public static void resetAccTimeZoneWeekTable(DataManager dataManager) {
        if (dataManager != null) {
            ArrayList arrayList = new ArrayList();
            for (int i = 1; i <= 50; i++) {
                String[] strArr = {String.valueOf(i), "0", "2359", "0", "2359", "0", "2359", "0", "2359", "0", "2359", "0", "2359", "0", "2359"};
                DatabaseInterface.SQLCommand sQLCommand = new DatabaseInterface.SQLCommand();
                sQLCommand.setArguments(strArr);
                sQLCommand.setSql("insert into acc_timezone( ID, SunStart, SunEnd, MonStart, MonEnd, TuesStart, TuesEnd, WedStart, WedEnd, ThursStart, ThursEnd, FriStart, FriEnd, SatStart, SatEnd)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                arrayList.add(sQLCommand);
            }
            dataManager.executeTransaction("ZKDB.db", arrayList);
            arrayList.clear();
        }
    }
}
