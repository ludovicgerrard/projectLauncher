package com.zktechnology.android.face;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.utils.GlobalConfig;
import com.zkteco.android.zkcore.utils.ZKSharedUtil;
import com.zkteco.liveface562.ZkFaceManager;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;

public class AlgorithmActivateReceiver extends BroadcastReceiver {
    public static final String ACTIVATE_ALGORITHM_ACTION = "com.zktechnology.android.zkliveface56.activation";
    public static final String ACTIVATE_ALGORITHM_ONLINE_ACTION = "com.zktechnology.android.zkliveface56.activation.online";
    public static final String ACTIVATE_DEVICEFINGERPRINT_ACTION = "com.zktechnology.android.zkliveface56.getDeviceFingerPrintAndMkdir";
    private static final String FACE_FINGERPRINT_FILE_PATH = (Environment.getExternalStorageDirectory() + File.separator + "zklivefacedevfp.txt");

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            if (ACTIVATE_ALGORITHM_ACTION.equals(action)) {
                Flowable.just(Integer.valueOf(ZkFaceManager.getInstance().activateTheFaceAlgorithm())).map($$Lambda$AlgorithmActivateReceiver$maX1wyd1LD8W3Bh1iC1g7Q7bkaQ.INSTANCE).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer(context) {
                    public final /* synthetic */ Context f$0;

                    {
                        this.f$0 = r1;
                    }

                    public final void accept(Object obj) {
                        Toast.makeText(this.f$0, String.valueOf((Integer) obj), 0).show();
                    }
                });
            } else if (ACTIVATE_DEVICEFINGERPRINT_ACTION.equals(action)) {
                Flowable.just(Integer.valueOf(ZkFaceManager.getInstance().generateFaceAlgorithmFile())).map($$Lambda$AlgorithmActivateReceiver$Me7ECaQI4QieoX3yquIh37Bx6l4.INSTANCE).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer(context) {
                    public final /* synthetic */ Context f$0;

                    {
                        this.f$0 = r1;
                    }

                    public final void accept(Object obj) {
                        Toast.makeText(this.f$0, (String) obj, 0).show();
                    }
                });
            }
        }
    }

    static /* synthetic */ Integer lambda$onReceive$0(Integer num) throws Exception {
        new ZKSharedUtil(LauncherApplication.getLauncherApplicationContext()).putBoolean(GlobalConfig.IS_FACE_AUTHORIZED, ZkFaceManager.getInstance().isAuthorized() == 0);
        return num;
    }

    static /* synthetic */ String lambda$onReceive$2(Integer num) throws Exception {
        return num.intValue() == 0 ? "Please go to" + FACE_FINGERPRINT_FILE_PATH + " download the file and request the license！" : "Get device fingerprint failed";
    }
}
