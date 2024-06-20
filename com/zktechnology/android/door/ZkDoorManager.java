package com.zktechnology.android.door;

import android.content.Context;
import com.zktechnology.android.acc.DoorAccessManager;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.verify.bean.process.ZKVerifyInfo;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.util.Date;
import javax.annotation.Nullable;

public class ZkDoorManager {
    private static final String TAG = "ZkDoorManager";
    private static ZkDoorManager instance;
    private Context context;
    private DoorAccessManager doorAccessManager;

    private ZkDoorManager(Context context2) {
        this.context = context2;
    }

    public static ZkDoorManager getInstance(Context context2) {
        if (instance == null) {
            synchronized (ZkDoorManager.class) {
                if (instance == null) {
                    instance = new ZkDoorManager(context2);
                }
            }
        }
        return instance;
    }

    public void initializeDoorManager() {
        if (this.doorAccessManager == null) {
            DoorAccessManager instance2 = DoorAccessManager.getInstance();
            this.doorAccessManager = instance2;
            instance2.initialize();
        }
    }

    public void releaseDoorManager() {
        DoorAccessManager doorAccessManager2 = this.doorAccessManager;
        if (doorAccessManager2 != null) {
            doorAccessManager2.releaseDevice();
            this.doorAccessManager = null;
        }
    }

    public DoorAccessResponse openDoor(ZKAccDao zKAccDao, @Nullable UserInfo userInfo, ZKVerifyInfo zKVerifyInfo, Date date) {
        DoorAccessManager doorAccessManager2 = this.doorAccessManager;
        if (doorAccessManager2 != null) {
            return doorAccessManager2.accessVerify(zKAccDao, userInfo, zKVerifyInfo, true, date);
        }
        return new DoorAccessResponse();
    }

    public void urgentOpenDoor() {
        DoorAccessManager doorAccessManager2 = this.doorAccessManager;
        if (doorAccessManager2 != null) {
            doorAccessManager2.urgentOpen();
        }
    }
}
