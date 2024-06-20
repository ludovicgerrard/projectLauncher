package com.zktechnology.android.att.AttInterceptors;

import com.zktechnology.android.att.AttResponse;
import com.zktechnology.android.att.types.AttAlarmType;
import com.zktechnology.android.att.types.AttDoorType;

public class BaseAttInterceptor {
    public void setAttResponse(AttResponse attResponse, boolean z, String str, int i, AttAlarmType attAlarmType, AttDoorType attDoorType, boolean z2) {
        attResponse.setCanProceed(z);
        attResponse.setErrorMessage(str);
        attResponse.setEventCode(i);
        attResponse.setAttAlarmType(attAlarmType);
        attResponse.setAttDoorType(attDoorType);
        attResponse.setOpenDoor(z2);
    }

    public void setAttDoorType(AttResponse attResponse, AttDoorType attDoorType) {
        attResponse.setAttDoorType(attDoorType);
    }
}
