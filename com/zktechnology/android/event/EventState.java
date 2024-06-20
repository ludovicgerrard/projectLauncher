package com.zktechnology.android.event;

public class EventState extends BaseEvent {
    private int IOState;
    private int auInput;
    private boolean isAlarm;
    private String openAlways;
    private int type;

    public EventState(int i) {
        this.type = i;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public boolean isAlarm() {
        return this.isAlarm;
    }

    public void setAlarm(boolean z) {
        this.isAlarm = z;
    }

    public int getIOState() {
        return this.IOState;
    }

    public void setIOState(int i) {
        this.IOState = i;
    }

    public int getAuInput() {
        return this.auInput;
    }

    public void setAuInput(int i) {
        this.auInput = i;
    }

    public String getOpenAlways() {
        return this.openAlways;
    }

    public void setOpenAlways(String str) {
        this.openAlways = str;
    }
}
