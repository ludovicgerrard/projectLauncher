package com.zktechnology.android.att.tasks;

import android.content.Context;
import com.zktechnology.android.att.AttParameter;
import com.zktechnology.android.att.DoorAttManager;
import com.zktechnology.android.att.types.AttDoorType;
import com.zkteco.android.db.orm.contants.BiometricCommuCMD;
import com.zkteco.android.db.orm.tna.AccHoliday;
import com.zkteco.android.db.orm.tna.AccTimeZoneWeek;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ContinuousStateTask implements Runnable {
    private int alwayCloseTime;
    private int alwayOpenTime;
    private Context context;
    private Date date;
    private int holidayTime;
    private boolean running = true;

    public ContinuousStateTask(Context context2) {
        this.context = context2;
    }

    public void setTimePeriod(int i, int i2) {
        this.alwayCloseTime = i;
        this.alwayOpenTime = i2;
        if (i == 0 && i2 == 0) {
            this.running = false;
            if (DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSOPENDOOR)) {
                DoorAttManager.setAttDoorTypeList(0, AttDoorType.ALWAYSOPENDOOR);
            }
            if (DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSCLOSEDOOR)) {
                DoorAttManager.setAttDoorTypeList(0, AttDoorType.ALWAYSCLOSEDOOR);
            }
            DoorAttManager.getInstance(this.context).controlDoor(false);
        }
    }

    public void run() {
        String str;
        String str2;
        while (this.running) {
            boolean isHolidayValid = AttParameter.getInstance().isHolidayValid();
            this.date = new Date(System.currentTimeMillis());
            int parseInt = Integer.parseInt(new SimpleDateFormat("HHmm", Locale.US).format(this.date));
            Calendar instance = Calendar.getInstance();
            instance.setTime(this.date);
            switch (instance.get(7)) {
                case 1:
                    str2 = "SunStart";
                    str = "SunEnd";
                    break;
                case 2:
                    str2 = "MonStart";
                    str = "MonEnd";
                    break;
                case 3:
                    str2 = "TuesStart";
                    str = "TuesEnd";
                    break;
                case 4:
                    str2 = "WedStart";
                    str = "WedEnd";
                    break;
                case 5:
                    str2 = "ThursStart";
                    str = "ThursEnd";
                    break;
                case 6:
                    str2 = "FriStart";
                    str = "FriEnd";
                    break;
                default:
                    str2 = "SatStart";
                    str = "SatEnd";
                    break;
            }
            try {
                if (this.alwayCloseTime != 0) {
                    AccTimeZoneWeek accTimeZoneWeek = (AccTimeZoneWeek) new AccTimeZoneWeek().getQueryBuilder().where().eq(BiometricCommuCMD.FIELD_DESC_TMP_ID, Integer.valueOf(this.alwayCloseTime)).and().le(str2, Integer.valueOf(parseInt)).and().ge(str, Integer.valueOf(parseInt)).queryForFirst();
                    if (isHolidayValid && isHoliday()) {
                        AccTimeZoneWeek accTimeZoneWeek2 = (AccTimeZoneWeek) new AccTimeZoneWeek().getQueryBuilder().where().eq(BiometricCommuCMD.FIELD_DESC_TMP_ID, Integer.valueOf(this.holidayTime)).and().le(str2, Integer.valueOf(parseInt)).and().ge(str, Integer.valueOf(parseInt)).queryForFirst();
                        if (accTimeZoneWeek != null && accTimeZoneWeek2 != null) {
                            if (DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSOPENDOOR)) {
                                DoorAttManager.setAttDoorTypeList(0, AttDoorType.ALWAYSOPENDOOR);
                            }
                            if (!DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSCLOSEDOOR)) {
                                DoorAttManager.setAttDoorTypeList(1, AttDoorType.ALWAYSCLOSEDOOR);
                            }
                            DoorAttManager.getInstance(this.context).controlDoor(false);
                        } else if (DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSCLOSEDOOR)) {
                            DoorAttManager.setAttDoorTypeList(0, AttDoorType.ALWAYSCLOSEDOOR);
                        }
                    } else if (accTimeZoneWeek != null) {
                        if (DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSOPENDOOR)) {
                            DoorAttManager.setAttDoorTypeList(0, AttDoorType.ALWAYSOPENDOOR);
                        }
                        if (!DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSCLOSEDOOR)) {
                            DoorAttManager.setAttDoorTypeList(1, AttDoorType.ALWAYSCLOSEDOOR);
                        }
                        DoorAttManager.getInstance(this.context).controlDoor(false);
                    } else if (DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSCLOSEDOOR)) {
                        DoorAttManager.setAttDoorTypeList(0, AttDoorType.ALWAYSCLOSEDOOR);
                    }
                } else if (DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSCLOSEDOOR)) {
                    DoorAttManager.setAttDoorTypeList(0, AttDoorType.ALWAYSCLOSEDOOR);
                }
                if (this.alwayOpenTime != 0) {
                    AccTimeZoneWeek accTimeZoneWeek3 = (AccTimeZoneWeek) new AccTimeZoneWeek().getQueryBuilder().where().eq(BiometricCommuCMD.FIELD_DESC_TMP_ID, Integer.valueOf(this.alwayOpenTime)).and().le(str2, Integer.valueOf(parseInt)).and().ge(str, Integer.valueOf(parseInt)).queryForFirst();
                    if (isHolidayValid && isHoliday()) {
                        AccTimeZoneWeek accTimeZoneWeek4 = (AccTimeZoneWeek) new AccTimeZoneWeek().getQueryBuilder().where().eq(BiometricCommuCMD.FIELD_DESC_TMP_ID, Integer.valueOf(this.holidayTime)).and().le(str2, Integer.valueOf(parseInt)).and().ge(str, Integer.valueOf(parseInt)).queryForFirst();
                        if (accTimeZoneWeek3 != null && accTimeZoneWeek4 != null) {
                            DoorAttManager.getInstance(this.context).controlDoor(true);
                            if (!DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSOPENDOOR)) {
                                DoorAttManager.setAttDoorTypeList(1, AttDoorType.ALWAYSOPENDOOR);
                            }
                        } else if (DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSOPENDOOR)) {
                            DoorAttManager.setAttDoorTypeList(0, AttDoorType.ALWAYSOPENDOOR);
                            DoorAttManager.getInstance(this.context).controlDoor(false);
                        }
                    } else if (accTimeZoneWeek3 != null) {
                        DoorAttManager.getInstance(this.context).controlDoor(true);
                        if (!DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSOPENDOOR)) {
                            DoorAttManager.setAttDoorTypeList(1, AttDoorType.ALWAYSOPENDOOR);
                        }
                    } else if (DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSOPENDOOR)) {
                        DoorAttManager.setAttDoorTypeList(0, AttDoorType.ALWAYSOPENDOOR);
                        DoorAttManager.getInstance(this.context).controlDoor(false);
                    }
                } else if (DoorAttManager.getAttDoorTypeList().contains(AttDoorType.ALWAYSOPENDOOR)) {
                    DoorAttManager.setAttDoorTypeList(0, AttDoorType.ALWAYSOPENDOOR);
                    DoorAttManager.getInstance(this.context).controlDoor(false);
                }
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isHoliday() {
        int parseInt = Integer.parseInt(new SimpleDateFormat("MMdd", Locale.US).format(this.date));
        try {
            AccHoliday accHoliday = (AccHoliday) new AccHoliday().getQueryBuilder().where().le("StartDate", Integer.valueOf(parseInt)).and().ge("EndDate", Integer.valueOf(parseInt)).queryForFirst();
            if (accHoliday == null) {
                return false;
            }
            this.holidayTime = accHoliday.getTimezone();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
