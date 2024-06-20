package com.zkteco.android.io;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public final class NetworkHelper {
    private static final int NTP_SERVER_ID = Resources.getSystem().getIdentifier("config_ntpServer", "string", "android");
    private static final String PING_SERVER_ONCE = "ping -c 1 %s";

    public static String getWifiMAC(Context context) {
        WifiInfo connectionInfo;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
            return null;
        }
        return connectionInfo.getMacAddress();
    }

    public static boolean hasNetworkConnectivity(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public static boolean isNTPServerReachable(Context context) {
        if (ShellHelper.execCommandAsSu(String.format(PING_SERVER_ONCE, new Object[]{context.getString(NTP_SERVER_ID)})).exitCode == 0) {
            return true;
        }
        return false;
    }

    private NetworkHelper() {
    }
}
