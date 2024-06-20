package com.zkteco.android.employeemgmt.util;

import android.content.Context;
import android.util.Pair;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.adapter.UserInfoCallback;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class SQLiteUtils {
    private static final String TAG = "SQLiteUtils";

    public static void queryUserByRx(Context context, String str, long j, final int i, final UserInfoCallback userInfoCallback) {
        Observable.create(new ObservableOnSubscribe(str, j, i) {
            public final /* synthetic */ String f$0;
            public final /* synthetic */ long f$1;
            public final /* synthetic */ int f$2;

            {
                this.f$0 = r1;
                this.f$1 = r2;
                this.f$2 = r4;
            }

            public final void subscribe(ObservableEmitter observableEmitter) {
                SQLiteUtils.lambda$queryUserByRx$0(this.f$0, this.f$1, this.f$2, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Pair<Long, UserInfo>>>() {
            public void onComplete() {
            }

            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<Pair<Long, UserInfo>> list) {
                UserInfoCallback.this.callback(list);
                UserInfoCallback.this.callback2(list, i);
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:0x0270  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x027a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void lambda$queryUserByRx$0(java.lang.String r22, long r23, int r25, io.reactivex.rxjava3.core.ObservableEmitter r26) throws java.lang.Throwable {
        /*
            r0 = r22
            r1 = r23
            r3 = r25
            r4 = r26
            java.lang.String r5 = "&"
            com.zkteco.android.db.orm.manager.DataManager r6 = com.zktechnology.android.utils.DBManager.getInstance()
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            int r8 = com.zktechnology.android.launcher2.ZKLauncher.longName
            java.lang.String r9 = ""
            r10 = 1
            if (r8 != r10) goto L_0x005e
            int r8 = com.zktechnology.android.launcher2.ZKLauncher.sAccessRuleType
            if (r8 != r10) goto L_0x005e
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r11 = "select * from USER_INFO As us        left join ExtUser As eu on us.User_PIN = eu.Pin            where                us.User_PIN LIKE '%"
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.StringBuilder r8 = r8.append(r0)
            java.lang.String r11 = "%'            or                eu.FirstName LIKE '%"
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.StringBuilder r8 = r8.append(r0)
            java.lang.String r11 = "%'            or                eu.LastName LIKE '%"
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.StringBuilder r0 = r8.append(r0)
            java.lang.String r8 = "%'        order by USER_PIN asc            limit "
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = "            offset "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.String r0 = r0.toString()
            goto L_0x0093
        L_0x005e:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r11 = "select * from USER_INFO    where        USER_INFO.Name LIKE  '%"
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.StringBuilder r8 = r8.append(r0)
            java.lang.String r11 = "%'    or        USER_INFO.User_PIN LIKE '%"
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.StringBuilder r0 = r8.append(r0)
            java.lang.String r8 = "%'    order by USER_PIN asc        limit "
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = "        offset "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.String r0 = r0.toString()
        L_0x0093:
            r1 = 0
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x0268, all -> 0x0264 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0268, all -> 0x0264 }
            r3.<init>()     // Catch:{ Exception -> 0x0268, all -> 0x0264 }
            java.lang.String r8 = "subscribe: "
            java.lang.StringBuilder r3 = r3.append(r8)     // Catch:{ Exception -> 0x0268, all -> 0x0264 }
            java.lang.StringBuilder r3 = r3.append(r0)     // Catch:{ Exception -> 0x0268, all -> 0x0264 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0268, all -> 0x0264 }
            android.util.Log.d(r2, r3)     // Catch:{ Exception -> 0x0268, all -> 0x0264 }
            android.database.Cursor r1 = r6.queryBySql(r0)     // Catch:{ Exception -> 0x0268, all -> 0x0264 }
            boolean r0 = r1.moveToFirst()     // Catch:{ Exception -> 0x0268, all -> 0x0264 }
            if (r0 == 0) goto L_0x025b
        L_0x00b6:
            boolean r0 = r1.isAfterLast()     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            if (r0 != 0) goto L_0x025b
            java.lang.String r0 = "ID"
            int r0 = r1.getColumnIndex(r0)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            long r2 = r1.getLong(r0)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r0 = "User_PIN"
            int r0 = r1.getColumnIndex(r0)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r0 = r1.getString(r0)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r6 = "Privilege"
            int r6 = r1.getColumnIndex(r6)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            int r6 = r1.getInt(r6)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r8 = "Name"
            int r8 = r1.getColumnIndex(r8)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r8 = r1.getString(r8)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r11 = "Password"
            int r11 = r1.getColumnIndex(r11)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r11 = r1.getString(r11)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r12 = "Face_Group_ID"
            int r12 = r1.getColumnIndex(r12)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            int r12 = r1.getInt(r12)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r13 = "Acc_Group_ID"
            int r13 = r1.getColumnIndex(r13)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            int r13 = r1.getInt(r13)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r14 = "Dept_ID"
            int r14 = r1.getColumnIndex(r14)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            int r14 = r1.getInt(r14)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r15 = "Is_Group_TZ"
            int r15 = r1.getColumnIndex(r15)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            int r15 = r1.getInt(r15)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r10 = "Verify_Type"
            int r10 = r1.getColumnIndex(r10)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            int r10 = r1.getInt(r10)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            r16 = r9
            java.lang.String r9 = "Main_Card"
            int r9 = r1.getColumnIndex(r9)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r9 = r1.getString(r9)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r4 = "Vice_Card"
            int r4 = r1.getColumnIndex(r4)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            java.lang.String r4 = r1.getString(r4)     // Catch:{ Exception -> 0x0256, all -> 0x0251 }
            r17 = r7
            java.lang.String r7 = "Expires"
            int r7 = r1.getColumnIndex(r7)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            int r7 = r1.getInt(r7)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r22 = r2
            java.lang.String r2 = "StartDatetime"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            java.lang.String r3 = "EndDatetime"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r24 = r3
            java.lang.String r3 = "VaildCount"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            int r3 = r1.getInt(r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r25 = r3
            java.lang.String r3 = "Timezone1"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            int r3 = r1.getInt(r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r18 = r3
            java.lang.String r3 = "Timezone2"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            int r3 = r1.getInt(r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r19 = r3
            java.lang.String r3 = "Timezone3"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            int r3 = r1.getInt(r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r20 = r3
            com.zkteco.android.db.orm.tna.UserInfo r3 = new com.zkteco.android.db.orm.tna.UserInfo     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.<init>()     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.setUser_PIN(r0)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.setPrivilege(r6)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sAccessRuleType     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            java.lang.String r6 = " "
            r21 = r2
            r2 = 1
            if (r0 != r2) goto L_0x01e4
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.longName     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            if (r0 != r2) goto L_0x01e4
            java.lang.String r0 = "FirstName"
            int r0 = r1.getColumnIndex(r0)     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            java.lang.String r0 = r1.getString(r0)     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            java.lang.String r8 = "LastName"
            int r8 = r1.getColumnIndex(r8)     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            java.lang.String r8 = r1.getString(r8)     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            if (r0 == 0) goto L_0x01b9
            goto L_0x01bb
        L_0x01b9:
            r0 = r16
        L_0x01bb:
            if (r8 == 0) goto L_0x01d2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            r2.<init>()     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            java.lang.StringBuilder r0 = r0.append(r6)     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
        L_0x01d2:
            r3.setName(r0)     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            goto L_0x01f3
        L_0x01d6:
            r0 = move-exception
            r3 = r26
            r2 = r17
            goto L_0x0278
        L_0x01dd:
            r0 = move-exception
            r3 = r26
            r2 = r17
            goto L_0x026b
        L_0x01e4:
            if (r8 == 0) goto L_0x01f0
            boolean r0 = r8.contains(r5)     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
            if (r0 == 0) goto L_0x01f0
            java.lang.String r8 = r8.replace(r5, r6)     // Catch:{ Exception -> 0x01dd, all -> 0x01d6 }
        L_0x01f0:
            r3.setName(r8)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
        L_0x01f3:
            r3.setPassword(r11)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.setFace_Group_ID(r12)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.setAcc_Group_ID(r13)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.setDept_ID(r14)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.setIs_Group_TZ(r15)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.setVerify_Type(r10)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.setMain_Card(r9)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.setVice_Card(r4)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r3.setExpires(r7)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r0 = r21
            r3.setStartDatetime(r0)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r0 = r24
            r3.setEndDatetime(r0)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r0 = r25
            r3.setVaildCount(r0)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r0 = r18
            r3.setTimezone1(r0)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r0 = r19
            r3.setTimezone2(r0)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r0 = r20
            r3.setTimezone3(r0)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            android.util.Pair r0 = new android.util.Pair     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            java.lang.Long r2 = java.lang.Long.valueOf(r22)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r0.<init>(r2, r3)     // Catch:{ Exception -> 0x024d, all -> 0x0249 }
            r2 = r17
            r2.add(r0)     // Catch:{ Exception -> 0x0247, all -> 0x0245 }
            r1.moveToNext()     // Catch:{ Exception -> 0x0247, all -> 0x0245 }
            r4 = r26
            r7 = r2
            r9 = r16
            r10 = 1
            goto L_0x00b6
        L_0x0245:
            r0 = move-exception
            goto L_0x0253
        L_0x0247:
            r0 = move-exception
            goto L_0x0258
        L_0x0249:
            r0 = move-exception
            r2 = r17
            goto L_0x0253
        L_0x024d:
            r0 = move-exception
            r2 = r17
            goto L_0x0258
        L_0x0251:
            r0 = move-exception
            r2 = r7
        L_0x0253:
            r3 = r26
            goto L_0x0278
        L_0x0256:
            r0 = move-exception
            r2 = r7
        L_0x0258:
            r3 = r26
            goto L_0x026b
        L_0x025b:
            r2 = r7
            if (r1 == 0) goto L_0x0261
            r1.close()
        L_0x0261:
            r3 = r26
            goto L_0x0273
        L_0x0264:
            r0 = move-exception
            r3 = r4
            r2 = r7
            goto L_0x0278
        L_0x0268:
            r0 = move-exception
            r3 = r4
            r2 = r7
        L_0x026b:
            r0.printStackTrace()     // Catch:{ all -> 0x0277 }
            if (r1 == 0) goto L_0x0273
            r1.close()
        L_0x0273:
            r3.onNext(r2)
            return
        L_0x0277:
            r0 = move-exception
        L_0x0278:
            if (r1 == 0) goto L_0x027d
            r1.close()
        L_0x027d:
            r3.onNext(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.util.SQLiteUtils.lambda$queryUserByRx$0(java.lang.String, long, int, io.reactivex.rxjava3.core.ObservableEmitter):void");
    }
}
