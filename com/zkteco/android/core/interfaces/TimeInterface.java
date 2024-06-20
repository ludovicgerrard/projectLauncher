package com.zkteco.android.core.interfaces;

public interface TimeInterface {
    public static final String SETTIME = "set-time";
    public static final String SETTIMEZONE = "set-timezone";

    void setTime(long j);

    void setTimeZone(String str);
}
