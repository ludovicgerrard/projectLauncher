package com.zktechnology.android.strategy;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MainThreadExecutor extends ThreadExecutor {

    static class SingletonHolder {
        static final MainThreadExecutor INSTANCE = new MainThreadExecutor();

        SingletonHolder() {
        }
    }

    public static MainThreadExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private MainThreadExecutor() {
        setHandler(new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message message) {
                if (MainThreadExecutor.this.mCallbacks != null) {
                    for (ICallback handleMessage : MainThreadExecutor.this.mCallbacks) {
                        handleMessage.handleMessage(message);
                    }
                }
            }
        });
    }

    public void execute(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            super.execute(runnable);
        }
    }
}
