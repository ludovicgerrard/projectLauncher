package com.zktechnology.android.view.controller;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.ShellUtils;
import com.zktechnology.android.utils.ZKThreadPool;

public class LocationStateController {
    private static final int MSG_START_LOCATE_INTERVAL = 10000;
    private static final String PROVIDER_STRING_DEFAULT = "locating";
    private static final String PROVIDER_STRING_FAILURE = "failure";
    private boolean isInit;
    private boolean isLocateFunOn;
    private boolean isSupportLocate;
    /* access modifiers changed from: private */
    public volatile int locationInterval;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            LocationStateController.this.startLocate();
            sendEmptyMessageDelayed(LocationStateController.MSG_START_LOCATE_INTERVAL, ((long) LocationStateController.this.locationInterval) * 1000);
        }
    };
    /* access modifiers changed from: private */
    public final OnLocationStateChangedListener mListener;
    private TencentLocationListener mLocationListener;
    private TencentLocationManager mLocationManager;
    private TencentLocationRequest mLocationRequest;

    public interface OnLocationStateChangedListener {
        void onLocationChanged(boolean z, String str, double d, double d2, float f);
    }

    /* access modifiers changed from: private */
    public void startLocate() {
        init();
        this.mListener.onLocationChanged(true, PROVIDER_STRING_DEFAULT, -1.0d, -1.0d, -1.0f);
        this.mLocationManager.requestSingleFreshLocation(this.mLocationRequest, this.mLocationListener, this.mHandler.getLooper());
    }

    public LocationStateController(OnLocationStateChangedListener onLocationStateChangedListener) {
        this.mListener = onLocationStateChangedListener;
    }

    private void enableLocation() {
        ShellUtils.execCommand("settings put secure location_providers_allowed +gps,network", false);
    }

    public void start() {
        ZKThreadPool.getInstance().executeTask(new Runnable() {
            public final void run() {
                LocationStateController.this.lambda$start$1$LocationStateController();
            }
        });
    }

    public /* synthetic */ void lambda$start$1$LocationStateController() {
        boolean z = false;
        this.isSupportLocate = DBManager.getInstance().getIntOption("IsSupportLocate", 0) == 1;
        if (DBManager.getInstance().getIntOption("IsLocateFunOn", 0) == 1) {
            z = true;
        }
        this.isLocateFunOn = z;
        if (!this.isSupportLocate || !z) {
            destroy();
            this.mHandler.post(new Runnable() {
                public final void run() {
                    LocationStateController.this.lambda$start$0$LocationStateController();
                }
            });
            return;
        }
        enableLocation();
        int intOption = DBManager.getInstance().getIntOption("LocationInterval", 90);
        if (this.locationInterval != intOption) {
            this.locationInterval = intOption;
            launchLocate();
        }
    }

    public /* synthetic */ void lambda$start$0$LocationStateController() {
        this.mListener.onLocationChanged(false, (String) null, -1.0d, -1.0d, -1.0f);
    }

    /* access modifiers changed from: private */
    public void launchLocate() {
        this.mHandler.removeCallbacksAndMessages((Object) null);
        this.mHandler.sendEmptyMessage(MSG_START_LOCATE_INTERVAL);
    }

    public void release() {
        destroy();
    }

    private void destroy() {
        this.locationInterval = 0;
        this.mHandler.removeCallbacksAndMessages((Object) null);
        if (this.isInit) {
            this.mLocationManager.removeUpdates(this.mLocationListener);
            this.mLocationManager = null;
            this.mLocationRequest = null;
            this.mLocationListener = null;
            this.isInit = false;
        }
    }

    private void init() {
        if (!this.isInit) {
            TencentLocationManager.setUserAgreePrivacy(true);
            TencentLocationManager instance = TencentLocationManager.getInstance(LauncherApplication.getLauncherApplicationContext());
            this.mLocationManager = instance;
            instance.setDebuggable(true);
            this.mLocationRequest = TencentLocationRequest.create().setAllowGPS(true).setIndoorLocationMode(true).setLocMode(10);
            this.mLocationListener = new TencentLocationListener() {
                public void onStatusUpdate(String str, int i, String str2) {
                }

                public void onLocationChanged(TencentLocation tencentLocation, int i, String str) {
                    if (i == 0) {
                        LocationStateController.this.mListener.onLocationChanged(true, tencentLocation.getProvider(), tencentLocation.getLatitude(), tencentLocation.getLongitude(), tencentLocation.getAccuracy());
                        return;
                    }
                    LocationStateController.this.mListener.onLocationChanged(true, LocationStateController.PROVIDER_STRING_FAILURE, -1.0d, -1.0d, -1.0f);
                    LocationStateController.this.launchLocate();
                }
            };
            this.isInit = true;
        }
    }
}
