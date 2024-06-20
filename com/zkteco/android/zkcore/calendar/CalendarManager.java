package com.zkteco.android.zkcore.calendar;

import android.content.Context;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.zkcore.utils.ZKPersiaCalendar;
import java.util.Calendar;

public class CalendarManager {
    private Context context;
    private DataManager dataManager = new DataManager();

    public CalendarManager(Context context2) {
        this.context = context2;
    }

    public void setCalenarType(CalendarType calendarType) {
        this.dataManager.open(this.context);
        this.dataManager.setIntOption("CalenderType", calendarType.getValue());
    }

    public CalendarType getCalendarType() {
        this.dataManager.open(this.context);
        return CalendarType.getCalendarType(this.dataManager.getIntOption("CalenderType", CalendarType.Calendar.getValue()));
    }

    public Calendar transformG2PCalendar(Calendar calendar) {
        return transformG2PCalendar(calendar, getCalendarType());
    }

    /* renamed from: com.zkteco.android.zkcore.calendar.CalendarManager$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zkteco$android$zkcore$calendar$CalendarType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.zkteco.android.zkcore.calendar.CalendarType[] r0 = com.zkteco.android.zkcore.calendar.CalendarType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$zkteco$android$zkcore$calendar$CalendarType = r0
                com.zkteco.android.zkcore.calendar.CalendarType r1 = com.zkteco.android.zkcore.calendar.CalendarType.Calendar     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$zkteco$android$zkcore$calendar$CalendarType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zkteco.android.zkcore.calendar.CalendarType r1 = com.zkteco.android.zkcore.calendar.CalendarType.PersianCalendar     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$zkteco$android$zkcore$calendar$CalendarType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.zkteco.android.zkcore.calendar.CalendarType r1 = com.zkteco.android.zkcore.calendar.CalendarType.IslamicCalendar     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.zkcore.calendar.CalendarManager.AnonymousClass1.<clinit>():void");
        }
    }

    public Calendar transformG2PCalendar(Calendar calendar, CalendarType calendarType) {
        int i = AnonymousClass1.$SwitchMap$com$zkteco$android$zkcore$calendar$CalendarType[calendarType.ordinal()];
        if (i == 2) {
            return ZKPersiaCalendar.GregorianToPersianCalendar(calendar);
        }
        if (i != 3) {
            return calendar;
        }
        return ZKPersiaCalendar.GregorianToIslamicCalendar(calendar);
    }

    public Calendar transformP2GCalendar(Calendar calendar) {
        return transformP2GCalendar(calendar, getCalendarType());
    }

    public Calendar transformP2GCalendar(Calendar calendar, CalendarType calendarType) {
        int i = AnonymousClass1.$SwitchMap$com$zkteco$android$zkcore$calendar$CalendarType[calendarType.ordinal()];
        if (i == 2) {
            return ZKPersiaCalendar.PersianToGregorianCalendar(calendar);
        }
        if (i != 3) {
            return calendar;
        }
        return ZKPersiaCalendar.IslamicToGregorianCalendar(calendar);
    }
}
