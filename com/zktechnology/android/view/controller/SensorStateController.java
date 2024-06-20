package com.zktechnology.android.view.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.zktechnology.android.push.acc.AccPush;
import com.zktechnology.android.push.util.AccEventType;

public class SensorStateController extends BroadcastReceiver {
    public static final String ACTION_SENSOR_STATE = "com.zkteco.android.core.ACTION_SENSOR_STATE";
    private final OnSensorStateChangedListener listener;

    public interface OnSensorStateChangedListener {
        void onSensorStateChanged(boolean z);
    }

    public SensorStateController(OnSensorStateChangedListener onSensorStateChangedListener) {
        this.listener = onSensorStateChangedListener;
    }

    public void register(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SENSOR_STATE);
        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public void onReceive(Context context, Intent intent) {
        if (ACTION_SENSOR_STATE.equals(intent.getAction())) {
            int intExtra = intent.getIntExtra("SensorState", -1);
            if (intExtra == 0) {
                this.listener.onSensorStateChanged(false);
                AccPush.getInstance().pushRealEvent(AccEventType.DOOR_CLOSE);
            } else if (intExtra == 1) {
                AccPush.getInstance().pushRealEvent(200);
                this.listener.onSensorStateChanged(true);
            }
        }
    }
}
