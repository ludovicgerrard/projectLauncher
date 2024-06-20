package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.DoorInterface;
import com.zkteco.android.core.interfaces.DoorProvider;
import com.zkteco.android.core.library.CoreProvider;

public class DoorManager implements DoorInterface {
    DoorProvider provider;

    public DoorManager(Context context) {
        this.provider = new DoorProvider(new CoreProvider(context));
    }

    public void openDoor() {
        this.provider.openDoor();
    }

    public void normallyOpenDoor() {
        this.provider.normallyOpenDoor();
    }

    public void closeDoor() {
        this.provider.closeDoor();
    }

    public void setLockDelay(int i) {
        this.provider.setLockDelay(i);
    }

    public void initAccessDevice() {
        this.provider.initAccessDevice();
    }

    public void realseAccessDevice() {
        this.provider.realseAccessDevice();
    }

    public void accessVerify(boolean z) {
        this.provider.accessVerify(z);
    }

    public void applyNewAccessConfig() {
        this.provider.applyNewAccessConfig();
    }

    public boolean getLockState() {
        return this.provider.getLockState();
    }

    public boolean getAlarmState() {
        return this.provider.getAlarmState();
    }

    public void playAlarm() {
        this.provider.playAlarm();
    }

    public void cancelAlarm() {
        this.provider.cancelAlarm();
    }

    public int getTearDownButtonState() {
        return this.provider.getTearDownButtonState();
    }

    public int getOutDoorSwitchState() {
        return this.provider.getOutDoorSwitchState();
    }

    public int getSensorState() {
        return this.provider.getSensorState();
    }
}
