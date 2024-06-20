package com.zkteco.zkinfraredservice.irpalm;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.text.TextUtils;

public class ZKPalmUKeyConnection {
    private static final int INTERFACE_PROTOCOL = 80;
    private static final int INTERFACE_SUBCLASS = 6;
    private final int TYPE_USB = 0;
    private int mBusNum = 0;
    private UsbDeviceConnection mConnection = null;
    private int mDevAddr = 0;
    private String mDeviceName = "";
    private int mFd = 0;
    private int mInEndPointAddr = 0;
    private UsbEndpoint mInEndpoint = null;
    private int mOutEndPointAddr = 0;
    private UsbEndpoint mOutEndpoint = null;
    private int mProductID = 0;
    private UsbInterface mUSBInterface = null;
    private int mVendorID = 0;

    public int getTransportType() {
        return 0;
    }

    public int getVendorID() {
        return this.mVendorID;
    }

    public int getProductID() {
        return this.mProductID;
    }

    public int getFD() {
        return this.mFd;
    }

    public int getBusNum() {
        return this.mBusNum;
    }

    public int getDevAddr() {
        return this.mDevAddr;
    }

    public int getInEndPointAddr() {
        return this.mInEndPointAddr;
    }

    public int getOutEndPointAddr() {
        return this.mOutEndPointAddr;
    }

    public int control(int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6) {
        return this.mConnection.controlTransfer(i, i2, i3, i4, bArr, i5, i6);
    }

    public int read(byte[] bArr, int i, int i2) {
        return this.mConnection.bulkTransfer(this.mInEndpoint, bArr, i, i2);
    }

    public int write(byte[] bArr, int i, int i2) {
        return this.mConnection.bulkTransfer(this.mOutEndpoint, bArr, i, i2);
    }

    public boolean openDevice(Context context, int i, int i2, int i3) {
        UsbManager usbManager = (UsbManager) context.getSystemService("usb");
        int i4 = 0;
        UsbDevice usbDevice = null;
        for (UsbDevice next : usbManager.getDeviceList().values()) {
            if (next.getVendorId() != i) {
                int i5 = i2;
            } else if (next.getProductId() == i2) {
                int i6 = i4 + 1;
                if (i4 == i3) {
                    int interfaceCount = next.getInterfaceCount();
                    int i7 = 0;
                    while (true) {
                        if (i7 >= interfaceCount) {
                            break;
                        }
                        UsbInterface usbInterface = next.getInterface(i7);
                        if (usbInterface.getInterfaceClass() == 8 && usbInterface.getInterfaceSubclass() == 6 && usbInterface.getInterfaceProtocol() == 80) {
                            for (int i8 = 0; i8 < usbInterface.getEndpointCount(); i8++) {
                                UsbEndpoint endpoint = usbInterface.getEndpoint(i8);
                                if (endpoint.getType() == 2) {
                                    if (endpoint.getDirection() == 0) {
                                        this.mOutEndpoint = endpoint;
                                    } else {
                                        this.mInEndpoint = endpoint;
                                    }
                                }
                            }
                            this.mUSBInterface = usbInterface;
                            usbDevice = next;
                        } else {
                            i7++;
                        }
                    }
                }
                i4 = i6;
            }
            int i9 = i3;
        }
        if (usbDevice == null || this.mUSBInterface == null) {
            return false;
        }
        UsbDeviceConnection openDevice = usbManager.openDevice(usbDevice);
        this.mConnection = openDevice;
        if (openDevice == null) {
            this.mUSBInterface = null;
            return false;
        }
        this.mVendorID = usbDevice.getVendorId();
        this.mProductID = usbDevice.getProductId();
        this.mFd = this.mConnection.getFileDescriptor();
        UsbEndpoint usbEndpoint = this.mInEndpoint;
        if (usbEndpoint != null) {
            this.mInEndPointAddr = usbEndpoint.getAddress();
        }
        UsbEndpoint usbEndpoint2 = this.mOutEndpoint;
        if (usbEndpoint2 != null) {
            this.mOutEndPointAddr = usbEndpoint2.getAddress();
        }
        String deviceName = usbDevice.getDeviceName();
        this.mDeviceName = deviceName;
        String[] split = !TextUtils.isEmpty(deviceName) ? this.mDeviceName.split("/") : null;
        if (split != null && split.length >= 2) {
            this.mBusNum = Integer.parseInt(split[split.length - 2]);
            this.mDevAddr = Integer.parseInt(split[split.length - 1]);
        }
        this.mConnection.claimInterface(this.mUSBInterface, true);
        return true;
    }

    public boolean closeDevice() {
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection != null) {
            usbDeviceConnection.releaseInterface(this.mUSBInterface);
            this.mUSBInterface = null;
            this.mConnection.close();
        }
        this.mConnection = null;
        this.mInEndpoint = null;
        this.mOutEndpoint = null;
        this.mUSBInterface = null;
        return true;
    }
}
