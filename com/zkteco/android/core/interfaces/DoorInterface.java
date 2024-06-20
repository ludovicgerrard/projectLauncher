package com.zkteco.android.core.interfaces;

public interface DoorInterface {
    public static final String ACCESS_VERIFY = "door_access_verify";
    public static final String APPLY_NEW_ACCESS_CONFIG = "door_apply_new_access_config";
    public static final String CANCEL_ALARM = "door_cancel_alarm";
    public static final String CLOSE_DOOR = "door_close";
    public static final String GET_ALARM_STATE = "door_alarm_state";
    public static final String GET_LOCK_STATE = "door_lock_state";
    public static final String GET_OUTDOORSWITCH_STATE = "door_outdoorswitch_state";
    public static final String GET_SENSOR_STATE = "door_sensor_state";
    public static final String GET_TEARDOWNBUTTON_STATE = "door_teardownbutton_state";
    public static final String INIT_ACCESS_DEVICE = "door_init_access_device";
    public static final String NORMALLY_OPEN_DOOR = "door_normally_open";
    public static final String OPEN_DOOR = "door_open";
    public static final String PLAY_ALARM = "door_play_alarm";
    public static final String REALSE_ACCESS_DEVICE = "door_realse_access_device";
    public static final String SET_LOCK_DELAY = "door_lock_delay";

    void accessVerify(boolean z);

    void applyNewAccessConfig();

    void cancelAlarm();

    void closeDoor();

    boolean getAlarmState();

    boolean getLockState();

    int getOutDoorSwitchState();

    int getSensorState();

    int getTearDownButtonState();

    void initAccessDevice();

    void normallyOpenDoor();

    void openDoor();

    void playAlarm();

    void realseAccessDevice();

    void setLockDelay(int i);
}
