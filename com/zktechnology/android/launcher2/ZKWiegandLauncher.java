package com.zktechnology.android.launcher2;

import android.os.Bundle;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.ZKThreadPool;
import com.zktechnology.android.verify.bean.process.ZKMarkTypeBean;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zktechnology.android.wiegand.WiegandUtil;
import com.zktechnology.android.wiegand.ZKWiegandManager;
import com.zktechnology.android.wiegand.bean.WiegandInData;
import com.zkteco.android.core.interfaces.WiegandListener;
import com.zkteco.android.core.interfaces.WiegandReceiver;
import com.zkteco.android.zkcore.wiegand.WiegandLogUtils;
import java.util.ArrayList;

public abstract class ZKWiegandLauncher extends ZKPushConnectStatusLauncher {
    private WiegandReceiver wiegandReceiver;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        WiegandReceiver wiegandReceiver2 = new WiegandReceiver();
        this.wiegandReceiver = wiegandReceiver2;
        wiegandReceiver2.setListener(new WiegandListener() {
            public final void onWiegandIn(String str) {
                ZKWiegandLauncher.this.onWiegandIn(str);
            }
        });
        this.wiegandReceiver.register(this);
    }

    public void onDestroy() {
        super.onDestroy();
        this.wiegandReceiver.unregister(this);
        this.wiegandReceiver = null;
    }

    /* access modifiers changed from: protected */
    public void onWiegandIn(String str) {
        ZKThreadPool.getInstance().executeTask(new Runnable(str) {
            public final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                ZKWiegandLauncher.this.lambda$onWiegandIn$0$ZKWiegandLauncher(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$onWiegandIn$0$ZKWiegandLauncher(String str) {
        boolean z = DBManager.getInstance().getIntOption("HasSupportABCPin", 0) == 1;
        boolean z2 = DBManager.getInstance().getIntOption("~RFCardOn", 0) == 0;
        if (!z || !z2) {
            if (DBManager.getInstance().getIntOption("WiegandMethod", 0) == 0) {
                if (ZKVerProcessPar.ACTION_BEAN.isVerifying() || !isWorkSpace()) {
                    WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, "wiegandIn###isVerifying = true");
                    return;
                }
            } else if (!ZKVerProcessPar.ACTION_BEAN.isBolKeyboard() && !ZKVerProcessPar.ACTION_BEAN.isBolRFidRead()) {
                return;
            }
            if (!ZKWiegandManager.getInstance().isOpenIn()) {
                WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, "wiegandIn###isOpenIn = false");
                return;
            }
            WiegandInData wiegandInData = getWiegandInData(str);
            if (wiegandInData == null) {
                WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, "wiegandIn###data = null");
            } else if (wiegandInData.getNumCode() < 0) {
                WiegandLogUtils.getInstance().d(WiegandUtil.WG_TAG, "wiegandIn###data.getNumCode() < 0");
            } else {
                ZKVerProcessPar.ACTION_BEAN.setFTouchAction();
                FileLogUtils.writeTouchLog("setFTouchAction: onWiegandIn");
                ZKVerProcessPar.CON_MARK_BEAN.setIntent(5);
                ArrayList arrayList = new ArrayList();
                ZKMarkTypeBean zKMarkTypeBean = new ZKMarkTypeBean();
                ZKVerProcessPar.VERIFY_SOURCE_TYPE = 2;
                int i = AnonymousClass1.$SwitchMap$com$zkteco$android$zkcore$wiegand$enmutype$WiegandType[wiegandInData.getType().ordinal()];
                if (i == 1) {
                    zKMarkTypeBean.setType(ZKVerifyType.CARD.getValue());
                    ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_CARD, String.valueOf(wiegandInData.getNumCode()));
                    ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_WIEGAND_VERIFY, 4);
                } else if (i == 2) {
                    zKMarkTypeBean.setType(ZKVerifyType.PIN.getValue());
                    ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_PIN, String.valueOf(wiegandInData.getNumCode()));
                    ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_WIEGAND_VERIFY, 2);
                }
                arrayList.add(zKMarkTypeBean);
                int verState = ZKVerProcessPar.CON_MARK_BEAN.getVerState();
                if (verState == 0) {
                    ZKVerProcessPar.CON_MARK_BEAN.setVerifyTypeList(arrayList);
                    ZKVerController.getInstance().changeState(ZKVerConState.STATE_WANT);
                } else if (verState == 1 || verState == 2) {
                    ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
                }
            }
        }
    }

    /* renamed from: com.zktechnology.android.launcher2.ZKWiegandLauncher$1  reason: invalid class name */
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
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.launcher2.ZKWiegandLauncher.AnonymousClass1.<clinit>():void");
        }
    }

    private WiegandInData getWiegandInData(String str) {
        if (str.length() <= 8) {
            return null;
        }
        byte[] binStrToByteArr4Long = WiegandUtil.binStrToByteArr4Long(str);
        WiegandInData wiegandInData = new WiegandInData();
        wiegandInData.setType(ZKWiegandManager.getInstance().getWiegandTypeIn());
        wiegandInData.setCardBit(ZKWiegandManager.getInstance().getWiegandCardBitIn().getValue());
        wiegandInData.setData(binStrToByteArr4Long);
        wiegandInData.getWiegandData();
        LogUtils.d(WiegandUtil.WG_TAG, "manufactureCode:%d,siteCodeOrDeviceID:%d,facilityCode:%d,numCode:%d", Integer.valueOf(wiegandInData.getManufactureCode()), Integer.valueOf(wiegandInData.getSiteCodeOrDeviceID()), Integer.valueOf(wiegandInData.getFacilityCode()), Long.valueOf(wiegandInData.getNumCode()));
        if (wiegandInData.getNumCode() < 0 || !wiegandInData.isCan()) {
            return null;
        }
        return wiegandInData;
    }
}
