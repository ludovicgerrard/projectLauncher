package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class WiegandProvider extends AbstractProvider implements WiegandInterface {
    public WiegandProvider(Provider provider) {
        super(provider);
    }

    public static WiegandProvider getInstance(Provider provider) {
        return new WiegandProvider(provider);
    }

    public boolean setWiegandOutProperty(int i, int i2, int i3) {
        return ((Boolean) getProvider().invoke(WiegandInterface.SET_WIEGANDOUT_PROPERTY, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3))).booleanValue();
    }

    public boolean sentWiegandData(byte[] bArr) {
        return ((Boolean) getProvider().invoke(WiegandInterface.SENT_WIEGAND_DATA, bArr)).booleanValue();
    }

    @Deprecated
    public String readWiegandInData() {
        return (String) getProvider().invoke(WiegandInterface.READ_WIEGANDIN_DATA, new Object[0]);
    }
}
