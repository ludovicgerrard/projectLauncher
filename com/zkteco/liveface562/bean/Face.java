package com.zkteco.liveface562.bean;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

public class Face implements Parcelable {
    public static final Parcelable.Creator<Face> CREATOR = new Parcelable.Creator<Face>() {
        public Face createFromParcel(Parcel parcel) {
            return new Face(parcel);
        }

        public Face[] newArray(int i) {
            return new Face[i];
        }
    };
    public int beard;
    public float blur;
    public float brightness;
    public float deviation;
    public EyeState eyeState;
    public FaceOccStatus faceOccStatus;
    public int glasses;
    public int hair;
    public int hat;
    public FacePose pose;
    public Rect rect;
    public int respirator;
    public int skinColor;
    public float smile;
    public long trackId;

    public int describeContents() {
        return 0;
    }

    public Face() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.trackId);
        parcel.writeParcelable(this.rect, i);
        parcel.writeParcelable(this.pose, i);
        parcel.writeFloat(this.blur);
        parcel.writeFloat(this.smile);
        parcel.writeInt(this.respirator);
        parcel.writeInt(this.hair);
        parcel.writeInt(this.beard);
        parcel.writeInt(this.hat);
        parcel.writeInt(this.glasses);
        parcel.writeInt(this.skinColor);
        parcel.writeParcelable(this.faceOccStatus, i);
        parcel.writeParcelable(this.eyeState, i);
        parcel.writeFloat(this.brightness);
        parcel.writeFloat(this.deviation);
    }

    public void readFromParcel(Parcel parcel) {
        this.trackId = parcel.readLong();
        this.rect = (Rect) parcel.readParcelable(Rect.class.getClassLoader());
        this.pose = (FacePose) parcel.readParcelable(FacePose.class.getClassLoader());
        this.blur = parcel.readFloat();
        this.smile = parcel.readFloat();
        this.respirator = parcel.readInt();
        this.hair = parcel.readInt();
        this.beard = parcel.readInt();
        this.hat = parcel.readInt();
        this.glasses = parcel.readInt();
        this.skinColor = parcel.readInt();
        this.faceOccStatus = (FaceOccStatus) parcel.readParcelable(FaceOccStatus.class.getClassLoader());
        this.eyeState = (EyeState) parcel.readParcelable(EyeState.class.getClassLoader());
        this.brightness = parcel.readFloat();
        this.deviation = parcel.readFloat();
    }

    protected Face(Parcel parcel) {
        this.trackId = parcel.readLong();
        this.rect = (Rect) parcel.readParcelable(Rect.class.getClassLoader());
        this.pose = (FacePose) parcel.readParcelable(FacePose.class.getClassLoader());
        this.blur = parcel.readFloat();
        this.smile = parcel.readFloat();
        this.respirator = parcel.readInt();
        this.hair = parcel.readInt();
        this.beard = parcel.readInt();
        this.hat = parcel.readInt();
        this.glasses = parcel.readInt();
        this.skinColor = parcel.readInt();
        this.faceOccStatus = (FaceOccStatus) parcel.readParcelable(FaceOccStatus.class.getClassLoader());
        this.eyeState = (EyeState) parcel.readParcelable(EyeState.class.getClassLoader());
        this.brightness = parcel.readFloat();
        this.deviation = parcel.readFloat();
    }
}
