package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.WiegandInterface;
import com.zkteco.android.core.interfaces.WiegandListener;
import com.zkteco.android.core.interfaces.WiegandProvider;
import com.zkteco.android.core.interfaces.WiegandReceiver;
import com.zkteco.android.core.library.CoreProvider;

public class WiegandManager implements WiegandInterface {
    private Context context;
    private WiegandProvider provider;
    private WiegandReceiver receiver = new WiegandReceiver();

    @Deprecated
    public String readWiegandInData() {
        return "";
    }

    public WiegandManager(Context context2) {
        this.context = context2;
        this.provider = WiegandProvider.getInstance(new CoreProvider(context2));
    }

    public void register() {
        this.receiver.register(this.context);
    }

    public void unregister() {
        this.receiver.unregister(this.context);
    }

    public void setListener(WiegandListener wiegandListener) {
        this.receiver.setListener(wiegandListener);
    }

    public boolean setWiegandOutProperty(int i, int i2, int i3) {
        return this.provider.setWiegandOutProperty(i, i2, i3);
    }

    public boolean sentWiegandData(byte[] bArr) {
        return this.provider.sentWiegandData(bArr);
    }
}
