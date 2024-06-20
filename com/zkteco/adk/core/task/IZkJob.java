package com.zkteco.adk.core.task;

import java.util.Map;

public interface IZkJob {
    int addParam(String str, Object obj);

    int addTask(IZkTask iZkTask);

    void destroy();

    int execute();

    Object getParam(String str);

    Map<String, Object> getParams();

    void setJobListener(IZkJobListener iZkJobListener);

    int setJobName(String str);

    int stop();
}
