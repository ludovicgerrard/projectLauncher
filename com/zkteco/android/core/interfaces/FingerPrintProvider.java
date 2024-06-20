package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class FingerPrintProvider extends AbstractProvider implements FingerPrintInterface {
    public FingerPrintProvider(Provider provider) {
        super(provider);
    }

    public static FingerPrintProvider getInstance(Provider provider) {
        return new FingerPrintProvider(provider);
    }

    public boolean startSensor() {
        return ((Boolean) getProvider().invoke(FingerPrintInterface.OPEN, new Object[0])).booleanValue();
    }

    public void close() {
        getProvider().invoke(FingerPrintInterface.CLOSE, new Object[0]);
    }
}
