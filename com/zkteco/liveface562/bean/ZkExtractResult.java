package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ZkExtractResult implements Parcelable {
    public static final Parcelable.Creator<ZkExtractResult> CREATOR = new Parcelable.Creator<ZkExtractResult>() {
        public ZkExtractResult createFromParcel(Parcel parcel) {
            return new ZkExtractResult(parcel);
        }

        public ZkExtractResult[] newArray(int i) {
            return new ZkExtractResult[i];
        }
    };
    public static final int DETECT_NO_FACE = 1;
    public static final int QUALITY_BAD = 2;
    public static final int SUCCESS = 0;
    public Face face;
    public byte[] feature;
    public int result;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeByteArray(this.feature);
        parcel.writeParcelable(this.face, i);
    }

    public ZkExtractResult() {
    }

    protected ZkExtractResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.feature = parcel.createByteArray();
        this.face = (Face) parcel.readParcelable(Face.class.getClassLoader());
    }
}
