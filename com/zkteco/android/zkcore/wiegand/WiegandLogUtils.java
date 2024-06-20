package com.zkteco.android.zkcore.wiegand;

import com.zkteco.android.zkcore.utils.log.LogBaseUtil;

public class WiegandLogUtils extends LogBaseUtil {
    /* access modifiers changed from: protected */
    public boolean isShowLog() {
        return this.isLog;
    }

    public void setLog(boolean z) {
        this.isLog = z;
    }

    private static class Holder {
        /* access modifiers changed from: private */
        public static final WiegandLogUtils instance = new WiegandLogUtils();

        private Holder() {
        }
    }

    public static WiegandLogUtils getInstance() {
        return Holder.instance;
    }

    private WiegandLogUtils() {
    }
}
