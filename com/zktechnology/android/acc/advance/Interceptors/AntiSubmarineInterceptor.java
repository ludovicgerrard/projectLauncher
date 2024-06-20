package com.zktechnology.android.acc.advance.Interceptors;

import com.zktechnology.android.acc.DoorAccessDao;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.util.Date;

public class AntiSubmarineInterceptor extends BaseInterceptor implements Interceptor {
    private static final String TAG = "AntiSubmarineInterceptor";

    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        UserInfo userInfo = doorAccessRequest.getUserInfo();
        if (userInfo != null && DoorAccessDao.isAntiSubmarine(userInfo.getUser_PIN())) {
            processResponse(false, true, doorAccessRequest.getContext().getResources().getString(R.string.illegal_entry), 24, doorAccessRequest, doorAccessResponse, date);
        }
    }
}
