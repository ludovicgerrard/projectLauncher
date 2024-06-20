package com.zkteco.android.videointercom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVideoBellCallStatusListener extends IInterface {

    public static class Default implements IVideoBellCallStatusListener {
        public IBinder asBinder() {
            return null;
        }

        public void bellCallStatus(int i) throws RemoteException {
        }
    }

    void bellCallStatus(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IVideoBellCallStatusListener {
        private static final String DESCRIPTOR = "com.zkteco.android.videointercom.IVideoBellCallStatusListener";
        static final int TRANSACTION_bellCallStatus = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVideoBellCallStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IVideoBellCallStatusListener)) {
                return new Proxy(iBinder);
            }
            return (IVideoBellCallStatusListener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                bellCallStatus(parcel.readInt());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IVideoBellCallStatusListener {
            public static IVideoBellCallStatusListener sDefaultImpl;
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

            public void bellCallStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().bellCallStatus(i);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVideoBellCallStatusListener iVideoBellCallStatusListener) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iVideoBellCallStatusListener == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = iVideoBellCallStatusListener;
                return true;
            }
        }

        public static IVideoBellCallStatusListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
