package com.zkteco.edk.qrcode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IZkOnQrCodeScanListener extends IInterface {

    public static class Default implements IZkOnQrCodeScanListener {
        public IBinder asBinder() {
            return null;
        }

        public void onScanQrCode(String str, String str2) throws RemoteException {
        }
    }

    void onScanQrCode(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IZkOnQrCodeScanListener {
        private static final String DESCRIPTOR = "com.zkteco.edk.qrcode.IZkOnQrCodeScanListener";
        static final int TRANSACTION_onScanQrCode = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IZkOnQrCodeScanListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IZkOnQrCodeScanListener)) {
                return new Proxy(iBinder);
            }
            return (IZkOnQrCodeScanListener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onScanQrCode(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IZkOnQrCodeScanListener {
            public static IZkOnQrCodeScanListener sDefaultImpl;
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onScanQrCode(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onScanQrCode(str, str2);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IZkOnQrCodeScanListener iZkOnQrCodeScanListener) {
            if (Proxy.sDefaultImpl != null || iZkOnQrCodeScanListener == null) {
                return false;
            }
            Proxy.sDefaultImpl = iZkOnQrCodeScanListener;
            return true;
        }

        public static IZkOnQrCodeScanListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
