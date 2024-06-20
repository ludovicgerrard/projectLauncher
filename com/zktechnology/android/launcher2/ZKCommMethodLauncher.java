package com.zktechnology.android.launcher2;

import android.os.SystemClock;
import com.zktechnology.android.launcher2.Launcher;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.bean.process.ZKMarkTypeBean;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.db.orm.util.SpeakerHelper;
import java.util.ArrayList;

public abstract class ZKCommMethodLauncher extends Launcher {
    protected static final long INTERVAL_CARD = 600;
    protected static final long INTERVAL_FINGER = 1200;
    protected boolean isDBOk = false;
    private long mCardCurrentTime = 0;
    private long mFingerCurrentTime = 0;

    /* access modifiers changed from: protected */
    public boolean isAllowVerifyCardForTime() {
        if (Math.abs(SystemClock.elapsedRealtime() - this.mCardCurrentTime) < INTERVAL_CARD) {
            return false;
        }
        this.mCardCurrentTime = SystemClock.elapsedRealtime();
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isAllowVerifyCardForState() {
        return ZKVerProcessPar.ACTION_BEAN.isBolRFidRead() && isWorkSpace();
    }

    /* access modifiers changed from: protected */
    public boolean isAllowVerifyFingerForTime() {
        if (Math.abs(SystemClock.elapsedRealtime() - this.mFingerCurrentTime) < INTERVAL_FINGER) {
            return false;
        }
        this.mFingerCurrentTime = SystemClock.elapsedRealtime();
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isAllowVerifyFingerForState() {
        return ZKVerProcessPar.ACTION_BEAN.isBolFingerprint() && isWorkSpace();
    }

    /* access modifiers changed from: protected */
    public boolean isAllowVerifyPin() {
        return ZKVerProcessPar.ACTION_BEAN.isBolKeyboard();
    }

    public /* synthetic */ void lambda$turnOnScreen$0$ZKCommMethodLauncher() {
        keyguardOperation(getWindow(), false);
    }

    /* access modifiers changed from: protected */
    public void turnOnScreen() {
        runOnUiThread(new Runnable() {
            public final void run() {
                ZKCommMethodLauncher.this.lambda$turnOnScreen$0$ZKCommMethodLauncher();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void playSoundBi() {
        SpeakerHelper.playSound(this, "beep.ogg", false, "");
    }

    /* access modifiers changed from: protected */
    public boolean isWorkSpace() {
        return this.mState == Launcher.State.WORKSPACE;
    }

    /* access modifiers changed from: protected */
    public void startVerify(int i) {
        LogUtils.e(LogUtils.TAG_VERIFY, "StartVerify-----> VState<%s> VType<%s>", Integer.valueOf(ZKVerProcessPar.CON_MARK_BEAN.getVerState()), Integer.valueOf(i));
        ZKVerProcessPar.ACTION_BEAN.setFTouchAction();
        FileLogUtils.writeTouchLog("setFTouchAction: startVerify");
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ZKMarkTypeBean(i, false));
        int verState = ZKVerProcessPar.CON_MARK_BEAN.getVerState();
        if (verState == 0) {
            ZKVerProcessPar.CON_MARK_BEAN.setVerifyTypeList(arrayList);
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_WANT);
        } else if (verState == 1 || verState == 2) {
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
            FileLogUtils.writeTouchLog("startVerify : ChangeState STATE_USER");
        }
    }
}
