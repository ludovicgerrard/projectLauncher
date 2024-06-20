package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.OpLogInterface;
import com.zkteco.android.core.interfaces.OpLogProvider;
import com.zkteco.android.core.library.CoreProvider;

public class OpLogManager implements OpLogInterface {
    private static OpLogManager mInstence;
    private OpLogProvider provider;

    private OpLogManager(Context context) {
        this.provider = OpLogProvider.getInstance(new CoreProvider(context));
    }

    public static OpLogManager getInstence(Context context) {
        if (mInstence == null) {
            mInstence = new OpLogManager(context);
        }
        return mInstence;
    }

    public void addOpLog(int i, String str, int i2, int i3, int i4) {
        this.provider.addOpLog(i, str, i2, i3, i4);
    }
}
