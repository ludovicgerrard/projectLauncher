package com.zktechnology.android.verify.dao;

import android.content.Context;
import android.database.Cursor;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.orm.tna.AttLog;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.sql.SQLException;
import java.util.Locale;

public class ZKVerDao extends ZKBaseDao implements IZKVerDao {
    private static final int ATT_LOG_STATE_NORMAL = 255;
    private static final String TAG = "ZKVerDao";
    private HubProtocolManager hubProtocolManager = new HubProtocolManager(this.mContext);

    public ZKVerDao(Context context) {
        super(context);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0040, code lost:
        if (r1 == null) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0043, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0034, code lost:
        if (r1 != null) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0036, code lost:
        r1.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getUserSmsCount(java.lang.String r5) {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003c }
            r2.<init>()     // Catch:{ Exception -> 0x003c }
            java.lang.String r3 = "SELECT count(*) as number FROM USER_SMS us LEFT JOIN SMS_INFO si ON us.Sms_ID = si.ID WHERE si.Type = 254 AND us.User_PIN = '"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x003c }
            java.lang.StringBuilder r5 = r2.append(r5)     // Catch:{ Exception -> 0x003c }
            java.lang.String r2 = "' "
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch:{ Exception -> 0x003c }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x003c }
            com.zkteco.android.db.orm.manager.DataManager r2 = mDb     // Catch:{ Exception -> 0x003c }
            android.database.Cursor r1 = r2.queryBySql(r5)     // Catch:{ Exception -> 0x003c }
            if (r1 == 0) goto L_0x0034
            boolean r5 = r1.moveToFirst()     // Catch:{ Exception -> 0x003c }
            if (r5 == 0) goto L_0x0034
            java.lang.String r5 = "number"
            int r5 = r1.getColumnIndex(r5)     // Catch:{ Exception -> 0x003c }
            int r5 = r1.getInt(r5)     // Catch:{ Exception -> 0x003c }
            r0 = r5
        L_0x0034:
            if (r1 == 0) goto L_0x0043
        L_0x0036:
            r1.close()
            goto L_0x0043
        L_0x003a:
            r5 = move-exception
            goto L_0x0044
        L_0x003c:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ all -> 0x003a }
            if (r1 == 0) goto L_0x0043
            goto L_0x0036
        L_0x0043:
            return r0
        L_0x0044:
            if (r1 == 0) goto L_0x0049
            r1.close()
        L_0x0049:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKVerDao.getUserSmsCount(java.lang.String):int");
    }

    public void addAttLog(UserInfo userInfo, String str, int i, int i2, String str2, double d, int i3) {
        int i4;
        UserInfo userInfo2 = userInfo;
        String str3 = str;
        int i5 = i;
        int i6 = i3;
        AttLog attLog = new AttLog();
        if (userInfo2 != null) {
            try {
                attLog.setUser_PIN(userInfo.getUser_PIN());
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
        } else {
            attLog.setUser_PIN("0");
        }
        if (ZKLauncher.locationFunOn) {
            attLog.setVerify_Time(String.format(Locale.US, "%s,%f,%f", new Object[]{str3, Double.valueOf(ZKLauncher.longitude), Double.valueOf(ZKLauncher.latitude)}));
        } else {
            attLog.setVerify_Time(str3);
        }
        int i7 = 15;
        if (ZKLauncher.sLockFunOn == 15 && ZKLauncher.sAPBFO == 1 && ZKLauncher.sAntiPassbackOn > 0) {
            int intOption = DBManager.getInstance().getIntOption(ZKDBConfig.DOO1_ACCESS_DIRECTION, 0);
            if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() != 5) {
                attLog.setStatus(intOption);
            } else if (intOption == 0) {
                attLog.setStatus(1);
            } else {
                attLog.setStatus(0);
            }
        } else {
            if (str2 == null) {
                i4 = 255;
            } else {
                try {
                    i4 = Integer.parseInt(str2);
                } catch (NumberFormatException e2) {
                    LogUtils.e(TAG, "Exc: " + e2.getMessage());
                    attLog.setStatus(255);
                }
            }
            attLog.setStatus(i4);
        }
        attLog.setWork_Code_ID(i2);
        if (userInfo2 != null) {
            i7 = getUserVerifyType(userInfo2, i5);
        } else if (i5 != 5) {
            i7 = i5;
        }
        if (i7 != -1) {
            attLog.setVerify_Type(i7);
        } else {
            attLog.setVerify_Type(0);
        }
        if (d == 255.0d) {
            attLog.setTemperature("255");
        } else if (d != -1.0d) {
            attLog.setTemperature(String.valueOf(d));
        }
        if (ZKLauncher.maskDetectionFunOn != 1 || ZKLauncher.enalbeMaskDetection != 1) {
            attLog.setMask_Flag(255);
        } else if (i6 == 2) {
            attLog.setMask_Flag(1);
        } else if (i6 == 1) {
            attLog.setMask_Flag(0);
        } else {
            attLog.setMask_Flag(255);
        }
        UserInfo userInfo3 = null;
        try {
            userInfo3 = (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", userInfo.getUser_PIN()).queryForFirst();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        sendHub(attLog, userInfo3);
        if ("1".equals(ZKLauncher.sUserValidTimeFun) && userInfo2 != null) {
            if (userInfo.getExpires() == 2 || userInfo.getExpires() == 3) {
                if (userInfo.getVaildCount() > 0) {
                    userInfo2.setVaildCount(userInfo.getVaildCount() - 1);
                } else {
                    userInfo2.setVaildCount(0);
                }
                userInfo2.setSEND_FLAG(0);
                userInfo.update();
                sendUpDataPush(attLog, userInfo3);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendUpDataPush(com.zkteco.android.db.orm.tna.AttLog r8, com.zkteco.android.db.orm.tna.UserInfo r9) {
        /*
            r7 = this;
            r0 = 0
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r7.hubProtocolManager     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            long r2 = r2.convertPushInit()     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0048 }
            java.lang.String r5 = "ATT_LOG"
            r4.setPushTableName(r2, r5)     // Catch:{ Exception -> 0x0048 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0048 }
            java.lang.String r5 = "User_PIN"
            java.lang.String r6 = r8.getUser_PIN()     // Catch:{ Exception -> 0x0048 }
            r4.setPushStrField(r2, r5, r6)     // Catch:{ Exception -> 0x0048 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0048 }
            java.lang.String r5 = "Verify_Type"
            int r9 = r9.getVerify_Type()     // Catch:{ Exception -> 0x0048 }
            r4.setPushIntField(r2, r5, r9)     // Catch:{ Exception -> 0x0048 }
            com.zkteco.android.core.sdk.HubProtocolManager r9 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0048 }
            java.lang.String r4 = "Verify_Time"
            java.lang.String r5 = r8.getVerify_Time()     // Catch:{ Exception -> 0x0048 }
            r9.setPushStrField(r2, r4, r5)     // Catch:{ Exception -> 0x0048 }
            com.zkteco.android.core.sdk.HubProtocolManager r9 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0048 }
            java.lang.String r4 = "Status"
            int r8 = r8.getStatus()     // Catch:{ Exception -> 0x0048 }
            r9.setPushIntField(r2, r4, r8)     // Catch:{ Exception -> 0x0048 }
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0048 }
            r9 = 0
            java.lang.String r4 = ""
            r8.sendHubAction(r9, r2, r4)     // Catch:{ Exception -> 0x0048 }
            int r8 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r8 == 0) goto L_0x005b
            goto L_0x0056
        L_0x0048:
            r8 = move-exception
            goto L_0x004f
        L_0x004a:
            r8 = move-exception
            r2 = r0
            goto L_0x005d
        L_0x004d:
            r8 = move-exception
            r2 = r0
        L_0x004f:
            r8.printStackTrace()     // Catch:{ all -> 0x005c }
            int r8 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r8 == 0) goto L_0x005b
        L_0x0056:
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r7.hubProtocolManager
            r8.convertPushFree(r2)
        L_0x005b:
            return
        L_0x005c:
            r8 = move-exception
        L_0x005d:
            int r9 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r9 == 0) goto L_0x0066
            com.zkteco.android.core.sdk.HubProtocolManager r9 = r7.hubProtocolManager
            r9.convertPushFree(r2)
        L_0x0066:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKVerDao.sendUpDataPush(com.zkteco.android.db.orm.tna.AttLog, com.zkteco.android.db.orm.tna.UserInfo):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x005f, code lost:
        r6.hubProtocolManager.convertStandaloneFree(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0071, code lost:
        if (r3 != 0) goto L_0x005f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r3 = r6.hubProtocolManager.convertStandaloneInit();
        r6.hubProtocolManager.convertStandaloneSetUserPIN(r3, r7.getUser_PIN());
        r6.hubProtocolManager.convertStandaloneSetEventType(r3, 234);
        r6.hubProtocolManager.sendHubAction(4, r3, "");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0092, code lost:
        if (r3 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0095, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0097, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r7.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x009d, code lost:
        if (r3 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009f, code lost:
        r6.hubProtocolManager.convertStandaloneFree(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a7, code lost:
        if (r3 != 0) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a9, code lost:
        r6.hubProtocolManager.convertStandaloneFree(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00ae, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x005d, code lost:
        if (r3 != 0) goto L_0x005f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendHub(com.zkteco.android.db.orm.tna.AttLog r7, com.zkteco.android.db.orm.tna.UserInfo r8) {
        /*
            r6 = this;
            java.lang.String r0 = ""
            com.zktechnology.android.push.att.AttPush r1 = com.zktechnology.android.push.att.AttPush.getInstance()
            r1.push((com.zkteco.android.db.orm.tna.AttLog) r7)
            r1 = 0
            com.zkteco.android.core.sdk.HubProtocolManager r3 = r6.hubProtocolManager     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            long r3 = r3.convertStandaloneInit()     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            if (r8 != 0) goto L_0x001d
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0065 }
            int r5 = r7.getVerify_Type()     // Catch:{ Exception -> 0x0065 }
            r8.convertStandaloneSetVerifyType(r3, r5)     // Catch:{ Exception -> 0x0065 }
            goto L_0x0026
        L_0x001d:
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0065 }
            int r8 = r8.getVerify_Type()     // Catch:{ Exception -> 0x0065 }
            r5.convertStandaloneSetVerifyType(r3, r8)     // Catch:{ Exception -> 0x0065 }
        L_0x0026:
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0065 }
            java.lang.String r5 = r7.getUser_PIN()     // Catch:{ Exception -> 0x0065 }
            r8.convertStandaloneSetUserPIN(r3, r5)     // Catch:{ Exception -> 0x0065 }
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0065 }
            int r5 = r7.getStatus()     // Catch:{ Exception -> 0x0065 }
            r8.convertStandaloneSetState(r3, r5)     // Catch:{ Exception -> 0x0065 }
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0065 }
            int r5 = r7.getWork_Code_ID()     // Catch:{ Exception -> 0x0065 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x0065 }
            r8.convertStandaloneSetWorkCodeNum(r3, r5)     // Catch:{ Exception -> 0x0065 }
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0065 }
            java.lang.String r5 = r7.getVerify_Time()     // Catch:{ Exception -> 0x0065 }
            r8.convertStandaloneSetTime(r3, r5)     // Catch:{ Exception -> 0x0065 }
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0065 }
            r5 = 245(0xf5, float:3.43E-43)
            r8.convertStandaloneSetEventType(r3, r5)     // Catch:{ Exception -> 0x0065 }
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0065 }
            r5 = 2
            r8.sendHubAction(r5, r3, r0)     // Catch:{ Exception -> 0x0065 }
            int r8 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r8 == 0) goto L_0x0074
        L_0x005f:
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager
            r8.convertStandaloneFree(r3)
            goto L_0x0074
        L_0x0065:
            r8 = move-exception
            goto L_0x006c
        L_0x0067:
            r7 = move-exception
            r3 = r1
            goto L_0x00b0
        L_0x006a:
            r8 = move-exception
            r3 = r1
        L_0x006c:
            r8.printStackTrace()     // Catch:{ all -> 0x00af }
            int r8 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r8 == 0) goto L_0x0074
            goto L_0x005f
        L_0x0074:
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0097 }
            long r3 = r8.convertStandaloneInit()     // Catch:{ Exception -> 0x0097 }
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0097 }
            java.lang.String r7 = r7.getUser_PIN()     // Catch:{ Exception -> 0x0097 }
            r8.convertStandaloneSetUserPIN(r3, r7)     // Catch:{ Exception -> 0x0097 }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0097 }
            r8 = 234(0xea, float:3.28E-43)
            r7.convertStandaloneSetEventType(r3, r8)     // Catch:{ Exception -> 0x0097 }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r6.hubProtocolManager     // Catch:{ Exception -> 0x0097 }
            r8 = 4
            r7.sendHubAction(r8, r3, r0)     // Catch:{ Exception -> 0x0097 }
            int r7 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r7 == 0) goto L_0x00a4
            goto L_0x009f
        L_0x0095:
            r7 = move-exception
            goto L_0x00a5
        L_0x0097:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ all -> 0x0095 }
            int r7 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r7 == 0) goto L_0x00a4
        L_0x009f:
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r6.hubProtocolManager
            r7.convertStandaloneFree(r3)
        L_0x00a4:
            return
        L_0x00a5:
            int r8 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r8 == 0) goto L_0x00ae
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager
            r8.convertStandaloneFree(r3)
        L_0x00ae:
            throw r7
        L_0x00af:
            r7 = move-exception
        L_0x00b0:
            int r8 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r8 == 0) goto L_0x00b9
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r6.hubProtocolManager
            r8.convertStandaloneFree(r3)
        L_0x00b9:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKVerDao.sendHub(com.zkteco.android.db.orm.tna.AttLog, com.zkteco.android.db.orm.tna.UserInfo):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0041, code lost:
        if (r1 == null) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0044, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        if (r1 != null) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        r1.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getAttLogCount() {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
            java.lang.String r2 = "SELECT count(*) as count FROM ATT_LOG"
            com.zkteco.android.db.orm.manager.DataManager r3 = mDb     // Catch:{ Exception -> 0x0024 }
            android.database.Cursor r1 = r3.queryBySql(r2)     // Catch:{ Exception -> 0x0024 }
            if (r1 == 0) goto L_0x001c
            boolean r2 = r1.moveToFirst()     // Catch:{ Exception -> 0x0024 }
            if (r2 == 0) goto L_0x001c
            java.lang.String r2 = "count"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Exception -> 0x0024 }
            int r0 = r1.getInt(r2)     // Catch:{ Exception -> 0x0024 }
        L_0x001c:
            if (r1 == 0) goto L_0x0044
        L_0x001e:
            r1.close()
            goto L_0x0044
        L_0x0022:
            r0 = move-exception
            goto L_0x0045
        L_0x0024:
            r2 = move-exception
            java.lang.String r3 = "ZKVerDao"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0022 }
            r4.<init>()     // Catch:{ all -> 0x0022 }
            java.lang.String r5 = "Exc: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0022 }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x0022 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x0022 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0022 }
            com.zktechnology.android.utils.LogUtils.e(r3, r2)     // Catch:{ all -> 0x0022 }
            if (r1 == 0) goto L_0x0044
            goto L_0x001e
        L_0x0044:
            return r0
        L_0x0045:
            if (r1 == 0) goto L_0x004a
            r1.close()
        L_0x004a:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKVerDao.getAttLogCount():int");
    }

    public void deleteAttLog(int i) {
        try {
            Cursor queryBySql = mDb.queryBySql("DELETE FROM ATT_LOG WHERE id IN( SELECT id FROM ATT_LOG ORDER BY id ASC LIMIT " + i + " )");
            if (queryBySql != null) {
                queryBySql.close();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "Exc: " + e.getMessage());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0028, code lost:
        if (r1 == null) goto L_0x002b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002b, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        if (r1 != null) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        r1.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getMaxCaptureCount() {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            java.lang.String r2 = "SELECT count(DISTINCT Photo_Time) as count FROM PHOTO_INDEX WHERE Photo_Type = 0"
            com.zkteco.android.db.orm.manager.DataManager r3 = mDb     // Catch:{ Exception -> 0x0024 }
            android.database.Cursor r1 = r3.queryBySql(r2)     // Catch:{ Exception -> 0x0024 }
            if (r1 == 0) goto L_0x001c
            boolean r2 = r1.moveToFirst()     // Catch:{ Exception -> 0x0024 }
            if (r2 == 0) goto L_0x001c
            java.lang.String r2 = "count"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Exception -> 0x0024 }
            int r0 = r1.getInt(r2)     // Catch:{ Exception -> 0x0024 }
        L_0x001c:
            if (r1 == 0) goto L_0x002b
        L_0x001e:
            r1.close()
            goto L_0x002b
        L_0x0022:
            r0 = move-exception
            goto L_0x002c
        L_0x0024:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x0022 }
            if (r1 == 0) goto L_0x002b
            goto L_0x001e
        L_0x002b:
            return r0
        L_0x002c:
            if (r1 == 0) goto L_0x0031
            r1.close()
        L_0x0031:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKVerDao.getMaxCaptureCount():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0059, code lost:
        if (r0 == null) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0062, code lost:
        if (r0 == null) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0064, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r6 = mDb.queryBySql("DELETE FROM PHOTO_INDEX WHERE ID IN( SELECT ID FROM PHOTO_INDEX WHERE Photo_Type = 0 ORDER BY ID ASC LIMIT " + r6 + " )");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0086, code lost:
        if (r6 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0088, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008e, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r6.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void deleteCapture(int r6) {
        /*
            r5 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005e }
            r1.<init>()     // Catch:{ Exception -> 0x005e }
            java.lang.String r2 = "SELECT replace(replace(replace(Photo_Time, 'T', ''), '-', ''),':','') || '-' ||User_PIN AS photoName FROM PHOTO_INDEX WHERE Photo_Type = 0 LIMIT 0 , "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r1 = r1.append(r6)     // Catch:{ Exception -> 0x005e }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x005e }
            com.zkteco.android.db.orm.manager.DataManager r2 = mDb     // Catch:{ Exception -> 0x005e }
            android.database.Cursor r0 = r2.queryBySql(r1)     // Catch:{ Exception -> 0x005e }
            if (r0 == 0) goto L_0x0059
            boolean r1 = r0.moveToFirst()     // Catch:{ Exception -> 0x005e }
            if (r1 == 0) goto L_0x0059
        L_0x0022:
            java.lang.String r1 = "photoName"
            int r1 = r0.getColumnIndex(r1)     // Catch:{ Exception -> 0x005e }
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x005e }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005e }
            r3.<init>()     // Catch:{ Exception -> 0x005e }
            java.lang.String r4 = com.zkteco.android.zkcore.utils.ZKFilePath.ATT_PHOTO_PATH     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ Exception -> 0x005e }
            java.lang.String r3 = com.zkteco.android.zkcore.utils.ZKFilePath.SUFFIX_IMAGE     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Exception -> 0x005e }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x005e }
            r2.<init>(r1)     // Catch:{ Exception -> 0x005e }
            boolean r1 = r2.exists()     // Catch:{ Exception -> 0x005e }
            if (r1 == 0) goto L_0x0053
            r2.delete()     // Catch:{ Exception -> 0x005e }
        L_0x0053:
            boolean r1 = r0.moveToNext()     // Catch:{ Exception -> 0x005e }
            if (r1 != 0) goto L_0x0022
        L_0x0059:
            if (r0 == 0) goto L_0x0067
            goto L_0x0064
        L_0x005c:
            r6 = move-exception
            goto L_0x0094
        L_0x005e:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x005c }
            if (r0 == 0) goto L_0x0067
        L_0x0064:
            r0.close()
        L_0x0067:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "DELETE FROM PHOTO_INDEX WHERE ID IN( SELECT ID FROM PHOTO_INDEX WHERE Photo_Type = 0 ORDER BY ID ASC LIMIT "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r6 = r0.append(r6)
            java.lang.String r0 = " )"
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r6 = r6.toString()
            com.zkteco.android.db.orm.manager.DataManager r0 = mDb     // Catch:{ Exception -> 0x008e }
            android.database.Cursor r6 = r0.queryBySql(r6)     // Catch:{ Exception -> 0x008e }
            if (r6 == 0) goto L_0x0092
            r6.close()
            goto L_0x0092
        L_0x008c:
            r6 = move-exception
            goto L_0x0093
        L_0x008e:
            r6 = move-exception
            r6.printStackTrace()     // Catch:{ all -> 0x008c }
        L_0x0092:
            return
        L_0x0093:
            throw r6
        L_0x0094:
            if (r0 == 0) goto L_0x0099
            r0.close()
        L_0x0099:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKVerDao.deleteCapture(int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0058, code lost:
        if (r8 != null) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005b, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0038, code lost:
        if (r8 != null) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x003a, code lost:
        r8.close();
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getLastVerifyTime(java.lang.String r8) {
        /*
            r7 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0045, all -> 0x0040 }
            r1.<init>()     // Catch:{ Exception -> 0x0045, all -> 0x0040 }
            java.lang.String r2 = "SELECT Verify_Time FROM ATT_LOG WHERE User_PIN = '"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x0045, all -> 0x0040 }
            java.lang.StringBuilder r8 = r1.append(r8)     // Catch:{ Exception -> 0x0045, all -> 0x0040 }
            java.lang.String r1 = "' ORDER BY id DESC LIMIT 1"
            java.lang.StringBuilder r8 = r8.append(r1)     // Catch:{ Exception -> 0x0045, all -> 0x0040 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0045, all -> 0x0040 }
            com.zkteco.android.db.orm.manager.DataManager r1 = mDb     // Catch:{ Exception -> 0x0045, all -> 0x0040 }
            android.database.Cursor r8 = r1.queryBySql(r8)     // Catch:{ Exception -> 0x0045, all -> 0x0040 }
            boolean r1 = r8.moveToFirst()     // Catch:{ Exception -> 0x003e }
            if (r1 == 0) goto L_0x0038
            java.lang.String r1 = "Verify_Time"
            int r1 = r8.getColumnIndex(r1)     // Catch:{ Exception -> 0x003e }
            java.lang.String r1 = r8.getString(r1)     // Catch:{ Exception -> 0x003e }
            java.lang.String r2 = "T"
            java.lang.String r3 = " "
            java.lang.String r0 = r1.replace(r2, r3)     // Catch:{ Exception -> 0x003e }
        L_0x0038:
            if (r8 == 0) goto L_0x005b
        L_0x003a:
            r8.close()
            goto L_0x005b
        L_0x003e:
            r1 = move-exception
            goto L_0x0047
        L_0x0040:
            r8 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
            goto L_0x005d
        L_0x0045:
            r1 = move-exception
            r8 = r0
        L_0x0047:
            java.lang.String r2 = "Verify"
            java.lang.String r3 = "Error: get last verify time error,message: %s"
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x005c }
            r5 = 0
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x005c }
            r4[r5] = r1     // Catch:{ all -> 0x005c }
            com.zktechnology.android.utils.LogUtils.e(r2, r3, r4)     // Catch:{ all -> 0x005c }
            if (r8 == 0) goto L_0x005b
            goto L_0x003a
        L_0x005b:
            return r0
        L_0x005c:
            r0 = move-exception
        L_0x005d:
            if (r8 == 0) goto L_0x0062
            r8.close()
        L_0x0062:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKVerDao.getLastVerifyTime(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005d, code lost:
        if (com.zktechnology.android.verify.utils.ZKVerProcessPar.KEY_BOARD_1V1 != false) goto L_0x0057;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getUserVerifyType(com.zkteco.android.db.orm.tna.UserInfo r9, int r10) {
        /*
            r8 = this;
            r0 = -1
            if (r0 != r10) goto L_0x0004
            return r0
        L_0x0004:
            com.zktechnology.android.verify.bean.process.ZKVerifyType r10 = com.zktechnology.android.verify.bean.process.ZKVerifyType.fromInteger(r10)
            int r1 = r9.getVerify_Type()
            r2 = 100
            if (r1 != r2) goto L_0x002e
            com.zkteco.android.db.orm.tna.AccGroup r1 = new com.zkteco.android.db.orm.tna.AccGroup     // Catch:{ Exception -> 0x0029 }
            r1.<init>()     // Catch:{ Exception -> 0x0029 }
            int r9 = r9.getAcc_Group_ID()     // Catch:{ Exception -> 0x0029 }
            long r2 = (long) r9     // Catch:{ Exception -> 0x0029 }
            java.lang.Long r9 = java.lang.Long.valueOf(r2)     // Catch:{ Exception -> 0x0029 }
            java.lang.Object r9 = r1.queryForId(r9)     // Catch:{ Exception -> 0x0029 }
            com.zkteco.android.db.orm.tna.AccGroup r9 = (com.zkteco.android.db.orm.tna.AccGroup) r9     // Catch:{ Exception -> 0x0029 }
            int r1 = r9.getVerification()     // Catch:{ Exception -> 0x0029 }
            goto L_0x002e
        L_0x0029:
            r9 = move-exception
            r9.printStackTrace()
            r1 = r0
        L_0x002e:
            r9 = 8
            r2 = 10
            r3 = 2
            r4 = 4
            r5 = 3
            r6 = 1
            if (r1 == r0) goto L_0x0087
            if (r1 == 0) goto L_0x0087
            r7 = 5
            if (r1 == r7) goto L_0x007a
            r7 = 6
            if (r1 == r7) goto L_0x006d
            r7 = 7
            if (r1 == r7) goto L_0x0060
            r3 = 14
            if (r1 == r3) goto L_0x0048
            return r1
        L_0x0048:
            int[] r1 = com.zktechnology.android.verify.dao.ZKVerDao.AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType
            int r10 = r10.ordinal()
            r10 = r1[r10]
            if (r10 == r6) goto L_0x005b
            if (r10 == r5) goto L_0x0059
            if (r10 == r4) goto L_0x0057
            goto L_0x00a0
        L_0x0057:
            r0 = r9
            goto L_0x00a0
        L_0x0059:
            r0 = r2
            goto L_0x00a0
        L_0x005b:
            boolean r10 = com.zktechnology.android.verify.utils.ZKVerProcessPar.KEY_BOARD_1V1
            if (r10 == 0) goto L_0x0059
            goto L_0x0057
        L_0x0060:
            int[] r9 = com.zktechnology.android.verify.dao.ZKVerDao.AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType
            int r10 = r10.ordinal()
            r9 = r9[r10]
            if (r9 == r3) goto L_0x009d
            if (r9 == r5) goto L_0x009b
            goto L_0x00a0
        L_0x006d:
            int[] r9 = com.zktechnology.android.verify.dao.ZKVerDao.AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType
            int r10 = r10.ordinal()
            r9 = r9[r10]
            if (r9 == r6) goto L_0x009f
            if (r9 == r5) goto L_0x009b
            goto L_0x00a0
        L_0x007a:
            int[] r9 = com.zktechnology.android.verify.dao.ZKVerDao.AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType
            int r10 = r10.ordinal()
            r9 = r9[r10]
            if (r9 == r6) goto L_0x009f
            if (r9 == r3) goto L_0x009d
            goto L_0x00a0
        L_0x0087:
            int[] r9 = com.zktechnology.android.verify.dao.ZKVerDao.AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType
            int r10 = r10.ordinal()
            r9 = r9[r10]
            switch(r9) {
                case 1: goto L_0x009f;
                case 2: goto L_0x009d;
                case 3: goto L_0x009b;
                case 4: goto L_0x0099;
                case 5: goto L_0x0096;
                case 6: goto L_0x0093;
                default: goto L_0x0092;
            }
        L_0x0092:
            goto L_0x00a0
        L_0x0093:
            r0 = 25
            goto L_0x00a0
        L_0x0096:
            r0 = 15
            goto L_0x00a0
        L_0x0099:
            r0 = r3
            goto L_0x00a0
        L_0x009b:
            r0 = r4
            goto L_0x00a0
        L_0x009d:
            r0 = r5
            goto L_0x00a0
        L_0x009f:
            r0 = r6
        L_0x00a0:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKVerDao.getUserVerifyType(com.zkteco.android.db.orm.tna.UserInfo, int):int");
    }

    /* renamed from: com.zktechnology.android.verify.dao.ZKVerDao$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|14) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.zktechnology.android.verify.bean.process.ZKVerifyType[] r0 = com.zktechnology.android.verify.bean.process.ZKVerifyType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType = r0
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.CARD     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PALM     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKVerDao.AnonymousClass1.<clinit>():void");
        }
    }
}
