package com.zkteco.adk.core.task;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ZkBasicJob implements IZkJob {
    private final AtomicBoolean mIsRunningTask = new AtomicBoolean(false);
    private boolean mIsStopJob = false;
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
                ZkBasicJob.this.lambda$execute$0$ZkBasicJob();
            }
        }, 10, TimeUnit.MILLISECONDS);
        return 0;
    }

    public /* synthetic */ void lambda$execute$0$ZkBasicJob() {
        try {
            executeJobInThreadPool();
        } catch (Exception e) {
            e.printStackTrace();
            jobExecuteFailed(-11);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00d8, code lost:
        r3 = r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void executeJobInThreadPool() throws java.lang.Exception {
        /*
            r12 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.String r2 = r12.mJobName
            r3 = 0
            r1[r3] = r2
            java.lang.String r2 = "Job:%s start execute."
            java.lang.String r1 = java.lang.String.format(r2, r1)
            com.zkteco.adk.core.task.ZkTaskLog.i(r1)
            java.util.List<com.zkteco.adk.core.task.IZkTask> r1 = r12.mTasks
            if (r1 == 0) goto L_0x00e5
            int r1 = r1.size()
            if (r1 != 0) goto L_0x001d
            goto L_0x00e5
        L_0x001d:
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            r4 = r0
            r2 = r3
            r5 = r2
        L_0x0025:
            java.util.List<com.zkteco.adk.core.task.IZkTask> r6 = r12.mTasks
            int r6 = r6.size()
            if (r2 >= r6) goto L_0x00d8
            boolean r6 = r12.mIsStopJob
            if (r6 == 0) goto L_0x0033
            goto L_0x00d8
        L_0x0033:
            java.util.List<com.zkteco.adk.core.task.IZkTask> r6 = r12.mTasks
            java.lang.Object r6 = r6.get(r2)
            com.zkteco.adk.core.task.IZkTask r6 = (com.zkteco.adk.core.task.IZkTask) r6
            java.lang.String r7 = r6.taskName()
            java.lang.Object r8 = r1.get(r7)
            java.lang.Integer r8 = (java.lang.Integer) r8
            if (r8 != 0) goto L_0x004e
            java.lang.Integer r8 = java.lang.Integer.valueOf(r2)
            r1.put(r7, r8)
        L_0x004e:
            boolean r8 = r12.mIsStopJob
            if (r8 == 0) goto L_0x0054
            goto L_0x00d8
        L_0x0054:
            r12.mNowRunningTask = r6
            long r4 = java.lang.System.currentTimeMillis()
            java.util.Map<java.lang.String, java.lang.Object> r8 = r12.mParams
            int r6 = r6.execute(r8)
            r8 = 2
            java.lang.Object[] r9 = new java.lang.Object[r8]
            r9[r3] = r7
            long r10 = java.lang.System.currentTimeMillis()
            long r10 = r10 - r4
            java.lang.Long r4 = java.lang.Long.valueOf(r10)
            r9[r0] = r4
            java.lang.String r4 = "Task:%s execute time:%s"
            java.lang.String r4 = java.lang.String.format(r4, r9)
            com.zkteco.adk.core.task.ZkTaskLog.d(r4)
            if (r6 != 0) goto L_0x0080
            r12.callBackTaskExecuteFinish(r7)
            r4 = r0
            goto L_0x00d2
        L_0x0080:
            r12.callBackTaskExecuteFailed(r6, r7)
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.String r5 = r12.mJobName
            r4[r3] = r5
            r4[r0] = r7
            java.lang.Integer r5 = java.lang.Integer.valueOf(r6)
            r4[r8] = r5
            java.lang.String r5 = "%s execute failed, task %s running failed. error code:%s"
            java.lang.String r4 = java.lang.String.format(r5, r4)
            com.zkteco.adk.core.task.ZkTaskLog.e(r4)
            boolean r4 = r12.mIsStopJob
            if (r4 == 0) goto L_0x00a0
            goto L_0x00d6
        L_0x00a0:
            java.util.List<com.zkteco.adk.core.task.IZkTask> r4 = r12.mTasks
            java.lang.Object r2 = r4.get(r2)
            com.zkteco.adk.core.task.IZkTask r2 = (com.zkteco.adk.core.task.IZkTask) r2
            java.lang.String r2 = r2.getFailedExecuteTask()
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x00d6
            java.lang.Object r4 = r1.get(r2)
            java.lang.Integer r4 = (java.lang.Integer) r4
            if (r4 != 0) goto L_0x00cc
            java.lang.Object[] r4 = new java.lang.Object[r8]
            r4[r3] = r2
            java.lang.String r2 = r12.mJobName
            r4[r0] = r2
            java.lang.String r0 = "failed to task:%s not exist in this job:%s"
            java.lang.String r0 = java.lang.String.format(r0, r4)
            com.zkteco.adk.core.task.ZkTaskLog.e(r0)
            goto L_0x00d6
        L_0x00cc:
            int r2 = r4.intValue()
            int r2 = r2 - r0
            r4 = r3
        L_0x00d2:
            int r2 = r2 + r0
            r5 = r6
            goto L_0x0025
        L_0x00d6:
            r5 = r6
            goto L_0x00d9
        L_0x00d8:
            r3 = r4
        L_0x00d9:
            r1.clear()
            if (r3 == 0) goto L_0x00e2
            r12.jobExecuteFinish()
            goto L_0x00e5
        L_0x00e2:
            r12.jobExecuteFailed(r5)
        L_0x00e5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.adk.core.task.ZkBasicJob.executeJobInThreadPool():void");
    }

    private void jobExecuteFinish() {
        ZkTaskLog.i(String.format("Job:%s execute finish.", new Object[]{this.mJobName}));
        this.mNowRunningTask = null;
        this.mIsRunningTask.set(false);
        callBackJobExecuteFinish();
        this.mParams.clear();
    }

    private void jobExecuteFailed(int i) {
        ZkTaskLog.i(String.format("Job:%s execute failed.", new Object[]{this.mJobName}));
        this.mNowRunningTask = null;
        this.mIsRunningTask.set(false);
        callBackJobExecuteFailed(i);
        this.mParams.clear();
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
