package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.RS232EncryptProtocolInterface;
import com.zkteco.android.core.interfaces.RS232EncryptProtocolListener;
import com.zkteco.android.core.interfaces.RS232EncryptProtocolProvider;
import com.zkteco.android.core.interfaces.RS232EncryptProtocolReceiver;
import com.zkteco.android.core.library.CoreProvider;

public class RS232EncryptManager implements RS232EncryptProtocolInterface {
    private Context context;
    private RS232EncryptProtocolProvider provider;
    private RS232EncryptProtocolReceiver receiver = new RS232EncryptProtocolReceiver();

    public RS232EncryptManager(Context context2) {
        this.context = context2;
        this.provider = new RS232EncryptProtocolProvider(new CoreProvider(context2));
    }

    public void setListener(RS232EncryptProtocolListener rS232EncryptProtocolListener) {
        this.receiver.setRS232EncryptProtocolListener(rS232EncryptProtocolListener);
    }

    public void register() {
        this.receiver.registerReceiver(this.context);
    }

    public void unregister() {
        this.receiver.unregisterReceiver(this.context);
    }

    public void setRS232SerialPortAddress(byte b) {
        this.provider.setRS232SerialPortAddress(b);
    }

    public void setRS232ParameterOne(int i, int i2, int i3, int i4) {
        this.provider.setRS232ParameterOne(i, i2, i3, i4);
    }

    public void setRS232ParameterTwo(int i, int i2, int i3, int i4) {
        this.provider.setRS232ParameterTwo(i, i2, i3, i4);
    }

    public void setRS232ParameterThree(int i, int i2, int i3, int i4) {
        this.provider.setRS232ParameterThree(i, i2, i3, i4);
    }

    public void set157VerifyType(byte b) {
        this.provider.set157VerifyType(b);
    }

    public void rs232Start() {
        this.provider.rs232Start();
    }

    public void rs232Stop() {
        this.provider.rs232Stop();
    }

    public void verifyResult(byte b) {
        this.provider.verifyResult(b);
    }
}
