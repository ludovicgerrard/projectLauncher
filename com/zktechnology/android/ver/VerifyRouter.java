package com.zktechnology.android.ver;

import android.util.Log;
import java.util.LinkedHashMap;
import java.util.Map;

public class VerifyRouter {
    private static final String TAG = "VerifyRouter";
    private boolean hasNext = true;
    private boolean isCompare = false;
    private int mUserType = -1;
    private LinkedHashMap<Integer, Boolean> mVerifyTypeMap;
    private int next;

    public void setUserInfoVerify(int i, int i2) {
        if (-1 != this.mUserType) {
            Log.e(TAG, "user verify type has already add!");
            return;
        }
        this.mUserType = i2;
        this.mVerifyTypeMap = VerifyMap.order(i, i2);
    }

    public void setPassVerifyType(int i) {
        this.isCompare = true;
        if (this.mVerifyTypeMap.containsKey(Integer.valueOf(i))) {
            this.mVerifyTypeMap.put(Integer.valueOf(i), true);
        }
        for (Map.Entry next2 : this.mVerifyTypeMap.entrySet()) {
            if (!this.mVerifyTypeMap.containsKey(Integer.valueOf(i))) {
                this.hasNext = true;
                this.next = ((Integer) next2.getKey()).intValue();
                return;
            } else if (!((Boolean) next2.getValue()).booleanValue()) {
                this.hasNext = true;
                this.next = ((Integer) next2.getKey()).intValue();
                return;
            } else {
                this.hasNext = false;
                this.next = -1;
            }
        }
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    public int next() {
        if (this.hasNext) {
            return this.next;
        }
        return -1;
    }

    public boolean isCompareSameUser() {
        return this.isCompare;
    }

    public LinkedHashMap<Integer, Boolean> getProcessDialogOrder() {
        return this.mVerifyTypeMap;
    }
}
