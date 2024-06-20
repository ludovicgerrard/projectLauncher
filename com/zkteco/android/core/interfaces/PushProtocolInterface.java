package com.zkteco.android.core.interfaces;

import java.io.IOException;

public interface PushProtocolInterface {
    public static final String PUSH_INIT = "push_init";
    public static final String PUSH_SEND = "push_send";

    void pushInit();

    void sendHubCmd(int i, int i2, int i3, String str) throws IOException;
}
