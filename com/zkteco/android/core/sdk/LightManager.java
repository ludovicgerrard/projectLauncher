package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.LightInterface;
import com.zkteco.android.core.interfaces.LightProvider;
import com.zkteco.android.core.library.CoreProvider;

public class LightManager implements LightInterface {
    private LightProvider mLightProvider;

    public LightManager(Context context) {
        this.mLightProvider = new LightProvider(new CoreProvider(context));
    }

    public int setLightState(int i, int i2) {
        return this.mLightProvider.setLightState(i, i2);
    }

    public int getLightState(int i) {
        return this.mLightProvider.getLightState(i);
    }
}
