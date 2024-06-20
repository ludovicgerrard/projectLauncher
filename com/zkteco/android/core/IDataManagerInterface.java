package com.zkteco.android.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDataManagerInterface extends IInterface {

    public static class Default implements IDataManagerInterface {
        public IBinder asBinder() {
            return null;
        }

        public int getIntOption(String str, int i) throws RemoteException {
            return 0;
        }

        public String getStrOption(String str, String str2) throws RemoteException {
            return null;
        }

        public int setIntOption(String str, int i) throws RemoteException {
            return 0;
        }

        public int setStrOption(String str, String str2) throws RemoteException {
            return 0;
        }
    }

    int getIntOption(String str, int i) throws RemoteException;

    String getStrOption(String str, String str2) throws RemoteException;

    int setIntOption(String str, int i) throws RemoteException;

    int setStrOption(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IDataManagerInterface {
        private static final String DESCRIPTOR = "com.zkteco.android.core.IDataManagerInterface";
        static final int TRANSACTION_getIntOption = 3;
        static final int TRANSACTION_getStrOption = 4;
        static final int TRANSACTION_setIntOption = 1;
        static final int TRANSACTION_setStrOption = 2;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDataManagerInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDataManagerInterface)) {
                return new Proxy(iBinder);
            }
            return (IDataManagerInterface) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                int intOption = setIntOption(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(intOption);
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                int strOption = setStrOption(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(strOption);
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                int intOption2 = getIntOption(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(intOption2);
                return true;
            } else if (i == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                String strOption2 = getStrOption(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(strOption2);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IDataManagerInterface {
            public static IDataManagerInterface sDefaultImpl;
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

            public int setIntOption(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setIntOption(str, i);
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

            public int setStrOption(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setStrOption(str, str2);
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

            public int getIntOption(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIntOption(str, i);
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

            public String getStrOption(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStrOption(str, str2);
                    }
                    obtain2.readException();
                    String readString = obtain2.readString();
                    obtain2.recycle();
                    obtain.recycle();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IDataManagerInterface iDataManagerInterface) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iDataManagerInterface == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = iDataManagerInterface;
                return true;
            }
        }

        public static IDataManagerInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
