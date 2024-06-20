package com.zktechnology.android.acc.advance.Interceptors;

import android.text.TextUtils;
import android.util.Log;
import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.sql.SQLException;
import java.util.Date;

public class StressFingerInterceptor extends BaseInterceptor implements Interceptor {
    private static final String TAG = "StressFingerInterceptor";

    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        UserInfo userInfo = doorAccessRequest.getUserInfo();
        if (userInfo != null) {
            String verifyInput = doorAccessRequest.getVerifyInfo().getVerifyInput();
            if (!TextUtils.isEmpty(verifyInput)) {
                try {
                    if (((FpTemplate10) new FpTemplate10().getQueryBuilder().where().eq("pin", Long.valueOf(userInfo.getID())).and().eq("fingerid", Integer.valueOf(Integer.parseInt(verifyInput))).and().eq("valid", 3).queryForFirst()) != null) {
                        processResponse(false, true, true, false, doorAccessRequest.getAccDao().getStressAlarmDelay(), 101, (String) null, (String) null, DoorOpenType.LOCAL_OPEN, doorAccessRequest, doorAccessResponse, date);
                    }
                } catch (SQLException e) {
                    Log.i(TAG, e.toString());
                }
            }
        }
    }
}
