package com.zkteco.android.core.interfaces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class DistanceReceiver extends BroadcastReceiver {
    public static final String ACTION_DISTANCE = "com.zkteco.android.core.distance.DISTANCE";
    public static final String IS_PERSON_COME = "is_person_come";
    public static final String TAG = "DistanceReceiver";
    private static DistanceListener mDistanceListener;

    public static void setDistanceListener(DistanceListener distanceListener) {
        mDistanceListener = distanceListener;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ACTION_DISTANCE)) {
            boolean booleanExtra = intent.getBooleanExtra(IS_PERSON_COME, false);
            DistanceListener distanceListener = mDistanceListener;
            if (distanceListener != null) {
                distanceListener.isPersonCome(booleanExtra);
            }
        }
    }

    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(ACTION_DISTANCE));
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public static void sendDistanceBroadcast(Context context, boolean z) {
        Intent intent = new Intent(ACTION_DISTANCE);
        intent.putExtra(IS_PERSON_COME, z);
        context.sendBroadcast(intent);
    }
}
