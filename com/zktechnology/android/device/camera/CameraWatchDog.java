package com.zktechnology.android.device.camera;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import java.util.ArrayList;
import java.util.List;

public class CameraWatchDog {
    private static final long DEFAULT_COUNT = 5;
    private static final int MSG_START_WATCH_DOG = 1000;
    private static final String THREAD_NAME = "Watch_Dog_Thread";
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private final List<CameraWatchDogTask> mTaskQueue = new ArrayList();
    private final List<CameraWatchDogTask> mTasks = new ArrayList();

    public interface IWatchDogCallback {
        void onCameraLost(String str);
    }

    public CameraWatchDog() {
        HandlerThread handlerThread = new HandlerThread(THREAD_NAME);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper()) {
            public void handleMessage(Message message) {
                CameraWatchDog.this.doWatch();
            }
        };
    }

    public void start() {
        if (this.mHandler.hasMessages(1000)) {
            this.mHandler.removeMessages(1000);
        }
        this.mHandler.sendEmptyMessage(1000);
    }

    public void stop() {
        this.mHandler.removeCallbacksAndMessages((Object) null);
        this.mTasks.clear();
        this.mTaskQueue.clear();
        release();
    }

    public void addTask(CameraWatchDogTask cameraWatchDogTask) {
        if (!this.mTaskQueue.contains(cameraWatchDogTask) && !this.mTasks.contains(cameraWatchDogTask)) {
            this.mTaskQueue.add(cameraWatchDogTask);
        }
    }

    /* access modifiers changed from: private */
    public void doWatch() {
        for (CameraWatchDogTask post : this.mTasks) {
            this.mHandler.post(post);
        }
        this.mTasks.addAll(this.mTaskQueue);
        this.mTaskQueue.clear();
        this.mHandler.sendEmptyMessageDelayed(1000, 5000);
    }

    public void release() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.getLooper().quit();
            this.mHandler = null;
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mHandlerThread.join(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.mHandlerThread = null;
        }
    }
}
