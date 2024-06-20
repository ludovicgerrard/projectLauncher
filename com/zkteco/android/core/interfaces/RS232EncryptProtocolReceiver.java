package com.zkteco.android.core.interfaces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.zkteco.android.core.interfaces.RS232EncryptProtocolListener;

public class RS232EncryptProtocolReceiver extends BroadcastReceiver {
    public static final String RS232_ACTION = "com.zkteco.android.core.RS232_RFID_READ";
    public static final String RS232_DATA = "rs232_data";
    private RS232EncryptProtocolListener mRS232EncryptProtocolListener;

    public void setRS232EncryptProtocolListener(RS232EncryptProtocolListener rS232EncryptProtocolListener) {
        this.mRS232EncryptProtocolListener = rS232EncryptProtocolListener;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(RS232_ACTION)) {
            this.mRS232EncryptProtocolListener.onReceiveData((RS232EncryptProtocolListener.RS232Data) intent.getParcelableExtra(RS232_DATA));
        }
    }

    public void registerReceiver(Context context) {
        context.registerReceiver(this, new IntentFilter(RS232_ACTION));
    }

    public void unregisterReceiver(Context context) {
        context.unregisterReceiver(this);
    }

    public static void sendReceivedMessage(Context context, RS232EncryptProtocolListener.RS232Data rS232Data) {
        Intent intent = new Intent(RS232_ACTION);
        intent.putExtra(RS232_DATA, rS232Data);
        context.sendBroadcast(intent);
    }
}
