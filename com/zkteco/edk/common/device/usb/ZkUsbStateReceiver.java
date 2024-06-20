package com.zkteco.edk.common.device.usb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import com.zkteco.edk.common.device.ZkDeviceConstants;

public class ZkUsbStateReceiver extends BroadcastReceiver {
    private IZkUsbStateListener mUsbStateListener;

    public void setUsbStateListener(IZkUsbStateListener iZkUsbStateListener) {
        this.mUsbStateListener = iZkUsbStateListener;
    }

    public void onReceive(Context context, Intent intent) {
        UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
        String action = intent.getAction();
        action.hashCode();
        char c2 = 65535;
        switch (action.hashCode()) {
            case -2114103349:
                if (action.equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                    c2 = 0;
                    break;
                }
                break;
            case -1608292967:
                if (action.equals("android.hardware.usb.action.USB_DEVICE_DETACHED")) {
                    c2 = 1;
                    break;
                }
                break;
            case 1410670919:
                if (action.equals(ZkDeviceConstants.ACTION_USB_DEVICE_PERMISSION)) {
                    c2 = 2;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                IZkUsbStateListener iZkUsbStateListener = this.mUsbStateListener;
                if (iZkUsbStateListener != null) {
                    iZkUsbStateListener.usbAttached(usbDevice);
                    return;
                }
                return;
            case 1:
                IZkUsbStateListener iZkUsbStateListener2 = this.mUsbStateListener;
                if (iZkUsbStateListener2 != null) {
                    iZkUsbStateListener2.usbDetached(usbDevice);
                    return;
                }
                return;
            case 2:
                IZkUsbStateListener iZkUsbStateListener3 = this.mUsbStateListener;
                if (iZkUsbStateListener3 != null) {
                    iZkUsbStateListener3.checkPermission();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
