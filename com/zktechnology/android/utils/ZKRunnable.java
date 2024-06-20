package com.zktechnology.android.utils;

import java.lang.ref.WeakReference;

public abstract class ZKRunnable<T> implements Runnable {
    private WeakReference<T> object;

    public abstract void run(T t);

    public ZKRunnable(T t) {
        this.object = new WeakReference<>(t);
    }

    public void run() {
        if (this.object.get() != null) {
            run(this.object.get());
        }
    }
}
