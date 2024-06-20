package com.zktechnology.android.launcher2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.PersBiotemplatedata;
import com.zkteco.biometric.ZKBioTemplateService;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmService12;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ZKPalmLauncher extends ZkFaceLauncher {
    public static final String ACTION_PALM = "com.zkteco.android.palm";
    public static final String path = "/sdcard/zkpalmveinlic.txt";
    public static final String path2 = "/sdcard/zkpalmveinlic.dat";
    /* access modifiers changed from: private */
    public final String TAG = ZKPalmLauncher.class.getSimpleName();
    /* access modifiers changed from: private */
    public ExecutorService mSingleService = Executors.newSingleThreadExecutor();
    BroadcastReceiver palmReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(ZKPalmLauncher.this.TAG, "palmReceiver: " + intent.getAction());
            if (ZKPalmLauncher.ACTION_PALM.equals(intent.getAction())) {
                String stringExtra = intent.getStringExtra("userpin");
                String stringExtra2 = intent.getStringExtra("type");
                Log.d(ZKPalmLauncher.this.TAG, "palmReceiver userpin: " + stringExtra);
                Log.d(ZKPalmLauncher.this.TAG, "palmReceiver type: " + stringExtra2);
                ZKPalmLauncher.this.mSingleService.submit(new OperationPalmTask(stringExtra, stringExtra2));
            }
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initReceiver();
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_PALM);
        registerReceiver(this.palmReceiver, intentFilter);
    }

    class OperationPalmTask implements Runnable {
        String type;
        String userpin;

        public OperationPalmTask(String str, String str2) {
            this.userpin = str;
            this.type = str2;
        }

        public void run() {
            if (!TextUtils.isEmpty(this.userpin)) {
                Log.d(ZKPalmLauncher.this.TAG, "retdel: " + ZKPalmService12.dbDel(this.userpin));
                if ("add".equals(this.type)) {
                    byte[][] bArr = (byte[][]) Array.newInstance(byte.class, new int[]{5, 2048});
                    byte[] bArr2 = new byte[ZKPalmService12.FIX_REG_TEMPLATE_LEN];
                    for (int i = 0; i < 5; i++) {
                        try {
                            PersBiotemplate persBiotemplate = (PersBiotemplate) new PersBiotemplate().getQueryBuilder().where().eq("user_pin", this.userpin).and().eq("template_no_index", Integer.valueOf(i)).queryForFirst();
                            if (persBiotemplate != null) {
                                bArr[i] = ((PersBiotemplatedata) new PersBiotemplatedata().queryForId(Long.valueOf((long) persBiotemplate.getTemplate_id()))).getTemplate_data();
                                Log.d(ZKPalmLauncher.this.TAG, "pertemp.getUser_pin(): " + persBiotemplate.getUser_pin());
                            }
                        } catch (SQLException e) {
                            Log.d(ZKPalmLauncher.this.TAG, "SQLException: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    int mergePalmTemplate = ZKBioTemplateService.mergePalmTemplate(bArr, bArr2);
                    if (mergePalmTemplate == 0) {
                        Log.d(ZKPalmLauncher.this.TAG, "retadd: " + ZKPalmService12.dbAdd(this.userpin, bArr2));
                        return;
                    }
                    Log.d(ZKPalmLauncher.this.TAG, "mergePalmTemplate failed: " + mergePalmTemplate);
                }
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            BroadcastReceiver broadcastReceiver = this.palmReceiver;
            if (broadcastReceiver != null) {
                unregisterReceiver(broadcastReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
