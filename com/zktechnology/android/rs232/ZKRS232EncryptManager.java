package com.zktechnology.android.rs232;

import android.content.Context;
import android.util.Log;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zkteco.android.core.interfaces.RS232EncryptProtocolListener;
import com.zkteco.android.core.sdk.RS232EncryptManager;
import com.zkteco.android.zkcore.read.ReadOptionsManager;

public class ZKRS232EncryptManager {
    private static ZKRS232EncryptManager instance;
    private Context context;
    private boolean init = false;
    private RS232EncryptManager mRs232EncryptManager;
    private ReadOptionsManager readOptionsManager;
    private int verifyType = 6;

    public static void newInstance(Context context2) {
        if (instance == null) {
            synchronized (ZKRS232EncryptManager.class) {
                if (instance == null) {
                    instance = new ZKRS232EncryptManager(context2);
                }
            }
        }
    }

    private ZKRS232EncryptManager(Context context2) {
        this.context = context2;
        this.mRs232EncryptManager = new RS232EncryptManager(context2);
        this.readOptionsManager = new ReadOptionsManager(context2);
    }

    public static ZKRS232EncryptManager getInstance() {
        ZKRS232EncryptManager zKRS232EncryptManager = instance;
        if (zKRS232EncryptManager != null) {
            return zKRS232EncryptManager;
        }
        throw new RuntimeException("rs232--ZKError:please invoke newInstance(Context application) first!");
    }

    public void init() {
        if (!this.init) {
            this.init = true;
            refreshData();
            this.mRs232EncryptManager.setRS232ParameterTwo(9600, 8, 1, 0);
            this.mRs232EncryptManager.setRS232ParameterThree(115200, 8, 1, 0);
            setVerifyType();
            this.mRs232EncryptManager.rs232Start();
        }
    }

    public int getVerifyType() {
        return this.verifyType;
    }

    public void refreshData() {
        this.verifyType = this.readOptionsManager.rs485VerifyType();
    }

    public void setVerifyType() {
        this.mRs232EncryptManager.set157VerifyType((byte) this.verifyType);
    }

    public void setListener(RS232EncryptProtocolListener rS232EncryptProtocolListener) {
        this.mRs232EncryptManager.setListener(rS232EncryptProtocolListener);
    }

    public void register() {
        try {
            RS232EncryptManager rS232EncryptManager = this.mRs232EncryptManager;
            if (rS232EncryptManager != null) {
                rS232EncryptManager.register();
                Log.i(ZKLauncher.TAG, "Rs232EncryptManager register");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregister() {
        try {
            RS232EncryptManager rS232EncryptManager = this.mRs232EncryptManager;
            if (rS232EncryptManager != null) {
                rS232EncryptManager.unregister();
                Log.i(ZKLauncher.TAG, "Rs232EncryptManager unregister");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void failedCmd() {
        this.mRs232EncryptManager.verifyResult((byte) -2);
    }

    public void successCmd() {
        this.mRs232EncryptManager.verifyResult((byte) 0);
    }

    public void noPermission() {
        this.mRs232EncryptManager.verifyResult((byte) -1);
    }

    public void continueVerify() {
        this.mRs232EncryptManager.verifyResult((byte) -3);
    }
}
