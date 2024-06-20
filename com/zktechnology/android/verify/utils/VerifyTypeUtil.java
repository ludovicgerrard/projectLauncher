package com.zktechnology.android.verify.utils;

import com.zktechnology.android.verify.bean.process.ZKVerifyType;

public class VerifyTypeUtil {
    public static int getDoorVerifyType(ZKVerifyType zKVerifyType, int i) {
        if (zKVerifyType == null) {
            return i;
        }
        if (i != 0 && i != 5 && i != 6 && i != 7) {
            return i;
        }
        if (zKVerifyType == ZKVerifyType.FACE) {
            return 15;
        }
        if (zKVerifyType == ZKVerifyType.PALM) {
            return 25;
        }
        return zKVerifyType.getValue();
    }
}
