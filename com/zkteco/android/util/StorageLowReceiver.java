package com.zkteco.android.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StorageLowReceiver extends BroadcastReceiver {
    private static final String TAG = "StorageLowReceiver";
    private static boolean isStorageLow = false;
    private static StorageChangeListener listener;

    public interface StorageChangeListener {
        void onStorageChanged(Context context, boolean z);
    }

    public static boolean isStorageLow() {
        return isStorageLow;
    }

    public static void setIsStorageLow(boolean z) {
        isStorageLow = z;
    }

    public static void addListener(StorageChangeListener storageChangeListener) {
        listener = storageChangeListener;
    }

    private static void notifyListener(Context context) {
        StorageChangeListener storageChangeListener = listener;
        if (storageChangeListener != null) {
            storageChangeListener.onStorageChanged(context, isStorageLow);
        }
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.intent.action.DEVICE_STORAGE_LOW")) {
            Log.d(TAG, "Storage low");
            isStorageLow = true;
            notifyListener(context);
        } else if (action.equals("android.intent.action.DEVICE_STORAGE_OK")) {
            Log.d(TAG, "Storage ok");
            isStorageLow = false;
            notifyListener(context);
        } else {
            Log.e(TAG, "Unknown action received: " + action);
        }
    }
}
