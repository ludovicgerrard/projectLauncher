package com.zktechnology.android.rs485;

import android.content.Context;
import com.zktechnology.android.cmd.EncryptProtocol;
import com.zktechnology.android.helper.McuServiceHelper;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class RS485Helper {
    private static final String TAG = "RS485Helper";
    private static byte cmdGetData = -105;
    private static byte cmdRet = -104;
    private static RS485Helper mInstance;
    private Disposable mDisposable;

    public RS485Helper(Context context) {
    }

    public static RS485Helper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (RS485Helper.class) {
                mInstance = new RS485Helper(context.getApplicationContext());
            }
        }
        return mInstance;
    }

    public void setBaudRate(int i) {
        if (i != 0) {
            McuServiceHelper.getInstance().setRS485Properties(i, 8, 1, 0);
        }
    }

    public void startGetRS485() {
        this.mDisposable = Flowable.interval(1, TimeUnit.SECONDS).observeOn(Schedulers.computation()).subscribe(new Consumer() {
            public final void accept(Object obj) {
                RS485Helper.this.lambda$startGetRS485$0$RS485Helper((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$startGetRS485$0$RS485Helper(Long l) throws Exception {
        McuServiceHelper.getInstance().sentRS485Data(buildCmd151());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007b, code lost:
        if (r9 != false) goto L_0x007f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String cardNumber(byte[] r7, int r8, int r9) {
        /*
            java.lang.String r0 = "convert hex:"
        L_0x0002:
            byte r1 = r7[r8]
            if (r1 != 0) goto L_0x000d
            if (r9 <= 0) goto L_0x000d
            int r8 = r8 + 1
            int r9 = r9 + -1
            goto L_0x0002
        L_0x000d:
            if (r9 != 0) goto L_0x0012
            java.lang.String r7 = "0"
            return r7
        L_0x0012:
            byte[] r1 = new byte[r9]
            r2 = 0
            java.lang.System.arraycopy(r7, r8, r1, r2, r9)
            java.lang.String r7 = com.zktechnology.android.utils.HexUtils.bytes2HexString(r1)
            r8 = 16
            r3 = 0
            long r1 = java.lang.Long.parseLong(r7, r8)     // Catch:{ Exception -> 0x0026 }
            r9 = 1
            goto L_0x004d
        L_0x0026:
            r9 = move-exception
            java.lang.String r1 = TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r6 = " wrong using parseLong, error="
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r9 = r9.getMessage()
            java.lang.StringBuilder r9 = r5.append(r9)
            java.lang.String r9 = r9.toString()
            android.util.Log.e(r1, r9)
            r9 = r2
            r1 = r3
        L_0x004d:
            if (r9 != 0) goto L_0x007f
            long r3 = parseUnsignedLong(r7, r8)     // Catch:{ Exception -> 0x0056 }
            goto L_0x0080
        L_0x0054:
            r7 = move-exception
            goto L_0x007e
        L_0x0056:
            r8 = move-exception
            java.lang.String r5 = TAG     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0054 }
            r6.<init>()     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r0 = r6.append(r0)     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r7 = r0.append(r7)     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = " wrong using parseUnsignedLong, error="
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x0054 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0054 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0054 }
            android.util.Log.e(r5, r7)     // Catch:{ all -> 0x0054 }
            if (r9 != 0) goto L_0x007f
            goto L_0x0080
        L_0x007e:
            throw r7
        L_0x007f:
            r3 = r1
        L_0x0080:
            java.lang.String r7 = java.lang.String.valueOf(r3)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.rs485.RS485Helper.cardNumber(byte[], int, int):java.lang.String");
    }

    public static long parseUnsignedLong(String str, int i) {
        if (str != null) {
            int length = str.length();
            if (length <= 0) {
                throw new NumberFormatException("For input string: \"" + str + "\"");
            } else if (str.charAt(0) == '-') {
                throw new NumberFormatException(String.format("Illegal leading minus sign on unsigned string %s.", new Object[]{str}));
            } else if (length <= 12 || (i == 10 && length <= 18)) {
                return Long.parseLong(str, i);
            } else {
                int i2 = length - 1;
                long parseLong = Long.parseLong(str.substring(0, i2), i);
                int digit = Character.digit(str.charAt(i2), i);
                if (digit >= 0) {
                    long j = (((long) i) * parseLong) + ((long) digit);
                    if (compareUnsigned(j, parseLong) >= 0) {
                        return j;
                    }
                    throw new NumberFormatException(String.format("String value %s exceeds range of unsigned long.", new Object[]{str}));
                }
                throw new NumberFormatException("Bad digit at end of " + str);
            }
        } else {
            throw new NumberFormatException("null");
        }
    }

    public static int compareUnsigned(long j, long j2) {
        return Long.compare(j - Long.MIN_VALUE, j2 - Long.MIN_VALUE);
    }

    public void closeRS485() {
        Disposable disposable = this.mDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString + " ");
        }
        return sb.toString();
    }

    public byte[] buildCmd151() {
        return EncryptProtocol.buildNormalMessage((byte) -86, (byte) 1, cmdGetData, new byte[]{0}, (byte) 85);
    }

    public byte[] buildCmd152(byte b) {
        return EncryptProtocol.buildNormalMessage((byte) -86, (byte) 1, cmdRet, new byte[]{b}, (byte) 85);
    }
}
