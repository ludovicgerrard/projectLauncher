package com.zkteco.adk.core.task;

import java.util.Map;

public interface IZkTask {
    public static final String TAG = "ADK-TASK";

    int execute(Map<String, Object> map);

    String getFailedExecuteTask();

    int setFailedExecuteTask(String str);

    int stop();

    String taskName();
}
