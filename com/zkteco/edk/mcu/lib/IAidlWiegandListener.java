package com.zkteco.edk.mcu.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAidlWiegandListener extends IInterface {

    public static class Default implements IAidlWiegandListener {
        public IBinder asBinder() {
            return null;
        }

        public void onWiegandDetected(String str) throws RemoteException {
        }
    }

    void onWiegandDetected(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IAidlWiegandListener {
        private static final String DESCRIPTOR = "com.zkteco.edk.mcu.lib.IAidlWiegandListener";
        static final int TRANSACTION_onWiegandDetected = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAidlWiegandListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAidlWiegandListener)) {
                return new Proxy(iBinder);
            }
            return (IAidlWiegandListener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onWiegandDetected(parcel.readString());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IAidlWiegandListener {
            public static IAidlWiegandListener sDefaultImpl;
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

            public void onWiegandDetected(String str) throws RemoteException {
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
                    Stub.getDefaultImpl().onWiegandDetected(str);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAidlWiegandListener iAidlWiegandListener) {
            if (Proxy.sDefaultImpl != null || iAidlWiegandListener == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAidlWiegandListener;
            return true;
        }

        public static IAidlWiegandListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
