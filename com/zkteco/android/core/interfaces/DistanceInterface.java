package com.zkteco.android.core.interfaces;

public interface DistanceInterface {
    public static final String OPEN_DISTANCE_DEVICE = "is_open_distance_device";
    public static final String SET_DISTANCE_THRESHOLD = "set_distance_threshold";
    public static final String SET_WORK_TIME = "set_distance_work_time";

    boolean isDeviceOpen();

    void setDistanceThreshold(int i);

    void setWorkTime(int i);
}
