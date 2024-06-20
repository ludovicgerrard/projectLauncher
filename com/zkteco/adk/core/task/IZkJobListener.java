package com.zkteco.adk.core.task;

import java.util.Map;

public interface IZkJobListener {
    void onJobFailed(int i, String str, Map<String, Object> map);

    void onJobFinish(String str, Map<String, Object> map);

    void onTaskFailed(int i, String str, String str2, Map<String, Object> map);

    void onTaskFinish(String str, String str2, Map<String, Object> map);
}
