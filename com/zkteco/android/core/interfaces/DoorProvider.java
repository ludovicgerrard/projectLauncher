package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class DoorProvider extends AbstractProvider implements DoorInterface {
    public DoorProvider(Provider provider) {
        super(provider);
    }

    public void openDoor() {
        getProvider().invoke(DoorInterface.OPEN_DOOR, new Object[0]);
    }

    public void normallyOpenDoor() {
        getProvider().invoke(DoorInterface.NORMALLY_OPEN_DOOR, new Object[0]);
    }

    public void closeDoor() {
        getProvider().invoke(DoorInterface.CLOSE_DOOR, new Object[0]);
    }

    public void setLockDelay(int i) {
        getProvider().invoke(DoorInterface.SET_LOCK_DELAY, Integer.valueOf(i));
    }

    public void initAccessDevice() {
        getProvider().invoke(DoorInterface.INIT_ACCESS_DEVICE, new Object[0]);
    }

    public void realseAccessDevice() {
        getProvider().invoke(DoorInterface.REALSE_ACCESS_DEVICE, new Object[0]);
    }

    public void accessVerify(boolean z) {
        getProvider().invoke(DoorInterface.ACCESS_VERIFY, Boolean.valueOf(z));
    }

    public void applyNewAccessConfig() {
        getProvider().invoke(DoorInterface.APPLY_NEW_ACCESS_CONFIG, new Object[0]);
    }

    public boolean getLockState() {
        return ((Boolean) getProvider().invoke(DoorInterface.GET_LOCK_STATE, new Object[0])).booleanValue();
    }

    public boolean getAlarmState() {
        return ((Boolean) getProvider().invoke(DoorInterface.GET_ALARM_STATE, new Object[0])).booleanValue();
    }

    public void playAlarm() {
        getProvider().invoke(DoorInterface.PLAY_ALARM, new Object[0]);
    }

    public void cancelAlarm() {
        getProvider().invoke(DoorInterface.CANCEL_ALARM, new Object[0]);
    }

    public int getTearDownButtonState() {
        return ((Integer) getProvider().invoke(DoorInterface.GET_TEARDOWNBUTTON_STATE, new Object[0])).intValue();
    }

    public int getOutDoorSwitchState() {
        return ((Integer) getProvider().invoke(DoorInterface.GET_OUTDOORSWITCH_STATE, new Object[0])).intValue();
    }

    public int getSensorState() {
        return ((Integer) getProvider().invoke(DoorInterface.GET_SENSOR_STATE, new Object[0])).intValue();
    }
}
