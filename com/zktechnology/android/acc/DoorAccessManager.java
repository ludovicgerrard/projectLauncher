package com.zktechnology.android.acc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.zktechnology.android.acc.advance.DoorAccessChecker;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.DoorVerifyCombination;
import com.zktechnology.android.acc.base.DoorAlwaysOpenChecker;
import com.zktechnology.android.acc.base.InterceptorChain;
import com.zktechnology.android.acc.base.Interceptors.BaseInterceptor;
import com.zktechnology.android.acc.base.Interceptors.DoorAlarmInterceptor;
import com.zktechnology.android.acc.base.Interceptors.DoorAlwaysOpenInterceptor;
import com.zktechnology.android.acc.base.Interceptors.DoorDuplicateOpenInterceptor;
import com.zktechnology.android.acc.base.Interceptors.DoorEffectiveTimePeriodInterceptor;
import com.zktechnology.android.acc.base.Interceptors.DoorOpenInterceptor;
import com.zktechnology.android.acc.base.Interceptors.DoorRemoteCloseInterceptor;
import com.zktechnology.android.acc.base.Interceptors.DoorRemoteOpenInterceptor;
import com.zktechnology.android.acc.base.Request;
import com.zktechnology.android.acc.base.Response;
import com.zktechnology.android.event.EventBusHelper;
import com.zktechnology.android.event.EventState;
import com.zktechnology.android.helper.DoorManagerHelper;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.push.acc.AccPush;
import com.zktechnology.android.push.util.AccEventType;
import com.zktechnology.android.receiver.OpLogReceiver;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.bean.process.ZKVerifyInfo;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zktechnology.android.verify.utils.VerifyTypeUtil;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.view.controller.SensorStateController;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.db.orm.util.SpeakerHelper;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.zkcore.utils.ZKSharedUtil;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class DoorAccessManager extends BaseInterceptor {
    public static final String ACTION_ALARM_CLOSE = "com.zkteco.android.core.ACTION_ALARM_CLOSE";
    public static final String ACTION_ALARM_OPEN = "com.zkteco.android.core.ACTION_ALARM_OPEN";
    public static final String ACTION_ALWAYS_OPEN_CHANGE = "com.zkteco.android.core.ACTION_ALWAYS_OPEN_CHANGE";
    public static final String ACTION_AUX_INPUT_STATE_CHANGE = "com.zkteco.android.core.ACTION_AUX_INPUT_CHANGE";
    public static final String ACTION_CLOSE_DOOR = "com.zkteco.android.core.ACTION_CLOSE";
    public static final String ACTION_DOOR_SENSOR_CHANGE = "com.zkteco.android.core.ACTION_DOOR_SENSOR_CHANGE";
    public static final String ACTION_NORMALLY_OPEN = "com.zkteco.android.core.ACTION_NORMALLY_OPEN";
    public static final String ACTION_OPEN_DOOR = "com.zkteco.android.core.ACTION_OPEN";
    public static final String ACTION_OPTION_CHANGE = "com.zkteco.android.core.ACTION_OPTION_CHANGE";
    public static final String ACTION_REMOTE_CANCEL_ALARM = "com.zkteco.android.core.ACTION_REMOTE_CANCEL_ALARM";
    public static final String ACTION_TAMPER_CHANGE = "com.zkteco.android.core.ACTION_TAMPER_CHANGE";
    public static final String CLOSE_ALL_ALARM = "com.zkteco.android.core.CLOSE_ALL_ALARM";
    private static final String EXIT_BUTTON = "EXIT_BUTTON";
    private static final String EXIT_REMOTE = "EXIT_REMOTE";
    public static final String TAG = "AccDoor";
    /* access modifiers changed from: private */
    public static Intent broadcastIntent;
    /* access modifiers changed from: private */
    public static BroadcastTask broadcastTask = new BroadcastTask((AnonymousClass1) null);
    private static CancelRemoteAlarmTask cancelRemoteAlarmTask = new CancelRemoteAlarmTask((AnonymousClass1) null);
    /* access modifiers changed from: private */
    public static InterceptorChain chain;
    private static CheckAntiTamperStateTask checkAntiTamperStateTask = new CheckAntiTamperStateTask((AnonymousClass1) null);
    private static Runnable closeLockTask = new CloseLockTask((AnonymousClass1) null);
    /* access modifiers changed from: private */
    public static Context context;
    private static DoorAlwaysOpenChecker doorAlwaysOpenChecker;
    /* access modifiers changed from: private */
    public static LocalMessageHandler handler;
    public static volatile boolean isAlreadyFirstAlwaysOpenDoor = false;
    /* access modifiers changed from: private */
    public static boolean isReadyToPlay;
    private static long lastOpenTime = SystemClock.elapsedRealtime();
    private static int lastTimeSensorState;
    /* access modifiers changed from: private */
    public static boolean localAlarmIsRinging = false;
    public static DoorAccessManager mInstance = null;
    /* access modifiers changed from: private */
    public static final ExecutorService mSingleService = Executors.newSingleThreadExecutor();
    private static Runnable openLockTask = new OpenLockTask((AnonymousClass1) null);
    private static Runnable playRemoteAlarmTask = new PlayRemoteAlarmTask((AnonymousClass1) null);
    /* access modifiers changed from: private */
    public static int playResourceID;
    private static int playStreamID;
    /* access modifiers changed from: private */
    public static boolean remoteAlarmIsRinging = false;
    private static RemoteDoorReceiver remoteDoorReceiver = new RemoteDoorReceiver((AnonymousClass1) null);
    /* access modifiers changed from: private */
    public static Request request;
    private static int sensorStateChangeFrequency;
    private static SoundPool soundPool;
    private static SoundPoolListener soundPoolListener = new SoundPoolListener((AnonymousClass1) null);
    /* access modifiers changed from: private */
    public static UpdateDeviceStateTask updateDeviceStateTask = new UpdateDeviceStateTask((AnonymousClass1) null);
    /* access modifiers changed from: private */
    public static ZKSharedUtil zkSharedUtil;
    private ZKAccDao accDao;
    LoadSettingTask loadSettingTask = new LoadSettingTask((AnonymousClass1) null);
    private AtomicBoolean mRegisterRemoteDoorBR = new AtomicBoolean(false);

    static /* synthetic */ int access$3608() {
        int i = sensorStateChangeFrequency;
        sensorStateChangeFrequency = i + 1;
        return i;
    }

    private DoorAccessManager() {
        Context launcherApplicationContext = LauncherApplication.getLauncherApplicationContext();
        context = launcherApplicationContext;
        DoorAccessDao.openDB(launcherApplicationContext);
        handler = new LocalMessageHandler(context);
        initRequest(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_OPEN_DOOR);
        intentFilter.addAction(ACTION_NORMALLY_OPEN);
        intentFilter.addAction(ACTION_CLOSE_DOOR);
        intentFilter.addAction("com.zkteco.android.core.ACTION_ALWAYS_OPEN_CHANGE");
        intentFilter.addAction(ACTION_TAMPER_CHANGE);
        intentFilter.addAction(ACTION_DOOR_SENSOR_CHANGE);
        intentFilter.addAction(ACTION_AUX_INPUT_STATE_CHANGE);
        intentFilter.addAction(ACTION_REMOTE_CANCEL_ALARM);
        intentFilter.addAction(ACTION_OPTION_CHANGE);
        intentFilter.addAction(CLOSE_ALL_ALARM);
        context.registerReceiver(remoteDoorReceiver, intentFilter);
        this.mRegisterRemoteDoorBR.set(true);
        zkSharedUtil = new ZKSharedUtil(context);
        mSingleService.submit(new GetStateTask(request));
    }

    public static DoorAccessManager getInstance() {
        if (mInstance == null) {
            synchronized (DoorAccessManager.class) {
                if (mInstance == null) {
                    mInstance = new DoorAccessManager();
                }
            }
        }
        return mInstance;
    }

    private static class GetStateTask implements Runnable {
        Request request;

        GetStateTask(Request request2) {
            this.request = request2;
        }

        public void run() {
            try {
                if (DBManager.getInstance().getIntOption(ZKDBConfig.TAMPER_ALARM_FUN, 1) == 1) {
                    this.request.setTamperState(DoorManagerHelper.getInstance().getTamperState());
                }
                int sensorState = DoorManagerHelper.getInstance().getSensorState();
                DoorAccessManager.sendChangeSensorIcon(sensorState);
                DoorSensorState fromInteger = DoorSensorState.fromInteger(sensorState);
                if (fromInteger != null) {
                    this.request.setCurrentDoorSensorState(fromInteger);
                }
            } catch (Exception e) {
                LogUtils.e("AccDoor", "GetStateTask run error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public static void sendChangeSensorIcon(int i) {
        Intent intent = new Intent();
        intent.setAction(SensorStateController.ACTION_SENSOR_STATE);
        intent.putExtra("SensorState", i);
        context.sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public static boolean isLocalAlarmStateChanged() {
        int localAlarmSwitch = request.getLocalAlarmSwitch();
        int intOption = DBManager.getInstance().getIntOption(ZKDBConfig.OPT_LOCAL_ALARM_ON, 0);
        LogUtils.i("AccDoor", "isLocalAlarmStateChanged old: " + localAlarmSwitch + " new: " + intOption);
        if (localAlarmSwitch == intOption) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static boolean isExtAlarmStateChanged() {
        if (request.getExtAlarmSwitch() != DBManager.getInstance().getIntOption(ZKDBConfig.OPT_EXT_ALARM_ON, 0)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean checkSensorType() {
        DataManager instance = DBManager.getInstance();
        DoorSensorType doorSensorType = request.getDoorSensorType();
        DoorSensorType fromInteger = DoorSensorType.fromInteger(instance.getIntOption(ZKLauncher.sAccessRuleType == 0 ? ZKDBConfig.OPT_DSM : ZKDBConfig.OPT_DOOR1_SENSOR_TYPE, 2));
        if (fromInteger == null) {
            fromInteger = DoorSensorType.NONE;
        }
        request.setDoorSensorType(fromInteger);
        if (doorSensorType != fromInteger) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static void loadSettings(Context context2) {
        LogUtils.i("AccDoor", "loadSettings start");
        DataManager instance = DBManager.getInstance();
        int intOption = instance.getIntOption("~AAFO", 0);
        int intOption2 = instance.getIntOption(ZKDBConfig.OPT_LOCAL_ALARM_ON, 0);
        int intOption3 = instance.getIntOption(ZKDBConfig.OPT_EXT_ALARM_ON, 0);
        if (intOption2 == 1 && intOption == 0) {
            instance.setIntOption(ZKDBConfig.OPT_LOCAL_ALARM_ON, 0);
            intOption2 = 0;
        }
        if (intOption3 == 1 && intOption == 0) {
            instance.setIntOption(ZKDBConfig.OPT_EXT_ALARM_ON, 0);
            intOption3 = 0;
        }
        request.setLocalAlarmSwitch(intOption2);
        request.setExtAlarmSwitch(intOption3);
        checkSensorType();
        boolean z = ZKLauncher.sAccessRuleType == 0;
        request.setLockDriveDurationInSeconds(instance.getIntOption(z ? ZKDBConfig.OPT_LOCK_ON : ZKDBConfig.OPT_DOOR1_DRIVER_TIME, 5));
        request.setDoorSensorLocalAlarmDelay(instance.getIntOption(z ? ZKDBConfig.OPT_ODD : ZKDBConfig.OPT_DOOR1_DETECTOR_TIME, 5));
        request.setDoorSensorRemoteAlarmDelay(instance.getIntOption(ZKDBConfig.OPT_DOOR_SENSOR_TIME_OUT, 10));
        request.setGateSwitchMode(instance.getIntOption(ZKDBConfig.GATE_MODE_SWITCH, 0));
        if (DoorAccessDao.checkDoor1CancelKeepOpenDay()) {
            controlDoorAlwaysOpenChecker(true);
        }
    }

    /* access modifiers changed from: private */
    public static void controlDoorAlwaysOpenChecker(boolean z) {
        DoorAlwaysOpenChecker doorAlwaysOpenChecker2;
        DoorAlwaysOpenChecker doorAlwaysOpenChecker3;
        if (z && doorAlwaysOpenChecker == null) {
            DoorAlwaysOpenChecker doorAlwaysOpenChecker4 = new DoorAlwaysOpenChecker(mInstance, context);
            doorAlwaysOpenChecker = doorAlwaysOpenChecker4;
            doorAlwaysOpenChecker4.start();
        } else if (!z && (doorAlwaysOpenChecker3 = doorAlwaysOpenChecker) != null) {
            doorAlwaysOpenChecker3.stop();
        } else if (z && (doorAlwaysOpenChecker2 = doorAlwaysOpenChecker) != null) {
            doorAlwaysOpenChecker2.start();
        }
    }

    private void initRequest(Context context2) {
        chain = new InterceptorChain();
        Request request2 = new Request();
        request = request2;
        request2.setDoorOpenType(DoorOpenType.NONE);
        request.setDoorSensorType(DoorSensorType.NONE);
        request.setCurrentDoorSensorState(DoorSensorState.NONE);
        request.setLastDoorSensorState(DoorSensorState.NONE);
        doorAlwaysOpenChecker = new DoorAlwaysOpenChecker(this, context2);
        mSingleService.submit(this.loadSettingTask);
    }

    private static class LoadSettingTask implements Runnable {
        private LoadSettingTask() {
        }

        /* synthetic */ LoadSettingTask(AnonymousClass1 r1) {
            this();
        }

        public void run() {
            DoorAccessManager.loadSettings(DoorAccessManager.context);
        }
    }

    /* access modifiers changed from: private */
    public static boolean checkActionFilter(String str) {
        if (!DoorAccessDao.isDoorAlwaysOpen(request.getDoorOpenType()) || !str.equalsIgnoreCase(ACTION_CLOSE_DOOR)) {
            return false;
        }
        ZKAccDao.getInstance(context).addAccAttLog(ZKLauncher.sInOutState, ZKLauncher.sDoorId, 37, ZKLauncher.sDoor1VerifyType);
        return true;
    }

    private static class RemoteDoorReceiver extends BroadcastReceiver {
        private RemoteDoorReceiver() {
        }

        /* synthetic */ RemoteDoorReceiver(AnonymousClass1 r1) {
            this();
        }

        public void onReceive(Context context, Intent intent) {
            Intent unused = DoorAccessManager.broadcastIntent = intent;
            DoorAccessManager.handler.sendEmptyMessage(4);
        }
    }

    /* access modifiers changed from: private */
    public static void sendAlwaysOpenState() {
        if (DBManager.getInstance().getIntOption("AccessRuleType", 0) == 1) {
            EventState eventState = new EventState(2);
            eventState.setOpenAlways("open");
            EventBusHelper.post(eventState);
        }
    }

    public void initialize() {
        initSoundPool();
        initLockState();
        mSingleService.submit(updateDeviceStateTask);
    }

    private static class UpdateDeviceStateTask implements Runnable {
        private UpdateDeviceStateTask() {
        }

        /* synthetic */ UpdateDeviceStateTask(AnonymousClass1 r1) {
            this();
        }

        public void run() {
            DoorAccessManager.updateDeviceState();
        }
    }

    private void initSoundPool() {
        SoundPool soundPool2 = new SoundPool(1, 3, 0);
        soundPool = soundPool2;
        soundPool2.load(context, R.raw.alarm, 1);
        soundPool.setOnLoadCompleteListener(soundPoolListener);
    }

    private static class SoundPoolListener implements SoundPool.OnLoadCompleteListener {
        private SoundPoolListener() {
        }

        /* synthetic */ SoundPoolListener(AnonymousClass1 r1) {
            this();
        }

        public void onLoadComplete(SoundPool soundPool, int i, int i2) {
            boolean unused = DoorAccessManager.isReadyToPlay = true;
            int unused2 = DoorAccessManager.playResourceID = i;
            DoorAccessManager.checkAntiTamperState();
        }
    }

    private void initLockState() {
        if (!DoorAccessDao.isInDoorAlwaysOpenTimeZone()) {
            closeLock();
        }
    }

    /* access modifiers changed from: private */
    public static void checkAntiTamperState() {
        mSingleService.submit(checkAntiTamperStateTask);
    }

    private static class CheckAntiTamperStateTask implements Runnable {
        private CheckAntiTamperStateTask() {
        }

        /* synthetic */ CheckAntiTamperStateTask(AnonymousClass1 r1) {
            this();
        }

        public void run() {
            int tamperState = DoorAccessManager.request.getTamperState();
            if (tamperState == 1) {
                if (DBManager.getInstance().getIntOption(ZKDBConfig.TAMPER_ALARM_FUN, 1) == 1) {
                    DoorAccessManager.request.setTamperState(tamperState);
                }
                DoorAccessManager.request.setAccessVerified(false);
                LogUtils.i("AccDoor", "chain.clearInterceptors()1");
                DoorAccessManager.chain.clearInterceptors();
                DoorAccessManager.chain.addInterceptors(new DoorAlarmInterceptor());
                LogUtils.i("AccDoor", "initAccess1");
                DoorAccessManager.initAccess();
            } else if ((DoorAccessManager.request.getDoorSensorType().getValue() == 1 && tamperState == 0) || (DoorAccessManager.request.getDoorSensorType().getValue() == 2 && tamperState != 0)) {
                DoorAccessManager.request.setAccessVerified(false);
                LogUtils.i("AccDoor", "chain.clearInterceptors()2");
                DoorAccessManager.chain.clearInterceptors();
                DoorAccessManager.chain.addInterceptors(new DoorAlarmInterceptor());
                LogUtils.i("AccDoor", "initAccess2");
                DoorAccessManager.initAccess();
            }
        }
    }

    /* access modifiers changed from: private */
    public static void processDoorSensorTypeChange() {
        request.setDoorSensorOpened(false);
        request.setTampered(false);
        request.setUnsafeOpened(false);
        request.setDoorSensorTimeout(false);
        request.setDoorAlarmType(DoorAlarmType.NONE);
        if (handler.hasMessages(1)) {
            handler.removeMessages(1);
        }
        if (handler.hasMessages(2)) {
            handler.removeMessages(2);
        }
    }

    /* access modifiers changed from: private */
    public static void initAccess() {
        initAccess(ZKLauncher.sDoor1VerifyType);
    }

    private static void initAccess(int i) {
        if (ZKVerController.getInstance().getState() != ZKVerConState.STATE_DELAY || request.isStressPasswordAlarmOn() || request.isStressFingerAlarmOn()) {
            Response response = new Response();
            response.setDoorOpenType(DoorOpenType.NONE);
            response.setInDoorEffectiveTimePeriod(true);
            boolean isRemoteAlarmOn = request.isRemoteAlarmOn();
            response.setRemoteAlarmOn(isRemoteAlarmOn);
            if (!isRemoteAlarmOn) {
                chain.addInterceptors(new DoorAlarmInterceptor());
            }
            loadSettings(context);
            chain.interceptor(request, response);
            switch (AnonymousClass1.$SwitchMap$com$zktechnology$android$acc$DoorOpenType[response.getDoorOpenType().ordinal()]) {
                case 1:
                case 2:
                case 3:
                    if (!response.isOpenDoorDuplicate()) {
                        openDoor(response.getLockDriveDurationInSeconds(), false, false);
                        break;
                    }
                    break;
                case 4:
                    openDoor(response.getLockDriveDurationInSeconds(), false, true);
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    openDoor(response.getLockDriveDurationInSeconds(), true, true);
                    break;
                case 9:
                    closeLock();
                    break;
            }
            LogUtils.i("AccDoor", "initAccess checkDoorSensor");
            checkDoorSensor(response);
            if (response.isLocalAlarmOn()) {
                LogUtils.i("AccDoor", "playLocalAlarm1");
                playLocalAlarm();
            } else {
                cancelLocalAlarm();
            }
            if (!response.isRemoteAlarmOn()) {
                cancelRemoteAlarm();
            } else if (!handler.hasMessages(2)) {
                playRemoteAlarm();
            }
            updateDeviceEvent(response, i);
            mSingleService.submit(updateDeviceStateTask);
        }
    }

    /* renamed from: com.zktechnology.android.acc.DoorAccessManager$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zktechnology$android$acc$DoorOpenType;

        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|(3:17|18|20)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.zktechnology.android.acc.DoorOpenType[] r0 = com.zktechnology.android.acc.DoorOpenType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$zktechnology$android$acc$DoorOpenType = r0
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.LOCAL_OPEN     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$zktechnology$android$acc$DoorOpenType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.REMOTE_OPEN     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$zktechnology$android$acc$DoorOpenType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.REMOTE_OPEN_EXIT_BUTTON     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$zktechnology$android$acc$DoorOpenType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.AUX_IN_REMOTE_OPEN     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$zktechnology$android$acc$DoorOpenType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.LOCAL_OPEN_ALWAYS     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$zktechnology$android$acc$DoorOpenType     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.FIRST_OPEN_ALWAYS     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$com$zktechnology$android$acc$DoorOpenType     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.REMOTE_OPEN_ALWAYS     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = $SwitchMap$com$zktechnology$android$acc$DoorOpenType     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.AUX_IN_OPEN_ALWAYS     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = $SwitchMap$com$zktechnology$android$acc$DoorOpenType     // Catch:{ NoSuchFieldError -> 0x006c }
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.REMOTE_CLOSE     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.acc.DoorAccessManager.AnonymousClass1.<clinit>():void");
        }
    }

    public void urgentOpen() {
        if (DoorAccessDao.isDoorAlwaysOpen(request.getDoorOpenType())) {
            cancelLocalAlarm();
            cancelRemoteAlarm();
            return;
        }
        request.setAccessVerified(true);
        request.setDoorSensorOpened(false);
        LogUtils.i("AccDoor", "chain.clearInterceptors()3");
        chain.clearInterceptors();
        chain.addInterceptors(new DoorOpenInterceptor());
        chain.addInterceptors(new DoorAlarmInterceptor());
        Response response = new Response();
        response.setInDoorEffectiveTimePeriod(true);
        response.setRemoteAlarmOn(request.isRemoteAlarmOn());
        loadSettings(context);
        chain.interceptor(request, response);
        openDoor(response.getLockDriveDurationInSeconds(), false, false);
        LogUtils.i("AccDoor", "urgentOpen checkDoorSensor");
        checkDoorSensor(response);
        if (response.isLocalAlarmOn()) {
            LogUtils.i("AccDoor", "playLocalAlarm2");
            playLocalAlarm();
        } else {
            cancelLocalAlarm();
        }
        if (!response.isRemoteAlarmOn()) {
            cancelRemoteAlarm();
        } else if (!handler.hasMessages(2)) {
            playRemoteAlarm();
        }
        updateDeviceEvent(response, 4);
        mSingleService.submit(updateDeviceStateTask);
    }

    private static void checkDoorSensor(Response response) {
        LogUtils.i("AccDoor", "checkDoorSensor start");
        if (response.isStartLocalAlarmDelay()) {
            response.setStartLocalAlarmDelay(false);
            boolean hasMessages = handler.hasMessages(1);
            LogUtils.i("AccDoor", "checkDoorSensor hasLocalAlarmMessage: " + hasMessages);
            if (!hasMessages) {
                handler.sendEmptyMessageDelayed(1, ((long) request.getDoorSensorLocalAlarmDelay()) * 1000);
            }
        }
        if (response.isCancelRemoteAlarm()) {
            handler.removeMessages(2);
        }
        if (request.isCheckLocalAlarm()) {
            request.setCheckLocalAlarm(false);
            if (handler.hasMessages(1)) {
                handler.removeMessages(1);
                handleLocalAlarmMessage();
            }
        }
    }

    private static void openLock() {
        if (Math.abs(SystemClock.elapsedRealtime() - lastOpenTime) > 300) {
            mSingleService.submit(openLockTask);
            lastOpenTime = SystemClock.elapsedRealtime();
        }
    }

    private static class OpenLockTask implements Runnable {
        private OpenLockTask() {
        }

        /* synthetic */ OpenLockTask(AnonymousClass1 r1) {
            this();
        }

        public void run() {
            DoorManagerHelper.getInstance().openDoor();
        }
    }

    /* access modifiers changed from: private */
    public static void closeLock() {
        mSingleService.submit(closeLockTask);
        LogUtils.i("AccDoor", "[CloseLock] the lock was closed.");
    }

    private static class CloseLockTask implements Runnable {
        private CloseLockTask() {
        }

        /* synthetic */ CloseLockTask(AnonymousClass1 r1) {
            this();
        }

        public void run() {
            DoorManagerHelper.getInstance().closeDoor();
        }
    }

    /* access modifiers changed from: private */
    public static void playRemoteAlarm() {
        if (request.getExtAlarmSwitch() == 1) {
            mSingleService.submit(playRemoteAlarmTask);
        } else {
            cancelRemoteAlarm();
        }
    }

    private static class PlayRemoteAlarmTask implements Runnable {
        private PlayRemoteAlarmTask() {
        }

        /* synthetic */ PlayRemoteAlarmTask(AnonymousClass1 r1) {
            this();
        }

        public void run() {
            try {
                DoorAccessManager.request.setRemoteAlarmOn(true);
                DoorManagerHelper.getInstance().playAlarm();
                DoorAccessManager.sendAlarmState(true);
                boolean unused = DoorAccessManager.remoteAlarmIsRinging = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public static void cancelRemoteAlarm() {
        mSingleService.submit(cancelRemoteAlarmTask);
    }

    private static class CancelRemoteAlarmTask implements Runnable {
        private CancelRemoteAlarmTask() {
        }

        /* synthetic */ CancelRemoteAlarmTask(AnonymousClass1 r1) {
            this();
        }

        public void run() {
            try {
                if (DoorAccessManager.zkSharedUtil.getInt("aux_alarm_on", 0) == 0) {
                    DoorAccessManager.request.setRemoteAlarmOn(false);
                    DoorManagerHelper.getInstance().cancelAlarm();
                    if (!DoorAccessManager.localAlarmIsRinging) {
                        DoorAccessManager.sendAlarmState(false);
                    }
                    boolean unused = DoorAccessManager.remoteAlarmIsRinging = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isLockOpen() {
        try {
            return DoorManagerHelper.getInstance().getLockState();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static void sendAlarmState(boolean z) {
        if (z) {
            Intent intent = new Intent();
            intent.setAction(ACTION_ALARM_OPEN);
            context.sendBroadcast(intent);
            return;
        }
        Intent intent2 = new Intent();
        intent2.setAction(ACTION_ALARM_CLOSE);
        context.sendBroadcast(intent2);
    }

    private static boolean playLocalAlarm() {
        OpLogReceiver.sendBroadcast(context, new Intent(OpLogReceiver.ACTION_ALARM));
        int localAlarmSwitch = request.getLocalAlarmSwitch();
        LogUtils.i("AccDoor", "playLocalAlarm localAlarmSwitch: " + localAlarmSwitch);
        if (localAlarmSwitch != 1) {
            cancelLocalAlarm();
            return false;
        } else if (!isReadyToPlay || playResourceID <= 0) {
            return false;
        } else {
            sendAlarmState(true);
            localAlarmIsRinging = true;
            playStreamID = soundPool.play(playResourceID, 1.0f, 1.0f, 0, -1, 1.0f);
            return true;
        }
    }

    private static void cancelLocalAlarm() {
        LogUtils.i("AccDoor", "cancelLocalAlarm start");
        if (!isReadyToPlay || playResourceID <= 0) {
            LogUtils.i("AccDoor", "Alarm cannot be cancelled, isReadyToPlay: " + isReadyToPlay + " playResourceID: " + playResourceID);
            return;
        }
        Request request2 = request;
        if (request2 == null || !request2.isTampered()) {
            Request request3 = request;
            if (request3 == null || !isDoorSensorStateConsistent(request3)) {
                Request request4 = request;
                if (request4 == null || request4.getLocalAlarmSwitch() == 1) {
                    Request request5 = request;
                    if (request5 != null && request5.isRemoteCancelAlarm()) {
                        forceStopLocalAlarm();
                        request.setRemoteCancelAlarm(false);
                        sendAlarmState(false);
                        return;
                    }
                    return;
                }
                forceStopLocalAlarm();
                return;
            }
            forceStopLocalAlarm();
            return;
        }
        LogUtils.i("AccDoor", "Tampered alarm cannot be cancelled");
    }

    private static void forceStopLocalAlarm() {
        SoundPool soundPool2 = soundPool;
        if (soundPool2 != null) {
            soundPool2.stop(playStreamID);
        }
        if (!remoteAlarmIsRinging) {
            sendAlarmState(false);
        }
        localAlarmIsRinging = false;
    }

    private void releaseLocalAlarm() {
        SoundPool soundPool2 = soundPool;
        if (soundPool2 != null) {
            soundPool2.stop(playStreamID);
            soundPool.release();
            soundPool = null;
        }
    }

    private static void openDoor(int i, boolean z, boolean z2) {
        LogUtils.i("AccDoor", String.format("Try to open lock, the drive duration is %d, always open flag is %s", new Object[]{Integer.valueOf(i), Boolean.valueOf(z)}));
        openLock();
        if (!z && i > 0) {
            if (handler.hasMessages(0)) {
                handler.removeMessages(0);
            }
            if (request.getGateSwitchMode() != 1 || z2) {
                handler.sendEmptyMessageDelayed(0, ((long) i) * 1000);
            } else {
                handler.sendEmptyMessageDelayed(0, 800);
            }
        }
    }

    private void preAccess(UserInfo userInfo, DoorAccessResponse doorAccessResponse, ZKVerifyInfo zKVerifyInfo, boolean z) {
        request.setAccessVerified(z);
        request.setDoorSensorOpened(false);
        LogUtils.i("AccDoor", "chain.clearInterceptors()4");
        chain.clearInterceptors();
        DoorOpenType doorOpenType = doorAccessResponse.getDoorOpenType();
        DoorOpenType doorOpenType2 = request.getDoorOpenType();
        if (doorOpenType2 != DoorOpenType.AUX_IN_OPEN_ALWAYS) {
            if (doorOpenType2 != DoorOpenType.REMOTE_OPEN_ALWAYS) {
                if (doorOpenType == DoorOpenType.ADMIN_OPEN_ALWAYS) {
                    controlDoorAlwaysOpenChecker(true);
                    return;
                } else if (doorOpenType == DoorOpenType.ADMIN_CANCEL_OPEN_ALWAYS) {
                    controlDoorAlwaysOpenChecker(false);
                    return;
                }
            }
            if (doorOpenType != DoorOpenType.NONE && !DoorAccessDao.isDoorAlwaysOpen(doorOpenType2)) {
                request.setDoorOpenType(doorOpenType);
                if (doorOpenType == DoorOpenType.LOCAL_OPEN) {
                    chain.addInterceptors(new DoorOpenInterceptor());
                } else if (doorOpenType == DoorOpenType.LOCAL_OPEN_ALWAYS || doorOpenType == DoorOpenType.FIRST_OPEN_ALWAYS) {
                    chain.addInterceptors(new DoorAlwaysOpenInterceptor(doorOpenType));
                }
            }
            boolean isRemoteAlarmOn = doorAccessResponse.isRemoteAlarmOn();
            if (!isRemoteAlarmOn && request.isRemoteAlarmOn() && !isDoorSensorStateConsistent(request)) {
                isRemoteAlarmOn = true;
            }
            request.setRemoteAlarmOn(isRemoteAlarmOn);
            boolean isStressPasswordAlarmOn = doorAccessResponse.isStressPasswordAlarmOn();
            request.setStressPasswordAlarmOn(isStressPasswordAlarmOn);
            if (isStressPasswordAlarmOn) {
                request.setDoorAlarmType(DoorAlarmType.STRESS_PASSWORD);
            }
            boolean isStressFingerAlarmOn = doorAccessResponse.isStressFingerAlarmOn();
            request.setStressFingerAlarmOn(isStressFingerAlarmOn);
            if (isStressFingerAlarmOn) {
                request.setDoorAlarmType(DoorAlarmType.STRESS_FINGER);
            }
            String firstOpenUserPin = doorAccessResponse.getFirstOpenUserPin();
            if (!TextUtils.isEmpty(firstOpenUserPin)) {
                request.setFirstOpenUserPin(firstOpenUserPin);
            }
            ZKVerifyType verifyType = zKVerifyInfo.getVerifyType();
            int verify_Type = (userInfo == null || DBManager.getInstance().getIntOption("AccessPersonalVerification", 0) != 1) ? -1 : userInfo.getVerify_Type();
            if (verify_Type == -1) {
                verify_Type = ZKLauncher.sDoor1VerifyType;
            }
            int doorVerifyType = VerifyTypeUtil.getDoorVerifyType(verifyType, verify_Type);
            if (chain.getInterceptorCount() == 0) {
                processAccess(doorVerifyType, z, isRemoteAlarmOn);
                return;
            }
            LogUtils.i("AccDoor", "initAccess3");
            initAccess(doorVerifyType);
        }
    }

    private void processAccess(int i, boolean z, boolean z2) {
        if (DoorAccessDao.isDoorAlwaysOpen(request.getDoorOpenType())) {
            cancelLocalAlarm();
            cancelRemoteAlarm();
            if (request.isStressFingerAlarmOn() || request.isStressPasswordAlarmOn()) {
                playRemoteAlarm();
                return;
            }
            return;
        }
        request.setAccessVerified(z);
        request.setDoorSensorOpened(false);
        LogUtils.i("AccDoor", "chain.clearInterceptors()5");
        chain.clearInterceptors();
        chain.addInterceptors(new DoorOpenInterceptor());
        if (!z2) {
            chain.addInterceptors(new DoorAlarmInterceptor());
        }
        LogUtils.i("AccDoor", "initAccess4");
        initAccess(i);
    }

    public DoorAccessResponse accessVerify(ZKAccDao zKAccDao, UserInfo userInfo, ZKVerifyInfo zKVerifyInfo, boolean z, Date date) {
        this.accDao = zKAccDao;
        DoorAccessResponse check = DoorAccessChecker.check(context, zKAccDao, userInfo, zKVerifyInfo, date);
        if (TextUtils.isEmpty(check.getErrorMessage())) {
            preAccess(userInfo, check, zKVerifyInfo, z);
        }
        boolean z2 = false;
        LogUtils.verifyFormatLog("accessVerify response:%s", check.toString());
        LogUtils.verifyFormatLog("accessVerify verifyInfo:%s", zKVerifyInfo.toString());
        if (ZKLauncher.maskDetectionFunOn == 1 && ZKLauncher.enalbeMaskDetection == 1) {
            z2 = true;
        }
        if ((check.getErrorCode() == 68 || (check.getErrorCode() == 69 && z2)) && ZKLauncher.enableTriggerAlarm == 1) {
            sendAlarmState(true);
            DoorManagerHelper.getInstance().playAlarm();
            if (ZKLauncher.externalAlarmDelay > 0) {
                if (handler.hasMessages(5)) {
                    handler.removeMessages(5);
                }
                handler.sendEmptyMessageDelayed(5, ((long) ZKLauncher.externalAlarmDelay) * 1000);
            }
        }
        return check;
    }

    public boolean isAccFirstOpen() {
        ZKAccDao zKAccDao = this.accDao;
        boolean z = false;
        if (zKAccDao == null || zKAccDao.getDoor1FirstCardOpenDoor() == 0) {
            return false;
        }
        if (this.accDao.checkDoor1CancelKeepOpenDay()) {
            String firstOpenUserPin = request.getFirstOpenUserPin();
            if (!TextUtils.isEmpty(firstOpenUserPin)) {
                int accFirstOpen = this.accDao.getAccFirstOpen(firstOpenUserPin);
                if (accFirstOpen > 0 && DoorAccessDao.isInAccTimeZoneExtension(accFirstOpen, true)) {
                    z = true;
                }
                if (!z) {
                    request.setFirstOpenUserPin((String) null);
                }
            }
        }
        return z;
    }

    public DoorOpenType getDoorOpenType() {
        Request request2 = request;
        if (request2 != null) {
            return request2.getDoorOpenType();
        }
        return DoorOpenType.NONE;
    }

    public void openDoorAlways(boolean z) {
        if (z) {
            if (handler.hasMessages(0)) {
                handler.removeMessages(0);
            }
            request.setAccessVerified(true);
            request.setDoorSensorOpened(false);
            LogUtils.i("AccDoor", "chain.clearInterceptors()6");
            chain.clearInterceptors();
            chain.addInterceptors(new DoorAlwaysOpenInterceptor());
            LogUtils.i("AccDoor", "initAccess5");
            initAccess();
            AccPush.getInstance().pushRealEvent(5);
            return;
        }
        DoorOpenType doorOpenType = request.getDoorOpenType();
        if (doorOpenType == DoorOpenType.LOCAL_OPEN_ALWAYS || doorOpenType == DoorOpenType.FIRST_OPEN_ALWAYS) {
            closeLock();
            AccPush.getInstance().pushRealEvent(AccEventType.DOOR_KEEP_OPEN_END);
            request.setFirstOpenUserPin((String) null);
            isAlreadyFirstAlwaysOpenDoor = false;
        }
        request.setDoorOpenType(DoorOpenType.NONE);
    }

    public void firstOpenDoorAlways() {
        if (handler.hasMessages(0)) {
            handler.removeMessages(0);
        }
        request.setAccessVerified(true);
        request.setDoorSensorOpened(false);
        LogUtils.i("AccDoor", "chain.clearInterceptors()7");
        chain.clearInterceptors();
        chain.addInterceptors(new DoorAlwaysOpenInterceptor(DoorOpenType.FIRST_OPEN_ALWAYS));
        LogUtils.i("AccDoor", "initAccess6");
        initAccess();
    }

    public void closeDoor() {
        request.setDoorOpenType(DoorOpenType.NONE);
        closeLock();
    }

    private static void updateDeviceEvent(Response response, int i) {
        int i2;
        DoorOpenType doorOpenType = response.getDoorOpenType();
        if (doorOpenType == DoorOpenType.REMOTE_OPEN_EXIT_BUTTON) {
            i = DoorVerifyCombination.OTHERS.getValue();
            ZKAccDao.getInstance(context).addAccAttLog(ZKLauncher.sInOutState, ZKLauncher.sDoorId, AccEventType.BUTTON_OPEN_DOOR, i);
        }
        if ((doorOpenType == DoorOpenType.REMOTE_OPEN || doorOpenType == DoorOpenType.REMOTE_OPEN_EXIT_BUTTON) && !response.isInDoorEffectiveTimePeriod()) {
            ZKAccDao.getInstance(context).addAccAttLog(ZKLauncher.sInOutState, ZKLauncher.sDoorId, 21, i);
        }
        if (request.isUnsafeOpened() && request.getCurrentDoorSensorState().getValue() == 1 && ((i2 = lastTimeSensorState) == 0 || i2 != sensorStateChangeFrequency)) {
            AccPush.getInstance().pushRealEvent(102);
        }
        lastTimeSensorState = sensorStateChangeFrequency;
        if (doorOpenType == DoorOpenType.AUX_IN_REMOTE_OPEN || doorOpenType == DoorOpenType.AUX_IN_OPEN_ALWAYS) {
            AccPush.getInstance().pushRealEvent(AccEventType.AUXIN_CLOSE);
        }
    }

    /* access modifiers changed from: private */
    public static void updateDeviceState() {
        int value = request.getCurrentDoorSensorState().getValue();
        int i = isLockOpen() ? 1 : -1;
        DoorAlarmType doorAlarmType = DoorAlarmType.NONE;
        if (request.isTampered() && DBManager.getInstance().getIntOption(ZKDBConfig.TAMPER_ALARM_FUN, 1) == 1) {
            doorAlarmType = DoorAlarmType.ANTI_TAMPER;
        }
        if (request.isUnsafeOpened()) {
            doorAlarmType = DoorAlarmType.UNSAFE_OPEN;
        }
        if (request.isStressPasswordAlarmOn()) {
            doorAlarmType = DoorAlarmType.STRESS_PASSWORD;
        }
        if (request.isStressFingerAlarmOn()) {
            doorAlarmType = DoorAlarmType.STRESS_FINGER;
        }
        if (request.isDoorSensorTimeout()) {
            doorAlarmType = DoorAlarmType.SENSOR_TIMEOUT;
        }
        AccPush.getInstance().pushDoorSensorEvent(value, i, doorAlarmType.getValue());
    }

    private static class LocalMessageHandler extends Handler {
        private WeakReference reference;

        public LocalMessageHandler(Context context) {
            this.reference = new WeakReference(context);
        }

        public void handleMessage(Message message) {
            if (message.what == 0) {
                LogUtils.i("AccDoor", "[CloseLock] the lock close event was closed.");
                DoorAccessManager.closeLock();
                if (DoorAccessManager.isDoorSensorStateConsistent(DoorAccessManager.request)) {
                    DoorAccessManager.handler.removeMessages(1);
                    DoorAccessManager.request.setAccessVerified(false);
                } else if (!DoorAccessManager.handler.hasMessages(1)) {
                    DoorAccessManager.request.setAccessVerified(false);
                }
            } else if (message.what == 1) {
                DoorAccessManager.request.setDoorSensorTimeout(true);
                sendDoorOpenTimeoutEvent();
                DoorAccessManager.request.setDoorAlarmType(DoorAlarmType.SENSOR_TIMEOUT);
                DoorAccessManager.handleLocalAlarmMessage();
            } else if (message.what == 2) {
                DoorAccessManager.playRemoteAlarm();
            } else if (message.what == 3) {
                SpeakerHelper.playSound(DoorAccessManager.context, "remote_alarm.ogg", true, "CH");
            } else if (message.what == 4) {
                DoorAccessManager.mSingleService.submit(DoorAccessManager.broadcastTask);
            } else if (message.what == 5) {
                DoorAccessManager.sendAlarmState(false);
                DoorManagerHelper.getInstance().cancelAlarm();
            }
            DoorAccessManager.mSingleService.submit(DoorAccessManager.updateDeviceStateTask);
        }

        private void sendDoorOpenTimeoutEvent() {
            if (DBManager.getInstance() != null) {
                AccPush.getInstance().pushRealEvent(28);
            }
        }
    }

    private static class BroadcastTask implements Runnable {
        private BroadcastTask() {
        }

        /* synthetic */ BroadcastTask(AnonymousClass1 r1) {
            this();
        }

        public void run() {
            Intent access$500 = DoorAccessManager.broadcastIntent;
            String action = access$500.getAction();
            if (!TextUtils.isEmpty(action) && !DoorAccessManager.checkActionFilter(action)) {
                LogUtils.i("AccDoor", "chain.clearInterceptors()8");
                DoorAccessManager.chain.clearInterceptors();
                if (action.equalsIgnoreCase(DoorAccessManager.ACTION_AUX_INPUT_STATE_CHANGE)) {
                    if (DoorAccessDao.getAuxFunOn() != 0) {
                        int intExtra = access$500.getIntExtra("state", 0);
                        if (intExtra == 0) {
                            AccPush.getInstance().pushRealEvent(AccEventType.AUXIN_OPEN);
                        } else if (intExtra == 1) {
                            int auxInOption = DoorAccessDao.getAuxInOption();
                            if (auxInOption == 1 || auxInOption == 3) {
                                int auxInKeepTime = DoorAccessDao.getAuxInKeepTime();
                                if (auxInKeepTime == 255) {
                                    DoorAccessManager.request.setDoorOpenType(DoorOpenType.AUX_IN_OPEN_ALWAYS);
                                    DoorAccessManager.chain.addInterceptors(new DoorAlwaysOpenInterceptor(DoorOpenType.AUX_IN_OPEN_ALWAYS));
                                } else if (auxInKeepTime > 0) {
                                    DoorAccessManager.request.setRemoteLockDriveDurationInSeconds(auxInKeepTime);
                                    DoorAccessManager.request.setDoorOpenType(DoorOpenType.AUX_IN_REMOTE_OPEN);
                                    DoorAccessManager.chain.addInterceptors(new DoorRemoteOpenInterceptor());
                                }
                            }
                            if (auxInOption == 2 || auxInOption == 3) {
                                DoorAccessManager.request.setRemoteAlarmOn(true);
                                if (auxInOption == 2) {
                                    DoorAccessManager.chain.addInterceptors(new DoorAlwaysOpenInterceptor(DoorOpenType.NONE));
                                }
                                DoorAccessManager.zkSharedUtil.putInt("aux_alarm_on", 1);
                            }
                        }
                    } else {
                        return;
                    }
                } else if (action.equalsIgnoreCase(DoorAccessManager.ACTION_OPEN_DOOR)) {
                    int intExtra2 = access$500.getIntExtra("delay", 0);
                    if (intExtra2 > 0) {
                        DoorAccessManager.request.setRemoteLockDriveDurationInSeconds(intExtra2);
                    }
                    DoorAccessManager.request.setDoorOpenType(DoorOpenType.REMOTE_OPEN);
                    DoorAccessManager.request.setAccessVerified(true);
                    if (access$500.getStringExtra(ZKStaffBaseActivity.HomeWatcherReceiver.SYSTEM_DIALOG_REASON_KEY).equals(DoorAccessManager.EXIT_BUTTON)) {
                        DoorAccessManager.request.setDoorOpenType(DoorOpenType.REMOTE_OPEN_EXIT_BUTTON);
                        DoorAccessManager.chain.addInterceptors(new DoorDuplicateOpenInterceptor());
                    }
                    DoorAccessManager.chain.addInterceptors(new DoorEffectiveTimePeriodInterceptor());
                    DoorAccessManager.chain.addInterceptors(new DoorRemoteOpenInterceptor());
                } else if (action.equalsIgnoreCase(DoorAccessManager.ACTION_NORMALLY_OPEN)) {
                    DoorAccessManager.request.setDoorOpenType(DoorOpenType.REMOTE_OPEN_ALWAYS);
                    DoorAccessManager.request.setAccessVerified(true);
                    DoorAccessManager.sendAlwaysOpenState();
                    DoorAccessManager.chain.addInterceptors(new DoorAlwaysOpenInterceptor(DoorOpenType.REMOTE_OPEN_ALWAYS));
                } else if (action.equalsIgnoreCase(DoorAccessManager.ACTION_CLOSE_DOOR)) {
                    DoorAccessManager.request.setDoorOpenType(DoorOpenType.REMOTE_CLOSE);
                    DoorAccessManager.chain.addInterceptors(new DoorRemoteCloseInterceptor());
                } else if (action.equalsIgnoreCase("com.zkteco.android.core.ACTION_ALWAYS_OPEN_CHANGE")) {
                    if (DoorAccessDao.checkDoor1CancelKeepOpenDay(access$500.getStringExtra("Door1CancelKeepOpenDay"))) {
                        DoorAccessManager.controlDoorAlwaysOpenChecker(true);
                    } else {
                        DoorAccessManager.isAlreadyFirstAlwaysOpenDoor = false;
                        DoorAccessManager.controlDoorAlwaysOpenChecker(false);
                    }
                } else if (action.equalsIgnoreCase(DoorAccessManager.ACTION_TAMPER_CHANGE)) {
                    if (DBManager.getInstance().getIntOption(ZKDBConfig.TAMPER_ALARM_FUN, 1) == 1) {
                        int intExtra3 = access$500.getIntExtra("state", 0);
                        if (intExtra3 == 1 && (DoorAccessManager.request.getLocalAlarmSwitch() == 1 || DoorAccessManager.request.getExtAlarmSwitch() == 1)) {
                            AccPush.getInstance().pushRealEvent(100);
                            try {
                                HubProtocolManager hubProtocolManager = new HubProtocolManager(DoorAccessManager.context);
                                hubProtocolManager.sendHubAction(18, hubProtocolManager.convertPushInit(), "0");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        DoorAccessManager.request.setTamperState(intExtra3);
                        DoorAccessManager.request.setAccessVerified(false);
                        DoorAccessManager.chain.addInterceptors(new DoorAlarmInterceptor());
                    }
                } else if (action.equalsIgnoreCase(DoorAccessManager.ACTION_DOOR_SENSOR_CHANGE)) {
                    DoorAccessManager.request.setLastDoorSensorState(DoorAccessManager.request.getCurrentDoorSensorState());
                    int intExtra4 = access$500.getIntExtra("state", -1);
                    DoorAccessManager.access$3608();
                    DoorAccessManager.sendChangeSensorIcon(intExtra4);
                    DoorAccessManager.request.setCurrentDoorSensorState(DoorSensorState.fromInteger(intExtra4));
                    DoorAccessManager.chain.addInterceptors(new DoorAlarmInterceptor());
                } else if (action.equalsIgnoreCase(DoorAccessManager.ACTION_REMOTE_CANCEL_ALARM)) {
                    if (access$500.getIntExtra("state", -1) == 0) {
                        DoorAccessManager.zkSharedUtil.putInt("aux_alarm_on", 0);
                        DoorAccessManager.request.setRemoteCancelAlarm(true);
                        DoorAccessManager.chain.addInterceptors(new DoorAlarmInterceptor());
                    }
                } else if (action.equalsIgnoreCase(DoorAccessManager.ACTION_OPTION_CHANGE)) {
                    if (DoorAccessManager.isLocalAlarmStateChanged()) {
                        DoorAccessManager.chain.addInterceptors(new DoorAlarmInterceptor(true));
                    } else if (DoorAccessManager.checkSensorType()) {
                        DoorAccessManager.processDoorSensorTypeChange();
                        DoorAccessManager.chain.addInterceptors(new DoorAlarmInterceptor());
                    } else if (DoorAccessManager.isExtAlarmStateChanged()) {
                        DoorAccessManager.chain.addInterceptors(new DoorAlarmInterceptor());
                    }
                    DoorAccessManager.request.setAccessVerified(false);
                    if (DoorAccessDao.getAuxFunOn() == 0) {
                        DoorAccessManager.zkSharedUtil.putInt("aux_alarm_on", 0);
                        if (DoorAccessManager.request.getDoorAlarmType() == null || DoorAccessManager.request.getDoorAlarmType().getValue() == 0) {
                            DoorAccessManager.cancelRemoteAlarm();
                        }
                    }
                } else if (action.equalsIgnoreCase(DoorAccessManager.CLOSE_ALL_ALARM)) {
                    DoorAccessManager.sendAlarmState(false);
                    DoorManagerHelper.getInstance().cancelAlarm();
                }
                if (DoorAccessManager.chain.getInterceptorCount() > 0) {
                    LogUtils.i("AccDoor", "initAccess7");
                    DoorAccessManager.initAccess();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void handleLocalAlarmMessage() {
        request.setAccessVerified(false);
        if (!isDoorSensorStateConsistent(request)) {
            LogUtils.i("AccDoor", "playLocalAlarm3");
            playLocalAlarm();
            int doorSensorRemoteAlarmDelay = request.getDoorSensorRemoteAlarmDelay();
            if (ZKLauncher.sAccessRuleType == 1) {
                handler.removeMessages(2);
                handler.sendEmptyMessage(2);
            } else if (doorSensorRemoteAlarmDelay >= 0) {
                handler.removeMessages(2);
                handler.sendEmptyMessageDelayed(2, ((long) request.getDoorSensorRemoteAlarmDelay()) * 1000);
            }
        }
    }

    public void releaseDevice() {
        releaseLocalAlarm();
        if (doorAlwaysOpenChecker != null) {
            controlDoorAlwaysOpenChecker(false);
            doorAlwaysOpenChecker = null;
        }
        if (context != null && this.mRegisterRemoteDoorBR.get()) {
            context.unregisterReceiver(remoteDoorReceiver);
            this.mRegisterRemoteDoorBR.set(false);
        }
        LocalMessageHandler localMessageHandler = handler;
        if (localMessageHandler != null) {
            localMessageHandler.removeCallbacksAndMessages((Object) null);
            handler = null;
        }
    }
}
