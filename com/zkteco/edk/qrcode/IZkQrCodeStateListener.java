package com.zkteco.edk.qrcode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IZkQrCodeStateListener extends IInterface {

    public static class Default implements IZkQrCodeStateListener {
        public IBinder asBinder() {
            return null;
        }

        public void onQrCodeStateListener(String str, int i) throws RemoteException {
        }
    }

    void onQrCodeStateListener(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IZkQrCodeStateListener {
        private static final String DESCRIPTOR = "com.zkteco.edk.qrcode.IZkQrCodeStateListener";
        static final int TRANSACTION_onQrCodeStateListener = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IZkQrCodeStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IZkQrCodeStateListener)) {
                return new Proxy(iBinder);
            }
            return (IZkQrCodeStateListener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onQrCodeStateListener(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IZkQrCodeStateListener {
            public static IZkQrCodeStateListener sDefaultImpl;
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

            public void onQrCodeStateListener(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onQrCodeStateListener(str, i);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IZkQrCodeStateListener iZkQrCodeStateListener) {
            if (Proxy.sDefaultImpl != null || iZkQrCodeStateListener == null) {
                return false;
            }
            Proxy.sDefaultImpl = iZkQrCodeStateListener;
            return true;
        }

        public static IZkQrCodeStateListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
