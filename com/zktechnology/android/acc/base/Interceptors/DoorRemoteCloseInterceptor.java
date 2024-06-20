package com.zktechnology.android.acc.base.Interceptors;

import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.base.Interceptor;
import com.zktechnology.android.acc.base.Request;
import com.zktechnology.android.acc.base.Response;

public class DoorRemoteCloseInterceptor extends BaseInterceptor implements Interceptor {
    public void interceptor(Request request, Response response) {
        DoorOpenType doorOpenType = request.getDoorOpenType();
        if (doorOpenType == DoorOpenType.REMOTE_CLOSE) {
            request.setDoorOpenType(DoorOpenType.NONE);
            response.setDoorOpenType(doorOpenType);
        }
    }
}
