package com.zkteco.edk.hardware.encrypt.task;

import com.zkteco.adk.core.task.IZkTask;
import com.zkteco.adk.core.task.ZkTaskLog;
import com.zkteco.adk.core.task.ZkTaskParamUtils;
import java.util.Map;

public class ZkCheckActivationEndTask implements IZkTask {
    private String mFailedExecuteTask;

    public int stop() {
        return 0;
    }

    public String taskName() {
        return "ZkCheckActivationEndTask";
    }

    public int execute(Map<String, Object> map) {
        ZkTaskLog.v("start execute " + taskName());
        if (map == null) {
            return -2;
        }
        map.put(ZkActivationParamName.FLAG_IS_FIRST_CHECK_FAILED_SKIP_TO_NEXT, false);
        map.put(ZkActivationParamName.FLAG_IS_RETRY_CHECK_ACTIVATION_BOOL, false);
        if (ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_ACTIVATION_BOOL, false)) {
            return 0;
        }
        ZkTaskLog.v("finish execute " + taskName());
        return -1;
    }

    public int setFailedExecuteTask(String str) {
        this.mFailedExecuteTask = str;
        return 0;
    }

    public String getFailedExecuteTask() {
        return this.mFailedExecuteTask;
    }
}
