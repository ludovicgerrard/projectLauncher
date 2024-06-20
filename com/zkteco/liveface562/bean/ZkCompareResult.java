package com.zkteco.liveface562.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ZkCompareResult implements Parcelable {
    public static final Parcelable.Creator<ZkCompareResult> CREATOR = new Parcelable.Creator<ZkCompareResult>() {
        public ZkCompareResult createFromParcel(Parcel parcel) {
            return new ZkCompareResult(parcel);
        }

        public ZkCompareResult[] newArray(int i) {
            return new ZkCompareResult[i];
        }
    };
    public float compareThreshold;
    public float livenessScore1;
    public float livenessScore2;
    public float livenessThreshold;
    public int result;
    public float score;

    public int describeContents() {
        return 0;
    }

    public ZkCompareResult() {
    }

    protected ZkCompareResult(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.result = parcel.readInt();
        this.score = parcel.readFloat();
        this.compareThreshold = parcel.readFloat();
        this.livenessThreshold = parcel.readFloat();
        this.livenessScore1 = parcel.readFloat();
        this.livenessScore2 = parcel.readFloat();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeFloat(this.score);
        parcel.writeFloat(this.compareThreshold);
        parcel.writeFloat(this.livenessThreshold);
        parcel.writeFloat(this.livenessScore1);
        parcel.writeFloat(this.livenessScore2);
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int i) {
        this.result = i;
    }

    public float getScore() {
        return this.score;
    }

    public void setScore(float f) {
        this.score = f;
    }

    public float getCompareThreshold() {
        return this.compareThreshold;
    }

    public void setCompareThreshold(float f) {
        this.compareThreshold = f;
    }

    public float getLivenessThreshold() {
        return this.livenessThreshold;
    }

    public void setLivenessThreshold(float f) {
        this.livenessThreshold = f;
    }

    public float getLivenessScore1() {
        return this.livenessScore1;
    }

    public void setLivenessScore1(float f) {
        this.livenessScore1 = f;
    }

    public float getLivenessScore2() {
        return this.livenessScore2;
    }

    public void setLivenessScore2(float f) {
        this.livenessScore2 = f;
    }

    public String toString() {
        return "ZkCompareResult{result=" + this.result + ", score=" + this.score + ", compareThreshold=" + this.compareThreshold + ", livenessThreshold=" + this.livenessThreshold + ", livenessScore1=" + this.livenessScore1 + ", livenessScore2=" + this.livenessScore2 + '}';
    }
}
