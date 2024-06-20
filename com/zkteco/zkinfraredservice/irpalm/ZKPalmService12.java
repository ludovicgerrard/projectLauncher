package com.zkteco.zkinfraredservice.irpalm;

import android.content.Context;
import com.zkteco.zkpalm.ZKPalmVein;

public class ZKPalmService12 {
    public static final int ERR_BAD_IMAGE = -11003;
    public static final int ERR_INVALID_PARAM = -11001;
    public static final int ERR_NOT_INITIALIZE = -11002;
    public static final int ERR_NO_LICENSE = -11007;
    public static final int ERR_SUCCESS = 0;
    public static final int ERR_UKEY_NOT_USBKEY = -11004;
    public static final int ERR_UKEY_NO_PERMISSION = -11005;
    public static final int ERR_UKEY_OPEN_FAIL = -11006;
    public static final int EXTRACT_TYPE_PRE_REGISTER = 0;
    public static final int EXTRACT_TYPE_VERIFY = 1;
    public static final int FIX_PREREG_TEMPLATE_LEN = 98448;
    public static final int FIX_REG_TEMPLATE_LEN = 8844;
    public static final int FIX_VER_TEMPLATE_LEN = 26448;
    public static final int LIC_TYPE_ETH0 = 1;
    private static final int LIC_TYPE_UKEY = 0;
    public static final int LIC_TYPE_WLAN0 = 2;
    private static final int PID = 1536;
    private static final int VID = 6997;
    private static byte[] handle = new byte[1];
    private static boolean haveOpenUKey = false;
    private static boolean isInitialize = false;
    private static int[] lastestRoi = new int[9];
    private static Context mContext = null;
    private static int mLicType = 0;
    private static Object objLock = new Object();
    private static byte[] preRegTemplate = new byte[FIX_PREREG_TEMPLATE_LEN];
    private static int[] quality = new int[1];
    private static byte[] verTemplate = new byte[FIX_VER_TEMPLATE_LEN];
    private static final String version = "12.7.2\t20200909";
    public static ZKPalmUKeyConnection zkuKeyConnection = new ZKPalmUKeyConnection();

    public static int getUKeyProductID() {
        return PID;
    }

    public static int getUKeyVendorID() {
        return VID;
    }

    private static String byteToStr(byte[] bArr) {
        int i = 0;
        while (true) {
            try {
                if (i >= bArr.length) {
                    i = 0;
                    break;
                } else if (bArr[i] == 0) {
                    break;
                } else {
                    i++;
                }
            } catch (Exception unused) {
                return "";
            }
        }
        return new String(bArr, 0, i);
    }

    public static int setLicenseData(int i, byte[] bArr) {
        if ((i != 1 && i != 2) || bArr == null) {
            return ERR_INVALID_PARAM;
        }
        int ZKGetLicense = ZKPalmVein.getInstance().ZKGetLicense((Object) null, i, bArr);
        if (ZKGetLicense == 0) {
            mLicType = i;
        }
        return ZKGetLicense;
    }

    private static int initUKeyLicense() {
        if (!zkuKeyConnection.openDevice(mContext, VID, PID, 0)) {
            return ERR_UKEY_OPEN_FAIL;
        }
        haveOpenUKey = true;
        return ZKPalmVein.getInstance().ZKGetLicense(zkuKeyConnection, 0, (byte[]) null);
    }

    private static void freeUKey() {
        if (haveOpenUKey) {
            zkuKeyConnection.closeDevice();
            haveOpenUKey = false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x002c, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int init(android.content.Context r2, int r3, int r4) {
        /*
            java.lang.Object r0 = objLock
            monitor-enter(r0)
            if (r2 != 0) goto L_0x0009
            r2 = -11001(0xffffffffffffd507, float:NaN)
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            return r2
        L_0x0009:
            mContext = r2     // Catch:{ all -> 0x002d }
            boolean r2 = isInitialize     // Catch:{ all -> 0x002d }
            r1 = 0
            if (r2 == 0) goto L_0x0012
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            return r1
        L_0x0012:
            int r2 = mLicType     // Catch:{ all -> 0x002d }
            if (r2 != 0) goto L_0x001e
            int r2 = initUKeyLicense()     // Catch:{ all -> 0x002d }
            if (r2 == 0) goto L_0x001e
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            return r2
        L_0x001e:
            com.zkteco.zkpalm.ZKPalmVein r2 = com.zkteco.zkpalm.ZKPalmVein.getInstance()     // Catch:{ all -> 0x002d }
            int r2 = r2.ZKPalmVeinInit(r3, r4, r1)     // Catch:{ all -> 0x002d }
            if (r2 != 0) goto L_0x002b
            r3 = 1
            isInitialize = r3     // Catch:{ all -> 0x002d }
        L_0x002b:
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            return r2
        L_0x002d:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.zkinfraredservice.irpalm.ZKPalmService12.init(android.content.Context, int, int):int");
    }

    public static void free() {
        synchronized (objLock) {
            if (isInitialize) {
                ZKPalmVein.getInstance().ZKPalmVeinFinal(handle);
                if (mLicType == 0) {
                    freeUKey();
                }
                isInitialize = false;
            }
        }
    }

    public static int getTemplateSize(byte b) {
        synchronized (objLock) {
            if (!isInitialize) {
                return ERR_NOT_INITIALIZE;
            }
            int ZKPalmVeinGetTemplateSize = ZKPalmVein.getInstance().ZKPalmVeinGetTemplateSize(handle, b);
            return ZKPalmVeinGetTemplateSize;
        }
    }

    public static int setParam(int i, int i2) {
        synchronized (objLock) {
            if (!isInitialize) {
                return ERR_NOT_INITIALIZE;
            }
            int ZKPalmVeinSetParam = ZKPalmVein.getInstance().ZKPalmVeinSetParam(handle, i, i2);
            return ZKPalmVeinSetParam;
        }
    }

    public static int getParam(int i) {
        synchronized (objLock) {
            if (!isInitialize) {
                return ERR_NOT_INITIALIZE;
            }
            int ZKPalmVeinGetParam = ZKPalmVein.getInstance().ZKPalmVeinGetParam(handle, i);
            return ZKPalmVeinGetParam;
        }
    }

    public static ZKPalmExtractResult extract(byte[] bArr) {
        return extract(0, bArr);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x009d, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.zkteco.zkinfraredservice.irpalm.ZKPalmExtractResult extract(int r12, byte[] r13) {
        /*
            java.lang.Object r0 = objLock
            monitor-enter(r0)
            com.zkteco.zkinfraredservice.irpalm.ZKPalmExtractResult r1 = new com.zkteco.zkinfraredservice.irpalm.ZKPalmExtractResult     // Catch:{ all -> 0x00a4 }
            r1.<init>()     // Catch:{ all -> 0x00a4 }
            if (r13 == 0) goto L_0x009e
            r2 = 1
            if (r12 == 0) goto L_0x0011
            if (r12 == r2) goto L_0x0011
            goto L_0x009e
        L_0x0011:
            boolean r3 = isInitialize     // Catch:{ all -> 0x00a4 }
            if (r3 != 0) goto L_0x001b
            r12 = -11002(0xffffffffffffd506, float:NaN)
            r1.result = r12     // Catch:{ all -> 0x00a4 }
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            return r1
        L_0x001b:
            int[] r3 = lastestRoi     // Catch:{ all -> 0x00a4 }
            r4 = 0
            java.util.Arrays.fill(r3, r4)     // Catch:{ all -> 0x00a4 }
            int[] r3 = quality     // Catch:{ all -> 0x00a4 }
            java.util.Arrays.fill(r3, r4)     // Catch:{ all -> 0x00a4 }
            byte[] r3 = preRegTemplate     // Catch:{ all -> 0x00a4 }
            java.util.Arrays.fill(r3, r4)     // Catch:{ all -> 0x00a4 }
            byte[] r3 = verTemplate     // Catch:{ all -> 0x00a4 }
            java.util.Arrays.fill(r3, r4)     // Catch:{ all -> 0x00a4 }
            if (r12 != 0) goto L_0x0046
            com.zkteco.zkpalm.ZKPalmVein r5 = com.zkteco.zkpalm.ZKPalmVein.getInstance()     // Catch:{ all -> 0x00a4 }
            byte[] r6 = handle     // Catch:{ all -> 0x00a4 }
            int[] r8 = lastestRoi     // Catch:{ all -> 0x00a4 }
            byte[] r9 = preRegTemplate     // Catch:{ all -> 0x00a4 }
            byte[] r10 = verTemplate     // Catch:{ all -> 0x00a4 }
            int[] r11 = quality     // Catch:{ all -> 0x00a4 }
            r7 = r13
            int r13 = r5.ZKPalmVeinExtractRawEnrollFeature(r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x00a4 }
            goto L_0x0054
        L_0x0046:
            com.zkteco.zkpalm.ZKPalmVein r3 = com.zkteco.zkpalm.ZKPalmVein.getInstance()     // Catch:{ all -> 0x00a4 }
            byte[] r5 = handle     // Catch:{ all -> 0x00a4 }
            int[] r6 = lastestRoi     // Catch:{ all -> 0x00a4 }
            byte[] r7 = verTemplate     // Catch:{ all -> 0x00a4 }
            int r13 = r3.ZKPalmVeinExtract(r5, r13, r6, r7)     // Catch:{ all -> 0x00a4 }
        L_0x0054:
            if (r13 == 0) goto L_0x005a
            r1.result = r13     // Catch:{ all -> 0x00a4 }
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            return r1
        L_0x005a:
            r1.result = r13     // Catch:{ all -> 0x00a4 }
            int[] r13 = lastestRoi     // Catch:{ all -> 0x00a4 }
            r13 = r13[r4]     // Catch:{ all -> 0x00a4 }
            r1.imageQuality = r13     // Catch:{ all -> 0x00a4 }
            r13 = 8
            int[] r3 = new int[r13]     // Catch:{ all -> 0x00a4 }
            r1.rect = r3     // Catch:{ all -> 0x00a4 }
            int[] r3 = lastestRoi     // Catch:{ all -> 0x00a4 }
            int[] r5 = r1.rect     // Catch:{ all -> 0x00a4 }
            java.lang.System.arraycopy(r3, r2, r5, r4, r13)     // Catch:{ all -> 0x00a4 }
            r13 = 26448(0x6750, float:3.7062E-41)
            byte[] r2 = new byte[r13]     // Catch:{ all -> 0x00a4 }
            r1.verTemplate = r2     // Catch:{ all -> 0x00a4 }
            byte[] r2 = verTemplate     // Catch:{ all -> 0x00a4 }
            byte[] r3 = r1.verTemplate     // Catch:{ all -> 0x00a4 }
            java.lang.System.arraycopy(r2, r4, r3, r4, r13)     // Catch:{ all -> 0x00a4 }
            if (r12 == 0) goto L_0x0088
            int[] r12 = lastestRoi     // Catch:{ all -> 0x00a4 }
            r12 = r12[r4]     // Catch:{ all -> 0x00a4 }
            r1.templateQuality = r12     // Catch:{ all -> 0x00a4 }
            r12 = 0
            r1.preRegTemplate = r12     // Catch:{ all -> 0x00a4 }
            goto L_0x009c
        L_0x0088:
            int[] r12 = quality     // Catch:{ all -> 0x00a4 }
            r12 = r12[r4]     // Catch:{ all -> 0x00a4 }
            r1.templateQuality = r12     // Catch:{ all -> 0x00a4 }
            r12 = 98448(0x18090, float:1.37955E-40)
            byte[] r13 = new byte[r12]     // Catch:{ all -> 0x00a4 }
            r1.preRegTemplate = r13     // Catch:{ all -> 0x00a4 }
            byte[] r13 = preRegTemplate     // Catch:{ all -> 0x00a4 }
            byte[] r2 = r1.preRegTemplate     // Catch:{ all -> 0x00a4 }
            java.lang.System.arraycopy(r13, r4, r2, r4, r12)     // Catch:{ all -> 0x00a4 }
        L_0x009c:
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            return r1
        L_0x009e:
            r12 = -11001(0xffffffffffffd507, float:NaN)
            r1.result = r12     // Catch:{ all -> 0x00a4 }
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            return r1
        L_0x00a4:
            r12 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.zkinfraredservice.irpalm.ZKPalmService12.extract(int, byte[]):com.zkteco.zkinfraredservice.irpalm.ZKPalmExtractResult");
    }

    public static int verify(byte[] bArr, byte[] bArr2) {
        synchronized (objLock) {
            if (bArr != null) {
                if (bArr.length >= 8844 && bArr2 != null) {
                    if (bArr2.length >= 26448) {
                        if (!isInitialize) {
                            return ERR_NOT_INITIALIZE;
                        }
                        int ZKPalmVeinVerify = ZKPalmVein.getInstance().ZKPalmVeinVerify(handle, bArr, bArr2);
                        return ZKPalmVeinVerify;
                    }
                }
            }
            return ERR_INVALID_PARAM;
        }
    }

    public static int mergeRegTemplate(byte[] bArr, int i, byte[] bArr2) {
        if (bArr == null || bArr2 == null || bArr.length < FIX_PREREG_TEMPLATE_LEN * i || i != 5 || bArr2.length < 8844) {
            return ERR_INVALID_PARAM;
        }
        synchronized (objLock) {
            if (!isInitialize) {
                return ERR_NOT_INITIALIZE;
            }
            int ZKPalmVeinEnroll = ZKPalmVein.getInstance().ZKPalmVeinEnroll(handle, bArr, bArr2);
            return ZKPalmVeinEnroll;
        }
    }

    public static int dbAdd(String str, byte[] bArr) {
        if (str == null || bArr == null || bArr.length < 8844) {
            return ERR_INVALID_PARAM;
        }
        synchronized (objLock) {
            if (!isInitialize) {
                return ERR_NOT_INITIALIZE;
            }
            int ZKPalmVeinDBSet = ZKPalmVein.getInstance().ZKPalmVeinDBSet(handle, str, bArr, 1);
            return ZKPalmVeinDBSet;
        }
    }

    public static int dbDel(String str) {
        if (str == null) {
            return ERR_INVALID_PARAM;
        }
        synchronized (objLock) {
            if (!isInitialize) {
                return ERR_NOT_INITIALIZE;
            }
            int ZKPalmVeinDBDel = ZKPalmVein.getInstance().ZKPalmVeinDBDel(handle, str);
            return ZKPalmVeinDBDel;
        }
    }

    public static int dbCount() {
        synchronized (objLock) {
            if (!isInitialize) {
                return ERR_NOT_INITIALIZE;
            }
            int ZKPalmVeinDBCount = ZKPalmVein.getInstance().ZKPalmVeinDBCount(handle);
            return ZKPalmVeinDBCount;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0031, code lost:
        return r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int dbIdentify(byte[] r8, java.lang.String[] r9) {
        /*
            if (r8 == 0) goto L_0x0035
            if (r9 == 0) goto L_0x0035
            int r0 = r8.length
            r1 = 26448(0x6750, float:3.7062E-41)
            if (r0 >= r1) goto L_0x000a
            goto L_0x0035
        L_0x000a:
            java.lang.Object r0 = objLock
            monitor-enter(r0)
            boolean r1 = isInitialize     // Catch:{ all -> 0x0032 }
            if (r1 != 0) goto L_0x0015
            r8 = -11002(0xffffffffffffd506, float:NaN)
            monitor-exit(r0)     // Catch:{ all -> 0x0032 }
            return r8
        L_0x0015:
            r1 = 256(0x100, float:3.59E-43)
            byte[] r1 = new byte[r1]     // Catch:{ all -> 0x0032 }
            com.zkteco.zkpalm.ZKPalmVein r2 = com.zkteco.zkpalm.ZKPalmVein.getInstance()     // Catch:{ all -> 0x0032 }
            byte[] r3 = handle     // Catch:{ all -> 0x0032 }
            r6 = 0
            r7 = 0
            r4 = r8
            r5 = r1
            int r8 = r2.ZKPalmVeinDBIdentify(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0032 }
            if (r8 <= 0) goto L_0x0030
            r2 = 0
            java.lang.String r1 = byteToStr(r1)     // Catch:{ all -> 0x0032 }
            r9[r2] = r1     // Catch:{ all -> 0x0032 }
        L_0x0030:
            monitor-exit(r0)     // Catch:{ all -> 0x0032 }
            return r8
        L_0x0032:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0032 }
            throw r8
        L_0x0035:
            r8 = -11001(0xffffffffffffd507, float:NaN)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.zkinfraredservice.irpalm.ZKPalmService12.dbIdentify(byte[], java.lang.String[]):int");
    }

    public static int dbClear() {
        synchronized (objLock) {
            if (!isInitialize) {
                return ERR_NOT_INITIALIZE;
            }
            int ZKPalmVeinDBReset = ZKPalmVein.getInstance().ZKPalmVeinDBReset(handle);
            return ZKPalmVeinDBReset;
        }
    }

    public static int dbVerify(byte[] bArr, String str) {
        if (bArr == null || str == null || bArr.length < 26448) {
            return ERR_INVALID_PARAM;
        }
        synchronized (objLock) {
            if (!isInitialize) {
                return ERR_NOT_INITIALIZE;
            }
            int ZKPalmVeinDBVerify = ZKPalmVein.getInstance().ZKPalmVeinDBVerify(handle, bArr, str);
            return ZKPalmVeinDBVerify;
        }
    }
}
