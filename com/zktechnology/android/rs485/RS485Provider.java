package com.zktechnology.android.rs485;

import com.zkteco.android.core.interfaces.AbstractProvider;
import com.zkteco.android.core.library.Provider;

public class RS485Provider extends AbstractProvider implements RS485Interface {
    public RS485Provider(Provider provider) {
        super(provider);
    }

    public void verifyResult(byte b) {
        getProvider().invoke(RS485Interface.VERIFY_RESULT, Byte.valueOf(b));
    }

    public void setBaudRate(int i) {
        getProvider().invoke(RS485Interface.SET_BAUDRATE, Integer.valueOf(i));
    }

    public void startRS() {
        getProvider().invoke(RS485Interface.START_RS, new Object[0]);
    }

    public void stopRS() {
        getProvider().invoke(RS485Interface.STOP_RS, new Object[0]);
    }
}
