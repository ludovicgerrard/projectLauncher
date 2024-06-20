package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class RfidProvider extends AbstractProvider implements RfidInterface {
    private RfidProvider(Provider provider) {
        super(provider);
    }

    public static RfidProvider getInstance(Provider provider) {
        return new RfidProvider(provider);
    }

    public boolean openDevice() {
        return ((Boolean) getProvider().invoke(RfidInterface.OPEN, new Object[0])).booleanValue();
    }

    public String getDeviceMAC() {
        return (String) getProvider().invoke(RfidInterface.MAC, new Object[0]);
    }

    public String getDeviceSN() {
        return (String) getProvider().invoke(RfidInterface.SN, new Object[0]);
    }

    public boolean initialize() {
        return ((Boolean) getProvider().invoke(RfidInterface.INITIALIZE, new Object[0])).booleanValue();
    }
}
