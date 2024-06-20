package com.zkteco.edk.hardware.encrypt;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import com.guide.guidecore.utils.ShutterHandler;
import com.zkteco.adk.core.task.IZkJob;
import com.zkteco.adk.core.task.IZkScheduler;
import com.zkteco.adk.core.task.ZkBasicScheduler;
import com.zkteco.adk.core.task.ZkJobFactory;
import com.zkteco.adk.core.task.ZkJobType;
import com.zkteco.edk.hardware.encrypt.task.ZkActivationParamName;
import com.zkteco.edk.hardware.encrypt.task.ZkCheckRuntimeTask;
import com.zkteco.edk.hardware.encrypt.task.ZkTaskName;
import java.util.Objects;

public class ZkActivationContentProvider extends ContentProvider {
    private static boolean created = false;
    private Context mContext;
    private final IZkScheduler mScheduler = new ZkBasicScheduler();

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public boolean onCreate() {
        if (created) {
            return true;
        }
        created = true;
        Context applicationContext = ((Context) Objects.requireNonNull(getContext())).getApplicationContext();
        this.mContext = applicationContext;
        ZkSPUtils.init(applicationContext);
        ZkFileLogUtils.setLogSavePath(this.mContext.getFilesDir().getPath());
        ZkCrashHandler.getInstance().init(this.mContext);
        startBackgroundService(this.mContext);
        startCheckProcess(this.mContext);
        return true;
    }

    private void startCheckProcess(Context context) {
        IZkJob create = ZkJobFactory.getInstance().create(ZkJobType.LOOP);
        create.setJobName(ZkTaskName.JOB_CHECK_RUNTIME);
        create.addTask(new ZkCheckRuntimeTask());
        create.addParam("loop_job_interval_time_long", Long.valueOf(ShutterHandler.NUC_TIME_MS));
        create.addParam(ZkActivationParamName.APPLICATION_CONTEXT, context);
        this.mScheduler.addJob(ZkTaskName.JOB_CHECK_RUNTIME, create);
        this.mScheduler.executeJob(ZkTaskName.JOB_CHECK_RUNTIME);
    }

    public Context getAppContext() {
        return this.mContext;
    }

    private void startBackgroundService(Context context) {
        if (!"com.zkteco.edk.hardware.encrypt.service".equals(context.getPackageName())) {
            Intent intent = new Intent();
            intent.setPackage(context.getPackageName());
            intent.setAction(context.getPackageName() + ".activation.ZkBackgroundService");
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }
}
