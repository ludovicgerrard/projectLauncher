package com.zktechnology.android.verify.dao;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.zktechnology.android.face.FaceDBUtils;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.push.util.Utils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.DateTimeUtils;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.controller.StaffHomeController;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import com.zkteco.edk.finger.lib.ZkFingerprintManager;
import com.zkteco.liveface562.ZkFaceManager;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ZKBaseDao implements IZKDao {
    protected static final String TAG = "Verify-Dao";
    public static final String USER_VALID_TIME_FUN_ON = "1";
    private static int dateFmtFunOn;
    protected static DataManager mDb;
    protected Context mContext;
    protected TemplateManager templateManager = new TemplateManager(this.mContext);

    public UserInfo getUserInfoByFace(byte[] bArr, int i, int i2) {
        return null;
    }

    public ZKBaseDao(Context context) {
        this.mContext = context;
        mDb = DBManager.getInstance();
        dateFmtFunOn = mDb.getIntOption("DateFmtFunOn", 0);
    }

    public boolean isSupportExpires() {
        if (TextUtils.isEmpty(ZKLauncher.sUserValidTimeFun)) {
            return false;
        }
        return "1".equals(ZKLauncher.sUserValidTimeFun);
    }

    public boolean checkInBlackList(UserInfo userInfo) {
        return userInfo.getExpires() == 1;
    }

    public boolean checkUserExpired(UserInfo userInfo) {
        return DateTimeUtils.isInExpires(userInfo.getStartDatetime(), userInfo.getEndDatetime(), dateFmtFunOn);
    }

    public boolean isInExpires(UserInfo userInfo) {
        if (!isSupportExpires()) {
            return true;
        }
        if (mDb.getIntOption("AccessRuleType", 0) == 1) {
            return DateTimeUtils.isInExpires(userInfo.getStartDatetime(), userInfo.getEndDatetime(), dateFmtFunOn);
        }
        int expires = userInfo.getExpires();
        if (expires == 0) {
            return true;
        }
        if (expires == 1) {
            return DateTimeUtils.isInExpires(userInfo.getStartDatetime(), userInfo.getEndDatetime(), dateFmtFunOn);
        }
        if (expires != 2) {
            if (expires != 3) {
                return false;
            }
            boolean isInExpires = DateTimeUtils.isInExpires(userInfo.getStartDatetime(), userInfo.getEndDatetime(), dateFmtFunOn);
            if (!isInExpires) {
                return isInExpires;
            }
            if (userInfo.getVaildCount() > 0) {
                return true;
            }
        } else if (userInfo.getVaildCount() > 0) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0026, code lost:
        if (r1 == null) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001a, code lost:
        if (r1 != null) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001c, code lost:
        r1.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int query14User() {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            java.lang.String r2 = "SELECT count(*) AS count FROM USER_INFO ui LEFT JOIN PERMISSION p ON ui.Privilege = p.Permission WHERE p.Permission = 14"
            com.zkteco.android.db.orm.manager.DataManager r3 = mDb     // Catch:{ Exception -> 0x0022 }
            android.database.Cursor r1 = r3.queryBySql(r2)     // Catch:{ Exception -> 0x0022 }
            boolean r2 = r1.moveToFirst()     // Catch:{ Exception -> 0x0022 }
            if (r2 == 0) goto L_0x001a
            java.lang.String r2 = "count"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Exception -> 0x0022 }
            int r0 = r1.getInt(r2)     // Catch:{ Exception -> 0x0022 }
        L_0x001a:
            if (r1 == 0) goto L_0x0029
        L_0x001c:
            r1.close()
            goto L_0x0029
        L_0x0020:
            r0 = move-exception
            goto L_0x002a
        L_0x0022:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x0029
            goto L_0x001c
        L_0x0029:
            return r0
        L_0x002a:
            if (r1 == 0) goto L_0x002f
            r1.close()
        L_0x002f:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKBaseDao.query14User():int");
    }

    public boolean hasSuperUsers() {
        return query14User() > 0;
    }

    public boolean isSuperUser(UserInfo userInfo) {
        return userInfo != null && userInfo.getPrivilege() == 14;
    }

    public UserInfo getUserInfoByFinger(byte[] bArr, int i) {
        String str;
        UserInfo userInfo = null;
        try {
            String[] strArr = new String[1];
            if (ZkFingerprintManager.getInstance().identify(bArr, i, strArr) != 0 || (str = strArr[0]) == null) {
                return null;
            }
            String[] split = str.split("_");
            String str2 = split[0];
            String str3 = split[1];
            ZKVerProcessPar.CON_MARK_BUNDLE.putSerializable(ZKVerConConst.BUNDLE_FINGER_TEMPLATE, str3);
            LogUtils.d(TAG, "user id = " + str2);
            UserInfo userInfo2 = (UserInfo) new UserInfo().queryForId(Long.valueOf(str2));
            if (userInfo2 == null) {
                try {
                    Log.d(TAG, "getUserInfoByFinger: userId == " + str2 + "  templateId == " + str3 + "  is not exist in sqlite.");
                } catch (SQLException e) {
                    e = e;
                    userInfo = userInfo2;
                }
            }
            return userInfo2;
        } catch (SQLException e2) {
            e = e2;
            LogUtils.e(TAG, "Exc: " + e.getMessage());
            return userInfo;
        }
    }

    public UserInfo getUserInfoByPin(String str) {
        try {
            return (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", str).queryForFirst();
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public UserInfo getUserInfoByMainCard(String str) {
        UserInfo userInfo = new UserInfo();
        try {
            userInfo = (UserInfo) new UserInfo().getQueryBuilder().where().eq("Main_Card", str).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (userInfo != null) {
            return userInfo;
        }
        return null;
    }

    public UserInfo getUserInfoByPalm(String str) {
        try {
            return (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", str).queryForFirst();
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public void deleteUser(UserInfo userInfo) {
        if (userInfo != null) {
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(System.currentTimeMillis()));
            String endDatetime = userInfo.getEndDatetime();
            if (userInfo.getEndDatetime().length() < 11) {
                endDatetime = userInfo.getEndDatetime() + "T23:59:59";
            } else if (userInfo.getEndDatetime().contains(" ")) {
                endDatetime = userInfo.getEndDatetime() + ":59";
            }
            int expires = userInfo.getExpires();
            if ((expires == 2 || expires == 3) && userInfo.getVaildCount() <= 0) {
                deleteValidUser(userInfo);
                return;
            }
            if (Utils.formatAccPushTime(format) > Utils.formatAccPushTime(endDatetime)) {
                deleteValidUser(userInfo);
            }
        }
    }

    private void deleteValidUser(UserInfo userInfo) {
        deleteUserPhoto(userInfo.getUser_PIN());
        try {
            StaffHomeController.deleteSingleUser(userInfo, this.templateManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUserInfo(UserInfo userInfo) {
        try {
            deleteExtUserByPIN(userInfo.getUser_PIN());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            userInfo.delete();
        } catch (SQLException e2) {
            LogUtils.e(TAG, "Exc: " + e2.getMessage());
        }
    }

    private void deleteExtUserByPIN(String str) {
        try {
            DBManager.getInstance().executeSql("ZKDB.db", "DELETE FROM ExtUser WHERE Pin = ?", new Object[]{str});
            Log.d("deleteUser", "deleteExtUser: " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUserPhoto(String str) {
        File file = new File(ZKFilePath.PICTURE_PATH + str + ZKFilePath.SUFFIX_IMAGE);
        if (file.exists()) {
            file.delete();
        }
        File file2 = new File(ZKFilePath.FACE_PATH + str + ZKFilePath.SUFFIX_IMAGE);
        if (file2.exists()) {
            file2.delete();
        }
    }

    public void deleteUserFingerTemp(String str) {
        for (FpTemplate10 next : this.templateManager.getFingerTemplateForUserPin(String.valueOf(str))) {
            try {
                next.delete();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ZkFingerprintManager.getInstance().deleteTemplate(str + "_" + next.getFingerid());
        }
    }

    public void deleteUserFaceTemp(String str) {
        ZkFaceManager.getInstance().dbDel(str);
        ZkFaceManager.getInstance().dbDel(str);
        ZkFaceManager.getInstance().dbDel(str);
    }

    public List<String> getFingerTempListByUserPIN(String str) {
        ArrayList arrayList = new ArrayList();
        for (FpTemplate10 template : this.templateManager.getFingerTemplateForUserPin(str)) {
            arrayList.add(Base64.encodeToString(template.getTemplate(), 0));
        }
        return arrayList;
    }

    public boolean registerFinger(UserInfo userInfo) {
        List<String> fingerTempListByUserPIN = getFingerTempListByUserPIN(String.valueOf(userInfo.getID()));
        return fingerTempListByUserPIN != null && fingerTempListByUserPIN.size() > 0;
    }

    public boolean registerMainCard(UserInfo userInfo) {
        return !TextUtils.isEmpty(userInfo.getMain_Card());
    }

    public boolean registerFace(UserInfo userInfo) {
        return ZkFaceManager.getInstance().queryFaceTemplate(userInfo.getUser_PIN(), new byte[256]) == 0;
    }

    public boolean registerPalm(UserInfo userInfo) {
        List<PersBiotemplate> biotemplateByUserId = FaceDBUtils.getBiotemplateByUserId(userInfo.getUser_PIN(), 8, 12, 0);
        if (biotemplateByUserId == null || biotemplateByUserId.size() <= 0) {
            return false;
        }
        return true;
    }

    public boolean registerPassword(UserInfo userInfo) {
        return !TextUtils.isEmpty(userInfo.getPassword());
    }

    public boolean registerPin(UserInfo userInfo) {
        return !TextUtils.isEmpty(userInfo.getUser_PIN());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003f, code lost:
        if (r1 == null) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0042, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001a, code lost:
        if (r1 != null) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001c, code lost:
        r1.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getBlackPhotoCount() {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
            java.lang.String r2 = "SELECT count(DISTINCT Photo_Time) as count FROM PHOTO_INDEX WHERE Photo_Type = 1"
            com.zkteco.android.db.orm.manager.DataManager r3 = mDb     // Catch:{ Exception -> 0x0022 }
            android.database.Cursor r1 = r3.queryBySql(r2)     // Catch:{ Exception -> 0x0022 }
            boolean r2 = r1.moveToFirst()     // Catch:{ Exception -> 0x0022 }
            if (r2 == 0) goto L_0x001a
            java.lang.String r2 = "count"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Exception -> 0x0022 }
            int r0 = r1.getInt(r2)     // Catch:{ Exception -> 0x0022 }
        L_0x001a:
            if (r1 == 0) goto L_0x0042
        L_0x001c:
            r1.close()
            goto L_0x0042
        L_0x0020:
            r0 = move-exception
            goto L_0x0043
        L_0x0022:
            r2 = move-exception
            java.lang.String r3 = "Verify-Dao"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0020 }
            r4.<init>()     // Catch:{ all -> 0x0020 }
            java.lang.String r5 = "Exc: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0020 }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x0020 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x0020 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0020 }
            com.zktechnology.android.utils.LogUtils.e(r3, r2)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x0042
            goto L_0x001c
        L_0x0042:
            return r0
        L_0x0043:
            if (r1 == 0) goto L_0x0048
            r1.close()
        L_0x0048:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKBaseDao.getBlackPhotoCount():int");
    }

    public void deleteBlackPhoto(int i) {
        Cursor cursor = null;
        try {
            Cursor queryBySql = mDb.queryBySql("SELECT replace(replace(replace(Photo_Time, 'T', ''), '-', ''),':','') AS photoName FROM PHOTO_INDEX WHERE Photo_Type = 1 LIMIT 0 , " + i);
            if (queryBySql.moveToFirst()) {
                do {
                    new File(ZKFilePath.BLACK_LIST_PATH + queryBySql.getString(queryBySql.getColumnIndex("photoName")) + ZKFilePath.SUFFIX_IMAGE).delete();
                } while (queryBySql.moveToNext());
            }
            Cursor queryBySql2 = mDb.queryBySql("DELETE FROM PHOTO_INDEX WHERE ID IN( SELECT ID FROM PHOTO_INDEX WHERE Photo_Type = 1 ORDER BY ID ASC LIMIT " + i + " )");
            if (queryBySql != null) {
                queryBySql.close();
            }
            if (queryBySql2 != null) {
                queryBySql2.close();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "Exc: " + e.getMessage());
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003f, code lost:
        if (r1 == null) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0042, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001a, code lost:
        if (r1 != null) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001c, code lost:
        r1.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getAllPhotoCount() {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
            java.lang.String r2 = "SELECT count(DISTINCT Photo_Time) as count FROM PHOTO_INDEX"
            com.zkteco.android.db.orm.manager.DataManager r3 = mDb     // Catch:{ Exception -> 0x0022 }
            android.database.Cursor r1 = r3.queryBySql(r2)     // Catch:{ Exception -> 0x0022 }
            boolean r2 = r1.moveToFirst()     // Catch:{ Exception -> 0x0022 }
            if (r2 == 0) goto L_0x001a
            java.lang.String r2 = "count"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Exception -> 0x0022 }
            int r0 = r1.getInt(r2)     // Catch:{ Exception -> 0x0022 }
        L_0x001a:
            if (r1 == 0) goto L_0x0042
        L_0x001c:
            r1.close()
            goto L_0x0042
        L_0x0020:
            r0 = move-exception
            goto L_0x0043
        L_0x0022:
            r2 = move-exception
            java.lang.String r3 = "Verify-Dao"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0020 }
            r4.<init>()     // Catch:{ all -> 0x0020 }
            java.lang.String r5 = "Exc: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0020 }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x0020 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x0020 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0020 }
            com.zktechnology.android.utils.LogUtils.e(r3, r2)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x0042
            goto L_0x001c
        L_0x0042:
            return r0
        L_0x0043:
            if (r1 == 0) goto L_0x0048
            r1.close()
        L_0x0048:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dao.ZKBaseDao.getAllPhotoCount():int");
    }

    public int getDoor1FirstCardOpenDoor() {
        return mDb.getIntOption("Door1FirstCardOpenDoor", 0);
    }

    public String getDoor1CancelKeepOpenDay() {
        String strOption = mDb.getStrOption("Door1CancelKeepOpenDay", "0");
        if (strOption.equals("1")) {
            return "0";
        }
        return strOption;
    }

    public boolean checkDoor1CancelKeepOpenDay() {
        String door1CancelKeepOpenDay = getDoor1CancelKeepOpenDay();
        if (!door1CancelKeepOpenDay.equals("0") && door1CancelKeepOpenDay.equals(String.valueOf(Utils.getVerifyDate()))) {
            return false;
        }
        return true;
    }

    public boolean checkSuperPassword(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String strOption = mDb.getStrOption("Door1SupperPassWord", "");
        if ("0".equals(strOption)) {
            strOption = "-1";
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(strOption) || !str.equals(strOption)) {
            return false;
        }
        return true;
    }

    public boolean checkStressPassword(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String strOption = mDb.getStrOption("Door1ForcePassWord", "");
        if ("0".equals(strOption)) {
            strOption = "-1";
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(strOption) || !str.equals(strOption)) {
            return false;
        }
        return true;
    }
}
