package com.zkteco.android.videointercom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.zkteco.android.videointercom.IVideoBellCallStatusListener;
import com.zkteco.android.videointercom.IVideoP2PEventListener;
import com.zkteco.android.videointercom.IWatchDog;

public interface IVideoIntercomAidlInterface extends IInterface {

    public static class Default implements IVideoIntercomAidlInterface {
        public void P2PEventListener(IVideoP2PEventListener iVideoP2PEventListener) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }

        public void serviceLiveListener(IWatchDog iWatchDog) throws RemoteException {
        }

        public void setBellStatus(IVideoBellCallStatusListener iVideoBellCallStatusListener) throws RemoteException {
        }

        public void setCallStatus(int i) throws RemoteException {
        }

        public void writeBytesFinish(int i) throws RemoteException {
        }
    }

    void P2PEventListener(IVideoP2PEventListener iVideoP2PEventListener) throws RemoteException;

    void serviceLiveListener(IWatchDog iWatchDog) throws RemoteException;

    void setBellStatus(IVideoBellCallStatusListener iVideoBellCallStatusListener) throws RemoteException;

    void setCallStatus(int i) throws RemoteException;

    void writeBytesFinish(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IVideoIntercomAidlInterface {
        private static final String DESCRIPTOR = "com.zkteco.android.videointercom.IVideoIntercomAidlInterface";
        static final int TRANSACTION_P2PEventListener = 4;
        static final int TRANSACTION_serviceLiveListener = 5;
        static final int TRANSACTION_setBellStatus = 3;
        static final int TRANSACTION_setCallStatus = 1;
        static final int TRANSACTION_writeBytesFinish = 2;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVideoIntercomAidlInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IVideoIntercomAidlInterface)) {
                return new Proxy(iBinder);
            }
            return (IVideoIntercomAidlInterface) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                setCallStatus(parcel.readInt());
                parcel2.writeNoException();
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                writeBytesFinish(parcel.readInt());
                parcel2.writeNoException();
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                setBellStatus(IVideoBellCallStatusListener.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            } else if (i == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                P2PEventListener(IVideoP2PEventListener.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            } else if (i == 5) {
                parcel.enforceInterface(DESCRIPTOR);
                serviceLiveListener(IWatchDog.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IVideoIntercomAidlInterface {
            public static IVideoIntercomAidlInterface sDefaultImpl;
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

            public void setCallStatus(int i) throws RemoteException {
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
                    Stub.getDefaultImpl().setCallStatus(i);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void writeBytesFinish(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(2, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().writeBytesFinish(i);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setBellStatus(IVideoBellCallStatusListener iVideoBellCallStatusListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iVideoBellCallStatusListener != null ? iVideoBellCallStatusListener.asBinder() : null);
                    if (this.mRemote.transact(3, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBellStatus(iVideoBellCallStatusListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void P2PEventListener(IVideoP2PEventListener iVideoP2PEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iVideoP2PEventListener != null ? iVideoP2PEventListener.asBinder() : null);
                    if (this.mRemote.transact(4, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().P2PEventListener(iVideoP2PEventListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void serviceLiveListener(IWatchDog iWatchDog) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iWatchDog != null ? iWatchDog.asBinder() : null);
                    if (this.mRemote.transact(5, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().serviceLiveListener(iWatchDog);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVideoIntercomAidlInterface iVideoIntercomAidlInterface) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iVideoIntercomAidlInterface == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = iVideoIntercomAidlInterface;
                return true;
            }
        }

        public static IVideoIntercomAidlInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
