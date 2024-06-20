package com.zktechnology.android.att;

import android.content.Context;
import com.zktechnology.android.verify.dao.ZKVerDao;
import com.zkteco.android.db.orm.tna.UserInfo;

public class AttRequest {
    private Context context;
    private int currentTypy;
    private UserInfo userInfo;
    private boolean verifyStateMulti;
    private ZKVerDao zkVerDao;

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context2) {
        this.context = context2;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo userInfo2) {
        this.userInfo = userInfo2;
    }

    public ZKVerDao getZkVerDao() {
        return this.zkVerDao;
    }

    public void setZkVerDao(ZKVerDao zKVerDao) {
        this.zkVerDao = zKVerDao;
    }

    public int getCurrentTypy() {
        return this.currentTypy;
    }

    public void setCurrentTypy(int i) {
        this.currentTypy = i;
    }

    public boolean isVerifyStateMulti() {
        return this.verifyStateMulti;
    }

    public void setVerifyStateMulti(boolean z) {
        this.verifyStateMulti = z;
    }
}
