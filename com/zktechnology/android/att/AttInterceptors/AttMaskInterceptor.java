package com.zktechnology.android.att.AttInterceptors;

import android.content.Intent;
import android.os.SystemClock;
import com.zktechnology.android.att.AttRequest;
import com.zktechnology.android.att.AttResponse;
import com.zktechnology.android.att.types.AttAlarmType;
import com.zktechnology.android.att.types.AttDoorType;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.launcher2.ZkFaceLauncher;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;

public class AttMaskInterceptor extends BaseAttInterceptor implements AttInterceptor {
    public void interceptor(AttRequest attRequest, AttResponse attResponse) {
        int i;
        if (ZKLauncher.maskDetectionFunOn == 1 && ZKLauncher.enalbeMaskDetection == 1) {
            if (attResponse.getWearMask() == 0) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Intent intent = new Intent();
                intent.setAction(ZkFaceLauncher.SHOW_MASK_SOUND);
                attRequest.getContext().sendBroadcast(intent);
                do {
                    long abs = Math.abs(elapsedRealtime - SystemClock.elapsedRealtime());
                    i = ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WEAR_MASK);
                    if (abs > 5000) {
                        break;
                    }
                } while (i <= 0);
                attResponse.setWearMask(i);
            }
            if (ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WEAR_MASK) == 0) {
                String string = attRequest.getContext().getResources().getString(R.string.get_temperature_failed);
                attResponse.setWearMask(0);
                setAttResponse(attResponse, false, string, 1001, AttAlarmType.NONE, AttDoorType.NONE, false);
            } else if (ZKLauncher.enableUnregisterPass == 0 && attRequest.getUserInfo() == null) {
                setAttResponse(attResponse, false, attRequest.getContext().getResources().getString(R.string.dlg_ver_process_fa_title), 27, AttAlarmType.NONE, AttDoorType.NONE, false);
            } else if (ZKLauncher.enableWearMaskPass == 1 && attResponse.getWearMask() == 1) {
                setAttResponse(attResponse, false, attRequest.getContext().getResources().getString(R.string.without_mask), 69, AttAlarmType.NONE, AttDoorType.NONE, false);
            }
        } else {
            attResponse.setWearMask(0);
        }
    }
}
