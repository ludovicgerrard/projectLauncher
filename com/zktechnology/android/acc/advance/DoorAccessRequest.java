package com.zktechnology.android.acc.advance;

import android.content.Context;
import com.zktechnology.android.verify.bean.process.ZKVerifyInfo;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zkteco.android.db.orm.tna.UserInfo;

public class DoorAccessRequest {
    private ZKAccDao accDao;
    private Context context;
    private UserInfo userInfo;
    private ZKVerifyInfo verifyInfo;

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context2) {
        this.context = context2;
    }

    public void setUserInfo(UserInfo userInfo2) {
        this.userInfo = userInfo2;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setAccDao(ZKAccDao zKAccDao) {
        this.accDao = zKAccDao;
    }

    public ZKAccDao getAccDao() {
        return this.accDao;
    }

    public void setVerifyInfo(ZKVerifyInfo zKVerifyInfo) {
        this.verifyInfo = zKVerifyInfo;
    }

    public ZKVerifyInfo getVerifyInfo() {
        return this.verifyInfo;
    }
}
