package com.zkteco.android.core.library;

import com.zkteco.android.io.ConfigurationReader;
import com.zkteco.android.io.DefaultConfigurationReader;
import com.zkteco.android.io.YAMLConfigurationReader;
import kotlin.Metadata;
import kotlin.TypeCastException;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/zkteco/android/core/library/Configuration;", "", "()V", "configurationReader", "Lcom/zkteco/android/io/ConfigurationReader;", "isLogInvokeMethodCalls", "", "()Z", "CoreServiceLibrary_release"}, k = 1, mv = {1, 1, 16})
/* compiled from: Configuration.kt */
public final class Configuration {
    public static final Configuration INSTANCE = new Configuration();
    private static final ConfigurationReader configurationReader;
    private static final boolean isLogInvokeMethodCalls;

    static {
        ConfigurationReader configurationReader2;
        try {
            configurationReader2 = new YAMLConfigurationReader();
        } catch (Exception unused) {
            configurationReader2 = new DefaultConfigurationReader();
        }
        configurationReader = configurationReader2;
        Object obj = configurationReader2.get("log-invoke-method", false);
        if (obj != null) {
            isLogInvokeMethodCalls = ((Boolean) obj).booleanValue();
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
    }

    private Configuration() {
    }

    public final boolean isLogInvokeMethodCalls() {
        return isLogInvokeMethodCalls;
    }
}
