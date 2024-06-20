package com.zkteco.android.core.interfaces;

import java.io.Serializable;
import java.util.List;

public interface WifiInterface {
    public static final String CONNECT = "wifi-connect";
    public static final String CONNECTED_WIFI_NAME = "wifi-connect-name";
    public static final String CONNECT_STATUS = "wifi-connect-status";
    public static final String DISABLE = "wifi-disable";
    public static final String DISCONNECT = "wifi-disconnect";
    public static final String ENABLE = "wifi-enable";
    public static final String FORGET = "wifi-forget";
    public static final String GET_CONFIGURATION = "wifi-get-configuration";
    public static final String NETWORKS = "wifi-networks";
    public static final String SET_CONFIGURATION = "wifi-set-configuration";
    public static final String WIFI_STATE = "wifi-state";

    List<WifiScanResult> availableNetworks();

    void connect(String str);

    void disable();

    void disconnect();

    void enable();

    void forget(String str);

    WifiConfig getConfiguration(String str);

    String getConnectStatus();

    String getConnectedWifiName();

    boolean getWifiState();

    void setConfiguration(String str, WifiConfig wifiConfig);

    public static class WifiScanResult implements Serializable {
        public String SSID;
        public String capabilities;
        public int connectState = 0;
        public int level;

        public WifiScanResult(String str, String str2, int i, int i2) {
            this.SSID = str;
            this.capabilities = str2;
            this.level = i;
            this.connectState = i2;
        }

        public int getConnectState() {
            return this.connectState;
        }

        public void setConnectState(int i) {
            this.connectState = i;
        }

        public WifiScanResult() {
        }

        public String getSSID() {
            return this.SSID;
        }

        public void setSSID(String str) {
            this.SSID = str;
        }

        public String getCapabilities() {
            return this.capabilities;
        }

        public void setCapabilities(String str) {
            this.capabilities = str;
        }

        public int getLevel() {
            return this.level;
        }

        public void setLevel(int i) {
            this.level = i;
        }
    }

    public static class WifiConfig implements Serializable {
        private String password;
        private PasswordType passwordType;

        public enum PasswordType {
            NONE,
            WEP,
            WPA
        }

        public WifiConfig() {
        }

        public WifiConfig(PasswordType passwordType2) {
            this.passwordType = passwordType2;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String str) {
            this.password = str;
        }

        public PasswordType getPasswordType() {
            return this.passwordType;
        }

        public void setPasswordType(PasswordType passwordType2) {
            this.passwordType = passwordType2;
        }
    }
}
