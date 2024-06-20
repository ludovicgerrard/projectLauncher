package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.StandaloneProtocolInterface;
import com.zkteco.android.core.interfaces.StandaloneProtocolProvider;
import com.zkteco.android.core.library.CoreProvider;
import java.io.IOException;

public class StandaloneProtocolManager implements StandaloneProtocolInterface {
    private StandaloneProtocolProvider provider;

    public StandaloneProtocolManager(Context context) {
        this.provider = StandaloneProtocolProvider.getInstance(new CoreProvider(context));
    }

    public void standaloneInit() {
        this.provider.standaloneInit();
    }

    public void sendHubCmd(int i, int i2, int i3, String str) throws IOException {
        this.provider.sendHubCmd(i, i2, i3, str);
    }
}
