package com.zkteco.android.io;

import android.util.Log;
import com.zkteco.android.util.DebugHelper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class ShellHelper {
    private static final String REMOUNT_SYSTEM = "mount -o rw,remount /system";
    /* access modifiers changed from: private */
    public static final String TAG = "com.zkteco.android.io.ShellHelper";

    public static final class ShellCommandResult {
        public String errorOutput;
        public int exitCode;
        public String output;

        private ShellCommandResult() {
            this.exitCode = Integer.MIN_VALUE;
        }

        public boolean finishedOk() {
            return this.exitCode == 0;
        }

        public void print() {
            Log.i(ShellHelper.TAG, toString());
        }

        public void print(String str) {
            Log.i(str, toString());
        }

        public String toString() {
            return "ShellCommandResult{errorOutput='" + this.errorOutput + '\'' + ", exitCode=" + this.exitCode + ", output='" + this.output + '\'' + '}';
        }
    }

    public static List<String> execCommandAndRetrieveStringResult(String str) throws IOException {
        ArrayList arrayList = new ArrayList();
        Process exec = Runtime.getRuntime().exec("su");
        DataOutputStream dataOutputStream = new DataOutputStream(exec.getOutputStream());
        try {
            dataOutputStream.writeBytes(str + "\n");
            dataOutputStream.flush();
            exec.getOutputStream();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            exec.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null || readLine.isEmpty()) {
                    break;
                }
                arrayList.add(readLine);
                Log.d(TAG, "Command result: " + readLine);
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "Error executing command", e);
        } catch (Throwable th) {
            dataOutputStream.close();
            throw th;
        }
        dataOutputStream.close();
        return arrayList;
    }

    public static void execCommandAsSuAsync(String str) throws IOException {
        Log.d(TAG, "Executing asynchronous command: " + str);
        Runtime.getRuntime().exec(new String[]{"su", "-c", str});
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0075, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0076, code lost:
        r4 = null;
        r1 = r3;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007a, code lost:
        r8 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007e, code lost:
        r3 = null;
        r4 = null;
        r1 = r2;
        r2 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x007a A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00b3 A[Catch:{ IOException -> 0x00c8, all -> 0x00c5 }, LOOP:1: B:31:0x00a7->B:36:0x00b3, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00bb A[Catch:{ IOException -> 0x00c8, all -> 0x00c5 }, EDGE_INSN: B:70:0x00bb->B:37:0x00bb ?: BREAK  
    EDGE_INSN: B:71:0x00bb->B:37:0x00bb ?: BREAK  ] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.zkteco.android.io.ShellHelper.ShellCommandResult execCommandAsSu(java.lang.String r8) {
        /*
            java.lang.String r0 = TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Executing command: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r8)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
            com.zkteco.android.io.ShellHelper$ShellCommandResult r0 = new com.zkteco.android.io.ShellHelper$ShellCommandResult
            r1 = 0
            r0.<init>()
            r2 = 3
            java.lang.String[] r2 = new java.lang.String[r2]
            r3 = 0
            java.lang.String r4 = "su"
            r2[r3] = r4
            r3 = 1
            java.lang.String r4 = "-c"
            r2[r3] = r4
            r3 = 2
            r2[r3] = r8
            r8 = 10
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch:{ IOException -> 0x007d, all -> 0x007a }
            java.lang.Process r2 = r3.exec(r2)     // Catch:{ IOException -> 0x007d, all -> 0x007a }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0075, all -> 0x007a }
            r3.<init>()     // Catch:{ IOException -> 0x0075, all -> 0x007a }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0070, all -> 0x007a }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0070, all -> 0x007a }
            java.io.InputStream r6 = r2.getInputStream()     // Catch:{ IOException -> 0x0070, all -> 0x007a }
            r5.<init>(r6)     // Catch:{ IOException -> 0x0070, all -> 0x007a }
            r4.<init>(r5)     // Catch:{ IOException -> 0x0070, all -> 0x007a }
        L_0x004b:
            java.lang.String r1 = r4.readLine()     // Catch:{ IOException -> 0x006e }
            if (r1 == 0) goto L_0x005f
            boolean r5 = r1.isEmpty()     // Catch:{ IOException -> 0x006e }
            if (r5 != 0) goto L_0x005f
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ IOException -> 0x006e }
            r1.append(r8)     // Catch:{ IOException -> 0x006e }
            goto L_0x004b
        L_0x005f:
            r4.close()     // Catch:{ IOException -> 0x0063 }
            goto L_0x008e
        L_0x0063:
            r1 = move-exception
            java.lang.String r5 = TAG
            java.lang.String r1 = android.util.Log.getStackTraceString(r1)
            android.util.Log.w(r5, r1)
            goto L_0x008e
        L_0x006e:
            r1 = move-exception
            goto L_0x0082
        L_0x0070:
            r4 = move-exception
            r7 = r4
            r4 = r1
            r1 = r7
            goto L_0x0082
        L_0x0075:
            r3 = move-exception
            r4 = r1
            r1 = r3
            r3 = r4
            goto L_0x0082
        L_0x007a:
            r8 = move-exception
            goto L_0x010e
        L_0x007d:
            r2 = move-exception
            r3 = r1
            r4 = r3
            r1 = r2
            r2 = r4
        L_0x0082:
            java.lang.String r5 = TAG     // Catch:{ all -> 0x010c }
            java.lang.String r1 = android.util.Log.getStackTraceString(r1)     // Catch:{ all -> 0x010c }
            android.util.Log.e(r5, r1)     // Catch:{ all -> 0x010c }
            r4.close()     // Catch:{ IOException -> 0x0063 }
        L_0x008e:
            java.lang.String r1 = r3.toString()     // Catch:{ IOException -> 0x00cd }
            r0.output = r1     // Catch:{ IOException -> 0x00cd }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00cd }
            r1.<init>()     // Catch:{ IOException -> 0x00cd }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ IOException -> 0x00cd }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x00cd }
            java.io.InputStream r6 = r2.getErrorStream()     // Catch:{ IOException -> 0x00cd }
            r5.<init>(r6)     // Catch:{ IOException -> 0x00cd }
            r3.<init>(r5)     // Catch:{ IOException -> 0x00cd }
        L_0x00a7:
            java.lang.String r4 = r3.readLine()     // Catch:{ IOException -> 0x00c8, all -> 0x00c5 }
            if (r4 == 0) goto L_0x00bb
            boolean r5 = r4.isEmpty()     // Catch:{ IOException -> 0x00c8, all -> 0x00c5 }
            if (r5 != 0) goto L_0x00bb
            java.lang.StringBuilder r4 = r1.append(r4)     // Catch:{ IOException -> 0x00c8, all -> 0x00c5 }
            r4.append(r8)     // Catch:{ IOException -> 0x00c8, all -> 0x00c5 }
            goto L_0x00a7
        L_0x00bb:
            java.lang.String r8 = r1.toString()     // Catch:{ IOException -> 0x00c8, all -> 0x00c5 }
            r0.errorOutput = r8     // Catch:{ IOException -> 0x00c8, all -> 0x00c5 }
            r3.close()     // Catch:{ IOException -> 0x00db }
            goto L_0x00e5
        L_0x00c5:
            r8 = move-exception
            r4 = r3
            goto L_0x00fd
        L_0x00c8:
            r8 = move-exception
            r4 = r3
            goto L_0x00ce
        L_0x00cb:
            r8 = move-exception
            goto L_0x00fd
        L_0x00cd:
            r8 = move-exception
        L_0x00ce:
            java.lang.String r1 = TAG     // Catch:{ all -> 0x00cb }
            java.lang.String r8 = android.util.Log.getStackTraceString(r8)     // Catch:{ all -> 0x00cb }
            android.util.Log.e(r1, r8)     // Catch:{ all -> 0x00cb }
            r4.close()     // Catch:{ IOException -> 0x00db }
            goto L_0x00e5
        L_0x00db:
            r8 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r8 = android.util.Log.getStackTraceString(r8)
            android.util.Log.w(r1, r8)
        L_0x00e5:
            r2.waitFor()     // Catch:{ InterruptedException -> 0x00ef }
            int r8 = r2.exitValue()     // Catch:{ InterruptedException -> 0x00ef }
            r0.exitCode = r8     // Catch:{ InterruptedException -> 0x00ef }
            goto L_0x00f9
        L_0x00ef:
            r8 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r8 = android.util.Log.getStackTraceString(r8)
            android.util.Log.e(r1, r8)
        L_0x00f9:
            r0.print()
            return r0
        L_0x00fd:
            r4.close()     // Catch:{ IOException -> 0x0101 }
            goto L_0x010b
        L_0x0101:
            r0 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)
            android.util.Log.w(r1, r0)
        L_0x010b:
            throw r8
        L_0x010c:
            r8 = move-exception
            r1 = r4
        L_0x010e:
            r1.close()     // Catch:{ IOException -> 0x0112 }
            goto L_0x011c
        L_0x0112:
            r0 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)
            android.util.Log.w(r1, r0)
        L_0x011c:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.io.ShellHelper.execCommandAsSu(java.lang.String):com.zkteco.android.io.ShellHelper$ShellCommandResult");
    }

    public static int execCommands(String... strArr) throws IOException {
        Process exec = Runtime.getRuntime().exec("su");
        DataOutputStream dataOutputStream = new DataOutputStream(exec.getOutputStream());
        int i = 0;
        while (i < strArr.length) {
            try {
                dataOutputStream.writeBytes(strArr[i] + "\n");
                dataOutputStream.flush();
                i++;
            } catch (InterruptedException unused) {
                return 1;
            } finally {
                dataOutputStream.close();
            }
        }
        dataOutputStream.writeBytes("exit\n");
        dataOutputStream.flush();
        exec.waitFor();
        int exitValue = exec.exitValue();
        Log.d(TAG, "process result is " + exitValue);
        return exitValue;
    }

    public static void reboot() {
        DebugHelper.logStackTrace(TAG, "reboot");
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot"});
            process.waitFor();
            if (process == null) {
                return;
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not reboot", e);
            if (process == null) {
                return;
            }
        } catch (Throwable th) {
            if (process != null) {
                process.destroy();
            }
            throw th;
        }
        process.destroy();
    }

    public static void powerOff() {
        DebugHelper.logStackTrace(TAG, "reboot");
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
            process.waitFor();
            if (process == null) {
                return;
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not reboot", e);
            if (process == null) {
                return;
            }
        } catch (Throwable th) {
            if (process != null) {
                process.destroy();
            }
            throw th;
        }
        process.destroy();
    }

    public static void remount() {
        execCommandAsSu(REMOUNT_SYSTEM);
    }

    private ShellHelper() {
    }
}
