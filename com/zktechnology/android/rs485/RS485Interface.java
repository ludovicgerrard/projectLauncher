package com.zktechnology.android.rs485;

public interface RS485Interface {
    public static final String SET_BAUDRATE = "set_baudrate";
    public static final String START_RS = "start_rs";
    public static final String STOP_RS = "stop_rs";
    public static final byte VERIFY_CONTINUE_VERIFY = -3;
    public static final byte VERIFY_FAILED = -2;
    public static final byte VERIFY_NO_PERMISSION = -1;
    public static final String VERIFY_RESULT = "verify_result";
    public static final byte VERIFY_SUCCESS = 0;

    void setBaudRate(int i);

    void startRS();

    void stopRS();

    void verifyResult(byte b);
}
