package com.zktechnology.android.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.helper.SystemServiceHelper;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.edk.system.lib.bean.ZkEthernetConfig;

public class NetworkUtils {
    private static final String KEY_CONNECT_TYPE = "connect_type";
    private static final String KEY_DHCP = "dhcp";
    private static final String KEY_DNS_1 = "dns1";
    private static final String KEY_DNS_2 = "dns2";
    private static final String KEY_ETHERNET_AVAILABLE = "available";
    private static final String KEY_ETHERNET_CONNECT = "connect";
    private static final String KEY_GATEWAY = "gateway";
    private static final String KEY_IP_ADDRESS = "ipAddress";
    private static final String KEY_IS_RESTORE_NETWORK = "is_restore_network";
    private static final String KEY_NETWORK_ID = "network_id";
    private static final String KEY_SUBNET_MASK = "subnetMask";
    private static final String TYPE_CELLULAR = "cellular";
    private static final String TYPE_ETHERNET = "ethernet";
    private static final String TYPE_WIFI = "WiFi";

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void updateConnectivity(android.content.Context r5) {
        /*
            java.lang.Class<com.zktechnology.android.utils.NetworkUtils> r0 = com.zktechnology.android.utils.NetworkUtils.class
            monitor-enter(r0)
            android.content.Context r1 = r5.getApplicationContext()     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "connectivity"
            java.lang.Object r1 = r1.getSystemService(r2)     // Catch:{ all -> 0x0054 }
            android.net.ConnectivityManager r1 = (android.net.ConnectivityManager) r1     // Catch:{ all -> 0x0054 }
            android.net.Network r2 = r1.getActiveNetwork()     // Catch:{ all -> 0x0054 }
            if (r2 != 0) goto L_0x001a
            clearConnectParams()     // Catch:{ all -> 0x0054 }
            monitor-exit(r0)
            return
        L_0x001a:
            android.net.NetworkCapabilities r1 = r1.getNetworkCapabilities(r2)     // Catch:{ all -> 0x0054 }
            if (r1 == 0) goto L_0x004f
            r2 = 12
            boolean r2 = r1.hasCapability(r2)     // Catch:{ all -> 0x0054 }
            if (r2 != 0) goto L_0x0029
            goto L_0x004f
        L_0x0029:
            r2 = 1
            boolean r2 = r1.hasTransport(r2)     // Catch:{ all -> 0x0054 }
            r3 = 3
            boolean r3 = r1.hasTransport(r3)     // Catch:{ all -> 0x0054 }
            r4 = 0
            boolean r1 = r1.hasTransport(r4)     // Catch:{ all -> 0x0054 }
            if (r3 == 0) goto L_0x003e
            syncWithEthernet()     // Catch:{ all -> 0x0054 }
            goto L_0x004d
        L_0x003e:
            if (r2 == 0) goto L_0x0044
            syncWithWiFi(r5)     // Catch:{ all -> 0x0054 }
            goto L_0x004d
        L_0x0044:
            if (r1 == 0) goto L_0x004a
            syncWithCellular()     // Catch:{ all -> 0x0054 }
            goto L_0x004d
        L_0x004a:
            clearConnectParams()     // Catch:{ all -> 0x0054 }
        L_0x004d:
            monitor-exit(r0)
            return
        L_0x004f:
            clearConnectParams()     // Catch:{ all -> 0x0054 }
            monitor-exit(r0)
            return
        L_0x0054:
            r5 = move-exception
            monitor-exit(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.NetworkUtils.updateConnectivity(android.content.Context):void");
    }

    private static void syncWithCellular() {
        DeviceManager.getDefault().setProp(KEY_CONNECT_TYPE, TYPE_CELLULAR);
        DeviceManager.getDefault().setProp(KEY_NETWORK_ID, "");
        updateEthernetConfig((ZkEthernetConfig) null);
        FileLogUtils.writeNetworkLog("写入移动数据配置");
    }

    private static void clearConnectParams() {
        DeviceManager.getDefault().setProp(KEY_CONNECT_TYPE, "");
        updateEthernetConfig((ZkEthernetConfig) null);
        DeviceManager.getDefault().setProp(KEY_NETWORK_ID, "");
        FileLogUtils.writeNetworkLog("清除网络连接参数");
    }

    private static void syncWithWiFi(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (connectionInfo != null) {
            DeviceManager.getDefault().setProp(KEY_CONNECT_TYPE, TYPE_WIFI);
            DeviceManager.getDefault().setProp(KEY_NETWORK_ID, String.valueOf(connectionInfo.getNetworkId()));
            updateEthernetConfig((ZkEthernetConfig) null);
            FileLogUtils.writeNetworkLog("写入WiFi配置");
        }
    }

    private static void syncWithEthernet() {
        ZkEthernetConfig ethernetConfig = SystemServiceHelper.getInstance().getEthernetConfig();
        if (ethernetConfig != null) {
            DeviceManager.getDefault().setProp(KEY_CONNECT_TYPE, TYPE_ETHERNET);
            updateEthernetConfig(ethernetConfig);
            DeviceManager.getDefault().setProp(KEY_NETWORK_ID, "");
            FileLogUtils.writeNetworkLog("写入以太网配置: " + ethernetConfig);
        }
    }

    private static void updateEthernetConfig(ZkEthernetConfig zkEthernetConfig) {
        if (zkEthernetConfig == null) {
            DeviceManager.getDefault().setProp(KEY_IP_ADDRESS, "");
            DeviceManager.getDefault().setProp(KEY_DNS_1, "");
            DeviceManager.getDefault().setProp(KEY_DNS_2, "");
            DeviceManager.getDefault().setProp(KEY_SUBNET_MASK, "");
            DeviceManager.getDefault().setProp(KEY_GATEWAY, "");
            DeviceManager.getDefault().setProp(KEY_DHCP, "");
            DeviceManager.getDefault().setProp(KEY_ETHERNET_CONNECT, "");
            DeviceManager.getDefault().setProp(KEY_ETHERNET_AVAILABLE, "");
            return;
        }
        DeviceManager.getDefault().setProp(KEY_IP_ADDRESS, zkEthernetConfig.getIpAddress());
        DeviceManager.getDefault().setProp(KEY_DNS_1, zkEthernetConfig.getDns1());
        DeviceManager.getDefault().setProp(KEY_DNS_2, zkEthernetConfig.getDns2());
        DeviceManager.getDefault().setProp(KEY_SUBNET_MASK, zkEthernetConfig.getSubnetMask());
        DeviceManager.getDefault().setProp(KEY_GATEWAY, zkEthernetConfig.getGateway());
        DeviceManager.getDefault().setProp(KEY_DHCP, Boolean.valueOf(zkEthernetConfig.isDhcp()).toString());
        DeviceManager.getDefault().setProp(KEY_ETHERNET_CONNECT, Boolean.valueOf(zkEthernetConfig.isConnect()).toString());
        DeviceManager.getDefault().setProp(KEY_ETHERNET_AVAILABLE, Boolean.valueOf(zkEthernetConfig.isAvailable()).toString());
    }

    private static boolean isRestoreNetwork() {
        String prop = DeviceManager.getDefault().getProp(KEY_IS_RESTORE_NETWORK);
        FileLogUtils.writeNetworkLog("读取恢复网络配置参数: " + prop);
        return "true".equals(prop);
    }

    public static boolean tryRestoreNetwork(Context context) {
        return isRestoreNetwork() && restoreNetwork(context);
    }

    private static boolean restoreNetwork(Context context) {
        boolean mobileDataState;
        FileLogUtils.writeNetworkLog("开始恢复网络");
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        String prop = DeviceManager.getDefault().getProp(KEY_CONNECT_TYPE);
        try {
            FileLogUtils.writeNetworkLog("恢复网络类型: " + prop);
            if (prop == null) {
                DeviceManager.getDefault().setProp(KEY_IS_RESTORE_NETWORK, "");
                return false;
            }
            char c2 = 65535;
            int hashCode = prop.hashCode();
            if (hashCode != -1419358249) {
                if (hashCode != -916596374) {
                    if (hashCode == 2694997) {
                        if (prop.equals(TYPE_WIFI)) {
                            c2 = 1;
                        }
                    }
                } else if (prop.equals(TYPE_CELLULAR)) {
                    c2 = 0;
                }
            } else if (prop.equals(TYPE_ETHERNET)) {
                c2 = 2;
            }
            if (c2 != 0) {
                if (c2 == 1) {
                    String prop2 = DeviceManager.getDefault().getProp(KEY_NETWORK_ID);
                    if (prop2 != null) {
                        SystemServiceHelper.getInstance().setEthernetEnabled(false);
                        DBManager.getInstance().setIntOption(ZKDBConfig.OPT_ETHERNET_STATE, 0);
                        DBManager.getInstance().setIntOption("WIFI", 1);
                        if (!wifiManager.isWifiEnabled()) {
                            wifiManager.setWifiEnabled(true);
                        }
                        wifiManager.enableNetwork(Integer.parseInt(prop2), true);
                        FileLogUtils.writeNetworkLog("恢复WiFi配置");
                        mobileDataState = wifiManager.reconnect();
                    }
                } else if (c2 == 2) {
                    String prop3 = DeviceManager.getDefault().getProp(KEY_IP_ADDRESS);
                    String prop4 = DeviceManager.getDefault().getProp(KEY_DNS_1);
                    String prop5 = DeviceManager.getDefault().getProp(KEY_DNS_2);
                    String prop6 = DeviceManager.getDefault().getProp(KEY_SUBNET_MASK);
                    String prop7 = DeviceManager.getDefault().getProp(KEY_GATEWAY);
                    boolean parseBoolean = Boolean.parseBoolean(DeviceManager.getDefault().getProp(KEY_DHCP));
                    boolean parseBoolean2 = Boolean.parseBoolean(DeviceManager.getDefault().getProp(KEY_ETHERNET_CONNECT));
                    boolean parseBoolean3 = Boolean.parseBoolean(DeviceManager.getDefault().getProp(KEY_ETHERNET_AVAILABLE));
                    if (!TextUtils.isEmpty(prop3) && !TextUtils.isEmpty(prop6) && !TextUtils.isEmpty(prop7) && !TextUtils.isEmpty(prop4)) {
                        ZkEthernetConfig zkEthernetConfig = new ZkEthernetConfig();
                        zkEthernetConfig.setIpAddress(prop3);
                        zkEthernetConfig.setSubnetMask(prop6);
                        zkEthernetConfig.setGateway(prop7);
                        zkEthernetConfig.setDns1(prop4);
                        if (prop5 == null) {
                            prop5 = "";
                        }
                        zkEthernetConfig.setDns2(prop5);
                        zkEthernetConfig.setDhcp(parseBoolean);
                        zkEthernetConfig.setConnect(parseBoolean2);
                        zkEthernetConfig.setAvailable(parseBoolean3);
                        if (wifiManager.isWifiEnabled()) {
                            wifiManager.setWifiEnabled(false);
                        }
                        FileLogUtils.writeNetworkLog("恢复以太网配置");
                        mobileDataState = SystemServiceHelper.getInstance().setEthernetConfig(zkEthernetConfig);
                    }
                }
                DeviceManager.getDefault().setProp(KEY_IS_RESTORE_NETWORK, "");
                return false;
            }
            DBManager.getInstance().setIntOption(ZKDBConfig.OPT_ETHERNET_STATE, 0);
            DBManager.getInstance().setIntOption(ZKDBConfig.MOBILE_DATA_FUN, 1);
            SystemServiceHelper.getInstance().setEthernetEnabled(false);
            wifiManager.setWifiEnabled(false);
            FileLogUtils.writeNetworkLog("恢复移动数据配置");
            mobileDataState = SystemServiceHelper.setMobileDataState(context, true);
            DeviceManager.getDefault().setProp(KEY_IS_RESTORE_NETWORK, "");
            return mobileDataState;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            DeviceManager.getDefault().setProp(KEY_IS_RESTORE_NETWORK, "");
            throw th;
        }
    }
}
