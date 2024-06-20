package com.zkteco.android.io;

import android.util.Log;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00020\u0007X¤\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0019\u0010\n\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lcom/zkteco/android/io/AbstractConfiguration;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "configurationReader", "Lcom/zkteco/android/io/ConfigurationReader;", "getConfigurationReader", "()Lcom/zkteco/android/io/ConfigurationReader;", "rootConfigDirectory", "getRootConfigDirectory", "()Ljava/lang/String;", "HelpersAndroidIO_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: AbstractConfiguration.kt */
public abstract class AbstractConfiguration {
    private final String TAG = getClass().getSimpleName();
    private final String rootConfigDirectory;

    /* access modifiers changed from: protected */
    public abstract ConfigurationReader getConfigurationReader();

    public AbstractConfiguration() {
        ConfigurationReader configurationReader;
        try {
            configurationReader = new YAMLConfigurationReader();
        } catch (Exception e) {
            Log.e(this.TAG, Log.getStackTraceString(e));
            configurationReader = new DefaultConfigurationReader();
        }
        this.rootConfigDirectory = (String) configurationReader.get("root-config-directory", "/system/config");
    }

    public final String getRootConfigDirectory() {
        return this.rootConfigDirectory;
    }
}
