package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LivenessResult implements Parcelable {
    public static final Parcelable.Creator<LivenessResult> CREATOR = new Parcelable.Creator<LivenessResult>() {
        public LivenessResult createFromParcel(Parcel parcel) {
            return new LivenessResult(parcel);
        }

        public LivenessResult[] newArray(int i) {
            return new LivenessResult[i];
        }
    };
    public int livenessResultType;
    public float livenessScore;
    public long trackId;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.trackId);
        parcel.writeFloat(this.livenessScore);
        parcel.writeInt(this.livenessResultType);
    }

    public LivenessResult() {
    }

    protected LivenessResult(Parcel parcel) {
        this.trackId = parcel.readLong();
        this.livenessScore = parcel.readFloat();
        this.livenessResultType = parcel.readInt();
    }
}
