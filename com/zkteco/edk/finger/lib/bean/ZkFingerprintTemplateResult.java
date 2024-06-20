package com.zkteco.edk.finger.lib.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class ZkFingerprintTemplateResult implements Parcelable, Serializable {
    public static final Parcelable.Creator<ZkFingerprintTemplateResult> CREATOR = new Parcelable.Creator<ZkFingerprintTemplateResult>() {
        public ZkFingerprintTemplateResult createFromParcel(Parcel parcel) {
            return new ZkFingerprintTemplateResult(parcel);
        }

        public ZkFingerprintTemplateResult[] newArray(int i) {
            return new ZkFingerprintTemplateResult[i];
        }
    };
    private String pin;
    private byte[] templateData;

    public int describeContents() {
        return 0;
    }

    public ZkFingerprintTemplateResult() {
    }

    protected ZkFingerprintTemplateResult(Parcel parcel) {
        this.pin = parcel.readString();
        this.templateData = parcel.createByteArray();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pin);
        parcel.writeByteArray(this.templateData);
    }

    public byte[] getTemplateData() {
        return this.templateData;
    }

    public void setTemplateData(byte[] bArr) {
        this.templateData = bArr;
    }

    public String getPin() {
        return this.pin;
    }

    public void setPin(String str) {
        this.pin = str;
    }
}
