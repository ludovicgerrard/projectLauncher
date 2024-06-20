package com.zktechnology.android.acc.base.Interceptors;

import com.zktechnology.android.acc.DoorAccessDao;
import com.zktechnology.android.acc.base.Interceptor;
import com.zktechnology.android.acc.base.Request;
import com.zktechnology.android.acc.base.Response;

public class DoorEffectiveTimePeriodInterceptor implements Interceptor {
    public void interceptor(Request request, Response response) {
        int door1ValidTimeZone = DoorAccessDao.getDoor1ValidTimeZone();
        boolean z = false;
        if (door1ValidTimeZone != 0) {
            z = DoorAccessDao.isInAccTimeZoneExtension(door1ValidTimeZone, false);
        }
        response.setInDoorEffectiveTimePeriod(z);
    }
}
