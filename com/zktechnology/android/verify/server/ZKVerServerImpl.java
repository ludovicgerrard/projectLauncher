package com.zktechnology.android.verify.server;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.guide.guidecore.utils.ShutterHandler;
import com.zktechnology.android.att.AttResponse;
import com.zktechnology.android.att.AttVerifyCombination;
import com.zktechnology.android.att.DoorAttManager;
import com.zktechnology.android.event.EventBusHelper;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.launcher2.ZKEventLauncher;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.launcher2.ZkFaceLauncher;
import com.zktechnology.android.push.att.AttPush;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.push.util.Utils;
import com.zktechnology.android.rs232.ZKRS232EncryptManager;
import com.zktechnology.android.rs485.RS485Manager;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.ZkLogTag;
import com.zktechnology.android.verify.bean.process.ZKMarkTypeBean;
import com.zktechnology.android.verify.bean.process.ZKVerConMarkBean;
import com.zktechnology.android.verify.bean.process.ZKVerViewBean;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.dao.ZKVerDao;
import com.zktechnology.android.verify.dialog.managment.ZKVerDlgMgt;
import com.zktechnology.android.verify.utils.ZKCameraController;
import com.zktechnology.android.verify.utils.ZKConstantConfig;
import com.zktechnology.android.verify.utils.ZKTemperatureUtil;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zktechnology.android.wiegand.ZKWiegandManager;
import com.zkteco.android.core.model.ActionModel;
import com.zkteco.android.core.model.ButtonWidgetInfo;
import com.zkteco.android.core.sdk.BtnWidgetManager;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.orm.tna.AccGroup;
import com.zkteco.android.db.orm.tna.AttLog;
import com.zkteco.android.db.orm.tna.PhotoIndex;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ZKVerServerImpl extends BaseServerImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int AM_TYPE_APP = 2;
    private static final int AM_TYPE_ATT = 1;
    private static final int AM_TYPE_ATT_RECORD = 4;
    private static final int AM_TYPE_PERSONAL_MSG = 5;
    private static final int AM_TYPE_WORK_CODE = 3;
    public static int FACE_RECOGNIZE_MAX_FAILED_COUNT = 5;
    private static final String MUST_CHOICE_WORK_CODE = "1";
    private static final int OVER_VALID_DELETE_ATT_LOG = 0;
    private static final String TAG = "ZKVerServerImpl";
    private int faceVerFailedCount = 0;
    private boolean is1v1Verify;
    private boolean isCurrentVTRegister = false;
    private boolean isSuccess;
    private long lastVerifyTime = 0;
    private String lastVerifyUserPin = "";
    private ZKVerDao mDao;
    private boolean mHadSetVerifyCount;
    private int palmVerFailedCount = 0;
    private int replayTime;
    private int stateIntent;

    private void dismissPersonalMesDialog() {
    }

    private void dismissRecordDialog() {
    }

    private void showAttRecordDialog(UserInfo userInfo) {
    }

    public void stateDelay() {
    }

    public void stateRemoteAuth() {
    }

    public ZKVerServerImpl() {
        ZKVerDao zKVerDao = new ZKVerDao(this.mContext);
        this.mDao = zKVerDao;
        setDB(zKVerDao);
        AttPush.newInstance(new HubProtocolManager(this.mContext));
    }

    public void stateIntent() {
        try {
            dismissRecordDialog();
            int intent = ZKVerProcessPar.CON_MARK_BEAN.getIntent();
            this.stateIntent = intent;
            if (intent != 0) {
                if (intent != 1) {
                    if (intent != 2) {
                        if (intent == 5) {
                            ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
                        }
                    } else if (this.mDao.hasSuperUsers()) {
                        showSelectVerifyTypeDialog();
                    } else {
                        openApp();
                        resetVerifyProcess();
                    }
                } else if (this.mDao.hasSuperUsers()) {
                    showSelectVerifyTypeDialog();
                } else {
                    ZKEventLauncher.setProcessDialogVisibility(false);
                    EventBusHelper.post(this.mShowNoSuperDialog);
                    changeStateToWait();
                }
            } else if (ZKVerProcessPar.CON_MARK_BEAN.getLastType() == null) {
                resetVerifyProcess();
            } else {
                int i = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(ZKVerProcessPar.CON_MARK_BEAN.getLastType().getType()).ordinal()];
                if (i == 1) {
                    showInputPinDialog();
                    ZKTemperatureUtil.getInstance(this.mContext).needShowTemperature();
                } else if (i == 2 || i == 3 || i == 4 || i == 5) {
                    this.mHadSetVerifyCount = false;
                    ZKTemperatureUtil.getInstance(this.mContext).needShowTemperature();
                    changeStateToUser();
                } else {
                    changeStateToUser();
                }
            }
        } catch (Exception e) {
            handleException(e);
            log("[stateIntent]It happens a exception.We will reset verify Process and you can see the detail in trace/VerifyException.txt");
        }
    }

    /* renamed from: com.zktechnology.android.verify.server.ZKVerServerImpl$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
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
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PALM     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.CARD     // Catch:{ NoSuchFieldError -> 0x0033 }
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
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.server.ZKVerServerImpl.AnonymousClass1.<clinit>():void");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x01cf A[Catch:{ Exception -> 0x0322 }] */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01d4 A[Catch:{ Exception -> 0x0322 }] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x01c6 A[Catch:{ Exception -> 0x0322 }] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01c8 A[Catch:{ Exception -> 0x0322 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void stateUser() {
        /*
            r17 = this;
            r8 = r17
            java.util.Date r9 = new java.util.Date     // Catch:{ Exception -> 0x0322 }
            r9.<init>()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r10 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            if (r10 == 0) goto L_0x031e
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r0 = r10.getLastType()     // Catch:{ Exception -> 0x0322 }
            if (r0 != 0) goto L_0x0013
            goto L_0x031e
        L_0x0013:
            int r0 = r10.getVerState()     // Catch:{ Exception -> 0x0322 }
            r1 = 1
            r8.isCurrentVTRegister = r1     // Catch:{ Exception -> 0x0322 }
            com.zkteco.android.db.orm.tna.UserInfo r11 = r8.getUserInfoByVerifyState(r10)     // Catch:{ Exception -> 0x0322 }
            if (r11 == 0) goto L_0x003c
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r2 = r10.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r2 = r2.getType()     // Catch:{ Exception -> 0x0322 }
            boolean r2 = r8.checkVerifyType(r11, r2)     // Catch:{ Exception -> 0x0322 }
            if (r2 != 0) goto L_0x003c
            int r0 = r10.getIntent()     // Catch:{ Exception -> 0x0322 }
            if (r0 != 0) goto L_0x003b
            java.lang.String r0 = r11.getUser_PIN()     // Catch:{ Exception -> 0x0322 }
            r8.pictureBlack(r0, r9)     // Catch:{ Exception -> 0x0322 }
        L_0x003b:
            return
        L_0x003c:
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r2 = r10.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r2 = r2.getType()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r3 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ Exception -> 0x0322 }
            int r3 = r3.getValue()     // Catch:{ Exception -> 0x0322 }
            r12 = 0
            if (r2 != r3) goto L_0x004f
            r2 = r1
            goto L_0x0050
        L_0x004f:
            r2 = r12
        L_0x0050:
            java.lang.String r3 = "bundle_1v1_pwd_retry"
            java.lang.String r4 = "bundle_1v1_retry"
            r5 = 2
            if (r2 == 0) goto L_0x006b
            int r2 = r10.getVerState()     // Catch:{ Exception -> 0x0322 }
            if (r2 != r5) goto L_0x006b
            android.os.Bundle r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0322 }
            int r6 = com.zktechnology.android.launcher2.ZKLauncher.sFPRetry     // Catch:{ Exception -> 0x0322 }
            r2.putInt(r4, r6)     // Catch:{ Exception -> 0x0322 }
            android.os.Bundle r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0322 }
            int r6 = com.zktechnology.android.launcher2.ZKLauncher.sPWDRetry     // Catch:{ Exception -> 0x0322 }
            r2.putInt(r3, r6)     // Catch:{ Exception -> 0x0322 }
        L_0x006b:
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r2 = r10.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r2 = r2.getType()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r6 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ Exception -> 0x0322 }
            int r6 = r6.getValue()     // Catch:{ Exception -> 0x0322 }
            if (r2 != r6) goto L_0x007d
            r2 = r1
            goto L_0x007e
        L_0x007d:
            r2 = r12
        L_0x007e:
            if (r2 == 0) goto L_0x008f
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            int r2 = r2.getVerState()     // Catch:{ Exception -> 0x0322 }
            if (r2 != r5) goto L_0x008f
            android.os.Bundle r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0322 }
            int r6 = com.zktechnology.android.launcher2.ZKLauncher.sPWDRetry     // Catch:{ Exception -> 0x0322 }
            r2.putInt(r3, r6)     // Catch:{ Exception -> 0x0322 }
        L_0x008f:
            if (r11 == 0) goto L_0x0093
            r13 = r1
            goto L_0x0094
        L_0x0093:
            r13 = r12
        L_0x0094:
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r2 = r10.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r2 = r2.getType()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r6 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE     // Catch:{ Exception -> 0x0322 }
            int r6 = r6.getValue()     // Catch:{ Exception -> 0x0322 }
            if (r2 != r6) goto L_0x00a6
            r2 = r1
            goto L_0x00a7
        L_0x00a6:
            r2 = r12
        L_0x00a7:
            r6 = 2131755097(0x7f100059, float:1.9141064E38)
            r14 = 0
            if (r2 == 0) goto L_0x0191
            if (r13 == 0) goto L_0x0117
            r8.faceVerFailedCount = r12     // Catch:{ Exception -> 0x0322 }
            r16 = r13
            long r12 = r8.lastVerifyTime     // Catch:{ Exception -> 0x0322 }
            int r2 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r2 == 0) goto L_0x00fb
            java.lang.String r2 = ""
            java.lang.String r7 = r8.lastVerifyUserPin     // Catch:{ Exception -> 0x0322 }
            boolean r2 = r2.equals(r7)     // Catch:{ Exception -> 0x0322 }
            if (r2 == 0) goto L_0x00c5
            goto L_0x00fb
        L_0x00c5:
            java.lang.String r2 = r11.getUser_PIN()     // Catch:{ Exception -> 0x0322 }
            java.lang.String r7 = r8.lastVerifyUserPin     // Catch:{ Exception -> 0x0322 }
            boolean r2 = r2.equals(r7)     // Catch:{ Exception -> 0x0322 }
            if (r2 == 0) goto L_0x00ee
            long r12 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x0322 }
            long r14 = r8.lastVerifyTime     // Catch:{ Exception -> 0x0322 }
            long r12 = r12 - r14
            long r12 = java.lang.Math.abs(r12)     // Catch:{ Exception -> 0x0322 }
            int r2 = com.zktechnology.android.launcher2.ZkFaceLauncher.DETECT_FACE_MIN_TIME_INTERVAL     // Catch:{ Exception -> 0x0322 }
            long r14 = (long) r2     // Catch:{ Exception -> 0x0322 }
            int r2 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r2 >= 0) goto L_0x00e7
            r17.resetVerifyProcess()     // Catch:{ Exception -> 0x0322 }
            return
        L_0x00e7:
            long r12 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x0322 }
            r8.lastVerifyTime = r12     // Catch:{ Exception -> 0x0322 }
            goto L_0x0107
        L_0x00ee:
            long r12 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x0322 }
            r8.lastVerifyTime = r12     // Catch:{ Exception -> 0x0322 }
            java.lang.String r2 = r11.getUser_PIN()     // Catch:{ Exception -> 0x0322 }
            r8.lastVerifyUserPin = r2     // Catch:{ Exception -> 0x0322 }
            goto L_0x0107
        L_0x00fb:
            long r12 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x0322 }
            r8.lastVerifyTime = r12     // Catch:{ Exception -> 0x0322 }
            java.lang.String r2 = r11.getUser_PIN()     // Catch:{ Exception -> 0x0322 }
            r8.lastVerifyUserPin = r2     // Catch:{ Exception -> 0x0322 }
        L_0x0107:
            android.os.Bundle r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0322 }
            int r7 = com.zktechnology.android.launcher2.ZKLauncher.sFPRetry     // Catch:{ Exception -> 0x0322 }
            r2.putInt(r4, r7)     // Catch:{ Exception -> 0x0322 }
            android.os.Bundle r2 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0322 }
            int r7 = com.zktechnology.android.launcher2.ZKLauncher.sPWDRetry     // Catch:{ Exception -> 0x0322 }
            r2.putInt(r3, r7)     // Catch:{ Exception -> 0x0322 }
            goto L_0x0193
        L_0x0117:
            r16 = r13
            int r2 = r8.faceVerFailedCount     // Catch:{ Exception -> 0x0322 }
            int r2 = r2 + r1
            r8.faceVerFailedCount = r2     // Catch:{ Exception -> 0x0322 }
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.faceRecogizeMaxCount     // Catch:{ Exception -> 0x0322 }
            if (r2 < r3) goto L_0x0193
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sMaxAttLogCount     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.dao.ZKVerDao r2 = r8.mDao     // Catch:{ Exception -> 0x0322 }
            int r2 = r2.getAttLogCount()     // Catch:{ Exception -> 0x0322 }
            if (r0 > r2) goto L_0x012e
            r7 = r1
            goto L_0x012f
        L_0x012e:
            r7 = 0
        L_0x012f:
            int r0 = r10.getIntent()     // Catch:{ Exception -> 0x0322 }
            if (r0 == r1) goto L_0x0143
            boolean r0 = com.zktechnology.android.launcher2.ZKLauncher.sEnalbeIRTempDetection     // Catch:{ Exception -> 0x0322 }
            if (r0 != 0) goto L_0x013e
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.enalbeMaskDetection     // Catch:{ Exception -> 0x0322 }
            if (r0 != 0) goto L_0x013e
            goto L_0x0143
        L_0x013e:
            r8.unregisteredOpenDoor(r10, r11, r9)     // Catch:{ Exception -> 0x0322 }
        L_0x0141:
            r0 = 0
            goto L_0x018b
        L_0x0143:
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r2 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0322 }
            r2.<init>()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r0 = r10.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r3 = r0.getType()     // Catch:{ Exception -> 0x0322 }
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0322 }
            android.content.res.Resources r0 = r0.getResources()     // Catch:{ Exception -> 0x0322 }
            java.lang.String r4 = r0.getString(r6)     // Catch:{ Exception -> 0x0322 }
            r5 = 27
            r6 = 0
            r1 = r17
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0322 }
            r17.playSoundTryAgain()     // Catch:{ Exception -> 0x0322 }
            r1 = 0
            r8.pictureBlack(r1, r9)     // Catch:{ Exception -> 0x0322 }
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sLockFunOn     // Catch:{ Exception -> 0x0322 }
            r1 = 15
            if (r0 != r1) goto L_0x0141
            java.util.List r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()     // Catch:{ Exception -> 0x0322 }
            r0.clear()     // Catch:{ Exception -> 0x0322 }
            java.util.Map r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x0322 }
            r0.clear()     // Catch:{ Exception -> 0x0322 }
            r1 = 0
            com.zktechnology.android.att.DoorAttManager.lastTime = r1     // Catch:{ Exception -> 0x0322 }
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.att.DoorAttManager r0 = com.zktechnology.android.att.DoorAttManager.getInstance(r0)     // Catch:{ Exception -> 0x0322 }
            r0.verifyFailAlarm()     // Catch:{ Exception -> 0x0322 }
            goto L_0x0141
        L_0x018b:
            r8.faceVerFailedCount = r0     // Catch:{ Exception -> 0x0322 }
            r17.resetVerifyProcess()     // Catch:{ Exception -> 0x0322 }
            return
        L_0x0191:
            r16 = r13
        L_0x0193:
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r2 = r10.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r2 = r2.getType()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r3 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PALM     // Catch:{ Exception -> 0x0322 }
            int r3 = r3.getValue()     // Catch:{ Exception -> 0x0322 }
            if (r2 != r3) goto L_0x01a5
            r2 = r1
            goto L_0x01a6
        L_0x01a5:
            r2 = 0
        L_0x01a6:
            r3 = 3
            if (r2 == 0) goto L_0x01be
            if (r16 == 0) goto L_0x01af
            r2 = 0
            r8.palmVerFailedCount = r2     // Catch:{ Exception -> 0x0322 }
            goto L_0x01be
        L_0x01af:
            int r2 = r8.palmVerFailedCount     // Catch:{ Exception -> 0x0322 }
            int r2 = r2 + r1
            r8.palmVerFailedCount = r2     // Catch:{ Exception -> 0x0322 }
            if (r2 >= r3) goto L_0x01ba
            r17.resetVerifyProcess()     // Catch:{ Exception -> 0x0322 }
            return
        L_0x01ba:
            r2 = 0
            r8.palmVerFailedCount = r2     // Catch:{ Exception -> 0x0322 }
            goto L_0x01bf
        L_0x01be:
            r2 = 0
        L_0x01bf:
            int r7 = r10.getIntent()     // Catch:{ Exception -> 0x0322 }
            r12 = 5
            if (r7 != r12) goto L_0x01c8
            r7 = r1
            goto L_0x01c9
        L_0x01c8:
            r7 = r2
        L_0x01c9:
            if (r16 == 0) goto L_0x01d4
            boolean r12 = r8.isCurrentVTRegister     // Catch:{ Exception -> 0x0322 }
            if (r12 == 0) goto L_0x01d4
            r8.checkUserValid(r11)     // Catch:{ Exception -> 0x0322 }
            goto L_0x032b
        L_0x01d4:
            int r12 = com.zktechnology.android.verify.utils.ZKVerProcessPar.VERIFY_SOURCE_TYPE     // Catch:{ Exception -> 0x0322 }
            if (r12 != r3) goto L_0x01e8
            com.zktechnology.android.rs232.ZKRS232EncryptManager r3 = com.zktechnology.android.rs232.ZKRS232EncryptManager.getInstance()     // Catch:{ Exception -> 0x0322 }
            r3.failedCmd()     // Catch:{ Exception -> 0x0322 }
            android.content.Context r3 = r8.mContext     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.rs485.RS485Manager r3 = com.zktechnology.android.rs485.RS485Manager.getInstance(r3)     // Catch:{ Exception -> 0x0322 }
            r3.failedCmd()     // Catch:{ Exception -> 0x0322 }
        L_0x01e8:
            if (r7 == 0) goto L_0x01f7
            if (r16 != 0) goto L_0x01f0
            r1 = 0
            r8.pictureBlack(r1, r9)     // Catch:{ Exception -> 0x0322 }
        L_0x01f0:
            r17.showWiegandFailedDialog()     // Catch:{ Exception -> 0x0322 }
            r17.playSoundTryAgain()     // Catch:{ Exception -> 0x0322 }
            return
        L_0x01f7:
            if (r16 != 0) goto L_0x0223
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            if (r3 == 0) goto L_0x0223
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r3 = r3.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r3 = r3.getType()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r7 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE     // Catch:{ Exception -> 0x0322 }
            int r7 = r7.getValue()     // Catch:{ Exception -> 0x0322 }
            if (r3 != r7) goto L_0x0223
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            int r3 = r3.getVerState()     // Catch:{ Exception -> 0x0322 }
            if (r3 == r5) goto L_0x0223
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            int r3 = r3.getIntent()     // Catch:{ Exception -> 0x0322 }
            if (r3 == r1) goto L_0x0223
            r17.resetVerifyProcess()     // Catch:{ Exception -> 0x0322 }
            return
        L_0x0223:
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            int r3 = r3.getVerState()     // Catch:{ Exception -> 0x0322 }
            if (r3 != r5) goto L_0x022d
            r3 = r1
            goto L_0x022e
        L_0x022d:
            r3 = r2
        L_0x022e:
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r7 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            int r7 = r7.getIntent()     // Catch:{ Exception -> 0x0322 }
            if (r7 == r1) goto L_0x023b
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r7 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            r7.getIntent()     // Catch:{ Exception -> 0x0322 }
        L_0x023b:
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r7 = r10.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r7 = r7.getType()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r12 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ Exception -> 0x0322 }
            int r12 = r12.getValue()     // Catch:{ Exception -> 0x0322 }
            if (r7 != r12) goto L_0x025a
            if (r3 == 0) goto L_0x025a
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.sFPRetry     // Catch:{ Exception -> 0x0322 }
            if (r3 == 0) goto L_0x025a
            android.os.Bundle r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0322 }
            int r3 = r3.getInt(r4)     // Catch:{ Exception -> 0x0322 }
            if (r3 != 0) goto L_0x025a
            return
        L_0x025a:
            int r3 = r10.getIntent()     // Catch:{ Exception -> 0x0322 }
            if (r3 == r1) goto L_0x0278
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.enableUnregisterPass     // Catch:{ Exception -> 0x0322 }
            if (r3 == 0) goto L_0x0278
            if (r0 == r5) goto L_0x0278
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r0 = r10.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r0 = r0.getType()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r3 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ Exception -> 0x0322 }
            int r3 = r3.getValue()     // Catch:{ Exception -> 0x0322 }
            if (r0 != r3) goto L_0x0277
            goto L_0x0278
        L_0x0277:
            r1 = r2
        L_0x0278:
            if (r1 == 0) goto L_0x0316
            r17.playSoundTryAgain()     // Catch:{ Exception -> 0x0322 }
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0322 }
            android.content.res.Resources r0 = r0.getResources()     // Catch:{ Exception -> 0x0322 }
            java.lang.String r0 = r0.getString(r6)     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r1 = r1.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r1 = r1.getType()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD     // Catch:{ Exception -> 0x0322 }
            int r2 = r2.getValue()     // Catch:{ Exception -> 0x0322 }
            if (r1 != r2) goto L_0x02a6
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0322 }
            android.content.res.Resources r0 = r0.getResources()     // Catch:{ Exception -> 0x0322 }
            r1 = 2131755107(0x7f100063, float:1.9141084E38)
            java.lang.String r0 = r0.getString(r1)     // Catch:{ Exception -> 0x0322 }
        L_0x02a6:
            r4 = r0
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r2 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0322 }
            r2.<init>()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r0 = r0.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r3 = r0.getType()     // Catch:{ Exception -> 0x0322 }
            r5 = 27
            r6 = 0
            r7 = 0
            r1 = r17
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.wiegand.ZKWiegandManager r0 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()     // Catch:{ Exception -> 0x02c7 }
            r0.wiegandOutFailedID()     // Catch:{ Exception -> 0x02c7 }
            goto L_0x02cb
        L_0x02c7:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0322 }
        L_0x02cb:
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sLockFunOn     // Catch:{ Exception -> 0x0322 }
            r1 = 15
            if (r0 != r1) goto L_0x02ec
            java.util.List r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()     // Catch:{ Exception -> 0x0322 }
            r0.clear()     // Catch:{ Exception -> 0x0322 }
            java.util.Map r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x0322 }
            r0.clear()     // Catch:{ Exception -> 0x0322 }
            r1 = 0
            com.zktechnology.android.att.DoorAttManager.lastTime = r1     // Catch:{ Exception -> 0x0322 }
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.att.DoorAttManager r0 = com.zktechnology.android.att.DoorAttManager.getInstance(r0)     // Catch:{ Exception -> 0x0322 }
            r0.verifyFailAlarm()     // Catch:{ Exception -> 0x0322 }
        L_0x02ec:
            boolean r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.KEY_BOARD_1V1     // Catch:{ Exception -> 0x0322 }
            if (r0 != 0) goto L_0x030f
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r0 = r10.getLastType()     // Catch:{ Exception -> 0x0322 }
            int r0 = r0.getType()     // Catch:{ Exception -> 0x0322 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ Exception -> 0x0322 }
            int r1 = r1.getValue()     // Catch:{ Exception -> 0x0322 }
            if (r0 != r1) goto L_0x030f
            if (r16 != 0) goto L_0x0307
            r1 = 0
            r8.pictureBlack(r1, r9)     // Catch:{ Exception -> 0x0322 }
            goto L_0x0319
        L_0x0307:
            java.lang.String r0 = r11.getUser_PIN()     // Catch:{ Exception -> 0x0322 }
            r8.pictureBlack(r0, r9)     // Catch:{ Exception -> 0x0322 }
            goto L_0x0319
        L_0x030f:
            if (r16 != 0) goto L_0x0319
            r1 = 0
            r8.pictureBlack(r1, r9)     // Catch:{ Exception -> 0x0322 }
            goto L_0x0319
        L_0x0316:
            r8.unregisteredOpenDoor(r10, r11, r9)     // Catch:{ Exception -> 0x0322 }
        L_0x0319:
            r1 = 0
            r8.lastVerifyTime = r1     // Catch:{ Exception -> 0x0322 }
            goto L_0x032b
        L_0x031e:
            r17.resetVerifyProcess()     // Catch:{ Exception -> 0x0322 }
            return
        L_0x0322:
            r0 = move-exception
            r8.handleException(r0)
            java.lang.String r0 = "[stateUser]It happens a exception.We will reset verify Process and you can see the detail in trace/VerifyException.txt"
            r8.log(r0)
        L_0x032b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.server.ZKVerServerImpl.stateUser():void");
    }

    private void unregisteredOpenDoor(ZKVerConMarkBean zKVerConMarkBean, UserInfo userInfo, Date date) {
        boolean z;
        Date date2 = date;
        boolean z2 = ZKLauncher.sLockFunOn != 15;
        int type = zKVerConMarkBean.getLastType().getType();
        AttResponse attVerify = DoorAttManager.getInstance(this.mContext).attVerify(this.mDao, type, userInfo, ZKVerProcessPar.CON_MARK_BEAN.getVerState() == 2, z2, false);
        sendBro(attVerify);
        if (attVerify.getEventCode() == 1001) {
            resetVerifyProcess();
            return;
        }
        if (ZKLauncher.enableUnregisterPass == 1 && ZKLauncher.enableUnregisterCapture == 1) {
            boolean isEmpty = TextUtils.isEmpty(attVerify.getErrorMessage());
            if (ZKLauncher.sCameraSystem != 2 && (ZKLauncher.sCameraSystem != 3 ? ZKLauncher.sCameraSystem != 4 || isEmpty : !isEmpty)) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                if (ZKLauncher.sMaxBlackPhotoCount <= this.mDao.getBlackPhotoCount()) {
                    if (ZKLauncher.mLoopDeleteBlackPic > 0) {
                        this.mDao.deleteBlackPhoto(ZKLauncher.mLoopDeleteBlackPic);
                        if (isEmpty) {
                            picture((String) null, date2, true, 3);
                        } else {
                            picture((String) null, date2, false, 4);
                        }
                    }
                } else if (isEmpty) {
                    picture((String) null, date2, true, 3);
                } else {
                    picture((String) null, date2, false, 4);
                }
            }
        } else if (ZKLauncher.enableUnregisterPass == 0 && attVerify.getEventCode() != 27) {
            pictureBlack((String) null, date2);
        }
        ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        if (TextUtils.isEmpty(attVerify.getErrorMessage())) {
            zKVerViewBean.setUiSpecialState(this.mContext.getResources().getString(R.string.person_not_registered));
            if (!ZKLauncher.sIRTempDetectionFunOn || !ZKLauncher.sEnalbeIRTempDetection) {
                playSoundOk("");
            }
            this.mDao.addAttLog(userInfo, simpleDateFormat.format(date2).replace(" ", ExifInterface.GPS_DIRECTION_TRUE), type, ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WORK_CODE, 0), ZKVerProcessPar.CON_MARK_BEAN.getVerAttAction(), attVerify.getTemperature(), attVerify.getWearMask());
            showUserSuccessDialog(zKVerViewBean, zKVerConMarkBean.getLastType().getType());
        } else if (attVerify.getEventCode() == 68 || attVerify.getEventCode() == 69) {
            showUserFailedDialog(zKVerViewBean, zKVerConMarkBean.getLastType().getType(), attVerify.getErrorMessage(), attVerify.getEventCode(), false, ZKLauncher.sMaxAttLogCount <= this.mDao.getAttLogCount());
            this.mDao.addAttLog(userInfo, simpleDateFormat.format(date2).replace(" ", ExifInterface.GPS_DIRECTION_TRUE), type, ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WORK_CODE, 0), ZKVerProcessPar.CON_MARK_BEAN.getVerAttAction(), attVerify.getTemperature(), attVerify.getWearMask());
            ZKWiegandManager.getInstance().wiegandOutFailedID();
        } else if (attVerify.getEventCode() == 27) {
            showUserFailedDialog(zKVerViewBean, zKVerConMarkBean.getLastType().getType(), attVerify.getErrorMessage(), attVerify.getEventCode(), false, false);
        }
    }

    private boolean checkVerifyType(UserInfo userInfo, int i) {
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i);
        int verify_Type = userInfo.getVerify_Type();
        boolean z = false;
        if (verify_Type == -1) {
            verify_Type = 0;
        }
        AccGroup accGroup = null;
        try {
            accGroup = (AccGroup) new AccGroup().queryForId(Long.valueOf((long) userInfo.getAcc_Group_ID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (verify_Type == 100 && userInfo.getAcc_Group_ID() > 0 && accGroup != null) {
            verify_Type = accGroup.getVerification();
            Log.d(TAG, "checkVerifyType2: " + verify_Type);
        }
        AttVerifyCombination fromInteger2 = AttVerifyCombination.fromInteger(verify_Type);
        if (fromInteger2 != null) {
            List<ZKVerifyType> verifyTypes = fromInteger2.getVerifyTypes();
            int intent = ZKVerProcessPar.CON_MARK_BEAN.getIntent();
            int intOption = DBManager.getInstance().getIntOption("WiegandMethod", 0);
            if (intent == 5 && intOption == 0) {
                z = true;
            }
            if (verifyTypes.contains(fromInteger) || fromInteger == ZKVerifyType.PIN) {
                if (userInfo.getVerify_Type() == 100 && accGroup != null) {
                    int verification = accGroup.getVerification();
                    if (fromInteger == ZKVerifyType.PIN && verification != ZKVerifyType.PIN.getValue()) {
                        ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
                    }
                } else if (fromInteger == ZKVerifyType.PIN && userInfo.getVerify_Type() != ZKVerifyType.PIN.getValue()) {
                    ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
                }
                z = true;
            }
        }
        if (!z) {
            String string = this.mContext.getResources().getString(R.string.illegal_verify_type);
            ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
            zKVerViewBean.setFailMsg(string);
            playSoundTryAgain();
            if (ZKVerProcessPar.VERIFY_SOURCE_TYPE == 3) {
                ZKRS232EncryptManager.getInstance().failedCmd();
                RS485Manager.getInstance(this.mContext).failedCmd();
            }
            showUserFailedDialog(zKVerViewBean, i, string, 41, false, false);
            this.lastVerifyTime = 0;
            if (ZKLauncher.sLockFunOn == 15) {
                DoorAttManager.getAccGroupPinList().clear();
                DoorAttManager.getAccGroupIdMap().clear();
                DoorAttManager.lastTime = 0;
                DoorAttManager.getInstance(this.mContext).verifyFailAlarm();
            }
        }
        return z;
    }

    public void showUserFailedDialog(ZKVerViewBean zKVerViewBean, int i, String str, int i2, boolean z, boolean z2) {
        ZKVerViewBean zKVerViewBean2 = zKVerViewBean;
        String str2 = str;
        int i3 = i2;
        log("显示用户验证失败 dialog    errorMessage : " + str2);
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i);
        if (!this.mContext.getString(R.string.multi_verify_wait).equals(str2)) {
            if (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 6) {
                if (z) {
                    zKVerViewBean2.setUiWayLogin(AppUtils.getString(R.string.dlg_ver_process_fa_title));
                }
                if ((ZKLauncher.sMaxBlackPhotoCount <= this.mDao.getBlackPhotoCount()) && ZKLauncher.mLoopDeleteBlackPic == 0 && !z2 && (ZKLauncher.sCameraSystem == 2 || ZKLauncher.sCameraSystem == 4)) {
                    zKVerViewBean2.setUiAttFull(AppUtils.getString(R.string.server_black_pic_full));
                }
            } else {
                String string = str2 == null ? AppUtils.getString(R.string.dlg_ver_process_password_msg) : str2;
                if (i3 > 0) {
                    string = String.format("%s E%d", new Object[]{string, Integer.valueOf(i2)});
                }
                zKVerViewBean2.setUiWayLogin(string);
            }
            if (z2 && ZKLauncher.sLoopDeleteLog == 0) {
                zKVerViewBean2.setUiAttFull(AppUtils.getString(R.string.server_att_full));
            }
            switch (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()]) {
                case 1:
                    if (TextUtils.isEmpty(str)) {
                        zKVerViewBean2.setUiType(22);
                    } else {
                        zKVerViewBean2.setUiType(23);
                    }
                    if (i3 > 0) {
                        if (i3 != 68 && i3 != 69 && i3 != 48) {
                            zKVerViewBean2.setFailMsg(str2 + ExifInterface.LONGITUDE_EAST + i3);
                            break;
                        } else {
                            zKVerViewBean2.setFailMsg(str2);
                            break;
                        }
                    } else {
                        zKVerViewBean2.setFailMsg(str2);
                        break;
                    }
                case 2:
                    if (ZKVerProcessPar.CON_MARK_BEAN.getVerState() == 2) {
                        ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_1V1_RETRY, ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_1V1_RETRY) - 1);
                    }
                    if (ZKLauncher.sFPRetry == 0 || ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_1V1_RETRY) != 0 || ZKVerProcessPar.CON_MARK_BEAN.getVerState() != 2) {
                        if (ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_1V1_RETRY) > 0 && ZKVerProcessPar.CON_MARK_BEAN.getIntent() != 1) {
                            ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(true);
                            zKVerViewBean2.setUiType(12);
                            if (i3 <= 0) {
                                zKVerViewBean2.setFailMsg(str2);
                            } else if (i3 == 68 || i3 == 69 || i3 == 48) {
                                zKVerViewBean2.setFailMsg(str2);
                            } else {
                                zKVerViewBean2.setFailMsg(str2 + ExifInterface.LONGITUDE_EAST + i3);
                            }
                            zKVerViewBean2.setUiFingerprint(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER));
                            zKVerViewBean2.setUiFPHeight(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_HEIGHT, 0));
                            zKVerViewBean2.setUiFPWidth(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_WIDTH, 0));
                            break;
                        } else {
                            if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 1 || i3 == 0) {
                                zKVerViewBean2.setFailMsg(str2);
                            } else if (i3 == 68 || i3 == 69 || i3 == 48) {
                                zKVerViewBean2.setFailMsg(str2);
                            } else {
                                zKVerViewBean2.setFailMsg(str2 + ExifInterface.LONGITUDE_EAST + i3);
                            }
                            ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(true);
                            zKVerViewBean2.setUiType(12);
                            zKVerViewBean2.setUiFingerprint(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER));
                            zKVerViewBean2.setUiFPHeight(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_HEIGHT, 0));
                            zKVerViewBean2.setUiFPWidth(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_WIDTH, 0));
                            break;
                        }
                    } else {
                        zKVerViewBean2.setUiType(23);
                        if (i3 <= 0) {
                            zKVerViewBean2.setFailMsg(str2);
                        } else if (i3 == 68 || i3 == 69 || i3 == 48) {
                            zKVerViewBean2.setFailMsg(str2);
                        } else {
                            zKVerViewBean2.setFailMsg(str2 + ExifInterface.LONGITUDE_EAST + i3);
                        }
                        ZKLauncher.setProcessDialogTimeOut(1);
                        break;
                    }
                    break;
                case 3:
                    ZKVerProcessPar.ACTION_BEAN.setBolPalm(true);
                    if (i3 <= 0) {
                        zKVerViewBean2.setFailMsg(str2);
                    } else if (i3 == 68 || i3 == 69 || i3 == 48) {
                        zKVerViewBean2.setFailMsg(str2);
                    } else {
                        zKVerViewBean2.setFailMsg(str2 + ExifInterface.LONGITUDE_EAST + i3);
                    }
                    zKVerViewBean2.setUiType(82);
                    break;
                case 4:
                    ZKVerProcessPar.ACTION_BEAN.setBolRFidRead(true);
                    if (i3 <= 0) {
                        zKVerViewBean2.setFailMsg(str2);
                    } else if (i3 == 68 || i3 == 69 || i3 == 48) {
                        zKVerViewBean2.setFailMsg(str2);
                    } else {
                        zKVerViewBean2.setFailMsg(str2 + ExifInterface.LONGITUDE_EAST + i3);
                    }
                    zKVerViewBean2.setUiType(42);
                    break;
                case 5:
                    ZKVerProcessPar.ACTION_BEAN.setBolFace(true);
                    if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 1 || i3 == 0) {
                        zKVerViewBean2.setFailMsg(str2);
                    } else if (i3 == 68 || i3 == 69 || i3 == 48) {
                        zKVerViewBean2.setFailMsg(str2);
                    } else {
                        zKVerViewBean2.setFailMsg(str2 + ExifInterface.LONGITUDE_EAST + i3);
                    }
                    zKVerViewBean2.setUiType(52);
                    break;
                case 6:
                    ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_1V1_PWD_RETRY, ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_1V1_PWD_RETRY) - 1);
                    if (ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_1V1_PWD_RETRY) != 0) {
                        zKVerViewBean2.setUiType(32);
                        zKVerViewBean2.setFailMsg(str2);
                        zKVerViewBean2.setUiWayLogin(str2);
                        break;
                    } else {
                        zKVerViewBean2.setUiType(23);
                        if (i3 <= 0) {
                            zKVerViewBean2.setFailMsg(str2);
                        } else if (i3 == 68 || i3 == 69 || i3 == 48) {
                            zKVerViewBean2.setFailMsg(str2);
                        } else {
                            zKVerViewBean2.setFailMsg(str2 + ExifInterface.LONGITUDE_EAST + i3);
                        }
                        ZKLauncher.setProcessDialogTimeOut(1);
                        break;
                    }
                case 7:
                    zKVerViewBean2.setUiType(72);
                    zKVerViewBean2.setFailMsg(str2);
                    zKVerViewBean2.setUiWayLogin(str2);
                    break;
            }
        } else {
            zKVerViewBean2.setUiType(100);
        }
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 1 && fromInteger == ZKVerifyType.FACE) {
            try {
                Thread.sleep(ShutterHandler.NUC_TIME_MS);
                ZKEventLauncher.sendVerProcessDismissed();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (ZKVerProcessPar.CON_MARK_BEAN.getVerState() != 2) {
            ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
        }
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
        if ((ZKVerProcessPar.CON_MARK_BEAN.getLastType() != null && ZKVerProcessPar.CON_MARK_BEAN.getLastType().getType() == ZKVerifyType.PIN.getValue()) || ZKVerProcessPar.CON_MARK_BEAN.getVerState() != 2 || i3 == 26) {
            ZKVerProcessPar.cleanData(8);
        }
    }

    private UserInfo getUserInfoByVerifyState(ZKVerConMarkBean zKVerConMarkBean) {
        UserInfo userInfo;
        int verState = ZKVerProcessPar.CON_MARK_BEAN.getVerState();
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(zKVerConMarkBean.getLastType().getType());
        if (verState != 0 && verState != 1) {
            if (verState == 2) {
                UserInfo userInfo2 = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
                LogUtils.d(LogUtils.TAG_VERIFY, "UserTem<name=%s,pin=%s>", userInfo2.getName(), userInfo2.getUser_PIN());
                if (!this.mHadSetVerifyCount) {
                    ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_1V1_RETRY, ZKLauncher.sFPRetry);
                    this.mHadSetVerifyCount = true;
                }
                switch (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()]) {
                    case 1:
                        userInfo = getMultiUserInfoByPin(userInfo2);
                        break;
                    case 2:
                        userInfo = getMultiUserInfoByFinger(userInfo2);
                        break;
                    case 3:
                        userInfo = getMultiUserInfoByPalm(userInfo2);
                        break;
                    case 4:
                        userInfo = getMultiUserInfoByCard(userInfo2);
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
            switch (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()]) {
                case 1:
                    userInfo = getUserInfoByPin();
                    break;
                case 2:
                    userInfo = getUserInfoByFingerprint(ZKLauncher.mMThreshold);
                    break;
                case 3:
                    userInfo = getUserInfoByPalm();
                    break;
                case 4:
                    userInfo = getUserInfoByCard();
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
        if (!(userInfo == null || userInfo.getName() == null || !userInfo.getName().contains("&"))) {
            userInfo.setName(userInfo.getName().replace("&", " "));
        }
        return userInfo;
    }

    /* access modifiers changed from: protected */
    public UserInfo getMultiUserInfoByFinger(UserInfo userInfo) {
        if (ZKVerProcessPar.KEY_BOARD_1V1 && ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_1V1_RETRY) == 0 && ZKLauncher.sFPRetry == 0) {
            playSoundTryAgain();
            ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            ZKVerDlgMgt.pop();
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
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
            return null;
        }
        this.isCurrentVTRegister = true;
        LogUtils.d(LogUtils.TAG_VERIFY, "Multi-指纹 比对成功");
        return userInfo;
    }

    public void stateWay() {
        try {
            UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
            ZKVerConMarkBean zKVerConMarkBean = ZKVerProcessPar.CON_MARK_BEAN;
            if (!(userInfo == null || zKVerConMarkBean == null)) {
                if (zKVerConMarkBean.getLastType() != null) {
                    ArrayList<Boolean> registeredVerifyType = getRegisteredVerifyType(userInfo);
                    this.replayTime = ZKLauncher.sFPRetry;
                    Date date = new Date();
                    AccGroup accGroup = null;
                    if (!checkNeedVerifyTypeIsRegistered(userInfo, registeredVerifyType, date)) {
                        pictureBlack((String) null, date);
                        resetVerifyProcess();
                        return;
                    }
                    boolean z = true;
                    if (!ZKVerProcessPar.KEY_BOARD_1V1 || zKVerConMarkBean.getVerifyTypeList().size() != 1) {
                        z = false;
                    }
                    if (z) {
                        try {
                            accGroup = (AccGroup) new AccGroup().queryForId(Long.valueOf((long) userInfo.getAcc_Group_ID()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if (userInfo.getVerify_Type() != 100 || userInfo.getAcc_Group_ID() <= 0 || accGroup == null) {
                            if (userInfo.getVerify_Type() == ZKVerifyType.PIN.getValue()) {
                                ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                            } else {
                                showCheck1v1VerifyTypeDialog(registeredVerifyType);
                                return;
                            }
                        } else if (accGroup.getVerification() == ZKVerifyType.PIN.getValue()) {
                            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        } else {
                            showCheck1v1VerifyTypeDialog(registeredVerifyType);
                            return;
                        }
                    }
                    startVerifyRouter(userInfo, zKVerConMarkBean, registeredVerifyType);
                    return;
                }
            }
            resetVerifyProcess();
        } catch (Exception e2) {
            handleException(e2);
            log("[stateWay]It happens a exception.We will reset verify Process and you can see the detail in trace/VerifyException.txt");
        }
    }

    public void stateAction() {
        try {
            UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
            ZKVerConMarkBean zKVerConMarkBean = ZKVerProcessPar.CON_MARK_BEAN;
            if (zKVerConMarkBean != null) {
                if (userInfo != null) {
                    int intent = zKVerConMarkBean.getIntent();
                    boolean z = false;
                    if (intent == 0) {
                        BtnWidgetManager btnWidgetManager = new BtnWidgetManager(LauncherApplication.getLauncherApplicationContext());
                        if (btnWidgetManager.getPressedWidgetId() != -1) {
                            z = true;
                        }
                        if (z) {
                            startActionWithBtnPressed(btnWidgetManager, userInfo, zKVerConMarkBean);
                            return;
                        } else {
                            startActionWithoutBtnPressed(userInfo);
                            return;
                        }
                    } else if (intent == 1) {
                        ZKEventLauncher.setProcessDialogVisibility(false);
                        openMenu();
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
                        return;
                    } else if (intent == 2) {
                        BtnWidgetManager btnWidgetManager2 = new BtnWidgetManager(this.mContext);
                        if (btnWidgetManager2.getPressedWidgetId() != -1) {
                            z = true;
                        }
                        if (z) {
                            startActionWithBtnPressed(btnWidgetManager2, userInfo, zKVerConMarkBean);
                            return;
                        }
                        return;
                    } else if (intent == 5) {
                        ZKVerProcessPar.CON_MARK_BEAN.setVerBRepeatTime(true);
                        ZKVerProcessPar.CON_MARK_BEAN.setVerAttAction(this.mContext.getSharedPreferences("STATE_MODEL_SP", 0).getString("ACTION_PARAM", "255"));
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_RECORD);
                        return;
                    } else {
                        return;
                    }
                }
            }
            LogUtils.v(LogUtils.TAG_VERIFY, "stateAction:  null == parBean || null == parUser");
            resetVerifyProcess();
        } catch (Exception e) {
            handleException(e);
            log("[stateAction]It happens a exception.We will reset verify Process and you can see the detail in trace/VerifyException.txt");
        }
    }

    private void startActionWithBtnPressed(BtnWidgetManager btnWidgetManager, UserInfo userInfo, ZKVerConMarkBean zKVerConMarkBean) {
        boolean equals = "1".equals(ZKLauncher.sMustChoiceWorkCode);
        LogUtils.d(LogUtils.TAG_VERIFY, "是否必须输入工作号码：%s, tag=%s", Boolean.valueOf(equals), ZKLauncher.sMustChoiceWorkCode);
        ButtonWidgetInfo buttonWidgetInfo = btnWidgetManager.getBtnWidgets(btnWidgetManager.getPressedWidgetId()).get(btnWidgetManager.getPressedPosition());
        ActionModel action = buttonWidgetInfo.getAction();
        int actionType = action.getActionType();
        LogUtils.d(LogUtils.TAG_VERIFY, "点击的Widget: name=%s, actionType=%s", buttonWidgetInfo.getName(), Integer.valueOf(actionType));
        if (actionType == 1) {
            ZKVerProcessPar.CON_MARK_BEAN.setVerAttAction(action.getActionParams());
            ZKVerProcessPar.CON_MARK_BEAN.setVerBRepeatTime(true);
            if (equals) {
                showInputWorkCodeDialog(userInfo);
            } else {
                ZKVerController.getInstance().changeState(ZKVerConState.STATE_RECORD);
            }
        } else if (actionType == 2) {
            boolean z = userInfo.getPrivilege() == 14;
            LogUtils.d(LogUtils.TAG_VERIFY, "打开应用: User<name=%s> is super=%s", userInfo.getName(), Boolean.valueOf(z));
            if (z) {
                openApp();
                ZKEventLauncher.setProcessDialogVisibility(false);
            } else {
                showUserFailedDialog(new ZKVerViewBean(), zKVerConMarkBean.getLastType().getType(), this.mContext.getResources().getString(R.string.dlg_ver_process_fa_title), 27, false, false);
                playSoundTryAgain();
                if (ZKVerProcessPar.VERIFY_SOURCE_TYPE == 3) {
                    ZKRS232EncryptManager.getInstance().failedCmd();
                    RS485Manager.getInstance(this.mContext).failedCmd();
                }
            }
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
        } else if (actionType == 3) {
            showInputWorkCodeDialog(userInfo);
            ZKVerProcessPar.CON_MARK_BEAN.setVerBRepeatTime(true);
        }
    }

    private void startActionWithoutBtnPressed(UserInfo userInfo) {
        ZKVerProcessPar.CON_MARK_BEAN.setVerAttAction(this.mContext.getSharedPreferences("STATE_MODEL_SP", 0).getString("ACTION_PARAM", "255"));
        ZKVerProcessPar.CON_MARK_BEAN.setVerBRepeatTime(true);
        boolean equals = "1".equals(ZKLauncher.sMustChoiceWorkCode);
        LogUtils.d(LogUtils.TAG_VERIFY, "是否必须输入工作号码：%s, tag=%s", Boolean.valueOf(equals), ZKLauncher.sMustChoiceWorkCode);
        if (equals) {
            showInputWorkCodeDialog(userInfo);
        } else {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_RECORD);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x0246 A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0258 A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x027d A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0280 A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x029c A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x029e A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x02b0 A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x030e  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0317 A[SYNTHETIC, Splitter:B:127:0x0317] */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x032d A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x0349 A[ADDED_TO_REGION, Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x0362 A[ADDED_TO_REGION, Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x0387 A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x03ad A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01d1 A[ADDED_TO_REGION, Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0210 A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0212 A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x022c A[Catch:{ Exception -> 0x0691 }] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x022e A[Catch:{ Exception -> 0x0691 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void stateRecord() {
        /*
            r37 = this;
            r8 = r37
            java.lang.String r1 = "ZKVerServerImpl"
            java.lang.String r0 = "1"
            java.lang.String r2 = "Verify"
            android.os.Bundle r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0691 }
            java.lang.String r4 = "bundle_user_info"
            java.io.Serializable r3 = r3.getSerializable(r4)     // Catch:{ Exception -> 0x0691 }
            com.zkteco.android.db.orm.tna.UserInfo r3 = (com.zkteco.android.db.orm.tna.UserInfo) r3     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r4 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0691 }
            if (r4 == 0) goto L_0x068d
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r5 = r4.getLastType()     // Catch:{ Exception -> 0x0691 }
            if (r5 == 0) goto L_0x068d
            if (r3 != 0) goto L_0x002e
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r5 = r4.getLastType()     // Catch:{ Exception -> 0x0691 }
            int r5 = r5.getType()     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r6 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ Exception -> 0x0691 }
            int r6 = r6.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r5 != r6) goto L_0x068d
        L_0x002e:
            java.lang.String r5 = com.zktechnology.android.launcher2.ZKLauncher.sDateFmt     // Catch:{ Exception -> 0x0691 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0691 }
            if (r5 != 0) goto L_0x068d
            java.lang.String r5 = com.zktechnology.android.launcher2.ZKLauncher.sPhotoFunOn     // Catch:{ Exception -> 0x0691 }
            if (r5 == 0) goto L_0x068d
            java.lang.String r5 = com.zktechnology.android.launcher2.ZKLauncher.sShowPhoto     // Catch:{ Exception -> 0x0691 }
            if (r5 != 0) goto L_0x0040
            goto L_0x068d
        L_0x0040:
            java.text.SimpleDateFormat r5 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0691 }
            java.lang.String r6 = "yyyy-MM-dd HH:mm:ss"
            java.util.Locale r7 = java.util.Locale.US     // Catch:{ Exception -> 0x0691 }
            r5.<init>(r6, r7)     // Catch:{ Exception -> 0x0691 }
            java.util.Date r6 = new java.util.Date     // Catch:{ Exception -> 0x0691 }
            r6.<init>()     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r7 = r4.getLastType()     // Catch:{ Exception -> 0x0691 }
            int r7 = r7.getType()     // Catch:{ Exception -> 0x0691 }
            int r9 = com.zktechnology.android.launcher2.ZKLauncher.sLockFunOn     // Catch:{ Exception -> 0x0691 }
            r16 = 0
            r15 = 15
            if (r9 != r15) goto L_0x0084
            com.zktechnology.android.att.DoorAttManager.verifyInput = r16     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r9 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ Exception -> 0x0691 }
            int r9 = r9.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 != r9) goto L_0x0072
            android.os.Bundle r9 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0691 }
            java.lang.String r10 = "bundle_finger_template"
            java.lang.String r9 = r9.getString(r10)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.att.DoorAttManager.verifyInput = r9     // Catch:{ Exception -> 0x0691 }
        L_0x0072:
            com.zktechnology.android.verify.bean.process.ZKVerifyType r9 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD     // Catch:{ Exception -> 0x0691 }
            int r9 = r9.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 != r9) goto L_0x0084
            android.os.Bundle r9 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0691 }
            java.lang.String r10 = "bundle_password"
            java.lang.String r9 = r9.getString(r10)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.att.DoorAttManager.verifyInput = r9     // Catch:{ Exception -> 0x0691 }
        L_0x0084:
            java.lang.String r9 = r8.getRecordTime(r5, r6)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r10 = "1: current record time = %s"
            r14 = 1
            java.lang.Object[] r11 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0691 }
            r13 = 0
            r11[r13] = r9     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r10, r11)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r10 = com.zktechnology.android.launcher2.ZKLauncher.sPhotoFunOn     // Catch:{ Exception -> 0x0691 }
            boolean r10 = r0.equals(r10)     // Catch:{ Exception -> 0x0691 }
            if (r10 == 0) goto L_0x00a5
            java.lang.String r10 = com.zktechnology.android.launcher2.ZKLauncher.sShowPhoto     // Catch:{ Exception -> 0x0691 }
            boolean r0 = r0.equals(r10)     // Catch:{ Exception -> 0x0691 }
            if (r0 == 0) goto L_0x00a5
            r10 = r14
            goto L_0x00a6
        L_0x00a5:
            r10 = r13
        L_0x00a6:
            java.lang.String r0 = "2：is show user icon? %s"
            java.lang.Object[] r11 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0691 }
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r10)     // Catch:{ Exception -> 0x0691 }
            r11[r13] = r12     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r0, r11)     // Catch:{ Exception -> 0x0691 }
            int r0 = r8.stateIntent     // Catch:{ Exception -> 0x0691 }
            r11 = 5
            if (r0 != r11) goto L_0x00bb
            r18 = r14
            goto L_0x00bd
        L_0x00bb:
            r18 = r13
        L_0x00bd:
            java.lang.String r0 = "3：is wiegandIn? %s"
            java.lang.Object[] r12 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0691 }
            java.lang.Boolean r17 = java.lang.Boolean.valueOf(r18)     // Catch:{ Exception -> 0x0691 }
            r12[r13] = r17     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r0, r12)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0691 }
            java.lang.String r0 = r0.getVerAttAction()     // Catch:{ Exception -> 0x0691 }
            if (r0 != 0) goto L_0x00d5
            r0 = 255(0xff, float:3.57E-43)
            goto L_0x00df
        L_0x00d5:
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0691 }
            java.lang.String r0 = r0.getVerAttAction()     // Catch:{ Exception -> 0x0691 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0691 }
        L_0x00df:
            int r12 = com.zktechnology.android.launcher2.ZKLauncher.sLockFunOn     // Catch:{ Exception -> 0x0691 }
            if (r12 != r15) goto L_0x00ee
            int r12 = com.zktechnology.android.launcher2.ZKLauncher.sAPBFO     // Catch:{ Exception -> 0x0691 }
            if (r12 != r14) goto L_0x00ee
            int r12 = com.zktechnology.android.launcher2.ZKLauncher.sAntiPassbackOn     // Catch:{ Exception -> 0x0691 }
            if (r12 <= 0) goto L_0x00ee
            r12 = 255(0xff, float:3.57E-43)
            goto L_0x00ef
        L_0x00ee:
            r12 = r0
        L_0x00ef:
            java.lang.String r0 = "4：att action = %s"
            java.lang.Object[] r15 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0691 }
            java.lang.Integer r19 = java.lang.Integer.valueOf(r12)     // Catch:{ Exception -> 0x0691 }
            r15[r13] = r19     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r0, r15)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0691 }
            boolean r15 = r0.isVerBRepeatTime()     // Catch:{ Exception -> 0x0691 }
            java.lang.String r0 = "5：is support repeat time? %s"
            java.lang.Object[] r11 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0691 }
            java.lang.Boolean r20 = java.lang.Boolean.valueOf(r15)     // Catch:{ Exception -> 0x0691 }
            r11[r13] = r20     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r0, r11)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r0 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ Exception -> 0x0691 }
            int r0 = r0.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 == r0) goto L_0x0122
            com.zktechnology.android.verify.dao.ZKVerDao r0 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            java.lang.String r11 = r3.getUser_PIN()     // Catch:{ Exception -> 0x0691 }
            java.lang.String r0 = r0.getLastVerifyTime(r11)     // Catch:{ Exception -> 0x0691 }
            goto L_0x0124
        L_0x0122:
            r0 = r16
        L_0x0124:
            java.lang.String r11 = "6：last verify time =  %s"
            r20 = r15
            java.lang.Object[] r15 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0691 }
            r15[r13] = r0     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r11, r15)     // Catch:{ Exception -> 0x0691 }
            boolean r11 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0691 }
            if (r11 != 0) goto L_0x0137
            r11 = r14
            goto L_0x0138
        L_0x0137:
            r11 = r13
        L_0x0138:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0691 }
            r15.<init>()     // Catch:{ Exception -> 0x0691 }
            java.lang.String r13 = "7：has last verify time?        "
            java.lang.StringBuilder r13 = r15.append(r13)     // Catch:{ Exception -> 0x0691 }
            java.lang.StringBuilder r13 = r13.append(r11)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r1, r13)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r13 = "7：has last verify time? %s"
            java.lang.Object[] r15 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0691 }
            r21 = 0
            r15[r21] = r0     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r13, r15)     // Catch:{ Exception -> 0x0691 }
            if (r11 == 0) goto L_0x01c6
            java.util.Date r13 = new java.util.Date     // Catch:{ ParseException -> 0x01ac }
            java.util.Date r15 = r5.parse(r0)     // Catch:{ ParseException -> 0x01ac }
            long r14 = r15.getTime()     // Catch:{ ParseException -> 0x01ac }
            r13.<init>(r14)     // Catch:{ ParseException -> 0x01ac }
            java.lang.String r14 = "8：repeat time start = %s"
            r23 = r11
            r15 = 1
            java.lang.Object[] r11 = new java.lang.Object[r15]     // Catch:{ ParseException -> 0x019c }
            r15 = 0
            r11[r15] = r13     // Catch:{ ParseException -> 0x019c }
            com.zktechnology.android.utils.LogUtils.d(r2, r14, r11)     // Catch:{ ParseException -> 0x019c }
            java.util.Date r11 = new java.util.Date     // Catch:{ ParseException -> 0x019c }
            java.util.Date r0 = r5.parse(r0)     // Catch:{ ParseException -> 0x019c }
            long r14 = r0.getTime()     // Catch:{ ParseException -> 0x019c }
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sAlarmReRec     // Catch:{ ParseException -> 0x019c }
            int r0 = r0 * 1000
            r24 = r4
            r25 = r5
            long r4 = (long) r0
            long r14 = r14 + r4
            r11.<init>(r14)     // Catch:{ ParseException -> 0x019a }
            java.lang.String r0 = "9：repeat time end = %s"
            r4 = 1
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ ParseException -> 0x0198 }
            r4 = 0
            r5[r4] = r11     // Catch:{ ParseException -> 0x0198 }
            com.zktechnology.android.utils.LogUtils.d(r2, r0, r5)     // Catch:{ ParseException -> 0x0198 }
            goto L_0x01cf
        L_0x0198:
            r0 = move-exception
            goto L_0x01b6
        L_0x019a:
            r0 = move-exception
            goto L_0x01a9
        L_0x019c:
            r0 = move-exception
            r24 = r4
            r25 = r5
            goto L_0x01a9
        L_0x01a2:
            r0 = move-exception
            r24 = r4
            r25 = r5
            r23 = r11
        L_0x01a9:
            r11 = r16
            goto L_0x01b6
        L_0x01ac:
            r0 = move-exception
            r24 = r4
            r25 = r5
            r23 = r11
            r11 = r16
            r13 = r11
        L_0x01b6:
            java.lang.String r4 = "8.9：parse time error,Message: %s"
            r5 = 1
            java.lang.Object[] r14 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0691 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ Exception -> 0x0691 }
            r5 = 0
            r14[r5] = r0     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.e(r2, r4, r14)     // Catch:{ Exception -> 0x0691 }
            goto L_0x01cf
        L_0x01c6:
            r24 = r4
            r25 = r5
            r23 = r11
            r11 = r16
            r13 = r11
        L_0x01cf:
            if (r13 == 0) goto L_0x01fe
            if (r11 == 0) goto L_0x01fe
            long r4 = r6.getTime()     // Catch:{ Exception -> 0x0691 }
            long r13 = r13.getTime()     // Catch:{ Exception -> 0x0691 }
            int r0 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r0 < 0) goto L_0x01ed
            long r4 = r6.getTime()     // Catch:{ Exception -> 0x0691 }
            long r13 = r11.getTime()     // Catch:{ Exception -> 0x0691 }
            int r0 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r0 > 0) goto L_0x01ed
            r0 = 1
            goto L_0x01ee
        L_0x01ed:
            r0 = 0
        L_0x01ee:
            java.lang.String r4 = "10：in repeat time zone? %s"
            r5 = 1
            java.lang.Object[] r11 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0691 }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0691 }
            r13 = 0
            r11[r13] = r5     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.e(r2, r4, r11)     // Catch:{ Exception -> 0x0691 }
            goto L_0x01ff
        L_0x01fe:
            r0 = 0
        L_0x01ff:
            int r4 = com.zktechnology.android.launcher2.ZKLauncher.sMaxAttLogCount     // Catch:{ Exception -> 0x0691 }
            int r5 = com.zktechnology.android.launcher2.ZKLauncher.sAlarmAttLog     // Catch:{ Exception -> 0x0691 }
            int r4 = r4 - r5
            com.zktechnology.android.verify.dao.ZKVerDao r5 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            int r5 = r5.getAttLogCount()     // Catch:{ Exception -> 0x0691 }
            if (r4 > r5) goto L_0x0212
            int r4 = com.zktechnology.android.launcher2.ZKLauncher.sLoopDeleteLog     // Catch:{ Exception -> 0x0691 }
            if (r4 != 0) goto L_0x0212
            r4 = 1
            goto L_0x0213
        L_0x0212:
            r4 = 0
        L_0x0213:
            java.lang.String r5 = "11：show log full alarm? %s"
            r11 = 1
            java.lang.Object[] r13 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0691 }
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r4)     // Catch:{ Exception -> 0x0691 }
            r14 = 0
            r13[r14] = r11     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r5, r13)     // Catch:{ Exception -> 0x0691 }
            int r5 = com.zktechnology.android.launcher2.ZKLauncher.sMaxAttLogCount     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.dao.ZKVerDao r11 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            int r11 = r11.getAttLogCount()     // Catch:{ Exception -> 0x0691 }
            if (r5 > r11) goto L_0x022e
            r5 = 1
            goto L_0x022f
        L_0x022e:
            r5 = 0
        L_0x022f:
            java.lang.String r11 = "12：is log full? %s"
            r13 = 1
            java.lang.Object[] r14 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0691 }
            java.lang.Boolean r13 = java.lang.Boolean.valueOf(r5)     // Catch:{ Exception -> 0x0691 }
            r15 = 0
            r14[r15] = r13     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r11, r14)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r11 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ Exception -> 0x0691 }
            int r11 = r11.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 == r11) goto L_0x0258
            com.zktechnology.android.verify.dao.ZKVerDao r11 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            java.lang.String r13 = r3.getUser_PIN()     // Catch:{ Exception -> 0x0691 }
            int r11 = r11.getUserSmsCount(r13)     // Catch:{ Exception -> 0x0691 }
            if (r11 <= 0) goto L_0x0254
            r11 = 1
            goto L_0x0255
        L_0x0254:
            r11 = 0
        L_0x0255:
            r26 = r11
            goto L_0x025a
        L_0x0258:
            r26 = 0
        L_0x025a:
            java.lang.String r11 = "13：has sms? %s"
            r13 = 1
            java.lang.Object[] r14 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0691 }
            java.lang.Boolean r13 = java.lang.Boolean.valueOf(r26)     // Catch:{ Exception -> 0x0691 }
            r15 = 0
            r14[r15] = r13     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r11, r14)     // Catch:{ Exception -> 0x0691 }
            int r15 = com.zktechnology.android.launcher2.ZKLauncher.sLoopDeleteLog     // Catch:{ Exception -> 0x0691 }
            java.lang.String r11 = "14：loop delete count = %s"
            r13 = 1
            java.lang.Object[] r14 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0691 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r15)     // Catch:{ Exception -> 0x0691 }
            r21 = 0
            r14[r21] = r13     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r11, r14)     // Catch:{ Exception -> 0x0691 }
            if (r15 <= 0) goto L_0x0280
            r27 = 1
            goto L_0x0282
        L_0x0280:
            r27 = 0
        L_0x0282:
            java.lang.String r11 = "15：support loop delete? %s"
            r13 = 1
            java.lang.Object[] r14 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0691 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r15)     // Catch:{ Exception -> 0x0691 }
            r21 = 0
            r14[r21] = r13     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r2, r11, r14)     // Catch:{ Exception -> 0x0691 }
            int r2 = com.zktechnology.android.launcher2.ZKLauncher.sMaxCaptureCount     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.dao.ZKVerDao r11 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            int r11 = r11.getMaxCaptureCount()     // Catch:{ Exception -> 0x0691 }
            if (r2 > r11) goto L_0x029e
            r2 = 1
            goto L_0x029f
        L_0x029e:
            r2 = 0
        L_0x029f:
            int r11 = com.zktechnology.android.launcher2.ZKLauncher.sMaxBlackPhotoCount     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.dao.ZKVerDao r13 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            int r13 = r13.getBlackPhotoCount()     // Catch:{ Exception -> 0x0691 }
            boolean r11 = com.zktechnology.android.launcher2.ZKLauncher.locationFunOn     // Catch:{ Exception -> 0x0691 }
            r13 = 2
            if (r11 == 0) goto L_0x030e
            boolean r11 = com.zktechnology.android.launcher2.ZKLauncher.isLimitAttLocateFunOn     // Catch:{ Exception -> 0x0691 }
            if (r11 == 0) goto L_0x030e
            double r28 = com.zktechnology.android.launcher2.ZKLauncher.longitude     // Catch:{ Exception -> 0x0691 }
            double r30 = com.zktechnology.android.launcher2.ZKLauncher.latitude     // Catch:{ Exception -> 0x0691 }
            double r32 = com.zktechnology.android.launcher2.ZKLauncher.targetLongitude     // Catch:{ Exception -> 0x0691 }
            double r34 = com.zktechnology.android.launcher2.ZKLauncher.targetLatitude     // Catch:{ Exception -> 0x0691 }
            double r28 = com.zktechnology.android.utils.DistanceUtils.getDistance(r28, r30, r32, r34)     // Catch:{ Exception -> 0x0691 }
            int r11 = com.zktechnology.android.launcher2.ZKLauncher.locationRange     // Catch:{ Exception -> 0x0691 }
            r30 = r15
            double r14 = (double) r11     // Catch:{ Exception -> 0x0691 }
            int r11 = (r28 > r14 ? 1 : (r28 == r14 ? 0 : -1))
            if (r11 >= 0) goto L_0x02c7
            r11 = 1
            goto L_0x02c8
        L_0x02c7:
            r11 = 0
        L_0x02c8:
            java.lang.String r14 = "stateRecord: [%f,%f]-[%f,%f]=[%f],[%d]"
            r15 = 6
            java.lang.Object[] r15 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x0691 }
            double r32 = com.zktechnology.android.launcher2.ZKLauncher.longitude     // Catch:{ Exception -> 0x0691 }
            java.lang.Double r32 = java.lang.Double.valueOf(r32)     // Catch:{ Exception -> 0x0691 }
            r21 = 0
            r15[r21] = r32     // Catch:{ Exception -> 0x0691 }
            double r32 = com.zktechnology.android.launcher2.ZKLauncher.latitude     // Catch:{ Exception -> 0x0691 }
            java.lang.Double r32 = java.lang.Double.valueOf(r32)     // Catch:{ Exception -> 0x0691 }
            r22 = 1
            r15[r22] = r32     // Catch:{ Exception -> 0x0691 }
            double r32 = com.zktechnology.android.launcher2.ZKLauncher.targetLongitude     // Catch:{ Exception -> 0x0691 }
            java.lang.Double r32 = java.lang.Double.valueOf(r32)     // Catch:{ Exception -> 0x0691 }
            r15[r13] = r32     // Catch:{ Exception -> 0x0691 }
            double r32 = com.zktechnology.android.launcher2.ZKLauncher.targetLatitude     // Catch:{ Exception -> 0x0691 }
            java.lang.Double r32 = java.lang.Double.valueOf(r32)     // Catch:{ Exception -> 0x0691 }
            r31 = 3
            r15[r31] = r32     // Catch:{ Exception -> 0x0691 }
            r32 = 4
            java.lang.Double r28 = java.lang.Double.valueOf(r28)     // Catch:{ Exception -> 0x0691 }
            r15[r32] = r28     // Catch:{ Exception -> 0x0691 }
            int r28 = com.zktechnology.android.launcher2.ZKLauncher.locationRange     // Catch:{ Exception -> 0x0691 }
            java.lang.Integer r28 = java.lang.Integer.valueOf(r28)     // Catch:{ Exception -> 0x0691 }
            r19 = 5
            r15[r19] = r28     // Catch:{ Exception -> 0x0691 }
            java.lang.String r14 = java.lang.String.format(r14, r15)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.utils.LogUtils.d(r1, r14)     // Catch:{ Exception -> 0x0691 }
            r15 = r11
            goto L_0x0313
        L_0x030e:
            r30 = r15
            r21 = 0
            r15 = 1
        L_0x0313:
            java.lang.String r1 = ""
            if (r3 == 0) goto L_0x0320
            java.lang.String r11 = r3.getName()     // Catch:{ Exception -> 0x0691 }
            if (r11 != 0) goto L_0x0320
            r3.setName(r1)     // Catch:{ Exception -> 0x0691 }
        L_0x0320:
            com.zktechnology.android.verify.bean.process.ZKVerViewBean r14 = new com.zktechnology.android.verify.bean.process.ZKVerViewBean     // Catch:{ Exception -> 0x0691 }
            r14.<init>()     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerifyType r11 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ Exception -> 0x0691 }
            int r11 = r11.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 == r11) goto L_0x033b
            java.lang.String r11 = r3.getName()     // Catch:{ Exception -> 0x0691 }
            r14.setUiName(r11)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r11 = r3.getUser_PIN()     // Catch:{ Exception -> 0x0691 }
            r14.setUiPin(r11)     // Catch:{ Exception -> 0x0691 }
        L_0x033b:
            r14.setUiSignInTime(r9)     // Catch:{ Exception -> 0x0691 }
            r14.setUiTFPhoto(r10)     // Catch:{ Exception -> 0x0691 }
            r14.setUiStatus(r12)     // Catch:{ Exception -> 0x0691 }
            r9 = 2131755293(0x7f10011d, float:1.9141461E38)
            if (r5 != 0) goto L_0x0360
            if (r4 == 0) goto L_0x0360
            if (r27 != 0) goto L_0x0360
            com.zktechnology.android.verify.bean.process.ZKVerifyType r4 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ Exception -> 0x0691 }
            int r4 = r4.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 == r4) goto L_0x0360
            r2 = 2131755298(0x7f100122, float:1.9141471E38)
            java.lang.String r2 = com.zktechnology.android.utils.AppUtils.getString(r2)     // Catch:{ Exception -> 0x0691 }
            r14.setUiSpecialState(r2)     // Catch:{ Exception -> 0x0691 }
            goto L_0x0385
        L_0x0360:
            if (r5 == 0) goto L_0x036c
            if (r27 != 0) goto L_0x036c
            java.lang.String r2 = com.zktechnology.android.utils.AppUtils.getString(r9)     // Catch:{ Exception -> 0x0691 }
            r14.setUiSpecialState(r2)     // Catch:{ Exception -> 0x0691 }
            goto L_0x0385
        L_0x036c:
            if (r2 == 0) goto L_0x0385
            int r2 = com.zktechnology.android.launcher2.ZKLauncher.sLoopDeletePic     // Catch:{ Exception -> 0x0691 }
            if (r2 != 0) goto L_0x0385
            int r2 = com.zktechnology.android.launcher2.ZKLauncher.sCameraSystem     // Catch:{ Exception -> 0x0691 }
            if (r2 == r13) goto L_0x037b
            int r2 = com.zktechnology.android.launcher2.ZKLauncher.sCameraSystem     // Catch:{ Exception -> 0x0691 }
            r4 = 3
            if (r2 != r4) goto L_0x0385
        L_0x037b:
            r2 = 2131755294(0x7f10011e, float:1.9141463E38)
            java.lang.String r2 = com.zktechnology.android.utils.AppUtils.getString(r2)     // Catch:{ Exception -> 0x0691 }
            r14.setUiSpecialState(r2)     // Catch:{ Exception -> 0x0691 }
        L_0x0385:
            if (r15 != 0) goto L_0x03ad
            r0 = 2131755252(0x7f1000f4, float:1.9141378E38)
            java.lang.String r0 = com.zktechnology.android.utils.AppUtils.getString(r0)     // Catch:{ Exception -> 0x0691 }
            r14.setUiAttFull(r0)     // Catch:{ Exception -> 0x0691 }
            r37.playSoundTryAgain()     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r0 = r24.getLastType()     // Catch:{ Exception -> 0x0691 }
            int r3 = r0.getType()     // Catch:{ Exception -> 0x0691 }
            r0 = 2131755098(0x7f10005a, float:1.9141066E38)
            java.lang.String r4 = com.zktechnology.android.utils.AppUtils.getString(r0)     // Catch:{ Exception -> 0x0691 }
            r5 = -1
            r6 = 0
            r7 = 0
            r1 = r37
            r2 = r14
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0691 }
            return
        L_0x03ad:
            r28 = 0
            if (r5 == 0) goto L_0x0425
            if (r27 != 0) goto L_0x0425
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ Exception -> 0x0691 }
            int r2 = r2.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 == r2) goto L_0x0425
            com.zktechnology.android.wiegand.ZKWiegandManager r0 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()     // Catch:{ Exception -> 0x0691 }
            r0.wiegandOutFailedID()     // Catch:{ Exception -> 0x0691 }
            java.lang.String r0 = r3.getUser_PIN()     // Catch:{ Exception -> 0x0691 }
            r8.pictureBlack(r0, r6)     // Catch:{ Exception -> 0x0691 }
            r37.playSoundTryAgain()     // Catch:{ Exception -> 0x0691 }
            if (r18 == 0) goto L_0x03d3
            r2 = 1
            r8.showWiegandFailedDialog(r2)     // Catch:{ Exception -> 0x0691 }
            goto L_0x03f0
        L_0x03d3:
            java.lang.String r0 = com.zktechnology.android.utils.AppUtils.getString(r9)     // Catch:{ Exception -> 0x0691 }
            r14.setUiAttFull(r0)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r0 = r24.getLastType()     // Catch:{ Exception -> 0x0691 }
            int r3 = r0.getType()     // Catch:{ Exception -> 0x0691 }
            java.lang.String r4 = com.zktechnology.android.utils.AppUtils.getString(r9)     // Catch:{ Exception -> 0x0691 }
            r5 = 27
            r6 = 0
            r7 = 1
            r1 = r37
            r2 = r14
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0691 }
        L_0x03f0:
            int r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.VERIFY_SOURCE_TYPE     // Catch:{ Exception -> 0x0691 }
            r4 = 3
            if (r0 != r4) goto L_0x0405
            com.zktechnology.android.rs232.ZKRS232EncryptManager r0 = com.zktechnology.android.rs232.ZKRS232EncryptManager.getInstance()     // Catch:{ Exception -> 0x0691 }
            r0.failedCmd()     // Catch:{ Exception -> 0x0691 }
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.rs485.RS485Manager r0 = com.zktechnology.android.rs485.RS485Manager.getInstance(r0)     // Catch:{ Exception -> 0x0691 }
            r0.failedCmd()     // Catch:{ Exception -> 0x0691 }
        L_0x0405:
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sLockFunOn     // Catch:{ Exception -> 0x0691 }
            r1 = 15
            if (r0 != r1) goto L_0x0424
            java.util.List r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()     // Catch:{ Exception -> 0x0691 }
            r0.clear()     // Catch:{ Exception -> 0x0691 }
            java.util.Map r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x0691 }
            r0.clear()     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.att.DoorAttManager.lastTime = r28     // Catch:{ Exception -> 0x0691 }
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.att.DoorAttManager r0 = com.zktechnology.android.att.DoorAttManager.getInstance(r0)     // Catch:{ Exception -> 0x0691 }
            r0.verifyFailAlarm()     // Catch:{ Exception -> 0x0691 }
        L_0x0424:
            return
        L_0x0425:
            r2 = 1
            r4 = 3
            r19 = 2131755297(0x7f100121, float:1.914147E38)
            if (r26 == 0) goto L_0x0431
            java.lang.String r9 = com.zktechnology.android.utils.AppUtils.getString(r19)     // Catch:{ Exception -> 0x0691 }
            goto L_0x0433
        L_0x0431:
            r9 = r16
        L_0x0433:
            r14.setUiShortMessage(r9)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r9 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0691 }
            int r9 = r9.getVerState()     // Catch:{ Exception -> 0x0691 }
            if (r9 != r13) goto L_0x0440
            r13 = r2
            goto L_0x0442
        L_0x0440:
            r13 = r21
        L_0x0442:
            int r9 = com.zktechnology.android.launcher2.ZKLauncher.sLockFunOn     // Catch:{ Exception -> 0x0691 }
            r15 = 15
            if (r9 == r15) goto L_0x044b
            r17 = r2
            goto L_0x044d
        L_0x044b:
            r17 = r21
        L_0x044d:
            if (r20 == 0) goto L_0x0455
            if (r23 == 0) goto L_0x0455
            if (r0 == 0) goto L_0x0455
            r0 = r2
            goto L_0x0457
        L_0x0455:
            r0 = r21
        L_0x0457:
            android.content.Context r9 = r8.mContext     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.att.DoorAttManager r9 = com.zktechnology.android.att.DoorAttManager.getInstance(r9)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.dao.ZKVerDao r10 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            r11 = r7
            r12 = r3
            r4 = r21
            r4 = r2
            r2 = r14
            r14 = r17
            r36 = r30
            r15 = r0
            com.zktechnology.android.att.AttResponse r15 = r9.attVerify(r10, r11, r12, r13, r14, r15)     // Catch:{ Exception -> 0x0691 }
            r8.sendBro(r15)     // Catch:{ Exception -> 0x0691 }
            int r9 = r15.getEventCode()     // Catch:{ Exception -> 0x0691 }
            r10 = 1001(0x3e9, float:1.403E-42)
            if (r9 != r10) goto L_0x047d
            r37.resetVerifyProcess()     // Catch:{ Exception -> 0x0691 }
            return
        L_0x047d:
            r8.isSuccess = r4     // Catch:{ Exception -> 0x0691 }
            boolean r9 = r15.isOpenDoor()     // Catch:{ Exception -> 0x0691 }
            java.lang.String r10 = "bundle_work_code"
            java.lang.String r11 = "T"
            java.lang.String r12 = " "
            if (r9 == 0) goto L_0x0578
            if (r0 == 0) goto L_0x04e0
            if (r26 == 0) goto L_0x0493
            java.lang.String r16 = com.zktechnology.android.utils.AppUtils.getString(r19)     // Catch:{ Exception -> 0x0691 }
        L_0x0493:
            r0 = r16
            r2.setUiShortMessage(r0)     // Catch:{ Exception -> 0x0691 }
            r0 = 2131755299(0x7f100123, float:1.9141473E38)
            java.lang.String r0 = com.zktechnology.android.utils.AppUtils.getString(r0)     // Catch:{ Exception -> 0x0691 }
            r2.setUiRepeatTime(r0)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r0 = r3.getName()     // Catch:{ Exception -> 0x0691 }
            r8.playSoundOk(r0)     // Catch:{ Exception -> 0x0691 }
            if (r18 == 0) goto L_0x04af
            r8.showWiegandSuccessDialog(r2)     // Catch:{ Exception -> 0x0691 }
            goto L_0x04ba
        L_0x04af:
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r0 = r24.getLastType()     // Catch:{ Exception -> 0x0691 }
            int r0 = r0.getType()     // Catch:{ Exception -> 0x0691 }
            r8.showUserSuccessDialog(r2, r0)     // Catch:{ Exception -> 0x0691 }
        L_0x04ba:
            int r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.VERIFY_SOURCE_TYPE     // Catch:{ Exception -> 0x0691 }
            r1 = 3
            if (r0 != r1) goto L_0x04cf
            com.zktechnology.android.rs232.ZKRS232EncryptManager r0 = com.zktechnology.android.rs232.ZKRS232EncryptManager.getInstance()     // Catch:{ Exception -> 0x0691 }
            r0.successCmd()     // Catch:{ Exception -> 0x0691 }
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.rs485.RS485Manager r0 = com.zktechnology.android.rs485.RS485Manager.getInstance(r0)     // Catch:{ Exception -> 0x0691 }
            r0.successCmd()     // Catch:{ Exception -> 0x0691 }
        L_0x04cf:
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sLockFunOn     // Catch:{ Exception -> 0x0691 }
            r4 = 15
            if (r0 != r4) goto L_0x04df
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.att.DoorAttManager r0 = com.zktechnology.android.att.DoorAttManager.getInstance(r0)     // Catch:{ Exception -> 0x0691 }
            r1 = 0
            r0.setTimes(r1)     // Catch:{ Exception -> 0x0691 }
        L_0x04df:
            return
        L_0x04e0:
            r4 = 15
            if (r5 == 0) goto L_0x04f5
            if (r27 == 0) goto L_0x04f5
            com.zktechnology.android.verify.bean.process.ZKVerifyType r0 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ Exception -> 0x0691 }
            int r0 = r0.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 == r0) goto L_0x04f5
            com.zktechnology.android.verify.dao.ZKVerDao r0 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            r9 = r36
            r0.deleteAttLog(r9)     // Catch:{ Exception -> 0x0691 }
        L_0x04f5:
            com.zktechnology.android.verify.bean.process.ZKVerifyType r0 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ Exception -> 0x0691 }
            int r0 = r0.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 == r0) goto L_0x0527
            com.zktechnology.android.verify.dao.ZKVerDao r9 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            r13 = r25
            java.lang.String r0 = r13.format(r6)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r11 = r0.replace(r12, r11)     // Catch:{ Exception -> 0x0691 }
            android.os.Bundle r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0691 }
            r5 = 0
            int r13 = r0.getInt(r10, r5)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0691 }
            java.lang.String r14 = r0.getVerAttAction()     // Catch:{ Exception -> 0x0691 }
            double r16 = r15.getTemperature()     // Catch:{ Exception -> 0x0691 }
            int r0 = r15.getWearMask()     // Catch:{ Exception -> 0x0691 }
            r10 = r3
            r12 = r7
            r15 = r16
            r17 = r0
            r9.addAttLog(r10, r11, r12, r13, r14, r15, r17)     // Catch:{ Exception -> 0x0691 }
        L_0x0527:
            com.zktechnology.android.verify.bean.process.ZKVerifyType r0 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ Exception -> 0x0691 }
            int r0 = r0.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 == r0) goto L_0x0551
            if (r18 != 0) goto L_0x0538
            java.lang.String r0 = r3.getUser_PIN()     // Catch:{ Exception -> 0x0691 }
            r8.pictureAtt(r0, r6)     // Catch:{ Exception -> 0x0691 }
        L_0x0538:
            if (r18 != 0) goto L_0x0542
            boolean r0 = com.zktechnology.android.launcher2.ZKLauncher.sIRTempDetectionFunOn     // Catch:{ Exception -> 0x0691 }
            if (r0 == 0) goto L_0x0542
            boolean r0 = com.zktechnology.android.launcher2.ZKLauncher.sEnalbeIRTempDetection     // Catch:{ Exception -> 0x0691 }
            if (r0 != 0) goto L_0x0549
        L_0x0542:
            java.lang.String r0 = r3.getName()     // Catch:{ Exception -> 0x0691 }
            r8.playSoundOk(r0)     // Catch:{ Exception -> 0x0691 }
        L_0x0549:
            com.zktechnology.android.wiegand.ZKWiegandManager r0 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()     // Catch:{ Exception -> 0x0691 }
            r0.wiegandOutUserInfo(r3)     // Catch:{ Exception -> 0x0691 }
            goto L_0x055c
        L_0x0551:
            boolean r0 = com.zktechnology.android.launcher2.ZKLauncher.sIRTempDetectionFunOn     // Catch:{ Exception -> 0x0691 }
            if (r0 == 0) goto L_0x0559
            boolean r0 = com.zktechnology.android.launcher2.ZKLauncher.sEnalbeIRTempDetection     // Catch:{ Exception -> 0x0691 }
            if (r0 != 0) goto L_0x055c
        L_0x0559:
            r8.playSoundOk(r1)     // Catch:{ Exception -> 0x0691 }
        L_0x055c:
            int r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.VERIFY_SOURCE_TYPE     // Catch:{ Exception -> 0x0691 }
            r1 = 3
            if (r0 != r1) goto L_0x0571
            com.zktechnology.android.rs232.ZKRS232EncryptManager r0 = com.zktechnology.android.rs232.ZKRS232EncryptManager.getInstance()     // Catch:{ Exception -> 0x0691 }
            r0.successCmd()     // Catch:{ Exception -> 0x0691 }
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.rs485.RS485Manager r0 = com.zktechnology.android.rs485.RS485Manager.getInstance(r0)     // Catch:{ Exception -> 0x0691 }
            r0.successCmd()     // Catch:{ Exception -> 0x0691 }
        L_0x0571:
            r8.showUserSuccessDialog(r2, r7)     // Catch:{ Exception -> 0x0691 }
            r11 = r4
            r12 = 0
            goto L_0x0671
        L_0x0578:
            r13 = r25
            r9 = r36
            r1 = 15
            int r0 = r15.getEventCode()     // Catch:{ Exception -> 0x0691 }
            r14 = 26
            if (r0 == r14) goto L_0x05f1
            com.zktechnology.android.verify.bean.process.ZKVerifyType r0 = com.zktechnology.android.verify.bean.process.ZKVerifyType.URGENTPASSWORD     // Catch:{ Exception -> 0x0691 }
            int r0 = r0.getValue()     // Catch:{ Exception -> 0x0691 }
            if (r7 == r0) goto L_0x05de
            com.zktechnology.android.wiegand.ZKWiegandManager r0 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()     // Catch:{ Exception -> 0x0691 }
            r0.wiegandOutFailedID()     // Catch:{ Exception -> 0x0691 }
            int r0 = r15.getEventCode()     // Catch:{ Exception -> 0x0691 }
            r5 = 68
            if (r0 == r5) goto L_0x05ae
            int r0 = r15.getEventCode()     // Catch:{ Exception -> 0x0691 }
            r5 = 69
            if (r0 != r5) goto L_0x05a6
            goto L_0x05ae
        L_0x05a6:
            java.lang.String r0 = r3.getUser_PIN()     // Catch:{ Exception -> 0x0691 }
            r8.pictureBlack(r0, r6)     // Catch:{ Exception -> 0x0691 }
            goto L_0x05de
        L_0x05ae:
            java.lang.String r0 = r3.getUser_PIN()     // Catch:{ Exception -> 0x0691 }
            int r5 = com.zktechnology.android.launcher2.ZKLauncher.sCameraSystem     // Catch:{ Exception -> 0x0691 }
            r8.picture(r0, r6, r4, r5)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.dao.ZKVerDao r9 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            java.lang.String r0 = r13.format(r6)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r11 = r0.replace(r12, r11)     // Catch:{ Exception -> 0x0691 }
            android.os.Bundle r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0691 }
            r4 = 0
            int r13 = r0.getInt(r10, r4)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r0 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0691 }
            java.lang.String r14 = r0.getVerAttAction()     // Catch:{ Exception -> 0x0691 }
            double r4 = r15.getTemperature()     // Catch:{ Exception -> 0x0691 }
            int r17 = r15.getWearMask()     // Catch:{ Exception -> 0x0691 }
            r10 = r3
            r12 = r7
            r0 = r15
            r15 = r4
            r9.addAttLog(r10, r11, r12, r13, r14, r15, r17)     // Catch:{ Exception -> 0x0691 }
            goto L_0x05df
        L_0x05de:
            r0 = r15
        L_0x05df:
            r3 = 0
            r8.isSuccess = r3     // Catch:{ Exception -> 0x0691 }
            if (r18 != 0) goto L_0x05ec
            boolean r3 = com.zktechnology.android.launcher2.ZKLauncher.sIRTempDetectionFunOn     // Catch:{ Exception -> 0x0691 }
            if (r3 == 0) goto L_0x05ec
            boolean r3 = com.zktechnology.android.launcher2.ZKLauncher.sEnalbeIRTempDetection     // Catch:{ Exception -> 0x0691 }
            if (r3 != 0) goto L_0x05ef
        L_0x05ec:
            r37.playSoundTryAgain()     // Catch:{ Exception -> 0x0691 }
        L_0x05ef:
            r5 = 0
            goto L_0x062f
        L_0x05f1:
            r0 = r15
            com.zktechnology.android.wiegand.ZKWiegandManager r4 = com.zktechnology.android.wiegand.ZKWiegandManager.getInstance()     // Catch:{ Exception -> 0x0691 }
            r4.wiegandOutUserInfo(r3)     // Catch:{ Exception -> 0x0691 }
            if (r5 == 0) goto L_0x0602
            if (r27 == 0) goto L_0x0602
            com.zktechnology.android.verify.dao.ZKVerDao r4 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            r4.deleteAttLog(r9)     // Catch:{ Exception -> 0x0691 }
        L_0x0602:
            com.zktechnology.android.verify.dao.ZKVerDao r9 = r8.mDao     // Catch:{ Exception -> 0x0691 }
            java.lang.String r4 = r13.format(r6)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r11 = r4.replace(r12, r11)     // Catch:{ Exception -> 0x0691 }
            android.os.Bundle r4 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BUNDLE     // Catch:{ Exception -> 0x0691 }
            r5 = 0
            int r13 = r4.getInt(r10, r5)     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.verify.bean.process.ZKVerConMarkBean r4 = com.zktechnology.android.verify.utils.ZKVerProcessPar.CON_MARK_BEAN     // Catch:{ Exception -> 0x0691 }
            java.lang.String r14 = r4.getVerAttAction()     // Catch:{ Exception -> 0x0691 }
            double r15 = r0.getTemperature()     // Catch:{ Exception -> 0x0691 }
            int r17 = r0.getWearMask()     // Catch:{ Exception -> 0x0691 }
            r10 = r3
            r12 = r7
            r9.addAttLog(r10, r11, r12, r13, r14, r15, r17)     // Catch:{ Exception -> 0x0691 }
            if (r18 != 0) goto L_0x062f
            java.lang.String r3 = r3.getUser_PIN()     // Catch:{ Exception -> 0x0691 }
            r8.pictureAtt(r3, r6)     // Catch:{ Exception -> 0x0691 }
        L_0x062f:
            int r3 = com.zktechnology.android.verify.utils.ZKVerProcessPar.VERIFY_SOURCE_TYPE     // Catch:{ Exception -> 0x0691 }
            r4 = 3
            if (r3 != r4) goto L_0x0644
            com.zktechnology.android.rs232.ZKRS232EncryptManager r3 = com.zktechnology.android.rs232.ZKRS232EncryptManager.getInstance()     // Catch:{ Exception -> 0x0691 }
            r3.failedCmd()     // Catch:{ Exception -> 0x0691 }
            android.content.Context r3 = r8.mContext     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.rs485.RS485Manager r3 = com.zktechnology.android.rs485.RS485Manager.getInstance(r3)     // Catch:{ Exception -> 0x0691 }
            r3.failedCmd()     // Catch:{ Exception -> 0x0691 }
        L_0x0644:
            java.lang.String r4 = r0.getErrorMessage()     // Catch:{ Exception -> 0x0691 }
            int r6 = r0.getEventCode()     // Catch:{ Exception -> 0x0691 }
            r9 = 0
            r10 = 0
            r11 = r1
            r1 = r37
            r3 = r7
            r12 = r5
            r5 = r6
            r6 = r9
            r7 = r10
            r1.showUserFailedDialog(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0691 }
            int r0 = r0.getEventCode()     // Catch:{ Exception -> 0x0691 }
            r1 = 22
            if (r0 != r1) goto L_0x0671
            java.util.List r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()     // Catch:{ Exception -> 0x0691 }
            r0.clear()     // Catch:{ Exception -> 0x0691 }
            java.util.Map r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x0691 }
            r0.clear()     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.att.DoorAttManager.lastTime = r28     // Catch:{ Exception -> 0x0691 }
        L_0x0671:
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sLockFunOn     // Catch:{ Exception -> 0x0691 }
            if (r0 != r11) goto L_0x069a
            boolean r0 = r8.isSuccess     // Catch:{ Exception -> 0x0691 }
            if (r0 == 0) goto L_0x0683
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.att.DoorAttManager r0 = com.zktechnology.android.att.DoorAttManager.getInstance(r0)     // Catch:{ Exception -> 0x0691 }
            r0.setTimes(r12)     // Catch:{ Exception -> 0x0691 }
            goto L_0x069a
        L_0x0683:
            android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0691 }
            com.zktechnology.android.att.DoorAttManager r0 = com.zktechnology.android.att.DoorAttManager.getInstance(r0)     // Catch:{ Exception -> 0x0691 }
            r0.verifyFailAlarm()     // Catch:{ Exception -> 0x0691 }
            goto L_0x069a
        L_0x068d:
            r37.resetVerifyProcess()     // Catch:{ Exception -> 0x0691 }
            return
        L_0x0691:
            r0 = move-exception
            r8.handleException(r0)
            java.lang.String r0 = "[stateRecord]It happens a exception.We will reset verify Process and you can see the detail in trace/VerifyException.txt"
            r8.log(r0)
        L_0x069a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.server.ZKVerServerImpl.stateRecord():void");
    }

    private void sendBro(AttResponse attResponse) {
        Intent intent = new Intent();
        intent.setAction(ZkFaceLauncher.CHANGE_TEMPERATURE_UI);
        intent.putExtra("temperature", attResponse.getTemperature());
        intent.putExtra("mask", attResponse.getWearMask());
        this.mContext.sendBroadcast(intent);
    }

    public void resetMultiVerify() {
        DoorAttManager.getAccGroupPinList().clear();
        DoorAttManager.getAccGroupIdMap().clear();
        DoorAttManager.lastTime = 0;
    }

    private void picture(String str, Date date, boolean z, int i) {
        String str2;
        Log.d("pic", "picture: ");
        LogUtils.d(LogUtils.TAG_VERIFY, "拍照收录到考勤照片 UserPin=%s, success=%s, capturePicValue=%s", str, Boolean.valueOf(z), Integer.valueOf(i));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ZKConstantConfig.DATE_FORMAT_2, Locale.US);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        if (i != 2) {
            if (i != 3) {
                if (i == 4 && !z) {
                    PhotoIndex photoIndex = new PhotoIndex();
                    String format = simpleDateFormat.format(date);
                    photoIndex.setPhoto_Time(simpleDateFormat2.format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE));
                    photoIndex.setPhoto_Type(1);
                    ZKCameraController.getInstance().takePicture(photoIndex, ZKFilePath.BLACK_LIST_PATH, format);
                }
            } else if (z) {
                PhotoIndex photoIndex2 = new PhotoIndex();
                if (str != null) {
                    photoIndex2.setUser_PIN(str);
                    str2 = simpleDateFormat.format(date) + "-" + str;
                } else {
                    str2 = simpleDateFormat.format(date) + "-";
                }
                photoIndex2.setPhoto_Time(simpleDateFormat2.format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE));
                photoIndex2.setPhoto_Type(0);
                ZKCameraController.getInstance().takePicture(photoIndex2, ZKFilePath.ATT_PHOTO_PATH, str2);
            }
        } else if (z) {
            PhotoIndex photoIndex3 = new PhotoIndex();
            photoIndex3.setUser_PIN(str);
            photoIndex3.setPhoto_Time(simpleDateFormat2.format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE));
            photoIndex3.setPhoto_Type(0);
            ZKCameraController.getInstance().takePicture(photoIndex3, ZKFilePath.ATT_PHOTO_PATH, simpleDateFormat.format(date) + "-" + str);
        } else {
            PhotoIndex photoIndex4 = new PhotoIndex();
            photoIndex4.setPhoto_Time(simpleDateFormat2.format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE));
            photoIndex4.setPhoto_Type(1);
            ZKCameraController.getInstance().takePicture(photoIndex4, ZKFilePath.BLACK_LIST_PATH, simpleDateFormat.format(date));
        }
    }

    private void checkUserValid(UserInfo userInfo) {
        boolean z;
        LogUtils.i(LogUtils.TAG_VERIFY, "checkUserValid------");
        int intent = ZKVerProcessPar.CON_MARK_BEAN.getIntent();
        if (DBManager.getInstance().getIntOption("WiegandMethod", 0) == 1 && intent == 5) {
            intent = 0;
        }
        int i = ZKVerProcessPar.VERIFY_SOURCE_TYPE;
        if (intent == 0) {
            boolean isInExpires = this.mDao.isInExpires(userInfo);
            if (!isInExpires && "1".equals(ZKLauncher.sUserValidTimeFun)) {
                int i2 = ZKLauncher.sOverValidTime;
                if (i2 == 0) {
                    deleteOverValidAttLog(userInfo);
                } else if (i2 == 1) {
                    "1".equals(ZKLauncher.sUserValidTimeFun);
                } else if (i2 == 2) {
                    this.mDao.deleteUser(userInfo);
                    deleteOverValidAttLog(userInfo);
                }
            }
            z = isInExpires;
        } else if (intent == 1 || intent == 2) {
            z = this.mDao.isSuperUser(userInfo);
        } else if (intent != 5) {
            z = false;
        } else {
            z = this.mDao.isInExpires(userInfo);
        }
        if (z) {
            ZKVerProcessPar.CON_MARK_BUNDLE.putSerializable(ZKVerConConst.BUNDLE_USER_INFO, userInfo);
            if (intent != 5) {
                changeStateToWay();
            } else {
                ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
            }
        } else if (intent != 5) {
            pictureBlack((String) null, new Date());
            ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            playSoundTryAgain();
            showUserInvalidDialog(userInfo, intent, false);
            DoorAttManager.getInstance(this.mContext).verifyFailAlarm();
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
        } else {
            showWiegandFailedDialog();
        }
    }

    private void deleteOverValidAttLog(UserInfo userInfo) {
        List<AttLog> query;
        AttLog attLog = new AttLog();
        try {
            new ArrayList();
            int expires = userInfo.getExpires();
            if (expires == 2 || expires == 3) {
                if (userInfo.getVaildCount() <= 0) {
                    List<AttLog> query2 = attLog.getQueryBuilder().where().eq("User_PIN", userInfo.getUser_PIN()).query();
                    if (query2 != null && query2.size() > 0) {
                        for (AttLog delete : query2) {
                            delete.delete();
                        }
                        return;
                    }
                    return;
                }
            }
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(System.currentTimeMillis()));
            String endDatetime = userInfo.getEndDatetime();
            if (userInfo.getEndDatetime().length() < 11) {
                endDatetime = userInfo.getEndDatetime() + "T23:59:59";
            } else if (userInfo.getEndDatetime().contains(" ")) {
                endDatetime = userInfo.getEndDatetime() + ":59";
            }
            int formatAccPushTime = Utils.formatAccPushTime(endDatetime);
            if (Utils.formatAccPushTime(format) > formatAccPushTime && (query = attLog.getQueryBuilder().where().eq("User_PIN", userInfo.getUser_PIN()).query()) != null && query.size() > 0) {
                for (AttLog attLog2 : query) {
                    if (Utils.formatAccPushTime(attLog2.getVerify_Time()) <= formatAccPushTime) {
                        attLog2.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearWorkNumber(UserInfo userInfo) {
        int verify_Type = userInfo.getVerify_Type();
        if (verify_Type == 0 || verify_Type == 1 || verify_Type == 3 || verify_Type == 4 || verify_Type == 5 || verify_Type == 6 || verify_Type == 7 || verify_Type == 9 || verify_Type == 10 || verify_Type == 11 || verify_Type == 12) {
            for (int i = 0; i < ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().size(); i++) {
                if (ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().get(i).getType() == 2) {
                    ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().remove(i);
                }
            }
        }
        if (verify_Type == 2 || verify_Type == 3 || verify_Type == 4 || verify_Type == 7 || verify_Type == 11) {
            for (int i2 = 0; i2 < ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().size(); i2++) {
                if (ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().get(i2).getType() == 1) {
                    ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().remove(i2);
                }
            }
        }
        if (verify_Type == 1 || verify_Type == 2 || verify_Type == 3 || verify_Type == 5 || verify_Type == 8 || verify_Type == 9 || verify_Type == 13) {
            for (int i3 = 0; i3 < ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().size(); i3++) {
                if (ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().get(i3).getType() == 4) {
                    ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().remove(i3);
                }
            }
        }
    }

    private void showUserInvalidDialog(UserInfo userInfo, int i, boolean z) {
        String str;
        ZKVerConMarkBean zKVerConMarkBean = ZKVerProcessPar.CON_MARK_BEAN;
        ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
        zKVerViewBean.setUiName(userInfo.getName());
        zKVerViewBean.setUiPin(userInfo.getUser_PIN());
        if (i == 1 || i == 2) {
            zKVerViewBean.setFailMsg(this.mContext.getResources().getString(R.string.dlg_ver_process_fa_title));
        } else {
            String string = this.mContext.getResources().getString(R.string.dlg_ver_process_fa_title);
            if (z) {
                str = zKVerViewBean.getErrorMessage(string, 39);
            } else {
                LogUtils.d(ZkLogTag.VERIFY_FLOW, "CARD_INVALID_TIME 3");
                str = zKVerViewBean.getErrorMessage(string, 29);
            }
            zKVerViewBean.setFailMsg(str);
        }
        switch (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(zKVerConMarkBean.getLastType().getType()).ordinal()]) {
            case 1:
                zKVerViewBean.setUiType(23);
                break;
            case 2:
                zKVerViewBean.setUiType(13);
                zKVerViewBean.setUiFingerprint(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER));
                zKVerViewBean.setUiFPHeight(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_HEIGHT, 0));
                zKVerViewBean.setUiFPWidth(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_WIDTH, 0));
                break;
            case 3:
                zKVerViewBean.setUiType(83);
                break;
            case 4:
                zKVerViewBean.setUiType(43);
                break;
            case 5:
                zKVerViewBean.setUiType(53);
                break;
            case 6:
                zKVerViewBean.setUiType(33);
                break;
        }
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
    }

    public void showWiegandSuccessDialog(ZKVerViewBean zKVerViewBean) {
        int i = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(ZKVerProcessPar.CON_MARK_BEAN.getLastType().getType()).ordinal()];
        if (i == 1) {
            zKVerViewBean.setUiType(62);
        } else if (i != 4) {
            zKVerViewBean.setUiType(60);
        } else {
            zKVerViewBean.setUiType(60);
        }
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        ZKVerProcessPar.cleanData(10);
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    private void startVerifyRouter(UserInfo userInfo, ZKVerConMarkBean zKVerConMarkBean, ArrayList<Boolean> arrayList) {
        int type = zKVerConMarkBean.getLastType().getType();
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(type);
        int size = zKVerConMarkBean.getVerifyTypeList().size();
        int verify_Type = userInfo.getVerify_Type();
        LogUtils.d(TAG, "startVerifyRouter=== " + verify_Type);
        if (verify_Type == 100 && userInfo.getAcc_Group_ID() > 0) {
            try {
                verify_Type = ((AccGroup) new AccGroup().queryForId(Long.valueOf(Long.valueOf((long) userInfo.getAcc_Group_ID()).longValue()))).getVerification();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (verify_Type != 25) {
            switch (verify_Type) {
                case -1:
                case 0:
                    if (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 1) {
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    } else {
                        startVerifyPassword();
                        return;
                    }
                case 1:
                    if (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 2) {
                        showFingerDialog();
                        return;
                    } else {
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    }
                case 2:
                    if (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 1) {
                        showPinDialog();
                        return;
                    } else {
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    }
                case 3:
                    int i = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
                    if (i == 1) {
                        startVerifyPassword();
                        return;
                    } else if (i != 6) {
                        showPasswordDialog();
                        return;
                    } else {
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    }
                case 4:
                    if (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 4) {
                        showCardDialog();
                        return;
                    } else {
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    }
                case 5:
                    int i2 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
                    if (i2 == 1) {
                        startVerifyPassword();
                        return;
                    } else if (i2 == 2 || i2 == 6) {
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    } else if (arrayList.get(0).booleanValue()) {
                        showPasswordDialog();
                        return;
                    } else if (arrayList.get(1).booleanValue()) {
                        showFingerDialog();
                        return;
                    } else {
                        return;
                    }
                case 6:
                    int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
                    if (i3 == 2 || i3 == 4) {
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
                        return;
                    } else if (arrayList.get(1).booleanValue()) {
                        showFingerDialog();
                        return;
                    } else if (arrayList.get(2).booleanValue()) {
                        showCardDialog();
                        return;
                    } else {
                        return;
                    }
                case 7:
                    startVerify7(arrayList, type);
                    return;
                case 8:
                    startVerify8(size, type);
                    return;
                case 9:
                    startVerify9(size, type);
                    return;
                case 10:
                    startVerify10(size, type);
                    return;
                case 11:
                    startVerify11(size, type);
                    return;
                case 12:
                    startVerify12(size, type);
                    return;
                case 13:
                    startVerify13(size, type);
                    return;
                case 14:
                    startVerify14(userInfo, size, type);
                    return;
                case 15:
                    startVerify15(type);
                    return;
                case 16:
                    startVerify16(size, type);
                    return;
                case 17:
                    startVerify17(size, type);
                    return;
                case 18:
                    startVerify18(size, type);
                    return;
                case 19:
                    startVerify19(size, type);
                    return;
                case 20:
                    startVerify20(size, type);
                    return;
                default:
                    return;
            }
        } else if (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()] != 3) {
            showPalmDialog();
        } else {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        }
    }

    private void startVerify7(ArrayList<Boolean> arrayList, int i) {
        int i2 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(i).ordinal()];
        if (i2 == 1) {
            startVerifyPassword();
        } else if (i2 == 4 || i2 == 6) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        } else if (arrayList.get(0).booleanValue()) {
            showPasswordDialog();
        } else if (arrayList.get(2).booleanValue()) {
            showCardDialog();
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
        int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 1) {
            startVerifyFinger();
        } else if (i3 != 2) {
            ZKVerProcessPar.OTHER_FLAG = true;
            showFingerDialog();
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
        int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 2) {
            disableVerifyType(ZKVerifyType.FINGER);
            startVerifyPassword();
        } else if (i3 != 6) {
            ZKVerProcessPar.OTHER_FLAG = true;
            showPasswordDialog();
        } else {
            startVerifyFinger();
        }
    }

    private void startVerify10(int i, int i2) {
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && i > 1) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i != 1) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
            return;
        }
        int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 2) {
            disableVerifyType(ZKVerifyType.FINGER);
            startVerifyCard();
        } else if (i3 != 4) {
            ZKVerProcessPar.OTHER_FLAG = true;
            showFingerDialog();
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
        int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 4) {
            disableVerifyType(ZKVerifyType.CARD);
            startVerifyPassword();
        } else if (i3 != 6) {
            ZKVerProcessPar.OTHER_FLAG = true;
            showPasswordDialog();
        } else {
            startVerifyCard();
        }
    }

    private void startVerify12(int i, int i2) {
        if (i > 1 && (ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG)) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i == 1) {
            int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i3 == 2) {
                disableVerifyType(ZKVerifyType.FINGER);
                startVerifyPassword();
            } else if (i3 == 4) {
                startVerifyFinger();
            } else if (i3 != 6) {
                ZKVerProcessPar.OTHER_FLAG = true;
                showFingerDialog();
            } else {
                startVerifyCard();
            }
        } else if (i != 2) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        } else {
            int i4 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i4 == 2) {
                disableVerifyType(ZKVerifyType.FINGER);
                startVerifyPassword();
            } else if (i4 == 4) {
                startVerifyFinger();
            } else if (i4 == 6) {
                startVerifyCard();
            }
        }
    }

    private void startVerify13(int i, int i2) {
        if (i > 1 && ZKVerProcessPar.OTHER_FLAG) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i == 1) {
            int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
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
            int i4 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
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

    private void startVerify14(UserInfo userInfo, int i, int i2) {
        if (i > 1 && ZKVerProcessPar.OTHER_FLAG) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i != 1) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
            return;
        }
        int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 != 1) {
            if (i3 == 2) {
                userInfo.setVerify_Type(10);
                startVerify10(i, i2);
                return;
            } else if (i3 != 4) {
                userInfo.setVerify_Type(10);
                startVerify10(i, i2);
                return;
            }
        }
        startVerifyFinger();
    }

    private void startVerify15(int i) {
        if (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(i).ordinal()] != 5) {
            showFaceDialog();
        } else {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
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
        int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 2) {
            ZKVerProcessPar.OTHER_FLAG = false;
            disableVerifyType(ZKVerifyType.FINGER);
            startVerifyFace();
        } else if (i3 != 5) {
            ZKVerProcessPar.OTHER_FLAG = true;
            showFaceDialog();
        } else {
            ZKVerProcessPar.OTHER_FLAG = false;
            disableVerifyType(ZKVerifyType.FACE);
            startVerifyFinger();
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
        int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 5) {
            ZKVerProcessPar.OTHER_FLAG = false;
            disableVerifyType(ZKVerifyType.FACE);
            startVerifyPassword();
        } else if (i3 != 6) {
            ZKVerProcessPar.OTHER_FLAG = true;
            showFaceDialog();
        } else {
            disableVerifyType(ZKVerifyType.PASSWORD);
            startVerifyFace();
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
        int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        if (i3 == 4) {
            disableVerifyType(ZKVerifyType.CARD);
            startVerifyFace();
        } else if (i3 != 5) {
            ZKVerProcessPar.OTHER_FLAG = true;
            showFaceDialog();
        } else {
            disableVerifyType(ZKVerifyType.FACE);
            startVerifyCard();
        }
    }

    private void startVerify19(int i, int i2) {
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && i > 1) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i == 1) {
            int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i3 == 2) {
                startVerifyCard();
            } else if (i3 == 4) {
                startVerifyFace();
            } else if (i3 != 5) {
                ZKVerProcessPar.OTHER_FLAG = true;
                showFaceDialog();
            } else {
                startVerifyFinger();
            }
        } else if (i != 2) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        } else {
            int i4 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i4 == 2) {
                startVerifyCard();
            } else if (i4 == 4) {
                startVerifyFace();
            } else if (i4 == 5) {
                startVerifyFinger();
            }
        }
    }

    private void startVerify20(int i, int i2) {
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && i > 1) {
            i--;
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(i2);
        if (i == 1) {
            int i3 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
            if (i3 == 2) {
                disableVerifyType(ZKVerifyType.FINGER);
                startVerifyPassword();
            } else if (i3 == 5) {
                disableVerifyType(ZKVerifyType.FACE);
                startVerifyFinger();
            } else if (i3 != 6) {
                ZKVerProcessPar.OTHER_FLAG = true;
                showFaceDialog();
            } else {
                startVerifyFace();
            }
        } else if (i != 2) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
        } else {
            int i4 = AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
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
        changeStateToWait();
    }

    private void showInputWorkCodeDialog(UserInfo userInfo) {
        changeStateToWait();
    }

    public void showUserSuccessDialog(ZKVerViewBean zKVerViewBean, int i) {
        switch (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(i).ordinal()]) {
            case 1:
                zKVerViewBean.setUiType(21);
                break;
            case 2:
                zKVerViewBean.setUiType(11);
                zKVerViewBean.setUiFingerprint(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER));
                zKVerViewBean.setUiFPHeight(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_HEIGHT, 0));
                zKVerViewBean.setUiFPWidth(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_WIDTH, 0));
                break;
            case 3:
                zKVerViewBean.setUiType(81);
                break;
            case 4:
                zKVerViewBean.setUiType(41);
                break;
            case 5:
                zKVerViewBean.setUiType(51);
                break;
            case 6:
                zKVerViewBean.setUiType(31);
                break;
            case 7:
                zKVerViewBean.setUiType(71);
                break;
        }
        FileLogUtils.writeVerifySuccessLog("userPin->" + zKVerViewBean.getUiPin() + "; " + "userName->" + zKVerViewBean.getUiName() + ": ");
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        ZKVerProcessPar.cleanData(12);
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    private void pictureAtt(String str, Date date) {
        if (ZKLauncher.sMaxCaptureCount > this.mDao.getMaxCaptureCount()) {
            picture(str, date, true, ZKLauncher.sCameraSystem);
        } else if ((ZKLauncher.sCameraSystem == 2 || ZKLauncher.sCameraSystem == 3) && ZKLauncher.sLoopDeletePic > 0) {
            this.mDao.deleteCapture(ZKLauncher.sLoopDeletePic + (this.mDao.getMaxCaptureCount() - ZKLauncher.sMaxCaptureCount));
            picture(str, date, true, ZKLauncher.sCameraSystem);
        }
    }

    public void pictureBlack(String str, Date date) {
        Log.d("pic", "pictureBlack: ");
        if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 1) {
            return;
        }
        if (ZKLauncher.sMaxBlackPhotoCount > this.mDao.getBlackPhotoCount()) {
            picture(str, date, false, ZKLauncher.sCameraSystem);
        } else if ((ZKLauncher.sCameraSystem == 2 || ZKLauncher.sCameraSystem == 4) && ZKLauncher.mLoopDeleteBlackPic > 0) {
            this.mDao.deleteBlackPhoto(ZKLauncher.mLoopDeleteBlackPic);
            picture(str, date, false, ZKLauncher.sCameraSystem);
        }
    }
}
