package com.zkteco.android.core.interfaces;

public interface RS232EncryptProtocolInterface {
    public static final String RS232_START = "rs232_start";
    public static final String RS232_STOP = "rs232_stop";
    public static final String SET_157_VERIFY_TYPE = "set_157_verify_type";
    public static final String SET_SERIAL_PORT_ADDRESS = "set_serial_port_address";
    public static final String SET_SERIAL_PORT_PARAMETER_ONE = "set_serial_port_parameter_one";
    public static final String SET_SERIAL_PORT_PARAMETER_THREE = "set_serial_port_parameter_three";
    public static final String SET_SERIAL_PORT_PARAMETER_TWO = "set_serial_port_parameter_two";
    public static final byte VERIFY_CONTINUE_VERIFY = -3;
    public static final byte VERIFY_FAILED = -2;
    public static final byte VERIFY_NO_PERMISSION = -1;
    public static final String VERIFY_RESULT_158 = "verify_result_158";
    public static final byte VERIFY_SUCCESS = 0;

    void rs232Start();

    void rs232Stop();

    void set157VerifyType(byte b);

    void setRS232ParameterOne(int i, int i2, int i3, int i4);

    void setRS232ParameterThree(int i, int i2, int i3, int i4);

    void setRS232ParameterTwo(int i, int i2, int i3, int i4);

    void setRS232SerialPortAddress(byte b);

    void verifyResult(byte b);
}
