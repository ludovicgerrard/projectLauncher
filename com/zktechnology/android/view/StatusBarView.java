package com.zktechnology.android.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.event.EventState;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.view.StatusBarView;
import com.zktechnology.android.view.controller.AlarmStateController;
import com.zktechnology.android.view.controller.LocationStateController;
import com.zktechnology.android.view.controller.NetworkStateController;
import com.zktechnology.android.view.controller.SensorStateController;
import com.zktechnology.android.view.controller.ServerConnectStateController;
import com.zktechnology.android.view.controller.UDiskStateController;
import java.util.Locale;

public class StatusBarView extends LinearLayout {
    private BatteryView batteryView;
    private ImageView deviceInfo;
    private ImageView ivAlarm;
    private ImageView ivAuInput;
    /* access modifiers changed from: private */
    public ImageView ivEthernet;
    protected ImageView ivIO;
    private ImageView ivLocation;
    protected ImageView ivOpenAlways;
    private ImageView ivSenseState;
    private ImageView ivUDisk;
    /* access modifiers changed from: private */
    public ImageView ivWifi;
    private AlarmStateController mAlarmStateController;
    private ImageView mConnectState;
    private Context mContext;
    private LinearLayout mLlStatusBar;
    private LocationStateController.OnLocationStateChangedListener mLocationStateChangedListener;
    private LocationStateController mLocationStateController;
    private NetworkStateController mNetworkStateController;
    private SensorStateController mSensorStateController;
    private ServerConnectStateController.OnServerConnectStateChangedListener mServerConnectStateChangedListener;
    private ServerConnectStateController mServerConnectStateController;
    private UDiskStateController mUDiskStateController;
    /* access modifiers changed from: private */
    public MobileDataSignalView mobileView;

    public StatusBarView(Context context) {
        super(context);
        init(context);
    }

    public StatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public StatusBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        initView();
        initData();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        release();
        super.onDetachedFromWindow();
    }

    private void release() {
        this.mAlarmStateController.unregister(this.mContext);
        this.mAlarmStateController = null;
        this.mUDiskStateController.unregister(this.mContext);
        this.mUDiskStateController = null;
        this.mSensorStateController.unregister(this.mContext);
        this.mSensorStateController = null;
        this.mServerConnectStateController.unregister(this.mContext);
        this.mServerConnectStateController = null;
        this.mNetworkStateController.release();
        this.mNetworkStateController = null;
        this.mLocationStateController.release();
        this.mLocationStateController = null;
        setClickListener((View.OnClickListener) null);
        setOnServerConnectStateChangedListener((ServerConnectStateController.OnServerConnectStateChangedListener) null);
        setOnLocationStateChangedListener((LocationStateController.OnLocationStateChangedListener) null);
    }

    private void initData() {
        AlarmStateController alarmStateController = new AlarmStateController(new AlarmStateController.OnAlarmStateChangedListener() {
            public final void onAlarmStateChanged(boolean z) {
                StatusBarView.this.lambda$initData$0$StatusBarView(z);
            }
        });
        this.mAlarmStateController = alarmStateController;
        alarmStateController.register(this.mContext);
        UDiskStateController uDiskStateController = new UDiskStateController(new UDiskStateController.OnUDiskStateChangedListener() {
            public final void onUDiskStateChanged(boolean z) {
                StatusBarView.this.lambda$initData$2$StatusBarView(z);
            }
        });
        this.mUDiskStateController = uDiskStateController;
        uDiskStateController.register(this.mContext);
        SensorStateController sensorStateController = new SensorStateController(new SensorStateController.OnSensorStateChangedListener() {
            public final void onSensorStateChanged(boolean z) {
                StatusBarView.this.lambda$initData$3$StatusBarView(z);
            }
        });
        this.mSensorStateController = sensorStateController;
        sensorStateController.register(this.mContext);
        ServerConnectStateController serverConnectStateController = new ServerConnectStateController(new ServerConnectStateController.OnServerConnectStateChangedListener() {
            public final void onServerConnectStateChanged(int i, boolean z) {
                StatusBarView.this.lambda$initData$4$StatusBarView(i, z);
            }
        });
        this.mServerConnectStateController = serverConnectStateController;
        serverConnectStateController.register(this.mContext);
        this.mLocationStateController = new LocationStateController(new LocationStateController.OnLocationStateChangedListener() {
            public final void onLocationChanged(boolean z, String str, double d, double d2, float f) {
                StatusBarView.this.lambda$initData$6$StatusBarView(z, str, d, d2, f);
            }
        });
    }

    public /* synthetic */ void lambda$initData$0$StatusBarView(boolean z) {
        this.ivAlarm.setVisibility(z ? 0 : 8);
    }

    public /* synthetic */ void lambda$initData$1$StatusBarView(boolean z) {
        this.ivUDisk.setVisibility(z ? 0 : 8);
    }

    public /* synthetic */ void lambda$initData$2$StatusBarView(boolean z) {
        this.ivUDisk.post(new Runnable(z) {
            public final /* synthetic */ boolean f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                StatusBarView.this.lambda$initData$1$StatusBarView(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$initData$3$StatusBarView(boolean z) {
        this.ivSenseState.setImageResource(z ? R.drawable.sense_open : R.drawable.sense_close);
    }

    public /* synthetic */ void lambda$initData$4$StatusBarView(int i, boolean z) {
        if (z) {
            this.mConnectState.setImageResource(i == 2 ? R.drawable.server_data : i == 1 ? R.drawable.connet_server : R.drawable.no_network_connect);
        }
        ServerConnectStateController.OnServerConnectStateChangedListener onServerConnectStateChangedListener = this.mServerConnectStateChangedListener;
        if (onServerConnectStateChangedListener != null) {
            onServerConnectStateChangedListener.onServerConnectStateChanged(i, z);
        }
    }

    public /* synthetic */ void lambda$initData$6$StatusBarView(boolean z, String str, double d, double d2, float f) {
        this.ivLocation.post(new Runnable(z, str, d, d2, f) {
            public final /* synthetic */ boolean f$1;
            public final /* synthetic */ String f$2;
            public final /* synthetic */ double f$3;
            public final /* synthetic */ double f$4;
            public final /* synthetic */ float f$5;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r6;
                this.f$5 = r8;
            }

            public final void run() {
                StatusBarView.this.lambda$initData$5$StatusBarView(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5);
            }
        });
    }

    public /* synthetic */ void lambda$initData$5$StatusBarView(boolean z, String str, double d, double d2, float f) {
        if (!z) {
            this.ivLocation.clearAnimation();
            this.ivLocation.setVisibility(8);
            String str2 = str;
        } else {
            String str3 = str;
            updateLocationIcon(str);
        }
        LocationStateController.OnLocationStateChangedListener onLocationStateChangedListener = this.mLocationStateChangedListener;
        if (onLocationStateChangedListener != null) {
            onLocationStateChangedListener.onLocationChanged(z, str, d, d2, f);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateLocationIcon(java.lang.String r5) {
        /*
            r4 = this;
            r5.hashCode()
            java.lang.String r0 = "gps"
            boolean r0 = r5.equals(r0)
            r1 = 0
            if (r0 != 0) goto L_0x001e
            java.lang.String r0 = "network"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L_0x0019
            r5 = 2131558498(0x7f0d0062, float:1.8742314E38)
            r0 = 1
            goto L_0x0022
        L_0x0019:
            r5 = 2131558499(0x7f0d0063, float:1.8742316E38)
        L_0x001c:
            r0 = r1
            goto L_0x0022
        L_0x001e:
            r5 = 2131558489(0x7f0d0059, float:1.8742295E38)
            goto L_0x001c
        L_0x0022:
            android.widget.ImageView r2 = r4.ivLocation
            r2.setImageResource(r5)
            android.widget.ImageView r5 = r4.ivLocation
            r5.clearAnimation()
            if (r0 == 0) goto L_0x004e
            android.view.animation.AlphaAnimation r5 = new android.view.animation.AlphaAnimation
            r0 = 0
            r2 = 1065353216(0x3f800000, float:1.0)
            r5.<init>(r0, r2)
            r2 = 300(0x12c, double:1.48E-321)
            r5.setDuration(r2)
            r0 = 2
            r5.setRepeatMode(r0)
            r0 = -1
            r5.setRepeatCount(r0)
            android.widget.ImageView r0 = r4.ivLocation
            r0.startAnimation(r5)
            android.widget.ImageView r5 = r4.ivLocation
            r5.setVisibility(r1)
            goto L_0x0053
        L_0x004e:
            android.widget.ImageView r5 = r4.ivLocation
            r5.setVisibility(r1)
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.view.StatusBarView.updateLocationIcon(java.lang.String):void");
    }

    private void initView() {
        inflate(this.mContext, R.layout.view_status_bar, this);
        this.mLlStatusBar = (LinearLayout) findViewById(R.id.ll_status_bar);
        this.mConnectState = (ImageView) findViewById(R.id.iv_connect_state);
        this.ivEthernet = (ImageView) findViewById(R.id.iv_connect_ethernet_state);
        this.ivWifi = (ImageView) findViewById(R.id.iv_connect_wifi_state);
        this.ivUDisk = (ImageView) findViewById(R.id.iv_connect_udisk_state);
        this.ivAlarm = (ImageView) findViewById(R.id.iv_alarm_state);
        this.ivIO = (ImageView) findViewById(R.id.iv_IO_state);
        this.ivAuInput = (ImageView) findViewById(R.id.iv_auInput_state);
        this.ivOpenAlways = (ImageView) findViewById(R.id.iv_open_always_state);
        this.ivSenseState = (ImageView) findViewById(R.id.iv_sense_state);
        this.deviceInfo = (ImageView) findViewById(R.id.iv_device_info);
        this.batteryView = (BatteryView) findViewById(R.id.battery_view);
        this.mobileView = (MobileDataSignalView) findViewById(R.id.view_mobile);
        this.ivLocation = (ImageView) findViewById(R.id.iv_location);
    }

    public void setClickListener(View.OnClickListener onClickListener) {
        this.mConnectState.setOnClickListener(onClickListener);
        this.ivEthernet.setOnClickListener(onClickListener);
        this.ivWifi.setOnClickListener(onClickListener);
        this.ivUDisk.setOnClickListener(onClickListener);
        this.ivAlarm.setOnClickListener(onClickListener);
        this.ivIO.setOnClickListener(onClickListener);
        this.ivAuInput.setOnClickListener(onClickListener);
        this.ivOpenAlways.setOnClickListener(onClickListener);
        this.ivSenseState.setOnClickListener(onClickListener);
        this.deviceInfo.setOnClickListener(onClickListener);
    }

    public void setIconState(EventState eventState) {
        int type = eventState.getType();
        int i = 8;
        if (type == 0) {
            ImageView imageView = this.ivAlarm;
            if (eventState.isAlarm()) {
                i = 0;
            }
            imageView.setVisibility(i);
        } else if (type == 1) {
            if (eventState.getIOState() == 1) {
                this.ivIO.setImageResource(R.drawable.in);
            } else {
                this.ivIO.setImageResource(R.drawable.out);
            }
            ImageView imageView2 = this.ivAuInput;
            if (eventState.getAuInput() != 0) {
                i = 0;
            }
            imageView2.setVisibility(i);
        } else if (type == 2) {
            String openAlways = eventState.getOpenAlways();
            if ("open".equals(openAlways)) {
                this.ivOpenAlways.setVisibility(0);
                this.ivOpenAlways.setImageResource(R.drawable.open_always);
            } else if ("forbidden".equals(openAlways)) {
                this.ivOpenAlways.setVisibility(8);
            } else if ("openInvalid".equals(openAlways)) {
                this.ivOpenAlways.setVisibility(0);
                this.ivOpenAlways.setImageResource(R.drawable.open_always_invalid);
            }
        }
    }

    public void setOnServerConnectStateChangedListener(ServerConnectStateController.OnServerConnectStateChangedListener onServerConnectStateChangedListener) {
        this.mServerConnectStateChangedListener = onServerConnectStateChangedListener;
    }

    public void setOnLocationStateChangedListener(LocationStateController.OnLocationStateChangedListener onLocationStateChangedListener) {
        this.mLocationStateChangedListener = onLocationStateChangedListener;
    }

    public void resetViewVisibility() {
        if (DeviceManager.getDefault().isH1()) {
            this.ivIO.setVisibility(8);
            this.ivSenseState.setVisibility(8);
            this.ivOpenAlways.setVisibility(8);
            this.ivAuInput.setVisibility(8);
            this.ivAlarm.setVisibility(8);
            this.ivEthernet.setVisibility(8);
            this.batteryView.setVisibility(0);
        }
    }

    public void startLocation() {
        this.mLocationStateController.start();
    }

    public void onInitComplete() {
        startLocation();
        this.mNetworkStateController = new NetworkStateController(this.mContext, new NetworkStateController.OnNetworkStateChangedListener() {
            public void onWiFiStateChanged(int i, int i2) {
                StatusBarView.this.ivWifi.post(new Runnable(i, i2) {
                    public final /* synthetic */ int f$1;
                    public final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        StatusBarView.AnonymousClass1.this.lambda$onWiFiStateChanged$0$StatusBarView$1(this.f$1, this.f$2);
                    }
                });
            }

            public /* synthetic */ void lambda$onWiFiStateChanged$0$StatusBarView$1(int i, int i2) {
                if (i == 1) {
                    StatusBarView.this.ivWifi.setVisibility(0);
                    StatusBarView.this.ivWifi.setImageResource(R.drawable.nowifi);
                } else if (i == 2) {
                    StatusBarView.this.ivWifi.setVisibility(0);
                    StatusBarView.this.ivWifi.setImageResource(R.drawable.wifi_not_connected);
                } else if (i != 3) {
                    StatusBarView.this.ivWifi.setVisibility(8);
                } else {
                    StatusBarView.this.ivWifi.setVisibility(0);
                    if (i2 == 0) {
                        StatusBarView.this.ivWifi.setImageResource(R.drawable.wifi1);
                    } else if (i2 == 1) {
                        StatusBarView.this.ivWifi.setImageResource(R.drawable.wifi2);
                    } else if (i2 == 2) {
                        StatusBarView.this.ivWifi.setImageResource(R.drawable.wifi3);
                    } else if (i2 == 3) {
                        StatusBarView.this.ivWifi.setImageResource(R.drawable.wifi4);
                    }
                }
            }

            public /* synthetic */ void lambda$onEthernetStateChanged$1$StatusBarView$1(boolean z) {
                StatusBarView.this.ivEthernet.setImageResource(z ? R.drawable.conn_ethernet : R.drawable.no_ehernet);
            }

            public void onEthernetStateChanged(boolean z) {
                StatusBarView.this.ivEthernet.post(new Runnable(z) {
                    public final /* synthetic */ boolean f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        StatusBarView.AnonymousClass1.this.lambda$onEthernetStateChanged$1$StatusBarView$1(this.f$1);
                    }
                });
            }

            public /* synthetic */ void lambda$onMobileStateChanged$2$StatusBarView$1(int i, int i2) {
                StatusBarView.this.mobileView.setLevelAndType(i, i2);
            }

            public void onMobileStateChanged(int i, int i2, int i3) {
                StatusBarView.this.mobileView.post(new Runnable(i3, i2) {
                    public final /* synthetic */ int f$1;
                    public final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        StatusBarView.AnonymousClass1.this.lambda$onMobileStateChanged$2$StatusBarView$1(this.f$1, this.f$2);
                    }
                });
            }

            public /* synthetic */ void lambda$onMobileAvailable$3$StatusBarView$1(boolean z) {
                StatusBarView.this.mobileView.setVisibility(z ? 0 : 8);
            }

            public void onMobileAvailable(boolean z) {
                StatusBarView.this.mobileView.post(new Runnable(z) {
                    public final /* synthetic */ boolean f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        StatusBarView.AnonymousClass1.this.lambda$onMobileAvailable$3$StatusBarView$1(this.f$1);
                    }
                });
            }
        });
    }

    public void updateStatus(int i, int i2) {
        if (i != 0) {
            this.ivIO.setVisibility(0);
        } else if (i2 != 15) {
            this.ivIO.setVisibility(8);
        } else if (!DeviceManager.getDefault().isH1()) {
            this.ivIO.setVisibility(0);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
            this.mLlStatusBar.setGravity(3);
            this.mLlStatusBar.setLayoutDirection(1);
            return;
        }
        this.mLlStatusBar.setGravity(5);
        this.mLlStatusBar.setLayoutDirection(0);
    }

    public void onPause() {
        this.ivLocation.clearAnimation();
    }
}
