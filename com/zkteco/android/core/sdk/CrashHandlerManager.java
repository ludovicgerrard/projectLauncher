package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.CrashHandlerInterface;
import com.zkteco.android.core.interfaces.CrashHandlerProvider;
import com.zkteco.android.core.library.CoreProvider;

public class CrashHandlerManager implements CrashHandlerInterface {
    private CrashHandlerProvider provider;

    public CrashHandlerManager(Context context) {
        this.provider = CrashHandlerProvider.getInstance(new CoreProvider(context));
    }

    public void saveException(Throwable th) {
        this.provider.saveException(th);
    }
}
