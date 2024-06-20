package com.zkteco.android.videointercom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVideoP2PEventListener extends IInterface {

    public static class Default implements IVideoP2PEventListener {
        public void P2PEvent(String str) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    void P2PEvent(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IVideoP2PEventListener {
        private static final String DESCRIPTOR = "com.zkteco.android.videointercom.IVideoP2PEventListener";
        static final int TRANSACTION_P2PEvent = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVideoP2PEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IVideoP2PEventListener)) {
                return new Proxy(iBinder);
            }
            return (IVideoP2PEventListener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                P2PEvent(parcel.readString());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IVideoP2PEventListener {
            public static IVideoP2PEventListener sDefaultImpl;
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

            public void P2PEvent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().P2PEvent(str);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVideoP2PEventListener iVideoP2PEventListener) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iVideoP2PEventListener == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = iVideoP2PEventListener;
                return true;
            }
        }

        public static IVideoP2PEventListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
