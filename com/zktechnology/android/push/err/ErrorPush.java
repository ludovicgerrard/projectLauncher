package com.zktechnology.android.push.err;

import android.content.Context;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.orm.manager.DataManager;

public class ErrorPush {
    private static Context mContext;
    private static ErrorPush mInstance;
    private int DEFAUT_DELETE_NUM = 100;
    private int MAX_LOG_NUM = 1000;
    DataManager dataManager;
    private HubProtocolManager hubProtocolManager;

    private ErrorPush(HubProtocolManager hubProtocolManager2) {
        this.hubProtocolManager = hubProtocolManager2;
        DataManager instance = DBManager.getInstance();
        this.dataManager = instance;
        try {
            this.MAX_LOG_NUM = instance.getIntOption("~MaxErrorLogCount", 1000);
            this.DEFAUT_DELETE_NUM = this.dataManager.getIntOption("~DeleteErrorLogCount", 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ErrorPush getInstance() {
        if (mInstance == null) {
            synchronized (ErrorPush.class) {
                if (mInstance == null) {
                    HubProtocolManager hubProtocolManager2 = new HubProtocolManager(LauncherApplication.getLauncherApplicationContext());
                    mContext = LauncherApplication.getLauncherApplicationContext();
                    mInstance = new ErrorPush(hubProtocolManager2);
                }
            }
        }
        return mInstance;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0088 A[SYNTHETIC, Splitter:B:35:0x0088] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0095 A[SYNTHETIC, Splitter:B:41:0x0095] */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addErrorLog(com.zkteco.android.db.orm.tna.ErrorLog r7) {
        /*
            r6 = this;
            int r0 = r6.MAX_LOG_NUM
            android.content.Context r1 = mContext
            com.zktechnology.android.verify.dao.ZKErrorLogDao r1 = com.zktechnology.android.verify.dao.ZKErrorLogDao.getInstance(r1)
            int r1 = r1.getErrorLogCount()
            r2 = 1
            r3 = 0
            if (r0 > r1) goto L_0x0012
            r0 = r2
            goto L_0x0013
        L_0x0012:
            r0 = r3
        L_0x0013:
            if (r0 == 0) goto L_0x0031
            android.content.Context r0 = mContext
            com.zktechnology.android.verify.dao.ZKErrorLogDao r0 = com.zktechnology.android.verify.dao.ZKErrorLogDao.getInstance(r0)
            int r1 = r6.DEFAUT_DELETE_NUM
            r0.deleteErrorLog(r1)
            int r0 = r6.MAX_LOG_NUM
            android.content.Context r1 = mContext
            com.zktechnology.android.verify.dao.ZKErrorLogDao r1 = com.zktechnology.android.verify.dao.ZKErrorLogDao.getInstance(r1)
            int r1 = r1.getErrorLogCount()
            if (r0 > r1) goto L_0x0030
            r0 = r2
            goto L_0x0031
        L_0x0030:
            r0 = r3
        L_0x0031:
            if (r0 == 0) goto L_0x0034
            return
        L_0x0034:
            r0 = 4
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x00a0 }
            java.lang.String r1 = r7.getErrorCode()     // Catch:{ Exception -> 0x00a0 }
            r0[r3] = r1     // Catch:{ Exception -> 0x00a0 }
            java.lang.String r1 = r7.getDataorigin()     // Catch:{ Exception -> 0x00a0 }
            r0[r2] = r1     // Catch:{ Exception -> 0x00a0 }
            r1 = 2
            java.lang.String r2 = r7.getCmdID()     // Catch:{ Exception -> 0x00a0 }
            r0[r1] = r2     // Catch:{ Exception -> 0x00a0 }
            r1 = 3
            java.lang.String r7 = r7.getAdditional()     // Catch:{ Exception -> 0x00a0 }
            r0[r1] = r7     // Catch:{ Exception -> 0x00a0 }
            com.zkteco.android.db.orm.manager.DataManager r7 = r6.dataManager     // Catch:{ Exception -> 0x00a0 }
            java.lang.String r1 = "ZKDB.db"
            java.lang.String r2 = "insert into ERROR_LOG(ErrorCode, dataorigin, CmdID, additional) values(?,?,?,?)"
            r7.executeSql(r1, r2, r0)     // Catch:{ Exception -> 0x00a0 }
            r0 = 0
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r6.hubProtocolManager     // Catch:{ Exception -> 0x007f, all -> 0x007c }
            long r2 = r7.convertPushInit()     // Catch:{ Exception -> 0x007f, all -> 0x007c }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r6.hubProtocolManager     // Catch:{ Exception -> 0x007a }
            r4 = 19
            java.lang.String r5 = "TableName=ERROR_LOG"
            r7.sendHubAction(r4, r2, r5)     // Catch:{ Exception -> 0x007a }
            int r7 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r7 == 0) goto L_0x00a4
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0075 }
            r7.convertPushFree(r2)     // Catch:{ Exception -> 0x0075 }
            goto L_0x00a4
        L_0x0075:
            r7 = move-exception
        L_0x0076:
            r7.printStackTrace()     // Catch:{ Exception -> 0x00a0 }
            goto L_0x00a4
        L_0x007a:
            r7 = move-exception
            goto L_0x0081
        L_0x007c:
            r7 = move-exception
            r2 = r0
            goto L_0x0091
        L_0x007f:
            r7 = move-exception
            r2 = r0
        L_0x0081:
            r7.printStackTrace()     // Catch:{ all -> 0x0090 }
            int r7 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r7 == 0) goto L_0x00a4
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r6.hubProtocolManager     // Catch:{ Exception -> 0x008e }
            r7.convertPushFree(r2)     // Catch:{ Exception -> 0x008e }
            goto L_0x00a4
        L_0x008e:
            r7 = move-exception
            goto L_0x0076
        L_0x0090:
            r7 = move-exception
        L_0x0091:
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x009f
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r6.hubProtocolManager     // Catch:{ Exception -> 0x009b }
            r0.convertPushFree(r2)     // Catch:{ Exception -> 0x009b }
            goto L_0x009f
        L_0x009b:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x00a0 }
        L_0x009f:
            throw r7     // Catch:{ Exception -> 0x00a0 }
        L_0x00a0:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00a4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.push.err.ErrorPush.addErrorLog(com.zkteco.android.db.orm.tna.ErrorLog):void");
    }
}
