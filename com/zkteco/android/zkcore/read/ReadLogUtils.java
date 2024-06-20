package com.zkteco.android.zkcore.read;

import com.zkteco.android.zkcore.utils.log.LogBaseUtil;

public class ReadLogUtils extends LogBaseUtil {
    /* access modifiers changed from: protected */
    public boolean isShowLog() {
        return this.isLog;
    }

    public void setLog(boolean z) {
        this.isLog = z;
    }

    private static class Holder {
        /* access modifiers changed from: private */
        public static final ReadLogUtils instance = new ReadLogUtils();

        private Holder() {
        }
    }

    public static ReadLogUtils getInstance() {
        return Holder.instance;
    }

    private ReadLogUtils() {
    }
}
