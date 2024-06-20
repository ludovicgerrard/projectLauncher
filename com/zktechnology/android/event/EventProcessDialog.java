package com.zktechnology.android.event;

public class EventProcessDialog extends BaseEvent {
    private boolean show;

    public EventProcessDialog(boolean z) {
        this.show = z;
    }

    public boolean isShow() {
        return this.show;
    }

    public void setShow(boolean z) {
        this.show = z;
    }
}
