package com.zktechnology.android.push.acc;

import android.content.Context;
import androidx.exifinterface.media.ExifInterface;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.push.util.AccEventType;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.push.util.Utils;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.core.sdk.SharedPreferencesManager;
import com.zkteco.android.db.orm.tna.AccAttLog;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AccPush {
    private static final String KEY_SEND_REBOOT = "reboot";
    private static final String TABLE_NAME_ACC_ATT_LOG = "ACC_ATT_LOG";
    private static final String TAG = "Access-pushEvent";
    private static Context mContext;
    private static AccPush mInstance;
    private HubProtocolManager hubProtocolManager;

    private AccPush(HubProtocolManager hubProtocolManager2) {
        this.hubProtocolManager = hubProtocolManager2;
    }

    public static AccPush getInstance() {
        if (mInstance == null) {
            synchronized (AccPush.class) {
                if (mInstance == null) {
                    HubProtocolManager hubProtocolManager2 = new HubProtocolManager(LauncherApplication.getLauncherApplicationContext());
                    mContext = LauncherApplication.getLauncherApplicationContext();
                    mInstance = new AccPush(hubProtocolManager2);
                }
            }
        }
        return mInstance;
    }

    public void pushReboot() {
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(LauncherApplication.getLauncherApplicationContext());
        String str = sharedPreferencesManager.get(KEY_SEND_REBOOT, "0");
        LogUtils.d(TAG, "Client connect server,is push reboot? %s", Boolean.valueOf("1".equals(str)));
        if ("1".equals(str)) {
            LogUtils.d("ZKTEST-PushReboot", "push reboot");
            pushRealEvent(AccEventType.DEVICE_START);
            sharedPreferencesManager.set(KEY_SEND_REBOOT, "0");
        }
    }

    public void pushRealEvent(int i) {
        pushRealEvent(i, new Date(), 0, -1.0d, 0);
    }

    public void pushIllegalCardRealEvent(int i, Date date, int i2, double d, int i3) {
        if (ZKLauncher.sAccessRuleType != 0) {
            LogUtils.d(TAG, "push illegal card event: %s", Integer.valueOf(i));
            AccAttLog accAttLog = new AccAttLog();
            accAttLog.setInOutState(ZKLauncher.sInOutState);
            accAttLog.setTimeSecond(Utils.formatAccPushTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE)));
            accAttLog.setEventType(i);
            accAttLog.setVerified(i2);
            accAttLog.setDoorID(ZKLauncher.sDoorId);
            accAttLog.setMainCard(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_CARD));
            if (d == 255.0d) {
                accAttLog.setTemperature("255");
            } else if (d != -1.0d) {
                accAttLog.setTemperature(String.valueOf(d));
            }
            if (ZKLauncher.maskDetectionFunOn != 1 || ZKLauncher.enalbeMaskDetection != 1) {
                accAttLog.setMask_Flag(255);
            } else if (i3 == 2) {
                accAttLog.setMask_Flag(1);
            } else if (i3 == 1) {
                accAttLog.setMask_Flag(0);
            } else {
                accAttLog.setMask_Flag(255);
            }
            FileLogUtils.writeVerifyLog("launcher push: pushRealEvent");
            push(accAttLog);
        }
    }

    public void pushRealEvent(int i, Date date, int i2, double d, int i3) {
        if (ZKLauncher.sAccessRuleType != 0) {
            LogUtils.d(TAG, "push real event: %s", Integer.valueOf(i));
            AccAttLog accAttLog = new AccAttLog();
            accAttLog.setInOutState(ZKLauncher.sInOutState);
            accAttLog.setTimeSecond(Utils.formatAccPushTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE)));
            accAttLog.setEventType(i);
            accAttLog.setVerified(i2);
            accAttLog.setDoorID(ZKLauncher.sDoorId);
            if (d == 255.0d) {
                accAttLog.setTemperature("255");
            } else if (d != -1.0d) {
                accAttLog.setTemperature(String.valueOf(d));
            }
            if (ZKLauncher.maskDetectionFunOn != 1 || ZKLauncher.enalbeMaskDetection != 1) {
                accAttLog.setMask_Flag(255);
            } else if (i3 == 2) {
                accAttLog.setMask_Flag(1);
            } else if (i3 == 1) {
                accAttLog.setMask_Flag(0);
            } else {
                accAttLog.setMask_Flag(255);
            }
            FileLogUtils.writeVerifyLog("launcher push: pushRealEvent");
            push(accAttLog);
        }
    }

    public void pushUnregisterEvent(int i, int i2, Date date) {
        if (i2 == ZKVerifyType.FACE.getValue()) {
            i2 = 15;
        }
        LogUtils.d(TAG, "push unregister event: %s", Integer.valueOf(i));
        AccAttLog accAttLog = new AccAttLog();
        accAttLog.setInOutState(ZKLauncher.sInOutState);
        accAttLog.setTimeSecond(Utils.formatAccPushTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE)));
        accAttLog.setEventType(i);
        accAttLog.setVerified(i2);
        accAttLog.setMask_Flag(255);
        accAttLog.setDoorID(ZKLauncher.sDoorId);
        FileLogUtils.writeVerifyLog("launcher push: pushUnregisterEvent");
        push(accAttLog);
    }

    /* JADX WARNING: Removed duplicated region for block: B:80:0x023c  */
    /* JADX WARNING: Removed duplicated region for block: B:90:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void push(com.zkteco.android.db.orm.tna.AccAttLog r13) {
        /*
            r12 = this;
            java.lang.String r0 = "Access-pushEvent"
            java.lang.String r1 = "push acc att log"
            com.zktechnology.android.utils.LogUtils.d(r0, r1)
            int r1 = com.zktechnology.android.launcher2.ZKLauncher.sMaxAttLogCount
            android.content.Context r2 = mContext
            com.zktechnology.android.verify.dao.ZKAccDao r2 = com.zktechnology.android.verify.dao.ZKAccDao.getInstance(r2)
            int r2 = r2.getAccAttLogCount()
            r3 = 0
            r4 = 1
            if (r1 > r2) goto L_0x0019
            r1 = r4
            goto L_0x001a
        L_0x0019:
            r1 = r3
        L_0x001a:
            int r2 = com.zktechnology.android.launcher2.ZKLauncher.sLoopDeleteLog
            if (r2 <= 0) goto L_0x003c
            if (r1 == 0) goto L_0x003c
            android.content.Context r1 = mContext
            com.zktechnology.android.verify.dao.ZKAccDao r1 = com.zktechnology.android.verify.dao.ZKAccDao.getInstance(r1)
            int r2 = com.zktechnology.android.launcher2.ZKLauncher.sLoopDeleteLog
            r1.deleteAccAttLog(r2)
            int r1 = com.zktechnology.android.launcher2.ZKLauncher.sMaxAttLogCount
            android.content.Context r2 = mContext
            com.zktechnology.android.verify.dao.ZKAccDao r2 = com.zktechnology.android.verify.dao.ZKAccDao.getInstance(r2)
            int r2 = r2.getAccAttLogCount()
            if (r1 > r2) goto L_0x003b
            r1 = r4
            goto L_0x003c
        L_0x003b:
            r1 = r3
        L_0x003c:
            if (r1 == 0) goto L_0x003f
            return
        L_0x003f:
            r1 = 0
            r2 = r3
        L_0x0041:
            java.lang.String r5 = "EventType->"
            java.lang.String r6 = "Verified->"
            java.lang.String r7 = "userPin->"
            r8 = 5
            java.lang.String r9 = "; "
            if (r2 >= r8) goto L_0x00cb
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "insert fail:"
            r10.<init>(r11)
            java.lang.StringBuilder r10 = r10.append(r7)
            java.lang.String r11 = r13.getUserPIN()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r9)
            java.lang.StringBuilder r10 = r10.append(r6)
            int r11 = r13.getVerified()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r9)
            java.lang.StringBuilder r10 = r10.append(r5)
            int r11 = r13.getEventType()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r9)
            java.lang.String r11 = "retryCount->"
            java.lang.StringBuilder r10 = r10.append(r11)
            int r2 = r2 + 1
            java.lang.StringBuilder r10 = r10.append(r2)
            java.lang.StringBuilder r10 = r10.append(r9)
            java.lang.Object r11 = r13.create()     // Catch:{ Exception -> 0x00b1 }
            com.zkteco.android.db.orm.tna.AccAttLog r11 = (com.zkteco.android.db.orm.tna.AccAttLog) r11     // Catch:{ Exception -> 0x00b1 }
            if (r11 != 0) goto L_0x00af
            java.lang.String r1 = r10.toString()     // Catch:{ Exception -> 0x00ad }
            com.zktechnology.android.push.util.FileLogUtils.writeDatabaseLog(r1)     // Catch:{ Exception -> 0x00ad }
            r5 = 500(0x1f4, double:2.47E-321)
            java.lang.Thread.sleep(r5)     // Catch:{ InterruptedException -> 0x00a8 }
            goto L_0x00c8
        L_0x00a8:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ Exception -> 0x00ad }
            goto L_0x00c8
        L_0x00ad:
            r1 = move-exception
            goto L_0x00b4
        L_0x00af:
            r1 = r11
            goto L_0x00cb
        L_0x00b1:
            r5 = move-exception
            r11 = r1
            r1 = r5
        L_0x00b4:
            r1.printStackTrace()
            java.lang.String r5 = "error->"
            java.lang.StringBuilder r5 = r10.append(r5)
            java.lang.StringBuilder r1 = r5.append(r1)
            java.lang.String r1 = r1.toString()
            com.zktechnology.android.push.util.FileLogUtils.writeDatabaseLog(r1)
        L_0x00c8:
            r1 = r11
            goto L_0x0041
        L_0x00cb:
            if (r1 != 0) goto L_0x010d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "five insert failures:"
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r7)
            java.lang.String r1 = r13.getUserPIN()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.StringBuilder r0 = r0.append(r6)
            int r1 = r13.getVerified()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.StringBuilder r0 = r0.append(r5)
            int r13 = r13.getEventType()
            java.lang.StringBuilder r13 = r0.append(r13)
            java.lang.StringBuilder r13 = r13.append(r9)
            java.lang.String r13 = r13.toString()
            com.zktechnology.android.push.util.FileLogUtils.writeDatabaseLog(r13)
            goto L_0x0237
        L_0x010d:
            r1 = 0
            com.zkteco.android.core.sdk.HubProtocolManager r5 = r12.hubProtocolManager     // Catch:{ Exception -> 0x0212, all -> 0x020f }
            long r5 = r5.convertPushInit()     // Catch:{ Exception -> 0x0212, all -> 0x020f }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r9 = "ACC_ATT_LOG"
            r7.setPushTableName(r5, r9)     // Catch:{ Exception -> 0x020d }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x020d }
            r9.<init>()     // Catch:{ Exception -> 0x020d }
            java.lang.String r10 = "ID="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x020d }
            long r10 = r13.getID()     // Catch:{ Exception -> 0x020d }
            int r10 = (int) r10     // Catch:{ Exception -> 0x020d }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x020d }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x020d }
            r7.setPushCon(r5, r9)     // Catch:{ Exception -> 0x020d }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r9 = "ID"
            long r10 = r13.getID()     // Catch:{ Exception -> 0x020d }
            int r10 = (int) r10     // Catch:{ Exception -> 0x020d }
            r7.setPushIntField(r5, r9, r10)     // Catch:{ Exception -> 0x020d }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r9 = "MainCard"
            java.lang.String r10 = r13.getMainCard()     // Catch:{ Exception -> 0x020d }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x020d }
            java.lang.String r11 = ""
            if (r10 == 0) goto L_0x0157
            r10 = r11
            goto L_0x015b
        L_0x0157:
            java.lang.String r10 = r13.getMainCard()     // Catch:{ Exception -> 0x020d }
        L_0x015b:
            r7.setPushStrField(r5, r9, r10)     // Catch:{ Exception -> 0x020d }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r9 = "UserPIN"
            java.lang.String r10 = r13.getUserPIN()     // Catch:{ Exception -> 0x020d }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x020d }
            if (r10 == 0) goto L_0x016e
            r10 = r11
            goto L_0x0172
        L_0x016e:
            java.lang.String r10 = r13.getUserPIN()     // Catch:{ Exception -> 0x020d }
        L_0x0172:
            r7.setPushStrField(r5, r9, r10)     // Catch:{ Exception -> 0x020d }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r9 = "Verified"
            int r10 = r13.getVerified()     // Catch:{ Exception -> 0x020d }
            r7.setPushIntField(r5, r9, r10)     // Catch:{ Exception -> 0x020d }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r9 = "DoorID"
            int r10 = com.zktechnology.android.launcher2.ZKLauncher.sDoorId     // Catch:{ Exception -> 0x020d }
            r7.setPushIntField(r5, r9, r10)     // Catch:{ Exception -> 0x020d }
            com.zkteco.android.core.sdk.HubProtocolManager r7 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r9 = "EventType"
            int r10 = r13.getEventType()     // Catch:{ Exception -> 0x020d }
            r7.setPushIntField(r5, r9, r10)     // Catch:{ Exception -> 0x020d }
            int r7 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState     // Catch:{ Exception -> 0x020d }
            int r9 = com.zktechnology.android.verify.utils.ZKVerProcessPar.VERIFY_SOURCE_TYPE     // Catch:{ Exception -> 0x020d }
            if (r9 != r4) goto L_0x01a1
            int r7 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState     // Catch:{ Exception -> 0x020d }
            if (r7 != 0) goto L_0x01a0
            r7 = r4
            goto L_0x01a1
        L_0x01a0:
            r7 = r3
        L_0x01a1:
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r9 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x020d }
            int r9 = r9.getIntent()     // Catch:{ Exception -> 0x020d }
            if (r9 != r8) goto L_0x01ad
            if (r7 != r4) goto L_0x01ac
            r4 = r3
        L_0x01ac:
            r7 = r4
        L_0x01ad:
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r8 = "InOutState"
            r4.setPushIntField(r5, r8, r7)     // Catch:{ Exception -> 0x020d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x020d }
            r4.<init>()     // Catch:{ Exception -> 0x020d }
            java.lang.String r7 = "launcher time = "
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ Exception -> 0x020d }
            int r7 = r13.getTimeSecond()     // Catch:{ Exception -> 0x020d }
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ Exception -> 0x020d }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x020d }
            com.zktechnology.android.push.util.FileLogUtils.writeVerifyLog(r4)     // Catch:{ Exception -> 0x020d }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r7 = "TimeSecond"
            int r8 = r13.getTimeSecond()     // Catch:{ Exception -> 0x020d }
            r4.setPushIntField(r5, r7, r8)     // Catch:{ Exception -> 0x020d }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r7 = "LogID"
            long r8 = r13.getID()     // Catch:{ Exception -> 0x020d }
            int r8 = (int) r8     // Catch:{ Exception -> 0x020d }
            r4.setPushIntField(r5, r7, r8)     // Catch:{ Exception -> 0x020d }
            java.lang.String r4 = r13.getTemperature()     // Catch:{ Exception -> 0x020d }
            if (r4 == 0) goto L_0x01f6
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r7 = "Temperature"
            java.lang.String r8 = r13.getTemperature()     // Catch:{ Exception -> 0x020d }
            r4.setPushStrField(r5, r7, r8)     // Catch:{ Exception -> 0x020d }
        L_0x01f6:
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            java.lang.String r7 = "Mask_Flag"
            int r13 = r13.getMask_Flag()     // Catch:{ Exception -> 0x020d }
            r4.setPushIntField(r5, r7, r13)     // Catch:{ Exception -> 0x020d }
            com.zkteco.android.core.sdk.HubProtocolManager r13 = r12.hubProtocolManager     // Catch:{ Exception -> 0x020d }
            r13.sendHubAction(r3, r5, r11)     // Catch:{ Exception -> 0x020d }
            int r13 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r13 == 0) goto L_0x0237
            goto L_0x0232
        L_0x020b:
            r13 = move-exception
            goto L_0x0238
        L_0x020d:
            r13 = move-exception
            goto L_0x0214
        L_0x020f:
            r13 = move-exception
            r5 = r1
            goto L_0x0238
        L_0x0212:
            r13 = move-exception
            r5 = r1
        L_0x0214:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x020b }
            r3.<init>()     // Catch:{ all -> 0x020b }
            java.lang.String r4 = "AccAttLog Exc: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x020b }
            java.lang.String r13 = r13.getMessage()     // Catch:{ all -> 0x020b }
            java.lang.StringBuilder r13 = r3.append(r13)     // Catch:{ all -> 0x020b }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x020b }
            com.zktechnology.android.utils.LogUtils.e(r0, r13)     // Catch:{ all -> 0x020b }
            int r13 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r13 == 0) goto L_0x0237
        L_0x0232:
            com.zkteco.android.core.sdk.HubProtocolManager r13 = r12.hubProtocolManager
            r13.convertPushFree(r5)
        L_0x0237:
            return
        L_0x0238:
            int r0 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x0241
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r12.hubProtocolManager
            r0.convertPushFree(r5)
        L_0x0241:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.push.acc.AccPush.push(com.zkteco.android.db.orm.tna.AccAttLog):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void pushDoorSensorEvent(int r18, int r19, int r20) {
        /*
            r17 = this;
            r1 = r17
            r0 = r19
            java.lang.String r2 = "Access-pushEvent"
            r3 = 0
            java.lang.String r5 = "push door sensor event"
            com.zktechnology.android.utils.LogUtils.d(r2, r5)     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            com.zktechnology.android.acc.DoorSensorState r5 = com.zktechnology.android.acc.DoorSensorState.fromInteger(r18)     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            int[] r6 = com.zktechnology.android.push.acc.AccPush.AnonymousClass1.$SwitchMap$com$zktechnology$android$acc$DoorSensorState     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            int r5 = r5.ordinal()     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r5 = r6[r5]     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            java.lang.String r6 = ""
            r7 = 3
            r8 = 2
            r9 = 1
            r10 = 0
            if (r5 == r9) goto L_0x0039
            if (r5 == r8) goto L_0x0030
            if (r5 == r7) goto L_0x0027
            r5 = r6
            goto L_0x0041
        L_0x0027:
            byte[] r5 = new byte[r9]     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r5[r10] = r10     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            java.lang.String r5 = parseByte2HexStr(r5)     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            goto L_0x0041
        L_0x0030:
            byte[] r5 = new byte[r9]     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r5[r10] = r9     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            java.lang.String r5 = parseByte2HexStr(r5)     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            goto L_0x0041
        L_0x0039:
            byte[] r5 = new byte[r9]     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r5[r10] = r8     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            java.lang.String r5 = parseByte2HexStr(r5)     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
        L_0x0041:
            if (r0 != r9) goto L_0x0045
            r0 = r9
            goto L_0x0047
        L_0x0045:
            r11 = -1
            r0 = r10
        L_0x0047:
            r11 = 8
            byte[] r11 = new byte[r11]     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r12 = r20
            byte r12 = (byte) r12     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r11[r10] = r12     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r11[r9] = r10     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r11[r8] = r10     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r11[r7] = r10     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r12 = 4
            r11[r12] = r10     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r13 = 5
            r11[r13] = r10     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r13 = 6
            r11[r13] = r10     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r13 = 7
            r11[r13] = r10     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            java.lang.String r11 = parseByte2HexStr(r11)     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            long r13 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r15 = 1000(0x3e8, double:4.94E-321)
            long r13 = r13 / r15
            int r13 = (int) r13     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            java.lang.String r14 = "time = %s,sensor = %s, relay = %s,alarm = %s"
            java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r13)     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r12[r10] = r15     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r18)     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r12[r9] = r15     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r12[r8] = r15     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            r12[r7] = r11     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            com.zktechnology.android.utils.LogUtils.d(r2, r14, r12)     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r1.hubProtocolManager     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            long r7 = r2.convertPushInit()     // Catch:{ Exception -> 0x00d6, all -> 0x00d3 }
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r1.hubProtocolManager     // Catch:{ Exception -> 0x00d1 }
            java.lang.String r12 = "ACC_REAL_STATUS"
            r2.setPushTableName(r7, r12)     // Catch:{ Exception -> 0x00d1 }
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r1.hubProtocolManager     // Catch:{ Exception -> 0x00d1 }
            java.lang.String r12 = "time"
            r2.setPushIntField(r7, r12, r13)     // Catch:{ Exception -> 0x00d1 }
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r1.hubProtocolManager     // Catch:{ Exception -> 0x00d1 }
            java.lang.String r12 = "sensor"
            r2.setPushStrField(r7, r12, r5)     // Catch:{ Exception -> 0x00d1 }
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r1.hubProtocolManager     // Catch:{ Exception -> 0x00d1 }
            java.lang.String r5 = "relay"
            r2.setPushIntField(r7, r5, r0)     // Catch:{ Exception -> 0x00d1 }
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r1.hubProtocolManager     // Catch:{ Exception -> 0x00d1 }
            java.lang.String r2 = "alarm"
            r0.setPushStrField(r7, r2, r11)     // Catch:{ Exception -> 0x00d1 }
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState     // Catch:{ Exception -> 0x00d1 }
            int r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.VERIFY_SOURCE_TYPE     // Catch:{ Exception -> 0x00d1 }
            if (r2 != r9) goto L_0x00bf
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState     // Catch:{ Exception -> 0x00d1 }
            if (r0 != 0) goto L_0x00bd
            goto L_0x00c0
        L_0x00bd:
            r9 = r10
            goto L_0x00c0
        L_0x00bf:
            r9 = r0
        L_0x00c0:
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r1.hubProtocolManager     // Catch:{ Exception -> 0x00d1 }
            java.lang.String r2 = "InOutState"
            r0.setPushIntField(r7, r2, r9)     // Catch:{ Exception -> 0x00d1 }
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r1.hubProtocolManager     // Catch:{ Exception -> 0x00d1 }
            r0.sendHubAction(r10, r7, r6)     // Catch:{ Exception -> 0x00d1 }
            int r0 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r0 == 0) goto L_0x00e4
            goto L_0x00df
        L_0x00d1:
            r0 = move-exception
            goto L_0x00d8
        L_0x00d3:
            r0 = move-exception
            r7 = r3
            goto L_0x00e6
        L_0x00d6:
            r0 = move-exception
            r7 = r3
        L_0x00d8:
            r0.printStackTrace()     // Catch:{ all -> 0x00e5 }
            int r0 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r0 == 0) goto L_0x00e4
        L_0x00df:
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r1.hubProtocolManager
            r0.convertPushFree(r7)
        L_0x00e4:
            return
        L_0x00e5:
            r0 = move-exception
        L_0x00e6:
            int r2 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r2 == 0) goto L_0x00ef
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r1.hubProtocolManager
            r2.convertPushFree(r7)
        L_0x00ef:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.push.acc.AccPush.pushDoorSensorEvent(int, int, int):void");
    }

    /* renamed from: com.zktechnology.android.push.acc.AccPush$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zktechnology$android$acc$DoorSensorState;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.zktechnology.android.acc.DoorSensorState[] r0 = com.zktechnology.android.acc.DoorSensorState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$zktechnology$android$acc$DoorSensorState = r0
                com.zktechnology.android.acc.DoorSensorState r1 = com.zktechnology.android.acc.DoorSensorState.OPEN     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$zktechnology$android$acc$DoorSensorState     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zktechnology.android.acc.DoorSensorState r1 = com.zktechnology.android.acc.DoorSensorState.CLOSE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$zktechnology$android$acc$DoorSensorState     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.zktechnology.android.acc.DoorSensorState r1 = com.zktechnology.android.acc.DoorSensorState.NONE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.push.acc.AccPush.AnonymousClass1.<clinit>():void");
        }
    }

    public static String parseByte2HexStr(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            stringBuffer.append(hexString.toUpperCase());
        }
        return stringBuffer.toString();
    }

    public static String parseByte2HexStr1(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            stringBuffer.append(hexString.toUpperCase());
        }
        return stringBuffer.toString();
    }

    public static AccAttLog getPushLog(UserInfo userInfo, int i, int i2, int i3, String str, int i4) {
        AccAttLog accAttLog = new AccAttLog();
        String str2 = "";
        accAttLog.setUserPIN(userInfo != null ? userInfo.getUser_PIN() : str2);
        accAttLog.setInOutState(i);
        accAttLog.setTimeSecond(Utils.formatAccPushTime(str));
        accAttLog.setDoorID(i2);
        accAttLog.setEventType(i3);
        accAttLog.setVerified(i4);
        if (userInfo != null) {
            str2 = userInfo.getMain_Card();
        }
        accAttLog.setMainCard(str2);
        accAttLog.setMask_Flag(255);
        return accAttLog;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void pushRemoteAuth(java.lang.String r3, java.lang.String r4, java.lang.String r5, int r6, int r7, int r8, int r9, int r10) {
        /*
            r2 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "time="
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r3 = r0.append(r3)
            java.lang.String r0 = "\tpin="
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = "\tcardno="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r4 = "\taddrtype="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.String r4 = "\teventaddr="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r4 = "\tevent="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.String r4 = "\tinoutstatus="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r9)
            java.lang.String r4 = "\tverifytype="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r10)
            java.lang.String r3 = r3.toString()
            r4 = 0
            com.zkteco.android.core.sdk.HubProtocolManager r6 = r2.hubProtocolManager     // Catch:{ Exception -> 0x0077, all -> 0x0074 }
            long r6 = r6.convertPushInit()     // Catch:{ Exception -> 0x0077, all -> 0x0074 }
            com.zkteco.android.core.sdk.HubProtocolManager r8 = r2.hubProtocolManager     // Catch:{ Exception -> 0x0072 }
            r9 = 23
            r8.sendHubAction(r9, r6, r3)     // Catch:{ Exception -> 0x0072 }
            int r3 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x009a
        L_0x006c:
            com.zkteco.android.core.sdk.HubProtocolManager r3 = r2.hubProtocolManager
            r3.convertPushFree(r6)
            goto L_0x009a
        L_0x0072:
            r3 = move-exception
            goto L_0x0079
        L_0x0074:
            r3 = move-exception
            r6 = r4
            goto L_0x009c
        L_0x0077:
            r3 = move-exception
            r6 = r4
        L_0x0079:
            java.lang.String r8 = "Access-pushEvent"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x009b }
            r9.<init>()     // Catch:{ all -> 0x009b }
            java.lang.String r10 = "AccAttLog Exc: "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x009b }
            java.lang.String r3 = r3.getMessage()     // Catch:{ all -> 0x009b }
            java.lang.StringBuilder r3 = r9.append(r3)     // Catch:{ all -> 0x009b }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x009b }
            com.zktechnology.android.utils.LogUtils.e(r8, r3)     // Catch:{ all -> 0x009b }
            int r3 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x009a
            goto L_0x006c
        L_0x009a:
            return
        L_0x009b:
            r3 = move-exception
        L_0x009c:
            int r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x00a5
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r2.hubProtocolManager
            r4.convertPushFree(r6)
        L_0x00a5:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.push.acc.AccPush.pushRemoteAuth(java.lang.String, java.lang.String, java.lang.String, int, int, int, int, int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void pushOsdp(com.zkteco.android.db.orm.tna.AccAttLog r9) {
        /*
            r8 = this;
            java.lang.String r0 = r9.getUserPIN()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x000b
            return
        L_0x000b:
            r0 = 0
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0053, all -> 0x0050 }
            long r2 = r2.convertPushInit()     // Catch:{ Exception -> 0x0053, all -> 0x0050 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x004e }
            java.lang.String r5 = "MainCard"
            java.lang.String r6 = r9.getMainCard()     // Catch:{ Exception -> 0x004e }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x004e }
            java.lang.String r7 = ""
            if (r6 == 0) goto L_0x0025
            r6 = r7
            goto L_0x0029
        L_0x0025:
            java.lang.String r6 = r9.getMainCard()     // Catch:{ Exception -> 0x004e }
        L_0x0029:
            r4.setPushStrField(r2, r5, r6)     // Catch:{ Exception -> 0x004e }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x004e }
            java.lang.String r5 = "UserPIN"
            java.lang.String r6 = r9.getUserPIN()     // Catch:{ Exception -> 0x004e }
            r4.setPushStrField(r2, r5, r6)     // Catch:{ Exception -> 0x004e }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x004e }
            java.lang.String r5 = "TimeSecond"
            int r9 = r9.getTimeSecond()     // Catch:{ Exception -> 0x004e }
            r4.setPushIntField(r2, r5, r9)     // Catch:{ Exception -> 0x004e }
            com.zkteco.android.core.sdk.HubProtocolManager r9 = r8.hubProtocolManager     // Catch:{ Exception -> 0x004e }
            r4 = 24
            r9.sendHubAction(r4, r2, r7)     // Catch:{ Exception -> 0x004e }
            int r9 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r9 == 0) goto L_0x007a
            goto L_0x0075
        L_0x004e:
            r9 = move-exception
            goto L_0x0055
        L_0x0050:
            r9 = move-exception
            r2 = r0
            goto L_0x007c
        L_0x0053:
            r9 = move-exception
            r2 = r0
        L_0x0055:
            java.lang.String r4 = "Access-pushEvent"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x007b }
            r5.<init>()     // Catch:{ all -> 0x007b }
            java.lang.String r6 = "AccAttLog Exc: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x007b }
            java.lang.String r9 = r9.getMessage()     // Catch:{ all -> 0x007b }
            java.lang.StringBuilder r9 = r5.append(r9)     // Catch:{ all -> 0x007b }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x007b }
            com.zktechnology.android.utils.LogUtils.e(r4, r9)     // Catch:{ all -> 0x007b }
            int r9 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r9 == 0) goto L_0x007a
        L_0x0075:
            com.zkteco.android.core.sdk.HubProtocolManager r9 = r8.hubProtocolManager
            r9.convertPushFree(r2)
        L_0x007a:
            return
        L_0x007b:
            r9 = move-exception
        L_0x007c:
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x0085
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r8.hubProtocolManager
            r0.convertPushFree(r2)
        L_0x0085:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.push.acc.AccPush.pushOsdp(com.zkteco.android.db.orm.tna.AccAttLog):void");
    }
}
