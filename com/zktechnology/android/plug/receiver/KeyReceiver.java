package com.zktechnology.android.plug.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;

public class KeyReceiver extends BroadcastReceiver {
    final String SYSTEM_DIALOG_REASON_HOME_KEY = ZKStaffBaseActivity.HomeWatcherReceiver.SYSTEM_DIALOG_REASON_HOME_KEY;
    final String SYSTEM_DIALOG_REASON_KEY = ZKStaffBaseActivity.HomeWatcherReceiver.SYSTEM_DIALOG_REASON_KEY;
    final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

    public void onReceive(Context context, Intent intent) {
        String stringExtra;
        if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction()) && (stringExtra = intent.getStringExtra(ZKStaffBaseActivity.HomeWatcherReceiver.SYSTEM_DIALOG_REASON_KEY)) != null && !stringExtra.equals(ZKStaffBaseActivity.HomeWatcherReceiver.SYSTEM_DIALOG_REASON_HOME_KEY)) {
            stringExtra.equals("recentapps");
        }
    }
}
