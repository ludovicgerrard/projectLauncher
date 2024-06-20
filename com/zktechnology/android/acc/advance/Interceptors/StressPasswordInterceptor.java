package com.zktechnology.android.acc.advance.Interceptors;

import android.text.TextUtils;
import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.verify.dao.ZKAccDao;
import java.util.Date;

public class StressPasswordInterceptor extends BaseInterceptor implements Interceptor {
    private static final String TAG = "StressPasswordInterceptor";

    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        String verifyInput = doorAccessRequest.getVerifyInfo().getVerifyInput();
        if (!TextUtils.isEmpty(verifyInput)) {
            ZKAccDao accDao = doorAccessRequest.getAccDao();
            if (accDao.checkStressPassword(verifyInput)) {
                processResponse(false, true, false, true, accDao.getStressAlarmDelay(), 101, (String) null, (String) null, DoorOpenType.LOCAL_OPEN, doorAccessRequest, doorAccessResponse, date);
            }
        }
    }
}
