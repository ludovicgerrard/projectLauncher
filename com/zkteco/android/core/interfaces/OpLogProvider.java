package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class OpLogProvider extends AbstractProvider implements OpLogInterface {
    private OpLogProvider(Provider provider) {
        super(provider);
    }

    public static OpLogProvider getInstance(Provider provider) {
        return new OpLogProvider(provider);
    }

    public void addOpLog(int i, String str, int i2, int i3, int i4) {
        getProvider().invoke(OpLogInterface.OPLOG_ADD, Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
    }
}
