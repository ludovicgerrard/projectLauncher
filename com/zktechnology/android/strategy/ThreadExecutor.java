package com.zktechnology.android.strategy;

import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ThreadExecutor implements Executor {
    protected List<ICallback> mCallbacks;
    private Handler mHandler;

    /* access modifiers changed from: protected */
    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    /* access modifiers changed from: protected */
    public Handler getHandler() {
        return this.mHandler;
    }

    public void remove(Runnable runnable) {
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        if (runnable == null) {
            handler.removeCallbacksAndMessages((Object) null);
        } else {
            handler.removeCallbacks(runnable);
        }
    }

    public void addCallback(ICallback iCallback) {
        if (this.mCallbacks == null) {
            this.mCallbacks = new ArrayList();
        }
        this.mCallbacks.add(iCallback);
    }

    public void removeCallback(ICallback iCallback) {
        int indexOf;
        List<ICallback> list = this.mCallbacks;
        if (list != null && (indexOf = list.indexOf(iCallback)) > -1) {
            this.mCallbacks.remove(indexOf);
        }
    }

    public void sendMessage(Message message) {
        sendMessageDelayed(message, 0);
    }

    public void sendMessageDelayed(Message message, long j) {
        if (getHandler() != null) {
            getHandler().sendMessageDelayed(message, j);
        }
    }

    public void execute(int i) {
        if (getHandler() != null) {
            getHandler().sendEmptyMessage(i);
        }
    }

    public void execute(Runnable runnable) {
        executeDelayed(runnable, 0);
    }

    public void executeDelayed(Runnable runnable, long j) {
        if (getHandler() != null) {
            getHandler().postDelayed(runnable, j);
        }
    }
}
