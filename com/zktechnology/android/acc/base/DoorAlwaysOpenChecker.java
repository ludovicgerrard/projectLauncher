package com.zktechnology.android.acc.base;

import android.content.Context;
import com.zktechnology.android.acc.DoorAccessManager;
import com.zktechnology.android.event.EventBusHelper;
import com.zktechnology.android.event.EventState;
import com.zktechnology.android.utils.DBManager;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DoorAlwaysOpenChecker {
    private static final int DELAY = 5000;
    private static final int INITIAL_DELAY = 0;
    private static String TAG = "DoorAlwaysOpenChecker";
    private static AlwaysOnOperation alwaysOnOperation = new AlwaysOnOperation();
    /* access modifiers changed from: private */
    public static DoorAccessManager doorAccessManager = null;
    /* access modifiers changed from: private */
    public static boolean isDoorAlwaysOpen = false;
    /* access modifiers changed from: private */
    public static boolean isfirstOpen = false;
    Context mContext;
    private ScheduledExecutorService scheduler = null;

    public DoorAlwaysOpenChecker(DoorAccessManager doorAccessManager2, Context context) {
        setParam(doorAccessManager2);
        this.mContext = context;
    }

    private static void setParam(DoorAccessManager doorAccessManager2) {
        isDoorAlwaysOpen = false;
        doorAccessManager = doorAccessManager2;
    }

    private static class AlwaysOnOperation implements Runnable {
        private AlwaysOnOperation() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:37:0x008e A[Catch:{ Exception -> 0x009b }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r7 = this;
                boolean r0 = com.zktechnology.android.acc.DoorAccessDao.checkDoor1CancelKeepOpenDay()     // Catch:{ Exception -> 0x009b }
                java.lang.String r1 = "forbidden"
                r2 = 0
                if (r0 != 0) goto L_0x0020
                boolean r0 = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.isDoorAlwaysOpen     // Catch:{ Exception -> 0x009b }
                if (r0 == 0) goto L_0x0019
                boolean unused = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.isDoorAlwaysOpen = r2     // Catch:{ Exception -> 0x009b }
                com.zktechnology.android.acc.DoorAccessManager r0 = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.doorAccessManager     // Catch:{ Exception -> 0x009b }
                r0.openDoorAlways(r2)     // Catch:{ Exception -> 0x009b }
            L_0x0019:
                boolean unused = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.isfirstOpen = r2     // Catch:{ Exception -> 0x009b }
                com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.sendOpenAlwaysState(r1)     // Catch:{ Exception -> 0x009b }
                return
            L_0x0020:
                com.zktechnology.android.acc.DoorAccessManager r0 = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.doorAccessManager     // Catch:{ Exception -> 0x009b }
                com.zktechnology.android.acc.DoorOpenType r0 = r0.getDoorOpenType()     // Catch:{ Exception -> 0x009b }
                int r3 = com.zktechnology.android.acc.DoorAccessDao.getDoorAlwaysOpenTimeZone()     // Catch:{ Exception -> 0x009b }
                java.lang.String r4 = "openInvalid"
                java.lang.String r5 = "open"
                r6 = 1
                if (r3 <= 0) goto L_0x003d
                boolean r1 = com.zktechnology.android.acc.DoorAccessDao.isInAccTimeZoneExtension(r3, r6)     // Catch:{ Exception -> 0x009b }
                if (r1 == 0) goto L_0x003b
                r3 = r5
                goto L_0x003f
            L_0x003b:
                r3 = r4
                goto L_0x003f
            L_0x003d:
                r3 = r1
                r1 = r2
            L_0x003f:
                if (r1 == 0) goto L_0x0052
                boolean unused = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.isDoorAlwaysOpen = r6     // Catch:{ Exception -> 0x009b }
                boolean r1 = com.zktechnology.android.acc.DoorAccessDao.isDoorAlwaysOpen(r0)     // Catch:{ Exception -> 0x009b }
                if (r1 != 0) goto L_0x0089
                com.zktechnology.android.acc.DoorAccessManager r1 = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.doorAccessManager     // Catch:{ Exception -> 0x009b }
                r1.openDoorAlways(r6)     // Catch:{ Exception -> 0x009b }
                goto L_0x0073
            L_0x0052:
                com.zktechnology.android.acc.DoorAccessManager r1 = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.doorAccessManager     // Catch:{ Exception -> 0x009b }
                boolean r1 = r1.isAccFirstOpen()     // Catch:{ Exception -> 0x009b }
                if (r1 == 0) goto L_0x0075
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.FIRST_OPEN_ALWAYS     // Catch:{ Exception -> 0x009b }
                if (r0 == r1) goto L_0x0066
                boolean r1 = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.isfirstOpen     // Catch:{ Exception -> 0x009b }
                if (r1 == 0) goto L_0x0089
            L_0x0066:
                boolean unused = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.isfirstOpen = r6     // Catch:{ Exception -> 0x009b }
                boolean unused = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.isDoorAlwaysOpen = r6     // Catch:{ Exception -> 0x009b }
                com.zktechnology.android.acc.DoorAccessManager r1 = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.doorAccessManager     // Catch:{ Exception -> 0x009b }
                r1.firstOpenDoorAlways()     // Catch:{ Exception -> 0x009b }
            L_0x0073:
                r4 = r5
                goto L_0x008a
            L_0x0075:
                boolean r1 = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.isDoorAlwaysOpen     // Catch:{ Exception -> 0x009b }
                if (r1 == 0) goto L_0x0089
                boolean unused = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.isDoorAlwaysOpen = r2     // Catch:{ Exception -> 0x009b }
                boolean unused = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.isfirstOpen = r2     // Catch:{ Exception -> 0x009b }
                com.zktechnology.android.acc.DoorAccessManager r1 = com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.doorAccessManager     // Catch:{ Exception -> 0x009b }
                r1.openDoorAlways(r2)     // Catch:{ Exception -> 0x009b }
                goto L_0x008a
            L_0x0089:
                r4 = r3
            L_0x008a:
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.REMOTE_OPEN_ALWAYS     // Catch:{ Exception -> 0x009b }
                if (r0 == r1) goto L_0x0097
                com.zktechnology.android.acc.DoorOpenType r1 = com.zktechnology.android.acc.DoorOpenType.AUX_IN_OPEN_ALWAYS     // Catch:{ Exception -> 0x009b }
                if (r0 != r1) goto L_0x0093
                goto L_0x0097
            L_0x0093:
                com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.sendOpenAlwaysState(r4)     // Catch:{ Exception -> 0x009b }
                goto L_0x009f
            L_0x0097:
                com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.sendOpenAlwaysState(r5)     // Catch:{ Exception -> 0x009b }
                goto L_0x009f
            L_0x009b:
                r0 = move-exception
                r0.printStackTrace()
            L_0x009f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.acc.base.DoorAlwaysOpenChecker.AlwaysOnOperation.run():void");
        }
    }

    /* access modifiers changed from: private */
    public static void sendOpenAlwaysState(String str) {
        if (DBManager.getInstance().getIntOption("AccessRuleType", 0) == 1) {
            EventState eventState = new EventState(2);
            eventState.setOpenAlways(str);
            EventBusHelper.post(eventState);
        }
    }

    public void start() {
        if (this.scheduler == null) {
            ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);
            this.scheduler = newScheduledThreadPool;
            synchronized (newScheduledThreadPool) {
                this.scheduler.scheduleAtFixedRate(alwaysOnOperation, 0, 5000, TimeUnit.MILLISECONDS);
            }
        }
    }

    public void stop() {
        ScheduledExecutorService scheduledExecutorService = this.scheduler;
        if (scheduledExecutorService != null) {
            synchronized (scheduledExecutorService) {
                ScheduledExecutorService scheduledExecutorService2 = this.scheduler;
                if (scheduledExecutorService2 != null && !scheduledExecutorService2.isShutdown()) {
                    this.scheduler.shutdown();
                    this.scheduler = null;
                }
            }
        }
        isDoorAlwaysOpen = false;
        doorAccessManager.closeDoor();
        sendOpenAlwaysState("forbidden");
    }
}
