package com.zktechnology.android.verify.dao;

import com.zkteco.android.db.orm.tna.UserInfo;

public interface IZKVerDao extends IZKDao {
    void addAttLog(UserInfo userInfo, String str, int i, int i2, String str2, double d, int i3);

    String getLastVerifyTime(String str);

    int getUserVerifyType(UserInfo userInfo, int i);
}
