package com.zktechnology.android.wiegand;

import android.content.Context;
import android.text.TextUtils;
import com.zktechnology.android.helper.McuServiceHelper;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.wiegand.bean.WiegandOutData;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.zkcore.wiegand.WiegandLogUtils;
import com.zkteco.android.zkcore.wiegand.WiegandOptionsManager;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandEnum;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandFailedID;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandSiteCode;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZKWiegandManager {
    private static ZKWiegandManager instance;
    private Context context;
    private int deviceID;
    private int failedID;
    private boolean init = false;
    /* access modifiers changed from: private */
    public boolean isCan = false;
    private boolean isOpenIn;
    /* access modifiers changed from: private */
    public boolean isOpenOut;
    /* access modifiers changed from: private */
    public boolean isSetPro = false;
    private int pulseIntervalOut;
    private int pulseWidthOut;
    private int siteCode;
    private WiegandEnum wiegandCardBitIn;
    private WiegandEnum wiegandCardBitOut;
    private WiegandOptionsManager wiegandOptionsManager;
    private ExecutorService wiegandOutService = Executors.newCachedThreadPool();
    private WiegandType wiegandTypeIn;
    /* access modifiers changed from: private */
    public WiegandType wiegandTypeOut;

    public static void newInstance(Context context2) {
        if (instance == null) {
            synchronized (ZKWiegandManager.class) {
                if (instance == null) {
                    instance = new ZKWiegandManager(context2);
                }
            }
        }
    }

    public int getSiteCodeOrDeviceID() {
        if (this.siteCode < WiegandSiteCode.MIN.getValue()) {
            return this.deviceID;
        }
        return this.siteCode;
    }

    public static ZKWiegandManager getInstance() {
        ZKWiegandManager zKWiegandManager = instance;
        if (zKWiegandManager != null) {
            return zKWiegandManager;
        }
        throw new RuntimeException("wiegand--ZKError:please invoke newInstance(Context application) first!");
    }

    private ZKWiegandManager(Context context2) {
        this.context = context2;
    }

    public void init() {
        if (!this.init) {
            this.wiegandOptionsManager = new WiegandOptionsManager(this.context);
            refresh();
            setWgOutProperty();
            this.init = true;
        }
    }

    public void setWgOutProperty() {
        if (this.init && this.isOpenOut) {
            int intValue = Integer.valueOf(this.wiegandCardBitOut.getValue()).intValue();
            int i = 0;
            while (i < 3) {
                boolean wiegandOutProperty = McuServiceHelper.getInstance().setWiegandOutProperty(intValue, this.pulseWidthOut, this.pulseIntervalOut);
                WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, "setWgOutProperty: flag is " + wiegandOutProperty);
                if (wiegandOutProperty) {
                    this.isSetPro = true;
                    i = 3;
                } else {
                    i++;
                    WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, "setWgOutProperty 失败次数" + i);
                }
            }
        }
    }

    public void refresh() {
        if (this.init) {
            this.isOpenOut = this.wiegandOptionsManager.isOpenOut();
            this.wiegandCardBitOut = this.wiegandOptionsManager.getBit4Out();
            this.failedID = this.wiegandOptionsManager.getFailedID();
            this.siteCode = this.wiegandOptionsManager.getSiteCode() == WiegandSiteCode.DEF.getValue() ? 1 : this.wiegandOptionsManager.getSiteCode();
            this.pulseWidthOut = this.wiegandOptionsManager.getPulseWidth4Out();
            this.pulseIntervalOut = this.wiegandOptionsManager.getPulseInterval4Out();
            this.wiegandTypeOut = this.wiegandOptionsManager.getType4Out();
            this.isOpenIn = this.wiegandOptionsManager.isOpenIn();
            this.wiegandCardBitIn = this.wiegandOptionsManager.getBit4In();
            this.wiegandTypeIn = this.wiegandOptionsManager.getType4In();
            this.isCan = !this.wiegandOptionsManager.isABC() || this.wiegandOptionsManager.isRFCardOn();
            this.deviceID = this.wiegandOptionsManager.getDeviceID();
            WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, "isOpenOut:%b,wiegandCardBitOut:%s,failedID:%d,siteCode:%d,pulseWidthOut:%d,pulseIntervalOut:%d,wiegandTypeOut:%d,isOpenIn:%b,wiegandCardBitIn:%s,wiegandTypeIn:%d,isCan:%b,deviceID:%b", Boolean.valueOf(this.isOpenOut), this.wiegandCardBitOut.getValue(), Integer.valueOf(this.failedID), Integer.valueOf(this.siteCode), Integer.valueOf(this.pulseWidthOut), Integer.valueOf(this.pulseIntervalOut), Integer.valueOf(this.wiegandTypeOut.getValue()), Boolean.valueOf(this.isOpenIn), this.wiegandCardBitIn.getValue(), Integer.valueOf(this.wiegandTypeIn.getValue()), Boolean.valueOf(this.isCan), Integer.valueOf(this.deviceID));
        }
    }

    public boolean isOpenIn() {
        return this.isOpenIn;
    }

    public boolean isCan() {
        return this.isCan;
    }

    public WiegandType getWiegandTypeIn() {
        return this.wiegandTypeIn;
    }

    public WiegandEnum getWiegandCardBitIn() {
        return this.wiegandCardBitIn;
    }

    public void wiegandOutUserInfo(UserInfo userInfo) {
        if (this.init) {
            WiegandOutRunnable wiegandOutRunnable = new WiegandOutRunnable();
            wiegandOutRunnable.setUserInfo(userInfo);
            this.wiegandOutService.submit(wiegandOutRunnable);
        }
    }

    public void wiegandOutFailedID() {
        if (this.init) {
            WiegandOutRunnableFailedID wiegandOutRunnableFailedID = new WiegandOutRunnableFailedID();
            refresh();
            wiegandOutRunnableFailedID.setFailedID((long) this.failedID);
            this.wiegandOutService.submit(wiegandOutRunnableFailedID);
        }
    }

    public class WiegandOutRunnableFailedID implements Runnable {
        private long failedID = ((long) WiegandFailedID.DEF.getValue());

        public WiegandOutRunnableFailedID() {
        }

        public void setFailedID(long j) {
            this.failedID = j;
        }

        public void run() {
            Thread.currentThread().setName("wiegand_out_failed");
            if (ZKWiegandManager.this.isOpenOut && ZKWiegandManager.this.isSetPro && ZKWiegandManager.this.isCan) {
                ZKWiegandManager.this.sendWiegandOut(this.failedID);
            }
        }
    }

    public class WiegandOutRunnable implements Runnable {
        private UserInfo userInfo;

        public WiegandOutRunnable() {
        }

        public void setUserInfo(UserInfo userInfo2) {
            this.userInfo = userInfo2;
        }

        public void run() {
            try {
                Thread.currentThread().setName("wigand_o_user");
                if (ZKWiegandManager.this.isOpenOut) {
                    if (ZKWiegandManager.this.isSetPro) {
                        if (!ZKWiegandManager.this.isCan) {
                            WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, " ! isCan return");
                            FileLogUtils.writeWiegandLog(" ! isCan return");
                            return;
                        } else if (this.userInfo == null) {
                            WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, " userInfo == null return ");
                            FileLogUtils.writeWiegandLog(" userInfo == null return ");
                            return;
                        } else {
                            String str = "";
                            int i = AnonymousClass1.$SwitchMap$com$zkteco$android$zkcore$wiegand$enmutype$WiegandType[ZKWiegandManager.this.wiegandTypeOut.ordinal()];
                            if (i == 1) {
                                str = this.userInfo.getMain_Card();
                            } else if (i == 2) {
                                str = this.userInfo.getUser_PIN();
                            }
                            long j = -1;
                            if (!TextUtils.isEmpty(str) && WiegandUtil.isNumeric(str)) {
                                try {
                                    j = Long.valueOf(str).longValue();
                                } catch (Exception e) {
                                    WiegandLogUtils.getInstance().e(WiegandUtil.WG_TAG, e.getMessage());
                                }
                            }
                            ZKWiegandManager.this.sendWiegandOut(j);
                            return;
                        }
                    }
                }
                WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, " !isOpenOut || !isSetPro  return ");
                FileLogUtils.writeWiegandLog(" !isOpenOut || !isSetPro  return ");
            } catch (Exception e2) {
                e2.printStackTrace();
                WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, e2.toString());
                FileLogUtils.writeWiegandLog(e2.toString());
            }
        }
    }

    /* renamed from: com.zktechnology.android.wiegand.ZKWiegandManager$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zkteco$android$zkcore$wiegand$enmutype$WiegandType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        static {
            /*
                com.zkteco.android.zkcore.wiegand.enmutype.WiegandType[] r0 = com.zkteco.android.zkcore.wiegand.enmutype.WiegandType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$zkteco$android$zkcore$wiegand$enmutype$WiegandType = r0
                com.zkteco.android.zkcore.wiegand.enmutype.WiegandType r1 = com.zkteco.android.zkcore.wiegand.enmutype.WiegandType.CARD_NUM     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$zkteco$android$zkcore$wiegand$enmutype$WiegandType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zkteco.android.zkcore.wiegand.enmutype.WiegandType r1 = com.zkteco.android.zkcore.wiegand.enmutype.WiegandType.PIN     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.wiegand.ZKWiegandManager.AnonymousClass1.<clinit>():void");
        }
    }

    /* access modifiers changed from: private */
    public void sendWiegandOut(long j) {
        if (this.init) {
            try {
                WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, "wiegandOut###numCode = " + j);
                FileLogUtils.writeWiegandLog("wiegandOut###numCode = " + j);
                if (j != -1) {
                    WiegandOutData wiegandOutData = new WiegandOutData();
                    wiegandOutData.setCardBit(this.wiegandCardBitOut.getValue());
                    wiegandOutData.setNumCode(j);
                    wiegandOutData.setSiteCode(getSiteCodeOrDeviceID());
                    byte[] wiegandBinaryData = wiegandOutData.getWiegandBinaryData();
                    if (wiegandBinaryData == null) {
                        WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, "b_data == null ");
                        FileLogUtils.writeWiegandLog("b_data == null ");
                        return;
                    }
                    boolean sentWiegandData = McuServiceHelper.getInstance().sentWiegandData(wiegandBinaryData);
                    WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, "wiegandOut###sendWg: flag is " + sentWiegandData);
                    FileLogUtils.writeWiegandLog("wiegandOut###sendWg: flag is " + sentWiegandData);
                }
            } catch (Exception e) {
                e.printStackTrace();
                WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, e.toString());
                FileLogUtils.writeWiegandLog(e.toString());
            }
        }
    }
}
