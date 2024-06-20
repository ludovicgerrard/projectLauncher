package com.zkteco.android.zkcore.calendar;

public enum CalendarType {
    Calendar(0),
    PersianCalendar(1),
    IslamicCalendar(2);
    
    private int value;

    private CalendarType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static CalendarType getCalendarType(int i) {
        for (CalendarType calendarType : values()) {
            if (calendarType.getValue() == i) {
                return calendarType;
            }
        }
        return Calendar;
    }
}
