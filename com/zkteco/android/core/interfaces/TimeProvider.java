package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class TimeProvider extends AbstractProvider implements TimeInterface {
    public TimeProvider(Provider provider) {
        super(provider);
    }

    public void setTime(long j) {
        getProvider().invoke(TimeInterface.SETTIME, Long.valueOf(j));
    }

    public void setTimeZone(String str) {
        getProvider().invoke(TimeInterface.SETTIMEZONE, str);
    }
}
