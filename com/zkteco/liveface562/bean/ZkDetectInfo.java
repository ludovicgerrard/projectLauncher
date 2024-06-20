package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ZkDetectInfo implements Parcelable {
    public static final Parcelable.Creator<ZkDetectInfo> CREATOR = new Parcelable.Creator<ZkDetectInfo>() {
        public ZkDetectInfo createFromParcel(Parcel parcel) {
            return new ZkDetectInfo(parcel);
        }

        public ZkDetectInfo[] newArray(int i) {
            return new ZkDetectInfo[i];
        }
    };
    public Face[] face;
    public byte[] message;

    public int describeContents() {
        return 0;
    }

    public ZkDetectInfo(Face[] faceArr, byte[] bArr) {
        this.face = faceArr;
        this.message = bArr;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(this.face, i);
        parcel.writeByteArray(this.message);
    }

    protected ZkDetectInfo(Parcel parcel) {
        this.face = (Face[]) parcel.createTypedArray(Face.CREATOR);
        this.message = parcel.createByteArray();
    }
}
