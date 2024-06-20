package com.zktechnology.android.verify.utils;

import android.content.Context;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.contants.BiometricCommuCMD;
import com.zkteco.android.db.orm.tna.AccGroup;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.sql.SQLException;

public class ZKVerOption {
    public int getVerifyType(Context context) {
        int verify_Type;
        AccGroup accGroup;
        int i = ZKVerProcessPar.VERIFY_SOURCE_TYPE;
        int i2 = 0;
        int intOption = DBManager.getInstance().getIntOption("WiegandMethod", 0);
        int i3 = ZKLauncher.sAccessRuleType;
        if (i3 == 0) {
            int i4 = ZKLauncher.sLockFunOn;
            if (i4 == 1) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            return 0;
                        }
                        return ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_RS232_VERIFY, 0);
                    } else if (intOption == 0) {
                        return ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WIEGAND_VERIFY, 0);
                    }
                }
                UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
                if (userInfo == null) {
                    return 0;
                }
                verify_Type = userInfo.getVerify_Type();
            } else if (i4 != 15) {
                return 0;
            } else {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            return 0;
                        }
                        return ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_RS232_VERIFY, 0);
                    } else if (intOption == 0) {
                        return ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WIEGAND_VERIFY, 0);
                    }
                }
                UserInfo userInfo2 = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
                if (userInfo2 != null) {
                    i2 = userInfo2.getVerify_Type();
                }
                if (i2 != 100) {
                    return i2;
                }
                try {
                    if (userInfo2.getAcc_Group_ID() <= 0 || (accGroup = (AccGroup) new AccGroup().getQueryBuilder().where().eq(BiometricCommuCMD.FIELD_DESC_TMP_ID, Integer.valueOf(userInfo2.getAcc_Group_ID())).queryForFirst()) == null) {
                        return i2;
                    }
                    verify_Type = accGroup.getVerification();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return i2;
                }
            }
            return verify_Type;
        } else if (i3 != 1 || ZKLauncher.sLockFunOn != 15) {
            return 0;
        } else {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return 0;
                    }
                    return ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_RS232_VERIFY, 0);
                } else if (intOption == 0) {
                    return ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WIEGAND_VERIFY, 0);
                }
            }
            UserInfo userInfo3 = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
            int verify_Type2 = (userInfo3 == null || DBManager.getInstance().getIntOption("AccessPersonalVerification", 0) != 1) ? -1 : userInfo3.getVerify_Type();
            if (verify_Type2 == -1) {
                return ZKLauncher.sDoor1VerifyType;
            }
            return verify_Type2;
        }
    }
}
