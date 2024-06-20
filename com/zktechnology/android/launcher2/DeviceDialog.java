package com.zktechnology.android.launcher2;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.launcher.BuildConfig;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.DeviceDialog;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.GlobalConfig;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.zkcore.utils.ZKSharedUtil;
import com.zkteco.edk.system.lib.base.ZkSystemConstants;
import com.zkteco.liveface562.ZkFaceManager;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.Callable;

public class DeviceDialog extends Dialog {
    private Context mContext;
    private Disposable mDisposable;

    public DeviceDialog(Context context) {
        super(context, R.style.initdialogstyle);
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_devicedetect);
        setCanceledOnTouchOutside(true);
        initData();
    }

    public void release() {
        Disposable disposable = this.mDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mDisposable.dispose();
        }
        this.mContext = null;
    }

    public void initData() {
        this.mDisposable = Flowable.fromCallable(new Callable() {
            public final Object call() {
                return DeviceDialog.this.lambda$initData$0$DeviceDialog();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() {
            public final void accept(Object obj) {
                DeviceDialog.this.lambda$initData$1$DeviceDialog((DeviceDialog.DeviceInfo) obj);
            }
        });
    }

    public /* synthetic */ DeviceInfo lambda$initData$0$DeviceDialog() throws Exception {
        DataManager instance = DBManager.getInstance();
        DeviceInfo deviceInfo = new DeviceInfo();
        String unused = deviceInfo.deviceName = instance.getStrOption("~DeviceName", "ZKTeco");
        String unused2 = deviceInfo.deviceSN = instance.getStrOption("~SerialNumber", "");
        String unused3 = deviceInfo.oemVendor = "‭" + instance.getStrOption("~OEMVendor", "") + "‬";
        ZKSharedUtil zKSharedUtil = new ZKSharedUtil(this.mContext.getApplicationContext());
        String unused4 = deviceInfo.cardModuleName = zKSharedUtil.getString("CardModuleName", "");
        String unused5 = deviceInfo.mcuVersion = zKSharedUtil.getString("MCU_Version", this.mContext.getApplicationContext().getResources().getString(R.string.no_mcu_version));
        String unused6 = deviceInfo.faceVersion = instance.getStrOption("~FaceAlgVer", "");
        String unused7 = deviceInfo.faceVersionNum = instance.getStrOption("ZKFaceVersion", "");
        String unused8 = deviceInfo.fingerVersionNum = instance.getStrOption("~ZKFPVersion", "");
        String unused9 = deviceInfo.fingerVersion = instance.getStrOption(ZKDBConfig.FINGER_ALGORITHM_VERSION, "");
        if (!"1".equals(instance.getStrOption("hasFingerModule", "0")) || !"1".equals(instance.getStrOption("FingerFunOn", "0"))) {
            String unused10 = deviceInfo.fingerprintVersion = "";
            String unused11 = deviceInfo.fpVersion = "";
        } else {
            String unused12 = deviceInfo.fingerprintVersion = instance.getStrOption("ZKFPFWVersion", this.mContext.getApplicationContext().getResources().getString(R.string.no_fingerprint_version));
            String unused13 = deviceInfo.fpVersion = deviceInfo.fingerVersion + "-" + deviceInfo.fingerVersionNum;
        }
        int unused14 = deviceInfo.mProductTimeFunOn = instance.getIntOption(DBConfig.ABOUT_PRODUCKTIMEFUNON, 1);
        String unused15 = deviceInfo.mProductTime = instance.getStrOption(DBConfig.ABOUT_PRODUCKTIME, "");
        int unused16 = deviceInfo.sAccessRuleType = instance.getIntOption("AccessRuleType", 0);
        String unused17 = deviceInfo.techSupportStr = instance.getStrOption(ZKDBConfig.DEVICE_TECH_SUPPORT, "");
        String unused18 = deviceInfo.landlineStr = instance.getStrOption(ZKDBConfig.DEVICE_LANDLINE, "");
        String unused19 = deviceInfo.emailStr = instance.getStrOption(ZKDBConfig.DEVICE_EMAIL, "");
        String unused20 = deviceInfo.mPushVer = instance.getStrOption(ZKDBConfig.PUSH_VERSION, "");
        String unused21 = deviceInfo.pvVersion = instance.getStrOption("PvVersion", "12.0");
        int unused22 = deviceInfo.pvFunOn = instance.getIntOption("PvFunOn", 0);
        deviceInfo.ipAddress = getIpAddress(this.mContext.getApplicationContext());
        if (deviceInfo.mPushVer.isEmpty()) {
            String unused23 = deviceInfo.mPushVer = new HubProtocolManager(this.mContext.getApplicationContext()).getPushVerInfo();
            instance.setStrOption(ZKDBConfig.PUSH_VERSION, deviceInfo.mPushVer);
        }
        return deviceInfo;
    }

    public /* synthetic */ void lambda$initData$1$DeviceDialog(DeviceInfo deviceInfo) throws Exception {
        TextView textView;
        int i;
        int i2;
        TextView textView2 = (TextView) findViewById(R.id.tv_ip_address);
        TextView textView3 = (TextView) findViewById(R.id.tv_sn);
        TextView textView4 = (TextView) findViewById(R.id.tv_device_name);
        TextView textView5 = (TextView) findViewById(R.id.tv_manufacturer);
        TextView textView6 = (TextView) findViewById(R.id.tv_pl_version);
        TextView textView7 = (TextView) findViewById(R.id.tv_fir_version);
        TextView textView8 = (TextView) findViewById(R.id.tv_Finger_module);
        TextView textView9 = (TextView) findViewById(R.id.tv_ext_module);
        TextView textView10 = (TextView) findViewById(R.id.tv_finger_version);
        TextView textView11 = (TextView) findViewById(R.id.tv_face_version);
        TextView textView12 = (TextView) findViewById(R.id.tv_made_time);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_made_time);
        TextView textView13 = (TextView) findViewById(R.id.tv_card_module);
        TextView textView14 = (TextView) findViewById(R.id.tv_landline);
        TextView textView15 = (TextView) findViewById(R.id.tv_email);
        TextView textView16 = (TextView) findViewById(R.id.tv_tech_support);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.rl_tech_support);
        RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.rl_email);
        RelativeLayout relativeLayout4 = (RelativeLayout) findViewById(R.id.rl_landline);
        TextView textView17 = (TextView) findViewById(R.id.tv_push_ver);
        RelativeLayout relativeLayout5 = (RelativeLayout) findViewById(R.id.rl_pv_version);
        boolean isEmpty = deviceInfo.cardModuleName.isEmpty();
        TextView textView18 = (TextView) findViewById(R.id.tv_pv_version);
        TextView textView19 = (TextView) findViewById(R.id.tv_title);
        if (isEmpty) {
            textView13.setVisibility(8);
        } else {
            textView13.setVisibility(0);
            textView13.setText(deviceInfo.cardModuleName);
        }
        if (deviceInfo.fpVersion.isEmpty()) {
            findViewById(R.id.rl_fingerprint_module).setVisibility(8);
            findViewById(R.id.rl_fingerprint_version).setVisibility(8);
        } else {
            findViewById(R.id.rl_fingerprint_module).setVisibility(0);
            findViewById(R.id.rl_fingerprint_version).setVisibility(0);
        }
        if (DeviceManager.getDefault().isH1()) {
            findViewById(R.id.rl_ext_module).setVisibility(8);
            findViewById(R.id.rl_pv).setVisibility(8);
            findViewById(R.id.rl_face_activation_status).setVisibility(0);
            findViewById(R.id.rl_firmware_activation_status).setVisibility(0);
            ZKSharedUtil zKSharedUtil = new ZKSharedUtil(LauncherApplication.getLauncherApplicationContext());
            ((ViewGroup) findViewById(R.id.rl_firmware_activation_status)).setVisibility(DeviceManager.getDefault().isH1() ? 8 : 0);
            TextView textView20 = (TextView) findViewById(R.id.tv_face_activation_status);
            textView = textView12;
            boolean z = ZkFaceManager.getInstance().isAuthorized() == 0;
            zKSharedUtil.putBoolean(GlobalConfig.IS_FACE_AUTHORIZED, z);
            textView20.setText(z ? "true" : "false");
        } else {
            textView = textView12;
        }
        textView3.setText(deviceInfo.deviceSN);
        textView4.setText(deviceInfo.deviceName);
        textView5.setText(deviceInfo.oemVendor);
        textView2.setText(deviceInfo.ipAddress);
        textView6.setText(Build.VERSION.INCREMENTAL);
        setMarquee(textView6);
        textView8.setText(deviceInfo.fingerprintVersion);
        setMarquee(textView8);
        textView9.setText(deviceInfo.mcuVersion);
        setMarquee(textView9);
        textView7.setText(getVersionName(this.mContext.getApplicationContext()));
        textView11.setText(String.format("%s%s", new Object[]{deviceInfo.faceVersion, deviceInfo.faceVersionNum}));
        textView10.setText(deviceInfo.fpVersion);
        if (TextUtils.isEmpty(deviceInfo.mProductTime) || deviceInfo.mProductTime.equals("null") || deviceInfo.mProductTimeFunOn != 1) {
            relativeLayout.setVisibility(8);
        } else {
            relativeLayout.setVisibility(0);
            textView.setText(deviceInfo.mProductTime);
        }
        if (deviceInfo.sAccessRuleType == 0) {
            textView19.setText(R.string.attendance);
        } else {
            textView19.setText(R.string.access);
        }
        if (deviceInfo.emailStr.isEmpty()) {
            i2 = 8;
            relativeLayout3.setVisibility(8);
            i = 0;
        } else {
            i2 = 8;
            i = 0;
            relativeLayout3.setVisibility(0);
            textView15.setText(deviceInfo.emailStr);
        }
        if (deviceInfo.landlineStr.isEmpty()) {
            relativeLayout4.setVisibility(i2);
        } else {
            relativeLayout4.setVisibility(i);
            textView14.setText(deviceInfo.landlineStr);
        }
        if (deviceInfo.techSupportStr.isEmpty()) {
            relativeLayout2.setVisibility(i2);
        } else {
            relativeLayout2.setVisibility(i);
            textView16.setText(deviceInfo.techSupportStr);
        }
        textView17.setText(deviceInfo.mPushVer);
        if (deviceInfo.pvFunOn == 1) {
            relativeLayout5.setVisibility(i);
            textView18.setText(deviceInfo.pvVersion);
            return;
        }
        relativeLayout5.setVisibility(8);
    }

    private static void setMarquee(TextView textView) {
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSingleLine(true);
        textView.setSelected(true);
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);
    }

    public static String getIpAddress(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return null;
        }
        if (activeNetworkInfo.getType() == 0) {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                    while (true) {
                        if (inetAddresses.hasMoreElements()) {
                            InetAddress nextElement = inetAddresses.nextElement();
                            if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                                return nextElement.getHostAddress();
                            }
                        }
                    }
                }
                return null;
            } catch (SocketException e) {
                e.printStackTrace();
                return null;
            }
        } else if (activeNetworkInfo.getType() == 1) {
            return intIP2StringIP(((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getIpAddress());
        } else {
            if (activeNetworkInfo.getType() == 9) {
                return getLocalIp();
            }
            return null;
        }
    }

    private static String intIP2StringIP(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    private static String getLocalIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                            return nextElement.getHostAddress();
                        }
                    }
                }
            }
            return ZkSystemConstants.NULL_IP_ADDRESS;
        } catch (SocketException unused) {
            return ZkSystemConstants.NULL_IP_ADDRESS;
        }
    }

    public static synchronized String getVersionName(Context context) {
        String str;
        synchronized (DeviceDialog.class) {
            try {
                str = context.getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, 0).versionName;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return str;
    }

    private static class DeviceInfo {
        /* access modifiers changed from: private */
        public String cardModuleName;
        /* access modifiers changed from: private */
        public String deviceName;
        /* access modifiers changed from: private */
        public String deviceSN;
        /* access modifiers changed from: private */
        public String emailStr;
        /* access modifiers changed from: private */
        public String faceVersion;
        /* access modifiers changed from: private */
        public String faceVersionNum;
        /* access modifiers changed from: private */
        public String fingerVersion;
        /* access modifiers changed from: private */
        public String fingerVersionNum;
        /* access modifiers changed from: private */
        public String fingerprintVersion;
        /* access modifiers changed from: private */
        public String fpVersion;
        public String ipAddress;
        /* access modifiers changed from: private */
        public String landlineStr;
        private int mFaceStatus;
        private int mFirmwareStatue;
        /* access modifiers changed from: private */
        public String mProductTime;
        /* access modifiers changed from: private */
        public int mProductTimeFunOn;
        /* access modifiers changed from: private */
        public String mPushVer;
        /* access modifiers changed from: private */
        public String mcuVersion;
        /* access modifiers changed from: private */
        public String oemVendor;
        /* access modifiers changed from: private */
        public int pvFunOn;
        /* access modifiers changed from: private */
        public String pvVersion;
        /* access modifiers changed from: private */
        public int sAccessRuleType;
        /* access modifiers changed from: private */
        public String techSupportStr;

        private DeviceInfo() {
        }
    }
}
