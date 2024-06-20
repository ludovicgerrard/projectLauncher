package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.LauncherInterface;
import com.zkteco.android.core.interfaces.LauncherProvider;
import com.zkteco.android.core.library.CoreProvider;

public class LauncherManager implements LauncherInterface {
    private LauncherProvider provider;

    public LauncherManager(Context context) {
        this.provider = LauncherProvider.getInstance(new CoreProvider(context));
    }

    public void setDefaultLauncher(String str, String str2) {
        this.provider.setDefaultLauncher(str, str2);
    }
}
