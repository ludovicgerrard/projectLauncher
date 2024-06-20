package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AgeAndGender implements Parcelable {
    public static final Parcelable.Creator<AgeAndGender> CREATOR = new Parcelable.Creator<AgeAndGender>() {
        public AgeAndGender createFromParcel(Parcel parcel) {
            return new AgeAndGender(parcel);
        }

        public AgeAndGender[] newArray(int i) {
            return new AgeAndGender[i];
        }
    };
    public float age;
    public int gender;
    public long trackId;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.trackId);
        parcel.writeFloat(this.age);
        parcel.writeInt(this.gender);
    }

    public AgeAndGender() {
    }

    protected AgeAndGender(Parcel parcel) {
        this.trackId = parcel.readLong();
        this.age = parcel.readFloat();
        this.gender = parcel.readInt();
    }
}
