package com.zkteco.android.zkcore.read;

import android.content.Context;
import android.content.Intent;
import com.zkteco.android.db.orm.manager.DataManager;

public class ReadOptionsManager {
    private Context context;
    private DataManager dataManager = new DataManager();

    public ReadOptionsManager(Context context2) {
        this.context = context2;
    }

    public int rs485VerifyType() {
        this.dataManager.open(this.context);
        return this.dataManager.getIntOption(ReadConfig.READ_VERIFY_TYPE, 6);
    }

    public void setRS485VerifyType(int i) {
        this.dataManager.open(this.context);
        this.dataManager.setIntOption(ReadConfig.READ_VERIFY_TYPE, i);
        sendBroadcast(ReadConfig.ACTION_CHANGE_READ_VERIFY_TYPE);
    }

    private void sendBroadcast(String str) {
        Intent intent = new Intent();
        intent.setAction(str);
        this.context.sendBroadcast(intent);
    }
}
