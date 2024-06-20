package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.AccPassInterface;
import com.zkteco.android.core.interfaces.AccPassProvider;
import com.zkteco.android.core.library.CoreProvider;

public class AccPassManager implements AccPassInterface {
    AccPassProvider provider;

    public AccPassManager(Context context) {
        this.provider = new AccPassProvider(new CoreProvider(context));
    }

    public int validCode(int i) {
        return this.provider.validCode(i);
    }
}
