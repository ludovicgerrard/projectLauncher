package com.zktechnology.android.att.AttInterceptors;

import com.tencent.map.geolocation.TencentLocationRequest;

public class AttMultiCardOpenDoorInterceptor extends BaseAttInterceptor implements AttInterceptor {
    private final int TIMEINTERVAL = TencentLocationRequest.ONLY_GPS_TIME_OUT;

    /* JADX WARNING: Removed duplicated region for block: B:101:0x01b3 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0108 A[Catch:{ Exception -> 0x036e }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0133 A[Catch:{ Exception -> 0x036e }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x015e A[Catch:{ Exception -> 0x036e }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0189 A[Catch:{ Exception -> 0x036e }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x01bd A[Catch:{ Exception -> 0x036e }, LOOP:0: B:17:0x00d3->B:62:0x01bd, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void interceptor(com.zktechnology.android.att.AttRequest r17, com.zktechnology.android.att.AttResponse r18) {
        /*
            r16 = this;
            com.zkteco.android.db.orm.tna.UserInfo r0 = r17.getUserInfo()
            r1 = 0
            if (r0 != 0) goto L_0x0019
            java.util.List r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()
            r0.clear()
            java.util.Map r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()
            r0.clear()
            com.zktechnology.android.att.DoorAttManager.lastTime = r1
            return
        L_0x0019:
            int r3 = r0.getAcc_Group_ID()
            if (r3 > 0) goto L_0x004c
            java.util.List r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()
            r0.clear()
            java.util.Map r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()
            r0.clear()
            com.zktechnology.android.att.DoorAttManager.lastTime = r1
            android.content.Context r0 = r17.getContext()
            r1 = 2131755154(0x7f100092, float:1.914118E38)
            java.lang.String r5 = r0.getString(r1)
            r4 = 0
            r6 = 23
            com.zktechnology.android.att.types.AttAlarmType r7 = com.zktechnology.android.att.types.AttAlarmType.NONE
            com.zktechnology.android.att.types.AttDoorType r8 = r18.getAttDoorType()
            r9 = 0
            r2 = r16
            r3 = r18
            r2.setAttResponse(r3, r4, r5, r6, r7, r8, r9)
            return
        L_0x004c:
            java.util.Map r3 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x036e }
            int r3 = r3.size()     // Catch:{ Exception -> 0x036e }
            r4 = 2131755156(0x7f100094, float:1.9141183E38)
            r5 = 2131755222(0x7f1000d6, float:1.9141317E38)
            r6 = 1
            r7 = 0
            if (r3 != 0) goto L_0x0215
            com.zkteco.android.db.orm.tna.AccUnlockcomb r3 = new com.zkteco.android.db.orm.tna.AccUnlockcomb     // Catch:{ Exception -> 0x036e }
            r3.<init>()     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.QueryBuilder r3 = r3.getQueryBuilder()     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.Where r3 = r3.where()     // Catch:{ Exception -> 0x036e }
            java.lang.String r8 = "Group1"
            int r9 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.Where r3 = r3.eq(r8, r9)     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.Where r3 = r3.or()     // Catch:{ Exception -> 0x036e }
            java.lang.String r8 = "Group2"
            int r9 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.Where r3 = r3.eq(r8, r9)     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.Where r3 = r3.or()     // Catch:{ Exception -> 0x036e }
            java.lang.String r8 = "Group3"
            int r9 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.Where r3 = r3.eq(r8, r9)     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.Where r3 = r3.or()     // Catch:{ Exception -> 0x036e }
            java.lang.String r8 = "Group4"
            int r9 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.Where r3 = r3.eq(r8, r9)     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.Where r3 = r3.or()     // Catch:{ Exception -> 0x036e }
            java.lang.String r8 = "Group5"
            int r9 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x036e }
            com.j256.ormlite.stmt.Where r3 = r3.eq(r8, r9)     // Catch:{ Exception -> 0x036e }
            java.util.List r3 = r3.query()     // Catch:{ Exception -> 0x036e }
            if (r3 == 0) goto L_0x01fa
            int r8 = r3.size()     // Catch:{ Exception -> 0x036e }
            if (r8 != 0) goto L_0x00cf
            goto L_0x01fa
        L_0x00cf:
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Exception -> 0x036e }
        L_0x00d3:
            boolean r4 = r3.hasNext()     // Catch:{ Exception -> 0x036e }
            if (r4 == 0) goto L_0x01ce
            java.lang.Object r4 = r3.next()     // Catch:{ Exception -> 0x036e }
            com.zkteco.android.db.orm.tna.AccUnlockcomb r4 = (com.zkteco.android.db.orm.tna.AccUnlockcomb) r4     // Catch:{ Exception -> 0x036e }
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ Exception -> 0x036e }
            r8.<init>()     // Catch:{ Exception -> 0x036e }
            int r9 = r4.getGroup1()     // Catch:{ Exception -> 0x036e }
            if (r9 == 0) goto L_0x0101
            int r9 = r4.getGroup1()     // Catch:{ Exception -> 0x036e }
            int r10 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            if (r9 != r10) goto L_0x00f6
            r9 = r7
            goto L_0x0102
        L_0x00f6:
            int r9 = r4.getGroup1()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x036e }
            r8.add(r9)     // Catch:{ Exception -> 0x036e }
        L_0x0101:
            r9 = r6
        L_0x0102:
            int r10 = r4.getGroup2()     // Catch:{ Exception -> 0x036e }
            if (r10 == 0) goto L_0x012d
            int r10 = r4.getGroup2()     // Catch:{ Exception -> 0x036e }
            int r11 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            if (r10 != r11) goto L_0x0122
            if (r9 == 0) goto L_0x0116
            r9 = r7
            goto L_0x012d
        L_0x0116:
            int r10 = r4.getGroup2()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x036e }
            r8.add(r10)     // Catch:{ Exception -> 0x036e }
            goto L_0x012d
        L_0x0122:
            int r10 = r4.getGroup2()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x036e }
            r8.add(r10)     // Catch:{ Exception -> 0x036e }
        L_0x012d:
            int r10 = r4.getGroup3()     // Catch:{ Exception -> 0x036e }
            if (r10 == 0) goto L_0x0158
            int r10 = r4.getGroup3()     // Catch:{ Exception -> 0x036e }
            int r11 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            if (r10 != r11) goto L_0x014d
            if (r9 == 0) goto L_0x0141
            r9 = r7
            goto L_0x0158
        L_0x0141:
            int r10 = r4.getGroup3()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x036e }
            r8.add(r10)     // Catch:{ Exception -> 0x036e }
            goto L_0x0158
        L_0x014d:
            int r10 = r4.getGroup3()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x036e }
            r8.add(r10)     // Catch:{ Exception -> 0x036e }
        L_0x0158:
            int r10 = r4.getGroup4()     // Catch:{ Exception -> 0x036e }
            if (r10 == 0) goto L_0x0183
            int r10 = r4.getGroup4()     // Catch:{ Exception -> 0x036e }
            int r11 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            if (r10 != r11) goto L_0x0178
            if (r9 == 0) goto L_0x016c
            r9 = r7
            goto L_0x0183
        L_0x016c:
            int r10 = r4.getGroup4()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x036e }
            r8.add(r10)     // Catch:{ Exception -> 0x036e }
            goto L_0x0183
        L_0x0178:
            int r10 = r4.getGroup4()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x036e }
            r8.add(r10)     // Catch:{ Exception -> 0x036e }
        L_0x0183:
            int r10 = r4.getGroup5()     // Catch:{ Exception -> 0x036e }
            if (r10 == 0) goto L_0x01ad
            int r10 = r4.getGroup5()     // Catch:{ Exception -> 0x036e }
            int r11 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            if (r10 != r11) goto L_0x01a2
            if (r9 == 0) goto L_0x0196
            goto L_0x01ad
        L_0x0196:
            int r9 = r4.getGroup5()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x036e }
            r8.add(r9)     // Catch:{ Exception -> 0x036e }
            goto L_0x01ad
        L_0x01a2:
            int r9 = r4.getGroup5()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x036e }
            r8.add(r9)     // Catch:{ Exception -> 0x036e }
        L_0x01ad:
            int r9 = r8.size()     // Catch:{ Exception -> 0x036e }
            if (r9 != 0) goto L_0x01bd
            java.util.Map r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x036e }
            r0.clear()     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.DoorAttManager.lastTime = r1     // Catch:{ Exception -> 0x036e }
            return
        L_0x01bd:
            java.util.Map r9 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x036e }
            long r10 = r4.getID()     // Catch:{ Exception -> 0x036e }
            java.lang.Long r4 = java.lang.Long.valueOf(r10)     // Catch:{ Exception -> 0x036e }
            r9.put(r4, r8)     // Catch:{ Exception -> 0x036e }
            goto L_0x00d3
        L_0x01ce:
            long r1 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.DoorAttManager.lastTime = r1     // Catch:{ Exception -> 0x036e }
            android.content.Context r1 = r17.getContext()     // Catch:{ Exception -> 0x036e }
            java.lang.String r9 = r1.getString(r5)     // Catch:{ Exception -> 0x036e }
            r8 = 1
            r10 = 26
            com.zktechnology.android.att.types.AttAlarmType r11 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.types.AttDoorType r12 = r18.getAttDoorType()     // Catch:{ Exception -> 0x036e }
            r13 = 0
            r6 = r16
            r7 = r18
            r6.setAttResponse(r7, r8, r9, r10, r11, r12, r13)     // Catch:{ Exception -> 0x036e }
            java.util.List r1 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()     // Catch:{ Exception -> 0x036e }
            java.lang.String r0 = r0.getUser_PIN()     // Catch:{ Exception -> 0x036e }
            r1.add(r0)     // Catch:{ Exception -> 0x036e }
            goto L_0x0372
        L_0x01fa:
            android.content.Context r0 = r17.getContext()     // Catch:{ Exception -> 0x036e }
            java.lang.String r8 = r0.getString(r4)     // Catch:{ Exception -> 0x036e }
            r7 = 0
            r9 = 48
            com.zktechnology.android.att.types.AttAlarmType r10 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.types.AttDoorType r11 = r18.getAttDoorType()     // Catch:{ Exception -> 0x036e }
            r12 = 0
            r5 = r16
            r6 = r18
            r5.setAttResponse(r6, r7, r8, r9, r10, r11, r12)     // Catch:{ Exception -> 0x036e }
            goto L_0x0372
        L_0x0215:
            long r8 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x036e }
            long r10 = com.zktechnology.android.att.DoorAttManager.lastTime     // Catch:{ Exception -> 0x036e }
            long r8 = r8 - r10
            r10 = 8000(0x1f40, double:3.9525E-320)
            int r3 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r3 <= 0) goto L_0x0250
            java.util.List r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()     // Catch:{ Exception -> 0x036e }
            r0.clear()     // Catch:{ Exception -> 0x036e }
            java.util.Map r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x036e }
            r0.clear()     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.DoorAttManager.lastTime = r1     // Catch:{ Exception -> 0x036e }
            android.content.Context r0 = r17.getContext()     // Catch:{ Exception -> 0x036e }
            r1 = 2131755221(0x7f1000d5, float:1.9141315E38)
            java.lang.String r5 = r0.getString(r1)     // Catch:{ Exception -> 0x036e }
            r4 = 0
            r6 = 48
            com.zktechnology.android.att.types.AttAlarmType r7 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.types.AttDoorType r8 = r18.getAttDoorType()     // Catch:{ Exception -> 0x036e }
            r9 = 0
            r2 = r16
            r3 = r18
            r2.setAttResponse(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x036e }
            goto L_0x0372
        L_0x0250:
            java.util.List r3 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()     // Catch:{ Exception -> 0x036e }
            java.lang.String r8 = r0.getUser_PIN()     // Catch:{ Exception -> 0x036e }
            boolean r3 = r3.contains(r8)     // Catch:{ Exception -> 0x036e }
            if (r3 != 0) goto L_0x034e
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x036e }
            r3.<init>()     // Catch:{ Exception -> 0x036e }
            java.util.Map r8 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x036e }
            java.util.Set r8 = r8.keySet()     // Catch:{ Exception -> 0x036e }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ Exception -> 0x036e }
            r9 = r7
        L_0x0270:
            boolean r10 = r8.hasNext()     // Catch:{ Exception -> 0x036e }
            if (r10 == 0) goto L_0x02f7
            java.lang.Object r10 = r8.next()     // Catch:{ Exception -> 0x036e }
            java.lang.Long r10 = (java.lang.Long) r10     // Catch:{ Exception -> 0x036e }
            long r10 = r10.longValue()     // Catch:{ Exception -> 0x036e }
            java.util.Map r12 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x036e }
            java.lang.Long r13 = java.lang.Long.valueOf(r10)     // Catch:{ Exception -> 0x036e }
            java.lang.Object r12 = r12.get(r13)     // Catch:{ Exception -> 0x036e }
            java.util.List r12 = (java.util.List) r12     // Catch:{ Exception -> 0x036e }
            int r13 = r12.size()     // Catch:{ Exception -> 0x036e }
            if (r13 != r6) goto L_0x02b3
            int r13 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ Exception -> 0x036e }
            boolean r13 = r12.contains(r13)     // Catch:{ Exception -> 0x036e }
            if (r13 == 0) goto L_0x02b3
            java.util.List r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()     // Catch:{ Exception -> 0x036e }
            r0.clear()     // Catch:{ Exception -> 0x036e }
            java.util.Map r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x036e }
            r0.clear()     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.DoorAttManager.lastTime = r1     // Catch:{ Exception -> 0x036e }
            return
        L_0x02b3:
            int r13 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ Exception -> 0x036e }
            boolean r13 = r12.contains(r13)     // Catch:{ Exception -> 0x036e }
            if (r13 == 0) goto L_0x02ee
            r13 = r7
        L_0x02c2:
            int r14 = r12.size()     // Catch:{ Exception -> 0x036e }
            if (r13 >= r14) goto L_0x0270
            java.lang.Object r14 = r12.get(r13)     // Catch:{ Exception -> 0x036e }
            java.lang.Integer r14 = (java.lang.Integer) r14     // Catch:{ Exception -> 0x036e }
            int r14 = r14.intValue()     // Catch:{ Exception -> 0x036e }
            int r15 = r0.getAcc_Group_ID()     // Catch:{ Exception -> 0x036e }
            if (r14 != r15) goto L_0x02eb
            java.util.Map r9 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x036e }
            java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ Exception -> 0x036e }
            java.lang.Object r9 = r9.get(r10)     // Catch:{ Exception -> 0x036e }
            java.util.List r9 = (java.util.List) r9     // Catch:{ Exception -> 0x036e }
            r9.remove(r13)     // Catch:{ Exception -> 0x036e }
            r9 = r6
            goto L_0x0270
        L_0x02eb:
            int r13 = r13 + 1
            goto L_0x02c2
        L_0x02ee:
            java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ Exception -> 0x036e }
            r3.add(r10)     // Catch:{ Exception -> 0x036e }
            goto L_0x0270
        L_0x02f7:
            if (r9 != 0) goto L_0x0323
            java.util.List r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()     // Catch:{ Exception -> 0x036e }
            r0.clear()     // Catch:{ Exception -> 0x036e }
            java.util.Map r0 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x036e }
            r0.clear()     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.DoorAttManager.lastTime = r1     // Catch:{ Exception -> 0x036e }
            android.content.Context r0 = r17.getContext()     // Catch:{ Exception -> 0x036e }
            java.lang.String r8 = r0.getString(r4)     // Catch:{ Exception -> 0x036e }
            r7 = 0
            r9 = 48
            com.zktechnology.android.att.types.AttAlarmType r10 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.types.AttDoorType r11 = r18.getAttDoorType()     // Catch:{ Exception -> 0x036e }
            r12 = 0
            r5 = r16
            r6 = r18
            r5.setAttResponse(r6, r7, r8, r9, r10, r11, r12)     // Catch:{ Exception -> 0x036e }
            return
        L_0x0323:
            java.util.Iterator r1 = r3.iterator()     // Catch:{ Exception -> 0x036e }
        L_0x0327:
            boolean r2 = r1.hasNext()     // Catch:{ Exception -> 0x036e }
            if (r2 == 0) goto L_0x0343
            java.lang.Object r2 = r1.next()     // Catch:{ Exception -> 0x036e }
            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ Exception -> 0x036e }
            long r2 = r2.longValue()     // Catch:{ Exception -> 0x036e }
            java.util.Map r4 = com.zktechnology.android.att.DoorAttManager.getAccGroupIdMap()     // Catch:{ Exception -> 0x036e }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ Exception -> 0x036e }
            r4.remove(r2)     // Catch:{ Exception -> 0x036e }
            goto L_0x0327
        L_0x0343:
            java.util.List r1 = com.zktechnology.android.att.DoorAttManager.getAccGroupPinList()     // Catch:{ Exception -> 0x036e }
            java.lang.String r0 = r0.getUser_PIN()     // Catch:{ Exception -> 0x036e }
            r1.add(r0)     // Catch:{ Exception -> 0x036e }
        L_0x034e:
            long r0 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.DoorAttManager.lastTime = r0     // Catch:{ Exception -> 0x036e }
            android.content.Context r0 = r17.getContext()     // Catch:{ Exception -> 0x036e }
            java.lang.String r9 = r0.getString(r5)     // Catch:{ Exception -> 0x036e }
            r8 = 1
            r10 = 26
            com.zktechnology.android.att.types.AttAlarmType r11 = com.zktechnology.android.att.types.AttAlarmType.NONE     // Catch:{ Exception -> 0x036e }
            com.zktechnology.android.att.types.AttDoorType r12 = r18.getAttDoorType()     // Catch:{ Exception -> 0x036e }
            r13 = 0
            r6 = r16
            r7 = r18
            r6.setAttResponse(r7, r8, r9, r10, r11, r12, r13)     // Catch:{ Exception -> 0x036e }
            goto L_0x0372
        L_0x036e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0372:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.att.AttInterceptors.AttMultiCardOpenDoorInterceptor.interceptor(com.zktechnology.android.att.AttRequest, com.zktechnology.android.att.AttResponse):void");
    }
}
