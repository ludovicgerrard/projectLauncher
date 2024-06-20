package com.zktechnology.android.utils;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

public abstract class ZKHandler<T> extends Handler {
    private WeakReference<T> object;

    public abstract void handleMessage(T t, Message message);

    public ZKHandler(T t) {
        this.object = new WeakReference<>(t);
    }

    public void handleMessage(Message message) {
        if (this.object.get() != null) {
            handleMessage(this.object.get(), message);
        }
    }
}
