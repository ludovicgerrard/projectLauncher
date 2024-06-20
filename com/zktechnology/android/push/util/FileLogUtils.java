package com.zktechnology.android.push.util;

import android.os.Handler;
import android.os.HandlerThread;
import com.zktechnology.android.utils.ZkLogFile;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileLogUtils {
    private static final Handler HANDLER;
    private static final HandlerThread HANDLER_THREAD;
    private static final String THREAD_NAME = "file_log_write_thread";

    static {
        HandlerThread handlerThread = new HandlerThread(THREAD_NAME);
        HANDLER_THREAD = handlerThread;
        handlerThread.start();
        HANDLER = new Handler(handlerThread.getLooper());
    }

    public static void writeNetworkLog(String str) {
        writeLog(str, "Network.txt", 1048576);
    }

    public static void writeCameraLog(String str) {
        writeLog(str, "Camera.txt", 1048576);
    }

    public static void writeWiegandLog(String str) {
        writeLog(str, "Wiegand.txt", 3145728);
    }

    public static void writeRebootLog(String str) {
        writeLog(str, "Reboot.txt", 3145728);
    }

    public static void writeStateLog(String str) {
        writeLog(str, "Verify.txt", 52428800);
    }

    public static void writeClearPhotoLog(String str) {
        writeLog(str, "ClearPhoto.txt", 52428800);
    }

    public static void writeVerifyLog(String str) {
        writeLog(str, "Verify.txt", 52428800);
    }

    public static void writeVerifySuccessLog(String str) {
        writeLog(str, "VerifySuccess.txt", 52428800);
    }

    public static void writeDatabaseLog(String str) {
        writeLog(str, "Database.txt", 52428800);
    }

    public static void writeVerifyExceptionLog(String str) {
        writeLog(str, "VerifyException.txt");
    }

    public static void writeLauncherInitRecord(String str) {
        writeLog(str, "LauncherInit.txt");
    }

    public static void writeTouchLog(String str) {
        writeLog(str, "Touch.txt");
    }

    public static void writeTemplateLog(String str) {
        writeLog(str, ZkLogFile.TEMPLATE_LOG);
    }

    public static void writeExtractLog(String str) {
        writeLog(str, "Extract.txt", 52428800);
    }

    private static void writeLog(String str, String str2, long j) {
        HANDLER.post(new Runnable(str2, j, str) {
            public final /* synthetic */ String f$0;
            public final /* synthetic */ long f$1;
            public final /* synthetic */ String f$2;

            {
                this.f$0 = r1;
                this.f$1 = r2;
                this.f$2 = r4;
            }

            public final void run() {
                FileLogUtils.lambda$writeLog$0(this.f$0, this.f$1, this.f$2);
            }
        });
    }

    static /* synthetic */ void lambda$writeLog$0(String str, long j, String str2) {
        File file = new File(ZkLogFile.TRACE_PATH + str);
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() > j) {
                file.delete();
                file.createNewFile();
            }
            methodBufferedWriter(file, (str2 + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.US).format(new Date()) + "    ") + "    \r\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeLog(String str, String str2) {
        writeLog(str, str2, 3145728);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0026 A[SYNTHETIC, Splitter:B:16:0x0026] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0031 A[SYNTHETIC, Splitter:B:21:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void methodBufferedWriter(java.io.File r5, java.lang.String r6) {
        /*
            r0 = 0
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x0020 }
            java.io.OutputStreamWriter r2 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x0020 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0020 }
            r4 = 1
            r3.<init>(r5, r4)     // Catch:{ Exception -> 0x0020 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0020 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0020 }
            r1.write(r6)     // Catch:{ Exception -> 0x001b, all -> 0x0018 }
            r1.close()     // Catch:{ IOException -> 0x002a }
            goto L_0x002e
        L_0x0018:
            r5 = move-exception
            r0 = r1
            goto L_0x002f
        L_0x001b:
            r5 = move-exception
            r0 = r1
            goto L_0x0021
        L_0x001e:
            r5 = move-exception
            goto L_0x002f
        L_0x0020:
            r5 = move-exception
        L_0x0021:
            r5.printStackTrace()     // Catch:{ all -> 0x001e }
            if (r0 == 0) goto L_0x002e
            r0.close()     // Catch:{ IOException -> 0x002a }
            goto L_0x002e
        L_0x002a:
            r5 = move-exception
            r5.printStackTrace()
        L_0x002e:
            return
        L_0x002f:
            if (r0 == 0) goto L_0x0039
            r0.close()     // Catch:{ IOException -> 0x0035 }
            goto L_0x0039
        L_0x0035:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0039:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.push.util.FileLogUtils.methodBufferedWriter(java.io.File, java.lang.String):void");
    }
}
