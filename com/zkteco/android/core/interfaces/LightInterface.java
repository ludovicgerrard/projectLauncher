package com.zkteco.android.core.interfaces;

public interface LightInterface {
    public static final int ACTION_OFF = 0;
    public static final int ACTION_ON = 1;
    public static final int FILL_LIGHT = 0;
    public static final int INFRARED_LIGHT = 1;
    public static final String getLightState = "get_Light_state";
    public static final String setLightState = "set_light_state";

    int getLightState(int i);

    int setLightState(int i, int i2);
}
