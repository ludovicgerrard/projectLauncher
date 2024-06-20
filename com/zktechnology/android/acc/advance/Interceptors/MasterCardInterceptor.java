package com.zktechnology.android.acc.advance.Interceptors;

import com.zktechnology.android.acc.DoorAccessDao;
import com.zktechnology.android.acc.DoorAccessManager;
import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.util.Date;

public class MasterCardInterceptor extends BaseInterceptor implements Interceptor {
    private static final String TAG = "MasterCardInterceptor";

    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        UserInfo userInfo;
        ZKAccDao accDao = doorAccessRequest.getAccDao();
        if (accDao.getDoor1FirstCardOpenDoor() != 0 && (userInfo = doorAccessRequest.getUserInfo()) != null) {
            doorAccessResponse.setFirstOpenUserPin((String) null);
            if (accDao.checkDoor1CancelKeepOpenDay()) {
                boolean z = false;
                int accFirstOpen = accDao.getAccFirstOpen(userInfo.getUser_PIN());
                if (accFirstOpen > 0) {
                    z = DoorAccessDao.isInAccTimeZoneExtension(accFirstOpen, true);
                }
                if (z && !doorAccessResponse.isAdminContinuousVerify()) {
                    int i = DoorAccessManager.isAlreadyFirstAlwaysOpenDoor ? 1 : 2;
                    if (i == 2) {
                        DoorAccessManager.isAlreadyFirstAlwaysOpenDoor = true;
                    }
                    processResponse(userInfo.getUser_PIN(), i, DoorOpenType.FIRST_OPEN_ALWAYS, doorAccessRequest, doorAccessResponse, date);
                    accDao.setDBOption(ZKDBConfig.OPT_DSM, 1);
                }
            }
        }
    }
}
