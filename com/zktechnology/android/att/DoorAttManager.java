package com.zktechnology.android.att;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.zktechnology.android.acc.DoorSensorState;
import com.zktechnology.android.acc.DoorSensorType;
import com.zktechnology.android.acc.base.Interceptors.DoorDuplicateOpenInterceptor;
import com.zktechnology.android.att.tasks.ContinuousStateTask;
import com.zktechnology.android.att.types.AttAlarmType;
import com.zktechnology.android.att.types.AttDoorType;
import com.zktechnology.android.helper.DoorManagerHelper;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.dao.ZKVerDao;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zktechnology.android.view.controller.SensorStateController;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class DoorAttManager {
    private static final int AAFO = 2;
    private static final String ACTION_ALARM_CLOSE = "com.zkteco.android.core.ACTION_ALARM_CLOSE";
    private static final String ACTION_ALARM_OPEN = "com.zkteco.android.core.ACTION_ALARM_OPEN";
    public static final String ACTION_ALWAYS_OPEN_CHANGE = "com.zkteco.android.core.ACTION_ALWAYS_OPEN_CHANGE";
    private static final String ACTION_AUX_INPUT_STATE_CHANGE = "com.zkteco.android.core.ACTION_AUX_INPUT_CHANGE";
    private static final String ACTION_DOOR_SENSOR_CHANGE = "com.zkteco.android.core.ACTION_DOOR_SENSOR_CHANGE";
    private static final String ACTION_OPEN_DOOR = "com.zkteco.android.core.ACTION_OPEN";
    private static final String ACTION_OPTION_CHANGE = "com.zkteco.firmware.action.ACC_SENIOR_OPTION_CHANGE";
    private static final String ACTION_REMOTE_CANCEL_ALARM = "com.zkteco.android.core.ACTION_REMOTE_CANCEL_ALARM";
    private static final String ACTION_TAMPER_CHANGE = "com.zkteco.android.core.ACTION_TAMPER_CHANGE";
    private static final String CLOSE_ALL_ALARM = "com.zkteco.android.core.CLOSE_ALL_ALARM";
    private static final String EXIT_BUTTON = "EXIT_BUTTON";
    private static final int EXTERNAL = 1;
    public static final int INIT_FINISH = 4;
    private static final int LOCAL = 0;
    public static final int MESSAGE_ALARMDELAY = 3;
    public static final int MESSAGE_AUX = 0;
    public static final int MESSAGE_CLOSE_EXTERNAL_ALARM = 6;
    public static final int MESSAGE_DURESS = 5;
    public static final int MESSAGE_EXIT_BUTTON = 7;
    public static final int MESSAGE_SDELAY = 2;
    public static final int MESSAGE_VEL = 1;
    private static final Map<Long, List<Integer>> accGroupIdMap = new HashMap();
    private static final List<String> accGroupPinList = new ArrayList();
    /* access modifiers changed from: private */
    public static final List<AttAlarmType> attAlarmTypeList = new ArrayList();
    /* access modifiers changed from: private */
    public static final List<AttDoorType> attDoorTypeList = new ArrayList();
    public static long lastTime;
    public static DoorAttManager mInstance;
    public static String verifyInput;
    Future closeLockFuture = null;
    Runnable closeLockTask = new Runnable() {
        public void run() {
            DoorManagerHelper.getInstance().closeDoor();
            DoorAttManager.this.closeLockFuture = null;
        }
    };
    public Context context;
    private ContinuousStateTask continuousStateTask;
    /* access modifiers changed from: private */
    public DoorSensorState doorSenserState;
    /* access modifiers changed from: private */
    public boolean externalAlarmIsRinging = false;
    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 0) {
                if (DoorAttManager.attDoorTypeList.contains(AttDoorType.AUXOPENDOOR)) {
                    DoorAttManager.attDoorTypeList.remove(AttDoorType.AUXOPENDOOR);
                }
                DoorAttManager.this.controlDoor(false);
            }
            if (message.what == 1) {
                DoorAttManager.this.controlDoor(false);
                if (DoorAttManager.attDoorTypeList.contains(AttDoorType.VERIFYOPENDOOR)) {
                    DoorAttManager.attDoorTypeList.remove(AttDoorType.VERIFYOPENDOOR);
                }
            }
            if (message.what == 7) {
                DoorAttManager.this.controlDoor(false);
                if (DoorAttManager.attDoorTypeList.contains(AttDoorType.EXIT_BUTTON)) {
                    DoorAttManager.attDoorTypeList.remove(AttDoorType.EXIT_BUTTON);
                }
            }
            if (message.what == 2) {
                DoorAttManager doorAttManager = DoorAttManager.this;
                if (!doorAttManager.isDoorSensorStateConsistent(doorAttManager.doorSenserState)) {
                    DoorAttManager.this.controlAlarm(0, true);
                    if (!DoorAttManager.attAlarmTypeList.contains(AttAlarmType.SENSORDELAY)) {
                        DoorAttManager.attAlarmTypeList.add(AttAlarmType.SENSORDELAY);
                    }
                }
            }
            if (message.what == 3) {
                DoorAttManager.this.controlAlarm(1, true);
                if (!DoorAttManager.attAlarmTypeList.contains(AttAlarmType.SENSORALARMDELAY)) {
                    DoorAttManager.attAlarmTypeList.add(AttAlarmType.SENSORALARMDELAY);
                }
            }
            if (message.what == 4) {
                DoorAttManager.this.setContinuousState();
            }
            if (message.what == 5) {
                DoorAttManager.this.controlAlarm(1, true);
                if (!DoorAttManager.attAlarmTypeList.contains(AttAlarmType.DURESSALARM)) {
                    DoorAttManager.attAlarmTypeList.add(AttAlarmType.DURESSALARM);
                }
            }
            if (message.what == 6) {
                DoorAttManager.this.sendAlarmState(false);
                DoorManagerHelper.getInstance().cancelAlarm();
            }
        }
    };
    Runnable initSomething = new Runnable() {
        public void run() {
            DoorAttManager.this.initSoundPool();
            DoorAttManager.this.initAttParameter();
            DoorAttManager.this.handler.sendEmptyMessage(4);
        }
    };
    /* access modifiers changed from: private */
    public boolean isReadyToPlay;
    /* access modifiers changed from: private */
    public boolean localAlarmIsRinging = false;
    private AtomicBoolean mRegisterAttParamBR = new AtomicBoolean(false);
    private ExecutorService mSingleService = Executors.newSingleThreadExecutor();
    private long nowNum;
    Future openLockFuture = null;
    Runnable openLockTask = new Runnable() {
        public void run() {
            DoorManagerHelper.getInstance().openDoor();
            DoorAttManager.this.openLockFuture = null;
        }
    };
    /* access modifiers changed from: private */
    public int playResourceID;
    /* access modifiers changed from: private */
    public int playStreamID;
    BroadcastReceiver remoteAttParameterReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.zkteco.android.core.CLOSE_ALL_ALARM")) {
                DoorAttManager.this.sendAlarmState(false);
                DoorManagerHelper.getInstance().cancelAlarm();
            }
            if (intent.getAction().equals("com.zkteco.android.core.ACTION_TAMPER_CHANGE") && DBManager.getInstance().getIntOption(ZKDBConfig.TAMPER_ALARM_FUN, 1) == 1) {
                if (intent.getIntExtra("state", 0) == 1) {
                    try {
                        HubProtocolManager hubProtocolManager = new HubProtocolManager(context);
                        hubProtocolManager.sendHubAction(18, hubProtocolManager.convertPushInit(), "0");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (intent.getIntExtra("state", 0) == 1) {
                    if (!DoorAttManager.attAlarmTypeList.contains(AttAlarmType.TAMPER)) {
                        DoorAttManager.attAlarmTypeList.add(AttAlarmType.TAMPER);
                    }
                    DoorAttManager.this.controlAlarm(0, true);
                    DoorAttManager.this.controlAlarm(1, true);
                } else {
                    if (DoorAttManager.attAlarmTypeList.contains(AttAlarmType.TAMPER)) {
                        DoorAttManager.attAlarmTypeList.remove(AttAlarmType.TAMPER);
                    }
                    DoorAttManager.this.controlAlarm(0, false);
                }
            }
            if (intent.getAction().equals("com.zkteco.android.core.ACTION_DOOR_SENSOR_CHANGE")) {
                int intExtra = intent.getIntExtra("state", -1);
                DoorAttManager.this.sendChangeSensorIcon(intExtra);
                DoorSensorState unused = DoorAttManager.this.doorSenserState = DoorSensorState.fromInteger(intExtra);
                if (!DoorAttManager.attDoorTypeList.contains(AttDoorType.ALWAYSOPENDOOR) && !DoorAttManager.attDoorTypeList.contains(AttDoorType.AUXOPENDOOR)) {
                    DoorAttManager doorAttManager = DoorAttManager.this;
                    if (doorAttManager.isDoorSensorStateConsistent(doorAttManager.doorSenserState)) {
                        DoorAttManager.this.controlAlarm(0, false);
                        if (DoorAttManager.this.handler.hasMessages(3)) {
                            DoorAttManager.this.handler.removeMessages(3);
                        }
                        DoorAttManager.attAlarmTypeList.remove(AttAlarmType.SENSORALARMDELAY);
                        DoorAttManager.attAlarmTypeList.remove(AttAlarmType.SENSORDELAY);
                        DoorAttManager.attAlarmTypeList.remove(AttAlarmType.ACCIDENTALLOPEN);
                    } else if (!DoorAttManager.attDoorTypeList.contains(AttDoorType.VERIFYOPENDOOR)) {
                        DoorAttManager.this.controlAlarm(0, true);
                        DoorAttManager.this.controlAlarm(1, true);
                        if (!DoorAttManager.attAlarmTypeList.contains(AttAlarmType.ACCIDENTALLOPEN)) {
                            DoorAttManager.attAlarmTypeList.add(AttAlarmType.ACCIDENTALLOPEN);
                        }
                    } else {
                        if (DoorAttManager.this.handler.hasMessages(2)) {
                            DoorAttManager.this.handler.removeMessages(2);
                        }
                        DoorAttManager.this.handler.sendEmptyMessageDelayed(2, (long) (AttParameter.getInstance().getDoorSensorDelay() * 1000));
                        if (DoorAttManager.this.handler.hasMessages(3)) {
                            DoorAttManager.this.handler.removeMessages(3);
                        }
                        DoorAttManager.this.handler.sendEmptyMessageDelayed(3, (long) ((AttParameter.getInstance().getDoorSensorDelay() + AttParameter.getInstance().getDoorSensorAlarmDelay()) * 1000));
                    }
                }
            }
            if (intent.getAction().equals("com.zkteco.android.core.ACTION_AUX_INPUT_CHANGE") && intent.getIntExtra("state", -1) == 1) {
                DoorAttManager.this.auxiliaryFunction();
            }
            if (intent.getAction().equals("com.zkteco.android.core.ACTION_OPEN") && DoorAttManager.EXIT_BUTTON.equals(intent.getStringExtra(ZKStaffBaseActivity.HomeWatcherReceiver.SYSTEM_DIALOG_REASON_KEY))) {
                if (!DoorDuplicateOpenInterceptor.DM10.equals(DBManager.getInstance().getStrOption(DBConfig.ACCESS_DEVICE, DoorDuplicateOpenInterceptor.MCU))) {
                    if (DoorAttManager.this.controlDoor(true)) {
                        if (!DoorAttManager.attDoorTypeList.contains(AttDoorType.EXIT_BUTTON)) {
                            DoorAttManager.attDoorTypeList.add(AttDoorType.EXIT_BUTTON);
                        }
                        if (DoorAttManager.this.handler.hasMessages(7)) {
                            DoorAttManager.this.handler.removeMessages(7);
                        }
                        DoorAttManager.this.handler.sendEmptyMessageDelayed(7, (long) (AttParameter.getInstance().getLockDriveTime() * 1000.0d));
                    }
                } else {
                    return;
                }
            }
            if (intent.getAction().equals(DoorAttManager.ACTION_OPTION_CHANGE)) {
                String stringExtra = intent.getStringExtra("key");
                if (stringExtra.equals(ZKDBConfig.OPT_LOCK_ON)) {
                    AttParameter.getInstance().setLockDriveTime((double) Integer.parseInt(intent.getStringExtra("value")));
                }
                if (stringExtra.equals(ZKDBConfig.GATE_MODE_SWITCH)) {
                    if ("0".equals(intent.getStringExtra("value"))) {
                        AttParameter.getInstance().setGateMode(false);
                        AttParameter.getInstance().setLockDriveTime((double) DBManager.getInstance().getIntOption(ZKDBConfig.OPT_LOCK_ON, 1));
                    } else {
                        AttParameter.getInstance().setGateMode(true);
                        AttParameter.getInstance().setLockDriveTime(0.8d);
                    }
                }
                if (stringExtra.equals(ZKDBConfig.OPT_ODD)) {
                    AttParameter.getInstance().setDoorSensorDelay(Integer.parseInt(intent.getStringExtra("value")));
                }
                if (stringExtra.equals(ZKDBConfig.OPT_DSM)) {
                    AttParameter.getInstance().setDoorSensorType(DoorSensorType.fromInteger(Integer.parseInt(intent.getStringExtra("value"))));
                    DoorAttManager doorAttManager2 = DoorAttManager.this;
                    if (!doorAttManager2.isDoorSensorStateConsistent(doorAttManager2.doorSenserState)) {
                        DoorAttManager.this.controlAlarm(0, true);
                        DoorAttManager.this.controlAlarm(1, true);
                        if (!DoorAttManager.attAlarmTypeList.contains(AttAlarmType.ACCIDENTALLOPEN)) {
                            DoorAttManager.attAlarmTypeList.add(AttAlarmType.ACCIDENTALLOPEN);
                        }
                    } else {
                        DoorAttManager.this.controlAlarm(0, false);
                        if (DoorAttManager.attAlarmTypeList.contains(AttAlarmType.SENSORDELAY)) {
                            DoorAttManager.attAlarmTypeList.remove(AttAlarmType.SENSORDELAY);
                        }
                        if (DoorAttManager.attAlarmTypeList.contains(AttAlarmType.ACCIDENTALLOPEN)) {
                            DoorAttManager.attAlarmTypeList.remove(AttAlarmType.ACCIDENTALLOPEN);
                        }
                    }
                }
                if (stringExtra.equals(ZKDBConfig.OPT_DOOR_SENSOR_TIME_OUT)) {
                    AttParameter.getInstance().setDoorSensorAlarmDelay(Integer.parseInt(intent.getStringExtra("value")));
                }
                if (stringExtra.equals("ERRTimes")) {
                    AttParameter.getInstance().setERRTimes(Integer.parseInt(intent.getStringExtra("value")));
                }
                if (stringExtra.equals("ErrTimeInterval")) {
                    AttParameter.getInstance().setErrTimeInterval(Integer.parseInt(intent.getStringExtra("value")));
                }
                if (stringExtra.equals("~DCTZ")) {
                    AttParameter.getInstance().setAlwayClose(Integer.parseInt(intent.getStringExtra("value")));
                    DoorAttManager.this.setContinuousState();
                }
                if (stringExtra.equals("~DOTZ")) {
                    AttParameter.getInstance().setAlwaysOpen(Integer.parseInt(intent.getStringExtra("value")));
                    DoorAttManager.this.setContinuousState();
                }
                if (stringExtra.equals("IsHolidayValid")) {
                    if ("0".equals(intent.getStringExtra("value"))) {
                        AttParameter.getInstance().setHolidayValid(false);
                    } else {
                        AttParameter.getInstance().setHolidayValid(true);
                    }
                }
                if (stringExtra.equals(ZKDBConfig.OPT_LOCAL_ALARM_ON)) {
                    if ("0".equals(intent.getStringExtra("value"))) {
                        AttParameter.getInstance().setLocalAlarm(false);
                        DoorAttManager.this.controlAlarm(0, false);
                    } else {
                        AttParameter.getInstance().setLocalAlarm(true);
                        if (DoorAttManager.attAlarmTypeList.contains(AttAlarmType.ACCIDENTALLOPEN) || DoorAttManager.attAlarmTypeList.contains(AttAlarmType.SENSORDELAY) || DoorAttManager.attAlarmTypeList.contains(AttAlarmType.TAMPER)) {
                            DoorAttManager.this.controlAlarm(0, true);
                        }
                    }
                }
                if (stringExtra.equals(ZKDBConfig.OPT_EXT_ALARM_ON)) {
                    if ("0".equals(intent.getStringExtra("value"))) {
                        AttParameter.getInstance().setExtAlarm(false);
                        DoorAttManager.this.controlAlarm(1, false);
                    } else {
                        AttParameter.getInstance().setExtAlarm(true);
                        if (DoorAttManager.attAlarmTypeList.contains(AttAlarmType.SENSORALARMDELAY) || DoorAttManager.attAlarmTypeList.contains(AttAlarmType.TAMPER) || DoorAttManager.attAlarmTypeList.contains(AttAlarmType.AUXALARM) || DoorAttManager.attAlarmTypeList.contains(AttAlarmType.ACCIDENTALLOPEN) || DoorAttManager.attAlarmTypeList.contains(AttAlarmType.VERIFYFAILALARM)) {
                            DoorAttManager.this.controlAlarm(1, true);
                        }
                    }
                }
                if (stringExtra.equals("~AAFO")) {
                    if ("0".equals(intent.getStringExtra("value"))) {
                        AttParameter.getInstance().setAAFO(false);
                        DoorAttManager.this.controlAlarm(2, false);
                    } else {
                        AttParameter.getInstance().setAAFO(true);
                    }
                }
                if (stringExtra.equals(ZKDBConfig.AUX_IN_FUN_ON)) {
                    if ("0".equals(intent.getStringExtra("value"))) {
                        AttParameter.getInstance().setAuxInFunOn(false);
                        DoorAttManager.this.auxiliaryFunction();
                    } else {
                        AttParameter.getInstance().setAuxInFunOn(true);
                    }
                }
                if (stringExtra.equals(ZKDBConfig.AUX_IN_OPTION)) {
                    AttParameter.getInstance().setAuxInOption(Integer.parseInt(intent.getStringExtra("value")));
                }
                if (stringExtra.equals(ZKDBConfig.AUX_IN_KEEP_TIME)) {
                    AttParameter.getInstance().setAuxInKeepTime(Integer.parseInt(intent.getStringExtra("value")));
                }
            }
            if (intent.getAction().equals("com.zkteco.android.core.ACTION_REMOTE_CANCEL_ALARM") && intent.getIntExtra("state", -1) == 0) {
                DoorAttManager.attAlarmTypeList.clear();
                DoorAttManager.this.soundPool.stop(DoorAttManager.this.playStreamID);
                DoorManagerHelper.getInstance().cancelAlarm();
                boolean unused2 = DoorAttManager.this.localAlarmIsRinging = false;
                boolean unused3 = DoorAttManager.this.externalAlarmIsRinging = false;
                DoorAttManager.this.sendAlarmState(false);
            }
            if (intent.getAction().equals("com.zkteco.android.core.ACTION_ALWAYS_OPEN_CHANGE")) {
                int intExtra2 = intent.getIntExtra("auxOnTime", 0);
                DoorAttManager.attDoorTypeList.clear();
                if (intExtra2 == 255) {
                    DoorAttManager.attDoorTypeList.add(AttDoorType.ALWAYSOPENDOOR);
                    if (DoorAttManager.this.controlDoor(true) && DoorAttManager.this.handler.hasMessages(0)) {
                        DoorAttManager.this.handler.removeMessages(0);
                    }
                } else if (intExtra2 == 0) {
                    DoorAttManager.attDoorTypeList.add(AttDoorType.ALWAYSCLOSEDOOR);
                    if (DoorAttManager.this.controlDoor(false) && DoorAttManager.this.handler.hasMessages(0)) {
                        DoorAttManager.this.handler.removeMessages(0);
                    }
                } else {
                    DoorAttManager.attDoorTypeList.add(AttDoorType.AUXOPENDOOR);
                    if (DoorAttManager.this.controlDoor(true)) {
                        if (DoorAttManager.this.handler.hasMessages(0)) {
                            DoorAttManager.this.handler.removeMessages(0);
                        }
                        DoorAttManager.this.handler.sendEmptyMessageDelayed(0, (long) (intExtra2 * 1000));
                    }
                }
            }
        }
    };
    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public SoundPool soundPool;
    private int tamperState = 0;
    private int times = 0;

    public static List<AttDoorType> getAttDoorTypeList() {
        return attDoorTypeList;
    }

    public static void setAttDoorTypeList(int i, AttDoorType attDoorType) {
        if (i == 0) {
            attDoorTypeList.remove(attDoorType);
        } else if (i == 1) {
            attDoorTypeList.add(attDoorType);
        }
    }

    public static Map<Long, List<Integer>> getAccGroupIdMap() {
        return accGroupIdMap;
    }

    public static List<String> getAccGroupPinList() {
        return accGroupPinList;
    }

    public static DoorAttManager getInstance(Context context2) {
        if (mInstance == null) {
            synchronized (DoorAttManager.class) {
                if (mInstance == null) {
                    mInstance = new DoorAttManager(context2);
                }
            }
        }
        return mInstance;
    }

    public DoorAttManager(Context context2) {
        this.context = context2;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zkteco.android.core.ACTION_TAMPER_CHANGE");
        intentFilter.addAction("com.zkteco.android.core.ACTION_DOOR_SENSOR_CHANGE");
        intentFilter.addAction("com.zkteco.android.core.ACTION_AUX_INPUT_CHANGE");
        intentFilter.addAction(ACTION_OPTION_CHANGE);
        intentFilter.addAction("com.zkteco.android.core.ACTION_OPEN");
        intentFilter.addAction("com.zkteco.android.core.ACTION_REMOTE_CANCEL_ALARM");
        intentFilter.addAction("com.zkteco.android.core.ACTION_ALWAYS_OPEN_CHANGE");
        intentFilter.addAction("com.zkteco.android.core.CLOSE_ALL_ALARM");
        context2.registerReceiver(this.remoteAttParameterReceiver, intentFilter);
        this.mRegisterAttParamBR.set(true);
        this.singleThreadExecutor.submit(this.initSomething);
    }

    /* access modifiers changed from: private */
    public void initSoundPool() {
        SoundPool soundPool2 = new SoundPool(1, 3, 0);
        this.soundPool = soundPool2;
        soundPool2.load(this.context, R.raw.alarm, 1);
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                boolean unused = DoorAttManager.this.isReadyToPlay = true;
                int unused2 = DoorAttManager.this.playResourceID = i;
                DoorAttManager.this.initDoorSenserState();
                DoorAttManager.this.initTamperState();
            }
        });
    }

    /* access modifiers changed from: private */
    public void initAttParameter() {
        AttParameter instance = AttParameter.getInstance();
        boolean z = false;
        boolean z2 = DBManager.getInstance().getIntOption(ZKDBConfig.GATE_MODE_SWITCH, 0) == 1;
        instance.setGateMode(z2);
        if (z2) {
            instance.setLockDriveTime(0.8d);
        } else {
            instance.setLockDriveTime((double) DBManager.getInstance().getIntOption(ZKDBConfig.OPT_LOCK_ON, 1));
        }
        instance.setDoorSensorDelay(DBManager.getInstance().getIntOption(ZKDBConfig.OPT_ODD, 1));
        instance.setDoorSensorType(DoorSensorType.fromInteger(DBManager.getInstance().getIntOption(ZKDBConfig.OPT_DSM, 1)));
        instance.setDoorSensorAlarmDelay(DBManager.getInstance().getIntOption(ZKDBConfig.OPT_DOOR_SENSOR_TIME_OUT, 1));
        instance.setAAFO(DBManager.getInstance().getIntOption("~AAFO", 1) == 1);
        instance.setLocalAlarm(DBManager.getInstance().getIntOption(ZKDBConfig.OPT_LOCAL_ALARM_ON, 1) == 1);
        instance.setExtAlarm(DBManager.getInstance().getIntOption(ZKDBConfig.OPT_EXT_ALARM_ON, 1) == 1);
        instance.setAlwaysOpen(DBManager.getInstance().getIntOption("~DOTZ", 0));
        instance.setAlwayClose(DBManager.getInstance().getIntOption("~DCTZ", 0));
        instance.setHolidayValid(DBManager.getInstance().getIntOption("IsHolidayValid", 1) == 1);
        instance.setERRTimes(DBManager.getInstance().getIntOption("ERRTimes", 0));
        instance.setErrTimeInterval(DBManager.getInstance().getIntOption("ErrTimeInterval", 8));
        instance.setAuxInFunOn(DBManager.getInstance().getStrOption(ZKDBConfig.AUX_IN_FUN_ON, "0").equals("1"));
        instance.setAuxInOption(DBManager.getInstance().getIntOption(ZKDBConfig.AUX_IN_OPTION, 0));
        instance.setAuxInKeepTime(DBManager.getInstance().getIntOption(ZKDBConfig.AUX_IN_KEEP_TIME, 1));
        if (DBManager.getInstance().getIntOption("~APBFO", 0) == 1) {
            z = true;
        }
        instance.setAPBFO(z);
    }

    /* access modifiers changed from: private */
    public void initDoorSenserState() {
        int sensorState = DoorManagerHelper.getInstance().getSensorState();
        sendChangeSensorIcon(sensorState);
        DoorSensorState fromInteger = DoorSensorState.fromInteger(sensorState);
        this.doorSenserState = fromInteger;
        if (!isDoorSensorStateConsistent(fromInteger)) {
            controlAlarm(0, true);
            controlAlarm(1, true);
        }
    }

    /* access modifiers changed from: private */
    public void initTamperState() {
        int tamperState2 = DoorManagerHelper.getInstance().getTamperState();
        this.tamperState = tamperState2;
        if (tamperState2 == 1) {
            controlAlarm(0, true);
            controlAlarm(1, true);
            attAlarmTypeList.add(AttAlarmType.TAMPER);
        }
    }

    public AttResponse attVerify(ZKVerDao zKVerDao, int i, UserInfo userInfo, boolean z, boolean z2, boolean z3) {
        AttResponse check = DoorAttChecker.check(this.context, i, zKVerDao, userInfo, z, z2);
        boolean z4 = ZKLauncher.maskDetectionFunOn == 1 && ZKLauncher.enalbeMaskDetection == 1;
        if ((check.getEventCode() == 68 || (check.getEventCode() == 69 && z4)) && ZKLauncher.enableTriggerAlarm == 1) {
            sendAlarmState(true);
            DoorManagerHelper.getInstance().playAlarm();
            if (ZKLauncher.externalAlarmDelay > 0) {
                if (this.handler.hasMessages(6)) {
                    this.handler.removeMessages(6);
                }
                this.handler.sendEmptyMessageDelayed(6, (long) (ZKLauncher.externalAlarmDelay * 1000));
            }
        }
        if (check.isOpenDoor()) {
            if (!z3 && controlDoor(true)) {
                List<AttDoorType> list = attDoorTypeList;
                if (!list.contains(AttDoorType.VERIFYOPENDOOR)) {
                    list.add(AttDoorType.VERIFYOPENDOOR);
                }
                if (this.handler.hasMessages(1)) {
                    this.handler.removeMessages(1);
                }
                this.handler.sendEmptyMessageDelayed(1, (long) (AttParameter.getInstance().getLockDriveTime() * 1000.0d));
            }
            if (check.getAttAlarmType() == AttAlarmType.DURESSALARM) {
                if (this.handler.hasMessages(5)) {
                    this.handler.removeMessages(5);
                }
                this.handler.sendEmptyMessageDelayed(5, (long) (AttParameter.getInstance().getDUAD() * 1000));
            } else if (check.getAttAlarmType() != AttAlarmType.HIGHTEMPERATUREALARM) {
                controlAlarm(1, false);
                List<AttAlarmType> list2 = attAlarmTypeList;
                if (list2.contains(AttAlarmType.SENSORALARMDELAY)) {
                    list2.remove(AttAlarmType.SENSORALARMDELAY);
                }
                if (list2.contains(AttAlarmType.VERIFYFAILALARM)) {
                    list2.remove(AttAlarmType.VERIFYFAILALARM);
                }
                if (list2.contains(AttAlarmType.DURESSALARM)) {
                    list2.remove(AttAlarmType.DURESSALARM);
                }
            }
        }
        return check;
    }

    /* access modifiers changed from: private */
    public void setContinuousState() {
        int alwaysOpen = AttParameter.getInstance().getAlwaysOpen();
        int alwayClose = AttParameter.getInstance().getAlwayClose();
        if (alwayClose == 0 && alwaysOpen == 0) {
            ContinuousStateTask continuousStateTask2 = this.continuousStateTask;
            if (continuousStateTask2 != null) {
                continuousStateTask2.setTimePeriod(alwayClose, alwaysOpen);
                this.continuousStateTask = null;
                return;
            }
            return;
        }
        ContinuousStateTask continuousStateTask3 = this.continuousStateTask;
        if (continuousStateTask3 == null) {
            ContinuousStateTask continuousStateTask4 = new ContinuousStateTask(this.context);
            this.continuousStateTask = continuousStateTask4;
            continuousStateTask4.setTimePeriod(alwayClose, alwaysOpen);
            this.singleThreadExecutor.submit(this.continuousStateTask);
            return;
        }
        continuousStateTask3.setTimePeriod(alwayClose, alwaysOpen);
    }

    public void verifyFailAlarm() {
        try {
            int eRRTimes = AttParameter.getInstance().getERRTimes();
            if (eRRTimes != 0) {
                int i = this.times;
                if (i == 0) {
                    this.times = i + 1;
                } else {
                    if (SystemClock.elapsedRealtime() - this.nowNum <= ((long) (AttParameter.getInstance().getErrTimeInterval() * 1000))) {
                        int i2 = this.times + 1;
                        this.times = i2;
                        if (i2 == eRRTimes) {
                            if (AttParameter.getInstance().isExtAlarm()) {
                                List<AttAlarmType> list = attAlarmTypeList;
                                if (!list.contains(AttAlarmType.VERIFYFAILALARM)) {
                                    list.add(AttAlarmType.VERIFYFAILALARM);
                                }
                                controlAlarm(1, true);
                            }
                            this.times = 0;
                        }
                    } else {
                        this.times = 1;
                    }
                }
                this.nowNum = SystemClock.elapsedRealtime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void auxiliaryFunction() {
        if (!AttParameter.getInstance().isAuxInFunOn()) {
            List<AttAlarmType> list = attAlarmTypeList;
            if (list.contains(AttAlarmType.AUXALARM)) {
                list.remove(AttAlarmType.AUXALARM);
                controlAlarm(1, false);
            }
            List<AttDoorType> list2 = attDoorTypeList;
            if (list2.contains(AttDoorType.AUXOPENDOOR)) {
                list2.remove(AttDoorType.AUXOPENDOOR);
                controlDoor(false);
                return;
            }
            return;
        }
        int auxInOption = AttParameter.getInstance().getAuxInOption();
        if (auxInOption == 2) {
            List<AttAlarmType> list3 = attAlarmTypeList;
            if (!list3.contains(AttAlarmType.AUXALARM)) {
                list3.add(AttAlarmType.AUXALARM);
            }
            controlAlarm(1, true);
        }
        if (auxInOption == 1) {
            if (AttParameter.getInstance().getAuxInKeepTime() == 255) {
                List<AttDoorType> list4 = attDoorTypeList;
                if (!list4.contains(AttDoorType.AUXOPENDOOR)) {
                    list4.add(AttDoorType.AUXOPENDOOR);
                }
                controlDoor(true);
            } else if (AttParameter.getInstance().getAuxInKeepTime() > 0) {
                List<AttDoorType> list5 = attDoorTypeList;
                if (!list5.contains(AttDoorType.AUXOPENDOOR)) {
                    list5.add(AttDoorType.AUXOPENDOOR);
                }
                controlDoor(true);
                if (this.handler.hasMessages(0)) {
                    this.handler.removeMessages(0);
                }
                this.handler.sendEmptyMessageDelayed(0, (long) (AttParameter.getInstance().getAuxInKeepTime() * 1000));
            }
        }
        if (auxInOption == 3) {
            List<AttAlarmType> list6 = attAlarmTypeList;
            if (!list6.contains(AttAlarmType.AUXALARM)) {
                list6.add(AttAlarmType.AUXALARM);
            }
            controlAlarm(1, true);
            if (AttParameter.getInstance().getAuxInKeepTime() == 255) {
                List<AttDoorType> list7 = attDoorTypeList;
                if (!list7.contains(AttDoorType.AUXOPENDOOR)) {
                    list7.add(AttDoorType.AUXOPENDOOR);
                }
                controlDoor(true);
            } else if (AttParameter.getInstance().getAuxInKeepTime() > 0) {
                List<AttDoorType> list8 = attDoorTypeList;
                if (!list8.contains(AttDoorType.AUXOPENDOOR)) {
                    list8.add(AttDoorType.AUXOPENDOOR);
                }
                controlDoor(true);
                if (this.handler.hasMessages(0)) {
                    this.handler.removeMessages(0);
                }
                this.handler.sendEmptyMessageDelayed(0, (long) (AttParameter.getInstance().getAuxInKeepTime() * 1000));
            }
        }
    }

    public boolean controlDoor(boolean z) {
        if (z) {
            List<AttDoorType> list = attDoorTypeList;
            if (!list.contains(AttDoorType.AUXOPENDOOR) && list.contains(AttDoorType.ALWAYSOPENDOOR) && list.contains(AttDoorType.ALWAYSCLOSEDOOR)) {
                return false;
            }
            openLock();
            return true;
        }
        List<AttDoorType> list2 = attDoorTypeList;
        if (list2.contains(AttDoorType.AUXOPENDOOR)) {
            return false;
        }
        if (!list2.contains(AttDoorType.ALWAYSCLOSEDOOR) && list2.contains(AttDoorType.ALWAYSOPENDOOR)) {
            return false;
        }
        closeLock();
        return true;
    }

    private void openLock() {
        if (this.openLockFuture == null) {
            this.openLockFuture = this.mSingleService.submit(this.openLockTask);
        }
    }

    private void closeLock() {
        if (this.closeLockFuture == null) {
            this.closeLockFuture = this.mSingleService.submit(this.closeLockTask);
        }
    }

    public void controlAlarm(int i, boolean z) {
        int i2;
        if (!AttParameter.getInstance().isAAFO()) {
            this.soundPool.stop(this.playStreamID);
            DoorManagerHelper.getInstance().cancelAlarm();
            this.localAlarmIsRinging = false;
            this.externalAlarmIsRinging = false;
            sendAlarmState(false);
            return;
        }
        if (i == 0) {
            if (z) {
                if (AttParameter.getInstance().isLocalAlarm() && this.isReadyToPlay && (i2 = this.playResourceID) > 0) {
                    this.playStreamID = this.soundPool.play(i2, 1.0f, 1.0f, 0, -1, 1.0f);
                    this.localAlarmIsRinging = true;
                    sendAlarmState(true);
                }
            } else if (!AttParameter.getInstance().isLocalAlarm() || (isDoorSensorStateConsistent(this.doorSenserState) && !attAlarmTypeList.contains(AttAlarmType.TAMPER))) {
                this.soundPool.stop(this.playStreamID);
                this.localAlarmIsRinging = false;
                if (!this.externalAlarmIsRinging) {
                    sendAlarmState(false);
                }
            }
        }
        if (i != 1) {
            return;
        }
        if (!z) {
            if (AttParameter.getInstance().isExtAlarm()) {
                List<AttAlarmType> list = attAlarmTypeList;
                if (list.contains(AttAlarmType.AUXALARM) || !isDoorSensorStateConsistent(this.doorSenserState) || list.contains(AttAlarmType.TAMPER)) {
                    return;
                }
            }
            DoorManagerHelper.getInstance().cancelAlarm();
            this.externalAlarmIsRinging = false;
            if (!this.localAlarmIsRinging) {
                sendAlarmState(false);
            }
        } else if (AttParameter.getInstance().isExtAlarm()) {
            DoorManagerHelper.getInstance().playAlarm();
            this.externalAlarmIsRinging = true;
            sendAlarmState(true);
        }
    }

    /* access modifiers changed from: private */
    public void sendAlarmState(boolean z) {
        if (z) {
            Intent intent = new Intent();
            intent.setAction("com.zkteco.android.core.ACTION_ALARM_OPEN");
            this.context.sendBroadcast(intent);
            return;
        }
        Intent intent2 = new Intent();
        intent2.setAction("com.zkteco.android.core.ACTION_ALARM_CLOSE");
        this.context.sendBroadcast(intent2);
    }

    public boolean isDoorSensorStateConsistent(DoorSensorState doorSensorState) {
        DoorSensorType doorSensorType = AttParameter.getInstance().getDoorSensorType();
        if (doorSensorType == DoorSensorType.NONE) {
            return true;
        }
        if (doorSensorType == DoorSensorType.ALWAYS_OPEN && doorSensorState == DoorSensorState.OPEN) {
            return true;
        }
        if (doorSensorType == DoorSensorType.ALWAYS_CLOSE && doorSensorState == DoorSensorState.CLOSE) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void sendChangeSensorIcon(int i) {
        Intent intent = new Intent();
        intent.setAction(SensorStateController.ACTION_SENSOR_STATE);
        intent.putExtra("SensorState", i);
        this.context.sendBroadcast(intent);
    }

    public void setTimes(int i) {
        this.times = i;
    }

    public void releaseDevice() {
        if (this.context != null && this.mRegisterAttParamBR.get()) {
            this.context.unregisterReceiver(this.remoteAttParameterReceiver);
            this.mRegisterAttParamBR.set(false);
        }
        Handler handler2 = this.handler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages((Object) null);
            this.handler = null;
        }
    }
}
