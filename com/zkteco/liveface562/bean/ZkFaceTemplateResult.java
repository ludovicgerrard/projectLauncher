package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ZkFaceTemplateResult implements Parcelable {
    public static final Parcelable.Creator<ZkFaceTemplateResult> CREATOR = new Parcelable.Creator<ZkFaceTemplateResult>() {
        public ZkFaceTemplateResult createFromParcel(Parcel parcel) {
            return new ZkFaceTemplateResult(parcel);
        }

        public ZkFaceTemplateResult[] newArray(int i) {
            return new ZkFaceTemplateResult[i];
        }
    };
    private String pin;
    private byte[] template;

    public int describeContents() {
        return 0;
    }

    public ZkFaceTemplateResult() {
    }

    protected ZkFaceTemplateResult(Parcel parcel) {
        this.pin = parcel.readString();
        this.template = parcel.createByteArray();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pin);
        parcel.writeByteArray(this.template);
    }

    public void readFromParcel(Parcel parcel) {
        this.pin = parcel.readString();
        this.template = parcel.createByteArray();
    }

    public String getPin() {
        return this.pin;
    }

    public void setPin(String str) {
        this.pin = str;
    }

    public byte[] getTemplate() {
        return this.template;
    }

    public void setTemplate(byte[] bArr) {
        this.template = bArr;
    }
}
