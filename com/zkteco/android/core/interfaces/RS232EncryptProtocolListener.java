package com.zkteco.android.core.interfaces;

import android.os.Parcel;
import android.os.Parcelable;

public interface RS232EncryptProtocolListener {
    public static final int RSTYPE_CARD = 2;
    public static final int RSTYPE_FINGER = 1;
    public static final int SERIAL_PORT_1 = 1;
    public static final int SERIAL_PORT_2 = 2;
    public static final int SERIAL_PORT_3 = 3;

    void onReceiveData(RS232Data rS232Data);

    public static class RS232Data implements Parcelable {
        public static final Parcelable.Creator<RS232Data> CREATOR = new Parcelable.Creator<RS232Data>() {
            public RS232Data createFromParcel(Parcel parcel) {
                return new RS232Data(parcel);
            }

            public RS232Data[] newArray(int i) {
                return new RS232Data[i];
            }
        };
        private byte[] data;
        private int rsType;
        private int serialPortType;

        public int describeContents() {
            return 0;
        }

        public int getSerialPortType() {
            return this.serialPortType;
        }

        public void setSerialPortType(int i) {
            this.serialPortType = i;
        }

        public int getRsType() {
            return this.rsType;
        }

        public void setRsType(int i) {
            this.rsType = i;
        }

        public byte[] getData() {
            return this.data;
        }

        public void setData(byte[] bArr) {
            this.data = bArr;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.rsType);
            parcel.writeByteArray(this.data);
        }

        public RS232Data() {
        }

        protected RS232Data(Parcel parcel) {
            this.rsType = parcel.readInt();
            this.data = parcel.createByteArray();
        }
    }
}
