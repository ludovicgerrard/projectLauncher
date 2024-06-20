package com.zkteco.edk.qrcode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.zkteco.edk.qrcode.IZkOnQrCodeScanListener;
import com.zkteco.edk.qrcode.IZkQrCodeStateListener;
import java.util.ArrayList;
import java.util.List;

public interface IZkQrCodeScannerInterface extends IInterface {

    public static class Default implements IZkQrCodeScannerInterface {
        public IBinder asBinder() {
            return null;
        }

        public int openDevice() throws RemoteException {
            return 0;
        }

        public int queryOnlineDevice(List<String> list) throws RemoteException {
            return 0;
        }

        public int registerQrCodeScanListener(IZkOnQrCodeScanListener iZkOnQrCodeScanListener) throws RemoteException {
            return 0;
        }

        public int registerQrCodeStateListener(IZkQrCodeStateListener iZkQrCodeStateListener) throws RemoteException {
            return 0;
        }

        public int unRegisterQrCodeScanListener(IZkOnQrCodeScanListener iZkOnQrCodeScanListener) throws RemoteException {
            return 0;
        }

        public int unRegisterQrCodeStateListener(IZkQrCodeStateListener iZkQrCodeStateListener) throws RemoteException {
            return 0;
        }
    }

    int openDevice() throws RemoteException;

    int queryOnlineDevice(List<String> list) throws RemoteException;

    int registerQrCodeScanListener(IZkOnQrCodeScanListener iZkOnQrCodeScanListener) throws RemoteException;

    int registerQrCodeStateListener(IZkQrCodeStateListener iZkQrCodeStateListener) throws RemoteException;

    int unRegisterQrCodeScanListener(IZkOnQrCodeScanListener iZkOnQrCodeScanListener) throws RemoteException;

    int unRegisterQrCodeStateListener(IZkQrCodeStateListener iZkQrCodeStateListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IZkQrCodeScannerInterface {
        private static final String DESCRIPTOR = "com.zkteco.edk.qrcode.IZkQrCodeScannerInterface";
        static final int TRANSACTION_openDevice = 6;
        static final int TRANSACTION_queryOnlineDevice = 5;
        static final int TRANSACTION_registerQrCodeScanListener = 1;
        static final int TRANSACTION_registerQrCodeStateListener = 3;
        static final int TRANSACTION_unRegisterQrCodeScanListener = 2;
        static final int TRANSACTION_unRegisterQrCodeStateListener = 4;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IZkQrCodeScannerInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IZkQrCodeScannerInterface)) {
                return new Proxy(iBinder);
            }
            return (IZkQrCodeScannerInterface) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        int registerQrCodeScanListener = registerQrCodeScanListener(IZkOnQrCodeScanListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(registerQrCodeScanListener);
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        int unRegisterQrCodeScanListener = unRegisterQrCodeScanListener(IZkOnQrCodeScanListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(unRegisterQrCodeScanListener);
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        int registerQrCodeStateListener = registerQrCodeStateListener(IZkQrCodeStateListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(registerQrCodeStateListener);
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        int unRegisterQrCodeStateListener = unRegisterQrCodeStateListener(IZkQrCodeStateListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(unRegisterQrCodeStateListener);
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                        int queryOnlineDevice = queryOnlineDevice(createStringArrayList);
                        parcel2.writeNoException();
                        parcel2.writeInt(queryOnlineDevice);
                        parcel2.writeStringList(createStringArrayList);
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        int openDevice = openDevice();
                        parcel2.writeNoException();
                        parcel2.writeInt(openDevice);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IZkQrCodeScannerInterface {
            public static IZkQrCodeScannerInterface sDefaultImpl;
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

            public int registerQrCodeScanListener(IZkOnQrCodeScanListener iZkOnQrCodeScanListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iZkOnQrCodeScanListener != null ? iZkOnQrCodeScanListener.asBinder() : null);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerQrCodeScanListener(iZkOnQrCodeScanListener);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int unRegisterQrCodeScanListener(IZkOnQrCodeScanListener iZkOnQrCodeScanListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iZkOnQrCodeScanListener != null ? iZkOnQrCodeScanListener.asBinder() : null);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unRegisterQrCodeScanListener(iZkOnQrCodeScanListener);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int registerQrCodeStateListener(IZkQrCodeStateListener iZkQrCodeStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iZkQrCodeStateListener != null ? iZkQrCodeStateListener.asBinder() : null);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerQrCodeStateListener(iZkQrCodeStateListener);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int unRegisterQrCodeStateListener(IZkQrCodeStateListener iZkQrCodeStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iZkQrCodeStateListener != null ? iZkQrCodeStateListener.asBinder() : null);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unRegisterQrCodeStateListener(iZkQrCodeStateListener);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int queryOnlineDevice(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryOnlineDevice(list);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readStringList(list);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int openDevice() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openDevice();
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IZkQrCodeScannerInterface iZkQrCodeScannerInterface) {
            if (Proxy.sDefaultImpl != null || iZkQrCodeScannerInterface == null) {
                return false;
            }
            Proxy.sDefaultImpl = iZkQrCodeScannerInterface;
            return true;
        }

        public static IZkQrCodeScannerInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
