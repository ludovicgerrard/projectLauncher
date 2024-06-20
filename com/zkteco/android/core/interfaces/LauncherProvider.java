package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class LauncherProvider extends AbstractProvider implements LauncherInterface {
    private static volatile LauncherProvider instance;

    private LauncherProvider(Provider provider) {
        super(provider);
    }

    public static LauncherProvider getInstance(Provider provider) {
        if (instance == null) {
            synchronized (LauncherProvider.class) {
                if (instance == null) {
                    instance = new LauncherProvider(provider);
                }
            }
        }
        return instance;
    }

    public void setDefaultLauncher(String str, String str2) {
        getProvider().invoke(LauncherInterface.SET_DEFAULT_HOME_LAUNCHER, str, str2);
    }
}
