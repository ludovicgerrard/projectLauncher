package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class FaceOccStatus implements Parcelable {
    public static final Parcelable.Creator<FaceOccStatus> CREATOR = new Parcelable.Creator<FaceOccStatus>() {
        public FaceOccStatus createFromParcel(Parcel parcel) {
            return new FaceOccStatus(parcel);
        }

        public FaceOccStatus[] newArray(int i) {
            return new FaceOccStatus[i];
        }
    };
    public boolean eyeOcc;
    public boolean mouthOcc;
    public boolean noseOcc;
    public boolean valid;

    public int describeContents() {
        return 0;
    }

    public FaceOccStatus(boolean z, boolean z2, boolean z3, boolean z4) {
        this.valid = z;
        this.eyeOcc = z2;
        this.noseOcc = z3;
        this.mouthOcc = z4;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.valid ? (byte) 1 : 0);
        parcel.writeByte(this.eyeOcc ? (byte) 1 : 0);
        parcel.writeByte(this.noseOcc ? (byte) 1 : 0);
        parcel.writeByte(this.mouthOcc ? (byte) 1 : 0);
    }

    public void readFromParcel(Parcel parcel) {
        boolean z = true;
        this.valid = parcel.readByte() != 0;
        this.eyeOcc = parcel.readByte() != 0;
        this.noseOcc = parcel.readByte() != 0;
        if (parcel.readByte() == 0) {
            z = false;
        }
        this.mouthOcc = z;
    }

    protected FaceOccStatus(Parcel parcel) {
        boolean z = true;
        this.valid = parcel.readByte() != 0;
        this.eyeOcc = parcel.readByte() != 0;
        this.noseOcc = parcel.readByte() != 0;
        this.mouthOcc = parcel.readByte() == 0 ? false : z;
    }
}
