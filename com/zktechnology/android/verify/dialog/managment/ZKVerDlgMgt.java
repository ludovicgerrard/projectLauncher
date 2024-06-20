package com.zktechnology.android.verify.dialog.managment;

import android.os.Bundle;
import com.zktechnology.android.launcher2.ZKEventLauncher;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.verify.bean.process.ZKVerViewBean;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import java.util.ArrayList;

public class ZKVerDlgMgt {
    public static void upDateTopUi(ZKVerViewBean zKVerViewBean) {
        push(zKVerViewBean);
    }

    public static void push(ZKVerViewBean zKVerViewBean) {
        if (ZKVerProcessPar.VIEW_BEAN_LIST.size() < 3) {
            ZKVerProcessPar.VIEW_BEAN_LIST.add(zKVerViewBean);
            ZKEventLauncher.setProcessDialogVisibility(true);
            return;
        }
        ZKVerProcessPar.VIEW_BEAN_LIST.clear();
        ZKEventLauncher.setProcessDialogVisibility(false);
        ZKVerProcessPar.cleanData(2);
    }

    public static void pop() {
        int size = ZKVerProcessPar.VIEW_BEAN_LIST.size();
        if (size == 1) {
            ZKVerProcessPar.VIEW_BEAN_LIST.remove(0);
            ZKEventLauncher.setProcessDialogVisibility(false);
            ZKVerProcessPar.cleanData(3);
        } else if (size == 2) {
            ZKVerProcessPar.VIEW_BEAN_LIST.remove(1);
            ZKEventLauncher.setProcessDialogVisibility(true);
            ZKVerProcessPar.ACTION_BEAN.setFTouchAction();
            FileLogUtils.writeTouchLog("setFTouchAction: pop2");
            ZKVerProcessPar.CON_MARK_BEAN.setVerifyTypeList(new ArrayList());
            ZKVerProcessPar.CON_MARK_BUNDLE = new Bundle();
        }
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }
}
