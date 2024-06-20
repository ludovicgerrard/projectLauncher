package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.UsbStorageInterface;
import com.zkteco.android.core.interfaces.UsbStorageListener;
import com.zkteco.android.core.interfaces.UsbStorageProvider;
import com.zkteco.android.core.interfaces.UsbStorageReceiver;
import com.zkteco.android.core.library.CoreProvider;

public class UsbStorageManager implements UsbStorageInterface {
    private Context context;
    private UsbStorageProvider provider;
    private UsbStorageReceiver receiver;

    public UsbStorageManager(Context context2) {
        this.context = context2;
        this.provider = UsbStorageProvider.getInstance(new CoreProvider(context2));
    }

    public void setListener(UsbStorageListener usbStorageListener) {
        this.receiver = new UsbStorageReceiver(usbStorageListener);
    }

    public void register() {
        this.receiver.register(this.context);
    }

    public void unregister() {
        this.receiver.unregister(this.context);
    }

    public Boolean isConnected() {
        return this.provider.isConnected();
    }

    public String getPath() {
        return this.provider.getPath();
    }

    public Boolean canWrite() {
        return this.provider.canWrite();
    }

    public Boolean canRead() {
        return this.provider.canRead();
    }

    public Boolean isHasUDiskDev() {
        return this.provider.isHasUDiskDev();
    }

    public long getUsbCapacity() {
        return this.provider.getUsbCapacity();
    }

    public long getUsbOccupiedSpace() {
        return this.provider.getUsbOccupiedSpace();
    }

    public long getUsbFreeSpace() {
        return this.provider.getUsbFreeSpace();
    }

    public long getUsbChunkSize() {
        return this.provider.getUsbChunkSize();
    }
}
