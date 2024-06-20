package com.zktechnology.android.helper;

import android.content.Context;
import android.util.Log;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zkteco.android.core.sdk.DoorManager;

public class DoorManagerHelper {
    public static final String TAG = "DoorManagerHelper";
    private static volatile DoorManagerHelper deviceHelper;
    private DoorManager doorManager = new DoorManager(this.mContext);
    private Context mContext = LauncherApplication.getLauncherApplicationContext();

    private DoorManagerHelper() {
    }

    public static DoorManagerHelper getInstance() {
        if (deviceHelper == null) {
            synchronized (DoorManagerHelper.class) {
                if (deviceHelper == null) {
                    deviceHelper = new DoorManagerHelper();
                }
            }
        }
        return deviceHelper;
    }

    public int getSensorState() {
        try {
            return this.doorManager.getSensorState();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean getLockState() {
        try {
            return this.doorManager.getLockState();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void openDoor() {
        try {
            this.doorManager.openDoor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDoor() {
        try {
            String str = TAG;
            Log.d(str, "closeDoor E:");
            this.doorManager.closeDoor();
            Log.d(str, "closeDoor X:");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTamperState() {
        try {
            return this.doorManager.getTearDownButtonState();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean getAlarmState() {
        try {
            return this.doorManager.getAlarmState();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void playAlarm() {
        try {
            this.doorManager.playAlarm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAlarm() {
        try {
            this.doorManager.cancelAlarm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
