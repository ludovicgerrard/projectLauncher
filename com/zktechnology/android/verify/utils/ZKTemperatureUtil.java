package com.zktechnology.android.verify.utils;

import android.content.Context;
import android.util.Log;
import com.zktechnology.android.launcher2.ZKGuideCoreLauncher;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zkteco.android.core.sdk.TemperatureManager;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ZKTemperatureUtil {
    /* access modifiers changed from: private */
    public static final String TAG = "ZKTemperatureUtil";
    private static double first = 0.0d;
    /* access modifiers changed from: private */
    public static boolean inrange = false;
    /* access modifiers changed from: private */
    public static boolean isRunning = false;
    private static ZKTemperatureUtil mInstance = null;
    private static double second = 0.0d;
    /* access modifiers changed from: private */
    public static double temperature = -1.0d;
    private static TemperatureManager temperatureManager;
    private static double third;
    private Context context;
    private Future future;
    private GetTemperatureTask getTemperatureTask = new GetTemperatureTask();
    private ExecutorService mSingleService = Executors.newSingleThreadExecutor();

    public static ZKTemperatureUtil getInstance(Context context2) {
        if (mInstance == null) {
            synchronized (ZKTemperatureUtil.class) {
                mInstance = new ZKTemperatureUtil(context2);
            }
        }
        return mInstance;
    }

    public ZKTemperatureUtil(Context context2) {
        this.context = context2;
        temperatureManager = new TemperatureManager(context2);
    }

    public double celsiustoFahrenheit(double d) {
        BigDecimal bigDecimal = new BigDecimal("1.8");
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(d));
        return bigDecimal.multiply(bigDecimal2).add(new BigDecimal("32")).doubleValue();
    }

    public void needShowTemperature() {
        if (!ZKLauncher.sIRTempDetectionFunOn || !ZKLauncher.sEnalbeIRTempDetection) {
            isRunning = false;
            Future future2 = this.future;
            if (future2 != null && !future2.isDone()) {
                this.future.cancel(true);
            }
        } else if (!isRunning) {
            temperature = -1.0d;
            this.future = this.mSingleService.submit(this.getTemperatureTask);
        }
    }

    private static class GetTemperatureTask implements Runnable {
        private GetTemperatureTask() {
        }

        public void run() {
            boolean unused = ZKTemperatureUtil.isRunning = true;
            while (ZKTemperatureUtil.isRunning) {
                Log.d(ZKTemperatureUtil.TAG, "inrange: " + ZKTemperatureUtil.inrange);
                if (ZKTemperatureUtil.inrange) {
                    double unused2 = ZKTemperatureUtil.temperature = ZKTemperatureUtil.getTemperatures();
                    if (ZKTemperatureUtil.temperature != -1.0d) {
                        boolean unused3 = ZKTemperatureUtil.isRunning = false;
                    }
                }
            }
        }
    }

    public void isFaceInRange(boolean z) {
        setInrange(z);
    }

    private static void setInrange(boolean z) {
        inrange = z;
    }

    public static double getTemperatures() {
        first = 0.0d;
        second = 0.0d;
        third = 0.0d;
        double d = -1.0d;
        try {
            boolean z = true;
            if (ZKGuideCoreLauncher.deviceType == 1) {
                while (z) {
                    Thread.sleep(200);
                    if ((first != 0.0d && second != 0.0d && third != 0.0d) || !isRunning) {
                        z = false;
                    }
                }
            } else {
                first = bytesdouble(temperatureManager.getMaxTemperature())[0];
                String str = TAG;
                Log.d(str, "first temp: " + first);
                if (first == 0.0d) {
                    return -1.0d;
                }
                Thread.sleep(500);
                second = bytesdouble(temperatureManager.getMaxTemperature())[0];
                Log.d(str, "second temp: " + second);
                double d2 = second;
                if (d2 != 0.0d) {
                    if (Math.abs(first - d2) <= 1.0d) {
                        Thread.sleep(500);
                        third = bytesdouble(temperatureManager.getMaxTemperature())[0];
                        Log.d(str, "third temp: " + third);
                        double d3 = third;
                        if (d3 != 0.0d && Math.abs(first - d3) <= 1.0d) {
                            if (Math.abs(second - third) > 1.0d) {
                            }
                        }
                    }
                }
                return -1.0d;
            }
            double d4 = first;
            double d5 = second;
            if (d4 < d5) {
                d4 = d5;
            }
            try {
                double d6 = third;
                if (d4 > d6) {
                    d4 = d6;
                }
                if (d4 == 0.0d) {
                    return -1.0d;
                }
                return d4;
            } catch (Exception e) {
                e = e;
                d = d4;
                e.printStackTrace();
                return d;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return d;
        }
    }

    private static double[] bytesdouble(byte[] bArr) {
        double[] dArr = new double[(bArr.length / 2)];
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            dArr[i2] = ((double) ((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8))) / 100.0d;
            i += 2;
            i2++;
        }
        return dArr;
    }

    public static void setRunning(boolean z) {
        isRunning = z;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public double getTemperature() {
        return temperature;
    }

    public static void setTemperature(double d) {
        temperature = d;
        if (d == -1.0d) {
            setThTemper("0");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00ad, code lost:
        if (java.lang.Math.abs(second - third) <= 1.0d) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x002d, code lost:
        if (first == 0.0d) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0068, code lost:
        if (java.lang.Math.abs(first - r6) <= 1.0d) goto L_0x00b0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setThTemper(java.lang.String r10) {
        /*
            double r0 = first
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 1
            if (r0 != 0) goto L_0x0031
            double r4 = java.lang.Double.parseDouble(r10)
            first = r4
            java.lang.String r10 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r4 = "first temp: "
            java.lang.StringBuilder r0 = r0.append(r4)
            double r4 = first
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r10, r0)
            double r4 = first
            int r10 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r10 != 0) goto L_0x00b0
            goto L_0x00b1
        L_0x0031:
            double r4 = second
            int r0 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            r4 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            if (r0 != 0) goto L_0x006b
            double r6 = java.lang.Double.parseDouble(r10)
            second = r6
            java.lang.String r10 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r6 = "second temp: "
            java.lang.StringBuilder r0 = r0.append(r6)
            double r6 = second
            java.lang.StringBuilder r0 = r0.append(r6)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r10, r0)
            double r6 = second
            int r10 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r10 == 0) goto L_0x00b1
            double r8 = first
            double r8 = r8 - r6
            double r6 = java.lang.Math.abs(r8)
            int r10 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r10 <= 0) goto L_0x00b0
            goto L_0x00b1
        L_0x006b:
            double r6 = third
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 != 0) goto L_0x00b0
            double r6 = java.lang.Double.parseDouble(r10)
            third = r6
            java.lang.String r10 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r6 = "third temp: "
            java.lang.StringBuilder r0 = r0.append(r6)
            double r6 = third
            java.lang.StringBuilder r0 = r0.append(r6)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r10, r0)
            double r6 = third
            int r10 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r10 == 0) goto L_0x00b1
            double r8 = first
            double r8 = r8 - r6
            double r6 = java.lang.Math.abs(r8)
            int r10 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r10 > 0) goto L_0x00b1
            double r6 = second
            double r8 = third
            double r6 = r6 - r8
            double r6 = java.lang.Math.abs(r6)
            int r10 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r10 <= 0) goto L_0x00b0
            goto L_0x00b1
        L_0x00b0:
            r1 = 0
        L_0x00b1:
            if (r1 == 0) goto L_0x00bd
            first = r2
            second = r2
            third = r2
            r0 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            temperature = r0
        L_0x00bd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.utils.ZKTemperatureUtil.setThTemper(java.lang.String):void");
    }
}
