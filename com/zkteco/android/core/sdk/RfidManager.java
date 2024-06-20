package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.RfidInterface;
import com.zkteco.android.core.interfaces.RfidListener;
import com.zkteco.android.core.interfaces.RfidProvider;
import com.zkteco.android.core.interfaces.RfidReceiver;
import com.zkteco.android.core.library.CoreProvider;

public class RfidManager implements RfidInterface {
    private Context context;
    private RfidProvider provider;
    private RfidReceiver receiver;

    public RfidManager(Context context2) {
        this.context = context2;
        this.provider = RfidProvider.getInstance(new CoreProvider(context2));
    }

    public void setListener(RfidListener rfidListener) {
        this.receiver = new RfidReceiver(rfidListener);
    }

    public void register() {
        this.receiver.register(this.context);
    }

    public void unregister() {
        this.receiver.unregister(this.context);
    }

    public boolean openDevice() {
        return this.provider.openDevice();
    }

    public String getDeviceMAC() {
        return this.provider.getDeviceMAC();
    }

    public String getDeviceSN() {
        return this.provider.getDeviceSN();
    }

    public boolean initialize() {
        return this.provider.initialize();
    }
}
