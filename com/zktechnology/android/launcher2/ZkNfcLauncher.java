package com.zktechnology.android.launcher2;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.util.Log;
import com.zktechnology.android.receiver.ZkNfcReceiver;
import java.lang.reflect.InvocationTargetException;

public abstract class ZkNfcLauncher extends ZKVideoIntercomLauncher {
    private static final String TAG = "ZkNfcLauncher";
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;

    /* access modifiers changed from: protected */
    public abstract void onCardRead(String str);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initData();
    }

    private void initData() {
        NfcAdapter defaultAdapter = ((NfcManager) getSystemService("nfc")).getDefaultAdapter();
        this.mNfcAdapter = defaultAdapter;
        if (defaultAdapter != null) {
            this.mPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, ZkNfcReceiver.class).addFlags(268435456), 134217728);
        } else {
            Log.e(TAG, "initData: 不支持NFC");
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.mNfcAdapter == null) {
            initData();
        }
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled()) {
                enable(this.mNfcAdapter);
            }
            this.mNfcAdapter.enableForegroundDispatch(this, this.mPendingIntent, (IntentFilter[]) null, (String[][]) null);
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
            disable(this.mNfcAdapter);
        }
    }

    private static void enable(NfcAdapter nfcAdapter) {
        if (nfcAdapter != null) {
            try {
                nfcAdapter.getClass().getDeclaredMethod("enable", new Class[0]).invoke(nfcAdapter, new Object[0]);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static void disable(NfcAdapter nfcAdapter) {
        if (nfcAdapter != null) {
            try {
                nfcAdapter.getClass().getDeclaredMethod("disable", new Class[0]).invoke(nfcAdapter, new Object[0]);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
