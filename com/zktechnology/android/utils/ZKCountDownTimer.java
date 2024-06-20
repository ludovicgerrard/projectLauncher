package com.zktechnology.android.utils;

import android.os.CountDownTimer;

public class ZKCountDownTimer extends CountDownTimer {
    private OnFinishCallBack finishCallBack;
    private boolean isStart;
    private onCancel onCancel;
    private OnTick onTick;

    public interface OnFinishCallBack {
        void onFinish();
    }

    public interface OnTick {
        void onTick(long j);
    }

    public interface onCancel {
        void onCancel();
    }

    public static ZKCountDownTimer newSecondIntervalInstance(int i) {
        return new ZKCountDownTimer((long) (i * 1000), 1000);
    }

    public static ZKCountDownTimer newInstance(int i, int i2) {
        return new ZKCountDownTimer((long) (i * 1000), (long) (i2 * 1000));
    }

    public void setFinishCallBack(OnFinishCallBack onFinishCallBack) {
        this.finishCallBack = onFinishCallBack;
    }

    public void setOnTick(OnTick onTick2) {
        this.onTick = onTick2;
    }

    public void setOnCancel(onCancel oncancel) {
        this.onCancel = oncancel;
    }

    private ZKCountDownTimer(long j, long j2) {
        super(j, j2);
    }

    public boolean isStart() {
        return this.isStart;
    }

    public void onStart() {
        if (!this.isStart) {
            start();
            this.isStart = true;
        }
    }

    public void onCancel() {
        if (this.isStart) {
            cancel();
            this.isStart = false;
        }
    }

    public void onFinished() {
        if (this.isStart) {
            cancel();
            onFinish();
            this.isStart = false;
        }
    }

    public void onTick(long j) {
        OnTick onTick2 = this.onTick;
        if (onTick2 != null) {
            onTick2.onTick(j);
        }
    }

    public void onFinish() {
        OnFinishCallBack onFinishCallBack = this.finishCallBack;
        if (onFinishCallBack != null) {
            onFinishCallBack.onFinish();
        }
    }
}
