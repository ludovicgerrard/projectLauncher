package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class LightProvider extends AbstractProvider implements LightInterface {
    public LightProvider(Provider provider) {
        super(provider);
    }

    public int setLightState(int i, int i2) {
        return ((Integer) getProvider().invoke(LightInterface.setLightState, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
    }

    public int getLightState(int i) {
        return ((Integer) getProvider().invoke(LightInterface.getLightState, Integer.valueOf(i))).intValue();
    }
}
