package com.zkteco.edk.system.lib.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class ZkShellResult implements Parcelable, Serializable {
    public static final Parcelable.Creator<ZkShellResult> CREATOR = new Parcelable.Creator<ZkShellResult>() {
        public ZkShellResult createFromParcel(Parcel parcel) {
            return new ZkShellResult(parcel);
        }

        public ZkShellResult[] newArray(int i) {
            return new ZkShellResult[i];
        }
    };
    private String errorMsg;
    private int result;
    private String successMsg;

    public int describeContents() {
        return 0;
    }

    public ZkShellResult() {
    }

    protected ZkShellResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.successMsg = parcel.readString();
        this.errorMsg = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeString(this.successMsg);
        parcel.writeString(this.errorMsg);
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int i) {
        this.result = i;
    }

    public String getSuccessMsg() {
        return this.successMsg;
    }

    public void setSuccessMsg(String str) {
        this.successMsg = str;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String str) {
        this.errorMsg = str;
    }
}
