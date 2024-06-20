package com.zktechnology.android.att.AttInterceptors;

import com.zkteco.android.db.orm.tna.AccHoliday;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttEffectiveTimePeriodInterceptor extends BaseAttInterceptor implements AttInterceptor {
    private Date date;
    private int holidayTime;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v27, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v4, resolved type: com.zkteco.android.db.orm.tna.AccTimeZoneWeek} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v49, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: com.zkteco.android.db.orm.tna.AccTimeZoneWeek} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v72, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v13, resolved type: com.zkteco.android.db.orm.tna.AccTimeZoneWeek} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void interceptor(com.zktechnology.android.att.AttRequest r20, com.zktechnology.android.att.AttResponse r21) {
        /*
            r19 = this;
            r9 = r19
            r0 = r21
            java.lang.String r10 = "ID"
            com.zkteco.android.db.orm.tna.UserInfo r1 = r20.getUserInfo()
            if (r1 != 0) goto L_0x000d
            return
        L_0x000d:
            long r2 = java.lang.System.currentTimeMillis()
            java.util.Date r4 = new java.util.Date
            r4.<init>(r2)
            r9.date = r4
            java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat
            java.util.Locale r3 = java.util.Locale.US
            java.lang.String r4 = "HHmm"
            r2.<init>(r4, r3)
            java.util.Date r3 = r9.date
            java.lang.String r2 = r2.format(r3)
            int r11 = java.lang.Integer.parseInt(r2)
            java.util.Calendar r2 = java.util.Calendar.getInstance()
            java.util.Date r3 = r9.date
            r2.setTime(r3)
            r3 = 7
            int r2 = r2.get(r3)
            switch(r2) {
                case 1: goto L_0x005a;
                case 2: goto L_0x0055;
                case 3: goto L_0x0050;
                case 4: goto L_0x004b;
                case 5: goto L_0x0046;
                case 6: goto L_0x0041;
                default: goto L_0x003c;
            }
        L_0x003c:
            java.lang.String r2 = "SatStart"
            java.lang.String r3 = "SatEnd"
            goto L_0x005e
        L_0x0041:
            java.lang.String r2 = "FriStart"
            java.lang.String r3 = "FriEnd"
            goto L_0x005e
        L_0x0046:
            java.lang.String r2 = "ThursStart"
            java.lang.String r3 = "ThursEnd"
            goto L_0x005e
        L_0x004b:
            java.lang.String r2 = "WedStart"
            java.lang.String r3 = "WedEnd"
            goto L_0x005e
        L_0x0050:
            java.lang.String r2 = "TuesStart"
            java.lang.String r3 = "TuesEnd"
            goto L_0x005e
        L_0x0055:
            java.lang.String r2 = "MonStart"
            java.lang.String r3 = "MonEnd"
            goto L_0x005e
        L_0x005a:
            java.lang.String r2 = "SunStart"
            java.lang.String r3 = "SunEnd"
        L_0x005e:
            r12 = r2
            r13 = r3
            com.zkteco.android.db.orm.tna.AccGroup r2 = new com.zkteco.android.db.orm.tna.AccGroup     // Catch:{ Exception -> 0x0416 }
            r2.<init>()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.QueryBuilder r2 = r2.getQueryBuilder()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.where()     // Catch:{ Exception -> 0x0416 }
            int r3 = r1.getAcc_Group_ID()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.eq(r10, r3)     // Catch:{ Exception -> 0x0416 }
            java.lang.Object r2 = r2.queryForFirst()     // Catch:{ Exception -> 0x0416 }
            com.zkteco.android.db.orm.tna.AccGroup r2 = (com.zkteco.android.db.orm.tna.AccGroup) r2     // Catch:{ Exception -> 0x0416 }
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ Exception -> 0x0416 }
            r14.<init>()     // Catch:{ Exception -> 0x0416 }
            r8 = 2131755157(0x7f100095, float:1.9141185E38)
            r3 = 1
            r7 = 26
            if (r2 == 0) goto L_0x02fb
            int r4 = r2.getVaildHoliday()     // Catch:{ Exception -> 0x0416 }
            if (r4 != r3) goto L_0x01d0
            boolean r4 = r19.isHoliday()     // Catch:{ Exception -> 0x0416 }
            if (r4 == 0) goto L_0x01d0
            int r4 = r1.getIs_Group_TZ()     // Catch:{ Exception -> 0x0416 }
            if (r4 != r3) goto L_0x00d2
            int r1 = r2.getTimezone1()     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x00af
            int r1 = r2.getTimezone1()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0416 }
            r14.add(r1)     // Catch:{ Exception -> 0x0416 }
        L_0x00af:
            int r1 = r2.getTimezone2()     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x00c0
            int r1 = r2.getTimezone2()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0416 }
            r14.add(r1)     // Catch:{ Exception -> 0x0416 }
        L_0x00c0:
            int r1 = r2.getTimezone3()     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x0105
            int r1 = r2.getTimezone3()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0416 }
            r14.add(r1)     // Catch:{ Exception -> 0x0416 }
            goto L_0x0105
        L_0x00d2:
            int r2 = r1.getTimezone1()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x00e3
            int r2 = r1.getTimezone1()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x0416 }
            r14.add(r2)     // Catch:{ Exception -> 0x0416 }
        L_0x00e3:
            int r2 = r1.getTimezone2()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x00f4
            int r2 = r1.getTimezone2()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x0416 }
            r14.add(r2)     // Catch:{ Exception -> 0x0416 }
        L_0x00f4:
            int r2 = r1.getTimezone3()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x0105
            int r1 = r1.getTimezone3()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0416 }
            r14.add(r1)     // Catch:{ Exception -> 0x0416 }
        L_0x0105:
            int r1 = r14.size()     // Catch:{ Exception -> 0x0416 }
            if (r1 <= 0) goto L_0x014f
            java.util.Iterator r1 = r14.iterator()     // Catch:{ Exception -> 0x0416 }
            r15 = 0
        L_0x0110:
            boolean r2 = r1.hasNext()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x0150
            java.lang.Object r2 = r1.next()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ Exception -> 0x0416 }
            com.zkteco.android.db.orm.tna.AccTimeZoneWeek r3 = new com.zkteco.android.db.orm.tna.AccTimeZoneWeek     // Catch:{ Exception -> 0x0416 }
            r3.<init>()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.QueryBuilder r3 = r3.getQueryBuilder()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r3 = r3.where()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r3.eq(r10, r2)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.and()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.le(r12, r3)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.and()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.ge(r13, r3)     // Catch:{ Exception -> 0x0416 }
            java.lang.Object r2 = r2.queryForFirst()     // Catch:{ Exception -> 0x0416 }
            r15 = r2
            com.zkteco.android.db.orm.tna.AccTimeZoneWeek r15 = (com.zkteco.android.db.orm.tna.AccTimeZoneWeek) r15     // Catch:{ Exception -> 0x0416 }
            if (r15 == 0) goto L_0x0110
            goto L_0x0150
        L_0x014f:
            r15 = 0
        L_0x0150:
            com.zkteco.android.db.orm.tna.AccTimeZoneWeek r1 = new com.zkteco.android.db.orm.tna.AccTimeZoneWeek     // Catch:{ Exception -> 0x0416 }
            r1.<init>()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.QueryBuilder r1 = r1.getQueryBuilder()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r1 = r1.where()     // Catch:{ Exception -> 0x0416 }
            int r2 = r9.holidayTime     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r1 = r1.eq(r10, r2)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r1 = r1.and()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r1 = r1.le(r12, r2)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r1 = r1.and()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r1 = r1.ge(r13, r2)     // Catch:{ Exception -> 0x0416 }
            java.lang.Object r1 = r1.queryForFirst()     // Catch:{ Exception -> 0x0416 }
            com.zkteco.android.db.orm.tna.AccTimeZoneWeek r1 = (com.zkteco.android.db.orm.tna.AccTimeZoneWeek) r1     // Catch:{ Exception -> 0x0416 }
            if (r15 == 0) goto L_0x01b3
            if (r1 == 0) goto L_0x01b3
            java.util.List r1 = com.zktechnology.android.att.DoorAttManager.getAttDoorTypeList()     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r2 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            boolean r1 = r1.contains(r2)     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x041a
            int r1 = r21.getEventCode()     // Catch:{ Exception -> 0x0416 }
            if (r1 != r7) goto L_0x01a2
            com.zktechnology.android.att.types.AttDoorType r1 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            r9.setAttDoorType(r0, r1)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x01a2:
            r3 = 1
            r4 = 0
            r5 = 1
            com.zktechnology.android.att.types.AttAlarmType r6 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r7 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            r8 = 1
            r1 = r19
            r2 = r21
            r1.setAttResponse(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x01b3:
            android.content.Context r1 = r20.getContext()     // Catch:{ Exception -> 0x0416 }
            android.content.res.Resources r1 = r1.getResources()     // Catch:{ Exception -> 0x0416 }
            java.lang.String r4 = r1.getString(r8)     // Catch:{ Exception -> 0x0416 }
            r3 = 0
            r5 = 22
            com.zktechnology.android.att.types.AttAlarmType r6 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r7 = com.zktechnology.android.att.types.AttDoorType.NONE     // Catch:{ Exception -> 0x0416 }
            r8 = 0
            r1 = r19
            r2 = r21
            r1.setAttResponse(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x01d0:
            int r4 = r1.getIs_Group_TZ()     // Catch:{ Exception -> 0x0416 }
            if (r4 != r3) goto L_0x020a
            int r1 = r2.getTimezone1()     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x01e7
            int r1 = r2.getTimezone1()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0416 }
            r14.add(r1)     // Catch:{ Exception -> 0x0416 }
        L_0x01e7:
            int r1 = r2.getTimezone2()     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x01f8
            int r1 = r2.getTimezone2()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0416 }
            r14.add(r1)     // Catch:{ Exception -> 0x0416 }
        L_0x01f8:
            int r1 = r2.getTimezone3()     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x023d
            int r1 = r2.getTimezone3()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0416 }
            r14.add(r1)     // Catch:{ Exception -> 0x0416 }
            goto L_0x023d
        L_0x020a:
            int r2 = r1.getTimezone1()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x021b
            int r2 = r1.getTimezone1()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x0416 }
            r14.add(r2)     // Catch:{ Exception -> 0x0416 }
        L_0x021b:
            int r2 = r1.getTimezone2()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x022c
            int r2 = r1.getTimezone2()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x0416 }
            r14.add(r2)     // Catch:{ Exception -> 0x0416 }
        L_0x022c:
            int r2 = r1.getTimezone3()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x023d
            int r1 = r1.getTimezone3()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0416 }
            r14.add(r1)     // Catch:{ Exception -> 0x0416 }
        L_0x023d:
            int r1 = r14.size()     // Catch:{ Exception -> 0x0416 }
            if (r1 <= 0) goto L_0x0287
            java.util.Iterator r1 = r14.iterator()     // Catch:{ Exception -> 0x0416 }
            r15 = 0
        L_0x0248:
            boolean r2 = r1.hasNext()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x0288
            java.lang.Object r2 = r1.next()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ Exception -> 0x0416 }
            com.zkteco.android.db.orm.tna.AccTimeZoneWeek r3 = new com.zkteco.android.db.orm.tna.AccTimeZoneWeek     // Catch:{ Exception -> 0x0416 }
            r3.<init>()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.QueryBuilder r3 = r3.getQueryBuilder()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r3 = r3.where()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r3.eq(r10, r2)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.and()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.le(r12, r3)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.and()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.ge(r13, r3)     // Catch:{ Exception -> 0x0416 }
            java.lang.Object r2 = r2.queryForFirst()     // Catch:{ Exception -> 0x0416 }
            r15 = r2
            com.zkteco.android.db.orm.tna.AccTimeZoneWeek r15 = (com.zkteco.android.db.orm.tna.AccTimeZoneWeek) r15     // Catch:{ Exception -> 0x0416 }
            if (r15 == 0) goto L_0x0248
            goto L_0x0288
        L_0x0287:
            r15 = 0
        L_0x0288:
            if (r15 == 0) goto L_0x02b4
            java.util.List r1 = com.zktechnology.android.att.DoorAttManager.getAttDoorTypeList()     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r2 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            boolean r1 = r1.contains(r2)     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x041a
            int r1 = r21.getEventCode()     // Catch:{ Exception -> 0x0416 }
            if (r1 != r7) goto L_0x02a3
            com.zktechnology.android.att.types.AttDoorType r1 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            r9.setAttDoorType(r0, r1)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x02a3:
            r3 = 1
            r4 = 0
            r5 = 1
            com.zktechnology.android.att.types.AttAlarmType r6 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r7 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            r8 = 1
            r1 = r19
            r2 = r21
            r1.setAttResponse(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x02b4:
            java.util.List r1 = com.zktechnology.android.att.DoorAttManager.getAttDoorTypeList()     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r2 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            boolean r1 = r1.contains(r2)     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x02de
            int r1 = r21.getEventCode()     // Catch:{ Exception -> 0x0416 }
            if (r1 != r7) goto L_0x02cd
            com.zktechnology.android.att.types.AttDoorType r1 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            r9.setAttDoorType(r0, r1)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x02cd:
            r3 = 1
            r4 = 0
            r5 = 1
            com.zktechnology.android.att.types.AttAlarmType r6 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r7 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            r8 = 1
            r1 = r19
            r2 = r21
            r1.setAttResponse(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x02de:
            android.content.Context r1 = r20.getContext()     // Catch:{ Exception -> 0x0416 }
            android.content.res.Resources r1 = r1.getResources()     // Catch:{ Exception -> 0x0416 }
            java.lang.String r4 = r1.getString(r8)     // Catch:{ Exception -> 0x0416 }
            r3 = 0
            r5 = 22
            com.zktechnology.android.att.types.AttAlarmType r6 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r7 = com.zktechnology.android.att.types.AttDoorType.NONE     // Catch:{ Exception -> 0x0416 }
            r8 = 0
            r1 = r19
            r2 = r21
            r1.setAttResponse(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x02fb:
            int r2 = r1.getIs_Group_TZ()     // Catch:{ Exception -> 0x0416 }
            if (r2 != r3) goto L_0x0323
            android.content.Context r1 = r20.getContext()     // Catch:{ Exception -> 0x0416 }
            android.content.res.Resources r1 = r1.getResources()     // Catch:{ Exception -> 0x0416 }
            java.lang.String r4 = r1.getString(r8)     // Catch:{ Exception -> 0x0416 }
            r3 = 0
            r5 = 22
            com.zktechnology.android.att.types.AttAlarmType r6 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r16 = com.zktechnology.android.att.types.AttDoorType.NONE     // Catch:{ Exception -> 0x0416 }
            r17 = 0
            r1 = r19
            r2 = r21
            r15 = r7
            r7 = r16
            r8 = r17
            r1.setAttResponse(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0416 }
            goto L_0x0357
        L_0x0323:
            r15 = r7
            int r2 = r1.getTimezone1()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x0335
            int r2 = r1.getTimezone1()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x0416 }
            r14.add(r2)     // Catch:{ Exception -> 0x0416 }
        L_0x0335:
            int r2 = r1.getTimezone2()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x0346
            int r2 = r1.getTimezone2()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x0416 }
            r14.add(r2)     // Catch:{ Exception -> 0x0416 }
        L_0x0346:
            int r2 = r1.getTimezone3()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x0357
            int r1 = r1.getTimezone3()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0416 }
            r14.add(r1)     // Catch:{ Exception -> 0x0416 }
        L_0x0357:
            int r1 = r14.size()     // Catch:{ Exception -> 0x0416 }
            if (r1 <= 0) goto L_0x03a3
            java.util.Iterator r1 = r14.iterator()     // Catch:{ Exception -> 0x0416 }
            r18 = 0
        L_0x0363:
            boolean r2 = r1.hasNext()     // Catch:{ Exception -> 0x0416 }
            if (r2 == 0) goto L_0x03a5
            java.lang.Object r2 = r1.next()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ Exception -> 0x0416 }
            com.zkteco.android.db.orm.tna.AccTimeZoneWeek r3 = new com.zkteco.android.db.orm.tna.AccTimeZoneWeek     // Catch:{ Exception -> 0x0416 }
            r3.<init>()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.QueryBuilder r3 = r3.getQueryBuilder()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r3 = r3.where()     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r3.eq(r10, r2)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.and()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.le(r12, r3)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.and()     // Catch:{ Exception -> 0x0416 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0416 }
            com.j256.ormlite.stmt.Where r2 = r2.ge(r13, r3)     // Catch:{ Exception -> 0x0416 }
            java.lang.Object r2 = r2.queryForFirst()     // Catch:{ Exception -> 0x0416 }
            r18 = r2
            com.zkteco.android.db.orm.tna.AccTimeZoneWeek r18 = (com.zkteco.android.db.orm.tna.AccTimeZoneWeek) r18     // Catch:{ Exception -> 0x0416 }
            if (r18 == 0) goto L_0x0363
            goto L_0x03a5
        L_0x03a3:
            r18 = 0
        L_0x03a5:
            if (r18 == 0) goto L_0x03cf
            java.util.List r1 = com.zktechnology.android.att.DoorAttManager.getAttDoorTypeList()     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r2 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            boolean r1 = r1.contains(r2)     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x041a
            int r1 = r21.getEventCode()     // Catch:{ Exception -> 0x0416 }
            if (r1 != r15) goto L_0x03bf
            com.zktechnology.android.att.types.AttDoorType r1 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            r9.setAttDoorType(r0, r1)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x03bf:
            r3 = 1
            r4 = 0
            r5 = 1
            com.zktechnology.android.att.types.AttAlarmType r6 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r7 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            r8 = 1
            r1 = r19
            r2 = r21
            r1.setAttResponse(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x03cf:
            java.util.List r1 = com.zktechnology.android.att.DoorAttManager.getAttDoorTypeList()     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r2 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            boolean r1 = r1.contains(r2)     // Catch:{ Exception -> 0x0416 }
            if (r1 == 0) goto L_0x03f7
            int r1 = r21.getEventCode()     // Catch:{ Exception -> 0x0416 }
            if (r1 != r15) goto L_0x03e7
            com.zktechnology.android.att.types.AttDoorType r1 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            r9.setAttDoorType(r0, r1)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x03e7:
            r3 = 1
            r4 = 0
            r5 = 1
            com.zktechnology.android.att.types.AttAlarmType r6 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r7 = com.zktechnology.android.att.types.AttDoorType.ALWAYSOPENDOOR     // Catch:{ Exception -> 0x0416 }
            r8 = 1
            r1 = r19
            r2 = r21
            r1.setAttResponse(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x03f7:
            android.content.Context r1 = r20.getContext()     // Catch:{ Exception -> 0x0416 }
            android.content.res.Resources r1 = r1.getResources()     // Catch:{ Exception -> 0x0416 }
            r2 = 2131755157(0x7f100095, float:1.9141185E38)
            java.lang.String r4 = r1.getString(r2)     // Catch:{ Exception -> 0x0416 }
            r3 = 0
            r5 = 22
            com.zktechnology.android.att.types.AttAlarmType r6 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x0416 }
            com.zktechnology.android.att.types.AttDoorType r7 = com.zktechnology.android.att.types.AttDoorType.NONE     // Catch:{ Exception -> 0x0416 }
            r8 = 0
            r1 = r19
            r2 = r21
            r1.setAttResponse(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0416 }
            goto L_0x041a
        L_0x0416:
            r0 = move-exception
            r0.printStackTrace()
        L_0x041a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.att.AttInterceptors.AttEffectiveTimePeriodInterceptor.interceptor(com.zktechnology.android.att.AttRequest, com.zktechnology.android.att.AttResponse):void");
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
