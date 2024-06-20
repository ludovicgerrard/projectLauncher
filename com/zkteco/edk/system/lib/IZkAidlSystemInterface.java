package com.zkteco.edk.system.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.zkteco.edk.system.lib.IZkAidlSystemListener;
import com.zkteco.edk.system.lib.bean.ZkEthernetConfig;
import com.zkteco.edk.system.lib.bean.ZkShellResult;
import com.zkteco.edk.system.lib.bean.ZkWifiConfig;
import java.util.ArrayList;
import java.util.List;

public interface IZkAidlSystemInterface extends IInterface {

    public static class Default implements IZkAidlSystemInterface {
        public void addSystemListener(IZkAidlSystemListener iZkAidlSystemListener) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }

        public void checkEthernet() throws RemoteException {
        }

        public int checkProcessesIsExist(String str) throws RemoteException {
            return 0;
        }

        public int connectEthernet(ZkEthernetConfig zkEthernetConfig) throws RemoteException {
            return 0;
        }

        public int connectWifi(String str, String str2, int i) throws RemoteException {
            return 0;
        }

        public int disconnectEthernet() throws RemoteException {
            return 0;
        }

        public int ethernetIsAvailable() throws RemoteException {
            return 0;
        }

        public int ethernetIsConnect() throws RemoteException {
            return 0;
        }

        public int executeCMD(String str, List<ZkShellResult> list) throws RemoteException {
            return 0;
        }

        public int forgetWifi(String str) throws RemoteException {
            return 0;
        }

        public int getAdbEnabled() throws RemoteException {
            return 0;
        }

        public int getEthernetConfig(List<ZkEthernetConfig> list) throws RemoteException {
            return 0;
        }

        public int getNtpServer(String[] strArr) throws RemoteException {
            return 0;
        }

        public int getScreenBrightness(int[] iArr) throws RemoteException {
            return 0;
        }

        public int getScreenBrightnessMode(int[] iArr) throws RemoteException {
            return 0;
        }

        public int isWifiConnect() throws RemoteException {
            return 0;
        }

        public void removeSystemListener(IZkAidlSystemListener iZkAidlSystemListener) throws RemoteException {
        }

        public int scanWifi(List<ZkWifiConfig> list) throws RemoteException {
            return 0;
        }

        public int setAdbEnabled(boolean z) throws RemoteException {
            return 0;
        }

        public int setEthernetEnabled(boolean z) throws RemoteException {
            return 0;
        }

        public int setNtpServer(String str) throws RemoteException {
            return 0;
        }

        public int setScreenBrightness(int i) throws RemoteException {
            return 0;
        }

        public int setScreenBrightnessMode(int i) throws RemoteException {
            return 0;
        }

        public int setWiFiEnabled(boolean z) throws RemoteException {
            return 0;
        }

        public int stopProcesses(String str) throws RemoteException {
            return 0;
        }
    }

    void addSystemListener(IZkAidlSystemListener iZkAidlSystemListener) throws RemoteException;

    void checkEthernet() throws RemoteException;

    int checkProcessesIsExist(String str) throws RemoteException;

    int connectEthernet(ZkEthernetConfig zkEthernetConfig) throws RemoteException;

    int connectWifi(String str, String str2, int i) throws RemoteException;

    int disconnectEthernet() throws RemoteException;

    int ethernetIsAvailable() throws RemoteException;

    int ethernetIsConnect() throws RemoteException;

    int executeCMD(String str, List<ZkShellResult> list) throws RemoteException;

    int forgetWifi(String str) throws RemoteException;

    int getAdbEnabled() throws RemoteException;

    int getEthernetConfig(List<ZkEthernetConfig> list) throws RemoteException;

    int getNtpServer(String[] strArr) throws RemoteException;

    int getScreenBrightness(int[] iArr) throws RemoteException;

    int getScreenBrightnessMode(int[] iArr) throws RemoteException;

    int isWifiConnect() throws RemoteException;

    void removeSystemListener(IZkAidlSystemListener iZkAidlSystemListener) throws RemoteException;

    int scanWifi(List<ZkWifiConfig> list) throws RemoteException;

    int setAdbEnabled(boolean z) throws RemoteException;

    int setEthernetEnabled(boolean z) throws RemoteException;

    int setNtpServer(String str) throws RemoteException;

    int setScreenBrightness(int i) throws RemoteException;

    int setScreenBrightnessMode(int i) throws RemoteException;

    int setWiFiEnabled(boolean z) throws RemoteException;

    int stopProcesses(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IZkAidlSystemInterface {
        private static final String DESCRIPTOR = "com.zkteco.edk.system.lib.IZkAidlSystemInterface";
        static final int TRANSACTION_addSystemListener = 1;
        static final int TRANSACTION_checkEthernet = 4;
        static final int TRANSACTION_checkProcessesIsExist = 18;
        static final int TRANSACTION_connectEthernet = 5;
        static final int TRANSACTION_connectWifi = 23;
        static final int TRANSACTION_disconnectEthernet = 6;
        static final int TRANSACTION_ethernetIsAvailable = 9;
        static final int TRANSACTION_ethernetIsConnect = 8;
        static final int TRANSACTION_executeCMD = 25;
        static final int TRANSACTION_forgetWifi = 24;
        static final int TRANSACTION_getAdbEnabled = 11;
        static final int TRANSACTION_getEthernetConfig = 7;
        static final int TRANSACTION_getNtpServer = 13;
        static final int TRANSACTION_getScreenBrightness = 17;
        static final int TRANSACTION_getScreenBrightnessMode = 15;
        static final int TRANSACTION_isWifiConnect = 21;
        static final int TRANSACTION_removeSystemListener = 2;
        static final int TRANSACTION_scanWifi = 22;
        static final int TRANSACTION_setAdbEnabled = 10;
        static final int TRANSACTION_setEthernetEnabled = 3;
        static final int TRANSACTION_setNtpServer = 12;
        static final int TRANSACTION_setScreenBrightness = 16;
        static final int TRANSACTION_setScreenBrightnessMode = 14;
        static final int TRANSACTION_setWiFiEnabled = 20;
        static final int TRANSACTION_stopProcesses = 19;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IZkAidlSystemInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IZkAidlSystemInterface)) {
                return new Proxy(iBinder);
            }
            return (IZkAidlSystemInterface) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                boolean z = false;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        addSystemListener(IZkAidlSystemListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeSystemListener(IZkAidlSystemListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        int ethernetEnabled = setEthernetEnabled(z);
                        parcel2.writeNoException();
                        parcel2.writeInt(ethernetEnabled);
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        checkEthernet();
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        int connectEthernet = connectEthernet(parcel.readInt() != 0 ? ZkEthernetConfig.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeInt(connectEthernet);
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        int disconnectEthernet = disconnectEthernet();
                        parcel2.writeNoException();
                        parcel2.writeInt(disconnectEthernet);
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        ArrayList<ZkEthernetConfig> createTypedArrayList = parcel.createTypedArrayList(ZkEthernetConfig.CREATOR);
                        int ethernetConfig = getEthernetConfig(createTypedArrayList);
                        parcel2.writeNoException();
                        parcel2.writeInt(ethernetConfig);
                        parcel2.writeTypedList(createTypedArrayList);
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        int ethernetIsConnect = ethernetIsConnect();
                        parcel2.writeNoException();
                        parcel2.writeInt(ethernetIsConnect);
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        int ethernetIsAvailable = ethernetIsAvailable();
                        parcel2.writeNoException();
                        parcel2.writeInt(ethernetIsAvailable);
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        int adbEnabled = setAdbEnabled(z);
                        parcel2.writeNoException();
                        parcel2.writeInt(adbEnabled);
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        int adbEnabled2 = getAdbEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(adbEnabled2);
                        return true;
                    case 12:
                        parcel.enforceInterface(DESCRIPTOR);
                        int ntpServer = setNtpServer(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(ntpServer);
                        return true;
                    case 13:
                        parcel.enforceInterface(DESCRIPTOR);
                        String[] createStringArray = parcel.createStringArray();
                        int ntpServer2 = getNtpServer(createStringArray);
                        parcel2.writeNoException();
                        parcel2.writeInt(ntpServer2);
                        parcel2.writeStringArray(createStringArray);
                        return true;
                    case 14:
                        parcel.enforceInterface(DESCRIPTOR);
                        int screenBrightnessMode = setScreenBrightnessMode(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(screenBrightnessMode);
                        return true;
                    case 15:
                        parcel.enforceInterface(DESCRIPTOR);
                        int[] createIntArray = parcel.createIntArray();
                        int screenBrightnessMode2 = getScreenBrightnessMode(createIntArray);
                        parcel2.writeNoException();
                        parcel2.writeInt(screenBrightnessMode2);
                        parcel2.writeIntArray(createIntArray);
                        return true;
                    case 16:
                        parcel.enforceInterface(DESCRIPTOR);
                        int screenBrightness = setScreenBrightness(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(screenBrightness);
                        return true;
                    case 17:
                        parcel.enforceInterface(DESCRIPTOR);
                        int[] createIntArray2 = parcel.createIntArray();
                        int screenBrightness2 = getScreenBrightness(createIntArray2);
                        parcel2.writeNoException();
                        parcel2.writeInt(screenBrightness2);
                        parcel2.writeIntArray(createIntArray2);
                        return true;
                    case 18:
                        parcel.enforceInterface(DESCRIPTOR);
                        int checkProcessesIsExist = checkProcessesIsExist(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(checkProcessesIsExist);
                        return true;
                    case 19:
                        parcel.enforceInterface(DESCRIPTOR);
                        int stopProcesses = stopProcesses(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(stopProcesses);
                        return true;
                    case 20:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        int wiFiEnabled = setWiFiEnabled(z);
                        parcel2.writeNoException();
                        parcel2.writeInt(wiFiEnabled);
                        return true;
                    case 21:
                        parcel.enforceInterface(DESCRIPTOR);
                        int isWifiConnect = isWifiConnect();
                        parcel2.writeNoException();
                        parcel2.writeInt(isWifiConnect);
                        return true;
                    case 22:
                        parcel.enforceInterface(DESCRIPTOR);
                        ArrayList<ZkWifiConfig> createTypedArrayList2 = parcel.createTypedArrayList(ZkWifiConfig.CREATOR);
                        int scanWifi = scanWifi(createTypedArrayList2);
                        parcel2.writeNoException();
                        parcel2.writeInt(scanWifi);
                        parcel2.writeTypedList(createTypedArrayList2);
                        return true;
                    case 23:
                        parcel.enforceInterface(DESCRIPTOR);
                        int connectWifi = connectWifi(parcel.readString(), parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(connectWifi);
                        return true;
                    case 24:
                        parcel.enforceInterface(DESCRIPTOR);
                        int forgetWifi = forgetWifi(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(forgetWifi);
                        return true;
                    case 25:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString = parcel.readString();
                        ArrayList<ZkShellResult> createTypedArrayList3 = parcel.createTypedArrayList(ZkShellResult.CREATOR);
                        int executeCMD = executeCMD(readString, createTypedArrayList3);
                        parcel2.writeNoException();
                        parcel2.writeInt(executeCMD);
                        parcel2.writeTypedList(createTypedArrayList3);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IZkAidlSystemInterface {
            public static IZkAidlSystemInterface sDefaultImpl;
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

            public void addSystemListener(IZkAidlSystemListener iZkAidlSystemListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iZkAidlSystemListener != null ? iZkAidlSystemListener.asBinder() : null);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addSystemListener(iZkAidlSystemListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeSystemListener(IZkAidlSystemListener iZkAidlSystemListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iZkAidlSystemListener != null ? iZkAidlSystemListener.asBinder() : null);
                    if (this.mRemote.transact(2, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeSystemListener(iZkAidlSystemListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int setEthernetEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setEthernetEnabled(z);
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

            public void checkEthernet() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(4, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().checkEthernet();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int connectEthernet(ZkEthernetConfig zkEthernetConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (zkEthernetConfig != null) {
                        obtain.writeInt(1);
                        zkEthernetConfig.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().connectEthernet(zkEthernetConfig);
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

            public int disconnectEthernet() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disconnectEthernet();
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

            public int getEthernetConfig(List<ZkEthernetConfig> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEthernetConfig(list);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readTypedList(list, ZkEthernetConfig.CREATOR);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int ethernetIsConnect() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().ethernetIsConnect();
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

            public int ethernetIsAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().ethernetIsAvailable();
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

            public int setAdbEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAdbEnabled(z);
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

            public int getAdbEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAdbEnabled();
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

            public int setNtpServer(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setNtpServer(str);
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

            public int getNtpServer(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNtpServer(strArr);
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

            public int setScreenBrightnessMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setScreenBrightnessMode(i);
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

            public int getScreenBrightnessMode(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getScreenBrightnessMode(iArr);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readIntArray(iArr);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int setScreenBrightness(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setScreenBrightness(i);
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

            public int getScreenBrightness(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getScreenBrightness(iArr);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readIntArray(iArr);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int checkProcessesIsExist(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkProcessesIsExist(str);
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

            public int stopProcesses(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopProcesses(str);
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

            public int setWiFiEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(20, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setWiFiEnabled(z);
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

            public int isWifiConnect() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWifiConnect();
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

            public int scanWifi(List<ZkWifiConfig> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list);
                    if (!this.mRemote.transact(22, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().scanWifi(list);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readTypedList(list, ZkWifiConfig.CREATOR);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int connectWifi(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(23, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().connectWifi(str, str2, i);
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

            public int forgetWifi(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(24, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().forgetWifi(str);
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

            public int executeCMD(String str, List<ZkShellResult> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list);
                    if (!this.mRemote.transact(25, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().executeCMD(str, list);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readTypedList(list, ZkShellResult.CREATOR);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IZkAidlSystemInterface iZkAidlSystemInterface) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iZkAidlSystemInterface == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = iZkAidlSystemInterface;
                return true;
            }
        }

        public static IZkAidlSystemInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
