package com.zkteco.android.db.dao;

import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.AccTimeZoneRule;
import com.zkteco.android.db.utils.FileLogUtils;
import java.util.List;

public class AccTimeZoneRuleDao {
    public static List<AccTimeZoneRule> queryDefaultAccTimeZoneRule() {
        try {
            return new AccTimeZoneRule().getQueryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void resetAccTimeZoneRuleTable(DataManager dataManager) {
        if (dataManager != null) {
            try {
                FileLogUtils.writeAccResetLog("start resetAccTimeZoneRuleTable.");
                new AccTimeZoneRule().clearTable();
                for (int i = 1; i <= 50; i++) {
                    dataManager.executeSql("ZKDB.db", "insert into ACC_TIME_ZONE_RULE(Time_Zone_ID, Type, SEND_FLAG, ID) values(?,?,?,?) ", new Object[]{Integer.valueOf(i), 0, 0, Integer.valueOf(i)});
                }
                FileLogUtils.writeAccResetLog("finish resetAccTimeZoneRuleTable.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("resetAccTimeZoneRuleTable failed.\n" + e.getMessage());
            }
        }
    }
}
