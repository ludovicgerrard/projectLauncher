package com.zktechnology.android.att.AttInterceptors;

import android.text.TextUtils;
import com.zktechnology.android.att.AttRequest;
import com.zktechnology.android.att.AttResponse;
import com.zktechnology.android.att.types.AttAlarmType;
import com.zktechnology.android.att.types.AttDoorType;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;

public class AttSuperPassWordInterceptor extends BaseAttInterceptor implements AttInterceptor {
    public void interceptor(AttRequest attRequest, AttResponse attResponse) {
        if (attRequest.getCurrentTypy() == ZKVerifyType.URGENTPASSWORD.getValue()) {
            String string = ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_PASSWORD);
            String strOption = DBManager.getInstance().getStrOption("Door1SupperPassWord", "");
            if (!strOption.isEmpty() && strOption.equals(string) && string.length() == 8) {
                setAttResponse(attResponse, false, (String) null, 4, AttAlarmType.NONE, AttDoorType.SUPERPASSWORDOPENDOOR, true);
            } else if (TextUtils.isEmpty(string) || string.length() != 8) {
                setAttResponse(attResponse, false, attRequest.getContext().getResources().getString(R.string.please_enter_urgent_password_length8), 30, AttAlarmType.NONE, AttDoorType.NONE, false);
            } else {
                setAttResponse(attResponse, false, attRequest.getContext().getResources().getString(R.string.please_enter_urgent_password), 30, AttAlarmType.NONE, AttDoorType.NONE, false);
            }
        }
    }
}
