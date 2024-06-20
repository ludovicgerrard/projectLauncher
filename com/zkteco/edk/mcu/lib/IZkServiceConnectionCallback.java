package com.zkteco.edk.mcu.lib;

import android.content.ComponentName;

public interface IZkServiceConnectionCallback {
    void onBindingDied(ComponentName componentName) {
    }

    void onNullBinding(ComponentName componentName) {
    }

    void onServiceConnected(ComponentName componentName);

    void onServiceDisconnected(ComponentName componentName);
}
