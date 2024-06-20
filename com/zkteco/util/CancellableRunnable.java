package com.zkteco.util;

public abstract class CancellableRunnable implements Runnable {
    private boolean cancelled;

    /* access modifiers changed from: protected */
    public abstract void runCancellable();

    public void cancel() {
        setCancelled(true);
    }

    public final void run() {
        if (!isCancelled()) {
            runCancellable();
        }
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean z) {
        this.cancelled = z;
    }
}
