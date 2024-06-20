package com.zkteco.edk.card.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAidlDeviceStateListener extends IInterface {

    public static class Default implements IAidlDeviceStateListener {
        public IBinder asBinder() {
            return null;
        }

        public void onDeviceStateChange(int i, String str) throws RemoteException {
        }
    }

    void onDeviceStateChange(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IAidlDeviceStateListener {
        private static final String DESCRIPTOR = "com.zkteco.edk.card.lib.IAidlDeviceStateListener";
        static final int TRANSACTION_onDeviceStateChange = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAidlDeviceStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAidlDeviceStateListener)) {
                return new Proxy(iBinder);
            }
            return (IAidlDeviceStateListener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onDeviceStateChange(parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IAidlDeviceStateListener {
            public static IAidlDeviceStateListener sDefaultImpl;
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

            public void onDeviceStateChange(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onDeviceStateChange(i, str);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAidlDeviceStateListener iAidlDeviceStateListener) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iAidlDeviceStateListener == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = iAidlDeviceStateListener;
                return true;
            }
        }

        public static IAidlDeviceStateListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
