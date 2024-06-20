package com.zkteco.android.core.interfaces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Base64;
import com.zkteco.util.CompressionHelper;

public class FingerPrintReceiver extends BroadcastReceiver {
    public static final String ACTION_ON_FINGERPRINT_RECEIVED = "com.zkteco.android.core.fingerprint.FINGERPRINT";
    private static final String FINGERPRINT = "fingerprint";
    private static final String HEIGHT = "height";
    private static final String TEMPLATE = "template";
    private static final String WIDTH = "width";
    private int HEIGHT_DEFAULT = 380;
    private int WIDTH_DEFAULT = 280;
    private FingerprintListener fingerprintListener;

    public FingerPrintReceiver(FingerprintListener fingerprintListener2) {
        this.fingerprintListener = fingerprintListener2;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == ACTION_ON_FINGERPRINT_RECEIVED) {
            this.fingerprintListener.onFingerprintPressed(intent.getStringExtra(FINGERPRINT), intent.getStringExtra(TEMPLATE), intent.getIntExtra(WIDTH, this.WIDTH_DEFAULT), intent.getIntExtra(HEIGHT, this.HEIGHT_DEFAULT));
        }
    }

    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(ACTION_ON_FINGERPRINT_RECEIVED));
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public static void sendFingerprintBroadcast(Context context, byte[] bArr, byte[] bArr2, int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putString(FINGERPRINT, Base64.encodeToString(CompressionHelper.compress(bArr), 0));
        bundle.putString(TEMPLATE, Base64.encodeToString(bArr2, 0));
        bundle.putInt(WIDTH, i);
        bundle.putInt(HEIGHT, i2);
        Intent intent = new Intent(ACTION_ON_FINGERPRINT_RECEIVED);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }
}
