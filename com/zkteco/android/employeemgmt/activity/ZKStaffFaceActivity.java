package com.zkteco.android.employeemgmt.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.uphoto.liveness.LivenessDetecter;
import com.uphoto.liveness.UlivenessTypes;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.device.camera.CameraViewManager;
import com.zktechnology.android.device.camera.CameraWatchDog;
import com.zktechnology.android.device.camera.CameraWatchDogTask;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.strategy.BackgroundThreadExecutor;
import com.zktechnology.android.utils.AES256Util;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.ZkBroadcastUtils;
import com.zktechnology.android.utils.ZkG6ShellCMD;
import com.zkteco.adk.core.task.ZkThreadPoolManager;
import com.zkteco.android.core.sdk.LightManager;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.employeemgmt.face.EnrollVLFaceInfo;
import com.zkteco.android.employeemgmt.util.HasFaceUtils;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.android.zkcore.view.alert.ZKConfirmDialog;
import com.zkteco.edk.camera.lib.ZkCameraPreviewCallback;
import com.zkteco.edk.camera.lib.ZkCameraView;
import com.zkteco.liveface562.ZkFaceManager;
import com.zkteco.liveface562.bean.Face;
import com.zkteco.liveface562.bean.IdentifyInfo;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import magic.hu.imageutil.YUVUtils;

public class ZKStaffFaceActivity extends ZKStaffBaseActivity {
    private static final String CAN_OVERWRITE = "1";
    /* access modifiers changed from: private */
    public static final String TAG = "ZKStaffFaceActivity";
    public static final String UPDATE_FACE_TEMPLATE_ACTION = "com.zkteco.android.employeemgmt.action.UPDATE_FACE_TEMPLATE";
    private float Volume;
    private AssetFileDescriptor afd;
    /* access modifiers changed from: private */
    public volatile int colorHeight;
    /* access modifiers changed from: private */
    public volatile int colorWidth;
    /* access modifiers changed from: private */
    public Rect faceRect = new Rect();
    private ImageView faceScanLine;
    private volatile int irHeight;
    private volatile int irWidth;
    private volatile boolean isAutoControllerLight = true;
    private boolean isFillLightOn = false;
    private boolean isModify = false;
    private boolean isNewFace = true;
    private boolean isReEnroll = false;
    private ImageView lightBtn;
    private LightManager lightManager = null;
    private byte[] mCacheNV21 = null;
    /* access modifiers changed from: private */
    public int mCameraType;
    private ZkCameraView mCameraView;
    private final ZkCameraPreviewCallback mColorPreviewCallback = new ZkCameraPreviewCallback() {
        public final void onPreviewFrame(byte[] bArr, Camera camera, int i, int i2) {
            ZKStaffFaceActivity.this.lambda$new$10$ZKStaffFaceActivity(bArr, camera, i, i2);
        }
    };
    private CameraWatchDogTask mColorWatchDog;
    private Thread mEnrollFaceThread = null;
    private Rect mEnrollRect;
    private final Runnable mEnrollVLTaskFor5_6_2 = new Runnable() {
        /* JADX WARNING: Code restructure failed: missing block: B:126:0x01e1, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:127:0x01e2, code lost:
            r19 = r5;
            r4 = r8;
            r10 = r17;
            r9 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:141:0x022c, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:142:0x022d, code lost:
            r19 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x005b, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x005c, code lost:
            r19 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:168:0x02cb, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:169:0x02cd, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:170:0x02ce, code lost:
            r19 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:171:0x02d0, code lost:
            r20 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:173:0x02da, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:174:0x02db, code lost:
            r2 = r0;
            r8 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:201:0x0356, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:202:0x0357, code lost:
            r5 = r0;
            r4 = r8;
            r6 = r11;
            r10 = r17;
            r9 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:295:0x0516, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:296:0x0519, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:308:0x054a, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:309:0x054b, code lost:
            r10 = r2;
            r15 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:321:0x057e, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:322:0x057f, code lost:
            r5 = r0;
            r4 = r8;
            r2 = r10;
            r6 = r11;
            r3 = r15;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:323:0x0586, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:324:0x0587, code lost:
            r10 = r2;
            r15 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:325:0x0589, code lost:
            r9 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:326:0x058b, code lost:
            r5 = r0;
            r4 = r8;
            r6 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:327:0x0590, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:328:0x0591, code lost:
            r10 = r2;
            r15 = r3;
            r11 = r6;
            r9 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a1, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:332:0x05a5, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:333:0x05a6, code lost:
            r10 = r2;
            r15 = r3;
            r19 = r5;
            r20 = r11;
            r9 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:334:0x05b0, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:335:0x05b1, code lost:
            r10 = r2;
            r15 = r3;
            r19 = r5;
            r20 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a2, code lost:
            r19 = r5;
            r12 = r6;
            r6 = null;
            r13 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:350:0x05ff, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:351:0x0600, code lost:
            r5 = r0;
            r2 = r10;
            r6 = r11;
            r3 = r15;
            r4 = r16;
            r10 = r17;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:352:0x0608, code lost:
            r11 = r20;
            r13 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:353:0x060d, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:354:0x060f, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:355:0x0610, code lost:
            r5 = r0;
            r2 = r10;
            r6 = r11;
            r3 = r15;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:356:0x0615, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:357:0x0617, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:358:0x0618, code lost:
            r15 = r3;
            r19 = r5;
            r17 = r10;
            r20 = r11;
            r10 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:359:0x0620, code lost:
            r11 = r6;
            r5 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:360:0x0622, code lost:
            r4 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:361:0x0625, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:362:0x0626, code lost:
            r11 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:363:0x0627, code lost:
            r2 = r0;
            r8 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:364:0x062b, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:365:0x062c, code lost:
            r15 = r3;
            r16 = r4;
            r19 = r5;
            r17 = r10;
            r20 = r11;
            r10 = r2;
            r11 = r6;
            r5 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:368:0x063e, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:369:0x063f, code lost:
            r11 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:370:0x0640, code lost:
            r2 = r0;
            r8 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:383:0x0684, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:384:0x0685, code lost:
            r5 = r0;
            r2 = r10;
            r3 = r15;
            r4 = r16;
            r10 = r17;
            r11 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:385:0x068f, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:386:0x0690, code lost:
            r15 = r3;
            r16 = r4;
            r19 = r5;
            r17 = r10;
            r20 = r11;
            r10 = r2;
            r5 = r0;
            r10 = r17;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:387:0x069e, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:388:0x069f, code lost:
            r16 = r4;
            r19 = r5;
            r17 = r10;
            r20 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:389:0x06a8, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:390:0x06a9, code lost:
            r16 = r4;
            r19 = r5;
            r17 = r10;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:391:0x06b0, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:392:0x06b1, code lost:
            r2 = r0;
            r8 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:393:0x06b5, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:394:0x06b6, code lost:
            r19 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:395:0x06b8, code lost:
            r5 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:396:0x06b9, code lost:
            r6 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:405:0x06cf, code lost:
            com.zkteco.liveface562.ZkFaceManager.getInstance().resetTrackId(r6[r8].trackId);
            r8 = r8 + 1;
            r2 = r2;
            r3 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:411:0x06f7, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:412:0x06f8, code lost:
            r2 = r0;
            r8 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:414:?, code lost:
            r2.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:415:0x06fd, code lost:
            com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.access$300(r1.this$0).clear();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:416:0x0706, code lost:
            if (r8 == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:419:0x070b, code lost:
            r2 = r8.length;
            r6 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:420:0x070d, code lost:
            if (r6 < r2) goto L_0x070f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:421:0x070f, code lost:
            com.zkteco.liveface562.ZkFaceManager.getInstance().resetTrackId(r8[r6].trackId);
            r6 = r6 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:422:0x071e, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:423:0x071f, code lost:
            r2 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:430:0x0732, code lost:
            com.zkteco.liveface562.ZkFaceManager.getInstance().resetTrackId(r8[r6].trackId);
            r6 = r6 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:453:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:454:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:173:0x02da A[ExcHandler: InterruptedException (r0v35 'e' java.lang.InterruptedException A[CUSTOM_DECLARE]), Splitter:B:121:0x01d0] */
        /* JADX WARNING: Removed duplicated region for block: B:251:0x044e A[SYNTHETIC, Splitter:B:251:0x044e] */
        /* JADX WARNING: Removed duplicated region for block: B:263:0x048b A[SYNTHETIC, Splitter:B:263:0x048b] */
        /* JADX WARNING: Removed duplicated region for block: B:353:0x060d A[ExcHandler: all (th java.lang.Throwable), PHI: r11 
          PHI: (r11v19 com.zkteco.liveface562.bean.Face[]) = (r11v22 com.zkteco.liveface562.bean.Face[]), (r11v22 com.zkteco.liveface562.bean.Face[]), (r11v22 com.zkteco.liveface562.bean.Face[]), (r11v22 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]) binds: [B:337:0x05c2, B:338:?, B:339:0x05cb, B:340:?, B:248:0x0442, B:263:0x048b, B:311:0x0552, B:312:?, B:298:0x051e, B:299:?, B:269:0x049a, B:287:0x04f5, B:288:?, B:290:0x04f9, B:278:0x04b1, B:279:?, B:251:0x044e, B:252:?, B:188:0x0323, B:189:?, B:204:0x0362, B:227:0x03e9, B:191:0x0327, B:192:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:188:0x0323] */
        /* JADX WARNING: Removed duplicated region for block: B:356:0x0615 A[ExcHandler: InterruptedException (e java.lang.InterruptedException), PHI: r11 
          PHI: (r11v18 com.zkteco.liveface562.bean.Face[]) = (r11v22 com.zkteco.liveface562.bean.Face[]), (r11v22 com.zkteco.liveface562.bean.Face[]), (r11v22 com.zkteco.liveface562.bean.Face[]), (r11v22 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v28 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]), (r11v31 com.zkteco.liveface562.bean.Face[]) binds: [B:337:0x05c2, B:338:?, B:339:0x05cb, B:340:?, B:248:0x0442, B:263:0x048b, B:311:0x0552, B:312:?, B:298:0x051e, B:299:?, B:269:0x049a, B:287:0x04f5, B:288:?, B:290:0x04f9, B:278:0x04b1, B:279:?, B:251:0x044e, B:252:?, B:188:0x0323, B:189:?, B:204:0x0362, B:227:0x03e9, B:191:0x0327, B:192:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:188:0x0323] */
        /* JADX WARNING: Removed duplicated region for block: B:361:0x0625 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:87:0x0160] */
        /* JADX WARNING: Removed duplicated region for block: B:368:0x063e A[ExcHandler: InterruptedException (e java.lang.InterruptedException), Splitter:B:87:0x0160] */
        /* JADX WARNING: Removed duplicated region for block: B:371:0x0644  */
        /* JADX WARNING: Removed duplicated region for block: B:381:0x066c A[LOOP:14: B:380:0x066a->B:381:0x066c, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:391:0x06b0 A[ExcHandler: all (r0v5 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:61:0x0104] */
        /* JADX WARNING: Removed duplicated region for block: B:405:0x06cf A[LOOP:15: B:404:0x06cd->B:405:0x06cf, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:411:0x06f7 A[ExcHandler: InterruptedException (r0v1 'e' java.lang.InterruptedException A[CUSTOM_DECLARE]), Splitter:B:9:0x0047] */
        /* JADX WARNING: Removed duplicated region for block: B:419:0x070b  */
        /* JADX WARNING: Removed duplicated region for block: B:430:0x0732 A[LOOP:17: B:429:0x0730->B:430:0x0732, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:453:? A[RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x00d2  */
        /* JADX WARNING: Removed duplicated region for block: B:66:0x0115 A[SYNTHETIC, Splitter:B:66:0x0115] */
        /* JADX WARNING: Removed duplicated region for block: B:68:0x0128 A[SYNTHETIC, Splitter:B:68:0x0128] */
        /* JADX WARNING: Removed duplicated region for block: B:71:0x0132  */
        /* JADX WARNING: Removed duplicated region for block: B:72:0x0143  */
        /* JADX WARNING: Removed duplicated region for block: B:84:0x0156 A[SYNTHETIC, Splitter:B:84:0x0156] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r22 = this;
                r1 = r22
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                int r2 = r2.colorWidth
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r3 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                int r3 = r3.colorHeight
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                com.zkteco.android.db.orm.manager.DataManager r5 = com.zktechnology.android.utils.DBManager.getInstance()
                java.lang.String r6 = "~FaceEThreshold"
                r7 = 77
                int r5 = r5.getIntOption(r6, r7)
                int unused = r4.mFaceEnrollThr = r5
                com.zkteco.android.db.orm.manager.DataManager r4 = com.zktechnology.android.utils.DBManager.getInstance()
                java.lang.String r5 = "IsSupportFaceAntiFake"
                r6 = 0
                int r4 = r4.getIntOption(r5, r6)
                r7 = 1
                if (r4 != r7) goto L_0x0039
                com.zktechnology.android.device.DeviceManager r4 = com.zktechnology.android.device.DeviceManager.getDefault()
                boolean r4 = r4.isSupportFaceAntiFake()
                if (r4 == 0) goto L_0x0039
                r4 = r7
                goto L_0x003a
            L_0x0039:
                r4 = r6
            L_0x003a:
                if (r4 == 0) goto L_0x0041
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r8 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                r8.controlInfraredLight(r7)
            L_0x0041:
                r11 = r6
                r13 = r11
                r9 = 0
                r10 = 0
                r12 = 0
                r14 = 0
            L_0x0047:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r15 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x06b5, all -> 0x06b0 }
                java.util.concurrent.ArrayBlockingQueue r15 = r15.mNV21ForEnroll     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x06b5, all -> 0x06b0 }
                java.lang.Object r15 = r15.take()     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x06b5, all -> 0x06b0 }
                byte[] r15 = (byte[]) r15     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x06b5, all -> 0x06b0 }
                if (r9 != 0) goto L_0x0062
                int r8 = r15.length     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                byte[] r9 = java.util.Arrays.copyOf(r15, r8)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                goto L_0x0066
            L_0x005b:
                r0 = move-exception
                r19 = r5
            L_0x005e:
                r6 = 0
            L_0x005f:
                r5 = r0
                goto L_0x06ba
            L_0x0062:
                int r8 = r15.length     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x06b5, all -> 0x06b0 }
                java.lang.System.arraycopy(r15, r6, r9, r6, r8)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x06b5, all -> 0x06b0 }
            L_0x0066:
                if (r4 == 0) goto L_0x00bb
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r15 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                int r15 = r15.mCameraType     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                if (r15 <= r7) goto L_0x00bb
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r15 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                java.util.concurrent.ArrayBlockingQueue r15 = r15.mIrNV21ForEnroll     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                r6 = 100
                java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                java.lang.Object r6 = r15.poll(r6, r8)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                byte[] r6 = (byte[]) r6     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                if (r6 != 0) goto L_0x00ad
                java.lang.String r7 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.TAG     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x00a8, all -> 0x06b0 }
                java.lang.String r8 = "irNv21 == null"
                android.util.Log.d(r7, r8)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x00a8, all -> 0x06b0 }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r7 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x00a1, all -> 0x06b0 }
                r8 = 2131755479(0x7f1001d7, float:1.9141838E38)
                r7.showTips(r8)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x00a1, all -> 0x06b0 }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r7 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r7 = r7.mNV21ForEnroll
                r7.clear()
                r12 = r6
            L_0x009d:
                r6 = 0
                r7 = 1
                r13 = 0
                goto L_0x0047
            L_0x00a1:
                r0 = move-exception
                r19 = r5
                r12 = r6
                r6 = 0
                r13 = 0
                goto L_0x005f
            L_0x00a8:
                r0 = move-exception
                r19 = r5
                r12 = r6
                goto L_0x005e
            L_0x00ad:
                if (r10 != 0) goto L_0x00b5
                int r7 = r6.length     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x00a8, all -> 0x06b0 }
                byte[] r10 = java.util.Arrays.copyOf(r6, r7)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x00a8, all -> 0x06b0 }
                goto L_0x00ba
            L_0x00b5:
                int r7 = r6.length     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x00a8, all -> 0x06b0 }
                r8 = 0
                java.lang.System.arraycopy(r6, r8, r10, r8, r7)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x00a8, all -> 0x06b0 }
            L_0x00ba:
                r12 = r6
            L_0x00bb:
                if (r4 == 0) goto L_0x00c9
                com.zktechnology.android.device.DeviceManager r6 = com.zktechnology.android.device.DeviceManager.getDefault()     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                boolean r4 = r6.isG6()     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                if (r4 != 0) goto L_0x00c9
                r4 = 1
                goto L_0x00ca
            L_0x00c9:
                r4 = 0
            L_0x00ca:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x06a8, all -> 0x06b0 }
                int r6 = r6.mCameraType     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x06a8, all -> 0x06b0 }
                if (r11 == r6) goto L_0x00fd
                r6 = 1
                com.zkteco.liveface562.bean.ZkFaceConfig[] r7 = new com.zkteco.liveface562.bean.ZkFaceConfig[r6]     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                com.zkteco.liveface562.ZkFaceManager r6 = com.zkteco.liveface562.ZkFaceManager.getInstance()     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                int r6 = r6.getConfig(r7)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                if (r6 == 0) goto L_0x00ea
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r6 = r6.mNV21ForEnroll
                r6.clear()
                goto L_0x06ef
            L_0x00ea:
                r6 = 0
                r7 = r7[r6]     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                r7.setRgbIrLivenessEnabled(r4)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                com.zkteco.liveface562.ZkFaceManager r6 = com.zkteco.liveface562.ZkFaceManager.getInstance()     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                r6.setConfig(r7)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                int r11 = r6.mCameraType     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
            L_0x00fd:
                if (r2 == 0) goto L_0x0104
                if (r3 != 0) goto L_0x0102
                goto L_0x0104
            L_0x0102:
                r6 = 1
                goto L_0x0111
            L_0x0104:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x069e, all -> 0x06b0 }
                int r2 = r6.colorWidth     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x069e, all -> 0x06b0 }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x069e, all -> 0x06b0 }
                int r3 = r6.colorHeight     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x069e, all -> 0x06b0 }
                goto L_0x0102
            L_0x0111:
                com.zkteco.liveface562.bean.ZkDetectInfo[] r7 = new com.zkteco.liveface562.bean.ZkDetectInfo[r6]     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x068f, all -> 0x06b0 }
                if (r4 == 0) goto L_0x0128
                com.zkteco.liveface562.ZkFaceManager r16 = com.zkteco.liveface562.ZkFaceManager.getInstance()     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                r17 = r9
                r18 = r10
                r19 = r2
                r20 = r3
                r21 = r7
                int r6 = r16.detectFacesFromRGBIR(r17, r18, r19, r20, r21)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                goto L_0x0130
            L_0x0128:
                com.zkteco.liveface562.ZkFaceManager r6 = com.zkteco.liveface562.ZkFaceManager.getInstance()     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x068f, all -> 0x06b0 }
                int r6 = r6.detectFromNV21(r9, r2, r3, r7)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x068f, all -> 0x06b0 }
            L_0x0130:
                if (r6 == 0) goto L_0x0143
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r6 = r6.mNV21ForEnroll
                r6.clear()
                r16 = r4
                r18 = r9
                r17 = r10
                goto L_0x01af
            L_0x0143:
                r6 = 0
                r7 = r7[r6]     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x068f, all -> 0x06b0 }
                if (r7 == 0) goto L_0x0153
                com.zkteco.liveface562.bean.Face[] r6 = r7.face     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                if (r6 == 0) goto L_0x0153
                com.zkteco.liveface562.bean.Face[] r6 = r7.face     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                int r6 = r6.length     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x005b, all -> 0x06b0 }
                if (r6 <= 0) goto L_0x0153
                r6 = 1
                goto L_0x0154
            L_0x0153:
                r6 = 0
            L_0x0154:
                if (r6 == 0) goto L_0x0644
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x068f, all -> 0x06b0 }
                com.zkteco.liveface562.bean.Face[] r8 = r7.face     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x068f, all -> 0x06b0 }
                r6.sortByFaceRect(r8)     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x068f, all -> 0x06b0 }
                com.zkteco.liveface562.bean.Face[] r6 = r7.face     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x068f, all -> 0x06b0 }
                r8 = 0
                r15 = r6[r8]     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x062b, all -> 0x0625 }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r8 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x062b, all -> 0x0625 }
                r16 = r4
                android.graphics.Rect r4 = r15.rect     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0617, all -> 0x0625 }
                android.graphics.Rect unused = r8.faceRect = r4     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0617, all -> 0x0625 }
                byte[] r4 = r7.message     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0617, all -> 0x0625 }
                if (r4 == 0) goto L_0x05b9
                byte[] r4 = r7.message     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0617, all -> 0x0625 }
                int r4 = r4.length     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0617, all -> 0x0625 }
                if (r4 != 0) goto L_0x0176
                goto L_0x05b9
            L_0x0176:
                java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0617, all -> 0x0625 }
                r4.<init>()     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0617, all -> 0x0625 }
                com.zkteco.liveface562.ZkFaceManager r8 = com.zkteco.liveface562.ZkFaceManager.getInstance()     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0617, all -> 0x0625 }
                r17 = r10
                byte[] r10 = r7.message     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x05b0, all -> 0x0625 }
                int r8 = r8.dbIdentify(r10, r4)     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x05b0, all -> 0x0625 }
                if (r8 == 0) goto L_0x01b7
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r6 == 0) goto L_0x01ad
                int r4 = r6.length
                if (r4 <= 0) goto L_0x01ad
                int r4 = r6.length
                r7 = 0
            L_0x0199:
                if (r7 >= r4) goto L_0x01ad
                r8 = r6[r7]
                com.zkteco.liveface562.ZkFaceManager r10 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                r18 = r9
                long r8 = r8.trackId
                r10.resetTrackId(r8)
                int r7 = r7 + 1
                r9 = r18
                goto L_0x0199
            L_0x01ad:
                r18 = r9
            L_0x01af:
                r4 = r16
                r10 = r17
                r9 = r18
                goto L_0x06ef
            L_0x01b7:
                r18 = r9
                com.zkteco.android.db.orm.manager.DataManager r8 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x05a5, all -> 0x0625 }
                r9 = 0
                int r8 = r8.getIntOption(r5, r9)     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x05a5, all -> 0x0625 }
                r9 = 1
                if (r8 != r9) goto L_0x01c7
                r8 = 1
                goto L_0x01c8
            L_0x01c7:
                r8 = 0
            L_0x01c8:
                boolean r9 = r4.isEmpty()     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0597, all -> 0x0625 }
                if (r9 != 0) goto L_0x02e4
                if (r8 == 0) goto L_0x02df
                com.zktechnology.android.device.DeviceManager r7 = com.zktechnology.android.device.DeviceManager.getDefault()     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cd }
                boolean r7 = r7.isG6()     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cd }
                if (r7 == 0) goto L_0x01eb
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r7 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x01e1 }
                boolean r7 = r7.is3DLiveCheckPass(r2, r3, r12)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x01e1 }
                goto L_0x01f1
            L_0x01e1:
                r0 = move-exception
                r19 = r5
                r4 = r8
                r10 = r17
                r9 = r18
                goto L_0x005f
            L_0x01eb:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r7 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cd }
                boolean r7 = r7.isPassFaceLivenessLimitFor5_6_2(r4)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cd }
            L_0x01f1:
                if (r7 != 0) goto L_0x0231
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x022c }
                r7 = 2131755479(0x7f1001d7, float:1.9141838E38)
                r4.showTips(r7)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x022c }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r6 == 0) goto L_0x0221
                int r4 = r6.length
                if (r4 <= 0) goto L_0x0221
                int r4 = r6.length
                r7 = 0
            L_0x020b:
                if (r7 >= r4) goto L_0x0221
                r9 = r6[r7]
                com.zkteco.liveface562.ZkFaceManager r10 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                r13 = r4
                r19 = r5
                long r4 = r9.trackId
                r10.resetTrackId(r4)
                int r7 = r7 + 1
                r4 = r13
                r5 = r19
                goto L_0x020b
            L_0x0221:
                r19 = r5
                r4 = r8
                r10 = r17
                r9 = r18
                r5 = r19
                goto L_0x009d
            L_0x022c:
                r0 = move-exception
                r19 = r5
                goto L_0x02d2
            L_0x0231:
                r19 = r5
                r5 = 0
                java.lang.Object r7 = r4.get(r5)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                com.zkteco.liveface562.bean.IdentifyInfo r7 = (com.zkteco.liveface562.bean.IdentifyInfo) r7     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.String r5 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.TAG     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                r9.<init>()     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.String r10 = "userId = "
                java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.String r10 = r7.pin     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.String r10 = "; score = "
                java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                float r10 = r7.identifyScore     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.String r10 = "; mFaceEnrollThr = "
                java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r10 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                int r10 = r10.mFaceEnrollThr     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.String r10 = "; checkFaceLivenessCount = "
                java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.StringBuilder r9 = r9.append(r13)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                java.lang.String r9 = r9.toString()     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                android.util.Log.i(r5, r9)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                if (r14 != 0) goto L_0x0283
                java.lang.String r14 = r7.pin     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x0281 }
                goto L_0x0283
            L_0x0281:
                r0 = move-exception
                goto L_0x02d2
            L_0x0283:
                java.lang.String r5 = r7.pin     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                boolean r5 = r5.equals(r14)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                if (r5 != 0) goto L_0x028f
                r5 = 1
                r13 = 0
                r14 = 0
                goto L_0x0290
            L_0x028f:
                r5 = 1
            L_0x0290:
                int r13 = r13 + r5
                int r5 = com.zktechnology.android.launcher2.ZKLauncher.sFaceAntiFakeRepeatedVerificationTimes     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                if (r13 < r5) goto L_0x029b
                r20 = r11
                r13 = 0
            L_0x0298:
                r11 = r6
                goto L_0x0442
            L_0x029b:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                r5 = 2131755479(0x7f1001d7, float:1.9141838E38)
                r4.showTips(r5)     // Catch:{ InterruptedException -> 0x02da, Exception -> 0x02cb }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r6 == 0) goto L_0x02c7
                int r4 = r6.length
                if (r4 <= 0) goto L_0x02c7
                int r4 = r6.length
                r5 = 0
            L_0x02b3:
                if (r5 >= r4) goto L_0x02c7
                r7 = r6[r5]
                com.zkteco.liveface562.ZkFaceManager r9 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                r20 = r11
                long r10 = r7.trackId
                r9.resetTrackId(r10)
                int r5 = r5 + 1
                r11 = r20
                goto L_0x02b3
            L_0x02c7:
                r20 = r11
                goto L_0x0437
            L_0x02cb:
                r0 = move-exception
                goto L_0x02d0
            L_0x02cd:
                r0 = move-exception
                r19 = r5
            L_0x02d0:
                r20 = r11
            L_0x02d2:
                r5 = r0
                r4 = r8
                r10 = r17
                r9 = r18
                goto L_0x06ba
            L_0x02da:
                r0 = move-exception
                r2 = r0
                r8 = r6
                goto L_0x06fa
            L_0x02df:
                r19 = r5
                r20 = r11
                goto L_0x0298
            L_0x02e4:
                r19 = r5
                r20 = r11
                java.lang.String r5 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.TAG     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0590, all -> 0x0625 }
                java.lang.String r9 = "identifyList == null || identifyList.isEmpty()"
                android.util.Log.d(r5, r9)     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0590, all -> 0x0625 }
                r5 = 1
                int[] r9 = new int[r5]     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0590, all -> 0x0625 }
                com.zkteco.liveface562.ZkFaceManager r10 = com.zkteco.liveface562.ZkFaceManager.getInstance()     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0590, all -> 0x0625 }
                int r10 = r10.dbCount(r9)     // Catch:{ InterruptedException -> 0x063e, Exception -> 0x0590, all -> 0x0625 }
                if (r10 == 0) goto L_0x0321
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r6 == 0) goto L_0x0437
                int r4 = r6.length
                if (r4 <= 0) goto L_0x0437
                int r4 = r6.length
                r7 = 0
            L_0x030e:
                if (r7 >= r4) goto L_0x0437
                r9 = r6[r7]
                com.zkteco.liveface562.ZkFaceManager r10 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                r11 = r6
                long r5 = r9.trackId
                r10.resetTrackId(r5)
                int r7 = r7 + 1
                r6 = r11
                r5 = 1
                goto L_0x030e
            L_0x0321:
                r11 = r6
                r5 = 0
                r6 = r9[r5]     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0586, all -> 0x060d }
                if (r6 == 0) goto L_0x0360
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0356, all -> 0x060d }
                r5 = 2131755479(0x7f1001d7, float:1.9141838E38)
                r4.showTips(r5)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0356, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r11 == 0) goto L_0x034f
                int r4 = r11.length
                if (r4 <= 0) goto L_0x034f
                int r4 = r11.length
                r5 = 0
            L_0x033f:
                if (r5 >= r4) goto L_0x034f
                r6 = r11[r5]
                com.zkteco.liveface562.ZkFaceManager r7 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                long r9 = r6.trackId
                r7.resetTrackId(r9)
                int r5 = r5 + 1
                goto L_0x033f
            L_0x034f:
                r4 = r8
                r10 = r17
                r9 = r18
                goto L_0x05f9
            L_0x0356:
                r0 = move-exception
                r5 = r0
                r4 = r8
                r6 = r11
                r10 = r17
                r9 = r18
                goto L_0x0608
            L_0x0360:
                if (r8 == 0) goto L_0x0442
                com.zktechnology.android.device.DeviceManager r5 = com.zktechnology.android.device.DeviceManager.getDefault()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                boolean r5 = r5.isG6()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                if (r5 == 0) goto L_0x0374
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r5 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                boolean r5 = r5.is3DLiveCheckPass(r2, r3, r12)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                r6 = 0
                goto L_0x03bc
            L_0x0374:
                java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                r5.<init>()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                com.zkteco.liveface562.ZkFaceManager r6 = com.zkteco.liveface562.ZkFaceManager.getInstance()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                byte[] r7 = r7.message     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                int r6 = r6.livenessClassify(r7, r5)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                if (r6 != 0) goto L_0x0417
                boolean r6 = r5.isEmpty()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                if (r6 == 0) goto L_0x038d
                goto L_0x0417
            L_0x038d:
                r6 = 0
                java.lang.Object r5 = r5.get(r6)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                com.zkteco.liveface562.bean.LivenessResult r5 = (com.zkteco.liveface562.bean.LivenessResult) r5     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                java.lang.String r7 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.TAG     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                r9.<init>()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                java.lang.String r10 = "isPassFaceLivenessLimit: faceLiveness = "
                java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                float r10 = r5.livenessScore     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                java.lang.String r9 = r9.toString()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                android.util.Log.e(r7, r9)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                float r5 = r5.livenessScore     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                int r7 = com.zktechnology.android.launcher2.ZKLauncher.mFaceLiveThreshold     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                float r7 = (float) r7     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r5 < 0) goto L_0x03bb
                r5 = 1
                goto L_0x03bc
            L_0x03bb:
                r5 = r6
            L_0x03bc:
                if (r5 != 0) goto L_0x03e7
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                r5 = 2131755479(0x7f1001d7, float:1.9141838E38)
                r4.showTips(r5)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r11 == 0) goto L_0x034f
                int r4 = r11.length
                if (r4 <= 0) goto L_0x034f
                int r4 = r11.length
                r5 = r6
            L_0x03d6:
                if (r5 >= r4) goto L_0x034f
                r7 = r11[r5]
                com.zkteco.liveface562.ZkFaceManager r9 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                long r6 = r7.trackId
                r9.resetTrackId(r6)
                int r5 = r5 + 1
                r6 = 0
                goto L_0x03d6
            L_0x03e7:
                int r13 = r13 + 1
                int r5 = com.zktechnology.android.launcher2.ZKLauncher.sFaceAntiFakeRepeatedVerificationTimes     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                if (r13 < r5) goto L_0x03ef
                r13 = 0
                goto L_0x0442
            L_0x03ef:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                r5 = 2131755479(0x7f1001d7, float:1.9141838E38)
                r4.showTips(r5)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r11 == 0) goto L_0x0437
                int r4 = r11.length
                if (r4 <= 0) goto L_0x0437
                int r4 = r11.length
                r5 = 0
            L_0x0407:
                if (r5 >= r4) goto L_0x0437
                r6 = r11[r5]
                com.zkteco.liveface562.ZkFaceManager r7 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                long r9 = r6.trackId
                r7.resetTrackId(r9)
                int r5 = r5 + 1
                goto L_0x0407
            L_0x0417:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r11 == 0) goto L_0x0437
                int r4 = r11.length
                if (r4 <= 0) goto L_0x0437
                int r4 = r11.length
                r5 = 0
            L_0x0427:
                if (r5 >= r4) goto L_0x0437
                r6 = r11[r5]
                com.zkteco.liveface562.ZkFaceManager r7 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                long r9 = r6.trackId
                r7.resetTrackId(r9)
                int r5 = r5 + 1
                goto L_0x0427
            L_0x0437:
                r4 = r8
                r10 = r17
                r9 = r18
            L_0x043c:
                r5 = r19
                r11 = r20
                goto L_0x06ef
            L_0x0442:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r5 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0586, all -> 0x060d }
                android.graphics.Rect r6 = r5.faceRect     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0586, all -> 0x060d }
                boolean r5 = r5.isFaceInTheEnrollArea(r6)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0586, all -> 0x060d }
                if (r5 != 0) goto L_0x048b
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                r4.flashTheRect()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                r5 = 2131755479(0x7f1001d7, float:1.9141838E38)
                r4.showTips(r5)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r11 == 0) goto L_0x047b
                int r4 = r11.length
                if (r4 <= 0) goto L_0x047b
                int r4 = r11.length
                r5 = 0
            L_0x046b:
                if (r5 >= r4) goto L_0x047b
                r6 = r11[r5]
                com.zkteco.liveface562.ZkFaceManager r7 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                long r9 = r6.trackId
                r7.resetTrackId(r9)
                int r5 = r5 + 1
                goto L_0x046b
            L_0x047b:
                r10 = r2
                r15 = r3
                r9 = r18
                goto L_0x0577
            L_0x0481:
                r0 = move-exception
                r5 = r0
                r4 = r8
                r6 = r11
                r10 = r17
                r9 = r18
                goto L_0x063a
            L_0x048b:
                com.zkteco.liveface562.bean.FacePose r5 = r15.pose     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0586, all -> 0x060d }
                r6 = 2131755410(0x7f100192, float:1.9141698E38)
                if (r5 == 0) goto L_0x054e
                float r7 = r5.yaw     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0586, all -> 0x060d }
                r9 = 1101004800(0x41a00000, float:20.0)
                int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                if (r7 > 0) goto L_0x051c
                float r7 = r5.pitch     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0519, all -> 0x060d }
                int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                if (r7 > 0) goto L_0x051c
                float r5 = r5.roll     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0519, all -> 0x060d }
                int r5 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
                if (r5 <= 0) goto L_0x04a8
                goto L_0x051c
            L_0x04a8:
                float r5 = r15.blur     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0519, all -> 0x060d }
                r6 = 1045220557(0x3e4ccccd, float:0.2)
                int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                if (r5 <= 0) goto L_0x04f5
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                r5 = 2131755479(0x7f1001d7, float:1.9141838E38)
                r4.showTips(r5)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                java.lang.String r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.TAG     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                r5.<init>()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                java.lang.String r6 = "[run]: face blur is "
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                float r6 = r15.blur     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                java.lang.String r5 = r5.toString()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                android.util.Log.w(r4, r5)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0481, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r11 == 0) goto L_0x047b
                int r4 = r11.length
                if (r4 <= 0) goto L_0x047b
                int r4 = r11.length
                r5 = 0
            L_0x04e5:
                if (r5 >= r4) goto L_0x047b
                r6 = r11[r5]
                com.zkteco.liveface562.ZkFaceManager r7 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                long r9 = r6.trackId
                r7.resetTrackId(r9)
                int r5 = r5 + 1
                goto L_0x04e5
            L_0x04f5:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r5 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0519, all -> 0x060d }
                r9 = r18
                r5.enrollVLBy56v2(r9, r2, r3, r4)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0516, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0516, all -> 0x060d }
                int r4 = r4.retryCount     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0516, all -> 0x060d }
                if (r4 != 0) goto L_0x0510
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0516, all -> 0x060d }
                java.lang.String r5 = "4"
                java.lang.String unused = r4.remoteEnrollResult = r5     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0516, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0516, all -> 0x060d }
                r4.finish()     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x0516, all -> 0x060d }
            L_0x0510:
                r10 = r2
                r15 = r3
                r4 = r8
                r6 = r11
                goto L_0x065a
            L_0x0516:
                r0 = move-exception
                goto L_0x058b
            L_0x0519:
                r0 = move-exception
                goto L_0x0589
            L_0x051c:
                r9 = r18
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x054a, all -> 0x060d }
                r4.showTips(r6)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x054a, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r4 = r4.mNV21ForEnroll
                r4.clear()
                if (r11 == 0) goto L_0x0547
                int r4 = r11.length
                if (r4 <= 0) goto L_0x0547
                int r4 = r11.length
                r5 = 0
            L_0x0533:
                if (r5 >= r4) goto L_0x0547
                r6 = r11[r5]
                com.zkteco.liveface562.ZkFaceManager r7 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                r10 = r2
                r15 = r3
                long r2 = r6.trackId
                r7.resetTrackId(r2)
                int r5 = r5 + 1
                r2 = r10
                r3 = r15
                goto L_0x0533
            L_0x0547:
                r10 = r2
                r15 = r3
                goto L_0x0577
            L_0x054a:
                r0 = move-exception
                r10 = r2
                r15 = r3
                goto L_0x058b
            L_0x054e:
                r10 = r2
                r15 = r3
                r9 = r18
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x057e, all -> 0x060d }
                r2.showTips(r6)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x057e, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r2 = r2.mNV21ForEnroll
                r2.clear()
                if (r11 == 0) goto L_0x0577
                int r2 = r11.length
                if (r2 <= 0) goto L_0x0577
                int r2 = r11.length
                r3 = 0
            L_0x0567:
                if (r3 >= r2) goto L_0x0577
                r4 = r11[r3]
                com.zkteco.liveface562.ZkFaceManager r5 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                long r6 = r4.trackId
                r5.resetTrackId(r6)
                int r3 = r3 + 1
                goto L_0x0567
            L_0x0577:
                r4 = r8
                r2 = r10
                r3 = r15
                r10 = r17
                goto L_0x043c
            L_0x057e:
                r0 = move-exception
                r5 = r0
                r4 = r8
                r2 = r10
                r6 = r11
                r3 = r15
                goto L_0x0638
            L_0x0586:
                r0 = move-exception
                r10 = r2
                r15 = r3
            L_0x0589:
                r9 = r18
            L_0x058b:
                r5 = r0
                r4 = r8
                r6 = r11
                goto L_0x0638
            L_0x0590:
                r0 = move-exception
                r10 = r2
                r15 = r3
                r11 = r6
                r9 = r18
                goto L_0x05a1
            L_0x0597:
                r0 = move-exception
                r10 = r2
                r15 = r3
                r19 = r5
                r20 = r11
                r9 = r18
                r11 = r6
            L_0x05a1:
                r5 = r0
                r4 = r8
                goto L_0x0638
            L_0x05a5:
                r0 = move-exception
                r10 = r2
                r15 = r3
                r19 = r5
                r20 = r11
                r9 = r18
                goto L_0x0620
            L_0x05b0:
                r0 = move-exception
                r10 = r2
                r15 = r3
                r19 = r5
                r20 = r11
                goto L_0x0620
            L_0x05b9:
                r15 = r3
                r19 = r5
                r17 = r10
                r20 = r11
                r10 = r2
                r11 = r6
                java.lang.String r2 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.TAG     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x060f, all -> 0x060d }
                java.lang.String r3 = "enrollVLBy56v2: message = null"
                android.util.Log.w(r2, r3)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x060f, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x05ff, all -> 0x060d }
                r3 = 2131755479(0x7f1001d7, float:1.9141838E38)
                r2.showTips(r3)     // Catch:{ InterruptedException -> 0x0615, Exception -> 0x05ff, all -> 0x060d }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r2 = r2.mNV21ForEnroll
                r2.clear()
                if (r11 == 0) goto L_0x05f3
                int r2 = r11.length
                if (r2 <= 0) goto L_0x05f3
                int r2 = r11.length
                r8 = 0
            L_0x05e3:
                if (r8 >= r2) goto L_0x05f3
                r3 = r11[r8]
                com.zkteco.liveface562.ZkFaceManager r4 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                long r5 = r3.trackId
                r4.resetTrackId(r5)
                int r8 = r8 + 1
                goto L_0x05e3
            L_0x05f3:
                r2 = r10
                r3 = r15
                r4 = r16
                r10 = r17
            L_0x05f9:
                r5 = r19
                r11 = r20
                goto L_0x009d
            L_0x05ff:
                r0 = move-exception
                r5 = r0
                r2 = r10
                r6 = r11
                r3 = r15
                r4 = r16
                r10 = r17
            L_0x0608:
                r11 = r20
                r13 = 0
                goto L_0x06ba
            L_0x060d:
                r0 = move-exception
                goto L_0x0627
            L_0x060f:
                r0 = move-exception
                r5 = r0
                r2 = r10
                r6 = r11
                r3 = r15
                goto L_0x0622
            L_0x0615:
                r0 = move-exception
                goto L_0x0640
            L_0x0617:
                r0 = move-exception
                r15 = r3
                r19 = r5
                r17 = r10
                r20 = r11
                r10 = r2
            L_0x0620:
                r11 = r6
                r5 = r0
            L_0x0622:
                r4 = r16
                goto L_0x0638
            L_0x0625:
                r0 = move-exception
                r11 = r6
            L_0x0627:
                r2 = r0
                r8 = r11
                goto L_0x0720
            L_0x062b:
                r0 = move-exception
                r15 = r3
                r16 = r4
                r19 = r5
                r17 = r10
                r20 = r11
                r10 = r2
                r11 = r6
                r5 = r0
            L_0x0638:
                r10 = r17
            L_0x063a:
                r11 = r20
                goto L_0x06ba
            L_0x063e:
                r0 = move-exception
                r11 = r6
            L_0x0640:
                r2 = r0
                r8 = r11
                goto L_0x06fa
            L_0x0644:
                r15 = r3
                r16 = r4
                r19 = r5
                r17 = r10
                r20 = r11
                r10 = r2
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x0684, all -> 0x06b0 }
                android.graphics.Rect r2 = r2.faceRect     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x0684, all -> 0x06b0 }
                r2.setEmpty()     // Catch:{ InterruptedException -> 0x06f7, Exception -> 0x0684, all -> 0x06b0 }
                r4 = r16
                r6 = 0
            L_0x065a:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r2 = r2.mNV21ForEnroll
                r2.clear()
                if (r6 == 0) goto L_0x067c
                int r2 = r6.length
                if (r2 <= 0) goto L_0x067c
                int r2 = r6.length
                r8 = 0
            L_0x066a:
                if (r8 >= r2) goto L_0x067c
                r3 = r6[r8]
                com.zkteco.liveface562.ZkFaceManager r5 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                r7 = r2
                long r2 = r3.trackId
                r5.resetTrackId(r2)
                int r8 = r8 + 1
                r2 = r7
                goto L_0x066a
            L_0x067c:
                r2 = r10
                r3 = r15
                r10 = r17
                r11 = r20
                goto L_0x06ed
            L_0x0684:
                r0 = move-exception
                r5 = r0
                r2 = r10
                r3 = r15
                r4 = r16
                r10 = r17
                r11 = r20
                goto L_0x06b9
            L_0x068f:
                r0 = move-exception
                r15 = r3
                r16 = r4
                r19 = r5
                r17 = r10
                r20 = r11
                r10 = r2
                r5 = r0
                r10 = r17
                goto L_0x06b9
            L_0x069e:
                r0 = move-exception
                r16 = r4
                r19 = r5
                r17 = r10
                r20 = r11
                goto L_0x06b8
            L_0x06a8:
                r0 = move-exception
                r16 = r4
                r19 = r5
                r17 = r10
                goto L_0x06b8
            L_0x06b0:
                r0 = move-exception
                r2 = r0
                r8 = 0
                goto L_0x0720
            L_0x06b5:
                r0 = move-exception
                r19 = r5
            L_0x06b8:
                r5 = r0
            L_0x06b9:
                r6 = 0
            L_0x06ba:
                r5.printStackTrace()     // Catch:{ all -> 0x06f3 }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r5 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r5 = r5.mNV21ForEnroll
                r5.clear()
                if (r6 == 0) goto L_0x06e5
                int r5 = r6.length
                if (r5 <= 0) goto L_0x06e5
                int r5 = r6.length
                r8 = 0
            L_0x06cd:
                if (r8 >= r5) goto L_0x06e5
                r7 = r6[r8]
                com.zkteco.liveface562.ZkFaceManager r15 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                r16 = r2
                r17 = r3
                long r2 = r7.trackId
                r15.resetTrackId(r2)
                int r8 = r8 + 1
                r2 = r16
                r3 = r17
                goto L_0x06cd
            L_0x06e5:
                r16 = r2
                r17 = r3
                r2 = r16
                r3 = r17
            L_0x06ed:
                r5 = r19
            L_0x06ef:
                r6 = 0
                r7 = 1
                goto L_0x0047
            L_0x06f3:
                r0 = move-exception
                r2 = r0
                r8 = r6
                goto L_0x0720
            L_0x06f7:
                r0 = move-exception
                r2 = r0
                r8 = 0
            L_0x06fa:
                r2.printStackTrace()     // Catch:{ all -> 0x071e }
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r2 = r2.mNV21ForEnroll
                r2.clear()
                if (r8 == 0) goto L_0x071d
                int r2 = r8.length
                if (r2 <= 0) goto L_0x071d
                int r2 = r8.length
                r6 = 0
            L_0x070d:
                if (r6 >= r2) goto L_0x071d
                r3 = r8[r6]
                com.zkteco.liveface562.ZkFaceManager r4 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                long r9 = r3.trackId
                r4.resetTrackId(r9)
                int r6 = r6 + 1
                goto L_0x070d
            L_0x071d:
                return
            L_0x071e:
                r0 = move-exception
                r2 = r0
            L_0x0720:
                com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity r3 = com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.this
                java.util.concurrent.ArrayBlockingQueue r3 = r3.mNV21ForEnroll
                r3.clear()
                if (r8 == 0) goto L_0x0740
                int r3 = r8.length
                if (r3 <= 0) goto L_0x0740
                int r3 = r8.length
                r6 = 0
            L_0x0730:
                if (r6 >= r3) goto L_0x0740
                r4 = r8[r6]
                com.zkteco.liveface562.ZkFaceManager r5 = com.zkteco.liveface562.ZkFaceManager.getInstance()
                long r9 = r4.trackId
                r5.resetTrackId(r9)
                int r6 = r6 + 1
                goto L_0x0730
            L_0x0740:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.AnonymousClass1.run():void");
        }
    };
    private ProgressBar mFaceEnrollProgressBar;
    /* access modifiers changed from: private */
    public int mFaceEnrollThr;
    private Paint mFacePaint;
    ZkCameraPreviewCallback mGrayPreviewCallback = new ZkCameraPreviewCallback() {
        public final void onPreviewFrame(byte[] bArr, Camera camera, int i, int i2) {
            ZKStaffFaceActivity.this.lambda$new$9$ZKStaffFaceActivity(bArr, camera, i, i2);
        }
    };
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private ZkCameraView mIrCameraView;
    /* access modifiers changed from: private */
    public final ArrayBlockingQueue<byte[]> mIrNV21ForEnroll = new ArrayBlockingQueue<>(1);
    private CameraWatchDogTask mIrWatchDog;
    /* access modifiers changed from: private */
    public volatile boolean mIsStart = false;
    private final AtomicBoolean mIsStartRestartCamera = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public final ArrayBlockingQueue<byte[]> mNV21ForEnroll = new ArrayBlockingQueue<>(1);
    private final ArrayList<EnrollVLFaceInfo> mReadyForEnrollVl = new ArrayList<>();
    private CameraWatchDog mWatchDog;
    private final CameraWatchDog.IWatchDogCallback mWatchDogCallback = new CameraWatchDog.IWatchDogCallback() {
        public final void onCameraLost(String str) {
            ZKStaffFaceActivity.this.lambda$new$4$ZKStaffFaceActivity(str);
        }
    };
    private RelativeLayout relRop;
    /* access modifiers changed from: private */
    public String remoteEnrollResult = "6";
    /* access modifiers changed from: private */
    public int retryCount = -1;
    private TextView tvPrompt;
    private UserInfo userInfo;
    private String userPin;

    private boolean hasTwoAlgorithmInit() {
        return false;
    }

    private void initCameraView() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_staff_edit_face);
        this.mCameraType = DeviceManager.getDefault().getCameraConfig().getCameraType();
        initView();
        initData();
    }

    private boolean isHaveFace() {
        return HasFaceUtils.isHasFace(this.userInfo);
    }

    private void initToolbar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.FaceToolBar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffFaceActivity.this.lambda$initToolbar$0$ZKStaffFaceActivity(view);
            }
        }, getString(R.string.zk_staff_face_title));
        zKToolbar.setRightView(getResources().getString(R.string.zk_staff_back), (View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffFaceActivity.this.lambda$initToolbar$1$ZKStaffFaceActivity(view);
            }
        });
        zKToolbar.setRightView();
    }

    public /* synthetic */ void lambda$initToolbar$0$ZKStaffFaceActivity(View view) {
        finish();
    }

    public /* synthetic */ void lambda$initToolbar$1$ZKStaffFaceActivity(View view) {
        finish();
    }

    public void initData() {
        Intent intent = getIntent();
        long longExtra = intent.getLongExtra("userInfo_id", 0);
        this.isModify = intent.getBooleanExtra("isModify", false);
        this.isReEnroll = intent.getBooleanExtra("isReEnrollFace", false);
        this.isNewFace = intent.getBooleanExtra("isNewFace", false);
        this.userPin = intent.getStringExtra("userPin");
        String stringExtra = intent.getStringExtra("overwrite");
        this.retryCount = intent.getIntExtra("retry", -1);
        this.mReadyForEnrollVl.clear();
        try {
            if (TextUtils.isEmpty(this.userPin)) {
                this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(longExtra));
            } else {
                UserInfo userInfo2 = (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", this.userPin).queryForFirst();
                this.userInfo = userInfo2;
                if (userInfo2 != null) {
                    boolean isHaveFace = isHaveFace();
                    this.isReEnroll = isHaveFace;
                    this.isNewFace = !isHaveFace;
                    this.isModify = isHaveFace;
                    if (!"1".equals(stringExtra) && this.isReEnroll) {
                        this.remoteEnrollResult = "2";
                        finish();
                        return;
                    }
                } else {
                    this.userPin = "";
                    finish();
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (this.mEnrollFaceThread == null) {
            Thread thread = new Thread(this.mEnrollVLTaskFor5_6_2);
            this.mEnrollFaceThread = thread;
            thread.start();
        }
        float previewWidth = ((float) DeviceManager.getDefault().getCameraConfig().getPreviewWidth()) / 5.0f;
        float f = 4.0f * previewWidth;
        if (this.mEnrollRect == null) {
            this.mEnrollRect = new Rect((int) previewWidth, 0, (int) f, DeviceManager.getDefault().getCameraConfig().getPreviewHeight());
        }
        this.lightManager = new LightManager(this);
        CameraWatchDog cameraWatchDog = new CameraWatchDog();
        this.mWatchDog = cameraWatchDog;
        cameraWatchDog.start();
        int intOption = DBManager.getInstance().getIntOption("CameraWatchDogMaxErrorCount", 3);
        int intOption2 = DBManager.getInstance().getIntOption("CameraWatchDogTimeInterval", 10);
        CameraWatchDogTask cameraWatchDogTask = new CameraWatchDogTask("RegisterColorCamera", intOption, intOption2);
        this.mColorWatchDog = cameraWatchDogTask;
        cameraWatchDogTask.addCallback(this.mWatchDogCallback);
        this.mWatchDog.addTask(this.mColorWatchDog);
        if (this.mCameraType > 1) {
            CameraWatchDogTask cameraWatchDogTask2 = new CameraWatchDogTask("RegisterColorCamera", intOption, intOption2);
            this.mIrWatchDog = cameraWatchDogTask2;
            cameraWatchDogTask2.addCallback(this.mWatchDogCallback);
            this.mWatchDog.addTask(this.mIrWatchDog);
        }
    }

    public /* synthetic */ void lambda$new$4$ZKStaffFaceActivity(String str) {
        ZkThreadPoolManager.getInstance().execute(new Runnable() {
            public final void run() {
                ZKStaffFaceActivity.this.lambda$new$3$ZKStaffFaceActivity();
            }
        });
    }

    public /* synthetic */ void lambda$new$3$ZKStaffFaceActivity() {
        if (this.mIsStartRestartCamera.get()) {
            FileLogUtils.writeCameraLog("ZKStaffFaceActivity Camera error, restarting now, skip!");
            return;
        }
        this.mIsStartRestartCamera.set(true);
        ZkBroadcastUtils.sendCameraErrorBroadcast(this);
        FileLogUtils.writeCameraLog("ZKStaffFaceActivity Camera error, start to restart!");
        runOnUiThread(new Runnable() {
            public final void run() {
                ZKStaffFaceActivity.this.removeCameraView();
            }
        });
        ZkG6ShellCMD.killCameraProcess();
        SystemClock.sleep(6000);
        runOnUiThread(new Runnable() {
            public final void run() {
                ZKStaffFaceActivity.this.lambda$new$2$ZKStaffFaceActivity();
            }
        });
    }

    public /* synthetic */ void lambda$new$2$ZKStaffFaceActivity() {
        try {
            addCameraView();
        } catch (Exception e) {
            FileLogUtils.writeCameraLog("ZKStaffFaceActivity Camera error, restart error, msg-> " + e.getMessage());
        } catch (Throwable th) {
            this.mIsStartRestartCamera.set(false);
            throw th;
        }
        this.mIsStartRestartCamera.set(false);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.faceScanLine.clearAnimation();
        controlInfraredLight(false);
        removeCameraView();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        LogUtils.d(TAG, "bind face service ret: " + ZkFaceManager.getInstance().bindService(this));
        initAnim();
        showInfo();
        addCameraView();
    }

    public void removeCameraView() {
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
            zkCameraView.setPreviewCallback((ZkCameraPreviewCallback) null);
            ZkCameraView zkCameraView2 = this.mCameraView;
            RelativeLayout relativeLayout = this.relRop;
            if (relativeLayout != null) {
                relativeLayout.removeView(zkCameraView2);
            }
            this.mCameraView = null;
            BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable(zkCameraView2) {
                public final /* synthetic */ ZkCameraView f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    ZKStaffFaceActivity.this.lambda$releaseColorCameraView$5$ZKStaffFaceActivity(this.f$1);
                }
            });
        }
    }

    public /* synthetic */ void lambda$releaseColorCameraView$5$ZKStaffFaceActivity(ZkCameraView zkCameraView) {
        zkCameraView.closeCamera();
        CameraWatchDogTask cameraWatchDogTask = this.mColorWatchDog;
        if (cameraWatchDogTask != null) {
            cameraWatchDogTask.pause();
        }
    }

    private void releaseIrCameraView() {
        ZkCameraView zkCameraView = this.mIrCameraView;
        if (zkCameraView != null) {
            zkCameraView.setPreviewCallback((ZkCameraPreviewCallback) null);
            ZkCameraView zkCameraView2 = this.mIrCameraView;
            this.mIrCameraView = null;
            BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable(zkCameraView2) {
                public final /* synthetic */ ZkCameraView f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    ZKStaffFaceActivity.this.lambda$releaseIrCameraView$6$ZKStaffFaceActivity(this.f$1);
                }
            });
        }
    }

    public /* synthetic */ void lambda$releaseIrCameraView$6$ZKStaffFaceActivity(ZkCameraView zkCameraView) {
        zkCameraView.closeCamera();
        CameraWatchDogTask cameraWatchDogTask = this.mIrWatchDog;
        if (cameraWatchDogTask != null) {
            cameraWatchDogTask.pause();
        }
    }

    public void addCameraView() {
        if (this.relRop.indexOfChild(this.mCameraView) == -1) {
            removeCameraView();
            if (this.mCameraType > 1) {
                if (this.mIrCameraView == null) {
                    this.mIrCameraView = CameraViewManager.createSecondary(this);
                }
                ZkCameraView zkCameraView = this.mIrCameraView;
                if (zkCameraView != null) {
                    zkCameraView.setPreviewCallback(this.mGrayPreviewCallback);
                    BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable() {
                        public final void run() {
                            ZKStaffFaceActivity.this.lambda$addCameraView$7$ZKStaffFaceActivity();
                        }
                    });
                }
            }
            ZkCameraView createDefault = CameraViewManager.createDefault(this);
            this.mCameraView = createDefault;
            createDefault.setPreviewCallback(this.mColorPreviewCallback);
            this.mCameraView.setPreviewCallback(this.mColorPreviewCallback);
            BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable() {
                public final void run() {
                    ZKStaffFaceActivity.this.lambda$addCameraView$8$ZKStaffFaceActivity();
                }
            });
            if (this.relRop.indexOfChild(this.mCameraView) == -1) {
                this.relRop.addView(this.mCameraView, 0);
                int previewWidth = DeviceManager.getDefault().getCameraConfig().getPreviewWidth();
                int previewHeight = DeviceManager.getDefault().getCameraConfig().getPreviewHeight();
                Point point = new Point();
                getWindowManager().getDefaultDisplay().getRealSize(point);
                int i = point.x;
                int i2 = point.y;
                int previewHeight2 = (DeviceManager.getDefault().getCameraConfig().getPreviewHeight() * i) / previewWidth;
                int previewWidth2 = (DeviceManager.getDefault().getCameraConfig().getPreviewWidth() * i2) / previewHeight;
                Log.e(TAG, "addCameraView: preWidth=" + previewWidth + ", preHeight=" + previewHeight + ", ScreenWidth=" + i + ", ScreenHeight=" + i2);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mCameraView.getLayoutParams();
                layoutParams.height = Math.max(previewHeight2, i2);
                layoutParams.width = Math.max(previewWidth2, i);
                layoutParams.addRule(13);
            }
        }
    }

    public /* synthetic */ void lambda$addCameraView$7$ZKStaffFaceActivity() {
        this.mIrCameraView.openCamera();
        CameraWatchDogTask cameraWatchDogTask = this.mIrWatchDog;
        if (cameraWatchDogTask != null) {
            cameraWatchDogTask.resume();
        }
    }

    public /* synthetic */ void lambda$addCameraView$8$ZKStaffFaceActivity() {
        this.mCameraView.openCamera();
        CameraWatchDogTask cameraWatchDogTask = this.mColorWatchDog;
        if (cameraWatchDogTask != null) {
            cameraWatchDogTask.resume();
        }
    }

    private void showInfo() {
        int state = getState();
        if (ZKLauncher.zkDataEncdec == 1 && state == 0) {
            this.mIsStart = false;
            ZKConfirmDialog zKConfirmDialog = new ZKConfirmDialog(this);
            zKConfirmDialog.show();
            zKConfirmDialog.setType(2, getString(R.string.zk_core_cancel), "", getString(R.string.zk_core_ok));
            zKConfirmDialog.setMessage(getString(R.string.delete_photo_tips));
            try {
                Field declaredField = zKConfirmDialog.getClass().getDeclaredField("mTvContent");
                declaredField.setAccessible(true);
                TextView textView = (TextView) declaredField.get(zKConfirmDialog);
                if (textView != null) {
                    textView.setMaxLines(6);
                    textView.setTextAlignment(5);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            zKConfirmDialog.setListener(new ZKConfirmDialog.ResultListener() {
                public void cover() {
                }

                public void failure() {
                    ZKStaffFaceActivity.this.finish();
                }

                public void success() {
                    boolean unused = ZKStaffFaceActivity.this.mIsStart = true;
                }
            });
            return;
        }
        this.mIsStart = true;
    }

    private int getState() {
        return ((LauncherApplication) getApplication()).getPushState();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        Thread thread = this.mEnrollFaceThread;
        if (thread != null) {
            thread.interrupt();
            this.mEnrollFaceThread = null;
        }
        sendPush();
        turnOnFillLight(false);
        this.mHandler.removeCallbacksAndMessages((Object) null);
        CameraWatchDog cameraWatchDog = this.mWatchDog;
        if (cameraWatchDog != null) {
            cameraWatchDog.stop();
        }
        CameraWatchDogTask cameraWatchDogTask = this.mColorWatchDog;
        if (cameraWatchDogTask != null) {
            cameraWatchDogTask.release();
        }
        CameraWatchDogTask cameraWatchDogTask2 = this.mIrWatchDog;
        if (cameraWatchDogTask2 != null) {
            cameraWatchDogTask2.release();
        }
    }

    private void initView() {
        initToolbar();
        this.tvPrompt = (TextView) findViewById(R.id.tv_prompt_p);
        this.mFaceEnrollProgressBar = (ProgressBar) findViewById(R.id.pb_face_enroll_progress);
        if (hasTwoAlgorithmInit()) {
            this.mFaceEnrollProgressBar.setMax(200);
        } else {
            this.mFaceEnrollProgressBar.setMax(100);
        }
        this.mFaceEnrollProgressBar.setVisibility(0);
        this.mFaceEnrollProgressBar.setProgress(0);
        this.lightBtn = (ImageView) findViewById(R.id.light_view);
        if (DeviceManager.getDefault().isG6() || DeviceManager.getDefault().isH1() || DeviceManager.getDefault().isG5()) {
            this.lightBtn.setVisibility(8);
        }
        Paint paint = new Paint(1);
        this.mFacePaint = paint;
        paint.setStrokeWidth(2.0f);
        this.mFacePaint.setStyle(Paint.Style.STROKE);
        this.mFacePaint.setColor(getResources().getColor(R.color.clr_7AC143));
        this.faceScanLine = (ImageView) findViewById(R.id.face_scan_line_iv);
        this.relRop = (RelativeLayout) findViewById(R.id.rel_rop);
        initCameraView();
    }

    public /* synthetic */ void lambda$new$9$ZKStaffFaceActivity(byte[] bArr, Camera camera, int i, int i2) {
        CameraWatchDogTask cameraWatchDogTask = this.mIrWatchDog;
        if (cameraWatchDogTask != null) {
            cameraWatchDogTask.feedDog();
        }
        if (this.mIsStart) {
            if (this.irWidth <= 0) {
                this.irWidth = i;
            }
            if (this.irHeight <= 0) {
                this.irHeight = i2;
            }
            this.mIrNV21ForEnroll.offer(bArr);
        }
    }

    public /* synthetic */ void lambda$new$10$ZKStaffFaceActivity(byte[] bArr, Camera camera, int i, int i2) {
        CameraWatchDogTask cameraWatchDogTask = this.mColorWatchDog;
        if (cameraWatchDogTask != null) {
            cameraWatchDogTask.feedDog();
        }
        if (this.mIsStart) {
            byte[] bArr2 = this.mCacheNV21;
            if (bArr2 == null) {
                this.mCacheNV21 = Arrays.copyOf(bArr, bArr.length);
            } else {
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            }
            if (this.colorWidth <= 0) {
                this.colorWidth = i;
            }
            if (this.colorHeight <= 0) {
                this.colorHeight = i2;
            }
            this.mNV21ForEnroll.offer(this.mCacheNV21);
            if (this.isAutoControllerLight && YUVUtils.getYUV420SPLuminance(bArr, i, i2) < 100) {
                turnOnFillLight(true);
            }
        }
    }

    private void beep(String str) {
        AudioManager audioManager = (AudioManager) getSystemService("audio");
        if (audioManager != null) {
            this.Volume = ((float) audioManager.getStreamVolume(3)) / ((float) audioManager.getStreamMaxVolume(3));
            try {
                this.afd = getAssets().openFd(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SoundPool soundPool = new SoundPool(1, 1, 0);
            soundPool.load(this.afd, 0);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                    ZKStaffFaceActivity.this.lambda$beep$11$ZKStaffFaceActivity(soundPool, i, i2);
                }
            });
        }
    }

    public /* synthetic */ void lambda$beep$11$ZKStaffFaceActivity(SoundPool soundPool, int i, int i2) {
        float f = this.Volume;
        soundPool.play(i, f, f, 1, 0, 1.0f);
    }

    public void lightSwitch(View view) {
        this.isAutoControllerLight = false;
        turnOnFillLight(!this.isFillLightOn);
    }

    private void turnOnFillLight(boolean z) {
        if (z != this.isFillLightOn) {
            this.isFillLightOn = z;
            LightManager lightManager2 = this.lightManager;
            if (lightManager2 != null) {
                try {
                    lightManager2.setLightState(0, z ? 1 : 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.mHandler.post(new Runnable() {
                public final void run() {
                    ZKStaffFaceActivity.this.lambda$turnOnFillLight$12$ZKStaffFaceActivity();
                }
            });
        }
    }

    public /* synthetic */ void lambda$turnOnFillLight$12$ZKStaffFaceActivity() {
        this.lightBtn.setImageResource(this.isFillLightOn ? R.drawable.light_on : R.drawable.light_off);
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

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0061 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0062  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void enrollVLBy56v2(byte[] r7, int r8, int r9, java.util.List<com.zkteco.liveface562.bean.IdentifyInfo> r10) {
        /*
            r6 = this;
            com.zkteco.android.db.orm.manager.DataManager r0 = com.zktechnology.android.utils.DBManager.getInstance()
            if (r0 != 0) goto L_0x000e
            java.lang.String r7 = TAG
            java.lang.String r8 = "[enrollVLOnSubThread]: data manager is null"
            android.util.Log.e(r7, r8)
            return
        L_0x000e:
            r1 = 0
            r2 = 0
            r3 = 1
            if (r10 == 0) goto L_0x002b
            boolean r4 = r10.isEmpty()
            if (r4 != 0) goto L_0x002b
            java.lang.Object r10 = r10.get(r2)
            r1 = r10
            com.zkteco.liveface562.bean.IdentifyInfo r1 = (com.zkteco.liveface562.bean.IdentifyInfo) r1
            float r10 = r1.identifyScore
            int r4 = r6.mFaceEnrollThr
            float r4 = (float) r4
            int r10 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r10 <= 0) goto L_0x002b
            r10 = r3
            goto L_0x002c
        L_0x002b:
            r10 = r2
        L_0x002c:
            boolean r4 = r6.isNewFace
            r5 = 2131755376(0x7f100170, float:1.914163E38)
            if (r4 == 0) goto L_0x003e
            if (r10 == 0) goto L_0x003e
            r6.showTips(r5)
            int r7 = r6.retryCount
            int r7 = r7 - r3
            r6.retryCount = r7
            return
        L_0x003e:
            boolean r4 = r6.isReEnroll
            if (r4 == 0) goto L_0x005b
            if (r10 == 0) goto L_0x005b
            com.zkteco.android.db.orm.tna.UserInfo r10 = r6.userInfo
            java.lang.String r10 = r10.getUser_PIN()
            java.lang.String r1 = r1.pin
            boolean r10 = r10.equals(r1)
            if (r10 != 0) goto L_0x005b
            r6.showTips(r5)
            int r7 = r6.retryCount
            int r7 = r7 - r3
            r6.retryCount = r7
            return
        L_0x005b:
            android.graphics.Bitmap r10 = com.zkteco.liveface562.util.ZkBitmapUtils.nv21ToBitmap(r7, r8, r9)
            if (r10 != 0) goto L_0x0062
            return
        L_0x0062:
            com.zkteco.liveface562.bean.ZkExtractResult[] r1 = new com.zkteco.liveface562.bean.ZkExtractResult[r3]
            com.zkteco.liveface562.ZkFaceManager r4 = com.zkteco.liveface562.ZkFaceManager.getInstance()
            int r4 = r4.extractFeature(r10, r2, r1)
            if (r4 == 0) goto L_0x006f
            return
        L_0x006f:
            r1 = r1[r2]
            if (r1 == 0) goto L_0x00fe
            byte[] r2 = r1.feature
            if (r2 != 0) goto L_0x0079
            goto L_0x00fe
        L_0x0079:
            com.zkteco.android.employeemgmt.face.EnrollVLFaceInfo r2 = new com.zkteco.android.employeemgmt.face.EnrollVLFaceInfo
            r2.<init>()
            r2.setWidth(r8)
            r2.setHeight(r9)
            r2.setNV21RawData(r7)
            byte[] r7 = r1.feature
            r2.setTemplate(r7)
            java.util.ArrayList<com.zkteco.android.employeemgmt.face.EnrollVLFaceInfo> r7 = r6.mReadyForEnrollVl
            r7.add(r2)
            java.lang.String r7 = TAG
            java.lang.String r8 = "[enrollVLOnSubThread]:extract success"
            android.util.Log.i(r7, r8)
            r6.updateProgress()
            java.util.ArrayList<com.zkteco.android.employeemgmt.face.EnrollVLFaceInfo> r8 = r6.mReadyForEnrollVl
            int r8 = r8.size()
            if (r8 < r3) goto L_0x00fd
            java.lang.String r8 = "[enrollVLOnSubThread]: second filter face templates "
            android.util.Log.i(r7, r8)
            boolean r7 = r6.isModify
            if (r7 == 0) goto L_0x00b8
            com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
            java.lang.String r7 = r7.getUser_PIN()
            java.util.ArrayList<com.zkteco.android.employeemgmt.face.EnrollVLFaceInfo> r8 = r6.mReadyForEnrollVl
            com.zkteco.android.employeemgmt.util.SQLiteFaceUtils.updateTemplateVL(r0, r7, r8)
            goto L_0x00c3
        L_0x00b8:
            com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
            java.lang.String r7 = r7.getUser_PIN()
            java.util.ArrayList<com.zkteco.android.employeemgmt.face.EnrollVLFaceInfo> r8 = r6.mReadyForEnrollVl
            com.zkteco.android.employeemgmt.util.SQLiteFaceUtils.saveTemplateVL(r0, r7, r8)
        L_0x00c3:
            com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
            java.lang.String r7 = r7.getUser_PIN()
            r6.sendUpdateBroadcast(r7)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = com.zkteco.android.zkcore.utils.ZKFilePath.FACE_PATH
            java.lang.StringBuilder r7 = r7.append(r8)
            com.zkteco.android.db.orm.tna.UserInfo r8 = r6.userInfo
            java.lang.String r8 = r8.getUser_PIN()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = com.zkteco.android.zkcore.utils.ZKFilePath.SUFFIX_IMAGE
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            compressPhoto(r10, r7)
            r6.sendPush()     // Catch:{ Exception -> 0x00f2 }
            goto L_0x00f6
        L_0x00f2:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00f6:
            java.lang.String r7 = "0"
            r6.remoteEnrollResult = r7
            r6.finish()
        L_0x00fd:
            return
        L_0x00fe:
            r7 = 2131755407(0x7f10018f, float:1.9141692E38)
            r6.showTips(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.enrollVLBy56v2(byte[], int, int, java.util.List):void");
    }

    /* access modifiers changed from: private */
    public boolean isPassFaceLivenessLimitFor5_6_2(List<IdentifyInfo> list) {
        float f = list.get(0).livenessScore;
        if (f >= ((float) ZKLauncher.mFaceLiveThreshold)) {
            return true;
        }
        Log.e(TAG, "isPassFaceLivenessLimit: faceLiveness = " + f);
        return false;
    }

    public static void compressPhoto(Bitmap bitmap, String str) {
        int i;
        File file = new File(ZKFilePath.FACE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(str);
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bitmap.getWidth() <= bitmap.getHeight() || ((float) bitmap.getWidth()) <= 1920.0f) {
            i = (bitmap.getWidth() >= bitmap.getHeight() || ((float) bitmap.getHeight()) <= 1080.0f) ? 1 : (int) (((float) bitmap.getHeight()) / 1080.0f);
        } else {
            i = (int) (((float) bitmap.getWidth()) / 1920.0f);
        }
        if (i <= 0) {
            i = 1;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth() / i, bitmap.getHeight() / i, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawBitmap(bitmap, (Rect) null, new Rect(0, 0, bitmap.getWidth() / i, bitmap.getHeight() / i), (Paint) null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 100;
        createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        Log.d("OOM", "总内存:" + Runtime.getRuntime().maxMemory() + "\t已用内存:" + Runtime.getRuntime().totalMemory() + "\t剩余内存:" + (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()));
        while (byteArrayOutputStream.toByteArray().length / 1024 > 255) {
            byteArrayOutputStream.reset();
            i2 -= 5;
            createBitmap.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            if (ZKLauncher.zkDataEncdec == 1) {
                fileOutputStream.write(AES256Util.encrypt(byteArrayOutputStream.toByteArray(), ZKLauncher.PUBLIC_KEY, ZKLauncher.iv));
            } else {
                fileOutputStream.write(byteArrayOutputStream.toByteArray());
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        createBitmap.recycle();
    }

    private void sendUpdateBroadcast(String str) {
        Intent intent = new Intent();
        intent.setAction("com.zkteco.android.employeemgmt.action.UPDATE_FACE_TEMPLATE");
        intent.putExtra("userPin", str);
        intent.putExtra("operate", "update");
        sendBroadcast(intent);
    }

    private void initAnim() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 70.0f, 700.0f);
        translateAnimation.setRepeatMode(2);
        translateAnimation.setRepeatCount(-1);
        translateAnimation.setDuration(1000);
        this.faceScanLine.startAnimation(translateAnimation);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0175, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0178, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x017b, code lost:
        if (r6 != 0) goto L_0x017d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x017d, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0180, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0054, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0068, code lost:
        if (r6 != 0) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0075, code lost:
        if (android.text.TextUtils.isEmpty(r12.userInfo.getUser_PIN()) != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r6 = r2.convertPushInit();
        r2.sendHubAction(20, r6, r12.remoteEnrollResult);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0084, code lost:
        if (r6 == 0) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0087, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008a, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r8.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0090, code lost:
        if (r6 == 0) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0092, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r6 = r2.convertPushInit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r2.sendHubAction(25, r6, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a1, code lost:
        if (r6 == 0) goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a3, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00aa, code lost:
        r8 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ac, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ad, code lost:
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b0, code lost:
        r8 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b1, code lost:
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        r8.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00b7, code lost:
        if (r6 != 0) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        r6 = r2.convertPushInit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        r8 = (com.zkteco.android.db.orm.tna.PersBiotemplate) new com.zkteco.android.db.orm.tna.PersBiotemplate().getQueryBuilder().where().eq("user_pin", r12.userInfo.getUser_PIN()).and().eq("bio_type", 9).queryForFirst();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ed, code lost:
        if (r8 != null) goto L_0x00f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00f1, code lost:
        if (r6 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00f3, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        r2.setPushTableName(r6, "Pers_BioTemplate");
        r2.setPushCon(r6, "ID=" + ((int) r8.getID()));
        r2.setPushIntField(r6, com.zkteco.android.db.orm.contants.BiometricCommuCMD.FIELD_DESC_TMP_ID, (int) r8.getID());
        r2.sendHubAction(0, r6, "");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0126, code lost:
        if (r6 == 0) goto L_0x013d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0128, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x012c, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x012e, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0130, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0131, code lost:
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0133, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0134, code lost:
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:?, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x013a, code lost:
        if (r6 != 0) goto L_0x0128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0143, code lost:
        if (android.text.TextUtils.isEmpty(r12.userPin) != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:?, code lost:
        r6 = r2.convertPushInit();
        r2.sendHubAction(20, r6, r12.remoteEnrollResult);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0150, code lost:
        if (r6 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0153, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0155, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x015b, code lost:
        if (r6 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x015d, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0163, code lost:
        if (r6 != 0) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0165, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0168, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x016b, code lost:
        if (r6 != 0) goto L_0x016d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x016d, code lost:
        r2.convertPushFree(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0170, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0173, code lost:
        if (r6 != 0) goto L_0x0175;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0052, code lost:
        if (r6 != 0) goto L_0x0054;
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0175  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0185  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x016d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendPush() {
        /*
            r12 = this;
            java.lang.String r0 = ""
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()
            java.lang.String r2 = "BioPhotoFun"
            r3 = 0
            int r1 = r1.getIntOption(r2, r3)
            com.zkteco.android.core.sdk.HubProtocolManager r2 = new com.zkteco.android.core.sdk.HubProtocolManager
            r2.<init>(r12)
            r4 = 1
            if (r1 != r4) goto L_0x0189
            r4 = 0
            long r6 = r2.convertPushInit()     // Catch:{ Exception -> 0x0061, all -> 0x005d }
            java.lang.String r1 = "BIO_PHOTO_INDEX"
            r2.setPushTableName(r6, r1)     // Catch:{ Exception -> 0x005b }
            java.lang.String r1 = "User_PIN"
            com.zkteco.android.db.orm.tna.UserInfo r8 = r12.userInfo     // Catch:{ Exception -> 0x005b }
            java.lang.String r8 = r8.getUser_PIN()     // Catch:{ Exception -> 0x005b }
            r2.setPushStrField(r6, r1, r8)     // Catch:{ Exception -> 0x005b }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005b }
            r1.<init>()     // Catch:{ Exception -> 0x005b }
            java.lang.String r8 = "User_PIN="
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ Exception -> 0x005b }
            com.zkteco.android.db.orm.tna.UserInfo r8 = r12.userInfo     // Catch:{ Exception -> 0x005b }
            java.lang.String r8 = r8.getUser_PIN()     // Catch:{ Exception -> 0x005b }
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ Exception -> 0x005b }
            java.lang.String r8 = "\tType=9"
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ Exception -> 0x005b }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x005b }
            r2.setPushCon(r6, r1)     // Catch:{ Exception -> 0x005b }
            r2.sendHubAction(r3, r6, r0)     // Catch:{ Exception -> 0x005b }
            int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x006b
        L_0x0054:
            r2.convertPushFree(r6)
            goto L_0x006b
        L_0x0058:
            r0 = move-exception
            goto L_0x0181
        L_0x005b:
            r1 = move-exception
            goto L_0x0063
        L_0x005d:
            r0 = move-exception
            r6 = r4
            goto L_0x0181
        L_0x0061:
            r1 = move-exception
            r6 = r4
        L_0x0063:
            r1.printStackTrace()     // Catch:{ all -> 0x0058 }
            int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x006b
            goto L_0x0054
        L_0x006b:
            com.zkteco.android.db.orm.tna.UserInfo r1 = r12.userInfo
            java.lang.String r1 = r1.getUser_PIN()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0189
            r1 = 20
            long r6 = r2.convertPushInit()     // Catch:{ Exception -> 0x008a }
            java.lang.String r8 = r12.remoteEnrollResult     // Catch:{ Exception -> 0x008a }
            r2.sendHubAction(r1, r6, r8)     // Catch:{ Exception -> 0x008a }
            int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r8 == 0) goto L_0x0095
            goto L_0x0092
        L_0x0087:
            r0 = move-exception
            goto L_0x0179
        L_0x008a:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x0087 }
            int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r8 == 0) goto L_0x0095
        L_0x0092:
            r2.convertPushFree(r6)
        L_0x0095:
            long r6 = r2.convertPushInit()     // Catch:{ Exception -> 0x00b0, all -> 0x00ac }
            r8 = 25
            r9 = 0
            r2.sendHubAction(r8, r6, r9)     // Catch:{ Exception -> 0x00aa }
            int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r8 == 0) goto L_0x00ba
        L_0x00a3:
            r2.convertPushFree(r6)
            goto L_0x00ba
        L_0x00a7:
            r0 = move-exception
            goto L_0x0171
        L_0x00aa:
            r8 = move-exception
            goto L_0x00b2
        L_0x00ac:
            r0 = move-exception
            r6 = r4
            goto L_0x0171
        L_0x00b0:
            r8 = move-exception
            r6 = r4
        L_0x00b2:
            r8.printStackTrace()     // Catch:{ all -> 0x00a7 }
            int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r8 == 0) goto L_0x00ba
            goto L_0x00a3
        L_0x00ba:
            long r6 = r2.convertPushInit()     // Catch:{ Exception -> 0x0133, all -> 0x0130 }
            com.zkteco.android.db.orm.tna.PersBiotemplate r8 = new com.zkteco.android.db.orm.tna.PersBiotemplate     // Catch:{ Exception -> 0x012e }
            r8.<init>()     // Catch:{ Exception -> 0x012e }
            com.j256.ormlite.stmt.QueryBuilder r8 = r8.getQueryBuilder()     // Catch:{ Exception -> 0x012e }
            com.j256.ormlite.stmt.Where r8 = r8.where()     // Catch:{ Exception -> 0x012e }
            java.lang.String r9 = "user_pin"
            com.zkteco.android.db.orm.tna.UserInfo r10 = r12.userInfo     // Catch:{ Exception -> 0x012e }
            java.lang.String r10 = r10.getUser_PIN()     // Catch:{ Exception -> 0x012e }
            com.j256.ormlite.stmt.Where r8 = r8.eq(r9, r10)     // Catch:{ Exception -> 0x012e }
            com.j256.ormlite.stmt.Where r8 = r8.and()     // Catch:{ Exception -> 0x012e }
            java.lang.String r9 = "bio_type"
            r10 = 9
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x012e }
            com.j256.ormlite.stmt.Where r8 = r8.eq(r9, r10)     // Catch:{ Exception -> 0x012e }
            java.lang.Object r8 = r8.queryForFirst()     // Catch:{ Exception -> 0x012e }
            com.zkteco.android.db.orm.tna.PersBiotemplate r8 = (com.zkteco.android.db.orm.tna.PersBiotemplate) r8     // Catch:{ Exception -> 0x012e }
            if (r8 != 0) goto L_0x00f7
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x00f6
            r2.convertPushFree(r6)
        L_0x00f6:
            return
        L_0x00f7:
            java.lang.String r9 = "Pers_BioTemplate"
            r2.setPushTableName(r6, r9)     // Catch:{ Exception -> 0x012e }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x012e }
            r9.<init>()     // Catch:{ Exception -> 0x012e }
            java.lang.String r10 = "ID="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x012e }
            long r10 = r8.getID()     // Catch:{ Exception -> 0x012e }
            int r10 = (int) r10     // Catch:{ Exception -> 0x012e }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x012e }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x012e }
            r2.setPushCon(r6, r9)     // Catch:{ Exception -> 0x012e }
            java.lang.String r9 = "ID"
            long r10 = r8.getID()     // Catch:{ Exception -> 0x012e }
            int r8 = (int) r10     // Catch:{ Exception -> 0x012e }
            r2.setPushIntField(r6, r9, r8)     // Catch:{ Exception -> 0x012e }
            r2.sendHubAction(r3, r6, r0)     // Catch:{ Exception -> 0x012e }
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x013d
        L_0x0128:
            r2.convertPushFree(r6)
            goto L_0x013d
        L_0x012c:
            r0 = move-exception
            goto L_0x0169
        L_0x012e:
            r0 = move-exception
            goto L_0x0135
        L_0x0130:
            r0 = move-exception
            r6 = r4
            goto L_0x0169
        L_0x0133:
            r0 = move-exception
            r6 = r4
        L_0x0135:
            r0.printStackTrace()     // Catch:{ all -> 0x012c }
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x013d
            goto L_0x0128
        L_0x013d:
            java.lang.String r0 = r12.userPin
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0189
            long r6 = r2.convertPushInit()     // Catch:{ Exception -> 0x0155 }
            java.lang.String r0 = r12.remoteEnrollResult     // Catch:{ Exception -> 0x0155 }
            r2.sendHubAction(r1, r6, r0)     // Catch:{ Exception -> 0x0155 }
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x0189
            goto L_0x015d
        L_0x0153:
            r0 = move-exception
            goto L_0x0161
        L_0x0155:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0153 }
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x0189
        L_0x015d:
            r2.convertPushFree(r6)
            goto L_0x0189
        L_0x0161:
            int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0168
            r2.convertPushFree(r6)
        L_0x0168:
            throw r0
        L_0x0169:
            int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0170
            r2.convertPushFree(r6)
        L_0x0170:
            throw r0
        L_0x0171:
            int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0178
            r2.convertPushFree(r6)
        L_0x0178:
            throw r0
        L_0x0179:
            int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0180
            r2.convertPushFree(r6)
        L_0x0180:
            throw r0
        L_0x0181:
            int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0188
            r2.convertPushFree(r6)
        L_0x0188:
            throw r0
        L_0x0189:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity.sendPush():void");
    }

    private void updateProgress() {
        for (int i = 1; i <= 100; i++) {
            this.mHandler.post(new Runnable() {
                public final void run() {
                    ZKStaffFaceActivity.this.lambda$updateProgress$13$ZKStaffFaceActivity();
                }
            });
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public /* synthetic */ void lambda$updateProgress$13$ZKStaffFaceActivity() {
        this.mFaceEnrollProgressBar.incrementProgressBy(1);
    }

    /* access modifiers changed from: private */
    public void flashTheRect() {
        this.mHandler.post(new Runnable() {
            public final void run() {
                ZKStaffFaceActivity.this.lambda$flashTheRect$14$ZKStaffFaceActivity();
            }
        });
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mHandler.post(new Runnable() {
            public final void run() {
                ZKStaffFaceActivity.this.lambda$flashTheRect$15$ZKStaffFaceActivity();
            }
        });
    }

    public /* synthetic */ void lambda$flashTheRect$14$ZKStaffFaceActivity() {
        this.mFacePaint.setStrokeWidth(5.0f);
    }

    public /* synthetic */ void lambda$flashTheRect$15$ZKStaffFaceActivity() {
        this.mFacePaint.setStrokeWidth(2.0f);
    }

    /* access modifiers changed from: private */
    public boolean isFaceInTheEnrollArea(Rect rect) {
        if (this.mEnrollRect == null) {
            return false;
        }
        if (rect.left < this.mEnrollRect.left) {
            showTips(R.string.zk_staff_put_face_on_right_place);
            return false;
        } else if (rect.right > this.mEnrollRect.right) {
            showTips(R.string.zk_staff_put_face_on_right_place);
            return false;
        } else {
            if (((double) (((float) (rect.right - rect.left)) / ((float) (this.mEnrollRect.right - this.mEnrollRect.left)))) < 0.5d) {
                showTips(R.string.zk_staff_face_move_close);
                return false;
            }
            showTips(R.string.zk_staff_face_move_keep);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void showTips(int i) {
        this.mHandler.post(new Runnable(i) {
            public final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                ZKStaffFaceActivity.this.lambda$showTips$16$ZKStaffFaceActivity(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$showTips$16$ZKStaffFaceActivity(int i) {
        this.tvPrompt.setText(i);
    }

    /* access modifiers changed from: private */
    public void sortByFaceRect(Face[] faceArr) {
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

    /* access modifiers changed from: private */
    public boolean is3DLiveCheckPass(int i, int i2, byte[] bArr) {
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
}
