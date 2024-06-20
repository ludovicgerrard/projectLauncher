package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class UsbStorageProvider extends AbstractProvider implements UsbStorageInterface {
    public UsbStorageProvider(Provider provider) {
        super(provider);
    }

    public static UsbStorageProvider getInstance(Provider provider) {
        return new UsbStorageProvider(provider);
    }

    public Boolean isConnected() {
        return (Boolean) getProvider().invoke(UsbStorageInterface.IS_CONNECTED, new Object[0]);
    }

    public String getPath() {
        return (String) getProvider().invoke(UsbStorageInterface.GET_PATH, new Object[0]);
    }

    public Boolean canWrite() {
        return (Boolean) getProvider().invoke(UsbStorageInterface.CAN_WRITE, new Object[0]);
    }

    public Boolean canRead() {
        return (Boolean) getProvider().invoke(UsbStorageInterface.CAN_READ, new Object[0]);
    }

    public Boolean isHasUDiskDev() {
        return (Boolean) getProvider().invoke(UsbStorageInterface.IS_HASUSB, new Object[0]);
    }

    public long getUsbCapacity() {
        return ((Long) getProvider().invoke(UsbStorageInterface.GET_CAPACITY, new Object[0])).longValue();
    }

    public long getUsbOccupiedSpace() {
        return ((Long) getProvider().invoke(UsbStorageInterface.GET_OCCUSPACE, new Object[0])).longValue();
    }

    public long getUsbFreeSpace() {
        return ((Long) getProvider().invoke(UsbStorageInterface.GET_FREESPACE, new Object[0])).longValue();
    }

    public long getUsbChunkSize() {
        return ((Long) getProvider().invoke(UsbStorageInterface.GET_CHUNKSIZE, new Object[0])).longValue();
    }
}
