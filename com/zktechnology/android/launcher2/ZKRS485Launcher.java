package com.zktechnology.android.launcher2;

import android.util.Base64;
import com.zktechnology.android.launcher2.Launcher;
import com.zktechnology.android.rs485.RS485Manager;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;

public abstract class ZKRS485Launcher extends ZKWiegandLauncher {
    /* access modifiers changed from: protected */
    public void onRs485DataReceived(int i, String str) {
        if (ZKVerProcessPar.ACTION_BEAN.isVerifying() && ZKVerProcessPar.VERIFY_SOURCE_TYPE != 3) {
            RS485Manager.getInstance(this.mContext).failedCmd();
        } else if (this.mState == Launcher.State.WORKSPACE) {
            if (str != null) {
                turnOnScreen();
                ZKVerProcessPar.VERIFY_SOURCE_TYPE = 3;
                ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_RS232_VERIFY, 6);
                if (i == 1) {
                    ZKVerProcessPar.CON_MARK_BUNDLE.putByteArray(ZKVerConConst.BUNDLE_FP_TEMPLATE_BUFFER, Base64.decode(str, 0));
                    startVerify(ZKVerifyType.FINGER.getValue());
                } else if (i == 2) {
                    ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_CARD, str);
                    startVerify(ZKVerifyType.CARD.getValue());
                }
                playSoundBi();
                return;
            }
            RS485Manager.getInstance(this.mContext).failedCmd();
        }
    }
}
