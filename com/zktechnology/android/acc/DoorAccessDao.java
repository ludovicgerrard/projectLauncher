package com.zktechnology.android.acc;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.text.TextUtils;
import android.util.Log;
import com.zktechnology.android.push.util.Utils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.db.orm.contants.BiometricCommuCMD;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.AccAttLog;

public class DoorAccessDao {
    private static final String TAG = "DoorAccessDao";
    private static DataManager dataManager;

    public static void openDB(Context context) {
        dataManager = DBManager.getInstance();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004c, code lost:
        if (r1 != null) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0034, code lost:
        if (r1 != null) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0036, code lost:
        r1.close();
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0053  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getHolidayName() {
        /*
            r0 = 0
            java.lang.String r1 = com.zktechnology.android.push.util.Utils.getCurrentDate()     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            r2.<init>()     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            java.lang.String r3 = "SELECT Name FROM ACC_DAT_OTHERNAME,ACC_HOLIDAY_TAB WHERE ACC_HOLIDAY_TAB.[HolidayDay]="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            java.lang.String r2 = " AND ACC_HOLIDAY_TAB.[HolidayType]=ACC_DAT_OTHERNAME.[Name] LIMIT 1"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            java.lang.String r1 = r1.toString()     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            com.zkteco.android.db.orm.manager.DataManager r2 = dataManager     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            android.database.Cursor r1 = r2.queryBySql(r1)     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            boolean r2 = r1.moveToFirst()     // Catch:{ SQLException -> 0x003a }
            if (r2 == 0) goto L_0x0034
            java.lang.String r2 = "Name"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ SQLException -> 0x003a }
            java.lang.String r0 = r1.getString(r2)     // Catch:{ SQLException -> 0x003a }
        L_0x0034:
            if (r1 == 0) goto L_0x004f
        L_0x0036:
            r1.close()
            goto L_0x004f
        L_0x003a:
            r2 = move-exception
            goto L_0x0043
        L_0x003c:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0051
        L_0x0041:
            r2 = move-exception
            r1 = r0
        L_0x0043:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x0050 }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x0050 }
            com.zktechnology.android.utils.LogUtils.e(r3, r2)     // Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x004f
            goto L_0x0036
        L_0x004f:
            return r0
        L_0x0050:
            r0 = move-exception
        L_0x0051:
            if (r1 == 0) goto L_0x0056
            r1.close()
        L_0x0056:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.acc.DoorAccessDao.getHolidayName():java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004c, code lost:
        if (r1 != null) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0034, code lost:
        if (r1 != null) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0036, code lost:
        r1.close();
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0053  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getHolidayNameByLoop() {
        /*
            r0 = 0
            java.lang.String r1 = com.zktechnology.android.push.util.Utils.getCurrentMonthAndDate()     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            r2.<init>()     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            java.lang.String r3 = "SELECT Name FROM ACC_DAT_OTHERNAME,ACC_HOLIDAY_TAB WHERE ACC_HOLIDAY_TAB.[HolidayDay] LIKE '%"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            java.lang.String r2 = "' AND Loop=1 AND ACC_HOLIDAY_TAB.[HolidayType]=ACC_DAT_OTHERNAME.[Name] LIMIT 1"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            java.lang.String r1 = r1.toString()     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            com.zkteco.android.db.orm.manager.DataManager r2 = dataManager     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            android.database.Cursor r1 = r2.queryBySql(r1)     // Catch:{ SQLException -> 0x0041, all -> 0x003c }
            boolean r2 = r1.moveToFirst()     // Catch:{ SQLException -> 0x003a }
            if (r2 == 0) goto L_0x0034
            java.lang.String r2 = "Name"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ SQLException -> 0x003a }
            java.lang.String r0 = r1.getString(r2)     // Catch:{ SQLException -> 0x003a }
        L_0x0034:
            if (r1 == 0) goto L_0x004f
        L_0x0036:
            r1.close()
            goto L_0x004f
        L_0x003a:
            r2 = move-exception
            goto L_0x0043
        L_0x003c:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0051
        L_0x0041:
            r2 = move-exception
            r1 = r0
        L_0x0043:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x0050 }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x0050 }
            com.zktechnology.android.utils.LogUtils.e(r3, r2)     // Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x004f
            goto L_0x0036
        L_0x004f:
            return r0
        L_0x0050:
            r0 = move-exception
        L_0x0051:
            if (r1 == 0) goto L_0x0056
            r1.close()
        L_0x0056:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.acc.DoorAccessDao.getHolidayNameByLoop():java.lang.String");
    }

    public static int getHolidayType() {
        try {
            String holidayName = getHolidayName();
            if (TextUtils.isEmpty(holidayName)) {
                holidayName = getHolidayNameByLoop();
            }
            if (!TextUtils.isEmpty(holidayName)) {
                return Integer.parseInt(holidayName);
            }
            return 0;
        } catch (NumberFormatException e) {
            Log.d(TAG, e.getMessage());
            return 0;
        }
    }

    public static boolean isInAccTimeZone(int i) {
        boolean z;
        String str = "SELECT Start_Time,End_Time FROM ACC_TIME_ZONE,ACC_DAT_OTHERNAME,ACC_TIME_ZONE_RULE,ACC_RULE_NAME,ACC_RULE_TIME WHERE ACC_DAT_OTHERNAME.[Name]='" + getWeek() + "' AND ACC_TIME_ZONE_RULE.[Time_Zone_ID]=" + i + " AND ACC_RULE_NAME.[Name_ID]=ACC_DAT_OTHERNAME.[ID] AND ACC_RULE_NAME.[Rule_ID]=ACC_TIME_ZONE_RULE.[ID] AND ACC_RULE_TIME.[Rule_Name_ID]=ACC_RULE_NAME.[ID] AND ACC_TIME_ZONE.[ID]=ACC_RULE_TIME.[Time_ID]";
        Cursor cursor = null;
        try {
            int verifyTime = Utils.getVerifyTime();
            Cursor queryBySql = dataManager.queryBySql(str);
            while (queryBySql.moveToNext()) {
                int i2 = queryBySql.getInt(queryBySql.getColumnIndex("Start_Time"));
                int i3 = queryBySql.getInt(queryBySql.getColumnIndex("End_Time"));
                if (verifyTime < i2 || verifyTime > i3) {
                    z = false;
                    continue;
                } else {
                    z = true;
                    continue;
                }
                if (z) {
                    if (queryBySql != null) {
                        queryBySql.close();
                    }
                    return true;
                }
            }
            if (queryBySql != null) {
                queryBySql.close();
            }
            return false;
        } catch (SQLException e) {
            LogUtils.e(TAG, e.getMessage());
            if (cursor != null) {
                cursor.close();
            }
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public static boolean isInAccTimeZoneExtension(int i, boolean z) {
        int holidayType = getHolidayType();
        int i2 = 0;
        boolean z2 = holidayType > 0 && holidayType <= 3;
        if (z) {
            i2 = i;
        }
        if (i2 == 0) {
            i2 = getDoorAlwaysOpenTimeZone();
        }
        if (z2) {
            if (isInAccHolidayTimeZone(i2, holidayType)) {
                return isInAccHolidayTimeZone(getDoor1ValidTimeZone(), holidayType);
            }
            return isInAccHolidayTimeZone(i, holidayType);
        } else if (!z) {
            return isInAccTimeZone(i);
        } else {
            boolean isInAccTimeZone = isInAccTimeZone(getDoor1ValidTimeZone());
            return isInAccTimeZone ? isInAccTimeZone(i) : isInAccTimeZone;
        }
    }

    public static boolean isInAccHolidayTimeZone(int i, int i2) {
        boolean z;
        String str = "SELECT Start_Time,End_Time FROM ACC_TIME_ZONE,ACC_DAT_OTHERNAME,ACC_TIME_ZONE_RULE,ACC_RULE_NAME,ACC_RULE_TIME WHERE ACC_DAT_OTHERNAME.[Name]='" + String.format("hol%d", new Object[]{Integer.valueOf(i2)}) + "' AND ACC_TIME_ZONE_RULE.[Time_Zone_ID]=" + i + " AND ACC_RULE_NAME.[Name_ID]=ACC_DAT_OTHERNAME.[ID] AND ACC_RULE_NAME.[Rule_ID]=ACC_TIME_ZONE_RULE.[ID] AND ACC_RULE_TIME.[Rule_Name_ID]=ACC_RULE_NAME.[ID] AND ACC_TIME_ZONE.[ID]=ACC_RULE_TIME.[Time_ID]";
        Cursor cursor = null;
        try {
            int verifyTime = Utils.getVerifyTime();
            Cursor queryBySql = dataManager.queryBySql(str);
            while (queryBySql.moveToNext()) {
                int i3 = queryBySql.getInt(queryBySql.getColumnIndex("Start_Time"));
                int i4 = queryBySql.getInt(queryBySql.getColumnIndex("End_Time"));
                if (verifyTime < i3 || verifyTime > i4) {
                    z = false;
                    continue;
                } else {
                    z = true;
                    continue;
                }
                if (z) {
                    if (queryBySql != null) {
                        queryBySql.close();
                    }
                    return true;
                }
            }
            if (queryBySql != null) {
                queryBySql.close();
            }
            return false;
        } catch (SQLException e) {
            LogUtils.e(TAG, e.getMessage());
            if (cursor != null) {
                cursor.close();
            }
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public static int getDoor1ValidTimeZone() {
        return dataManager.getIntOption(ZKDBConfig.OPT_DOOR_1_VALID_TIME_ZONE, 1);
    }

    public static int getDoorAlwaysOpenTimeZone() {
        return dataManager.getIntOption(ZKDBConfig.DOOR1_KEEP_OPEN_TIME_ZONE, 0);
    }

    public static int getAuxFunOn() {
        return dataManager.getIntOption(ZKDBConfig.AUX_IN_FUN_ON, 0);
    }

    public static int getAuxInOption() {
        return dataManager.getIntOption(ZKDBConfig.AUX_IN_OPTION, 0);
    }

    public static int getAuxInKeepTime() {
        return dataManager.getIntOption(ZKDBConfig.AUX_IN_KEEP_TIME, 255);
    }

    public static String getDoor1CancelKeepOpenDay() {
        String strOption = dataManager.getStrOption("Door1CancelKeepOpenDay", "0");
        if (strOption.equals("1")) {
            return "0";
        }
        return strOption;
    }

    public static void setDoor1CancelKeepOpenDay(boolean z) {
        if (z) {
            dataManager.setStrOption("Door1CancelKeepOpenDay", "0");
            return;
        }
        dataManager.setStrOption("Door1CancelKeepOpenDay", String.valueOf(Utils.getVerifyDate()));
    }

    public static boolean isInAlwaysOpenTimeZone() {
        int doorAlwaysOpenTimeZone;
        if (!checkDoor1CancelKeepOpenDay() || (doorAlwaysOpenTimeZone = getDoorAlwaysOpenTimeZone()) <= 0) {
            return false;
        }
        return isInAccTimeZone(doorAlwaysOpenTimeZone);
    }

    public static boolean checkDoor1CancelKeepOpenDay() {
        return checkDoor1CancelKeepOpenDay(getDoor1CancelKeepOpenDay());
    }

    public static boolean checkDoor1CancelKeepOpenDay(String str) {
        if (str.equals("0") || str.equals("1") || !str.equals(String.valueOf(Utils.getVerifyDate()))) {
            return true;
        }
        return false;
    }

    public static boolean isInDoorAlwaysOpenTimeZone() {
        int doorAlwaysOpenTimeZone;
        if (!checkDoor1CancelKeepOpenDay() || (doorAlwaysOpenTimeZone = getDoorAlwaysOpenTimeZone()) <= 0) {
            return false;
        }
        return isInAccTimeZoneExtension(doorAlwaysOpenTimeZone, true);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: java.lang.String} */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v4, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r1v5, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r1v16 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0065 A[SYNTHETIC, Splitter:B:27:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006d A[Catch:{ Exception -> 0x0071 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getWeek() {
        /*
            java.util.Calendar r0 = java.util.Calendar.getInstance()
            r1 = 7
            int r0 = r0.get(r1)
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0076 }
            r2.<init>()     // Catch:{ Exception -> 0x0076 }
            java.lang.String r3 = "SELECT Name from ACC_DAT_OTHERNAME where ID="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x0076 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Exception -> 0x0076 }
            java.lang.String r2 = ";"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Exception -> 0x0076 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0076 }
            com.zkteco.android.db.orm.manager.DataManager r2 = dataManager     // Catch:{ SQLException -> 0x0058, all -> 0x0055 }
            android.database.Cursor r0 = r2.queryBySql(r0)     // Catch:{ SQLException -> 0x0058, all -> 0x0055 }
        L_0x0029:
            boolean r2 = r0.moveToNext()     // Catch:{ SQLException -> 0x0050, all -> 0x004b }
            if (r2 == 0) goto L_0x0045
            java.lang.String r2 = "Name"
            int r2 = r0.getColumnIndex(r2)     // Catch:{ SQLException -> 0x0050, all -> 0x004b }
            java.lang.String r1 = r0.getString(r2)     // Catch:{ SQLException -> 0x0050, all -> 0x004b }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ SQLException -> 0x0050, all -> 0x004b }
            if (r2 != 0) goto L_0x0029
            if (r0 == 0) goto L_0x0044
            r0.close()     // Catch:{ Exception -> 0x0076 }
        L_0x0044:
            return r1
        L_0x0045:
            if (r0 == 0) goto L_0x0093
            r0.close()     // Catch:{ Exception -> 0x0076 }
            goto L_0x0093
        L_0x004b:
            r2 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x006b
        L_0x0050:
            r2 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x005a
        L_0x0055:
            r2 = move-exception
            r0 = r1
            goto L_0x006b
        L_0x0058:
            r2 = move-exception
            r0 = r1
        L_0x005a:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x006a }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x006a }
            com.zktechnology.android.utils.LogUtils.e(r3, r2)     // Catch:{ all -> 0x006a }
            if (r1 == 0) goto L_0x0068
            r1.close()     // Catch:{ Exception -> 0x0071 }
        L_0x0068:
            r1 = r0
            goto L_0x0093
        L_0x006a:
            r2 = move-exception
        L_0x006b:
            if (r1 == 0) goto L_0x0070
            r1.close()     // Catch:{ Exception -> 0x0071 }
        L_0x0070:
            throw r2     // Catch:{ Exception -> 0x0071 }
        L_0x0071:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x0077
        L_0x0076:
            r0 = move-exception
        L_0x0077:
            java.lang.String r2 = TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Exc: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            com.zktechnology.android.utils.LogUtils.e(r2, r0)
        L_0x0093:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.acc.DoorAccessDao.getWeek():java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x005d, code lost:
        if (r1 == null) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005f, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0062, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x004e, code lost:
        if (r1 != null) goto L_0x005f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<com.zkteco.android.db.orm.tna.AccUserAuthorize> getUserAccessGroup(java.lang.String r4) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x0053 }
            r2.<init>()     // Catch:{ SQLException -> 0x0053 }
            java.lang.String r3 = "SELECT * FROM ACC_USER_AUTHORIZE WHERE UserPIN='"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ SQLException -> 0x0053 }
            java.lang.StringBuilder r4 = r2.append(r4)     // Catch:{ SQLException -> 0x0053 }
            java.lang.String r2 = "'"
            java.lang.StringBuilder r4 = r4.append(r2)     // Catch:{ SQLException -> 0x0053 }
            java.lang.String r4 = r4.toString()     // Catch:{ SQLException -> 0x0053 }
            com.zkteco.android.db.orm.manager.DataManager r2 = dataManager     // Catch:{ SQLException -> 0x0053 }
            android.database.Cursor r1 = r2.queryBySql(r4)     // Catch:{ SQLException -> 0x0053 }
        L_0x0025:
            boolean r4 = r1.moveToNext()     // Catch:{ SQLException -> 0x0053 }
            if (r4 == 0) goto L_0x004e
            java.lang.String r4 = "AuthorizeTimezone"
            int r4 = r1.getColumnIndex(r4)     // Catch:{ SQLException -> 0x0053 }
            int r4 = r1.getInt(r4)     // Catch:{ SQLException -> 0x0053 }
            java.lang.String r2 = "AuthorizeDoor"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ SQLException -> 0x0053 }
            int r2 = r1.getInt(r2)     // Catch:{ SQLException -> 0x0053 }
            com.zkteco.android.db.orm.tna.AccUserAuthorize r3 = new com.zkteco.android.db.orm.tna.AccUserAuthorize     // Catch:{ SQLException -> 0x0053 }
            r3.<init>()     // Catch:{ SQLException -> 0x0053 }
            r3.setAuthorizeTimezone(r4)     // Catch:{ SQLException -> 0x0053 }
            r3.setAuthorizeDoor(r2)     // Catch:{ SQLException -> 0x0053 }
            r0.add(r3)     // Catch:{ SQLException -> 0x0053 }
            goto L_0x0025
        L_0x004e:
            if (r1 == 0) goto L_0x0062
            goto L_0x005f
        L_0x0051:
            r4 = move-exception
            goto L_0x0063
        L_0x0053:
            r4 = move-exception
            java.lang.String r2 = TAG     // Catch:{ all -> 0x0051 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0051 }
            android.util.Log.e(r2, r4)     // Catch:{ all -> 0x0051 }
            if (r1 == 0) goto L_0x0062
        L_0x005f:
            r1.close()
        L_0x0062:
            return r0
        L_0x0063:
            if (r1 == 0) goto L_0x0068
            r1.close()
        L_0x0068:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.acc.DoorAccessDao.getUserAccessGroup(java.lang.String):java.util.List");
    }

    public static boolean isAntiSubmarine(String str) {
        try {
            DoorAntiSubmarineType fromInteger = DoorAntiSubmarineType.fromInteger(dataManager.getIntOption(ZKDBConfig.ANTI_PASSBACK_TYPE, 0));
            if (fromInteger == DoorAntiSubmarineType.NONE) {
                return false;
            }
            AccAttLog accAttLog = (AccAttLog) new AccAttLog().getQueryBuilder().orderBy(BiometricCommuCMD.FIELD_DESC_TMP_ID, false).where().eq("UserPIN", str).queryForFirst();
            if (accAttLog == null) {
                return false;
            }
            int intOption = dataManager.getIntOption(ZKDBConfig.DOO1_ACCESS_DIRECTION, 0);
            if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 5) {
                intOption = intOption == 1 ? 0 : 1;
            }
            DoorAccessDirection fromInteger2 = DoorAccessDirection.fromInteger(intOption);
            DoorAccessDirection fromInteger3 = DoorAccessDirection.fromInteger(accAttLog.getInOutState());
            if (!(fromInteger == DoorAntiSubmarineType.EXIT && fromInteger2 == DoorAccessDirection.EXIT && fromInteger3 == DoorAccessDirection.EXIT)) {
                if (fromInteger != DoorAntiSubmarineType.ENTER || fromInteger2 != DoorAccessDirection.ENTER || fromInteger3 != DoorAccessDirection.ENTER) {
                    if (!(fromInteger == DoorAntiSubmarineType.EXIT_ENTER && fromInteger2 == fromInteger3)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isDoorAlwaysOpen(DoorOpenType doorOpenType) {
        return doorOpenType == DoorOpenType.LOCAL_OPEN_ALWAYS || doorOpenType == DoorOpenType.FIRST_OPEN_ALWAYS || doorOpenType == DoorOpenType.REMOTE_OPEN_ALWAYS || doorOpenType == DoorOpenType.AUX_IN_OPEN_ALWAYS;
    }
}
