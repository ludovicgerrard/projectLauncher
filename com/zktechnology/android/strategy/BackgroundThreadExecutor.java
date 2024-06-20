package com.zktechnology.android.strategy;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

public class BackgroundThreadExecutor extends ThreadExecutor {
    private static final String BACKGROUND_THREAD_NAME = "BackgroundThread";
    private static final String TAG = "BackgroundThreadExecutor";
    private HandlerThread mHandlerThread;

    static class SingletonHolder {
        static final BackgroundThreadExecutor INSTANCE = new BackgroundThreadExecutor();

        SingletonHolder() {
        }
    }

    public static BackgroundThreadExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private BackgroundThreadExecutor() {
        HandlerThread handlerThread = new HandlerThread(BACKGROUND_THREAD_NAME);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        setHandler(new Handler(this.mHandlerThread.getLooper()) {
            public void handleMessage(Message message) {
                if (BackgroundThreadExecutor.this.mCallbacks != null) {
                    for (ICallback handleMessage : BackgroundThreadExecutor.this.mCallbacks) {
                        handleMessage.handleMessage(message);
                    }
                }
            }
        });
    }

    public void release() {
        if (getHandler() != null) {
            getHandler().getLooper().quit();
            setHandler((Handler) null);
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mHandlerThread.join(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.mHandlerThread.isAlive()) {
                Log.e(TAG, "Failed to stop the looper.");
            }
            this.mHandlerThread = null;
        }
    }
}
