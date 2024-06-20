package com.zkteco.edk.system.lib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.zkteco.edk.system.lib.IZkAidlSystemInterface;
import com.zkteco.edk.system.lib.IZkAidlSystemListener;
import com.zkteco.edk.system.lib.base.ZkSystemConstants;
import com.zkteco.edk.system.lib.bean.ZkEthernetConfig;
import com.zkteco.edk.system.lib.bean.ZkShellResult;
import com.zkteco.edk.system.lib.bean.ZkWifiConfig;
import com.zkteco.edk.system.lib.listener.IZkNetworkListener;
import com.zkteco.edk.system.lib.utils.ZkNetWorkUtils;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ZkSystemManager {
    /* access modifiers changed from: private */
    public static final String TAG = "ZkSystemManager";
    private static volatile ZkSystemManager mInstance;
    /* access modifiers changed from: private */
    public IZkNetworkListener mIZkNetworkListener;
    /* access modifiers changed from: private */
    public final AtomicBoolean mIsConnectedService = new AtomicBoolean(false);
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(ZkSystemManager.TAG, "onServiceConnected");
            IZkAidlSystemInterface unused = ZkSystemManager.this.mSystemInterface = IZkAidlSystemInterface.Stub.asInterface(iBinder);
            ZkSystemManager.this.mIsConnectedService.set(true);
            try {
                ZkSystemManager.this.mSystemInterface.addSystemListener(ZkSystemManager.this.mSystemListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(ZkSystemManager.TAG, "onServiceDisconnected");
            if (ZkSystemManager.this.mSystemInterface != null) {
                try {
                    ZkSystemManager.this.mSystemInterface.removeSystemListener(ZkSystemManager.this.mSystemListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            ZkSystemManager.this.mIsConnectedService.set(false);
        }

        public void onBindingDied(ComponentName componentName) {
            Log.d(ZkSystemManager.TAG, "onBindingDied");
            if (ZkSystemManager.this.mSystemInterface != null) {
                try {
                    ZkSystemManager.this.mSystemInterface.removeSystemListener(ZkSystemManager.this.mSystemListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            ZkSystemManager.this.mIsConnectedService.set(false);
        }
    };
    /* access modifiers changed from: private */
    public volatile IZkAidlSystemInterface mSystemInterface;
    /* access modifiers changed from: private */
    public final IZkAidlSystemListener.Stub mSystemListener = new IZkAidlSystemListener.Stub() {
        public void onNetworkConnectivityChange(boolean z, int i) throws RemoteException {
            if (ZkSystemManager.this.mIZkNetworkListener != null) {
                ZkSystemManager.this.mIZkNetworkListener.onNetworkConnectivityChange(z, i);
            }
        }
    };

    public void addSystemListener(IZkNetworkListener iZkNetworkListener) {
        this.mIZkNetworkListener = iZkNetworkListener;
    }

    public void removeSystemListener() {
        this.mIZkNetworkListener = null;
    }

    public static ZkSystemManager getInstance() {
        if (mInstance == null) {
            synchronized (ZkSystemManager.class) {
                if (mInstance == null) {
                    mInstance = new ZkSystemManager();
                }
            }
        }
        return mInstance;
    }

    public int bindService(Context context) {
        if (context == null) {
            return -1004;
        }
        if (this.mIsConnectedService.get()) {
            return 0;
        }
        Intent intent = new Intent();
        intent.setPackage(ZkSystemConstants.SYSTEM_SERVICE_PACKAGE);
        intent.setAction(ZkSystemConstants.SYSTEM_SERVICE_ACTION);
        if (context.getApplicationContext().bindService(intent, this.mServiceConnection, 1)) {
            return 0;
        }
        return -1001;
    }

    public int unbindService(Context context) {
        if (context == null) {
            return -1004;
        }
        if (!isConnectService()) {
            return -1002;
        }
        try {
            this.mIZkNetworkListener = null;
            if (this.mIsConnectedService.get()) {
                if (this.mSystemInterface != null) {
                    this.mSystemInterface.removeSystemListener(this.mSystemListener);
                }
                context.getApplicationContext().unbindService(this.mServiceConnection);
                this.mSystemInterface = null;
                this.mIsConnectedService.set(false);
            }
            return 0;
        } catch (Exception unused) {
            return -1000;
        }
    }

    private boolean isConnectService() {
        String str = TAG;
        Log.d(str, "mSystemInterface==null:" + (this.mSystemInterface == null));
        Log.d(str, "mIsConnectedService.get():" + this.mIsConnectedService.get());
        if (this.mSystemInterface != null) {
            Log.d(str, "mSystemInterface.asBinder().isBinderAlive():" + this.mSystemInterface.asBinder().isBinderAlive());
        }
        if (this.mSystemInterface == null || !this.mSystemInterface.asBinder().isBinderAlive() || !this.mIsConnectedService.get()) {
            return false;
        }
        return true;
    }

    public int setEthernetEnabled(boolean z) {
        if (!isConnectService()) {
            return -1002;
        }
        try {
            return this.mSystemInterface.setEthernetEnabled(z);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public void checkEthernet() {
        if (isConnectService()) {
            try {
                this.mSystemInterface.checkEthernet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int connectEthernet(ZkEthernetConfig zkEthernetConfig) {
        if (!isConnectService()) {
            return -1002;
        }
        if (zkEthernetConfig == null) {
            return -1004;
        }
        if (!zkEthernetConfig.isDhcp() && (!ZkNetWorkUtils.isValidIPV4ByCustomRegex(zkEthernetConfig.getIpAddress()) || !ZkNetWorkUtils.isValidIPV4ByCustomRegex(zkEthernetConfig.getSubnetMask()) || !ZkNetWorkUtils.isValidIPV4ByCustomRegex(zkEthernetConfig.getGateway()) || !ZkNetWorkUtils.isValidIPV4ByCustomRegex(zkEthernetConfig.getDns1()))) {
            return -1004;
        }
        try {
            return this.mSystemInterface.connectEthernet(zkEthernetConfig);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int disconnectEthernet() {
        if (!isConnectService()) {
            return -1002;
        }
        try {
            return this.mSystemInterface.disconnectEthernet();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int getEthernetConfig(List<ZkEthernetConfig> list) {
        if (!isConnectService()) {
            return -1002;
        }
        if (list == null) {
            return -1004;
        }
        try {
            return this.mSystemInterface.getEthernetConfig(list);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int ethernetIsConnect() {
        if (!isConnectService()) {
            return -1002;
        }
        try {
            return this.mSystemInterface.ethernetIsConnect();
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int ethernetIsAvailable() {
        if (!isConnectService()) {
            return -1002;
        }
        try {
            return this.mSystemInterface.ethernetIsAvailable();
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int setAdbEnabled(boolean z) {
        if (!isConnectService()) {
            return -1002;
        }
        try {
            return this.mSystemInterface.setAdbEnabled(z);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int getAdbEnabled() {
        if (!isConnectService()) {
            return -1002;
        }
        try {
            return this.mSystemInterface.getAdbEnabled();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int setNtpServer(String str) {
        if (!isConnectService()) {
            return -1002;
        }
        if (TextUtils.isEmpty(str)) {
            return -1004;
        }
        try {
            return this.mSystemInterface.setNtpServer(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int getNtpServer(String[] strArr) {
        if (!isConnectService()) {
            return -1002;
        }
        if (strArr == null || strArr.length < 1) {
            return -1004;
        }
        try {
            return this.mSystemInterface.getNtpServer(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int setScreenBrightnessMode(int i) {
        if (!isConnectService()) {
            return -1002;
        }
        if (i != 0 && i != 1) {
            return -1004;
        }
        try {
            return this.mSystemInterface.setScreenBrightnessMode(i);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int getScreenBrightnessMode(int[] iArr) {
        if (!isConnectService()) {
            return -1002;
        }
        if (iArr == null || iArr.length < 1) {
            return -1004;
        }
        try {
            return this.mSystemInterface.getScreenBrightnessMode(iArr);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int setScreenBrightness(int i) {
        if (!isConnectService()) {
            return -1002;
        }
        if (i < 0 || i > 255) {
            return -1004;
        }
        try {
            return this.mSystemInterface.setScreenBrightness(i);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int getScreenBrightness(int[] iArr) {
        if (!isConnectService()) {
            return -1002;
        }
        if (iArr == null || iArr.length < 1) {
            return -1004;
        }
        try {
            return this.mSystemInterface.getScreenBrightness(iArr);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int checkProcessesIsExist(String str) {
        if (!isConnectService()) {
            return -1002;
        }
        if (TextUtils.isEmpty(str)) {
            return -1004;
        }
        try {
            return this.mSystemInterface.checkProcessesIsExist(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int stopProcesses(String str) {
        if (!isConnectService()) {
            return -1002;
        }
        if (TextUtils.isEmpty(str)) {
            return -1004;
        }
        try {
            return this.mSystemInterface.stopProcesses(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int setWiFitEnabled(boolean z) {
        if (!isConnectService()) {
            return -1002;
        }
        try {
            return this.mSystemInterface.setWiFiEnabled(z);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int isWifiConnect() {
        if (!isConnectService()) {
            return -1002;
        }
        try {
            return this.mSystemInterface.isWifiConnect();
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int scanWifi(List<ZkWifiConfig> list) {
        if (!isConnectService()) {
            return -1002;
        }
        if (list == null) {
            return -1004;
        }
        try {
            return this.mSystemInterface.scanWifi(list);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int connectWifi(String str, String str2, int i) {
        if (!isConnectService()) {
            return -1002;
        }
        if (TextUtils.isEmpty(str)) {
            return -1004;
        }
        try {
            return this.mSystemInterface.connectWifi(str, str2, i);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int forgetWifi(String str) {
        if (!isConnectService()) {
            return -1002;
        }
        if (TextUtils.isEmpty(str)) {
            return -1004;
        }
        try {
            return this.mSystemInterface.forgetWifi(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }

    public int executeCMD(String str, List<ZkShellResult> list) {
        if (!isConnectService()) {
            return -1002;
        }
        if (TextUtils.isEmpty(str) || list == null) {
            return -1004;
        }
        try {
            return this.mSystemInterface.executeCMD(str, list);
        } catch (Exception e) {
            e.printStackTrace();
            return -1003;
        }
    }
}
