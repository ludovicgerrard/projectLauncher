package com.zkteco.android.core.device;

import android.text.TextUtils;
import com.zkteco.android.core.interfaces.IDeviceStore;

public class H1Store implements IDeviceStore {
    public boolean setProp(String str, String str2) {
        boolean z = false;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        synchronized (this) {
            if (!TextUtils.isEmpty(str2) && str2.contains(" ")) {
                str2 = "'" + str2 + "'";
            }
            if (ZkShellUtils.execCommand(String.format("device_config put ZKTECO %s %s", new Object[]{str, str2}), false).result == 0) {
                z = true;
            }
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0034, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getProp(java.lang.String r5) {
        /*
            r4 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            monitor-enter(r4)
            java.lang.String r0 = "device_config get ZKTECO %s"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0035 }
            r3 = 0
            r2[r3] = r5     // Catch:{ all -> 0x0035 }
            java.lang.String r5 = java.lang.String.format(r0, r2)     // Catch:{ all -> 0x0035 }
            com.zkteco.android.core.device.ZkShellUtils$CommandResult r5 = com.zkteco.android.core.device.ZkShellUtils.execCommand((java.lang.String) r5, (boolean) r3)     // Catch:{ all -> 0x0035 }
            int r0 = r5.result     // Catch:{ all -> 0x0035 }
            if (r0 != 0) goto L_0x0033
            java.lang.String r0 = r5.successMsg     // Catch:{ all -> 0x0035 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0035 }
            if (r0 != 0) goto L_0x0033
            java.lang.String r0 = "null"
            java.lang.String r2 = r5.successMsg     // Catch:{ all -> 0x0035 }
            boolean r0 = r0.equals(r2)     // Catch:{ all -> 0x0035 }
            if (r0 != 0) goto L_0x0033
            java.lang.String r5 = r5.successMsg     // Catch:{ all -> 0x0035 }
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            return r5
        L_0x0033:
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            return r1
        L_0x0035:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.core.device.H1Store.getProp(java.lang.String):java.lang.String");
    }
}
