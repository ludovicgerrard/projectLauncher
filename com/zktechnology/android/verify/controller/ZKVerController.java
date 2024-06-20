package com.zktechnology.android.verify.controller;

import android.os.Process;
import android.util.Log;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.server.IBaseServerImpl;
import com.zktechnology.android.verify.server.ZKAccServerImpl;
import com.zktechnology.android.verify.server.ZKVerServerImpl;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerConState;
import java.util.Random;

public class ZKVerController implements Runnable, ZKVerConConst {
    private static ZKVerController instance;
    public static String synchronizedstate;
    private final String TAG = "ZKVerController";
    private boolean isRelease = false;
    private final Object lock = new Object();
    private IBaseServerImpl mServer;
    private ZKVerConState mState = ZKVerConState.STATE_WAIT;

    public static ZKVerController getInstance() {
        if (instance == null) {
            synchronized (ZKVerController.class) {
                if (instance == null) {
                    instance = new ZKVerController();
                }
            }
        }
        return instance;
    }

    public void init(int i) {
        this.isRelease = false;
        FileLogUtils.writeTouchLog("isRelease1 " + this.isRelease);
        if (i == 0) {
            Log.i("ZKVerController", "init: ZKVerServerImpl");
            this.mServer = new ZKVerServerImpl();
        } else if (i == 1) {
            Log.i("ZKVerController", "init: ZKAccServerImpl");
            this.mServer = new ZKAccServerImpl();
        }
    }

    public void release() {
        this.isRelease = true;
        FileLogUtils.writeTouchLog("isRelease2 " + this.isRelease);
        try {
            this.lock.notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Thread.currentThread().setName("verify_ctrl_" + new Random().nextInt());
        Process.setThreadPriority(-2);
        while (!this.isRelease) {
            try {
                switch (AnonymousClass1.$SwitchMap$com$zktechnology$android$verify$utils$ZKVerConState[this.mState.ordinal()]) {
                    case 1:
                        FileLogUtils.writeVerifyLog("onWait");
                        stateWait();
                        FileLogUtils.writeTouchLog("onWait " + getInstance().getState());
                        break;
                    case 2:
                        FileLogUtils.writeVerifyLog("onWant");
                        stateWant();
                        FileLogUtils.writeTouchLog("onWant " + getInstance().getState());
                        break;
                    case 3:
                        FileLogUtils.writeVerifyLog("onUser");
                        stateUser();
                        FileLogUtils.writeTouchLog("onUser " + getInstance().getState());
                        break;
                    case 4:
                        FileLogUtils.writeVerifyLog("onWay");
                        stateWay();
                        FileLogUtils.writeTouchLog("onWay " + getInstance().getState());
                        break;
                    case 5:
                        FileLogUtils.writeVerifyLog("onAction");
                        stateAction();
                        FileLogUtils.writeTouchLog("onAction " + getInstance().getState());
                        break;
                    case 6:
                        FileLogUtils.writeVerifyLog("onRecord");
                        stateRecord();
                        FileLogUtils.writeTouchLog("onRecord " + getInstance().getState());
                        break;
                    case 7:
                        FileLogUtils.writeVerifyLog("onRemoteAuth");
                        stateRemoteAuth();
                        FileLogUtils.writeTouchLog("onRemoteAuth " + getInstance().getState());
                        break;
                    case 8:
                        FileLogUtils.writeVerifyLog("onRemoteAuthDelay");
                        stateDelay();
                        FileLogUtils.writeTouchLog("onRemoteAuthDelay " + getInstance().getState());
                        break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                FileLogUtils.writeVerifyExceptionLog(e2.toString());
                changeState(ZKVerConState.STATE_WAIT);
            }
        }
    }

    /* renamed from: com.zktechnology.android.verify.controller.ZKVerController$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zktechnology$android$verify$utils$ZKVerConState;

        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|18) */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.zktechnology.android.verify.utils.ZKVerConState[] r0 = com.zktechnology.android.verify.utils.ZKVerConState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$zktechnology$android$verify$utils$ZKVerConState = r0
                com.zktechnology.android.verify.utils.ZKVerConState r1 = com.zktechnology.android.verify.utils.ZKVerConState.STATE_WAIT     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$utils$ZKVerConState     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zktechnology.android.verify.utils.ZKVerConState r1 = com.zktechnology.android.verify.utils.ZKVerConState.STATE_WANT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$utils$ZKVerConState     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.zktechnology.android.verify.utils.ZKVerConState r1 = com.zktechnology.android.verify.utils.ZKVerConState.STATE_USER     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$utils$ZKVerConState     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.zktechnology.android.verify.utils.ZKVerConState r1 = com.zktechnology.android.verify.utils.ZKVerConState.STATE_WAY     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$utils$ZKVerConState     // Catch:{ NoSuchFieldError -> 0x003e }
                com.zktechnology.android.verify.utils.ZKVerConState r1 = com.zktechnology.android.verify.utils.ZKVerConState.STATE_ACTION     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$utils$ZKVerConState     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.zktechnology.android.verify.utils.ZKVerConState r1 = com.zktechnology.android.verify.utils.ZKVerConState.STATE_RECORD     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$utils$ZKVerConState     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.zktechnology.android.verify.utils.ZKVerConState r1 = com.zktechnology.android.verify.utils.ZKVerConState.STATE_REMOTE_AUTH     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$utils$ZKVerConState     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.zktechnology.android.verify.utils.ZKVerConState r1 = com.zktechnology.android.verify.utils.ZKVerConState.STATE_DELAY     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.controller.ZKVerController.AnonymousClass1.<clinit>():void");
        }
    }

    private void stateWait() {
        synchronized (this.lock) {
            waitForUserInteraction();
        }
    }

    private void stateWant() {
        synchronized (this.lock) {
            try {
                this.mServer.stateIntent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stateUser() {
        synchronized (this.lock) {
            try {
                this.mServer.stateUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stateWay() {
        synchronized (this.lock) {
            try {
                this.mServer.stateWay();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stateAction() {
        synchronized (this.lock) {
            try {
                this.mServer.stateAction();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stateRecord() {
        synchronized (this.lock) {
            try {
                this.mServer.stateRecord();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stateRemoteAuth() {
        synchronized (this.lock) {
            try {
                this.mServer.stateRemoteAuth();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stateDelay() {
        synchronized (this.lock) {
            try {
                this.mServer.stateDelay();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void waitForUserInteraction() {
        synchronized (this.lock) {
            try {
                FileLogUtils.writeTouchLog("lock.wait");
                synchronizedstate = "wait";
                this.lock.wait();
            } catch (InterruptedException e) {
                FileLogUtils.writeTouchLog("waitForUserInteraction InterruptedException" + e.getMessage());
                Log.e("ZKVerController", Log.getStackTraceString(e));
            }
        }
    }

    public void changeState(ZKVerConState zKVerConState) {
        LogUtils.verifyLog("变更状态 : " + zKVerConState.name());
        synchronized (this.lock) {
            this.mState = zKVerConState;
            LogUtils.d(LogUtils.TAG_VERIFY, "Controller: change state to %s", zKVerConState);
            try {
                FileLogUtils.writeTouchLog("lock.notify: " + this.mState);
                synchronizedstate = "notify";
                this.lock.notify();
            } catch (Exception e) {
                FileLogUtils.writeTouchLog("changeState Exception" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public ZKVerConState getState() {
        return this.mState;
    }

    public void resetMultiVerify() {
        IBaseServerImpl iBaseServerImpl = this.mServer;
        if (iBaseServerImpl != null) {
            iBaseServerImpl.resetMultiVerify();
        }
    }
}
