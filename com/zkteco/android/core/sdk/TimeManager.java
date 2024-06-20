package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.TimeInterface;
import com.zkteco.android.core.interfaces.TimeProvider;
import com.zkteco.android.core.library.CoreProvider;

public class TimeManager implements TimeInterface {
    private TimeProvider mTimeProvider;

    public TimeManager(Context context) {
        this.mTimeProvider = new TimeProvider(new CoreProvider(context));
    }

    public void setTime(long j) {
        this.mTimeProvider.setTime(j);
    }

    public void setTimeZone(String str) {
        this.mTimeProvider.setTimeZone(str);
    }
}
