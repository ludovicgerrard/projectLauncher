package com.zkteco.edk.mcu.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.zkteco.edk.mcu.lib.IAidlRs232Listener;
import com.zkteco.edk.mcu.lib.IAidlRs485Listener;
import com.zkteco.edk.mcu.lib.IAidlWiegandListener;

public interface IAidlMcuServiceInterface extends IInterface {

    public static class Default implements IAidlMcuServiceInterface {
        public void addRs232Listener(IAidlRs232Listener iAidlRs232Listener) throws RemoteException {
        }

        public void addRs485Listener(IAidlRs485Listener iAidlRs485Listener) throws RemoteException {
        }

        public void addWiegandListener(IAidlWiegandListener iAidlWiegandListener) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }

        public void close() throws RemoteException {
        }

        public boolean firmwareUpgrade(byte[] bArr) throws RemoteException {
            return false;
        }

        public int getAlarmKey() throws RemoteException {
            return 0;
        }

        public int getAlarmState() throws RemoteException {
            return 0;
        }

        public int getAuxInput() throws RemoteException {
            return 0;
        }

        public int getBUT1() throws RemoteException {
            return 0;
        }

        public int getBUT2() throws RemoteException {
            return 0;
        }

        public String getDeviceMAC() throws RemoteException {
            return null;
        }

        public int getGPIOStatus(int i) throws RemoteException {
            return 0;
        }

        public int getLock1State() throws RemoteException {
            return 0;
        }

        public int getLock2State() throws RemoteException {
            return 0;
        }

        public String getMCUVersion() throws RemoteException {
            return null;
        }

        public int[] getRTCTime() throws RemoteException {
            return null;
        }

        public String getSDKVersion() throws RemoteException {
            return null;
        }

        public int getSensor1() throws RemoteException {
            return 0;
        }

        public String getSerialNumber() throws RemoteException {
            return null;
        }

        public boolean isMCUOpen() throws RemoteException {
            return false;
        }

        public byte[] readInternalRS232Data() throws RemoteException {
            return null;
        }

        public int readWiegandInData(byte[] bArr) throws RemoteException {
            return 0;
        }

        public void removeRs232Listener(IAidlRs232Listener iAidlRs232Listener) throws RemoteException {
        }

        public void removeRs485Listener(IAidlRs485Listener iAidlRs485Listener) throws RemoteException {
        }

        public void removeWiegandListener(IAidlWiegandListener iAidlWiegandListener) throws RemoteException {
        }

        public boolean sentInternalRS232Data(byte[] bArr) throws RemoteException {
            return false;
        }

        public boolean sentRS232Data(byte[] bArr) throws RemoteException {
            return false;
        }

        public boolean sentRS485Data(byte[] bArr) throws RemoteException {
            return false;
        }

        public boolean sentWiegandData(byte[] bArr) throws RemoteException {
            return false;
        }

        public boolean setAlarm(int i) throws RemoteException {
            return false;
        }

        public boolean setInfraredLED(int i) throws RemoteException {
            return false;
        }

        public boolean setInternalRS232Properties(int i, int i2, int i3, int i4) throws RemoteException {
            return false;
        }

        public boolean setLED(int i) throws RemoteException {
            return false;
        }

        public boolean setLock1(int i) throws RemoteException {
            return false;
        }

        public boolean setLock2(int i) throws RemoteException {
            return false;
        }

        public boolean setRS232Properties(int i, int i2, int i3, int i4) throws RemoteException {
            return false;
        }

        public boolean setRS485Properties(int i, int i2, int i3, int i4) throws RemoteException {
            return false;
        }

        public boolean setRTCTime(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
            return false;
        }

        public boolean setUSBPower(int i) throws RemoteException {
            return false;
        }

        public boolean setWatchDogTime(int i) throws RemoteException {
            return false;
        }

        public boolean setWiegandOutProperty(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        public boolean watchDog() throws RemoteException {
            return false;
        }
    }

    void addRs232Listener(IAidlRs232Listener iAidlRs232Listener) throws RemoteException;

    void addRs485Listener(IAidlRs485Listener iAidlRs485Listener) throws RemoteException;

    void addWiegandListener(IAidlWiegandListener iAidlWiegandListener) throws RemoteException;

    void close() throws RemoteException;

    boolean firmwareUpgrade(byte[] bArr) throws RemoteException;

    int getAlarmKey() throws RemoteException;

    int getAlarmState() throws RemoteException;

    int getAuxInput() throws RemoteException;

    int getBUT1() throws RemoteException;

    int getBUT2() throws RemoteException;

    String getDeviceMAC() throws RemoteException;

    int getGPIOStatus(int i) throws RemoteException;

    int getLock1State() throws RemoteException;

    int getLock2State() throws RemoteException;

    String getMCUVersion() throws RemoteException;

    int[] getRTCTime() throws RemoteException;

    String getSDKVersion() throws RemoteException;

    int getSensor1() throws RemoteException;

    String getSerialNumber() throws RemoteException;

    boolean isMCUOpen() throws RemoteException;

    byte[] readInternalRS232Data() throws RemoteException;

    int readWiegandInData(byte[] bArr) throws RemoteException;

    void removeRs232Listener(IAidlRs232Listener iAidlRs232Listener) throws RemoteException;

    void removeRs485Listener(IAidlRs485Listener iAidlRs485Listener) throws RemoteException;

    void removeWiegandListener(IAidlWiegandListener iAidlWiegandListener) throws RemoteException;

    boolean sentInternalRS232Data(byte[] bArr) throws RemoteException;

    boolean sentRS232Data(byte[] bArr) throws RemoteException;

    boolean sentRS485Data(byte[] bArr) throws RemoteException;

    boolean sentWiegandData(byte[] bArr) throws RemoteException;

    boolean setAlarm(int i) throws RemoteException;

    boolean setInfraredLED(int i) throws RemoteException;

    boolean setInternalRS232Properties(int i, int i2, int i3, int i4) throws RemoteException;

    boolean setLED(int i) throws RemoteException;

    boolean setLock1(int i) throws RemoteException;

    boolean setLock2(int i) throws RemoteException;

    boolean setRS232Properties(int i, int i2, int i3, int i4) throws RemoteException;

    boolean setRS485Properties(int i, int i2, int i3, int i4) throws RemoteException;

    boolean setRTCTime(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    boolean setUSBPower(int i) throws RemoteException;

    boolean setWatchDogTime(int i) throws RemoteException;

    boolean setWiegandOutProperty(int i, int i2, int i3) throws RemoteException;

    boolean watchDog() throws RemoteException;

    public static abstract class Stub extends Binder implements IAidlMcuServiceInterface {
        private static final String DESCRIPTOR = "com.zkteco.edk.mcu.lib.IAidlMcuServiceInterface";
        static final int TRANSACTION_addRs232Listener = 40;
        static final int TRANSACTION_addRs485Listener = 38;
        static final int TRANSACTION_addWiegandListener = 36;
        static final int TRANSACTION_close = 42;
        static final int TRANSACTION_firmwareUpgrade = 30;
        static final int TRANSACTION_getAlarmKey = 34;
        static final int TRANSACTION_getAlarmState = 17;
        static final int TRANSACTION_getAuxInput = 13;
        static final int TRANSACTION_getBUT1 = 11;
        static final int TRANSACTION_getBUT2 = 12;
        static final int TRANSACTION_getDeviceMAC = 5;
        static final int TRANSACTION_getGPIOStatus = 35;
        static final int TRANSACTION_getLock1State = 8;
        static final int TRANSACTION_getLock2State = 9;
        static final int TRANSACTION_getMCUVersion = 3;
        static final int TRANSACTION_getRTCTime = 7;
        static final int TRANSACTION_getSDKVersion = 2;
        static final int TRANSACTION_getSensor1 = 10;
        static final int TRANSACTION_getSerialNumber = 4;
        static final int TRANSACTION_isMCUOpen = 1;
        static final int TRANSACTION_readInternalRS232Data = 23;
        static final int TRANSACTION_readWiegandInData = 26;
        static final int TRANSACTION_removeRs232Listener = 41;
        static final int TRANSACTION_removeRs485Listener = 39;
        static final int TRANSACTION_removeWiegandListener = 37;
        static final int TRANSACTION_sentInternalRS232Data = 22;
        static final int TRANSACTION_sentRS232Data = 21;
        static final int TRANSACTION_sentRS485Data = 24;
        static final int TRANSACTION_sentWiegandData = 27;
        static final int TRANSACTION_setAlarm = 16;
        static final int TRANSACTION_setInfraredLED = 28;
        static final int TRANSACTION_setInternalRS232Properties = 19;
        static final int TRANSACTION_setLED = 29;
        static final int TRANSACTION_setLock1 = 14;
        static final int TRANSACTION_setLock2 = 15;
        static final int TRANSACTION_setRS232Properties = 18;
        static final int TRANSACTION_setRS485Properties = 20;
        static final int TRANSACTION_setRTCTime = 6;
        static final int TRANSACTION_setUSBPower = 31;
        static final int TRANSACTION_setWatchDogTime = 33;
        static final int TRANSACTION_setWiegandOutProperty = 25;
        static final int TRANSACTION_watchDog = 32;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAidlMcuServiceInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAidlMcuServiceInterface)) {
                return new Proxy(iBinder);
            }
            return (IAidlMcuServiceInterface) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            byte[] bArr;
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean isMCUOpen = isMCUOpen();
                        parcel2.writeNoException();
                        parcel2.writeInt(isMCUOpen ? 1 : 0);
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        String sDKVersion = getSDKVersion();
                        parcel2.writeNoException();
                        parcel2.writeString(sDKVersion);
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        String mCUVersion = getMCUVersion();
                        parcel2.writeNoException();
                        parcel2.writeString(mCUVersion);
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        String serialNumber = getSerialNumber();
                        parcel2.writeNoException();
                        parcel2.writeString(serialNumber);
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        String deviceMAC = getDeviceMAC();
                        parcel2.writeNoException();
                        parcel2.writeString(deviceMAC);
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean rTCTime = setRTCTime(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(rTCTime ? 1 : 0);
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        int[] rTCTime2 = getRTCTime();
                        parcel2.writeNoException();
                        parcel2.writeIntArray(rTCTime2);
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        int lock1State = getLock1State();
                        parcel2.writeNoException();
                        parcel2.writeInt(lock1State);
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        int lock2State = getLock2State();
                        parcel2.writeNoException();
                        parcel2.writeInt(lock2State);
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        int sensor1 = getSensor1();
                        parcel2.writeNoException();
                        parcel2.writeInt(sensor1);
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        int but1 = getBUT1();
                        parcel2.writeNoException();
                        parcel2.writeInt(but1);
                        return true;
                    case 12:
                        parcel.enforceInterface(DESCRIPTOR);
                        int but2 = getBUT2();
                        parcel2.writeNoException();
                        parcel2.writeInt(but2);
                        return true;
                    case 13:
                        parcel.enforceInterface(DESCRIPTOR);
                        int auxInput = getAuxInput();
                        parcel2.writeNoException();
                        parcel2.writeInt(auxInput);
                        return true;
                    case 14:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean lock1 = setLock1(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(lock1 ? 1 : 0);
                        return true;
                    case 15:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean lock2 = setLock2(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(lock2 ? 1 : 0);
                        return true;
                    case 16:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean alarm = setAlarm(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(alarm ? 1 : 0);
                        return true;
                    case 17:
                        parcel.enforceInterface(DESCRIPTOR);
                        int alarmState = getAlarmState();
                        parcel2.writeNoException();
                        parcel2.writeInt(alarmState);
                        return true;
                    case 18:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean rS232Properties = setRS232Properties(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(rS232Properties ? 1 : 0);
                        return true;
                    case 19:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean internalRS232Properties = setInternalRS232Properties(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(internalRS232Properties ? 1 : 0);
                        return true;
                    case 20:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean rS485Properties = setRS485Properties(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(rS485Properties ? 1 : 0);
                        return true;
                    case 21:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean sentRS232Data = sentRS232Data(parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(sentRS232Data ? 1 : 0);
                        return true;
                    case 22:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean sentInternalRS232Data = sentInternalRS232Data(parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(sentInternalRS232Data ? 1 : 0);
                        return true;
                    case 23:
                        parcel.enforceInterface(DESCRIPTOR);
                        byte[] readInternalRS232Data = readInternalRS232Data();
                        parcel2.writeNoException();
                        parcel2.writeByteArray(readInternalRS232Data);
                        return true;
                    case 24:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean sentRS485Data = sentRS485Data(parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(sentRS485Data ? 1 : 0);
                        return true;
                    case 25:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean wiegandOutProperty = setWiegandOutProperty(parcel.readInt(), parcel.readInt(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(wiegandOutProperty ? 1 : 0);
                        return true;
                    case 26:
                        parcel.enforceInterface(DESCRIPTOR);
                        int readInt = parcel.readInt();
                        if (readInt < 0) {
                            bArr = null;
                        } else {
                            bArr = new byte[readInt];
                        }
                        int readWiegandInData = readWiegandInData(bArr);
                        parcel2.writeNoException();
                        parcel2.writeInt(readWiegandInData);
                        parcel2.writeByteArray(bArr);
                        return true;
                    case 27:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean sentWiegandData = sentWiegandData(parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(sentWiegandData ? 1 : 0);
                        return true;
                    case 28:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean infraredLED = setInfraredLED(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(infraredLED ? 1 : 0);
                        return true;
                    case 29:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean led = setLED(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(led ? 1 : 0);
                        return true;
                    case 30:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean firmwareUpgrade = firmwareUpgrade(parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(firmwareUpgrade ? 1 : 0);
                        return true;
                    case 31:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean uSBPower = setUSBPower(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(uSBPower ? 1 : 0);
                        return true;
                    case 32:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean watchDog = watchDog();
                        parcel2.writeNoException();
                        parcel2.writeInt(watchDog ? 1 : 0);
                        return true;
                    case 33:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean watchDogTime = setWatchDogTime(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(watchDogTime ? 1 : 0);
                        return true;
                    case 34:
                        parcel.enforceInterface(DESCRIPTOR);
                        int alarmKey = getAlarmKey();
                        parcel2.writeNoException();
                        parcel2.writeInt(alarmKey);
                        return true;
                    case 35:
                        parcel.enforceInterface(DESCRIPTOR);
                        int gPIOStatus = getGPIOStatus(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(gPIOStatus);
                        return true;
                    case 36:
                        parcel.enforceInterface(DESCRIPTOR);
                        addWiegandListener(IAidlWiegandListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 37:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeWiegandListener(IAidlWiegandListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 38:
                        parcel.enforceInterface(DESCRIPTOR);
                        addRs485Listener(IAidlRs485Listener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 39:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeRs485Listener(IAidlRs485Listener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 40:
                        parcel.enforceInterface(DESCRIPTOR);
                        addRs232Listener(IAidlRs232Listener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 41:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeRs232Listener(IAidlRs232Listener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 42:
                        parcel.enforceInterface(DESCRIPTOR);
                        close();
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IAidlMcuServiceInterface {
            public static IAidlMcuServiceInterface sDefaultImpl;
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

            public boolean isMCUOpen() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMCUOpen();
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getSDKVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSDKVersion();
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

            public String getMCUVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMCUVersion();
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

            public String getSerialNumber() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSerialNumber();
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

            public String getDeviceMAC() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceMAC();
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

            public boolean setRTCTime(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    int i7 = i;
                    obtain.writeInt(i);
                    int i8 = i2;
                    obtain.writeInt(i2);
                    int i9 = i3;
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    try {
                        boolean z = false;
                        if (this.mRemote.transact(6, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                            obtain2.readException();
                            if (obtain2.readInt() != 0) {
                                z = true;
                            }
                            obtain2.recycle();
                            obtain.recycle();
                            return z;
                        }
                        boolean rTCTime = Stub.getDefaultImpl().setRTCTime(i, i2, i3, i4, i5, i6);
                        obtain2.recycle();
                        obtain.recycle();
                        return rTCTime;
                    } catch (Throwable th) {
                        th = th;
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            public int[] getRTCTime() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRTCTime();
                    }
                    obtain2.readException();
                    int[] createIntArray = obtain2.createIntArray();
                    obtain2.recycle();
                    obtain.recycle();
                    return createIntArray;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getLock1State() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLock1State();
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

            public int getLock2State() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLock2State();
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

            public int getSensor1() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSensor1();
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

            public int getBUT1() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBUT1();
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

            public int getBUT2() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBUT2();
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

            public int getAuxInput() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAuxInput();
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

            public boolean setLock1(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = false;
                    if (!this.mRemote.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setLock1(i);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setLock2(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = false;
                    if (!this.mRemote.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setLock2(i);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setAlarm(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = false;
                    if (!this.mRemote.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAlarm(i);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getAlarmState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAlarmState();
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

            public boolean setRS232Properties(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    boolean z = false;
                    if (!this.mRemote.transact(18, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setRS232Properties(i, i2, i3, i4);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setInternalRS232Properties(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    boolean z = false;
                    if (!this.mRemote.transact(19, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setInternalRS232Properties(i, i2, i3, i4);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setRS485Properties(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    boolean z = false;
                    if (!this.mRemote.transact(20, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setRS485Properties(i, i2, i3, i4);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean sentRS232Data(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    boolean z = false;
                    if (!this.mRemote.transact(21, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sentRS232Data(bArr);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean sentInternalRS232Data(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    boolean z = false;
                    if (!this.mRemote.transact(22, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sentInternalRS232Data(bArr);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public byte[] readInternalRS232Data() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(23, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().readInternalRS232Data();
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

            public boolean sentRS485Data(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    boolean z = false;
                    if (!this.mRemote.transact(24, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sentRS485Data(bArr);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setWiegandOutProperty(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    boolean z = false;
                    if (!this.mRemote.transact(25, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setWiegandOutProperty(i, i2, i3);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int readWiegandInData(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bArr == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(bArr.length);
                    }
                    if (!this.mRemote.transact(26, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().readWiegandInData(bArr);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean sentWiegandData(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    boolean z = false;
                    if (!this.mRemote.transact(27, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sentWiegandData(bArr);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setInfraredLED(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = false;
                    if (!this.mRemote.transact(28, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setInfraredLED(i);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setLED(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = false;
                    if (!this.mRemote.transact(29, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setLED(i);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean firmwareUpgrade(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    boolean z = false;
                    if (!this.mRemote.transact(30, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().firmwareUpgrade(bArr);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setUSBPower(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = false;
                    if (!this.mRemote.transact(31, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setUSBPower(i);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean watchDog() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(32, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().watchDog();
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setWatchDogTime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = false;
                    if (!this.mRemote.transact(33, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setWatchDogTime(i);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getAlarmKey() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(34, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAlarmKey();
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

            public int getGPIOStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(35, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGPIOStatus(i);
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

            public void addWiegandListener(IAidlWiegandListener iAidlWiegandListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlWiegandListener != null ? iAidlWiegandListener.asBinder() : null);
                    if (this.mRemote.transact(36, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addWiegandListener(iAidlWiegandListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeWiegandListener(IAidlWiegandListener iAidlWiegandListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlWiegandListener != null ? iAidlWiegandListener.asBinder() : null);
                    if (this.mRemote.transact(37, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeWiegandListener(iAidlWiegandListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void addRs485Listener(IAidlRs485Listener iAidlRs485Listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlRs485Listener != null ? iAidlRs485Listener.asBinder() : null);
                    if (this.mRemote.transact(38, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addRs485Listener(iAidlRs485Listener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeRs485Listener(IAidlRs485Listener iAidlRs485Listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlRs485Listener != null ? iAidlRs485Listener.asBinder() : null);
                    if (this.mRemote.transact(39, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeRs485Listener(iAidlRs485Listener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void addRs232Listener(IAidlRs232Listener iAidlRs232Listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlRs232Listener != null ? iAidlRs232Listener.asBinder() : null);
                    if (this.mRemote.transact(40, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addRs232Listener(iAidlRs232Listener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeRs232Listener(IAidlRs232Listener iAidlRs232Listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlRs232Listener != null ? iAidlRs232Listener.asBinder() : null);
                    if (this.mRemote.transact(41, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeRs232Listener(iAidlRs232Listener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(42, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().close();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAidlMcuServiceInterface iAidlMcuServiceInterface) {
            if (Proxy.sDefaultImpl != null || iAidlMcuServiceInterface == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAidlMcuServiceInterface;
            return true;
        }

        public static IAidlMcuServiceInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
