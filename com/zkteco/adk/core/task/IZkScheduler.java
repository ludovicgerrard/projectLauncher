package com.zkteco.adk.core.task;

public interface IZkScheduler {
    int addJob(String str, IZkJob iZkJob);

    void destroy();

    int executeJob(String str);

    IZkJob getJob(String str);

    void setJobListener(IZkJobListener iZkJobListener);

    int stopAllJob();

    int stopJob(String str);
}
