package com.zkteco.android.core.library;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import java.util.concurrent.Executors;

public abstract class RunnableCoreModule extends CoreModule implements Runnable {
    private static final String TAG = "RunnableCoreModule";
    private boolean loop = false;
    private boolean restarted = false;

    /* access modifiers changed from: protected */
    public abstract void cleanup();

    /* access modifiers changed from: protected */
    public abstract void doWork();

    /* access modifiers changed from: protected */
    public abstract long getWaitInterval();

    /* access modifiers changed from: protected */
    public abstract boolean initialize();

    protected RunnableCoreModule(Context context, Resources resources) {
        super(context, resources);
    }

    public void run() {
        Log.i(TAG, this + " - Starting runnable device");
        do {
            try {
                this.restarted = false;
                String str = TAG;
                Log.d(str, this + " - Initializing...");
                boolean initialize = initialize();
                this.loop = initialize;
                if (initialize) {
                    Log.d(str, this + " - Initialization success");
                    do {
                        doWork();
                        Thread.sleep(getWaitInterval());
                    } while (this.loop);
                    String str2 = TAG;
                    Log.d(str2, this + " - Stopped");
                    cleanup();
                    Log.d(str2, this + " - Cleaned up");
                } else {
                    Log.e(str, this + " - Initialization failed");
                    Thread.sleep(getWaitInterval());
                }
            } catch (InterruptedException e) {
                String str3 = TAG;
                Log.e(str3, this + " - Device has been interrupted, exiting");
                Log.e(str3, Log.getStackTraceString(e));
                Thread.currentThread().interrupt();
            }
        } while (this.restarted);
        Log.i(TAG, this + " - Runnable device dead");
    }

    /* access modifiers changed from: protected */
    public void start() {
        Executors.defaultThreadFactory().newThread(this).start();
    }

    /* access modifiers changed from: protected */
    public void stop() {
        Log.d(TAG, this + " - Stopping");
        this.loop = false;
    }

    /* access modifiers changed from: protected */
    public final void setRestart(boolean z) {
        this.restarted = z;
    }

    public String toString() {
        return "RunnableCoreModule{loop=" + this.loop + ", restarted=" + this.restarted + "} " + super.toString();
    }
}
