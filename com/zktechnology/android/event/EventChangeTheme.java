package com.zktechnology.android.event;

public class EventChangeTheme extends BaseEvent {
    private boolean isChange;

    public EventChangeTheme(boolean z) {
        this.isChange = z;
    }

    public boolean isChange() {
        return this.isChange;
    }

    public void setChange(boolean z) {
        this.isChange = z;
    }
}
