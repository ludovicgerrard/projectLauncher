package com.zktechnology.android.att;

import android.content.Context;
import com.zktechnology.android.att.AttInterceptors.AttAntiSubmarineInterceptor;
import com.zktechnology.android.att.AttInterceptors.AttContinuousstateInterceptor;
import com.zktechnology.android.att.AttInterceptors.AttDuressAlarmInterceptor;
import com.zktechnology.android.att.AttInterceptors.AttEffectiveTimePeriodInterceptor;
import com.zktechnology.android.att.AttInterceptors.AttMaskInterceptor;
import com.zktechnology.android.att.AttInterceptors.AttMultiCardOpenDoorInterceptor;
import com.zktechnology.android.att.AttInterceptors.AttSuperPassWordInterceptor;
import com.zktechnology.android.att.AttInterceptors.AttTemperatureInterceptor;
import com.zktechnology.android.att.types.AttAlarmType;
import com.zktechnology.android.att.types.AttDoorType;
import com.zktechnology.android.verify.dao.ZKVerDao;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.db.orm.tna.UserInfo;

public class DoorAttChecker {
    public static AttResponse check(Context context, int i, ZKVerDao zKVerDao, UserInfo userInfo, boolean z, boolean z2) {
        AttRequest attRequest = new AttRequest();
        attRequest.setContext(context);
        attRequest.setUserInfo(userInfo);
        attRequest.setZkVerDao(zKVerDao);
        attRequest.setVerifyStateMulti(z);
        attRequest.setCurrentTypy(i);
        AttResponse attResponse = new AttResponse();
        attResponse.setCanProceed(true);
        attResponse.setErrorMessage((String) null);
        attResponse.setEventCode(0);
        attResponse.setAttAlarmType(AttAlarmType.NONE);
        attResponse.setAttDoorType(AttDoorType.NONE);
        attResponse.setOpenDoor(true);
        attResponse.setTemperature(-1.0d);
        attResponse.setTempHigh(false);
        attResponse.setWearMask(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WEAR_MASK));
        AttInterceptorChain attInterceptorChain = new AttInterceptorChain();
        if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 5) {
            attInterceptorChain.addInterceptors(new AttAntiSubmarineInterceptor());
            attInterceptorChain.addInterceptors(new AttContinuousstateInterceptor());
            attInterceptorChain.addInterceptors(new AttMultiCardOpenDoorInterceptor());
            attInterceptorChain.addInterceptors(new AttEffectiveTimePeriodInterceptor());
        } else if (z2) {
            attInterceptorChain.addInterceptors(new AttSuperPassWordInterceptor());
            attInterceptorChain.addInterceptors(new AttTemperatureInterceptor());
            attInterceptorChain.addInterceptors(new AttMaskInterceptor());
        } else {
            attInterceptorChain.addInterceptors(new AttSuperPassWordInterceptor());
            attInterceptorChain.addInterceptors(new AttDuressAlarmInterceptor());
            attInterceptorChain.addInterceptors(new AttAntiSubmarineInterceptor());
            attInterceptorChain.addInterceptors(new AttContinuousstateInterceptor());
            attInterceptorChain.addInterceptors(new AttMultiCardOpenDoorInterceptor());
            attInterceptorChain.addInterceptors(new AttEffectiveTimePeriodInterceptor());
            attInterceptorChain.addInterceptors(new AttTemperatureInterceptor());
            attInterceptorChain.addInterceptors(new AttMaskInterceptor());
        }
        attInterceptorChain.interceptor(attRequest, attResponse);
        return attResponse;
    }
}
