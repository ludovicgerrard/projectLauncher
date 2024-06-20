package com.zktechnology.android.view.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class NetworkStateController extends BroadcastReceiver {
    private static final String TAG = "NetworkStateController";
    private final ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private final OnNetworkStateChangedListener mListener;
    private final PhoneStateTracker mPhoneStateListener;
    private final WifiManager mWifiManager;

    public interface OnNetworkStateChangedListener {
        void onEthernetStateChanged(boolean z);

        void onMobileAvailable(boolean z);

        void onMobileStateChanged(int i, int i2, int i3);

        void onWiFiStateChanged(int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WiFiState {
        public static final int STATE_CONNECTED = 3;
        public static final int STATE_DISABLED = 1;
        public static final int STATE_DISCONNECTED = 2;
        public static final int STATE_OFF = 0;
    }

    public NetworkStateController(Context context, OnNetworkStateChangedListener onNetworkStateChangedListener) {
        this.mContext = context;
        this.mListener = onNetworkStateChangedListener;
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
        PhoneStateTracker phoneStateTracker = new PhoneStateTracker(context, SubscriptionManager.getDefaultDataSubscriptionId(), onNetworkStateChangedListener);
        this.mPhoneStateListener = phoneStateTracker;
        phoneStateTracker.subscribe();
        updateConnectivity();
        register(context, this);
    }

    public void onReceive(Context context, Intent intent) {
        updateConnectivity();
    }

    private static void register(Context context, BroadcastReceiver broadcastReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    static class PhoneStateTracker extends PhoneStateListener {
        private final OnNetworkStateChangedListener listener;
        private final int subId;
        private final TelephonyManager telephonyManager;

        private PhoneStateTracker(Context context, int i, OnNetworkStateChangedListener onNetworkStateChangedListener) {
            this.subId = i;
            this.listener = onNetworkStateChangedListener;
            this.telephonyManager = ((TelephonyManager) context.getSystemService("phone")).createForSubscriptionId(i);
        }

        public void subscribe() {
            this.telephonyManager.listen(this, 256);
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            this.listener.onMobileStateChanged(this.subId, this.telephonyManager.getDataNetworkType(), signalStrength.getLevel());
        }

        /* access modifiers changed from: private */
        public void unsubscribe() {
            this.telephonyManager.listen(this, 0);
        }
    }

    private boolean isNetworkAvailable(int i) {
        NetworkInfo networkInfo;
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (connectivityManager != null && (networkInfo = connectivityManager.getNetworkInfo(i)) != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            return true;
        }
        return false;
    }

    private void updateConnectivity() {
        boolean isNetworkAvailable = isNetworkAvailable(1);
        boolean isNetworkAvailable2 = isNetworkAvailable(9);
        boolean isNetworkAvailable3 = isNetworkAvailable(0);
        FileLogUtils.writeNetworkLog("updateConnectivity-->hasWiFi-" + isNetworkAvailable + ", hasEthernet-" + isNetworkAvailable2 + ", hasCellular-" + isNetworkAvailable3);
        notifyEthernet(isNetworkAvailable2);
        notifyWiFi(isNetworkAvailable);
        notifyMobile(isNetworkAvailable3);
    }

    private void notifyEthernet(boolean z) {
        this.mListener.onEthernetStateChanged(z);
    }

    private void notifyWiFi(boolean z) {
        boolean z2 = false;
        if (!(DBManager.getInstance().getIntOption("WIFI", 0) == 1)) {
            this.mListener.onWiFiStateChanged(0, -1);
            return;
        }
        WifiManager wifiManager = this.mWifiManager;
        if (!(wifiManager != null && wifiManager.isWifiEnabled())) {
            this.mListener.onWiFiStateChanged(1, -1);
            return;
        }
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (!(connectionInfo == null || connectionInfo.getNetworkId() == -1 || connectionInfo.getBSSID() == null)) {
            z2 = true;
        }
        if (z2) {
            String ssid = connectionInfo.getSSID();
            int calculateSignalLevel = WifiManager.calculateSignalLevel(connectionInfo.getRssi(), 4);
            Log.e(TAG, "getWifiInfo: ssid=" + ssid + ",signalLevel=" + calculateSignalLevel + ",speed=" + connectionInfo.getLinkSpeed() + ",units=" + "Mbps");
            this.mListener.onWiFiStateChanged(3, calculateSignalLevel);
            return;
        }
        this.mListener.onWiFiStateChanged(2, -1);
    }

    private void notifyMobile(boolean z) {
        boolean z2 = false;
        if (DBManager.getInstance().getIntOption(ZKDBConfig.MOBILE_DATA_FUN, 0) == 1 && z) {
            z2 = true;
        }
        this.mListener.onMobileAvailable(z2);
    }

    public void release() {
        unregister(this.mContext);
        PhoneStateTracker phoneStateTracker = this.mPhoneStateListener;
        if (phoneStateTracker != null) {
            phoneStateTracker.unsubscribe();
        }
    }
}
