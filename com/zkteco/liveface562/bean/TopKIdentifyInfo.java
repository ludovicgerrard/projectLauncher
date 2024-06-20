package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TopKIdentifyInfo implements Parcelable {
    public static final Parcelable.Creator<TopKIdentifyInfo> CREATOR = new Parcelable.Creator<TopKIdentifyInfo>() {
        public TopKIdentifyInfo createFromParcel(Parcel parcel) {
            return new TopKIdentifyInfo(parcel);
        }

        public TopKIdentifyInfo[] newArray(int i) {
            return new TopKIdentifyInfo[i];
        }
    };
    private IdentifyInfo[] identifyInfos;

    public int describeContents() {
        return 0;
    }

    public IdentifyInfo[] getIdentifyInfos() {
        return this.identifyInfos;
    }

    public void setIdentifyInfos(IdentifyInfo[] identifyInfoArr) {
        this.identifyInfos = identifyInfoArr;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(this.identifyInfos, i);
    }

    public TopKIdentifyInfo() {
    }

    protected TopKIdentifyInfo(Parcel parcel) {
        this.identifyInfos = (IdentifyInfo[]) parcel.createTypedArray(IdentifyInfo.CREATOR);
    }
}
