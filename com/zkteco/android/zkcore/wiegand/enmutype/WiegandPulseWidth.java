package com.zkteco.android.zkcore.wiegand.enmutype;

import com.guide.guidecore.GuideUsbManager;

public enum WiegandPulseWidth {
    MIN(20),
    MAX(GuideUsbManager.CONTRAST_MAX),
    DEF(100);
    
    private int value;

    private WiegandPulseWidth(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
