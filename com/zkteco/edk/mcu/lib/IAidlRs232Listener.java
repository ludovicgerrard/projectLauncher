package com.zkteco.edk.mcu.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAidlRs232Listener extends IInterface {

    public static class Default implements IAidlRs232Listener {
        public IBinder asBinder() {
            return null;
        }

        public void onRs232Detected(byte[] bArr) throws RemoteException {
        }
    }

    void onRs232Detected(byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IAidlRs232Listener {
        private static final String DESCRIPTOR = "com.zkteco.edk.mcu.lib.IAidlRs232Listener";
        static final int TRANSACTION_onRs232Detected = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAidlRs232Listener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAidlRs232Listener)) {
                return new Proxy(iBinder);
            }
            return (IAidlRs232Listener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onRs232Detected(parcel.createByteArray());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IAidlRs232Listener {
            public static IAidlRs232Listener sDefaultImpl;
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

            public void onRs232Detected(byte[] bArr) throws RemoteException {
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
                    Stub.getDefaultImpl().onRs232Detected(bArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAidlRs232Listener iAidlRs232Listener) {
            if (Proxy.sDefaultImpl != null || iAidlRs232Listener == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAidlRs232Listener;
            return true;
        }

        public static IAidlRs232Listener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
