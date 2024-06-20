package com.zktechnology.android.verify.utils;

import android.os.Bundle;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.verify.bean.process.ZKTouchActionBean;
import com.zktechnology.android.verify.bean.process.ZKVerConMarkBean;
import com.zktechnology.android.verify.bean.process.ZKVerViewBean;
import java.util.ArrayList;
import java.util.List;

public class ZKVerProcessPar {
    public static ZKTouchActionBean ACTION_BEAN = new ZKTouchActionBean();
    public static ZKVerConMarkBean CON_MARK_BEAN = new ZKVerConMarkBean();
    public static Bundle CON_MARK_BUNDLE = new Bundle();
    public static boolean KEY_BOARD_1V1 = false;
    public static boolean OTHER_FLAG = false;
    public static int VERIFY_SOURCE_TYPE = 1;
    public static List<ZKVerViewBean> VIEW_BEAN_LIST = new ArrayList();

    public static void cleanBtnWidget() {
    }

    public static void cleanData(int i) {
        ACTION_BEAN = new ZKTouchActionBean();
        CON_MARK_BEAN = new ZKVerConMarkBean();
        CON_MARK_BUNDLE = new Bundle();
        FileLogUtils.writeVerifyLog("cleanData: CON_MARK_BUNDLE " + i);
        KEY_BOARD_1V1 = false;
        OTHER_FLAG = false;
        VERIFY_SOURCE_TYPE = 1;
    }

    public static void cleanView() {
        VIEW_BEAN_LIST = new ArrayList();
    }

    public static ZKVerViewBean getLastViewBean() {
        if (VIEW_BEAN_LIST.size() <= 0) {
            return null;
        }
        List<ZKVerViewBean> list = VIEW_BEAN_LIST;
        return list.get(list.size() - 1);
    }
}
