package com.zkteco.edk.mcu.lib;

public interface IRs485Listener {
    void onRs485Detected(byte[] bArr);
}
