package com.zktechnology.android.face;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.zktechnology.android.launcher2.ZKExtractLauncher;
import com.zktechnology.android.utils.ZKThreadPool;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.PersBiotemplatedata;
import com.zkteco.biometric.ZKBioTemplateService;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmService12;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

public class UpdateFaceTemplateReceiver extends BroadcastReceiver {
    private static final String OPERATE_ADD = "add";
    private static final String OPERATE_CLEAR = "clear";
    private static final String OPERATE_DELETE = "delete";
    private static final String OPERATE_UPDATE = "update";
    public static final String TAG = "UpdateFaceTemplate";
    public static final String UPDATE_EMPLOYEE = "com.zkteco.android.employeemgmt.action.UPDATE_FACE_TEMPLATE";

    public void onReceive(Context context, Intent intent) {
        if (!TextUtils.isEmpty(intent.getAction()) && "com.zkteco.android.employeemgmt.action.UPDATE_FACE_TEMPLATE".equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra("userPin");
            String stringExtra2 = intent.getStringExtra("operate");
            Log.i("LiveFace", "onReceive:  operate user,userPin=" + stringExtra + ",operate=" + stringExtra2);
            operateFaceBioTemplate2DB(context, stringExtra, stringExtra2);
        }
    }

    private void operateFaceBioTemplate2DB(Context context, String str, String str2) {
        ZKThreadPool.getInstance().executeTask(new Runnable(str, str2) {
            public final /* synthetic */ String f$1;
            public final /* synthetic */ String f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                UpdateFaceTemplateReceiver.this.lambda$operateFaceBioTemplate2DB$0$UpdateFaceTemplateReceiver(this.f$1, this.f$2);
            }
        });
    }

    public /* synthetic */ void lambda$operateFaceBioTemplate2DB$0$UpdateFaceTemplateReceiver(String str, String str2) {
        ZKExtractLauncher.pauseExtract();
        Thread.currentThread().setName("operate_face_temp");
        if (str == null) {
            setDbPalm();
        } else if (str2.equals(OPERATE_ADD) || str2.equals(OPERATE_UPDATE)) {
            FaceDBUtils.updateTemplateByUserPin(str);
        } else if (str2.equals(OPERATE_DELETE)) {
            FaceDBUtils.deleteUserTemplate(str);
        } else if (str2.equals(OPERATE_CLEAR)) {
            FaceDBUtils.clearBiotemplateData();
        }
        ZKExtractLauncher.resumeExtract();
    }

    public void setDbPalm() {
        try {
            ZKPalmService12.dbClear();
            List<PersBiotemplate> query = new PersBiotemplate().getQueryBuilder().where().eq("bio_type", 8).query();
            HashMap hashMap = new HashMap();
            for (PersBiotemplate persBiotemplate : query) {
                int[] iArr = hashMap.containsKey(persBiotemplate.getUser_pin()) ? (int[]) hashMap.get(persBiotemplate.getUser_pin()) : new int[5];
                if (iArr != null && iArr.length > 0) {
                    iArr[persBiotemplate.getTemplate_no_index()] = persBiotemplate.getTemplate_id();
                    hashMap.put(persBiotemplate.getUser_pin(), iArr);
                }
            }
            for (String str : hashMap.keySet()) {
                int[] iArr2 = new int[2];
                iArr2[1] = 2048;
                iArr2[0] = 5;
                byte[][] bArr = (byte[][]) Array.newInstance(byte.class, iArr2);
                for (int i = 0; i < ((int[]) hashMap.get(str)).length; i++) {
                    bArr[i] = ((PersBiotemplatedata) new PersBiotemplatedata().queryForId(Long.valueOf((long) ((int[]) hashMap.get(str))[i]))).getTemplate_data();
                }
                byte[] bArr2 = new byte[ZKPalmService12.FIX_REG_TEMPLATE_LEN];
                int mergePalmTemplate = ZKBioTemplateService.mergePalmTemplate(bArr, bArr2);
                if (mergePalmTemplate == 0) {
                    ZKPalmService12.dbAdd(str, bArr2);
                } else {
                    Log.d(TAG, "setDBPlam mergePalmTemplate failed: " + mergePalmTemplate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
