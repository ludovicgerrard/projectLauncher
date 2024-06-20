package com.zkteco.adk.core.task;

import android.text.TextUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZkBasicScheduler implements IZkScheduler, IZkJobListener {
    private Map<String, IZkJob> mJobs = new ConcurrentHashMap();
    private IZkJobListener mListener;

    public void setJobListener(IZkJobListener iZkJobListener) {
        this.mListener = iZkJobListener;
    }

    public int addJob(String str, IZkJob iZkJob) {
        if (TextUtils.isEmpty(str) || iZkJob == null) {
            return -2;
        }
        Map<String, IZkJob> map = this.mJobs;
        if (map == null) {
            return -13;
        }
        if (map.get(str) != null) {
            return -4;
        }
        iZkJob.setJobListener(this);
        this.mJobs.put(str, iZkJob);
        return 0;
    }

    public IZkJob getJob(String str) {
        Map<String, IZkJob> map = this.mJobs;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public int executeJob(String str) {
        Map<String, IZkJob> map = this.mJobs;
        if (map == null) {
            return -13;
        }
        IZkJob iZkJob = map.get(str);
        if (iZkJob == null) {
            return -6;
        }
        return iZkJob.execute();
    }

    public int stopJob(String str) {
        if (TextUtils.isEmpty(str)) {
            return -2;
        }
        Map<String, IZkJob> map = this.mJobs;
        if (map == null) {
            return -13;
        }
        IZkJob iZkJob = map.get(str);
        if (iZkJob == null) {
            return -6;
        }
        int stop = iZkJob.stop();
        if (stop == 0) {
            this.mJobs.remove(str);
        }
        return stop;
    }

    public int stopAllJob() {
        Map<String, IZkJob> map = this.mJobs;
        if (map == null) {
            return -13;
        }
        for (IZkJob stop : map.values()) {
            stop.stop();
        }
        return 0;
    }

    public void destroy() {
        this.mListener = null;
        Map<String, IZkJob> map = this.mJobs;
        if (map != null) {
            for (IZkJob next : map.values()) {
                next.stop();
                next.destroy();
            }
            this.mJobs.clear();
            this.mJobs = null;
        }
    }

    public void onJobFinish(String str, Map<String, Object> map) {
        IZkJobListener iZkJobListener = this.mListener;
        if (iZkJobListener != null) {
            iZkJobListener.onJobFinish(str, map);
        }
    }

    public void onJobFailed(int i, String str, Map<String, Object> map) {
        IZkJobListener iZkJobListener = this.mListener;
        if (iZkJobListener != null) {
            iZkJobListener.onJobFailed(i, str, map);
        }
    }

    public void onTaskFinish(String str, String str2, Map<String, Object> map) {
        IZkJobListener iZkJobListener = this.mListener;
        if (iZkJobListener != null) {
            iZkJobListener.onTaskFinish(str, str2, map);
        }
    }

    public void onTaskFailed(int i, String str, String str2, Map<String, Object> map) {
        IZkJobListener iZkJobListener = this.mListener;
        if (iZkJobListener != null) {
            iZkJobListener.onTaskFailed(i, str, str2, map);
        }
    }
}
