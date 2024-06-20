package com.zkteco.liveface562;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.zkteco.liveface562.bean.AgeAndGender;
import com.zkteco.liveface562.bean.IdentifyInfo;
import com.zkteco.liveface562.bean.LivenessResult;
import com.zkteco.liveface562.bean.TopKIdentifyInfo;
import com.zkteco.liveface562.bean.ZkCompareResult;
import com.zkteco.liveface562.bean.ZkDetectInfo;
import com.zkteco.liveface562.bean.ZkExtractResult;
import com.zkteco.liveface562.bean.ZkFaceConfig;
import com.zkteco.liveface562.bean.ZkFaceTemplateResult;
import java.util.ArrayList;
import java.util.List;

public interface IFaceEdkService extends IInterface {

    public static class Default implements IFaceEdkService {
        public int activateFaceAlgorithm() throws RemoteException {
            return 0;
        }

        public void addFaceInitListener(IFaceInitListener iFaceInitListener) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }

        public int checkLicense(long[] jArr, int[] iArr) throws RemoteException {
            return 0;
        }

        public ZkCompareResult compare(Bitmap bitmap, Bitmap bitmap2, boolean z) throws RemoteException {
            return null;
        }

        public int dbAdd(String str, byte[] bArr) throws RemoteException {
            return 0;
        }

        public int dbClear() throws RemoteException {
            return 0;
        }

        public int dbCount(int[] iArr) throws RemoteException {
            return 0;
        }

        public int dbDel(String str) throws RemoteException {
            return 0;
        }

        public List<IdentifyInfo> dbIdentify(byte[] bArr) throws RemoteException {
            return null;
        }

        public List<TopKIdentifyInfo> dbIdentifyTopK(byte[] bArr, int i) throws RemoteException {
            return null;
        }

        public ZkDetectInfo detectFacesFromRGBIR(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, int i, int i2, int i3) throws RemoteException {
            return null;
        }

        public ZkDetectInfo detectFacesFromRGBIREx(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, int i, int i2, int i3, int i4, int i5) throws RemoteException {
            return null;
        }

        public ZkDetectInfo detectFromNV21(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException {
            return null;
        }

        public ZkDetectInfo detectFromNV21Ex(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3, int i4) throws RemoteException {
            return null;
        }

        public ZkExtractResult extractFeature(Bitmap bitmap, boolean z) throws RemoteException {
            return null;
        }

        public int generateFaceAlgorithmFile() throws RemoteException {
            return 0;
        }

        public AgeAndGender[] getAgeAndGender(byte[] bArr) throws RemoteException {
            return null;
        }

        public ZkFaceConfig getConfig() throws RemoteException {
            return null;
        }

        public int getFaceEngineSdkVersion(String[] strArr) throws RemoteException {
            return 0;
        }

        public void init() throws RemoteException {
        }

        public void initWithConfig(ZkFaceConfig zkFaceConfig) throws RemoteException {
        }

        public int isAuthorized() throws RemoteException {
            return 0;
        }

        public int isHaveChip() throws RemoteException {
            return 0;
        }

        public int isInit() throws RemoteException {
            return 0;
        }

        public int livenessClassify(byte[] bArr, List<LivenessResult> list) throws RemoteException {
            return 0;
        }

        public int loadTemplateFromAlgorithmDatabase() throws RemoteException {
            return 0;
        }

        public int queryFaceTemplate(String str, byte[] bArr) throws RemoteException {
            return 0;
        }

        public int queryFaceTemplatePins(long j, long j2, List<ZkFaceTemplateResult> list) throws RemoteException {
            return 0;
        }

        public int release() throws RemoteException {
            return 0;
        }

        public void removeFaceInitListener(IFaceInitListener iFaceInitListener) throws RemoteException {
        }

        public int reset() throws RemoteException {
            return 0;
        }

        public int resetTrackId(long j) throws RemoteException {
            return 0;
        }

        public int setConfig(ZkFaceConfig zkFaceConfig) throws RemoteException {
            return 0;
        }
    }

    int activateFaceAlgorithm() throws RemoteException;

    void addFaceInitListener(IFaceInitListener iFaceInitListener) throws RemoteException;

    int checkLicense(long[] jArr, int[] iArr) throws RemoteException;

    ZkCompareResult compare(Bitmap bitmap, Bitmap bitmap2, boolean z) throws RemoteException;

    int dbAdd(String str, byte[] bArr) throws RemoteException;

    int dbClear() throws RemoteException;

    int dbCount(int[] iArr) throws RemoteException;

    int dbDel(String str) throws RemoteException;

    List<IdentifyInfo> dbIdentify(byte[] bArr) throws RemoteException;

    List<TopKIdentifyInfo> dbIdentifyTopK(byte[] bArr, int i) throws RemoteException;

    ZkDetectInfo detectFacesFromRGBIR(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, int i, int i2, int i3) throws RemoteException;

    ZkDetectInfo detectFacesFromRGBIREx(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, int i, int i2, int i3, int i4, int i5) throws RemoteException;

    ZkDetectInfo detectFromNV21(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException;

    ZkDetectInfo detectFromNV21Ex(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3, int i4) throws RemoteException;

    ZkExtractResult extractFeature(Bitmap bitmap, boolean z) throws RemoteException;

    int generateFaceAlgorithmFile() throws RemoteException;

    AgeAndGender[] getAgeAndGender(byte[] bArr) throws RemoteException;

    ZkFaceConfig getConfig() throws RemoteException;

    int getFaceEngineSdkVersion(String[] strArr) throws RemoteException;

    void init() throws RemoteException;

    void initWithConfig(ZkFaceConfig zkFaceConfig) throws RemoteException;

    int isAuthorized() throws RemoteException;

    int isHaveChip() throws RemoteException;

    int isInit() throws RemoteException;

    int livenessClassify(byte[] bArr, List<LivenessResult> list) throws RemoteException;

    int loadTemplateFromAlgorithmDatabase() throws RemoteException;

    int queryFaceTemplate(String str, byte[] bArr) throws RemoteException;

    int queryFaceTemplatePins(long j, long j2, List<ZkFaceTemplateResult> list) throws RemoteException;

    int release() throws RemoteException;

    void removeFaceInitListener(IFaceInitListener iFaceInitListener) throws RemoteException;

    int reset() throws RemoteException;

    int resetTrackId(long j) throws RemoteException;

    int setConfig(ZkFaceConfig zkFaceConfig) throws RemoteException;

    public static abstract class Stub extends Binder implements IFaceEdkService {
        private static final String DESCRIPTOR = "com.zkteco.liveface562.IFaceEdkService";
        static final int TRANSACTION_activateFaceAlgorithm = 30;
        static final int TRANSACTION_addFaceInitListener = 26;
        static final int TRANSACTION_checkLicense = 31;
        static final int TRANSACTION_compare = 17;
        static final int TRANSACTION_dbAdd = 18;
        static final int TRANSACTION_dbClear = 20;
        static final int TRANSACTION_dbCount = 21;
        static final int TRANSACTION_dbDel = 19;
        static final int TRANSACTION_dbIdentify = 11;
        static final int TRANSACTION_dbIdentifyTopK = 12;
        static final int TRANSACTION_detectFacesFromRGBIR = 7;
        static final int TRANSACTION_detectFacesFromRGBIREx = 9;
        static final int TRANSACTION_detectFromNV21 = 6;
        static final int TRANSACTION_detectFromNV21Ex = 8;
        static final int TRANSACTION_extractFeature = 10;
        static final int TRANSACTION_generateFaceAlgorithmFile = 29;
        static final int TRANSACTION_getAgeAndGender = 13;
        static final int TRANSACTION_getConfig = 4;
        static final int TRANSACTION_getFaceEngineSdkVersion = 28;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_initWithConfig = 2;
        static final int TRANSACTION_isAuthorized = 33;
        static final int TRANSACTION_isHaveChip = 32;
        static final int TRANSACTION_isInit = 5;
        static final int TRANSACTION_livenessClassify = 14;
        static final int TRANSACTION_loadTemplateFromAlgorithmDatabase = 15;
        static final int TRANSACTION_queryFaceTemplate = 24;
        static final int TRANSACTION_queryFaceTemplatePins = 25;
        static final int TRANSACTION_release = 23;
        static final int TRANSACTION_removeFaceInitListener = 27;
        static final int TRANSACTION_reset = 22;
        static final int TRANSACTION_resetTrackId = 16;
        static final int TRANSACTION_setConfig = 3;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFaceEdkService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IFaceEdkService)) {
                return new Proxy(iBinder);
            }
            return (IFaceEdkService) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: com.zkteco.liveface562.bean.ZkFaceConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: com.zkteco.liveface562.bean.ZkFaceConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v16, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v18, resolved type: android.graphics.Bitmap} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v22, resolved type: android.graphics.Bitmap} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v26, resolved type: byte[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v29, resolved type: java.lang.String[]} */
        /* JADX WARNING: type inference failed for: r2v1 */
        /* JADX WARNING: type inference failed for: r2v12 */
        /* JADX WARNING: type inference failed for: r2v13 */
        /* JADX WARNING: type inference failed for: r2v15 */
        /* JADX WARNING: type inference failed for: r2v17 */
        /* JADX WARNING: type inference failed for: r2v32 */
        /* JADX WARNING: type inference failed for: r2v33 */
        /* JADX WARNING: type inference failed for: r2v34 */
        /* JADX WARNING: type inference failed for: r2v35 */
        /* JADX WARNING: type inference failed for: r2v36 */
        /* JADX WARNING: type inference failed for: r2v37 */
        /* JADX WARNING: type inference failed for: r2v38 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r14, android.os.Parcel r15, android.os.Parcel r16, int r17) throws android.os.RemoteException {
            /*
                r13 = this;
                r8 = r13
                r0 = r14
                r1 = r15
                r9 = r16
                r2 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                java.lang.String r3 = "com.zkteco.liveface562.IFaceEdkService"
                if (r0 == r2) goto L_0x039a
                r11 = 0
                r2 = 0
                switch(r0) {
                    case 1: goto L_0x0393;
                    case 2: goto L_0x037d;
                    case 3: goto L_0x0360;
                    case 4: goto L_0x0349;
                    case 5: goto L_0x033b;
                    case 6: goto L_0x0309;
                    case 7: goto L_0x02c1;
                    case 8: goto L_0x0285;
                    case 9: goto L_0x0233;
                    case 10: goto L_0x0204;
                    case 11: goto L_0x01f2;
                    case 12: goto L_0x01dc;
                    case 13: goto L_0x01ca;
                    case 14: goto L_0x01af;
                    case 15: goto L_0x01a1;
                    case 16: goto L_0x018f;
                    case 17: goto L_0x0151;
                    case 18: goto L_0x013b;
                    case 19: goto L_0x0129;
                    case 20: goto L_0x011b;
                    case 21: goto L_0x0106;
                    case 22: goto L_0x00f8;
                    case 23: goto L_0x00ea;
                    case 24: goto L_0x00cc;
                    case 25: goto L_0x00a9;
                    case 26: goto L_0x0097;
                    case 27: goto L_0x0085;
                    case 28: goto L_0x006b;
                    case 29: goto L_0x005d;
                    case 30: goto L_0x004f;
                    case 31: goto L_0x0033;
                    case 32: goto L_0x0025;
                    case 33: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r14, r15, r16, r17)
                return r0
            L_0x0017:
                r15.enforceInterface(r3)
                int r0 = r13.isAuthorized()
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x0025:
                r15.enforceInterface(r3)
                int r0 = r13.isHaveChip()
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x0033:
                r15.enforceInterface(r3)
                long[] r0 = r15.createLongArray()
                int[] r1 = r15.createIntArray()
                int r2 = r13.checkLicense(r0, r1)
                r16.writeNoException()
                r9.writeInt(r2)
                r9.writeLongArray(r0)
                r9.writeIntArray(r1)
                return r10
            L_0x004f:
                r15.enforceInterface(r3)
                int r0 = r13.activateFaceAlgorithm()
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x005d:
                r15.enforceInterface(r3)
                int r0 = r13.generateFaceAlgorithmFile()
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x006b:
                r15.enforceInterface(r3)
                int r0 = r15.readInt()
                if (r0 >= 0) goto L_0x0075
                goto L_0x0077
            L_0x0075:
                java.lang.String[] r2 = new java.lang.String[r0]
            L_0x0077:
                int r0 = r13.getFaceEngineSdkVersion(r2)
                r16.writeNoException()
                r9.writeInt(r0)
                r9.writeStringArray(r2)
                return r10
            L_0x0085:
                r15.enforceInterface(r3)
                android.os.IBinder r0 = r15.readStrongBinder()
                com.zkteco.liveface562.IFaceInitListener r0 = com.zkteco.liveface562.IFaceInitListener.Stub.asInterface(r0)
                r13.removeFaceInitListener(r0)
                r16.writeNoException()
                return r10
            L_0x0097:
                r15.enforceInterface(r3)
                android.os.IBinder r0 = r15.readStrongBinder()
                com.zkteco.liveface562.IFaceInitListener r0 = com.zkteco.liveface562.IFaceInitListener.Stub.asInterface(r0)
                r13.addFaceInitListener(r0)
                r16.writeNoException()
                return r10
            L_0x00a9:
                r15.enforceInterface(r3)
                long r2 = r15.readLong()
                long r4 = r15.readLong()
                android.os.Parcelable$Creator<com.zkteco.liveface562.bean.ZkFaceTemplateResult> r0 = com.zkteco.liveface562.bean.ZkFaceTemplateResult.CREATOR
                java.util.ArrayList r6 = r15.createTypedArrayList(r0)
                r0 = r13
                r1 = r2
                r3 = r4
                r5 = r6
                int r0 = r0.queryFaceTemplatePins(r1, r3, r5)
                r16.writeNoException()
                r9.writeInt(r0)
                r9.writeTypedList(r6)
                return r10
            L_0x00cc:
                r15.enforceInterface(r3)
                java.lang.String r0 = r15.readString()
                int r1 = r15.readInt()
                if (r1 >= 0) goto L_0x00da
                goto L_0x00dc
            L_0x00da:
                byte[] r2 = new byte[r1]
            L_0x00dc:
                int r0 = r13.queryFaceTemplate(r0, r2)
                r16.writeNoException()
                r9.writeInt(r0)
                r9.writeByteArray(r2)
                return r10
            L_0x00ea:
                r15.enforceInterface(r3)
                int r0 = r13.release()
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x00f8:
                r15.enforceInterface(r3)
                int r0 = r13.reset()
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x0106:
                r15.enforceInterface(r3)
                int[] r0 = r15.createIntArray()
                int r1 = r13.dbCount(r0)
                r16.writeNoException()
                r9.writeInt(r1)
                r9.writeIntArray(r0)
                return r10
            L_0x011b:
                r15.enforceInterface(r3)
                int r0 = r13.dbClear()
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x0129:
                r15.enforceInterface(r3)
                java.lang.String r0 = r15.readString()
                int r0 = r13.dbDel(r0)
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x013b:
                r15.enforceInterface(r3)
                java.lang.String r0 = r15.readString()
                byte[] r1 = r15.createByteArray()
                int r0 = r13.dbAdd(r0, r1)
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x0151:
                r15.enforceInterface(r3)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x0163
                android.os.Parcelable$Creator r0 = android.graphics.Bitmap.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
                goto L_0x0164
            L_0x0163:
                r0 = r2
            L_0x0164:
                int r3 = r15.readInt()
                if (r3 == 0) goto L_0x0172
                android.os.Parcelable$Creator r2 = android.graphics.Bitmap.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r15)
                android.graphics.Bitmap r2 = (android.graphics.Bitmap) r2
            L_0x0172:
                int r1 = r15.readInt()
                if (r1 == 0) goto L_0x017a
                r1 = r10
                goto L_0x017b
            L_0x017a:
                r1 = r11
            L_0x017b:
                com.zkteco.liveface562.bean.ZkCompareResult r0 = r13.compare(r0, r2, r1)
                r16.writeNoException()
                if (r0 == 0) goto L_0x018b
                r9.writeInt(r10)
                r0.writeToParcel(r9, r10)
                goto L_0x018e
            L_0x018b:
                r9.writeInt(r11)
            L_0x018e:
                return r10
            L_0x018f:
                r15.enforceInterface(r3)
                long r0 = r15.readLong()
                int r0 = r13.resetTrackId(r0)
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x01a1:
                r15.enforceInterface(r3)
                int r0 = r13.loadTemplateFromAlgorithmDatabase()
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x01af:
                r15.enforceInterface(r3)
                byte[] r0 = r15.createByteArray()
                android.os.Parcelable$Creator<com.zkteco.liveface562.bean.LivenessResult> r2 = com.zkteco.liveface562.bean.LivenessResult.CREATOR
                java.util.ArrayList r1 = r15.createTypedArrayList(r2)
                int r0 = r13.livenessClassify(r0, r1)
                r16.writeNoException()
                r9.writeInt(r0)
                r9.writeTypedList(r1)
                return r10
            L_0x01ca:
                r15.enforceInterface(r3)
                byte[] r0 = r15.createByteArray()
                com.zkteco.liveface562.bean.AgeAndGender[] r0 = r13.getAgeAndGender(r0)
                r16.writeNoException()
                r9.writeTypedArray(r0, r10)
                return r10
            L_0x01dc:
                r15.enforceInterface(r3)
                byte[] r0 = r15.createByteArray()
                int r1 = r15.readInt()
                java.util.List r0 = r13.dbIdentifyTopK(r0, r1)
                r16.writeNoException()
                r9.writeTypedList(r0)
                return r10
            L_0x01f2:
                r15.enforceInterface(r3)
                byte[] r0 = r15.createByteArray()
                java.util.List r0 = r13.dbIdentify(r0)
                r16.writeNoException()
                r9.writeTypedList(r0)
                return r10
            L_0x0204:
                r15.enforceInterface(r3)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x0216
                android.os.Parcelable$Creator r0 = android.graphics.Bitmap.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                r2 = r0
                android.graphics.Bitmap r2 = (android.graphics.Bitmap) r2
            L_0x0216:
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x021e
                r0 = r10
                goto L_0x021f
            L_0x021e:
                r0 = r11
            L_0x021f:
                com.zkteco.liveface562.bean.ZkExtractResult r0 = r13.extractFeature(r2, r0)
                r16.writeNoException()
                if (r0 == 0) goto L_0x022f
                r9.writeInt(r10)
                r0.writeToParcel(r9, r10)
                goto L_0x0232
            L_0x022f:
                r9.writeInt(r11)
            L_0x0232:
                return r10
            L_0x0233:
                r15.enforceInterface(r3)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x0246
                android.os.Parcelable$Creator r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                r3 = r0
                goto L_0x0247
            L_0x0246:
                r3 = r2
            L_0x0247:
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x0256
                android.os.Parcelable$Creator r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                r2 = r0
            L_0x0256:
                int r4 = r15.readInt()
                int r5 = r15.readInt()
                int r6 = r15.readInt()
                int r7 = r15.readInt()
                int r12 = r15.readInt()
                r0 = r13
                r1 = r3
                r3 = r4
                r4 = r5
                r5 = r6
                r6 = r7
                r7 = r12
                com.zkteco.liveface562.bean.ZkDetectInfo r0 = r0.detectFacesFromRGBIREx(r1, r2, r3, r4, r5, r6, r7)
                r16.writeNoException()
                if (r0 == 0) goto L_0x0281
                r9.writeInt(r10)
                r0.writeToParcel(r9, r10)
                goto L_0x0284
            L_0x0281:
                r9.writeInt(r11)
            L_0x0284:
                return r10
            L_0x0285:
                r15.enforceInterface(r3)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x0297
                android.os.Parcelable$Creator r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                r2 = r0
            L_0x0297:
                int r3 = r15.readInt()
                int r4 = r15.readInt()
                int r5 = r15.readInt()
                int r6 = r15.readInt()
                r0 = r13
                r1 = r2
                r2 = r3
                r3 = r4
                r4 = r5
                r5 = r6
                com.zkteco.liveface562.bean.ZkDetectInfo r0 = r0.detectFromNV21Ex(r1, r2, r3, r4, r5)
                r16.writeNoException()
                if (r0 == 0) goto L_0x02bd
                r9.writeInt(r10)
                r0.writeToParcel(r9, r10)
                goto L_0x02c0
            L_0x02bd:
                r9.writeInt(r11)
            L_0x02c0:
                return r10
            L_0x02c1:
                r15.enforceInterface(r3)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x02d4
                android.os.Parcelable$Creator r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                r3 = r0
                goto L_0x02d5
            L_0x02d4:
                r3 = r2
            L_0x02d5:
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x02e4
                android.os.Parcelable$Creator r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                r2 = r0
            L_0x02e4:
                int r4 = r15.readInt()
                int r5 = r15.readInt()
                int r6 = r15.readInt()
                r0 = r13
                r1 = r3
                r3 = r4
                r4 = r5
                r5 = r6
                com.zkteco.liveface562.bean.ZkDetectInfo r0 = r0.detectFacesFromRGBIR(r1, r2, r3, r4, r5)
                r16.writeNoException()
                if (r0 == 0) goto L_0x0305
                r9.writeInt(r10)
                r0.writeToParcel(r9, r10)
                goto L_0x0308
            L_0x0305:
                r9.writeInt(r11)
            L_0x0308:
                return r10
            L_0x0309:
                r15.enforceInterface(r3)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x031b
                android.os.Parcelable$Creator r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                r2 = r0
                android.os.ParcelFileDescriptor r2 = (android.os.ParcelFileDescriptor) r2
            L_0x031b:
                int r0 = r15.readInt()
                int r3 = r15.readInt()
                int r1 = r15.readInt()
                com.zkteco.liveface562.bean.ZkDetectInfo r0 = r13.detectFromNV21(r2, r0, r3, r1)
                r16.writeNoException()
                if (r0 == 0) goto L_0x0337
                r9.writeInt(r10)
                r0.writeToParcel(r9, r10)
                goto L_0x033a
            L_0x0337:
                r9.writeInt(r11)
            L_0x033a:
                return r10
            L_0x033b:
                r15.enforceInterface(r3)
                int r0 = r13.isInit()
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x0349:
                r15.enforceInterface(r3)
                com.zkteco.liveface562.bean.ZkFaceConfig r0 = r13.getConfig()
                r16.writeNoException()
                if (r0 == 0) goto L_0x035c
                r9.writeInt(r10)
                r0.writeToParcel(r9, r10)
                goto L_0x035f
            L_0x035c:
                r9.writeInt(r11)
            L_0x035f:
                return r10
            L_0x0360:
                r15.enforceInterface(r3)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x0372
                android.os.Parcelable$Creator<com.zkteco.liveface562.bean.ZkFaceConfig> r0 = com.zkteco.liveface562.bean.ZkFaceConfig.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                r2 = r0
                com.zkteco.liveface562.bean.ZkFaceConfig r2 = (com.zkteco.liveface562.bean.ZkFaceConfig) r2
            L_0x0372:
                int r0 = r13.setConfig(r2)
                r16.writeNoException()
                r9.writeInt(r0)
                return r10
            L_0x037d:
                r15.enforceInterface(r3)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x038f
                android.os.Parcelable$Creator<com.zkteco.liveface562.bean.ZkFaceConfig> r0 = com.zkteco.liveface562.bean.ZkFaceConfig.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                r2 = r0
                com.zkteco.liveface562.bean.ZkFaceConfig r2 = (com.zkteco.liveface562.bean.ZkFaceConfig) r2
            L_0x038f:
                r13.initWithConfig(r2)
                return r10
            L_0x0393:
                r15.enforceInterface(r3)
                r13.init()
                return r10
            L_0x039a:
                r9.writeString(r3)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zkteco.liveface562.IFaceEdkService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IFaceEdkService {
            public static IFaceEdkService sDefaultImpl;
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

            public void init() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, obtain, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        obtain.recycle();
                    } else {
                        Stub.getDefaultImpl().init();
                    }
                } finally {
                    obtain.recycle();
                }
            }

            public void initWithConfig(ZkFaceConfig zkFaceConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (zkFaceConfig != null) {
                        obtain.writeInt(1);
                        zkFaceConfig.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (this.mRemote.transact(2, obtain, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        obtain.recycle();
                    } else {
                        Stub.getDefaultImpl().initWithConfig(zkFaceConfig);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            public int setConfig(ZkFaceConfig zkFaceConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (zkFaceConfig != null) {
                        obtain.writeInt(1);
                        zkFaceConfig.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setConfig(zkFaceConfig);
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

            public ZkFaceConfig getConfig() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfig();
                    }
                    obtain2.readException();
                    ZkFaceConfig createFromParcel = obtain2.readInt() != 0 ? ZkFaceConfig.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return createFromParcel;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int isInit() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
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

            public ZkDetectInfo detectFromNV21(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        obtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detectFromNV21(parcelFileDescriptor, i, i2, i3);
                    }
                    obtain2.readException();
                    ZkDetectInfo createFromParcel = obtain2.readInt() != 0 ? ZkDetectInfo.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return createFromParcel;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public ZkDetectInfo detectFacesFromRGBIR(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        obtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (parcelFileDescriptor2 != null) {
                        obtain.writeInt(1);
                        parcelFileDescriptor2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detectFacesFromRGBIR(parcelFileDescriptor, parcelFileDescriptor2, i, i2, i3);
                    }
                    obtain2.readException();
                    ZkDetectInfo createFromParcel = obtain2.readInt() != 0 ? ZkDetectInfo.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return createFromParcel;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public ZkDetectInfo detectFromNV21Ex(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        obtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detectFromNV21Ex(parcelFileDescriptor, i, i2, i3, i4);
                    }
                    obtain2.readException();
                    ZkDetectInfo createFromParcel = obtain2.readInt() != 0 ? ZkDetectInfo.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return createFromParcel;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public ZkDetectInfo detectFacesFromRGBIREx(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, int i, int i2, int i3, int i4, int i5) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor3 = parcelFileDescriptor;
                ParcelFileDescriptor parcelFileDescriptor4 = parcelFileDescriptor2;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor3 != null) {
                        obtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (parcelFileDescriptor4 != null) {
                        obtain.writeInt(1);
                        parcelFileDescriptor2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    try {
                        if (this.mRemote.transact(9, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                            obtain2.readException();
                            ZkDetectInfo createFromParcel = obtain2.readInt() != 0 ? ZkDetectInfo.CREATOR.createFromParcel(obtain2) : null;
                            obtain2.recycle();
                            obtain.recycle();
                            return createFromParcel;
                        }
                        ZkDetectInfo detectFacesFromRGBIREx = Stub.getDefaultImpl().detectFacesFromRGBIREx(parcelFileDescriptor, parcelFileDescriptor2, i, i2, i3, i4, i5);
                        obtain2.recycle();
                        obtain.recycle();
                        return detectFacesFromRGBIREx;
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

            public ZkExtractResult extractFeature(Bitmap bitmap, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    int i = 1;
                    if (bitmap != null) {
                        obtain.writeInt(1);
                        bitmap.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().extractFeature(bitmap, z);
                    }
                    obtain2.readException();
                    ZkExtractResult createFromParcel = obtain2.readInt() != 0 ? ZkExtractResult.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return createFromParcel;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public List<IdentifyInfo> dbIdentify(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dbIdentify(bArr);
                    }
                    obtain2.readException();
                    ArrayList<IdentifyInfo> createTypedArrayList = obtain2.createTypedArrayList(IdentifyInfo.CREATOR);
                    obtain2.recycle();
                    obtain.recycle();
                    return createTypedArrayList;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public List<TopKIdentifyInfo> dbIdentifyTopK(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dbIdentifyTopK(bArr, i);
                    }
                    obtain2.readException();
                    ArrayList<TopKIdentifyInfo> createTypedArrayList = obtain2.createTypedArrayList(TopKIdentifyInfo.CREATOR);
                    obtain2.recycle();
                    obtain.recycle();
                    return createTypedArrayList;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public AgeAndGender[] getAgeAndGender(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAgeAndGender(bArr);
                    }
                    obtain2.readException();
                    AgeAndGender[] ageAndGenderArr = (AgeAndGender[]) obtain2.createTypedArray(AgeAndGender.CREATOR);
                    obtain2.recycle();
                    obtain.recycle();
                    return ageAndGenderArr;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int livenessClassify(byte[] bArr, List<LivenessResult> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedList(list);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().livenessClassify(bArr, list);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readTypedList(list, LivenessResult.CREATOR);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int loadTemplateFromAlgorithmDatabase() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().loadTemplateFromAlgorithmDatabase();
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

            public int resetTrackId(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resetTrackId(j);
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

            public ZkCompareResult compare(Bitmap bitmap, Bitmap bitmap2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    int i = 1;
                    if (bitmap != null) {
                        obtain.writeInt(1);
                        bitmap.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bitmap2 != null) {
                        obtain.writeInt(1);
                        bitmap2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().compare(bitmap, bitmap2, z);
                    }
                    obtain2.readException();
                    ZkCompareResult createFromParcel = obtain2.readInt() != 0 ? ZkCompareResult.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return createFromParcel;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int dbAdd(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dbAdd(str, bArr);
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

            public int dbDel(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dbDel(str);
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

            public int dbClear() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dbClear();
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

            public int dbCount(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dbCount(iArr);
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

            public int reset() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reset();
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

            public int release() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(23, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().release();
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

            public int queryFaceTemplate(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bArr == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(bArr.length);
                    }
                    if (!this.mRemote.transact(24, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryFaceTemplate(str, bArr);
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

            public int queryFaceTemplatePins(long j, long j2, List<ZkFaceTemplateResult> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedList(list);
                    if (!this.mRemote.transact(25, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryFaceTemplatePins(j, j2, list);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readTypedList(list, ZkFaceTemplateResult.CREATOR);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void addFaceInitListener(IFaceInitListener iFaceInitListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iFaceInitListener != null ? iFaceInitListener.asBinder() : null);
                    if (this.mRemote.transact(26, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addFaceInitListener(iFaceInitListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeFaceInitListener(IFaceInitListener iFaceInitListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iFaceInitListener != null ? iFaceInitListener.asBinder() : null);
                    if (this.mRemote.transact(27, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeFaceInitListener(iFaceInitListener);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getFaceEngineSdkVersion(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (strArr == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(strArr.length);
                    }
                    if (!this.mRemote.transact(28, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFaceEngineSdkVersion(strArr);
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

            public int generateFaceAlgorithmFile() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().generateFaceAlgorithmFile();
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

            public int activateFaceAlgorithm() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(30, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().activateFaceAlgorithm();
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

            public int checkLicense(long[] jArr, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLongArray(jArr);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(31, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkLicense(jArr, iArr);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readLongArray(jArr);
                    obtain2.readIntArray(iArr);
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int isHaveChip() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHaveChip();
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

            public int isAuthorized() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(33, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAuthorized();
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

        public static boolean setDefaultImpl(IFaceEdkService iFaceEdkService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iFaceEdkService == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = iFaceEdkService;
                return true;
            }
        }

        public static IFaceEdkService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
