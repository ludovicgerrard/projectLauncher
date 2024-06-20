package com.zkteco.adk.core.task;

import android.os.SystemClock;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ZkLoopJob implements IZkJob {
    private final AtomicBoolean mIsRunningTask = new AtomicBoolean(false);
    private boolean mIsStopJob = false;
    private long mJobIntervalTime = 200;
    private String mJobName = "Unknown-Job";
    private IZkJobListener mListener;
    private IZkTask mNowRunningTask;
    private Map<String, Object> mParams = new HashMap();
    private ScheduledFuture<?> mSchedule;
    private List<IZkTask> mTasks = new ArrayList();

    public int setJobName(String str) {
        if (TextUtils.isEmpty(str)) {
            return -2;
        }
        this.mJobName = str;
        return 0;
    }

    public int addTask(IZkTask iZkTask) {
        if (iZkTask == null) {
            return -2;
        }
        this.mTasks.add(iZkTask);
        return 0;
    }

    public int execute() {
        this.mIsStopJob = false;
        if (this.mIsRunningTask.get()) {
            return -9;
        }
        this.mIsRunningTask.set(true);
        this.mSchedule = ZkThreadPoolManager.getInstance().schedule(new Runnable() {
            public final void run() {
                ZkLoopJob.this.lambda$execute$0$ZkLoopJob();
            }
        }, 10, TimeUnit.MILLISECONDS);
        return 0;
    }

    public /* synthetic */ void lambda$execute$0$ZkLoopJob() {
        try {
            executeJobInThreadPool();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeJobInThreadPool() throws Exception {
        int i;
        ZkTaskLog.i(String.format("Job:%s start execute.", new Object[]{this.mJobName}));
        HashMap hashMap = new HashMap();
        List<IZkTask> list = this.mTasks;
        if (list != null && list.size() != 0) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.mTasks.size()) {
                    break;
                }
                int i3 = -1;
                try {
                    if (this.mIsStopJob) {
                        break;
                    }
                    this.mJobIntervalTime = ZkTaskParamUtils.getLongParam(this.mParams, "loop_job_interval_time_long", 200);
                    IZkTask iZkTask = this.mTasks.get(i2);
                    String taskName = iZkTask.taskName();
                    if (((Integer) hashMap.get(taskName)) == null) {
                        hashMap.put(taskName, Integer.valueOf(i2));
                    }
                    if (this.mIsStopJob) {
                        break;
                    }
                    this.mNowRunningTask = iZkTask;
                    long currentTimeMillis = System.currentTimeMillis();
                    try {
                        i = iZkTask.execute(this.mParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                        i = -1;
                    }
                    ZkTaskLog.d(String.format("Task:%s execute time:%s", new Object[]{taskName, Long.valueOf(System.currentTimeMillis() - currentTimeMillis)}));
                    if (i == 0) {
                        callBackTaskExecuteFinish(taskName);
                        if (i2 == this.mTasks.size() - 1) {
                            callBackJobExecuteFinish();
                            SystemClock.sleep(this.mJobIntervalTime);
                            i2 = -1;
                        }
                        i3 = i2;
                    } else {
                        callBackTaskExecuteFailed(i, taskName);
                        ZkTaskLog.e(String.format("%s execute failed, task %s running failed. error code:%s", new Object[]{this.mJobName, taskName, Integer.valueOf(i)}));
                        if (this.mIsStopJob) {
                            break;
                        }
                        SystemClock.sleep(this.mJobIntervalTime);
                    }
                    i2 = i3 + 1;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    SystemClock.sleep(this.mJobIntervalTime);
                }
            }
            hashMap.clear();
            jobExecuteFinish();
        }
    }

    private void jobExecuteFinish() {
        try {
            ZkTaskLog.i(String.format("Job:%s execute finish.", new Object[]{this.mJobName}));
            this.mNowRunningTask = null;
            this.mIsRunningTask.set(false);
            callBackJobExecuteFinish();
            this.mParams.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callBackJobExecuteFinish() {
        IZkJobListener iZkJobListener = this.mListener;
        if (iZkJobListener != null && !this.mIsStopJob) {
            iZkJobListener.onJobFinish(this.mJobName, this.mParams);
        }
    }

    private void callBackJobExecuteFailed(int i) {
        IZkJobListener iZkJobListener = this.mListener;
        if (iZkJobListener != null && !this.mIsStopJob) {
            iZkJobListener.onJobFailed(i, this.mJobName, this.mParams);
        }
    }

    private void callBackTaskExecuteFinish(String str) {
        if (this.mListener != null && !TextUtils.isEmpty(str)) {
            this.mListener.onTaskFinish(this.mJobName, str, this.mParams);
        }
    }

    private void callBackTaskExecuteFailed(int i, String str) {
        if (this.mListener != null && !TextUtils.isEmpty(str)) {
            this.mListener.onTaskFailed(i, this.mJobName, str, this.mParams);
        }
    }

    public int stop() {
        ZkTaskLog.w(String.format("Job:%s receiver stop command.", new Object[]{this.mJobName}));
        this.mIsStopJob = true;
        IZkTask iZkTask = this.mNowRunningTask;
        if (iZkTask != null) {
            iZkTask.stop();
            this.mNowRunningTask = null;
        }
        for (IZkTask next : this.mTasks) {
            ZkTaskLog.w(String.format("Job:%s Task:%s stop result:%s", new Object[]{this.mJobName, next.taskName(), Integer.valueOf(next.stop())}));
        }
        this.mParams.clear();
        if (ZkThreadPoolManager.getInstance().finishFuture(this.mSchedule)) {
            return 0;
        }
        return -1;
    }

    public int addParam(String str, Object obj) {
        if (TextUtils.isEmpty(str)) {
            return -2;
        }
        this.mParams.put(str, obj);
        return 0;
    }

    public Object getParam(String str) {
        return this.mParams.get(str);
    }

    public Map<String, Object> getParams() {
        return this.mParams;
    }

    public void setJobListener(IZkJobListener iZkJobListener) {
        this.mListener = iZkJobListener;
    }

    public void destroy() {
        stop();
        this.mTasks.clear();
        this.mTasks = null;
        this.mListener = null;
        this.mParams.clear();
        this.mParams = null;
    }
}
