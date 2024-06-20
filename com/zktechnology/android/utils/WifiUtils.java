package com.zktechnology.android.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.io.ShellHelper;
import com.zkteco.android.zkcore.utils.ZKSharedUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class WifiUtils {
    public static final String COMMUNICATIONLINK_WIFI = "WIFI";
    public static final String COMMUNICATIONLINK_WIRELESSADDR = "WirelessAddr";
    public static final String COMMUNICATIONLINK_WIRELESSDHCP = "WirelessDHCP";
    public static final String COMMUNICATIONLINK_WIRELESSGATEWAY = "WirelessGateWay";
    public static final String COMMUNICATIONLINK_WIRELESSMASK = "WirelessMask";
    private static final String SET_STATIC_IP_COMMAND = "ifconfig %s %s netmask %s";
    private static String TAG = "WifiUtils";
    private static final int WIFICIPHER_NOPASS = 0;
    private static final int WIFICIPHER_WEP = 1;
    private static final int WIFICIPHER_WPA = 2;
    private static final String WIFI_INTERFACE = "wlan0";

    public static void connectByConfig(IpConfig ipConfig) {
        if (ipConfig == null) {
            throw new IllegalArgumentException("No TCP/IP configuration specified");
        } else if (ipConfig.isDhcpEnabled()) {
            Log.i(TAG, "Using DHCP for interface wlan0");
        } else {
            try {
                ShellHelper.execCommands(String.format(SET_STATIC_IP_COMMAND, new Object[]{WIFI_INTERFACE, ipConfig.getIpAddress(), ipConfig.getSubnetMask()}));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void autoContWifi(Context context, WifiManager wifiManager) {
        ZKSharedUtil zKSharedUtil = new ZKSharedUtil(context);
        String string = zKSharedUtil.getString("WIFI_SSID", "");
        int i = zKSharedUtil.getInt("WIFI_TYPE", -1);
        String string2 = zKSharedUtil.getString("WIFI_PWD", "");
        if (!StrUtil.isEmpty(string) && i != -1) {
            List<ScanResult> scanResults = wifiManager.getScanResults();
            for (int i2 = 0; i2 < scanResults.size(); i2++) {
                if (scanResults.get(i2).SSID.equals(string)) {
                    wifiManager.enableNetwork(wifiManager.addNetwork(createWifiConfig(string, string2, i)), true);
                    wifiManager.reconnect();
                    return;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r1 = r1.getActiveNetworkInfo();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isNetworkAvailable(android.content.Context r1) {
        /*
            java.lang.String r0 = "connectivity"
            java.lang.Object r1 = r1.getSystemService(r0)
            android.net.ConnectivityManager r1 = (android.net.ConnectivityManager) r1
            if (r1 == 0) goto L_0x0020
            android.net.NetworkInfo r1 = r1.getActiveNetworkInfo()
            if (r1 == 0) goto L_0x0020
            boolean r0 = r1.isConnected()
            if (r0 == 0) goto L_0x0020
            android.net.NetworkInfo$State r1 = r1.getState()
            android.net.NetworkInfo$State r0 = android.net.NetworkInfo.State.CONNECTED
            if (r1 != r0) goto L_0x0020
            r1 = 1
            return r1
        L_0x0020:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.WifiUtils.isNetworkAvailable(android.content.Context):boolean");
    }

    private WifiConfiguration isExist(String str, WifiManager wifiManager) {
        for (WifiConfiguration next : wifiManager.getConfiguredNetworks()) {
            if (next.SSID.equals("\"" + str + "\"")) {
                return next;
            }
        }
        return null;
    }

    public static WifiConfiguration createWifiConfig(String str, String str2, int i) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + str + "\"";
        if (i == 0) {
            wifiConfiguration.allowedKeyManagement.set(0);
        } else if (i == 1) {
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.wepKeys[0] = "\"" + str2 + "\"";
            wifiConfiguration.allowedAuthAlgorithms.set(0);
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;
        } else if (i == 2) {
            wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.allowedAuthAlgorithms.set(0);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedKeyManagement.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.status = 2;
        }
        return wifiConfiguration;
    }

    public static IpConfig getConfigFromDB(Context context) throws SQLException {
        DataManager instance = DBManager.getInstance();
        IpConfig ipConfig = new IpConfig();
        ipConfig.setIpAddress(instance.getStrOption("WirelessAddr", ""));
        ipConfig.setGateway(instance.getStrOption("WirelessGateWay", ""));
        ipConfig.setSubnetMask(instance.getStrOption("WirelessMask", ""));
        ipConfig.setDhcpEnabled(instance.getStrOption("WirelessDHCP", "").equals("1"));
        return ipConfig;
    }

    public static class IpConfig {
        private boolean dhcpEnabled;
        private String dns1;
        private String gateway;
        private String ipAddress;
        private String subnetMask;

        public String getIpAddress() {
            return this.ipAddress;
        }

        public void setIpAddress(String str) {
            this.ipAddress = str;
        }

        public String getDns1() {
            return this.dns1;
        }

        public void setDns1(String str) {
            this.dns1 = str;
        }

        public String getSubnetMask() {
            return this.subnetMask;
        }

        public void setSubnetMask(String str) {
            this.subnetMask = str;
        }

        public String getGateway() {
            return this.gateway;
        }

        public void setGateway(String str) {
            this.gateway = str;
        }

        public boolean isDhcpEnabled() {
            return this.dhcpEnabled;
        }

        public void setDhcpEnabled(boolean z) {
            this.dhcpEnabled = z;
        }
    }
}
