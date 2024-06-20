package com.zkteco.edk.system.lib.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ZkEthernetConfig implements Parcelable {
    public static final Parcelable.Creator<ZkEthernetConfig> CREATOR = new Parcelable.Creator<ZkEthernetConfig>() {
        public ZkEthernetConfig createFromParcel(Parcel parcel) {
            return new ZkEthernetConfig(parcel);
        }

        public ZkEthernetConfig[] newArray(int i) {
            return new ZkEthernetConfig[i];
        }
    };
    private boolean available;
    private boolean connect;
    private boolean dhcp;
    private String dns1;
    private String dns2;
    private String gateway;
    private String ipAddress;
    private String subnetMask;

    public int describeContents() {
        return 0;
    }

    public ZkEthernetConfig() {
    }

    protected ZkEthernetConfig(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.ipAddress = parcel.readString();
        this.dns1 = parcel.readString();
        this.dns2 = parcel.readString();
        this.subnetMask = parcel.readString();
        this.gateway = parcel.readString();
        boolean z = true;
        this.dhcp = parcel.readByte() != 0;
        this.connect = parcel.readByte() != 0;
        if (parcel.readByte() == 0) {
            z = false;
        }
        this.available = z;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.ipAddress);
        parcel.writeString(this.dns1);
        parcel.writeString(this.dns2);
        parcel.writeString(this.subnetMask);
        parcel.writeString(this.gateway);
        parcel.writeByte(this.dhcp ? (byte) 1 : 0);
        parcel.writeByte(this.connect ? (byte) 1 : 0);
        parcel.writeByte(this.available ? (byte) 1 : 0);
    }

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

    public String getDns2() {
        return this.dns2;
    }

    public void setDns2(String str) {
        this.dns2 = str;
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

    public boolean isDhcp() {
        return this.dhcp;
    }

    public void setDhcp(boolean z) {
        this.dhcp = z;
    }

    public boolean isConnect() {
        return this.connect;
    }

    public void setConnect(boolean z) {
        this.connect = z;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean z) {
        this.available = z;
    }

    public String toString() {
        return "ZkEthernetConfig{ipAddress='" + this.ipAddress + '\'' + ", dns1='" + this.dns1 + '\'' + ", dns2='" + this.dns2 + '\'' + ", subnetMask='" + this.subnetMask + '\'' + ", gateway='" + this.gateway + '\'' + ", dhcp=" + this.dhcp + ", connect=" + this.connect + ", available=" + this.available + '}';
    }
}
