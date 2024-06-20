package com.zkteco.android.employeemgmt.view;

import android.content.Context;
import android.os.CountDownTimer;
import com.zkteco.android.zkcore.view.alert.ZKConfirmDialog;

public abstract class RebootDialog extends ZKConfirmDialog {
    private CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
        public void onTick(long j) {
            RebootDialog.this.tick(String.valueOf((int) (j / 1000)));
        }

        public void onFinish() {
            RebootDialog.this.finish();
        }
    };

    public abstract void finish();

    public abstract void tick(String str);

    public RebootDialog(Context context) {
        super(context);
    }

    public void stop() {
        this.countDownTimer.cancel();
    }

    public void start() {
        this.countDownTimer.start();
    }
}
