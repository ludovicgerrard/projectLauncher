package com.zktechnology.android.att.AttInterceptors;

import com.zktechnology.android.att.AttRequest;
import com.zktechnology.android.att.AttResponse;
import com.zktechnology.android.att.DoorAttManager;
import com.zktechnology.android.att.types.AttAlarmType;
import com.zktechnology.android.att.types.AttDoorType;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;

public class AttContinuousstateInterceptor extends BaseAttInterceptor implements AttInterceptor {
    public void interceptor(AttRequest attRequest, AttResponse attResponse) {
        if (attRequest.getUserInfo() != null && DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSCLOSEDOOR) && !DBManager.getInstance().getStrOption("~DCTZ", "").equals("0")) {
            setAttResponse(attResponse, false, attRequest.getContext().getResources().getString(R.string.illegal_time_period), 22, AttAlarmType.NONE, AttDoorType.ALWAYSCLOSEDOOR, false);
        }
    }
}
