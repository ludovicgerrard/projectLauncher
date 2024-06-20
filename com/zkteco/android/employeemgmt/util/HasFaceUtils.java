package com.zkteco.android.employeemgmt.util;

import android.text.TextUtils;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.liveface562.ZkFaceManager;

public class HasFaceUtils {
    public static boolean isHasFace(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        return isHasFace(userInfo.getUser_PIN());
    }

    public static boolean isHasFace(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (ZkFaceManager.getInstance().queryFaceTemplate(str, new byte[256]) == 0) {
            return true;
        }
        return false;
    }
}
