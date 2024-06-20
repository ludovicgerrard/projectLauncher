package com.zktechnology.android.launcher2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.common.base.Ascii;
import com.google.zxing.Result;
import com.zktechnology.android.att.ZkAttDoorManager;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.door.ZkDoorManager;
import com.zktechnology.android.event.EventBusHelper;
import com.zktechnology.android.event.EventOpenMenu;
import com.zktechnology.android.event.EventShowNoSuperDialog;
import com.zktechnology.android.event.EventState;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.Launcher;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.plug.receiver.StateReceiver;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.qrcode.aes.AESUtils;
import com.zktechnology.android.qrcode.aes.BASE64Decoder;
import com.zktechnology.android.receiver.OpLogReceiver;
import com.zktechnology.android.receiver.ZkNfcReceiver;
import com.zktechnology.android.rs485.RS485Manager;
import com.zktechnology.android.strategy.ICallback;
import com.zktechnology.android.strategy.MainThreadExecutor;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.AttStateListener;
import com.zktechnology.android.utils.CanVerifyUtil;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.SecretUtil;
import com.zktechnology.android.utils.WifiUtils;
import com.zktechnology.android.utils.ZKReceiver;
import com.zktechnology.android.utils.ZKRunnable;
import com.zktechnology.android.utils.ZKThreadPool;
import com.zktechnology.android.verify.bean.process.ZKVerViewBean;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.dialog.managment.ZKVerDlgMgt;
import com.zktechnology.android.verify.dialog.view.ZKAttRecordDialog;
import com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog;
import com.zktechnology.android.verify.dialog.view.ZKWorkCodeDialog;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zktechnology.android.view.ClockView;
import com.zktechnology.android.view.CustomWidgetView;
import com.zktechnology.android.wiegand.WiegandReceiver;
import com.zkteco.android.core.model.ButtonWidgetInfo;
import com.zkteco.android.core.sdk.BtnWidgetManager;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.core.sdk.PushProtocolManager;
import com.zkteco.android.core.sdk.StandaloneProtocolManager;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.zkcore.utils.ZKSharedUtil;
import com.zkteco.android.zkcore.view.alert.ZKConfirmDialog;
import com.zkteco.android.zkcore.view.util.Constant;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;
import com.zkteco.util.CompressionHelper;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmExtractResult;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public final class ZKLauncher extends ZKGuideCoreLauncher implements AttStateListener {
    private static final String ACTION_BTN_CLICKED = "com.zkmsgwidget.action.ACTION_BTN_WAS_CLICKED";
    private static final String ACTION_OPTION_CHANGE = "com.zkteco.android.core.ACTION_OPTION_CHANGE";
    private static final String ACTION_TYPE_CLICKED = "ACTION_TYPE";
    private static final int ACTION_TYPE_VALUE_OPEN_APP = 2;
    public static final String CALENDER_TYPE_INRAN_GREGORIAN = "1";
    public static final String CALENDER_TYPE_IRAN_LUNA = "2";
    private static final String CORE_SERVICE_INIT_ACTION = "com.zkteco.android.core.CORESERVICE_INIT_BROADCAST";
    public static final String FINGER_PHOTO = "1";
    public static final String HEJIRA_FUN_ON = "1";
    private static final long INTERVAL_CARD = 600;
    private static final long INTERVAL_FINGER_PRESS = 600;
    public static int IsSupportABCPin = 0;
    private static final String KEY_SET_VERIFY_TYPE = "VerifyStyles";
    private static final String KEY_WIFI_CONNECT_STATE_IN = "WIFI_CONNECT_STATE_IN";
    public static final int OVER_VALID_ADD_ATT_LOG = 1;
    public static final int OVER_VALID_DELETE_USER = 2;
    public static final String PHOTO_FUN_ON = "1";
    public static int PIN2Width = 0;
    public static final byte[] PUBLIC_KEY = new byte[32];
    public static final String SETTING_TAG = "Setting";
    public static final String SHOW_PHOTO = "1";
    private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    public static final String TAG = "ZKLauncher";
    public static final String USER_VALID_FUN_ON = "1";
    public static int correction = 0;
    public static boolean databaseInitialized = false;
    public static int enableNormalIRTempPass = 0;
    public static boolean enableScreenEdgeFaceFilter = false;
    public static int enableTriggerAlarm = 0;
    public static int enableUnregisterCapture = 0;
    public static int enableUnregisterPass = 0;
    public static int enableWearMaskPass = 0;
    public static int enalbeMaskDetection = 0;
    public static int externalAlarmDelay = 0;
    public static int faceFunOn = 1;
    public static int faceMinThreshold = 100;
    public static int faceRecogizeMaxCount = 0;
    public static int fingerFunOn = 1;
    public static int hasFingerModule = 1;
    public static double irTempCFP = 0.0d;
    public static boolean isLimitAttLocateFunOn = false;
    public static boolean isMultiMachineDetectFaceLimit = false;
    public static boolean isNeedFacePoseLimit = false;
    public static boolean isNeedLiveDetect = false;
    public static boolean isOpenSupportLine = false;
    public static final byte[] iv = new byte[16];
    public static double latitude = -1.0d;
    public static boolean locationFunOn = false;
    public static int locationRange = 0;
    public static int longName = 0;
    public static double longitude = -1.0d;
    public static int mDetectFaceWidth = 90;
    public static int mFaceLiveThreshold = 75;
    public static int mFacePoseThreshold = 20;
    public static int mLoopDeleteBlackPic = 0;
    public static int mMThreshold = 0;
    public static int mMultiMachineDetectFaceThreshold = 0;
    public static int mVThreshold = 0;
    public static int mVerifyFaceMinScore = 70;
    public static int maskDetectionFunOn = 0;
    public static int pvMThreshold = 0;
    public static int pvVThreshold = 0;
    public static int qrcodeFunc = 1;
    public static int rfCardOn = 1;
    public static int s1v1VerifyFaceSuccessScore = 0;
    public static int sAPBFO = 0;
    public static int sAccessRuleType = 0;
    public static int sAlarmAttLog = 0;
    public static int sAlarmReRec = 0;
    public static int sAntiPassbackOn = 0;
    public static String sCalenderType = "";
    public static int sCameraSystem = 0;
    public static String sDateFmt = "";
    public static int sDoor1ValidTZ = 0;
    public static int sDoor1VerifyType = 0;
    public static int sDoorId = 1;
    public static boolean sEnalbeIRTempDetection = false;
    public static int sFPRetry = 3;
    public static int sFaceAntiFakeRepeatedVerificationTimes = 0;
    public static String sFingerphoto = "";
    public static String sHejiraCalendar = "";
    public static String sHideNameFunOn = null;
    public static String sHidePinFunOn = "1";
    public static boolean sIRTempDetectionFunOn = false;
    public static int sIRTempUnit = 0;
    public static volatile int sInOutState = 1;
    public static int sIsSupportPull = 0;
    public static int sLockFunOn = 0;
    public static int sLoopDeleteLog = 0;
    public static int sLoopDeletePic = 0;
    public static int sMaxAttLogCount = 0;
    public static int sMaxBlackPhotoCount = 0;
    public static int sMaxCaptureCount = 0;
    public static String sMustChoiceWorkCode = "";
    public static int sOverValidTime = 0;
    public static int sPWDRetry = 3;
    public static String sPhotoFunOn = "";
    public static String sSaveBioPhotoFunOn = "1";
    public static String sShowPhoto = "";
    public static String sTimeFormat = "";
    public static String sUserValidTimeFun = "";
    public static int sVerifyDialogTimeOut = 3;
    public static int sVerifyFaceMaxScore = 300;
    public static int sVerifyFaceSuccessScore = 0;
    public static int screenTimeoutMs = -1;
    public static double targetLatitude = 0.0d;
    public static double targetLongitude = 0.0d;
    public static int temperHigh = 0;
    public static int videoIntercomFunOn = 0;
    public static int voiceOn = 1;
    public static int zkDataEncdec = 1;
    private ActionChangeReceiver actionChangeReceiver = null;
    private ActionReceiver actionReceiver = null;
    private int baudRate;
    private ClockView clockView;
    /* access modifiers changed from: private */
    public Runnable coreServiceInitRunnable;
    public DataManager dataManager;
    private Runnable dataManagerRunnable;
    private ZKConfirmDialog dialog;
    private int enableIRTempImage;
    private int enableShowTemp;
    private int ext485funType;
    private boolean firstSetIcon = false;
    private int isAlwaysShowClock;
    private int mAuxInFunOn;
    /* access modifiers changed from: private */
    public final ExecutorService mCacheService = Executors.newCachedThreadPool();
    private long mCardCurrentTime = 0;
    private int mFaceDetectDistanceThreshold = Constant.DEFAULT_SIZE;
    public String mFaceLiveThr;
    public String mFacemthr;
    public String mFacevthr;
    private long mFingerCurrentTime = 0;
    private BroadcastReceiver mIntentReceiver;
    public String mIsWorkCodeEx = "";
    private final ICallback mMainCallback = new ICallback() {
        public final void handleMessage(Message message) {
            ZKLauncher.this.lambda$new$5$ZKLauncher(message);
        }
    };
    private int mMotionDetectThreshold = 100;
    private final BroadcastReceiver mNfcReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String stringExtra = intent.getStringExtra(ZkNfcReceiver.EXTRA_KEY);
            if (!TextUtils.isEmpty(stringExtra)) {
                ZKLauncher.this.onCardRead(stringExtra);
            }
        }
    };
    private int mOpenVisibleLightThreshold = 80;
    public int mReader1IOState = 1;
    private boolean mStartRs485;
    private String mStateMode;
    private int mValidTime = 15;
    private Future<?> mVerifyTask;
    /* access modifiers changed from: private */
    public BtnWidgetManager manager;
    private OpLogReceiver opLogReceiver;
    private int readerFunOn;
    private BroadcastReceiver remoteEnrollReceiver;
    private int rs485fo;
    private int sLanguage = 0;
    /* access modifiers changed from: private */
    public SharedPreferences stateModesp;
    private StateReceiver stateReceiver = null;
    private long stateTime;
    StringBuffer stringBuffer;
    private int temperDistance;
    /* access modifiers changed from: private */
    public TextView textView;
    private BroadcastReceiver touchChangeStateReceiver;
    private String type;
    private int wantNum;
    private WiegandReceiver wiegandReceiver = null;
    private ZKVerConState zkVerConState;

    /* access modifiers changed from: private */
    public void initAutoConnectEthernet() {
    }

    private static class CoreServiceInitRunnable extends ZKRunnable<ZKLauncher> {
        CoreServiceInitRunnable(ZKLauncher zKLauncher) {
            super(zKLauncher);
        }

        public void run(ZKLauncher zKLauncher) {
            FileLogUtils.writeLauncherInitRecord("CoreServiceInitRunnable isFinishing: " + zKLauncher.isFinishing());
            if (!zKLauncher.isFinishing()) {
                zKLauncher.systemOptionsSanityCheck();
                zKLauncher.initThreadHub();
            }
        }
    }

    public static void setDatabaseInitialized(boolean z) {
        databaseInitialized = z;
    }

    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate");
        super.onCreate(bundle);
        this.dataManagerRunnable = new DataManagerRunnable(this);
        this.coreServiceInitRunnable = new CoreServiceInitRunnable(this);
        this.touchChangeStateReceiver = new TouchChangeStateReceiver(this);
        MainThreadExecutor.getInstance().addCallback(this.mMainCallback);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(201326592);
            window.getDecorView().setSystemUiVisibility(1280);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
            window.setNavigationBarColor(1711276032);
        }
        this.manager = new BtnWidgetManager(this.mContext);
        this.stateModesp = this.mContext.getSharedPreferences("STATE_MODEL_SP", 0);
        this.clockView = new ClockView(this.mContext);
        this.textView = new TextView(this.mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(0, dip2px(this.mContext, 15.0f), 0, 0);
        this.textView.setLayoutParams(layoutParams);
        this.textView.setGravity(17);
        this.textView.setTextColor(ContextCompat.getColor(this.mContext, 17170443));
        this.textView.setTextSize((float) dip2px(this.mContext, 14.0f));
        initStateReceiver();
        initWiegandReceiver();
        initOptionChangeReceiver();
        initNfcReceiver();
        initData();
        initListener();
        initTouchChangeStateReceiver();
        turnVisibleLightOff();
        turnInfraredLightOff();
    }

    private void initTouchChangeStateReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CustomWidgetView.ACTION_TOUCH_ATT_CHANGE);
        registerReceiver(this.touchChangeStateReceiver, intentFilter);
    }

    private static class TouchChangeStateReceiver extends ZKReceiver<ZKLauncher> {
        public TouchChangeStateReceiver(ZKLauncher zKLauncher) {
            super(zKLauncher);
        }

        public void onReceive(ZKLauncher zKLauncher, Context context, Intent intent) {
            if (CustomWidgetView.ACTION_TOUCH_ATT_CHANGE.equals(intent.getAction()) && ZKLauncher.sAccessRuleType == 0) {
                if (intent.getIntExtra("selectedId", -1) != -1) {
                    List<ButtonWidgetInfo> btnWidgets = zKLauncher.manager.getBtnWidgets(zKLauncher.manager.getPressedWidgetId());
                    if (btnWidgets != null) {
                        zKLauncher.textView.setText(zKLauncher.getFromShortState(Integer.parseInt(btnWidgets.get(zKLauncher.manager.getPressedPosition()).getAction().getActionParams())));
                    }
                } else if ("0".equals(zKLauncher.stateModesp.getString("STATE_MODEL_TAG", "0"))) {
                    zKLauncher.textView.setText("");
                } else {
                    String string = zKLauncher.stateModesp.getString("ACTION_PARAM", "-1");
                    if (!"-1".equals(string)) {
                        zKLauncher.textView.setText(zKLauncher.getFromShortState(Integer.parseInt(string)));
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initTaskComplete() {
        super.initTaskComplete();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (isDBOk) {
            ZKThreadPool.getInstance().executeTask(new Runnable() {
                public final void run() {
                    ZKLauncher.this.lambda$onResume$0$ZKLauncher();
                }
            });
            startLocation();
        }
        resetAccelerometer();
    }

    public /* synthetic */ void lambda$onResume$0$ZKLauncher() {
        getSystemData();
        setVerifyFun2DB();
        Log.i(TAG, "getSystemData");
    }

    private void resetAccelerometer() {
        try {
            if (Settings.System.getInt(getContentResolver(), "accelerometer_rotation") == 1) {
                Settings.System.putInt(getContentResolver(), "accelerometer_rotation", 0);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initController() {
        if (this.mVerifyTask == null) {
            ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
            ZKVerController.getInstance().init(sAccessRuleType);
            this.mVerifyTask = newSingleThreadExecutor.submit(ZKVerController.getInstance());
            newSingleThreadExecutor.shutdown();
        }
        if (sAccessRuleType == 0) {
            ZkAttDoorManager.getInstance(getApplicationContext()).initializeDoorManager();
        } else {
            ZkDoorManager.getInstance(getApplicationContext()).initializeDoorManager();
        }
    }

    private void initData() {
        this.actionReceiver = new ActionReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BTN_CLICKED);
        registerReceiver(this.actionReceiver, intentFilter);
    }

    private void initListener() {
        this.stateReceiver.setAttStateListener(this);
    }

    /* access modifiers changed from: private */
    public void onActionOpenApp() {
        ZKConfirmDialog zKConfirmDialog;
        if (CanVerifyUtil.getInstance().isCanVerify()) {
            if (ZKVerProcessPar.ACTION_BEAN.isBolActionOpen() && this.mState == Launcher.State.WORKSPACE && ((zKConfirmDialog = this.dialog) == null || !zKConfirmDialog.isShowing())) {
                ZKVerProcessPar.ACTION_BEAN.setFTouchAction();
                ZKVerProcessPar.CON_MARK_BEAN.setIntent(2);
                ZKVerController.getInstance().changeState(ZKVerConState.STATE_WANT);
            } else if (!"wait".equals(ZKVerController.synchronizedstate)) {
            } else {
                if (ZKVerController.getInstance().getState() != ZKVerConState.STATE_WAIT || !ZKEventLauncher.show) {
                    ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onFpCapture(byte[] bArr, int i, int i2) {
        if (CanVerifyUtil.getInstance().isCanVerify() && ZKVerController.getInstance().getState() != ZKVerConState.STATE_DELAY && Math.abs(SystemClock.elapsedRealtime() - this.mFingerCurrentTime) >= 600 && ZKVerProcessPar.ACTION_BEAN.isBolFingerprint() && this.mState == Launcher.State.WORKSPACE) {
            ZKConfirmDialog zKConfirmDialog = this.dialog;
            if (zKConfirmDialog == null || !zKConfirmDialog.isShowing()) {
                ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_FP_BUFFER, Base64.encodeToString(CompressionHelper.compress(bArr), 0));
                ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_FP_WIDTH, i);
                ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_FP_HEIGHT, i2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onFpExtract(byte[] bArr) {
        if (!CanVerifyUtil.getInstance().isCanVerify())
            return;
        if (ZKVerController.getInstance().getState() == ZKVerConState.STATE_DELAY)
            return;

        if (CanVerifyUtil.getInstance().isCanVerify() && ZKVerController.getInstance().getState() != ZKVerConState.STATE_DELAY) {
            ZKThreadPool.getInstance().executeTask(new Runnable(bArr) {
                public final /* synthetic */ byte[] f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    ZKLauncher.this.lambda$onFpExtract$1$ZKLauncher(this.f$1);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onFpExtract$1$ZKLauncher(byte[] bArr) {
        ZKConfirmDialog zKConfirmDialog;
        turnOnScreen();
        if (Math.abs(SystemClock.elapsedRealtime() - this.mFingerCurrentTime) >= 600) {
            this.mFingerCurrentTime = SystemClock.elapsedRealtime();
            if (ZKVerProcessPar.ACTION_BEAN.isBolFingerprint() && this.mState == Launcher.State.WORKSPACE && ((zKConfirmDialog = this.dialog) == null || !zKConfirmDialog.isShowing())) {
                FileLogUtils.writeStateLog("fingerprintImage set BUNDLE_FP_BUFFER:" + (bArr == null ? "== null" : "!= null"));
                ZKVerProcessPar.CON_MARK_BUNDLE.putByteArray(ZKVerConConst.BUNDLE_FP_TEMPLATE_BUFFER, bArr);
                playSoundBi();
                startVerify(ZKVerifyType.FINGER.getValue());
            } else if (!"wait".equals(ZKVerController.synchronizedstate)) {
            } else {
                if (ZKVerController.getInstance().getState() != ZKVerConState.STATE_WAIT || !ZKEventLauncher.show) {
                    ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onQRCodeDetected(Result result) {
        lambda$onQrcodeRead$6$ZKLauncher(result.getText());
    }

    /* access modifiers changed from: protected */
    public void onPalmDetected(ZKPalmExtractResult zKPalmExtractResult) {
        if (CanVerifyUtil.getInstance().isCanVerify()) {
            ZKThreadPool.getInstance().executeTask(new Runnable(zKPalmExtractResult) {
                public final /* synthetic */ ZKPalmExtractResult f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    ZKLauncher.this.lambda$onPalmDetected$2$ZKLauncher(this.f$1);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onPalmDetected$2$ZKLauncher(ZKPalmExtractResult zKPalmExtractResult) {
        ZKConfirmDialog zKConfirmDialog;
        LogUtils.e(LogUtils.TAG_VERIFY, "onPalmDetected");
        turnOnScreen();
        if (ZKVerProcessPar.ACTION_BEAN.isBolPalm() && this.mState == Launcher.State.WORKSPACE && ((zKConfirmDialog = this.dialog) == null || !zKConfirmDialog.isShowing())) {
            ZKVerProcessPar.CON_MARK_BUNDLE.putByteArray(ZKVerConConst.BUNDLE_PALM_VERIFY, zKPalmExtractResult.verTemplate);
            startVerify(ZKVerifyType.PALM.getValue());
        } else if (!"wait".equals(ZKVerController.synchronizedstate)) {
        } else {
            if (ZKVerController.getInstance().getState() != ZKVerConState.STATE_WAIT || !ZKEventLauncher.show) {
                ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCardRead(String str) {
        onRfidRead(str);
    }

    public void onRfidRead(String str) {
        if (CanVerifyUtil.getInstance().isCanVerify()) {
            ZKThreadPool.getInstance().executeTask(new Runnable(str) {
                public final /* synthetic */ String f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    ZKLauncher.this.lambda$onRfidRead$3$ZKLauncher(this.f$1);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onRfidRead$3$ZKLauncher(String str) {
        ZKConfirmDialog zKConfirmDialog;
        LogUtils.e(LogUtils.TAG_VERIFY, "onRfidRead");
        turnOnScreen();
        if (Math.abs(SystemClock.elapsedRealtime() - this.mCardCurrentTime) < 600) {
            LogUtils.e(LogUtils.TAG_VERIFY, "规避1秒钟打两次卡");
        } else if (!ZKVerProcessPar.ACTION_BEAN.isBolRFidRead() || this.mState != Launcher.State.WORKSPACE || ((zKConfirmDialog = this.dialog) != null && zKConfirmDialog.isShowing())) {
            if ("wait".equals(ZKVerController.synchronizedstate) && (ZKVerController.getInstance().getState() != ZKVerConState.STATE_WAIT || !ZKEventLauncher.show)) {
                ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            }
            Log.e(TAG, "onRfidRead: 拒绝卡验证");
        } else {
            this.mCardCurrentTime = SystemClock.elapsedRealtime();
            ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_CARD, str);
            playSoundBi();
            startVerify(ZKVerifyType.CARD.getValue());
        }
    }

    /* access modifiers changed from: protected */
    public void onSingleLiveFaceDetected(byte[] bArr, int i, int i2) {
        ZKConfirmDialog zKConfirmDialog;
        if (CanVerifyUtil.getInstance().isCanVerify()) {
            turnOnScreen();
            if (ZKVerController.getInstance().getState() != ZKVerConState.STATE_DELAY) {
                if (ZKVerProcessPar.ACTION_BEAN.isBolFace() && this.mState == Launcher.State.WORKSPACE && ((zKConfirmDialog = this.dialog) == null || !zKConfirmDialog.isShowing())) {
                    ZKVerProcessPar.CON_MARK_BUNDLE.putByteArray(ZKVerConConst.BUNDLE_FACE_FEATURE, bArr);
                    ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_FACE_WIDTH, i);
                    ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_FACE_HEIGHT, i2);
                    startVerify(ZKVerifyType.FACE.getValue());
                } else if (!"wait".equals(ZKVerController.synchronizedstate)) {
                } else {
                    if (ZKVerController.getInstance().getState() != ZKVerConState.STATE_WAIT || !ZKEventLauncher.show) {
                        ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSingleLiveFaceRecognize(String str, float f, byte[] bArr, int i) {
        ZKConfirmDialog zKConfirmDialog;
        if (CanVerifyUtil.getInstance().isCanVerify() && ZKVerController.getInstance().getState() != ZKVerConState.STATE_DELAY) {
            turnOnScreen();
            if (ZKVerProcessPar.ACTION_BEAN.isBolFace() && this.mState == Launcher.State.WORKSPACE && ((zKConfirmDialog = this.dialog) == null || !zKConfirmDialog.isShowing())) {
                ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_FACE_RECOGNIZE_PIN, str);
                ZKVerProcessPar.CON_MARK_BUNDLE.putByteArray(ZKVerConConst.BUNDLE_FACE_IAMGE_DATA, bArr);
                ZKVerProcessPar.CON_MARK_BUNDLE.putFloat(ZKVerConConst.BUNDLE_FACE_RECOGNIZE_SCORE, f);
                ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_WEAR_MASK, i);
                startVerify(ZKVerifyType.FACE.getValue());
            } else if (!"wait".equals(ZKVerController.synchronizedstate)) {
            } else {
                if (ZKVerController.getInstance().getState() != ZKVerConState.STATE_WAIT || !ZKEventLauncher.show) {
                    ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMaskDetected(int i) {
        ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_WEAR_MASK, i);
    }

    public void attStateChange(String str, String str2) {
        this.type = str2;
        if (this.textView == null || sAccessRuleType != 0 || (sLockFunOn == 15 && sAPBFO != 0 && sAntiPassbackOn != 0)) {
            MainThreadExecutor.getInstance().execute(32);
        } else if (this.manager.getPressedWidgetId() != -1) {
        } else {
            if ("2".equals(str) || "5".equals(str)) {
                MainThreadExecutor.getInstance().execute(16);
            } else if ("0".equals(str)) {
                MainThreadExecutor.getInstance().execute(32);
            }
        }
    }

    public static class ActionReceiver extends ZKReceiver<ZKLauncher> {
        public ActionReceiver(ZKLauncher zKLauncher) {
            super(zKLauncher);
        }

        public void onReceive(ZKLauncher zKLauncher, Context context, Intent intent) {
            if (intent != null && intent.getAction() != null && ZKLauncher.ACTION_BTN_CLICKED.equals(intent.getAction()) && intent.getIntExtra(ZKLauncher.ACTION_TYPE_CLICKED, 0) == 2) {
                zKLauncher.onActionOpenApp();
            }
        }
    }

    public class ActionChangeReceiver extends ZKReceiver<ZKLauncher> {
        public ActionChangeReceiver(ZKLauncher zKLauncher) {
            super(zKLauncher);
        }

        public void onReceive(ZKLauncher zKLauncher, Context context, Intent intent) {
            if (intent != null && intent.getAction() != null && "com.zkteco.android.core.ACTION_OPTION_CHANGE".equalsIgnoreCase(intent.getAction())) {
                ZKThreadPool.getInstance().executeTask(new Runnable() {
                    public final void run() {
                        ZKLauncher.ActionChangeReceiver.lambda$onReceive$0(ZKLauncher.this);
                    }
                });
            }
        }

        static /* synthetic */ void lambda$onReceive$0(ZKLauncher zKLauncher) {
            Log.i(ZKLauncher.TAG, "obj.getSystemData(): E");
            zKLauncher.getSystemData();
            Log.i(ZKLauncher.TAG, "obj.getSystemData(): X");
        }
    }

    public void showAllApps() {
        super.showAllApps();
    }

    /* access modifiers changed from: package-private */
    public void showWorkspace(boolean z) {
        super.showWorkspace(z);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(37:0|1|2|(1:4)|5|6|7|8|9|10|(1:12)(1:13)|14|(1:16)(1:17)|18|(1:20)(1:21)|22|(1:24)(1:25)|26|(1:(1:(1:30)(1:31))(1:32))(1:33)|34|(1:36)(1:37)|38|(1:40)(1:41)|42|(1:44)(1:45)|46|(1:51)(1:50)|52|(1:54)(1:55)|56|59|(1:61)|62|63|64|67|(9:69|(1:71)|(1:73)(1:74)|75|(1:77)(1:78)|79|(1:81)(1:(1:83)(1:84))|85|86)(1:87)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x01d8 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getSystemData() {
        /*
            r14 = this;
            java.lang.String r0 = "-1"
            java.lang.String r1 = "~FaceMThr"
            java.lang.String r2 = "82"
            java.lang.String r3 = "~FaceVThr"
            java.lang.String r4 = "Reader1IOState"
            java.lang.String r5 = "PhotoFunOn"
            java.lang.String r6 = "1"
            java.lang.String r7 = "0"
            java.lang.String r8 = "Setting"
            com.zkteco.android.db.orm.manager.DataManager r9 = com.zktechnology.android.utils.DBManager.getInstance()
            r14.dataManager = r9
            r10 = 1
            r11 = 0
            java.lang.String r12 = "~PIN2Width"
            r13 = 9
            int r9 = r9.getIntOption(r12, r13)     // Catch:{ Exception -> 0x05fc }
            PIN2Width = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "IsSupportABCPin"
            int r9 = r9.getIntOption(r12, r11)     // Catch:{ Exception -> 0x05fc }
            IsSupportABCPin = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "FingerFunOn"
            int r9 = r9.getIntOption(r12, r10)     // Catch:{ Exception -> 0x05fc }
            fingerFunOn = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "hasFingerModule"
            int r9 = r9.getIntOption(r12, r10)     // Catch:{ Exception -> 0x05fc }
            hasFingerModule = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "~RFCardOn"
            int r9 = r9.getIntOption(r12, r11)     // Catch:{ Exception -> 0x05fc }
            rfCardOn = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "FaceFunOn"
            int r9 = r9.getIntOption(r12, r10)     // Catch:{ Exception -> 0x05fc }
            faceFunOn = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "MThreshold"
            r13 = 35
            int r9 = r9.getIntOption(r12, r13)     // Catch:{ Exception -> 0x05fc }
            r12 = 15
            int r9 = r9 + r12
            mMThreshold = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r13 = "VThreshold"
            int r9 = r9.getIntOption(r13, r12)     // Catch:{ Exception -> 0x05fc }
            int r9 = r9 + r12
            mVThreshold = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "UserValidTimeFun"
            java.lang.String r9 = r9.getStrOption(r12, r7)     // Catch:{ Exception -> 0x05fc }
            sUserValidTimeFun = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "MustChoiceWorkCode"
            java.lang.String r9 = r9.getStrOption(r12, r7)     // Catch:{ Exception -> 0x05fc }
            sMustChoiceWorkCode = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "HejiraCalendar"
            java.lang.String r9 = r9.getStrOption(r12, r7)     // Catch:{ Exception -> 0x05fc }
            sHejiraCalendar = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "CalenderType"
            java.lang.String r9 = r9.getStrOption(r12, r7)     // Catch:{ Exception -> 0x05fc }
            sCalenderType = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "DtFmt"
            java.lang.String r13 = "9"
            java.lang.String r9 = r9.getStrOption(r12, r13)     // Catch:{ Exception -> 0x05fc }
            sDateFmt = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "timeFormat"
            java.lang.String r9 = r9.getStrOption(r12, r6)     // Catch:{ Exception -> 0x05fc }
            sTimeFormat = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = r9.getStrOption(r5, r7)     // Catch:{ Exception -> 0x05fc }
            sPhotoFunOn = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "SaveBioPhotoFunOn"
            java.lang.String r9 = r9.getStrOption(r12, r6)     // Catch:{ Exception -> 0x05fc }
            sSaveBioPhotoFunOn = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r9 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = "ShowPhoto"
            java.lang.String r9 = r9.getStrOption(r12, r7)     // Catch:{ Exception -> 0x05fc }
            sShowPhoto = r9     // Catch:{ Exception -> 0x05fc }
            java.lang.String r12 = sPhotoFunOn     // Catch:{ Exception -> 0x05fc }
            if (r9 == r12) goto L_0x00d5
            sPhotoFunOn = r9     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r12 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            r12.setStrOption(r5, r9)     // Catch:{ Exception -> 0x05fc }
        L_0x00d5:
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "ShowFingerprint"
            java.lang.String r5 = r5.getStrOption(r9, r7)     // Catch:{ Exception -> 0x05fc }
            sFingerphoto = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "AlarmReRec"
            int r5 = r5.getIntOption(r9, r10)     // Catch:{ Exception -> 0x05fc }
            sAlarmReRec = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "~MaxAttLogCount"
            r12 = 100
            int r5 = r5.getIntOption(r9, r12)     // Catch:{ Exception -> 0x05fc }
            int r5 = r5 * 10000
            sMaxAttLogCount = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "AlarmAttLog"
            r13 = 99
            int r5 = r5.getIntOption(r9, r13)     // Catch:{ Exception -> 0x05fc }
            sAlarmAttLog = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "LoopDeleteLog"
            int r5 = r5.getIntOption(r9, r11)     // Catch:{ Exception -> 0x05fc }
            sLoopDeleteLog = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "maxCaptureCount"
            r13 = 5000(0x1388, float:7.006E-42)
            int r5 = r5.getIntOption(r9, r13)     // Catch:{ Exception -> 0x05fc }
            sMaxCaptureCount = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "LoopDeletePic"
            int r5 = r5.getIntOption(r9, r11)     // Catch:{ Exception -> 0x05fc }
            sLoopDeletePic = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "CapturePic"
            int r5 = r5.getIntOption(r9, r11)     // Catch:{ Exception -> 0x05fc }
            sCameraSystem = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "~MaxBlackPhotoCount"
            r13 = 500(0x1f4, float:7.0E-43)
            int r5 = r5.getIntOption(r9, r13)     // Catch:{ Exception -> 0x05fc }
            sMaxBlackPhotoCount = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "LoopDeleteBlackPic"
            int r5 = r5.getIntOption(r9, r11)     // Catch:{ Exception -> 0x05fc }
            mLoopDeleteBlackPic = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "IsWorkCodeEx"
            java.lang.String r5 = r5.getStrOption(r9, r7)     // Catch:{ Exception -> 0x05fc }
            r14.mIsWorkCodeEx = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "FPRetry"
            r13 = 3
            int r5 = r5.getIntOption(r9, r13)     // Catch:{ Exception -> 0x05fc }
            sFPRetry = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "PWRetry"
            int r5 = r5.getIntOption(r9, r13)     // Catch:{ Exception -> 0x05fc }
            sPWDRetry = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r9 = "OverValidTime"
            int r5 = r5.getIntOption(r9, r11)     // Catch:{ Exception -> 0x05fc }
            sOverValidTime = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            int r5 = r5.getIntOption(r4, r11)     // Catch:{ Exception -> 0x05fc }
            r14.mReader1IOState = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = r5.getStrOption(r3, r2)     // Catch:{ Exception -> 0x05fc }
            r14.mFacevthr = r5     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r5 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r5.getStrOption(r1, r2)     // Catch:{ Exception -> 0x05fc }
            r14.mFacemthr = r2     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            int r2 = r2.getIntOption(r4, r11)     // Catch:{ Exception -> 0x05fc }
            sInOutState = r2     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "HidePinFunOn"
            java.lang.String r2 = r2.getStrOption(r4, r7)     // Catch:{ Exception -> 0x05fc }
            sHidePinFunOn = r2     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "HideName"
            java.lang.String r2 = r2.getStrOption(r4, r6)     // Catch:{ Exception -> 0x05fc }
            sHideNameFunOn = r2     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.core.sdk.ZKDateTimeManager r2 = com.zkteco.android.core.sdk.ZKDateTimeManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = sDateFmt     // Catch:{ Exception -> 0x05fc }
            int r4 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x05fc }
            r2.setTime_dtFmt(r4)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "IsSupportLongName"
            int r2 = r2.getIntOption(r4, r10)     // Catch:{ Exception -> 0x05fc }
            longName = r2     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "TOMenu"
            r5 = 60
            int r2 = r2.getIntOption(r4, r5)     // Catch:{ Exception -> 0x05fc }
            int r2 = r2 * 1000
            screenTimeoutMs = r2     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "~FaceLiveThreshold"
            java.lang.String r5 = "85"
            java.lang.String r2 = r2.getStrOption(r4, r5)     // Catch:{ Exception -> 0x05fc }
            r14.mFaceLiveThr = r2     // Catch:{ Exception -> 0x05fc }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x01d8 }
            mFaceLiveThreshold = r2     // Catch:{ NumberFormatException -> 0x01d8 }
            goto L_0x01f0
        L_0x01d8:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "getSystemData: mFaceLiveThreshold convert failed,mFaceLiveThreshold="
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            int r4 = mFaceLiveThreshold     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.e(r8, r2)     // Catch:{ Exception -> 0x05fc }
        L_0x01f0:
            int r2 = mFaceLiveThreshold     // Catch:{ Exception -> 0x05fc }
            if (r2 == 0) goto L_0x01f6
            r2 = r10
            goto L_0x01f7
        L_0x01f6:
            r2 = r11
        L_0x01f7:
            isNeedLiveDetect = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "getSystemData: liveness threshold="
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = r14.mFaceLiveThr     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "~AntiMultiMachineDetectFace"
            int r2 = r2.getIntOption(r4, r11)     // Catch:{ Exception -> 0x05fc }
            if (r2 != r10) goto L_0x021d
            r2 = r10
            goto L_0x021e
        L_0x021d:
            r2 = r11
        L_0x021e:
            isMultiMachineDetectFaceLimit = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "getSystemData: isMultiMachineDetectLimit="
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            boolean r4 = isMultiMachineDetectFaceLimit     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "~AntiScreenEdgeFace"
            int r2 = r2.getIntOption(r4, r11)     // Catch:{ Exception -> 0x05fc }
            if (r2 != r10) goto L_0x0244
            r2 = r10
            goto L_0x0245
        L_0x0244:
            r2 = r11
        L_0x0245:
            enableScreenEdgeFaceFilter = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "getSystemData: enableScreenEdgeFaceFilter="
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            boolean r4 = enableScreenEdgeFaceFilter     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "~AntiMultiMachineDetectFaceThreshold"
            int r2 = r2.getIntOption(r4, r11)     // Catch:{ Exception -> 0x05fc }
            mMultiMachineDetectFaceThreshold = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "getSystemData: mMultiMachineDetectFaceThreshold="
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            int r4 = mMultiMachineDetectFaceThreshold     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "~IsOpenAuxiliaryLine"
            int r2 = r2.getIntOption(r4, r11)     // Catch:{ Exception -> 0x05fc }
            if (r2 != r10) goto L_0x028d
            r2 = r10
            goto L_0x028e
        L_0x028d:
            r2 = r11
        L_0x028e:
            isOpenSupportLine = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r4 = "getSystemData: isOpenSupportLine="
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            boolean r4 = isOpenSupportLine     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            int r2 = mMultiMachineDetectFaceThreshold     // Catch:{ Exception -> 0x05fc }
            r4 = 2
            if (r2 == 0) goto L_0x02bf
            if (r2 == r10) goto L_0x02b8
            if (r2 == r4) goto L_0x02b2
            goto L_0x02c5
        L_0x02b2:
            int r2 = WINDOWS_WIDTH     // Catch:{ Exception -> 0x05fc }
            int r2 = r2 / r13
            mRecognizeRange = r2     // Catch:{ Exception -> 0x05fc }
            goto L_0x02c5
        L_0x02b8:
            int r2 = WINDOWS_WIDTH     // Catch:{ Exception -> 0x05fc }
            int r2 = r2 / 4
            mRecognizeRange = r2     // Catch:{ Exception -> 0x05fc }
            goto L_0x02c5
        L_0x02bf:
            int r2 = WINDOWS_WIDTH     // Catch:{ Exception -> 0x05fc }
            int r2 = r2 / 6
            mRecognizeRange = r2     // Catch:{ Exception -> 0x05fc }
        L_0x02c5:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "onResume: mRecognizeRange="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            int r5 = mRecognizeRange     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "~FaceDetectSizeThreshold"
            r6 = 90
            int r2 = r2.getIntOption(r5, r6)     // Catch:{ Exception -> 0x05fc }
            mDetectFaceWidth = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "getSystemData: face width="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            int r5 = mDetectFaceWidth     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "ValidSkipTime"
            r9 = 5
            int r2 = r2.getIntOption(r5, r9)     // Catch:{ Exception -> 0x05fc }
            r14.mValidTime = r2     // Catch:{ Exception -> 0x05fc }
            int r2 = r2 * 1000
            com.zktechnology.android.launcher2.ZkFaceLauncher.TURN_OFF_VISIBLE_LIGHT_TIME_OUT = r2     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "~FaceLuminanceThreshold"
            r13 = 80
            int r2 = r2.getIntOption(r5, r13)     // Catch:{ Exception -> 0x05fc }
            r14.mOpenVisibleLightThreshold = r2     // Catch:{ Exception -> 0x05fc }
            com.zktechnology.android.launcher2.ZkFaceLauncher.OPEN_VISIBLE_LIGHT_THRESHOLD = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "getSystemData: OpenVisibleLightThreshold ="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            int r5 = r14.mOpenVisibleLightThreshold     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "~MotionDetectThreshold"
            int r2 = r2.getIntOption(r5, r12)     // Catch:{ Exception -> 0x05fc }
            r14.mMotionDetectThreshold = r2     // Catch:{ Exception -> 0x05fc }
            com.zktechnology.android.launcher2.ZkFaceLauncher.mMotionDetectThreshold = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "getSystemData: motion detect threshold="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            int r5 = r14.mMotionDetectThreshold     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "~FacePoseThreshold"
            r13 = 20
            int r2 = r2.getIntOption(r5, r13)     // Catch:{ Exception -> 0x05fc }
            mFacePoseThreshold = r2     // Catch:{ Exception -> 0x05fc }
            if (r2 == r6) goto L_0x036a
            r2 = r10
            goto L_0x036b
        L_0x036a:
            r2 = r11
        L_0x036b:
            isNeedFacePoseLimit = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "getSystemData: face pose threshold="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            int r5 = mFacePoseThreshold     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "~FVInterval"
            int r2 = r2.getIntOption(r5, r10)     // Catch:{ Exception -> 0x05fc }
            int r2 = r2 * 1000
            com.zktechnology.android.launcher2.ZkFaceLauncher.DETECT_FACE_MIN_TIME_INTERVAL = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "getSystemData: face detect interval="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            int r5 = com.zktechnology.android.launcher2.ZkFaceLauncher.DETECT_FACE_MIN_TIME_INTERVAL     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "~FaceDetectDistanceThreshold"
            r6 = 150(0x96, float:2.1E-43)
            int r2 = r2.getIntOption(r5, r6)     // Catch:{ Exception -> 0x05fc }
            r14.mFaceDetectDistanceThreshold = r2     // Catch:{ Exception -> 0x05fc }
            com.zktechnology.android.launcher2.ZkFaceLauncher.mFaceDetectDistanceThreshold = r2     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r2.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r5 = "getSystemData: face detect distance="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            int r5 = r14.mFaceDetectDistanceThreshold     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r2)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r2 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            r5 = 82
            int r1 = r2.getIntOption(r1, r5)     // Catch:{ Exception -> 0x05fc }
            sVerifyFaceSuccessScore = r1     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r1.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "getSystemData: face recognize 1:N threshold="
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x05fc }
            int r2 = sVerifyFaceSuccessScore     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r1)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "FaceAntiFakeRepeatedVerificationTimes"
            int r1 = r1.getIntOption(r2, r10)     // Catch:{ Exception -> 0x05fc }
            sFaceAntiFakeRepeatedVerificationTimes = r1     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r1.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "getSystemData: face recognize FaceAntiFakeRepeatedVerificationTimes = "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x05fc }
            int r2 = sFaceAntiFakeRepeatedVerificationTimes     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r1)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            int r1 = r1.getIntOption(r3, r5)     // Catch:{ Exception -> 0x05fc }
            s1v1VerifyFaceSuccessScore = r1     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05fc }
            r1.<init>()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "getSystemData: face recognize 1:1 threshold="
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x05fc }
            int r2 = s1v1VerifyFaceSuccessScore     // Catch:{ Exception -> 0x05fc }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x05fc }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x05fc }
            android.util.Log.i(r8, r1)     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "AuxInFunOn"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            r14.mAuxInFunOn = r1     // Catch:{ Exception -> 0x05fc }
            android.content.SharedPreferences r1 = r14.stateModesp     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "STATE_MODEL_TAG"
            java.lang.String r1 = r1.getString(r2, r7)     // Catch:{ Exception -> 0x05fc }
            r14.mStateMode = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "~APBFO"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            sAPBFO = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "AntiPassback"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            sAntiPassbackOn = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "Language"
            r3 = 69
            int r1 = r1.getIntOption(r2, r3)     // Catch:{ Exception -> 0x05fc }
            r14.sLanguage = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "ZKDataEncdec"
            int r1 = r1.getIntOption(r2, r10)     // Catch:{ Exception -> 0x05fc }
            zkDataEncdec = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "FaceMinThreshold"
            int r1 = r1.getIntOption(r2, r12)     // Catch:{ Exception -> 0x05fc }
            faceMinThreshold = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "IRTempDetectionFunOn"
            int r1 = r1.getIntOption(r2, r10)     // Catch:{ Exception -> 0x05fc }
            if (r1 != r10) goto L_0x0489
            r1 = r10
            goto L_0x048a
        L_0x0489:
            r1 = r11
        L_0x048a:
            sIRTempDetectionFunOn = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "EnalbeIRTempDetection"
            int r1 = r1.getIntOption(r2, r10)     // Catch:{ Exception -> 0x05fc }
            if (r1 != r10) goto L_0x049a
            r1 = r10
            goto L_0x049b
        L_0x049a:
            r1 = r11
        L_0x049b:
            sEnalbeIRTempDetection = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "IRTempUnit"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            sIRTempUnit = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "IRTempCorrection"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            correction = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "IRTempDetectDistance"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            r14.temperDistance = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "IRTempThreshold"
            r3 = 3730(0xe92, float:5.227E-42)
            int r1 = r1.getIntOption(r2, r3)     // Catch:{ Exception -> 0x05fc }
            temperHigh = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "EnableUnregisterPass"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            enableUnregisterPass = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "EnableUnregisterCapture"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            enableUnregisterCapture = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "EnableNormalIRTempPass"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            enableNormalIRTempPass = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "EnableTriggerAlarm"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            enableTriggerAlarm = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "EnableShowTemp"
            int r1 = r1.getIntOption(r2, r10)     // Catch:{ Exception -> 0x05fc }
            r14.enableShowTemp = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "EnableIRTempImage"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            r14.enableIRTempImage = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "~RS485FO"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            r14.rs485fo = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "~Ext485ReaderFunOn"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            r14.readerFunOn = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "RS232BaudRate"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            r14.baudRate = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = r14.dataManager     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "EXT232FunType"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            r14.ext485funType = r1     // Catch:{ Exception -> 0x05fc }
            boolean r1 = sIRTempDetectionFunOn     // Catch:{ Exception -> 0x05fc }
            if (r1 == 0) goto L_0x0546
            boolean r1 = sEnalbeIRTempDetection     // Catch:{ Exception -> 0x05fc }
            if (r1 == 0) goto L_0x0546
            faceRecogizeMaxCount = r4     // Catch:{ Exception -> 0x05fc }
            goto L_0x0548
        L_0x0546:
            faceRecogizeMaxCount = r9     // Catch:{ Exception -> 0x05fc }
        L_0x0548:
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "EnalbeMaskDetection"
            int r1 = r1.getIntOption(r2, r10)     // Catch:{ Exception -> 0x05fc }
            enalbeMaskDetection = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "MaskDetectionFunOn"
            int r1 = r1.getIntOption(r2, r10)     // Catch:{ Exception -> 0x05fc }
            maskDetectionFunOn = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "EnableWearMaskPass"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            enableWearMaskPass = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "ExternalAlarmDelay"
            r3 = 10
            int r1 = r1.getIntOption(r2, r3)     // Catch:{ Exception -> 0x05fc }
            externalAlarmDelay = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "~qrcode"
            int r1 = r1.getIntOption(r2, r10)     // Catch:{ Exception -> 0x05fc }
            qrcodeFunc = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "IRTempCorrectionForProduction"
            java.lang.String r3 = "0.3"
            java.lang.String r1 = r1.getStrOption(r2, r3)     // Catch:{ Exception -> 0x05fc }
            double r1 = java.lang.Double.parseDouble(r1)     // Catch:{ Exception -> 0x05fc }
            irTempCFP = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "PvMThreshold"
            r3 = 576(0x240, float:8.07E-43)
            int r1 = r1.getIntOption(r2, r3)     // Catch:{ Exception -> 0x05fc }
            pvMThreshold = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "PvVThreshold"
            int r1 = r1.getIntOption(r2, r3)     // Catch:{ Exception -> 0x05fc }
            pvVThreshold = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "VideoIntercomFunOn"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            videoIntercomFunOn = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "IsLimitAttLocateFunOn"
            int r1 = r1.getIntOption(r2, r11)     // Catch:{ Exception -> 0x05fc }
            if (r1 != r10) goto L_0x05cc
            r1 = r10
            goto L_0x05cd
        L_0x05cc:
            r1 = r11
        L_0x05cd:
            isLimitAttLocateFunOn = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "TargetLongitude"
            java.lang.String r1 = r1.getStrOption(r2, r0)     // Catch:{ Exception -> 0x05fc }
            double r1 = java.lang.Double.parseDouble(r1)     // Catch:{ Exception -> 0x05fc }
            targetLongitude = r1     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r2 = "TargetLatitude"
            java.lang.String r0 = r1.getStrOption(r2, r0)     // Catch:{ Exception -> 0x05fc }
            double r0 = java.lang.Double.parseDouble(r0)     // Catch:{ Exception -> 0x05fc }
            targetLatitude = r0     // Catch:{ Exception -> 0x05fc }
            com.zkteco.android.db.orm.manager.DataManager r0 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x05fc }
            java.lang.String r1 = "LocationRange"
            int r0 = r0.getIntOption(r1, r11)     // Catch:{ Exception -> 0x05fc }
            locationRange = r0     // Catch:{ Exception -> 0x05fc }
            goto L_0x0600
        L_0x05fc:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0600:
            int r0 = r14.sLanguage
            if (r0 == 0) goto L_0x0615
            com.zktechnology.android.utils.LanguageUtil r0 = com.zktechnology.android.utils.LanguageUtil.getInstance()
            int r1 = r14.sLanguage
            r0.setLanguage(r1)
            com.zktechnology.android.launcher2.-$$Lambda$ZKLauncher$etGnTq8ZVsIiTDni5aJA7N-EQx0 r0 = new com.zktechnology.android.launcher2.-$$Lambda$ZKLauncher$etGnTq8ZVsIiTDni5aJA7N-EQx0
            r0.<init>()
            r14.runOnUiThread(r0)
        L_0x0615:
            r14.setAboutRs485()
            r14.sendAuxInState()
            r14.getDoorOptionsData()
            com.zktechnology.android.strategy.MainThreadExecutor r0 = com.zktechnology.android.strategy.MainThreadExecutor.getInstance()
            r1 = 64
            r0.execute((int) r1)
            com.zkteco.android.db.orm.manager.DataManager r0 = r14.dataManager     // Catch:{ Exception -> 0x0632 }
            java.lang.String r1 = "VoiceOn"
            int r0 = r0.getIntOption(r1, r10)     // Catch:{ Exception -> 0x0632 }
            voiceOn = r0     // Catch:{ Exception -> 0x0632 }
            goto L_0x0636
        L_0x0632:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0636:
            int r0 = voiceOn
            r14.setVoiceOn(r0)
            com.zktechnology.android.device.DeviceManager r0 = com.zktechnology.android.device.DeviceManager.getDefault()
            boolean r0 = r0.isG6()
            if (r0 == 0) goto L_0x0692
            com.zkteco.android.db.orm.manager.DataManager r0 = r14.dataManager
            java.lang.String r1 = "UsbMasterMode"
            int r0 = r0.getIntOption(r1, r10)
            if (r0 != r10) goto L_0x0650
            goto L_0x0651
        L_0x0650:
            r10 = r11
        L_0x0651:
            if (r10 == 0) goto L_0x0658
            int r0 = com.zktechnology.android.utils.ShellCmds.setMaster()
            goto L_0x065c
        L_0x0658:
            int r0 = com.zktechnology.android.utils.ShellCmds.setSlaver()
        L_0x065c:
            java.lang.String r1 = TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getSystemData: 设置USB"
            java.lang.StringBuilder r2 = r2.append(r3)
            if (r10 == 0) goto L_0x066e
            java.lang.String r3 = "主模式"
            goto L_0x0670
        L_0x066e:
            java.lang.String r3 = "从模式"
        L_0x0670:
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = ", 结果: "
            java.lang.StringBuilder r2 = r2.append(r3)
            r3 = -1
            if (r0 != r3) goto L_0x0680
            java.lang.String r0 = "跳过"
            goto L_0x0687
        L_0x0680:
            if (r0 != 0) goto L_0x0685
            java.lang.String r0 = "成功"
            goto L_0x0687
        L_0x0685:
            java.lang.String r0 = "失败"
        L_0x0687:
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            android.util.Log.e(r1, r0)
        L_0x0692:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.launcher2.ZKLauncher.getSystemData():void");
    }

    public /* synthetic */ void lambda$getSystemData$4$ZKLauncher() {
        ((TextView) findViewById(R.id.measure_temperature)).setText(R.string.measure_temp);
    }

    private void setVoiceOn(int i) {
        AudioManager audioManager = (AudioManager) getSystemService("audio");
        if (!(i == 1)) {
            audioManager.setStreamVolume(3, 0, 0);
        } else if (this.dataManager.getIntOption("~hadTimeSetVolume", 0) == 0) {
            audioManager.setStreamVolume(3, 10, 0);
            this.dataManager.setIntOption("~hadTimeSetVolume", 1);
        } else if (audioManager.getStreamVolume(3) == 0) {
            audioManager.setStreamVolume(3, 10, 0);
        }
    }

    public /* synthetic */ void lambda$new$5$ZKLauncher(Message message) {
        int i = message.what;
        if (i == 16) {
            this.textView.setText(getFromShortState(Integer.parseInt(this.type)));
        } else if (i == 32) {
            this.textView.setText("");
        } else if (i == 64) {
            if (!this.firstSetIcon) {
                this.firstSetIcon = true;
                updateStatus(sAccessRuleType, sLockFunOn);
            }
            this.rlAttClock.removeAllViews();
            this.ll_dontworkroot.removeAllViews();
            int parseInt = Integer.parseInt(this.stateModesp.getString("ACTION_PARAM", "-1"));
            this.rlAttClock.setVisibility(8);
            this.ll_dontworkroot.setVisibility(0);
            this.ll_dontworkroot.addView(this.clockView);
            this.ll_dontworkroot.addView(this.textView);
            if ("2".equals(this.mStateMode) || "5".equals(this.mStateMode)) {
                if (sAccessRuleType == 0 && (sLockFunOn != 15 || sAPBFO == 0 || sAntiPassbackOn == 0)) {
                    this.textView.setText(getFromShortState(parseInt));
                } else {
                    this.textView.setText("");
                }
            }
            this.mPreviewTime.setVisibility(View.VISIBLE);
            showTemperatureRegion(sIRTempDetectionFunOn, sEnalbeIRTempDetection, sIRTempUnit, correction, this.temperDistance, temperHigh, this.enableShowTemp, this.enableIRTempImage);
        } else if (i == 128) {
            initController();
            FileLogUtils.writeLauncherInitRecord("handler dismissInitDialog");
            ZKEventLauncher.dismissInitDialog(true);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getFromShortState(int r10) {
        /*
            r9 = this;
            com.zkteco.android.db.orm.tna.ShortState r0 = new com.zkteco.android.db.orm.tna.ShortState     // Catch:{ Exception -> 0x00c7 }
            r0.<init>()     // Catch:{ Exception -> 0x00c7 }
            com.j256.ormlite.stmt.QueryBuilder r0 = r0.getQueryBuilder()     // Catch:{ Exception -> 0x00c7 }
            com.j256.ormlite.stmt.Where r0 = r0.where()     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r1 = "State_No"
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x00c7 }
            com.j256.ormlite.stmt.Where r10 = r0.eq(r1, r10)     // Catch:{ Exception -> 0x00c7 }
            java.util.List r10 = r10.query()     // Catch:{ Exception -> 0x00c7 }
            r0 = 0
            if (r10 == 0) goto L_0x00c6
            boolean r1 = r10.isEmpty()     // Catch:{ Exception -> 0x00c7 }
            if (r1 != 0) goto L_0x00c6
            r1 = 0
            java.lang.Object r10 = r10.get(r1)     // Catch:{ Exception -> 0x00c7 }
            com.zkteco.android.db.orm.tna.ShortState r10 = (com.zkteco.android.db.orm.tna.ShortState) r10     // Catch:{ Exception -> 0x00c7 }
            java.lang.String r2 = r10.getDescription()     // Catch:{ Exception -> 0x00c7 }
            if (r2 == 0) goto L_0x0036
            java.lang.String r10 = r10.getDescription()     // Catch:{ Exception -> 0x00c7 }
            return r10
        L_0x0036:
            java.lang.String r2 = r10.getState_Name()     // Catch:{ Exception -> 0x00c7 }
            if (r2 == 0) goto L_0x00c6
            java.lang.String r10 = r10.getState_Name()     // Catch:{ Exception -> 0x00c7 }
            r2 = -1
            int r3 = r10.hashCode()     // Catch:{ Exception -> 0x00c7 }
            r4 = 5
            r5 = 4
            r6 = 3
            r7 = 2
            r8 = 1
            switch(r3) {
                case -892482113: goto L_0x0080;
                case -892482112: goto L_0x0076;
                case -892482111: goto L_0x006c;
                case -892482110: goto L_0x0062;
                case -892482109: goto L_0x0058;
                case -892482108: goto L_0x004e;
                default: goto L_0x004d;
            }     // Catch:{ Exception -> 0x00c7 }
        L_0x004d:
            goto L_0x0089
        L_0x004e:
            java.lang.String r1 = "state5"
            boolean r10 = r10.equals(r1)     // Catch:{ Exception -> 0x00c7 }
            if (r10 == 0) goto L_0x0089
            r1 = r4
            goto L_0x008a
        L_0x0058:
            java.lang.String r1 = "state4"
            boolean r10 = r10.equals(r1)     // Catch:{ Exception -> 0x00c7 }
            if (r10 == 0) goto L_0x0089
            r1 = r5
            goto L_0x008a
        L_0x0062:
            java.lang.String r1 = "state3"
            boolean r10 = r10.equals(r1)     // Catch:{ Exception -> 0x00c7 }
            if (r10 == 0) goto L_0x0089
            r1 = r6
            goto L_0x008a
        L_0x006c:
            java.lang.String r1 = "state2"
            boolean r10 = r10.equals(r1)     // Catch:{ Exception -> 0x00c7 }
            if (r10 == 0) goto L_0x0089
            r1 = r7
            goto L_0x008a
        L_0x0076:
            java.lang.String r1 = "state1"
            boolean r10 = r10.equals(r1)     // Catch:{ Exception -> 0x00c7 }
            if (r10 == 0) goto L_0x0089
            r1 = r8
            goto L_0x008a
        L_0x0080:
            java.lang.String r3 = "state0"
            boolean r10 = r10.equals(r3)     // Catch:{ Exception -> 0x00c7 }
            if (r10 == 0) goto L_0x0089
            goto L_0x008a
        L_0x0089:
            r1 = r2
        L_0x008a:
            if (r1 == 0) goto L_0x00bf
            if (r1 == r8) goto L_0x00b7
            if (r1 == r7) goto L_0x00af
            if (r1 == r6) goto L_0x00a7
            if (r1 == r5) goto L_0x009f
            if (r1 == r4) goto L_0x0097
            goto L_0x00c6
        L_0x0097:
            r10 = 2131755363(0x7f100163, float:1.9141603E38)
            java.lang.String r0 = r9.getString(r10)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c6
        L_0x009f:
            r10 = 2131755362(0x7f100162, float:1.9141601E38)
            java.lang.String r0 = r9.getString(r10)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c6
        L_0x00a7:
            r10 = 2131755359(0x7f10015f, float:1.9141595E38)
            java.lang.String r0 = r9.getString(r10)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c6
        L_0x00af:
            r10 = 2131755358(0x7f10015e, float:1.9141593E38)
            java.lang.String r0 = r9.getString(r10)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c6
        L_0x00b7:
            r10 = 2131755361(0x7f100161, float:1.91416E38)
            java.lang.String r0 = r9.getString(r10)     // Catch:{ Exception -> 0x00c7 }
            goto L_0x00c6
        L_0x00bf:
            r10 = 2131755360(0x7f100160, float:1.9141597E38)
            java.lang.String r0 = r9.getString(r10)     // Catch:{ Exception -> 0x00c7 }
        L_0x00c6:
            return r0
        L_0x00c7:
            r10 = move-exception
            r10.printStackTrace()
            java.lang.String r10 = ""
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.launcher2.ZKLauncher.getFromShortState(int):java.lang.String");
    }

    private synchronized void setAboutRs485() {
        RS485Manager.getInstance(this.mContext).setBaudRate(this.baudRate);
        if (this.rs485fo == 1 && this.readerFunOn == 1 && this.ext485funType == 5) {
            if (!this.mStartRs485) {
                RS485Manager.getInstance(this.mContext).startRS();
                this.mStartRs485 = true;
            }
        } else if (this.mStartRs485) {
            RS485Manager.getInstance(this.mContext).stopRS();
            this.mStartRs485 = false;
        }
    }

    private void sendAuxInState() {
        EventState eventState = new EventState(1);
        eventState.setIOState(this.mReader1IOState);
        if (sAccessRuleType == 0) {
            eventState.setAuInput(0);
        } else {
            eventState.setAuInput(this.mAuxInFunOn);
        }
        EventBusHelper.post(eventState);
    }

    /* access modifiers changed from: private */
    public void setVerifyFun2DB() {
        int intOption = this.dataManager.getIntOption("FaceFunOn", 0);
        int intOption2 = this.dataManager.getIntOption("FingerFunOn", 0);
        int intOption3 = this.dataManager.getIntOption("hasFingerModule", 0);
        int intOption4 = this.dataManager.getIntOption("~RFCardOn", 0);
        int intOption5 = this.dataManager.getIntOption(ZKDBConfig.OPT_FV_FUN, 0);
        boolean z = intOption2 == 1 && intOption3 == 1;
        boolean z2 = intOption == 1;
        boolean z3 = intOption4 == 1;
        boolean z4 = intOption5 == 1;
        byte[] bArr = new byte[10];
        bArr[0] = (byte) (bArr[0] | Ascii.CR);
        if (z4) {
            bArr[2] = (byte) (bArr[2] | 96);
            if (z3) {
                bArr[2] = (byte) (bArr[2] | 128);
                bArr[3] = (byte) (bArr[3] | 1);
            }
        } else if (z) {
            bArr[0] = (byte) (bArr[0] | 2);
            bArr[1] = (byte) (bArr[1] | 35);
            if (z3) {
                bArr[0] = (byte) (bArr[0] | 96);
                bArr[1] = (byte) (bArr[1] | 84);
                if (z2) {
                    bArr[2] = (byte) (bArr[2] | 8);
                }
            }
            if (z2) {
                bArr[2] = (byte) (bArr[2] | 17);
            }
        }
        if (z3) {
            if (z4) {
                bArr[0] = (byte) (bArr[0] | 16);
            } else {
                bArr[0] = (byte) (bArr[0] | 144);
            }
            bArr[1] = (byte) (bArr[1] | 8);
        }
        if (z2) {
            bArr[1] = (byte) (bArr[1] | 128);
            bArr[2] = (byte) (bArr[2] | 2);
            if (z3) {
                bArr[2] = (byte) (bArr[2] | 4);
            }
        }
        this.dataManager.setStrOption(KEY_SET_VERIFY_TYPE, String.format("%02x%02x%02x%02x", new Object[]{Byte.valueOf(bArr[0]), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[2]), Byte.valueOf(bArr[3])}));
    }

    private void attSystemOptionsSanityCheck() {
        try {
            int intOption = this.dataManager.getIntOption("~LockFunOn", 999);
            if (!(1 == intOption || intOption == 0 || 15 == intOption)) {
                this.dataManager.setIntOption("~LockFunOn", 15);
            }
            if (this.dataManager.getIntOption("LogIDFunOn", 999) != 0) {
                this.dataManager.setIntOption("LogIDFunOn", 0);
            }
            if (!"att".equals(this.dataManager.getStrOption("DeviceType", "att"))) {
                this.dataManager.setStrOption("DeviceType", "att");
            }
            if (DeviceManager.getDefault().isH1()) {
                this.dataManager.setIntOption("~LockFunOn", 1);
                if (DBManager.getInstance().getIntOption("IsSupportLocate", 999) == 999) {
                    DBManager.getInstance().setIntOption("IsSupportLocate", 1);
                }
                if (DBManager.getInstance().getIntOption(ZKDBConfig.MOBILE_DATA_FUN, 999) == 999) {
                    DBManager.getInstance().setIntOption(ZKDBConfig.MOBILE_DATA_FUN, 1);
                }
                if (DBManager.getInstance().getIntOption("IsSupportAccess", 999) == 999) {
                    DBManager.getInstance().setIntOption("IsSupportAccess", 0);
                }
            }
        } catch (Exception e) {
            Log.e(SETTING_TAG, "sanity checking failure", e);
        }
    }

    private void accSystemOptionsSanityCheck() {
        try {
            if (15 != this.dataManager.getIntOption("~LockFunOn", 999)) {
                this.dataManager.setIntOption("~LockFunOn", 15);
            }
            if (1 != this.dataManager.getIntOption("LogIDFunOn", 999)) {
                this.dataManager.setIntOption("LogIDFunOn", 1);
            }
            if (!"acc".equals(this.dataManager.getStrOption("DeviceType", "att"))) {
                this.dataManager.setStrOption("DeviceType", "acc");
            }
        } catch (Exception e) {
            Log.e(SETTING_TAG, "sanity checking failure", e);
        }
    }

    private void publicSystemOptionsSanityCheck() {
        int i;
        String str = "~MaxAttLogCount";
        String str2 = "InvalidString";
        String str3 = "~MaxUserCount";
        try {
            String str4 = "PhotoFunOn";
            String str5 = "IMEFunOn";
            if (999 == this.dataManager.getIntOption("ZKlivefaceInit", 999)) {
                this.dataManager.setIntOption("ZKlivefaceInit", 1);
                this.dataManager.setIntOption("~FaceVThr", 56);
                this.dataManager.setIntOption("~FaceMThr", 65);
                this.dataManager.setIntOption(DBConfig.FACE_ENROLL_THRESHOLD, 68);
            }
            if (999 == this.dataManager.getIntOption("~OS", 999)) {
                this.dataManager.setIntOption("~OS", 1);
            }
            if (999 == this.dataManager.getIntOption("MachineTZFunOn", 999)) {
                this.dataManager.setIntOption("MachineTZFunOn", 1);
            }
            if (999 == this.dataManager.getIntOption("~PushFunOn", 999)) {
                this.dataManager.setIntOption("~PushFunOn", 1);
            }
            if (999 == this.dataManager.getIntOption("UserPicURLFunOn", 999)) {
                this.dataManager.setIntOption("UserPicURLFunOn", 1);
            }
            if (999 == this.dataManager.getIntOption("CameraOpen", 999)) {
                this.dataManager.setIntOption("CameraOpen", 1);
            }
            if (999 == this.dataManager.getIntOption("WirelessDHCP", 999)) {
                this.dataManager.setIntOption("WirelessDHCP", 1);
            }
            if (999 == this.dataManager.getIntOption("MaxMQCount", 999)) {
                this.dataManager.setIntOption("MaxMQCount", 10000);
            }
            if (999 == this.dataManager.getIntOption(ZKDBConfig.OPT_PIN2WIDTH, 999)) {
                this.dataManager.setIntOption(ZKDBConfig.OPT_PIN2WIDTH, 14);
            }
            if (999 == this.dataManager.getIntOption("~SSR", 999)) {
                this.dataManager.setIntOption("~SSR", 1);
            }
            if (999 == this.dataManager.getIntOption("IntWGInFunOn", 999)) {
                this.dataManager.setIntOption("IntWGInFunOn", 1);
            }
            if (999 == this.dataManager.getIntOption("AttPhotoForSDK", 999)) {
                this.dataManager.setIntOption("AttPhotoForSDK", 1);
            }
            String str6 = str5;
            if (999 == this.dataManager.getIntOption(str6, 999)) {
                this.dataManager.setIntOption(str6, 1);
            }
            String str7 = str4;
            if (999 == this.dataManager.getIntOption(str7, 999)) {
                this.dataManager.setIntOption(str7, 1);
            }
            String str8 = str3;
            if (999 == this.dataManager.getIntOption(str8, 999)) {
                this.dataManager.setIntOption(str8, 100);
            }
            String str9 = str;
            if (999 == this.dataManager.getIntOption(str9, 999)) {
                this.dataManager.setIntOption(str9, 10);
            }
            if (999 == this.dataManager.getIntOption(DBConfig.ABOUT_MAXFINGERCOUNT, 999)) {
                this.dataManager.setIntOption(DBConfig.ABOUT_MAXFINGERCOUNT, 100);
            }
            if (999 == this.dataManager.getIntOption("~MaxUserPhotoCount", 999)) {
                this.dataManager.setIntOption("~MaxUserPhotoCount", 10000);
            }
            if (999 == this.dataManager.getIntOption("FaceFunOn", 999)) {
                this.dataManager.setIntOption("FaceFunOn", 1);
            }
            if (999 == this.dataManager.getIntOption("~MaxBlackPhotoCount", 999)) {
                this.dataManager.setIntOption("~MaxBlackPhotoCount", 500);
            }
            if (999 == this.dataManager.getIntOption("~MaxBioPhotoCount", 999)) {
                this.dataManager.setIntOption("~MaxBioPhotoCount", 10000);
            }
            if (999 == this.dataManager.getIntOption("maxCaptureCount", 999)) {
                this.dataManager.setIntOption("maxCaptureCount", 5000);
            }
            if (999 == this.dataManager.getIntOption(DBConfig.CLOUD_ICLOCKSVRFUN, 999)) {
                this.dataManager.setIntOption(DBConfig.CLOUD_ICLOCKSVRFUN, 1);
            }
            int i2 = 0;
            if (999 == this.dataManager.getIntOption("Door1ForcePassWord", 999)) {
                this.dataManager.setIntOption("Door1ForcePassWord", 0);
            }
            if (999 == this.dataManager.getIntOption("Door1SupperPassWord", 999)) {
                this.dataManager.setIntOption("Door1SupperPassWord", 0);
            }
            if (999 == this.dataManager.getIntOption("UploadPhoto", 999)) {
                this.dataManager.setIntOption("UploadPhoto", 1);
            }
            if (999 == this.dataManager.getIntOption("UserPicURLFunOn", 999)) {
                this.dataManager.setIntOption("UserPicURLFunOn", 1);
            }
            if (999 == this.dataManager.getIntOption("SubcontractingUpgradeFunOn", 999)) {
                this.dataManager.setIntOption("SubcontractingUpgradeFunOn", 1);
            }
            String str10 = str2;
            if (str10.equals(this.dataManager.getStrOption("~OEMVendor", str10))) {
                this.dataManager.setStrOption("~OEMVendor", "ZKTeco Inc.");
            }
            if (str10.equals(this.dataManager.getStrOption("~DeviceName", str10))) {
                this.dataManager.setStrOption("~DeviceName", "G4-Pro");
            }
            if (999 == this.dataManager.getIntOption(ZKDBConfig.TAMPER_ALARM_FUN, 999)) {
                this.dataManager.setIntOption(ZKDBConfig.TAMPER_ALARM_FUN, 1);
            }
            if (this.dataManager.getIntOption(DBConfig.QRCODE_FUN, 999) == 999) {
                this.dataManager.setIntOption(DBConfig.QRCODE_FUN, 1);
            }
            if (!DeviceManager.getDefault().isG6() || this.dataManager.getIntOption("AccessDeviceSwitchFunOn", 999) != 999) {
                i = 1;
            } else {
                i = 1;
                this.dataManager.setIntOption("AccessDeviceSwitchFunOn", 1);
            }
            if (DeviceManager.getDefault().isH1()) {
                new ZKSharedUtil(this.mContext).putBoolean("IsSupportEthernet", false);
                if (this.dataManager.getIntOption(ZKDBConfig.OPT_IS_SUPPORT_ONLINE_UPGRADE, 999) == 999) {
                    this.dataManager.setIntOption(ZKDBConfig.OPT_IS_SUPPORT_ONLINE_UPGRADE, 0);
                }
            }
            if (this.dataManager.getIntOption(ZKDBConfig.OPT_IS_SUPPORT_LIGHT, 999) == 999) {
                DataManager dataManager2 = this.dataManager;
                if (DeviceManager.getDefault().isG4Pro()) {
                    i2 = i;
                }
                dataManager2.getIntOption(ZKDBConfig.OPT_IS_SUPPORT_LIGHT, i2);
            }
            Log.d(TAG, "deviceType: " + ZKGuideCoreLauncher.deviceType);
        } catch (Exception e) {
            Log.e(SETTING_TAG, "sanity checking failure", e);
        }
    }

    private void setOptionBaseCoreVersionName() {
        try {
            String strOption = this.dataManager.getStrOption("CoreServiceVersionName", "");
            String strOption2 = this.dataManager.getStrOption("BiometricVersion", "0");
            if (strOption2.contains("12.0")) {
                this.dataManager.setStrOption("BiometricVersion", strOption2.replace("12.0", "10.0"));
            }
            if (this.dataManager.getIntOption("~ZKFPVersion", 0) != 10) {
                this.dataManager.setIntOption("~ZKFPVersion", 10);
            }
            if (TextUtils.isEmpty(strOption)) {
                if (this.dataManager.getIntOption("Door1MultiCardOpenDoorFunOn", 0) == 0) {
                    this.dataManager.setIntOption("Door1MultiCardOpenDoorFunOn", 1);
                    this.dataManager.setIntOption("Door1MultiCardOpenDoor", 0);
                }
                if (this.dataManager.getIntOption("~CardByteRevert", 0) == 0) {
                    this.dataManager.setIntOption("~CardByteRevert", 1);
                }
            }
            String coreServiceVersionName = getCoreServiceVersionName();
            if (!strOption.equals(coreServiceVersionName)) {
                this.dataManager.setStrOption("CoreServiceVersionName", coreServiceVersionName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCoreServiceVersionName() {
        String str = "";
        try {
            str = this.mContext.getPackageManager().getPackageInfo("com.zkteco.android.core", 0).versionName;
            Log.d(TAG, "getCoreServiceVersionName: " + str);
            return str;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return str;
        }
    }

    private void getDoorOptionsData() {
        try {
            sLockFunOn = this.dataManager.getIntOption("~LockFunOn", 0);
            sAccessRuleType = this.dataManager.getIntOption("AccessRuleType", 0);
            sDoor1ValidTZ = this.dataManager.getIntOption(ZKDBConfig.OPT_DOOR_1_VALID_TIME_ZONE, 1);
            sDoor1VerifyType = this.dataManager.getIntOption(ZKDBConfig.OPT_DOOR_1_VERIFY_TYPE, 0);
            sIsSupportPull = this.dataManager.getIntOption(ZKDBConfig.OPT_IS_SUPPORT_PULL, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onZKInitializeComplete() {
        initTaskComplete();
        startCheckPushBioPhotoThread();
    }

    private static class IntentReceiver extends ZKReceiver<ZKLauncher> {
        public IntentReceiver(ZKLauncher zKLauncher) {
            super(zKLauncher);
        }

        public void onReceive(ZKLauncher zKLauncher, Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(ZKLauncher.CORE_SERVICE_INIT_ACTION) && zKLauncher.mCacheService != null) {
                zKLauncher.mCacheService.submit(zKLauncher.coreServiceInitRunnable);
            }
        }
    }

    public void onCoreServiceInitBroadcast() {
        this.mIntentReceiver = new IntentReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CORE_SERVICE_INIT_ACTION);
        registerReceiver(this.mIntentReceiver, intentFilter);
    }

    public void onHomePressed(Intent intent) {
        super.onHomePressed(intent);
        if ("homekey".equals(intent.getStringExtra("reason"))) {
            if (ZKVerProcessDialog.getInstance().getActivity() != null) {
                ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            }
            ZKAttRecordDialog zKAttRecordDialog = (ZKAttRecordDialog) getSupportFragmentManager().findFragmentByTag(ZKAttRecordDialog.TAG);
            if (zKAttRecordDialog != null) {
                zKAttRecordDialog.dismiss();
            }
            ZKWorkCodeDialog zKWorkCodeDialog = (ZKWorkCodeDialog) getSupportFragmentManager().findFragmentByTag(ZKWorkCodeDialog.TAG);
            if (zKWorkCodeDialog != null) {
                zKWorkCodeDialog.dismiss();
            }
        }
    }

    public void onUserInteraction() {
        super.onUserInteraction();
    }

    private static class DataManagerRunnable extends ZKRunnable<ZKLauncher> {
        DataManagerRunnable(ZKLauncher zKLauncher) {
            super(zKLauncher);
        }

        public void run(ZKLauncher zKLauncher) {
            if (!zKLauncher.isFinishing()) {
                zKLauncher.getSystemData();
                zKLauncher.setVerifyFun2DB();
                zKLauncher.initAutoConnectEthernet();
            }
        }
    }

    public void initThreadDataManager() {
        ExecutorService executorService = this.mCacheService;
        if (executorService != null) {
            executorService.submit(this.dataManagerRunnable);
        }
    }

    public void initDataSuccess() {
        initThreadDataManager();
        initOpLogReceiver();
    }

    private void initOpLogReceiver() {
        if (DBManager.getInstance().getIntOption("AccessRuleType", -1) == 0) {
            this.opLogReceiver = new OpLogReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(OpLogReceiver.ACTION_BOOT_COMPLETED);
            intentFilter.addAction(OpLogReceiver.ACTION_SHUTDOWN);
            intentFilter.addAction(OpLogReceiver.ACTION_VERIFICATIONFAILED);
            intentFilter.addAction(OpLogReceiver.ACTION_ALARM);
            intentFilter.addAction(OpLogReceiver.ACTION_ENTER_MENU);
            intentFilter.addAction(OpLogReceiver.ACTION_SETUP);
            intentFilter.addAction(OpLogReceiver.ACTION_ADD_FINGER_PRINT);
            intentFilter.addAction(OpLogReceiver.ACTION_SET_PASSWORD);
            intentFilter.addAction(OpLogReceiver.ACTION_SET_HID_CARD);
            intentFilter.addAction(OpLogReceiver.ACTION_DELETE_USER);
            intentFilter.addAction(OpLogReceiver.ACTION_DELETE_FINGER_PRINT);
            intentFilter.addAction(OpLogReceiver.ACTION_DELETE_PASSWORD);
            intentFilter.addAction(OpLogReceiver.ACTION_DELETE_RF_CARD);
            intentFilter.addAction(OpLogReceiver.ACTION_DELETE_DATA);
            intentFilter.addAction(OpLogReceiver.ACTION_CREATE_MF_CARD);
            intentFilter.addAction(OpLogReceiver.ACTION_RECORD_MF_CARD);
            intentFilter.addAction(OpLogReceiver.ACTION_REGISTER_MF_CARD);
            intentFilter.addAction(OpLogReceiver.ACTION_DELETE_MF_CARD);
            intentFilter.addAction(OpLogReceiver.ACTION_CLEAR_MF_CARD);
            intentFilter.addAction(OpLogReceiver.ACTION_DOWNLOAD_DATA_TO_UDISK);
            intentFilter.addAction(OpLogReceiver.ACTION_UPLOAD_DATA_TO_EQUIPMENT);
            intentFilter.addAction(OpLogReceiver.ACTION_SET_DATE);
            intentFilter.addAction(OpLogReceiver.ACTION_RESET);
            intentFilter.addAction(OpLogReceiver.ACTION_DELETE_ATT_RECORD);
            intentFilter.addAction(OpLogReceiver.ACTION_CLEAR_ADMIN_PERMISSIONS);
            intentFilter.addAction(OpLogReceiver.ACTION_MODIFY_DOOR_ACCESS_GROUP);
            intentFilter.addAction(OpLogReceiver.ACTION_MODIFY_USER_ACCESS_GROUP);
            intentFilter.addAction(OpLogReceiver.ACTION_MODIFY_ACCESS_TIME);
            intentFilter.addAction(OpLogReceiver.ACTION_MODIFY_UNLOCKED_GROUP);
            intentFilter.addAction(OpLogReceiver.ACTION_UNLOCKED);
            intentFilter.addAction(OpLogReceiver.ACTION_REGISTER_USER);
            intentFilter.addAction(OpLogReceiver.ACTION_MODIFY_FINGER_PRINT_ATTR);
            intentFilter.addAction(OpLogReceiver.ACTION_STRESS_ALARM);
            registerReceiver(this.opLogReceiver, intentFilter);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openMenu(EventOpenMenu eventOpenMenu) {
        showAllApps();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showNoSuperDialog(EventShowNoSuperDialog eventShowNoSuperDialog) {
        if (this.dialog == null) {
            this.dialog = new ZKConfirmDialog(this);
        }
        if (!this.dialog.isShowing()) {
            this.dialog.show();
            this.dialog.setType(1, "", "", getString(R.string.zk_core_ok));
            this.dialog.setMessage(getString(R.string.no_super_tips));
            this.dialog.setListener(new ZKConfirmDialog.ResultListener() {
                public void cover() {
                }

                public void failure() {
                }

                public void success() {
                    ZKLauncher.this.showAllApps();
                    ZKVerProcessPar.cleanData(14);
                    ZKVerProcessPar.cleanBtnWidget();
                }
            });
        }
    }

    private void initWiegandReceiver() {
        this.wiegandReceiver = new WiegandReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_BIT);
        intentFilter.addAction(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_PULSE_WIDTH);
        intentFilter.addAction(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_PULSE_INTERVAL);
        intentFilter.addAction(WiegandConfig.ACTION_WIEGAND_IN_CHANGE_BIT);
        intentFilter.addAction(WiegandConfig.ACTION_WIEGAND_IN_CHANGE_VERIFY_TYPE);
        intentFilter.addAction(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_VERIFY_TYPE);
        intentFilter.addAction(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_SITE_CODE);
        intentFilter.addAction(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_FAILED_ID);
        registerReceiver(this.wiegandReceiver, intentFilter);
    }

    class NetworkConnectChangedReceiver extends BroadcastReceiver {
        NetworkConnectChangedReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            Parcelable parcelableExtra;
            String action = intent.getAction();
            ZKSharedUtil zKSharedUtil = new ZKSharedUtil(context.getApplicationContext());
            zKSharedUtil.getBoolean(ZKLauncher.KEY_WIFI_CONNECT_STATE_IN, true);
            if (zKSharedUtil.getBoolean(ZKLauncher.KEY_WIFI_CONNECT_STATE_IN, true) && "android.net.wifi.STATE_CHANGE".equals(action) && (parcelableExtra = intent.getParcelableExtra("networkInfo")) != null && ((NetworkInfo) parcelableExtra).getState() == NetworkInfo.State.CONNECTED) {
                zKSharedUtil.putBoolean(ZKLauncher.KEY_WIFI_CONNECT_STATE_IN, false);
                WifiUtils.IpConfig ipConfig = null;
                try {
                    ipConfig = WifiUtils.getConfigFromDB(ZKLauncher.this);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                WifiUtils.connectByConfig(ipConfig);
            }
        }
    }

    private void initNfcReceiver() {
        registerReceiver(this.mNfcReceiver, new IntentFilter(ZkNfcReceiver.ACTION_NFC_CARD));
    }

    private void initStateReceiver() {
        this.stateReceiver = new StateReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zktechnology.android.state.changed");
        intentFilter.addAction("com.zktechnology.android.state.fix.changed");
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("com.zktechnology.android.att.identifier.mode");
        intentFilter.setPriority(100);
        registerReceiver(this.stateReceiver, intentFilter);
    }

    private void initOptionChangeReceiver() {
        this.actionChangeReceiver = new ActionChangeReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zkteco.android.core.ACTION_OPTION_CHANGE");
        registerReceiver(this.actionChangeReceiver, intentFilter);
    }

    public void initHub() {
        FileLogUtils.writeLauncherInitRecord("initHub mCacheService: " + this.mCacheService);
        ExecutorService executorService = this.mCacheService;
        if (executorService != null) {
            executorService.submit(this.coreServiceInitRunnable);
        }
    }

    /* access modifiers changed from: private */
    public void systemOptionsSanityCheck() {
        try {
            int intOption = this.dataManager.getIntOption("AccessRuleType", 999);
            if (999 == intOption) {
                this.dataManager.setIntOption("AccessRuleType", 0);
                attSystemOptionsSanityCheck();
            } else if (intOption == 0) {
                attSystemOptionsSanityCheck();
            } else if (1 == intOption) {
                accSystemOptionsSanityCheck();
            }
            publicSystemOptionsSanityCheck();
            setOptionBaseCoreVersionName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initThreadHub() {
        if (SecretUtil.getPublicKey() != null) {
            System.arraycopy(SecretUtil.getPublicKey(), 0, PUBLIC_KEY, 0, 32);
        }
        if (SecretUtil.getIv() != null) {
            System.arraycopy(SecretUtil.getIv(), 0, iv, 0, 16);
        }
        try {
            initAndConnectHub();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            initPushProtocol();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if ("1".equals(this.dataManager.getStrOption(DBConfig.STAND_ALONE, "1"))) {
            try {
                initStandalone();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        initPushVer();
        FileLogUtils.writeLauncherInitRecord("initThreadhub dismissInitDialogHandler");
        MainThreadExecutor.getInstance().execute(128);
    }

    private static void initPushVer() {
        String strOption = DBManager.getInstance().getStrOption(ZKDBConfig.PUSH_VERSION, "");
        HubProtocolManager hubProtocolManager = null;
        while (TextUtils.isEmpty(strOption)) {
            if (hubProtocolManager == null) {
                hubProtocolManager = new HubProtocolManager(LauncherApplication.getLauncherApplicationContext());
            }
            strOption = hubProtocolManager.getPushVerInfo();
            if (!TextUtils.isEmpty(strOption)) {
                DBManager.getInstance().setStrOption(ZKDBConfig.PUSH_VERSION, strOption);
            } else {
                SystemClock.sleep(100);
            }
        }
    }

    private void initStandalone() {
        new StandaloneProtocolManager(this).standaloneInit();
    }

    private void initPushProtocol() {
        new PushProtocolManager(this).pushInit();
    }

    private HubProtocolManager initAndConnectHub() {
        HubProtocolManager hubProtocolManager = new HubProtocolManager(this);
        hubProtocolManager.hubServiceInit();
        hubProtocolManager.connectHubServer();
        return hubProtocolManager;
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
        OpLogReceiver opLogReceiver2 = this.opLogReceiver;
        if (opLogReceiver2 != null) {
            unregisterReceiver(opLogReceiver2);
        }
        Future<?> future = this.mVerifyTask;
        if (future != null && !future.isCancelled() && !this.mVerifyTask.isDone()) {
            this.mVerifyTask.cancel(true);
        }
        ZKVerController.getInstance().release();
        MainThreadExecutor.getInstance().removeCallback(this.mMainCallback);
        MainThreadExecutor.getInstance().remove((Runnable) null);
        ExecutorService executorService = this.mCacheService;
        if (executorService != null) {
            executorService.shutdown();
            this.dataManagerRunnable = null;
            this.coreServiceInitRunnable = null;
        }
        try {
            unregisterReceiver(this.mIntentReceiver);
            unregisterReceiver(this.actionReceiver);
            unregisterReceiver(this.actionChangeReceiver);
            unregisterReceiver(this.stateReceiver);
            unregisterReceiver(this.wiegandReceiver);
            unregisterReceiver(this.touchChangeStateReceiver);
            unregisterReceiver(this.mNfcReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.stateReceiver.setAttStateListener((AttStateListener) null);
        try {
            turnVisibleLightOff();
            turnInfraredLightOff();
            keyguardOperation(getWindow(), true);
            ZkDoorManager.getInstance(getApplicationContext()).releaseDoorManager();
            ZkAttDoorManager.getInstance(getApplicationContext()).releaseDoorManager();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onQrcodeRead(String str) {
        ZKThreadPool.getInstance().executeTask(new Runnable(str) {
            public final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                ZKLauncher.this.lambda$onQrcodeRead$6$ZKLauncher(this.f$1);
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: verifyQrcode */
    public void lambda$onQrcodeRead$6$ZKLauncher(String str) {
        if (qrcodeFunc == 1) {
            Log.d("qrcode", "getWorkSpaceState() == " + getWorkSpaceState().name());
            Log.d("qrcode", "catch qrcodeStr: " + str);
            Log.d("qrcode", "barcode: " + str);
            if (ZKVerController.getInstance().getState() == ZKVerConState.STATE_WAIT && !ZKVerProcessDialog.getInstance().isAdded()) {
                if (str.startsWith("2#")) {
                    str = str.substring(2);
                }
                try {
                    byte[] decodeBuffer = new BASE64Decoder().decodeBuffer(str);
                    ArrayList arrayList = new ArrayList();
                    try {
                        Cursor queryBySql = DBManager.getInstance().queryBySql("select * from QRCODE_KEY_DATA");
                        while (queryBySql.moveToNext()) {
                            arrayList.add(new Pair(queryBySql.getString(queryBySql.getColumnIndex("QRCodeType")), queryBySql.getString(queryBySql.getColumnIndex("QRCodeKey"))));
                        }
                    } catch (Exception e) {
                        Log.d("qrcode", "Launcher queryBySql(select * from QRCODE_KEY_DATA) exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                    if (arrayList.size() > 0) {
                        String str2 = (String) ((Pair) arrayList.get(0)).second;
                        Log.d("qrcode", "Launcher qrKey: " + str2);
                        String[] split = new String(new AESUtils().decrypt(decodeBuffer, str2)).split("\t");
                        String str3 = split[0];
                        String str4 = split[1];
                        long parseLong = Long.parseLong(split[2]);
                        int parseInt = Integer.parseInt(split[3]);
                        Log.d("qrcode", "Launcher decodeQrcode: pin == " + str3 + "    cardNo == " + str4 + "    createTime == " + parseLong + "    validTime == " + parseInt);
                        long currentTimeMillis = System.currentTimeMillis() / 1000;
                        if (parseLong > currentTimeMillis || currentTimeMillis > parseLong + ((long) parseInt)) {
                            runOnUiThread($$Lambda$ZKLauncher$T2Cif5UseaaTj887CUNy7peF6mU.INSTANCE);
                        } else if (str3.isEmpty() || str4.isEmpty()) {
                            startVerify(ZKVerifyType.PIN.getValue());
                            ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_PIN, str3);
                            ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
                        } else {
                            onRfidRead(str4);
                        }
                    } else {
                        Log.d("qrcode", "Launcher query qrcode key fail, empty data in QRCODE_KEY_DATA.");
                        ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
                        zKVerViewBean.setUiWayLogin(AppUtils.getString(R.string.qrcode_over_valid));
                        zKVerViewBean.setUiType(42);
                        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
                    }
                } catch (Exception e2) {
                    Log.d("qrcode", "Launcher qrcode valid");
                    ZKVerViewBean zKVerViewBean2 = new ZKVerViewBean();
                    zKVerViewBean2.setUiWayLogin(AppUtils.getString(R.string.qrcode_over_valid));
                    zKVerViewBean2.setUiType(42);
                    ZKVerDlgMgt.upDateTopUi(zKVerViewBean2);
                    e2.printStackTrace();
                }
            }
        } else {
            Log.d("qrcode", "Launcher qrcode function is not activated!");
        }
    }

    static /* synthetic */ void lambda$verifyQrcode$7() {
        ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
        zKVerViewBean.setUiWayLogin(AppUtils.getString(R.string.qrcode_expired));
        zKVerViewBean.setUiType(42);
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent != null && keyEvent.getAction() == 0) {
            if (this.stringBuffer == null) {
                this.stringBuffer = new StringBuffer();
            }
            char unicodeChar = (char) keyEvent.getUnicodeChar();
            if (keyEvent.getKeyCode() == 66) {
                Log.d("qrcodeStr", "dispatchKeyEvent: stringBuffer.toString() = " + this.stringBuffer.toString());
                lambda$onQrcodeRead$6$ZKLauncher(this.stringBuffer.toString());
                this.stringBuffer = null;
            } else {
                int keyCode = keyEvent.getKeyCode();
                if (!(keyCode == 59 || keyCode == 115)) {
                    Log.d("qrcodeStr", "stringBuffer.append(pressedKey) = " + unicodeChar);
                    this.stringBuffer.append(unicodeChar);
                }
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }
}
