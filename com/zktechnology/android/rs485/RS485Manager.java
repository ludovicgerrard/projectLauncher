package com.zktechnology.android.rs485;

import android.content.Context;
import com.zktechnology.android.helper.McuServiceHelper;
import com.zktechnology.android.utils.LogUtils;

public class RS485Manager implements RS485Interface {
    private static RS485Manager instance;
    private final Context mContext;

    public static RS485Manager getInstance(Context context) {
        if (instance == null) {
            synchronized (RS485Manager.class) {
                if (instance == null) {
                    instance = new RS485Manager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private RS485Manager(Context context) {
        this.mContext = context;
    }

    public void verifyResult(byte b) {
        LogUtils.verifyLog("verifyResult  " + b);
        McuServiceHelper.getInstance().sentRS485Data(RS485Helper.getInstance(this.mContext).buildCmd152(b));
    }

    public void setBaudRate(int i) {
        RS485Helper.getInstance(this.mContext).setBaudRate(i);
    }

    public void startRS() {
        LogUtils.log("startRS");
        RS485Helper.getInstance(this.mContext).startGetRS485();
    }

    public void stopRS() {
        LogUtils.log("stopRS");
        RS485Helper.getInstance(this.mContext).closeRS485();
    }

    public void failedCmd() {
        verifyResult((byte) -2);
    }

    public void successCmd() {
        verifyResult((byte) 0);
    }

    public void noPermission() {
        verifyResult((byte) -1);
    }

    public void continueVerify() {
        verifyResult((byte) -3);
    }
}
