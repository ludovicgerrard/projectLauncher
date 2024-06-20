package com.zkteco.android.employeemgmt.util;

import com.zkteco.android.db.orm.tna.PersBiotemplate;

public class ZkPalmUtils {
    public static boolean isHasPv(String str) {
        if (str != null && !str.isEmpty()) {
            try {
                if (((PersBiotemplate) new PersBiotemplate().getQueryBuilder().where().eq("user_pin", str).and().eq("bio_type", 8).and().eq("major_ver", 12).and().eq("minor_ver", 0).queryForFirst()) != null) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
