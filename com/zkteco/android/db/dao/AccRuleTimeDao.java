package com.zkteco.android.db.dao;

import com.zkteco.android.core.interfaces.DatabaseInterface;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.AccRuleTime;
import com.zkteco.android.db.utils.FileLogUtils;
import java.util.ArrayList;

public class AccRuleTimeDao {
    public static void resetAccRuleTimeTable(DataManager dataManager) {
        if (dataManager != null) {
            try {
                FileLogUtils.writeAccResetLog("start resetAccRuleTimeTable.");
                new AccRuleTime().clearTable();
                ArrayList arrayList = new ArrayList();
                int i = 0;
                for (int i2 = 1; i2 <= 500; i2++) {
                    String valueOf = String.valueOf(i2);
                    for (int i3 = 0; i3 < 3; i3++) {
                        i++;
                        String valueOf2 = String.valueOf(i);
                        DatabaseInterface.SQLCommand sQLCommand = new DatabaseInterface.SQLCommand();
                        sQLCommand.setArguments(new String[]{valueOf, valueOf2, "0", valueOf2});
                        sQLCommand.setSql("insert into ACC_RULE_TIME(Rule_Name_ID ,Time_ID, SEND_FLAG, ID) values(?,?,?,?) ");
                        arrayList.add(sQLCommand);
                        if (arrayList.size() >= 100) {
                            dataManager.executeTransaction("ZKDB.db", arrayList);
                            arrayList.clear();
                        }
                    }
                }
                if (arrayList.size() > 0) {
                    dataManager.executeTransaction("ZKDB.db", arrayList);
                    arrayList.clear();
                }
                FileLogUtils.writeAccResetLog("finish resetAccRuleTimeTable.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("resetAccRuleTimeTable failed.\n" + e.getMessage());
            }
        }
    }
}
