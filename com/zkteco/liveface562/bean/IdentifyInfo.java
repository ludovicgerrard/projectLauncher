package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class IdentifyInfo implements Parcelable {
    public static final Parcelable.Creator<IdentifyInfo> CREATOR = new Parcelable.Creator<IdentifyInfo>() {
        public IdentifyInfo createFromParcel(Parcel parcel) {
            return new IdentifyInfo(parcel);
        }

        public IdentifyInfo[] newArray(int i) {
            return new IdentifyInfo[i];
        }
    };
    public int beardType;
    public int glasses;
    public int hairType;
    public int hatType;
    public float identifyScore;
    public float livenessScore;
    public String pin;
    public int respirator;
    public int skinColor;
    public long trackId;

    public int describeContents() {
        return 0;
    }

    public IdentifyInfo() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.trackId);
        parcel.writeInt(this.respirator);
        parcel.writeInt(this.hairType);
        parcel.writeInt(this.beardType);
        parcel.writeInt(this.hatType);
        parcel.writeInt(this.glasses);
        parcel.writeInt(this.skinColor);
        parcel.writeFloat(this.identifyScore);
        parcel.writeFloat(this.livenessScore);
        parcel.writeString(this.pin);
    }

    protected IdentifyInfo(Parcel parcel) {
        this.trackId = parcel.readLong();
        this.respirator = parcel.readInt();
        this.hairType = parcel.readInt();
        this.beardType = parcel.readInt();
        this.hatType = parcel.readInt();
        this.glasses = parcel.readInt();
        this.skinColor = parcel.readInt();
        this.identifyScore = parcel.readFloat();
        this.livenessScore = parcel.readFloat();
        this.pin = parcel.readString();
    }

    public String toString() {
        return "IdentifyInfo{trackId=" + this.trackId + ", respirator=" + this.respirator + ", hairType=" + this.hairType + ", beardType=" + this.beardType + ", hatType=" + this.hatType + ", glasses=" + this.glasses + ", skinColor=" + this.skinColor + ", identifyScore=" + this.identifyScore + ", livenessScore=" + this.livenessScore + ", pin='" + this.pin + '\'' + '}';
    }
}
