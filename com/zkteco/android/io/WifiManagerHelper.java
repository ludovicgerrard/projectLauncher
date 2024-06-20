package com.zkteco.android.io;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0006H\u0007J\b\u0010\u0017\u001a\u00020\u0015H\u0007J\b\u0010\u0018\u001a\u00020\u0015H\u0007J\b\u0010\u0019\u001a\u00020\u0015H\u0007J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0016\u001a\u00020\u0006H\u0003J\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0006H\u0007J\b\u0010\u001c\u001a\u00020\u000fH\u0002J\b\u0010\u001d\u001a\u00020\u0015H\u0002J\u000e\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020 J\b\u0010!\u001a\u00020\u0015H\u0002J\b\u0010\"\u001a\u00020\u0015H\u0002J\b\u0010#\u001a\u00020\u0015H\u0002J\b\u0010$\u001a\u00020\u0015H\u0003J\b\u0010%\u001a\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006XD¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006XD¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \t*\u0004\u0018\u00010\u00060\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX.¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/zkteco/android/io/WifiManagerHelper;", "", "()V", "DEFAULT_PRIORITY", "", "DEFAULT_STRING", "", "SURROUND_BY_QUOTES", "TAG", "kotlin.jvm.PlatformType", "androidWifiManager", "Landroid/net/wifi/WifiManager;", "lock", "Ljava/lang/Object;", "nonePasswordConfig", "Landroid/net/wifi/WifiConfiguration;", "wepPasswordConfig", "wpaPasswordConfig", "availableNetworks", "", "connect", "", "wifiName", "disable", "disconnect", "enable", "findNetworkConfig", "forget", "getDefaultConfig", "initConfigs", "initialize", "context", "Landroid/content/Context;", "setNonePasswordConfig", "setWepPasswordConfig", "setWpaPasswordConfig", "startScan", "waitForResult", "HelpersAndroidIO_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: WifiManagerHelper.kt */
public final class WifiManagerHelper {
    private static final int DEFAULT_PRIORITY = 5;
    private static final String DEFAULT_STRING = DEFAULT_STRING;
    public static final WifiManagerHelper INSTANCE = new WifiManagerHelper();
    private static final String SURROUND_BY_QUOTES = SURROUND_BY_QUOTES;
    private static final String TAG = WifiManagerHelper.class.getSimpleName();
    private static WifiManager androidWifiManager;
    private static final Object lock = new Object();
    private static WifiConfiguration nonePasswordConfig;
    private static WifiConfiguration wepPasswordConfig;
    private static WifiConfiguration wpaPasswordConfig;

    private WifiManagerHelper() {
    }

    public final void initialize(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Object systemService = context.getApplicationContext().getSystemService("wifi");
        if (systemService != null) {
            androidWifiManager = (WifiManager) systemService;
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.net.wifi.WifiManager");
    }

    public final void enable() {
        WifiManager wifiManager = androidWifiManager;
        if (wifiManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
        }
        wifiManager.setWifiEnabled(true);
    }

    public final void disable() {
        WifiManager wifiManager = androidWifiManager;
        if (wifiManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
        }
        wifiManager.setWifiEnabled(false);
    }

    public final List<String> availableNetworks() {
        startScan();
        waitForResult();
        ArrayList arrayList = new ArrayList();
        WifiManager wifiManager = androidWifiManager;
        if (wifiManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        Log.d(TAG, String.valueOf(scanResults.size()) + " available wifi network found!");
        for (ScanResult scanResult : scanResults) {
            arrayList.add(scanResult.SSID);
        }
        return arrayList;
    }

    public final void connect(String str) {
        Intrinsics.checkParameterIsNotNull(str, "wifiName");
        WifiConfiguration findNetworkConfig = findNetworkConfig(str);
        if (findNetworkConfig != null) {
            disconnect();
            WifiManager wifiManager = androidWifiManager;
            if (wifiManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
            }
            wifiManager.enableNetwork(findNetworkConfig.networkId, true);
            WifiManager wifiManager2 = androidWifiManager;
            if (wifiManager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
            }
            wifiManager2.reconnect();
        }
    }

    public final void disconnect() {
        WifiManager wifiManager = androidWifiManager;
        if (wifiManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
        }
        wifiManager.disconnect();
    }

    public final void forget(String str) {
        Intrinsics.checkParameterIsNotNull(str, "wifiName");
        WifiConfiguration findNetworkConfig = findNetworkConfig(str);
        if (findNetworkConfig != null) {
            WifiManager wifiManager = androidWifiManager;
            if (wifiManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
            }
            wifiManager.removeNetwork(findNetworkConfig.networkId);
            WifiManager wifiManager2 = androidWifiManager;
            if (wifiManager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
            }
            wifiManager2.saveConfiguration();
        }
    }

    private final WifiConfiguration findNetworkConfig(String str) {
        WifiConfiguration wifiConfiguration = null;
        WifiManager wifiManager = androidWifiManager;
        if (wifiManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
        }
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        for (int i = 0; i < configuredNetworks.size() && wifiConfiguration == null; i++) {
            WifiConfiguration wifiConfiguration2 = configuredNetworks.get(i);
            String str2 = wifiConfiguration2.SSID;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(SURROUND_BY_QUOTES, Arrays.copyOf(new Object[]{str}, 1));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
            if (Intrinsics.areEqual((Object) str2, (Object) format)) {
                wifiConfiguration = wifiConfiguration2;
            }
        }
        return wifiConfiguration;
    }

    private final void initConfigs() {
        setNonePasswordConfig();
        setWepPasswordConfig();
        setWpaPasswordConfig();
    }

    private final void setNonePasswordConfig() {
        WifiConfiguration defaultConfig = getDefaultConfig();
        nonePasswordConfig = defaultConfig;
        if (defaultConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nonePasswordConfig");
        }
        defaultConfig.allowedAuthAlgorithms.clear();
        defaultConfig.allowedKeyManagement.set(0);
        defaultConfig.allowedGroupCiphers.set(3);
        defaultConfig.allowedGroupCiphers.set(2);
    }

    private final void setWepPasswordConfig() {
        WifiConfiguration defaultConfig = getDefaultConfig();
        wepPasswordConfig = defaultConfig;
        if (defaultConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wepPasswordConfig");
        }
        defaultConfig.allowedKeyManagement.set(0);
        WifiConfiguration wifiConfiguration = wepPasswordConfig;
        if (wifiConfiguration == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wepPasswordConfig");
        }
        wifiConfiguration.allowedAuthAlgorithms.set(0);
        WifiConfiguration wifiConfiguration2 = wepPasswordConfig;
        if (wifiConfiguration2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wepPasswordConfig");
        }
        wifiConfiguration2.allowedAuthAlgorithms.set(1);
        WifiConfiguration wifiConfiguration3 = wepPasswordConfig;
        if (wifiConfiguration3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wepPasswordConfig");
        }
        wifiConfiguration3.wepTxKeyIndex = 0;
    }

    private final void setWpaPasswordConfig() {
        WifiConfiguration defaultConfig = getDefaultConfig();
        wpaPasswordConfig = defaultConfig;
        if (defaultConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wpaPasswordConfig");
        }
        defaultConfig.allowedKeyManagement.set(1);
        WifiConfiguration wifiConfiguration = wpaPasswordConfig;
        if (wifiConfiguration == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wpaPasswordConfig");
        }
        wifiConfiguration.allowedGroupCiphers.set(3);
        WifiConfiguration wifiConfiguration2 = wpaPasswordConfig;
        if (wifiConfiguration2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wpaPasswordConfig");
        }
        wifiConfiguration2.allowedGroupCiphers.set(2);
    }

    private final WifiConfiguration getDefaultConfig() {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.status = 1;
        wifiConfiguration.priority = DEFAULT_PRIORITY;
        wifiConfiguration.allowedProtocols.set(1);
        wifiConfiguration.allowedProtocols.set(0);
        wifiConfiguration.allowedPairwiseCiphers.set(2);
        wifiConfiguration.allowedPairwiseCiphers.set(1);
        wifiConfiguration.allowedGroupCiphers.set(0);
        wifiConfiguration.allowedGroupCiphers.set(1);
        return wifiConfiguration;
    }

    private final void startScan() {
        WifiManager wifiManager = androidWifiManager;
        if (wifiManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
        }
        if (!wifiManager.isWifiEnabled()) {
            Object obj = lock;
            synchronized (obj) {
                try {
                    Log.d(TAG, "Wifi has not enabled yet!Waiting for it!!");
                    obj.wait();
                    Unit unit = Unit.INSTANCE;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Integer.valueOf(Log.e(TAG, Log.getStackTraceString(e)));
                }
            }
        }
        Log.d(TAG, "Starting wifi list scan!");
        WifiManager wifiManager2 = androidWifiManager;
        if (wifiManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("androidWifiManager");
        }
        wifiManager2.startScan();
        return;
    }

    private final void waitForResult() {
        Object obj = lock;
        synchronized (obj) {
            try {
                Log.d(TAG, "Wifi list has not arrived yet!Waiting for it!!");
                obj.wait();
                Unit unit = Unit.INSTANCE;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Integer.valueOf(Log.e(TAG, Log.getStackTraceString(e)));
            }
        }
        return;
    }
}
