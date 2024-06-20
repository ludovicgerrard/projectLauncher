package com.zktechnology.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.zktechnology.android.launcher2.DateUtil;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.orm.tna.OpLogs;
import com.zkteco.edk.camera.lib.ZkThreadPoolManager;
import java.sql.SQLException;

public class OpLogReceiver extends BroadcastReceiver {
    public static final String ACTION_ADD_FINGER_PRINT = "com.zkteco.oplog.action.ADD_FINGER_PRINT";
    public static final String ACTION_ALARM = "com.zkteco.oplog.action.ALARM";
    public static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    public static final String ACTION_CLEAR_ADMIN_PERMISSIONS = "com.zkteco.oplog.action.CLEAR_ADMIN_PERMISSIONS";
    public static final String ACTION_CLEAR_MF_CARD = "com.zkteco.oplog.action.CLEAR_MF_CARD";
    public static final String ACTION_CREATE_MF_CARD = "com.zkteco.oplog.action.CREATE_MF_CARD";
    public static final String ACTION_DELETE_ATT_RECORD = "com.zkteco.oplog.action.DELETE_ATT_RECORD";
    public static final String ACTION_DELETE_DATA = "com.zkteco.oplog.action.DELETE_DATA";
    public static final String ACTION_DELETE_FINGER_PRINT = "com.zkteco.oplog.action.DELETE_FINGER_PRINT";
    public static final String ACTION_DELETE_MF_CARD = "com.zkteco.oplog.action.DELETE_MF_CARD";
    public static final String ACTION_DELETE_PASSWORD = "com.zkteco.oplog.action.DELETE_PASSWORD";
    public static final String ACTION_DELETE_RF_CARD = "com.zkteco.oplog.action.DELETE_RF_CARD";
    public static final String ACTION_DELETE_USER = "com.zkteco.oplog.action.DELETE_USER";
    public static final String ACTION_DOWNLOAD_DATA_TO_UDISK = "com.zkteco.oplog.action.DOWNLOAD_DATA_TO_UDISK";
    public static final String ACTION_ENTER_MENU = "com.zkteco.oplog.action.ENTER_MENU";
    public static final String ACTION_MODIFY_ACCESS_TIME = "com.zkteco.oplog.action.MODIFY_ACCESS_TIME";
    public static final String ACTION_MODIFY_DOOR_ACCESS_GROUP = "com.zkteco.oplog.action.MODIFY_DOOR_ACCESS_GROUP";
    public static final String ACTION_MODIFY_FINGER_PRINT_ATTR = "com.zkteco.oplog.action.MODIFY_FINGER_PRINT_ATTR";
    public static final String ACTION_MODIFY_UNLOCKED_GROUP = "com.zkteco.oplog.action.MODIFY_UNLOCKED_GROUP";
    public static final String ACTION_MODIFY_USER_ACCESS_GROUP = "com.zkteco.oplog.action.MODIFY_USER_ACCESS_GROUP";
    public static final String ACTION_RECORD_MF_CARD = "com.zkteco.oplog.action.RECORD_MF_CARD";
    public static final String ACTION_REGISTER_MF_CARD = "com.zkteco.oplog.action.REGISTER_MF_CARD";
    public static final String ACTION_REGISTER_USER = "com.zkteco.oplog.action.REGISTER_USER";
    public static final String ACTION_RESET = "com.zkteco.oplog.action.RESET";
    public static final String ACTION_SETUP = "com.zkteco.oplog.action.SETUP";
    public static final String ACTION_SET_DATE = "com.zkteco.oplog.action.SET_DATE";
    public static final String ACTION_SET_HID_CARD = "com.zkteco.oplog.action.SET_HID_CARD";
    public static final String ACTION_SET_PASSWORD = "com.zkteco.oplog.action.SET_PASSWORD";
    public static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";
    public static final String ACTION_STRESS_ALARM = "com.zkteco.oplog.action.STRESS_ALARM";
    public static final String ACTION_UNLOCKED = "com.zkteco.oplog.action.UNLOCKED";
    public static final String ACTION_UPLOAD_DATA_TO_EQUIPMENT = "com.zkteco.oplog.action.UPLOAD_DATA_TO_EQUIPMENT";
    public static final String ACTION_VERIFICATIONFAILED = "com.zkteco.oplog.action.VERIFICATIONFAILED";
    HubProtocolManager hubProtocolManager = new HubProtocolManager(LauncherApplication.getLauncherApplicationContext());

    public static void sendBroadcast(Context context, Intent intent) {
        context.sendBroadcast(intent);
    }

    public void onReceive(Context context, Intent intent) {
        ZkThreadPoolManager.getInstance().execute(new Runnable(intent) {
            public final /* synthetic */ Intent f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                OpLogReceiver.this.lambda$onReceive$0$OpLogReceiver(this.f$1);
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: generateOpLogs */
    public void lambda$onReceive$0$OpLogReceiver(Intent intent) {
        Intent intent2 = intent;
        Log.d("OPLOG------>:", intent.getAction());
        String action = intent.getAction();
        OpLogs opLogs = new OpLogs();
        action.hashCode();
        char c2 = 65535;
        switch (action.hashCode()) {
            case -2111932517:
                if (action.equals(ACTION_UPLOAD_DATA_TO_EQUIPMENT)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1448192483:
                if (action.equals(ACTION_UNLOCKED)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1170958866:
                if (action.equals(ACTION_MODIFY_FINGER_PRINT_ATTR)) {
                    c2 = 2;
                    break;
                }
                break;
            case -923926318:
                if (action.equals(ACTION_VERIFICATIONFAILED)) {
                    c2 = 3;
                    break;
                }
                break;
            case -912141262:
                if (action.equals(ACTION_SET_PASSWORD)) {
                    c2 = 4;
                    break;
                }
                break;
            case -656851759:
                if (action.equals(ACTION_DELETE_FINGER_PRINT)) {
                    c2 = 5;
                    break;
                }
                break;
            case -506027721:
                if (action.equals(ACTION_ALARM)) {
                    c2 = 6;
                    break;
                }
                break;
            case -490519499:
                if (action.equals(ACTION_RESET)) {
                    c2 = 7;
                    break;
                }
                break;
            case -489594525:
                if (action.equals(ACTION_SETUP)) {
                    c2 = 8;
                    break;
                }
                break;
            case -391988156:
                if (action.equals(ACTION_DELETE_DATA)) {
                    c2 = 9;
                    break;
                }
                break;
            case -391464859:
                if (action.equals(ACTION_DELETE_USER)) {
                    c2 = 10;
                    break;
                }
                break;
            case -73695424:
                if (action.equals(ACTION_ENTER_MENU)) {
                    c2 = 11;
                    break;
                }
                break;
            case 44212789:
                if (action.equals(ACTION_DELETE_PASSWORD)) {
                    c2 = 12;
                    break;
                }
                break;
            case 90344422:
                if (action.equals(ACTION_DOWNLOAD_DATA_TO_UDISK)) {
                    c2 = 13;
                    break;
                }
                break;
            case 207385797:
                if (action.equals(ACTION_SET_DATE)) {
                    c2 = 14;
                    break;
                }
                break;
            case 398429098:
                if (action.equals(ACTION_MODIFY_DOOR_ACCESS_GROUP)) {
                    c2 = 15;
                    break;
                }
                break;
            case 717661000:
                if (action.equals(ACTION_CLEAR_ADMIN_PERMISSIONS)) {
                    c2 = 16;
                    break;
                }
                break;
            case 798292259:
                if (action.equals(ACTION_BOOT_COMPLETED)) {
                    c2 = 17;
                    break;
                }
                break;
            case 1859761385:
                if (action.equals(ACTION_DELETE_ATT_RECORD)) {
                    c2 = 18;
                    break;
                }
                break;
            case 1947666138:
                if (action.equals(ACTION_SHUTDOWN)) {
                    c2 = 19;
                    break;
                }
                break;
            case 1959496303:
                if (action.equals(ACTION_ADD_FINGER_PRINT)) {
                    c2 = 20;
                    break;
                }
                break;
            case 2060707053:
                if (action.equals(ACTION_REGISTER_USER)) {
                    c2 = 21;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                opLogs.setOpType(20);
                break;
            case 1:
                opLogs.setOpType(29);
                break;
            case 2:
                opLogs.setOpType(31);
                break;
            case 3:
                opLogs.setOpType(2);
                break;
            case 4:
                opLogs.setOpType(7);
                opLogs.setOpWho(intent2.getStringExtra("OpWho"));
                break;
            case 5:
                opLogs.setOpType(10);
                opLogs.setOpWho(intent2.getStringExtra("OpWho"));
                break;
            case 6:
                opLogs.setOpType(3);
                break;
            case 7:
                opLogs.setOpType(22);
                break;
            case 8:
                opLogs.setOpType(5);
                break;
            case 9:
                opLogs.setOpType(13);
                break;
            case 10:
                opLogs.setOpType(9);
                opLogs.setOpWho(intent2.getStringExtra("OpWho"));
                break;
            case 11:
                opLogs.setOpType(4);
                break;
            case 12:
                opLogs.setOpType(11);
                opLogs.setOpWho(intent2.getStringExtra("OpWho"));
                break;
            case 13:
                opLogs.setOpType(19);
                break;
            case 14:
                opLogs.setOpType(21);
                break;
            case 15:
                opLogs.setOpType(25);
                break;
            case 16:
                opLogs.setOpType(24);
                break;
            case 17:
                opLogs.setOpType(0);
                break;
            case 18:
                opLogs.setOpType(23);
                break;
            case 19:
                opLogs.setOpType(1);
                break;
            case 20:
                opLogs.setOpType(6);
                opLogs.setOpWho(intent2.getStringExtra("OpWho"));
                opLogs.setValue1(intent2.getIntExtra("Value1", 0));
                break;
            case 21:
                opLogs.setOpType(30);
                break;
        }
        opLogs.setOpTime(DateUtil.getDateTimeFromMillis(System.currentTimeMillis()));
        try {
            opLogs.create();
            try {
                sendOpLogs(opLogs);
            } catch (SQLException e) {
                e = e;
            }
        } catch (SQLException e2) {
            e = e2;
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendOpLogs(com.zkteco.android.db.orm.tna.OpLogs r9) {
        /*
            r8 = this;
            r0 = 0
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r8.hubProtocolManager     // Catch:{ Exception -> 0x008b, all -> 0x0088 }
            long r2 = r2.convertPushInit()     // Catch:{ Exception -> 0x008b, all -> 0x0088 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0086 }
            java.lang.String r5 = "OP_LOGS"
            r4.setPushTableName(r2, r5)     // Catch:{ Exception -> 0x0086 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0086 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0086 }
            r5.<init>()     // Catch:{ Exception -> 0x0086 }
            java.lang.String r6 = "ID="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0086 }
            long r6 = r9.getID()     // Catch:{ Exception -> 0x0086 }
            int r6 = (int) r6     // Catch:{ Exception -> 0x0086 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0086 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0086 }
            r4.setPushCon(r2, r5)     // Catch:{ Exception -> 0x0086 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0086 }
            java.lang.String r5 = "OpType"
            int r6 = r9.getOpType()     // Catch:{ Exception -> 0x0086 }
            r4.setPushIntField(r2, r5, r6)     // Catch:{ Exception -> 0x0086 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0086 }
            java.lang.String r5 = "Operator"
            java.lang.String r6 = r9.getOperator()     // Catch:{ Exception -> 0x0086 }
            r4.setPushStrField(r2, r5, r6)     // Catch:{ Exception -> 0x0086 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0086 }
            java.lang.String r5 = "OpTime"
            java.lang.String r6 = r9.getOpTime()     // Catch:{ Exception -> 0x0086 }
            r4.setPushStrField(r2, r5, r6)     // Catch:{ Exception -> 0x0086 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0086 }
            java.lang.String r5 = "OpWho"
            java.lang.String r6 = r9.getOpWho()     // Catch:{ Exception -> 0x0086 }
            r4.setPushStrField(r2, r5, r6)     // Catch:{ Exception -> 0x0086 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0086 }
            java.lang.String r5 = "Value1"
            int r6 = r9.getValue1()     // Catch:{ Exception -> 0x0086 }
            r4.setPushIntField(r2, r5, r6)     // Catch:{ Exception -> 0x0086 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0086 }
            java.lang.String r5 = "Value2"
            int r6 = r9.getValue2()     // Catch:{ Exception -> 0x0086 }
            r4.setPushIntField(r2, r5, r6)     // Catch:{ Exception -> 0x0086 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0086 }
            java.lang.String r5 = "Value3"
            int r9 = r9.getValue3()     // Catch:{ Exception -> 0x0086 }
            r4.setPushIntField(r2, r5, r9)     // Catch:{ Exception -> 0x0086 }
            com.zkteco.android.core.sdk.HubProtocolManager r9 = r8.hubProtocolManager     // Catch:{ Exception -> 0x0086 }
            r4 = 0
            java.lang.String r5 = ""
            r9.sendHubAction(r4, r2, r5)     // Catch:{ Exception -> 0x0086 }
            int r9 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r9 == 0) goto L_0x0099
            goto L_0x0094
        L_0x0086:
            r9 = move-exception
            goto L_0x008d
        L_0x0088:
            r9 = move-exception
            r2 = r0
            goto L_0x009b
        L_0x008b:
            r9 = move-exception
            r2 = r0
        L_0x008d:
            r9.printStackTrace()     // Catch:{ all -> 0x009a }
            int r9 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r9 == 0) goto L_0x0099
        L_0x0094:
            com.zkteco.android.core.sdk.HubProtocolManager r9 = r8.hubProtocolManager
            r9.convertPushFree(r2)
        L_0x0099:
            return
        L_0x009a:
            r9 = move-exception
        L_0x009b:
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto L_0x00a4
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r8.hubProtocolManager
            r0.convertPushFree(r2)
        L_0x00a4:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.receiver.OpLogReceiver.sendOpLogs(com.zkteco.android.db.orm.tna.OpLogs):void");
    }
}
