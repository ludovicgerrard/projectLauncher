package com.zktechnology.android.helper;

import android.content.Context;
import android.util.Log;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.GlobalConfig;
import com.zktechnology.android.utils.TemplateUtil;
import com.zkteco.adk.core.task.ZkThreadPoolManager;
import com.zkteco.android.zkcore.utils.ZKSharedUtil;
import com.zkteco.liveface562.ZkFaceManager;
import com.zkteco.liveface562.bean.ZkFaceConfig;
import com.zkteco.liveface562.common.ZkFaceStateCallback;
import java.util.concurrent.atomic.AtomicBoolean;

public class FaceServiceHelper {
    private static final String TAG = "FaceServiceHelper";
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public final AtomicBoolean mDisconnect;
    private final ZkFaceStateCallback mFaceStateCallback;

    static class SingletonHolder {
        static final FaceServiceHelper INSTANCE = new FaceServiceHelper();

        SingletonHolder() {
        }
    }

    private FaceServiceHelper() {
        this.mDisconnect = new AtomicBoolean();
        this.mFaceStateCallback = new ZkFaceStateCallback() {
            public void onServiceConnected() {
            }

            public void onServiceDisconnected() {
                if (!FaceServiceHelper.this.mDisconnect.get()) {
                    ZkThreadPoolManager.getInstance().execute(new Runnable() {
                        public final void run() {
                            FaceServiceHelper.this.init();
                        }
                    });
                }
            }

            public void onInitState(int i) {
                boolean z = true;
                if (i == 1) {
                    Log.i(FaceServiceHelper.TAG, "人脸算法激活成功");
                    ZkFaceConfig[] zkFaceConfigArr = new ZkFaceConfig[1];
                    if (ZkFaceManager.getInstance().getConfig(zkFaceConfigArr) == 0) {
                        ZkFaceConfig zkFaceConfig = zkFaceConfigArr[0];
                        zkFaceConfig.setLivenessEnabled(true);
                        zkFaceConfig.setRcAttributeAndOcclusionMode(3);
                        ZkFaceManager.getInstance().setConfig(zkFaceConfig);
                    }
                    TemplateUtil.compareFaceCounts(FaceServiceHelper.this.mContext);
                    ZKSharedUtil zKSharedUtil = new ZKSharedUtil(LauncherApplication.getLauncherApplicationContext());
                    String[] strArr = new String[1];
                    if (ZkFaceManager.getInstance().getFaceEngineSdkVersion(strArr) == 0) {
                        String str = strArr[0];
                        zKSharedUtil.putString("realfaceVersion", str);
                        Log.d(FaceServiceHelper.TAG, "onInitState: version=" + str);
                        DBManager.getInstance().setStrOption("FaceEngineIdentification", str);
                    } else {
                        Log.e(FaceServiceHelper.TAG, "onServiceConnected: get face version failed");
                    }
                    int isAuthorized = ZkFaceManager.getInstance().isAuthorized();
                    zKSharedUtil.putBoolean(GlobalConfig.IS_FACE_AUTHORIZED, isAuthorized == 0);
                    Log.d(FaceServiceHelper.TAG, "onInitState: isAuthorized=" + isAuthorized);
                    int isHaveChip = ZkFaceManager.getInstance().isHaveChip();
                    if (isHaveChip != 0) {
                        z = false;
                    }
                    zKSharedUtil.putBoolean("isHaveChip", z);
                    Log.d(FaceServiceHelper.TAG, "onInitState: isHaveChip=" + isHaveChip);
                    return;
                }
                Log.e(FaceServiceHelper.TAG, "人脸算法激活失败");
            }
        };
        this.mContext = LauncherApplication.getLauncherApplicationContext().getApplicationContext();
    }

    public static FaceServiceHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public final void init() {
        this.mDisconnect.compareAndSet(true, false);
        Log.d(TAG, "initFace: [start init face]");
        ZkFaceManager.getInstance().setZkFaceStateCallback(this.mFaceStateCallback);
        Log.d(TAG, String.format("initFace: [bindService result %d]", new Object[]{Integer.valueOf(ZkFaceManager.getInstance().bindService(this.mContext))}));
    }

    public final void disconnect() {
        this.mDisconnect.compareAndSet(false, true);
        ZkFaceManager.getInstance().setZkFaceStateCallback((ZkFaceStateCallback) null);
        ZkFaceManager.getInstance().unbindService(this.mContext);
    }
}
