package com.zktechnology.android.verify.dao;

import android.content.Context;
import android.database.Cursor;
import androidx.exifinterface.media.ExifInterface;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.push.acc.AccPush;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.push.util.Utils;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.orm.tna.AccAttLog;
import com.zkteco.android.db.orm.tna.AccMultiUser;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ZKAccDao extends ZKBaseDao implements IZKAccDao {
    private static final String TAG = "Access--ZKAccDao";
    private static ZKAccDao instance;
    private String sql;

    private ZKAccDao(Context context) {
        super(context);
    }

    public static ZKAccDao getInstance(Context context) {
        if (instance == null) {
            synchronized (ZKAccDao.class) {
                if (instance == null) {
                    instance = new ZKAccDao(context);
                }
            }
        }
        return instance;
    }

    private String getVerifyTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()).replace(" ", ExifInterface.GPS_DIRECTION_TRUE);
    }

    public void addAccAttLog(int i, int i2, int i3, int i4) {
        addAccAttLog((UserInfo) null, i, i2, i3, i4);
    }

    public void addAccAttLog(UserInfo userInfo, int i, int i2, int i3, int i4) {
        addAccAttLog(userInfo, i, i2, i3, getVerifyTime(), i4, -1.0d, 0);
    }

    public void addAccAttLog(UserInfo userInfo, int i, int i2, int i3, String str, int i4, double d, int i5) {
        AccAttLog accAttLog = new AccAttLog();
        if (userInfo != null) {
            accAttLog.setUserPIN(userInfo.getUser_PIN());
            accAttLog.setMainCard(userInfo.getMain_Card());
        }
        accAttLog.setInOutState(i);
        accAttLog.setTimeSecond(Utils.formatAccPushTime(str));
        accAttLog.setDoorID(i2);
        accAttLog.setEventType(i3);
        accAttLog.setVerified(i4);
        if (d == 255.0d) {
            accAttLog.setTemperature("255");
        } else if (d != -1.0d) {
            accAttLog.setTemperature(String.valueOf(d));
        }
        if (ZKLauncher.maskDetectionFunOn != 1 || ZKLauncher.enalbeMaskDetection != 1) {
            accAttLog.setMask_Flag(255);
        } else if (i5 == 2) {
            accAttLog.setMask_Flag(1);
        } else if (i5 == 1) {
            accAttLog.setMask_Flag(0);
        } else {
            accAttLog.setMask_Flag(255);
        }
        try {
            FileLogUtils.writeDatabaseLog("launcher push: addAccAttLog");
            if (i3 != 4) {
                AccPush.getInstance().push(accAttLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAccAttLog(int i) {
        try {
            Cursor queryBySql = mDb.queryBySql("DELETE FROM ACC_ATT_LOG WHERE id IN(SELECT id FROM ACC_ATT_LOG ORDER BY id ASC LIMIT " + i + ")");
            if (queryBySql != null) {
                queryBySql.close();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "Exc: " + e.getMessage());
        }
    }

    public void deleteUserAccAttLog(UserInfo userInfo) {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(System.currentTimeMillis()));
        try {
            String endDatetime = userInfo.getEndDatetime();
            if (userInfo.getEndDatetime().length() < 11) {
                endDatetime = userInfo.getEndDatetime() + "T23:59:59";
            } else if (userInfo.getEndDatetime().contains(" ")) {
                endDatetime = userInfo.getEndDatetime() + ":59";
            }
            int formatAccPushTime = Utils.formatAccPushTime(endDatetime);
            this.sql = "DELETE FROM ACC_ATT_LOG WHERE UserPIN = " + userInfo.getUser_PIN() + " AND TimeSecond < " + formatAccPushTime;
            Cursor queryBySql = Utils.formatAccPushTime(format) > formatAccPushTime ? mDb.queryBySql(this.sql) : null;
            if (queryBySql != null) {
                queryBySql.close();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "Exc: " + e.getMessage());
        }
    }

    public int getAccAttLogCount() {
        int i = 0;
        Cursor cursor = null;
        try {
            Cursor queryBySql = mDb.queryBySql("SELECT COUNT(*) as count FROM ACC_ATT_LOG");
            if (queryBySql.moveToFirst()) {
                i = queryBySql.getInt(queryBySql.getColumnIndex("count"));
            }
            if (queryBySql != null) {
                queryBySql.close();
            }
            return i;
        } catch (Exception e) {
            LogUtils.e(TAG, "Exc: " + e.getMessage());
            if (cursor != null) {
                cursor.close();
            }
            return 0;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void setDBOption(String str, int i) {
        mDb.setIntOption(str, i);
    }

    public void setDBOption(String str, String str2) {
        mDb.setStrOption(str, str2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0028, code lost:
        if (r1 == null) goto L_0x002b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002b, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        if (r1 != null) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        r1.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getMaxCaptureCount() {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            java.lang.String r2 = "SELECT COUNT(DISTINCT Photo_Time) as count FROM PHOTO_INDEX WHERE Photo_Type=0"
            com.zkteco.android.db.orm.manager.DataManager r3 = mDb     // Catch:{ Exception -> 0x0024 }
            android.database.Cursor r1 = r3.queryBySql(r2)     // Catch:{ Exception -> 0x0024 }
            if (r1 == 0) goto L_0x001c
            boolean r2 = r1.moveToFirst()     // Catch:{ Exception -> 0x0024 }
            if (r2 == 0) goto L_0x001c
            java.lang.String r2 = "count"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Exception -> 0x0024 }
            int r0 = r1.getInt(r2)     // Catch:{ Exception -> 0x0024 }
        L_0x001c:
            if (r1 == 0) goto L_0x002b
        L_0x001e:
            r1.close()
            goto L_0x002b
        L_0x0022:
            r0 = move-exception
            goto L_0x002c
        L_0x0024:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x0022 }
            if (r1 == 0) goto L_0x002b
            goto L_0x001e
        L_0x002b:
            return r0
        L_0x002c:
            if (r1 == 0) goto L_0x0031
            r1.close()
        L_0x0031:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKAccDao.getMaxCaptureCount():int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void deleteAttImage(int r5) {
        /*
            r4 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005e }
            r1.<init>()     // Catch:{ Exception -> 0x005e }
            java.lang.String r2 = "SELECT replace(replace(replace(Photo_Time, 'T', ''), '-', ''),':','') || '-' ||User_PIN AS photoName FROM PHOTO_INDEX WHERE Photo_Type = 0 LIMIT 0,"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r5 = r1.append(r5)     // Catch:{ Exception -> 0x005e }
            java.lang.String r1 = ""
            java.lang.StringBuilder r5 = r5.append(r1)     // Catch:{ Exception -> 0x005e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x005e }
            com.zkteco.android.db.orm.manager.DataManager r1 = mDb     // Catch:{ Exception -> 0x005e }
            android.database.Cursor r0 = r1.queryBySql(r5)     // Catch:{ Exception -> 0x005e }
            if (r0 == 0) goto L_0x0059
            boolean r5 = r0.moveToFirst()     // Catch:{ Exception -> 0x005e }
            if (r5 == 0) goto L_0x0059
        L_0x0028:
            java.lang.String r5 = "photoName"
            int r5 = r0.getColumnIndex(r5)     // Catch:{ Exception -> 0x005e }
            java.lang.String r5 = r0.getString(r5)     // Catch:{ Exception -> 0x005e }
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005e }
            r2.<init>()     // Catch:{ Exception -> 0x005e }
            java.lang.String r3 = com.zkteco.android.zkcore.utils.ZKFilePath.ATT_PHOTO_PATH     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r5 = r2.append(r5)     // Catch:{ Exception -> 0x005e }
            java.lang.String r2 = com.zkteco.android.zkcore.utils.ZKFilePath.SUFFIX_IMAGE     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch:{ Exception -> 0x005e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x005e }
            r1.<init>(r5)     // Catch:{ Exception -> 0x005e }
            r1.delete()     // Catch:{ Exception -> 0x005e }
            boolean r5 = r0.moveToNext()     // Catch:{ Exception -> 0x005e }
            if (r5 != 0) goto L_0x0028
        L_0x0059:
            if (r0 == 0) goto L_0x0067
            goto L_0x0064
        L_0x005c:
            r5 = move-exception
            goto L_0x0068
        L_0x005e:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ all -> 0x005c }
            if (r0 == 0) goto L_0x0067
        L_0x0064:
            r0.close()
        L_0x0067:
            return
        L_0x0068:
            if (r0 == 0) goto L_0x006d
            r0.close()
        L_0x006d:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKAccDao.deleteAttImage(int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void deleteBlackListImage(int r5) {
        /*
            r4 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005e }
            r1.<init>()     // Catch:{ Exception -> 0x005e }
            java.lang.String r2 = "SELECT replace(replace(replace(Photo_Time, 'T', ''), '-', ''),':','') AS photoName FROM PHOTO_INDEX WHERE Photo_Type=1 LIMIT 0,"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r5 = r1.append(r5)     // Catch:{ Exception -> 0x005e }
            java.lang.String r1 = ""
            java.lang.StringBuilder r5 = r5.append(r1)     // Catch:{ Exception -> 0x005e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x005e }
            com.zkteco.android.db.orm.manager.DataManager r1 = mDb     // Catch:{ Exception -> 0x005e }
            android.database.Cursor r0 = r1.queryBySql(r5)     // Catch:{ Exception -> 0x005e }
            if (r0 == 0) goto L_0x0059
            boolean r5 = r0.moveToFirst()     // Catch:{ Exception -> 0x005e }
            if (r5 == 0) goto L_0x0059
        L_0x0028:
            java.lang.String r5 = "photoName"
            int r5 = r0.getColumnIndex(r5)     // Catch:{ Exception -> 0x005e }
            java.lang.String r5 = r0.getString(r5)     // Catch:{ Exception -> 0x005e }
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005e }
            r2.<init>()     // Catch:{ Exception -> 0x005e }
            java.lang.String r3 = com.zkteco.android.zkcore.utils.ZKFilePath.BLACK_LIST_PATH     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r5 = r2.append(r5)     // Catch:{ Exception -> 0x005e }
            java.lang.String r2 = com.zkteco.android.zkcore.utils.ZKFilePath.SUFFIX_IMAGE     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch:{ Exception -> 0x005e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x005e }
            r1.<init>(r5)     // Catch:{ Exception -> 0x005e }
            r1.delete()     // Catch:{ Exception -> 0x005e }
            boolean r5 = r0.moveToNext()     // Catch:{ Exception -> 0x005e }
            if (r5 != 0) goto L_0x0028
        L_0x0059:
            if (r0 == 0) goto L_0x0067
            goto L_0x0064
        L_0x005c:
            r5 = move-exception
            goto L_0x0068
        L_0x005e:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ all -> 0x005c }
            if (r0 == 0) goto L_0x0067
        L_0x0064:
            r0.close()
        L_0x0067:
            return
        L_0x0068:
            if (r0 == 0) goto L_0x006d
            r0.close()
        L_0x006d:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKAccDao.deleteBlackListImage(int):void");
    }

    private void deleteAttRecord(int i) {
        try {
            Cursor queryBySql = mDb.queryBySql("DELETE FROM PHOTO_INDEX WHERE ID IN(SELECT ID FROM PHOTO_INDEX WHERE Photo_Type=0 ORDER BY ID ASC LIMIT " + i + ")");
            if (queryBySql != null) {
                queryBySql.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAttRecord2(int i) {
        try {
            Cursor queryBySql = mDb.queryBySql("DELETE FROM PHOTO_INDEX WHERE ID IN(SELECT ID FROM PHOTO_INDEX WHERE Photo_Type=1 ORDER BY ID ASC LIMIT " + i + " )");
            if (queryBySql != null) {
                queryBySql.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCapture(int i) {
        deleteAttImage(i);
        deleteAttRecord(i);
    }

    public int getMultiCardOpenDoorFuncState() {
        return mDb.getIntOption("Door1MultiCardOpenDoorFunOn", 1);
    }

    public int getMultiCardOpenDoorState() {
        return mDb.getIntOption("Door1MultiCardOpenDoor", 0);
    }

    public int getStressAlarmDelay() {
        return mDb.getIntOption("DUAD", 0);
    }

    public int getDoor1VerifyType() {
        return mDb.getIntOption(ZKDBConfig.OPT_DOOR_1_VERIFY_TYPE, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003e, code lost:
        if (r1 == null) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0041, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0032, code lost:
        if (r1 != null) goto L_0x0034;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0034, code lost:
        r1.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getAccFirstOpen(java.lang.String r5) {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003a }
            r2.<init>()     // Catch:{ Exception -> 0x003a }
            java.lang.String r3 = "SELECT Timezone FROM ACC_FIRST_OPEN WHERE UserPIN = "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x003a }
            java.lang.StringBuilder r5 = r2.append(r5)     // Catch:{ Exception -> 0x003a }
            java.lang.String r2 = " LIMIT 1"
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch:{ Exception -> 0x003a }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x003a }
            com.zkteco.android.db.orm.manager.DataManager r2 = mDb     // Catch:{ Exception -> 0x003a }
            android.database.Cursor r1 = r2.queryBySql(r5)     // Catch:{ Exception -> 0x003a }
            boolean r5 = r1.moveToFirst()     // Catch:{ Exception -> 0x003a }
            if (r5 == 0) goto L_0x0032
            java.lang.String r5 = "Timezone"
            int r5 = r1.getColumnIndex(r5)     // Catch:{ Exception -> 0x003a }
            int r5 = r1.getInt(r5)     // Catch:{ Exception -> 0x003a }
            r0 = r5
        L_0x0032:
            if (r1 == 0) goto L_0x0041
        L_0x0034:
            r1.close()
            goto L_0x0041
        L_0x0038:
            r5 = move-exception
            goto L_0x0042
        L_0x003a:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ all -> 0x0038 }
            if (r1 == 0) goto L_0x0041
            goto L_0x0034
        L_0x0041:
            return r0
        L_0x0042:
            if (r1 == 0) goto L_0x0047
            r1.close()
        L_0x0047:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKAccDao.getAccFirstOpen(java.lang.String):int");
    }

    public List<AccMultiUser> getAccGroupList(int i) {
        try {
            return new AccMultiUser().getQueryBuilder().where().eq("Group1", Integer.valueOf(i)).or().eq("Group2", Integer.valueOf(i)).or().eq("Group3", Integer.valueOf(i)).or().eq("Group4", Integer.valueOf(i)).or().eq("Group5", Integer.valueOf(i)).query();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletePhoto(int i) {
        deleteAttImage(i);
        deleteAttRecord(i);
        deleteBlackListImage(i);
        deleteAttRecord2(i);
    }
}
