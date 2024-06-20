package com.zktechnology.android.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.lang.ref.WeakReference;

public abstract class ZKReceiver<T> extends BroadcastReceiver {
    private WeakReference<T> object;

    public abstract void onReceive(T t, Context context, Intent intent);

    public ZKReceiver(T t) {
        this.object = new WeakReference<>(t);
    }

    public void onReceive(Context context, Intent intent) {
        if (this.object.get() != null) {
            onReceive(this.object.get(), context, intent);
        }
    }
}
