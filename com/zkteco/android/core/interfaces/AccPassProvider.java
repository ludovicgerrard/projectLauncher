package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class AccPassProvider extends AbstractProvider implements AccPassInterface {
    public AccPassProvider(Provider provider) {
        super(provider);
    }

    public static AccPassProvider getInstance(Provider provider) {
        return new AccPassProvider(provider);
    }

    public int validCode(int i) {
        return ((Integer) getProvider().invoke(AccPassInterface.VALID_CODE, Integer.valueOf(i))).intValue();
    }
}
