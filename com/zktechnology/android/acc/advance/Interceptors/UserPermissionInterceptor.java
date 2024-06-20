package com.zktechnology.android.acc.advance.Interceptors;

import android.content.Context;
import com.zktechnology.android.acc.DoorAccessDao;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.ZkLogTag;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zkteco.android.db.orm.tna.AccUserAuthorize;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.util.Date;
import java.util.List;

public class UserPermissionInterceptor extends BaseInterceptor implements Interceptor {
    private static final String TAG = "UserPermissionInterceptor";

    private boolean isUserValid(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        boolean z;
        UserInfo userInfo = doorAccessRequest.getUserInfo();
        if (userInfo == null) {
            return false;
        }
        ZKAccDao accDao = doorAccessRequest.getAccDao();
        if ("1".equals(ZKLauncher.sUserValidTimeFun)) {
            if (accDao.isSuperUser(userInfo)) {
                z = true;
            } else {
                z = accDao.isInExpires(userInfo);
            }
            if (!z) {
                String string = doorAccessRequest.getContext().getResources().getString(R.string.the_personal_ID_is_not_in_period_of_validity);
                LogUtils.d(ZkLogTag.VERIFY_FLOW, "CARD_INVALID_TIME 2");
                processResponse(string, 29, doorAccessRequest, doorAccessResponse, date);
                return false;
            }
        }
        return true;
    }

    private void checkUserPermission(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        UserInfo userInfo = doorAccessRequest.getUserInfo();
        if (userInfo != null) {
            Context context = doorAccessRequest.getContext();
            List<AccUserAuthorize> userAccessGroup = DoorAccessDao.getUserAccessGroup(userInfo.getUser_PIN());
            if (userAccessGroup.size() == 0) {
                processResponse(context.getResources().getString(R.string.illegal_access), 23, doorAccessRequest, doorAccessResponse, date);
                return;
            }
            int holidayType = DoorAccessDao.getHolidayType();
            int i = 0;
            boolean z = true;
            if (holidayType <= 0 || holidayType > 3) {
                while (i < userAccessGroup.size() && !(z = DoorAccessDao.isInAccTimeZone(userAccessGroup.get(i).getAuthorizeTimezone()))) {
                    i++;
                }
            } else {
                while (i < userAccessGroup.size() && !(z = DoorAccessDao.isInAccHolidayTimeZone(userAccessGroup.get(i).getAuthorizeTimezone(), holidayType))) {
                    i++;
                }
            }
            if (!z) {
                processResponse(context.getResources().getString(R.string.illegal_time_period), 22, doorAccessRequest, doorAccessResponse, date);
            }
        }
    }

    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        if (isUserValid(doorAccessRequest, doorAccessResponse, date)) {
            checkUserPermission(doorAccessRequest, doorAccessResponse, date);
        }
    }
}
