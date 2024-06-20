package com.zktechnology.android.launcher2;

import com.zktechnology.android.rs232.ZKRS232EncryptManager;
import com.zktechnology.android.utils.HexUtils;
import com.zktechnology.android.utils.ZKThreadPool;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.core.interfaces.RS232EncryptProtocolListener;

public abstract class ZKRS232Launcher extends ZKRS485Launcher implements RS232EncryptProtocolListener {
    private boolean isRegister = false;

    /* access modifiers changed from: protected */
    public void initRS232() {
        ZKRS232EncryptManager.getInstance().setListener(this);
    }

    /* access modifiers changed from: protected */
    public void registerRS232() {
        synchronized (ZKRS232Launcher.class) {
            if (!this.isRegister) {
                this.isRegister = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void unregisterRS232() {
        synchronized (ZKRS232Launcher.class) {
            if (this.isRegister) {
                this.isRegister = false;
            }
        }
    }

    public void onReceiveData(final RS232EncryptProtocolListener.RS232Data rS232Data) {
        ZKThreadPool.getInstance().executeTask(new Runnable() {
            public void run() {
                ZKRS232Launcher.this.turnOnScreen();
                if (!ZKVerProcessPar.ACTION_BEAN.isVerifying() || ZKVerProcessPar.VERIFY_SOURCE_TYPE == 3) {
                    RS232EncryptProtocolListener.RS232Data rS232Data = rS232Data;
                    if (rS232Data == null) {
                        ZKRS232EncryptManager.getInstance().failedCmd();
                        return;
                    }
                    int rsType = rS232Data.getRsType();
                    if (rsType != 1) {
                        if (rsType == 2 && ZKRS232Launcher.this.isAllowVerifyCardForTime() && ZKRS232Launcher.this.isAllowVerifyCardForState()) {
                            ZKVerProcessPar.VERIFY_SOURCE_TYPE = 3;
                            ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_CARD, HexUtils.convertHexToString(HexUtils.bytes2HexString(rS232Data.getData()).replace("00", "")));
                            ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_RS232_VERIFY, ZKRS232EncryptManager.getInstance().getVerifyType());
                            ZKRS232Launcher.this.playSoundBi();
                            ZKRS232Launcher.this.startVerify(ZKVerifyType.CARD.getValue());
                        }
                    } else if (ZKRS232Launcher.this.isAllowVerifyFingerForTime() && ZKRS232Launcher.this.isAllowVerifyCardForState()) {
                        ZKVerProcessPar.VERIFY_SOURCE_TYPE = 3;
                        ZKVerProcessPar.CON_MARK_BUNDLE.putByteArray(ZKVerConConst.BUNDLE_FP_TEMPLATE_BUFFER, rS232Data.getData());
                        ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_RS232_VERIFY, ZKRS232EncryptManager.getInstance().getVerifyType());
                        ZKRS232Launcher.this.playSoundBi();
                        ZKRS232Launcher.this.startVerify(ZKVerifyType.FINGER.getValue());
                    }
                } else {
                    ZKRS232EncryptManager.getInstance().failedCmd();
                }
            }
        });
    }
}
