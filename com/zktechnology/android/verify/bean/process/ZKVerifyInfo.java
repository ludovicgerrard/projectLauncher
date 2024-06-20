package com.zktechnology.android.verify.bean.process;

import com.zktechnology.android.utils.LogUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;

public class ZKVerifyInfo implements Serializable {
    private Hashtable<Integer, ArrayList<String>> groupUserList;
    private long lastMultipleVerifyTime;
    private ArrayList<ZKVerifyHistory> verifyHistories;
    private String verifyInput;
    private ZKVerifyType verifyType;

    public Hashtable<Integer, ArrayList<String>> getGroupUserList() {
        if (this.groupUserList == null) {
            this.groupUserList = new Hashtable<>();
            LogUtils.verifyLog("create new group user list");
        }
        return this.groupUserList;
    }

    public void setGroupUserList(Hashtable<Integer, ArrayList<String>> hashtable) {
        if (hashtable == null) {
            LogUtils.verifyLog("clear group user list");
        }
        this.groupUserList = hashtable;
    }

    public long getLastMultipleVerifyTime() {
        return this.lastMultipleVerifyTime;
    }

    public void setLastMultipleVerifyTime(long j) {
        this.lastMultipleVerifyTime = j;
    }

    public void setVerifyType(ZKVerifyType zKVerifyType) {
        this.verifyType = zKVerifyType;
    }

    public ZKVerifyType getVerifyType() {
        return this.verifyType;
    }

    public String getVerifyInput() {
        return this.verifyInput;
    }

    public void setVerifyInput(String str) {
        this.verifyInput = str;
    }

    public ArrayList<ZKVerifyHistory> getVerifyHistories() {
        if (this.verifyHistories == null) {
            this.verifyHistories = new ArrayList<>();
        }
        return this.verifyHistories;
    }

    public void setVerifyHistories(ArrayList<ZKVerifyHistory> arrayList) {
        this.verifyHistories = arrayList;
    }

    public String toString() {
        return "ZKVerifyInfo{verifyType=" + this.verifyType + ", verifyInput='" + this.verifyInput + '\'' + ", verifyHistories=" + getVerifyHistoriesString() + ", lastMultipleVerifyTime=" + this.lastMultipleVerifyTime + ", groupUserList=" + getGroupUserListString() + '}';
    }

    private String getVerifyHistoriesString() {
        if (this.verifyHistories == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        Iterator<ZKVerifyHistory> it = this.verifyHistories.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString()).append("\n");
        }
        return sb.toString();
    }

    private String getGroupUserListString() {
        if (this.groupUserList == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Integer next : this.groupUserList.keySet()) {
            ArrayList arrayList = this.groupUserList.get(next);
            if (arrayList != null) {
                sb.append("groupId:").append(next).append("\t").append(Arrays.toString(new ArrayList[]{arrayList})).append("\n");
            }
        }
        return sb.toString();
    }
}
