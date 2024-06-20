package com.zkteco.android.core.interfaces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class WiegandReceiver extends BroadcastReceiver {
    private static final String ACTION_ON_WIEGAND_IN = "com.zkteco.android.core.Wiegand_In";
    private static final String WIEGAND_INFO = "wiegand-info";
    private WiegandListener listener;

    public void setListener(WiegandListener wiegandListener) {
        this.listener = wiegandListener;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == ACTION_ON_WIEGAND_IN) {
            String stringExtra = intent.getStringExtra(WIEGAND_INFO);
            WiegandListener wiegandListener = this.listener;
            if (wiegandListener != null) {
                wiegandListener.onWiegandIn(stringExtra);
            }
        }
    }

    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(ACTION_ON_WIEGAND_IN));
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public static void sendWiegandBroadcast(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(WIEGAND_INFO, str);
        Intent intent = new Intent(ACTION_ON_WIEGAND_IN);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }
}
