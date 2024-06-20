package com.zkteco.edk.mcu.lib;

public interface IRs232Listener {
    void onRs232Detected(byte[] bArr);
}
