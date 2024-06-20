package com.zkteco.edk.mcu.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAidlRs485Listener extends IInterface {

    public static class Default implements IAidlRs485Listener {
        public IBinder asBinder() {
            return null;
        }

        public void onRs485Detected(byte[] bArr) throws RemoteException {
        }
    }

    void onRs485Detected(byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IAidlRs485Listener {
        private static final String DESCRIPTOR = "com.zkteco.edk.mcu.lib.IAidlRs485Listener";
        static final int TRANSACTION_onRs485Detected = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAidlRs485Listener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAidlRs485Listener)) {
                return new Proxy(iBinder);
            }
            return (IAidlRs485Listener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onRs485Detected(parcel.createByteArray());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IAidlRs485Listener {
            public static IAidlRs485Listener sDefaultImpl;
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

            public void onRs485Detected(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onRs485Detected(bArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAidlRs485Listener iAidlRs485Listener) {
            if (Proxy.sDefaultImpl != null || iAidlRs485Listener == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAidlRs485Listener;
            return true;
        }

        public static IAidlRs485Listener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
