package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.PushProtocolInterface;
import com.zkteco.android.core.interfaces.PushProtocolProvider;
import com.zkteco.android.core.library.CoreProvider;
import java.io.IOException;

public class PushProtocolManager implements PushProtocolInterface {
    private PushProtocolProvider provider;

    public PushProtocolManager(Context context) {
        this.provider = PushProtocolProvider.getInstance(new CoreProvider(context));
    }

    public void pushInit() {
        this.provider.pushInit();
    }

    public void sendHubCmd(int i, int i2, int i3, String str) throws IOException {
        this.provider.sendHubCmd(i, i2, i3, str);
    }
}
