package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class RS232EncryptProtocolProvider extends AbstractProvider implements RS232EncryptProtocolInterface {
    public RS232EncryptProtocolProvider(Provider provider) {
        super(provider);
    }

    public void setRS232SerialPortAddress(byte b) {
        getProvider().invoke(RS232EncryptProtocolInterface.SET_SERIAL_PORT_ADDRESS, Byte.valueOf(b));
    }

    public void setRS232ParameterOne(int i, int i2, int i3, int i4) {
        getProvider().invoke(RS232EncryptProtocolInterface.SET_SERIAL_PORT_PARAMETER_ONE, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
    }

    public void setRS232ParameterTwo(int i, int i2, int i3, int i4) {
        getProvider().invoke(RS232EncryptProtocolInterface.SET_SERIAL_PORT_PARAMETER_TWO, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
    }

    public void setRS232ParameterThree(int i, int i2, int i3, int i4) {
        getProvider().invoke(RS232EncryptProtocolInterface.SET_SERIAL_PORT_PARAMETER_THREE, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
    }

    public void set157VerifyType(byte b) {
        getProvider().invoke(RS232EncryptProtocolInterface.SET_157_VERIFY_TYPE, Byte.valueOf(b));
    }

    public void rs232Start() {
        getProvider().invoke(RS232EncryptProtocolInterface.RS232_START, new Object[0]);
    }

    public void rs232Stop() {
        getProvider().invoke(RS232EncryptProtocolInterface.RS232_STOP, new Object[0]);
    }

    public void verifyResult(byte b) {
        getProvider().invoke(RS232EncryptProtocolInterface.VERIFY_RESULT_158, Byte.valueOf(b));
    }
}
