package com.zktechnology.android.wiegand;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;

public class WiegandReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                Log.d(WiegandUtil.WG_TAG, "action is null");
                return;
            }
            Log.d(WiegandUtil.WG_TAG, "action:" + action);
            if (action.equals(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_BIT) || action.equals(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_PULSE_INTERVAL) || action.equals(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_PULSE_WIDTH)) {
                ZKWiegandManager.getInstance().refresh();
                ZKWiegandManager.getInstance().setWgOutProperty();
            } else if (action.equals(WiegandConfig.ACTION_WIEGAND_IN_CHANGE_BIT) || action.equals(WiegandConfig.ACTION_WIEGAND_IN_CHANGE_VERIFY_TYPE) || action.equals(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_VERIFY_TYPE) || action.equals(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_SITE_CODE) || action.equals(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_FAILED_ID)) {
                ZKWiegandManager.getInstance().refresh();
            }
        } else {
            Log.d(WiegandUtil.WG_TAG, "intent is null");
        }
    }
}
