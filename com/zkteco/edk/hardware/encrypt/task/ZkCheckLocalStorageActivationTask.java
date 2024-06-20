package com.zkteco.edk.hardware.encrypt.task;

import android.content.Context;
import android.util.Log;
import com.zkteco.adk.core.task.IZkTask;
import com.zkteco.adk.core.task.ZkTaskLog;
import com.zkteco.adk.core.task.ZkTaskParamUtils;
import com.zkteco.edk.hardware.encrypt.ZkLogTag;
import com.zkteco.edk.hardware.encrypt.ZkSPUtils;
import com.zkteco.zkliveface.ver56.auth.FaceAuthNative;
import java.util.Map;

public class ZkCheckLocalStorageActivationTask implements IZkTask {
    private static final String SP_KEY_IS_ACTIVATION_INT = "SP_KEY_IS_ACTIVATION";
    private String mFailedExecuteTask;
    private int mQueryStateFailedCount = 0;

    public int stop() {
        return 0;
    }

    public String taskName() {
        return "ZkCheckLocalStorageActivationTask";
    }

    public int execute(Map<String, Object> map) {
        ZkTaskLog.v("start execute " + taskName());
        if (map == null) {
            return -2;
        }
        try {
            Object obj = map.get(ZkActivationParamName.APPLICATION_CONTEXT);
            if (obj == null) {
                Log.v(ZkLogTag.EDK_ACTIVATION, "ZkCheckLocalStorageActivationTask not get context");
                return 0;
            } else if (!"com.zkteco.edk.hardware.encrypt.service".equals(((Context) obj).getPackageName())) {
                return 0;
            } else {
                boolean boolParam = ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_ACTIVATION_BOOL, false);
                if (ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_RETRY_CHECK_ACTIVATION_BOOL, false)) {
                    this.mQueryStateFailedCount = 0;
                }
                if (boolParam) {
                    ZkSPUtils.setInt(SP_KEY_IS_ACTIVATION_INT, 1);
                } else {
                    boolParam = ZkSPUtils.getInt(SP_KEY_IS_ACTIVATION_INT, 0) == 1;
                    if (boolParam) {
                        map.put(ZkActivationParamName.FLAG_IS_ACTIVATION_BOOL, true);
                    }
                }
                if (!boolParam) {
                    if (this.mQueryStateFailedCount <= 3) {
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
                        ZkTaskLog.v(String.format("encrypt storage state, active: %s", new Object[]{Boolean.valueOf(chipAuthStatus)}));
                        ZkTaskLog.v("finish execute " + taskName());
                        return 0;
                    }
                }
                return 0;
            }
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
