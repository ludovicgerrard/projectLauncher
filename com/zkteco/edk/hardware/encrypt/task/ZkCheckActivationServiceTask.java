package com.zkteco.edk.hardware.encrypt.task;

import android.content.Context;
import android.util.Log;
import com.zkteco.adk.core.task.IZkTask;
import com.zkteco.edk.hardware.encrypt.ZkActivationManager;
import com.zkteco.edk.hardware.encrypt.ZkLogTag;
import java.util.Map;

public class ZkCheckActivationServiceTask implements IZkTask {
    private String mFailedExecuteTask;

    public int stop() {
        return 0;
    }

    public String taskName() {
        return "ZkCheckActivationServiceTask";
    }

    public int execute(Map<String, Object> map) {
        try {
            Object obj = map.get(ZkActivationParamName.APPLICATION_CONTEXT);
            if (obj == null) {
                Log.v(ZkLogTag.EDK_ACTIVATION, "ZkCheckActivationServiceTask not get context");
                return 0;
            }
            Context context = (Context) obj;
            if (!"com.zkteco.edk.hardware.encrypt.service".equals(context.getPackageName()) && ZkActivationManager.getInstance().bindService(context) == 0) {
                map.put(ZkActivationParamName.FLAG_IS_NEED_START_AS_SERVER_BOOL, true);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(ZkLogTag.EDK_ACTIVATION, e.getMessage());
        }
    }

    public int setFailedExecuteTask(String str) {
        this.mFailedExecuteTask = str;
        return 0;
    }

    public String getFailedExecuteTask() {
        return this.mFailedExecuteTask;
    }
}
