package com.zktechnology.android.acc.base.Interceptors;

import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.base.Interceptor;
import com.zktechnology.android.acc.base.Request;
import com.zktechnology.android.acc.base.Response;

public class DoorRemoteOpenInterceptor extends BaseInterceptor implements Interceptor {
    public void interceptor(Request request, Response response) {
        DoorOpenType doorOpenType = request.getDoorOpenType();
        int lockDriveDurationInSeconds = request.getLockDriveDurationInSeconds();
        if (doorOpenType == DoorOpenType.REMOTE_OPEN || doorOpenType == DoorOpenType.REMOTE_OPEN_EXIT_BUTTON || doorOpenType == DoorOpenType.AUX_IN_REMOTE_OPEN) {
            int remoteLockDriveDurationInSeconds = request.getRemoteLockDriveDurationInSeconds();
            request.setRemoteLockDriveDurationInSeconds(0);
            if (remoteLockDriveDurationInSeconds > 0) {
                lockDriveDurationInSeconds = remoteLockDriveDurationInSeconds;
            }
            request.setDoorOpenType(DoorOpenType.NONE);
        }
        response.setDoorOpenType(doorOpenType);
        response.setLockDriveDurationInSeconds(lockDriveDurationInSeconds);
    }
}
