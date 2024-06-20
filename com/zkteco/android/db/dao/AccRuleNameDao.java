package com.zkteco.android.db.dao;

import com.zkteco.android.core.interfaces.DatabaseInterface;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.AccDatOthername;
import com.zkteco.android.db.orm.tna.AccRuleName;
import com.zkteco.android.db.orm.tna.AccTimeZoneRule;
import com.zkteco.android.db.utils.FileLogUtils;
import java.util.ArrayList;
import java.util.List;

public class AccRuleNameDao {
    public static void resetAccRuleNameTable(DataManager dataManager) {
        if (dataManager != null) {
            try {
                FileLogUtils.writeAccResetLog("start resetAccRuleNameTable.");
                new AccRuleName().clearTable();
                List<AccDatOthername> queryDefaultAccDatName = AccDatOtherNameDao.queryDefaultAccDatName();
                if (queryDefaultAccDatName == null) {
                    FileLogUtils.writeAccResetLog("start resetAccRuleNameTable failed, accDatOthernames is null.");
                    return;
                }
                List<AccTimeZoneRule> queryDefaultAccTimeZoneRule = AccTimeZoneRuleDao.queryDefaultAccTimeZoneRule();
                if (queryDefaultAccTimeZoneRule == null) {
                    FileLogUtils.writeAccResetLog("start resetAccRuleNameTable failed, accTimeZoneRules is null.");
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < queryDefaultAccDatName.size(); i++) {
                    for (int i2 = 0; i2 < queryDefaultAccTimeZoneRule.size(); i2++) {
                        String[] strArr = {String.valueOf(queryDefaultAccTimeZoneRule.get(i2).getID()), String.valueOf(queryDefaultAccDatName.get(i).getID()), "0", String.valueOf((queryDefaultAccDatName.size() * i2) + i + 1)};
                        DatabaseInterface.SQLCommand sQLCommand = new DatabaseInterface.SQLCommand();
                        sQLCommand.setArguments(strArr);
                        sQLCommand.setSql("insert into ACC_RULE_NAME(Rule_ID ,Name_ID, SEND_FLAG, ID) values(?,?,?,?) ");
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
                queryDefaultAccDatName.clear();
                queryDefaultAccTimeZoneRule.clear();
                FileLogUtils.writeAccResetLog("finish resetAccRuleNameTable.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("resetAccRuleNameTable failed.\n" + e.getMessage());
            }
        }
    }
}
