package com.zkteco.android.db.dao;

import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.AbstractORM;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.utils.FileLogUtils;
import com.zkteco.android.zkcore.read.ReadConfig;

public class ZkAccDao {
    public static void deleteHoliday() {
        AccHolidayTabDao.deleteHoliday();
    }

    public static void resetTimeRule(DataManager dataManager) {
        if (dataManager != null) {
            try {
                resetAccTimeZoneWeekTable(dataManager);
                FileLogUtils.writeAccResetLog("start delete AccTimeZoneRule.");
                resetAccTimeZoneRuleTable(dataManager);
                FileLogUtils.writeAccResetLog("start delete AccDatOthername.");
                resetAccDatOthernameTable(dataManager);
                FileLogUtils.writeAccResetLog("start delete AccRuleName.");
                resetAccRuleNameTable(dataManager);
                FileLogUtils.writeAccResetLog("start delete AccTimeZone.");
                resetAccTimeZoneTable(dataManager);
                FileLogUtils.writeAccResetLog("start delete AccRuleTime.");
                resetAccRuleTimeTable(dataManager);
                FileLogUtils.writeAccResetLog("finish reset all table data.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("reset all table data failed.\n" + e.getMessage());
            }
        }
    }

    private static void resetAccTimeZoneWeekTable(DataManager dataManager) {
        if (dataManager != null) {
            AccTimeZoneWeekDao.resetAccTimeZoneWeekTable(dataManager);
        }
    }

    private static void resetAccRuleTimeTable(DataManager dataManager) {
        if (dataManager != null) {
            AccRuleTimeDao.resetAccRuleTimeTable(dataManager);
        }
    }

    private static void resetAccRuleNameTable(DataManager dataManager) {
        if (dataManager != null) {
            AccRuleNameDao.resetAccRuleNameTable(dataManager);
        }
    }

    private static void resetAccTimeZoneTable(DataManager dataManager) {
        if (dataManager != null) {
            AccTimeZoneDao.resetAccTimeZoneTable(dataManager);
        }
    }

    private static void resetAccDatOthernameTable(DataManager dataManager) {
        if (dataManager != null) {
            AccDatOtherNameDao.resetAccDatOthernameTable(dataManager);
        }
    }

    private static void resetAccTimeZoneRuleTable(DataManager dataManager) {
        if (dataManager != null) {
            AccTimeZoneRuleDao.resetAccTimeZoneRuleTable(dataManager);
        }
    }

    public static void resetTableData(AbstractORM<?> abstractORM) {
        if (abstractORM != null) {
            try {
                abstractORM.clearTable();
                abstractORM.createTableWithDefaults();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void resetAccOptions(DataManager dataManager) {
        if (dataManager != null) {
            try {
                FileLogUtils.writeAccResetLog("start reset acc options.");
                dataManager.setIntOption(ZKDBConfig.OPT_DOOR1_DRIVER_TIME, 5);
                dataManager.setIntOption(ZKDBConfig.OPT_DOOR1_DETECTOR_TIME, 10);
                dataManager.setIntOption(ZKDBConfig.OPT_DOOR1_SENSOR_TYPE, 0);
                dataManager.setIntOption(ZKDBConfig.OPT_DOOR_1_VERIFY_TYPE, 0);
                dataManager.setIntOption(ZKDBConfig.OPT_DOOR_1_VALID_TIME_ZONE, 1);
                dataManager.setIntOption(ZKDBConfig.DOOR1_KEEP_OPEN_TIME_ZONE, 0);
                dataManager.setIntOption(ZKDBConfig.DOO1_ACCESS_DIRECTION, 1);
                dataManager.setIntOption(ZKDBConfig.AUX_IN_KEEP_TIME, 255);
                dataManager.setIntOption(ZKDBConfig.AUX_IN_OPTION, 1);
                dataManager.setIntOption(ReadConfig.READ_VERIFY_TYPE, 0);
                dataManager.setIntOption("~AAFO", 0);
                dataManager.setIntOption(ZKDBConfig.OPT_LOCAL_ALARM_ON, 0);
                dataManager.setIntOption(ZKDBConfig.OPT_EXT_ALARM_ON, 0);
                dataManager.setIntOption(ZKDBConfig.GATE_MODE_SWITCH, 0);
                dataManager.setIntOption(ZKDBConfig.ANTI_PASSBACK_TYPE, 0);
                dataManager.setIntOption(ZKDBConfig.DOO1_ACCESS_DIRECTION, 0);
                dataManager.setIntOption(ZKDBConfig.AUX_IN_FUN_ON, 0);
                dataManager.setIntOption("AuxInCount", 1);
                dataManager.setIntOption(ZKDBConfig.ACC_MULTI_USER_VERIFY_TIME, 8);
                dataManager.setIntOption("AccessPersonalVerification", 0);
                FileLogUtils.writeAccResetLog("finish reset acc options.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("reset acc options failed.\n" + e.getMessage());
            }
        }
    }

    public static void resetOSDPOptions(DataManager dataManager) {
        if (dataManager != null) {
            try {
                FileLogUtils.writeAccResetLog("start reset OSDP options.");
                dataManager.setStrOption(DBConfig.DBKEY_OSDP_ADDR, "1");
                dataManager.setStrOption(DBConfig.DBKEY_OSDP_485_BAUDRATE, "9600");
                dataManager.setStrOption(DBConfig.DBKEY_OSPD_OUTPUT_TYPE, DBConfig.DBKEY_OSPD_OUTPUT_TYPE_USRPIN);
                FileLogUtils.writeAccResetLog("finish reset OSDP options.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("reset OSDP options failed.\n" + e.getMessage());
            }
        }
    }

    public static void resetProtectOptions(DataManager dataManager) {
        if (dataManager != null) {
            try {
                FileLogUtils.writeAccResetLog("start reset protect options.");
                dataManager.setIntOption("IRTempDetectionFunOn", 0);
                dataManager.setIntOption("EnalbeIRTempDetection", 0);
                dataManager.setIntOption("IRTempThreshold", 3730);
                dataManager.setIntOption("IRTempLowThreshold", 0);
                dataManager.setIntOption("EnableNormalIRTempPass", 0);
                dataManager.setIntOption("IRTempCorrection", 0);
                dataManager.setIntOption("IRTempCalibFunOn", 0);
                dataManager.setIntOption("IRTempUnit", 0);
                dataManager.setIntOption("IRTempModuleType", 0);
                dataManager.setIntOption("IRTempDetectDistance", 2);
                dataManager.setIntOption("MaskDetectionFunOn", 0);
                dataManager.setIntOption("EnalbeMaskDetection", 0);
                dataManager.setIntOption("EnableWearMaskPass", 0);
                dataManager.setIntOption("EnableTriggerAlarm", 0);
                dataManager.setIntOption("EnableUnregisterPass", 0);
                dataManager.setIntOption("EnableUnregisterCapture", 0);
                dataManager.setIntOption("ExternalAlarmDelay", 10);
                dataManager.setIntOption("EnableShowTemp", 1);
                dataManager.setIntOption("EnableIRTempImage", 0);
                FileLogUtils.writeAccResetLog("finish reset protect options.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("reset protect options failed.\n" + e.getMessage());
            }
        }
    }

    public static void resetBiometricOptions(DataManager dataManager) {
        if (dataManager != null) {
            try {
                FileLogUtils.writeAccResetLog("start reset biometric options.");
                dataManager.setIntOption(DBConfig.BIOMETRIC_PALM_PVMTHRESHOLD, 576);
                dataManager.setIntOption(DBConfig.BIOMETRIC_PALM_PVVTHRESHOLD, 576);
                dataManager.setIntOption("PvEThreshold", 576);
                dataManager.setIntOption("~FaceVThr", 56);
                dataManager.setIntOption("~FaceMThr", 65);
                dataManager.setIntOption(DBConfig.FACE_ENROLL_THRESHOLD, 68);
                FileLogUtils.writeAccResetLog("finish reset biometric options.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("reset biometric options failed.\n" + e.getMessage());
            }
        }
    }

    public static void resetOtherOptions(DataManager dataManager) {
        if (dataManager != null) {
            try {
                FileLogUtils.writeAccResetLog("start reset other options.");
                dataManager.setIntOption("Antibanding", 0);
                dataManager.setIntOption("IsSupportLongName", 1);
                dataManager.setIntOption("~ML", 1);
                dataManager.setIntOption("Language", 69);
                FileLogUtils.writeAccResetLog("finish reset other options.");
            } catch (Exception e) {
                e.printStackTrace();
                FileLogUtils.writeAccResetLog("reset other options failed.\n" + e.getMessage());
            }
        }
    }
}
