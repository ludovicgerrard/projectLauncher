package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;
import java.io.IOException;

public class StandaloneProtocolProvider extends AbstractProvider implements StandaloneProtocolInterface {
    private StandaloneProtocolProvider(Provider provider) {
        super(provider);
    }

    public static StandaloneProtocolProvider getInstance(Provider provider) {
        return new StandaloneProtocolProvider(provider);
    }

    public void standaloneInit() {
        getProvider().invoke(StandaloneProtocolInterface.STANDALONE_INIT, new Object[0]);
    }

    public void sendHubCmd(int i, int i2, int i3, String str) throws IOException {
        getProvider().invoke(StandaloneProtocolInterface.STANDALONE_SEND, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str);
    }
}
