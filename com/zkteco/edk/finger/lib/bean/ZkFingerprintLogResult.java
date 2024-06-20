package com.zkteco.edk.finger.lib.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ZkFingerprintLogResult implements Parcelable {
    public static final Parcelable.Creator<ZkFingerprintLogResult> CREATOR = new Parcelable.Creator<ZkFingerprintLogResult>() {
        public ZkFingerprintLogResult createFromParcel(Parcel parcel) {
            return new ZkFingerprintLogResult(parcel);
        }

        public ZkFingerprintLogResult[] newArray(int i) {
            return new ZkFingerprintLogResult[i];
        }
    };
    private String pin;
    private String time;

    public int describeContents() {
        return 0;
    }

    public ZkFingerprintLogResult() {
    }

    protected ZkFingerprintLogResult(Parcel parcel) {
        this.pin = parcel.readString();
        this.time = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pin);
        parcel.writeString(this.time);
    }

    public void readFromParcel(Parcel parcel) {
        this.pin = parcel.readString();
        this.time = parcel.readString();
    }

    public String getPin() {
        return this.pin;
    }

    public void setPin(String str) {
        this.pin = str;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String str) {
        this.time = str;
    }
}
