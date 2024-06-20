package com.zkteco.edk.card.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.zkteco.edk.card.lib.IAidlCardListener;
import com.zkteco.edk.card.lib.IAidlDeviceStateListener;
import java.util.ArrayList;
import java.util.List;

public interface IAidlCardInterface extends IInterface {

    public static class Default implements IAidlCardInterface {
        public void addCardListener(IAidlCardListener iAidlCardListener) throws RemoteException {
        }

        public void addDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }

        public int getCardRule(String str, String[] strArr) throws RemoteException {
            return 0;
        }

        public int isInit() throws RemoteException {
            return 0;
        }

        public void loadCardDevice() throws RemoteException {
        }

        public void queryOnlineDevice(List<String> list) throws RemoteException {
        }

        public void removeCardListener(IAidlCardListener iAidlCardListener) throws RemoteException {
        }

        public void removeDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException {
        }

        public byte[] sendCMD(String str) throws RemoteException {
            return null;
        }

        public int setCardRule(String str, String str2) throws RemoteException {
            return 0;
        }
    }

    void addCardListener(IAidlCardListener iAidlCardListener) throws RemoteException;

    void addDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException;

    int getCardRule(String str, String[] strArr) throws RemoteException;

    int isInit() throws RemoteException;

    void loadCardDevice() throws RemoteException;

    void queryOnlineDevice(List<String> list) throws RemoteException;

    void removeCardListener(IAidlCardListener iAidlCardListener) throws RemoteException;

    void removeDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException;

    byte[] sendCMD(String str) throws RemoteException;

    int setCardRule(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IAidlCardInterface {
        private static final String DESCRIPTOR = "com.zkteco.edk.card.lib.IAidlCardInterface";
        static final int TRANSACTION_addCardListener = 3;
        static final int TRANSACTION_addDeviceStateListener = 5;
        static final int TRANSACTION_getCardRule = 10;
        static final int TRANSACTION_isInit = 1;
        static final int TRANSACTION_loadCardDevice = 2;
        static final int TRANSACTION_queryOnlineDevice = 7;
        static final int TRANSACTION_removeCardListener = 4;
        static final int TRANSACTION_removeDeviceStateListener = 6;
        static final int TRANSACTION_sendCMD = 8;
        static final int TRANSACTION_setCardRule = 9;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAidlCardInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAidlCardInterface)) {
                return new Proxy(iBinder);
            }
            return (IAidlCardInterface) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        int isInit = isInit();
                        parcel2.writeNoException();
                        parcel2.writeInt(isInit);
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        loadCardDevice();
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        addCardListener(IAidlCardListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeCardListener(IAidlCardListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        addDeviceStateListener(IAidlDeviceStateListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeDeviceStateListener(IAidlDeviceStateListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                        queryOnlineDevice(createStringArrayList);
                        parcel2.writeNoException();
                        parcel2.writeStringList(createStringArrayList);
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        byte[] sendCMD = sendCMD(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeByteArray(sendCMD);
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        int cardRule = setCardRule(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(cardRule);
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString = parcel.readString();
                        String[] createStringArray = parcel.createStringArray();
                        int cardRule2 = getCardRule(readString, createStringArray);
                        parcel2.writeNoException();
                        parcel2.writeInt(cardRule2);
                        parcel2.writeStringArray(createStringArray);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IAidlCardInterface {
            public static IAidlCardInterface sDefaultImpl;
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

            public int isInit() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInit();
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

            public void loadCardDevice() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().loadCardDevice();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void addCardListener(IAidlCardListener iAidlCardListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlCardListener != null ? iAidlCardListener.asBinder() : null);
                    if (this.mRemote.transact(3, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addCardListener(iAidlCardListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeCardListener(IAidlCardListener iAidlCardListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlCardListener != null ? iAidlCardListener.asBinder() : null);
                    if (this.mRemote.transact(4, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeCardListener(iAidlCardListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void addDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlDeviceStateListener != null ? iAidlDeviceStateListener.asBinder() : null);
                    if (this.mRemote.transact(5, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addDeviceStateListener(iAidlDeviceStateListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlDeviceStateListener != null ? iAidlDeviceStateListener.asBinder() : null);
                    if (this.mRemote.transact(6, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeDeviceStateListener(iAidlDeviceStateListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void queryOnlineDevice(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    if (this.mRemote.transact(7, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.readStringList(list);
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().queryOnlineDevice(list);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public byte[] sendCMD(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendCMD(str);
                    }
                    obtain2.readException();
                    byte[] createByteArray = obtain2.createByteArray();
                    obtain2.recycle();
                    obtain.recycle();
                    return createByteArray;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int setCardRule(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setCardRule(str, str2);
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

            public int getCardRule(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCardRule(str, strArr);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readStringArray(strArr);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAidlCardInterface iAidlCardInterface) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iAidlCardInterface == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = iAidlCardInterface;
                return true;
            }
        }

        public static IAidlCardInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
