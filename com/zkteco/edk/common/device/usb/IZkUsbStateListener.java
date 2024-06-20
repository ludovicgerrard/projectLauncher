package com.zkteco.edk.common.device.usb;

import android.hardware.usb.UsbDevice;

public interface IZkUsbStateListener {
    boolean checkPermission();

    void usbAttached(UsbDevice usbDevice);

    void usbDetached(UsbDevice usbDevice);
}
