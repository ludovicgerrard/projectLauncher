package com.zkteco.android.core.device;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.SystemClock;
import com.google.common.base.Ascii;
import java.io.IOException;

public class SysPropertyUtil {
    public static final int CMD_GET_PROP = 1;
    public static final int CMD_SET_PROP = 0;
    private static final String SOCKET_NAME = "zk_prop";
    private static LocalSocket socketClient;

    public static boolean setKeyValue(String str, byte[] bArr) {
        boolean z;
        synchronized (SysPropertyUtil.class) {
            ZktecoPropertyMessage zktecoPropertyMessage = new ZktecoPropertyMessage();
            z = false;
            try {
                zktecoPropertyMessage.setCmd(0);
                zktecoPropertyMessage.setPropertyKey(str.getBytes());
                zktecoPropertyMessage.setPropertyValue(bArr);
                z = setPropertyWithSocket(zktecoPropertyMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    public static byte[] getKeyValue(String str) {
        byte[] bArr;
        synchronized (SysPropertyUtil.class) {
            ZktecoPropertyMessage zktecoPropertyMessage = new ZktecoPropertyMessage();
            try {
                zktecoPropertyMessage.setCmd(1);
                zktecoPropertyMessage.setPropertyKey(str.getBytes());
                bArr = getPropertyWithSocket(zktecoPropertyMessage);
            } catch (IOException e) {
                e.printStackTrace();
                bArr = null;
            }
        }
        return bArr;
    }

    private static void sleepms(int i) {
        try {
            Thread.sleep((long) i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean setPropertyWithSocket(ZktecoPropertyMessage zktecoPropertyMessage) throws IOException {
        if (zktecoPropertyMessage.getCmd() != 0) {
            return false;
        }
        socketClient = new LocalSocket();
        socketClient.connect(new LocalSocketAddress(SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED));
        if (socketClient.isConnected()) {
            socketClient.getOutputStream().write(zktecoPropertyMessage.getZktecoMessage());
            sleepms(500);
            socketClient.shutdownOutput();
            socketClient.close();
            socketClient = null;
            return true;
        }
        LocalSocket localSocket = socketClient;
        if (localSocket != null) {
            localSocket.close();
        }
        socketClient = null;
        return false;
    }

    private static byte[] getPropertyWithSocket(ZktecoPropertyMessage zktecoPropertyMessage) throws IOException {
        byte[] bArr;
        if (zktecoPropertyMessage.getCmd() != 1) {
            return null;
        }
        byte[] bArr2 = new byte[1024];
        socketClient = new LocalSocket();
        socketClient.connect(new LocalSocketAddress(SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED));
        if (socketClient.isConnected()) {
            socketClient.setSoTimeout(500);
            socketClient.getOutputStream().write(zktecoPropertyMessage.getZktecoMessage());
            byte[] bArr3 = new byte[4];
            long elapsedRealtime = SystemClock.elapsedRealtime() + 500;
            while (socketClient.getInputStream().available() < 4) {
                if (elapsedRealtime < SystemClock.elapsedRealtime()) {
                    socketClient.close();
                    socketClient = null;
                    return null;
                }
                sleepms(5);
            }
            socketClient.getInputStream().read(bArr3);
            int i = ((bArr3[1] & 255) << 8) | (bArr3[0] & 255) | ((bArr3[2] & 255) << 16) | ((bArr3[3] & 255) << Ascii.CAN);
            if (i <= 0 || socketClient.getInputStream().read(bArr2, 0, i) != i) {
                bArr = null;
            } else {
                bArr = new byte[i];
                System.arraycopy(bArr2, 0, bArr, 0, i);
            }
            socketClient.shutdownOutput();
            socketClient.shutdownInput();
            socketClient.close();
            socketClient = null;
            return bArr;
        }
        LocalSocket localSocket = socketClient;
        if (localSocket != null) {
            localSocket.close();
        }
        socketClient = null;
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.io.DataOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.io.DataOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: java.lang.String} */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x008c A[SYNTHETIC, Splitter:B:30:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x009c A[SYNTHETIC, Splitter:B:38:0x009c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String upgradeRootPermissionEx(java.lang.String r6) {
        /*
            r0 = 0
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x0084, all -> 0x0081 }
            java.lang.String r2 = "sh"
            java.lang.Process r1 = r1.exec(r2)     // Catch:{ Exception -> 0x0084, all -> 0x0081 }
            java.io.InputStream r2 = r1.getInputStream()     // Catch:{ Exception -> 0x007e }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x007e }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x007e }
            r4.<init>(r2)     // Catch:{ Exception -> 0x007e }
            r3.<init>(r4)     // Catch:{ Exception -> 0x007e }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x007e }
            java.io.OutputStream r4 = r1.getOutputStream()     // Catch:{ Exception -> 0x007e }
            r2.<init>(r4)     // Catch:{ Exception -> 0x007e }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            r4.<init>()     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.lang.StringBuilder r6 = r4.append(r6)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.lang.String r4 = "\n"
            java.lang.StringBuilder r6 = r6.append(r4)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            r2.writeBytes(r6)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.lang.String r6 = "exit\n"
            r2.writeBytes(r6)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            r2.flush()     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
        L_0x0040:
            int r6 = r3.read()     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            if (r6 <= 0) goto L_0x0067
            java.lang.String r6 = r3.readLine()     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.lang.String r4 = "vendor.cam.orientation"
            int r4 = r6.indexOf(r4)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            if (r4 <= 0) goto L_0x0040
            java.lang.String r3 = "["
            int r3 = r6.indexOf(r3)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            int r3 = r3 + 1
            java.lang.String r4 = "]"
            int r4 = r6.lastIndexOf(r4)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.lang.CharSequence r6 = r6.subSequence(r3, r4)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            r0 = r6
        L_0x0067:
            r1.waitFor()     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            r2.close()     // Catch:{ Exception -> 0x0071 }
            r1.destroy()     // Catch:{ Exception -> 0x0071 }
            goto L_0x0098
        L_0x0071:
            r6 = move-exception
            r6.printStackTrace()
            goto L_0x0098
        L_0x0076:
            r6 = move-exception
            r0 = r2
            goto L_0x009a
        L_0x0079:
            r6 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x0087
        L_0x007e:
            r6 = move-exception
            r2 = r0
            goto L_0x0087
        L_0x0081:
            r6 = move-exception
            r1 = r0
            goto L_0x009a
        L_0x0084:
            r6 = move-exception
            r1 = r0
            r2 = r1
        L_0x0087:
            r6.printStackTrace()     // Catch:{ all -> 0x0099 }
            if (r0 == 0) goto L_0x008f
            r0.close()     // Catch:{ Exception -> 0x0093 }
        L_0x008f:
            r1.destroy()     // Catch:{ Exception -> 0x0093 }
            goto L_0x0097
        L_0x0093:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0097:
            r0 = r2
        L_0x0098:
            return r0
        L_0x0099:
            r6 = move-exception
        L_0x009a:
            if (r0 == 0) goto L_0x009f
            r0.close()     // Catch:{ Exception -> 0x00a3 }
        L_0x009f:
            r1.destroy()     // Catch:{ Exception -> 0x00a3 }
            goto L_0x00a7
        L_0x00a3:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00a7:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.core.device.SysPropertyUtil.upgradeRootPermissionEx(java.lang.String):java.lang.String");
    }
}
