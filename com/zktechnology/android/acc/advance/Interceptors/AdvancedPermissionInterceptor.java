package com.zktechnology.android.acc.advance.Interceptors;

import android.text.TextUtils;
import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.push.util.AccEventType;
import com.zktechnology.android.verify.bean.process.ZKVerifyInfo;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import java.util.Date;

public class AdvancedPermissionInterceptor extends BaseInterceptor implements Interceptor {
    private static final String TAG = "AdvancedPermissionInterceptor";

    public boolean isSuperPassword(ZKVerifyInfo zKVerifyInfo, ZKAccDao zKAccDao) {
        String verifyInput = zKVerifyInfo.getVerifyInput();
        if (!TextUtils.isEmpty(verifyInput)) {
            return zKAccDao.checkSuperPassword(verifyInput);
        }
        return false;
    }

    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        ZKAccDao accDao = doorAccessRequest.getAccDao();
        if (isSuperPassword(doorAccessRequest.getVerifyInfo(), accDao)) {
            processResponse(4, DoorOpenType.LOCAL_OPEN, doorAccessRequest, doorAccessResponse, date);
        } else if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 1 && accDao.isSuperUser(doorAccessRequest.getUserInfo())) {
            processResponse((int) AccEventType.SUPER_USER_OPEN, DoorOpenType.LOCAL_OPEN, doorAccessRequest, doorAccessResponse, date);
        }
    }
}
