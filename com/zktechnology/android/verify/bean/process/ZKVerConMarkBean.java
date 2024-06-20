package com.zktechnology.android.verify.bean.process;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ZKVerConMarkBean implements Serializable {
    public static final int INTENT_OPEN_APP = 2;
    public static final int INTENT_OPEN_MENU = 1;
    public static final int INTENT_URGENT_PASSWORD = 6;
    public static final int INTENT_USER_RECORD = 4;
    public static final int INTENT_VERIFY = 0;
    public static final int INTENT_WIEGAND = 5;
    public static final int INTENT_WORK_CODE = 3;
    public static final int VERIFY_STATE_FIRST_TIME = 0;
    public static final int VERIFY_STATE_MULTI = 2;
    public static final int VERIFY_STATE_USER = 1;
    private static final long serialVersionUID = -897451649865L;
    private boolean isSuperCrack;
    private int mIntent;
    private String verAttAction;
    private boolean verBRepeatTime;
    private boolean verBWorkCode;
    private int verState;
    private List<ZKMarkTypeBean> verifyTypeList = new ArrayList();

    public boolean isSuperCrack() {
        return this.isSuperCrack;
    }

    public void setSuperCrack(boolean z) {
        this.isSuperCrack = z;
    }

    public ZKVerConMarkBean() {
    }

    public ZKVerConMarkBean(int i) {
        this.mIntent = i;
    }

    public ZKVerConMarkBean(int i, List<ZKMarkTypeBean> list) {
        this.mIntent = i;
        this.verifyTypeList = list;
    }

    public ZKVerConMarkBean(int i, List<ZKMarkTypeBean> list, boolean z) {
        this.mIntent = i;
        this.verifyTypeList = list;
        this.verBWorkCode = z;
    }

    public ZKVerConMarkBean(int i, List<ZKMarkTypeBean> list, boolean z, int i2) {
        this.mIntent = i;
        this.verifyTypeList = list;
        this.verBWorkCode = z;
        this.verState = i2;
    }

    public int getIntent() {
        return this.mIntent;
    }

    public void setIntent(int i) {
        this.mIntent = i;
    }

    public List<ZKMarkTypeBean> getVerifyTypeList() {
        return this.verifyTypeList;
    }

    public void setVerifyTypeList(List<ZKMarkTypeBean> list) {
        this.verifyTypeList = list;
    }

    public boolean isVerBWorkCode() {
        return this.verBWorkCode;
    }

    public void setVerBWorkCode(boolean z) {
        this.verBWorkCode = z;
    }

    public int getVerState() {
        return this.verState;
    }

    public void setVerState(int i) {
        this.verState = i;
    }

    public boolean isVerBRepeatTime() {
        return this.verBRepeatTime;
    }

    public void setVerBRepeatTime(boolean z) {
        this.verBRepeatTime = z;
    }

    public String getVerAttAction() {
        return this.verAttAction;
    }

    public void setVerAttAction(String str) {
        this.verAttAction = str;
    }

    public ZKMarkTypeBean getLastType() {
        if (this.verifyTypeList.size() <= 0) {
            return null;
        }
        List<ZKMarkTypeBean> list = this.verifyTypeList;
        return list.get(list.size() - 1);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<ZKMarkTypeBean> list = this.verifyTypeList;
        if (list != null && list.size() > 1) {
            int size = this.verifyTypeList.size();
            for (int i = 0; i < size; i++) {
                sb.append(this.verifyTypeList.get(i).getType());
            }
        }
        return String.format("ZKVerConMarkBean: VerState<%s>,VerUse<%s>,VerTypes<%s>,LastVerType<%s>,AttAction<%s>,isVerBRepeatTime<%s> isVerBWorkCode<%s>", new Object[]{Integer.valueOf(this.verState), Integer.valueOf(this.mIntent), sb.toString(), Integer.valueOf(getLastType().getType()), this.verAttAction, Boolean.valueOf(this.verBRepeatTime), Boolean.valueOf(this.verBWorkCode)});
    }
}
