package com.zktechnology.android.event;

public class EventDismissInitDialog extends BaseEvent {
    private boolean isDismiss;

    public EventDismissInitDialog(boolean z) {
        this.isDismiss = z;
    }

    public boolean isDismiss() {
        return this.isDismiss;
    }

    public void setDismiss(boolean z) {
        this.isDismiss = z;
    }
}
