package com.zktechnology.android.push.att;

import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.orm.tna.PhotoIndex;

public class AttPush {
    private static final String KEY_PHOTO_TIME = "Photo_Time";
    private static final String KEY_PHOTO_TYPE = "Photo_Type";
    private static final String KEY_STATUS = "Status";
    private static final String KEY_USER_PIN = "User_PIN";
    private static final String KEY_VERIFY_TIME = "Verify_Time";
    private static final String KEY_VERIFY_TYPE = "Verify_Type";
    private static final String TABLE_NAME_ATT_LOG = "ATT_LOG";
    private static final String TABLE_NAME_PHOTO_INDEX = "PHOTO_INDEX";
    private static final String TAG = "Verify-Push";
    private static AttPush mInstance;
    private HubProtocolManager mManager;

    private AttPush(HubProtocolManager hubProtocolManager) {
        this.mManager = hubProtocolManager;
    }

    public static void newInstance(HubProtocolManager hubProtocolManager) {
        if (mInstance == null) {
            synchronized (AttPush.class) {
                if (mInstance == null) {
                    mInstance = new AttPush(hubProtocolManager);
                }
            }
        }
    }

    public static AttPush getInstance() {
        return mInstance;
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0135  */
    /* JADX WARNING: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void push(com.zkteco.android.db.orm.tna.AttLog r10) {
        /*
            r9 = this;
            r0 = 0
            r1 = 0
            r2 = r0
        L_0x0003:
            r3 = 5
            java.lang.String r4 = "Type->"
            java.lang.String r5 = "userPin->"
            java.lang.String r6 = "; "
            if (r2 >= r3) goto L_0x007a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r7 = "insert fail:"
            r3.<init>(r7)
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r7 = r10.getUser_PIN()
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.StringBuilder r3 = r3.append(r4)
            int r7 = r10.getVerify_Type()
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.String r7 = "retryCount->"
            java.lang.StringBuilder r3 = r3.append(r7)
            int r2 = r2 + 1
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.Object r7 = r10.create()     // Catch:{ Exception -> 0x0061 }
            com.zkteco.android.db.orm.tna.AttLog r7 = (com.zkteco.android.db.orm.tna.AttLog) r7     // Catch:{ Exception -> 0x0061 }
            if (r7 != 0) goto L_0x005f
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x005d }
            com.zktechnology.android.push.util.FileLogUtils.writeDatabaseLog(r1)     // Catch:{ Exception -> 0x005d }
            r4 = 500(0x1f4, double:2.47E-321)
            java.lang.Thread.sleep(r4)     // Catch:{ InterruptedException -> 0x0058 }
            goto L_0x0078
        L_0x0058:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ Exception -> 0x005d }
            goto L_0x0078
        L_0x005d:
            r1 = move-exception
            goto L_0x0064
        L_0x005f:
            r1 = r7
            goto L_0x007a
        L_0x0061:
            r4 = move-exception
            r7 = r1
            r1 = r4
        L_0x0064:
            r1.printStackTrace()
            java.lang.String r4 = "error->"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            com.zktechnology.android.push.util.FileLogUtils.writeDatabaseLog(r1)
        L_0x0078:
            r1 = r7
            goto L_0x0003
        L_0x007a:
            if (r1 != 0) goto L_0x00ac
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "five insert failures:"
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r1 = r10.getUser_PIN()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r6)
            java.lang.StringBuilder r0 = r0.append(r4)
            int r10 = r10.getVerify_Type()
            java.lang.StringBuilder r10 = r0.append(r10)
            java.lang.StringBuilder r10 = r10.append(r6)
            java.lang.String r10 = r10.toString()
            com.zktechnology.android.push.util.FileLogUtils.writeDatabaseLog(r10)
            goto L_0x012f
        L_0x00ac:
            r1 = 0
            com.zkteco.android.core.sdk.HubProtocolManager r3 = r9.mManager     // Catch:{ Exception -> 0x0121, all -> 0x011e }
            long r3 = r3.convertPushInit()     // Catch:{ Exception -> 0x0121, all -> 0x011e }
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r9.mManager     // Catch:{ Exception -> 0x011c }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x011c }
            r6.<init>()     // Catch:{ Exception -> 0x011c }
            java.lang.String r7 = "ID="
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x011c }
            long r7 = r10.getID()     // Catch:{ Exception -> 0x011c }
            int r7 = (int) r7     // Catch:{ Exception -> 0x011c }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x011c }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x011c }
            r5.setPushCon(r3, r6)     // Catch:{ Exception -> 0x011c }
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r9.mManager     // Catch:{ Exception -> 0x011c }
            java.lang.String r6 = "ID"
            long r7 = r10.getID()     // Catch:{ Exception -> 0x011c }
            int r7 = (int) r7     // Catch:{ Exception -> 0x011c }
            r5.setPushIntField(r3, r6, r7)     // Catch:{ Exception -> 0x011c }
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r9.mManager     // Catch:{ Exception -> 0x011c }
            java.lang.String r6 = "ATT_LOG"
            r5.setPushTableName(r3, r6)     // Catch:{ Exception -> 0x011c }
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r9.mManager     // Catch:{ Exception -> 0x011c }
            java.lang.String r6 = "User_PIN"
            java.lang.String r7 = r10.getUser_PIN()     // Catch:{ Exception -> 0x011c }
            r5.setPushStrField(r3, r6, r7)     // Catch:{ Exception -> 0x011c }
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r9.mManager     // Catch:{ Exception -> 0x011c }
            java.lang.String r6 = "Verify_Type"
            int r7 = r10.getVerify_Type()     // Catch:{ Exception -> 0x011c }
            r5.setPushIntField(r3, r6, r7)     // Catch:{ Exception -> 0x011c }
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r9.mManager     // Catch:{ Exception -> 0x011c }
            java.lang.String r6 = "Verify_Time"
            java.lang.String r7 = r10.getVerify_Time()     // Catch:{ Exception -> 0x011c }
            r5.setPushStrField(r3, r6, r7)     // Catch:{ Exception -> 0x011c }
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r9.mManager     // Catch:{ Exception -> 0x011c }
            java.lang.String r6 = "Status"
            int r10 = r10.getStatus()     // Catch:{ Exception -> 0x011c }
            r5.setPushIntField(r3, r6, r10)     // Catch:{ Exception -> 0x011c }
            com.zkteco.android.core.sdk.HubProtocolManager r10 = r9.mManager     // Catch:{ Exception -> 0x011c }
            java.lang.String r5 = ""
            r10.sendHubAction(r0, r3, r5)     // Catch:{ Exception -> 0x011c }
            int r10 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r10 == 0) goto L_0x012f
            goto L_0x012a
        L_0x011c:
            r10 = move-exception
            goto L_0x0123
        L_0x011e:
            r10 = move-exception
            r3 = r1
            goto L_0x0131
        L_0x0121:
            r10 = move-exception
            r3 = r1
        L_0x0123:
            r10.printStackTrace()     // Catch:{ all -> 0x0130 }
            int r10 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r10 == 0) goto L_0x012f
        L_0x012a:
            com.zkteco.android.core.sdk.HubProtocolManager r10 = r9.mManager
            r10.convertPushFree(r3)
        L_0x012f:
            return
        L_0x0130:
            r10 = move-exception
        L_0x0131:
            int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x013a
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r9.mManager
            r0.convertPushFree(r3)
        L_0x013a:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.push.att.AttPush.push(com.zkteco.android.db.orm.tna.AttLog):void");
    }

    public void push(PhotoIndex photoIndex) {
        try {
            this.mManager.convertPushInit();
            this.mManager.setPushTableName(0, TABLE_NAME_PHOTO_INDEX);
            this.mManager.setPushStrField(0, KEY_USER_PIN, photoIndex.getUser_PIN());
            this.mManager.setPushStrField(0, KEY_PHOTO_TIME, photoIndex.getPhoto_Time());
            this.mManager.setPushIntField(0, KEY_PHOTO_TYPE, photoIndex.getPhoto_Type());
            this.mManager.sendHubAction(0, 0, (String) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
