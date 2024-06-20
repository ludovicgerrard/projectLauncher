package com.zktechnology.android.launcher2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.exifinterface.media.ExifInterface;
import com.android.common.speech.LoggingEvents;
import com.guide.guidecore.UsbStatusInterface;
import com.guide.guidecore.view.IrSurfaceView;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.strategy.BackgroundThreadExecutor;
import com.zktechnology.android.utils.GuideHelper;
import com.zktechnology.android.utils.SaveImage;
import com.zktechnology.android.verify.utils.ZKTemperatureUtil;
import com.zkteco.android.zkcore.utils.ZKSharedUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class ZKGuideCoreLauncher extends ZKEventLauncher implements GuideHelper.ImageCallBackInterface, UsbStatusInterface {
    /* access modifiers changed from: private */
    public static final String TAG = "ZKGuideCoreLauncher";
    public static int deviceType;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.zkteco.android.update")) {
                String unused = ZKGuideCoreLauncher.this.path = intent.getStringExtra("path");
                ZKGuideCoreLauncher.this.mThreadService.submit(ZKGuideCoreLauncher.this.updateTask);
            }
            if (intent.getAction().equals("com.zkteco.android.closeThermalImaging")) {
                try {
                    Log.d(ZKGuideCoreLauncher.TAG, "close ThermalImaging");
                    ZKGuideCoreLauncher.writeStringAsFile("0", ZKGuideCoreLauncher.this.path, false);
                    FileLogUtils.writeStateLog("broadcastReceiver power down");
                } catch (Exception e) {
                    Log.d(ZKGuideCoreLauncher.TAG, "close Exception: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public Future future;
    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 0 && !ZKGuideCoreLauncher.this.isOnPause) {
                boolean unused = ZKGuideCoreLauncher.this.isgetting = false;
                if (ZKGuideCoreLauncher.this.future != null && !ZKGuideCoreLauncher.this.future.isDone()) {
                    ZKGuideCoreLauncher.this.future.cancel(true);
                }
                ZKGuideCoreLauncher zKGuideCoreLauncher = ZKGuideCoreLauncher.this;
                Future unused2 = zKGuideCoreLauncher.future = zKGuideCoreLauncher.mStartTask.submit(ZKGuideCoreLauncher.this.startTask);
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean isOnPause;
    /* access modifiers changed from: private */
    public boolean isgetting = true;
    /* access modifiers changed from: private */
    public long lastTime;
    /* access modifiers changed from: private */
    public GuideHelper mGuideInterface;
    private IrSurfaceView mIrSurfaceView;
    private ExecutorService mSingService = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public ExecutorService mStartTask = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public ExecutorService mThreadService = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public String maxTempStr = "0";
    Runnable maxTempTask = new Runnable() {
        public void run() {
            if (ZKGuideCoreLauncher.this.rectface != null) {
                ZKGuideCoreLauncher zKGuideCoreLauncher = ZKGuideCoreLauncher.this;
                GuideHelper access$700 = zKGuideCoreLauncher.mGuideInterface;
                GuideHelper access$7002 = ZKGuideCoreLauncher.this.mGuideInterface;
                ZKGuideCoreLauncher zKGuideCoreLauncher2 = ZKGuideCoreLauncher.this;
                String unused = zKGuideCoreLauncher.maxTempStr = access$700.getHumanTemp(Float.parseFloat(access$7002.measureTemByY16(zKGuideCoreLauncher2.getMaxY16(zKGuideCoreLauncher2.getIndex(zKGuideCoreLauncher2.rectface, ZKGuideCoreLauncher.this.newShort)))), -1314.0f);
                ZKTemperatureUtil.setThTemper(ZKGuideCoreLauncher.this.maxTempStr);
            }
        }
    };
    /* access modifiers changed from: private */
    public short[] newShort;
    /* access modifiers changed from: private */
    public String path;
    private String powerName = null;
    Runnable startTask = new Runnable() {
        public void run() {
            if (!ZKGuideCoreLauncher.this.isgetting) {
                try {
                    ZKGuideCoreLauncher.this.stopGuide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (ZKGuideCoreLauncher.this.mGuideInterface != null) {
                    ZKGuideCoreLauncher.this.mGuideInterface.guideCoreDestory();
                }
                Log.d(ZKGuideCoreLauncher.TAG, "writeStringAsFile: 0");
                ZKGuideCoreLauncher.writeStringAsFile("0", ZKGuideCoreLauncher.this.sysPath, false);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                Log.d(ZKGuideCoreLauncher.TAG, "writeStringAsFile: 1");
                ZKGuideCoreLauncher.writeStringAsFile("1", ZKGuideCoreLauncher.this.sysPath, false);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
                GuideHelper unused = ZKGuideCoreLauncher.this.mGuideInterface = new GuideHelper();
                ZKGuideCoreLauncher.this.mGuideInterface.guideCoreInit(ZKGuideCoreLauncher.this, 2, 1.0f, 3);
                ZKGuideCoreLauncher.this.mGuideInterface.controlImageOptimizer(true);
            }
            long elapsedRealtime = SystemClock.elapsedRealtime() - ZKGuideCoreLauncher.this.lastTime;
            if (elapsedRealtime > 0 && elapsedRealtime < 5000) {
                try {
                    Thread.sleep(5000 - elapsedRealtime);
                } catch (InterruptedException e4) {
                    e4.printStackTrace();
                }
            }
            if (!ZKGuideCoreLauncher.this.isOnPause) {
                ZKGuideCoreLauncher.this.startGuide();
            }
        }
    };
    /* access modifiers changed from: private */
    public String sysPath = "/sys/class/gpio/gpio136/value";
    Runnable updateTask = new Runnable() {
        public void run() {
            int code = ZKGuideCoreLauncher.this.mGuideInterface.firmwareUpgrade(ZKGuideCoreLauncher.this.path).getCode();
            if (code == 0 && ZKGuideCoreLauncher.this.mGuideInterface != null) {
                ZKGuideCoreLauncher.writeStringAsFile("0", ZKGuideCoreLauncher.this.path, false);
            }
            Intent intent = new Intent();
            intent.setAction("com.zkteco.android.update.callback");
            intent.putExtra(LoggingEvents.VoiceIme.EXTRA_ERROR_CODE, code);
            ZKGuideCoreLauncher.this.sendBroadcast(intent);
        }
    };
    private String version = "0";
    private ZKSharedUtil zkSharedUtil;

    public static void setDeviceType(int i) {
        deviceType = i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ZKSharedUtil zKSharedUtil = new ZKSharedUtil(this);
        this.zkSharedUtil = zKSharedUtil;
        String str = "0";
        String string = zKSharedUtil.getString("deviceType", str);
        if (!TextUtils.isEmpty(string)) {
            str = string;
        }
        setDeviceType(Integer.parseInt(str));
        if (deviceType == 0) {
            setDeviceType(guideIsConnect());
            this.zkSharedUtil.putString("deviceType", String.valueOf(deviceType));
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zkteco.android.update");
        intentFilter.addAction("com.zkteco.android.closeThermalImaging");
        registerReceiver(this.broadcastReceiver, intentFilter);
        this.mGuideInterface = new GuideHelper();
        BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable() {
            public final void run() {
                ZKGuideCoreLauncher.this.lambda$onCreate$0$ZKGuideCoreLauncher();
            }
        });
        initView();
    }

    public /* synthetic */ void lambda$onCreate$0$ZKGuideCoreLauncher() {
        this.mGuideInterface.guideCoreInit(this, 2, 1.0f, 3);
        this.mGuideInterface.controlImageOptimizer(true);
    }

    private int guideIsConnect() {
        HashMap<String, UsbDevice> deviceList = ((UsbManager) getSystemService("usb")).getDeviceList();
        for (String str : deviceList.keySet()) {
            UsbDevice usbDevice = deviceList.get(str);
            if (usbDevice != null && usbDevice.getProductId() == 42144 && usbDevice.getVendorId() == 1317) {
                return 1;
            }
        }
        return 0;
    }

    private void initView() {
        this.mIrSurfaceView = new IrSurfaceView(this);
        this.mIrSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
        this.mIrSurfaceView.setMatrix(dip2px(120) / 120.0f, 0.0f, 0.0f);
        this.irLayout.addView(this.mIrSurfaceView);
    }

    private float dip2px(int i) {
        return ((float) i) * getResources().getDisplayMetrics().density;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        try {
            this.isOnPause = false;
            this.irLayout.setVisibility(8);
            setDeviceType(Integer.parseInt(this.zkSharedUtil.getString("deviceType", "0")));
            if (deviceType == 1) {
                this.isgetting = true;
                Future future2 = this.future;
                if (future2 != null && !future2.isDone()) {
                    this.future.cancel(true);
                }
                this.future = this.mStartTask.submit(this.startTask);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeStringAsFile(String str, String str2, boolean z) {
        try {
            File file = new File(str2);
            if (file.exists()) {
                FileWriter fileWriter = new FileWriter(file, z);
                fileWriter.write(str);
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void startGuide() {
        this.mGuideInterface.registUsbPermissions();
        this.mGuideInterface.registUsbStatus(this);
        this.mGuideInterface.setAutoShutter(true, 20000, 10000);
        this.mGuideInterface.startGetImage(this);
        if (this.handler.hasMessages(0)) {
            this.handler.removeMessages(0);
        }
        this.handler.sendEmptyMessageDelayed(0, 20000);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.isOnPause = true;
        Future future2 = this.future;
        if (future2 != null && !future2.isDone()) {
            this.future.cancel(true);
        }
        BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable() {
            public final void run() {
                ZKGuideCoreLauncher.this.lambda$onPause$1$ZKGuideCoreLauncher();
            }
        });
    }

    public /* synthetic */ void lambda$onPause$1$ZKGuideCoreLauncher() {
        try {
            stopGuide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void stopGuide() {
        if (this.handler.hasMessages(0)) {
            this.handler.removeMessages(0);
        }
        this.lastTime = SystemClock.elapsedRealtime();
        this.mGuideInterface.unRegistUsbPermissions();
        this.mGuideInterface.unRigistUsbStatus();
        this.mGuideInterface.stopGetImage();
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            BroadcastReceiver broadcastReceiver2 = this.broadcastReceiver;
            if (broadcastReceiver2 != null) {
                unregisterReceiver(broadcastReceiver2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callBackOneFrameBitmap(Bitmap bitmap, short[] sArr) {
        if (ZkFaceLauncher.goOn) {
            new SaveImage().saveBmp(bitmap, SaveImage.thermalimagePath);
        }
        if (this.handler.hasMessages(0)) {
            this.handler.removeMessages(0);
        }
        this.handler.sendEmptyMessageDelayed(0, 10000);
        this.isgetting = true;
        Log.d(TAG, "bitmap: " + bitmap);
        runOnUiThread(new Runnable() {
            public void run() {
                if (ZKGuideCoreLauncher.this.irLayout.getVisibility() == 8 && ZKGuideCoreLauncher.this.enableIRTempImage == 1 && ZKGuideCoreLauncher.deviceType == 1 && ZKGuideCoreLauncher.this.isworked) {
                    Log.d(ZKGuideCoreLauncher.TAG, "irLayout VISIBLE");
                    ZKGuideCoreLauncher.this.irLayout.setVisibility(0);
                }
            }
        });
        this.mIrSurfaceView.doDraw(bitmap, this.mGuideInterface.getShutterStatus());
        if (!this.version.equals(this.mGuideInterface.getFirmwareVersion())) {
            String firmwareVersion = this.mGuideInterface.getFirmwareVersion();
            this.version = firmwareVersion;
            this.zkSharedUtil.putString("GuideVersion", firmwareVersion);
        }
        this.newShort = sArr;
        this.mSingService.submit(this.maxTempTask);
    }

    /* access modifiers changed from: private */
    public short[] getIndex(Rect rect, short[] sArr) {
        ArrayList arrayList = new ArrayList();
        int intValue = new BigDecimal(rect.top).divide(new BigDecimal("1.5"), RoundingMode.UP).intValue();
        int intValue2 = new BigDecimal(rect.right).divide(new BigDecimal("1.5"), RoundingMode.UP).intValue();
        int intValue3 = new BigDecimal(rect.bottom).divide(new BigDecimal("1.5"), RoundingMode.UP).intValue();
        for (int intValue4 = new BigDecimal(rect.left).divide(new BigDecimal("1.5"), RoundingMode.UP).intValue(); intValue4 <= intValue2; intValue4++) {
            for (int i = intValue; i <= intValue3; i++) {
                arrayList.add(Short.valueOf(sArr[new BigDecimal(i - 1).multiply(new BigDecimal("90")).add(new BigDecimal(intValue4)).intValue()]));
            }
        }
        short[] sArr2 = new short[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            sArr2[i2] = ((Short) arrayList.get(i2)).shortValue();
        }
        return sArr2;
    }

    public void usbConnect() {
        try {
            setDeviceType(1);
            Log.d(TAG, "usbConnect: ");
            FileLogUtils.writeStateLog("thermalImaging usbConnect");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void usbDisConnect() {
        try {
            Log.d(TAG, "usbDisConnect: ");
            FileLogUtils.writeStateLog("thermalImaging usbDisConnect");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public short getMaxY16(short[] sArr) {
        for (int i = 0; i < 5; i++) {
            int i2 = 0;
            while (i2 < (sArr.length - i) - 1) {
                int i3 = i2 + 1;
                if (sArr[i2] > sArr[i3]) {
                    short s = sArr[i2];
                    sArr[i2] = sArr[i3];
                    sArr[i3] = s;
                }
                i2 = i3;
            }
        }
        return new BigDecimal((short) (sArr[sArr.length - 2] + sArr[sArr.length - 3] + sArr[sArr.length - 4])).divide(new BigDecimal(ExifInterface.GPS_MEASUREMENT_3D), 0).shortValueExact();
    }
}
