package com.zktechnology.android.device.camera;

import com.zktechnology.android.device.camera.CameraWatchDog;

public class CameraWatchDogTask implements Runnable {
    private final Object LOCK = new Object();
    private boolean isStart;
    private CameraWatchDog.IWatchDogCallback mCallback;
    private long mCameraErrorTime = 0;
    private final int mWatchDogMaxErrorCount;
    private final String mWatchDogName;
    private final int mWatchDogTimeInterval;
    private long sLastNotifyTime;

    public CameraWatchDogTask(String str, int i, int i2) {
        this.mWatchDogName = str;
        this.mWatchDogMaxErrorCount = i <= 0 ? 3 : i;
        this.mWatchDogTimeInterval = (i2 <= 0 ? 5 : i2) * 1000;
    }

    public void addCallback(CameraWatchDog.IWatchDogCallback iWatchDogCallback) {
        this.mCallback = iWatchDogCallback;
    }

    public void feedDog() {
        synchronized (this.LOCK) {
            long j = this.mCameraErrorTime;
            if (j > 0) {
                this.mCameraErrorTime = j - 1;
            }
        }
    }

    public void pause() {
        synchronized (this.LOCK) {
            this.isStart = false;
        }
    }

    public void resume() {
        synchronized (this.LOCK) {
            this.isStart = true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0043, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r7 = this;
            java.lang.Object r0 = r7.LOCK
            monitor-enter(r0)
            boolean r1 = r7.isStart     // Catch:{ all -> 0x0044 }
            if (r1 != 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0044 }
            return
        L_0x0009:
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0044 }
            long r3 = r7.sLastNotifyTime     // Catch:{ all -> 0x0044 }
            long r3 = r1 - r3
            int r5 = r7.mWatchDogTimeInterval     // Catch:{ all -> 0x0044 }
            long r5 = (long) r5     // Catch:{ all -> 0x0044 }
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x0034
            long r3 = r7.mCameraErrorTime     // Catch:{ all -> 0x0044 }
            int r5 = r7.mWatchDogMaxErrorCount     // Catch:{ all -> 0x0044 }
            long r5 = (long) r5     // Catch:{ all -> 0x0044 }
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x0034
            com.zktechnology.android.device.camera.CameraWatchDog$IWatchDogCallback r3 = r7.mCallback     // Catch:{ all -> 0x0044 }
            if (r3 == 0) goto L_0x002a
            java.lang.String r4 = r7.mWatchDogName     // Catch:{ all -> 0x0044 }
            r3.onCameraLost(r4)     // Catch:{ all -> 0x0044 }
        L_0x002a:
            r7.sLastNotifyTime = r1     // Catch:{ all -> 0x0044 }
            java.lang.String r1 = r7.mWatchDogName     // Catch:{ all -> 0x0044 }
            java.lang.String r2 = "run: notify lost"
            android.util.Log.e(r1, r2)     // Catch:{ all -> 0x0044 }
            goto L_0x0042
        L_0x0034:
            long r1 = r7.mCameraErrorTime     // Catch:{ all -> 0x0044 }
            int r3 = r7.mWatchDogMaxErrorCount     // Catch:{ all -> 0x0044 }
            long r3 = (long) r3     // Catch:{ all -> 0x0044 }
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 > 0) goto L_0x0042
            r3 = 1
            long r1 = r1 + r3
            r7.mCameraErrorTime = r1     // Catch:{ all -> 0x0044 }
        L_0x0042:
            monitor-exit(r0)     // Catch:{ all -> 0x0044 }
            return
        L_0x0044:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0044 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.device.camera.CameraWatchDogTask.run():void");
    }

    public void release() {
        synchronized (this.LOCK) {
            pause();
            this.mCallback = null;
        }
    }
}
