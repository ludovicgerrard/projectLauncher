package com.zkteco.android.employeemgmt.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.device.camera.CameraViewManager;
import com.zktechnology.android.device.camera.ICameraPictureListener;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.AES256Util;
import com.zktechnology.android.utils.BitmapHelper;
import com.zktechnology.android.utils.BitmapUtils;
import com.zkteco.android.employeemgmt.activity.ZKStaffIconCollectionActivity;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.edk.camera.lib.ZkCameraPreviewCallback;
import com.zkteco.edk.camera.lib.ZkCameraView;
import com.zkteco.liveface562.ZkFaceManager;
import com.zkteco.liveface562.bean.Face;
import com.zkteco.liveface562.bean.ZkDetectInfo;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedTransferQueue;

public class ZKStaffIconCollectionActivity extends ZKStaffBaseActivity implements View.OnClickListener {
    private static final int MSG_OPEN_CAMERA = 10000;
    private static final int MSG_SAVE_PHOTO = 10001;
    public static final int RESULT_CODE_ADD = 1999;
    /* access modifiers changed from: private */
    public static final String TAG = "ZKStaffIconCollectionActivity";
    private boolean PORTRAIT = true;
    private Future future;
    private ImageFeedReceived imageFeedReceived;
    private boolean isDestory = false;
    /* access modifiers changed from: private */
    public ImageView ivLeft;
    private long lastShowToastTime;
    ICameraPictureListener listener = new ICameraPictureListener() {
        public String getPath() {
            return null;
        }

        public void onPictureAndSaveFinish() {
            ZKStaffIconCollectionActivity.this.runOnUiThread(new Runnable() {
                public final void run() {
                    ZKStaffIconCollectionActivity.AnonymousClass2.this.lambda$onPictureAndSaveFinish$0$ZKStaffIconCollectionActivity$2();
                }
            });
        }

        public /* synthetic */ void lambda$onPictureAndSaveFinish$0$ZKStaffIconCollectionActivity$2() {
            ZKStaffIconCollectionActivity.this.mBTsave.setVisibility(0);
        }
    };
    /* access modifiers changed from: private */
    public Bitmap mAvatar;
    /* access modifiers changed from: private */
    public ImageView mBTsave;
    private ImageView mBTtakephoto;
    private ZkCameraView mCameraView;
    private final ZkCameraPreviewCallback mColorPreviewCallback = new ZkCameraPreviewCallback() {
        public final void onPreviewFrame(byte[] bArr, Camera camera, int i, int i2) {
            ZKStaffIconCollectionActivity.this.lambda$new$1$ZKStaffIconCollectionActivity(bArr, camera, i, i2);
        }
    };
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private final LinkedTransferQueue<ICameraPictureListener> mQueue = new LinkedTransferQueue<>();
    private ExecutorService mSingleService = Executors.newSingleThreadExecutor();
    private Toast toast;
    private String userpin;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_staff_edit_photo);
        boolean z = true;
        if (getResources().getConfiguration().orientation != 1) {
            z = false;
        }
        this.PORTRAIT = z;
        initBackgroundHandler();
        initToolbar();
        initView();
    }

    private void initBackgroundHandler() {
        HandlerThread handlerThread = new HandlerThread("backgroundThread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper()) {
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == ZKStaffIconCollectionActivity.MSG_OPEN_CAMERA) {
                    ((ZkCameraView) message.obj).openCamera();
                } else if (i == ZKStaffIconCollectionActivity.MSG_SAVE_PHOTO) {
                    ZKStaffIconCollectionActivity.this.savePhoto();
                }
            }
        };
    }

    private void releaseHandler() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.getLooper().quit();
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

    private void initToolbar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.PhotoToolBar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffIconCollectionActivity.this.lambda$initToolbar$0$ZKStaffIconCollectionActivity(view);
            }
        }, getString(R.string.zk_staff_employee_icon));
        zKToolbar.setRightView();
    }

    public /* synthetic */ void lambda$initToolbar$0$ZKStaffIconCollectionActivity(View view) {
        finish();
    }

    private void initView() {
        this.userpin = getIntent().getStringExtra("user_pin");
        this.mBTtakephoto = (ImageView) findViewById(R.id.iv_take_photo);
        this.ivLeft = (ImageView) findViewById(R.id.iv_image_left);
        this.mBTsave = (ImageView) findViewById(R.id.iv_save);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("userpin");
        this.userpin = stringExtra;
        if (stringExtra == null || BitmapUtils.getUserPhoto(stringExtra) == null) {
            this.ivLeft.setImageResource(R.mipmap.ic_pic_null);
        } else {
            byte[] userPhoto = BitmapUtils.getUserPhoto(this.userpin);
            this.ivLeft.setImageBitmap(BitmapFactory.decodeByteArray(userPhoto, 0, userPhoto.length));
        }
        if (intent.getStringExtra("operate").equals("add")) {
            File file = new File(ZKFilePath.IMAGE_PATH + "temp/" + this.userpin + ".jpg");
            if (file.exists()) {
                int length = (int) file.length();
                byte[] bArr = new byte[length];
                try {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    bufferedInputStream.read(bArr, 0, length);
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (ZKLauncher.zkDataEncdec == 1) {
                    this.ivLeft.setImageBitmap(BitmapFactory.decodeByteArray(AES256Util.decrypt(bArr, ZKLauncher.PUBLIC_KEY, ZKLauncher.iv), 0, length));
                } else {
                    this.ivLeft.setImageBitmap(BitmapFactory.decodeByteArray(bArr, 0, length));
                }
            }
        }
        this.mBTsave.setVisibility(4);
        this.mBTtakephoto.setOnClickListener(this);
        this.mBTsave.setOnClickListener(this);
        ZkCameraView zkCameraView = (ZkCameraView) findViewById(R.id.camera_view);
        this.mCameraView = zkCameraView;
        zkCameraView.setPreviewCallback(this.mColorPreviewCallback);
        CameraViewManager.initDefaultCameraView(this.mCameraView);
        setPreviewSize();
        Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = MSG_OPEN_CAMERA;
        obtainMessage.obj = this.mCameraView;
        this.mHandler.sendMessage(obtainMessage);
    }

    public /* synthetic */ void lambda$new$1$ZKStaffIconCollectionActivity(byte[] bArr, Camera camera, int i, int i2) {
        ICameraPictureListener poll = this.mQueue.poll();
        if (poll != null) {
            this.imageFeedReceived = new ImageFeedReceived(Arrays.copyOf(bArr, bArr.length), i, i2, poll);
            Future future2 = this.future;
            if (future2 != null && !future2.isDone() && !this.future.isCancelled()) {
                this.future.cancel(true);
            }
            this.future = this.mSingleService.submit(this.imageFeedReceived);
        }
    }

    private void releaseCameraView() {
        ZkCameraView zkCameraView = this.mCameraView;
        if (zkCameraView != null) {
            zkCameraView.setPreviewCallback((ZkCameraPreviewCallback) null);
            this.mCameraView.closeCamera();
            this.mCameraView.releaseResource();
            this.mCameraView = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00ca A[LOOP:0: B:15:0x00bf->B:17:0x00ca, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00dc A[Catch:{ Exception -> 0x00fa }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00ec A[Catch:{ Exception -> 0x00fa }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void compressPhoto(android.graphics.Bitmap r7, java.lang.String r8, boolean r9) {
        /*
            int r0 = r7.getWidth()
            int r1 = r7.getHeight()
            r2 = 1
            if (r0 <= r1) goto L_0x001e
            int r0 = r7.getWidth()
            float r0 = (float) r0
            r1 = 1156579328(0x44f00000, float:1920.0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x001e
            int r0 = r7.getWidth()
        L_0x001a:
            float r0 = (float) r0
            float r0 = r0 / r1
            int r0 = (int) r0
            goto L_0x0039
        L_0x001e:
            int r0 = r7.getWidth()
            int r1 = r7.getHeight()
            if (r0 >= r1) goto L_0x0038
            int r0 = r7.getHeight()
            float r0 = (float) r0
            r1 = 1149698048(0x44870000, float:1080.0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x0038
            int r0 = r7.getHeight()
            goto L_0x001a
        L_0x0038:
            r0 = r2
        L_0x0039:
            if (r0 > 0) goto L_0x003c
            goto L_0x003d
        L_0x003c:
            r2 = r0
        L_0x003d:
            int r0 = r7.getWidth()
            int r0 = r0 / r2
            int r1 = r7.getHeight()
            int r1 = r1 / r2
            android.graphics.Bitmap$Config r3 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r0, r1, r3)
            android.graphics.Canvas r1 = new android.graphics.Canvas
            r1.<init>(r0)
            android.graphics.Rect r3 = new android.graphics.Rect
            int r4 = r7.getWidth()
            int r4 = r4 / r2
            int r5 = r7.getHeight()
            int r5 = r5 / r2
            r2 = 0
            r3.<init>(r2, r2, r4, r5)
            r2 = 0
            r1.drawBitmap(r7, r2, r3, r2)
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream
            r7.<init>()
            r1 = 30
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG
            r0.compress(r2, r1, r7)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "总内存:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()
            long r3 = r3.maxMemory()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = "\t已用内存:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()
            long r3 = r3.totalMemory()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = "\t剩余内存:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()
            long r3 = r3.maxMemory()
            java.lang.Runtime r5 = java.lang.Runtime.getRuntime()
            long r5 = r5.totalMemory()
            long r3 = r3 - r5
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "OOM"
            android.util.Log.d(r3, r2)
        L_0x00bf:
            byte[] r2 = r7.toByteArray()
            int r2 = r2.length
            int r2 = r2 / 1024
            r3 = 255(0xff, float:3.57E-43)
            if (r2 <= r3) goto L_0x00d5
            r7.reset()
            int r1 = r1 + -5
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG
            r0.compress(r2, r1, r7)
            goto L_0x00bf
        L_0x00d5:
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00fa }
            r1.<init>(r8)     // Catch:{ Exception -> 0x00fa }
            if (r9 == 0) goto L_0x00ec
            byte[] r8 = r7.toByteArray()     // Catch:{ Exception -> 0x00fa }
            byte[] r9 = com.zktechnology.android.launcher2.ZKLauncher.PUBLIC_KEY     // Catch:{ Exception -> 0x00fa }
            byte[] r2 = com.zktechnology.android.launcher2.ZKLauncher.iv     // Catch:{ Exception -> 0x00fa }
            byte[] r8 = com.zktechnology.android.utils.AES256Util.encrypt(r8, r9, r2)     // Catch:{ Exception -> 0x00fa }
            r1.write(r8)     // Catch:{ Exception -> 0x00fa }
            goto L_0x00f3
        L_0x00ec:
            byte[] r8 = r7.toByteArray()     // Catch:{ Exception -> 0x00fa }
            r1.write(r8)     // Catch:{ Exception -> 0x00fa }
        L_0x00f3:
            r1.flush()     // Catch:{ Exception -> 0x00fa }
            r1.close()     // Catch:{ Exception -> 0x00fa }
            goto L_0x00fe
        L_0x00fa:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00fe:
            r7.close()     // Catch:{ IOException -> 0x0102 }
            goto L_0x0106
        L_0x0102:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0106:
            r0.recycle()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffIconCollectionActivity.compressPhoto(android.graphics.Bitmap, java.lang.String, boolean):void");
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.iv_save) {
            if (id == R.id.iv_take_photo && this.PORTRAIT) {
                this.mQueue.offer(this.listener);
            }
        } else if (!TextUtils.isEmpty(this.userpin)) {
            this.mHandler.sendEmptyMessage(MSG_SAVE_PHOTO);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0089, code lost:
        if (r5 != 0) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x009a, code lost:
        if (r5 != 0) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009c, code lost:
        r0.convertPushFree(r5);
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void savePhoto() {
        /*
            r8 = this;
            android.graphics.Bitmap r0 = r8.mAvatar
            if (r0 == 0) goto L_0x0107
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.ARGB_8888
            r2 = 0
            android.graphics.Bitmap r0 = r0.copy(r1, r2)
            if (r0 != 0) goto L_0x000e
            return
        L_0x000e:
            android.content.Intent r1 = r8.getIntent()
            java.lang.String r3 = "operate"
            java.lang.String r1 = r1.getStringExtra(r3)
            java.lang.String r4 = "modify"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x00a8
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = com.zkteco.android.zkcore.utils.ZKFilePath.PICTURE_PATH
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = r8.userpin
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = com.zkteco.android.zkcore.utils.ZKFilePath.SUFFIX_IMAGE
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            int r3 = com.zktechnology.android.launcher2.ZKLauncher.zkDataEncdec
            r4 = 1
            if (r3 != r4) goto L_0x0041
            goto L_0x0042
        L_0x0041:
            r4 = r2
        L_0x0042:
            compressPhoto(r0, r1, r4)
            com.zkteco.android.core.sdk.HubProtocolManager r0 = new com.zkteco.android.core.sdk.HubProtocolManager
            android.content.Context r1 = r8.getApplicationContext()
            r0.<init>(r1)
            java.lang.String r1 = com.zkteco.android.zkcore.utils.ZKFilePath.PICTURE_PATH
            java.lang.String r3 = r8.userpin
            boolean r1 = com.zkteco.android.zkcore.utils.FileUtil.isUserPhotoExist(r1, r3, r8)
            if (r1 == 0) goto L_0x00ff
            r3 = 0
            long r5 = r0.convertPushInit()     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            java.lang.String r1 = "USER_PHOTO_INDEX"
            r0.setPushTableName(r5, r1)     // Catch:{ Exception -> 0x008e }
            java.lang.String r1 = "User_PIN"
            java.lang.String r7 = r8.userpin     // Catch:{ Exception -> 0x008e }
            r0.setPushStrField(r5, r1, r7)     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008e }
            r1.<init>()     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = "User_PIN="
            java.lang.StringBuilder r1 = r1.append(r7)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = r8.userpin     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r1 = r1.append(r7)     // Catch:{ Exception -> 0x008e }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x008e }
            r0.setPushCon(r5, r1)     // Catch:{ Exception -> 0x008e }
            java.lang.String r1 = ""
            r0.sendHubAction(r2, r5, r1)     // Catch:{ Exception -> 0x008e }
            int r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x00ff
            goto L_0x009c
        L_0x008c:
            r1 = move-exception
            goto L_0x00a0
        L_0x008e:
            r1 = move-exception
            goto L_0x0095
        L_0x0090:
            r1 = move-exception
            r5 = r3
            goto L_0x00a0
        L_0x0093:
            r1 = move-exception
            r5 = r3
        L_0x0095:
            r1.printStackTrace()     // Catch:{ all -> 0x008c }
            int r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x00ff
        L_0x009c:
            r0.convertPushFree(r5)
            goto L_0x00ff
        L_0x00a0:
            int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r2 == 0) goto L_0x00a7
            r0.convertPushFree(r5)
        L_0x00a7:
            throw r1
        L_0x00a8:
            android.content.Intent r1 = r8.getIntent()
            java.lang.String r1 = r1.getStringExtra(r3)
            java.lang.String r3 = "add"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00ff
            java.io.File r1 = new java.io.File
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = com.zkteco.android.zkcore.utils.ZKFilePath.IMAGE_PATH
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = "temp"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.<init>(r3)
            boolean r3 = r1.exists()
            if (r3 != 0) goto L_0x00db
            r1.mkdirs()
        L_0x00db:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = com.zkteco.android.zkcore.utils.ZKFilePath.IMAGE_PATH
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = "temp/"
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = r8.userpin
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = com.zkteco.android.zkcore.utils.ZKFilePath.SUFFIX_IMAGE
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            compressPhoto(r0, r1, r2)
        L_0x00ff:
            r0 = 1999(0x7cf, float:2.801E-42)
            r8.setResult(r0)
            r8.finish()
        L_0x0107:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffIconCollectionActivity.savePhoto():void");
    }

    /* access modifiers changed from: private */
    public void showToast() {
        if (Build.VERSION.SDK_INT != 28) {
            Toast toast2 = this.toast;
            if (toast2 == null) {
                this.toast = Toast.makeText(this, R.string.zk_staff_move_face_to_windows_keep, 0);
            } else {
                toast2.setText(R.string.zk_staff_move_face_to_windows_keep);
                this.toast.setDuration(0);
            }
            this.toast.show();
        } else if (SystemClock.elapsedRealtime() - this.lastShowToastTime > 2500) {
            Toast.makeText(this, R.string.zk_staff_move_face_to_windows_keep, 0).show();
            this.lastShowToastTime = SystemClock.elapsedRealtime();
        }
    }

    /* access modifiers changed from: private */
    public void setBtTakeVisibility(int i) {
        this.mBTtakephoto.setVisibility(i);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        releaseHandler();
        releaseCameraView();
        this.isDestory = true;
        Glide.with(getApplicationContext()).pauseRequests();
    }

    private void setPreviewSize() {
        ViewGroup.LayoutParams layoutParams = this.mCameraView.getLayoutParams();
        layoutParams.width = (int) (((float) DeviceManager.getDefault().getCameraConfig().getPreviewWidth()) * 0.5f);
        layoutParams.height = (int) (((float) DeviceManager.getDefault().getCameraConfig().getPreviewHeight()) * 0.5f);
        String str = TAG;
        Log.d(str, "lp.width:" + layoutParams.width);
        Log.d(str, "lp.height:" + layoutParams.height);
        this.mCameraView.setLayoutParams(layoutParams);
    }

    public void onBackPressed() {
        setResult(RESULT_CODE_ADD);
        super.onBackPressed();
    }

    class ImageFeedReceived implements Runnable {
        byte[] bytes;
        ICameraPictureListener cameraPictureListener;
        int height;
        int width;

        public ImageFeedReceived(byte[] bArr, int i, int i2, ICameraPictureListener iCameraPictureListener) {
            this.width = i;
            this.height = i2;
            this.cameraPictureListener = iCameraPictureListener;
            byte[] bArr2 = new byte[bArr.length];
            this.bytes = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        }

        public void run() {
            ZkDetectInfo[] zkDetectInfoArr = new ZkDetectInfo[1];
            if (ZkFaceManager.getInstance().detectFromNV21(this.bytes, this.width, this.height, zkDetectInfoArr) == 0) {
                ZkDetectInfo zkDetectInfo = zkDetectInfoArr[0];
                if (zkDetectInfo != null) {
                    Face face = zkDetectInfo.face[0];
                    Rect rect = face.rect;
                    ZkFaceManager.getInstance().resetTrackId(face.trackId);
                    Bitmap nv21ToBitmap = BitmapHelper.nv21ToBitmap(this.bytes, this.width, this.height);
                    if (nv21ToBitmap != null) {
                        Bitmap unused = ZKStaffIconCollectionActivity.this.mAvatar = BitmapUtils.cropAvatar(nv21ToBitmap, rect);
                        ZKStaffIconCollectionActivity.this.runOnUiThread(new Runnable() {
                            public final void run() {
                                ZKStaffIconCollectionActivity.ImageFeedReceived.this.lambda$run$0$ZKStaffIconCollectionActivity$ImageFeedReceived();
                            }
                        });
                        return;
                    }
                    return;
                }
                ZKStaffIconCollectionActivity zKStaffIconCollectionActivity = ZKStaffIconCollectionActivity.this;
                zKStaffIconCollectionActivity.runOnUiThread(new Runnable() {
                    public final void run() {
                        ZKStaffIconCollectionActivity.this.showToast();
                    }
                });
            }
        }

        public /* synthetic */ void lambda$run$0$ZKStaffIconCollectionActivity$ImageFeedReceived() {
            ZKStaffIconCollectionActivity.this.mBTsave.setVisibility(0);
            if (ZKStaffIconCollectionActivity.this.mAvatar != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZKStaffIconCollectionActivity.this.mAvatar.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                if (!ZKStaffIconCollectionActivity.this.isFinishing()) {
                    ZKStaffIconCollectionActivity.this.ivLeft.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                    Log.d(ZKStaffIconCollectionActivity.TAG, "run: 10");
                }
            }
            ZKStaffIconCollectionActivity.this.setBtTakeVisibility(0);
        }
    }
}
