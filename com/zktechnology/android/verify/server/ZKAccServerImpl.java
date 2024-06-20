package com.zktechnology.android.verify.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import com.zktechnology.android.acc.DoorAccessManager;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.DoorVerifyCombination;
import com.zktechnology.android.door.ZkDoorManager;
import com.zktechnology.android.event.EventBusHelper;
import com.zktechnology.android.helper.DoorManagerHelper;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKEventLauncher;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.launcher2.ZkFaceLauncher;
import com.zktechnology.android.push.acc.AccPush;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.receiver.OpLogReceiver;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.ZkLogTag;
import com.zktechnology.android.verify.bean.process.ZKMarkTypeBean;
import com.zktechnology.android.verify.bean.process.ZKVerConMarkBean;
import com.zktechnology.android.verify.bean.process.ZKVerViewBean;
import com.zktechnology.android.verify.bean.process.ZKVerifyInfo;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zktechnology.android.verify.dialog.managment.ZKVerDlgMgt;
import com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog;
import com.zktechnology.android.verify.utils.VerifyTypeUtil;
import com.zktechnology.android.verify.utils.ZKCameraController;
import com.zktechnology.android.verify.utils.ZKConstantConfig;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zktechnology.android.verify.utils.ZKTemperatureUtil;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.db.orm.tna.ExtUser;
import com.zkteco.android.db.orm.tna.PhotoIndex;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import io.reactivex.rxjava3.disposables.Disposable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class ZKAccServerImpl extends BaseServerImpl {
    private static final int AM_TYPE_APP = 2;
    private static final int OVER_VALID_ADD_ATT_LOG = 1;
    private static final int OVER_VALID_DELETE_USER = 2;
    public static final int REGISTER_CARD = 2;
    public static final int REGISTER_FACE = 3;
    public static final int REGISTER_FINGER = 1;
    public static final int REGISTER_PW = 0;
    /* access modifiers changed from: private */
    public static final String TAG = "ZKAccServerImpl";
    public static final int USER_VT_GROUNP = -1;
    private DoorAccessResponse accResponse;
    private int faceVerFailedCount = 0;
    private boolean isCurrentVTRegister = false;
    private long lastVerifyTime = 0;
    private String lastVerifyUserPin = "";
    private final ZKAccDao mDao;
    Disposable mdDisposable;
    private long nowNum;
    RemoteAuthReceiver remoteAuthReceiver;
    private int replayTime;
    private int times = 0;
    private final ZKVerifyInfo verifyInfo;

    private boolean checkMultiVerify(int i) {
        return i == 5 || i == 6 || i == 8 || i == 9 || i == 10 || i == 12 || i == 13 || i == 14 || i == 16 || i == 19 || i == 20 || i == 11 || i == 17 || i == 18;
    }

    public void stateDelay() {
    }

    public ZKAccServerImpl() {
        ZKAccDao instance = ZKAccDao.getInstance(this.mContext);
        this.mDao = instance;
        setDB(instance);
        this.verifyInfo = new ZKVerifyInfo();
        initRemoteAuthReceiver();
    }

    private void initRemoteAuthReceiver() {
        this.remoteAuthReceiver = new RemoteAuthReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RemoteAuthReceiver.ACTION_REMOTE_AUTH_SUCCESS);
        intentFilter.addAction(RemoteAuthReceiver.ACTION_REMOTE_AUTH_FAILED);
        intentFilter.addAction(RemoteAuthReceiver.ACTION_REMOTE_AUTH_TIMEOUT);
        this.mContext.registerReceiver(this.remoteAuthReceiver, intentFilter);
    }

    public void stateIntent() {
        log("2.stateIntent]");
        FileLogUtils.writeTouchLog("stateIntent: " + ZKVerController.getInstance().getState());
        try {
            log("[stateIntent]: 开始");
            int intent = ZKVerProcessPar.CON_MARK_BEAN.getIntent();
            int i = ZKVerProcessPar.VERIFY_SOURCE_TYPE;
            if (intent == 0) {
                log("[stateIntent]: 验证");
                if (ZKVerProcessPar.CON_MARK_BEAN.getLastType() == null) {
                    log("[stateIntent]: 获取验证方式为空，重置流程，resetVerifyProcess");
                    resetVerifyProcess();
                    return;
                }
                ZKVerifyType fromInteger = ZKVerifyType.fromInteger(ZKVerProcessPar.CON_MARK_BEAN.getLastType().getType());
                if (fromInteger != null) {
                    int i2 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
                    if (i2 == 1) {
                        log("[stateIntent]: currentType is user pin");
                        if (i == 2) {
                            log("[stateIntent]: user pin if from WG, change to state user");
                            ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
                            return;
                        }
                        log("[stateIntent]:showInputPinDialog");
                        ZKTemperatureUtil.getInstance(this.mContext).needShowTemperature();
                        showInputPinDialog();
                    } else if (i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5) {
                        ZKTemperatureUtil.getInstance(this.mContext).needShowTemperature();
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
                        log("[stateIntent]: changeStateToUser");
                    } else {
                        log("[stateIntent]: default way ->do nothing");
                    }
                }
            } else if (intent == 1) {
                log("2.[stateIntent]: 打开菜单");
                if (this.mDao.hasSuperUsers()) {
                    log("[stateIntent]: has super user, showSelectVerifyTypeDialog ");
                    showSelectVerifyTypeDialog();
                    return;
                }
                log("[stateIntent]: no super user");
                EventBusHelper.post(this.mShowNoSuperDialog);
                changeStateToWait();
            } else if (intent != 2) {
                if (intent == 5) {
                    ZKTemperatureUtil.getInstance(this.mContext).needShowTemperature();
                    ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
                }
                log("[stateIntent]: default ,do nothing");
            } else {
                log("[stateIntent]: open app");
                if (this.mDao.hasSuperUsers()) {
                    log("[stateIntent]: has super user,showSelectVerifyTypeDialog");
                    showSelectVerifyTypeDialog();
                    return;
                }
                log("[stateIntent]: no super user");
                openApp();
                log("[stateIntent]: open app");
                ZKVerProcessPar.cleanData(15);
                log("[stateIntent]: cleanData");
                ZKVerProcessPar.cleanBtnWidget();
                log("[stateIntent]: cleanBtnWidget");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleException(e);
            log("[stateIntent]It happens a exception.We will reset verify Process and you can see the detail in trace/VerifyException.txt");
        }
    }

    private boolean checkVerifyType(int i, int i2) {
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        DoorVerifyCombination fromInteger2 = DoorVerifyCombination.fromInteger(i);
        boolean z = false;
        if (fromInteger2 != null) {
            List<ZKVerifyType> verifyTypes = fromInteger2.getVerifyTypes();
            int intent = ZKVerProcessPar.CON_MARK_BEAN.getIntent();
            int intOption = DBManager.getInstance().getIntOption("WiegandMethod", 0);
            if (intent == 5 && intOption == 0) {
                z = true;
            }
            if (verifyTypes.contains(fromInteger) || fromInteger == ZKVerifyType.PIN) {
                return true;
            }
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00e1, code lost:
        if (checkMultiVerify(r12) != false) goto L_0x00e3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void stateUser() {
        /*
            r27 = this;
            r8 = r27
            java.lang.String r0 = "3.stateUser: "
            r8.log(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "stateUser: "
            java.lang.StringBuilder r0 = r0.append(r1)
            com.zktechnology.android.verify.controller.ZKVerController r1 = com.zktechnology.android.verify.controller.ZKVerController.getInstance()
            com.zktechnology.android.verify.utils.ZKVerConState r1 = r1.getState()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.zktechnology.android.push.util.FileLogUtils.writeTouchLog(r0)
            com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog r0 = com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog.getInstance()     // Catch:{ Exception -> 0x0578 }
            r0.disViewAndClearData()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r0 = "[stateUser]: start"
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
            java.util.Date r0 = new java.util.Date     // Catch:{ Exception -> 0x0578 }
            r0.<init>()     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0578 }
            r9 = 0
            r10 = 1
            if (r1 == 0) goto L_0x0534
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r2 = r1.getLastType()     // Catch:{ Exception -> 0x0578 }
            if (r2 != 0) goto L_0x0044
            goto L_0x0534
        L_0x0044:
            int r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.VERIFY_SOURCE_TYPE     // Catch:{ Exception -> 0x0578 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0578 }
            r3.<init>()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r4 = "[stateUser]: verifyGenre = "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x0578 }
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0578 }
            r8.log(r3)     // Catch:{ Exception -> 0x0578 }
            int r3 = r1.getVerState()     // Catch:{ Exception -> 0x0578 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0578 }
            r4.<init>()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r5 = "[stateUser]: verifyState = "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0578 }
            java.lang.StringBuilder r4 = r4.append(r3)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0578 }
            r8.log(r4)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r4 = r1.getLastType()     // Catch:{ Exception -> 0x0578 }
            int r11 = r4.getType()     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r4 = com.zktechnology.android.verify.bean.process.ZKVerifyType.fromInteger(r11)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r5 = r8.verifyInfo     // Catch:{ Exception -> 0x0578 }
            r5.setVerifyType(r4)     // Catch:{ Exception -> 0x0578 }
            r5 = 2
            if (r3 != r5) goto L_0x0095
            android.os.Bundle r6 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            java.lang.String r7 = "bundle_user_info"
            java.io.Serializable r6 = r6.getSerializable(r7)     // Catch:{ Exception -> 0x0578 }
            com.zkteco.android.db.orm.tna.UserInfo r6 = (com.zkteco.android.db.orm.tna.UserInfo) r6     // Catch:{ Exception -> 0x0578 }
            goto L_0x0099
        L_0x0095:
            com.zkteco.android.db.orm.tna.UserInfo r6 = r8.getUserInfoByVerifyState(r1)     // Catch:{ Exception -> 0x0578 }
        L_0x0099:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0578 }
            r7.<init>()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r12 = "[stateUser]:getUserInfo success? "
            java.lang.StringBuilder r7 = r7.append(r12)     // Catch:{ Exception -> 0x0578 }
            if (r6 == 0) goto L_0x00a8
            r12 = r10
            goto L_0x00a9
        L_0x00a8:
            r12 = r9
        L_0x00a9:
            java.lang.StringBuilder r7 = r7.append(r12)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0578 }
            r8.log(r7)     // Catch:{ Exception -> 0x0578 }
            r7 = -1
            if (r6 == 0) goto L_0x00c8
            com.zkteco.android.db.orm.manager.DataManager r12 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r13 = "AccessPersonalVerification"
            int r12 = r12.getIntOption(r13, r9)     // Catch:{ Exception -> 0x0578 }
            if (r12 != r10) goto L_0x00c8
            int r12 = r6.getVerify_Type()     // Catch:{ Exception -> 0x0578 }
            goto L_0x00c9
        L_0x00c8:
            r12 = r7
        L_0x00c9:
            if (r12 != r7) goto L_0x00cd
            int r12 = com.zktechnology.android.launcher2.ZKLauncher.sDoor1VerifyType     // Catch:{ Exception -> 0x0578 }
        L_0x00cd:
            int r7 = r4.getValue()     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r13 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ Exception -> 0x0578 }
            int r13 = r13.getValue()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r14 = "bundle_1v1_pwd_retry"
            java.lang.String r15 = "bundle_1v1_retry"
            if (r7 == r13) goto L_0x00e3
            boolean r7 = r8.checkMultiVerify(r12)     // Catch:{ Exception -> 0x0578 }
            if (r7 == 0) goto L_0x00f3
        L_0x00e3:
            if (r3 == r5) goto L_0x00f3
            android.os.Bundle r7 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            int r13 = com.zktechnology.android.launcher2.ZKLauncher.sFPRetry     // Catch:{ Exception -> 0x0578 }
            r7.putInt(r15, r13)     // Catch:{ Exception -> 0x0578 }
            android.os.Bundle r7 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            int r13 = com.zktechnology.android.launcher2.ZKLauncher.sPWDRetry     // Catch:{ Exception -> 0x0578 }
            r7.putInt(r14, r13)     // Catch:{ Exception -> 0x0578 }
        L_0x00f3:
            int r13 = com.zktechnology.android.verify.utils.VerifyTypeUtil.getDoorVerifyType(r4, r12)     // Catch:{ Exception -> 0x0578 }
            r8.isCurrentVTRegister = r10     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r7 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE     // Catch:{ Exception -> 0x0578 }
            if (r4 != r7) goto L_0x00ff
            r7 = r10
            goto L_0x0100
        L_0x00ff:
            r7 = r9
        L_0x0100:
            if (r3 != r5) goto L_0x0106
            com.zkteco.android.db.orm.tna.UserInfo r6 = r8.getUserInfoByVerifyState(r1)     // Catch:{ Exception -> 0x0578 }
        L_0x0106:
            if (r6 == 0) goto L_0x010b
            r16 = r10
            goto L_0x010d
        L_0x010b:
            r16 = r9
        L_0x010d:
            r26 = r11
            r10 = 0
            if (r7 == 0) goto L_0x0233
            java.lang.String r7 = "[stateUser]: do verify repeat face recognize"
            r8.log(r7)     // Catch:{ Exception -> 0x0578 }
            if (r16 == 0) goto L_0x019c
            java.lang.String r7 = "[stateUser]: user exist"
            r8.log(r7)     // Catch:{ Exception -> 0x0578 }
            r8.faceVerFailedCount = r9     // Catch:{ Exception -> 0x0578 }
            r7 = r6
            long r5 = r8.lastVerifyTime     // Catch:{ Exception -> 0x0578 }
            int r5 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r5 == 0) goto L_0x0189
            java.lang.String r5 = ""
            java.lang.String r6 = r8.lastVerifyUserPin     // Catch:{ Exception -> 0x0578 }
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x0578 }
            if (r5 == 0) goto L_0x0133
            goto L_0x0189
        L_0x0133:
            java.lang.String r5 = r7.getUser_PIN()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r6 = r8.lastVerifyUserPin     // Catch:{ Exception -> 0x0578 }
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x0578 }
            if (r5 == 0) goto L_0x0176
            java.lang.String r5 = "[stateUser]:same face recognize"
            r8.log(r5)     // Catch:{ Exception -> 0x0578 }
            long r5 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x0578 }
            long r10 = r8.lastVerifyTime     // Catch:{ Exception -> 0x0578 }
            long r5 = r5 - r10
            long r5 = java.lang.Math.abs(r5)     // Catch:{ Exception -> 0x0578 }
            int r10 = com.zktechnology.android.launcher2.ZkFaceLauncher.DETECT_FACE_MIN_TIME_INTERVAL     // Catch:{ Exception -> 0x0578 }
            long r10 = (long) r10     // Catch:{ Exception -> 0x0578 }
            int r5 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r5 >= 0) goto L_0x0169
            java.lang.String r0 = "[stateUser]:face recognize in the repeat time zone"
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r0 = "[stateUser]:reset Verify Process"
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r0 = "识别人脸间隔太短，重置流程"
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
            r27.resetVerifyProcess()     // Catch:{ Exception -> 0x0578 }
            return
        L_0x0169:
            java.lang.String r5 = "[stateUser]:reset last verify time"
            r8.log(r5)     // Catch:{ Exception -> 0x0578 }
            long r5 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x0578 }
            r8.lastVerifyTime = r5     // Catch:{ Exception -> 0x0578 }
            goto L_0x0234
        L_0x0176:
            java.lang.String r5 = "[stateUser]:another person face recognize"
            r8.log(r5)     // Catch:{ Exception -> 0x0578 }
            long r5 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x0578 }
            r8.lastVerifyTime = r5     // Catch:{ Exception -> 0x0578 }
            java.lang.String r5 = r7.getUser_PIN()     // Catch:{ Exception -> 0x0578 }
            r8.lastVerifyUserPin = r5     // Catch:{ Exception -> 0x0578 }
            goto L_0x0234
        L_0x0189:
            long r5 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x0578 }
            r8.lastVerifyTime = r5     // Catch:{ Exception -> 0x0578 }
            java.lang.String r5 = r7.getUser_PIN()     // Catch:{ Exception -> 0x0578 }
            r8.lastVerifyUserPin = r5     // Catch:{ Exception -> 0x0578 }
            java.lang.String r5 = "[stateUser]: first face recognize"
            r8.log(r5)     // Catch:{ Exception -> 0x0578 }
            goto L_0x0234
        L_0x019c:
            r7 = r6
            int r5 = r8.faceVerFailedCount     // Catch:{ Exception -> 0x0578 }
            r6 = 1
            int r5 = r5 + r6
            r8.faceVerFailedCount = r5     // Catch:{ Exception -> 0x0578 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0578 }
            r5.<init>()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r6 = "[stateUser]:face recognize failed times is"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0578 }
            int r6 = r8.faceVerFailedCount     // Catch:{ Exception -> 0x0578 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0578 }
            r8.log(r5)     // Catch:{ Exception -> 0x0578 }
            int r5 = r8.faceVerFailedCount     // Catch:{ Exception -> 0x0578 }
            int r6 = com.zktechnology.android.launcher2.ZKLauncher.faceRecogizeMaxCount     // Catch:{ Exception -> 0x0578 }
            if (r5 < r6) goto L_0x0234
            java.lang.String r2 = "[stateUser]:face recognize failed times is 5,show user failed dialog"
            r8.log(r2)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r2 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0578 }
            r2.<init>()     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.dao.ZKAccDao r3 = r8.mDao     // Catch:{ Exception -> 0x0578 }
            int r3 = r3.getAccAttLogCount()     // Catch:{ Exception -> 0x0578 }
            int r4 = com.zktechnology.android.launcher2.ZKLauncher.sMaxAttLogCount     // Catch:{ Exception -> 0x0578 }
            if (r4 > r3) goto L_0x01d7
            r7 = 1
            goto L_0x01d8
        L_0x01d7:
            r7 = r9
        L_0x01d8:
            int r3 = r1.getIntent()     // Catch:{ Exception -> 0x0578 }
            r4 = 1
            if (r3 == r4) goto L_0x01ec
            boolean r3 = com.zktechnology.android.launcher2.ZKLauncher.sEnalbeIRTempDetection     // Catch:{ Exception -> 0x0578 }
            if (r3 != 0) goto L_0x01e8
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.enalbeMaskDetection     // Catch:{ Exception -> 0x0578 }
            if (r3 != 0) goto L_0x01e8
            goto L_0x01ec
        L_0x01e8:
            r8.unregisteredOpenDoor(r2, r7, r1, r0)     // Catch:{ Exception -> 0x0578 }
            goto L_0x0228
        L_0x01ec:
            r4 = 0
            r5 = 27
            r6 = 0
            r1 = r27
            r3 = r26
            r10 = 0
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0578 }
            r27.verifyFailAlarm()     // Catch:{ Exception -> 0x0578 }
            r27.playSoundTryAgain()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r1 = "[stateUser]:upload Acc Att Log"
            r8.log(r1)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0578 }
            int r1 = r1.getIntent()     // Catch:{ Exception -> 0x0578 }
            r2 = 1
            if (r1 == r2) goto L_0x0228
            com.zktechnology.android.verify.dao.ZKAccDao r1 = r8.mDao     // Catch:{ Exception -> 0x0578 }
            r17 = 0
            int r18 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState     // Catch:{ Exception -> 0x0578 }
            int r19 = com.zktechnology.android.launcher2.ZKLauncher.sDoorId     // Catch:{ Exception -> 0x0578 }
            r20 = 27
            java.lang.String r21 = r8.getVerifyTime(r0)     // Catch:{ Exception -> 0x0578 }
            r23 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            r25 = 0
            r16 = r1
            r22 = r13
            r16.addAccAttLog(r17, r18, r19, r20, r21, r22, r23, r25)     // Catch:{ Exception -> 0x0578 }
            r8.pictureBlack(r10, r0)     // Catch:{ Exception -> 0x0578 }
        L_0x0228:
            java.lang.String r0 = "[stateUser]:reset verify process"
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
            r8.faceVerFailedCount = r9     // Catch:{ Exception -> 0x0578 }
            r27.resetVerifyProcess()     // Catch:{ Exception -> 0x0578 }
            return
        L_0x0233:
            r7 = r6
        L_0x0234:
            r10 = 0
            java.lang.String r5 = "[stateUser]:do state user"
            r8.log(r5)     // Catch:{ Exception -> 0x0578 }
            if (r16 == 0) goto L_0x02d0
            boolean r5 = r8.isCurrentVTRegister     // Catch:{ Exception -> 0x0578 }
            if (r5 == 0) goto L_0x02d0
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ Exception -> 0x0578 }
            if (r4 != r2) goto L_0x0261
            boolean r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.KEY_BOARD_1V1     // Catch:{ Exception -> 0x0578 }
            if (r2 != 0) goto L_0x024b
            r2 = 2
            if (r3 != r2) goto L_0x0261
        L_0x024b:
            int r2 = com.zktechnology.android.launcher2.ZKLauncher.sFPRetry     // Catch:{ Exception -> 0x0578 }
            if (r2 == 0) goto L_0x0261
            android.os.Bundle r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            int r2 = r2.getInt(r15)     // Catch:{ Exception -> 0x0578 }
            if (r2 != 0) goto L_0x0261
            r2 = 1
            com.zktechnology.android.launcher2.ZKEventLauncher.setProcessDialogTimeOut((int) r2)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r0 = "[stateUser]:finger recognize,playSoundFlag = false"
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
            return
        L_0x0261:
            r11 = r26
            boolean r2 = r8.checkVerifyType(r12, r11)     // Catch:{ Exception -> 0x0578 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0578 }
            r3.<init>()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r4 = "[stateUser]:userInfo not null, checkUserValid: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x0578 }
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0578 }
            r8.log(r3)     // Catch:{ Exception -> 0x0578 }
            if (r2 != 0) goto L_0x02cb
            android.content.Context r2 = r8.mContext     // Catch:{ Exception -> 0x0578 }
            android.content.res.Resources r2 = r2.getResources()     // Catch:{ Exception -> 0x0578 }
            r3 = 2131755158(0x7f100096, float:1.9141187E38)
            java.lang.String r4 = r2.getString(r3)     // Catch:{ Exception -> 0x0578 }
            r27.playSoundTryAgain()     // Catch:{ Exception -> 0x0578 }
            int r1 = r1.getIntent()     // Catch:{ Exception -> 0x0578 }
            r2 = 1
            if (r1 == r2) goto L_0x02b4
            r8.pictureBlack(r10, r0)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.dao.ZKAccDao r1 = r8.mDao     // Catch:{ Exception -> 0x0578 }
            int r18 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState     // Catch:{ Exception -> 0x0578 }
            int r19 = com.zktechnology.android.launcher2.ZKLauncher.sDoorId     // Catch:{ Exception -> 0x0578 }
            r20 = 41
            java.lang.String r21 = r8.getVerifyTime(r0)     // Catch:{ Exception -> 0x0578 }
            int r22 = r8.convertVerifyType(r11)     // Catch:{ Exception -> 0x0578 }
            r23 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            r25 = 0
            r16 = r1
            r17 = r7
            r16.addAccAttLog(r17, r18, r19, r20, r21, r22, r23, r25)     // Catch:{ Exception -> 0x0578 }
        L_0x02b4:
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r2 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0578 }
            r2.<init>()     // Catch:{ Exception -> 0x0578 }
            r5 = 41
            r6 = 0
            r7 = 0
            r1 = r27
            r3 = r11
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0578 }
            r0 = 1
            com.zktechnology.android.launcher2.ZKEventLauncher.setProcessDialogTimeOut((int) r0)     // Catch:{ Exception -> 0x0578 }
            r27.verifyFailAlarm()     // Catch:{ Exception -> 0x0578 }
            return
        L_0x02cb:
            r8.checkUserValid(r7, r13)     // Catch:{ Exception -> 0x0578 }
            goto L_0x0584
        L_0x02d0:
            r11 = r26
            java.lang.String r5 = "[stateUser]:userInfo is null"
            r8.log(r5)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r5 = "[stateUser]:playSoundTryAgain"
            r12 = 27
            r6 = 2
            if (r2 != r6) goto L_0x0389
            java.lang.String r2 = "[stateUser]:verifyGenre is wg"
            r8.log(r2)     // Catch:{ Exception -> 0x0578 }
            int r2 = com.zktechnology.android.launcher2.ZKLauncher.enableUnregisterPass     // Catch:{ Exception -> 0x0578 }
            if (r2 != 0) goto L_0x0375
            r8.pictureBlack(r10, r0)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.wiegand.ZKWiegandManager r1 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()     // Catch:{ Exception -> 0x0578 }
            com.zkteco.android.zkcore.wiegand.enmutype.WiegandType r1 = r1.getWiegandTypeIn()     // Catch:{ Exception -> 0x0578 }
            com.zkteco.android.db.orm.tna.AccAttLog r2 = new com.zkteco.android.db.orm.tna.AccAttLog     // Catch:{ Exception -> 0x0578 }
            r2.<init>()     // Catch:{ Exception -> 0x0578 }
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState     // Catch:{ Exception -> 0x0578 }
            r4 = 1
            if (r3 != r4) goto L_0x02fd
            goto L_0x02fe
        L_0x02fd:
            r9 = 1
        L_0x02fe:
            r2.setInOutState(r9)     // Catch:{ Exception -> 0x0578 }
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0578 }
            java.lang.String r4 = "yyyy-MM-dd HH:mm:ss"
            java.util.Locale r6 = java.util.Locale.US     // Catch:{ Exception -> 0x0578 }
            r3.<init>(r4, r6)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r0 = r3.format(r0)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r3 = " "
            java.lang.String r4 = "T"
            java.lang.String r0 = r0.replace(r3, r4)     // Catch:{ Exception -> 0x0578 }
            int r0 = com.zktechnology.android.push.util.Utils.formatAccPushTime(r0)     // Catch:{ Exception -> 0x0578 }
            r2.setTimeSecond(r0)     // Catch:{ Exception -> 0x0578 }
            int[] r0 = com.zktechnology.android.verify.server.ZKAccServerImpl.AnonymousClass2.$SwitchMap$com$zkteco$android$zkcore$wiegand$enmutype$WiegandType     // Catch:{ Exception -> 0x0578 }
            int r1 = r1.ordinal()     // Catch:{ Exception -> 0x0578 }
            r0 = r0[r1]     // Catch:{ Exception -> 0x0578 }
            r1 = 1
            if (r0 == r1) goto L_0x0338
            r1 = 2
            if (r0 == r1) goto L_0x032c
            goto L_0x0343
        L_0x032c:
            android.os.Bundle r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            java.lang.String r1 = "bundle_card"
            java.lang.String r0 = r0.getString(r1)     // Catch:{ Exception -> 0x0578 }
            r2.setMainCard(r0)     // Catch:{ Exception -> 0x0578 }
            goto L_0x0343
        L_0x0338:
            android.os.Bundle r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            java.lang.String r1 = "bundle_pin"
            java.lang.String r0 = r0.getString(r1)     // Catch:{ Exception -> 0x0578 }
            r2.setUserPIN(r0)     // Catch:{ Exception -> 0x0578 }
        L_0x0343:
            r2.setEventType(r12)     // Catch:{ Exception -> 0x0578 }
            int r0 = r8.convertVerifyType(r11)     // Catch:{ Exception -> 0x0578 }
            r2.setVerified(r0)     // Catch:{ Exception -> 0x0578 }
            r0 = 255(0xff, float:3.57E-43)
            r2.setMask_Flag(r0)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r0 = "launcher push: stateUser"
            com.zktechnology.android.push.util.FileLogUtils.writeVerifyLog(r0)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.push.acc.AccPush r0 = com.zktechnology.android.push.acc.AccPush.getInstance()     // Catch:{ Exception -> 0x0578 }
            r0.push(r2)     // Catch:{ Exception -> 0x0578 }
            r27.playSoundTryAgain()     // Catch:{ Exception -> 0x0578 }
            r8.log(r5)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r2 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0578 }
            r2.<init>()     // Catch:{ Exception -> 0x0578 }
            r4 = 0
            r5 = 27
            r6 = 0
            r7 = 0
            r1 = r27
            r3 = r11
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0578 }
            goto L_0x0380
        L_0x0375:
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r2 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0578 }
            r2.<init>()     // Catch:{ Exception -> 0x0578 }
            r8.unregisteredOpenDoor(r2, r9, r1, r0)     // Catch:{ Exception -> 0x0578 }
            r27.resetVerifyProcess()     // Catch:{ Exception -> 0x0578 }
        L_0x0380:
            r27.verifyFailAlarm()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r0 = "[stateUser]:showUserFailedDialog"
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
            return
        L_0x0389:
            r6 = 3
            if (r2 != r6) goto L_0x039c
            com.zktechnology.android.rs232.ZKRS232EncryptManager r2 = com.zktechnology.android.rs232.ZKRS232EncryptManager.getInstance()     // Catch:{ Exception -> 0x0578 }
            r2.failedCmd()     // Catch:{ Exception -> 0x0578 }
            android.content.Context r2 = r8.mContext     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.rs485.RS485Manager r2 = com.zktechnology.android.rs485.RS485Manager.getInstance(r2)     // Catch:{ Exception -> 0x0578 }
            r2.failedCmd()     // Catch:{ Exception -> 0x0578 }
        L_0x039c:
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE     // Catch:{ Exception -> 0x0578 }
            if (r4 != r2) goto L_0x03b5
            r2 = 2
            if (r3 == r2) goto L_0x03b5
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0578 }
            int r2 = r2.getIntent()     // Catch:{ Exception -> 0x0578 }
            r6 = 1
            if (r2 == r6) goto L_0x03b5
            java.lang.String r0 = "[stateUser]:fist face recognize ,resetVerifyProcess"
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
            r27.resetVerifyProcess()     // Catch:{ Exception -> 0x0578 }
            return
        L_0x03b5:
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ Exception -> 0x0578 }
            if (r4 != r2) goto L_0x042a
            boolean r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.KEY_BOARD_1V1     // Catch:{ Exception -> 0x0578 }
            if (r2 != 0) goto L_0x03c0
            r2 = 2
            if (r3 != r2) goto L_0x042a
        L_0x03c0:
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0578 }
            int r1 = r1.getIntent()     // Catch:{ Exception -> 0x0578 }
            r2 = 1
            if (r1 == r2) goto L_0x03d5
            android.os.Bundle r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            android.os.Bundle r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            int r3 = r3.getInt(r15)     // Catch:{ Exception -> 0x0578 }
            int r3 = r3 - r2
            r1.putInt(r15, r3)     // Catch:{ Exception -> 0x0578 }
        L_0x03d5:
            int r1 = com.zktechnology.android.launcher2.ZKLauncher.sFPRetry     // Catch:{ Exception -> 0x0578 }
            if (r1 == 0) goto L_0x0417
            android.os.Bundle r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            int r1 = r1.getInt(r15)     // Catch:{ Exception -> 0x0578 }
            if (r1 != 0) goto L_0x0417
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r2 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0578 }
            r2.<init>()     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ Exception -> 0x0578 }
            int r3 = r1.getValue()     // Catch:{ Exception -> 0x0578 }
            r4 = 0
            r5 = 27
            r6 = 0
            r7 = 0
            r1 = r27
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0578 }
            r1 = 1
            com.zktechnology.android.launcher2.ZKEventLauncher.setProcessDialogTimeOut((int) r1)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0578 }
            int r2 = r2.getIntent()     // Catch:{ Exception -> 0x0578 }
            if (r2 == r1) goto L_0x0409
            com.zktechnology.android.push.acc.AccPush r1 = com.zktechnology.android.push.acc.AccPush.getInstance()     // Catch:{ Exception -> 0x0578 }
            r1.pushUnregisterEvent(r12, r11, r0)     // Catch:{ Exception -> 0x0578 }
        L_0x0409:
            r27.playSoundTryAgain()     // Catch:{ Exception -> 0x0578 }
            r27.resetVerifyProcess()     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.wiegand.ZKWiegandManager r0 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()     // Catch:{ Exception -> 0x0578 }
            r0.wiegandOutFailedID()     // Catch:{ Exception -> 0x0578 }
            return
        L_0x0417:
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r2 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0578 }
            r2.<init>()     // Catch:{ Exception -> 0x0578 }
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r1 = r27
            r3 = r11
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0578 }
            r27.playSoundTryAgain()     // Catch:{ Exception -> 0x0578 }
            return
        L_0x042a:
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD     // Catch:{ Exception -> 0x0578 }
            if (r4 != r2) goto L_0x04a5
            boolean r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.KEY_BOARD_1V1     // Catch:{ Exception -> 0x0578 }
            if (r2 != 0) goto L_0x0435
            r2 = 2
            if (r3 != r2) goto L_0x04a5
        L_0x0435:
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0578 }
            int r1 = r1.getIntent()     // Catch:{ Exception -> 0x0578 }
            r2 = 1
            if (r1 == r2) goto L_0x044a
            android.os.Bundle r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            android.os.Bundle r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            int r3 = r3.getInt(r14)     // Catch:{ Exception -> 0x0578 }
            int r3 = r3 - r2
            r1.putInt(r14, r3)     // Catch:{ Exception -> 0x0578 }
        L_0x044a:
            android.os.Bundle r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            int r1 = r1.getInt(r14)     // Catch:{ Exception -> 0x0578 }
            if (r1 != 0) goto L_0x048b
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r2 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0578 }
            r2.<init>()     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ Exception -> 0x0578 }
            int r3 = r1.getValue()     // Catch:{ Exception -> 0x0578 }
            r4 = 0
            r5 = 27
            r6 = 0
            r7 = 0
            r1 = r27
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0578 }
            r1 = 1
            com.zktechnology.android.launcher2.ZKEventLauncher.setProcessDialogTimeOut((int) r1)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0578 }
            int r2 = r2.getIntent()     // Catch:{ Exception -> 0x0578 }
            if (r2 == r1) goto L_0x047d
            r8.pictureBlack(r10, r0)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.push.acc.AccPush r1 = com.zktechnology.android.push.acc.AccPush.getInstance()     // Catch:{ Exception -> 0x0578 }
            r1.pushUnregisterEvent(r12, r11, r0)     // Catch:{ Exception -> 0x0578 }
        L_0x047d:
            r27.playSoundTryAgain()     // Catch:{ Exception -> 0x0578 }
            r27.resetVerifyProcess()     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.wiegand.ZKWiegandManager r0 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()     // Catch:{ Exception -> 0x0578 }
            r0.wiegandOutFailedID()     // Catch:{ Exception -> 0x0578 }
            return
        L_0x048b:
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r2 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0578 }
            r2.<init>()     // Catch:{ Exception -> 0x0578 }
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r1 = r27
            r3 = r11
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r0 = r8.verifyInfo     // Catch:{ Exception -> 0x0578 }
            r1 = 0
            r0.setLastMultipleVerifyTime(r1)     // Catch:{ Exception -> 0x0578 }
            r27.playSoundTryAgain()     // Catch:{ Exception -> 0x0578 }
            return
        L_0x04a5:
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r12 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0578 }
            r12.<init>()     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.dao.ZKAccDao r2 = r8.mDao     // Catch:{ Exception -> 0x0578 }
            int r2 = r2.getAccAttLogCount()     // Catch:{ Exception -> 0x0578 }
            int r4 = com.zktechnology.android.launcher2.ZKLauncher.sMaxAttLogCount     // Catch:{ Exception -> 0x0578 }
            if (r4 > r2) goto L_0x04b5
            r9 = 1
        L_0x04b5:
            int r2 = r1.getIntent()     // Catch:{ Exception -> 0x0578 }
            r4 = 1
            if (r2 == r4) goto L_0x04d3
            int r2 = com.zktechnology.android.launcher2.ZKLauncher.enableUnregisterPass     // Catch:{ Exception -> 0x0578 }
            if (r2 == 0) goto L_0x04d3
            r2 = 2
            if (r3 == r2) goto L_0x04d3
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ Exception -> 0x0578 }
            int r2 = r2.getValue()     // Catch:{ Exception -> 0x0578 }
            if (r11 != r2) goto L_0x04cc
            goto L_0x04d3
        L_0x04cc:
            r8.unregisteredOpenDoor(r12, r9, r1, r0)     // Catch:{ Exception -> 0x0578 }
            r27.resetVerifyProcess()     // Catch:{ Exception -> 0x0578 }
            goto L_0x0527
        L_0x04d3:
            r27.playSoundTryAgain()     // Catch:{ Exception -> 0x0578 }
            r8.log(r5)     // Catch:{ Exception -> 0x0578 }
            r1 = 2
            if (r3 != r1) goto L_0x04ed
            boolean r0 = r8.checkVerifyTempRegisterState(r11)     // Catch:{ Exception -> 0x0578 }
            if (r0 != 0) goto L_0x0511
            r0 = 2131755296(0x7f100120, float:1.9141467E38)
            java.lang.String r0 = com.zktechnology.android.utils.AppUtils.getString(r0)     // Catch:{ Exception -> 0x0578 }
            r12.setUiWayLogin(r0)     // Catch:{ Exception -> 0x0578 }
            goto L_0x0511
        L_0x04ed:
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0578 }
            int r1 = r1.getIntent()     // Catch:{ Exception -> 0x0578 }
            r6 = 1
            if (r1 == r6) goto L_0x0511
            com.zktechnology.android.push.acc.AccPush r1 = com.zktechnology.android.push.acc.AccPush.getInstance()     // Catch:{ Exception -> 0x0578 }
            r2 = 27
            int r4 = r8.convertVerifyType(r11)     // Catch:{ Exception -> 0x0578 }
            r5 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            android.os.Bundle r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0578 }
            java.lang.String r7 = "bundle_wear_mask"
            int r7 = r3.getInt(r7)     // Catch:{ Exception -> 0x0578 }
            r3 = r0
            r1.pushIllegalCardRealEvent(r2, r3, r4, r5, r7)     // Catch:{ Exception -> 0x0578 }
            r8.pictureBlack(r10, r0)     // Catch:{ Exception -> 0x0578 }
        L_0x0511:
            r4 = 0
            r5 = 27
            r6 = 0
            r1 = r27
            r2 = r12
            r3 = r11
            r7 = r9
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.wiegand.ZKWiegandManager r0 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()     // Catch:{ Exception -> 0x0578 }
            r0.wiegandOutFailedID()     // Catch:{ Exception -> 0x0578 }
            r27.verifyFailAlarm()     // Catch:{ Exception -> 0x0578 }
        L_0x0527:
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r0 = r8.verifyInfo     // Catch:{ Exception -> 0x0578 }
            r0.setGroupUserList(r10)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r0 = r8.verifyInfo     // Catch:{ Exception -> 0x0578 }
            r1 = 0
            r0.setLastMultipleVerifyTime(r1)     // Catch:{ Exception -> 0x0578 }
            goto L_0x0584
        L_0x0534:
            r6 = r10
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0578 }
            r0.<init>()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r2 = "[stateUser]: parBean == null?"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Exception -> 0x0578 }
            if (r1 != 0) goto L_0x0544
            r2 = r6
            goto L_0x0545
        L_0x0544:
            r2 = r9
        L_0x0545:
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0578 }
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
            if (r1 == 0) goto L_0x056f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0578 }
            r0.<init>()     // Catch:{ Exception -> 0x0578 }
            java.lang.String r2 = "[stateUser]: parBean.getLastType() == null?"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Exception -> 0x0578 }
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r1 = r1.getLastType()     // Catch:{ Exception -> 0x0578 }
            if (r1 != 0) goto L_0x0564
            r9 = r6
        L_0x0564:
            java.lang.StringBuilder r0 = r0.append(r9)     // Catch:{ Exception -> 0x0578 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0578 }
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
        L_0x056f:
            java.lang.String r0 = "[stateUser]: resetVerifyProcess"
            r8.log(r0)     // Catch:{ Exception -> 0x0578 }
            r27.resetVerifyProcess()     // Catch:{ Exception -> 0x0578 }
            return
        L_0x0578:
            r0 = move-exception
            r0.printStackTrace()
            r8.handleException(r0)
            java.lang.String r0 = "[stateUser]It happens a exception.We will reset verify Process and you can see the detail in trace/VerifyException.txt"
            r8.log(r0)
        L_0x0584:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.server.ZKAccServerImpl.stateUser():void");
    }

    /* renamed from: com.zktechnology.android.verify.server.ZKAccServerImpl$2  reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType;
        static final /* synthetic */ int[] $SwitchMap$com$zkteco$android$zkcore$wiegand$enmutype$WiegandType;

        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Can't wrap try/catch for region: R(21:0|1|2|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x002e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0038 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0043 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x004e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0059 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0064 */
        static {
            /*
                com.zkteco.android.zkcore.wiegand.enmutype.WiegandType[] r0 = com.zkteco.android.zkcore.wiegand.enmutype.WiegandType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$zkteco$android$zkcore$wiegand$enmutype$WiegandType = r0
                r1 = 1
                com.zkteco.android.zkcore.wiegand.enmutype.WiegandType r2 = com.zkteco.android.zkcore.wiegand.enmutype.WiegandType.PIN     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                r0 = 2
                int[] r2 = $SwitchMap$com$zkteco$android$zkcore$wiegand$enmutype$WiegandType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zkteco.android.zkcore.wiegand.enmutype.WiegandType r3 = com.zkteco.android.zkcore.wiegand.enmutype.WiegandType.CARD_NUM     // Catch:{ NoSuchFieldError -> 0x001d }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                com.zktechnology.android.verify.bean.process.ZKVerifyType[] r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType = r2
                com.zktechnology.android.verify.bean.process.ZKVerifyType r3 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ NoSuchFieldError -> 0x002e }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x002e }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x002e }
            L_0x002e:
                int[] r1 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0038 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ NoSuchFieldError -> 0x0038 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0038 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0038 }
            L_0x0038:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0043 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.CARD     // Catch:{ NoSuchFieldError -> 0x0043 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0043 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0043 }
            L_0x0043:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x004e }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PALM     // Catch:{ NoSuchFieldError -> 0x004e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004e }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004e }
            L_0x004e:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0059 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE     // Catch:{ NoSuchFieldError -> 0x0059 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0059 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0059 }
            L_0x0059:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0064 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD     // Catch:{ NoSuchFieldError -> 0x0064 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0064 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0064 }
            L_0x0064:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x006f }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.NONE     // Catch:{ NoSuchFieldError -> 0x006f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006f }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006f }
            L_0x006f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.server.ZKAccServerImpl.AnonymousClass2.<clinit>():void");
        }
    }

    private void sendBro(DoorAccessResponse doorAccessResponse) {
        Intent intent = new Intent();
        intent.setAction(ZkFaceLauncher.CHANGE_TEMPERATURE_UI);
        intent.putExtra("temperature", doorAccessResponse.getTemperature());
        intent.putExtra("mask", doorAccessResponse.getWearMask());
        this.mContext.sendBroadcast(intent);
    }

    private void unregisteredOpenDoor(ZKVerViewBean zKVerViewBean, boolean z, ZKVerConMarkBean zKVerConMarkBean, Date date) {
        boolean z2;
        DoorAccessResponse openDoor = ZkDoorManager.getInstance(this.mContext).openDoor(this.mDao, (UserInfo) null, this.verifyInfo, date);
        sendBro(openDoor);
        if (openDoor.getErrorCode() == 1001) {
            resetVerifyProcess();
            return;
        }
        if (ZKLauncher.enableUnregisterPass == 1 && ZKLauncher.enableUnregisterCapture == 1) {
            boolean isEmpty = TextUtils.isEmpty(openDoor.getErrorMessage());
            if (ZKLauncher.sCameraSystem != 2 && (ZKLauncher.sCameraSystem != 3 ? ZKLauncher.sCameraSystem != 4 || isEmpty : !isEmpty)) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                if (ZKLauncher.sMaxBlackPhotoCount <= this.mDao.getBlackPhotoCount()) {
                    if (ZKLauncher.mLoopDeleteBlackPic > 0) {
                        this.mDao.deleteBlackPhoto(ZKLauncher.mLoopDeleteBlackPic);
                        if (isEmpty) {
                            picture((String) null, date, true, 3);
                        } else {
                            picture((String) null, date, false, 4);
                        }
                    }
                } else if (isEmpty) {
                    picture((String) null, date, true, 3);
                } else {
                    picture((String) null, date, false, 4);
                }
            }
        } else if (ZKLauncher.enableUnregisterPass == 0 && openDoor.getErrorCode() != 27) {
            pictureBlack((String) null, date);
        }
        if (TextUtils.isEmpty(openDoor.getErrorMessage())) {
            AccPush.getInstance().pushRealEvent(0, date, convertVerifyType(zKVerConMarkBean.getLastType().getType()), openDoor.getTemperature(), openDoor.getWearMask());
            zKVerViewBean.setUiSpecialState(this.mContext.getResources().getString(R.string.person_not_registered));
            if (!ZKLauncher.sIRTempDetectionFunOn || !ZKLauncher.sEnalbeIRTempDetection) {
                playSoundOk("");
            }
            showUserSuccessDialog(zKVerViewBean, zKVerConMarkBean.getLastType().getType());
        } else if (openDoor.getErrorCode() == 68 || openDoor.getErrorCode() == 69) {
            showUserFailedDialog(zKVerViewBean, zKVerConMarkBean.getLastType().getType(), openDoor.getErrorMessage(), openDoor.getErrorCode(), false, z);
        }
    }

    private int convertVerifyType(int i) {
        if (ZKVerifyType.PIN.getValue() == i) {
            return 2;
        }
        if (ZKVerifyType.PASSWORD.getValue() == i) {
            return 3;
        }
        if (ZKVerifyType.FINGER.getValue() == i) {
            return 1;
        }
        if (ZKVerifyType.CARD.getValue() == i) {
            return 4;
        }
        if (ZKVerifyType.FACE.getValue() == i) {
            return 15;
        }
        return ZKVerifyType.PALM.getValue() == i ? 25 : 0;
    }

    private void verifyFailAlarm() {
        try {
            int intOption = DBManager.getInstance().getIntOption("ERRTimes", 0);
            if (intOption != 0) {
                int i = this.times;
                if (i == 0) {
                    this.times = i + 1;
                } else {
                    if (SystemClock.elapsedRealtime() - this.nowNum <= ((long) (DBManager.getInstance().getIntOption("ErrTimeInterval", 8) * 1000))) {
                        int i2 = this.times + 1;
                        this.times = i2;
                        if (i2 == intOption) {
                            if (DBManager.getInstance().getIntOption(ZKDBConfig.OPT_EXT_ALARM_ON, 0) == 1) {
                                DoorManagerHelper.getInstance().playAlarm();
                                Intent intent = new Intent();
                                intent.setAction(DoorAccessManager.ACTION_ALARM_OPEN);
                                this.mContext.sendBroadcast(intent);
                            }
                            this.times = 0;
                        }
                    } else {
                        this.times = 1;
                    }
                }
                this.nowNum = SystemClock.elapsedRealtime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UserInfo getUserInfoByVerifyState(ZKVerConMarkBean zKVerConMarkBean) {
        UserInfo userInfo;
        ExtUser extUser;
        UserInfo userInfo2;
        int verState = zKVerConMarkBean.getVerState();
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(zKVerConMarkBean.getLastType().getType());
        if (verState != 0 && verState != 1) {
            if (verState == 2 && (userInfo2 = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO)) != null) {
                switch (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()]) {
                    case 1:
                        userInfo = getMultiUserInfoByPin(userInfo2);
                        break;
                    case 2:
                        userInfo = getMultiUserInfoByFinger(userInfo2);
                        break;
                    case 3:
                        userInfo = getMultiUserInfoByCard(userInfo2);
                        break;
                    case 4:
                        userInfo = getMultiUserInfoByPalm(userInfo2);
                        break;
                    case 5:
                        userInfo = getMultiUserInfoByFace(userInfo2);
                        break;
                    case 6:
                        userInfo = getMultiUserInfoByPassword(userInfo2);
                        break;
                }
            }
        } else {
            switch (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()]) {
                case 1:
                    userInfo = getUserInfoByPin();
                    break;
                case 2:
                    userInfo = getUserInfoByFingerprint(ZKLauncher.mMThreshold);
                    break;
                case 3:
                    userInfo = getUserInfoByCard();
                    break;
                case 4:
                    userInfo = getUserInfoByPalm();
                    break;
                case 5:
                    userInfo = getUserInfoByFace();
                    break;
                case 6:
                    userInfo = getUserInfoByPassword();
                    break;
            }
        }
        userInfo = null;
        if (userInfo != null) {
            LogUtils.d(LogUtils.TAG_VERIFY, "UserInfo<name=%s,pin=%s>", userInfo.getName(), userInfo.getUser_PIN());
        } else {
            LogUtils.e(LogUtils.TAG_VERIFY, "Error: UserInfo == null !");
        }
        if (userInfo != null) {
            try {
                if (!TextUtils.isEmpty(userInfo.getUser_PIN()) && ((ZKLauncher.longName == 1 || TextUtils.isEmpty(userInfo.getName())) && (extUser = (ExtUser) new ExtUser().getQueryBuilder().where().eq("Pin", userInfo.getUser_PIN()).queryForFirst()) != null)) {
                    userInfo.setName(extUser.getFirstName() + " " + extUser.getLastName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }

    /* access modifiers changed from: protected */
    public UserInfo getMultiUserInfoByFinger(UserInfo userInfo) {
        if (ZKVerProcessPar.KEY_BOARD_1V1 && ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_1V1_RETRY) == 0 && ZKLauncher.sFPRetry == 0) {
            playSoundTryAgain();
            ZKVerDlgMgt.pop();
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            FileLogUtils.writeTouchLog("setTTouchAction: getMultiUserInfoByFinger");
        }
        UserInfo userInfoByFingerprint = getUserInfoByFingerprint(ZKLauncher.mVThreshold);
        if (userInfoByFingerprint == null || userInfo == null || !userInfoByFingerprint.getUser_PIN().equals(userInfo.getUser_PIN())) {
            int i = this.replayTime - 1;
            this.replayTime = i;
            if (i == 0 && (ZKLauncher.sCameraSystem == 2 || ZKLauncher.sCameraSystem == 4)) {
                pictureBlack((String) null, new Date());
            }
            this.isCurrentVTRegister = false;
            LogUtils.e(LogUtils.TAG_VERIFY, "UserTem<name=%s,pin=%s> 未注册 指纹", userInfo.getName(), userInfo.getUser_PIN());
            return userInfoByFingerprint;
        }
        this.isCurrentVTRegister = true;
        LogUtils.d(LogUtils.TAG_VERIFY, "Multi-指纹 比对成功");
        return userInfo;
    }

    public void stateWay() {
        log("4.stateWay: ");
        FileLogUtils.writeTouchLog("stateWay: " + ZKVerController.getInstance().getState());
        try {
            log("[stateWay]start");
            UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
            log("[stateWay] get userInfo");
            ZKVerConMarkBean zKVerConMarkBean = ZKVerProcessPar.CON_MARK_BEAN;
            log("[stateWay] get CON_MARK_BEAN");
            boolean z = false;
            log("[stateWay] userInfo == null?" + (userInfo == null));
            log("[stateWay] parBean == null?" + (zKVerConMarkBean == null));
            if (!(zKVerConMarkBean == null || zKVerConMarkBean.getLastType() == null)) {
                log("[stateWay] parBean.getLastType().getType() == " + zKVerConMarkBean.getLastType().getType());
            }
            if (!(userInfo == null || zKVerConMarkBean == null)) {
                if (zKVerConMarkBean.getLastType() != null) {
                    int verifyType = this.zkVerOption.getVerifyType(this.mContext);
                    log("[stateWay] get needVerifyType  == " + verifyType);
                    int type = zKVerConMarkBean.getLastType().getType();
                    log("[stateWay] get currentVerifyType   == " + type);
                    int size = zKVerConMarkBean.getVerifyTypeList().size();
                    log(String.format("doorVerifyType = %s,currentType = %s,addSize = %s", new Object[]{Integer.valueOf(verifyType), Integer.valueOf(type), Integer.valueOf(size)}));
                    ArrayList<Boolean> registeredVerifyType = getRegisteredVerifyType(userInfo);
                    this.replayTime = ZKLauncher.sFPRetry;
                    log("[stateWay] get RegisteredVerifyType = " + registeredVerifyType.size());
                    Date date = new Date();
                    if (!checkNeedVerifyTypeIsRegistered(userInfo, registeredVerifyType, date)) {
                        log("[stateWay] checkNeedVerifyTypeIsRegistered ,do resetVerifyProcess");
                        pictureBlack((String) null, date);
                        resetVerifyProcess();
                        return;
                    }
                    if (ZKVerProcessPar.KEY_BOARD_1V1 && size == 1) {
                        z = true;
                    }
                    if (z) {
                        log("[stateWay] is1v1Verify start");
                        if (this.zkVerOption.getVerifyType(this.mContext) == ZKVerifyType.PIN.getValue()) {
                            log("[stateWay] verify pin success ,change State To Action");
                            changeStateToAction();
                        } else {
                            log("[stateWay] verify pin failed ,show Check 1v1 Verify Type Dialog");
                            showCheck1v1VerifyTypeDialog(registeredVerifyType);
                            return;
                        }
                    }
                    log("[stateWay] start Verify Router");
                    startVerifyRouter(verifyType, type, size, registeredVerifyType);
                    return;
                }
            }
            log("[stateWay] userInfo == null?" + (userInfo == null));
            StringBuilder append = new StringBuilder().append("[stateWay] parBean == null?");
            if (zKVerConMarkBean == null) {
                z = true;
            }
            log(append.append(z).toString());
            log("[stateWay] resetVerifyProcess");
            resetVerifyProcess();
        } catch (Exception e) {
            e.printStackTrace();
            handleException(e);
            log("[stateWay]It happens a exception.We will reset verify Process and you can see the detail in trace/VerifyException.txt");
        }
    }

    public void stateAction() {
        log("5.stateAction: ");
        FileLogUtils.writeTouchLog("stateAction: " + ZKVerController.getInstance().getState());
        try {
            log("[stateAction] start ");
            UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
            ZKVerConMarkBean zKVerConMarkBean = ZKVerProcessPar.CON_MARK_BEAN;
            boolean z = true;
            if (zKVerConMarkBean != null) {
                if (userInfo != null) {
                    int intent = zKVerConMarkBean.getIntent();
                    int type = zKVerConMarkBean.getLastType().getType();
                    log("[stateAction] 当前验证方式 " + type);
                    if (intent == 0) {
                        log("[stateAction] intent is 验证 ");
                        if (isWidgetPressed()) {
                            log("[stateAction] isPressedWidget");
                            startBtnWidgetAction(userInfo, type);
                            return;
                        }
                        changeStateToRecord();
                        return;
                    } else if (intent != 1) {
                        changeStateToRecord();
                        return;
                    } else {
                        log("stateAction: INTENT_OPEN_MENU 打开菜单");
                        ZKEventLauncher.setProcessDialogVisibility(false);
                        openMenu();
                        changeStateToWait();
                        return;
                    }
                }
            }
            StringBuilder append = new StringBuilder().append("[stateAction]parBean == null?").append(zKVerConMarkBean == null).append(" null == userInfo?");
            if (userInfo != null) {
                z = false;
            }
            log(append.append(z).append(",do resetVerifyProcess").toString());
            resetVerifyProcess();
        } catch (Exception e) {
            e.printStackTrace();
            handleException(e);
            log("[stateAction]It happens a exception.We will reset verify Process and you can see the detail in trace/VerifyException.txt");
        }
    }

    public void stateRecord() {
        log("6.stateRecord");
        FileLogUtils.writeTouchLog("stateRecord: " + ZKVerController.getInstance().getState());
        try {
            log("[stateRecord] 记录签到");
            UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
            ZKVerConMarkBean zKVerConMarkBean = ZKVerProcessPar.CON_MARK_BEAN;
            if (zKVerConMarkBean.getLastType() != null && zKVerConMarkBean.getLastType().getType() == ZKVerifyType.URGENTPASSWORD.getValue()) {
                String string = ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_PASSWORD);
                String strOption = DBManager.getInstance().getStrOption("Door1SupperPassWord", "");
                ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                Date date = new Date();
                String recordTime = getRecordTime(simpleDateFormat, date);
                int accAttLogCount = this.mDao.getAccAttLogCount();
                boolean z = ZKLauncher.sMaxAttLogCount - ZKLauncher.sAlarmAttLog <= accAttLogCount && ZKLauncher.sLoopDeleteLog == 0;
                String str = TAG;
                LogUtils.d(str, "4：show log full alarm? %s", Boolean.valueOf(z));
                boolean z2 = ZKLauncher.sMaxAttLogCount <= accAttLogCount;
                LogUtils.d(str, "5: is log full? %s", Boolean.valueOf(z2));
                if (!z2 && z && ZKLauncher.sLoopDeleteLog == 0) {
                    log("[stateRecord] 17：set att log will full tips");
                    zKVerViewBean.setUiSpecialState(AppUtils.getString(R.string.server_special_state));
                } else if (z2 && ZKLauncher.sLoopDeleteLog == 0) {
                    log("[stateRecord] 18：set att photo  full tips");
                    zKVerViewBean.setUiSpecialState(AppUtils.getString(R.string.server_att_full));
                }
                zKVerViewBean.setUiSignInTime(recordTime);
                if (strOption.isEmpty() || !strOption.equals(string) || string.length() != 8) {
                    if (!string.isEmpty()) {
                        if (!"".equals(string)) {
                            if (string.length() == 8) {
                                zKVerViewBean.setFailMsg(this.mContext.getResources().getString(R.string.please_enter_urgent_password) + " E" + 30);
                            } else {
                                zKVerViewBean.setFailMsg(this.mContext.getResources().getString(R.string.please_enter_urgent_password_length8) + " E" + 30);
                            }
                            this.mDao.addAccAttLog((UserInfo) null, ZKLauncher.sInOutState, ZKLauncher.sDoorId, 30, getVerifyTime(date), 3, -1.0d, 0);
                            zKVerViewBean.setUiType(72);
                            playSoundTryAgain();
                        }
                    }
                    zKVerViewBean.setFailMsg(this.mContext.getResources().getString(R.string.please_enter_urgent_password_length8));
                    zKVerViewBean.setUiType(72);
                    playSoundTryAgain();
                } else {
                    zKVerViewBean.setFailMsg("E4");
                    zKVerViewBean.setUiType(71);
                    this.mDao.addAccAttLog((UserInfo) null, ZKLauncher.sInOutState, ZKLauncher.sDoorId, 4, getVerifyTime(date), 3, -1.0d, 0);
                    this.mContext.sendBroadcast(new Intent(OpLogReceiver.ACTION_UNLOCKED));
                    ZkDoorManager.getInstance(this.mContext).urgentOpenDoor();
                    AccPush.getInstance().pushRealEvent(4);
                    playSoundOk("");
                }
                ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
                ZKVerProcessPar.cleanData(16);
                ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
                resetVerifyProcess();
            }
            if (!(userInfo == null || zKVerConMarkBean == null || TextUtils.isEmpty(ZKLauncher.sDateFmt) || ZKLauncher.sPhotoFunOn == null)) {
                if (ZKLauncher.sShowPhoto != null) {
                    if (DBManager.getInstance().getIntOption(ZKDBConfig.AUTOSERVERMODE, 0) == 1 && DBManager.getInstance().getIntOption(ZKDBConfig.AUTOSERVERFUNON, 0) == 1) {
                        changeStateRemoteAuthToDelay();
                        ZKVerProcessDialog.getInstance().onlyDisView();
                        ZKEventLauncher.setProcessDialogVisibility(false);
                    }
                    openDoor();
                    return;
                }
            }
            log("[stateRecord] userInfo，parBean等相关数据为空重置流程 ,do resetVerifyProcess");
            resetVerifyProcess();
        } catch (Exception e) {
            e.printStackTrace();
            handleException(e);
            log("[stateRecord]It happens a exception.We will reset verify Process and you can see the detail in trace/VerifyException.txt");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:122:0x02ad  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x02b1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void openDoor() {
        /*
            r29 = this;
            r8 = r29
            java.lang.String r0 = "BgAuthTime"
            android.os.Bundle r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE
            java.lang.String r2 = "bundle_user_info"
            java.io.Serializable r1 = r1.getSerializable(r2)
            r10 = r1
            com.zkteco.android.db.orm.tna.UserInfo r10 = (com.zkteco.android.db.orm.tna.UserInfo) r10
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN
            int r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.VERIFY_SOURCE_TYPE
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat
            java.util.Locale r4 = java.util.Locale.US
            java.lang.String r5 = "yyyy-MM-dd HH:mm:ss"
            r3.<init>(r5, r4)
            java.util.Date r4 = new java.util.Date
            r4.<init>()
            java.lang.String r12 = r8.getRecordTime(r3, r4)
            java.lang.String r3 = com.zktechnology.android.launcher2.ZKLauncher.sPhotoFunOn
            java.lang.String r5 = "1"
            boolean r3 = r5.equals(r3)
            r7 = 0
            r9 = 1
            if (r3 == 0) goto L_0x003b
            java.lang.String r3 = com.zktechnology.android.launcher2.ZKLauncher.sShowPhoto
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003b
            r3 = r9
            goto L_0x003c
        L_0x003b:
            r3 = r7
        L_0x003c:
            com.zktechnology.android.verify.dao.ZKAccDao r5 = r8.mDao
            int r5 = r5.getAccAttLogCount()
            int r6 = com.zktechnology.android.launcher2.ZKLauncher.sMaxAttLogCount
            int r11 = com.zktechnology.android.launcher2.ZKLauncher.sAlarmAttLog
            int r6 = r6 - r11
            if (r6 > r5) goto L_0x004f
            int r6 = com.zktechnology.android.launcher2.ZKLauncher.sLoopDeleteLog
            if (r6 != 0) goto L_0x004f
            r6 = r9
            goto L_0x0050
        L_0x004f:
            r6 = r7
        L_0x0050:
            int r11 = com.zktechnology.android.launcher2.ZKLauncher.sMaxAttLogCount
            if (r11 > r5) goto L_0x0056
            r11 = r9
            goto L_0x0057
        L_0x0056:
            r11 = r7
        L_0x0057:
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r5 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean
            r5.<init>()
            java.lang.String r13 = r10.getName()
            r5.setUiName(r13)
            java.lang.String r13 = r10.getUser_PIN()
            r5.setUiPin(r13)
            r5.setUiSignInTime(r12)
            r5.setUiTFPhoto(r3)
            java.lang.String r3 = "stateRecord===================================================== "
            com.zktechnology.android.push.util.FileLogUtils.writeVerifyLog(r3)
            if (r11 != 0) goto L_0x0088
            if (r6 == 0) goto L_0x0088
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.sLoopDeleteLog
            if (r3 != 0) goto L_0x0088
            r3 = 2131755298(0x7f100122, float:1.9141471E38)
            java.lang.String r3 = com.zktechnology.android.utils.AppUtils.getString(r3)
            r5.setUiSpecialState(r3)
            goto L_0x0098
        L_0x0088:
            if (r11 == 0) goto L_0x0098
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.sLoopDeleteLog
            if (r3 != 0) goto L_0x0098
            r3 = 2131755293(0x7f10011d, float:1.9141461E38)
            java.lang.String r3 = com.zktechnology.android.utils.AppUtils.getString(r3)
            r5.setUiSpecialState(r3)
        L_0x0098:
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r3 = r1.getLastType()
            int r3 = r3.getType()
            com.zktechnology.android.verify.bean.process.ZKVerifyType r3 = com.zktechnology.android.verify.bean.process.ZKVerifyType.fromInteger(r3)
            r6 = -1
            if (r10 == 0) goto L_0x00b8
            com.zkteco.android.db.orm.manager.DataManager r13 = com.zktechnology.android.utils.DBManager.getInstance()
            java.lang.String r14 = "AccessPersonalVerification"
            int r13 = r13.getIntOption(r14, r7)
            if (r13 != r9) goto L_0x00b8
            int r13 = r10.getVerify_Type()
            goto L_0x00b9
        L_0x00b8:
            r13 = r6
        L_0x00b9:
            if (r13 != r6) goto L_0x00bd
            int r13 = com.zktechnology.android.launcher2.ZKLauncher.sDoor1VerifyType
        L_0x00bd:
            int r19 = com.zktechnology.android.verify.utils.VerifyTypeUtil.getDoorVerifyType(r3, r13)
            if (r3 == 0) goto L_0x00c8
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r6 = r8.verifyInfo
            r6.setVerifyType(r3)
        L_0x00c8:
            java.util.List r3 = r1.getVerifyTypeList()
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            if (r3 == 0) goto L_0x00ee
            r13 = r7
        L_0x00d4:
            int r14 = r3.size()
            if (r13 >= r14) goto L_0x00ee
            java.lang.Object r14 = r3.get(r13)
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r14 = (com.zktechnology.android.verify.bean.process.ZKMarkTypeBean) r14
            int r14 = r14.getType()
            com.zktechnology.android.verify.bean.process.ZKVerifyType r14 = com.zktechnology.android.verify.bean.process.ZKVerifyType.fromInteger(r14)
            r6.add(r14)
            int r13 = r13 + 1
            goto L_0x00d4
        L_0x00ee:
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r3 = r8.verifyInfo
            r13 = 0
            r3.setVerifyInput(r13)
            android.os.Bundle r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE
            java.lang.String r14 = "bundle_password"
            java.lang.String r3 = r3.getString(r14)
            com.zktechnology.android.verify.dao.ZKAccDao r14 = r8.mDao
            boolean r14 = r14.checkStressPassword(r3)
            com.zktechnology.android.verify.bean.process.ZKVerifyType r15 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            boolean r15 = r6.contains(r15)
            if (r15 == 0) goto L_0x0112
            if (r14 == 0) goto L_0x0112
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r6 = r8.verifyInfo
            r6.setVerifyInput(r3)
            goto L_0x0127
        L_0x0112:
            com.zktechnology.android.verify.bean.process.ZKVerifyType r3 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            boolean r3 = r6.contains(r3)
            if (r3 == 0) goto L_0x0127
            android.os.Bundle r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE
            java.lang.String r6 = "bundle_finger_template"
            java.lang.String r3 = r3.getString(r6)
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r6 = r8.verifyInfo
            r6.setVerifyInput(r3)
        L_0x0127:
            android.content.Context r3 = r8.mContext
            com.zktechnology.android.door.ZkDoorManager r3 = com.zktechnology.android.door.ZkDoorManager.getInstance(r3)
            com.zktechnology.android.verify.dao.ZKAccDao r6 = r8.mDao
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r14 = r8.verifyInfo
            com.zktechnology.android.acc.advance.DoorAccessResponse r6 = r3.openDoor(r6, r10, r14, r4)
            r8.sendBro(r6)
            com.zktechnology.android.verify.bean.ZKVerifyOpenDoorResultBean.resetAllStatus()
            int r3 = r6.getErrorCode()
            r14 = 1001(0x3e9, float:1.403E-42)
            if (r3 != r14) goto L_0x0147
            r29.resetVerifyProcess()
            return
        L_0x0147:
            java.lang.String r3 = r6.getErrorMessage()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 == 0) goto L_0x02f3
            int r3 = r6.getErrorCode()
            java.lang.String r15 = "Reader1IOState"
            if (r3 != 0) goto L_0x01f6
            boolean r3 = r6.isAdminContinuousVerify()
            if (r3 != 0) goto L_0x01f6
            com.zktechnology.android.verify.controller.ZKVerController r3 = com.zktechnology.android.verify.controller.ZKVerController.getInstance()
            com.zktechnology.android.verify.utils.ZKVerConState r3 = r3.getState()
            com.zktechnology.android.verify.utils.ZKVerConState r14 = com.zktechnology.android.verify.utils.ZKVerConState.STATE_DELAY
            if (r3 != r14) goto L_0x01f6
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x01ec }
            int r1 = r1.getIntOption(r0, r9)     // Catch:{ Exception -> 0x01ec }
            if (r1 >= r9) goto L_0x0177
            r0 = r9
            goto L_0x017f
        L_0x0177:
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x01ec }
            int r0 = r1.getIntOption(r0, r9)     // Catch:{ Exception -> 0x01ec }
        L_0x017f:
            r20 = 0
            long r1 = (long) r0     // Catch:{ Exception -> 0x01ec }
            r24 = 0
            r26 = 1
            java.util.concurrent.TimeUnit r28 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ Exception -> 0x01ec }
            r22 = r1
            io.reactivex.rxjava3.core.Observable r1 = io.reactivex.rxjava3.core.Observable.intervalRange(r20, r22, r24, r26, r28)     // Catch:{ Exception -> 0x01ec }
            io.reactivex.rxjava3.core.Scheduler r2 = io.reactivex.rxjava3.schedulers.Schedulers.single()     // Catch:{ Exception -> 0x01ec }
            io.reactivex.rxjava3.core.Observable r1 = r1.subscribeOn(r2)     // Catch:{ Exception -> 0x01ec }
            io.reactivex.rxjava3.core.Scheduler r2 = io.reactivex.rxjava3.schedulers.Schedulers.single()     // Catch:{ Exception -> 0x01ec }
            io.reactivex.rxjava3.core.Observable r1 = r1.observeOn(r2)     // Catch:{ Exception -> 0x01ec }
            com.zktechnology.android.verify.server.ZKAccServerImpl$1 r2 = new com.zktechnology.android.verify.server.ZKAccServerImpl$1     // Catch:{ Exception -> 0x01ec }
            r2.<init>()     // Catch:{ Exception -> 0x01ec }
            io.reactivex.rxjava3.core.Observable r1 = r1.doOnComplete(r2)     // Catch:{ Exception -> 0x01ec }
            io.reactivex.rxjava3.disposables.Disposable r1 = r1.subscribe()     // Catch:{ Exception -> 0x01ec }
            r8.mdDisposable = r1     // Catch:{ Exception -> 0x01ec }
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r1 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x01ec }
            r1.<init>()     // Catch:{ Exception -> 0x01ec }
            r2 = 999(0x3e7, float:1.4E-42)
            r1.setUiType(r2)     // Catch:{ Exception -> 0x01ec }
            com.zktechnology.android.verify.dialog.managment.ZKVerDlgMgt.upDateTopUi(r1)     // Catch:{ Exception -> 0x01ec }
            com.zktechnology.android.launcher2.ZKEventLauncher.setProcessDialogTimeOut((int) r0)     // Catch:{ Exception -> 0x01ec }
            com.zktechnology.android.push.acc.AccPush r11 = com.zktechnology.android.push.acc.AccPush.getInstance()     // Catch:{ Exception -> 0x01ec }
            java.lang.String r13 = r10.getUser_PIN()     // Catch:{ Exception -> 0x01ec }
            java.lang.String r0 = r10.getMain_Card()     // Catch:{ Exception -> 0x01ec }
            if (r0 != 0) goto L_0x01ce
            java.lang.String r0 = "0"
            goto L_0x01d2
        L_0x01ce:
            java.lang.String r0 = r10.getMain_Card()     // Catch:{ Exception -> 0x01ec }
        L_0x01d2:
            r14 = r0
            r0 = 3
            r16 = 1
            r17 = 223(0xdf, float:3.12E-43)
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x01ec }
            int r1 = r1.getIntOption(r15, r7)     // Catch:{ Exception -> 0x01ec }
            if (r1 != 0) goto L_0x01e5
            r18 = r9
            goto L_0x01e7
        L_0x01e5:
            r18 = r7
        L_0x01e7:
            r15 = r0
            r11.pushRemoteAuth(r12, r13, r14, r15, r16, r17, r18, r19)     // Catch:{ Exception -> 0x01ec }
            goto L_0x01f3
        L_0x01ec:
            r0 = move-exception
            r0.printStackTrace()
            r29.resetVerifyProcess()
        L_0x01f3:
            r8.accResponse = r6
            return
        L_0x01f6:
            com.zktechnology.android.acc.advance.DoorAccessResponse r0 = r8.accResponse
            if (r0 == 0) goto L_0x01fb
            r6 = r0
        L_0x01fb:
            r8.accResponse = r13
            java.lang.String r0 = "验证成功 enableOpen 可以开门 "
            com.zktechnology.android.push.util.FileLogUtils.writeVerifyLog(r0)
            com.zktechnology.android.wiegand.ZKWiegandManager r0 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()
            r0.wiegandOutUserInfo(r10)
            r0 = 3
            if (r2 != r0) goto L_0x0221
            java.lang.String r0 = "VerifySourceType.VS_READ "
            com.zktechnology.android.push.util.FileLogUtils.writeVerifyLog(r0)
            com.zktechnology.android.rs232.ZKRS232EncryptManager r0 = com.zktechnology.android.rs232.ZKRS232EncryptManager.getInstance()
            r0.successCmd()
            android.content.Context r0 = r8.mContext
            com.zktechnology.android.rs485.RS485Manager r0 = com.zktechnology.android.rs485.RS485Manager.getInstance(r0)
            r0.successCmd()
        L_0x0221:
            boolean r0 = com.zktechnology.android.launcher2.ZKLauncher.sIRTempDetectionFunOn
            if (r0 == 0) goto L_0x0229
            boolean r0 = com.zktechnology.android.launcher2.ZKLauncher.sEnalbeIRTempDetection
            if (r0 != 0) goto L_0x0230
        L_0x0229:
            java.lang.String r0 = r10.getName()
            r8.playSoundOk(r0)
        L_0x0230:
            java.lang.String r0 = "验证成功 播放声音 "
            com.zktechnology.android.push.util.FileLogUtils.writeVerifyLog(r0)
            java.lang.String r0 = r10.getUser_PIN()
            r8.pictureAcc(r0, r4)
            r0 = 2
            if (r2 == r0) goto L_0x0245
            r0 = 3
            if (r2 == r0) goto L_0x0245
            r8.clearWorkNumber(r10)
        L_0x0245:
            java.lang.String r0 = "验证成功 插入考勤数据 "
            com.zktechnology.android.push.util.FileLogUtils.writeVerifyLog(r0)
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN
            int r0 = r0.getIntent()
            r2 = 5
            if (r0 != r2) goto L_0x0262
            com.zkteco.android.db.orm.manager.DataManager r3 = com.zktechnology.android.utils.DBManager.getInstance()
            int r3 = r3.getIntOption(r15, r7)
            if (r3 != r9) goto L_0x025f
            r3 = r7
            goto L_0x0260
        L_0x025f:
            r3 = r9
        L_0x0260:
            com.zktechnology.android.launcher2.ZKLauncher.sInOutState = r3
        L_0x0262:
            int r3 = r6.getErrorCode()
            if (r3 != 0) goto L_0x02df
            int r3 = com.zktechnology.android.acc.DoorAccessDao.getDoorAlwaysOpenTimeZone()
            boolean r3 = com.zktechnology.android.acc.DoorAccessDao.isInAccTimeZoneExtension(r3, r9)
            boolean r12 = com.zktechnology.android.acc.DoorAccessDao.checkDoor1CancelKeepOpenDay()
            if (r3 == 0) goto L_0x027a
            if (r12 == 0) goto L_0x027a
        L_0x0278:
            r13 = r9
            goto L_0x029c
        L_0x027a:
            com.zkteco.android.db.orm.manager.DataManager r3 = com.zktechnology.android.utils.DBManager.getInstance()
            java.lang.String r12 = "AutoServerMode"
            int r3 = r3.getIntOption(r12, r7)
            if (r3 != r9) goto L_0x0296
            com.zkteco.android.db.orm.manager.DataManager r3 = com.zktechnology.android.utils.DBManager.getInstance()
            java.lang.String r12 = "AutoServerFunOn"
            int r3 = r3.getIntOption(r12, r7)
            if (r3 != r9) goto L_0x0296
            r3 = 222(0xde, float:3.11E-43)
            r13 = r3
            goto L_0x029c
        L_0x0296:
            boolean r3 = com.zktechnology.android.acc.DoorAccessManager.isAlreadyFirstAlwaysOpenDoor
            if (r3 == 0) goto L_0x029b
            goto L_0x0278
        L_0x029b:
            r13 = r7
        L_0x029c:
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.sLoopDeleteLog
            if (r3 <= 0) goto L_0x02af
            if (r11 == 0) goto L_0x02af
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.sMaxAttLogCount
            com.zktechnology.android.verify.dao.ZKAccDao r11 = r8.mDao
            int r11 = r11.getAccAttLogCount()
            if (r3 >= r11) goto L_0x02ad
            goto L_0x02ae
        L_0x02ad:
            r9 = r7
        L_0x02ae:
            r11 = r9
        L_0x02af:
            if (r11 != 0) goto L_0x02df
            boolean r3 = r6.isAdminContinuousVerify()
            if (r3 == 0) goto L_0x02bb
            r6.setAdminContinuousVerify(r7)
            goto L_0x02df
        L_0x02bb:
            com.zktechnology.android.verify.dao.ZKAccDao r9 = r8.mDao
            int r11 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState
            int r12 = com.zktechnology.android.launcher2.ZKLauncher.sDoorId
            java.lang.String r14 = r8.getVerifyTime(r4)
            double r16 = r6.getTemperature()
            int r18 = r6.getWearMask()
            r3 = r15
            r15 = r19
            r9.addAccAttLog(r10, r11, r12, r13, r14, r15, r16, r18)
            if (r0 != r2) goto L_0x02df
            com.zkteco.android.db.orm.manager.DataManager r0 = com.zktechnology.android.utils.DBManager.getInstance()
            int r0 = r0.getIntOption(r3, r7)
            com.zktechnology.android.launcher2.ZKLauncher.sInOutState = r0
        L_0x02df:
            java.lang.String r0 = "验证结束 开门 openDoor: "
            r8.log(r0)
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r0 = r1.getLastType()
            int r0 = r0.getType()
            r8.showUserSuccessDialog(r5, r0)
            r8.times = r7
            goto L_0x03c8
        L_0x02f3:
            com.zktechnology.android.wiegand.ZKWiegandManager r0 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()
            r0.wiegandOutFailedID()
            r0 = 3
            if (r2 != r0) goto L_0x030d
            com.zktechnology.android.rs232.ZKRS232EncryptManager r0 = com.zktechnology.android.rs232.ZKRS232EncryptManager.getInstance()
            r0.failedCmd()
            android.content.Context r0 = r8.mContext
            com.zktechnology.android.rs485.RS485Manager r0 = com.zktechnology.android.rs485.RS485Manager.getInstance(r0)
            r0.failedCmd()
        L_0x030d:
            java.lang.String r0 = r10.getUser_PIN()
            r8.pictureBlack(r0, r4)
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r0 = r1.getLastType()
            int r3 = r0.getType()
            java.lang.String r4 = r6.getErrorMessage()
            int r0 = r6.getErrorCode()
            r10 = 0
            r1 = r29
            r2 = r5
            r5 = r0
            r12 = r6
            r6 = r10
            r0 = r7
            r7 = r11
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)
            android.content.Context r1 = r8.mContext
            android.content.res.Resources r1 = r1.getResources()
            r2 = 2131755156(0x7f100094, float:1.9141183E38)
            java.lang.String r1 = r1.getString(r2)
            java.lang.String r2 = r12.getErrorMessage()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0356
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r1 = r8.verifyInfo
            r1.setGroupUserList(r13)
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r1 = r8.verifyInfo
            r2 = 0
            r1.setLastMultipleVerifyTime(r2)
            r29.verifyFailAlarm()
        L_0x0356:
            android.content.Context r1 = r8.mContext
            android.content.res.Resources r1 = r1.getResources()
            r2 = 2131755157(0x7f100095, float:1.9141185E38)
            java.lang.String r1 = r1.getString(r2)
            java.lang.String r2 = r12.getErrorMessage()
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x039b
            android.content.Context r1 = r8.mContext
            android.content.res.Resources r1 = r1.getResources()
            r2 = 2131755221(0x7f1000d5, float:1.9141315E38)
            java.lang.String r1 = r1.getString(r2)
            java.lang.String r2 = r12.getErrorMessage()
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x039b
            android.content.Context r1 = r8.mContext
            android.content.res.Resources r1 = r1.getResources()
            r2 = 2131755305(0x7f100129, float:1.9141486E38)
            java.lang.String r1 = r1.getString(r2)
            java.lang.String r2 = r12.getErrorMessage()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x039e
        L_0x039b:
            r29.verifyFailAlarm()
        L_0x039e:
            android.content.Context r1 = r8.mContext
            android.content.res.Resources r1 = r1.getResources()
            r2 = 2131755222(0x7f1000d6, float:1.9141317E38)
            java.lang.String r1 = r1.getString(r2)
            java.lang.String r2 = r12.getErrorMessage()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x03ba
            r29.resetVerifyProcess()
            r7 = r9
            goto L_0x03bb
        L_0x03ba:
            r7 = r0
        L_0x03bb:
            if (r7 != 0) goto L_0x03c8
            boolean r0 = com.zktechnology.android.launcher2.ZKLauncher.sIRTempDetectionFunOn
            if (r0 == 0) goto L_0x03c5
            boolean r0 = com.zktechnology.android.launcher2.ZKLauncher.sEnalbeIRTempDetection
            if (r0 != 0) goto L_0x03c8
        L_0x03c5:
            r29.playSoundTryAgain()
        L_0x03c8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.server.ZKAccServerImpl.openDoor():void");
    }

    public void stateRemoteAuth() {
        FileLogUtils.writeTouchLog("stateRemoteAuth: " + ZKVerController.getInstance().getState());
        try {
            ZKVerProcessDialog.getInstance().disViewAndClearData();
            UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
            String string = ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.REMOTE_AUTH_RESULT);
            if (userInfo != null) {
                if (string != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    Date date = new Date();
                    String recordTime = getRecordTime(simpleDateFormat, date);
                    ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
                    int accAttLogCount = this.mDao.getAccAttLogCount();
                    boolean z = ZKLauncher.sMaxAttLogCount - ZKLauncher.sAlarmAttLog <= accAttLogCount && ZKLauncher.sLoopDeleteLog == 0;
                    boolean z2 = ZKLauncher.sMaxAttLogCount <= accAttLogCount;
                    if (!z2 && z && ZKLauncher.sLoopDeleteLog == 0) {
                        zKVerViewBean.setUiSpecialState(AppUtils.getString(R.string.server_special_state));
                    } else if (z2 && ZKLauncher.sLoopDeleteLog == 0) {
                        zKVerViewBean.setUiSpecialState(AppUtils.getString(R.string.server_att_full));
                    }
                    boolean z3 = "1".equals(ZKLauncher.sPhotoFunOn) && "1".equals(ZKLauncher.sShowPhoto);
                    zKVerViewBean.setUiName(userInfo.getName());
                    zKVerViewBean.setUiPin(userInfo.getUser_PIN());
                    zKVerViewBean.setUiSignInTime(recordTime);
                    zKVerViewBean.setUiTFPhoto(z3);
                    int type = ZKVerProcessPar.CON_MARK_BEAN.getLastType().getType();
                    ZKVerifyType fromInteger = ZKVerifyType.fromInteger(type);
                    int verify_Type = userInfo.getVerify_Type();
                    if (verify_Type == -1) {
                        verify_Type = ZKLauncher.sDoor1VerifyType;
                    }
                    int doorVerifyType = VerifyTypeUtil.getDoorVerifyType(fromInteger, verify_Type);
                    if (RemoteAuthReceiver.ACTION_REMOTE_AUTH_SUCCESS.equals(string)) {
                        openDoor();
                    } else if (RemoteAuthReceiver.ACTION_REMOTE_AUTH_FAILED.equals(string)) {
                        this.mDao.addAccAttLog(userInfo, ZKLauncher.sInOutState, ZKLauncher.sDoorId, 44, doorVerifyType);
                        playSoundTryAgain();
                        pictureBlack(userInfo.getUser_PIN(), date);
                        if (type == 3) {
                            type = 2;
                        }
                        showUserFailedDialog(zKVerViewBean, ZKVerifyType.fromInteger(type).getValue(), this.mContext.getString(R.string.remote_auth_fail), 44, false, z2);
                    } else if (!RemoteAuthReceiver.ACTION_REMOTE_AUTH_TIMEOUT.equals(string)) {
                        this.mDao.addAccAttLog(userInfo, ZKLauncher.sInOutState, ZKLauncher.sDoorId, 44, convertVerifyType(fromInteger.getValue()));
                        playSoundTryAgain();
                        pictureBlack(userInfo.getUser_PIN(), date);
                        showUserFailedDialog(zKVerViewBean, fromInteger.getValue(), this.mContext.getString(R.string.remote_auth_fail), 44, false, z2);
                    } else if (DBManager.getInstance().getIntOption(ZKDBConfig.READER1OFFLINEREFUSE, 0) == 1) {
                        this.mDao.addAccAttLog(userInfo, ZKLauncher.sInOutState, ZKLauncher.sDoorId, 45, convertVerifyType(fromInteger.getValue()));
                        playSoundTryAgain();
                        pictureBlack(userInfo.getUser_PIN(), date);
                        showUserFailedDialog(zKVerViewBean, fromInteger.getValue(), this.mContext.getString(R.string.remote_auth_timeout), 45, false, z2);
                    } else {
                        openDoor();
                    }
                    resetVerifyProcess();
                    return;
                }
            }
            LogUtils.d(TAG, "userInfo or remoteAuthResult is null, reset the verification state");
            resetVerifyProcess();
        } catch (Exception e) {
            e.printStackTrace();
            String str = TAG;
            LogUtils.d(str, "----------------------------------------------------------------------------------------");
            LogUtils.d(str, "stateRemoteAuth Exception.getMessage" + e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                LogUtils.d(TAG, stackTraceElement.getClassName() + 9 + stackTraceElement.getFileName() + 9 + stackTraceElement.getLineNumber() + 9 + stackTraceElement.getMethodName());
            }
            LogUtils.d(TAG, "----------------------------------------------------------------------------------------");
        }
    }

    public void resetMultiVerify() {
        ZKVerifyInfo zKVerifyInfo = this.verifyInfo;
        if (zKVerifyInfo != null) {
            zKVerifyInfo.setGroupUserList((Hashtable<Integer, ArrayList<String>>) null);
            this.verifyInfo.setLastMultipleVerifyTime(0);
        }
    }

    private void startBtnWidgetAction(UserInfo userInfo, int i) {
        if (getWidgetActionType() == 2) {
            if (this.mDao.isSuperUser(userInfo)) {
                ZKEventLauncher.setProcessDialogVisibility(false);
                openApp();
            } else {
                showUserFailedDialog(new ZKVerViewBean(), i, (String) null, 27, false, false);
                playSoundTryAgain();
            }
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
            return;
        }
        changeStateToRecord();
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x01a0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkUserValid(com.zkteco.android.db.orm.tna.UserInfo r21, int r22) {
        /*
            r20 = this;
            r1 = r20
            r0 = r21
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "checkUserValid: 检查用户有效性   userInfo : "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r21.getUser_PIN()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.log(r2)
            java.lang.String r2 = "[stateUser][checkUserValid]:start"
            r1.log(r2)
            java.util.Date r12 = new java.util.Date
            r12.<init>()
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN
            int r2 = r2.getIntent()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "[stateUser][checkUserValid]:get UserIntent "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r3 = r3.toString()
            r1.log(r3)
            r3 = 5
            if (r2 != r3) goto L_0x0049
            r14 = 0
            goto L_0x004a
        L_0x0049:
            r14 = r2
        L_0x004a:
            int r15 = com.zktechnology.android.verify.utils.ZKVerProcessPar.VERIFY_SOURCE_TYPE
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "[stateUser][checkUserValid]:verifyGenre "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r15)
            java.lang.String r2 = r2.toString()
            r1.log(r2)
            r2 = 2
            r11 = 1
            if (r14 == 0) goto L_0x008d
            if (r14 == r11) goto L_0x006f
            if (r14 == r2) goto L_0x006f
            r13 = 0
        L_0x006b:
            r16 = 0
            goto L_0x0178
        L_0x006f:
            com.zktechnology.android.verify.dao.ZKAccDao r2 = r1.mDao
            boolean r2 = r2.isSuperUser(r0)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "[stateUser][checkUserValid]:open menu or open app ，is super user?"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r3 = r3.toString()
            r1.log(r3)
            r13 = r2
            goto L_0x006b
        L_0x008d:
            com.zktechnology.android.verify.dao.ZKAccDao r3 = r1.mDao
            boolean r9 = r3.isInExpires(r0)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "[stateUser][checkUserValid]:isUserValid "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r9)
            java.lang.String r3 = r3.toString()
            r1.log(r3)
            if (r9 != 0) goto L_0x0148
            java.lang.String r3 = com.zktechnology.android.launcher2.ZKLauncher.sUserValidTimeFun
            java.lang.String r4 = "1"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x0148
            java.lang.String r3 = "[stateUser][checkUserValid]:user is invalid or user valid function is close"
            r1.log(r3)
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.sOverValidTime
            if (r3 == r11) goto L_0x0117
            if (r3 == r2) goto L_0x00e9
            com.zktechnology.android.verify.dao.ZKAccDao r2 = r1.mDao
            r2.deleteUserAccAttLog(r0)
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState
            int r4 = com.zktechnology.android.launcher2.ZKLauncher.sDoorId
            r5 = 29
            java.lang.String r6 = r1.getVerifyTime(r12)
            r2 = r21
            r7 = r22
            com.zkteco.android.db.orm.tna.AccAttLog r2 = com.zktechnology.android.push.acc.AccPush.getPushLog(r2, r3, r4, r5, r6, r7)
            java.lang.String r3 = "launcher push: checkUserValid2"
            com.zktechnology.android.push.util.FileLogUtils.writeVerifyLog(r3)
            com.zktechnology.android.push.acc.AccPush r3 = com.zktechnology.android.push.acc.AccPush.getInstance()
            r3.push(r2)
            java.lang.String r2 = "[stateUser][checkUserValid]:default ,do nothing"
            r1.log(r2)
            goto L_0x0148
        L_0x00e9:
            com.zktechnology.android.verify.dao.ZKAccDao r2 = r1.mDao
            r2.deleteUser(r0)
            com.zktechnology.android.verify.dao.ZKAccDao r2 = r1.mDao
            r2.deleteUserAccAttLog(r0)
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState
            int r4 = com.zktechnology.android.launcher2.ZKLauncher.sDoorId
            r5 = 29
            java.lang.String r6 = r1.getVerifyTime(r12)
            r2 = r21
            r7 = r22
            com.zkteco.android.db.orm.tna.AccAttLog r2 = com.zktechnology.android.push.acc.AccPush.getPushLog(r2, r3, r4, r5, r6, r7)
            java.lang.String r3 = "launcher push: checkUserValid1"
            com.zktechnology.android.push.util.FileLogUtils.writeVerifyLog(r3)
            com.zktechnology.android.push.acc.AccPush r3 = com.zktechnology.android.push.acc.AccPush.getInstance()
            r3.push(r2)
            java.lang.String r2 = "[stateUser][checkUserValid]:user is over valid,delete user"
            r1.log(r2)
            goto L_0x0148
        L_0x0117:
            java.lang.String r2 = com.zktechnology.android.launcher2.ZKLauncher.sUserValidTimeFun
            boolean r2 = r4.equals(r2)
            java.lang.String r3 = "[stateUser][checkUserValid]:user is over valid ,add att log"
            r1.log(r3)
            if (r2 == 0) goto L_0x0148
            com.zktechnology.android.verify.dao.ZKAccDao r2 = r1.mDao
            int r4 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState
            int r5 = com.zktechnology.android.launcher2.ZKLauncher.sDoorId
            r6 = 29
            java.lang.String r7 = r1.getVerifyTime(r12)
            r16 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            r18 = 0
            r3 = r21
            r8 = r22
            r19 = r9
            r9 = r16
            r13 = r11
            r11 = r18
            r2.addAccAttLog(r3, r4, r5, r6, r7, r8, r9, r11)
            java.lang.String r2 = "[stateUser][checkUserValid]:submitTask ,add att log task"
            r1.log(r2)
            goto L_0x014b
        L_0x0148:
            r19 = r9
            r13 = r11
        L_0x014b:
            int r2 = com.zktechnology.android.launcher2.ZKLauncher.sAccessRuleType
            if (r2 != r13) goto L_0x0174
            if (r19 == 0) goto L_0x0174
            com.zktechnology.android.verify.dao.ZKAccDao r2 = r1.mDao
            boolean r13 = r2.checkInBlackList(r0)
            if (r13 == 0) goto L_0x016f
            com.zktechnology.android.verify.dao.ZKAccDao r2 = r1.mDao
            int r4 = com.zktechnology.android.launcher2.ZKLauncher.sInOutState
            int r5 = com.zktechnology.android.launcher2.ZKLauncher.sDoorId
            r6 = 39
            java.lang.String r7 = r1.getVerifyTime(r12)
            r9 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            r11 = 0
            r3 = r21
            r8 = r22
            r2.addAccAttLog(r3, r4, r5, r6, r7, r8, r9, r11)
        L_0x016f:
            r16 = r13
            r13 = r19
            goto L_0x0178
        L_0x0174:
            r13 = r19
            goto L_0x006b
        L_0x0178:
            if (r13 == 0) goto L_0x0198
            if (r16 == 0) goto L_0x017d
            goto L_0x0198
        L_0x017d:
            java.lang.String r2 = "[stateUser][checkUserValid]:user is valid"
            r1.log(r2)
            android.os.Bundle r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE
            java.lang.String r3 = "bundle_user_info"
            r2.putSerializable(r3, r0)
            java.lang.String r0 = "[stateUser][checkUserValid]CON_MARK_BUNDLE add user info"
            r1.log(r0)
            r20.changeStateToWay()
            java.lang.String r0 = "[stateUser][checkUserValid]change State To Way"
            r1.log(r0)
            goto L_0x0244
        L_0x0198:
            java.lang.String r2 = "[stateUser][checkUserValid]:user is not valid , out put failed cmd"
            r1.log(r2)
            r2 = 3
            if (r15 != r2) goto L_0x01b5
            com.zktechnology.android.rs232.ZKRS232EncryptManager r2 = com.zktechnology.android.rs232.ZKRS232EncryptManager.getInstance()
            r2.failedCmd()
            android.content.Context r2 = r1.mContext
            com.zktechnology.android.rs485.RS485Manager r2 = com.zktechnology.android.rs485.RS485Manager.getInstance(r2)
            r2.failedCmd()
            java.lang.String r2 = "[stateUser][checkUserValid]:rs232  out put failed cmd"
            r1.log(r2)
        L_0x01b5:
            com.zktechnology.android.wiegand.ZKWiegandManager r2 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()
            r2.wiegandOutFailedID()
            java.lang.String r2 = "[stateUser][checkUserValid]:wg  out put failed cmd"
            r1.log(r2)
            r2 = 0
            r1.pictureBlack(r2, r12)
            java.lang.String r3 = "[stateUser][checkUserValid]:pictureBlack"
            r1.log(r3)
            r20.playSoundTryAgain()
            java.lang.String r3 = "[stateUser][checkUserValid]:playSoundTryAgain"
            r1.log(r3)
            r1.showUserInvalidDialog(r0, r14, r13)
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r0 = r1.verifyInfo
            r0.setGroupUserList(r2)
            com.zktechnology.android.verify.bean.process.ZKVerifyInfo r0 = r1.verifyInfo
            r2 = 0
            r0.setLastMultipleVerifyTime(r2)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r2 = "[stateUser][checkUserValid]:showUserInvalidDialog"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r1.log(r0)
            java.lang.String r0 = TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "检查用户有效性:"
            java.lang.StringBuilder r2 = r2.append(r3)
            com.zktechnology.android.verify.controller.ZKVerController r3 = com.zktechnology.android.verify.controller.ZKVerController.getInstance()
            com.zktechnology.android.verify.utils.ZKVerConState r3 = r3.getState()
            java.lang.String r3 = r3.name()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.zktechnology.android.utils.LogUtils.d(r0, r2)
            r20.verifyFailAlarm()
            r20.changeStateToWait()
            java.lang.String r0 = "[stateUser][checkUserValid]:changeState to WAIT"
            r1.log(r0)
            r2 = 20
            java.lang.Thread.sleep(r2)     // Catch:{ InterruptedException -> 0x0230 }
            goto L_0x0235
        L_0x0230:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x0235:
            com.zktechnology.android.verify.bean.process.ZKTouchActionBean r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.ACTION_BEAN
            r0.setTTouchAction()
            java.lang.String r0 = "setTTouchAction: checkUserValid"
            com.zktechnology.android.push.util.FileLogUtils.writeTouchLog(r0)
            java.lang.String r0 = "[stateUser][checkUserValid]:setTTouchAction"
            r1.log(r0)
        L_0x0244:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.server.ZKAccServerImpl.checkUserValid(com.zkteco.android.db.orm.tna.UserInfo, int):void");
    }

    private void picture(String str, Date date, boolean z, int i) {
        String str2;
        String str3;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ZKConstantConfig.DATE_FORMAT_2, Locale.US);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        if (i != 2) {
            if (i != 3) {
                if (i == 4 && !z) {
                    PhotoIndex photoIndex = new PhotoIndex();
                    photoIndex.setPhoto_Time(simpleDateFormat2.format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE));
                    photoIndex.setPhoto_Type(1);
                    ZKCameraController.getInstance().takePicture(photoIndex, ZKFilePath.BLACK_LIST_PATH, simpleDateFormat.format(date));
                }
            } else if (z) {
                PhotoIndex photoIndex2 = new PhotoIndex();
                if (str != null) {
                    photoIndex2.setUser_PIN(str);
                    str3 = simpleDateFormat.format(date) + "-" + str;
                } else {
                    str3 = simpleDateFormat.format(date) + "-";
                }
                photoIndex2.setPhoto_Time(simpleDateFormat2.format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE));
                photoIndex2.setPhoto_Type(0);
                ZKCameraController.getInstance().takePicture(photoIndex2, ZKFilePath.ATT_PHOTO_PATH, str3);
            }
        } else if (z) {
            PhotoIndex photoIndex3 = new PhotoIndex();
            if (str != null) {
                photoIndex3.setUser_PIN(str);
                str2 = simpleDateFormat.format(date) + "-" + str;
            } else {
                str2 = simpleDateFormat.format(date) + "-";
            }
            photoIndex3.setPhoto_Time(simpleDateFormat2.format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE));
            photoIndex3.setPhoto_Type(0);
            ZKCameraController.getInstance().takePicture(photoIndex3, ZKFilePath.ATT_PHOTO_PATH, str2);
        } else {
            String format = simpleDateFormat.format(date);
            PhotoIndex photoIndex4 = new PhotoIndex();
            photoIndex4.setPhoto_Time(simpleDateFormat2.format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE));
            photoIndex4.setPhoto_Type(1);
            ZKCameraController.getInstance().takePicture(photoIndex4, ZKFilePath.BLACK_LIST_PATH, format);
        }
    }

    private void clearWorkNumber(UserInfo userInfo) {
        int verifyType = this.zkVerOption.getVerifyType(this.mContext);
        if (verifyType == 0 || verifyType == 1 || verifyType == 3 || verifyType == 4 || verifyType == 5 || verifyType == 6 || verifyType == 7 || verifyType == 9 || verifyType == 10 || verifyType == 11 || verifyType == 12) {
            for (int i = 0; i < ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().size(); i++) {
                if (ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().get(i).getType() == 2) {
                    ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().remove(i);
                }
            }
        }
        if (verifyType == 2 || verifyType == 3 || verifyType == 4 || verifyType == 7 || verifyType == 11) {
            for (int i2 = 0; i2 < ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().size(); i2++) {
                if (ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().get(i2).getType() == 1) {
                    ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().remove(i2);
                }
            }
        }
        if (verifyType == 1 || verifyType == 2 || verifyType == 3 || verifyType == 5 || verifyType == 8 || verifyType == 9 || verifyType == 13) {
            for (int i3 = 0; i3 < ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().size(); i3++) {
                if (ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().get(i3).getType() == 4) {
                    ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().remove(i3);
                }
            }
        }
    }

    public void showUserFailedDialog(ZKVerViewBean zKVerViewBean, int i, String str, int i2, boolean z, boolean z2) {
        log("显示用户验证失败 dialog    errorMessage : " + str);
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i);
        if (!this.mContext.getString(R.string.multi_verify_wait).equals(str)) {
            if (z) {
                zKVerViewBean.setUiWayLogin(AppUtils.getString(R.string.dlg_ver_process_fa_title));
            }
            if (z2 && ZKLauncher.sLoopDeleteLog == 0) {
                zKVerViewBean.setUiAttFull(AppUtils.getString(R.string.server_att_full));
            }
            if ((ZKLauncher.sMaxBlackPhotoCount <= this.mDao.getBlackPhotoCount()) && ZKLauncher.mLoopDeleteBlackPic == 0 && !z2 && (ZKLauncher.sCameraSystem == 2 || ZKLauncher.sCameraSystem == 4)) {
                zKVerViewBean.setUiAttFull(AppUtils.getString(R.string.server_black_pic_full));
            }
            if (TextUtils.isEmpty(str)) {
                str = this.mContext.getResources().getString(R.string.dlg_ver_process_finger_fa_content);
                if (ZKVerProcessPar.CON_MARK_BEAN.getVerState() == 2 && fromInteger == ZKVerifyType.PASSWORD) {
                    str = this.mContext.getResources().getString(R.string.dlg_ver_process_password_msg);
                }
                if (i2 > 0 && ZKVerProcessPar.CON_MARK_BEAN.getIntent() != 1) {
                    str = String.format("%s E%d", new Object[]{str, Integer.valueOf(i2)});
                }
            } else if (i2 > 0 && ZKVerProcessPar.CON_MARK_BEAN.getIntent() != 1) {
                if (i2 == 68 || i2 == 69 || i2 == 48) {
                    str = String.format("%s", new Object[]{str});
                } else {
                    str = String.format("%s E%d", new Object[]{str, Integer.valueOf(i2)});
                }
            }
            zKVerViewBean.setFailMsg(str);
            switch (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()]) {
                case 1:
                    if (!TextUtils.isEmpty(str)) {
                        zKVerViewBean.setUiType(23);
                        break;
                    } else {
                        zKVerViewBean.setUiType(22);
                        break;
                    }
                case 2:
                    if (!ZKVerProcessPar.KEY_BOARD_1V1 || ZKLauncher.sFPRetry == 0 || ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_1V1_RETRY) != 0) {
                        ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(true);
                    } else {
                        ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(false);
                    }
                    zKVerViewBean.setUiType(12);
                    FileLogUtils.writeVerifyLog("fingerprintImage FailedDialog get BUNDLE_FP_BUFFER:" + (ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER) == null ? "== null" : "!= null"));
                    zKVerViewBean.setUiFingerprint(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER));
                    zKVerViewBean.setUiFPHeight(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_HEIGHT, 0));
                    zKVerViewBean.setUiFPWidth(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_WIDTH, 0));
                    break;
                case 3:
                    ZKVerProcessPar.ACTION_BEAN.setBolRFidRead(true);
                    zKVerViewBean.setUiType(42);
                    break;
                case 4:
                    ZKVerProcessPar.ACTION_BEAN.setBolPalm(true);
                    zKVerViewBean.setUiType(82);
                    break;
                case 5:
                    ZKVerProcessPar.ACTION_BEAN.setBolFace(true);
                    zKVerViewBean.setUiType(52);
                    break;
                case 6:
                    zKVerViewBean.setUiType(32);
                    break;
            }
        } else {
            zKVerViewBean.setUiType(100);
        }
        boolean z3 = ZKLauncher.sAccessRuleType == 1 && ZKVerifyType.PASSWORD == fromInteger && i2 == 26;
        LogUtils.verifyFormatLog("showUserFailedDialog verifyType:%s isMultiPWProcess:%s", fromInteger, Boolean.valueOf(z3));
        if (!z3) {
            ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        } else {
            ZKEventLauncher.setProcessDialogVisibility(false);
            resetVerifyProcess();
        }
        if (ZKVerProcessPar.CON_MARK_BEAN.getVerState() != 2) {
            ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            FileLogUtils.writeTouchLog("setTTouchAction: showUserFailedDialog");
        }
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
        if ((ZKVerProcessPar.CON_MARK_BEAN != null && ((ZKVerProcessPar.CON_MARK_BEAN.getLastType() != null && ZKVerProcessPar.CON_MARK_BEAN.getLastType().getType() == ZKVerifyType.PIN.getValue()) || ZKVerProcessPar.CON_MARK_BEAN.getVerState() != 2)) || i2 == 26) {
            ZKVerProcessPar.cleanData(17);
        }
    }

    private void showUserInvalidDialog(UserInfo userInfo, int i, boolean z) {
        String str;
        ZKVerConMarkBean zKVerConMarkBean = ZKVerProcessPar.CON_MARK_BEAN;
        ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
        zKVerViewBean.setUiName(userInfo.getName());
        zKVerViewBean.setUiPin(userInfo.getUser_PIN());
        boolean z2 = true;
        if (i == 1 || i == 2) {
            zKVerViewBean.setFailMsg(this.mContext.getResources().getString(R.string.dlg_ver_process_fa_title));
        } else {
            String string = this.mContext.getResources().getString(R.string.dlg_ver_process_fa_title);
            if (!z) {
                LogUtils.d(ZkLogTag.VERIFY_FLOW, "CARD_INVALID_TIME 1");
                str = zKVerViewBean.getErrorMessage(string, 29);
            } else {
                str = zKVerViewBean.getErrorMessage(string, 39);
            }
            zKVerViewBean.setFailMsg(str);
        }
        switch (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(zKVerConMarkBean.getLastType().getType()).ordinal()]) {
            case 1:
                zKVerViewBean.setUiType(23);
                break;
            case 2:
                zKVerViewBean.setUiType(13);
                FileLogUtils.writeVerifyLog("InvalidDialog get BUNDLE_FP_BUFFER:" + (ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER) == null ? "== null" : "!= null"));
                zKVerViewBean.setUiFingerprint(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER));
                zKVerViewBean.setUiFPHeight(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_HEIGHT, 0));
                zKVerViewBean.setUiFPWidth(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_WIDTH, 0));
                break;
            case 3:
                zKVerViewBean.setUiType(43);
                break;
            case 4:
                zKVerViewBean.setUiType(83);
                break;
            case 5:
                zKVerViewBean.setUiType(53);
                break;
            case 6:
                zKVerViewBean.setUiType(33);
                break;
        }
        if ((ZKLauncher.sMaxBlackPhotoCount <= this.mDao.getBlackPhotoCount()) && ZKLauncher.mLoopDeleteBlackPic == 0 && (ZKLauncher.sCameraSystem == 2 || ZKLauncher.sCameraSystem == 4)) {
            zKVerViewBean.setUiAttFull(AppUtils.getString(R.string.server_black_pic_full));
        }
        if (ZKLauncher.sMaxCaptureCount > this.mDao.getMaxCaptureCount()) {
            z2 = false;
        }
        if (z2 && ZKLauncher.sLoopDeletePic == 0 && (ZKLauncher.sCameraSystem == 2 || ZKLauncher.sCameraSystem == 3)) {
            zKVerViewBean.setUiSpecialState(AppUtils.getString(R.string.server_black_pic_full));
        }
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        resetVerifyProcess();
    }

    private void startVerifyRouter(int i, int i2, int i3, ArrayList<Boolean> arrayList) {
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        log("[stateWay][startVerifyRouter] start");
        log("[stateWay][startVerifyRouter] doorVerifyType is " + i);
        if (i != 25) {
            switch (i) {
                case -1:
                case 0:
                    if (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 1) {
                        log("[stateWay][startVerifyRouter] default change to state action");
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    }
                    log("[stateWay][startVerifyRouter] start Verify Password");
                    if (arrayList.get(0).booleanValue()) {
                        startVerifyPassword();
                        return;
                    } else if (arrayList.get(1).booleanValue()) {
                        startVerifyFinger();
                        return;
                    } else if (arrayList.get(2).booleanValue()) {
                        startVerifyCard();
                        return;
                    } else if (arrayList.get(3).booleanValue()) {
                        startVerifyFace();
                        return;
                    } else if (arrayList.get(4).booleanValue()) {
                        startVerifyPalm();
                        return;
                    } else {
                        return;
                    }
                case 1:
                    if (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 2) {
                        log("[stateWay][startVerifyRouter]default,show Finger Dialog");
                        startVerifyFinger();
                        return;
                    }
                    log("[stateWay][startVerifyRouter] verify type is finger, change to state action");
                    ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                    return;
                case 2:
                    if (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 1) {
                        log("[stateWay][startVerifyRouter]default,show pin dialog");
                        showPinDialog();
                        return;
                    }
                    log("[stateWay][startVerifyRouter]verify type pin,change to state action");
                    ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                    return;
                case 3:
                    int i4 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
                    if (i4 == 1) {
                        log("[stateWay][startVerifyRouter]verify_type_password,,start Verify Password");
                        startVerifyPassword();
                        return;
                    } else if (i4 != 6) {
                        log("[stateWay][startVerifyRouter]default,,show password dialog");
                        showPasswordDialog();
                        return;
                    } else {
                        log("[stateWay][startVerifyRouter]verify_type_password,,change to state action");
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    }
                case 4:
                    if (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 3) {
                        log("[stateWay][startVerifyRouter]default,show card dialog");
                        startVerifyCard();
                        return;
                    }
                    log("[stateWay][startVerifyRouter]verify_type_card,change state to action");
                    ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                    return;
                case 5:
                    int i5 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
                    if (i5 == 2 || i5 == 6) {
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    } else if (arrayList.get(0).booleanValue()) {
                        startVerifyPassword();
                        return;
                    } else if (arrayList.get(1).booleanValue()) {
                        startVerifyFinger();
                        return;
                    } else {
                        return;
                    }
                case 6:
                    int i6 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
                    if (i6 == 2 || i6 == 3) {
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    } else if (arrayList.get(1).booleanValue()) {
                        startVerifyFinger();
                        return;
                    } else if (arrayList.get(2).booleanValue()) {
                        startVerifyCard();
                        return;
                    } else {
                        return;
                    }
                case 7:
                    startVerify7(arrayList, i2);
                    return;
                case 8:
                    startVerify8(i3, i2);
                    return;
                case 9:
                    startVerify9(i3, i2);
                    return;
                case 10:
                    startVerify10(i3, i2);
                    return;
                case 11:
                    startVerify11(i3, i2);
                    return;
                case 12:
                    startVerify12(i3, i2);
                    return;
                case 13:
                    startVerify13(i3, i2);
                    return;
                case 14:
                    startVerify14(i3, i2);
                    return;
                case 15:
                    startVerify15(i2);
                    log("[stateWay][startVerifyRouter]face ,startVerify15");
                    return;
                case 16:
                    startVerify16(i3, i2);
                    return;
                case 17:
                    startVerify17(i3, i2);
                    return;
                case 18:
                    startVerify18(i3, i2);
                    return;
                case 19:
                    startVerify19(i3, i2);
                    return;
                case 20:
                    startVerify20(i3, i2);
                    return;
                default:
                    log("[stateWay][startVerifyRouter]default ,do nothing");
                    return;
            }
        } else if (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 4) {
            startVerifyPalm();
        } else {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        }
    }

    private void startVerify7(ArrayList<Boolean> arrayList, int i) {
        int i2 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(i).ordinal()];
        if (i2 == 3 || i2 == 6) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        } else if (arrayList.get(0).booleanValue()) {
            startVerifyPassword();
        } else if (arrayList.get(2).booleanValue()) {
            startVerifyCard();
        }
    }

    private void startVerify15(int i) {
        if (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(i).ordinal()] != 5) {
            log("[stateWay][startVerifyRouter][startVerify15]show Face Dialog");
            startVerifyFace();
            return;
        }
        log("[stateWay][startVerifyRouter][startVerify15]change State action");
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
    }

    private void startVerify14(int i, int i2) {
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i != 1) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
            return;
        }
        int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 != 1) {
            if (i3 == 2) {
                startVerify10(i, i2);
                return;
            } else if (i3 != 3) {
                startVerify10(i, i2);
                return;
            }
        }
        startVerifyFinger();
    }

    private void startVerify13(int i, int i2) {
        if (i > 1 && ZKVerProcessPar.OTHER_FLAG) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i == 1) {
            int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i3 == 1) {
                startVerifyPassword();
            } else if (i3 == 2) {
                disableVerifyType(ZKVerifyType.FINGER);
                startVerifyPin();
            } else if (i3 != 6) {
                ZKVerProcessPar.OTHER_FLAG = true;
                showPasswordDialog();
            } else {
                startVerifyFinger();
            }
        } else if (i != 2) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        } else {
            int i4 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i4 == 1) {
                startVerifyPassword();
            } else if (i4 != 2) {
                if (i4 == 6) {
                    startVerifyFinger();
                }
            } else if (ZKVerProcessPar.KEY_BOARD_1V1) {
                startVerifyPassword();
            } else {
                startVerifyPin();
            }
        }
    }

    private void startVerify12(int i, int i2) {
        if (i > 1 && (ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG)) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i == 1) {
            int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i3 == 2) {
                disableVerifyType(ZKVerifyType.FINGER);
                startVerifyPassword();
            } else if (i3 == 3) {
                startVerifyFinger();
            } else if (i3 != 6) {
                ZKVerProcessPar.OTHER_FLAG = true;
                startVerifyFinger();
            } else {
                startVerifyCard();
            }
        } else if (i != 2) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        } else {
            int i4 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i4 == 2) {
                disableVerifyType(ZKVerifyType.FINGER);
                startVerifyPassword();
            } else if (i4 == 3) {
                startVerifyFinger();
            } else if (i4 == 6) {
                startVerifyCard();
            }
        }
    }

    private void startVerify20(int i, int i2) {
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && i > 1) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i == 1) {
            int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i3 == 2) {
                disableVerifyType(ZKVerifyType.FINGER);
                startVerifyPassword();
            } else if (i3 == 5) {
                disableVerifyType(ZKVerifyType.FACE);
                startVerifyFinger();
            } else if (i3 != 6) {
                ZKVerProcessPar.OTHER_FLAG = true;
                startVerifyFace();
            } else {
                startVerifyFace();
            }
        } else if (i != 2) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        } else {
            int i4 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i4 == 2) {
                disableVerifyType(ZKVerifyType.FINGER);
                startVerifyPassword();
            } else if (i4 == 5) {
                disableVerifyType(ZKVerifyType.FACE);
                startVerifyFinger();
            } else if (i4 == 6) {
                startVerifyFace();
            }
        }
    }

    private void startVerify19(int i, int i2) {
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && i > 1) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i == 1) {
            int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i3 == 2) {
                startVerifyCard();
            } else if (i3 == 3) {
                startVerifyFace();
            } else if (i3 != 5) {
                ZKVerProcessPar.OTHER_FLAG = true;
                startVerifyFace();
            } else {
                startVerifyFinger();
            }
        } else if (i != 2) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        } else {
            int i4 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i4 == 2) {
                startVerifyCard();
            } else if (i4 == 3) {
                startVerifyFace();
            } else if (i4 == 5) {
                startVerifyFinger();
            }
        }
    }

    private void startVerify16(int i, int i2) {
        if (i > 1 && (ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG)) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i != 1) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
            return;
        }
        int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 2) {
            ZKVerProcessPar.OTHER_FLAG = false;
            disableVerifyType(ZKVerifyType.FINGER);
            startVerifyFace();
        } else if (i3 != 5) {
            ZKVerProcessPar.OTHER_FLAG = true;
            startVerifyFace();
        } else {
            disableVerifyType(ZKVerifyType.FACE);
            startVerifyFinger();
        }
    }

    private void startVerify18(int i, int i2) {
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && i > 1) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i != 1) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
            return;
        }
        int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 3) {
            ZKVerProcessPar.OTHER_FLAG = false;
            disableVerifyType(ZKVerifyType.CARD);
            startVerifyFace();
        } else if (i3 != 5) {
            ZKVerProcessPar.OTHER_FLAG = true;
            startVerifyFace();
        } else {
            disableVerifyType(ZKVerifyType.FACE);
            startVerifyCard();
        }
    }

    private void startVerify17(int i, int i2) {
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && i > 1) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i != 1) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
            return;
        }
        int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 5) {
            disableVerifyType(ZKVerifyType.FACE);
            startVerifyPassword();
        } else if (i3 != 6) {
            ZKVerProcessPar.OTHER_FLAG = true;
            startVerifyFace();
        } else {
            ZKVerProcessPar.OTHER_FLAG = false;
            disableVerifyType(ZKVerifyType.PASSWORD);
            startVerifyFace();
        }
    }

    private void startVerify8(int i, int i2) {
        if (ZKVerProcessPar.OTHER_FLAG) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i != 1) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
            return;
        }
        int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 1) {
            startVerifyFinger();
        } else if (i3 != 2) {
            ZKVerProcessPar.OTHER_FLAG = true;
            showFingerDialog(1);
        } else {
            disableVerifyType(ZKVerifyType.FINGER);
            startVerifyPin();
        }
    }

    private void startVerify9(int i, int i2) {
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && i > 1) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i != 1) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
            return;
        }
        int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 2) {
            disableVerifyType(ZKVerifyType.FINGER);
            startVerifyPassword();
        } else if (i3 != 6) {
            ZKVerProcessPar.OTHER_FLAG = true;
            startVerifyPassword();
        } else {
            startVerifyFinger();
        }
    }

    private void startVerify10(int i, int i2) {
        UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && i > 1) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i != 1) {
            changeStateToAction();
            return;
        }
        int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 2) {
            disableVerifyType(ZKVerifyType.FINGER);
            startVerifyCard();
        } else if (i3 != 3) {
            ZKVerProcessPar.OTHER_FLAG = true;
            startVerifyFinger();
        } else {
            disableVerifyType(ZKVerifyType.CARD);
            startVerifyFinger();
        }
    }

    private void startVerify11(int i, int i2) {
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && i > 1) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i != 1) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
            return;
        }
        int i3 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 3) {
            disableVerifyType(ZKVerifyType.CARD);
            startVerifyPassword();
        } else if (i3 != 6) {
            ZKVerProcessPar.OTHER_FLAG = true;
            startVerifyPassword();
        } else {
            startVerifyCard();
        }
    }

    private void showCheck1v1VerifyTypeDialog(ArrayList<Boolean> arrayList) {
        if (arrayList.get(0).booleanValue() && !arrayList.get(1).booleanValue() && !arrayList.get(2).booleanValue() && !arrayList.get(3).booleanValue() && !arrayList.get(4).booleanValue()) {
            ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.PASSWORD.getValue()));
            ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
        } else if (arrayList.get(1).booleanValue() && !arrayList.get(0).booleanValue() && !arrayList.get(2).booleanValue() && !arrayList.get(3).booleanValue() && !arrayList.get(4).booleanValue()) {
            ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.FINGER.getValue()));
            ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(true);
            ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
        } else if (arrayList.get(2).booleanValue() && !arrayList.get(0).booleanValue() && !arrayList.get(1).booleanValue() && !arrayList.get(3).booleanValue() && !arrayList.get(4).booleanValue()) {
            ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.CARD.getValue()));
            ZKVerProcessPar.ACTION_BEAN.setBolRFidRead(true);
            ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
        } else if (arrayList.get(3).booleanValue() && !arrayList.get(0).booleanValue() && !arrayList.get(1).booleanValue() && !arrayList.get(2).booleanValue() && !arrayList.get(4).booleanValue()) {
            ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.FACE.getValue(), true));
            ZKVerProcessPar.ACTION_BEAN.setBolFace(true);
            ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
        } else if (arrayList.get(4).booleanValue() && !arrayList.get(0).booleanValue() && !arrayList.get(1).booleanValue() && !arrayList.get(2).booleanValue() && !arrayList.get(3).booleanValue()) {
            ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.PALM.getValue(), true));
            ZKVerProcessPar.ACTION_BEAN.setBolPalm(true);
            ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
        }
        ZKVerProcessPar.CON_MARK_BEAN.getLastType().setState(true);
        ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
        zKVerViewBean.setUiType(1);
        zKVerViewBean.setRegister(arrayList);
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
        LogUtils.e(TAG, "stateWay:  员工管理（employeeMgnt）存储验证方式获取完毕！Dialog弹窗UI：%s ->stateWait ", Integer.valueOf(zKVerViewBean.getUiType()));
    }

    public void showUserSuccessDialog(ZKVerViewBean zKVerViewBean, int i) {
        boolean z = false;
        switch (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(i).ordinal()]) {
            case 1:
                zKVerViewBean.setUiType(21);
                break;
            case 2:
                zKVerViewBean.setUiType(11);
                FileLogUtils.writeVerifyLog("SuccessDialog get BUNDLE_FP_BUFFER:" + (ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER) == null ? "== null" : "!= null"));
                zKVerViewBean.setUiFingerprint(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER));
                zKVerViewBean.setUiFPHeight(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_HEIGHT, 0));
                zKVerViewBean.setUiFPWidth(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_WIDTH, 0));
                break;
            case 3:
                zKVerViewBean.setUiType(41);
                break;
            case 4:
                zKVerViewBean.setUiType(81);
                break;
            case 5:
                zKVerViewBean.setUiType(51);
                break;
            case 6:
                zKVerViewBean.setUiType(31);
                break;
        }
        FileLogUtils.writeVerifySuccessLog("userPin->" + zKVerViewBean.getUiPin() + "; " + "userName->" + zKVerViewBean.getUiName() + ": ");
        if (ZKLauncher.sMaxCaptureCount <= this.mDao.getMaxCaptureCount()) {
            z = true;
        }
        if (z && ZKLauncher.sLoopDeletePic == 0 && (ZKLauncher.sCameraSystem == 2 || ZKLauncher.sCameraSystem == 3)) {
            zKVerViewBean.setUiSpecialState(AppUtils.getString(R.string.server_acc_pic_full));
        }
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        ZKVerProcessPar.cleanData(18);
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    private void pictureAcc(String str, Date date) {
        if (ZKLauncher.sMaxCaptureCount > this.mDao.getMaxCaptureCount()) {
            picture(str, date, true, ZKLauncher.sCameraSystem);
        } else if (ZKLauncher.sLoopDeletePic > 0) {
            this.mDao.deleteCapture(ZKLauncher.sLoopDeletePic + (this.mDao.getMaxCaptureCount() - ZKLauncher.sMaxCaptureCount));
            picture(str, date, true, ZKLauncher.sCameraSystem);
        }
    }

    public void pictureBlack(String str, Date date) {
        if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 1) {
            return;
        }
        if (ZKLauncher.sMaxBlackPhotoCount > this.mDao.getBlackPhotoCount()) {
            picture(str, date, false, ZKLauncher.sCameraSystem);
        } else if (ZKLauncher.mLoopDeleteBlackPic > 0) {
            this.mDao.deleteBlackPhoto(ZKLauncher.mLoopDeleteBlackPic);
            picture(str, date, false, ZKLauncher.sCameraSystem);
        }
    }

    public class RemoteAuthReceiver extends BroadcastReceiver {
        public static final String ACTION_REMOTE_AUTH_FAILED = "com.zkteco.action.REMOTE_AUTH_FAILED";
        public static final String ACTION_REMOTE_AUTH_SUCCESS = "com.zkteco.action.REMOTE_AUTH_SUCCESS";
        public static final String ACTION_REMOTE_AUTH_TIMEOUT = "com.zkteco.action.REMOTE_AUTH_TIMEOUT";

        public RemoteAuthReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (ZKAccServerImpl.this.mdDisposable != null) {
                ZKAccServerImpl.this.mdDisposable.dispose();
            }
            String action = intent.getAction();
            LogUtils.d(ZKAccServerImpl.TAG, "RemoteAuthReceiver action : " + action);
            ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.REMOTE_AUTH_RESULT, action);
            ZKAccServerImpl.this.changeStateToRemoteAuth();
            LogUtils.d(ZKAccServerImpl.TAG, "changeStateToRemoteAuth action");
        }
    }
}
