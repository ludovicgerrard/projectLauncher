package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class FacePose implements Parcelable {
    public static final Parcelable.Creator<FacePose> CREATOR = new Parcelable.Creator<FacePose>() {
        public FacePose createFromParcel(Parcel parcel) {
            return new FacePose(parcel);
        }

        public FacePose[] newArray(int i) {
            return new FacePose[i];
        }
    };
    public float pitch;
    public float roll;
    public float yaw;

    public int describeContents() {
        return 0;
    }

    public FacePose(float f, float f2, float f3) {
        this.roll = f;
        this.pitch = f2;
        this.yaw = f3;
    }

    public String toString() {
        return "ZkFacePose{roll=" + this.roll + ", pitch=" + this.pitch + ", yaw=" + this.yaw + '}';
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.roll);
        parcel.writeFloat(this.pitch);
        parcel.writeFloat(this.yaw);
    }

    protected FacePose(Parcel parcel) {
        this.roll = parcel.readFloat();
        this.pitch = parcel.readFloat();
        this.yaw = parcel.readFloat();
    }
}
