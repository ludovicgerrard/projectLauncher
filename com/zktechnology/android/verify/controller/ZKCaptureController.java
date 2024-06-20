package com.zktechnology.android.verify.controller;

import com.zkteco.android.db.orm.tna.AccAttLog;
import java.util.concurrent.LinkedBlockingQueue;

public class ZKCaptureController {
    private static volatile LinkedBlockingQueue<AccAttLog> accAttLogs = new LinkedBlockingQueue<>(10);

    public static synchronized LinkedBlockingQueue<AccAttLog> getAccAttLogs() {
        LinkedBlockingQueue<AccAttLog> linkedBlockingQueue;
        synchronized (ZKCaptureController.class) {
            linkedBlockingQueue = accAttLogs;
        }
        return linkedBlockingQueue;
    }

    public static synchronized void setAccAttLogs(LinkedBlockingQueue<AccAttLog> linkedBlockingQueue) {
        synchronized (ZKCaptureController.class) {
            accAttLogs = linkedBlockingQueue;
        }
    }
}
