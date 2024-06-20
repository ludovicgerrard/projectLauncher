package com.zkteco.edk.mcu.lib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.zkteco.edk.mcu.lib.IAidlMcuServiceInterface;
import com.zkteco.edk.mcu.lib.IAidlRs232Listener;
import com.zkteco.edk.mcu.lib.IAidlRs485Listener;
import com.zkteco.edk.mcu.lib.IAidlWiegandListener;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ZkMcuManager {
    private static final int SERVICE_CONNECT_FAILED = 0;
    private static final int SERVICE_CONNECT_SUCCESS = 1;
    private static final String TAG = "EDK-MCU-MANAGER";
    private static ZkMcuManager mInstance;
    /* access modifiers changed from: private */
    public IAidlMcuServiceInterface mAidlMcuServiceInterface;
    private final IAidlRs232Listener mAidlRs232Listener = new IAidlRs232Listener.Stub() {
        public void onRs232Detected(byte[] bArr) throws RemoteException {
            if (bArr != null && bArr.length > 0 && ZkMcuManager.this.mRs232Listener != null) {
                ZkMcuManager.this.mRs232Listener.onRs232Detected(bArr);
            }
        }
    };
    private final IAidlRs485Listener mAidlRs485Listener = new IAidlRs485Listener.Stub() {
        public void onRs485Detected(byte[] bArr) throws RemoteException {
            if (bArr != null && bArr.length > 0 && ZkMcuManager.this.mRs485Listener != null) {
                ZkMcuManager.this.mRs485Listener.onRs485Detected(bArr);
            }
        }
    };
    private final IAidlWiegandListener mAidlWiegandListener = new IAidlWiegandListener.Stub() {
        public void onWiegandDetected(String str) throws RemoteException {
            if (ZkMcuManager.this.mWiegandListener != null) {
                ZkMcuManager.this.mWiegandListener.onWiegandRead(str);
            }
        }
    };
    /* access modifiers changed from: private */
    public final AtomicBoolean mIsConnected = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public IRs232Listener mRs232Listener;
    /* access modifiers changed from: private */
    public IRs485Listener mRs485Listener;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IAidlMcuServiceInterface unused = ZkMcuManager.this.mAidlMcuServiceInterface = IAidlMcuServiceInterface.Stub.asInterface(iBinder);
            ZkMcuManager.this.mIsConnected.set(true);
            try {
                ZkMcuManager.this.addMcuListener();
                ZkMcuManager.this.addRs485Listener();
                ZkMcuManager.this.addRs232Listener();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ZkMcuManager.this.mServiceConnectionCallback != null) {
                ZkMcuManager.this.mServiceConnectionCallback.onServiceConnected(componentName);
            }
            Log.d(ZkMcuManager.TAG, "onServiceConnected");
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (ZkMcuManager.this.mIsConnected.get()) {
                ZkMcuManager.this.removeWiegandListener();
                ZkMcuManager.this.removeRs485Listener();
                ZkMcuManager.this.removeRs232Listener();
            }
            IAidlMcuServiceInterface unused = ZkMcuManager.this.mAidlMcuServiceInterface = null;
            ZkMcuManager.this.mIsConnected.set(false);
            if (ZkMcuManager.this.mServiceConnectionCallback != null) {
                ZkMcuManager.this.mServiceConnectionCallback.onServiceDisconnected(componentName);
            }
            Log.d(ZkMcuManager.TAG, "onServiceDisconnected");
        }

        public void onBindingDied(ComponentName componentName) {
            ZkMcuManager.this.mIsConnected.set(false);
            if (ZkMcuManager.this.mServiceConnectionCallback != null) {
                ZkMcuManager.this.mServiceConnectionCallback.onBindingDied(componentName);
            }
        }

        public void onNullBinding(ComponentName componentName) {
            if (ZkMcuManager.this.mServiceConnectionCallback != null) {
                ZkMcuManager.this.mServiceConnectionCallback.onNullBinding(componentName);
            }
        }
    };
    /* access modifiers changed from: private */
    public IZkServiceConnectionCallback mServiceConnectionCallback;
    /* access modifiers changed from: private */
    public IWiegandReaderListener mWiegandListener;

    public void setServiceConnectionCallback(IZkServiceConnectionCallback iZkServiceConnectionCallback) {
        this.mServiceConnectionCallback = iZkServiceConnectionCallback;
    }

    private ZkMcuManager() {
    }

    public static ZkMcuManager getInstance() {
        if (mInstance == null) {
            synchronized (ZkMcuManager.class) {
                if (mInstance == null) {
                    mInstance = new ZkMcuManager();
                }
            }
        }
        return mInstance;
    }

    public int connectService(Context context) {
        Objects.requireNonNull(context, "context cannot be null!");
        if (isConnectMcuService()) {
            return 1;
        }
        Intent intent = new Intent();
        intent.setPackage("com.zkteco.edk.mcu.service");
        intent.setAction("com.zkteco.edk.mcu.action");
        return context.getApplicationContext().bindService(intent, this.mServiceConnection, 1) ? 1 : 0;
    }

    /* access modifiers changed from: private */
    public void addMcuListener() {
        IAidlMcuServiceInterface iAidlMcuServiceInterface = this.mAidlMcuServiceInterface;
        if (iAidlMcuServiceInterface != null) {
            try {
                iAidlMcuServiceInterface.addWiegandListener(this.mAidlWiegandListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void removeWiegandListener() {
        IAidlMcuServiceInterface iAidlMcuServiceInterface = this.mAidlMcuServiceInterface;
        if (iAidlMcuServiceInterface != null) {
            try {
                iAidlMcuServiceInterface.removeWiegandListener(this.mAidlWiegandListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void addRs485Listener() {
        IAidlMcuServiceInterface iAidlMcuServiceInterface = this.mAidlMcuServiceInterface;
        if (iAidlMcuServiceInterface != null) {
            try {
                iAidlMcuServiceInterface.addRs485Listener(this.mAidlRs485Listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void removeRs485Listener() {
        IAidlMcuServiceInterface iAidlMcuServiceInterface = this.mAidlMcuServiceInterface;
        if (iAidlMcuServiceInterface != null) {
            try {
                iAidlMcuServiceInterface.removeRs485Listener(this.mAidlRs485Listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void addRs232Listener() {
        IAidlMcuServiceInterface iAidlMcuServiceInterface = this.mAidlMcuServiceInterface;
        if (iAidlMcuServiceInterface != null) {
            try {
                iAidlMcuServiceInterface.addRs232Listener(this.mAidlRs232Listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void removeRs232Listener() {
        IAidlMcuServiceInterface iAidlMcuServiceInterface = this.mAidlMcuServiceInterface;
        if (iAidlMcuServiceInterface != null) {
            try {
                iAidlMcuServiceInterface.removeRs232Listener(this.mAidlRs232Listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void disconnectService(Context context) {
        if (context == null) {
            throw new NullPointerException("context cannot be null!");
        } else if (isConnectMcuService()) {
            context.getApplicationContext().unbindService(this.mServiceConnection);
            this.mIsConnected.set(false);
        }
    }

    public void setWiegandListener(IWiegandReaderListener iWiegandReaderListener) {
        this.mWiegandListener = iWiegandReaderListener;
    }

    public void setRs485Listener(IRs485Listener iRs485Listener) {
        this.mRs485Listener = iRs485Listener;
    }

    public void setRs232Listener(IRs232Listener iRs232Listener) {
        this.mRs232Listener = iRs232Listener;
    }

    public boolean isMCUOpen() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.isMCUOpen();
        }
        throw new RemoteException("service not connect");
    }

    public String getSDKVersion() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getSDKVersion();
        }
        throw new RemoteException("service not connect");
    }

    public String getMCUVersion() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getMCUVersion();
        }
        throw new RemoteException("service not connect");
    }

    public boolean setRTCTime(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.setRTCTime(i, i2, i3, i4, i5, i6);
        }
        throw new RemoteException("service not connect");
    }

    public int[] getRTCTime() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getRTCTime();
        }
        throw new RemoteException("service not connect");
    }

    public boolean firmwareUpgrade(byte[] bArr) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.firmwareUpgrade(bArr);
        }
        throw new RemoteException("service not connect");
    }

    public int getLock1State() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getLock1State();
        }
        throw new RemoteException("service not connect");
    }

    public int getAlarmState() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getAlarmState();
        }
        throw new RemoteException("service not connect");
    }

    public int getBUT1() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getBUT1();
        }
        throw new RemoteException("service not connect");
    }

    public int getBUT2() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getBUT2();
        }
        throw new RemoteException("service not connect");
    }

    public int getAuxInput() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getAuxInput();
        }
        throw new RemoteException("service not connect");
    }

    public boolean setLock1(int i) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.setLock1(i);
        }
        throw new RemoteException("service not connect");
    }

    public boolean setAlarm(int i) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.setAlarm(i);
        }
        throw new RemoteException("service not connect");
    }

    public boolean setRS232Properties(int i, int i2, int i3, int i4) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.setRS232Properties(i, i2, i3, i4);
        }
        throw new RemoteException("service not connect");
    }

    public boolean setRS485Properties(int i, int i2, int i3, int i4) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.setRS485Properties(i, i2, i3, i4);
        }
        throw new RemoteException("service not connect");
    }

    public boolean sentRS232Data(byte[] bArr) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.sentRS232Data(bArr);
        }
        throw new RemoteException("service not connect");
    }

    public boolean sentInternalRS232Data(byte[] bArr) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.sentInternalRS232Data(bArr);
        }
        throw new RemoteException("service not connect");
    }

    public byte[] readInternalRS232Data() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.readInternalRS232Data();
        }
        throw new RemoteException("service not connect");
    }

    public boolean sentRS485Data(byte[] bArr) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.sentRS485Data(bArr);
        }
        throw new RemoteException("service not connect");
    }

    public boolean setWiegandOutProperty(int i, int i2, int i3) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.setWiegandOutProperty(i, i2, i3);
        }
        throw new RemoteException("service not connect");
    }

    public int readWiegandInData(byte[] bArr) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.readWiegandInData(bArr);
        }
        throw new RemoteException("service not connect");
    }

    public boolean sentWiegandData(byte[] bArr) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.sentWiegandData(bArr);
        }
        throw new RemoteException("service not connect");
    }

    public boolean setInfraredLED(int i) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.setInfraredLED(i);
        }
        throw new RemoteException("service not connect");
    }

    public boolean setLED(int i) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.setLED(i);
        }
        throw new RemoteException("service not connect");
    }

    public int getSensor1() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getSensor1();
        }
        throw new RemoteException("service not connect");
    }

    public boolean setUSBPower(int i) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.setUSBPower(i);
        }
        throw new RemoteException("service not connect");
    }

    public int getAlarmKey() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getAlarmKey();
        }
        throw new RemoteException("service not connect");
    }

    public int getGPIOStatus(int i) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getGPIOStatus(i);
        }
        throw new RemoteException("service not connect");
    }

    public String getSerialNumber() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getSerialNumber();
        }
        throw new RemoteException("service not connect");
    }

    public boolean setWatchDogTime(int i) throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.setWatchDogTime(i);
        }
        throw new RemoteException("service not connect");
    }

    public String getDeviceMAC() throws RemoteException {
        if (isConnectMcuService()) {
            return this.mAidlMcuServiceInterface.getDeviceMAC();
        }
        throw new RemoteException("service not connect");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0008, code lost:
        r0 = r1.mAidlMcuServiceInterface;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isConnectMcuService() {
        /*
            r1 = this;
            java.util.concurrent.atomic.AtomicBoolean r0 = r1.mIsConnected
            boolean r0 = r0.get()
            if (r0 == 0) goto L_0x0018
            com.zkteco.edk.mcu.lib.IAidlMcuServiceInterface r0 = r1.mAidlMcuServiceInterface
            if (r0 == 0) goto L_0x0018
            android.os.IBinder r0 = r0.asBinder()
            boolean r0 = r0.isBinderAlive()
            if (r0 == 0) goto L_0x0018
            r0 = 1
            goto L_0x0019
        L_0x0018:
            r0 = 0
        L_0x0019:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.edk.mcu.lib.ZkMcuManager.isConnectMcuService():boolean");
    }
}
