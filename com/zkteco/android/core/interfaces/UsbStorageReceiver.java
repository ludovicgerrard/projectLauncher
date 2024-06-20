package com.zkteco.android.core.interfaces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class UsbStorageReceiver extends BroadcastReceiver {
    private UsbStorageListener usbStorageListener;

    public UsbStorageReceiver(UsbStorageListener usbStorageListener2) {
        this.usbStorageListener = usbStorageListener2;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == "android.hardware.usb.action.USB_DEVICE_ATTACHED") {
            this.usbStorageListener.onUsbAttached();
        }
        if (intent.getAction() == "android.hardware.usb.action.USB_DEVICE_DETACHED") {
            this.usbStorageListener.onUsbDetached();
        }
    }

    public void register(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }
}
