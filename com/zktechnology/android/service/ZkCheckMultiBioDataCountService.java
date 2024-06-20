package com.zktechnology.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.work.WorkRequest;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.adk.core.task.ZkThreadPoolManager;
import com.zkteco.android.db.orm.manager.DataManager;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ZkCheckMultiBioDataCountService extends Service {
    private ScheduledFuture<?> mCheckCountTask = null;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        ZkThreadPoolManager.getInstance().finishFuture(this.mCheckCountTask);
        this.mCheckCountTask = ZkThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            public final void run() {
                ZkCheckMultiBioDataCountService.this.refreshMultiBioDataCount();
            }
        }, 5000, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: private */
    public void refreshMultiBioDataCount() {
        DataManager instance = DBManager.getInstance();
        if (instance != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                sb.append(queryBioDataCountByType(instance, i));
                if (i != 9) {
                    sb.append(":");
                }
            }
            instance.setStrOption("MultiBioDataCount", sb.toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0032, code lost:
        if (r2 != null) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003b, code lost:
        if (r2 == null) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003d, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0040, code lost:
        return -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long queryBioDataCountByType(com.zkteco.android.db.orm.manager.DataManager r6, int r7) {
        /*
            r5 = this;
            r0 = -1
            if (r7 < 0) goto L_0x0047
            r2 = 10
            if (r7 < r2) goto L_0x0009
            goto L_0x0047
        L_0x0009:
            r2 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x0037 }
            r3.<init>()     // Catch:{ SQLException -> 0x0037 }
            java.lang.String r4 = "select count(id) from Pers_BioTemplate where bio_type="
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ SQLException -> 0x0037 }
            java.lang.StringBuilder r7 = r3.append(r7)     // Catch:{ SQLException -> 0x0037 }
            java.lang.String r7 = r7.toString()     // Catch:{ SQLException -> 0x0037 }
            android.database.Cursor r2 = r6.queryBySql(r7)     // Catch:{ SQLException -> 0x0037 }
            boolean r6 = r2.moveToFirst()     // Catch:{ SQLException -> 0x0037 }
            if (r6 == 0) goto L_0x0032
            r6 = 0
            long r6 = r2.getLong(r6)     // Catch:{ SQLException -> 0x0037 }
            if (r2 == 0) goto L_0x0031
            r2.close()
        L_0x0031:
            return r6
        L_0x0032:
            if (r2 == 0) goto L_0x0040
            goto L_0x003d
        L_0x0035:
            r6 = move-exception
            goto L_0x0041
        L_0x0037:
            r6 = move-exception
            r6.printStackTrace()     // Catch:{ all -> 0x0035 }
            if (r2 == 0) goto L_0x0040
        L_0x003d:
            r2.close()
        L_0x0040:
            return r0
        L_0x0041:
            if (r2 == 0) goto L_0x0046
            r2.close()
        L_0x0046:
            throw r6
        L_0x0047:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.service.ZkCheckMultiBioDataCountService.queryBioDataCountByType(com.zkteco.android.db.orm.manager.DataManager, int):long");
    }

    public void onDestroy() {
        super.onDestroy();
        ZkThreadPoolManager.getInstance().finishFuture(this.mCheckCountTask);
    }
}
