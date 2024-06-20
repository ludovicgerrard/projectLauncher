package com.zktechnology.android.event;

public class EventSetProcessDialogTimeOut extends BaseEvent {
    private int timeOut;

    public EventSetProcessDialogTimeOut(int i) {
        this.timeOut = i;
    }

    public int getTimeOut() {
        return this.timeOut;
    }

    public void setTimeOut(int i) {
        this.timeOut = i;
    }
}
