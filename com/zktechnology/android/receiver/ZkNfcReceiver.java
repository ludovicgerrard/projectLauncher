package com.zktechnology.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.HexUtils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ZkNfcReceiver extends BroadcastReceiver {
    public static final String ACTION_NFC_CARD = "com.zkteco.nfc.ACTION_CARD_READ";
    public static final String EXTRA_KEY = "extra_key";

    public void onReceive(Context context, Intent intent) {
        Tag tag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
        if (tag != null) {
            Flowable.just(tag.getId()).map($$Lambda$ZkNfcReceiver$rBN7ZNC9iLIvF_zRXLjl74qsQi8.INSTANCE).map($$Lambda$ZkNfcReceiver$YQdellMkdbc1tZHZ2ENTVazkfeM.INSTANCE).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer(context) {
                public final /* synthetic */ Context f$0;

                {
                    this.f$0 = r1;
                }

                public final void accept(Object obj) {
                    this.f$0.sendBroadcast(new Intent(ZkNfcReceiver.ACTION_NFC_CARD).putExtra(ZkNfcReceiver.EXTRA_KEY, (String) obj));
                }
            });
        }
    }

    static /* synthetic */ String lambda$onReceive$0(byte[] bArr) throws Exception {
        if (DBManager.getInstance().getIntOption("~CardByteRevert", 0) == 1) {
            HexUtils.reserveByte(bArr);
        }
        return HexUtils.bytes2HexString(bArr);
    }

    static /* synthetic */ String lambda$onReceive$1(String str) throws Exception {
        try {
            return String.valueOf(Long.parseLong(str, 16));
        } catch (NumberFormatException unused) {
            return str;
        }
    }
}
