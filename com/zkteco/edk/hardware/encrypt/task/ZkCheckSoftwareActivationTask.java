package com.zkteco.edk.hardware.encrypt.task;

import com.zkteco.adk.core.task.IZkTask;
import com.zkteco.adk.core.task.ZkTaskLog;
import com.zkteco.adk.core.task.ZkTaskParamUtils;
import com.zkteco.edk.hardware.encrypt.ZkOfflineActivationUtils;
import java.util.Map;

public class ZkCheckSoftwareActivationTask implements IZkTask {
    private String mFailedExecuteTask;
    private boolean mIsAlreadyInit = false;
    private int mQueryStateFailedCount = 0;

    public int stop() {
        return 0;
    }

    public String taskName() {
        return "ZkCheckSoftwareActivationTask";
    }

    public int execute(Map<String, Object> map) {
        ZkTaskLog.v("start execute " + taskName());
        if (map == null) {
            return -2;
        }
        boolean boolParam = ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_ACTIVATION_BOOL, false);
        if (ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_RETRY_CHECK_ACTIVATION_BOOL, false)) {
            this.mQueryStateFailedCount = 0;
        }
        if (!boolParam && this.mQueryStateFailedCount <= 3) {
            if (!this.mIsAlreadyInit) {
                ZkTaskLog.v(String.format("initLicence ret: %s", new Object[]{Boolean.valueOf(ZkOfflineActivationUtils.initLicence())}));
                ZkTaskLog.v(String.format("createDeviceSN ret: %s", new Object[]{Boolean.valueOf(ZkOfflineActivationUtils.createDeviceSN())}));
                this.mIsAlreadyInit = true;
            }
            boolean activateLicense = ZkOfflineActivationUtils.activateLicense();
            if (!activateLicense) {
                this.mQueryStateFailedCount++;
                boolean boolParam2 = ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_FIRST_CHECK_FAILED_SKIP_TO_NEXT, false);
                if (this.mQueryStateFailedCount != 1 || !boolParam2) {
                    return -1;
                }
                return 0;
            }
            map.put(ZkActivationParamName.FLAG_IS_ACTIVATION_BOOL, Boolean.valueOf(activateLicense));
            ZkTaskLog.v(String.format("activateLicense ret: %s", new Object[]{Boolean.valueOf(activateLicense)}));
            ZkTaskLog.v("finish execute " + taskName());
        }
        return 0;
    }

    public int setFailedExecuteTask(String str) {
        this.mFailedExecuteTask = str;
        return 0;
    }

    public String getFailedExecuteTask() {
        return this.mFailedExecuteTask;
    }
}
