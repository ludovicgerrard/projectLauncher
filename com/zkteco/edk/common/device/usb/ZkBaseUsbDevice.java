package com.zkteco.edk.common.device.usb;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import com.zkteco.edk.common.device.IZkDevice;
import com.zkteco.edk.common.device.IZkDeviceStateListener;
import com.zkteco.edk.common.device.ZkDeviceConstants;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ZkBaseUsbDevice implements IZkDevice, IZkUsbStateListener {
    private Context mContext;
    private int mDeviceState;
    private boolean mIsDeviceExist = false;
    private final AtomicBoolean mIsHaveUsbPermission = new AtomicBoolean(false);
    private final AtomicBoolean mIsRegisterBR;
    private IZkDeviceStateListener mStateListener;
    private UsbDevice mUsbDevice;
    private UsbManager mUsbManager;
    private ZkUsbStateReceiver mUsbStateReceiver;

    /* access modifiers changed from: protected */
    public abstract int devicePid();

    /* access modifiers changed from: protected */
    public abstract int deviceVid();

    public ZkBaseUsbDevice(Context context) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.mIsRegisterBR = atomicBoolean;
        this.mDeviceState = 1;
        this.mContext = context.getApplicationContext();
        createUsbManager();
        if (!atomicBoolean.get()) {
            this.mUsbStateReceiver = new ZkUsbStateReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
            intentFilter.addAction(ZkDeviceConstants.ACTION_USB_DEVICE_PERMISSION);
            this.mUsbStateReceiver.setUsbStateListener(this);
            this.mContext.registerReceiver(this.mUsbStateReceiver, intentFilter);
            atomicBoolean.set(true);
        }
        allowPermissionIfDeviceExist();
    }

    private void allowPermissionIfDeviceExist() {
        if (deviceExist()) {
            checkPermission();
        }
    }

    private synchronized void createUsbManager() {
        if (this.mUsbManager == null) {
            this.mUsbManager = (UsbManager) this.mContext.getSystemService("usb");
        }
    }

    /* access modifiers changed from: protected */
    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: protected */
    public UsbManager getUsbManager() {
        return this.mUsbManager;
    }

    /* access modifiers changed from: protected */
    public UsbDevice getUsbDevice() {
        return this.mUsbDevice;
    }

    public void setDeviceStateListener(IZkDeviceStateListener iZkDeviceStateListener) {
        this.mStateListener = iZkDeviceStateListener;
    }

    /* access modifiers changed from: protected */
    public void deviceStateChange(int i, String str) {
        this.mDeviceState = i;
        IZkDeviceStateListener iZkDeviceStateListener = this.mStateListener;
        if (iZkDeviceStateListener != null) {
            iZkDeviceStateListener.onDeviceStateChange(i, str);
        }
    }

    public boolean deviceDestroy() {
        if (this.mIsRegisterBR.get()) {
            this.mContext.unregisterReceiver(this.mUsbStateReceiver);
            this.mIsRegisterBR.set(false);
            this.mUsbStateReceiver = null;
            this.mContext = null;
        }
        this.mUsbManager = null;
        this.mUsbDevice = null;
        return false;
    }

    public void usbAttached(UsbDevice usbDevice) {
        if (isThisUsbDevice(usbDevice)) {
            allowPermissionIfDeviceExist();
            deviceStateChange(0, deviceName());
        }
    }

    public void usbDetached(UsbDevice usbDevice) {
        if (isThisUsbDevice(usbDevice)) {
            this.mIsHaveUsbPermission.set(false);
            this.mUsbDevice = null;
            this.mIsDeviceExist = false;
            deviceStateChange(1, deviceName());
        }
    }

    private UsbDevice checkAndReturnUsbDevice() {
        if (this.mContext == null) {
            return null;
        }
        createUsbManager();
        for (UsbDevice next : this.mUsbManager.getDeviceList().values()) {
            if (isThisUsbDevice(next)) {
                return next;
            }
        }
        return null;
    }

    public boolean deviceExist() {
        boolean z = true;
        if (this.mIsDeviceExist) {
            return true;
        }
        UsbDevice checkAndReturnUsbDevice = checkAndReturnUsbDevice();
        this.mUsbDevice = checkAndReturnUsbDevice;
        if (checkAndReturnUsbDevice == null) {
            z = false;
        }
        this.mIsDeviceExist = z;
        return z;
    }

    public boolean checkPermission() {
        if (this.mIsHaveUsbPermission.get()) {
            return true;
        }
        createUsbManager();
        if (this.mUsbDevice == null) {
            this.mUsbDevice = checkAndReturnUsbDevice();
        }
        UsbDevice usbDevice = this.mUsbDevice;
        if (usbDevice == null) {
            return false;
        }
        if (!this.mUsbManager.hasPermission(usbDevice)) {
            Intent intent = new Intent(ZkDeviceConstants.ACTION_USB_DEVICE_PERMISSION);
            intent.setPackage(this.mContext.getPackageName());
            this.mUsbManager.requestPermission(this.mUsbDevice, PendingIntent.getBroadcast(this.mContext, 0, intent, 1073741824));
            return false;
        }
        this.mIsHaveUsbPermission.set(true);
        onObtainedPermission();
        return true;
    }

    public void onObtainedPermission() {
        deviceStateChange(0, deviceName());
    }

    /* access modifiers changed from: protected */
    public boolean isThisUsbDevice(UsbDevice usbDevice) {
        if (usbDevice != null && usbDevice.getProductId() == devicePid() && usbDevice.getVendorId() == deviceVid()) {
            return true;
        }
        return false;
    }

    public int deviceState() {
        return this.mDeviceState;
    }
}
