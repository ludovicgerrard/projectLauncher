package com.zkteco.android.core.interfaces;

public interface RfidInterface {
    public static final String INITIALIZE = "rfid-initialize";
    public static final String MAC = "rfid-mac";
    public static final String OPEN = "rfid-open";
    public static final String SN = "rfid-sn";

    String getDeviceMAC();

    String getDeviceSN();

    boolean initialize();

    boolean openDevice();
}
