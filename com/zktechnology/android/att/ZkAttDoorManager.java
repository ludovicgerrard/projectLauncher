package com.zktechnology.android.att;

import android.content.Context;

public class ZkAttDoorManager {
    private static ZkAttDoorManager instance;
    private Context context;
    private DoorAttManager doorAttManager;

    public static ZkAttDoorManager getInstance(Context context2) {
        if (instance == null) {
            synchronized (ZkAttDoorManager.class) {
                if (instance == null) {
                    instance = new ZkAttDoorManager(context2);
                }
            }
        }
        return instance;
    }

    public ZkAttDoorManager(Context context2) {
        this.context = context2;
    }

    public void initializeDoorManager() {
        if (this.doorAttManager == null) {
            this.doorAttManager = DoorAttManager.getInstance(this.context);
        }
    }

    public void releaseDoorManager() {
        DoorAttManager doorAttManager2 = this.doorAttManager;
        if (doorAttManager2 != null) {
            doorAttManager2.releaseDevice();
            this.doorAttManager = null;
        }
    }
}
