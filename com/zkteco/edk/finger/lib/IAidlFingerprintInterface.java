package com.zkteco.edk.finger.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.zkteco.edk.finger.lib.bean.ZkFingerprintLogResult;
import com.zkteco.edk.finger.lib.bean.ZkFingerprintTemplateResult;
import java.util.List;

public interface IAidlFingerprintInterface extends IInterface {

    public static class Default implements IAidlFingerprintInterface {
        public void addFingerprintListener(IAidlFingerprintListener iAidlFingerprintListener) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }

        public int clearTemplate() throws RemoteException {
            return 0;
        }

        public int deleteFingerprintVerifyLog(String str, String str2) throws RemoteException {
            return 0;
        }

        public int deleteTemplate(String str) throws RemoteException {
            return 0;
        }

        public int enroll(String str, List<String> list, byte[] bArr) throws RemoteException {
            return 0;
        }

        public int extract(byte[] bArr, int i, int i2, byte[] bArr2, int[] iArr) throws RemoteException {
            return 0;
        }

        public int getAlgorithmVersion(String str, String[] strArr) throws RemoteException {
            return 0;
        }

        public int getFirmwareVersion(String str, String[] strArr) throws RemoteException {
            return 0;
        }

        public int identify(byte[] bArr, int i, String[] strArr) throws RemoteException {
            return 0;
        }

        public int insertFingerprintVerifyLog(String str) throws RemoteException {
            return 0;
        }

        public int isInit() throws RemoteException {
            return 0;
        }

        public int likeQueryFingerprintTemplate(String str, long j, long j2, List<ZkFingerprintTemplateResult> list) throws RemoteException {
            return 0;
        }

        public int merge(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws RemoteException {
            return 0;
        }

        public int queryFingerprintTemplate(String str, List<ZkFingerprintTemplateResult> list) throws RemoteException {
            return 0;
        }

        public int queryFingerprintVerifyLog(String str, String str2, String str3, long j, long j2, List<ZkFingerprintLogResult> list) throws RemoteException {
            return 0;
        }

        public int queryOnlineDevice(List<String> list) throws RemoteException {
            return 0;
        }

        public int queryTemplateCount(int[] iArr) throws RemoteException {
            return 0;
        }

        public void removeFingerprintListener(IAidlFingerprintListener iAidlFingerprintListener) throws RemoteException {
        }

        public int saveTemplate(String str, byte[] bArr) throws RemoteException {
            return 0;
        }

        public int setParameter(int i) throws RemoteException {
            return 0;
        }

        public int verify(byte[] bArr, byte[] bArr2, int i) throws RemoteException {
            return 0;
        }
    }

    void addFingerprintListener(IAidlFingerprintListener iAidlFingerprintListener) throws RemoteException;

    int clearTemplate() throws RemoteException;

    int deleteFingerprintVerifyLog(String str, String str2) throws RemoteException;

    int deleteTemplate(String str) throws RemoteException;

    int enroll(String str, List<String> list, byte[] bArr) throws RemoteException;

    int extract(byte[] bArr, int i, int i2, byte[] bArr2, int[] iArr) throws RemoteException;

    int getAlgorithmVersion(String str, String[] strArr) throws RemoteException;

    int getFirmwareVersion(String str, String[] strArr) throws RemoteException;

    int identify(byte[] bArr, int i, String[] strArr) throws RemoteException;

    int insertFingerprintVerifyLog(String str) throws RemoteException;

    int isInit() throws RemoteException;

    int likeQueryFingerprintTemplate(String str, long j, long j2, List<ZkFingerprintTemplateResult> list) throws RemoteException;

    int merge(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws RemoteException;

    int queryFingerprintTemplate(String str, List<ZkFingerprintTemplateResult> list) throws RemoteException;

    int queryFingerprintVerifyLog(String str, String str2, String str3, long j, long j2, List<ZkFingerprintLogResult> list) throws RemoteException;

    int queryOnlineDevice(List<String> list) throws RemoteException;

    int queryTemplateCount(int[] iArr) throws RemoteException;

    void removeFingerprintListener(IAidlFingerprintListener iAidlFingerprintListener) throws RemoteException;

    int saveTemplate(String str, byte[] bArr) throws RemoteException;

    int setParameter(int i) throws RemoteException;

    int verify(byte[] bArr, byte[] bArr2, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IAidlFingerprintInterface {
        private static final String DESCRIPTOR = "com.zkteco.edk.finger.lib.IAidlFingerprintInterface";
        static final int TRANSACTION_addFingerprintListener = 12;
        static final int TRANSACTION_clearTemplate = 10;
        static final int TRANSACTION_deleteFingerprintVerifyLog = 18;
        static final int TRANSACTION_deleteTemplate = 9;
        static final int TRANSACTION_enroll = 5;
        static final int TRANSACTION_extract = 6;
        static final int TRANSACTION_getAlgorithmVersion = 14;
        static final int TRANSACTION_getFirmwareVersion = 16;
        static final int TRANSACTION_identify = 3;
        static final int TRANSACTION_insertFingerprintVerifyLog = 17;
        static final int TRANSACTION_isInit = 1;
        static final int TRANSACTION_likeQueryFingerprintTemplate = 20;
        static final int TRANSACTION_merge = 7;
        static final int TRANSACTION_queryFingerprintTemplate = 21;
        static final int TRANSACTION_queryFingerprintVerifyLog = 19;
        static final int TRANSACTION_queryOnlineDevice = 15;
        static final int TRANSACTION_queryTemplateCount = 11;
        static final int TRANSACTION_removeFingerprintListener = 13;
        static final int TRANSACTION_saveTemplate = 8;
        static final int TRANSACTION_setParameter = 2;
        static final int TRANSACTION_verify = 4;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAidlFingerprintInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAidlFingerprintInterface)) {
                return new Proxy(iBinder);
            }
            return (IAidlFingerprintInterface) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: byte[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: byte[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: byte[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: byte[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: int[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: byte[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: byte[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: byte[]} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r12, android.os.Parcel r13, android.os.Parcel r14, int r15) throws android.os.RemoteException {
            /*
                r11 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                java.lang.String r2 = "com.zkteco.edk.finger.lib.IAidlFingerprintInterface"
                if (r12 == r0) goto L_0x0227
                r0 = 0
                switch(r12) {
                    case 1: goto L_0x0219;
                    case 2: goto L_0x0207;
                    case 3: goto L_0x01ea;
                    case 4: goto L_0x01d0;
                    case 5: goto L_0x01b3;
                    case 6: goto L_0x0187;
                    case 7: goto L_0x0161;
                    case 8: goto L_0x014b;
                    case 9: goto L_0x0139;
                    case 10: goto L_0x012b;
                    case 11: goto L_0x0111;
                    case 12: goto L_0x00ff;
                    case 13: goto L_0x00ed;
                    case 14: goto L_0x00d4;
                    case 15: goto L_0x00bf;
                    case 16: goto L_0x00a6;
                    case 17: goto L_0x0094;
                    case 18: goto L_0x007e;
                    case 19: goto L_0x0051;
                    case 20: goto L_0x002c;
                    case 21: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r12 = super.onTransact(r12, r13, r14, r15)
                return r12
            L_0x0011:
                r13.enforceInterface(r2)
                java.lang.String r12 = r13.readString()
                android.os.Parcelable$Creator<com.zkteco.edk.finger.lib.bean.ZkFingerprintTemplateResult> r15 = com.zkteco.edk.finger.lib.bean.ZkFingerprintTemplateResult.CREATOR
                java.util.ArrayList r13 = r13.createTypedArrayList(r15)
                int r12 = r11.queryFingerprintTemplate(r12, r13)
                r14.writeNoException()
                r14.writeInt(r12)
                r14.writeTypedList(r13)
                return r1
            L_0x002c:
                r13.enforceInterface(r2)
                java.lang.String r3 = r13.readString()
                long r4 = r13.readLong()
                long r6 = r13.readLong()
                android.os.Parcelable$Creator<com.zkteco.edk.finger.lib.bean.ZkFingerprintTemplateResult> r12 = com.zkteco.edk.finger.lib.bean.ZkFingerprintTemplateResult.CREATOR
                java.util.ArrayList r12 = r13.createTypedArrayList(r12)
                r2 = r11
                r8 = r12
                int r13 = r2.likeQueryFingerprintTemplate(r3, r4, r6, r8)
                r14.writeNoException()
                r14.writeInt(r13)
                r14.writeTypedList(r12)
                return r1
            L_0x0051:
                r13.enforceInterface(r2)
                java.lang.String r3 = r13.readString()
                java.lang.String r4 = r13.readString()
                java.lang.String r5 = r13.readString()
                long r6 = r13.readLong()
                long r8 = r13.readLong()
                android.os.Parcelable$Creator<com.zkteco.edk.finger.lib.bean.ZkFingerprintLogResult> r12 = com.zkteco.edk.finger.lib.bean.ZkFingerprintLogResult.CREATOR
                java.util.ArrayList r12 = r13.createTypedArrayList(r12)
                r2 = r11
                r10 = r12
                int r13 = r2.queryFingerprintVerifyLog(r3, r4, r5, r6, r8, r10)
                r14.writeNoException()
                r14.writeInt(r13)
                r14.writeTypedList(r12)
                return r1
            L_0x007e:
                r13.enforceInterface(r2)
                java.lang.String r12 = r13.readString()
                java.lang.String r13 = r13.readString()
                int r12 = r11.deleteFingerprintVerifyLog(r12, r13)
                r14.writeNoException()
                r14.writeInt(r12)
                return r1
            L_0x0094:
                r13.enforceInterface(r2)
                java.lang.String r12 = r13.readString()
                int r12 = r11.insertFingerprintVerifyLog(r12)
                r14.writeNoException()
                r14.writeInt(r12)
                return r1
            L_0x00a6:
                r13.enforceInterface(r2)
                java.lang.String r12 = r13.readString()
                java.lang.String[] r13 = r13.createStringArray()
                int r12 = r11.getFirmwareVersion(r12, r13)
                r14.writeNoException()
                r14.writeInt(r12)
                r14.writeStringArray(r13)
                return r1
            L_0x00bf:
                r13.enforceInterface(r2)
                java.util.ArrayList r12 = r13.createStringArrayList()
                int r13 = r11.queryOnlineDevice(r12)
                r14.writeNoException()
                r14.writeInt(r13)
                r14.writeStringList(r12)
                return r1
            L_0x00d4:
                r13.enforceInterface(r2)
                java.lang.String r12 = r13.readString()
                java.lang.String[] r13 = r13.createStringArray()
                int r12 = r11.getAlgorithmVersion(r12, r13)
                r14.writeNoException()
                r14.writeInt(r12)
                r14.writeStringArray(r13)
                return r1
            L_0x00ed:
                r13.enforceInterface(r2)
                android.os.IBinder r12 = r13.readStrongBinder()
                com.zkteco.edk.finger.lib.IAidlFingerprintListener r12 = com.zkteco.edk.finger.lib.IAidlFingerprintListener.Stub.asInterface(r12)
                r11.removeFingerprintListener(r12)
                r14.writeNoException()
                return r1
            L_0x00ff:
                r13.enforceInterface(r2)
                android.os.IBinder r12 = r13.readStrongBinder()
                com.zkteco.edk.finger.lib.IAidlFingerprintListener r12 = com.zkteco.edk.finger.lib.IAidlFingerprintListener.Stub.asInterface(r12)
                r11.addFingerprintListener(r12)
                r14.writeNoException()
                return r1
            L_0x0111:
                r13.enforceInterface(r2)
                int r12 = r13.readInt()
                if (r12 >= 0) goto L_0x011b
                goto L_0x011d
            L_0x011b:
                int[] r0 = new int[r12]
            L_0x011d:
                int r12 = r11.queryTemplateCount(r0)
                r14.writeNoException()
                r14.writeInt(r12)
                r14.writeIntArray(r0)
                return r1
            L_0x012b:
                r13.enforceInterface(r2)
                int r12 = r11.clearTemplate()
                r14.writeNoException()
                r14.writeInt(r12)
                return r1
            L_0x0139:
                r13.enforceInterface(r2)
                java.lang.String r12 = r13.readString()
                int r12 = r11.deleteTemplate(r12)
                r14.writeNoException()
                r14.writeInt(r12)
                return r1
            L_0x014b:
                r13.enforceInterface(r2)
                java.lang.String r12 = r13.readString()
                byte[] r13 = r13.createByteArray()
                int r12 = r11.saveTemplate(r12, r13)
                r14.writeNoException()
                r14.writeInt(r12)
                return r1
            L_0x0161:
                r13.enforceInterface(r2)
                byte[] r12 = r13.createByteArray()
                byte[] r15 = r13.createByteArray()
                byte[] r2 = r13.createByteArray()
                int r13 = r13.readInt()
                if (r13 >= 0) goto L_0x0177
                goto L_0x0179
            L_0x0177:
                byte[] r0 = new byte[r13]
            L_0x0179:
                int r12 = r11.merge(r12, r15, r2, r0)
                r14.writeNoException()
                r14.writeInt(r12)
                r14.writeByteArray(r0)
                return r1
            L_0x0187:
                r13.enforceInterface(r2)
                byte[] r3 = r13.createByteArray()
                int r4 = r13.readInt()
                int r5 = r13.readInt()
                int r12 = r13.readInt()
                if (r12 >= 0) goto L_0x019d
                goto L_0x019f
            L_0x019d:
                byte[] r0 = new byte[r12]
            L_0x019f:
                int[] r7 = r13.createIntArray()
                r2 = r11
                r6 = r0
                int r12 = r2.extract(r3, r4, r5, r6, r7)
                r14.writeNoException()
                r14.writeInt(r12)
                r14.writeByteArray(r0)
                return r1
            L_0x01b3:
                r13.enforceInterface(r2)
                java.lang.String r12 = r13.readString()
                java.util.ArrayList r15 = r13.createStringArrayList()
                byte[] r13 = r13.createByteArray()
                int r12 = r11.enroll(r12, r15, r13)
                r14.writeNoException()
                r14.writeInt(r12)
                r14.writeByteArray(r13)
                return r1
            L_0x01d0:
                r13.enforceInterface(r2)
                byte[] r12 = r13.createByteArray()
                byte[] r15 = r13.createByteArray()
                int r13 = r13.readInt()
                int r12 = r11.verify(r12, r15, r13)
                r14.writeNoException()
                r14.writeInt(r12)
                return r1
            L_0x01ea:
                r13.enforceInterface(r2)
                byte[] r12 = r13.createByteArray()
                int r15 = r13.readInt()
                java.lang.String[] r13 = r13.createStringArray()
                int r12 = r11.identify(r12, r15, r13)
                r14.writeNoException()
                r14.writeInt(r12)
                r14.writeStringArray(r13)
                return r1
            L_0x0207:
                r13.enforceInterface(r2)
                int r12 = r13.readInt()
                int r12 = r11.setParameter(r12)
                r14.writeNoException()
                r14.writeInt(r12)
                return r1
            L_0x0219:
                r13.enforceInterface(r2)
                int r12 = r11.isInit()
                r14.writeNoException()
                r14.writeInt(r12)
                return r1
            L_0x0227:
                r14.writeString(r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zkteco.edk.finger.lib.IAidlFingerprintInterface.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAidlFingerprintInterface {
            public static IAidlFingerprintInterface sDefaultImpl;
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

            public int setParameter(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setParameter(i);
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

            public int identify(byte[] bArr, int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().identify(bArr, i, strArr);
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

            public int verify(byte[] bArr, byte[] bArr2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().verify(bArr, bArr2, i);
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

            public int enroll(String str, List<String> list, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enroll(str, list, bArr);
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

            public int extract(byte[] bArr, int i, int i2, byte[] bArr2, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (bArr2 == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(bArr2.length);
                    }
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().extract(bArr, i, i2, bArr2, iArr);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr2);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int merge(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    if (bArr4 == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(bArr4.length);
                    }
                    if (!this.mRemote.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().merge(bArr, bArr2, bArr3, bArr4);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr4);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int saveTemplate(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().saveTemplate(str, bArr);
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

            public int deleteTemplate(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deleteTemplate(str);
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

            public int clearTemplate() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().clearTemplate();
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

            public int queryTemplateCount(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iArr == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(iArr.length);
                    }
                    if (!this.mRemote.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryTemplateCount(iArr);
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

            public void addFingerprintListener(IAidlFingerprintListener iAidlFingerprintListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlFingerprintListener != null ? iAidlFingerprintListener.asBinder() : null);
                    if (this.mRemote.transact(12, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addFingerprintListener(iAidlFingerprintListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeFingerprintListener(IAidlFingerprintListener iAidlFingerprintListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAidlFingerprintListener != null ? iAidlFingerprintListener.asBinder() : null);
                    if (this.mRemote.transact(13, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeFingerprintListener(iAidlFingerprintListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getAlgorithmVersion(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAlgorithmVersion(str, strArr);
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

            public int queryOnlineDevice(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
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

            public int getFirmwareVersion(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFirmwareVersion(str, strArr);
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

            public int insertFingerprintVerifyLog(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().insertFingerprintVerifyLog(str);
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

            public int deleteFingerprintVerifyLog(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deleteFingerprintVerifyLog(str, str2);
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

            public int queryFingerprintVerifyLog(String str, String str2, String str3, long j, long j2, List<ZkFingerprintLogResult> list) throws RemoteException {
                List<ZkFingerprintLogResult> list2 = list;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    String str4 = str;
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedList(list2);
                    try {
                        if (this.mRemote.transact(19, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                            obtain2.readException();
                            int readInt = obtain2.readInt();
                            obtain2.readTypedList(list2, ZkFingerprintLogResult.CREATOR);
                            obtain2.recycle();
                            obtain.recycle();
                            return readInt;
                        }
                        int queryFingerprintVerifyLog = Stub.getDefaultImpl().queryFingerprintVerifyLog(str, str2, str3, j, j2, list);
                        obtain2.recycle();
                        obtain.recycle();
                        return queryFingerprintVerifyLog;
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

            public int likeQueryFingerprintTemplate(String str, long j, long j2, List<ZkFingerprintTemplateResult> list) throws RemoteException {
                List<ZkFingerprintTemplateResult> list2 = list;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    String str2 = str;
                    obtain.writeString(str);
                    long j3 = j;
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedList(list2);
                    try {
                        if (this.mRemote.transact(20, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                            obtain2.readException();
                            int readInt = obtain2.readInt();
                            obtain2.readTypedList(list2, ZkFingerprintTemplateResult.CREATOR);
                            obtain2.recycle();
                            obtain.recycle();
                            return readInt;
                        }
                        int likeQueryFingerprintTemplate = Stub.getDefaultImpl().likeQueryFingerprintTemplate(str, j, j2, list);
                        obtain2.recycle();
                        obtain.recycle();
                        return likeQueryFingerprintTemplate;
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

            public int queryFingerprintTemplate(String str, List<ZkFingerprintTemplateResult> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryFingerprintTemplate(str, list);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readTypedList(list, ZkFingerprintTemplateResult.CREATOR);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAidlFingerprintInterface iAidlFingerprintInterface) {
            if (Proxy.sDefaultImpl != null || iAidlFingerprintInterface == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAidlFingerprintInterface;
            return true;
        }

        public static IAidlFingerprintInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
