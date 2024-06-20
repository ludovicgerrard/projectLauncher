package com.zktechnology.android.att.AttInterceptors;

import com.zktechnology.android.acc.DoorAccessDirection;
import com.zktechnology.android.acc.DoorAntiSubmarineType;
import com.zktechnology.android.att.AttParameter;
import com.zktechnology.android.att.AttRequest;
import com.zktechnology.android.att.AttResponse;
import com.zktechnology.android.att.types.AttAlarmType;
import com.zktechnology.android.att.types.AttDoorType;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.db.orm.contants.BiometricCommuCMD;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.AttLog;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.sql.SQLException;

public class AttAntiSubmarineInterceptor extends BaseAttInterceptor implements AttInterceptor {
    public void interceptor(AttRequest attRequest, AttResponse attResponse) {
        UserInfo userInfo;
        DataManager instance = DBManager.getInstance();
        if (AttParameter.getInstance().isAPBFO() && (userInfo = attRequest.getUserInfo()) != null && isAntiSubmarine(userInfo.getUser_PIN(), instance)) {
            setAttResponse(attResponse, false, attRequest.getContext().getResources().getString(R.string.illegal_entry), 24, AttAlarmType.NONE, AttDoorType.NONE, false);
        }
    }

    public boolean isAntiSubmarine(String str, DataManager dataManager) {
        try {
            DoorAntiSubmarineType fromInteger = DoorAntiSubmarineType.fromInteger(dataManager.getIntOption(ZKDBConfig.ANTI_PASSBACK_TYPE, 0));
            if (fromInteger == DoorAntiSubmarineType.NONE) {
                return false;
            }
            AttLog attLog = (AttLog) new AttLog().getQueryBuilder().orderBy(BiometricCommuCMD.FIELD_DESC_TMP_ID, false).where().eq("User_PIN", str).queryForFirst();
            if (attLog == null) {
                return false;
            }
            int intOption = dataManager.getIntOption(ZKDBConfig.DOO1_ACCESS_DIRECTION, 0);
            if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 5) {
                intOption = intOption == 1 ? 0 : 1;
            }
            DoorAccessDirection fromInteger2 = DoorAccessDirection.fromInteger(intOption);
            int status = attLog.getStatus();
            if (status == 255) {
                return false;
            }
            DoorAccessDirection fromInteger3 = DoorAccessDirection.fromInteger(status);
            if (!(fromInteger == DoorAntiSubmarineType.EXIT && fromInteger2 == DoorAccessDirection.EXIT && fromInteger3 == DoorAccessDirection.EXIT)) {
                if (fromInteger != DoorAntiSubmarineType.ENTER || fromInteger2 != DoorAccessDirection.ENTER || fromInteger3 != DoorAccessDirection.ENTER) {
                    if (!(fromInteger == DoorAntiSubmarineType.EXIT_ENTER && fromInteger2 == fromInteger3)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
