package com.zktechnology.android.acc.base.Interceptors;

import com.zktechnology.android.acc.DoorSensorState;
import com.zktechnology.android.acc.DoorSensorType;
import com.zktechnology.android.acc.base.Request;
import com.zktechnology.android.utils.LogUtils;

public class BaseInterceptor {
    protected static final int ALWAYS_OPEN_DELAY = 255;
    protected static final int GATE_SWITCH_LOCK_DRIVE_MILLS = 800;
    protected static final int MESSAGE_BROADCAST = 4;
    protected static final int MESSAGE_CLOSE_DOOR = 0;
    protected static final int MESSAGE_CLOSE_EXTERNAL_ALARM = 5;
    protected static final int MESSAGE_LOCAL_ALARM = 1;
    protected static final int MESSAGE_PLAY_REMOTE_ALARM = 3;
    protected static final int MESSAGE_REMOTE_ALARM = 2;
    protected static final int REMOTE_ALARM_DELAY_SECONDS = 3;

    /* access modifiers changed from: protected */
    public static boolean isDoorSensorStateConsistent(Request request) {
        DoorSensorState currentDoorSensorState = request.getCurrentDoorSensorState();
        DoorSensorType doorSensorType = request.getDoorSensorType();
        LogUtils.i("AccDoor", String.format("currentDoorSensorState:%s, doorSensorType:%s", new Object[]{currentDoorSensorState, doorSensorType}));
        if (doorSensorType == DoorSensorType.NONE) {
            return true;
        }
        if (doorSensorType == DoorSensorType.ALWAYS_OPEN && currentDoorSensorState == DoorSensorState.OPEN) {
            return true;
        }
        return doorSensorType == DoorSensorType.ALWAYS_CLOSE && currentDoorSensorState == DoorSensorState.CLOSE;
    }

    /* access modifiers changed from: protected */
    public boolean isDoorSensorOpened(Request request) {
        return request.getDoorSensorType() == DoorSensorType.NONE && request.getCurrentDoorSensorState() == DoorSensorState.OPEN;
    }
}
