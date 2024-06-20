package com.zktechnology.android.acc.base.Interceptors;

import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.base.Interceptor;
import com.zktechnology.android.acc.base.Request;
import com.zktechnology.android.acc.base.Response;

public class DoorOpenInterceptor extends BaseInterceptor implements Interceptor {
    public void interceptor(Request request, Response response) {
        DoorOpenType doorOpenType = request.getDoorOpenType();
        if (request.isAccessVerified()) {
            boolean isDoorSensorOpened = request.isDoorSensorOpened();
            boolean isDoorSensorStateConsistent = isDoorSensorStateConsistent(request);
            if (!isDoorSensorOpened || !isDoorSensorStateConsistent) {
                response.setDoorOpenType(DoorOpenType.LOCAL_OPEN);
            } else {
                request.setDoorSensorOpened(false);
            }
        } else {
            response.setDoorOpenType(doorOpenType);
        }
        response.setLockDriveDurationInSeconds(request.getLockDriveDurationInSeconds());
    }
}
