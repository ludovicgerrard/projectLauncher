package com.zktechnology.android.helper;

import android.content.Context;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zkteco.edk.card.lib.ICardReaderListener;
import com.zkteco.edk.card.lib.ZkCardManager;
import java.util.ArrayList;
import java.util.List;

public class CardServiceHelper {
    private static final String TAG = "CardServiceHelper";
    private final Context mContext;
    private List<OnCardReadListener> mListeners;

    static class SingletonHolder {
        static final CardServiceHelper INSTANCE = new CardServiceHelper();

        SingletonHolder() {
        }
    }

    private CardServiceHelper() {
        this.mContext = LauncherApplication.getLauncherApplicationContext().getApplicationContext();
    }

    public static CardServiceHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0068  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void init() {
        /*
            r9 = this;
            java.lang.String r0 = "CardServiceHelper"
            java.lang.String r1 = "initCard: [start init card]"
            android.util.Log.d(r0, r1)
            com.zkteco.edk.card.lib.ZkCardManager r1 = com.zkteco.edk.card.lib.ZkCardManager.getInstance()
            com.zktechnology.android.helper.-$$Lambda$CardServiceHelper$wKifpDyRjfVrdGz1oIO7R8gSuww r2 = new com.zktechnology.android.helper.-$$Lambda$CardServiceHelper$wKifpDyRjfVrdGz1oIO7R8gSuww
            r2.<init>()
            r1.setCardReaderListener(r2)
            com.zkteco.edk.card.lib.ZkCardManager r1 = com.zkteco.edk.card.lib.ZkCardManager.getInstance()
            android.content.Context r2 = r9.mContext
            int r1 = r1.bindService(r2)
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.Integer r4 = java.lang.Integer.valueOf(r1)
            r5 = 0
            r3[r5] = r4
            java.lang.String r4 = "initCard: [bindService result %d]"
            java.lang.String r3 = java.lang.String.format(r4, r3)
            android.util.Log.d(r0, r3)
            java.lang.String r3 = "nfc"
            java.lang.String r4 = "~RFCardOn"
            if (r1 == 0) goto L_0x004e
            android.content.Context r0 = r9.mContext
            java.lang.Object r0 = r0.getSystemService(r3)
            android.nfc.NfcManager r0 = (android.nfc.NfcManager) r0
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()
            android.nfc.NfcAdapter r0 = r0.getDefaultAdapter()
            if (r0 == 0) goto L_0x0049
            goto L_0x004a
        L_0x0049:
            r2 = r5
        L_0x004a:
            r1.setIntOption(r4, r2)
            return
        L_0x004e:
            r6 = 3000(0xbb8, double:1.482E-320)
            android.os.SystemClock.sleep(r6)
            r1 = 3
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
        L_0x0059:
            com.zkteco.edk.card.lib.ZkCardManager r7 = com.zkteco.edk.card.lib.ZkCardManager.getInstance()
            r7.queryOnlineDevice(r6)
            int r1 = r1 + -1
            boolean r7 = r6.isEmpty()
            if (r7 == 0) goto L_0x006d
            r7 = 2000(0x7d0, double:9.88E-321)
            android.os.SystemClock.sleep(r7)
        L_0x006d:
            if (r1 <= 0) goto L_0x0075
            boolean r7 = r6.isEmpty()
            if (r7 != 0) goto L_0x0059
        L_0x0075:
            boolean r1 = r6.isEmpty()
            if (r1 != 0) goto L_0x0094
            java.lang.Object[] r1 = new java.lang.Object[r2]
            java.lang.Object r3 = r6.get(r5)
            r1[r5] = r3
            java.lang.String r3 = "Load card module: [%s]"
            java.lang.String r1 = java.lang.String.format(r3, r1)
            android.util.Log.i(r0, r1)
            com.zkteco.android.db.orm.manager.DataManager r0 = com.zktechnology.android.utils.DBManager.getInstance()
            r0.setIntOption(r4, r2)
            goto L_0x00ab
        L_0x0094:
            android.content.Context r0 = r9.mContext
            java.lang.Object r0 = r0.getSystemService(r3)
            android.nfc.NfcManager r0 = (android.nfc.NfcManager) r0
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()
            android.nfc.NfcAdapter r0 = r0.getDefaultAdapter()
            if (r0 == 0) goto L_0x00a7
            goto L_0x00a8
        L_0x00a7:
            r2 = r5
        L_0x00a8:
            r1.setIntOption(r4, r2)
        L_0x00ab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.helper.CardServiceHelper.init():void");
    }

    public /* synthetic */ void lambda$init$0$CardServiceHelper(String str) {
        List<OnCardReadListener> list = this.mListeners;
        if (list != null) {
            for (OnCardReadListener onCardRead : list) {
                onCardRead.onCardRead(str);
            }
        }
    }

    public final void addOnCardReadListener(OnCardReadListener onCardReadListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(onCardReadListener);
    }

    public final void removeOnCardReadListener(OnCardReadListener onCardReadListener) {
        List<OnCardReadListener> list = this.mListeners;
        if (list != null) {
            list.remove(onCardReadListener);
        }
    }

    public final void disconnect() {
        ZkCardManager.getInstance().unbindService(this.mContext);
        ZkCardManager.getInstance().setCardReaderListener((ICardReaderListener) null);
    }
}
