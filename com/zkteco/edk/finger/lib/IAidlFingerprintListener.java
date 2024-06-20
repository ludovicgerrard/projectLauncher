package com.zkteco.edk.finger.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAidlFingerprintListener extends IInterface {

    public static class Default implements IAidlFingerprintListener {
        public IBinder asBinder() {
            return null;
        }

        public void onFingerprintSensorCaptureError(String str) throws RemoteException {
        }

        public void onFingerprintSensorCaptureSuccess(byte[] bArr, int i, int i2) throws RemoteException {
        }

        public void onFingerprintSensorExtractFail(int i) throws RemoteException {
        }

        public void onFingerprintSensorExtractSuccess(byte[] bArr) throws RemoteException {
        }

        public void onFingerprintStateCallback(int i) throws RemoteException {
        }
    }

    void onFingerprintSensorCaptureError(String str) throws RemoteException;

    void onFingerprintSensorCaptureSuccess(byte[] bArr, int i, int i2) throws RemoteException;

    void onFingerprintSensorExtractFail(int i) throws RemoteException;

    void onFingerprintSensorExtractSuccess(byte[] bArr) throws RemoteException;

    void onFingerprintStateCallback(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IAidlFingerprintListener {
        private static final String DESCRIPTOR = "com.zkteco.edk.finger.lib.IAidlFingerprintListener";
        static final int TRANSACTION_onFingerprintSensorCaptureError = 3;
        static final int TRANSACTION_onFingerprintSensorCaptureSuccess = 2;
        static final int TRANSACTION_onFingerprintSensorExtractFail = 5;
        static final int TRANSACTION_onFingerprintSensorExtractSuccess = 4;
        static final int TRANSACTION_onFingerprintStateCallback = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAidlFingerprintListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAidlFingerprintListener)) {
                return new Proxy(iBinder);
            }
            return (IAidlFingerprintListener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onFingerprintStateCallback(parcel.readInt());
                parcel2.writeNoException();
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                onFingerprintSensorCaptureSuccess(parcel.createByteArray(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                onFingerprintSensorCaptureError(parcel.readString());
                parcel2.writeNoException();
                return true;
            } else if (i == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                onFingerprintSensorExtractSuccess(parcel.createByteArray());
                parcel2.writeNoException();
                return true;
            } else if (i == 5) {
                parcel.enforceInterface(DESCRIPTOR);
                onFingerprintSensorExtractFail(parcel.readInt());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IAidlFingerprintListener {
            public static IAidlFingerprintListener sDefaultImpl;
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

            public void onFingerprintStateCallback(int i) throws RemoteException {
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
                    Stub.getDefaultImpl().onFingerprintStateCallback(i);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onFingerprintSensorCaptureSuccess(byte[] bArr, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(2, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onFingerprintSensorCaptureSuccess(bArr, i, i2);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onFingerprintSensorCaptureError(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (this.mRemote.transact(3, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onFingerprintSensorCaptureError(str);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onFingerprintSensorExtractSuccess(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(4, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onFingerprintSensorExtractSuccess(bArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onFingerprintSensorExtractFail(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onFingerprintSensorExtractFail(i);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAidlFingerprintListener iAidlFingerprintListener) {
            if (Proxy.sDefaultImpl != null || iAidlFingerprintListener == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAidlFingerprintListener;
            return true;
        }

        public static IAidlFingerprintListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
