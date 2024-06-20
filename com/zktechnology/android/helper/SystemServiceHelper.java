package com.zktechnology.android.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.guide.guidecore.utils.ShutterHandler;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.NetWorkUtil;
import com.zktechnology.android.utils.NetworkUtils;
import com.zktechnology.android.utils.ShellCmds;
import com.zktechnology.android.utils.ZkLogTag;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.edk.camera.lib.ZkThreadPoolManager;
import com.zkteco.edk.system.lib.ZkSystemManager;
import com.zkteco.edk.system.lib.base.ZkSystemConstants;
import com.zkteco.edk.system.lib.bean.ZkEthernetConfig;
import com.zkteco.edk.system.lib.listener.IZkNetworkListener;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SystemServiceHelper {
    private static final String TAG = "SystemServiceHelper";
    /* access modifiers changed from: private */
    public final Context mContext;
    private boolean mLastConnectNetworkState;
    private ScheduledFuture<?> mSchedule;
    private final Runnable task;

    static class SingletonHolder {
        static final SystemServiceHelper INSTANCE = new SystemServiceHelper();

        SingletonHolder() {
        }
    }

    private SystemServiceHelper() {
        this.mLastConnectNetworkState = false;
        this.task = new Runnable() {
            public void run() {
                Log.i(ZkLogTag.NETWORK, "Start to query and set network information");
                FileLogUtils.writeNetworkLog("Start to query and set network information");
                try {
                    Context launcherApplicationContext = LauncherApplication.getLauncherApplicationContext();
                    int bindService = ZkSystemManager.getInstance().bindService(launcherApplicationContext);
                    if (bindService != 0) {
                        String str = "EDK System bind failed, ret: " + bindService;
                        Log.w(ZkLogTag.NETWORK, str);
                        FileLogUtils.writeNetworkLog(str);
                        return;
                    }
                    SystemServiceHelper.this.waitBindSystemServiceSuccess();
                    NetworkUtils.updateConnectivity(SystemServiceHelper.this.mContext);
                    NetworkInfo activeNetworkInfo = ((ConnectivityManager) launcherApplicationContext.getSystemService("connectivity")).getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) {
                        if (activeNetworkInfo.getType() == 9) {
                            SystemServiceHelper.this.setEthernetInfo();
                        } else if (activeNetworkInfo.getType() == 1) {
                            SystemServiceHelper.this.setWifiInfo(launcherApplicationContext);
                        }
                    }
                    SystemServiceHelper.this.setWebServerURL();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.mContext = LauncherApplication.getLauncherApplicationContext().getApplicationContext();
    }

    public static SystemServiceHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public final void init() {
        Log.d(TAG, "initSystem: [start init system]");
        ZkSystemManager.getInstance().addSystemListener(new IZkNetworkListener() {
            public final void onNetworkConnectivityChange(boolean z, int i) {
                SystemServiceHelper.this.lambda$init$0$SystemServiceHelper(z, i);
            }
        });
        Log.d(TAG, String.format("initSystem: [bindService result %d]", new Object[]{Integer.valueOf(ZkSystemManager.getInstance().bindService(this.mContext))}));
        waitBindSystemServiceSuccess();
        ZkThreadPoolManager.getInstance().execute(this.task);
        if (!NetworkUtils.tryRestoreNetwork(this.mContext)) {
            connectNetwork(this.mContext);
        }
    }

    public /* synthetic */ void lambda$init$0$SystemServiceHelper(boolean z, int i) {
        if (!this.mLastConnectNetworkState && z) {
            ScheduledFuture<?> scheduledFuture = this.mSchedule;
            if (scheduledFuture != null && !scheduledFuture.isCancelled() && !this.mSchedule.isDone()) {
                this.mSchedule.cancel(true);
            }
            this.mSchedule = ZkThreadPoolManager.getInstance().schedule(this.task, 5, TimeUnit.SECONDS);
        }
        this.mLastConnectNetworkState = z;
    }

    private static void connectNetwork(Context context) {
        boolean z = true;
        if (DBManager.getInstance().getIntOption("~NetworkFunOn", 0) != 1) {
            z = false;
        }
        if (!z) {
            getInstance().setEthernetEnabled(false);
            ZkSystemManager.getInstance().setWiFitEnabled(false);
            setMobileDataState(context, false);
            return;
        }
        tryConnectEthernet();
    }

    public static boolean setMobileDataState(Context context, boolean z) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            telephonyManager.getClass().getDeclaredMethod("setDataEnabled", new Class[]{Integer.TYPE, Boolean.TYPE}).invoke(telephonyManager, new Object[]{Integer.valueOf(defaultDataSubscriptionId), Boolean.valueOf(z)});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void tryConnectEthernet() {
        FileLogUtils.writeNetworkLog("尝试连接以太网");
        boolean z = true;
        if ((DBManager.getInstance().getIntOption(ZKDBConfig.OPT_ETHERNET_STATE, 1) == 1) && !DeviceManager.getDefault().isH1()) {
            initG6Usb();
            ZkEthernetConfig zkEthernetConfig = new ZkEthernetConfig();
            zkEthernetConfig.setIpAddress(DBManager.getInstance().getStrOption(DBConfig.COMMUNICATIONLINK_IPADDRESS, ZkSystemConstants.NULL_IP_ADDRESS));
            zkEthernetConfig.setGateway(DBManager.getInstance().getStrOption(DBConfig.COMMUNICATIONLINK_GATEIPADDRESS, ZkSystemConstants.NULL_IP_ADDRESS));
            zkEthernetConfig.setSubnetMask(DBManager.getInstance().getStrOption(DBConfig.COMMUNICATIONLINK_NETMASK, ZkSystemConstants.NULL_IP_ADDRESS));
            zkEthernetConfig.setDns1(DBManager.getInstance().getStrOption(DBConfig.COMMUNICATIONLINK_DNS, ZkSystemConstants.NULL_IP_ADDRESS));
            zkEthernetConfig.setDns2("");
            if (DBManager.getInstance().getIntOption(DBConfig.COMMUNICATIONLINK_DHCP, 0) != 1) {
                z = false;
            }
            zkEthernetConfig.setDhcp(z);
            ZkSystemManager.getInstance().setWiFitEnabled(false);
            getInstance().setEthernetConfig(zkEthernetConfig);
            FileLogUtils.writeNetworkLog("连接以太网: " + zkEthernetConfig);
        }
    }

    private static void initG6Usb() {
        int i;
        if (DeviceManager.getDefault().isG6()) {
            boolean z = true;
            if (DBManager.getInstance().getIntOption(DBConfig.USB_MASTER_MODE, 1) != 1) {
                z = false;
            }
            if (z) {
                i = ShellCmds.setMaster();
            } else {
                i = ShellCmds.setSlaver();
            }
            Log.e(TAG, "getSystemData: 设置USB" + (z ? "主模式" : "从模式") + ", 结果: " + (i == -1 ? "跳过" : i == 0 ? "成功" : "失败"));
        }
    }

    private static void tryConnectWiFi() {
        boolean z = true;
        if (DBManager.getInstance().getIntOption("WIFI", 1) != 1) {
            z = false;
        }
        ZkSystemManager.getInstance().isWifiConnect();
        ZkSystemManager.getInstance().setWiFitEnabled(z);
    }

    /* access modifiers changed from: private */
    public void waitBindSystemServiceSuccess() {
        int i = 3;
        int i2 = -1002;
        while (i2 == -1002 && i > 0) {
            i2 = ZkSystemManager.getInstance().isWifiConnect();
            i--;
            SystemClock.sleep(ShutterHandler.NUC_TIME_MS);
        }
    }

    /* access modifiers changed from: private */
    public void setWebServerURL() {
        String str;
        DataManager instance = DBManager.getInstance();
        if (instance.getIntOption(DBConfig.CLOUD_WEBSERVERURLMODEL, 0) == 1) {
            instance.setStrOption("WebServerURL", instance.getStrOption(DBConfig.CLOUD_ICLOCKSVRURL, ""));
            return;
        }
        String strOption = instance.getStrOption(DBConfig.CLOUD_ICLOCKSVRURL, "");
        String strOption2 = instance.getStrOption(DBConfig.CLOUD_ICLOCKSVRPORT, "");
        if ("0".equals(instance.getStrOption(DBConfig.CLOUD_ISUPPORTSSL, "0"))) {
            str = "http://" + strOption + ":" + strOption2;
        } else {
            str = "https://" + strOption + ":" + strOption2;
        }
        instance.setStrOption("WebServerURL", str);
    }

    /* access modifiers changed from: private */
    public void setWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        wifiManager.setWifiEnabled(true);
        String trim = wifiManager.getConnectionInfo().getMacAddress().trim();
        if (TextUtils.isEmpty(trim)) {
            trim = getMAC("wlan0");
        }
        DataManager instance = DBManager.getInstance();
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        String empty2Zero = empty2Zero(NetWorkUtil.intToIp(dhcpInfo.ipAddress));
        String empty2Zero2 = empty2Zero(NetWorkUtil.getMaskAddress());
        String empty2Zero3 = empty2Zero(NetWorkUtil.intToIp(dhcpInfo.gateway));
        instance.setStrOption("MAC", trim);
        instance.setStrOption(DBConfig.COMMUNICATIONLINK_IPADDRESS, empty2Zero);
        instance.setStrOption(DBConfig.COMMUNICATIONLINK_NETMASK, empty2Zero2);
        instance.setStrOption(DBConfig.COMMUNICATIONLINK_GATEIPADDRESS, empty2Zero3);
        String format = String.format("setWifiInfo mac:%s mask:%s ip:%s gateway:%s", new Object[]{trim, empty2Zero2, empty2Zero, empty2Zero3});
        Log.i(ZkLogTag.NETWORK, format);
        FileLogUtils.writeNetworkLog(format);
    }

    /* access modifiers changed from: private */
    public void setEthernetInfo() {
        ZkEthernetConfig ethernetConfig = getEthernetConfig();
        if (ethernetConfig != null) {
            DataManager instance = DBManager.getInstance();
            String mac = getMAC("eth0");
            String subnetMask = ethernetConfig.getSubnetMask();
            String ipAddress = ethernetConfig.getIpAddress();
            String gateway = ethernetConfig.getGateway();
            String dns1 = ethernetConfig.getDns1();
            instance.setStrOption("MAC", mac);
            instance.setStrOption(DBConfig.COMMUNICATIONLINK_NETMASK, subnetMask);
            instance.setStrOption(DBConfig.COMMUNICATIONLINK_IPADDRESS, ipAddress);
            instance.setStrOption(DBConfig.COMMUNICATIONLINK_GATEIPADDRESS, gateway);
            instance.setStrOption(DBConfig.COMMUNICATIONLINK_DNS, dns1);
            String format = String.format("setEthernetInfo mac:%s mask:%s ip:%s gateway:%s DNS:%s", new Object[]{mac, subnetMask, ipAddress, gateway, dns1});
            Log.i(ZkLogTag.NETWORK, format);
            FileLogUtils.writeNetworkLog(format);
        }
    }

    public String getMAC(String str) {
        try {
            for (T t : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (t.getName().equalsIgnoreCase(str)) {
                    byte[] hardwareAddress = t.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return null;
                    }
                    StringBuilder sb = new StringBuilder();
                    int length = hardwareAddress.length;
                    for (int i = 0; i < length; i++) {
                        sb.append(String.format("%02X:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String empty2Zero(String str) {
        return !TextUtils.isEmpty(str) ? str : ZkSystemConstants.NULL_IP_ADDRESS;
    }

    public final void disconnect() {
        ZkSystemManager.getInstance().removeSystemListener();
        ZkSystemManager.getInstance().unbindService(this.mContext);
    }

    public ZkEthernetConfig getEthernetConfig() {
        ArrayList arrayList = new ArrayList(1);
        int ethernetConfig = ZkSystemManager.getInstance().getEthernetConfig(arrayList);
        if (ethernetConfig == 0) {
            return (ZkEthernetConfig) arrayList.get(0);
        }
        String str = "getEthernetConfig failed, ret " + ethernetConfig;
        Log.w(ZkLogTag.NETWORK, str);
        FileLogUtils.writeNetworkLog(str);
        return null;
    }

    public boolean setEthernetConfig(ZkEthernetConfig zkEthernetConfig) {
        return ZkSystemManager.getInstance().connectEthernet(zkEthernetConfig) == 0;
    }

    public void setEthernetEnabled(boolean z) {
        ZkSystemManager.getInstance().setEthernetEnabled(z);
    }
}
