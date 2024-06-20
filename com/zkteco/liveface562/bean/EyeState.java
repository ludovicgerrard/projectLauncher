package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class EyeState implements Parcelable {
    public static final Parcelable.Creator<EyeState> CREATOR = new Parcelable.Creator<EyeState>() {
        public EyeState createFromParcel(Parcel parcel) {
            return new EyeState(parcel);
        }

        public EyeState[] newArray(int i) {
            return new EyeState[i];
        }
    };
    public boolean left_eye;
    public boolean right_eye;
    public boolean valid;

    public int describeContents() {
        return 0;
    }

    public EyeState(boolean z, boolean z2, boolean z3) {
        this.valid = z;
        this.left_eye = z2;
        this.right_eye = z3;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.valid ? (byte) 1 : 0);
        parcel.writeByte(this.left_eye ? (byte) 1 : 0);
        parcel.writeByte(this.right_eye ? (byte) 1 : 0);
    }

    public void readFromParcel(Parcel parcel) {
        boolean z = true;
        this.valid = parcel.readByte() != 0;
        this.left_eye = parcel.readByte() != 0;
        if (parcel.readByte() == 0) {
            z = false;
        }
        this.right_eye = z;
    }

    protected EyeState(Parcel parcel) {
        boolean z = true;
        this.valid = parcel.readByte() != 0;
        this.left_eye = parcel.readByte() != 0;
        this.right_eye = parcel.readByte() == 0 ? false : z;
    }
}
