package com.zktechnology.android.utils;

import android.content.Context;
import com.zkteco.adk.core.task.ZkThreadPoolManager;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.PersBiotemplatedata;
import com.zkteco.edk.finger.lib.ZkFingerprintManager;
import com.zkteco.liveface562.ZkFaceManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;

public class TemplateUtil {
    private static final Object FACE_LOCK = new Object();
    private static final Object FINGER_LOCK = new Object();
    private static final String TAG = "TemplateUtil";
    private static Future<?> sFaceFuture;
    private static Future<?> sFpFuture;

    public static void compareFaceCounts(Context context) {
        Future<?> future = sFaceFuture;
        if (future != null && !future.isCancelled() && !sFaceFuture.isDone()) {
            ZkThreadPoolManager.getInstance().cancel(sFaceFuture);
            sFaceFuture = null;
        }
        sFaceFuture = ZkThreadPoolManager.getInstance().submit(new Runnable(context) {
            public final /* synthetic */ Context f$0;

            {
                this.f$0 = r1;
            }

            public final void run() {
                TemplateUtil.lambda$compareFaceCounts$0(this.f$0);
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void lambda$compareFaceCounts$0(android.content.Context r4) {
        /*
            java.lang.Object r0 = FACE_LOCK
            monitor-enter(r0)
            r1 = 1
            int[] r1 = new int[r1]     // Catch:{ all -> 0x004b }
            com.zkteco.liveface562.ZkFaceManager r2 = com.zkteco.liveface562.ZkFaceManager.getInstance()     // Catch:{ all -> 0x004b }
            int r2 = r2.dbCount(r1)     // Catch:{ all -> 0x004b }
            if (r2 == 0) goto L_0x0012
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            return
        L_0x0012:
            r2 = 0
            r1 = r1[r2]     // Catch:{ all -> 0x004b }
            com.zkteco.android.db.orm.manager.template.TemplateManager r2 = new com.zkteco.android.db.orm.manager.template.TemplateManager     // Catch:{ all -> 0x004b }
            r2.<init>(r4)     // Catch:{ all -> 0x004b }
            long r2 = r2.getLightFaceCount()     // Catch:{ all -> 0x004b }
            int r4 = (int) r2     // Catch:{ all -> 0x004b }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x004b }
            r2.<init>()     // Catch:{ all -> 0x004b }
            java.lang.String r3 = "FaceTemplate: localCount="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x004b }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x004b }
            java.lang.String r3 = ", remoteCount="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x004b }
            java.lang.StringBuilder r2 = r2.append(r1)     // Catch:{ all -> 0x004b }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x004b }
            com.zktechnology.android.push.util.FileLogUtils.writeTemplateLog(r2)     // Catch:{ all -> 0x004b }
            if (r4 == r1) goto L_0x0049
            java.lang.String r4 = "compareFaceCounts: 人脸模板数量不一致, 开始同步人脸模板数据"
            com.zktechnology.android.push.util.FileLogUtils.writeTemplateLog(r4)     // Catch:{ all -> 0x004b }
            updateFaceTemplates()     // Catch:{ all -> 0x004b }
        L_0x0049:
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            return
        L_0x004b:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.TemplateUtil.lambda$compareFaceCounts$0(android.content.Context):void");
    }

    private static void updateFaceTemplates() {
        try {
            ZkFaceManager.getInstance().dbClear();
            List<PersBiotemplate> query = new PersBiotemplate().getQueryBuilder().where().eq("bio_type", 9).query();
            if (query != null && !query.isEmpty()) {
                for (PersBiotemplate persBiotemplate : query) {
                    PersBiotemplatedata persBiotemplatedata = (PersBiotemplatedata) new PersBiotemplatedata().queryForId(Long.valueOf(persBiotemplate.getID()));
                    if (persBiotemplatedata != null) {
                        ZkFaceManager.getInstance().dbAdd(persBiotemplate.getUser_pin(), persBiotemplatedata.getTemplate_data());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void compareFpCounts(Context context) {
        Future<?> future = sFpFuture;
        if (future != null && !future.isCancelled() && !sFpFuture.isDone()) {
            ZkThreadPoolManager.getInstance().cancel(sFpFuture);
            sFpFuture = null;
        }
        sFpFuture = ZkThreadPoolManager.getInstance().submit(new Runnable(context) {
            public final /* synthetic */ Context f$0;

            {
                this.f$0 = r1;
            }

            public final void run() {
                TemplateUtil.lambda$compareFpCounts$1(this.f$0);
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void lambda$compareFpCounts$1(android.content.Context r5) {
        /*
            java.lang.Object r0 = FINGER_LOCK
            monitor-enter(r0)
            r1 = 1
            int[] r1 = new int[r1]     // Catch:{ all -> 0x004b }
            com.zkteco.edk.finger.lib.ZkFingerprintManager r2 = com.zkteco.edk.finger.lib.ZkFingerprintManager.getInstance()     // Catch:{ all -> 0x004b }
            int r2 = r2.queryTemplateCount(r1)     // Catch:{ all -> 0x004b }
            if (r2 == 0) goto L_0x0012
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            return
        L_0x0012:
            r2 = 0
            r1 = r1[r2]     // Catch:{ all -> 0x004b }
            com.zkteco.android.db.orm.manager.template.TemplateManager r2 = new com.zkteco.android.db.orm.manager.template.TemplateManager     // Catch:{ all -> 0x004b }
            r2.<init>(r5)     // Catch:{ all -> 0x004b }
            long r3 = r2.getFingerCount()     // Catch:{ all -> 0x004b }
            int r5 = (int) r3     // Catch:{ all -> 0x004b }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x004b }
            r3.<init>()     // Catch:{ all -> 0x004b }
            java.lang.String r4 = "compareFpCounts: localCount="
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x004b }
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ all -> 0x004b }
            java.lang.String r4 = ", remoteCount="
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x004b }
            java.lang.StringBuilder r3 = r3.append(r1)     // Catch:{ all -> 0x004b }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x004b }
            com.zktechnology.android.push.util.FileLogUtils.writeTemplateLog(r3)     // Catch:{ all -> 0x004b }
            if (r5 == r1) goto L_0x0049
            java.lang.String r5 = "compareFpCounts: 指纹模板数量不一致, 开始同步指纹模板数据"
            com.zktechnology.android.push.util.FileLogUtils.writeTemplateLog(r5)     // Catch:{ all -> 0x004b }
            updateFingerTemplates(r2)     // Catch:{ all -> 0x004b }
        L_0x0049:
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            return
        L_0x004b:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.TemplateUtil.lambda$compareFpCounts$1(android.content.Context):void");
    }

    private static void updateFingerTemplates(TemplateManager templateManager) {
        ZkFingerprintManager.getInstance().clear();
        List<FpTemplate10> allFingerTemplate = templateManager.getAllFingerTemplate();
        if (allFingerTemplate != null && !allFingerTemplate.isEmpty()) {
            for (FpTemplate10 next : allFingerTemplate) {
                ZkFingerprintManager.getInstance().insertTemplate(next.getPin() + "_" + next.getFingerid(), next.getTemplate());
            }
        }
    }
}
