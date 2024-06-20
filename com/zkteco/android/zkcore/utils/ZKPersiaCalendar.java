package com.zkteco.android.zkcore.utils;

import com.guide.guidecore.GuideUsbManager;
import com.youth.banner.BannerConfig;
import java.util.Calendar;

public class ZKPersiaCalendar {
    private static double ISLAMIC_EPOCH = 1948439.5d;
    private static Calendar calendar = Calendar.getInstance();

    public static double GetMin(double d, double d2) {
        return d < d2 ? d : d2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0039 A[LOOP:0: B:7:0x0039->B:10:0x0041, LOOP_START, PHI: r3 
      PHI: (r3v3 int) = (r3v2 int), (r3v6 int) binds: [B:6:0x0034, B:10:0x0041] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Calendar GregorianToPersianCalendar(java.util.Calendar r9) {
        /*
            r0 = 1
            int r1 = r9.get(r0)
            r2 = 2
            int r3 = r9.get(r2)
            r4 = 5
            int r9 = r9.get(r4)
            r5 = 12
            int[] r5 = new int[r5]
            r5 = {20, 50, 79, 110, 141, 172, 203, 234, 265, 295, 325, 355} // fill-array
            r6 = 13
            int[] r6 = new int[r6]
            r6 = {10, 11, 12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10} // fill-array
            int r1 = r1 + 1900
            int r3 = r3 + r0
            int r7 = r1 + -1
            int r7 = r7 % 4
            r8 = 0
            if (r7 != 0) goto L_0x0029
            r7 = r0
            goto L_0x002a
        L_0x0029:
            r7 = r8
        L_0x002a:
            if (r7 == 0) goto L_0x0034
            r7 = 19
            r5[r8] = r7
            r7 = 49
            r5[r0] = r7
        L_0x0034:
            int r9 = getTotalDay(r1, r3, r9)
            r3 = r8
        L_0x0039:
            r7 = r5[r3]
            if (r9 <= r7) goto L_0x0043
            int r3 = r3 + 1
            r7 = 11
            if (r3 <= r7) goto L_0x0039
        L_0x0043:
            r6 = r6[r3]
            if (r3 <= 0) goto L_0x004d
            int r7 = r3 + -1
            r5 = r5[r7]
            int r9 = r9 - r5
            goto L_0x0052
        L_0x004d:
            r5 = r5[r8]
            int r5 = 30 - r5
            int r9 = r9 + r5
        L_0x0052:
            if (r3 > r2) goto L_0x0057
            int r1 = r1 + -622
            goto L_0x0059
        L_0x0057:
            int r1 = r1 + -621
        L_0x0059:
            int r1 = r1 + -1900
            int r6 = r6 + -1
            java.util.Calendar r3 = calendar
            r3.set(r0, r1)
            java.util.Calendar r0 = calendar
            r0.set(r2, r6)
            java.util.Calendar r0 = calendar
            r0.set(r4, r9)
            java.util.Calendar r9 = calendar
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.zkcore.utils.ZKPersiaCalendar.GregorianToPersianCalendar(java.util.Calendar):java.util.Calendar");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00d7 A[LOOP:1: B:24:0x00d7->B:27:0x00dd, LOOP_START, PHI: r0 
      PHI: (r0v4 int) = (r0v3 int), (r0v9 int) binds: [B:23:0x00d4, B:27:0x00dd] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Calendar PersianToGregorianCalendar(java.util.Calendar r19) {
        /*
            r0 = r19
            r1 = 13
            int[] r1 = new int[r1]
            r1 = {3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 1, 2, 3} // fill-array
            r2 = 12
            int[] r3 = new int[r2]
            r3 = {11, 41, 72, 102, 133, 164, 194, 225, 255, 286, 317, 345} // fill-array
            int[] r4 = new int[r2]
            r4 = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29} // fill-array
            r5 = 1
            int r6 = r0.get(r5)
            r7 = 100
            int r6 = r6 % r7
            r8 = 2
            int r9 = r0.get(r8)
            int r9 = r9 + r5
            r10 = 5
            int r11 = r0.get(r10)
            int r12 = r6 % 4
            r13 = 11
            if (r12 != 0) goto L_0x0032
            r12 = 30
            r4[r13] = r12
        L_0x0032:
            int r12 = r6 + 1
            r14 = 4
            int r12 = r12 % r14
            r15 = 346(0x15a, float:4.85E-43)
            r7 = 9
            r16 = 0
            if (r12 != 0) goto L_0x0074
            r3[r16] = r2
            r17 = 42
            r3[r5] = r17
            r17 = 73
            r3[r8] = r17
            r17 = 3
            r18 = 103(0x67, float:1.44E-43)
            r3[r17] = r18
            r17 = 134(0x86, float:1.88E-43)
            r3[r14] = r17
            r17 = 165(0xa5, float:2.31E-43)
            r3[r10] = r17
            r17 = 6
            r18 = 195(0xc3, float:2.73E-43)
            r3[r17] = r18
            r17 = 7
            r18 = 226(0xe2, float:3.17E-43)
            r3[r17] = r18
            r17 = 8
            r18 = 256(0x100, float:3.59E-43)
            r3[r17] = r18
            r17 = 287(0x11f, float:4.02E-43)
            r3[r7] = r17
            r17 = 10
            r18 = 318(0x13e, float:4.46E-43)
            r3[r17] = r18
            r3[r13] = r15
        L_0x0074:
            int r17 = r6 + 2
            int r17 = r17 % 4
            if (r17 != 0) goto L_0x007c
            r3[r13] = r15
        L_0x007c:
            if (r12 != 0) goto L_0x00a3
            if (r9 != r2) goto L_0x00a3
            r2 = r4[r13]
            int r2 = r2 + r5
            if (r11 <= r2) goto L_0x00c7
            java.util.Calendar r1 = calendar
            int r2 = r0.get(r5)
            r1.set(r5, r2)
            java.util.Calendar r1 = calendar
            int r2 = r0.get(r8)
            r1.set(r8, r2)
            java.util.Calendar r1 = calendar
            int r0 = r0.get(r10)
            r1.set(r10, r0)
            java.util.Calendar r0 = calendar
            return r0
        L_0x00a3:
            int r2 = r9 + -1
            r2 = r4[r2]
            if (r11 <= r2) goto L_0x00c7
            java.util.Calendar r1 = calendar
            int r2 = r0.get(r5)
            r1.set(r5, r2)
            java.util.Calendar r1 = calendar
            int r2 = r0.get(r8)
            r1.set(r8, r2)
            java.util.Calendar r1 = calendar
            int r0 = r0.get(r10)
            r1.set(r10, r0)
            java.util.Calendar r0 = calendar
            return r0
        L_0x00c7:
            r0 = r16
            r2 = r0
        L_0x00ca:
            int r8 = r9 + -1
            if (r0 >= r8) goto L_0x00d4
            r8 = r4[r0]
            int r2 = r2 + r8
            int r0 = r0 + 1
            goto L_0x00ca
        L_0x00d4:
            int r2 = r2 + r11
            r0 = r16
        L_0x00d7:
            r4 = r3[r0]
            if (r2 <= r4) goto L_0x00df
            int r0 = r0 + 1
            if (r0 <= r13) goto L_0x00d7
        L_0x00df:
            r1 = r1[r0]
            int r1 = r1 - r5
            if (r0 <= 0) goto L_0x00ea
            int r4 = r0 + -1
            r3 = r3[r4]
            int r2 = r2 - r3
            goto L_0x00ef
        L_0x00ea:
            r3 = r3[r16]
            int r3 = 31 - r3
            int r2 = r2 + r3
        L_0x00ef:
            if (r0 > r7) goto L_0x00f4
            int r6 = r6 + 21
            goto L_0x00f6
        L_0x00f4:
            int r6 = r6 + 22
        L_0x00f6:
            r0 = 100
            if (r6 < r0) goto L_0x00fc
            int r6 = r6 + -100
        L_0x00fc:
            int r6 = r6 + 2000
            java.util.Calendar r0 = calendar
            r0.set(r6, r1, r2)
            java.util.Calendar r0 = calendar
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.zkcore.utils.ZKPersiaCalendar.PersianToGregorianCalendar(java.util.Calendar):java.util.Calendar");
    }

    public static Calendar IslamicToGregorianCalendar(Calendar calendar2) {
        int i;
        int i2;
        int i3 = calendar2.get(1);
        int i4 = calendar2.get(2);
        int i5 = calendar2.get(5);
        int i6 = 12;
        int[] iArr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] iArr2 = {355, 354, 354, 355, 354, 354, 355, 354, 355, 354, 354, 355, 354, 354, 355, 354, 355, 354, 354, 355, 354, 354, 355, 354, 354, 355, 354, 355, 354, 354};
        int i7 = i3 - 1421;
        int i8 = i7 / 30;
        int i9 = 0;
        if (i8 != 0) {
            i = (i8 * 10631) + 0;
            for (int i10 = i7 % 30; i10 > 0; i10--) {
                i += iArr2[i10];
            }
        } else {
            int i11 = 0;
            for (int i12 = i7 % 30; i12 > 0; i12--) {
                i11 = i + iArr2[i12];
            }
        }
        int i13 = i4 - 1;
        int i14 = i + ((i13 / 2) * 59) + ((i13 % 2) * 30) + i5 + 96;
        int i15 = i14 % 1461;
        if (i15 < 367) {
            i2 = ((i14 / 1461) * 4) + BannerConfig.TIME;
        } else {
            int i16 = i15 - 366;
            i2 = ((i14 / 1461) * 4) + BannerConfig.TIME + 1 + (i16 / 365);
            i15 = i16 % 365;
        }
        if (i15 == 0 || i15 == 366) {
            if (i15 != 366) {
                i2--;
            }
            i9 = 31;
        } else {
            iArr[1] = isLeapYear(i2) ? 29 : 28;
            i6 = 0;
            while (i15 > -1) {
                if (i15 != 0) {
                    i9 = i15;
                }
                i15 -= iArr[i6];
                i6++;
            }
        }
        calendar.set((i2 - 2000) + BannerConfig.TIME, i6, i9);
        return calendar;
    }

    public static Calendar GregorianToIslamicCalendar(Calendar calendar2) {
        int i;
        int i2;
        int[] iArr = {354, 354, 355, 354, 354, 355, 354, 355, 354, 354, 355, 354, 354, 355, 354, 355, 354, 354, 355, 354, 354, 355, 354, 354, 355, 354, 355, 354, 354, 355, 354};
        int totalDayYin = getTotalDayYin(calendar2.get(1), calendar2.get(2) + 1, calendar2.get(5)) - 97;
        int i3 = 11;
        int i4 = 1420;
        if (totalDayYin < 0) {
            int i5 = totalDayYin + 97;
            if (i5 < 8) {
                i = (i5 - 1) + 24;
                i3 = 9;
            } else if (i5 < 37) {
                i3 = 10;
                i = i5 - 7;
            } else if (i5 < 67) {
                i = i5 - 36;
            } else {
                i3 = 12;
                i = i5 - 66;
            }
        } else {
            int i6 = totalDayYin / 10631;
            if (i6 != 0) {
                i4 = 1420 + (i6 * 30);
                totalDayYin -= i6 * 10631;
            }
            int i7 = 0;
            int i8 = 0;
            while (totalDayYin > -1) {
                i7++;
                i8 = totalDayYin;
                totalDayYin -= iArr[i7];
            }
            i4 += i7;
            if (i8 != 354) {
                i3 = ((i8 % 59) / 30) + ((i8 / 59) * 2);
                i2 = i8 - (((i3 / 2) * 59) + ((i3 % 2) * 30));
            } else {
                i2 = 29;
            }
            i3++;
            i = i2 + 1;
        }
        calendar.set(1, i4);
        calendar.set(2, i3 - 1);
        calendar.set(5, i);
        return calendar;
    }

    public static int GetPersianMonthDay(int i, int i2) {
        if (i2 >= 1 && i2 <= 6) {
            return 31;
        }
        if (i2 >= 7 && i2 <= 11) {
            return 30;
        }
        if (i2 == 12) {
            return IsLeapPersian(i) ? 29 : 30;
        }
        return 0;
    }

    public static int GetIslamicMonthDay(int i, int i2) {
        JulianToIslamic(IslamicToJulian(i, i2, 30), i, i2, 30);
        if (i2 != i2) {
            return 29;
        }
        return 30;
    }

    public static boolean IsLeapPersian(int i) {
        return (((((i - (i > 0 ? 474 : 473)) % 2820) + 474) + 38) * 682) % 2816 < 682;
    }

    public static double IslamicToJulian(int i, int i2, int i3) {
        return ((((((double) i3) + Math.ceil(((double) (i2 - 1)) * 29.5d)) + ((double) ((i - 1) * 354))) + Math.floor((double) (((i * 11) + 3) / 30))) + ISLAMIC_EPOCH) - 1.0d;
    }

    public static Calendar JulianToIslamic(double d, int i, int i2, int i3) {
        double floor = Math.floor(d) + 0.5d;
        int floor2 = (int) Math.floor((((floor - ISLAMIC_EPOCH) * 30.0d) + 10646.0d) / 10631.0d);
        int GetMin = (int) GetMin(12.0d, Math.ceil((floor - (IslamicToJulian(floor2, 1, 1) + 29.0d)) / 29.5d) + 1.0d);
        calendar.set(1, floor2);
        calendar.set(2, GetMin);
        calendar.set(5, ((int) (floor - IslamicToJulian(floor2, GetMin, 1))) + 1);
        return calendar;
    }

    private static int getTotalDayYin(int i, int i2, int i3) {
        int i4;
        int[] iArr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        iArr[1] = isLeapYear(i) ? 29 : 28;
        int i5 = 0;
        for (int i6 = 0; i6 < i2 - 1; i6++) {
            i5 += iArr[i6];
        }
        if (i % 4 != 0) {
            int i7 = i - 2000;
            i4 = (i7 * 365) + (i7 / 4) + 1;
        } else {
            int i8 = i - 2000;
            i4 = (i8 * 365) + (i8 / 4);
        }
        return i4 + i5 + i3;
    }

    private static int getTotalDay(int i, int i2, int i3) {
        int[] iArr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        iArr[1] = isLeapYear(i) ? 29 : 28;
        int i4 = 0;
        for (int i5 = 0; i5 < i2 - 1; i5++) {
            i4 += iArr[i5];
        }
        return i4 + i3;
    }

    private static boolean isLeapYear(int i) {
        return (i % 4 == 0 && i % 100 != 0) || i % GuideUsbManager.CONTRAST_MAX == 0;
    }
}
