package com.zkteco.edk.hardware.encrypt.task;

import com.zkteco.adk.core.task.IZkTask;
import com.zkteco.adk.core.task.ZkTaskLog;
import com.zkteco.adk.core.task.ZkTaskParamUtils;
import com.zkteco.zkliveface.ver56.auth.FaceAuthNative;
import java.util.Map;

public class ZkCheckHardwareActivationTask implements IZkTask {
    private String mFailedExecuteTask;
    private int mQueryStateFailedCount = 0;

    public int stop() {
        return 0;
    }

    public String taskName() {
        return "ZkCheckHardwareActivationTask";
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
            boolean chipAuthStatus = FaceAuthNative.getChipAuthStatus();
            if (!chipAuthStatus) {
                this.mQueryStateFailedCount++;
                boolean boolParam2 = ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_FIRST_CHECK_FAILED_SKIP_TO_NEXT, false);
                if (this.mQueryStateFailedCount != 1 || !boolParam2) {
                    return -1;
                }
                return 0;
            }
            map.put(ZkActivationParamName.FLAG_IS_ACTIVATION_BOOL, Boolean.valueOf(chipAuthStatus));
            ZkTaskLog.v(String.format("encrypt hardware state, active: %s", new Object[]{Boolean.valueOf(chipAuthStatus)}));
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
