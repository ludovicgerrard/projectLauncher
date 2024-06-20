package com.zktechnology.android.event;

public class EventOpenMenu extends BaseEvent {
    private boolean isShowAnimation;

    public EventOpenMenu(boolean z) {
        this.isShowAnimation = z;
    }

    public boolean isShowAnimation() {
        return this.isShowAnimation;
    }

    public void setShowAnimation(boolean z) {
        this.isShowAnimation = z;
    }
}
