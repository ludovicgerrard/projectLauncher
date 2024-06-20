package com.zktechnology.android.event;

public class EventInitTask extends BaseEvent {
    public static final int EVENT_ALL_INIT_OK = 2;
    public static final int EVENT_INIT_DB_SUCCESS = 0;
    public static final int EVENT_INIT_DEVICE = 3;
    public static final int EVENT_INIT_HUB = 1;
    private int task = -1;

    public EventInitTask(int i) {
        this.task = i;
    }

    public int getTask() {
        return this.task;
    }

    public void setTask(int i) {
        this.task = i;
    }
}
