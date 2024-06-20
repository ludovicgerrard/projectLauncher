package com.zktechnology.android.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.Window;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.strategy.MainThreadExecutor;
import com.zkteco.android.db.orm.contants.PushCommuCMD;

public class Device070ExceptionRebootReceiver extends BroadcastReceiver {
    private static final String DEVICE_070_EXCEPTION_REBOOT_ACTION = "com.zkteco.android.core.reboot.device";

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action) && action.equals(DEVICE_070_EXCEPTION_REBOOT_ACTION)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.error_dialog_title);
            builder.setMessage(R.string.error_dialog_message);
            builder.setCancelable(false);
            AlertDialog create = builder.create();
            Window window = create.getWindow();
            if (window != null) {
                window.setType(PushCommuCMD.PUSH_CMD_SEND_DATA);
            }
            create.show();
            MainThreadExecutor.getInstance().executeDelayed($$Lambda$Device070ExceptionRebootReceiver$MfuceLoGRXUbnOYlUxu52fr8DrY.INSTANCE, 5000);
        }
    }

    static /* synthetic */ void lambda$onReceive$0() {
        PowerManager powerManager = (PowerManager) LauncherApplication.getApplication().getSystemService("power");
        if (powerManager != null) {
            powerManager.reboot((String) null);
        }
    }
}
