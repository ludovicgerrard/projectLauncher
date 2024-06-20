package com.zkteco.android.core.interfaces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class RfidReceiver extends BroadcastReceiver {
    private static final String ACTION_ON_RFID_READ = "com.zkteco.android.core.RFID_READ";
    private static final String RFID_INFO = "rfid-info";
    private RfidListener listener;

    public RfidReceiver(RfidListener rfidListener) {
        this.listener = rfidListener;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == ACTION_ON_RFID_READ) {
            this.listener.onRfidRead(intent.getStringExtra(RFID_INFO));
        }
    }

    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(ACTION_ON_RFID_READ));
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public static void sendRfidBroadcast(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(RFID_INFO, str);
        Intent intent = new Intent(ACTION_ON_RFID_READ);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }
}
