package com.zkteco.android.core.interfaces;

import java.io.IOException;

public interface StandaloneProtocolInterface {
    public static final String STANDALONE_INIT = "standalone_init";
    public static final String STANDALONE_SEND = "standalone_send";

    void sendHubCmd(int i, int i2, int i3, String str) throws IOException;

    void standaloneInit();
}
