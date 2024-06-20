package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class DistanceProvider extends AbstractProvider implements DistanceInterface {
    public DistanceProvider(Provider provider) {
        super(provider);
    }

    public static DistanceProvider getNewProvider(Provider provider) {
        return new DistanceProvider(provider);
    }

    public boolean isDeviceOpen() {
        return ((Boolean) getProvider().invoke(DistanceInterface.OPEN_DISTANCE_DEVICE, new Object[0])).booleanValue();
    }

    public void setDistanceThreshold(int i) {
        getProvider().invoke(DistanceInterface.SET_DISTANCE_THRESHOLD, Integer.valueOf(i));
    }

    public void setWorkTime(int i) {
        getProvider().invoke(DistanceInterface.SET_WORK_TIME, Integer.valueOf(i));
    }
}
