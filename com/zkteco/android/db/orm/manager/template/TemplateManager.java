package com.zkteco.android.db.orm.manager.template;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.zktechnology.android.aop.DataManagerAspect;
import com.zkteco.android.core.sdk.Biokey10Manager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.reflect.Factory;

public class TemplateManager {
    public static final int IN_VALID_VALUE = -1;
    private static final String TAG = TemplateManager.class.getSimpleName();
    private static final /* synthetic */ JoinPoint.StaticPart ajc$tjp_0 = null;
    private Context context;
    private DataManager dataManager;
    private int majorVerFinger;
    private int minorVerFinger;

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("TemplateManager.java", TemplateManager.class);
        ajc$tjp_0 = factory.makeSJP(JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig("1", "open", "com.zkteco.android.db.orm.manager.DataManager", "android.content.Context", "context", "", "void"), 42);
    }

    static {
        ajc$preClinit();
    }

    /* JADX INFO: finally extract failed */
    public TemplateManager(Context context2) {
        this.context = context2;
        DataManager dataManager2 = new DataManager();
        this.dataManager = dataManager2;
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_0, (Object) this, (Object) dataManager2, (Object) context2);
        try {
            DataManagerAspect.aspectOf().beforeOpen(makeJP);
            dataManager2.open(context2);
            DataManagerAspect.aspectOf().afterOpen(makeJP);
            this.majorVerFinger = this.dataManager.getIntOption("~ZKFPVersion", 0);
            this.minorVerFinger = 0;
        } catch (Throwable th) {
            DataManagerAspect.aspectOf().afterOpen(makeJP);
            throw th;
        }
    }

    public void deleteAllFingerTemplate() {
        try {
            for (FpTemplate10 delete : getAllFingerTemplate()) {
                delete.delete();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public List<FpTemplate10> getFingerTemplatePage(long j, long j2) {
        return getTemplates((String) null, -1, j, j2);
    }

    public void deleteFingerTemplateAndBiokey10ForUserPin(String str) {
        try {
            List<FpTemplate10> fingerTemplateForUserPin = getFingerTemplateForUserPin(String.valueOf(getUserID(str)));
            Biokey10Manager biokey10Manager = new Biokey10Manager(this.context);
            for (FpTemplate10 next : fingerTemplateForUserPin) {
                biokey10Manager.delete(str + "_" + next.getFingerid());
                next.delete();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        if (r3 == null) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0047, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0038, code lost:
        if (r3 != null) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003a, code lost:
        r3.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getUserID(java.lang.String r7) {
        /*
            r6 = this;
            java.lang.String r0 = "'"
            java.lang.String r1 = "select * from USER_INFO where User_PIN = "
            r2 = 0
            r3 = 0
            com.zkteco.android.db.orm.manager.DataManager r4 = r6.dataManager     // Catch:{ Exception -> 0x0040 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0040 }
            r5.<init>()     // Catch:{ Exception -> 0x0040 }
            java.lang.StringBuilder r1 = r5.append(r1)     // Catch:{ Exception -> 0x0040 }
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ Exception -> 0x0040 }
            java.lang.StringBuilder r7 = r1.append(r7)     // Catch:{ Exception -> 0x0040 }
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ Exception -> 0x0040 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0040 }
            android.database.Cursor r3 = r4.queryBySql(r7)     // Catch:{ Exception -> 0x0040 }
            if (r3 == 0) goto L_0x0038
            boolean r7 = r3.moveToFirst()     // Catch:{ Exception -> 0x0040 }
            if (r7 == 0) goto L_0x0038
            java.lang.String r7 = "ID"
            int r7 = r3.getColumnIndex(r7)     // Catch:{ Exception -> 0x0040 }
            int r7 = r3.getInt(r7)     // Catch:{ Exception -> 0x0040 }
            r2 = r7
        L_0x0038:
            if (r3 == 0) goto L_0x0047
        L_0x003a:
            r3.close()
            goto L_0x0047
        L_0x003e:
            r7 = move-exception
            goto L_0x0048
        L_0x0040:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ all -> 0x003e }
            if (r3 == 0) goto L_0x0047
            goto L_0x003a
        L_0x0047:
            return r2
        L_0x0048:
            if (r3 == 0) goto L_0x004d
            r3.close()
        L_0x004d:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.db.orm.manager.template.TemplateManager.getUserID(java.lang.String):int");
    }

    public void deleteFingerTemplateForUserPin(String str) {
        try {
            for (FpTemplate10 delete : getFingerTemplateForUserPin(String.valueOf(getUserID(str)))) {
                delete.delete();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public List<FpTemplate10> getAllLightFaceTemplate() {
        return getTemplates((String) null, -1, -1, -1);
    }

    public long getFingerCount() {
        return getCount(1);
    }

    public long getLightFaceCount() {
        return getCount(9);
    }

    public List<FpTemplate10> getAllFingerTemplate() {
        return getTemplates((String) null, -1, -1, -1);
    }

    public void updateFingerDuressForUserPinAndFingerNum(int i, String str, int i2) {
        try {
            List<FpTemplate10> fingerTemplateForUserPinAndFingerNum = getFingerTemplateForUserPinAndFingerNum(String.valueOf(getUserID(str)), i2);
            if (fingerTemplateForUserPinAndFingerNum != null) {
                for (FpTemplate10 next : fingerTemplateForUserPinAndFingerNum) {
                    if (i == 1) {
                        next.setValid(3);
                    } else {
                        next.setValid(1);
                    }
                    next.update();
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public List<FpTemplate10> getFingerTemplateForUserPinAndFingerNum(String str, int i) {
        return getTemplates(str, i, -1, -1);
    }

    public List<FpTemplate10> getFingerTemplateForUserPin(String str) {
        return getTemplates(str, -1, -1, -1);
    }

    public void updateFingerTemplate(UserInfo userInfo, int i, byte[] bArr) {
        updateTemplateForOther(bArr, userInfo, i);
    }

    public void insertFingerTemplate(UserInfo userInfo, int i, byte[] bArr, int i2) {
        insertPersBioTemplate(bArr, i2, i, userInfo);
    }

    public void updateTemplateForOther(byte[] bArr, UserInfo userInfo, int i) {
        if (bArr != null && bArr.length != 0 && userInfo != null) {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("update fptemplate10");
                stringBuffer.append(" set template = ?");
                stringBuffer.append(" where ");
                stringBuffer.append("pin = " + userInfo.getID());
                stringBuffer.append(" and ");
                stringBuffer.append("fingerid = " + i);
                this.dataManager.executeSql("ZKDB.db", stringBuffer.toString(), new Object[]{bArr});
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    public void insertPersBioTemplate(byte[] bArr, int i, int i2, UserInfo userInfo) {
        if (userInfo != null && bArr != null && bArr.length != 0) {
            this.dataManager.executeSql("ZKDB.db", "insert into fptemplate10(SEND_FLAG, fingerid, pin, size, template, valid) values(?,?,?,?,?,?)", new Object[]{0, Integer.valueOf(i2), Long.valueOf(userInfo.getID()), Integer.valueOf(bArr.length), bArr, Integer.valueOf(i == 1 ? 3 : 1)});
        }
    }

    public List<FpTemplate10> getTemplates(String str, int i, long j, long j2) {
        boolean z;
        List<FpTemplate10> queryForAll;
        ArrayList arrayList = new ArrayList();
        try {
            QueryBuilder queryBuilder = new FpTemplate10().getQueryBuilder();
            boolean z2 = true;
            if (j != -1) {
                queryBuilder = queryBuilder.limit(Long.valueOf(j));
                z = true;
            } else {
                z = false;
            }
            if (j2 != -1) {
                queryBuilder = queryBuilder.offset(Long.valueOf(j2));
            } else {
                z2 = z;
            }
            Where where = null;
            if (!TextUtils.isEmpty(str)) {
                where = queryBuilder.where().eq("pin", "'" + str + "'");
                if (i != -1) {
                    where = where.and().eq("fingerid", Integer.valueOf(i));
                }
            } else if (i != -1) {
                where = queryBuilder.where().eq("fingerid", Integer.valueOf(i));
            }
            if (where != null) {
                queryForAll = where.query();
            } else if (z2) {
                queryForAll = queryBuilder.query();
            } else {
                queryForAll = new FpTemplate10().queryForAll();
            }
            return queryForAll;
        } catch (Exception e) {
            e.getStackTrace();
            arrayList.clear();
            return arrayList;
        }
    }

    public long getCount(int i) {
        Cursor cursor;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            if (i == 1) {
                stringBuffer.append("select count(*) as number from fptemplate10");
            } else {
                stringBuffer.append("select count(*) as number from Pers_BioTemplate where bio_type = ");
                stringBuffer.append(i);
            }
            cursor = null;
            try {
                Cursor queryBySql = this.dataManager.queryBySql(stringBuffer.toString());
                long j = (queryBySql == null || !queryBySql.moveToFirst()) ? 0 : (long) queryBySql.getInt(queryBySql.getColumnIndex("number"));
                if (queryBySql != null) {
                    queryBySql.close();
                }
                return j;
            } catch (Exception e) {
                e.getStackTrace();
                if (cursor == null) {
                    return 0;
                }
                cursor.close();
                return 0;
            }
        } catch (Exception e2) {
            e2.getStackTrace();
            return 0;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}
