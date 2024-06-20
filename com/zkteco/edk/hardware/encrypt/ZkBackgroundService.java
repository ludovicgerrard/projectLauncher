package com.zkteco.edk.hardware.encrypt;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import com.guide.guidecore.utils.ShutterHandler;
import com.zkteco.adk.core.task.IZkJob;
import com.zkteco.adk.core.task.IZkJobListener;
import com.zkteco.adk.core.task.IZkScheduler;
import com.zkteco.adk.core.task.ZkBasicScheduler;
import com.zkteco.adk.core.task.ZkJobFactory;
import com.zkteco.adk.core.task.ZkJobType;
import com.zkteco.adk.core.task.ZkTaskLog;
import com.zkteco.adk.core.task.ZkTaskParamUtils;
import com.zkteco.edk.hardware.encrypt.task.ZkActivationParamName;
import com.zkteco.edk.hardware.encrypt.task.ZkCheckActivationEndTask;
import com.zkteco.edk.hardware.encrypt.task.ZkCheckActivationServiceTask;
import com.zkteco.edk.hardware.encrypt.task.ZkCheckHardwareActivationTask;
import com.zkteco.edk.hardware.encrypt.task.ZkCheckLocalStorageActivationTask;
import com.zkteco.edk.hardware.encrypt.task.ZkCheckSoftwareActivationTask;
import com.zkteco.edk.hardware.encrypt.task.ZkStartActivationClientTask;
import com.zkteco.edk.hardware.encrypt.task.ZkStartActivationServerTask;
import com.zkteco.edk.hardware.encrypt.task.ZkTaskName;
import java.util.Map;

public class ZkBackgroundService extends Service implements IZkJobListener {
    private static final String CHANNEL_NAME = "ZkActivationService";
    private static final int ID = 1000;
    public static final String INTENT_KEY_RETRY_CHECK_BOOL = "retry_check_activation";
    private static boolean mIsHardwareState = false;
    private Object mNotificationCache = null;
    private IZkScheduler mScheduler = new ZkBasicScheduler();

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onJobFailed(int i, String str, Map<String, Object> map) {
    }

    public void onTaskFailed(int i, String str, String str2, Map<String, Object> map) {
    }

    public void onTaskFinish(String str, String str2, Map<String, Object> map) {
    }

    public static boolean getHardwareState() {
        return mIsHardwareState;
    }

    public void onCreate() {
        super.onCreate();
        ZkTaskLog.setLogLevel(ZkTaskLog.LEVEL_W);
        Log.i(ZkLogTag.EDK_ACTIVATION, "onCreate");
        showNotification();
        this.mScheduler.setJobListener(this);
        startActivationCheckJob();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Log.i(ZkLogTag.EDK_ACTIVATION, "onStartCommand");
        if (intent == null) {
            Log.w(ZkLogTag.EDK_ACTIVATION, "onStartCommand intent == null!");
            return super.onStartCommand((Intent) null, i, i2);
        }
        if (intent.hasExtra(INTENT_KEY_RETRY_CHECK_BOOL)) {
            if (!intent.getBooleanExtra(INTENT_KEY_RETRY_CHECK_BOOL, false) || this.mScheduler == null) {
                return super.onStartCommand(intent, i, i2);
            }
            startActivationCheckJob();
            IZkJob job = this.mScheduler.getJob(ZkTaskName.JOB_CHECK_ACTIVATION);
            if (job != null) {
                job.addParam(ZkActivationParamName.FLAG_IS_RETRY_CHECK_ACTIVATION_BOOL, true);
                job.addParam(ZkActivationParamName.FLAG_IS_FIRST_CHECK_FAILED_SKIP_TO_NEXT, true);
            }
        }
        return super.onStartCommand(intent, i, i2);
    }

    private void startActivationCheckJob() {
        IZkScheduler iZkScheduler = this.mScheduler;
        if (iZkScheduler == null) {
            Log.w(ZkLogTag.EDK_ACTIVATION, "startActivationCheckJob mScheduler == null!");
            return;
        }
        iZkScheduler.stopJob(ZkTaskName.JOB_CHECK_ACTIVATION);
        IZkJob create = ZkJobFactory.getInstance().create(ZkJobType.LOOP);
        create.setJobName(ZkTaskName.JOB_CHECK_ACTIVATION);
        create.addTask(new ZkCheckActivationServiceTask());
        create.addTask(new ZkStartActivationServerTask());
        create.addTask(new ZkStartActivationClientTask());
        create.addTask(new ZkCheckHardwareActivationTask());
        create.addTask(new ZkCheckSoftwareActivationTask());
        create.addTask(new ZkCheckLocalStorageActivationTask());
        create.addTask(new ZkCheckActivationEndTask());
        create.addParam("loop_job_interval_time_long", Long.valueOf(ShutterHandler.NUC_TIME_MS));
        create.addParam(ZkActivationParamName.APPLICATION_CONTEXT, getApplicationContext());
        create.addParam(ZkActivationParamName.FLAG_IS_FIRST_CHECK_FAILED_SKIP_TO_NEXT, true);
        this.mScheduler.addJob(ZkTaskName.JOB_CHECK_ACTIVATION, create);
        this.mScheduler.executeJob(ZkTaskName.JOB_CHECK_ACTIVATION);
    }

    private void showNotification() {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mNotificationCache = createNotificationChannel();
            return;
        }
        Notification notification = new Notification();
        startForeground(1000, notification);
        this.mNotificationCache = notification;
    }

    private void hideNotification() {
        if (Build.VERSION.SDK_INT >= 24) {
            Object obj = this.mNotificationCache;
            if (obj instanceof NotificationManager) {
                ((NotificationManager) obj).cancel(1000);
            }
        } else if (this.mNotificationCache instanceof Notification) {
            stopForeground(true);
        }
    }

    private void stopSelfService(Context context) {
        Intent intent = new Intent();
        intent.setPackage(context.getPackageName());
        intent.setAction(context.getPackageName() + ".activation.ZkBackgroundService");
        context.stopService(intent);
    }

    private NotificationManager createNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        String str = getApplicationContext().getPackageName() + ".activation.ZkBackgroundService";
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(str, CHANNEL_NAME, 1);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setVibrationPattern(new long[]{0});
            notificationChannel.setSound((Uri) null, (AudioAttributes) null);
            notificationManager.createNotificationChannel(notificationChannel);
            if (notificationChannel.getImportance() == 0) {
                Intent intent = new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                intent.putExtra("android.provider.extra.CHANNEL_ID", notificationChannel.getId());
                startActivity(intent);
            } else {
                Notification build = new Notification.Builder(this, str).setContentTitle(CHANNEL_NAME).setContentText(" ").setSettingsText(" ").setSmallIcon(R.mipmap.ic_activation).build();
                notificationManager.notify(1000, build);
                startForeground(1000, build);
            }
        }
        return notificationManager;
    }

    public void onDestroy() {
        this.mScheduler.destroy();
        super.onDestroy();
        Log.e(ZkLogTag.EDK_ACTIVATION, "onDestroy");
        this.mScheduler = null;
    }

    public void onJobFinish(String str, Map<String, Object> map) {
        if (ZkTaskName.JOB_CHECK_ACTIVATION.equals(str)) {
            mIsHardwareState = ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_ACTIVATION_BOOL, false);
            if (!ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_ACTIVATION_SERVER_BOOL, false)) {
                this.mScheduler.stopJob(ZkTaskName.JOB_CHECK_ACTIVATION);
                stopSelfService(getApplicationContext());
            }
        }
    }
}
