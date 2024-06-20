package com.zkteco.edk.system.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IZkAidlSystemListener extends IInterface {

    public static class Default implements IZkAidlSystemListener {
        public IBinder asBinder() {
            return null;
        }

        public void onNetworkConnectivityChange(boolean z, int i) throws RemoteException {
        }
    }

    void onNetworkConnectivityChange(boolean z, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IZkAidlSystemListener {
        private static final String DESCRIPTOR = "com.zkteco.edk.system.lib.IZkAidlSystemListener";
        static final int TRANSACTION_onNetworkConnectivityChange = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IZkAidlSystemListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IZkAidlSystemListener)) {
                return new Proxy(iBinder);
            }
            return (IZkAidlSystemListener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onNetworkConnectivityChange(parcel.readInt() != 0, parcel.readInt());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IZkAidlSystemListener {
            public static IZkAidlSystemListener sDefaultImpl;
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

            public void onNetworkConnectivityChange(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onNetworkConnectivityChange(z, i);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IZkAidlSystemListener iZkAidlSystemListener) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iZkAidlSystemListener == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = iZkAidlSystemListener;
                return true;
            }
        }

        public static IZkAidlSystemListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
