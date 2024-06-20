package com.zkteco.android.core.interfaces;

public interface WiegandInterface {
    @Deprecated
    public static final String READ_WIEGANDIN_DATA = "read-wiegandin-data";
    public static final String SENT_WIEGAND_DATA = "sent-wiegand-data";
    public static final String SET_WIEGANDOUT_PROPERTY = "set-wiegandout-property";

    @Deprecated
    String readWiegandInData();

    boolean sentWiegandData(byte[] bArr);

    boolean setWiegandOutProperty(int i, int i2, int i3);
}
