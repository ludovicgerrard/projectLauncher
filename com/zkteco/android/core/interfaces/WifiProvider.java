package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.interfaces.WifiInterface;
import com.zkteco.android.core.library.Provider;
import java.util.List;

public class WifiProvider extends AbstractProvider implements WifiInterface {
    private WifiProvider(Provider provider) {
        super(provider);
    }

    public static WifiProvider getInstance(Provider provider) {
        return new WifiProvider(provider);
    }

    public void enable() {
        getProvider().invoke(WifiInterface.ENABLE, new Object[0]);
    }

    public void disable() {
        getProvider().invoke(WifiInterface.DISABLE, new Object[0]);
    }

    public List<WifiInterface.WifiScanResult> availableNetworks() {
        return (List) getProvider().invoke(WifiInterface.NETWORKS, new Object[0]);
    }

    public void setConfiguration(String str, WifiInterface.WifiConfig wifiConfig) {
        getProvider().invoke(WifiInterface.SET_CONFIGURATION, str, wifiConfig);
    }

    public WifiInterface.WifiConfig getConfiguration(String str) {
        return (WifiInterface.WifiConfig) getProvider().invoke(WifiInterface.GET_CONFIGURATION, str);
    }

    public void connect(String str) {
        getProvider().invoke(WifiInterface.CONNECT, str);
    }

    public void disconnect() {
        getProvider().invoke(WifiInterface.DISCONNECT, new Object[0]);
    }

    public void forget(String str) {
        getProvider().invoke(WifiInterface.FORGET, str);
    }

    public String getConnectStatus() {
        return (String) getProvider().invoke(WifiInterface.CONNECT_STATUS, new Object[0]);
    }

    public String getConnectedWifiName() {
        return (String) getProvider().invoke(WifiInterface.CONNECTED_WIFI_NAME, new Object[0]);
    }

    public boolean getWifiState() {
        return ((Boolean) getProvider().invoke(WifiInterface.WIFI_STATE, new Object[0])).booleanValue();
    }
}
