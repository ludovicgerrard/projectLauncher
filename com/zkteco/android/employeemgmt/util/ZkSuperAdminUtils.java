package com.zkteco.android.employeemgmt.util;

import android.text.TextUtils;
import com.zkteco.android.db.dao.FpTemplate10Dao;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.util.List;

public class ZkSuperAdminUtils {
    public static boolean isSupportDelCard(UserInfo userInfo) {
        if (userInfo.getPrivilege() != 14) {
            return true;
        }
        int verify_Type = userInfo.getVerify_Type();
        if (verify_Type != -1 && verify_Type != 0) {
            if (verify_Type != 6) {
                if (verify_Type != 7) {
                    if (verify_Type != 14) {
                        return false;
                    }
                } else if (!TextUtils.isEmpty(userInfo.getPassword())) {
                    return true;
                } else {
                    return false;
                }
            }
            if (isHaveFingerTemperature(userInfo.getID())) {
                return true;
            }
            return false;
        } else if (!(!TextUtils.isEmpty(userInfo.getPassword())) && !HasFaceUtils.isHasFace(userInfo) && !isHaveFingerTemperature(userInfo.getID())) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean isHaveFingerTemperature(long j) {
        List<FpTemplate10> templates = FpTemplate10Dao.getTemplates(String.valueOf(j));
        return templates != null && templates.size() > 0;
    }
}
