package com.zktechnology.android.view.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.zktechnology.android.acc.DoorAccessManager;

public class AlarmStateController extends BroadcastReceiver {
    private final OnAlarmStateChangedListener mListener;

    public interface OnAlarmStateChangedListener {
        void onAlarmStateChanged(boolean z);
    }

    public AlarmStateController(OnAlarmStateChangedListener onAlarmStateChangedListener) {
        this.mListener = onAlarmStateChangedListener;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            if (action.equalsIgnoreCase(DoorAccessManager.ACTION_ALARM_OPEN)) {
                this.mListener.onAlarmStateChanged(true);
            } else if (action.equalsIgnoreCase(DoorAccessManager.ACTION_ALARM_CLOSE)) {
                this.mListener.onAlarmStateChanged(false);
            }
        }
    }

    public void register(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DoorAccessManager.ACTION_ALARM_OPEN);
        intentFilter.addAction(DoorAccessManager.ACTION_ALARM_CLOSE);
        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }
}
