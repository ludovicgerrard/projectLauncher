package com.zkteco.edk.common.device;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.zkteco.edk.common.device.IAidlDeviceStateListener;
import java.util.ArrayList;
import java.util.List;

public interface IAidlDeviceInterface extends IInterface {

    public static class Default implements IAidlDeviceInterface {
        public void addDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }

        public void queryOnlineDevice(List<String> list) throws RemoteException {
        }

        public void removeDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException {
        }
    }

    void addDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException;

    void queryOnlineDevice(List<String> list) throws RemoteException;

    void removeDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IAidlDeviceInterface {
        private static final String DESCRIPTOR = "com.zkteco.edk.common.device.IAidlDeviceInterface";
        static final int TRANSACTION_addDeviceStateListener = 1;
        static final int TRANSACTION_queryOnlineDevice = 3;
        static final int TRANSACTION_removeDeviceStateListener = 2;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAidlDeviceInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAidlDeviceInterface)) {
                return new Proxy(iBinder);
            }
            return (IAidlDeviceInterface) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                addDeviceStateListener(IAidlDeviceStateListener.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                removeDeviceStateListener(IAidlDeviceStateListener.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                queryOnlineDevice(createStringArrayList);
                parcel2.writeNoException();
                parcel2.writeStringList(createStringArrayList);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IAidlDeviceInterface {
            public static IAidlDeviceInterface sDefaultImpl;
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

            public void addDeviceStateListener(IAidlDeviceStateListener iAidlDeviceStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlDeviceStateListener != null ? iAidlDeviceStateListener.asBinder() : null);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
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
                    if (this.mRemote.transact(2, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
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
                    if (this.mRemote.transact(3, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
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
        }

        public static boolean setDefaultImpl(IAidlDeviceInterface iAidlDeviceInterface) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iAidlDeviceInterface == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = iAidlDeviceInterface;
                return true;
            }
        }

        public static IAidlDeviceInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
