package com.zktechnology.android.work;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.tencent.map.geolocation.util.DateUtils;
import com.zktechnology.android.device.DeviceManager;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.GlobalConfig;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.employeemgmt.util.SQLiteFaceUtils;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ZkClearPhotoWork extends Worker {
    private static final String TAG = "Work-Clear-Photo";
    private final SimpleDateFormat mSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public ZkClearPhotoWork(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    public ListenableWorker.Result doWork() {
        Log.i(TAG, "Work-Clear-Photo---> doWork: start to schedule work");
        String workTime = DeviceManager.getDefault().getWorkTime();
        Calendar instance = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        if (!TextUtils.isEmpty(workTime)) {
            Date date = null;
            try {
                date = this.mSdf.parse(workTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                instance2.setTime(date);
            }
        } else {
            instance2.set(11, 2);
            instance2.set(12, 0);
            instance2.set(13, 0);
            instance2.add(5, -1);
        }
        long timeInMillis = instance.getTimeInMillis() - instance2.getTimeInMillis();
        int i = (int) (timeInMillis / DateUtils.ONE_HOUR);
        int i2 = (int) (timeInMillis / DateUtils.ONE_DAY);
        boolean z = true;
        if (!(i2 > 1 || (i2 > 0 && i >= 0))) {
            Log.e(TAG, "Work-Clear-Photo---> doWork: 未到时间 跳过任务");
            return ListenableWorker.Result.failure();
        }
        instance.set(11, 2);
        instance.set(12, 0);
        instance.set(13, 0);
        DeviceManager.getDefault().setWorkTime(this.mSdf.format(instance.getTime()));
        Log.e(TAG, "Work-Clear-Photo---> doWork: time is up, start to work now--->");
        FileLogUtils.writeClearPhotoLog("Work-Clear-Photo---> doWork: time is up, start to work now--->");
        try {
            if (DBManager.getInstance().getIntOption(DBConfig.PRIVACY_MODE, 1) != 1) {
                z = false;
            }
            if (z) {
                FileLogUtils.writeClearPhotoLog("安全模式, 开始清除照片");
                File[] listFiles = new File(ZKFilePath.FACE_PATH).listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    for (File file : listFiles) {
                        String pinFromPath = getPinFromPath(file.getAbsolutePath());
                        if (!isPinInUserInfo(pinFromPath)) {
                            FileLogUtils.writeClearPhotoLog(pinFromPath + " 对应的用户信息不存在, 删除");
                            file.delete();
                        }
                        if (SQLiteFaceUtils.isFaceAlreadyInDB(DBManager.getInstance(), pinFromPath, 9, 5, GlobalConfig.FACE_ALGO_MINOR_VER)) {
                            FileLogUtils.writeClearPhotoLog(pinFromPath + " 已提取到模板, 删除");
                            file.delete();
                        }
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return ListenableWorker.Result.success();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0067, code lost:
        if (r2 == null) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x006a, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0040, code lost:
        if (r2 != null) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0042, code lost:
        r2.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isPinInUserInfo(java.lang.String r7) {
        /*
            java.lang.String r0 = "ZK_L_IS_EMPTY"
            r1 = 0
            r2 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0048 }
            r3.<init>()     // Catch:{ Exception -> 0x0048 }
            java.lang.String r4 = "select count(*) from USER_INFO WHERE USER_PIN = '"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x0048 }
            java.lang.StringBuilder r7 = r3.append(r7)     // Catch:{ Exception -> 0x0048 }
            java.lang.String r3 = "'"
            java.lang.StringBuilder r7 = r7.append(r3)     // Catch:{ Exception -> 0x0048 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0048 }
            com.zkteco.android.db.orm.manager.DataManager r3 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x0048 }
            android.database.Cursor r2 = r3.queryBySql(r7)     // Catch:{ Exception -> 0x0048 }
            if (r2 == 0) goto L_0x0037
            r2.moveToFirst()     // Catch:{ Exception -> 0x0048 }
            long r3 = r2.getLong(r1)     // Catch:{ Exception -> 0x0048 }
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x0040
            r7 = 1
            r1 = r7
            goto L_0x0040
        L_0x0037:
            com.zkteco.android.logfile.LogManager r7 = com.zkteco.android.logfile.LogManager.getInstance()     // Catch:{ Exception -> 0x0048 }
            java.lang.String r3 = "isPinInUserInfo: cursor == null"
            r7.OutputLog(r0, r3)     // Catch:{ Exception -> 0x0048 }
        L_0x0040:
            if (r2 == 0) goto L_0x006a
        L_0x0042:
            r2.close()
            goto L_0x006a
        L_0x0046:
            r7 = move-exception
            goto L_0x006b
        L_0x0048:
            r7 = move-exception
            com.zkteco.android.logfile.LogManager r3 = com.zkteco.android.logfile.LogManager.getInstance()     // Catch:{ all -> 0x0046 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0046 }
            r4.<init>()     // Catch:{ all -> 0x0046 }
            java.lang.String r5 = "isPinInUserInfo: Exc:"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0046 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0046 }
            java.lang.StringBuilder r7 = r4.append(r7)     // Catch:{ all -> 0x0046 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0046 }
            r3.OutputLog(r0, r7)     // Catch:{ all -> 0x0046 }
            if (r2 == 0) goto L_0x006a
            goto L_0x0042
        L_0x006a:
            return r1
        L_0x006b:
            if (r2 == 0) goto L_0x0070
            r2.close()
        L_0x0070:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.work.ZkClearPhotoWork.isPinInUserInfo(java.lang.String):boolean");
    }

    private static String getPinFromPath(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        int lastIndexOf2 = str.lastIndexOf(".");
        if (lastIndexOf2 == -1) {
            return "";
        }
        return str.substring(lastIndexOf + 1, lastIndexOf2);
    }

    public void onStopped() {
        super.onStopped();
        Log.e(TAG, "Work-Clear-Photo---> doWork: onStopped");
    }

    public static void startClearPhotoWork(Context context) {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.KEEP, (PeriodicWorkRequest) new PeriodicWorkRequest.Builder(ZkClearPhotoWork.class, Duration.of(1, ChronoUnit.HOURS)).build());
    }
}
