package com.zkteco.android.db.dao;

import androidx.exifinterface.media.ExifInterface;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.AccDatOthername;
import com.zkteco.android.db.utils.FileLogUtils;
import java.util.List;

public class AccDatOtherNameDao {
    public static List<AccDatOthername> queryDefaultAccDatName() {
        try {
            return new AccDatOthername().getQueryBuilder().where().eq("Name", "sun").or().eq("Name", "mon").or().eq("Name", "tue").or().eq("Name", "wed").or().eq("Name", "thu").or().eq("Name", "fri").or().eq("Name", "sat").or().eq("Name", "hol1").or().eq("Name", "hol2").or().eq("Name", "hol3").query();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void resetAccDatOthernameTable(DataManager dataManager) {
        DataManager dataManager2 = dataManager;
        if (dataManager2 != null) {
            try {
                FileLogUtils.writeAccResetLog("start resetAccTimeZoneRuleTable.");
                new AccDatOthername().clearTable();
                String[] strArr = {"sun", "mon", "tue", "wed", "thu", "fri", "sat", "1", "2", ExifInterface.GPS_MEASUREMENT_3D, "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "hol1", "hol2", "hol3"};
                for (int i = 1; i < 42; i++) {
                    dataManager2.executeSql("ZKDB.db", "insert into ACC_DAT_OTHERNAME(Name,  SEND_FLAG, ID) values(?,?,?) ", new Object[]{strArr[i - 1], 0, Integer.valueOf(i)});
                }
                FileLogUtils.writeAccResetLog("finish resetAccTimeZoneRuleTable.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("resetAccTimeZoneRuleTable failed.\n" + e.getMessage());
            }
        }
    }
}
