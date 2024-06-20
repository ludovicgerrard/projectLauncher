package com.zkteco.edk.system.lib.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class ZkWifiConfig implements Parcelable, Serializable {
    public static final Parcelable.Creator<ZkWifiConfig> CREATOR = new Parcelable.Creator<ZkWifiConfig>() {
        public ZkWifiConfig createFromParcel(Parcel parcel) {
            return new ZkWifiConfig(parcel);
        }

        public ZkWifiConfig[] newArray(int i) {
            return new ZkWifiConfig[i];
        }
    };
    private String SSID;
    private int cipherType;
    private int connectState;
    private int level;

    public int describeContents() {
        return 0;
    }

    public ZkWifiConfig() {
    }

    protected ZkWifiConfig(Parcel parcel) {
        this.SSID = parcel.readString();
        this.cipherType = parcel.readInt();
        this.connectState = parcel.readInt();
        this.level = parcel.readInt();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.SSID);
        parcel.writeInt(this.cipherType);
        parcel.writeInt(this.connectState);
        parcel.writeInt(this.level);
    }

    public String getSSID() {
        return this.SSID;
    }

    public void setSSID(String str) {
        this.SSID = str;
    }

    public int getCipherType() {
        return this.cipherType;
    }

    public void setCipherType(int i) {
        this.cipherType = i;
    }

    public int getConnectState() {
        return this.connectState;
    }

    public void setConnectState(int i) {
        this.connectState = i;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int i) {
        this.level = i;
    }
}
