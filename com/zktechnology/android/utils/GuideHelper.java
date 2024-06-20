package com.zktechnology.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import androidx.work.WorkRequest;
import com.google.common.base.Ascii;
import com.guide.guidecore.BuildConfig;
import com.guide.guidecore.EnvironmentTempHandler;
import com.guide.guidecore.GuideUsbManager;
import com.guide.guidecore.Logger;
import com.guide.guidecore.UsbStatusInterface;
import com.guide.guidecore.jni.AutoCorrectResult;
import com.guide.guidecore.jni.IrUtil;
import com.guide.guidecore.jni.MeasureParam;
import com.guide.guidecore.jni.NativeGuideCore;
import com.guide.guidecore.prase.PraseSrcData;
import com.guide.guidecore.utils.BaseDataTypeConvertUtils;
import com.guide.guidecore.utils.FileUtils;
import com.guide.guidecore.utils.MediaUtils;
import com.guide.guidecore.utils.OtherUtils;
import com.guide.guidecore.utils.ShutterHandler;
import com.parts.mobileir.mobileirparts.engine.ParamLineParser;
import com.parts.mobileir.mobileirparts.engine.model.CustomParamLine;
import com.parts.mobileir.mobileirparts.engine.model.FixedParamLine;
import com.parts.mobileir.mobileirparts.engine.model.ParamLine;
import com.tencent.map.geolocation.util.DateUtils;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.verify.utils.ZKConstantConfig;
import com.zkteco.db.SQLConstants;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.UShort;
import kotlin.jvm.internal.ShortCompanionObject;

public class GuideHelper implements PraseSrcData.PraseInterface {
    private static int CENTER_Y16_QUEUE_SIZE = 7;
    /* access modifiers changed from: private */
    public static int COLD_HOT_MAX_COUNT = 10;
    private static long COLD_HOT_PERIOD = 5000;
    private static long COLD_HOT_STARTUP_DELAY = 600000;
    public static final float DEFAULT_AMBIENT_TEMP = -1314.0f;
    private static final int FAR_B2_INDEX = 15;
    private static final int FAR_B_INDEX = 10;
    private static final int FAR_KF2_INDEX = 16;
    private static final int FAR_KF_INDEX = 11;
    private static final short FIRMWARE_UPGRADE_PAGE_END = 191;
    private static final short FIRMWARE_UPGRADE_PAGE_SIZE = 2048;
    private static final short FIRMWARE_UPGRADE_PAGE_START = 128;
    private static final long FOCUS_PERIOD = 1000;
    public static final float MAX_NUC_RETRY_COUNT = 3.0f;
    private static final int NEAR_B2_INDEX = 13;
    private static final int NEAR_B_INDEX = 7;
    private static final int NEAR_KF2_INDEX = 14;
    private static final int NEAR_KF_INDEX = 8;
    private static final int SRC_HIGHT = 92;
    private static final int SRC_REF_HIEHT = 2;
    private static final int SRC_WIDTH = 120;
    public static final String TAG = "guidecore";
    public static final int VIS_HIGHT = 480;
    public static final int VIS_WIDTH = 640;
    private static LinkedBlockingQueue<Short> centerY16Queue = null;
    private static short filterCenterY16 = 0;
    private static float startUpShutterTemp = -1000.0f;
    private final int CLEAR_COUNT = 50;
    private float SRC_SCALE = 3.0f;
    /* access modifiers changed from: private */
    public float ambientTempForDebug = -1314.0f;
    /* access modifiers changed from: private */
    public short autoCorrectFar30Y16;
    /* access modifiers changed from: private */
    public short autoCorrectFar33Y16;
    /* access modifiers changed from: private */
    public short autoCorrectFar36Y16;
    /* access modifiers changed from: private */
    public short autoCorrectNear30Y16;
    /* access modifiers changed from: private */
    public short autoCorrectNear33Y16;
    /* access modifiers changed from: private */
    public short autoCorrectNear36Y16;
    /* access modifiers changed from: private */
    public int autoCorrectShutterCount = 0;
    /* access modifiers changed from: private */
    public Timer autoCorrectTimer;
    /* access modifiers changed from: private */
    public TimerTask autoCorrectTimerTask;
    private long autoShutterDelayFromUser = DateUtils.ONE_MINUTE;
    private boolean autoShutterFromUser = true;
    private long autoShutterPeriodFromUser = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
    private short[] bNoParamLineShortDataArr = null;
    private short[] bShortDataArr = null;
    private volatile int clearCount = 0;
    /* access modifiers changed from: private */
    public int coldHotCount = 0;
    private TimerTask coldHotStartupTask = null;
    private Timer coldHotStartupTimer = null;
    /* access modifiers changed from: private */
    public TimerTask coldHotTask = null;
    /* access modifiers changed from: private */
    public Timer coldHotTimer = null;
    /* access modifiers changed from: private */
    public float envTemp = 0.0f;
    private EnvironmentTempHandler environmentTempHandler = new EnvironmentTempHandler();
    private TimerTask focusTask = null;
    private Timer focusTimer = null;
    /* access modifiers changed from: private */
    public boolean isCalcY16 = false;
    private boolean isCallBackRgb = true;
    private boolean isFilpY = true;
    /* access modifiers changed from: private */
    public volatile boolean isFirstNucOrShutterFinish = false;
    private volatile boolean isInitShutter = false;
    private volatile boolean isRenderStart = false;
    /* access modifiers changed from: private */
    public volatile boolean isRunning = false;
    /* access modifiers changed from: private */
    public volatile boolean isShutter = false;
    private int isShutterCount = 0;
    /* access modifiers changed from: private */
    public volatile boolean isStopXOrderIng = false;
    private volatile boolean isStreamResume = false;
    /* access modifiers changed from: private */
    public boolean isTempOk = false;
    /* access modifiers changed from: private */
    public volatile boolean isfirmwareUpgradeReadData = false;
    /* access modifiers changed from: private */
    public short[] mAllCurveShortArray;
    private short[] mAllKArray;
    /* access modifiers changed from: private */
    public AutoCorrectInterface mAutoCorrectInterface;
    private Bitmap mBitmap;
    private String mCenterFoucsTemp;
    /* access modifiers changed from: private */
    public int mCenterIndex;
    /* access modifiers changed from: private */
    public String mCenterTemp;
    private Context mCtx;
    /* access modifiers changed from: private */
    public int mCurrChangeRIndex = -1;
    private short[] mCurrKArray;
    private byte[] mDeviceSN;
    private String mDeviceSNStr;
    private int mFrameCount = 0;
    /* access modifiers changed from: private */
    public GuideUsbManager mGuideUsbManager;
    /* access modifiers changed from: private */
    public byte[] mHeader;
    private ImageCallBackInterface mImageCallBackInterface;
    private Handler mImageHandler;
    private HandlerThread mImageHandlerThread;
    private volatile Runnable mImageRunnable;
    private AtomicBoolean mIsRegisterUsbPermission = new AtomicBoolean(false);
    private AtomicBoolean mIsRegisterUsbStatus = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public volatile short mJwbNumber = 0;
    /* access modifiers changed from: private */
    public short[] mJwtTabArrShort;
    protected final Object mKInitLock = new Object();
    /* access modifiers changed from: private */
    public MeasureParam mMeasureParam;
    /* access modifiers changed from: private */
    public NativeGuideCore mNativeGuideCore;
    private byte[] mOnePageBuff = new byte[2048];
    private byte[] mOnePageMaxBuff = new byte[16384];
    private int[] mPaletteArray;
    /* access modifiers changed from: private */
    public ParamLine mParamLine;
    private ParamLineParser mParamLineParser;
    /* access modifiers changed from: private */
    public PraseSrcData mPraseSrcData;
    private String mPreCenterTemp;
    /* access modifiers changed from: private */
    public ShutterHandler mShutterHandler;
    /* access modifiers changed from: private */
    public short[] mTempY16Array = new short[10800];
    /* access modifiers changed from: private */
    public short[] mY16Array;
    private volatile boolean nucResult = true;
    private volatile int nucRetryCount = 0;
    private short[] paramLineArr = null;
    private boolean preIsShutter = false;
    private int previousFlag = 0;
    /* access modifiers changed from: private */
    public boolean rebootColdHotState = true;
    private String recordDebugDataPath = null;
    private TimerTask recordDebugDataTask = null;
    private Timer recordDebugDataTimer = null;
    /* access modifiers changed from: private */
    public int rototeType = 1;
    private UsbStatusInterface specialUsbStatusInterface;
    private boolean startColdHotTimerFlag = true;
    /* access modifiers changed from: private */
    public short[] xShortDataArr = null;

    public enum AutoCorrectCalcY16Mode {
        near30,
        near33,
        near36,
        far30,
        far33,
        far36
    }

    public interface AutoCorrectInterface {
        void shutterEnd();
    }

    public interface ImageCallBackInterface {
        void callBackOneFrameBitmap(Bitmap bitmap, short[] sArr);
    }

    public String getVersion() {
        return BuildConfig.VERSION_NAME;
    }

    static /* synthetic */ int access$1708(GuideHelper guideHelper) {
        int i = guideHelper.coldHotCount;
        guideHelper.coldHotCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$2308(GuideHelper guideHelper) {
        int i = guideHelper.autoCorrectShutterCount;
        guideHelper.autoCorrectShutterCount = i + 1;
        return i;
    }

    public void guideCoreInit(Context context, int i, float f, int i2) {
        this.rototeType = i2;
        guideCoreInit(context, i, f);
    }

    public void guideCoreInit(Context context, int i, float f) {
        if (f <= 1.0f) {
            this.SRC_SCALE = 1.0f;
        } else if (f >= 3.0f) {
            this.SRC_SCALE = 3.0f;
        } else {
            this.SRC_SCALE = f;
        }
        guideCoreInit(context, i);
    }

    public void guideCoreInit(Context context, int i) {
        this.mCtx = context;
        NativeGuideCore nativeGuideCore = new NativeGuideCore();
        this.mNativeGuideCore = nativeGuideCore;
        nativeGuideCore.guideCoreCreate(120, 92, 2, this.SRC_SCALE);
        this.mGuideUsbManager = new GuideUsbManager(context);
        this.mPaletteArray = new int[256];
        changePalette(i);
        this.mCurrKArray = new short[11040];
        for (int i2 = 0; i2 < 11040; i2++) {
            this.mCurrKArray[i2] = 8192;
        }
        this.mNativeGuideCore.guideCoreSetCurrentK(this.mCurrKArray);
        this.mY16Array = new short[10800];
        this.mMeasureParam = new MeasureParam();
        int i3 = this.rototeType;
        if (i3 == 1 || i3 == 3) {
            float f = this.SRC_SCALE;
            this.mBitmap = Bitmap.createBitmap((int) (90.0f * f), (int) (f * 120.0f), Bitmap.Config.ARGB_8888);
            this.mCenterIndex = 5445;
        } else {
            float f2 = this.SRC_SCALE;
            this.mBitmap = Bitmap.createBitmap((int) (120.0f * f2), (int) (f2 * 90.0f), Bitmap.Config.ARGB_8888);
            this.mCenterIndex = 5460;
        }
        PraseSrcData praseSrcData = new PraseSrcData(120, 92, this, this.mNativeGuideCore);
        this.mPraseSrcData = praseSrcData;
        praseSrcData.startParseDataHandleThread();
        this.mParamLine = new ParamLine(new FixedParamLine(), new CustomParamLine());
        this.mParamLineParser = new ParamLineParser(this.mParamLine);
        initShutterHandler();
        HandlerThread handlerThread = new HandlerThread("ImageHandlerThread");
        this.mImageHandlerThread = handlerThread;
        handlerThread.start();
        this.mImageHandler = new Handler(this.mImageHandlerThread.getLooper());
        centerY16Queue = new LinkedBlockingQueue<>();
        Logger.d("guidecore", "guideCoreInit");
    }

    public void guideCoreDestory() {
        Timer timer = this.focusTimer;
        if (timer != null) {
            timer.cancel();
            this.focusTimer = null;
        }
        TimerTask timerTask = this.focusTask;
        if (timerTask != null) {
            timerTask.cancel();
            this.focusTask = null;
        }
        Timer timer2 = this.coldHotStartupTimer;
        if (timer2 != null) {
            timer2.cancel();
            this.coldHotStartupTimer = null;
        }
        TimerTask timerTask2 = this.coldHotStartupTask;
        if (timerTask2 != null) {
            timerTask2.cancel();
            this.coldHotStartupTask = null;
        }
        if (this.coldHotTimer != null) {
            this.coldHotTask.cancel();
            this.coldHotTask = null;
        }
        Timer timer3 = this.coldHotTimer;
        if (timer3 != null) {
            timer3.cancel();
            this.coldHotTimer = null;
        }
        this.startColdHotTimerFlag = true;
        this.coldHotCount = 0;
        this.rebootColdHotState = true;
        this.isTempOk = false;
        this.mShutterHandler.setNucTimeReset();
        HandlerThread handlerThread = this.mImageHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.mImageHandlerThread = null;
            this.mImageHandler = null;
        }
        PraseSrcData praseSrcData = this.mPraseSrcData;
        if (praseSrcData != null) {
            praseSrcData.stopParseDataHandleThread();
        }
        this.isRenderStart = false;
        ShutterHandler shutterHandler = this.mShutterHandler;
        if (shutterHandler != null) {
            shutterHandler.stopShutterThread();
        }
        NativeGuideCore nativeGuideCore = this.mNativeGuideCore;
        if (nativeGuideCore != null) {
            nativeGuideCore.guideCoreDestory();
        }
    }

    private void initShutterHandler() {
        ShutterHandler shutterHandler = new ShutterHandler(new ShutterHandler.ShutterControl() {
            public void shutterStop() {
            }

            public void shutterEnd() {
                if (GuideHelper.this.mAutoCorrectInterface != null) {
                    GuideHelper.this.mAutoCorrectInterface.shutterEnd();
                }
            }

            public void firstNucOrShutterFinish(boolean z) {
                if (z) {
                    boolean unused = GuideHelper.this.isFirstNucOrShutterFinish = true;
                }
            }

            public void nuc() {
                GuideHelper.this.mGuideUsbManager.sendDonucCmd();
            }

            public void shutterOff() {
                GuideHelper.this.mGuideUsbManager.sendShutterOffCmd();
            }

            public void shutterOn() {
                GuideHelper.this.mGuideUsbManager.sendShutterOnCmd();
            }

            public void shutterBegin() {
                boolean unused = GuideHelper.this.isShutter = true;
            }

            public void preFpaNucShutter() {
                Logger.d("guidecore", "nuc: preFpaNucShutter reset nuc retry count");
                GuideHelper.this.setNucRetryCount(0);
            }
        });
        this.mShutterHandler = shutterHandler;
        shutterHandler.startShutterThread();
    }

    public void registUsbPermissions() {
        try {
            this.mGuideUsbManager.registerUsbPermissions();
            this.mIsRegisterUsbPermission.set(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegistUsbPermissions() {
        try {
            if (this.mIsRegisterUsbPermission.get()) {
                this.mGuideUsbManager.unRegisterUsbPermissions();
                this.mIsRegisterUsbPermission.set(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGetImage(ImageCallBackInterface imageCallBackInterface) {
        this.isRunning = false;
        this.isInitShutter = false;
        this.isShutter = false;
        this.preIsShutter = false;
        this.isShutterCount = 0;
        this.isFirstNucOrShutterFinish = false;
        this.isStreamResume = true;
        setNucRetryCount(0);
        if (imageCallBackInterface != null) {
            this.mImageCallBackInterface = imageCallBackInterface;
            int connnectUsbDevice = this.mGuideUsbManager.connnectUsbDevice();
            Logger.d("guidecore", "usbCode = " + connnectUsbDevice);
            this.mCurrChangeRIndex = -1;
            this.mShutterHandler.setIsShuttering(false);
            this.mGuideUsbManager.sendShutterOnCmd();
            if (connnectUsbDevice > 0) {
                startThreadSendData();
                UsbStatusInterface usbStatusInterface = this.specialUsbStatusInterface;
                if (usbStatusInterface != null) {
                    usbStatusInterface.usbConnect();
                    return;
                }
                return;
            }
            UsbStatusInterface usbStatusInterface2 = this.specialUsbStatusInterface;
            if (usbStatusInterface2 != null) {
                usbStatusInterface2.usbDisConnect();
                return;
            }
            return;
        }
        Logger.d("guidecore", "ImageCallBackInterface is NULL ");
    }

    public void stopGetImage() {
        this.mGuideUsbManager.sendStopGetY16Cmd();
        clearReadForStopXOperation();
        this.isRunning = false;
        this.isShutter = false;
        if (this.mShutterHandler.getIsRunning()) {
            this.mShutterHandler.stop();
        }
        this.mPraseSrcData.stopPraseData();
        int i = 100;
        while (!this.mPraseSrcData.isParseFrameLoopDone() && i > 0) {
            i--;
            Logger.d("guidecore", "wait ParseFrameLoop Done.");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.isRenderStart = false;
        this.mGuideUsbManager.disconnectUsbDevice();
    }

    private void startThreadSendData() {
        this.mPraseSrcData.startPraseData();
        if (this.mImageRunnable != null) {
            this.mImageHandler.removeCallbacks(this.mImageRunnable);
        }
        this.mImageRunnable = new Runnable() {
            public void run() {
                int access$400;
                Process.setThreadPriority(1);
                byte[] bArr = new byte[16384];
                Logger.d("guidecore", "initDeviceParam begin");
                int i = 0;
                while (true) {
                    if (i >= 5) {
                        break;
                    }
                    synchronized (GuideHelper.this.mKInitLock) {
                        access$400 = GuideHelper.this.initDeviceParam();
                    }
                    if (access$400 == 0) {
                        Logger.d("guidecore", "initDeviceParam succ ");
                        break;
                    } else {
                        Logger.d("guidecore", "initDeviceParam failed ");
                        i++;
                    }
                }
                if (GuideHelper.this.mJwtTabArrShort == null) {
                    Logger.d("guidecore", "mImageRunnable exit");
                    return;
                }
                GuideHelper.this.mNativeGuideCore.guideCoreRealTimeMeasureInit(GuideHelper.this.mJwtTabArrShort, GuideHelper.this.mJwbNumber, GuideHelper.this.mAllCurveShortArray, GuideHelper.this.mJwbNumber * 2, GuideUsbManager.CURVE_LENGTH);
                GuideHelper.this.mNativeGuideCore.guideCoreRealTimeHeaderInit(GuideHelper.this.mHeader);
                Logger.d("guidecore", "guideCoreRealTimeMeasureInit");
                Logger.d("guidecore", "StartGetY16Cmd res =" + GuideHelper.this.mGuideUsbManager.sendStartGetY16Cmd());
                GuideHelper.this.mGuideUsbManager.sendFrameVerificationCmd();
                boolean unused = GuideHelper.this.isRunning = true;
                while (GuideHelper.this.isRunning) {
                    if (!GuideHelper.this.isfirmwareUpgradeReadData) {
                        int startGetY16Data = GuideHelper.this.mGuideUsbManager.startGetY16Data(bArr);
                        Log.d("guidecore", "GET Y16 length = " + startGetY16Data);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (startGetY16Data > 0) {
                            GuideHelper.this.mPraseSrcData.setPraseData(bArr, startGetY16Data);
                        }
                    }
                }
            }
        };
        this.mImageHandler.post(this.mImageRunnable);
    }

    public void isShowIrImage(boolean z) {
        this.isCallBackRgb = z;
    }

    public void changePalette(int i) {
        int i2 = R.raw.palette2;
        switch (i) {
            case 0:
                i2 = R.raw.palette0;
                break;
            case 1:
                i2 = R.raw.palette1;
                break;
            case 3:
                i2 = R.raw.palette3;
                break;
            case 4:
                i2 = R.raw.palette4;
                break;
            case 5:
                i2 = R.raw.palette5;
                break;
            case 6:
                i2 = R.raw.palette6;
                break;
            case 7:
                i2 = R.raw.palette7;
                break;
            case 8:
                i2 = R.raw.palette8;
                break;
            case 9:
                i2 = R.raw.palette9;
                break;
            case 10:
                i2 = R.raw.palette10;
                break;
        }
        this.mPaletteArray = IrUtil.palette2Array(this.mCtx, i2);
    }

    public void setFilpY(boolean z) {
        this.isFilpY = z;
    }

    public void setRototeType(int i) {
        this.rototeType = i;
    }

    public void convertY16ToImage(short[] sArr, Bitmap bitmap) {
        this.mNativeGuideCore.guideCoreConvertY16ToImage(sArr, bitmap, this.mPaletteArray, false, 0);
    }

    public void convertY16ToY8(short[] sArr, byte[] bArr) {
        this.mNativeGuideCore.guideCoreConvertY16ToY8(sArr, bArr, false, 0);
    }

    public void convertY8ToImage(byte[] bArr, Bitmap bitmap) {
        this.mNativeGuideCore.guideCoreConvertY8ToImage(bArr, bitmap, this.mPaletteArray, false, 0);
    }

    private void centerY16TimeFilter() {
        if (centerY16Queue.size() == CENTER_Y16_QUEUE_SIZE) {
            centerY16Queue.poll();
        }
        centerY16Queue.offer(Short.valueOf(this.mNativeGuideCore.guideCoreRealTimeGetFilterY16(this.mY16Array, this.mCenterIndex, this.rototeType)));
        if (centerY16Queue.size() > 0) {
            long j = 0;
            Iterator<Short> it = centerY16Queue.iterator();
            while (it.hasNext()) {
                j += (long) it.next().shortValue();
            }
            filterCenterY16 = (short) ((int) (((double) ((((float) j) * 1.0f) / ((float) centerY16Queue.size()))) + 0.5d));
        }
    }

    private boolean isBFrameValid(short[] sArr) {
        if (this.bNoParamLineShortDataArr == null) {
            this.bNoParamLineShortDataArr = new short[10800];
        }
        System.arraycopy(sArr, 240, this.bNoParamLineShortDataArr, 0, 10800);
        long j = 0;
        long j2 = 0;
        for (int i = 0; i < 10800; i++) {
            j2 += (long) this.bNoParamLineShortDataArr[i];
        }
        short s = (short) ((int) (j2 / 10800));
        for (int i2 = 0; i2 < 10800; i2++) {
            j += (long) Math.abs(this.bNoParamLineShortDataArr[i2] - s);
        }
        if (((int) (j / 10800)) <= 400) {
            return true;
        }
        return false;
    }

    private boolean isXFrameValid(short[] sArr, short[] sArr2) {
        short s = (sArr2[118] & UShort.MAX_VALUE) | ((sArr2[119] & UShort.MAX_VALUE) << 16);
        if (s == -1) {
            Logger.d("guidecore", "isXFrameValid: not start Verification mechanism");
            return true;
        }
        short[] sArr3 = new short[45];
        int i = 0;
        while (i < 30) {
            sArr3[i / 2] = sArr[(i * 120) + 240];
            i += 2;
        }
        while (i < 60) {
            sArr3[i / 2] = sArr[(i * 120) + 240 + 59];
            i += 2;
        }
        while (i < 90) {
            sArr3[i / 2] = sArr[(i * 120) + 240 + 119];
            i += 2;
        }
        int guideCoreCalcCRC32Short = this.mNativeGuideCore.guideCoreCalcCRC32Short(sArr3, 45);
        if (s == guideCoreCalcCRC32Short) {
            return true;
        }
        Logger.d("guidecore", "isXFrameValid check failed! crcValue = " + s + " crcV = " + guideCoreCalcCRC32Short);
        return false;
    }

    public void callBackOneFrame(short[] sArr, short[] sArr2) {
        Logger.v("guidecore", "deubgNoImage: callBackOneFrame called");
        if (!isXFrameValid(sArr, sArr2)) {
            Logger.v("guidecore", "deubgNoImage: isXFrameValid return false");
            return;
        }
        this.isRenderStart = true;
        this.xShortDataArr = sArr;
        this.paramLineArr = sArr2;
        if (this.isCallBackRgb) {
            this.mNativeGuideCore.guideCoreConvertXToImage1(sArr, this.mBitmap, this.mPaletteArray, this.isFilpY, this.rototeType);
        } else {
            this.mNativeGuideCore.guideCoreConvertXToY16(sArr, this.isFilpY, this.rototeType);
        }
        this.mNativeGuideCore.guideCoreGetCurrentY16(this.mY16Array);
        centerY16TimeFilter();
        this.mParamLineParser.parse(sArr2);
        Logger.v("guidecore", "deubgNoImage: isShutter = " + this.isShutter + " isFirstNucOrShutterFinish = " + this.isFirstNucOrShutterFinish);
        if (this.mImageCallBackInterface != null && !this.isShutter && this.isFirstNucOrShutterFinish) {
            if (this.isCallBackRgb) {
                this.mImageCallBackInterface.callBackOneFrameBitmap(this.mBitmap, this.mY16Array);
            } else {
                this.mImageCallBackInterface.callBackOneFrameBitmap((Bitmap) null, this.mY16Array);
            }
        }
        int shutterFlag = this.mParamLine.getFixedParamLine().getShutterFlag();
        if (shutterFlag == 1) {
            this.bShortDataArr = sArr;
            this.nucResult = isBFrameValid(sArr);
            if (this.nucResult) {
                this.mNativeGuideCore.guideCoreUpdateB(sArr);
            }
            if (centerY16Queue.size() > 0) {
                centerY16Queue.clear();
            }
        }
        Logger.v("guidecore", "deubgNoImage: isShutter= " + this.isShutter + " currentFlag =" + shutterFlag + " previousFlag = " + this.previousFlag);
        if (shutterFlag == 0 && this.previousFlag == 1) {
            this.isShutter = false;
            this.mShutterHandler.setIsShuttering(false);
            if (this.nucResult) {
                Logger.d("guidecore", "nuc: success");
                setNucRetryCount(0);
            } else {
                if (((float) getNucRetryCount()) < 3.0f) {
                    setNucRetryCount(getNucRetryCount() + 1);
                    Logger.d("guidecore", "nuc: retry do nuc shutter " + getNucRetryCount());
                    nucTest();
                } else {
                    Logger.d("guidecore", "nuc: limit reached");
                }
                this.nucResult = true;
            }
        }
        this.previousFlag = shutterFlag;
        if (this.preIsShutter && this.isShutter) {
            this.isShutterCount++;
            Logger.d("guidecore", "shutterRecover: isShutterCount = " + this.isShutterCount);
            if (this.isShutterCount == 150) {
                Logger.d("guidecore", "shutterRecover: Count reached! Reset ");
                this.isShutterCount = 0;
                this.preIsShutter = false;
                this.isShutter = false;
                this.mShutterHandler.setIsShuttering(false);
            }
        }
        if (!this.isShutter) {
            this.isShutterCount = 0;
            this.preIsShutter = false;
        }
        this.preIsShutter = this.isShutter;
        this.mFrameCount++;
        if (this.isStreamResume) {
            this.mShutterHandler.firstNucOrShutter(this.mParamLine.getFixedParamLine().getRealtimeFpaTemp());
            this.isStreamResume = false;
        }
        if (this.mFrameCount % 25 == 0) {
            this.mFrameCount = 0;
            changeR((short) ((int) (this.mParamLine.getFixedParamLine().getRealtimeFpaTemp() * 100.0f)));
            measureCenterTemp(this.mParamLine, this.mY16Array);
            if (this.isFirstNucOrShutterFinish) {
                if (!this.isInitShutter) {
                    this.isInitShutter = true;
                    this.mShutterHandler.start(this.autoShutterFromUser, this.autoShutterPeriodFromUser, this.autoShutterDelayFromUser, this.mParamLine.getFixedParamLine().getRealtimeFpaTemp());
                    Logger.d("guidecore", "mShutterHandler start");
                } else {
                    Logger.d("guidecore", "realTimeTfpa " + (((float) this.mMeasureParam.realTimeTfpa) / 100.0f));
                    this.mShutterHandler.updateKeyParam2Control(true, 50, 10, ((float) this.mMeasureParam.realTimeTfpa) / 100.0f, this.mParamLine.getFixedParamLine().getRealtimeLenTemp(), this.mParamLine.getFixedParamLine().getRealtimeShutterTemp());
                }
            }
            this.envTemp = this.environmentTempHandler.refreshEnvironmentTemp(this.mParamLine.getFixedParamLine().getRealtimeLenTemp(), this.mParamLine.getFixedParamLine().getRealtimeShutterTemp());
        }
        if (this.startColdHotTimerFlag) {
            this.focusTimer = new Timer("focusTimer");
            AnonymousClass3 r2 = new TimerTask() {
                public void run() {
                    float realtimeFpaTemp = GuideHelper.this.mParamLine.getFixedParamLine().getRealtimeFpaTemp();
                    float guideCoreSmoothFocusTemp = GuideHelper.this.mNativeGuideCore.guideCoreSmoothFocusTemp(realtimeFpaTemp);
                    GuideHelper.this.mMeasureParam.realTimeTfpa = (short) ((int) (100.0f * guideCoreSmoothFocusTemp));
                    Logger.d("guidecore", "focusTask exec fpaTempOriginal = " + realtimeFpaTemp + " fpaTemp =" + guideCoreSmoothFocusTemp);
                }
            };
            this.focusTask = r2;
            this.focusTimer.schedule(r2, 0, 1000);
            this.coldHotStartupTimer = new Timer("coldHotStartupTimer");
            AnonymousClass4 r12 = new TimerTask() {
                public void run() {
                    boolean unused = GuideHelper.this.isTempOk = true;
                    Logger.d("guidecore", "coldHotStartupTask exec isTempOk = true");
                }
            };
            this.coldHotStartupTask = r12;
            this.coldHotStartupTimer.schedule(r12, COLD_HOT_STARTUP_DELAY);
            this.coldHotTimer = new Timer("coldHotTimer");
            AnonymousClass5 r22 = new TimerTask() {
                public void run() {
                    float realtimeLenTemp = GuideHelper.this.mParamLine.getFixedParamLine().getRealtimeLenTemp();
                    GuideHelper guideHelper = GuideHelper.this;
                    boolean unused = guideHelper.rebootColdHotState = guideHelper.mNativeGuideCore.guideCoreGetColdHot(realtimeLenTemp);
                    GuideHelper guideHelper2 = GuideHelper.this;
                    boolean unused2 = guideHelper2.isTempOk = !guideHelper2.rebootColdHotState;
                    GuideHelper.access$1708(GuideHelper.this);
                    Logger.d("guidecore", "coldHotTask coldHotCount = " + GuideHelper.this.coldHotCount + " rebootColdHotState = " + GuideHelper.this.rebootColdHotState);
                    Logger.d("guidecore", "coldHotTask coldHotCount = " + GuideHelper.this.coldHotCount + " isTempOk = " + GuideHelper.this.isTempOk);
                    if (GuideHelper.this.coldHotCount == GuideHelper.COLD_HOT_MAX_COUNT) {
                        GuideHelper.this.coldHotTask.cancel();
                        TimerTask unused3 = GuideHelper.this.coldHotTask = null;
                        GuideHelper.this.coldHotTimer.cancel();
                        Timer unused4 = GuideHelper.this.coldHotTimer = null;
                    }
                }
            };
            this.coldHotTask = r22;
            this.coldHotTimer.schedule(r22, 0, COLD_HOT_PERIOD);
            this.startColdHotTimerFlag = false;
        }
    }

    public int getShutterStatus() {
        return this.mParamLine.getFixedParamLine().getShutterFlag();
    }

    public String measureTemByY16(short s) {
        this.mMeasureParam.ks = (short) this.mParamLine.getCustomParamLine().getKs();
        this.mMeasureParam.k0 = (short) this.mParamLine.getCustomParamLine().getK0();
        this.mMeasureParam.k1 = (short) this.mParamLine.getCustomParamLine().getK1();
        this.mMeasureParam.k2 = (short) this.mParamLine.getCustomParamLine().getK2();
        this.mMeasureParam.k3 = (short) this.mParamLine.getCustomParamLine().getK3();
        this.mMeasureParam.k4 = (short) this.mParamLine.getCustomParamLine().getK4();
        this.mMeasureParam.k5 = (short) this.mParamLine.getCustomParamLine().getK5();
        this.mMeasureParam.b = (short) this.mParamLine.getCustomParamLine().getB();
        this.mMeasureParam.kf = (short) this.mParamLine.getCustomParamLine().getKf();
        this.mMeasureParam.nearB = (short) this.mParamLine.getCustomParamLine().getNearB();
        this.mMeasureParam.nearKf = (short) this.mParamLine.getCustomParamLine().getNearKf();
        this.mMeasureParam.farB = (short) this.mParamLine.getCustomParamLine().getFarB();
        this.mMeasureParam.farKf = (short) this.mParamLine.getCustomParamLine().getFarKf();
        this.mMeasureParam.tref = (short) this.mParamLine.getCustomParamLine().getTref();
        this.mMeasureParam.nearKf2 = (short) this.mParamLine.getCustomParamLine().getNearkf2();
        this.mMeasureParam.nearB2 = (short) this.mParamLine.getCustomParamLine().getNearb2();
        this.mMeasureParam.farKf2 = (short) this.mParamLine.getCustomParamLine().getFarkf2();
        this.mMeasureParam.farB2 = (short) this.mParamLine.getCustomParamLine().getFarb2();
        this.mMeasureParam.realTimeTs = (short) ((int) (this.mParamLine.getFixedParamLine().getRealtimeShutterTemp() * 100.0f));
        this.mMeasureParam.realTimeTlen = (short) ((int) (this.mParamLine.getFixedParamLine().getRealtimeLenTemp() * 100.0f));
        this.mMeasureParam.lastShutterTs = (short) ((int) (this.mShutterHandler.getShutterTs() * 100.0f));
        this.mMeasureParam.lastShutterTfpa = (short) ((int) (this.mShutterHandler.getShutterTfpa() * 100.0f));
        this.mMeasureParam.lastShutterTlen = (short) ((int) (this.mShutterHandler.getShutterTlen() * 100.0f));
        this.mMeasureParam.lastLastShutterTs = (short) ((int) (this.mShutterHandler.getLastShutterTs() * 100.0f));
        this.mMeasureParam.lastLastShutterTfpa = (short) ((int) (this.mShutterHandler.getLastShutterTfpa() * 100.0f));
        this.mMeasureParam.lastLastShutterTlen = (short) ((int) (this.mShutterHandler.getLastShutterTlen() * 100.0f));
        this.mMeasureParam.changRTfpgIndex = (short) this.mCurrChangeRIndex;
        this.mMeasureParam.jwbLength = this.mJwbNumber;
        this.mMeasureParam.avgB = this.mNativeGuideCore.guideCoreGetAvgB();
        this.mMeasureParam.startupShutterTemp = this.mParamLine.getFixedParamLine().getStartupShutterTemp();
        this.mMeasureParam.bShutterCorrection = needShutterCorrection();
        return BaseDataTypeConvertUtils.Companion.float2StrWithOneDecimal(this.mNativeGuideCore.guideCoreRealTimeGetTemp((short) (s - this.mMeasureParam.avgB), this.mMeasureParam));
    }

    public String measureTemByY16(short s, int i) {
        if (!needFilter(i)) {
            return measureTemByY16(s);
        }
        short guideCoreRealTimeGetFilterY16 = this.mNativeGuideCore.guideCoreRealTimeGetFilterY16(this.mY16Array, i, this.rototeType);
        Logger.d("guidecore", "y16 = " + s + " y16NewTest " + ((short) (guideCoreRealTimeGetFilterY16 - this.mNativeGuideCore.guideCoreGetAvgB())));
        return measureTemByY16(guideCoreRealTimeGetFilterY16);
    }

    private boolean needShutterCorrection() {
        if (!TextUtils.isEmpty(this.mDeviceSNStr) && this.mDeviceSNStr.startsWith("ZX02A")) {
            return !this.isTempOk;
        }
        return false;
    }

    private boolean needFilter(int i) {
        int i2 = this.rototeType;
        int i3 = 120;
        int i4 = 90;
        if (i2 == 1 || i2 == 3) {
            i4 = 120;
            i3 = 90;
        }
        if (i >= 0) {
            int i5 = i3 * i4;
            if (i <= i5 - 1) {
                if (i >= 0 && i < i3) {
                    return false;
                }
                if ((i >= (i4 - 1) * i3 && i < i5) || i % i3 == 0) {
                    return false;
                }
                if ((i + 1) % i3 != 0) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public short getAvgB() {
        NativeGuideCore nativeGuideCore = this.mNativeGuideCore;
        if (nativeGuideCore == null) {
            return 0;
        }
        return nativeGuideCore.guideCoreGetAvgB();
    }

    public short getY16(float f, short s) {
        if (this.mNativeGuideCore == null) {
            return 0;
        }
        this.mMeasureParam.ks = (short) this.mParamLine.getCustomParamLine().getKs();
        this.mMeasureParam.k0 = (short) this.mParamLine.getCustomParamLine().getK0();
        this.mMeasureParam.k1 = (short) this.mParamLine.getCustomParamLine().getK1();
        this.mMeasureParam.k2 = (short) this.mParamLine.getCustomParamLine().getK2();
        this.mMeasureParam.k3 = (short) this.mParamLine.getCustomParamLine().getK3();
        this.mMeasureParam.k4 = (short) this.mParamLine.getCustomParamLine().getK4();
        this.mMeasureParam.k5 = (short) this.mParamLine.getCustomParamLine().getK5();
        this.mMeasureParam.b = (short) this.mParamLine.getCustomParamLine().getB();
        this.mMeasureParam.kf = (short) this.mParamLine.getCustomParamLine().getKf();
        this.mMeasureParam.nearB = (short) this.mParamLine.getCustomParamLine().getNearB();
        this.mMeasureParam.nearKf = (short) this.mParamLine.getCustomParamLine().getNearKf();
        this.mMeasureParam.farB = (short) this.mParamLine.getCustomParamLine().getFarB();
        this.mMeasureParam.farKf = (short) this.mParamLine.getCustomParamLine().getFarKf();
        this.mMeasureParam.tref = (short) this.mParamLine.getCustomParamLine().getTref();
        this.mMeasureParam.nearKf2 = (short) this.mParamLine.getCustomParamLine().getNearkf2();
        this.mMeasureParam.nearB2 = (short) this.mParamLine.getCustomParamLine().getNearb2();
        this.mMeasureParam.farKf2 = (short) this.mParamLine.getCustomParamLine().getFarkf2();
        this.mMeasureParam.farB2 = (short) this.mParamLine.getCustomParamLine().getFarb2();
        this.mMeasureParam.realTimeTs = (short) ((int) (this.mParamLine.getFixedParamLine().getRealtimeShutterTemp() * 100.0f));
        this.mMeasureParam.realTimeTlen = (short) ((int) (this.mParamLine.getFixedParamLine().getRealtimeLenTemp() * 100.0f));
        this.mMeasureParam.lastShutterTs = (short) ((int) (this.mShutterHandler.getShutterTs() * 100.0f));
        this.mMeasureParam.lastShutterTfpa = (short) ((int) (this.mShutterHandler.getShutterTfpa() * 100.0f));
        this.mMeasureParam.lastShutterTlen = (short) ((int) (this.mShutterHandler.getShutterTlen() * 100.0f));
        this.mMeasureParam.lastLastShutterTs = (short) ((int) (this.mShutterHandler.getLastShutterTs() * 100.0f));
        this.mMeasureParam.lastLastShutterTfpa = (short) ((int) (this.mShutterHandler.getLastShutterTfpa() * 100.0f));
        this.mMeasureParam.lastLastShutterTlen = (short) ((int) (this.mShutterHandler.getLastShutterTlen() * 100.0f));
        this.mMeasureParam.changRTfpgIndex = (short) this.mCurrChangeRIndex;
        this.mMeasureParam.jwbLength = this.mJwbNumber;
        this.mMeasureParam.avgB = this.mNativeGuideCore.guideCoreGetAvgB();
        this.mMeasureParam.startupShutterTemp = this.mParamLine.getFixedParamLine().getStartupShutterTemp();
        this.mMeasureParam.bShutterCorrection = needShutterCorrection();
        return this.mNativeGuideCore.guideCoreRealTimeGetY16(f, s, this.mMeasureParam);
    }

    private void measureCenterTemp(ParamLine paramLine, short[] sArr) {
        short s;
        short s2;
        short s3 = sArr[this.mCenterIndex];
        this.mNativeGuideCore.guideCoreGetAvgB();
        this.mMeasureParam.ks = (short) paramLine.getCustomParamLine().getKs();
        this.mMeasureParam.k0 = (short) paramLine.getCustomParamLine().getK0();
        this.mMeasureParam.k1 = (short) paramLine.getCustomParamLine().getK1();
        this.mMeasureParam.k2 = (short) paramLine.getCustomParamLine().getK2();
        this.mMeasureParam.k3 = (short) paramLine.getCustomParamLine().getK3();
        this.mMeasureParam.k4 = (short) paramLine.getCustomParamLine().getK4();
        this.mMeasureParam.k5 = (short) paramLine.getCustomParamLine().getK5();
        this.mMeasureParam.b = (short) paramLine.getCustomParamLine().getB();
        this.mMeasureParam.kf = (short) paramLine.getCustomParamLine().getKf();
        this.mMeasureParam.nearB = (short) paramLine.getCustomParamLine().getNearB();
        this.mMeasureParam.nearKf = (short) paramLine.getCustomParamLine().getNearKf();
        this.mMeasureParam.farB = (short) paramLine.getCustomParamLine().getFarB();
        this.mMeasureParam.farKf = (short) paramLine.getCustomParamLine().getFarKf();
        this.mMeasureParam.tref = (short) paramLine.getCustomParamLine().getTref();
        this.mMeasureParam.nearKf2 = (short) paramLine.getCustomParamLine().getNearkf2();
        this.mMeasureParam.nearB2 = (short) paramLine.getCustomParamLine().getNearb2();
        this.mMeasureParam.farKf2 = (short) paramLine.getCustomParamLine().getFarkf2();
        this.mMeasureParam.farB2 = (short) paramLine.getCustomParamLine().getFarb2();
        this.mMeasureParam.realTimeTs = (short) ((int) (paramLine.getFixedParamLine().getRealtimeShutterTemp() * 100.0f));
        this.mMeasureParam.realTimeTlen = (short) ((int) (paramLine.getFixedParamLine().getRealtimeLenTemp() * 100.0f));
        this.mMeasureParam.lastShutterTs = (short) ((int) (this.mShutterHandler.getShutterTs() * 100.0f));
        this.mMeasureParam.lastShutterTfpa = (short) ((int) (this.mShutterHandler.getShutterTfpa() * 100.0f));
        this.mMeasureParam.lastShutterTlen = (short) ((int) (this.mShutterHandler.getShutterTlen() * 100.0f));
        this.mMeasureParam.lastLastShutterTs = (short) ((int) (this.mShutterHandler.getLastShutterTs() * 100.0f));
        this.mMeasureParam.lastLastShutterTfpa = (short) ((int) (this.mShutterHandler.getLastShutterTfpa() * 100.0f));
        this.mMeasureParam.lastLastShutterTlen = (short) ((int) (this.mShutterHandler.getLastShutterTlen() * 100.0f));
        this.mMeasureParam.changRTfpgIndex = (short) this.mCurrChangeRIndex;
        this.mMeasureParam.jwbLength = this.mJwbNumber;
        this.mMeasureParam.avgB = this.mNativeGuideCore.guideCoreGetAvgB();
        this.mMeasureParam.startupShutterTemp = paramLine.getFixedParamLine().getStartupShutterTemp();
        this.mMeasureParam.bShutterCorrection = needShutterCorrection();
        if (paramLine.getFixedParamLine().getShutterFlag() == 1) {
            this.mCenterTemp = this.mPreCenterTemp;
            Logger.d("guidecore", "exec shutter use previous center temp = " + this.mPreCenterTemp);
        } else {
            if (centerY16Queue.size() == 0) {
                s2 = this.mNativeGuideCore.guideCoreRealTimeGetFilterY16(sArr, this.mCenterIndex, this.rototeType);
                s = this.mNativeGuideCore.guideCoreGetAvgB();
            } else {
                s2 = filterCenterY16;
                s = this.mNativeGuideCore.guideCoreGetAvgB();
            }
            this.mCenterTemp = BaseDataTypeConvertUtils.Companion.float2StrWithOneDecimal(this.mNativeGuideCore.guideCoreRealTimeGetTemp((short) (s2 - s), this.mMeasureParam));
        }
        this.mPreCenterTemp = this.mCenterTemp;
        this.mCenterFoucsTemp = BaseDataTypeConvertUtils.Companion.float2StrWithOneDecimal((((float) this.mMeasureParam.realTimeTfpa) * 1.0f) / 100.0f);
    }

    public String getCenterTemp() {
        return this.mCenterTemp;
    }

    public String getFoucsTemp() {
        return this.mCenterFoucsTemp;
    }

    public void shutter() {
        shutter("");
    }

    public void shutter(String str) {
        ShutterHandler shutterHandler = this.mShutterHandler;
        if (shutterHandler != null) {
            shutterHandler.manualShutterWithoutNuc(str);
        }
    }

    public void sendStopXOrder() {
        Log.v("guidecore", "sendStopXOrder:" + this.isStopXOrderIng);
        if (!this.isStopXOrderIng) {
            new Thread(new Runnable() {
                public void run() {
                    boolean unused = GuideHelper.this.isStopXOrderIng = true;
                    GuideHelper.this.mGuideUsbManager.sendStopGetY16Cmd();
                    OtherUtils.Companion.sleep(100);
                    GuideHelper.this.mGuideUsbManager.sendStopGetY16Cmd();
                    GuideHelper.this.clearReadForStopXOperation();
                    boolean unused2 = GuideHelper.this.isStopXOrderIng = false;
                }
            }).start();
        }
    }

    public void sendResetOrder() {
        Logger.v("dddd", "sendResetOrder");
        this.mGuideUsbManager.sendResetCmd();
    }

    private void saveRawData(String str, String str2) {
        String str3 = str + str2 + File.separator;
        String str4 = str + str2 + File.separator + getCurrentTime() + File.separator;
        File file = new File(str3);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        File file2 = new File(str4);
        if (!file2.exists() && !file2.isDirectory()) {
            file2.mkdir();
        }
        FileUtils.Companion.saveFile(this.xShortDataArr, str4 + "x.raw", false);
        FileUtils.Companion.saveFile(this.bShortDataArr, str4 + "b.raw", false);
        FileUtils.Companion.saveFile(this.mCurrKArray, str4 + "k.raw", false);
        FileUtils.Companion.saveFile(this.mY16Array, str4 + "y16.raw", false);
        FileUtils.Companion.saveFile(this.paramLineArr, str4 + "param.raw", false);
    }

    public void saveIRRawData(String str) {
        saveRawData(str, "frame_ok");
    }

    public void saveBadIRRawData(String str) {
        saveRawData(str, "frame_bad");
    }

    /* access modifiers changed from: private */
    public int initDeviceParam() {
        Logger.d("guidecore", "send stop x cmd begin ");
        this.mGuideUsbManager.sendStopGetY16Cmd();
        OtherUtils.Companion.sleep(10);
        Logger.d("guidecore", "send stop x cmd  end");
        clearReadForStopXOperation();
        this.mJwbNumber = getJpmRNum();
        if (this.mJwbNumber <= 0) {
            Logger.d("guidecore", "mJwbNumber = NULL");
            return -1;
        }
        Logger.d("guidecore", "mJwbNumber = " + this.mJwbNumber);
        this.mJwtTabArrShort = new short[this.mJwbNumber];
        byte[] jwTab = getJwTab(this.mJwbNumber);
        if (jwTab == null) {
            return -1;
        }
        this.mNativeGuideCore.guideCoreByteArr2ShortArr(jwTab, this.mJwtTabArrShort);
        this.mHeader = getHeader();
        this.mDeviceSN = getDeviceSN();
        this.mDeviceSNStr = "";
        for (int i = 0; i < this.mDeviceSN.length; i++) {
            this.mDeviceSNStr += ((char) this.mDeviceSN[i]);
        }
        for (int i2 = 0; i2 < this.mJwbNumber; i2++) {
            Logger.d("guidecore", "mJwbNumber " + i2 + SQLConstants.EQUAL + this.mJwtTabArrShort[i2]);
        }
        this.mAllKArray = new short[(this.mJwbNumber * 120 * 92)];
        byte[] allKData = getAllKData(this.mJwbNumber);
        if (allKData != null) {
            this.mNativeGuideCore.guideCoreByteArr2ShortArr(allKData, this.mAllKArray);
            System.arraycopy(this.mAllKArray, 0, this.mCurrKArray, 0, 11040);
            Logger.v("guidecore", "获取K成功");
            this.mAllCurveShortArray = new short[(this.mJwbNumber * 600 * 2)];
            byte[] allCurveData = getAllCurveData(this.mJwbNumber);
            if (allCurveData != null) {
                this.mNativeGuideCore.guideCoreByteArr2ShortArr(allCurveData, this.mAllCurveShortArray);
                Logger.v("guidecore", "获取曲线成功");
                return 0;
            }
        }
        return -1;
    }

    public void registUsbStatus(UsbStatusInterface usbStatusInterface) {
        try {
            this.mGuideUsbManager.registerUsbStatusReceiver(usbStatusInterface);
            this.specialUsbStatusInterface = usbStatusInterface;
            this.mIsRegisterUsbStatus.set(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRigistUsbStatus() {
        try {
            if (this.mIsRegisterUsbStatus.get()) {
                this.mGuideUsbManager.unRegisterUsbStatusReceiver();
                this.mIsRegisterUsbStatus.set(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHumanTemp(float f, float f2) {
        this.ambientTempForDebug = f2;
        if (f2 == -1314.0f) {
            f2 = this.envTemp;
        }
        return this.mNativeGuideCore != null ? BaseDataTypeConvertUtils.Companion.float2StrWithOneDecimal(this.mNativeGuideCore.guideCoreGetMapTemperature(f2, f, this.mMeasureParam)) : "";
    }

    public void setDistance(float f) {
        MeasureParam measureParam = this.mMeasureParam;
        if (measureParam != null) {
            measureParam.distance = (short) ((int) (f * 100.0f));
        }
    }

    public String getSN() {
        return this.mDeviceSNStr;
    }

    public String getFirmwareVersion() {
        if (this.mParamLine == null) {
            return "";
        }
        return this.mParamLine.getFixedParamLine().getUsbDeviceVersion() + "";
    }

    public String getEnvTemp() {
        return BaseDataTypeConvertUtils.Companion.float2StrWithTwoDecimal(this.envTemp);
    }

    public boolean isCalTempOk() {
        return this.isTempOk;
    }

    public synchronized int getNucRetryCount() {
        return this.nucRetryCount;
    }

    public synchronized void setNucRetryCount(int i) {
        this.nucRetryCount = i;
    }

    private boolean isFirmwareUpgradeFileValid(byte[] bArr) {
        if (bArr.length < 8) {
            Logger.d("guidecore", "FU data length < 8");
            return false;
        } else if (bArr[0] != 85 || bArr[1] != -86) {
            Logger.d("guidecore", "FU tag not 55AA");
            return false;
        } else if ((((bArr[7] & 255) << 8) | (bArr[6] & 255)) != bArr.length - 8) {
            return false;
        } else {
            int length = bArr.length - 8;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 8, bArr2, 0, bArr.length - 8);
            byte b = ((bArr[5] & 255) << Ascii.CAN) | ((bArr[4] & 255) << 16);
            if (((bArr[2] & 255) | ((bArr[3] & 255) << 8) | b) == this.mNativeGuideCore.guideCoreCalcCRC32(bArr2, length)) {
                return true;
            }
            Logger.d("guidecore", "FU crc32 check failed");
            return false;
        }
    }

    private boolean isFirmwareUpgradeDataWriteIn(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[(i * 2048)];
        byte[] bArr3 = new byte[2048];
        for (int i3 = 0; i3 < i; i3++) {
            short s = (short) (i3 + 128);
            if (s > 191) {
                return false;
            }
            writeIrUsbDevice(this.mGuideUsbManager.getFirmwareUpgradeDataCmd(s, FIRMWARE_UPGRADE_PAGE_SIZE));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.d("guidecore", "FU try to read: readLen " + readIrUsbDevice(bArr3));
            System.arraycopy(bArr3, 0, bArr2, i3 * 2048, 2048);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        for (int i4 = 0; i4 < i; i4++) {
            short s2 = (short) (i4 + 128);
            if (s2 > 191) {
                return false;
            }
            writeIrUsbDevice(this.mGuideUsbManager.getFirmwareUpgradeDataCmd(s2, FIRMWARE_UPGRADE_PAGE_SIZE));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e3) {
                e3.printStackTrace();
            }
            Logger.d("guidecore", "FU read readLen " + readIrUsbDevice(bArr3));
            System.arraycopy(bArr3, 0, bArr2, i4 * 2048, 2048);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e4) {
                e4.printStackTrace();
            }
        }
        byte[] bArr4 = new byte[i2];
        System.arraycopy(bArr2, 0, bArr4, 0, i2);
        int guideCoreCalcCRC32 = this.mNativeGuideCore.guideCoreCalcCRC32(bArr4, i2);
        byte b = (bArr[2] & 255) | ((bArr[5] & 255) << Ascii.CAN) | ((bArr[4] & 255) << 16) | ((bArr[3] & 255) << 8);
        Logger.d("guidecore", "FU crcValue " + b);
        Logger.d("guidecore", "FU crcRead " + guideCoreCalcCRC32);
        if (b != guideCoreCalcCRC32) {
            return false;
        }
        return true;
    }

    private void beforeFirmwareUpgrade() {
        Logger.d("guidecore", "FU beforeFirmwareUpgrade");
        this.mShutterHandler.setPauseShutter(true);
        this.isfirmwareUpgradeReadData = true;
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.d("guidecore", "FU clean read start");
        clearReadForStopXOperation();
        Logger.d("guidecore", "FU clean read end");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        this.mGuideUsbManager.sendStopGetY16Cmd();
    }

    private void afterFirmwareUpgrade() {
        Logger.d("guidecore", "FU afterFirmwareUpgrade");
        this.mShutterHandler.setPauseShutter(false);
        this.mGuideUsbManager.sendStartGetY16Cmd();
        this.isfirmwareUpgradeReadData = false;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:72:0x00f1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.guide.guidecore.FirmwareUpgradeResultCode firmwareUpgrade(java.lang.String r11) {
        /*
            r10 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 == 0) goto L_0x0009
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.FILE_ERROR
            return r11
        L_0x0009:
            java.io.File r0 = new java.io.File
            r0.<init>(r11)
            boolean r11 = r0.exists()
            if (r11 != 0) goto L_0x0017
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.FILE_NOT_EXISTS
            return r11
        L_0x0017:
            com.guide.guidecore.GuideUsbManager r11 = r10.mGuideUsbManager
            boolean r11 = r11.isUsbValid()
            if (r11 != 0) goto L_0x0022
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.USB_DEVICE_ERROR
            return r11
        L_0x0022:
            java.io.ByteArrayOutputStream r11 = new java.io.ByteArrayOutputStream
            long r1 = r0.length()
            int r1 = (int) r1
            r11.<init>(r1)
            r1 = 0
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x00f1 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00f1 }
            r3.<init>(r0)     // Catch:{ Exception -> 0x00f1 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00f1 }
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r0]     // Catch:{ Exception -> 0x00f0 }
        L_0x003b:
            r3 = 0
            int r4 = r2.read(r1, r3, r0)     // Catch:{ Exception -> 0x00f0 }
            r5 = -1
            if (r5 == r4) goto L_0x0047
            r11.write(r1, r3, r4)     // Catch:{ Exception -> 0x00f0 }
            goto L_0x003b
        L_0x0047:
            byte[] r11 = r11.toByteArray()
            boolean r0 = r10.isFirmwareUpgradeFileValid(r11)
            if (r0 != 0) goto L_0x0056
            r10.isfirmwareUpgradeReadData = r3
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.INVALID_FILE_ERROR
            return r11
        L_0x0056:
            boolean r0 = r10.isRenderStart
            if (r0 != 0) goto L_0x005d
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.RENDER_DATA_ERROR
            return r11
        L_0x005d:
            boolean r0 = r10.isRenderStart
            java.lang.String r1 = "guidecore"
            if (r0 == 0) goto L_0x006f
            boolean r0 = r10.isShutter
            if (r0 == 0) goto L_0x006f
            java.lang.String r11 = "FU isShutter = true, return"
            com.guide.guidecore.Logger.d((java.lang.String) r1, (java.lang.String) r11)
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.RENDER_DATA_ERROR
            return r11
        L_0x006f:
            boolean r0 = r10.isRenderStart
            if (r0 == 0) goto L_0x007f
            boolean r0 = r10.isFirstNucOrShutterFinish
            if (r0 != 0) goto L_0x007f
            java.lang.String r11 = "FU isFirstNucOrShutterFinish = false, return"
            com.guide.guidecore.Logger.d((java.lang.String) r1, (java.lang.String) r11)
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.RENDER_DATA_ERROR
            return r11
        L_0x007f:
            r10.beforeFirmwareUpgrade()
            int r0 = r11.length
            r1 = 8
            int r0 = r0 - r1
            byte[] r2 = new byte[r0]
            int r4 = r11.length
            int r4 = r4 - r1
            java.lang.System.arraycopy(r11, r1, r2, r3, r4)
            int r1 = r0 / 2048
            int r4 = r0 % 2048
            if (r4 == 0) goto L_0x0095
            int r1 = r1 + 1
        L_0x0095:
            int r4 = r1 * 2048
            byte[] r6 = new byte[r4]
            r7 = r3
        L_0x009a:
            if (r7 >= r4) goto L_0x00a1
            r6[r7] = r5
            int r7 = r7 + 1
            goto L_0x009a
        L_0x00a1:
            java.lang.System.arraycopy(r2, r3, r6, r3, r0)
            r2 = 2048(0x800, float:2.87E-42)
            byte[] r4 = new byte[r2]
            r5 = r3
        L_0x00a9:
            if (r5 >= r1) goto L_0x00de
            int r7 = r5 + 128
            short r7 = (short) r7
            r8 = 191(0xbf, float:2.68E-43)
            if (r7 <= r8) goto L_0x00b8
            r10.afterFirmwareUpgrade()
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.PAGE_ERROR
            return r11
        L_0x00b8:
            com.guide.guidecore.GuideUsbManager r8 = r10.mGuideUsbManager
            byte[] r7 = r8.firmwareUpgradeCmd(r7, r2)
            r10.writeIrUsbDevice(r7)
            r7 = 300(0x12c, double:1.48E-321)
            java.lang.Thread.sleep(r7)     // Catch:{ InterruptedException -> 0x00c7 }
            goto L_0x00cb
        L_0x00c7:
            r9 = move-exception
            r9.printStackTrace()
        L_0x00cb:
            int r9 = r5 * 2048
            java.lang.System.arraycopy(r6, r9, r4, r3, r2)
            r10.writeIrUsbDevice(r4)
            java.lang.Thread.sleep(r7)     // Catch:{ InterruptedException -> 0x00d7 }
            goto L_0x00db
        L_0x00d7:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00db:
            int r5 = r5 + 1
            goto L_0x00a9
        L_0x00de:
            boolean r11 = r10.isFirmwareUpgradeDataWriteIn(r11, r1, r0)
            if (r11 != 0) goto L_0x00ea
            r10.afterFirmwareUpgrade()
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.FILE_WRITE_ERROR
            return r11
        L_0x00ea:
            r10.afterFirmwareUpgrade()
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.SUCCESS
            return r11
        L_0x00f0:
            r1 = r2
        L_0x00f1:
            r1.close()     // Catch:{ Exception -> 0x00f8 }
            r11.close()     // Catch:{ Exception -> 0x00f8 }
            goto L_0x00fc
        L_0x00f8:
            r11 = move-exception
            r11.printStackTrace()
        L_0x00fc:
            com.guide.guidecore.FirmwareUpgradeResultCode r11 = com.guide.guidecore.FirmwareUpgradeResultCode.FILE_READ_ERROR
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.GuideHelper.firmwareUpgrade(java.lang.String):com.guide.guidecore.FirmwareUpgradeResultCode");
    }

    public short getNearKf() {
        return (short) this.mParamLine.getCustomParamLine().getNearKf();
    }

    public short getNearB() {
        return (short) this.mParamLine.getCustomParamLine().getNearB();
    }

    public short getFarKf() {
        return (short) this.mParamLine.getCustomParamLine().getFarKf();
    }

    public short getFarB() {
        return (short) this.mParamLine.getCustomParamLine().getFarB();
    }

    public short getNearKf2() {
        return (short) this.mParamLine.getCustomParamLine().getNearkf2();
    }

    public short getNearB2() {
        return (short) this.mParamLine.getCustomParamLine().getNearb2();
    }

    public short getFarKf2() {
        return (short) this.mParamLine.getCustomParamLine().getFarkf2();
    }

    public short getFarB2() {
        return (short) this.mParamLine.getCustomParamLine().getFarb2();
    }

    public void setNearKf(short s) {
        setExpertParam(8, s);
    }

    public void setNearB(short s) {
        setExpertParam(7, s);
    }

    public void setFarKf(short s) {
        setExpertParam(11, s);
    }

    public void setFarB(short s) {
        setExpertParam(10, s);
    }

    public boolean setNearKf2(short s) {
        if (s < 9000 || s > 11000) {
            return false;
        }
        setExpertParam(14, s);
        return true;
    }

    public boolean setNearB2(short s) {
        if (s < -300 || s > 300) {
            return false;
        }
        setExpertParam(13, s);
        return true;
    }

    public boolean setFarKf2(short s) {
        if (s < 9000 || s > 11000) {
            return false;
        }
        setExpertParam(16, s);
        return true;
    }

    public boolean setFarB2(short s) {
        if (s < -300 || s > 300) {
            return false;
        }
        setExpertParam(15, s);
        return true;
    }

    private void setExpertParam(int i, short s) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mGuideUsbManager.sendStopGetY16Cmd();
        writeIrUsbDevice(this.mGuideUsbManager.setExpertParamCmd());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        short[] sArr = new short[32];
        System.arraycopy(this.mPraseSrcData.getCurrParamLine(), 72, sArr, 0, 32);
        short[] sArr2 = new short[32];
        System.arraycopy(sArr, 0, sArr2, 0, 32);
        sArr2[i] = s;
        writeIrUsbDevice(BaseDataTypeConvertUtils.Companion.convertShortArr2LittleEndianByteArr(sArr2));
        try {
            Thread.sleep(300);
        } catch (InterruptedException e3) {
            e3.printStackTrace();
        }
        this.mGuideUsbManager.sendStartGetY16Cmd();
    }

    public void startSDKDebug(String str, String str2) {
        if (this.mNativeGuideCore != null) {
            NativeGuideCore.guideCoreMtLogStart(str, str2);
        }
    }

    public void stopSDKDebug() {
        if (this.mNativeGuideCore != null) {
            NativeGuideCore.guideCoreMtLogStop();
        }
    }

    public void setAutoShutter(boolean z, long j, long j2) {
        this.autoShutterFromUser = z;
        this.autoShutterPeriodFromUser = j;
        this.autoShutterDelayFromUser = j2;
    }

    public void setBright(int i) {
        if (i >= 0 && i <= 100) {
            int i2 = (int) ((((double) i) * 1.8d) + 0.0d + 0.5d);
            NativeGuideCore nativeGuideCore = this.mNativeGuideCore;
            if (nativeGuideCore != null) {
                nativeGuideCore.guideCoreSetBright(i2);
            }
        }
    }

    public void setContrast(int i) {
        if (i >= 0 && i <= 100) {
            int i2 = (int) ((((double) i) * 2.4d) + 60.0d + 0.5d);
            NativeGuideCore nativeGuideCore = this.mNativeGuideCore;
            if (nativeGuideCore != null) {
                nativeGuideCore.guideCoreSetContrast(i2);
            }
        }
    }

    public void controlImageOptimizer(boolean z) {
        NativeGuideCore nativeGuideCore = this.mNativeGuideCore;
        if (nativeGuideCore != null) {
            nativeGuideCore.guideCoreOpenImageOptimizer(z);
        }
    }

    public void nucTest() {
        ShutterHandler shutterHandler = this.mShutterHandler;
        if (shutterHandler != null) {
            shutterHandler.nucTest(this.mParamLine.getFixedParamLine().getRealtimeFpaTemp());
        }
    }

    public short autoCorrectCalcY16(final AutoCorrectCalcY16Mode autoCorrectCalcY16Mode) {
        if (autoCorrectCalcY16Mode == AutoCorrectCalcY16Mode.near30 || autoCorrectCalcY16Mode == AutoCorrectCalcY16Mode.near33 || autoCorrectCalcY16Mode == AutoCorrectCalcY16Mode.near36) {
            setDistance(0.5f);
        } else {
            setDistance(1.2f);
        }
        if (this.isCalcY16) {
            Logger.d("guidecore", "calcY16 calc in process, return");
            return ShortCompanionObject.MIN_VALUE;
        }
        this.isCalcY16 = true;
        final ArrayList arrayList = new ArrayList();
        ShutterHandler shutterHandler = this.mShutterHandler;
        if (shutterHandler != null) {
            shutterHandler.setPauseShutter(true);
            while (true) {
                if (!this.isShutter && this.isFirstNucOrShutterFinish) {
                    break;
                }
                Logger.d("guidecore", "autoCorrectCalcY16 isShutter = " + this.isShutter + " isFirstNucOrShutterFinish = " + this.isFirstNucOrShutterFinish);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.mAutoCorrectInterface = new AutoCorrectInterface() {
            public void shutterEnd() {
                Logger.d("guidecore", "autoCorrectCalcY16 autoCorrectShutterCount = " + GuideHelper.this.autoCorrectShutterCount);
                try {
                    Thread.sleep(ShutterHandler.NUC_TIME_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GuideHelper.this.mNativeGuideCore.guideCoreGetCurrentY16(GuideHelper.this.mTempY16Array);
                short guideCoreRealTimeGetFilterY16 = (short) (GuideHelper.this.mNativeGuideCore.guideCoreRealTimeGetFilterY16(GuideHelper.this.mTempY16Array, GuideHelper.this.mCenterIndex, GuideHelper.this.rototeType) - GuideHelper.this.mNativeGuideCore.guideCoreGetAvgB());
                arrayList.add(Short.valueOf(guideCoreRealTimeGetFilterY16));
                Logger.d("guidecore", "autoCorrectCalcY16 temp2sY16 " + guideCoreRealTimeGetFilterY16);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                GuideHelper.this.mNativeGuideCore.guideCoreGetCurrentY16(GuideHelper.this.mTempY16Array);
                short guideCoreRealTimeGetFilterY162 = (short) (GuideHelper.this.mNativeGuideCore.guideCoreRealTimeGetFilterY16(GuideHelper.this.mTempY16Array, GuideHelper.this.mCenterIndex, GuideHelper.this.rototeType) - GuideHelper.this.mNativeGuideCore.guideCoreGetAvgB());
                arrayList.add(Short.valueOf(guideCoreRealTimeGetFilterY162));
                Logger.d("guidecore", "autoCorrectCalcY16 temp3sY16 " + guideCoreRealTimeGetFilterY162);
                if (GuideHelper.this.autoCorrectShutterCount == 3) {
                    long j = 0;
                    int i = 0;
                    for (Short shortValue : arrayList) {
                        j += (long) shortValue.shortValue();
                        i++;
                    }
                    long j2 = j / ((long) i);
                    short s = (short) ((int) j2);
                    switch (AnonymousClass10.$SwitchMap$com$zktechnology$android$utils$GuideHelper$AutoCorrectCalcY16Mode[autoCorrectCalcY16Mode.ordinal()]) {
                        case 1:
                            short unused = GuideHelper.this.autoCorrectNear30Y16 = s;
                            break;
                        case 2:
                            short unused2 = GuideHelper.this.autoCorrectNear33Y16 = s;
                            break;
                        case 3:
                            short unused3 = GuideHelper.this.autoCorrectNear36Y16 = s;
                            break;
                        case 4:
                            short unused4 = GuideHelper.this.autoCorrectFar30Y16 = s;
                            break;
                        case 5:
                            short unused5 = GuideHelper.this.autoCorrectFar33Y16 = s;
                            break;
                        case 6:
                            short unused6 = GuideHelper.this.autoCorrectFar36Y16 = s;
                            break;
                    }
                    Logger.d("guidecore", autoCorrectCalcY16Mode + " autoCorrectCalcY16 sum = " + j + " count = " + i + " avg = " + j2);
                    AutoCorrectInterface unused7 = GuideHelper.this.mAutoCorrectInterface = null;
                    if (GuideHelper.this.mShutterHandler != null) {
                        GuideHelper.this.mShutterHandler.setPauseShutter(false);
                    }
                    boolean unused8 = GuideHelper.this.isCalcY16 = false;
                }
            }
        };
        this.autoCorrectShutterCount = 0;
        if (this.autoCorrectTimer == null) {
            this.autoCorrectTimer = new Timer();
        }
        if (this.autoCorrectTimerTask == null) {
            this.autoCorrectTimerTask = new TimerTask() {
                public void run() {
                    GuideHelper.access$2308(GuideHelper.this);
                    if (GuideHelper.this.autoCorrectShutterCount == 3) {
                        Logger.d("guidecore", "autoCorrectCalcY16 autoCorrectTimerTask cancel");
                        GuideHelper.this.autoCorrectTimerTask.cancel();
                        TimerTask unused = GuideHelper.this.autoCorrectTimerTask = null;
                        GuideHelper.this.autoCorrectTimer.cancel();
                        Timer unused2 = GuideHelper.this.autoCorrectTimer = null;
                    }
                    Logger.d("guidecore", "autoCorrectCalcY16 send AUTO_CORRECT_SHUTTER");
                    GuideHelper.this.shutter(ShutterHandler.AUTO_CORRECT_SHUTTER);
                }
            };
        }
        this.autoCorrectTimer.schedule(this.autoCorrectTimerTask, 0, 6000);
        while (this.isCalcY16) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        if (autoCorrectCalcY16Mode == AutoCorrectCalcY16Mode.near30) {
            return this.autoCorrectNear30Y16;
        }
        if (autoCorrectCalcY16Mode == AutoCorrectCalcY16Mode.near33) {
            return this.autoCorrectNear33Y16;
        }
        if (autoCorrectCalcY16Mode == AutoCorrectCalcY16Mode.near36) {
            return this.autoCorrectNear36Y16;
        }
        if (autoCorrectCalcY16Mode == AutoCorrectCalcY16Mode.far30) {
            return this.autoCorrectFar30Y16;
        }
        if (autoCorrectCalcY16Mode == AutoCorrectCalcY16Mode.far33) {
            return this.autoCorrectFar33Y16;
        }
        if (autoCorrectCalcY16Mode == AutoCorrectCalcY16Mode.far36) {
            return this.autoCorrectFar36Y16;
        }
        return ShortCompanionObject.MIN_VALUE;
    }

    /* renamed from: com.zktechnology.android.utils.GuideHelper$10  reason: invalid class name */
    static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$com$zktechnology$android$utils$GuideHelper$AutoCorrectCalcY16Mode;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|14) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.zktechnology.android.utils.GuideHelper$AutoCorrectCalcY16Mode[] r0 = com.zktechnology.android.utils.GuideHelper.AutoCorrectCalcY16Mode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$zktechnology$android$utils$GuideHelper$AutoCorrectCalcY16Mode = r0
                com.zktechnology.android.utils.GuideHelper$AutoCorrectCalcY16Mode r1 = com.zktechnology.android.utils.GuideHelper.AutoCorrectCalcY16Mode.near30     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$zktechnology$android$utils$GuideHelper$AutoCorrectCalcY16Mode     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zktechnology.android.utils.GuideHelper$AutoCorrectCalcY16Mode r1 = com.zktechnology.android.utils.GuideHelper.AutoCorrectCalcY16Mode.near33     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$zktechnology$android$utils$GuideHelper$AutoCorrectCalcY16Mode     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.zktechnology.android.utils.GuideHelper$AutoCorrectCalcY16Mode r1 = com.zktechnology.android.utils.GuideHelper.AutoCorrectCalcY16Mode.near36     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$zktechnology$android$utils$GuideHelper$AutoCorrectCalcY16Mode     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.zktechnology.android.utils.GuideHelper$AutoCorrectCalcY16Mode r1 = com.zktechnology.android.utils.GuideHelper.AutoCorrectCalcY16Mode.far30     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$zktechnology$android$utils$GuideHelper$AutoCorrectCalcY16Mode     // Catch:{ NoSuchFieldError -> 0x003e }
                com.zktechnology.android.utils.GuideHelper$AutoCorrectCalcY16Mode r1 = com.zktechnology.android.utils.GuideHelper.AutoCorrectCalcY16Mode.far33     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$zktechnology$android$utils$GuideHelper$AutoCorrectCalcY16Mode     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.zktechnology.android.utils.GuideHelper$AutoCorrectCalcY16Mode r1 = com.zktechnology.android.utils.GuideHelper.AutoCorrectCalcY16Mode.far36     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.GuideHelper.AnonymousClass10.<clinit>():void");
        }
    }

    public boolean autoCorrect(AutoCorrectResult autoCorrectResult, boolean z, boolean z2, float f, float f2, float f3) {
        NativeGuideCore nativeGuideCore = this.mNativeGuideCore;
        if (nativeGuideCore == null) {
            return false;
        }
        MeasureParam measureParam = this.mMeasureParam;
        if (z2) {
            return nativeGuideCore.guideCoreAutoCorrectFar(autoCorrectResult, z, measureParam, this.autoCorrectFar30Y16, this.autoCorrectFar33Y16, this.autoCorrectFar36Y16, f, f2, f3);
        }
        return nativeGuideCore.guideCoreAutoCorrectNear(autoCorrectResult, z, measureParam, this.autoCorrectNear30Y16, this.autoCorrectNear33Y16, this.autoCorrectNear36Y16, f, f2, f3);
    }

    public boolean autoCorrect(AutoCorrectResult autoCorrectResult, boolean z, float f, float f2, float f3) {
        return autoCorrect(autoCorrectResult, true, z, f, f2, f3);
    }

    public void saveAutoCorrectResult(boolean z, boolean z2, AutoCorrectResult autoCorrectResult) {
        float f;
        int i;
        int i2;
        short s;
        if (z) {
            if (z2) {
                i2 = 16;
                i = 15;
                s = (short) ((int) (autoCorrectResult.farKf * 10000.0f));
                f = autoCorrectResult.farB;
            } else {
                i2 = 14;
                i = 13;
                s = (short) ((int) (autoCorrectResult.nearKf * 10000.0f));
                f = autoCorrectResult.nearB;
            }
        } else if (z2) {
            i2 = 11;
            i = 10;
            s = (short) ((int) (autoCorrectResult.farKf * 10000.0f));
            f = autoCorrectResult.farB;
        } else {
            i2 = 8;
            i = 7;
            s = (short) ((int) (autoCorrectResult.nearKf * 10000.0f));
            f = autoCorrectResult.nearB;
        }
        setExpertParam(i2, s);
        setExpertParam(i, (short) ((int) (f * 100.0f)));
    }

    public void saveAutoCorrectResult(boolean z, AutoCorrectResult autoCorrectResult) {
        saveAutoCorrectResult(true, z, autoCorrectResult);
    }

    public boolean getColdHotState() {
        return this.rebootColdHotState;
    }

    public void getTempMatrix(float[] fArr, short[] sArr, int i, int i2) {
        NativeGuideCore nativeGuideCore = this.mNativeGuideCore;
        if (nativeGuideCore != null) {
            nativeGuideCore.guideCoreGetTempMatrix(fArr, sArr, i, i2, this.mMeasureParam);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void changeR(short r7) {
        /*
            r6 = this;
            short[] r0 = r6.mJwtTabArrShort
            r1 = 0
            short r2 = r0[r1]
            if (r7 > r2) goto L_0x0009
        L_0x0007:
            r0 = r1
            goto L_0x003e
        L_0x0009:
            short r2 = r6.mJwbNumber
            r3 = 1
            int r2 = r2 - r3
            short r0 = r0[r2]
            if (r7 < r0) goto L_0x0015
            short r0 = r6.mJwbNumber
            int r0 = r0 - r3
            goto L_0x003e
        L_0x0015:
            short r0 = r6.mJwbNumber
            if (r3 >= r0) goto L_0x0007
            short[] r0 = r6.mJwtTabArrShort
            short r2 = r0[r3]
            if (r7 >= r2) goto L_0x003b
            int r2 = r6.mCurrChangeRIndex
            int r4 = r3 + -1
            r5 = 50
            if (r2 != r4) goto L_0x002f
            short r0 = r0[r4]
            int r0 = r7 - r0
            if (r0 >= r5) goto L_0x0039
        L_0x002d:
            r0 = r4
            goto L_0x003e
        L_0x002f:
            int r4 = r3 + 1
            if (r2 != r4) goto L_0x0039
            short r0 = r0[r3]
            int r0 = r0 - r7
            if (r0 >= r5) goto L_0x0039
            goto L_0x002d
        L_0x0039:
            r0 = r3
            goto L_0x003e
        L_0x003b:
            int r3 = r3 + 1
            goto L_0x0015
        L_0x003e:
            int r2 = r6.mCurrChangeRIndex
            if (r2 == r0) goto L_0x0082
            short[] r2 = r6.mAllKArray
            int r3 = r0 * 11040
            short[] r4 = r6.mCurrKArray
            r5 = 11040(0x2b20, float:1.547E-41)
            java.lang.System.arraycopy(r2, r3, r4, r1, r5)
            com.guide.guidecore.jni.NativeGuideCore r1 = r6.mNativeGuideCore
            short[] r2 = r6.mCurrKArray
            r1.guideCoreSetCurrentK(r2)
            r6.mCurrChangeRIndex = r0
            com.guide.guidecore.GuideUsbManager r1 = r6.mGuideUsbManager
            byte[] r0 = r1.getChangeRCmd(r0)
            r6.writeIrUsbDevice(r0)
            com.guide.guidecore.utils.ShutterHandler r0 = r6.mShutterHandler
            float r7 = (float) r7
            r1 = 1120403456(0x42c80000, float:100.0)
            float r7 = r7 / r1
            r0.manualShutter(r7)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r0 = "ChangeR mCurrChangeRIndex = "
            java.lang.StringBuilder r7 = r7.append(r0)
            int r0 = r6.mCurrChangeRIndex
            java.lang.StringBuilder r7 = r7.append(r0)
            java.lang.String r7 = r7.toString()
            java.lang.String r0 = "guidecore"
            com.guide.guidecore.Logger.d((java.lang.String) r0, (java.lang.String) r7)
        L_0x0082:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.GuideHelper.changeR(short):void");
    }

    private short getJpmRNum() {
        Logger.d("guidecore", "getJpmRNum enter");
        writeIrUsbDevice(this.mGuideUsbManager.getJwTabNumCmd());
        byte[] bArr = new byte[32];
        short[] sArr = new short[16];
        int readIrUsbDevice = readIrUsbDevice(bArr);
        int i = 0;
        while (i < 3 && readIrUsbDevice < 0) {
            readIrUsbDevice = readIrUsbDevice(bArr);
            i++;
            Logger.d("guidecore", "get jpm num failed next");
        }
        Logger.d("guidecore", "get jpm num succ succ len:" + readIrUsbDevice);
        if (readIrUsbDevice == 2) {
            this.mNativeGuideCore.guideCoreByteArr2ShortArr(bArr, sArr);
            return sArr[0];
        }
        Logger.d("guidecore", "get jpm num failed");
        return 0;
    }

    private byte[] getJwTab(int i) {
        if (i == 0) {
            return null;
        }
        writeIrUsbDevice(this.mGuideUsbManager.getJwTabCmd(i));
        byte[] bArr = new byte[(i * 2)];
        do {
        } while (readIrUsbDevice(bArr) <= 0);
        return bArr;
    }

    private byte[] getHeader() {
        writeIrUsbDevice(this.mGuideUsbManager.getHeaderCmd());
        byte[] bArr = new byte[216];
        do {
        } while (readIrUsbDevice(bArr) <= 0);
        return bArr;
    }

    private byte[] getDeviceSN() {
        writeIrUsbDevice(this.mGuideUsbManager.getDeviceSNCmd());
        byte[] bArr = new byte[14];
        do {
        } while (readIrUsbDevice(bArr) <= 0);
        return bArr;
    }

    private int calcReadLagerTime(int i) {
        int i2 = i / GuideUsbManager.LAGER_MAX_LENGTH;
        return i % GuideUsbManager.LAGER_MAX_LENGTH != 0 ? i2 + 1 : i2;
    }

    private byte[] getAllKData(int i) {
        int i2 = i * 120 * 92 * 2;
        int calcReadLagerTime = calcReadLagerTime(i2);
        byte[] bArr = new byte[i2];
        byte[] bArr2 = new byte[(calcReadLagerTime * GuideUsbManager.LAGER_MAX_LENGTH)];
        for (int i3 = 0; i3 < calcReadLagerTime; i3++) {
            short s = GuideUsbManager.START_PAGE_K;
            if (i3 > 0) {
                s = (short) ((i3 * 25) + 300);
            }
            writeIrUsbDevice(this.mGuideUsbManager.getKDiskDataCmd(s, 51201));
            byte[] bArr3 = new byte[51201];
            int i4 = 0;
            while (i4 < 51201) {
                int readIrUsbDevice = readIrUsbDevice(this.mOnePageMaxBuff);
                if (readIrUsbDevice >= 0) {
                    System.arraycopy(this.mOnePageMaxBuff, 0, bArr3, i4, readIrUsbDevice);
                    i4 += readIrUsbDevice;
                }
            }
            System.arraycopy(bArr3, 0, bArr2, i3 * GuideUsbManager.LAGER_MAX_LENGTH, GuideUsbManager.LAGER_MAX_LENGTH);
        }
        System.arraycopy(bArr2, 0, bArr, 0, i2);
        return bArr;
    }

    private byte[] getAllCurveData(int i) {
        int i2 = i * GuideUsbManager.CURVE_LENGTH * 2 * 2;
        boolean z = i2 % 64 == 0;
        if (z) {
            i2++;
        }
        byte[] bArr = new byte[i2];
        writeIrUsbDevice(this.mGuideUsbManager.getDiskDataCmd(GuideUsbManager.START_PAGE_CURVE, 0, i2));
        int i3 = 0;
        while (i3 < i2) {
            int readIrUsbDevice = readIrUsbDevice(this.mOnePageMaxBuff);
            if (readIrUsbDevice >= 0) {
                System.arraycopy(this.mOnePageMaxBuff, 0, bArr, i3, readIrUsbDevice);
                i3 += readIrUsbDevice;
            }
        }
        if (!z) {
            return bArr;
        }
        int i4 = i2 - 1;
        byte[] bArr2 = new byte[i4];
        System.arraycopy(bArr, 0, bArr2, 0, i4);
        return bArr2;
    }

    /* access modifiers changed from: private */
    public void clearReadForStopXOperation() {
        if (this.mGuideUsbManager.isUsbValid()) {
            int i = 0;
            this.clearCount = 0;
            while (i != -1 && this.clearCount < 50) {
                i = readIrUsbDevice(this.mOnePageBuff);
                Logger.d("guidecore", "CLEAR READ clearLen=" + i);
                this.clearCount++;
            }
            if (this.clearCount >= 50) {
                Logger.d("guidecore", "CLEAR READ count is MAX COUNT");
                return;
            }
            return;
        }
        Logger.d("guidecore", "usb is invalid");
    }

    private void clearRead() {
        int i = 0;
        while (i != -1) {
            i = readIrUsbDevice(this.mOnePageBuff);
            Logger.d("guidecore", "CLEAR READ");
        }
    }

    private int writeIrUsbDevice(byte[] bArr) {
        return this.mGuideUsbManager.writeIrUsbDevice(bArr);
    }

    private int readIrUsbDevice(byte[] bArr) {
        return this.mGuideUsbManager.readIrUsbDevice(bArr);
    }

    /* access modifiers changed from: private */
    public String getCurrentTime() {
        Date date = new Date();
        date.toLocaleString();
        return new SimpleDateFormat(ZKConstantConfig.DATE_FORMAT_2, Locale.US).format(date);
    }

    public void startRecordDebugData(final String str) {
        this.recordDebugDataPath = str;
        FileUtils.Companion.appFile("时刻\t中心点X\t中心点Y16\t实时快门温度\t实时镜筒温度\t实时焦温\t快门标志位\tNUC标志位\t环境温度\t体表中心温度\t体内中心温度\t最高温X\t最高温Y16\t体表最高温\t体内最高温\t最高温X坐标\t最高温Y坐标\t本底均值\t档位\r\n".getBytes(), str);
        this.recordDebugDataTimer = new Timer();
        AnonymousClass9 r2 = new TimerTask() {
            public void run() {
                if (GuideHelper.this.xShortDataArr != null) {
                    try {
                        float floatValue = Float.valueOf(GuideHelper.this.mCenterTemp).floatValue();
                        boolean shutterRecordFlag = GuideHelper.this.mShutterHandler.getShutterRecordFlag();
                        if (shutterRecordFlag) {
                            GuideHelper.this.mShutterHandler.setShutterRecordFlag(false);
                        }
                        GuideHelper guideHelper = GuideHelper.this;
                        String humanTemp = guideHelper.getHumanTemp(floatValue, guideHelper.ambientTempForDebug);
                        int length = GuideHelper.this.mY16Array.length;
                        short s = Short.MIN_VALUE;
                        short s2 = Short.MIN_VALUE;
                        int i = 0;
                        for (int i2 = 0; i2 < length; i2++) {
                            if (s < GuideHelper.this.mY16Array[i2]) {
                                short s3 = GuideHelper.this.xShortDataArr[i2];
                                s = GuideHelper.this.mY16Array[i2];
                                s2 = s3;
                                i = i2;
                            }
                        }
                        int i3 = 120;
                        if (GuideHelper.this.rototeType == 1 || GuideHelper.this.rototeType == 3) {
                            i3 = 90;
                        }
                        int i4 = i % i3;
                        int i5 = i / i3;
                        String measureTemByY16 = GuideHelper.this.measureTemByY16(s);
                        float floatValue2 = Float.valueOf(measureTemByY16).floatValue();
                        GuideHelper guideHelper2 = GuideHelper.this;
                        FileUtils.Companion.appFile((GuideHelper.this.getCurrentTime() + "\t" + GuideHelper.this.xShortDataArr[GuideHelper.this.mCenterIndex] + "\t" + GuideHelper.this.mY16Array[GuideHelper.this.mCenterIndex] + "\t" + GuideHelper.this.mParamLine.getFixedParamLine().getRealtimeShutterTemp() + "\t" + GuideHelper.this.mParamLine.getFixedParamLine().getRealtimeLenTemp() + "\t" + (((float) GuideHelper.this.mMeasureParam.realTimeTfpa) / 100.0f) + "\t" + (shutterRecordFlag ? 1 : 0) + "\t" + GuideHelper.this.mParamLine.getFixedParamLine().getShutterFlag() + "\t" + GuideHelper.this.envTemp + "\t" + GuideHelper.this.mCenterTemp + "\t" + humanTemp + "\t" + s2 + "\t" + s + "\t" + measureTemByY16 + "\t" + guideHelper2.getHumanTemp(floatValue2, guideHelper2.ambientTempForDebug) + "\t" + i4 + "\t" + i5 + "\t" + GuideHelper.this.getAvgB() + "\t" + GuideHelper.this.mCurrChangeRIndex + "\r\n").getBytes(), str);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.recordDebugDataTask = r2;
        this.recordDebugDataTimer.schedule(r2, 1000, 40);
    }

    public void stopRecordDebugData() {
        MediaUtils.Companion.noticeMediaScanner(this.mCtx, this.recordDebugDataPath);
        this.recordDebugDataTask.cancel();
        this.recordDebugDataTask = null;
        this.recordDebugDataTimer.cancel();
        this.recordDebugDataTimer = null;
    }
}
