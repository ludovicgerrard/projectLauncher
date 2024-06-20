package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;
import java.io.IOException;

public class PushProtocolProvider extends AbstractProvider implements PushProtocolInterface {
    private PushProtocolProvider(Provider provider) {
        super(provider);
    }

    public static PushProtocolProvider getInstance(Provider provider) {
        return new PushProtocolProvider(provider);
    }

    public void pushInit() {
        getProvider().invoke(PushProtocolInterface.PUSH_INIT, new Object[0]);
    }

    public void sendHubCmd(int i, int i2, int i3, String str) throws IOException {
        getProvider().invoke(PushProtocolInterface.PUSH_SEND, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str);
    }
}
