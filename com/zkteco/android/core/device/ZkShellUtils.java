package com.zkteco.android.core.device;

import java.util.List;

public class ZkShellUtils {
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_SU = "su";
    public static final int RESULT_OK = 0;

    private ZkShellUtils() {
        throw new AssertionError();
    }

    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    public static CommandResult execCommand(String str, boolean z) {
        return execCommand(new String[]{str}, z, true);
    }

    public static CommandResult execCommand(List<String> list, boolean z) {
        return execCommand(list == null ? null : (String[]) list.toArray(new String[0]), z, true);
    }

    public static CommandResult execCommand(String[] strArr, boolean z) {
        return execCommand(strArr, z, true);
    }

    public static CommandResult execCommand(String str, boolean z, boolean z2) {
        return execCommand(new String[]{str}, z, z2);
    }

    public static CommandResult execCommand(List<String> list, boolean z, boolean z2) {
        return execCommand(list == null ? null : (String[]) list.toArray(new String[0]), z, z2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.io.DataOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: java.lang.StringBuilder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: java.lang.StringBuilder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.io.DataOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: java.io.DataOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v18, resolved type: java.lang.StringBuilder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v19, resolved type: java.lang.StringBuilder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v24, resolved type: java.lang.StringBuilder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v36, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v40, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v25, resolved type: java.lang.StringBuilder} */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0136, code lost:
        if (r10 != null) goto L_0x015c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x015a, code lost:
        if (r10 != null) goto L_0x015c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x015c, code lost:
        r10.destroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x015f, code lost:
        r8 = r9;
        r10 = r1;
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00b9, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ba, code lost:
        r4 = null;
        r5 = null;
        r3 = r2;
        r2 = r1;
        r1 = null;
        r7 = r9;
        r9 = r8;
        r8 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00c5, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00c6, code lost:
        r4 = null;
        r5 = null;
        r3 = r2;
        r2 = r1;
        r1 = null;
        r7 = r9;
        r9 = r8;
        r8 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00ee, code lost:
        r8 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00ef, code lost:
        r3 = null;
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00f4, code lost:
        r8 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00f5, code lost:
        r10 = r9;
        r9 = null;
        r4 = null;
        r5 = null;
        r3 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x00fb, code lost:
        r8 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x00fc, code lost:
        r10 = r9;
        r9 = null;
        r4 = null;
        r5 = null;
        r3 = r2;
     */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x012a A[Catch:{ IOException -> 0x0126 }] */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x012f A[Catch:{ IOException -> 0x0126 }] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0146 A[SYNTHETIC, Splitter:B:112:0x0146] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x014e A[Catch:{ IOException -> 0x014a }] */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0153 A[Catch:{ IOException -> 0x014a }] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x0166  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x017e A[SYNTHETIC, Splitter:B:135:0x017e] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x0186 A[Catch:{ IOException -> 0x0182 }] */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x018b A[Catch:{ IOException -> 0x0182 }] */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x0194  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00da A[Catch:{ IOException -> 0x00e3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00df A[Catch:{ IOException -> 0x00e3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00ee A[ExcHandler: all (th java.lang.Throwable), Splitter:B:12:0x0021] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0122 A[SYNTHETIC, Splitter:B:95:0x0122] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:109:0x0141=Splitter:B:109:0x0141, B:92:0x011d=Splitter:B:92:0x011d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.zkteco.android.core.device.ZkShellUtils.CommandResult execCommand(java.lang.String[] r8, boolean r9, boolean r10) {
        /*
            r0 = 0
            r1 = -1
            if (r8 == 0) goto L_0x0198
            int r2 = r8.length
            if (r2 != 0) goto L_0x0009
            goto L_0x0198
        L_0x0009:
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ IOException -> 0x0139, Exception -> 0x0115, all -> 0x010f }
            if (r9 == 0) goto L_0x0012
            java.lang.String r9 = "su"
            goto L_0x0014
        L_0x0012:
            java.lang.String r9 = "sh"
        L_0x0014:
            java.lang.Process r9 = r2.exec(r9)     // Catch:{ IOException -> 0x0139, Exception -> 0x0115, all -> 0x010f }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ IOException -> 0x010a, Exception -> 0x0105, all -> 0x0102 }
            java.io.OutputStream r3 = r9.getOutputStream()     // Catch:{ IOException -> 0x010a, Exception -> 0x0105, all -> 0x0102 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x010a, Exception -> 0x0105, all -> 0x0102 }
            int r3 = r8.length     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
            r4 = 0
        L_0x0023:
            if (r4 >= r3) goto L_0x003c
            r5 = r8[r4]     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
            if (r5 != 0) goto L_0x002a
            goto L_0x0039
        L_0x002a:
            byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
            r2.write(r5)     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
            java.lang.String r5 = "\n"
            r2.writeBytes(r5)     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
            r2.flush()     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
        L_0x0039:
            int r4 = r4 + 1
            goto L_0x0023
        L_0x003c:
            java.lang.String r8 = "exit\n"
            r2.writeBytes(r8)     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
            r2.flush()     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
            int r1 = r9.waitFor()     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
            if (r10 == 0) goto L_0x00d1
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
            r8.<init>()     // Catch:{ IOException -> 0x00fb, Exception -> 0x00f4, all -> 0x00ee }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c5, Exception -> 0x00b9, all -> 0x00ee }
            r10.<init>()     // Catch:{ IOException -> 0x00c5, Exception -> 0x00b9, all -> 0x00ee }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ IOException -> 0x00af, Exception -> 0x00a5, all -> 0x00ee }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x00af, Exception -> 0x00a5, all -> 0x00ee }
            java.io.InputStream r5 = r9.getInputStream()     // Catch:{ IOException -> 0x00af, Exception -> 0x00a5, all -> 0x00ee }
            r4.<init>(r5)     // Catch:{ IOException -> 0x00af, Exception -> 0x00a5, all -> 0x00ee }
            r3.<init>(r4)     // Catch:{ IOException -> 0x00af, Exception -> 0x00a5, all -> 0x00ee }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x009e, Exception -> 0x0097, all -> 0x0093 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x009e, Exception -> 0x0097, all -> 0x0093 }
            java.io.InputStream r6 = r9.getErrorStream()     // Catch:{ IOException -> 0x009e, Exception -> 0x0097, all -> 0x0093 }
            r5.<init>(r6)     // Catch:{ IOException -> 0x009e, Exception -> 0x0097, all -> 0x0093 }
            r4.<init>(r5)     // Catch:{ IOException -> 0x009e, Exception -> 0x0097, all -> 0x0093 }
        L_0x0070:
            java.lang.String r5 = r3.readLine()     // Catch:{ IOException -> 0x008d, Exception -> 0x0087, all -> 0x0084 }
            if (r5 == 0) goto L_0x007a
            r8.append(r5)     // Catch:{ IOException -> 0x008d, Exception -> 0x0087, all -> 0x0084 }
            goto L_0x0070
        L_0x007a:
            java.lang.String r5 = r4.readLine()     // Catch:{ IOException -> 0x008d, Exception -> 0x0087, all -> 0x0084 }
            if (r5 == 0) goto L_0x00d5
            r10.append(r5)     // Catch:{ IOException -> 0x008d, Exception -> 0x0087, all -> 0x0084 }
            goto L_0x007a
        L_0x0084:
            r8 = move-exception
            goto L_0x00f1
        L_0x0087:
            r5 = move-exception
            r7 = r9
            r9 = r8
            r8 = r5
            r5 = r4
            goto L_0x009c
        L_0x008d:
            r5 = move-exception
            r7 = r9
            r9 = r8
            r8 = r5
            r5 = r4
            goto L_0x00a3
        L_0x0093:
            r8 = move-exception
            r4 = r0
            goto L_0x00f1
        L_0x0097:
            r4 = move-exception
            r5 = r0
            r7 = r9
            r9 = r8
            r8 = r4
        L_0x009c:
            r4 = r3
            goto L_0x00ab
        L_0x009e:
            r4 = move-exception
            r5 = r0
            r7 = r9
            r9 = r8
            r8 = r4
        L_0x00a3:
            r4 = r3
            goto L_0x00b5
        L_0x00a5:
            r3 = move-exception
            r4 = r0
            r5 = r4
            r7 = r9
            r9 = r8
            r8 = r3
        L_0x00ab:
            r3 = r2
            r2 = r1
            r1 = r10
            goto L_0x00c2
        L_0x00af:
            r3 = move-exception
            r4 = r0
            r5 = r4
            r7 = r9
            r9 = r8
            r8 = r3
        L_0x00b5:
            r3 = r2
            r2 = r1
            r1 = r10
            goto L_0x00ce
        L_0x00b9:
            r10 = move-exception
            r4 = r0
            r5 = r4
            r3 = r2
            r2 = r1
            r1 = r5
            r7 = r9
            r9 = r8
            r8 = r10
        L_0x00c2:
            r10 = r7
            goto L_0x011d
        L_0x00c5:
            r10 = move-exception
            r4 = r0
            r5 = r4
            r3 = r2
            r2 = r1
            r1 = r5
            r7 = r9
            r9 = r8
            r8 = r10
        L_0x00ce:
            r10 = r7
            goto L_0x0141
        L_0x00d1:
            r8 = r0
            r10 = r8
            r3 = r10
            r4 = r3
        L_0x00d5:
            r2.close()     // Catch:{ IOException -> 0x00e3 }
            if (r3 == 0) goto L_0x00dd
            r3.close()     // Catch:{ IOException -> 0x00e3 }
        L_0x00dd:
            if (r4 == 0) goto L_0x00e7
            r4.close()     // Catch:{ IOException -> 0x00e3 }
            goto L_0x00e7
        L_0x00e3:
            r2 = move-exception
            r2.printStackTrace()
        L_0x00e7:
            if (r9 == 0) goto L_0x0162
            r9.destroy()
            goto L_0x0162
        L_0x00ee:
            r8 = move-exception
            r3 = r0
            r4 = r3
        L_0x00f1:
            r0 = r2
            goto L_0x017c
        L_0x00f4:
            r8 = move-exception
            r10 = r9
            r9 = r0
            r4 = r9
            r5 = r4
            r3 = r2
            goto L_0x011b
        L_0x00fb:
            r8 = move-exception
            r10 = r9
            r9 = r0
            r4 = r9
            r5 = r4
            r3 = r2
            goto L_0x013f
        L_0x0102:
            r8 = move-exception
            r3 = r0
            goto L_0x0112
        L_0x0105:
            r8 = move-exception
            r10 = r9
            r9 = r0
            r3 = r9
            goto L_0x0119
        L_0x010a:
            r8 = move-exception
            r10 = r9
            r9 = r0
            r3 = r9
            goto L_0x013d
        L_0x010f:
            r8 = move-exception
            r9 = r0
            r3 = r9
        L_0x0112:
            r4 = r3
            goto L_0x017c
        L_0x0115:
            r8 = move-exception
            r9 = r0
            r10 = r9
            r3 = r10
        L_0x0119:
            r4 = r3
            r5 = r4
        L_0x011b:
            r2 = r1
            r1 = r5
        L_0x011d:
            r8.printStackTrace()     // Catch:{ all -> 0x0177 }
            if (r3 == 0) goto L_0x0128
            r3.close()     // Catch:{ IOException -> 0x0126 }
            goto L_0x0128
        L_0x0126:
            r8 = move-exception
            goto L_0x0133
        L_0x0128:
            if (r4 == 0) goto L_0x012d
            r4.close()     // Catch:{ IOException -> 0x0126 }
        L_0x012d:
            if (r5 == 0) goto L_0x0136
            r5.close()     // Catch:{ IOException -> 0x0126 }
            goto L_0x0136
        L_0x0133:
            r8.printStackTrace()
        L_0x0136:
            if (r10 == 0) goto L_0x015f
            goto L_0x015c
        L_0x0139:
            r8 = move-exception
            r9 = r0
            r10 = r9
            r3 = r10
        L_0x013d:
            r4 = r3
            r5 = r4
        L_0x013f:
            r2 = r1
            r1 = r5
        L_0x0141:
            r8.printStackTrace()     // Catch:{ all -> 0x0177 }
            if (r3 == 0) goto L_0x014c
            r3.close()     // Catch:{ IOException -> 0x014a }
            goto L_0x014c
        L_0x014a:
            r8 = move-exception
            goto L_0x0157
        L_0x014c:
            if (r4 == 0) goto L_0x0151
            r4.close()     // Catch:{ IOException -> 0x014a }
        L_0x0151:
            if (r5 == 0) goto L_0x015a
            r5.close()     // Catch:{ IOException -> 0x014a }
            goto L_0x015a
        L_0x0157:
            r8.printStackTrace()
        L_0x015a:
            if (r10 == 0) goto L_0x015f
        L_0x015c:
            r10.destroy()
        L_0x015f:
            r8 = r9
            r10 = r1
            r1 = r2
        L_0x0162:
            com.zkteco.android.core.device.ZkShellUtils$CommandResult r9 = new com.zkteco.android.core.device.ZkShellUtils$CommandResult
            if (r8 != 0) goto L_0x0168
            r8 = r0
            goto L_0x016c
        L_0x0168:
            java.lang.String r8 = r8.toString()
        L_0x016c:
            if (r10 != 0) goto L_0x016f
            goto L_0x0173
        L_0x016f:
            java.lang.String r0 = r10.toString()
        L_0x0173:
            r9.<init>(r1, r8, r0)
            return r9
        L_0x0177:
            r8 = move-exception
            r9 = r10
            r0 = r3
            r3 = r4
            r4 = r5
        L_0x017c:
            if (r0 == 0) goto L_0x0184
            r0.close()     // Catch:{ IOException -> 0x0182 }
            goto L_0x0184
        L_0x0182:
            r10 = move-exception
            goto L_0x018f
        L_0x0184:
            if (r3 == 0) goto L_0x0189
            r3.close()     // Catch:{ IOException -> 0x0182 }
        L_0x0189:
            if (r4 == 0) goto L_0x0192
            r4.close()     // Catch:{ IOException -> 0x0182 }
            goto L_0x0192
        L_0x018f:
            r10.printStackTrace()
        L_0x0192:
            if (r9 == 0) goto L_0x0197
            r9.destroy()
        L_0x0197:
            throw r8
        L_0x0198:
            com.zkteco.android.core.device.ZkShellUtils$CommandResult r8 = new com.zkteco.android.core.device.ZkShellUtils$CommandResult
            r8.<init>(r1, r0, r0)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.core.device.ZkShellUtils.execCommand(java.lang.String[], boolean, boolean):com.zkteco.android.core.device.ZkShellUtils$CommandResult");
    }

    public static class CommandResult {
        public String errorMsg;
        public int result;
        public String successMsg;

        public CommandResult(int i) {
            this.result = i;
        }

        public CommandResult(int i, String str, String str2) {
            this.result = i;
            this.successMsg = str;
            this.errorMsg = str2;
        }
    }
}
