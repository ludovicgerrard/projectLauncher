package com.zktechnology.android.launcher2;

import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class StateChangeNotifier implements Callable<Integer> {
    private final String TAG = StateChangeNotifier.class.getSimpleName();
    private final CountDownLatch mCountDownLatch = new CountDownLatch(1);
    private final AtomicInteger mState = new AtomicInteger(999);

    StateChangeNotifier() {
    }

    public void notifyStateChange(int i) {
        this.mState.set(i);
    }

    public void notifyRelease() {
        this.mCountDownLatch.countDown();
    }

    public Integer call() {
        if (this.mCountDownLatch.getCount() > 0) {
            Log.d(this.TAG, "StateChangeNotifier进入等待");
            try {
                this.mCountDownLatch.await(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(this.TAG, "StateChangeNotifier退出等待");
        }
        Log.d(this.TAG, "StateChangeNotifier: state=" + this.mState.get());
        return Integer.valueOf(this.mState.get());
    }
}
