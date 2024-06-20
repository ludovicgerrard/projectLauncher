package com.zkteco.edk.card.lib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.zkteco.edk.card.lib.IAidlCardInterface;
import com.zkteco.edk.card.lib.IAidlCardListener;
import com.zkteco.edk.card.lib.IAidlDeviceStateListener;
import com.zkteco.edk.common.device.IZkDeviceStateListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ZkCardManager {
    private static final String EDK_SERVICE_APPLICATION_NAME = "com.zkteco.edk.card.service";
    private static final String EDK_SERVICE_NAME = "com.zkteco.edk.card.service.ZkCardService";
    private static volatile ZkCardManager mInstance;
    /* access modifiers changed from: private */
    public final IAidlCardListener mAidlCardListener = new IAidlCardListener.Stub() {
        public void onReadCard(String str) throws RemoteException {
            if (ZkCardManager.this.mCardListener != null) {
                ZkCardManager.this.mCardListener.onCardRead(str);
            }
        }
    };
    /* access modifiers changed from: private */
    public final IAidlDeviceStateListener mAidlDeviceStateListener = new IAidlDeviceStateListener.Stub() {
        public void onDeviceStateChange(int i, String str) throws RemoteException {
            Log.i("EDK-CARD-LIB", String.format("%s device state: %s", new Object[]{str, Integer.valueOf(i)}));
            if (ZkCardManager.this.mDeviceStateListener != null) {
                ZkCardManager.this.mDeviceStateListener.onDeviceStateChange(i, str);
            }
        }
    };
    /* access modifiers changed from: private */
    public volatile IAidlCardInterface mCardInterface;
    /* access modifiers changed from: private */
    public ICardReaderListener mCardListener;
    /* access modifiers changed from: private */
    public IZkDeviceStateListener mDeviceStateListener;
    /* access modifiers changed from: private */
    public final AtomicBoolean mIsConnectServiceSuccess = new AtomicBoolean(false);
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IAidlCardInterface unused = ZkCardManager.this.mCardInterface = IAidlCardInterface.Stub.asInterface(iBinder);
            try {
                ZkCardManager.this.mCardInterface.addCardListener(ZkCardManager.this.mAidlCardListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                ZkCardManager.this.mCardInterface.addDeviceStateListener(ZkCardManager.this.mAidlDeviceStateListener);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            try {
                ZkCardManager.this.mCardInterface.removeCardListener(ZkCardManager.this.mAidlCardListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                ZkCardManager.this.mCardInterface.removeDeviceStateListener(ZkCardManager.this.mAidlDeviceStateListener);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            IAidlCardInterface unused = ZkCardManager.this.mCardInterface = null;
        }

        public void onBindingDied(ComponentName componentName) {
            IAidlCardInterface unused = ZkCardManager.this.mCardInterface = null;
            ZkCardManager.this.mIsConnectServiceSuccess.set(false);
        }
    };

    private ZkCardManager() {
    }

    public void setCardReaderListener(ICardReaderListener iCardReaderListener) {
        this.mCardListener = iCardReaderListener;
    }

    public void setCardReaderStateListener(IZkDeviceStateListener iZkDeviceStateListener) {
        this.mDeviceStateListener = iZkDeviceStateListener;
    }

    public static ZkCardManager getInstance() {
        if (mInstance == null) {
            synchronized (ZkCardManager.class) {
                if (mInstance == null) {
                    mInstance = new ZkCardManager();
                }
            }
        }
        return mInstance;
    }

    public int bindService(Context context) {
        if (context == null) {
            return -1004;
        }
        if (this.mIsConnectServiceSuccess.get()) {
            return 0;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(EDK_SERVICE_APPLICATION_NAME, EDK_SERVICE_NAME));
        boolean bindService = context.getApplicationContext().bindService(intent, this.mServiceConnection, 1);
        this.mIsConnectServiceSuccess.set(bindService);
        if (bindService) {
            return 0;
        }
        return -1001;
    }

    public int unbindService(Context context) {
        if (!this.mIsConnectServiceSuccess.get()) {
            return 0;
        }
        if (context == null) {
            return -1004;
        }
        context.getApplicationContext().unbindService(this.mServiceConnection);
        this.mIsConnectServiceSuccess.set(false);
        return 0;
    }

    public int setRevertCardNumber(boolean z) {
        if (!isConnectService()) {
            return -1001;
        }
        try {
            this.mCardInterface.setCardRule(ZkCardRuleName.CARD_REVERT, z ? "1" : "0");
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1001;
        }
    }

    public int getRevertCardNumber(boolean[] zArr) {
        if (!isConnectService()) {
            return -1001;
        }
        if (zArr == null || zArr.length < 1) {
            return -1004;
        }
        try {
            String[] strArr = new String[1];
            if (this.mCardInterface.getCardRule(ZkCardRuleName.CARD_REVERT, strArr) == 0) {
                zArr[0] = "1".equals(strArr[0]);
                return 0;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1001;
    }

    public int queryOnlineDevice(List<String> list) {
        if (!isConnectService()) {
            return -1001;
        }
        if (list == null) {
            return -1004;
        }
        try {
            this.mCardInterface.queryOnlineDevice(list);
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1001;
        }
    }

    public int loadCardDevice() {
        if (!isConnectService()) {
            return -1001;
        }
        try {
            this.mCardInterface.loadCardDevice();
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1001;
        }
    }

    private boolean isConnectService() {
        return this.mCardInterface != null && this.mCardInterface.asBinder().isBinderAlive() && this.mIsConnectServiceSuccess.get();
    }

    public int isInit() {
        if (!isConnectService()) {
            return -1001;
        }
        try {
            return this.mCardInterface.isInit();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public byte[] sendCMD(String str) {
        if (!isConnectService() || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return this.mCardInterface.sendCMD(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int setCardRule(String str, String str2) {
        if (!isConnectService()) {
            return -1001;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return -1004;
        }
        try {
            return this.mCardInterface.setCardRule(str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1001;
        }
    }

    public int getCardRule(String str, String[] strArr) {
        if (!isConnectService()) {
            return -1001;
        }
        if (TextUtils.isEmpty(str) || strArr == null || strArr.length <= 0) {
            return -1004;
        }
        try {
            return this.mCardInterface.getCardRule(str, strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1001;
        }
    }
}
