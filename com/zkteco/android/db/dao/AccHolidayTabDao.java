package com.zkteco.android.db.dao;

import com.zkteco.android.db.orm.tna.AccHolidayTab;
import com.zkteco.android.db.utils.FileLogUtils;

public class AccHolidayTabDao {
    public static void deleteHoliday() {
        try {
            FileLogUtils.writeAccResetLog("start delete holiday.");
            new AccHolidayTab().clearTable();
            FileLogUtils.writeAccResetLog("finish delete holiday.");
        } catch (Exception e) {
            e.printStackTrace();
            FileLogUtils.writeAccResetLog("delete holiday failed.\n" + e.getMessage());
        }
    }
}
