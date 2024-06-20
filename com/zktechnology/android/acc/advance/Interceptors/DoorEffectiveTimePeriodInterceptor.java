package com.zktechnology.android.acc.advance.Interceptors;

import com.zktechnology.android.acc.DoorAccessDao;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.launcher.R;
import java.util.Date;

public class DoorEffectiveTimePeriodInterceptor extends BaseInterceptor implements Interceptor {
    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        int door1ValidTimeZone = DoorAccessDao.getDoor1ValidTimeZone();
        boolean z = false;
        if (door1ValidTimeZone != 0) {
            z = DoorAccessDao.isInAccTimeZoneExtension(door1ValidTimeZone, false);
        }
        if (!z) {
            processResponse(doorAccessRequest.getContext().getResources().getString(R.string.the_door_is_not_in_period_of_validity), 21, doorAccessRequest, doorAccessResponse, date);
        }
    }
}
