package com.zktechnology.android.rs232;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.zkteco.android.zkcore.read.ReadConfig;

public class ReadReceiver extends BroadcastReceiver {
    public static final String READ_TAG = "read_tag";

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                Log.d(READ_TAG, "action is null");
                return;
            }
            Log.d(READ_TAG, "action:" + action);
            if (action.equals(ReadConfig.ACTION_CHANGE_READ_VERIFY_TYPE)) {
                ZKRS232EncryptManager.getInstance().refreshData();
                ZKRS232EncryptManager.getInstance().setVerifyType();
                return;
            }
            return;
        }
        Log.d(READ_TAG, "intent is null");
    }
}
