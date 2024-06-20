package com.zkteco.edk.hardware.encrypt.task;

import android.content.Context;
import com.zkteco.adk.core.task.IZkTask;
import com.zkteco.adk.core.task.ZkTaskLog;
import java.util.Map;

public class ZkCheckRuntimeTask implements IZkTask {
    private String mFailedExecuteTask;

    public int stop() {
        return 0;
    }

    public String taskName() {
        return "ZkCheckRuntimeTask";
    }

    public int execute(Map<String, Object> map) {
        ZkTaskLog.v("start execute " + taskName());
        if (map == null) {
            return -2;
        }
        Object obj = map.get(ZkActivationParamName.APPLICATION_CONTEXT);
        if (!(obj instanceof Context)) {
            return -3;
        }
        ZkDebuggerUtils.checkDebuggableInNotDebugModel((Context) obj);
        ZkTaskLog.v("finish execute " + taskName());
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
