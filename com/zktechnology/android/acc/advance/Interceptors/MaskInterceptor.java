package com.zktechnology.android.acc.advance.Interceptors;

import android.content.Intent;
import android.os.SystemClock;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.launcher2.ZkFaceLauncher;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import java.util.Date;

public class MaskInterceptor extends BaseInterceptor implements Interceptor {
    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        int i;
        if (ZKLauncher.maskDetectionFunOn == 1 && ZKLauncher.enalbeMaskDetection == 1) {
            if (doorAccessResponse.getWearMask() == 0) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Intent intent = new Intent();
                intent.setAction(ZkFaceLauncher.SHOW_MASK_SOUND);
                doorAccessRequest.getContext().sendBroadcast(intent);
                do {
                    long abs = Math.abs(elapsedRealtime - SystemClock.elapsedRealtime());
                    i = ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WEAR_MASK);
                    if (abs > 5000) {
                        break;
                    }
                } while (i <= 0);
                doorAccessResponse.setWearMask(i);
            }
            if (ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WEAR_MASK) == 0) {
                String string = doorAccessRequest.getContext().getResources().getString(R.string.get_temperature_failed);
                doorAccessResponse.setWearMask(0);
                processResponse(string, 1001, doorAccessRequest, doorAccessResponse, date);
            } else if (ZKLauncher.enableUnregisterPass == 0 && doorAccessRequest.getUserInfo() == null) {
                processResponse(doorAccessRequest.getContext().getResources().getString(R.string.dlg_ver_process_fa_title), 27, doorAccessRequest, doorAccessResponse, date);
            } else if (ZKLauncher.enableWearMaskPass == 1 && doorAccessResponse.getWearMask() == 1) {
                processResponse(doorAccessRequest.getContext().getResources().getString(R.string.without_mask), 69, doorAccessRequest, doorAccessResponse, date);
            }
        } else {
            doorAccessResponse.setWearMask(0);
        }
    }
}
