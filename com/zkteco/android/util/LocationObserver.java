package com.zkteco.android.util;

import android.location.LocationListener;

public interface LocationObserver extends LocationListener {
    void onLocationReady();

    void onLocationRequested();
}
