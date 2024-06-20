package com.zktechnology.android.att.AttInterceptors;

import android.content.Context;
import android.text.TextUtils;
import com.zktechnology.android.att.AttParameter;
import com.zktechnology.android.att.AttRequest;
import com.zktechnology.android.att.AttResponse;
import com.zktechnology.android.att.DoorAttManager;
import com.zktechnology.android.att.types.AttAlarmType;
import com.zktechnology.android.att.types.AttDoorType;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.sql.SQLException;

public class AttDuressAlarmInterceptor extends BaseAttInterceptor implements AttInterceptor {
    private Context context;

    public void interceptor(AttRequest attRequest, AttResponse attResponse) {
        this.context = attRequest.getContext();
        UserInfo userInfo = attRequest.getUserInfo();
        if (userInfo != null) {
            ZKVerifyType fromInteger = ZKVerifyType.fromInteger(attRequest.getCurrentTypy());
            if (DoorAttManager.verifyInput != null) {
                if (fromInteger == ZKVerifyType.FINGER) {
                    if (attRequest.isVerifyStateMulti()) {
                        if (!AttParameter.getInstance().isDU11()) {
                            return;
                        }
                    } else if (!AttParameter.getInstance().isDU1N()) {
                        return;
                    }
                    try {
                        if (((FpTemplate10) new FpTemplate10().getQueryBuilder().where().eq("pin", Long.valueOf(userInfo.getID())).and().eq("fingerid", Integer.valueOf(Integer.parseInt(DoorAttManager.verifyInput))).and().eq("valid", 3).queryForFirst()) != null) {
                            setAttResponse(attResponse, false, (String) null, 101, AttAlarmType.DURESSALARM, AttDoorType.DURESSOPENDOOR, true);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (fromInteger == ZKVerifyType.PASSWORD && AttParameter.getInstance().isDUPWD() && AttParameter.getInstance().isDU11() && checkStressPassword(DoorAttManager.verifyInput)) {
                    setAttResponse(attResponse, false, (String) null, 101, AttAlarmType.DURESSALARM, AttDoorType.DURESSOPENDOOR, true);
                }
            }
        }
    }

    public boolean checkStressPassword(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String strOption = DBManager.getInstance().getStrOption("Door1ForcePassWord", "");
        if ("0".equals(strOption)) {
            strOption = "-1";
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(strOption) || !str.equals(strOption)) {
            return false;
        }
        return true;
    }
}
