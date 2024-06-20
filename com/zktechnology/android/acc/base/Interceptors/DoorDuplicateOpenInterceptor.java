package com.zktechnology.android.acc.base.Interceptors;

import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.base.Interceptor;
import com.zktechnology.android.acc.base.Request;
import com.zktechnology.android.acc.base.Response;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.DBConfig;

public class DoorDuplicateOpenInterceptor extends BaseInterceptor implements Interceptor {
    public static final String DM10 = "DM10";
    public static final String MCU = "MCU";

    public void interceptor(Request request, Response response) {
        if (DoorOpenType.REMOTE_OPEN_EXIT_BUTTON == request.getDoorOpenType() && DM10.equals(DBManager.getInstance().getStrOption(DBConfig.ACCESS_DEVICE, MCU))) {
            response.setOpenDoorDuplicate(true);
        }
    }
}
