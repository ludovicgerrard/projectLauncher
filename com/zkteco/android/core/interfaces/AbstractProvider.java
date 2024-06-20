package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public abstract class AbstractProvider {
    private final Provider provider;

    public AbstractProvider(Provider provider2) {
        this.provider = provider2;
    }

    public Provider getProvider() {
        return this.provider;
    }
}
