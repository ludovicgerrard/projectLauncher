package com.zkteco.android.core.interfaces;

public interface FingerPrintInterface {
    public static final String CLOSE = "fp-close";
    public static final String OPEN = "fp-open";

    void close();

    boolean startSensor();
}
