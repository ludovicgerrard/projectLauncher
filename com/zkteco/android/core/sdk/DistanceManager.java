package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.DistanceInterface;
import com.zkteco.android.core.interfaces.DistanceProvider;
import com.zkteco.android.core.library.CoreProvider;

public class DistanceManager implements DistanceInterface {
    private DistanceProvider mDistanceProvider;

    public DistanceManager(Context context) {
        this.mDistanceProvider = new DistanceProvider(new CoreProvider(context));
    }

    public boolean isDeviceOpen() {
        return this.mDistanceProvider.isDeviceOpen();
    }

    public void setDistanceThreshold(int i) {
        this.mDistanceProvider.setDistanceThreshold(i);
    }

    public void setWorkTime(int i) {
        this.mDistanceProvider.setWorkTime(i);
    }
}
