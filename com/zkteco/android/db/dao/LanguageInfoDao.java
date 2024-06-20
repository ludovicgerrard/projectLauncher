package com.zkteco.android.db.dao;

import com.zkteco.android.core.interfaces.DatabaseInterface;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.LanguageInfo;
import com.zkteco.android.db.utils.FileLogUtils;
import java.util.ArrayList;

public class LanguageInfoDao {
    public static void resetLanguageInfoTable(DataManager dataManager) {
        DataManager dataManager2 = dataManager;
        if (dataManager2 != null) {
            try {
                FileLogUtils.writeAccResetLog("start resetLanguageInfoTable.");
                new LanguageInfo().clearTable();
                int[] iArr = {83, 69, 101, 80, 76, 73, 86, 97, 84, 74, 82, 75, 116, 119, 78, 65, 70, 66};
                String[] strArr = {"简体中文", "English", "Español(Latinoamérica)", "Protuguês(Brasil)", "ไทย", "Indonesia", "tiếng việt", "Español(España)", "繁体中文(香港)", "日本語の言語", "Русский", "한국의", "Türkçe", "繁体中文(台湾)", "Español(Argentina)", "فارسی", "Français", "العربية"};
                ArrayList arrayList = new ArrayList();
                for (int i = 1; i <= 18; i++) {
                    DatabaseInterface.SQLCommand sQLCommand = new DatabaseInterface.SQLCommand();
                    sQLCommand.setSql("insert into LANGUAGE_INFO(Language_Flag ,Language_Name, SEND_FLAG, ID) values(?,?,?,?) ");
                    int i2 = i - 1;
                    sQLCommand.setArguments(new String[]{String.valueOf(iArr[i2]), strArr[i2], "0", String.valueOf(i)});
                    arrayList.add(sQLCommand);
                }
                dataManager2.executeTransaction("ZKDB.db", arrayList);
                arrayList.clear();
                FileLogUtils.writeAccResetLog("finish resetLanguageInfoTable.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("resetLanguageInfoTable failed.\n" + e.getMessage());
            }
        }
    }
}
