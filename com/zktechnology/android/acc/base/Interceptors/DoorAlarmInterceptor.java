package com.zktechnology.android.acc.base.Interceptors;

import android.os.SystemClock;
import com.zktechnology.android.acc.DoorAlarmType;
import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.base.Interceptor;
import com.zktechnology.android.acc.base.Request;
import com.zktechnology.android.acc.base.Response;

public class DoorAlarmInterceptor extends BaseInterceptor implements Interceptor {
    private static final String TAG = "DoorAlarmInterceptor";
    private boolean isLocalAlarmStateChanged;

    public DoorAlarmInterceptor() {
        this.isLocalAlarmStateChanged = false;
    }

    public DoorAlarmInterceptor(boolean z) {
        this.isLocalAlarmStateChanged = z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0119, code lost:
        if (r10.getCancelRemoteAlarmTime() > r10.getAntiTamperAlarmTime()) goto L_0x0124;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x009b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void interceptor(com.zktechnology.android.acc.base.Request r10, com.zktechnology.android.acc.base.Response r11) {
        /*
            r9 = this;
            boolean r0 = r10.isRemoteCancelAlarm()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "interceptor: isRemoteCancelAlarm="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "DoorAlarmInterceptor"
            android.util.Log.d(r2, r1)
            if (r0 == 0) goto L_0x0023
            r9.setRemoteCancelAlarmParams(r10, r11)
            goto L_0x0137
        L_0x0023:
            boolean r0 = r10.isAccessVerified()
            boolean r1 = isDoorSensorStateConsistent(r10)
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r0)
            r5 = 0
            r3[r5] = r4
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r1)
            r6 = 1
            r3[r6] = r4
            java.lang.String r4 = "interceptor: isAccessVerified=%s, isDoorSensorStateConsistent=%s"
            java.lang.String r3 = java.lang.String.format(r4, r3)
            android.util.Log.d(r2, r3)
            if (r1 == 0) goto L_0x0059
            r11.setLocalAlarmOn(r5)
            r10.setUnsafeOpened(r5)
            if (r0 == 0) goto L_0x00ac
            r11.setCancelRemoteAlarm(r6)
            r10.setCheckLocalAlarm(r6)
            r10.setDoorSensorTimeout(r5)
            goto L_0x00ac
        L_0x0059:
            if (r0 == 0) goto L_0x0069
            r10.setDoorSensorOpened(r6)
            java.lang.String r3 = "AccDoor"
            java.lang.String r4 = "DoorAlarmInterceptor setStartLocalAlarmDelay->true"
            com.zktechnology.android.utils.LogUtils.i(r3, r4)
            r11.setStartLocalAlarmDelay(r6)
            goto L_0x00ac
        L_0x0069:
            boolean r3 = r9.isLocalAlarmStateChanged
            if (r3 == 0) goto L_0x007b
            long r3 = r10.getCancelRemoteAlarmTime()
            long r7 = r10.getUnsafeOpenAlarmTime()
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 <= 0) goto L_0x0082
            r3 = r5
            goto L_0x0083
        L_0x007b:
            long r3 = android.os.SystemClock.elapsedRealtime()
            r10.setUnsafeOpenAlarmTime(r3)
        L_0x0082:
            r3 = r6
        L_0x0083:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r7 = "interceptor: isStartUnsafeOpenAlarm="
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.StringBuilder r4 = r4.append(r3)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r2, r4)
            if (r3 == 0) goto L_0x00ac
            r11.setLocalAlarmOn(r6)
            r11.setRemoteAlarmOn(r6)
            r10.setRemoteAlarmOn(r6)
            r10.setUnsafeOpened(r6)
            com.zktechnology.android.acc.DoorAlarmType r3 = com.zktechnology.android.acc.DoorAlarmType.UNSAFE_OPEN
            r10.setDoorAlarmType(r3)
        L_0x00ac:
            boolean r3 = r10.isTampered()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r7 = "interceptor: isTampered="
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.StringBuilder r4 = r4.append(r3)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r2, r4)
            if (r3 == 0) goto L_0x00d1
            r11.setLocalAlarmOn(r6)
            r11.setRemoteAlarmOn(r6)
            r10.setRemoteAlarmOn(r6)
        L_0x00d1:
            int r3 = r10.getTamperState()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r7 = "interceptor: tamperState="
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.StringBuilder r4 = r4.append(r3)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r2, r4)
            if (r3 != 0) goto L_0x0109
            if (r0 == 0) goto L_0x0137
            r11.setLocalAlarmOn(r5)
            if (r1 == 0) goto L_0x0137
            com.zktechnology.android.acc.DoorAlarmType r0 = r10.getDoorAlarmType()
            com.zktechnology.android.acc.DoorAlarmType r1 = com.zktechnology.android.acc.DoorAlarmType.STRESS_FINGER
            if (r0 == r1) goto L_0x0137
            r11.setRemoteAlarmOn(r5)
            r11.setCancelRemoteAlarm(r6)
            r10.setTampered(r5)
            r10.setRemoteAlarmOn(r5)
            goto L_0x0137
        L_0x0109:
            if (r3 != r6) goto L_0x0137
            boolean r0 = r9.isLocalAlarmStateChanged
            if (r0 == 0) goto L_0x011c
            long r0 = r10.getCancelRemoteAlarmTime()
            long r2 = r10.getAntiTamperAlarmTime()
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x0123
            goto L_0x0124
        L_0x011c:
            long r0 = android.os.SystemClock.elapsedRealtime()
            r10.setAntiTamperAlarmTime(r0)
        L_0x0123:
            r5 = r6
        L_0x0124:
            if (r5 == 0) goto L_0x0137
            r11.setLocalAlarmOn(r6)
            r11.setRemoteAlarmOn(r6)
            r10.setTampered(r6)
            com.zktechnology.android.acc.DoorAlarmType r11 = com.zktechnology.android.acc.DoorAlarmType.ANTI_TAMPER
            r10.setDoorAlarmType(r11)
            r10.setRemoteAlarmOn(r6)
        L_0x0137:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.acc.base.Interceptors.DoorAlarmInterceptor.interceptor(com.zktechnology.android.acc.base.Request, com.zktechnology.android.acc.base.Response):void");
    }

    private void setRemoteCancelAlarmParams(Request request, Response response) {
        response.setLocalAlarmOn(false);
        response.setRemoteAlarmOn(false);
        response.setCancelRemoteAlarm(true);
        response.setDoorOpenType(DoorOpenType.NONE);
        request.setCancelRemoteAlarmTime(SystemClock.elapsedRealtime());
        request.setRemoteAlarmOn(false);
        request.setUnsafeOpened(false);
        request.setTampered(false);
        request.setDoorSensorTimeout(false);
        request.setDoorAlarmType(DoorAlarmType.NONE);
        request.setDoorSensorOpened(false);
        request.setStressFingerAlarmOn(false);
        request.setStressPasswordAlarmOn(false);
    }
}
