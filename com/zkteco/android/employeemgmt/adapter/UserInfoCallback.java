package com.zkteco.android.employeemgmt.adapter;

import android.util.Pair;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.util.List;

public interface UserInfoCallback {
    void callback(List<Pair<Long, UserInfo>> list);

    void callback2(List<Pair<Long, UserInfo>> list, int i);
}
