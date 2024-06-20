package com.zktechnology.android.view.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.ZKThreadPool;
import com.zkteco.android.core.sdk.UsbStorageManager;

public class UDiskStateController extends BroadcastReceiver {
    public static final String ACTION_USB_DEVICE_CHANGE = "com.zkteco.android.core.usb.DEVICE_CHANGE";
    /* access modifiers changed from: private */
    public boolean lastUsbState;
    /* access modifiers changed from: private */
    public final OnUDiskStateChangedListener listener;

    public interface OnUDiskStateChangedListener {
        void onUDiskStateChanged(boolean z);
    }

    public UDiskStateController(OnUDiskStateChangedListener onUDiskStateChangedListener) {
        this.listener = onUDiskStateChangedListener;
    }

    public void onReceive(Context context, Intent intent) {
        FileLogUtils.writeStateLog("start getUSBStateTask");
        ZKThreadPool.getInstance().executeTask(new GetUsbStateTask(context));
    }

    private class GetUsbStateTask implements Runnable {
        private final UsbStorageManager usbStorageManager;

        private GetUsbStateTask(Context context) {
            this.usbStorageManager = new UsbStorageManager(context);
        }

        public void run() {
            boolean booleanValue = this.usbStorageManager.isConnected().booleanValue();
            FileLogUtils.writeStateLog("isHasUDisk1: " + booleanValue + "-#######-lastUsbState1: " + UDiskStateController.this.lastUsbState);
            if (UDiskStateController.this.lastUsbState == booleanValue) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                booleanValue = this.usbStorageManager.isConnected().booleanValue();
            }
            boolean unused = UDiskStateController.this.lastUsbState = booleanValue;
            FileLogUtils.writeStateLog("isHasUDisk2: " + booleanValue + "-#######-lastUsbState2: " + UDiskStateController.this.lastUsbState);
            FileLogUtils.writeStateLog("----------------------------------------------- ");
            UDiskStateController.this.listener.onUDiskStateChanged(booleanValue);
        }
    }

    public void register(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_USB_DEVICE_CHANGE);
        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }
}
