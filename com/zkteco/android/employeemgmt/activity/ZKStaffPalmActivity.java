package com.zkteco.android.employeemgmt.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.device.camera.CameraViewManager;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.core.sdk.LightManager;
import com.zkteco.android.zkcore.base.ZKBaseActivity;
import com.zkteco.android.zkcore.view.ZKProgressBarDialog;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.edk.camera.lib.ZkCameraPreviewCallback;
import com.zkteco.edk.camera.lib.ZkCameraView;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmExtractResult;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmService12;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ZKStaffPalmActivity extends ZKBaseActivity {
    public static final int PALM_FINISH = 1003;
    public static final int PALM_IN_THE_AREA = 1001;
    public static final int PALM_REGISTERED = 1000;
    public static final int PALM_REGISTERING = 1002;
    public static final int PALM_VEIN_VL_MAJOR_VERSION = 12;
    public static final int PALM_VEIN_VL_MINOR_VERSION = 0;
    /* access modifiers changed from: private */
    public static final String TAG = "ZKStaffPalmActivity";
    public static final int TEMPLATE_TYPE_PALM_VEIN_VL = 8;
    /* access modifiers changed from: private */
    public Future future;
    Runnable getGrayDateTask = new Runnable() {
        public void run() {
            while (ZKStaffPalmActivity.this.isGetting) {
                try {
                    if (!ZKStaffPalmActivity.this.isFull) {
                        ZKPalmExtractResult extract = ZKPalmService12.extract((byte[]) ZKStaffPalmActivity.this.mNV21ForEnroll.poll(100, TimeUnit.MILLISECONDS));
                        Log.d(ZKStaffPalmActivity.TAG, "zkPalmExtractResult.result: " + extract.result);
                        Log.d(ZKStaffPalmActivity.TAG, "zkPalmExtractResult.imageQuality: " + extract.imageQuality);
                        Log.d(ZKStaffPalmActivity.TAG, "zkPalmExtractResult.templateQuality: " + extract.templateQuality);
                        if ((!(extract.result == 0) || !(extract.imageQuality >= 100)) || extract.templateQuality < 40) {
                            ZKStaffPalmActivity.this.handler.sendEmptyMessage(1001);
                        } else {
                            ZKStaffPalmActivity.this.handler.sendEmptyMessage(1002);
                            if (ZKStaffPalmActivity.this.isNewPalm) {
                                ZKStaffPalmActivity.this.palmList.clear();
                                int dbIdentify = ZKPalmService12.dbIdentify(extract.verTemplate, new String[1]);
                                Log.d(ZKStaffPalmActivity.TAG, "ret: " + dbIdentify);
                                Log.d(ZKStaffPalmActivity.TAG, "pvETheshold: " + ZKStaffPalmActivity.this.pvETheshold);
                                if (dbIdentify > 0 && dbIdentify > ZKStaffPalmActivity.this.pvETheshold) {
                                    Log.d(ZKStaffPalmActivity.TAG, "dbIdentify: Palm already exists");
                                    ZKStaffPalmActivity.this.handler.sendEmptyMessage(1000);
                                }
                            }
                            boolean unused = ZKStaffPalmActivity.this.isNewPalm = false;
                            ZKStaffPalmActivity.this.palmList.add(extract);
                            ZKStaffPalmActivity.this.addProgress(20);
                            if (ZKStaffPalmActivity.this.palmList.size() == 5) {
                                byte[] bArr = new byte[492240];
                                byte[] bArr2 = new byte[ZKPalmService12.FIX_REG_TEMPLATE_LEN];
                                for (int i = 0; i < 5; i++) {
                                    System.arraycopy(((ZKPalmExtractResult) ZKStaffPalmActivity.this.palmList.get(i)).preRegTemplate, 0, bArr, i * ZKPalmService12.FIX_PREREG_TEMPLATE_LEN, ZKPalmService12.FIX_PREREG_TEMPLATE_LEN);
                                }
                                int mergeRegTemplate = ZKPalmService12.mergeRegTemplate(bArr, 5, bArr2);
                                Log.d(ZKStaffPalmActivity.TAG, "regtemplateNum: " + mergeRegTemplate);
                                if (mergeRegTemplate == 0) {
                                    ZKPalmService12.dbDel(ZKStaffPalmActivity.this.userPin);
                                    ZKPalmService12.dbAdd(ZKStaffPalmActivity.this.userPin, bArr2);
                                    if (ZKStaffPalmActivity.this.isModify) {
                                        ZKStaffPalmActivity.this.updatePalmTemplate(bArr2);
                                    } else {
                                        ZKStaffPalmActivity.this.savePalmTemplate(bArr2);
                                    }
                                }
                                ZKStaffPalmActivity.this.handler.sendEmptyMessage(1003);
                                boolean unused2 = ZKStaffPalmActivity.this.isGetting = false;
                                ZKStaffPalmActivity.this.future.cancel(true);
                                ZKStaffPalmActivity.this.finish();
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.d(ZKStaffPalmActivity.TAG, "Exception: " + e);
                    e.printStackTrace();
                } catch (Throwable th) {
                    ZKStaffPalmActivity.this.mNV21ForEnroll.clear();
                    throw th;
                }
                ZKStaffPalmActivity.this.mNV21ForEnroll.clear();
            }
        }
    };
    /* access modifiers changed from: private */
    public final Handler handler = new Handler() {
        public void handleMessage(Message message) {
            String str;
            super.handleMessage(message);
            switch (message.what) {
                case 1000:
                    str = ZKStaffPalmActivity.this.getResources().getString(R.string.palm_registered);
                    break;
                case 1001:
                    str = ZKStaffPalmActivity.this.getResources().getString(R.string.palm_in_the_area);
                    break;
                case 1002:
                    str = ZKStaffPalmActivity.this.getResources().getString(R.string.registering_palm);
                    break;
                case 1003:
                    str = ZKStaffPalmActivity.this.getResources().getString(R.string.palm_finish);
                    break;
                default:
                    str = null;
                    break;
            }
            ZKStaffPalmActivity.this.prompt.setText(str);
        }
    };
    /* access modifiers changed from: private */
    public boolean isFull = false;
    /* access modifiers changed from: private */
    public boolean isGetting;
    /* access modifiers changed from: private */
    public boolean isModify = false;
    /* access modifiers changed from: private */
    public boolean isNewPalm = true;
    private LightManager lightManager;
    private byte[] mCacheNV21 = null;
    private ZKProgressBarDialog mCameraDialog;
    private ZkCameraView mCameraView;
    private Rect mEnrollRect;
    private ProgressBar mFaceEnrollProgressBar;
    private final ZkCameraPreviewCallback mGrayCallback = new ZkCameraPreviewCallback() {
        public final void onPreviewFrame(byte[] bArr, Camera camera, int i, int i2) {
            ZKStaffPalmActivity.this.lambda$new$0$ZKStaffPalmActivity(bArr, camera, i, i2);
        }
    };
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private ZkCameraView mIrCameraView;
    /* access modifiers changed from: private */
    public ArrayBlockingQueue<byte[]> mNV21ForEnroll = new ArrayBlockingQueue<>(1);
    private Paint mPalmPaint;
    private ExecutorService mSingleService = Executors.newSingleThreadExecutor();
    private int maxPvCount;
    /* access modifiers changed from: private */
    public final List<ZKPalmExtractResult> palmList = new ArrayList();
    private ImageView palmScanLine;
    /* access modifiers changed from: private */
    public TextView prompt;
    /* access modifiers changed from: private */
    public int pvETheshold;
    /* access modifiers changed from: private */
    public String userPin;
    private ZKProgressBarDialog zkProgressBarDialog;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_staff_palm);
        Paint paint = new Paint(1);
        this.mPalmPaint = paint;
        paint.setStrokeWidth(2.0f);
        this.mPalmPaint.setStyle(Paint.Style.STROKE);
        this.mPalmPaint.setColor(getResources().getColor(R.color.clr_7AC143));
        ImageView imageView = (ImageView) findViewById(R.id.face_scan_line_iv);
        this.palmScanLine = imageView;
        imageView.setVisibility(8);
        this.lightManager = new LightManager(this);
        controlInfraredLight(true);
        initBackgroundHandler();
        initToolbar();
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        this.userPin = intent.getStringExtra("userPin");
        this.isModify = intent.getBooleanExtra("isModify", false);
        this.isNewPalm = intent.getBooleanExtra("isNewPalm", false);
        this.pvETheshold = DBManager.getInstance().getIntOption("PvEThreshold", 576);
        this.maxPvCount = DBManager.getInstance().getIntOption("~MaxPvCount", 10000);
        if (ZKPalmService12.dbCount() >= this.maxPvCount) {
            this.isFull = true;
            this.prompt.setText(R.string.palm_full);
        } else {
            this.isFull = false;
        }
        Future future2 = this.future;
        if (future2 != null && !future2.isDone()) {
            this.future.cancel(true);
        }
        this.isGetting = true;
        this.future = this.mSingleService.submit(this.getGrayDateTask);
        String str = TAG;
        Log.d(str, "count: " + ZKPalmService12.dbCount());
        Log.d(str, "maxPvCount: " + this.maxPvCount);
    }

    private void initView() {
        if (this.zkProgressBarDialog == null) {
            this.zkProgressBarDialog = new ZKProgressBarDialog.Builder(this).setCancelable(true).setCancelOutside(false).create();
        }
        if (this.mCameraDialog == null) {
            this.mCameraDialog = new ZKProgressBarDialog.Builder(this).setCancelable(true).setCancelOutside(false).create();
        }
        this.prompt = (TextView) findViewById(R.id.tv_prompt_p);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pb_face_enroll_progress);
        this.mFaceEnrollProgressBar = progressBar;
        progressBar.setMax(100);
        initCameraView();
        float previewWidth = ((float) DeviceManager.getDefault().getCameraConfig().getPreviewWidth()) / 5.0f;
        float f = 4.0f * previewWidth;
        if (this.mEnrollRect == null) {
            this.mEnrollRect = new Rect((int) previewWidth, 0, (int) f, DeviceManager.getDefault().getCameraConfig().getPreviewHeight());
        }
    }

    private void initBackgroundHandler() {
        HandlerThread handlerThread = new HandlerThread("backgroundThread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper()) {
            public void handleMessage(Message message) {
                ((ZkCameraView) message.obj).openCamera();
            }
        };
    }

    private void releaseHandler() {
        Handler handler2 = this.mHandler;
        if (handler2 != null) {
            handler2.getLooper().quit();
            this.mHandler = null;
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mHandlerThread.join(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.mHandlerThread.isAlive()) {
                Log.e(TAG, "Failed to stop the looper.");
            }
            this.mHandlerThread = null;
        }
    }

    private void initCameraView() {
        this.mCameraView = (ZkCameraView) findViewById(R.id.camera_view);
        int cameraType = DeviceManager.getDefault().getCameraConfig().getCameraType();
        Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.obj = this.mCameraView;
        this.mHandler.sendMessage(obtainMessage);
        if (cameraType > 1 && DeviceManager.getDefault().isSupportPalm()) {
            ZkCameraView createSecondary = CameraViewManager.createSecondary(this);
            this.mIrCameraView = createSecondary;
            if (createSecondary != null) {
                createSecondary.setPreviewCallback(this.mGrayCallback);
                Message obtainMessage2 = this.mHandler.obtainMessage();
                obtainMessage2.obj = this.mIrCameraView;
                this.mHandler.sendMessage(obtainMessage2);
            }
        }
    }

    public /* synthetic */ void lambda$new$0$ZKStaffPalmActivity(byte[] bArr, Camera camera, int i, int i2) {
        byte[] bArr2 = this.mCacheNV21;
        if (bArr2 == null) {
            this.mCacheNV21 = Arrays.copyOf(bArr, bArr.length);
        } else {
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        }
        this.mNV21ForEnroll.offer(this.mCacheNV21);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: java.lang.Object[]} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0072, code lost:
        if (r5 != null) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0074, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x007e, code lost:
        if (r5 == null) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0081, code lost:
        if (r6 <= 0) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0083, code lost:
        com.zktechnology.android.utils.DBManager.getInstance().executeSql("ZKDB.db", "insert into Pers_BioTemplate(user_pin, template_no_index,template_id, bio_type, major_ver, minor_ver, send_flag, template_no) values(?,?,?,?,?,?,?,?)", new java.lang.Object[]{r12.userPin, java.lang.Integer.valueOf(r3), java.lang.Integer.valueOf(r6), 8, 12, 0, 1, 0});
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00c2, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void savePalmTemplate(byte[] r13) {
        /*
            r12 = this;
            r0 = 2
            int[] r1 = new int[r0]
            r1 = {5, 2048} // fill-array
            java.lang.Class<byte> r2 = byte.class
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r2, r1)
            byte[][] r1 = (byte[][]) r1
            r2 = 5
            int[] r3 = new int[r2]
            int r13 = com.zkteco.biometric.ZKBioTemplateService.splitPalmTemplate(r13, r1, r3)
            java.lang.String r3 = TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "retSplit: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r13)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r3, r4)
            if (r13 != 0) goto L_0x00cc
            r13 = 0
            r3 = r13
        L_0x0031:
            int r4 = r1.length
            if (r3 >= r4) goto L_0x00e2
            r4 = 4
            java.lang.Object[] r5 = new java.lang.Object[r4]
            r6 = r1[r3]
            r5[r13] = r6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r13)
            r7 = 1
            r5[r7] = r6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r13)
            r5[r0] = r6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r13)
            r8 = 3
            r5[r8] = r6
            com.zkteco.android.db.orm.manager.DataManager r6 = com.zktechnology.android.utils.DBManager.getInstance()
            java.lang.String r9 = "ZKDB.db"
            java.lang.String r10 = "insert into Pers_BioTemplateData(template_data, CREATE_ID, MODIFY_TIME, SEND_FLAG) values(?,?,?,?)"
            r6.executeSql(r9, r10, r5)
            r5 = 0
            r6 = -1
            java.lang.String r10 = "select max(id) from pers_biotemplatedata"
            com.zkteco.android.db.orm.manager.DataManager r11 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x007a }
            android.database.Cursor r5 = r11.queryBySql(r10)     // Catch:{ Exception -> 0x007a }
            if (r5 == 0) goto L_0x0072
            boolean r10 = r5.moveToFirst()     // Catch:{ Exception -> 0x007a }
            if (r10 == 0) goto L_0x0072
            int r6 = r5.getInt(r13)     // Catch:{ Exception -> 0x007a }
        L_0x0072:
            if (r5 == 0) goto L_0x0081
        L_0x0074:
            r5.close()
            goto L_0x0081
        L_0x0078:
            r13 = move-exception
            goto L_0x00c6
        L_0x007a:
            r10 = move-exception
            r10.printStackTrace()     // Catch:{ all -> 0x0078 }
            if (r5 == 0) goto L_0x0081
            goto L_0x0074
        L_0x0081:
            if (r6 <= 0) goto L_0x00c2
            r5 = 8
            java.lang.Object[] r10 = new java.lang.Object[r5]
            java.lang.String r11 = r12.userPin
            r10[r13] = r11
            java.lang.Integer r11 = java.lang.Integer.valueOf(r3)
            r10[r7] = r11
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r10[r0] = r6
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r10[r8] = r5
            r5 = 12
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r10[r4] = r5
            java.lang.Integer r4 = java.lang.Integer.valueOf(r13)
            r10[r2] = r4
            r4 = 6
            java.lang.Integer r5 = java.lang.Integer.valueOf(r7)
            r10[r4] = r5
            r4 = 7
            java.lang.Integer r5 = java.lang.Integer.valueOf(r13)
            r10[r4] = r5
            com.zkteco.android.db.orm.manager.DataManager r4 = com.zktechnology.android.utils.DBManager.getInstance()
            java.lang.String r5 = "insert into Pers_BioTemplate(user_pin, template_no_index,template_id, bio_type, major_ver, minor_ver, send_flag, template_no) values(?,?,?,?,?,?,?,?)"
            r4.executeSql(r9, r5, r10)
        L_0x00c2:
            int r3 = r3 + 1
            goto L_0x0031
        L_0x00c6:
            if (r5 == 0) goto L_0x00cb
            r5.close()
        L_0x00cb:
            throw r13
        L_0x00cc:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "savePalmTemplate splitPalmTemplate failed: "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r13 = r0.append(r13)
            java.lang.String r13 = r13.toString()
            android.util.Log.d(r3, r13)
        L_0x00e2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffPalmActivity.savePalmTemplate(byte[]):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: java.lang.Object[]} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updatePalmTemplate(byte[] r13) {
        /*
            r12 = this;
            java.lang.String r0 = "ZKDB.db"
            r1 = 2048(0x800, float:2.87E-42)
            r2 = 2
            int[] r3 = new int[r2]     // Catch:{ SQLException -> 0x00f0 }
            r4 = 1
            r3[r4] = r1     // Catch:{ SQLException -> 0x00f0 }
            r1 = 5
            r5 = 0
            r3[r5] = r1     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Class<byte> r6 = byte.class
            java.lang.Object r3 = java.lang.reflect.Array.newInstance(r6, r3)     // Catch:{ SQLException -> 0x00f0 }
            byte[][] r3 = (byte[][]) r3     // Catch:{ SQLException -> 0x00f0 }
            int[] r6 = new int[r1]     // Catch:{ SQLException -> 0x00f0 }
            int r13 = com.zkteco.biometric.ZKBioTemplateService.splitPalmTemplate(r13, r3, r6)     // Catch:{ SQLException -> 0x00f0 }
            if (r13 != 0) goto L_0x00d7
            r13 = r5
        L_0x001f:
            int r6 = r3.length     // Catch:{ SQLException -> 0x00f0 }
            if (r13 >= r6) goto L_0x00f4
            com.zkteco.android.db.orm.tna.PersBiotemplate r6 = new com.zkteco.android.db.orm.tna.PersBiotemplate     // Catch:{ SQLException -> 0x00f0 }
            r6.<init>()     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.QueryBuilder r6 = r6.getQueryBuilder()     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.Where r6 = r6.where()     // Catch:{ SQLException -> 0x00f0 }
            java.lang.String r7 = "user_pin"
            java.lang.String r8 = r12.userPin     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.Where r6 = r6.eq(r7, r8)     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.Where r6 = r6.and()     // Catch:{ SQLException -> 0x00f0 }
            java.lang.String r7 = "bio_type"
            r8 = 8
            java.lang.Integer r9 = java.lang.Integer.valueOf(r8)     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.Where r6 = r6.eq(r7, r9)     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.Where r6 = r6.and()     // Catch:{ SQLException -> 0x00f0 }
            java.lang.String r7 = "major_ver"
            r9 = 12
            java.lang.Integer r10 = java.lang.Integer.valueOf(r9)     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.Where r6 = r6.eq(r7, r10)     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.Where r6 = r6.and()     // Catch:{ SQLException -> 0x00f0 }
            java.lang.String r7 = "minor_ver"
            java.lang.Integer r10 = java.lang.Integer.valueOf(r5)     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.Where r6 = r6.eq(r7, r10)     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.Where r6 = r6.and()     // Catch:{ SQLException -> 0x00f0 }
            java.lang.String r7 = "template_no_index"
            java.lang.Integer r10 = java.lang.Integer.valueOf(r13)     // Catch:{ SQLException -> 0x00f0 }
            com.j256.ormlite.stmt.Where r6 = r6.eq(r7, r10)     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Object r6 = r6.queryForFirst()     // Catch:{ SQLException -> 0x00f0 }
            com.zkteco.android.db.orm.tna.PersBiotemplate r6 = (com.zkteco.android.db.orm.tna.PersBiotemplate) r6     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Object[] r7 = new java.lang.Object[r1]     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r4)     // Catch:{ SQLException -> 0x00f0 }
            r7[r5] = r10     // Catch:{ SQLException -> 0x00f0 }
            int r10 = r6.getTemplate_id()     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ SQLException -> 0x00f0 }
            r7[r4] = r10     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ SQLException -> 0x00f0 }
            r7[r2] = r8     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r9)     // Catch:{ SQLException -> 0x00f0 }
            r9 = 3
            r7[r9] = r8     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)     // Catch:{ SQLException -> 0x00f0 }
            r10 = 4
            r7[r10] = r8     // Catch:{ SQLException -> 0x00f0 }
            com.zkteco.android.db.orm.manager.DataManager r8 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ SQLException -> 0x00f0 }
            java.lang.String r11 = "update pers_biotemplate set send_flag = ? where template_id = ? and bio_type = ? and major_ver = ? and minor_ver = ?"
            r8.executeSql(r0, r11, r7)     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Object[] r7 = new java.lang.Object[r1]     // Catch:{ SQLException -> 0x00f0 }
            r8 = r3[r13]     // Catch:{ SQLException -> 0x00f0 }
            r7[r5] = r8     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)     // Catch:{ SQLException -> 0x00f0 }
            r7[r4] = r8     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)     // Catch:{ SQLException -> 0x00f0 }
            r7[r2] = r8     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)     // Catch:{ SQLException -> 0x00f0 }
            r7[r9] = r8     // Catch:{ SQLException -> 0x00f0 }
            int r6 = r6.getTemplate_id()     // Catch:{ SQLException -> 0x00f0 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ SQLException -> 0x00f0 }
            r7[r10] = r6     // Catch:{ SQLException -> 0x00f0 }
            com.zkteco.android.db.orm.manager.DataManager r6 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ SQLException -> 0x00f0 }
            java.lang.String r8 = "update Pers_BioTemplateData set template_data = ?, CREATE_ID = ?, MODIFY_TIME = ?, SEND_FLAG = ? where ID = ?"
            r6.executeSql(r0, r8, r7)     // Catch:{ SQLException -> 0x00f0 }
            int r13 = r13 + 1
            goto L_0x001f
        L_0x00d7:
            java.lang.String r0 = TAG     // Catch:{ SQLException -> 0x00f0 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x00f0 }
            r1.<init>()     // Catch:{ SQLException -> 0x00f0 }
            java.lang.String r2 = "updatePalmTemplate splitPalmTemplate failed: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ SQLException -> 0x00f0 }
            java.lang.StringBuilder r13 = r1.append(r13)     // Catch:{ SQLException -> 0x00f0 }
            java.lang.String r13 = r13.toString()     // Catch:{ SQLException -> 0x00f0 }
            android.util.Log.d(r0, r13)     // Catch:{ SQLException -> 0x00f0 }
            goto L_0x00f4
        L_0x00f0:
            r13 = move-exception
            r13.printStackTrace()
        L_0x00f4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffPalmActivity.updatePalmTemplate(byte[]):void");
    }

    /* access modifiers changed from: private */
    public void addProgress(int i) {
        for (int i2 = 1; i2 <= i; i2++) {
            runOnUiThread(new Runnable() {
                public final void run() {
                    ZKStaffPalmActivity.this.lambda$addProgress$1$ZKStaffPalmActivity();
                }
            });
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public /* synthetic */ void lambda$addProgress$1$ZKStaffPalmActivity() {
        this.mFaceEnrollProgressBar.incrementProgressBy(1);
    }

    private void releaseCamera() {
        ZkCameraView zkCameraView = this.mCameraView;
        if (zkCameraView != null) {
            zkCameraView.setPreviewCallback((ZkCameraPreviewCallback) null);
            this.mCameraView.closeCamera();
            this.mCameraView.releaseResource();
            this.mCameraView = null;
        }
        ZkCameraView zkCameraView2 = this.mIrCameraView;
        if (zkCameraView2 != null) {
            zkCameraView2.setPreviewCallback((ZkCameraPreviewCallback) null);
            this.mIrCameraView.closeCamera();
            this.mIrCameraView.releaseResource();
            this.mIrCameraView = null;
        }
    }

    private void initToolbar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.FaceToolBar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffPalmActivity.this.lambda$initToolbar$2$ZKStaffPalmActivity(view);
            }
        }, getString(R.string.zk_staff_palm_title));
        zKToolbar.setRightView(getResources().getString(R.string.zk_staff_back), (View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffPalmActivity.this.lambda$initToolbar$3$ZKStaffPalmActivity(view);
            }
        });
        zKToolbar.setRightView();
    }

    public /* synthetic */ void lambda$initToolbar$2$ZKStaffPalmActivity(View view) {
        finish();
    }

    public /* synthetic */ void lambda$initToolbar$3$ZKStaffPalmActivity(View view) {
        finish();
    }

    public void controlInfraredLight(boolean z) {
        LightManager lightManager2 = this.lightManager;
        if (lightManager2 != null) {
            if (z) {
                try {
                    lightManager2.setLightState(1, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                lightManager2.setLightState(1, 0);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        releaseHandler();
        releaseCamera();
        this.isGetting = false;
        ZKProgressBarDialog zKProgressBarDialog = this.zkProgressBarDialog;
        if (zKProgressBarDialog != null && zKProgressBarDialog.isShowing()) {
            this.zkProgressBarDialog.dismiss();
            this.zkProgressBarDialog = null;
        }
        ZKProgressBarDialog zKProgressBarDialog2 = this.mCameraDialog;
        if (zKProgressBarDialog2 != null && zKProgressBarDialog2.isShowing()) {
            this.mCameraDialog.dismiss();
            this.mCameraDialog = null;
        }
        sendPush();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x006a, code lost:
        if (r6 != 0) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0079, code lost:
        if (r6 != 0) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007b, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r5 = r2.convertPushInit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r2.sendHubAction(25, r5, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008a, code lost:
        if (r5 == 0) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008c, code lost:
        r2.convertPushFree(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0090, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0092, code lost:
        r7 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0094, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0095, code lost:
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0097, code lost:
        r7 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0098, code lost:
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r7.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009e, code lost:
        if (r5 == 0) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a1, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a7, code lost:
        if (r5 != 0) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a9, code lost:
        r2.convertPushFree(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ac, code lost:
        throw r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a1 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendPush() {
        /*
            r11 = this;
            r0 = 0
            r1 = r0
        L_0x0002:
            r2 = 5
            if (r1 >= r2) goto L_0x00b6
            com.zkteco.android.core.sdk.HubProtocolManager r2 = new com.zkteco.android.core.sdk.HubProtocolManager
            r2.<init>(r11)
            r3 = 0
            com.zkteco.android.db.orm.tna.PersBiotemplate r5 = new com.zkteco.android.db.orm.tna.PersBiotemplate     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            r5.<init>()     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            com.j256.ormlite.stmt.QueryBuilder r5 = r5.getQueryBuilder()     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            com.j256.ormlite.stmt.Where r5 = r5.where()     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            java.lang.String r6 = "user_pin"
            java.lang.String r7 = r11.userPin     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            com.j256.ormlite.stmt.Where r5 = r5.eq(r6, r7)     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            com.j256.ormlite.stmt.Where r5 = r5.and()     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            java.lang.String r6 = "template_no_index"
            java.lang.Integer r7 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            com.j256.ormlite.stmt.Where r5 = r5.eq(r6, r7)     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            java.lang.Object r5 = r5.queryForFirst()     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            com.zkteco.android.db.orm.tna.PersBiotemplate r5 = (com.zkteco.android.db.orm.tna.PersBiotemplate) r5     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            long r6 = r2.convertPushInit()     // Catch:{ Exception -> 0x0072, all -> 0x006f }
            java.lang.String r8 = "Pers_BioTemplate"
            r2.setPushTableName(r6, r8)     // Catch:{ Exception -> 0x006d }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006d }
            r8.<init>()     // Catch:{ Exception -> 0x006d }
            java.lang.String r9 = "ID="
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x006d }
            long r9 = r5.getID()     // Catch:{ Exception -> 0x006d }
            int r9 = (int) r9     // Catch:{ Exception -> 0x006d }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x006d }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x006d }
            r2.setPushCon(r6, r8)     // Catch:{ Exception -> 0x006d }
            java.lang.String r8 = "ID"
            long r9 = r5.getID()     // Catch:{ Exception -> 0x006d }
            int r5 = (int) r9     // Catch:{ Exception -> 0x006d }
            r2.setPushIntField(r6, r8, r5)     // Catch:{ Exception -> 0x006d }
            java.lang.String r5 = ""
            r2.sendHubAction(r0, r6, r5)     // Catch:{ Exception -> 0x006d }
            int r5 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x007e
            goto L_0x007b
        L_0x006d:
            r5 = move-exception
            goto L_0x0074
        L_0x006f:
            r0 = move-exception
            r6 = r3
            goto L_0x00ae
        L_0x0072:
            r5 = move-exception
            r6 = r3
        L_0x0074:
            r5.printStackTrace()     // Catch:{ all -> 0x00ad }
            int r5 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x007e
        L_0x007b:
            r2.convertPushFree(r6)
        L_0x007e:
            long r5 = r2.convertPushInit()     // Catch:{ Exception -> 0x0097, all -> 0x0094 }
            r7 = 25
            r8 = 0
            r2.sendHubAction(r7, r5, r8)     // Catch:{ Exception -> 0x0092 }
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x00a1
        L_0x008c:
            r2.convertPushFree(r5)
            goto L_0x00a1
        L_0x0090:
            r0 = move-exception
            goto L_0x00a5
        L_0x0092:
            r7 = move-exception
            goto L_0x0099
        L_0x0094:
            r0 = move-exception
            r5 = r3
            goto L_0x00a5
        L_0x0097:
            r7 = move-exception
            r5 = r3
        L_0x0099:
            r7.printStackTrace()     // Catch:{ all -> 0x0090 }
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x00a1
            goto L_0x008c
        L_0x00a1:
            int r1 = r1 + 1
            goto L_0x0002
        L_0x00a5:
            int r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x00ac
            r2.convertPushFree(r5)
        L_0x00ac:
            throw r0
        L_0x00ad:
            r0 = move-exception
        L_0x00ae:
            int r1 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x00b5
            r2.convertPushFree(r6)
        L_0x00b5:
            throw r0
        L_0x00b6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffPalmActivity.sendPush():void");
    }
}
