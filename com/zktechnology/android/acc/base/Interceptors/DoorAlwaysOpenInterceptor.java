package com.zktechnology.android.acc.base.Interceptors;

import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.base.Interceptor;
import com.zktechnology.android.acc.base.Request;
import com.zktechnology.android.acc.base.Response;

public class DoorAlwaysOpenInterceptor implements Interceptor {
    private DoorOpenType doorOpenType;

    public DoorAlwaysOpenInterceptor() {
        this(DoorOpenType.LOCAL_OPEN_ALWAYS);
    }

    public DoorAlwaysOpenInterceptor(DoorOpenType doorOpenType2) {
        this.doorOpenType = doorOpenType2;
    }

    public void interceptor(Request request, Response response) {
        request.setDoorOpenType(this.doorOpenType);
        response.setDoorOpenType(this.doorOpenType);
    }
}
