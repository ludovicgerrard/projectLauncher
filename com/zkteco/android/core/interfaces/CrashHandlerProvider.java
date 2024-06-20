package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class CrashHandlerProvider extends AbstractProvider implements CrashHandlerInterface {
    private CrashHandlerProvider(Provider provider) {
        super(provider);
    }

    public static CrashHandlerProvider getInstance(Provider provider) {
        return new CrashHandlerProvider(provider);
    }

    public void saveException(Throwable th) {
        getProvider().invoke(CrashHandlerInterface.CRASHHANDLER_SAVEEXCEPTION, th);
    }
}
