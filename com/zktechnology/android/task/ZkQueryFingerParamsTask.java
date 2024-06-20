package com.zktechnology.android.task;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.ZkLogTag;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.edk.finger.lib.ZkFingerprintManager;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\u0018\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\nH\u0002¨\u0006\r"}, d2 = {"Lcom/zktechnology/android/task/ZkQueryFingerParamsTask;", "Ljava/lang/Runnable;", "()V", "queryFingerVersionAndSave", "", "run", "saveFingerAlgorithmVersion", "dataManager", "Lcom/zkteco/android/db/orm/manager/DataManager;", "algorithmVersion", "", "saveFingerFirmwareVersion", "firmwareVersion", "Launcher2_horush1Release"}, k = 1, mv = {1, 4, 1})
/* compiled from: ZkQueryFingerParamsTask.kt */
public final class ZkQueryFingerParamsTask implements Runnable {
    public void run() {
        try {
            queryFingerVersionAndSave();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void queryFingerVersionAndSave() {
        Context launcherApplicationContext = LauncherApplication.getLauncherApplicationContext();
        if (launcherApplicationContext == null) {
            Log.w(ZkLogTag.BACKGROUND_TASK, "ZkQueryFingerParamsTask application is null");
            return;
        }
        int bindService = ZkFingerprintManager.getInstance().bindService(launcherApplicationContext);
        if (bindService != 0) {
            Log.w(ZkLogTag.BACKGROUND_TASK, "ZkQueryFingerParamsTask bind finger service failed, ret:" + bindService);
            return;
        }
        SystemClock.sleep(8000);
        ArrayList arrayList = new ArrayList();
        int queryOnlineDevice = ZkFingerprintManager.getInstance().queryOnlineDevice(arrayList);
        if (queryOnlineDevice != 0) {
            Log.w(ZkLogTag.BACKGROUND_TASK, "ZkQueryFingerParamsTask queryOnlineDevice failed, ret:" + queryOnlineDevice);
        } else if (arrayList.size() == 0) {
            Log.w(ZkLogTag.BACKGROUND_TASK, "ZkQueryFingerParamsTask queryOnlineDevice failed, device size == 0");
        } else {
            Object obj = arrayList.get(0);
            Intrinsics.checkNotNullExpressionValue(obj, "onlineDevices[0]");
            String str = (String) obj;
            String[] strArr = new String[1];
            for (int i = 0; i < 1; i++) {
                strArr[i] = "";
            }
            int algorithmVersion = ZkFingerprintManager.getInstance().getAlgorithmVersion(str, strArr);
            if (algorithmVersion != 0) {
                Log.w(ZkLogTag.BACKGROUND_TASK, "ZkQueryFingerParamsTask getAlgorithmVersion failed, ret:" + algorithmVersion);
                return;
            }
            DataManager instance = DBManager.getInstance();
            if (!TextUtils.isEmpty(strArr[0])) {
                Intrinsics.checkNotNullExpressionValue(instance, "dataManager");
                saveFingerAlgorithmVersion(instance, strArr[0]);
            }
            String[] strArr2 = new String[1];
            for (int i2 = 0; i2 < 1; i2++) {
                strArr2[i2] = "";
            }
            int firmwareVersion = ZkFingerprintManager.getInstance().getFirmwareVersion(str, strArr2);
            if (firmwareVersion != 0) {
                Log.w(ZkLogTag.BACKGROUND_TASK, "ZkQueryFingerParamsTask getFirmwareVersion failed, ret:" + firmwareVersion);
            } else if (!TextUtils.isEmpty(strArr2[0])) {
                Intrinsics.checkNotNullExpressionValue(instance, "dataManager");
                saveFingerFirmwareVersion(instance, strArr2[0]);
            }
        }
    }

    private final void saveFingerFirmwareVersion(DataManager dataManager, String str) {
        dataManager.setStrOption("ZKFPFWVersion", str);
    }

    private final void saveFingerAlgorithmVersion(DataManager dataManager, String str) {
        dataManager.setStrOption(ZKDBConfig.FINGER_ALGORITHM_VERSION, str);
    }
}
