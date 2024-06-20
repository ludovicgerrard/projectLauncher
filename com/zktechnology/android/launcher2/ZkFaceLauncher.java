package com.zktechnology.android.launcher2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.zxing.Result;
import com.uphoto.liveness.LivenessDetecter;
import com.uphoto.liveness.UlivenessTypes;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.device.camera.CameraPictureInfo;
import com.zktechnology.android.device.camera.CameraViewManager;
import com.zktechnology.android.device.camera.CameraWatchDog;
import com.zktechnology.android.device.camera.CameraWatchDogTask;
import com.zktechnology.android.device.camera.ICameraPictureListener;
import com.zktechnology.android.face.FaceDBUtils;
import com.zktechnology.android.hardware.ScreenLightUtils;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.Launcher;
import com.zktechnology.android.launcher2.ZkFaceLauncher;
import com.zktechnology.android.plug.receiver.EnrollLiveFaceReceiver;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.strategy.BackgroundThreadExecutor;
import com.zktechnology.android.strategy.ICallback;
import com.zktechnology.android.strategy.MainThreadExecutor;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.BitmapHelper;
import com.zktechnology.android.utils.ClickUtils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.DrawFaceUtils;
import com.zktechnology.android.utils.GlobalConfig;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.SaveImage;
import com.zktechnology.android.utils.ZKReceiver;
import com.zktechnology.android.utils.ZKRunnable;
import com.zktechnology.android.utils.ZkBroadcastUtils;
import com.zktechnology.android.utils.ZkG6ShellCMD;
import com.zktechnology.android.verify.utils.ZKConstantConfig;
import com.zktechnology.android.verify.utils.ZKTemperatureUtil;
import com.zkteco.adk.core.task.ZkThreadPoolManager;
import com.zkteco.android.core.sdk.LightManager;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.util.SpeakerHelper;
import com.zkteco.android.employeemgmt.util.VerifyTypeUtils;
import com.zkteco.android.employeemgmt.view.RebootDialog;
import com.zkteco.edk.camera.lib.ZkCameraPreviewCallback;
import com.zkteco.edk.camera.lib.ZkCameraView;
import com.zkteco.liveface562.ZkFaceManager;
import com.zkteco.liveface562.bean.Face;
import com.zkteco.liveface562.bean.IdentifyInfo;
import com.zkteco.liveface562.bean.LivenessResult;
import com.zkteco.liveface562.bean.ZkDetectInfo;
import com.zkteco.liveface562.bean.ZkExtractResult;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmExtractResult;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmService12;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import magic.hu.imageutil.YUVUtils;

public abstract class ZkFaceLauncher extends ZkServiceLauncher {
    public static final String ACTION_ENTER_WORK_MODE = "com.zktechnology.android.launcher.ACTION_ENTER_WORK_MODE";
    public static final String ACTION_TAKE_PICTURE = "com.zktechnology.android.launcher.ACTION_TAKE_PICTURE";
    private static final int ADJUST_TO_THE_AREA = 103;
    public static final String CHANGE_TEMPERATURE_UI = "com.zktechnology.android.launcher.CHANGE_TEMPERATURE_UI";
    private static int DETECT_FACE_COUNT = 1;
    public static int DETECT_FACE_MIN_TIME_INTERVAL = 1000;
    private static final int FACE_STROKE_WIDTH = 10;
    private static final int HIGH_ALARM = 107;
    private static final int MASK_ALARM = 108;
    public static final int MIDDLE = 1;
    private static final int MSG_CLOSE_CAMERA = 10001;
    private static final int MSG_OPEN_CAMERA = 10000;
    private static final int NORMAL_BODY_TEMPERATURE = 104;
    public static int OPEN_VISIBLE_LIGHT_THRESHOLD = 80;
    private static final int PLEASE_MEASURE_THE_TEMPERATURE = 106;
    private static final int PLEASE_WATCH_CAMERA = 109;
    public static final String SHOW_MASK_SOUND = "com.zktechnology.android.launcher.SHOW_MASK_SOUND";
    public static final String SHOW_TEMPERATURE_UI = "com.zktechnology.android.launcher.SHOW_TEMPERATURE_UI";
    public static final int STRONGER = 2;
    /* access modifiers changed from: private */
    public static final String TAG = "ZkFaceLauncher";
    public static final String TAKE_PICTURE_INFO = "TAKE_PICTURE_INFO";
    private static final int TOO_CLOSE = 101;
    private static final int TOO_FAR = 102;
    public static int TURN_OFF_VISIBLE_LIGHT_TIME_OUT = 5000;
    public static final int WEAK = 0;
    public static int WINDOWS_HEIGHT = 0;
    public static int WINDOWS_WIDTH = 0;
    /* access modifiers changed from: private */
    public static CountDownTimer countDownTimer2 = null;
    public static boolean goOn = false;
    public static boolean isDebugFaceLauncher = false;
    public static int mFaceDetectDistanceThreshold = 150;
    public static int mMotionDetectThreshold = 100;
    public static int mRecognizeRange;
    /* access modifiers changed from: private */
    public int cameraImageType;
    private int checkFaceLivenessCount = 0;
    private CountDownTimer countDownTimer1;
    /* access modifiers changed from: private */
    public String data_fm;
    /* access modifiers changed from: private */
    public RebootDialog dialog;
    protected int enableIRTempImage;
    /* access modifiers changed from: private */
    public int enableShowTemp;
    /* access modifiers changed from: private */
    public boolean far = false;
    private FingerClickView fingerClickView;
    private Runnable getDtFmtTask;
    private int imageFPS = 0;
    public FrameLayout irLayout;
    /* access modifiers changed from: private */
    public boolean isDarkLight = false;
    private boolean isDefaultCamera = true;
    private boolean isDetectPalm = false;
    /* access modifiers changed from: private */
    public boolean isFirstConfig = true;
    /* access modifiers changed from: private */
    public AtomicBoolean isInWorkMode = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public AtomicBoolean isInfraredLightOn = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public volatile boolean isInitComplete = false;
    /* access modifiers changed from: private */
    public boolean isNeedPauseMotionDetect = false;
    boolean isOnStop = false;
    /* access modifiers changed from: private */
    public AtomicBoolean isVisibleLightOn = new AtomicBoolean(false);
    protected boolean isworked = false;
    /* access modifiers changed from: private */
    public ImageView ivTemperature;
    private long lastHasFace;
    private long lastLowLightTriggerTime = 0;
    private int lastMotionValue = 0;
    private int lastSound = -1;
    private long lastTime = 0;
    private volatile long lastTimeNoFaceDetected = 0;
    private int lowLightTriggerCount = 0;
    private final ICallback mBackgroundCallback = new ICallback() {
        public final void handleMessage(Message message) {
            ZkFaceLauncher.this.lambda$new$0$ZkFaceLauncher(message);
        }
    };
    /* access modifiers changed from: private */
    public byte[] mCameraNv21ForDetect;
    private ZkCameraView mCameraView;
    /* access modifiers changed from: private */
    public CameraWatchDogTask mColorWatchDog;
    /* access modifiers changed from: private */
    public byte[] mDataCache;
    private int mDefaultWidth = 0;
    private Thread mDetectFaceThread = null;
    /* access modifiers changed from: private */
    public final AtomicBoolean mDetectMask = new AtomicBoolean();
    private EnrollFaceReceiver mEnrollFaceReceiver;
    private final Camera.ErrorCallback mErrorCallback = new Camera.ErrorCallback() {
        public final void onError(int i, Camera camera) {
            ZkFaceLauncher.this.lambda$new$15$ZkFaceLauncher(i, camera);
        }
    };
    private View mFabCamera;
    private Paint mFacePaint;
    private SurfaceHolder mFaceRectHolder;
    private SurfaceView mFaceRectSurface;
    /* access modifiers changed from: private */
    public byte[] mIrCameraNv21ForDetect;
    private ZkCameraView mIrCameraView;
    /* access modifiers changed from: private */
    public final LinkedBlockingQueue<byte[]> mIrNV21ForFaceDetect = new LinkedBlockingQueue<>(1);
    /* access modifiers changed from: private */
    public CameraWatchDogTask mIrWatchDog;
    private final AtomicBoolean mIsStartRestartCamera = new AtomicBoolean(false);
    private byte[] mLastNV21 = null;
    /* access modifiers changed from: private */
    public LightManager mLightManager;
    private final ICallback mMainCallback = new ICallback() {
        public final void handleMessage(Message message) {
            ZkFaceLauncher.this.lambda$new$13$ZkFaceLauncher(message);
        }
    };
    private Thread mMotionDetectThread = null;
    /* access modifiers changed from: private */
    public final LinkedBlockingQueue<byte[]> mNV21ForDetect = new LinkedBlockingQueue<>(1);
    /* access modifiers changed from: private */
    public final LinkedBlockingQueue<byte[]> mNV21ForMotionDetect = new LinkedBlockingQueue<>(1);
    private int mNonWorkModeBrightness = 0;
    protected TextClock mPreviewTime;
    /* access modifiers changed from: private */
    public final ExecutorService mSingleVideo = Executors.newSingleThreadExecutor();
    private FrameLayout mSurfaceRoot;
    private RelativeLayout mSvTitleBar;
    /* access modifiers changed from: private */
    public final LinkedTransferQueue<ICameraPictureListener> mTakePhotoQueue = new LinkedTransferQueue<>();
    /* access modifiers changed from: private */
    public ThreadLocal<ExecutorService> mThreadPool;
    private ExecutorService mTurnLightService = Executors.newSingleThreadExecutor();
    private CameraWatchDog mWatchDog;
    private final CameraWatchDog.IWatchDogCallback mWatchDogCallback = new CameraWatchDog.IWatchDogCallback() {
        public final void onCameraLost(String str) {
            ZkFaceLauncher.this.lambda$new$7$ZkFaceLauncher(str);
        }
    };
    private int mWorkModeBrightness = 100;
    private TextView measureTemperature;
    private boolean needRun = false;
    private final ZkCameraPreviewCallback onColorCallback = new ZkCameraPreviewCallback() {
        public void onPreviewFrame(byte[] bArr, Camera camera, int i, int i2) {
            if (bArr == null) {
                FileLogUtils.writeVerifyLog("onColorRawData: data == null");
                return;
            }
            if (ZkFaceLauncher.this.mColorWatchDog != null) {
                ZkFaceLauncher.this.mColorWatchDog.feedDog();
            }
            ICameraPictureListener iCameraPictureListener = (ICameraPictureListener) ZkFaceLauncher.this.mTakePhotoQueue.poll();
            if (iCameraPictureListener != null) {
                Log.e(ZkFaceLauncher.TAG, "onPreviewFrame: 取出拍照数据");
                if (ZkFaceLauncher.this.mDataCache == null) {
                    byte[] unused = ZkFaceLauncher.this.mDataCache = Arrays.copyOf(bArr, bArr.length);
                } else {
                    System.arraycopy(bArr, 0, ZkFaceLauncher.this.mDataCache, 0, bArr.length);
                }
                ((ExecutorService) ZkFaceLauncher.this.mThreadPool.get()).submit(new SavePhotoTask(ZkFaceLauncher.this.mDataCache, i, i2, iCameraPictureListener.getPath(), iCameraPictureListener));
            }
            if (SystemClock.elapsedRealtime() - ZkFaceLauncher.this.videoLastTime > 50 && bArr.length > 0 && ZKLauncher.videoIntercomFunOn == 1) {
                ZkFaceLauncher.this.sendVideoTask.setData(bArr);
                ZkFaceLauncher.this.mSingleVideo.submit(ZkFaceLauncher.this.sendVideoTask);
                long unused2 = ZkFaceLauncher.this.videoLastTime = SystemClock.elapsedRealtime();
            }
            if (ZkFaceLauncher.this.isInitComplete && ZkFaceLauncher.this.mNV21ForDetect != null && ZkFaceLauncher.this.mNV21ForMotionDetect != null) {
                if (ZkFaceLauncher.this.mCameraNv21ForDetect == null) {
                    byte[] unused3 = ZkFaceLauncher.this.mCameraNv21ForDetect = Arrays.copyOf(bArr, bArr.length);
                } else {
                    System.arraycopy(bArr, 0, ZkFaceLauncher.this.mCameraNv21ForDetect, 0, bArr.length);
                }
                if (ZkFaceLauncher.this.mState == Launcher.State.WORKSPACE) {
                    if (ZkFaceLauncher.this.isInWorkMode.get()) {
                        LogUtils.d(ZkFaceLauncher.TAG, "mNV21ForDetect.offer:");
                        ZkFaceLauncher.this.mNV21ForDetect.offer(ZkFaceLauncher.this.mCameraNv21ForDetect);
                    }
                    ZkFaceLauncher.this.mNV21ForMotionDetect.offer(ZkFaceLauncher.this.mCameraNv21ForDetect);
                }
                if (ZkFaceLauncher.goOn) {
                    YuvImage yuvImage = new YuvImage(bArr, 17, 720, 1280, (int[]) null);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    yuvImage.compressToJpeg(new Rect(0, 0, 720, 1280), 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    new SaveImage().saveBmp(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length), SaveImage.visibleimagePath);
                    ZkFaceLauncher.goOn = false;
                    Intent intent = new Intent();
                    intent.setAction("com.zkteco.android.core.callbackgetImage");
                    ZkFaceLauncher.this.sendBroadcast(intent);
                }
            }
        }
    };
    private final ZkCameraPreviewCallback onGrayCallback = new ZkCameraPreviewCallback() {
        public void onPreviewFrame(byte[] bArr, Camera camera, int i, int i2) {
            if (bArr == null) {
                FileLogUtils.writeVerifyLog("onGrayRawData: data == null");
                return;
            }
            if (ZkFaceLauncher.this.mIrWatchDog != null) {
                ZkFaceLauncher.this.mIrWatchDog.feedDog();
            }
            if (ZkFaceLauncher.this.mIrCameraNv21ForDetect == null) {
                byte[] unused = ZkFaceLauncher.this.mIrCameraNv21ForDetect = Arrays.copyOf(bArr, bArr.length);
            } else {
                System.arraycopy(bArr, 0, ZkFaceLauncher.this.mIrCameraNv21ForDetect, 0, bArr.length);
            }
            if (ZkFaceLauncher.this.mState == Launcher.State.WORKSPACE && ZkFaceLauncher.this.isInWorkMode.get()) {
                ZkFaceLauncher.this.mIrNV21ForFaceDetect.offer(ZkFaceLauncher.this.mIrCameraNv21ForDetect);
            }
        }
    };
    private String passFaceLivenessUserId = null;
    public Rect rectface;
    /* access modifiers changed from: private */
    public final Runnable resetMaskDetectionFlag = new Runnable() {
        public final void run() {
            ZkFaceLauncher.this.lambda$new$16$ZkFaceLauncher();
        }
    };
    private RelativeLayout rlTempterature;
    private boolean sEnalbeIRTempDetection = false;
    private boolean sIRTempDetectionFunOn = false;
    /* access modifiers changed from: private */
    public int sIRTempUnit;
    private ScreenStatusReceiver screenStatusReceiver;
    private ShowAboutTemperature showAboutTemperature;
    private ShowTemperatureReceiver showTemperatureReceiver;
    /* access modifiers changed from: private */
    public long showlasttemp;
    /* access modifiers changed from: private */
    public boolean stopFaceDetect = false;
    /* access modifiers changed from: private */
    public boolean stopMotionDetect = false;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private TakePhotoReceiver takePhotoReceiver;
    private int temperDistance;
    /* access modifiers changed from: private */
    public int temperHigh;
    /* access modifiers changed from: private */
    public boolean termFaceDetectThread = false;
    /* access modifiers changed from: private */
    public boolean termMotionDetectThread = false;
    private TextView testMotionDetectTv;
    private TurnOffVisibleLightRunnable turnOffVisibleLightRunnable = null;
    private TextView tvMask;
    /* access modifiers changed from: private */
    public TextView tvShowTemp;
    /* access modifiers changed from: private */
    public TextView tvTemperature;
    boolean verifyDialogIsShow = false;
    /* access modifiers changed from: private */
    public long videoLastTime;
    private WorkModeChangeReceiver workModeChangeReceiver;

    /* access modifiers changed from: protected */
    public void initDBComplete() {
    }

    /* access modifiers changed from: protected */
    public abstract void onMaskDetected(int i);

    /* access modifiers changed from: protected */
    public abstract void onPalmDetected(ZKPalmExtractResult zKPalmExtractResult);

    /* access modifiers changed from: protected */
    public abstract void onQRCodeDetected(Result result);

    /* access modifiers changed from: protected */
    public abstract void onSingleLiveFaceDetected(byte[] bArr, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract void onSingleLiveFaceRecognize(String str, float f, byte[] bArr, int i);

    private class TurnOffVisibleLightRunnable implements Runnable {
        private boolean killRunnable;

        public TurnOffVisibleLightRunnable() {
            this.killRunnable = false;
            this.killRunnable = false;
        }

        public void killRunnable() {
            this.killRunnable = true;
        }

        public void run() {
            if (!this.killRunnable && !ZkFaceLauncher.this.isFinishing()) {
                try {
                    ZkFaceLauncher.this.mLightManager.setLightState(0, 0);
                    ZkFaceLauncher.this.isVisibleLightOn.set(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private static class WorkModeChangeReceiver extends ZKReceiver<ZkFaceLauncher> {
        public WorkModeChangeReceiver(ZkFaceLauncher zkFaceLauncher) {
            super(zkFaceLauncher);
        }

        public void onReceive(ZkFaceLauncher zkFaceLauncher, Context context, Intent intent) {
            if (ZkFaceLauncher.ACTION_ENTER_WORK_MODE.equals(intent.getAction())) {
                if (zkFaceLauncher.isDarkLight) {
                    zkFaceLauncher.turnVisibleLightOn();
                }
                zkFaceLauncher.enterWorkMode();
            }
        }
    }

    private static class TakePhotoReceiver extends ZKReceiver<ZkFaceLauncher> {
        public TakePhotoReceiver(ZkFaceLauncher zkFaceLauncher) {
            super(zkFaceLauncher);
        }

        public void onReceive(ZkFaceLauncher zkFaceLauncher, Context context, Intent intent) {
            Log.e(ZkFaceLauncher.TAG, "onReceive: 接收拍照广播");
            if (intent != null && ZkFaceLauncher.ACTION_TAKE_PICTURE.equals(intent.getAction())) {
                zkFaceLauncher.mTakePhotoQueue.offer((CameraPictureInfo) intent.getSerializableExtra(ZkFaceLauncher.TAKE_PICTURE_INFO));
            }
        }
    }

    private static class EnrollFaceReceiver extends EnrollLiveFaceReceiver<ZkFaceLauncher> {
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        public EnrollFaceReceiver(ZkFaceLauncher zkFaceLauncher) {
            super(zkFaceLauncher);
        }

        public boolean onEnroll(ZkFaceLauncher zkFaceLauncher, Context context, String str, String str2) {
            if (zkFaceLauncher.isFinishing()) {
                return false;
            }
            LogUtils.d(ZkFaceLauncher.TAG, "onEnroll: userPin = " + str + " photoPath = " + str2);
            this.executorService.execute(new Runnable(str2, str) {
                public final /* synthetic */ String f$0;
                public final /* synthetic */ String f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final void run() {
                    ZkFaceLauncher.EnrollFaceReceiver.lambda$onEnroll$0(this.f$0, this.f$1);
                }
            });
            this.executorService.shutdown();
            return true;
        }

        static /* synthetic */ void lambda$onEnroll$0(String str, String str2) {
            ZkExtractResult zkExtractResult;
            try {
                Bitmap decodeStream = BitmapFactory.decodeStream(new FileInputStream(str));
                if (decodeStream != null) {
                    ZkExtractResult[] zkExtractResultArr = new ZkExtractResult[1];
                    if (ZkFaceManager.getInstance().extractFeature(decodeStream, false, zkExtractResultArr) == 0 && (zkExtractResult = zkExtractResultArr[0]) != null && zkExtractResult.result == 0) {
                        ZkFaceManager.getInstance().dbAdd(str2, zkExtractResult.feature);
                        FaceDBUtils.saveTemplateVL(str2, zkExtractResult.feature, 5, GlobalConfig.FACE_ALGO_MINOR_VER, FaceDBUtils.isFaceAdd(str2));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initData();
        initView();
        initReceiver();
    }

    private void initData() {
        this.mThreadPool = new ThreadLocal<ExecutorService>() {
            /* access modifiers changed from: protected */
            public ExecutorService initialValue() {
                return Executors.newSingleThreadExecutor();
            }
        };
        this.getDtFmtTask = new GetDtFmtTask();
        MainThreadExecutor.getInstance().addCallback(this.mMainCallback);
        iniDistanceAndLightManager();
        this.soundPool = new SoundPool(1, 3, 0);
        this.mTurnLightService.submit(new AboutLight(0, 0));
        this.dialog = new RebootDialog(this) {
            public void tick(String str) {
                ZkFaceLauncher.this.dialog.setMessage(ZkFaceLauncher.this.getString(R.string.face_init_error_restart) + 10 + str);
            }

            public void finish() {
                ZkFaceLauncher.this.dialog.stop();
                ZkFaceLauncher.this.dialog.dismiss();
                AppUtils.recordZkFaceException("初始化再次全部失败，准备重启：");
                AppUtils.rebootSystem(ZkFaceLauncher.this.getApplicationContext(), "人脸识别算法异常：");
            }
        };
        BackgroundThreadExecutor.getInstance().addCallback(this.mBackgroundCallback);
        CameraWatchDog cameraWatchDog = new CameraWatchDog();
        this.mWatchDog = cameraWatchDog;
        cameraWatchDog.start();
    }

    private void initReceiver() {
        EnrollFaceReceiver enrollFaceReceiver = new EnrollFaceReceiver(this);
        this.mEnrollFaceReceiver = enrollFaceReceiver;
        enrollFaceReceiver.registerReceiver(this);
        this.screenStatusReceiver = new ScreenStatusReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("com.zkteco.android.core.getImage");
        registerReceiver(this.screenStatusReceiver, intentFilter);
        this.showTemperatureReceiver = new ShowTemperatureReceiver(this);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(CHANGE_TEMPERATURE_UI);
        intentFilter2.addAction(SHOW_TEMPERATURE_UI);
        intentFilter2.addAction(SHOW_MASK_SOUND);
        registerReceiver(this.showTemperatureReceiver, intentFilter2);
        this.takePhotoReceiver = new TakePhotoReceiver(this);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction(ACTION_TAKE_PICTURE);
        registerReceiver(this.takePhotoReceiver, intentFilter3);
        this.workModeChangeReceiver = new WorkModeChangeReceiver(this);
        registerReceiver(this.workModeChangeReceiver, new IntentFilter(ACTION_ENTER_WORK_MODE));
    }

    public /* synthetic */ void lambda$new$0$ZkFaceLauncher(Message message) {
        if (message.what == MSG_CLOSE_CAMERA) {
            ZkCameraView zkCameraView = (ZkCameraView) message.obj;
            zkCameraView.setPreviewCallback((ZkCameraPreviewCallback) null);
            zkCameraView.closeCamera();
            zkCameraView.releaseResource();
        } else if (message.what == MSG_OPEN_CAMERA) {
            ZkCameraView zkCameraView2 = (ZkCameraView) message.obj;
            zkCameraView2.openCamera();
            if (message.arg1 == 1) {
                try {
                    hookCamera(zkCameraView2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CameraWatchDogTask cameraWatchDogTask = this.mColorWatchDog;
                if (cameraWatchDogTask != null) {
                    cameraWatchDogTask.resume();
                    return;
                }
                return;
            }
            CameraWatchDogTask cameraWatchDogTask2 = this.mIrWatchDog;
            if (cameraWatchDogTask2 != null) {
                cameraWatchDogTask2.resume();
            }
        }
    }

    private void hookCamera(ZkCameraView zkCameraView) throws Exception {
        if (zkCameraView != null) {
            Field declaredField = zkCameraView.getClass().getDeclaredField("mCamera");
            declaredField.setAccessible(true);
            Camera camera = (Camera) declaredField.get(zkCameraView);
            if (camera != null) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setAntibanding(getResources().getStringArray(R.array.antibanding_array)[DBManager.getInstance().getIntOption("Antibanding", 0)]);
                if (DeviceManager.getDefault().isG6()) {
                    parameters.setExposureCompensation(-6);
                }
                camera.setParameters(parameters);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
        this.tvMask.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        this.measureTemperature.setText(R.string.measure_temp);
        if (this.isInitComplete) {
            setSoundId();
            addCameraView(false);
        }
        keyguardOperation(getWindow(), false);
    }

    private void sleepMs(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class DetectFaceThread extends Thread {
        private final WeakReference<ZkFaceLauncher> obj;

        public DetectFaceThread(String str, ZkFaceLauncher zkFaceLauncher) {
            super(str);
            this.obj = new WeakReference<>(zkFaceLauncher);
        }

        /* JADX INFO: finally extract failed */
        /* JADX WARNING: Removed duplicated region for block: B:28:0x00a3 A[SYNTHETIC, Splitter:B:28:0x00a3] */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x00a9 A[Catch:{ Exception -> 0x014e }] */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x00b0  */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x00e1 A[Catch:{ Exception -> 0x0149 }] */
        /* JADX WARNING: Removed duplicated region for block: B:65:0x013b A[Catch:{ Exception -> 0x0149 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r14 = this;
                r0 = -1
                android.os.Process.setThreadPriority(r0)
                java.lang.ref.WeakReference<com.zktechnology.android.launcher2.ZkFaceLauncher> r0 = r14.obj
                java.lang.Object r0 = r0.get()
                com.zktechnology.android.launcher2.ZkFaceLauncher r0 = (com.zktechnology.android.launcher2.ZkFaceLauncher) r0
                if (r0 != 0) goto L_0x000f
                return
            L_0x000f:
                com.uphoto.liveness.UlivenessTypes$UFaceCondfig r1 = new com.uphoto.liveness.UlivenessTypes$UFaceCondfig
                r2 = 3
                r1.<init>(r2)
                com.uphoto.liveness.LivenessDetecter.initFaceDetect(r1)
                com.uphoto.liveness.UlivenessTypes$ULivenessConfig r1 = new com.uphoto.liveness.UlivenessTypes$ULivenessConfig
                r1.<init>(r2)
                com.uphoto.liveness.LivenessDetecter.initLiveDetect(r1)
                r1 = 0
                r7 = 0
                r2 = r1
                r3 = r2
                r4 = r7
            L_0x0025:
                boolean r5 = r0.termFaceDetectThread
                if (r5 != 0) goto L_0x0174
                java.util.concurrent.LinkedBlockingQueue r5 = r0.mNV21ForDetect     // Catch:{ Exception -> 0x015c }
                java.lang.Object r5 = r5.take()     // Catch:{ Exception -> 0x015c }
                byte[] r5 = (byte[]) r5     // Catch:{ Exception -> 0x015c }
                java.lang.String r6 = com.zktechnology.android.launcher2.ZkFaceLauncher.TAG     // Catch:{ Exception -> 0x015c }
                java.lang.String r8 = "vlNv21 = mNV21ForDetect.take()"
                com.zktechnology.android.utils.LogUtils.d(r6, r8)     // Catch:{ Exception -> 0x015c }
                com.zktechnology.android.utils.CanVerifyUtil r6 = com.zktechnology.android.utils.CanVerifyUtil.getInstance()     // Catch:{ Exception -> 0x015c }
                boolean r6 = r6.isCanVerify()     // Catch:{ Exception -> 0x015c }
                if (r6 == 0) goto L_0x0151
                int r6 = r0.cameraImageType     // Catch:{ Exception -> 0x015c }
                r8 = 2
                r9 = 1
                if (r6 != r8) goto L_0x0052
                r6 = r9
                goto L_0x0053
            L_0x0052:
                r6 = r7
            L_0x0053:
                java.lang.String r8 = com.zktechnology.android.launcher2.ZkFaceLauncher.TAG     // Catch:{ Exception -> 0x015c }
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x015c }
                r10.<init>()     // Catch:{ Exception -> 0x015c }
                java.lang.String r11 = "isDoubleCameraOn = "
                java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x015c }
                java.lang.StringBuilder r10 = r10.append(r6)     // Catch:{ Exception -> 0x015c }
                java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x015c }
                com.zktechnology.android.utils.LogUtils.d(r8, r10)     // Catch:{ Exception -> 0x015c }
                if (r6 == 0) goto L_0x009f
                java.util.concurrent.LinkedBlockingQueue r8 = r0.mIrNV21ForFaceDetect     // Catch:{ Exception -> 0x015c }
                r10 = 100
                java.util.concurrent.TimeUnit r12 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Exception -> 0x015c }
                java.lang.Object r8 = r8.poll(r10, r12)     // Catch:{ Exception -> 0x015c }
                byte[] r8 = (byte[]) r8     // Catch:{ Exception -> 0x015c }
                if (r8 != 0) goto L_0x00a0
                int r1 = r4 + 1
                r6 = 10
                if (r4 != r6) goto L_0x0095
                java.lang.String r4 = com.zktechnology.android.launcher2.ZkFaceLauncher.TAG     // Catch:{ Exception -> 0x0091 }
                java.lang.String r6 = "irNv21 is null, set isDoubleCameraOn to false"
                com.zktechnology.android.utils.LogUtils.d(r4, r6)     // Catch:{ Exception -> 0x0091 }
                r10 = r1
                r6 = r7
                goto L_0x00a1
            L_0x0091:
                r4 = move-exception
                r10 = r1
                goto L_0x014f
            L_0x0095:
                java.util.concurrent.LinkedBlockingQueue r4 = r0.mNV21ForDetect
                r4.clear()
                r4 = r1
                r1 = r8
                goto L_0x0025
            L_0x009f:
                r8 = r1
            L_0x00a0:
                r10 = r4
            L_0x00a1:
                if (r3 != 0) goto L_0x00a9
                int r1 = r5.length     // Catch:{ Exception -> 0x014e }
                byte[] r3 = java.util.Arrays.copyOf(r5, r1)     // Catch:{ Exception -> 0x014e }
                goto L_0x00ad
            L_0x00a9:
                int r1 = r5.length     // Catch:{ Exception -> 0x014e }
                java.lang.System.arraycopy(r5, r7, r3, r7, r1)     // Catch:{ Exception -> 0x014e }
            L_0x00ad:
                r11 = r3
                if (r6 == 0) goto L_0x00c2
                if (r2 != 0) goto L_0x00b8
                int r1 = r8.length     // Catch:{ Exception -> 0x00bd }
                byte[] r2 = java.util.Arrays.copyOf(r8, r1)     // Catch:{ Exception -> 0x00bd }
                goto L_0x00c2
            L_0x00b8:
                int r1 = r8.length     // Catch:{ Exception -> 0x00bd }
                java.lang.System.arraycopy(r8, r7, r2, r7, r1)     // Catch:{ Exception -> 0x00bd }
                goto L_0x00c2
            L_0x00bd:
                r4 = move-exception
                r1 = r8
                r3 = r11
                goto L_0x015f
            L_0x00c2:
                r12 = r2
                com.zktechnology.android.device.DeviceManager r1 = com.zktechnology.android.device.DeviceManager.getDefault()     // Catch:{ Exception -> 0x0149 }
                com.zktechnology.android.device.camera.CameraConfig r1 = r1.getCameraConfig()     // Catch:{ Exception -> 0x0149 }
                int r4 = r1.getPreviewWidth()     // Catch:{ Exception -> 0x0149 }
                com.zktechnology.android.device.DeviceManager r1 = com.zktechnology.android.device.DeviceManager.getDefault()     // Catch:{ Exception -> 0x0149 }
                com.zktechnology.android.device.camera.CameraConfig r1 = r1.getCameraConfig()     // Catch:{ Exception -> 0x0149 }
                int r5 = r1.getPreviewHeight()     // Catch:{ Exception -> 0x0149 }
                boolean r1 = r0.stopFaceDetect     // Catch:{ Exception -> 0x0149 }
                if (r1 != 0) goto L_0x013b
                com.zktechnology.android.device.DeviceManager r1 = com.zktechnology.android.device.DeviceManager.getDefault()     // Catch:{ Exception -> 0x0149 }
                boolean r1 = r1.isG6()     // Catch:{ Exception -> 0x0149 }
                if (r1 != 0) goto L_0x00fc
                com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x0149 }
                java.lang.String r2 = "IsSupportFaceAntiFake"
                int r1 = r1.getIntOption(r2, r7)     // Catch:{ Exception -> 0x0149 }
                if (r1 != r9) goto L_0x00f9
                r1 = r9
                goto L_0x00fa
            L_0x00f9:
                r1 = r7
            L_0x00fa:
                r13 = r1
                goto L_0x00fd
            L_0x00fc:
                r13 = r7
            L_0x00fd:
                boolean r1 = r0.isFirstConfig     // Catch:{ Exception -> 0x0149 }
                if (r1 == 0) goto L_0x0126
                boolean unused = r0.isFirstConfig = r7     // Catch:{ Exception -> 0x0149 }
                com.zkteco.liveface562.bean.ZkFaceConfig[] r1 = new com.zkteco.liveface562.bean.ZkFaceConfig[r9]     // Catch:{ Exception -> 0x0149 }
                com.zkteco.liveface562.ZkFaceManager r2 = com.zkteco.liveface562.ZkFaceManager.getInstance()     // Catch:{ Exception -> 0x0149 }
                int r2 = r2.getConfig(r1)     // Catch:{ Exception -> 0x0149 }
                if (r2 != 0) goto L_0x0126
                r1 = r1[r7]     // Catch:{ Exception -> 0x0149 }
                if (r1 == 0) goto L_0x0126
                if (r13 == 0) goto L_0x011b
                if (r6 == 0) goto L_0x011b
                goto L_0x011c
            L_0x011b:
                r9 = r7
            L_0x011c:
                r1.setRgbIrLivenessEnabled(r9)     // Catch:{ Exception -> 0x0149 }
                com.zkteco.liveface562.ZkFaceManager r2 = com.zkteco.liveface562.ZkFaceManager.getInstance()     // Catch:{ Exception -> 0x0149 }
                r2.setConfig(r1)     // Catch:{ Exception -> 0x0149 }
            L_0x0126:
                r1 = r0
                r2 = r12
                r3 = r11
                r6 = r13
                r1.startDetect(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x0149 }
                com.zktechnology.android.device.DeviceManager r1 = com.zktechnology.android.device.DeviceManager.getDefault()     // Catch:{ Exception -> 0x0149 }
                boolean r1 = r1.isSupportPalm()     // Catch:{ Exception -> 0x0149 }
                if (r1 == 0) goto L_0x0144
                r0.startDetectPalm(r12)     // Catch:{ Exception -> 0x0149 }
                goto L_0x0144
            L_0x013b:
                java.lang.String r1 = com.zktechnology.android.launcher2.ZkFaceLauncher.TAG     // Catch:{ Exception -> 0x0149 }
                java.lang.String r2 = "face detect thread is stopped but still getting frame from queue"
                android.util.Log.w(r1, r2)     // Catch:{ Exception -> 0x0149 }
            L_0x0144:
                r1 = r8
                r4 = r10
                r3 = r11
                r2 = r12
                goto L_0x0151
            L_0x0149:
                r4 = move-exception
                r1 = r8
                r3 = r11
                r2 = r12
                goto L_0x015f
            L_0x014e:
                r4 = move-exception
            L_0x014f:
                r1 = r8
                goto L_0x015f
            L_0x0151:
                java.util.concurrent.LinkedBlockingQueue r5 = r0.mNV21ForDetect
                r5.clear()
                goto L_0x0025
            L_0x015a:
                r1 = move-exception
                goto L_0x016c
            L_0x015c:
                r5 = move-exception
                r10 = r4
                r4 = r5
            L_0x015f:
                r4.printStackTrace()     // Catch:{ all -> 0x015a }
                java.util.concurrent.LinkedBlockingQueue r4 = r0.mNV21ForDetect
                r4.clear()
                r4 = r10
                goto L_0x0025
            L_0x016c:
                java.util.concurrent.LinkedBlockingQueue r0 = r0.mNV21ForDetect
                r0.clear()
                throw r1
            L_0x0174:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.launcher2.ZkFaceLauncher.DetectFaceThread.run():void");
        }
    }

    private boolean is3DLiveCheckPass(int i, int i2, byte[] bArr) {
        if (bArr == null) {
            Log.w(TAG, "is3DLiveCheckPass: invalid ir nv21");
            return true;
        }
        UlivenessTypes.UFaceInfo faceInfo = LivenessDetecter.getFaceInfo(new UlivenessTypes.UFaceInput(i, i2, new UlivenessTypes.URect(0, 0, i, i2), bArr, UlivenessTypes.UPixelSize.INPUT_8_BIT));
        if (faceInfo == null) {
            Log.e(TAG, "faceInfo =>>> null");
            return false;
        }
        String str = TAG;
        Log.d(str, "is3DLiveCheckPass:face info,x= " + faceInfo.getFaceRect().getX());
        Log.d(str, "is3DLiveCheckPass:face info,Y= " + faceInfo.getFaceRect().getY());
        Log.d(str, "is3DLiveCheckPass:face info,width= " + faceInfo.getFaceRect().getWidth());
        Log.d(str, "is3DLiveCheckPass:face info,height= " + faceInfo.getFaceRect().getHeight());
        UlivenessTypes.ULivenessInfo livenessInfo = LivenessDetecter.getLivenessInfo(new UlivenessTypes.ULivenessInput(i, i2, new UlivenessTypes.URect(faceInfo.getFaceRect().getX(), faceInfo.getFaceRect().getY(), faceInfo.getFaceRect().getWidth(), faceInfo.getFaceRect().getHeight()), bArr, UlivenessTypes.UPixelSize.INPUT_8_BIT));
        if (livenessInfo == null) {
            Log.e(str, "3D Live check failed!");
            return false;
        }
        Log.d(str, "3D Live check  result = " + livenessInfo.getLiveResult());
        if (livenessInfo.getLiveResult() == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void startDetectPalm(byte[] bArr) {
        if (VerifyTypeUtils.enablePv()) {
            ZKPalmExtractResult extract = ZKPalmService12.extract(bArr);
            String str = TAG;
            Log.d(str, "zkPalmExtractResult.result: " + extract.result);
            Log.d(str, "imageQuality: " + extract.imageQuality);
            Log.d(str, "templateQuality: " + extract.templateQuality);
            int i = 0;
            this.isDetectPalm = extract.result == 0 && extract.imageQuality >= 100 && extract.templateQuality >= 35;
            if (extract.imageQuality > 100) {
                if (extract.templateQuality >= 20 && extract.templateQuality <= 30) {
                    i = ContextCompat.getColor(this, R.color.color_1ddfe9);
                }
                if (extract.templateQuality > 30 && extract.templateQuality < 35) {
                    i = ContextCompat.getColor(this, R.color.color_1ddfe9);
                }
                if (extract.templateQuality >= 35) {
                    i = ContextCompat.getColor(this, R.color.color_1ddfe9);
                }
                this.mFacePaint.setColor(i);
                startDrawPalmRectTask(extract.rect);
            }
            Log.d(str, "isDetectPalm: " + this.isDetectPalm);
            if (this.isDetectPalm) {
                onPalmDetected(extract);
                this.lastTimeNoFaceDetected = 0;
                return;
            }
            startDrawPalmRectTask((int[]) null);
            if (this.lastTimeNoFaceDetected == 0 && this.isInWorkMode.get()) {
                this.lastTimeNoFaceDetected = SystemClock.elapsedRealtime();
            }
        }
    }

    private void startDetectFaceThread() {
        this.stopFaceDetect = false;
        Thread thread = this.mDetectFaceThread;
        if (thread == null || !thread.isAlive()) {
            DetectFaceThread detectFaceThread = new DetectFaceThread("detect_face_thread", this);
            this.mDetectFaceThread = detectFaceThread;
            this.termFaceDetectThread = false;
            detectFaceThread.start();
            Log.w(TAG, "face detection thread is started");
            return;
        }
        Log.i(TAG, "startDetectFaceThread: detect_face_thread is alive");
    }

    private void stopDetectFaceThread() {
        this.stopFaceDetect = true;
    }

    private static class MotionDetectThread extends Thread {
        private WeakReference<ZkFaceLauncher> obj;

        public MotionDetectThread(String str, ZkFaceLauncher zkFaceLauncher) {
            super(str);
            this.obj = new WeakReference<>(zkFaceLauncher);
        }

        public void run() {
            ZkFaceLauncher zkFaceLauncher = (ZkFaceLauncher) this.obj.get();
            if (zkFaceLauncher != null) {
                while (!zkFaceLauncher.termMotionDetectThread) {
                    Launcher.State state = zkFaceLauncher.mState;
                    Launcher.State state2 = Launcher.State.APPS_CUSTOMIZE;
                    try {
                        byte[] bArr = (byte[]) zkFaceLauncher.mNV21ForMotionDetect.take();
                        if (!zkFaceLauncher.stopMotionDetect) {
                            zkFaceLauncher.canEnterWorkMode(bArr, DeviceManager.getDefault().getCameraConfig().getPreviewWidth(), DeviceManager.getDefault().getCameraConfig().getPreviewHeight());
                        } else {
                            Log.w(ZkFaceLauncher.TAG, "motion detect thread is stopped but still getting frame from queue");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void startMotionDetectThread() {
        this.stopMotionDetect = false;
        Thread thread = this.mMotionDetectThread;
        if (thread == null || !thread.isAlive()) {
            MotionDetectThread motionDetectThread = new MotionDetectThread("motion_detect_thread", this);
            this.mMotionDetectThread = motionDetectThread;
            this.termMotionDetectThread = false;
            motionDetectThread.start();
            Log.i(TAG, "motion detection thread started");
            return;
        }
        Log.i(TAG, "motion_detect_thread is already started as alive");
    }

    private void stopMotionDetectThread() {
        this.stopMotionDetect = true;
    }

    private void iniDistanceAndLightManager() {
        this.mLightManager = new LightManager(this);
    }

    private void initView() {
        this.testMotionDetectTv = (TextView) findViewById(R.id.test_motion_tv);
        Paint paint = new Paint(1);
        this.mFacePaint = paint;
        paint.setStrokeWidth(10.0f);
        this.mFacePaint.setStyle(Paint.Style.STROKE);
        this.mFacePaint.setColor(ContextCompat.getColor(this, R.color.color_1ddfe9));
        this.mSvTitleBar = (RelativeLayout) findViewById(R.id.sv_title_bar);
        this.mFabCamera = findViewById(R.id.fab_camera);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.sv_root);
        this.mSurfaceRoot = frameLayout;
        frameLayout.setOnClickListener(this);
        this.mFabCamera.setOnClickListener(this);
        if (getResources().getConfiguration().orientation != 1) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mSurfaceRoot.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            this.mSurfaceRoot.setLayoutParams(layoutParams);
        }
        SurfaceView surfaceView2 = (SurfaceView) findViewById(R.id.sv_face_rect);
        this.mFaceRectSurface = surfaceView2;
        surfaceView2.setZOrderOnTop(true);
        this.rlTempterature = (RelativeLayout) findViewById(R.id.rl_tempterature);
        this.measureTemperature = (TextView) findViewById(R.id.measure_temperature);
        this.ivTemperature = (ImageView) findViewById(R.id.iv_temperature);
        this.tvTemperature = (TextView) findViewById(R.id.tv_temperature);
        TextView textView = (TextView) findViewById(R.id.tv_show_temperature);
        this.tvShowTemp = textView;
        textView.setVisibility(8);
        this.tvMask = (TextView) findViewById(R.id.tv_mask);
        SurfaceHolder holder = this.mFaceRectSurface.getHolder();
        this.mFaceRectHolder = holder;
        holder.setFormat(-2);
        this.mPreviewTime = (TextClock) findViewById(R.id.timer_launcher);
        FingerClickView fingerClickView2 = (FingerClickView) findViewById(R.id.fingerClickView);
        this.fingerClickView = fingerClickView2;
        fingerClickView2.setOnClickListener(this);
        SurfaceView surfaceView3 = (SurfaceView) findViewById(R.id.th_rect);
        this.surfaceView = surfaceView3;
        surfaceView3.setZOrderOnTop(true);
        SurfaceHolder holder2 = this.surfaceView.getHolder();
        this.surfaceHolder = holder2;
        holder2.setFormat(-3);
        this.irLayout = (FrameLayout) findViewById(R.id.final_ir_layout);
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        WINDOWS_WIDTH = point.x;
        WINDOWS_HEIGHT = point.y;
        this.mDefaultWidth = WINDOWS_WIDTH / 3;
    }

    public void showTemperatureRegion(boolean z, boolean z2, int i, int i2, int i3, int i4, int i5, int i6) {
        this.sIRTempDetectionFunOn = z;
        this.sEnalbeIRTempDetection = z2;
        this.sIRTempUnit = i;
        this.temperDistance = i3;
        this.temperHigh = i4;
        this.enableIRTempImage = i6;
        this.enableShowTemp = i5;
        if (!z || !z2) {
            this.rlTempterature.setVisibility(8);
        } else {
            ZkCameraView zkCameraView = this.mCameraView;
            if (zkCameraView != null && zkCameraView.getVisibility() == 0) {
                this.rlTempterature.setVisibility(0);
            }
        }
        if (i6 == 0 || ZKGuideCoreLauncher.deviceType == 0) {
            this.irLayout.setVisibility(8);
        }
    }

    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id != R.id.fab_camera) {
            if (id == R.id.fingerClickView) {
                if (this.isDarkLight) {
                    turnVisibleLightOn();
                }
                enterWorkMode();
            } else if (id == R.id.sv_root && this.isDarkLight) {
                turnVisibleLightOn();
            }
        } else if (ClickUtils.executeClick(view, 3000)) {
            this.lastTimeNoFaceDetected = 0;
            enterWorkMode();
            this.isDefaultCamera = !this.isDefaultCamera;
            addCameraView(true);
        }
    }

    private static class GetDtFmtTask extends ZKRunnable<ZkFaceLauncher> {
        private GetDtFmtTask(ZkFaceLauncher zkFaceLauncher) {
            super(zkFaceLauncher);
        }

        public void run(final ZkFaceLauncher zkFaceLauncher) {
            try {
                String strOption = DBManager.getInstance().getStrOption("DtFmt", "9");
                String unused = zkFaceLauncher.data_fm = null;
                if ("0".equals(strOption)) {
                    String unused2 = zkFaceLauncher.data_fm = ZKConstantConfig.DATE_FM_0;
                }
                if ("1".equals(strOption)) {
                    String unused3 = zkFaceLauncher.data_fm = ZKConstantConfig.DATE_FM_1;
                }
                if ("2".equals(strOption)) {
                    String unused4 = zkFaceLauncher.data_fm = ZKConstantConfig.DATE_FM_2;
                }
                if (ExifInterface.GPS_MEASUREMENT_3D.equals(strOption)) {
                    String unused5 = zkFaceLauncher.data_fm = ZKConstantConfig.DATE_FM_3;
                }
                if ("4".equals(strOption)) {
                    String unused6 = zkFaceLauncher.data_fm = ZKConstantConfig.DATE_FM_4;
                }
                if ("5".equals(strOption)) {
                    String unused7 = zkFaceLauncher.data_fm = ZKConstantConfig.DATE_FM_5;
                }
                if ("6".equals(strOption)) {
                    String unused8 = zkFaceLauncher.data_fm = ZKConstantConfig.DATE_FM_6;
                }
                if ("7".equals(strOption)) {
                    String unused9 = zkFaceLauncher.data_fm = ZKConstantConfig.DATE_FM_7;
                }
                if ("8".equals(strOption)) {
                    String unused10 = zkFaceLauncher.data_fm = ZKConstantConfig.DATE_FM_8;
                }
                if ("9".equals(strOption)) {
                    String unused11 = zkFaceLauncher.data_fm = "yyyy-MM-dd";
                }
                if (zkFaceLauncher.data_fm != null) {
                    zkFaceLauncher.runOnUiThread(new Runnable() {
                        public void run() {
                            zkFaceLauncher.mPreviewTime.setFormat12Hour(zkFaceLauncher.data_fm + " hh:mm aa");
                            zkFaceLauncher.mPreviewTime.setFormat24Hour(zkFaceLauncher.data_fm + " HH:mm");
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        if (this.isInitComplete) {
            setCameraImageType();
            setBrightness();
            runningTask(this.needRun);
        }
        if (this.mState == Launcher.State.WORKSPACE) {
            turnInfraredLightOn();
        }
        startMotionDetectThread();
        enterWorkMode();
        this.isOnStop = false;
    }

    public void runningTask(boolean z) {
        if (z) {
            this.mThreadPool.get().submit(this.getDtFmtTask);
            BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable() {
                public final void run() {
                    ZkFaceLauncher.this.lambda$runningTask$1$ZkFaceLauncher();
                }
            });
            this.needRun = true;
        }
    }

    public /* synthetic */ void lambda$runningTask$1$ZkFaceLauncher() {
        boolean z = false;
        if (DBManager.getInstance().getIntOption("FaceFunOn", 0) == 1) {
            z = true;
        }
        if (z) {
            startDetectFaceThread();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        stopDetectFaceThread();
        stopMotionDetectThread();
        this.isOnStop = true;
        exitWorkMode();
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        BackgroundThreadExecutor.getInstance().removeCallback(this.mBackgroundCallback);
        BackgroundThreadExecutor.getInstance().remove((Runnable) null);
        BackgroundThreadExecutor.getInstance().release();
        this.mWatchDog.stop();
        CameraWatchDogTask cameraWatchDogTask = this.mColorWatchDog;
        if (cameraWatchDogTask != null) {
            cameraWatchDogTask.release();
        }
        CameraWatchDogTask cameraWatchDogTask2 = this.mIrWatchDog;
        if (cameraWatchDogTask2 != null) {
            cameraWatchDogTask2.release();
        }
        this.stopFaceDetect = true;
        this.termFaceDetectThread = true;
        this.termMotionDetectThread = true;
        unregisterReceiver(this.screenStatusReceiver);
        this.screenStatusReceiver = null;
        unregisterReceiver(this.showTemperatureReceiver);
        this.showTemperatureReceiver = null;
        unregisterReceiver(this.takePhotoReceiver);
        this.takePhotoReceiver = null;
        unregisterReceiver(this.workModeChangeReceiver);
        this.workModeChangeReceiver = null;
        MainThreadExecutor.getInstance().removeCallback(this.mMainCallback);
        this.showAboutTemperature = null;
        this.getDtFmtTask = null;
        this.mThreadPool.get().shutdown();
        this.mThreadPool.get().shutdownNow();
        this.mThreadPool.remove();
        this.mThreadPool = null;
        this.fingerClickView.setOnClickListener((View.OnClickListener) null);
        this.mSurfaceRoot.setOnClickListener((View.OnClickListener) null);
        try {
            this.mDetectFaceThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.mMotionDetectThread.interrupt();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        EnrollFaceReceiver enrollFaceReceiver = this.mEnrollFaceReceiver;
        if (enrollFaceReceiver != null) {
            enrollFaceReceiver.unregisterReceiver(this);
            this.mEnrollFaceReceiver = null;
        }
        this.isVisibleLightOn.set(false);
        CountDownTimer countDownTimer = countDownTimer2;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer2 = null;
        }
    }

    private void setCameraImageType() {
        this.cameraImageType = DeviceManager.getDefault().getCameraConfig().getCameraType();
        if (DeviceManager.getDefault().isH1()) {
            this.mFabCamera.setVisibility(0);
        }
        if (this.mColorWatchDog == null) {
            BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable() {
                public final void run() {
                    ZkFaceLauncher.this.lambda$setCameraImageType$3$ZkFaceLauncher();
                }
            });
        }
    }

    public /* synthetic */ void lambda$setCameraImageType$3$ZkFaceLauncher() {
        MainThreadExecutor.getInstance().execute(new Runnable(DBManager.getInstance().getIntOption("CameraWatchDogMaxErrorCount", 3), DBManager.getInstance().getIntOption("CameraWatchDogTimeInterval", 10)) {
            public final /* synthetic */ int f$1;
            public final /* synthetic */ int f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                ZkFaceLauncher.this.lambda$setCameraImageType$2$ZkFaceLauncher(this.f$1, this.f$2);
            }
        });
    }

    public /* synthetic */ void lambda$setCameraImageType$2$ZkFaceLauncher(int i, int i2) {
        CameraWatchDogTask cameraWatchDogTask = new CameraWatchDogTask("LauncherColorCamera", i, i2);
        this.mColorWatchDog = cameraWatchDogTask;
        cameraWatchDogTask.addCallback(this.mWatchDogCallback);
        this.mWatchDog.addTask(this.mColorWatchDog);
        if (this.cameraImageType > 1 && this.mIrWatchDog == null) {
            CameraWatchDogTask cameraWatchDogTask2 = new CameraWatchDogTask("LauncherGreyCamera", i, i2);
            this.mIrWatchDog = cameraWatchDogTask2;
            cameraWatchDogTask2.addCallback(this.mWatchDogCallback);
            this.mWatchDog.addTask(this.mIrWatchDog);
        }
    }

    public /* synthetic */ void lambda$new$7$ZkFaceLauncher(String str) {
        MainThreadExecutor.getInstance().execute(new Runnable() {
            public final void run() {
                ZkFaceLauncher.this.lambda$new$6$ZkFaceLauncher();
            }
        });
    }

    public /* synthetic */ void lambda$new$6$ZkFaceLauncher() {
        ZkThreadPoolManager.getInstance().execute(new Runnable() {
            public final void run() {
                ZkFaceLauncher.this.lambda$new$5$ZkFaceLauncher();
            }
        });
    }

    public /* synthetic */ void lambda$new$5$ZkFaceLauncher() {
        if (this.mIsStartRestartCamera.get()) {
            FileLogUtils.writeCameraLog("Launcher Camera error, restarting now, skip!");
            return;
        }
        this.mIsStartRestartCamera.set(true);
        ZkBroadcastUtils.sendCameraErrorBroadcast(this);
        FileLogUtils.writeCameraLog("Launcher Camera error, start to restart!");
        runOnUiThread(new Runnable() {
            public final void run() {
                ZkFaceLauncher.this.removeCameraView();
            }
        });
        ZkG6ShellCMD.killCameraProcess();
        SystemClock.sleep(6000);
        runOnUiThread(new Runnable() {
            public final void run() {
                ZkFaceLauncher.this.lambda$new$4$ZkFaceLauncher();
            }
        });
    }

    public /* synthetic */ void lambda$new$4$ZkFaceLauncher() {
        try {
            addCameraView(false);
        } catch (Exception e) {
            FileLogUtils.writeCameraLog("Launcher Camera error, restart error, msg-> " + e.getMessage());
        } catch (Throwable th) {
            this.mIsStartRestartCamera.set(false);
            throw th;
        }
        this.mIsStartRestartCamera.set(false);
    }

    private void setBrightness() {
        BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable() {
            public final void run() {
                ZkFaceLauncher.this.lambda$setBrightness$8$ZkFaceLauncher();
            }
        });
    }

    public /* synthetic */ void lambda$setBrightness$8$ZkFaceLauncher() {
        try {
            this.mWorkModeBrightness = DBManager.getInstance().getIntOption(DBConfig.DISPLAY_WORKMODE_BRIGHTNESS, 100);
            this.mNonWorkModeBrightness = DBManager.getInstance().getIntOption(DBConfig.DISPLAY_NON_WORKMODE_BRIGHTNESS, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ScreenLightUtils.getInstance().setNonWorkModeBrightness(this.mNonWorkModeBrightness);
    }

    public /* synthetic */ void lambda$showAllApps$9$ZkFaceLauncher() {
        ScreenLightUtils.getInstance().setScreenBrightness(this.mWorkModeBrightness, "show all app");
    }

    /* access modifiers changed from: package-private */
    public void showAllApps() {
        runOnUiThread(new Runnable() {
            public final void run() {
                ZkFaceLauncher.this.lambda$showAllApps$9$ZkFaceLauncher();
            }
        });
        super.showAllApps();
    }

    /* access modifiers changed from: private */
    public void canEnterWorkMode(byte[] bArr, int i, int i2) {
        boolean z = true;
        if (bArr == null || i == 0 || i2 == 0) {
            String str = TAG;
            StringBuilder append = new StringBuilder().append("canEnterWorkMode: failed,nv21 == null?");
            if (bArr != null) {
                z = false;
            }
            Log.e(str, append.append(z).append(" width =").append(i).append(" height=").append(i2).toString());
            return;
        }
        int i3 = this.imageFPS + 1;
        this.imageFPS = i3;
        if (i3 % 3 == 0) {
            this.imageFPS = 0;
            int yUV420SPLuminance = YUVUtils.getYUV420SPLuminance(bArr, i, i2);
            if (yUV420SPLuminance < OPEN_VISIBLE_LIGHT_THRESHOLD) {
                if (isDebugFaceLauncher) {
                    Log.i(TAG, "ronColorRawData: luminance=" + yUV420SPLuminance);
                }
                if (this.lastLowLightTriggerTime <= 0) {
                    this.lowLightTriggerCount++;
                    this.lastLowLightTriggerTime = SystemClock.elapsedRealtime();
                } else if (SystemClock.elapsedRealtime() - this.lastLowLightTriggerTime < 3000) {
                    this.lowLightTriggerCount++;
                } else {
                    this.lowLightTriggerCount = 0;
                    this.lastLowLightTriggerTime = SystemClock.elapsedRealtime();
                }
                if (this.lowLightTriggerCount >= 14) {
                    this.lowLightTriggerCount = 0;
                    this.lastLowLightTriggerTime = 0;
                    Log.i(TAG, "detect the surround light is low");
                    this.isDarkLight = true;
                }
            } else {
                this.lowLightTriggerCount = 0;
                this.lastLowLightTriggerTime = 0;
                this.isDarkLight = false;
            }
            int motionDetect = YUVUtils.motionDetect(this.mLastNV21, bArr, i, i2);
            int abs = Math.abs(motionDetect - this.lastMotionValue);
            doTestMotionDetect(yUV420SPLuminance, abs);
            if (abs == 0) {
                String str2 = TAG;
                StringBuilder append2 = new StringBuilder().append("canEnterWorkMode: subValue =0,current width is ").append(i).append(", height is").append(i2).append(",mLastNV21 is ").append(this.mLastNV21 == null).append(",nv21 is ");
                if (bArr != null) {
                    z = false;
                }
                Log.w(str2, append2.append(z).toString());
            }
            if (abs > mMotionDetectThreshold && !this.isNeedPauseMotionDetect) {
                if (isDebugFaceLauncher) {
                    Log.i(TAG, "onColorRawData: subValue=" + abs);
                }
                if (this.isDarkLight) {
                    turnVisibleLightOn();
                }
                Message obtain = Message.obtain();
                obtain.what = 4;
                MainThreadExecutor.getInstance().sendMessage(obtain);
            }
            this.lastMotionValue = motionDetect;
            byte[] bArr2 = this.mLastNV21;
            if (bArr2 == null) {
                this.mLastNV21 = Arrays.copyOf(bArr, bArr.length);
            } else {
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initTaskComplete() {
        Log.d(TAG, "initTaskComplete ->setCameraImageType");
        this.isInitComplete = true;
        setCameraImageType();
        addCameraView(false);
        setBrightness();
        enterWorkMode();
    }

    private void doTestMotionDetect(int i, int i2) {
        if (isDebugFaceLauncher) {
            if (this.testMotionDetectTv.getVisibility() != 0) {
                runOnUiThread(new Runnable() {
                    public final void run() {
                        ZkFaceLauncher.this.lambda$doTestMotionDetect$10$ZkFaceLauncher();
                    }
                });
            }
            runOnUiThread(new Runnable(i, i2) {
                public final /* synthetic */ int f$1;
                public final /* synthetic */ int f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run() {
                    ZkFaceLauncher.this.lambda$doTestMotionDetect$11$ZkFaceLauncher(this.f$1, this.f$2);
                }
            });
        } else if (this.testMotionDetectTv.getVisibility() != 8) {
            runOnUiThread(new Runnable() {
                public final void run() {
                    ZkFaceLauncher.this.lambda$doTestMotionDetect$12$ZkFaceLauncher();
                }
            });
        }
    }

    public /* synthetic */ void lambda$doTestMotionDetect$10$ZkFaceLauncher() {
        this.testMotionDetectTv.setVisibility(0);
    }

    public /* synthetic */ void lambda$doTestMotionDetect$11$ZkFaceLauncher(int i, int i2) {
        this.testMotionDetectTv.setText("L:" + i + "\nM:" + i2);
    }

    public /* synthetic */ void lambda$doTestMotionDetect$12$ZkFaceLauncher() {
        this.testMotionDetectTv.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void startDetect(byte[] bArr, byte[] bArr2, int i, int i2, boolean z) {
        int i3;
        ZkDetectInfo[] zkDetectInfoArr;
        int i4;
        int i5;
        boolean z2;
        IdentifyInfo identifyInfo;
        boolean z3;
        byte[] bArr3 = bArr;
        byte[] bArr4 = bArr2;
        int i6 = i;
        int i7 = i2;
        boolean z4 = z;
        if (bArr4 != null && bArr4.length > 0) {
            startDrawFaceRectTask((Rect[]) null, true);
            ZkDetectInfo[] zkDetectInfoArr2 = new ZkDetectInfo[1];
            if (!z4 || !DeviceManager.getDefault().isSupportFaceAntiFake() || bArr3 == null) {
                i3 = ZkFaceManager.getInstance().detectFromNV21(bArr4, i6, i7, zkDetectInfoArr2);
            } else {
                i3 = ZkFaceManager.getInstance().detectFacesFromRGBIR(bArr2, bArr, i, i2, zkDetectInfoArr2);
            }
            if (i3 == 0) {
                ZkDetectInfo zkDetectInfo = zkDetectInfoArr2[0];
                boolean z5 = (zkDetectInfo == null || zkDetectInfo.face == null || zkDetectInfo.face.length <= 0) ? false : true;
                if (z5) {
                    try {
                        sortByFaceRect(zkDetectInfo.face);
                        Face face = zkDetectInfo.face[0];
                        String str = TAG;
                        Log.d(str, String.format("startDetect: faceOccStatus-valid=%s, noseOcc=%s, mouthOcc=%s", new Object[]{Boolean.valueOf(face.faceOccStatus.valid), Boolean.valueOf(face.faceOccStatus.noseOcc), Boolean.valueOf(face.faceOccStatus.mouthOcc)}));
                        boolean z6 = face.faceOccStatus.valid && (face.faceOccStatus.noseOcc || face.faceOccStatus.mouthOcc);
                        Rect rect = face.rect;
                        if (!isInDistance(rect) || !canAccessMultiMachineDetectFaceLimit(rect)) {
                            zkDetectInfoArr = zkDetectInfoArr2;
                            this.checkFaceLivenessCount = 0;
                            i5 = 0;
                        } else {
                            LogUtils.verifyLog("人脸在识别距离内，且开启了[防止多闸机同时识别的功能]");
                            if (isPassFacePoseLimitFor5_6_2(face)) {
                                ArrayList arrayList = new ArrayList();
                                if (ZkFaceManager.getInstance().dbIdentify(zkDetectInfo.message, arrayList) != 0) {
                                    for (Face face2 : zkDetectInfo.face) {
                                        ZkFaceManager.getInstance().resetTrackId(face2.trackId);
                                    }
                                    return;
                                } else if (!arrayList.isEmpty()) {
                                    identifyInfo = (IdentifyInfo) arrayList.get(0);
                                    zkDetectInfoArr = zkDetectInfoArr2;
                                    boolean z7 = DBManager.getInstance().getIntOption(DBConfig.BIOMETRIC_IS_SUPPORT_FACE_ANTI_FAKE, 0) == 1;
                                    if (!DeviceManager.getDefault().isG6() || !z7) {
                                        z3 = isPassFaceLivenessLimitFor5_6_2(arrayList);
                                    } else {
                                        z3 = is3DLiveCheckPass(i6, i7, bArr3);
                                    }
                                    if (z3) {
                                        i5 = z6 ? 2 : 1;
                                        Log.i(str, "[startDetectVlFace] MaskType:" + identifyInfo.respirator);
                                        startDrawFaceRectTask(new Rect[]{rect}, true);
                                        if (this.passFaceLivenessUserId == null) {
                                            this.passFaceLivenessUserId = identifyInfo.pin;
                                        }
                                        if (!identifyInfo.pin.equals(this.passFaceLivenessUserId)) {
                                            this.checkFaceLivenessCount = 0;
                                            this.passFaceLivenessUserId = null;
                                        }
                                        this.checkFaceLivenessCount++;
                                        LogUtils.verifyLog("[startDetectVlFace]: userId = " + identifyInfo.pin + " score = " + identifyInfo.identifyScore + "  threshold=" + ZKLauncher.sVerifyFaceSuccessScore + "; checkFaceLivenessCount = " + this.checkFaceLivenessCount + "; isDoubleCameraOn = " + z4 + "; sFaceAntiFakeRepeatedVerificationTimes = " + ZKLauncher.sFaceAntiFakeRepeatedVerificationTimes);
                                        if (!z4 || this.checkFaceLivenessCount >= ZKLauncher.sFaceAntiFakeRepeatedVerificationTimes) {
                                            this.checkFaceLivenessCount = 0;
                                            if (identifyInfo.identifyScore > ((float) ZKLauncher.sVerifyFaceSuccessScore)) {
                                                onSingleLiveFaceRecognize(identifyInfo.pin, identifyInfo.identifyScore, bArr4, i5);
                                            } else {
                                                onSingleLiveFaceRecognize((String) null, 0.0f, bArr4, i5);
                                            }
                                        }
                                    } else {
                                        this.checkFaceLivenessCount = 0;
                                        i5 = 0;
                                    }
                                    ZkFaceManager.getInstance().resetTrackId(identifyInfo.trackId);
                                } else {
                                    zkDetectInfoArr = zkDetectInfoArr2;
                                    this.checkFaceLivenessCount = 0;
                                    i5 = z6 ? 2 : 1;
                                    try {
                                        int[] iArr = new int[1];
                                        if (ZkFaceManager.getInstance().dbCount(iArr) != 0) {
                                            for (Face face3 : zkDetectInfo.face) {
                                                ZkFaceManager.getInstance().resetTrackId(face3.trackId);
                                            }
                                            return;
                                        } else if (iArr[0] == 0) {
                                            boolean z8 = DBManager.getInstance().getIntOption(DBConfig.BIOMETRIC_IS_SUPPORT_FACE_ANTI_FAKE, 0) == 1;
                                            if (!DeviceManager.getDefault().isG6() || !z8) {
                                                ArrayList arrayList2 = new ArrayList();
                                                if (ZkFaceManager.getInstance().livenessClassify(zkDetectInfo.message, arrayList2) == 0) {
                                                    if (!arrayList2.isEmpty()) {
                                                        float f = ((LivenessResult) arrayList2.get(0)).livenessScore;
                                                        Log.e(str, "isPassFaceLivenessLimit: faceLiveness = " + f);
                                                        z2 = f > ((float) ZKLauncher.mFaceLiveThreshold);
                                                    }
                                                }
                                                for (Face face4 : zkDetectInfo.face) {
                                                    ZkFaceManager.getInstance().resetTrackId(face4.trackId);
                                                }
                                                return;
                                            }
                                            z2 = is3DLiveCheckPass(i6, i7, bArr3);
                                            if (z2) {
                                                startDrawFaceRectTask(new Rect[]{rect}, true);
                                                onSingleLiveFaceRecognize((String) null, 0.0f, bArr4, i5);
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                zkDetectInfoArr = zkDetectInfoArr2;
                                this.checkFaceLivenessCount = 0;
                                i5 = 0;
                            }
                            this.lastTimeNoFaceDetected = 0;
                        }
                        for (Face face5 : zkDetectInfo.face) {
                            ZkFaceManager.getInstance().resetTrackId(face5.trackId);
                        }
                        i4 = i5;
                    } catch (Throwable th) {
                        for (Face face6 : zkDetectInfo.face) {
                            ZkFaceManager.getInstance().resetTrackId(face6.trackId);
                        }
                        throw th;
                    }
                } else {
                    zkDetectInfoArr = zkDetectInfoArr2;
                    startDrawFaceRectTask((Rect[]) null, true);
                    if (this.lastTimeNoFaceDetected == 0 && this.isInWorkMode.get()) {
                        this.lastTimeNoFaceDetected = SystemClock.elapsedRealtime();
                    }
                    i4 = 0;
                }
                if (this.mDetectMask.get() && i4 != 0) {
                    onMaskDetected(i4);
                    this.mDetectMask.set(false);
                    MainThreadExecutor.getInstance().remove(this.resetMaskDetectionFlag);
                }
                if (this.lastTimeNoFaceDetected != 0 && SystemClock.elapsedRealtime() - this.lastTimeNoFaceDetected > ((long) (TURN_OFF_VISIBLE_LIGHT_TIME_OUT - 300))) {
                    Message obtain = Message.obtain();
                    obtain.what = 8;
                    MainThreadExecutor.getInstance().sendMessage(obtain);
                    this.lastTimeNoFaceDetected = 0;
                }
                if ((z5 && isInDistance(zkDetectInfoArr[0].face[0].rect)) || ZKPalmService12.extract(bArr).result == 0) {
                    if (this.mState == Launcher.State.WORKSPACE) {
                        Message obtain2 = Message.obtain();
                        obtain2.what = 4;
                        MainThreadExecutor.getInstance().sendMessage(obtain2);
                        if (this.isDarkLight) {
                            turnVisibleLightOn();
                        }
                    } else {
                        Log.d(TAG, "face detected but not in workspace state");
                    }
                }
                Message obtain3 = Message.obtain();
                obtain3.arg1 = i4;
                obtain3.what = 2;
                MainThreadExecutor.getInstance().sendMessage(obtain3);
            }
        }
    }

    private boolean isInDistance(Rect rect) {
        return rect != null && Math.abs(rect.right - rect.left) >= ZKLauncher.mDetectFaceWidth;
    }

    private void sortByFaceRect(Face[] faceArr) {
        for (int i = 0; i < faceArr.length - 1; i++) {
            int i2 = 0;
            while (i2 < (faceArr.length - 1) - i) {
                int i3 = i2 + 1;
                if (faceArr[i2].rect.right - faceArr[i2].rect.left < faceArr[i3].rect.right - faceArr[i3].rect.left) {
                    Face face = faceArr[i2];
                    faceArr[i2] = faceArr[i3];
                    faceArr[i3] = face;
                }
                i2 = i3;
            }
        }
    }

    private boolean canAccessMultiMachineDetectFaceLimit(Rect rect) {
        if (!ZKLauncher.isMultiMachineDetectFaceLimit) {
            return true;
        }
        int i = mRecognizeRange;
        int i2 = WINDOWS_WIDTH - i;
        if (Math.abs(rect.right - rect.left) <= this.mDefaultWidth || ZKLauncher.isOpenSupportLine) {
            int i3 = ((rect.right - rect.left) / 2) + rect.left;
            if (i3 < i || i3 > i2) {
                return false;
            }
            return true;
        }
        Log.i(TAG, "face is bigger enough,can extract face template");
        return true;
    }

    private boolean isPassFacePoseLimitFor5_6_2(Face face) {
        if (!ZKLauncher.isNeedFacePoseLimit || face == null) {
            return true;
        }
        return Math.abs(face.pose.yaw) <= ((float) ZKLauncher.mFacePoseThreshold) && Math.abs(face.pose.pitch) <= ((float) ZKLauncher.mFacePoseThreshold) && Math.abs(face.pose.roll) <= ((float) ZKLauncher.mFacePoseThreshold);
    }

    private boolean isPassFaceLivenessLimitFor5_6_2(List<IdentifyInfo> list) {
        if (!ZKLauncher.isNeedLiveDetect) {
            return true;
        }
        float f = list.get(0).livenessScore;
        if (f >= ((float) ZKLauncher.mFaceLiveThreshold)) {
            return true;
        }
        Log.e(TAG, "isPassFaceLivenessLimit: faceLiveness = " + f);
        return false;
    }

    private void startDrawPalmRectTask(int[] iArr) {
        ZkCameraView zkCameraView;
        SurfaceHolder surfaceHolder2;
        SurfaceHolder surfaceHolder3;
        SurfaceView surfaceView2 = this.mFaceRectSurface;
        if ((surfaceView2 != null && surfaceView2.getVisibility() != 0) || ((zkCameraView = this.mCameraView) != null && zkCameraView.getVisibility() != 0)) {
            Log.e("ZKPalmLauncher", "face rect surfaceView or preview surfaceView not visible");
        } else if (isFinishing()) {
            Log.e("ZKPalmLauncher", "startDrawPalmRectTask: Current context is collecting");
        } else {
            Canvas canvas = null;
            try {
                Canvas lockCanvas = this.mFaceRectHolder.lockCanvas((Rect) null);
                if (lockCanvas == null) {
                    try {
                        Log.e("ZKPalmLauncher", "startDrawPalmRectTask: mFaceRectHolder get null canvas");
                        SurfaceHolder surfaceHolder4 = this.mFaceRectHolder;
                        if (surfaceHolder4 != null && lockCanvas != null) {
                            surfaceHolder4.unlockCanvasAndPost(lockCanvas);
                        }
                    } catch (Exception e) {
                        e = e;
                        canvas = lockCanvas;
                        try {
                            e.printStackTrace();
                            surfaceHolder3 = this.mFaceRectHolder;
                            if (surfaceHolder3 != null) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            surfaceHolder2 = this.mFaceRectHolder;
                            surfaceHolder2.unlockCanvasAndPost(canvas);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        canvas = lockCanvas;
                        surfaceHolder2 = this.mFaceRectHolder;
                        if (!(surfaceHolder2 == null || canvas == null)) {
                            surfaceHolder2.unlockCanvasAndPost(canvas);
                        }
                        throw th;
                    }
                } else {
                    lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    if (ZKLauncher.isOpenSupportLine) {
                        int height = lockCanvas.getHeight();
                        int i = mRecognizeRange;
                        float f = (float) height;
                        lockCanvas.drawLine((float) i, 0.0f, (float) i, f, this.mFacePaint);
                        int i2 = WINDOWS_WIDTH;
                        int i3 = mRecognizeRange;
                        lockCanvas.drawLine((float) (i2 - i3), 0.0f, (float) (i2 - i3), f, this.mFacePaint);
                    }
                    if (iArr != null) {
                        int sqrt = ((int) Math.sqrt(Math.pow((double) Math.abs(iArr[0] - iArr[2]), 2.0d) + Math.pow((double) Math.abs(iArr[1] - iArr[3]), 2.0d))) / 5;
                        int[] newCoorDinate = newCoorDinate(iArr[2], iArr[3], iArr[0], iArr[1], sqrt);
                        int[] newCoorDinate2 = newCoorDinate(iArr[2], iArr[3], iArr[4], iArr[5], sqrt);
                        int[] newCoorDinate3 = newCoorDinate(iArr[0], iArr[1], iArr[6], iArr[7], sqrt);
                        int[] newCoorDinate4 = newCoorDinate(iArr[6], iArr[7], iArr[4], iArr[5], sqrt);
                        lockCanvas.drawLine((float) iArr[2], (float) iArr[3], (float) newCoorDinate[0], (float) newCoorDinate[1], this.mFacePaint);
                        lockCanvas.drawLine((float) iArr[2], (float) iArr[3], (float) newCoorDinate2[0], (float) newCoorDinate2[1], this.mFacePaint);
                        lockCanvas.drawLine((float) iArr[0], (float) iArr[1], (float) newCoorDinate[2], (float) newCoorDinate[3], this.mFacePaint);
                        lockCanvas.drawLine((float) iArr[0], (float) iArr[1], (float) newCoorDinate3[0], (float) newCoorDinate3[1], this.mFacePaint);
                        lockCanvas.drawLine((float) iArr[4], (float) iArr[5], (float) newCoorDinate2[2], (float) newCoorDinate2[3], this.mFacePaint);
                        lockCanvas.drawLine((float) iArr[4], (float) iArr[5], (float) newCoorDinate4[2], (float) newCoorDinate4[3], this.mFacePaint);
                        lockCanvas.drawLine((float) iArr[6], (float) iArr[7], (float) newCoorDinate3[2], (float) newCoorDinate3[3], this.mFacePaint);
                        lockCanvas.drawLine((float) iArr[6], (float) iArr[7], (float) newCoorDinate4[0], (float) newCoorDinate4[1], this.mFacePaint);
                    } else if (!ZKLauncher.isOpenSupportLine) {
                        lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    }
                    SurfaceHolder surfaceHolder5 = this.mFaceRectHolder;
                    if (surfaceHolder5 != null && lockCanvas != null) {
                        surfaceHolder5.unlockCanvasAndPost(lockCanvas);
                    }
                }
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                surfaceHolder3 = this.mFaceRectHolder;
                if (surfaceHolder3 != null && canvas != null) {
                    surfaceHolder3.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private int[] newCoorDinate(int i, int i2, int i3, int i4, int i5) {
        int i6 = i;
        int i7 = i2;
        int i8 = i3;
        int i9 = i4;
        int i10 = i5;
        int[] iArr = new int[4];
        int abs = Math.abs(i6 - i8);
        int abs2 = Math.abs(i7 - i9);
        if (abs != 0 && abs2 != 0) {
            double d = (double) abs2;
            double d2 = (double) abs;
            double sqrt = Math.sqrt((Math.pow((double) i10, 2.0d) * Math.pow(d, 2.0d)) / (Math.pow(d2, 2.0d) + Math.pow(d, 2.0d)));
            double d3 = (d2 * sqrt) / d;
            if (i6 > i8) {
                if (i7 > i9) {
                    iArr[0] = (int) (((double) i6) - d3);
                    iArr[1] = (int) (((double) i7) - sqrt);
                    iArr[2] = (int) (((double) i8) + d3);
                    iArr[3] = (int) (((double) i9) + sqrt);
                } else {
                    iArr[0] = (int) (((double) i6) - d3);
                    iArr[1] = (int) (((double) i7) + sqrt);
                    iArr[2] = (int) (((double) i8) + d3);
                    iArr[3] = (int) (((double) i9) - sqrt);
                }
            } else if (i7 > i9) {
                iArr[0] = (int) (((double) i6) + d3);
                iArr[1] = (int) (((double) i7) - sqrt);
                iArr[2] = (int) (((double) i8) - d3);
                iArr[3] = (int) (((double) i9) + sqrt);
            } else {
                iArr[0] = (int) (((double) i6) + d3);
                iArr[1] = (int) (((double) i7) + sqrt);
                iArr[2] = (int) (((double) i8) - d3);
                iArr[3] = (int) (((double) i9) - sqrt);
            }
        } else if (abs != 0 || abs2 == 0) {
            if (i6 > i8) {
                iArr[0] = i6 - i10;
                iArr[1] = i7;
                iArr[2] = i8 + i10;
                iArr[3] = i9;
            } else {
                iArr[0] = i6 + i10;
                iArr[1] = i7;
                iArr[2] = i8 - i10;
                iArr[3] = i9;
            }
        } else if (i7 > i9) {
            iArr[0] = i6;
            iArr[1] = i7 - i10;
            iArr[2] = i8;
            iArr[3] = i9 + i10;
        } else {
            iArr[0] = i6;
            iArr[1] = i7 + i10;
            iArr[2] = i8;
            iArr[3] = i9 - i10;
        }
        return iArr;
    }

    private void startDrawFaceRectTask(Rect[] rectArr, boolean z) {
        ZkCameraView zkCameraView;
        SurfaceHolder surfaceHolder2;
        SurfaceHolder surfaceHolder3;
        SurfaceView surfaceView2 = this.mFaceRectSurface;
        if ((surfaceView2 != null && surfaceView2.getVisibility() != 0) || ((zkCameraView = this.mCameraView) != null && zkCameraView.getVisibility() != 0)) {
            Log.e(TAG, "face rect surfaceView or preview surfaceView not visible");
        } else if (isFinishing()) {
            Log.e(TAG, "startDrawFaceRectTask: Current context is collecting");
        } else {
            Canvas canvas = null;
            try {
                Canvas lockCanvas = this.mFaceRectHolder.lockCanvas((Rect) null);
                if (lockCanvas == null) {
                    try {
                        Log.e(TAG, "startDrawFaceRectTask: mFaceRectHolder get null canvas");
                        SurfaceHolder surfaceHolder4 = this.mFaceRectHolder;
                        if (surfaceHolder4 != null && lockCanvas != null) {
                            surfaceHolder4.unlockCanvasAndPost(lockCanvas);
                        }
                    } catch (Exception e) {
                        e = e;
                        canvas = lockCanvas;
                        try {
                            e.printStackTrace();
                            surfaceHolder3 = this.mFaceRectHolder;
                            if (surfaceHolder3 != null) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            surfaceHolder2 = this.mFaceRectHolder;
                            surfaceHolder2.unlockCanvasAndPost(canvas);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        canvas = lockCanvas;
                        surfaceHolder2 = this.mFaceRectHolder;
                        if (!(surfaceHolder2 == null || canvas == null)) {
                            surfaceHolder2.unlockCanvasAndPost(canvas);
                        }
                        throw th;
                    }
                } else {
                    lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    if (ZKLauncher.isOpenSupportLine) {
                        int height = lockCanvas.getHeight();
                        int i = mRecognizeRange;
                        float f = (float) height;
                        lockCanvas.drawLine((float) i, 0.0f, (float) i, f, this.mFacePaint);
                        int i2 = WINDOWS_WIDTH;
                        int i3 = mRecognizeRange;
                        lockCanvas.drawLine((float) (i2 - i3), 0.0f, (float) (i2 - i3), f, this.mFacePaint);
                    }
                    if (rectArr == null) {
                        if (ZKGuideCoreLauncher.deviceType == 1) {
                            drawBullLine((Rect) null);
                        }
                        if (!ZKLauncher.isOpenSupportLine) {
                            lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                        }
                        if (z && Math.abs(this.lastHasFace - SystemClock.elapsedRealtime()) > 1000) {
                            this.showAboutTemperature = new ShowAboutTemperature(this, false, false);
                            MainThreadExecutor.getInstance().execute(this.showAboutTemperature);
                            ZKTemperatureUtil.getInstance(this).isFaceInRange(false);
                            this.far = false;
                        }
                    } else if (Math.abs(this.showlasttemp - SystemClock.elapsedRealtime()) > 800) {
                        LogUtils.verifyLog("startDrawFaceRectTask: 绘制人脸框");
                        this.lastHasFace = SystemClock.elapsedRealtime();
                        for (Rect rect : rectArr) {
                            if (ZKGuideCoreLauncher.deviceType == 1) {
                                drawBullLine(rect);
                            }
                            this.showAboutTemperature = new ShowAboutTemperature(this, false, false);
                            MainThreadExecutor.getInstance().execute(this.showAboutTemperature);
                            if (ZKGuideCoreLauncher.deviceType != 0 || (rect.left >= this.ivTemperature.getLeft() && rect.right <= this.ivTemperature.getRight() && rect.top >= this.ivTemperature.getTop() && rect.bottom <= this.ivTemperature.getBottom())) {
                                this.showAboutTemperature = new ShowAboutTemperature(this, false, true);
                                MainThreadExecutor.getInstance().execute(this.showAboutTemperature);
                                MainThreadExecutor.getInstance().execute(1);
                                if (ZKGuideCoreLauncher.deviceType == 0) {
                                    int i4 = this.temperDistance;
                                    if (i4 != 0) {
                                        if (i4 != 1) {
                                            if (i4 == 2) {
                                                if (Math.abs(rect.right - rect.left) < 280) {
                                                    ZKTemperatureUtil.getInstance(this).isFaceInRange(false);
                                                    this.far = true;
                                                } else {
                                                    ZKTemperatureUtil.getInstance(this).isFaceInRange(true);
                                                    this.far = false;
                                                }
                                            }
                                        } else if (Math.abs(rect.right - rect.left) < 350) {
                                            ZKTemperatureUtil.getInstance(this).isFaceInRange(false);
                                            this.far = true;
                                        } else {
                                            ZKTemperatureUtil.getInstance(this).isFaceInRange(true);
                                            this.far = false;
                                        }
                                    } else if (Math.abs(rect.right - rect.left) < 320) {
                                        ZKTemperatureUtil.getInstance(this).isFaceInRange(false);
                                        this.far = true;
                                    } else {
                                        ZKTemperatureUtil.getInstance(this).isFaceInRange(true);
                                        this.far = false;
                                    }
                                } else {
                                    ZKTemperatureUtil.getInstance(this).isFaceInRange(true);
                                    this.far = false;
                                }
                            } else {
                                this.showAboutTemperature = new ShowAboutTemperature(this, true, false);
                                MainThreadExecutor.getInstance().execute(this.showAboutTemperature);
                                ZKTemperatureUtil.getInstance(this).isFaceInRange(false);
                                this.far = false;
                                if (Math.abs(rect.right - rect.left) > Math.abs(this.ivTemperature.getRight() - this.ivTemperature.getLeft())) {
                                    playSound(101);
                                } else {
                                    playSound(103);
                                }
                            }
                            DrawFaceUtils.drawFaceRect(lockCanvas, rect, this.mFacePaint);
                            if (DETECT_FACE_COUNT == 1) {
                                break;
                            }
                        }
                    }
                    SurfaceHolder surfaceHolder5 = this.mFaceRectHolder;
                    if (surfaceHolder5 != null && lockCanvas != null) {
                        surfaceHolder5.unlockCanvasAndPost(lockCanvas);
                    }
                }
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                surfaceHolder3 = this.mFaceRectHolder;
                if (surfaceHolder3 != null && canvas != null) {
                    surfaceHolder3.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void drawBullLine(Rect rect) {
        SurfaceHolder surfaceHolder2;
        Canvas lockCanvas = this.surfaceHolder.lockCanvas();
        if (lockCanvas != null) {
            try {
                boolean z = false;
                lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                if (rect != null) {
                    this.rectface = CoordinateChange(rect);
                    Paint paint = new Paint();
                    paint.setColor(-16776961);
                    paint.setStrokeWidth(3.0f);
                    if (this.irLayout.getVisibility() == 0) {
                        z = true;
                    }
                    if (this.enableIRTempImage == 1 && z) {
                        lockCanvas.drawLine((float) this.rectface.left, (float) this.rectface.top, (float) this.rectface.left, (float) (this.rectface.top + (this.rectface.bottom - this.rectface.top)), paint);
                        lockCanvas.drawLine((float) this.rectface.right, (float) this.rectface.top, (float) this.rectface.right, (float) (this.rectface.top + (this.rectface.bottom - this.rectface.top)), paint);
                        lockCanvas.drawLine((float) this.rectface.left, (float) this.rectface.bottom, (float) (this.rectface.left + (this.rectface.right - this.rectface.left)), (float) this.rectface.bottom, paint);
                        lockCanvas.drawLine((float) this.rectface.left, (float) this.rectface.top, (float) (this.rectface.left + (this.rectface.right - this.rectface.left)), (float) this.rectface.top, paint);
                    }
                } else {
                    this.rectface = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                surfaceHolder2 = this.surfaceHolder;
                if (surfaceHolder2 == null || lockCanvas == null) {
                    return;
                }
            } catch (Throwable th) {
                SurfaceHolder surfaceHolder3 = this.surfaceHolder;
                if (!(surfaceHolder3 == null || lockCanvas == null)) {
                    surfaceHolder3.unlockCanvasAndPost(lockCanvas);
                }
                throw th;
            }
        }
        surfaceHolder2 = this.surfaceHolder;
        if (surfaceHolder2 == null || lockCanvas == null) {
            return;
        }
        surfaceHolder2.unlockCanvasAndPost(lockCanvas);
    }

    private Rect CoordinateChange(Rect rect) {
        Rect rect2 = new Rect();
        try {
            boolean z = true;
            float[] fArr = {Float.parseFloat(DBManager.getInstance().getStrOption("IRTempTransform0", "0")), Float.parseFloat(DBManager.getInstance().getStrOption("IRTempTransform1", "0")), Float.parseFloat(DBManager.getInstance().getStrOption("IRTempTransform2", "0")), Float.parseFloat(DBManager.getInstance().getStrOption("IRTempTransform3", "0")), Float.parseFloat(DBManager.getInstance().getStrOption("IRTempTransform4", "0")), Float.parseFloat(DBManager.getInstance().getStrOption("IRTempTransform5", "0")), Float.parseFloat(DBManager.getInstance().getStrOption("IRTempTransform6", "0")), Float.parseFloat(DBManager.getInstance().getStrOption("IRTempTransform7", "0")), Float.parseFloat(DBManager.getInstance().getStrOption("IRTempTransform8", "0"))};
            int i = rect.right - rect.left;
            int i2 = rect.bottom - rect.top;
            float f = ((float) rect.left) + ((float) (i / 8));
            float f2 = ((float) rect.top) - ((float) (i2 / 3));
            float f3 = ((float) rect.right) - ((float) (i / 8));
            float f4 = (float) rect.top;
            rect2.left = getNumRect(fArr, f, f2, true);
            rect2.right = getNumRect(fArr, f3, f4, true);
            rect2.top = getNumRect(fArr, f, f2, false);
            rect2.bottom = getNumRect(fArr, f3, f4, false);
            if (i <= 300) {
                z = false;
            }
            float floatValue = ((float) (rect2.bottom - rect2.top)) / new BigDecimal("30").divide(new BigDecimal(Math.abs(60 - new BigDecimal("18000").divide(new BigDecimal(i), 0).intValue())), 0).floatValue();
            if (z) {
                rect2.top = (int) (((float) rect2.top) + floatValue + 1.0f);
                rect2.bottom = (int) (((float) rect2.bottom) + floatValue + 1.0f);
            } else {
                rect2.top = (int) ((((float) rect2.top) - floatValue) + 1.0f);
                rect2.bottom = (int) ((((float) rect2.bottom) - floatValue) + 1.0f);
            }
            return rect2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getNumRect(float[] fArr, float f, float f2, boolean z) {
        BigDecimal bigDecimal;
        if (z) {
            bigDecimal = BigDecimal.valueOf((double) fArr[0]).multiply(new BigDecimal((double) f)).add(BigDecimal.valueOf((double) fArr[1]).multiply(new BigDecimal((double) f2))).add(BigDecimal.valueOf((double) fArr[2]));
        } else {
            bigDecimal = BigDecimal.valueOf((double) fArr[3]).multiply(new BigDecimal((double) f)).add(BigDecimal.valueOf((double) fArr[4]).multiply(new BigDecimal((double) f2))).add(BigDecimal.valueOf((double) fArr[5]));
        }
        return bigDecimal.divide(BigDecimal.valueOf((double) fArr[6]).multiply(new BigDecimal((double) f)).add(BigDecimal.valueOf((double) fArr[7]).multiply(new BigDecimal((double) f2))).add(BigDecimal.valueOf((double) fArr[8])), RoundingMode.UP).multiply(new BigDecimal("1.5")).intValue() + 1;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void playSound(int r10) {
        /*
            r9 = this;
            long r0 = r9.lastTime
            long r2 = android.os.SystemClock.elapsedRealtime()
            long r0 = r0 - r2
            long r0 = java.lang.Math.abs(r0)
            r2 = 2000(0x7d0, double:9.88E-321)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x00cc
            boolean r0 = r9.sIRTempDetectionFunOn
            r1 = -1
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x0084
            boolean r0 = r9.sEnalbeIRTempDetection
            if (r0 == 0) goto L_0x0084
            r0 = 102(0x66, float:1.43E-43)
            if (r10 != r0) goto L_0x002f
            int r0 = r9.soundFar
            if (r0 <= 0) goto L_0x0027
            int r0 = r9.soundFar
            goto L_0x0028
        L_0x0027:
            r0 = r1
        L_0x0028:
            long r4 = android.os.SystemClock.elapsedRealtime()
            r9.lastTime = r4
            goto L_0x0056
        L_0x002f:
            r0 = 101(0x65, float:1.42E-43)
            if (r10 != r0) goto L_0x0042
            int r0 = r9.soundClose
            if (r0 <= 0) goto L_0x003a
            int r0 = r9.soundClose
            goto L_0x003b
        L_0x003a:
            r0 = r1
        L_0x003b:
            long r4 = android.os.SystemClock.elapsedRealtime()
            r9.lastTime = r4
            goto L_0x0056
        L_0x0042:
            r0 = 103(0x67, float:1.44E-43)
            if (r10 != r0) goto L_0x0055
            int r0 = r9.soundArea
            if (r0 <= 0) goto L_0x004d
            int r0 = r9.soundArea
            goto L_0x004e
        L_0x004d:
            r0 = r1
        L_0x004e:
            long r4 = android.os.SystemClock.elapsedRealtime()
            r9.lastTime = r4
            goto L_0x0056
        L_0x0055:
            r0 = r1
        L_0x0056:
            r4 = 104(0x68, float:1.46E-43)
            if (r10 != r4) goto L_0x0067
            int r4 = r9.soundNormal
            if (r4 <= 0) goto L_0x0060
            int r0 = r9.soundNormal
        L_0x0060:
            long r4 = android.os.SystemClock.elapsedRealtime()
            r9.lastTime = r4
            goto L_0x0085
        L_0x0067:
            r4 = 106(0x6a, float:1.49E-43)
            if (r10 != r4) goto L_0x0078
            int r4 = r9.soundMT
            if (r4 <= 0) goto L_0x0071
            int r0 = r9.soundMT
        L_0x0071:
            long r4 = android.os.SystemClock.elapsedRealtime()
            r9.lastTime = r4
            goto L_0x0085
        L_0x0078:
            r4 = 107(0x6b, float:1.5E-43)
            if (r10 != r4) goto L_0x0085
            long r3 = android.os.SystemClock.elapsedRealtime()
            r9.lastTime = r3
            r3 = r2
            goto L_0x0085
        L_0x0084:
            r0 = r1
        L_0x0085:
            int r4 = com.zktechnology.android.launcher2.ZKLauncher.maskDetectionFunOn
            if (r4 != r2) goto L_0x00a9
            int r4 = com.zktechnology.android.launcher2.ZKLauncher.enalbeMaskDetection
            if (r4 != r2) goto L_0x00a9
            r4 = 108(0x6c, float:1.51E-43)
            if (r10 != r4) goto L_0x0098
            long r3 = android.os.SystemClock.elapsedRealtime()
            r9.lastTime = r3
            goto L_0x00aa
        L_0x0098:
            r2 = 109(0x6d, float:1.53E-43)
            if (r10 != r2) goto L_0x00a9
            int r10 = r9.soundMask
            if (r10 <= 0) goto L_0x00a3
            int r10 = r9.soundMask
            r0 = r10
        L_0x00a3:
            long r4 = android.os.SystemClock.elapsedRealtime()
            r9.lastTime = r4
        L_0x00a9:
            r2 = r3
        L_0x00aa:
            if (r2 == 0) goto L_0x00b2
            int r10 = r9.soundid
            if (r10 <= 0) goto L_0x00b2
            int r0 = r9.soundid
        L_0x00b2:
            r3 = r0
            if (r3 == r1) goto L_0x00cc
            android.media.SoundPool r10 = r9.soundPool
            int r0 = r9.lastSound
            r10.stop(r0)
            android.media.SoundPool r2 = r9.soundPool
            r4 = 1065353216(0x3f800000, float:1.0)
            r5 = 1065353216(0x3f800000, float:1.0)
            r6 = 0
            r7 = 0
            r8 = 1065353216(0x3f800000, float:1.0)
            int r10 = r2.play(r3, r4, r5, r6, r7, r8)
            r9.lastSound = r10
        L_0x00cc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.launcher2.ZkFaceLauncher.playSound(int):void");
    }

    /* JADX INFO: finally extract failed */
    private void drawTemperatureRect() {
        try {
            Canvas lockCanvas = this.mFaceRectHolder.lockCanvas((Rect) null);
            if (lockCanvas == null) {
                Log.e(TAG, "drawTemperatureRect: mFaceRectHolder get null canvas");
            }
            SurfaceHolder surfaceHolder2 = this.mFaceRectHolder;
            if (surfaceHolder2 != null && lockCanvas != null) {
                surfaceHolder2.unlockCanvasAndPost(lockCanvas);
            }
        } catch (Throwable th) {
            SurfaceHolder surfaceHolder3 = this.mFaceRectHolder;
            if (!(surfaceHolder3 == null || 0 == 0)) {
                surfaceHolder3.unlockCanvasAndPost((Canvas) null);
            }
            throw th;
        }
    }

    public void showProcessDialog(boolean z) {
        this.verifyDialogIsShow = z;
        if (z) {
            if (this.fingerClickView.getVisibility() == 0) {
                this.fingerClickView.setVisibility(4);
            }
        } else if (this.fingerClickView.getVisibility() == 4) {
            this.fingerClickView.setVisibility(0);
        }
    }

    public void changeWorkSpaceWorkModeVisible(boolean z) {
        this.isworked = z;
        if (z) {
            if (this.fingerClickView.getVisibility() == 0) {
                this.fingerClickView.setVisibility(4);
            }
            if (this.mSurfaceRoot.getVisibility() != 0) {
                this.mSurfaceRoot.setVisibility(0);
            }
            ZkCameraView zkCameraView = this.mCameraView;
            if (!(zkCameraView == null || zkCameraView.getVisibility() == 0)) {
                this.mCameraView.setVisibility(0);
            }
            if (this.mFaceRectSurface.getVisibility() != 0) {
                this.mFaceRectSurface.setVisibility(0);
            }
            if (this.rlTempterature.getVisibility() != 0 && this.sIRTempDetectionFunOn && this.sEnalbeIRTempDetection) {
                this.rlTempterature.setVisibility(0);
            }
            if (this.mSvTitleBar.getVisibility() != 0) {
                this.mSvTitleBar.setVisibility(0);
            }
            if (this.tvTemperature.getVisibility() != 0) {
                this.tvTemperature.setVisibility(0);
                if (ZKGuideCoreLauncher.deviceType == 0) {
                    this.tvTemperature.setText(R.string.please_adjust_your_position);
                } else {
                    this.tvTemperature.setText((CharSequence) null);
                }
            }
            if (this.measureTemperature.getVisibility() != 0 && ZKGuideCoreLauncher.deviceType == 0) {
                this.measureTemperature.setVisibility(0);
                return;
            }
            return;
        }
        if (this.mFaceRectSurface.getVisibility() == 0) {
            this.mFaceRectSurface.setVisibility(4);
        }
        if (this.rlTempterature.getVisibility() == 0) {
            this.rlTempterature.setVisibility(8);
        }
        if (this.tvTemperature.getVisibility() == 0) {
            this.tvTemperature.setVisibility(8);
        }
        if (this.measureTemperature.getVisibility() == 0) {
            this.measureTemperature.setVisibility(8);
        }
        ZkCameraView zkCameraView2 = this.mCameraView;
        if (zkCameraView2 != null && zkCameraView2.getVisibility() == 0) {
            this.mCameraView.setVisibility(4);
        }
        if (this.mSvTitleBar.getVisibility() == 0) {
            this.mSvTitleBar.setVisibility(4);
        }
        if (this.mSurfaceRoot.getVisibility() == 0) {
            this.mSurfaceRoot.setVisibility(4);
        }
        if (this.fingerClickView.getVisibility() == 4 && !this.verifyDialogIsShow) {
            this.fingerClickView.setVisibility(0);
        }
        if (this.verifyDialogIsShow && this.fingerClickView.getVisibility() == 0) {
            this.fingerClickView.setVisibility(4);
        }
        if (this.irLayout.getVisibility() == 0) {
            this.irLayout.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    public void playSoundBi() {
        SpeakerHelper.playSound(this, "beep.ogg", false, "");
    }

    private void playSoundOk() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        language.hashCode();
        char c2 = 65535;
        switch (language.hashCode()) {
            case 3241:
                if (language.equals("en")) {
                    c2 = 0;
                    break;
                }
                break;
            case 3246:
                if (language.equals("es")) {
                    c2 = 1;
                    break;
                }
                break;
            case 3259:
                if (language.equals("fa")) {
                    c2 = 2;
                    break;
                }
                break;
            case 3276:
                if (language.equals("fr")) {
                    c2 = 3;
                    break;
                }
                break;
            case 3710:
                if (language.equals("tr")) {
                    c2 = 4;
                    break;
                }
                break;
            case 3886:
                if (language.equals("zh")) {
                    c2 = 5;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                SpeakerHelper.playSound(this, "0.ogg", true, "EN");
                return;
            case 1:
                if ("MX".equals(locale.getCountry())) {
                    SpeakerHelper.playSound(this.mContext, "0.ogg", true, "ES-MX");
                    return;
                }
                return;
            case 2:
                SpeakerHelper.playSound(this.mContext, "0.ogg", true, "FA");
                return;
            case 3:
                SpeakerHelper.playSound(this.mContext, "0.ogg", true, "FR");
                return;
            case 4:
                SpeakerHelper.playSound(this, "0.ogg", true, "TR");
                return;
            case 5:
                SpeakerHelper.playSound(this, "0.ogg", true, "CH");
                return;
            default:
                SpeakerHelper.playSound(this, "0.ogg", true, "EN");
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void turnVisibleLightOn() {
        enableTurnOffVisibleLightTimeout();
        if (!this.isVisibleLightOn.get() && !isFinishing()) {
            this.mTurnLightService.submit(new AboutLight(0, 1));
        }
    }

    /* access modifiers changed from: protected */
    public void turnVisibleLightOff() {
        if (this.isVisibleLightOn.get() && !isFinishing()) {
            this.mTurnLightService.submit(new AboutLight(0, 0));
            disableTurnOffVisibleLightTimeout();
        }
    }

    /* access modifiers changed from: protected */
    public void turnInfraredLightOn() {
        if (!this.isInfraredLightOn.get() && !isFinishing()) {
            this.mTurnLightService.submit(new AboutLight(1, 1));
        }
    }

    /* access modifiers changed from: protected */
    public void turnInfraredLightOff() {
        if (this.isInfraredLightOn.get() && !isFinishing()) {
            this.mTurnLightService.submit(new AboutLight(1, 0));
        }
    }

    private void disableTurnOffVisibleLightTimeout() {
        TurnOffVisibleLightRunnable turnOffVisibleLightRunnable2 = this.turnOffVisibleLightRunnable;
        if (turnOffVisibleLightRunnable2 != null) {
            turnOffVisibleLightRunnable2.killRunnable();
            BackgroundThreadExecutor.getInstance().remove(this.turnOffVisibleLightRunnable);
        }
    }

    private void enableTurnOffVisibleLightTimeout() {
        disableTurnOffVisibleLightTimeout();
        this.turnOffVisibleLightRunnable = null;
        this.turnOffVisibleLightRunnable = new TurnOffVisibleLightRunnable();
        BackgroundThreadExecutor.getInstance().executeDelayed(this.turnOffVisibleLightRunnable, (long) TURN_OFF_VISIBLE_LIGHT_TIME_OUT);
    }

    /* access modifiers changed from: private */
    public void enterWorkMode() {
        if (!this.isInitComplete) {
            Log.e(TAG, "enterWorkMode: failed , not init complete");
        } else if (!this.isInWorkMode.get()) {
            Log.i(TAG, "enterWorkMode: success");
            this.isInWorkMode.set(true);
            if (!isFinishing()) {
                if (this.cameraImageType == 2) {
                    turnInfraredLightOn();
                } else {
                    turnInfraredLightOff();
                }
                ScreenLightUtils.getInstance().setScreenBrightness(this.mWorkModeBrightness, "enter work mode");
                changeWorkSpaceWorkModeVisible(true);
                keyguardOperation(getWindow(), false);
            }
        } else if (isDebugFaceLauncher) {
            Log.e(TAG, "enterWorkMode: failed ,already in work mode");
        }
    }

    private void exitWorkMode() {
        if (!this.isInWorkMode.get()) {
            Log.e(TAG, "exitWorkMode: failed ,not in work mode");
            return;
        }
        Log.i(TAG, "exitWorkMode:success");
        this.isInWorkMode.set(false);
        if (!isFinishing()) {
            this.lastTimeNoFaceDetected = 0;
            changeWorkSpaceWorkModeVisible(false);
            keyguardOperation(getWindow(), true);
            turnVisibleLightOff();
            turnInfraredLightOff();
            if (!this.isOnStop) {
                ScreenLightUtils.getInstance().setScreenBrightness(this.mNonWorkModeBrightness, "exit work mode");
            }
            this.isNeedPauseMotionDetect = true;
            CountDownTimer countDownTimer = countDownTimer2;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer2 = null;
            }
            runOnUiThread(new CountdownTime2Runnable(this));
        }
    }

    private static class CountdownTime2Runnable extends ZKRunnable<ZkFaceLauncher> {
        public CountdownTime2Runnable(ZkFaceLauncher zkFaceLauncher) {
            super(zkFaceLauncher);
        }

        public void run(ZkFaceLauncher zkFaceLauncher) {
            final ZkFaceLauncher zkFaceLauncher2 = zkFaceLauncher;
            CountDownTimer unused = ZkFaceLauncher.countDownTimer2 = new CountDownTimer(2500, 1000) {
                public void onTick(long j) {
                    Log.i(ZkFaceLauncher.TAG, j + "s can turn on the light");
                }

                public void onFinish() {
                    boolean unused = zkFaceLauncher2.isNeedPauseMotionDetect = false;
                    ZkFaceLauncher.countDownTimer2.cancel();
                }
            };
            ZkFaceLauncher.countDownTimer2.start();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        this.showAboutTemperature = new ShowAboutTemperature(this, false, false);
        MainThreadExecutor.getInstance().execute(this.showAboutTemperature);
        ZKTemperatureUtil.getInstance(this).isFaceInRange(false);
        this.far = false;
        removeCameraView();
        this.isFirstConfig = true;
    }

    private static class ShowAboutTemperature implements Runnable {
        private boolean error;
        private boolean isIn;
        private WeakReference<ZkFaceLauncher> wrf = null;

        public ShowAboutTemperature(ZkFaceLauncher zkFaceLauncher, boolean z, boolean z2) {
            this.wrf = new WeakReference<>(zkFaceLauncher);
            this.error = z;
            this.isIn = z2;
        }

        public void run() {
            ZkFaceLauncher zkFaceLauncher = (ZkFaceLauncher) this.wrf.get();
            if (this.error) {
                zkFaceLauncher.ivTemperature.setImageResource(R.drawable.temperature_error);
                if (ZKGuideCoreLauncher.deviceType == 0) {
                    zkFaceLauncher.tvTemperature.setText(R.string.please_adjust_your_position);
                } else {
                    zkFaceLauncher.tvTemperature.setText((CharSequence) null);
                }
                zkFaceLauncher.tvTemperature.setTextColor(ContextCompat.getColor(zkFaceLauncher, R.color.launcher_clr_ff0000));
                return;
            }
            if (zkFaceLauncher.far) {
                zkFaceLauncher.playSound(102);
                zkFaceLauncher.ivTemperature.setImageResource(R.drawable.temperature_error);
                zkFaceLauncher.tvTemperature.setText(R.string.please_move_a_bit_forward);
            } else {
                zkFaceLauncher.ivTemperature.setImageResource(R.drawable.temperature_right);
                if (this.isIn) {
                    if (!zkFaceLauncher.tvTemperature.getText().toString().equals(zkFaceLauncher.getResources().getString(R.string.high_body_temperature)) && !zkFaceLauncher.tvTemperature.getText().toString().equals(zkFaceLauncher.getResources().getString(R.string.normal_temperature))) {
                        zkFaceLauncher.tvTemperature.setText("");
                    }
                } else if (!zkFaceLauncher.tvTemperature.getText().toString().equals(zkFaceLauncher.getResources().getString(R.string.please_measure_your_temperature))) {
                    if (ZKGuideCoreLauncher.deviceType == 0) {
                        zkFaceLauncher.tvTemperature.setText(R.string.please_adjust_your_position);
                    } else {
                        zkFaceLauncher.tvTemperature.setText((CharSequence) null);
                    }
                    zkFaceLauncher.tvShowTemp.setVisibility(8);
                }
            }
            if (zkFaceLauncher.tvTemperature.getText().toString().equals(zkFaceLauncher.getResources().getString(R.string.high_body_temperature)) || zkFaceLauncher.tvTemperature.getText().toString().equals(zkFaceLauncher.getResources().getString(R.string.please_move_a_bit_forward))) {
                zkFaceLauncher.tvTemperature.setTextColor(ContextCompat.getColor(zkFaceLauncher, R.color.launcher_clr_ff0000));
            } else {
                zkFaceLauncher.tvTemperature.setTextColor(ContextCompat.getColor(zkFaceLauncher, 17170443));
            }
        }
    }

    private static class SavePhotoTask implements Runnable {
        ICameraPictureListener cpl;
        private byte[] data;
        int height;
        private String path;
        int width;

        public SavePhotoTask(byte[] bArr, int i, int i2, String str, ICameraPictureListener iCameraPictureListener) {
            this.data = bArr;
            this.width = i;
            this.height = i2;
            this.path = str;
            this.cpl = iCameraPictureListener;
        }

        public void run() {
            Bitmap nv21ToBitmap = BitmapHelper.nv21ToBitmap(this.data, this.width, this.height);
            if (nv21ToBitmap != null) {
                BitmapHelper.writeBitmap(this.path, nv21ToBitmap, 30);
                nv21ToBitmap.recycle();
                ICameraPictureListener iCameraPictureListener = this.cpl;
                if (iCameraPictureListener != null) {
                    iCameraPictureListener.onPictureAndSaveFinish();
                    this.cpl = null;
                }
            }
        }
    }

    public /* synthetic */ void lambda$new$13$ZkFaceLauncher(Message message) {
        int i = message.what;
        if (i != 1) {
            if (i == 2) {
                int i2 = message.arg1;
                if (ZKLauncher.maskDetectionFunOn == 0 || ZKLauncher.enalbeMaskDetection == 0) {
                    this.tvMask.setVisibility(8);
                } else if (i2 == 2) {
                    this.tvMask.setVisibility(0);
                    this.tvMask.setTextColor(ContextCompat.getColor(this, 17170443));
                    this.tvMask.setText(R.string.mask_detected);
                } else if (i2 == 1) {
                    this.tvMask.setVisibility(0);
                    this.tvMask.setTextColor(ContextCompat.getColor(this, R.color.launcher_clr_ff0000));
                    this.tvMask.setText(R.string.without_mask);
                } else {
                    this.tvMask.setVisibility(8);
                }
            } else if (i == 4) {
                enterWorkMode();
            } else if (i == 8) {
                exitWorkMode();
            }
        } else if (this.tvShowTemp.getVisibility() != 0) {
            this.tvShowTemp.setVisibility(0);
            this.tvShowTemp.setTextColor(ContextCompat.getColor(this, 17170443));
            this.tvShowTemp.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            this.tvShowTemp.setText(getResources().getString(R.string.temp) + ":" + getResources().getString(R.string.measuring));
        }
    }

    public void removeCameraView() {
        FileLogUtils.writeCameraLog("Launcher start remove camera view");
        releaseIrCameraView();
        releaseColorCameraView();
        CameraWatchDogTask cameraWatchDogTask = this.mColorWatchDog;
        if (cameraWatchDogTask != null) {
            cameraWatchDogTask.pause();
        }
        CameraWatchDogTask cameraWatchDogTask2 = this.mIrWatchDog;
        if (cameraWatchDogTask2 != null) {
            cameraWatchDogTask2.pause();
        }
    }

    private void releaseColorCameraView() {
        ZkCameraView zkCameraView = this.mCameraView;
        if (zkCameraView != null) {
            Message obtain = Message.obtain();
            obtain.what = MSG_CLOSE_CAMERA;
            obtain.obj = zkCameraView;
            BackgroundThreadExecutor.getInstance().sendMessage(obtain);
            FrameLayout frameLayout = this.mSurfaceRoot;
            if (frameLayout != null) {
                frameLayout.removeView(this.mCameraView);
            }
            this.mCameraView = null;
        }
    }

    private void releaseIrCameraView() {
        ZkCameraView zkCameraView = this.mIrCameraView;
        if (zkCameraView != null) {
            Message obtain = Message.obtain();
            obtain.what = MSG_CLOSE_CAMERA;
            obtain.obj = zkCameraView;
            BackgroundThreadExecutor.getInstance().sendMessage(obtain);
            this.mIrCameraView = null;
        }
    }

    public void addCameraView(boolean z) {
        FileLogUtils.writeCameraLog("Launcher start add camera view, isForce:" + z);
        if (this.mSurfaceRoot.indexOfChild(this.mCameraView) == -1 || z) {
            removeCameraView();
            if (this.cameraImageType > 1) {
                if (this.mIrCameraView == null) {
                    this.mIrCameraView = CameraViewManager.createSecondary(this);
                }
                ZkCameraView zkCameraView = this.mIrCameraView;
                if (zkCameraView != null) {
                    zkCameraView.setPreviewCallback(this.onGrayCallback);
                    Message obtain = Message.obtain();
                    obtain.what = MSG_OPEN_CAMERA;
                    obtain.obj = this.mIrCameraView;
                    BackgroundThreadExecutor.getInstance().sendMessage(obtain);
                }
            }
            ZkCameraView createDefault = this.isDefaultCamera ? CameraViewManager.createDefault(this) : CameraViewManager.createSecondary(this);
            this.mCameraView = createDefault;
            if (createDefault != null) {
                createDefault.setPreviewCallback(this.onColorCallback);
                this.mCameraView.setErrorCallback(this.mErrorCallback);
                Message obtain2 = Message.obtain();
                obtain2.what = MSG_OPEN_CAMERA;
                obtain2.obj = this.mCameraView;
                obtain2.arg1 = 1;
                BackgroundThreadExecutor.getInstance().sendMessageDelayed(obtain2, 0);
                if (this.mSurfaceRoot.indexOfChild(this.mCameraView) == -1) {
                    this.mSurfaceRoot.addView(this.mCameraView, 0);
                    int previewWidth = DeviceManager.getDefault().getCameraConfig().getPreviewWidth();
                    int previewHeight = DeviceManager.getDefault().getCameraConfig().getPreviewHeight();
                    Point point = new Point();
                    getWindowManager().getDefaultDisplay().getRealSize(point);
                    int i = point.x;
                    int i2 = point.y;
                    int previewHeight2 = (DeviceManager.getDefault().getCameraConfig().getPreviewHeight() * i) / previewWidth;
                    int previewWidth2 = (DeviceManager.getDefault().getCameraConfig().getPreviewWidth() * i2) / previewHeight;
                    Log.e(TAG, "addCameraView: preWidth=" + previewWidth + ", preHeight=" + previewHeight + ", ScreenWidth=" + i + ", ScreenHeight=" + i2);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCameraView.getLayoutParams();
                    layoutParams.height = Math.max(previewHeight2, i2);
                    layoutParams.width = Math.max(previewWidth2, i);
                    layoutParams.gravity = 17;
                }
            }
        }
    }

    public /* synthetic */ void lambda$new$15$ZkFaceLauncher(int i, Camera camera) {
        runOnUiThread(new Runnable(i) {
            public final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                ZkFaceLauncher.this.lambda$new$14$ZkFaceLauncher(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$new$14$ZkFaceLauncher(int i) {
        FileLogUtils.writeCameraLog("mErrorCallback isInitComplete: " + this.isInitComplete + " error: " + i);
        if (this.isInitComplete) {
            removeCameraView();
            addCameraView(false);
        }
    }

    private static class ScreenStatusReceiver extends ZKReceiver<ZkFaceLauncher> {
        public ScreenStatusReceiver(ZkFaceLauncher zkFaceLauncher) {
            super(zkFaceLauncher);
        }

        public void onReceive(ZkFaceLauncher zkFaceLauncher, Context context, Intent intent) {
            if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                Log.d(ZkFaceLauncher.TAG, "onReceive: Intent.ACTION_USER_PRESENT");
                if (zkFaceLauncher.mState == Launcher.State.WORKSPACE) {
                    zkFaceLauncher.enterWorkMode();
                }
            } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                Log.d(ZkFaceLauncher.TAG, "onReceive: Intent.ACTION_SCREEN_OFF");
                ZKEventLauncher.setProcessDialogVisibility(false);
            }
            if ("com.zkteco.android.core.getImage".equals(intent.getAction())) {
                ZkFaceLauncher.goOn = true;
            }
        }
    }

    private static class ShowTemperatureReceiver extends ZKReceiver<ZkFaceLauncher> {
        public ShowTemperatureReceiver(ZkFaceLauncher zkFaceLauncher) {
            super(zkFaceLauncher);
        }

        public void onReceive(ZkFaceLauncher zkFaceLauncher, Context context, Intent intent) {
            String str;
            String str2;
            if (intent.getAction().equals(ZkFaceLauncher.CHANGE_TEMPERATURE_UI)) {
                double doubleExtra = intent.getDoubleExtra("temperature", -1.0d);
                int intExtra = intent.getIntExtra("mask", 0);
                if (doubleExtra == -1.0d || doubleExtra == 255.0d) {
                    zkFaceLauncher.tvTemperature.setText("");
                    if (intExtra == 1) {
                        zkFaceLauncher.playSound(108);
                    }
                } else {
                    if (zkFaceLauncher.tvShowTemp.getVisibility() == 8) {
                        zkFaceLauncher.tvShowTemp.setVisibility(0);
                    }
                    DecimalFormat decimalFormat = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US));
                    String str3 = zkFaceLauncher.getResources().getString(R.string.temp) + ":";
                    if (zkFaceLauncher.sIRTempUnit == 0) {
                        str = decimalFormat.format(doubleExtra);
                        str2 = "℃";
                    } else {
                        str = decimalFormat.format(ZKTemperatureUtil.getInstance(context).celsiustoFahrenheit(doubleExtra));
                        str2 = "℉";
                    }
                    String str4 = str3 + str + str2;
                    if (str.contains(",")) {
                        str = str.replace(",", ".");
                    }
                    if (zkFaceLauncher.enableShowTemp == 1) {
                        zkFaceLauncher.tvShowTemp.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                        zkFaceLauncher.tvShowTemp.setText(str4);
                    } else {
                        zkFaceLauncher.tvShowTemp.setText((CharSequence) null);
                    }
                    if (zkFaceLauncher.temperHigh < new BigDecimal(str).multiply(new BigDecimal("100")).intValue()) {
                        if (zkFaceLauncher.enableShowTemp == 1) {
                            zkFaceLauncher.tvShowTemp.setTextColor(ContextCompat.getColor(zkFaceLauncher, R.color.launcher_clr_ff0000));
                        } else {
                            zkFaceLauncher.tvShowTemp.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, ContextCompat.getDrawable(zkFaceLauncher, R.mipmap.wrong), (Drawable) null, (Drawable) null);
                        }
                        zkFaceLauncher.tvTemperature.setTextColor(ContextCompat.getColor(zkFaceLauncher, R.color.launcher_clr_ff0000));
                        zkFaceLauncher.tvTemperature.setText(R.string.high_body_temperature);
                        zkFaceLauncher.playSound(107);
                    } else {
                        if (zkFaceLauncher.enableShowTemp == 1) {
                            zkFaceLauncher.tvShowTemp.setTextColor(ContextCompat.getColor(zkFaceLauncher, 17170443));
                        } else {
                            zkFaceLauncher.tvShowTemp.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, ContextCompat.getDrawable(zkFaceLauncher, R.mipmap.right), (Drawable) null, (Drawable) null);
                        }
                        zkFaceLauncher.tvTemperature.setTextColor(ContextCompat.getColor(zkFaceLauncher, 17170443));
                        zkFaceLauncher.tvTemperature.setText(R.string.normal_temperature);
                        if (intExtra == 1 && ZKLauncher.enalbeMaskDetection == 1) {
                            zkFaceLauncher.playSound(108);
                        } else {
                            zkFaceLauncher.playSound(104);
                        }
                    }
                    long unused = zkFaceLauncher.showlasttemp = SystemClock.elapsedRealtime();
                }
            }
            if (intent.getAction().equals(ZkFaceLauncher.SHOW_TEMPERATURE_UI)) {
                zkFaceLauncher.enterWorkMode();
                zkFaceLauncher.tvTemperature.setTextColor(ContextCompat.getColor(zkFaceLauncher, 17170443));
                zkFaceLauncher.tvTemperature.setText(R.string.please_measure_your_temperature);
                zkFaceLauncher.tvShowTemp.setText((CharSequence) null);
                zkFaceLauncher.tvShowTemp.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                zkFaceLauncher.playSound(106);
            }
            if (intent.getAction().equals(ZkFaceLauncher.SHOW_MASK_SOUND)) {
                zkFaceLauncher.enterWorkMode();
                zkFaceLauncher.playSound(109);
                MainThreadExecutor.getInstance().remove(zkFaceLauncher.resetMaskDetectionFlag);
                zkFaceLauncher.mDetectMask.set(true);
                MainThreadExecutor.getInstance().executeDelayed(zkFaceLauncher.resetMaskDetectionFlag, 5000);
            }
        }
    }

    public /* synthetic */ void lambda$new$16$ZkFaceLauncher() {
        this.mDetectMask.set(false);
    }

    public class AboutLight implements Runnable {
        int lightStatu;
        int lightType;

        public AboutLight(int i, int i2) {
            this.lightStatu = i2;
            this.lightType = i;
        }

        public void run() {
            try {
                ZkFaceLauncher.this.mLightManager.setLightState(this.lightType, this.lightStatu);
                boolean z = false;
                if (this.lightType == 0) {
                    AtomicBoolean access$100 = ZkFaceLauncher.this.isVisibleLightOn;
                    if (this.lightStatu == 1) {
                        z = true;
                    }
                    access$100.set(z);
                    return;
                }
                AtomicBoolean access$3400 = ZkFaceLauncher.this.isInfraredLightOn;
                if (this.lightStatu == 1) {
                    z = true;
                }
                access$3400.set(z);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
