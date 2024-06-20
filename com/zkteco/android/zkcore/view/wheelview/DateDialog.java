package com.zkteco.android.zkcore.view.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zkteco.android.zkcore.R;
import com.zkteco.android.zkcore.utils.ZKPersiaCalendar;
import com.zkteco.android.zkcore.view.ZKToast;
import com.zkteco.android.zkcore.view.wheelview.adapters.AbstractWheelTextAdapter;
import com.zkteco.android.zkcore.view.wheelview.views.OnWheelChangedListener;
import com.zkteco.android.zkcore.view.wheelview.views.OnWheelScrollListener;
import com.zkteco.android.zkcore.view.wheelview.views.WheelView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateDialog extends Dialog implements View.OnClickListener {
    /* access modifiers changed from: private */
    public ArrayList<String> arry_days = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<String> arry_months = new ArrayList<>();
    private ArrayList<String> arry_years = new ArrayList<>();
    private TextView btnCancel;
    private TextView btnSure;
    /* access modifiers changed from: private */
    public Context context;
    private String currentDay = "01";
    private String currentMonth = "01";
    /* access modifiers changed from: private */
    public String currentYear = getYear();
    private String day;
    private int day1 = 10;
    private int day2 = 23;
    private boolean isHideYear = false;
    private boolean issetdata = false;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mDaydapter;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mMonthAdapter;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mYearAdapter;
    /* access modifiers changed from: private */
    public int maxTextSize = 26;
    /* access modifiers changed from: private */
    public int minTextSize = 16;
    /* access modifiers changed from: private */
    public String month;
    private int month1 = 10;
    private int month2 = 11;
    private OnDateListener onDateListener;
    /* access modifiers changed from: private */
    public String selectDay;
    /* access modifiers changed from: private */
    public String selectMonth;
    /* access modifiers changed from: private */
    public String selectYear;
    private boolean staOrEnd = true;
    private TextView tvTitle;
    /* access modifiers changed from: private */
    public int type = 0;
    private View vChangeBirth;
    private View vChangeBirthChild;
    /* access modifiers changed from: private */
    public WheelView wvDay;
    /* access modifiers changed from: private */
    public WheelView wvMonth;
    private WheelView wvYear;
    private int year0 = 2037;
    private int year1 = 1416;
    private int year2 = 1459;

    public interface OnDateListener {
        void onClick(String str, String str2, String str3);
    }

    public DateDialog(Context context2) {
        super(context2, R.style.WheelViewDialog);
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.context.getResources().getConfiguration().orientation == 2) {
            setContentView(R.layout.layout_wheel_view_date);
        } else {
            setContentView(R.layout.layout_wheel_view_date_port);
        }
        this.wvYear = (WheelView) findViewById(R.id.core_wv_birth_year);
        this.wvMonth = (WheelView) findViewById(R.id.core_wv_birth_month);
        this.wvDay = (WheelView) findViewById(R.id.core_wv_birth_day);
        this.tvTitle = (TextView) findViewById(R.id.core_tv_title);
        this.vChangeBirth = findViewById(R.id.core_date);
        this.vChangeBirthChild = findViewById(R.id.core_ll_date);
        this.btnSure = (TextView) findViewById(R.id.core_btn_myinfo_sure);
        this.btnCancel = (TextView) findViewById(R.id.core_btn_myinfo_cancel);
        this.vChangeBirth.setOnClickListener(this);
        this.vChangeBirthChild.setOnClickListener(this);
        this.btnSure.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
        if (!this.issetdata) {
            initData();
        }
        this.wvYear.setVisibility(this.isHideYear ? 8 : 0);
        initYears();
        this.mYearAdapter = new CalendarTextAdapter(this.context, this.arry_years, setYear(this.currentYear), this.maxTextSize, this.minTextSize);
        this.wvYear.setVisibleItems(3);
        this.wvYear.setViewAdapter(this.mYearAdapter);
        this.wvYear.setCurrentItem(setYear(this.currentYear));
        initMonths(this.month);
        this.mMonthAdapter = new CalendarTextAdapter(this.context, this.arry_months, setMonth(this.currentMonth), this.maxTextSize, this.minTextSize);
        this.wvMonth.setVisibleItems(3);
        this.wvMonth.setViewAdapter(this.mMonthAdapter);
        this.wvMonth.setCurrentItem(setMonth(this.currentMonth));
        initDays(this.currentYear, this.currentMonth, this.type);
        this.mDaydapter = new CalendarTextAdapter(this.context, this.arry_days, Integer.parseInt(this.currentDay) - 1, this.maxTextSize, this.minTextSize);
        this.wvDay.setVisibleItems(3);
        this.wvDay.setViewAdapter(this.mDaydapter);
        this.wvDay.setCurrentItem(Integer.parseInt(this.currentDay) - 1);
        setText();
        this.wvYear.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DateDialog.this.mYearAdapter.getItemText(wheelView.getCurrentItem());
                String unused = DateDialog.this.selectYear = str;
                DateDialog dateDialog = DateDialog.this;
                dateDialog.setTextviewSize(str, dateDialog.mYearAdapter);
                String unused2 = DateDialog.this.currentYear = str;
                DateDialog dateDialog2 = DateDialog.this;
                dateDialog2.setYear(dateDialog2.currentYear);
                DateDialog dateDialog3 = DateDialog.this;
                dateDialog3.initMonths(dateDialog3.month);
                DateDialog dateDialog4 = DateDialog.this;
                dateDialog4.initDays(dateDialog4.selectYear, DateDialog.this.selectMonth, DateDialog.this.type);
                DateDialog dateDialog5 = DateDialog.this;
                DateDialog dateDialog6 = DateDialog.this;
                CalendarTextAdapter unused3 = dateDialog5.mMonthAdapter = new CalendarTextAdapter(dateDialog6.context, DateDialog.this.arry_months, 0, DateDialog.this.maxTextSize, DateDialog.this.minTextSize);
                DateDialog.this.wvMonth.setVisibleItems(3);
                DateDialog.this.wvMonth.setViewAdapter(DateDialog.this.mMonthAdapter);
                DateDialog.this.wvMonth.setCurrentItem(0);
                DateDialog.this.setText();
            }
        });
        this.wvYear.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DateDialog dateDialog = DateDialog.this;
                dateDialog.setTextviewSize((String) DateDialog.this.mYearAdapter.getItemText(wheelView.getCurrentItem()), dateDialog.mYearAdapter);
            }
        });
        this.wvMonth.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DateDialog.this.mMonthAdapter.getItemText(wheelView.getCurrentItem());
                String unused = DateDialog.this.selectMonth = str;
                DateDialog dateDialog = DateDialog.this;
                dateDialog.setTextviewSize(str, dateDialog.mMonthAdapter);
                DateDialog.this.setMonth(str);
                DateDialog dateDialog2 = DateDialog.this;
                dateDialog2.initDays(dateDialog2.selectYear, DateDialog.this.selectMonth, DateDialog.this.type);
                DateDialog dateDialog3 = DateDialog.this;
                DateDialog dateDialog4 = DateDialog.this;
                CalendarTextAdapter unused2 = dateDialog3.mDaydapter = new CalendarTextAdapter(dateDialog4.context, DateDialog.this.arry_days, 0, DateDialog.this.maxTextSize, DateDialog.this.minTextSize);
                DateDialog.this.wvDay.setVisibleItems(3);
                DateDialog.this.wvDay.setViewAdapter(DateDialog.this.mDaydapter);
                DateDialog.this.wvDay.setCurrentItem(0);
                DateDialog.this.setText();
            }
        });
        this.wvMonth.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DateDialog dateDialog = DateDialog.this;
                dateDialog.setTextviewSize((String) DateDialog.this.mMonthAdapter.getItemText(wheelView.getCurrentItem()), dateDialog.mMonthAdapter);
            }
        });
        this.wvDay.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DateDialog.this.mDaydapter.getItemText(wheelView.getCurrentItem());
                DateDialog dateDialog = DateDialog.this;
                dateDialog.setTextviewSize(str, dateDialog.mDaydapter);
                String unused = DateDialog.this.selectDay = str;
                DateDialog.this.setText();
            }
        });
        this.wvDay.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DateDialog dateDialog = DateDialog.this;
                dateDialog.setTextviewSize((String) DateDialog.this.mDaydapter.getItemText(wheelView.getCurrentItem()), dateDialog.mDaydapter);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setText() {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            date = simpleDateFormat.parse(this.selectYear + "-" + this.selectMonth + "-" + this.selectDay);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        String[] stringArray = this.context.getResources().getStringArray(R.array.zk_core_rec_wv_week);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = instance.get(7) - 1;
        if (i < 0) {
            i = 0;
        }
        this.tvTitle.setText(simpleDateFormat.format(date) + (this.type != 0 ? "" : " " + stringArray[i]));
    }

    public void initYears() {
        int i = this.type;
        if (i == 0) {
            for (int i2 = this.year0; i2 >= 2000; i2--) {
                this.arry_years.add(i2 + "");
            }
        } else if (i == 1) {
            for (int i3 = this.year1; i3 >= 1378; i3--) {
                this.arry_years.add(i3 + "");
            }
        } else {
            for (int i4 = this.year2; i4 >= 1420; i4--) {
                this.arry_years.add(i4 + "");
            }
        }
    }

    public void initMonths(String str) {
        int i;
        this.arry_months.clear();
        if (this.type == 1 && Integer.parseInt(this.currentYear) == this.year1) {
            i = this.month1;
        } else {
            i = (this.type == 2 && Integer.parseInt(this.currentYear) == this.year2) ? this.month2 : 12;
        }
        for (int i2 = 1; i2 <= i; i2++) {
            if (i2 < 10) {
                this.arry_months.add("0" + i2);
            } else {
                this.arry_months.add(i2 + "");
            }
        }
    }

    public void initDays(String str, String str2, int i) {
        this.arry_days.clear();
        getDaysByType(str, str2, i);
        int parseInt = Integer.parseInt(this.day);
        if (i == 1 && Integer.parseInt(this.currentYear) == this.year1 && Integer.parseInt(this.currentMonth) == this.month1) {
            parseInt = this.day1;
        } else if (i == 2 && Integer.parseInt(this.currentYear) == this.year2 && Integer.parseInt(this.currentMonth) == this.month2) {
            parseInt = this.day2;
        }
        for (int i2 = 1; i2 <= parseInt; i2++) {
            if (i2 < 10) {
                this.arry_days.add("0" + i2);
            } else {
                this.arry_days.add(i2 + "");
            }
        }
    }

    private void getDaysByType(String str, String str2, int i) {
        if (i == 1) {
            this.day = String.valueOf(ZKPersiaCalendar.GetPersianMonthDay(Integer.parseInt(str), Integer.parseInt(str2)));
        } else if (i == 2) {
            this.day = String.valueOf(ZKPersiaCalendar.GetIslamicMonthDay(Integer.parseInt(str), Integer.parseInt(str2)));
        } else {
            calDays(str, str2);
        }
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> arrayList, int i, int i2, int i3) {
            super(context, R.layout.item_wheel_view_date, 0, i, i2, i3);
            this.list = arrayList;
            setItemTextResource(R.id.core_wv_temp_value);
        }

        public View getItem(int i, View view, ViewGroup viewGroup) {
            return super.getItem(i, view, viewGroup);
        }

        public int getItemsCount() {
            return this.list.size();
        }

        /* access modifiers changed from: protected */
        public CharSequence getItemText(int i) {
            return this.list.get(i) + "";
        }
    }

    public void setOnDateListener(OnDateListener onDateListener2) {
        this.onDateListener = onDateListener2;
    }

    public void onClick(View view) {
        if (view == this.btnSure) {
            if (TextUtils.isEmpty(this.selectYear) || TextUtils.isEmpty(this.selectMonth) || TextUtils.isEmpty(this.selectDay)) {
                ZKToast.showToast(this.context, R.string.zk_core_rec_wv_hint_start_time);
                return;
            }
            OnDateListener onDateListener2 = this.onDateListener;
            if (onDateListener2 != null) {
                onDateListener2.onClick(this.selectYear, this.selectMonth, this.selectDay);
            }
            dismiss();
        } else if (view == this.btnCancel) {
            dismiss();
        }
    }

    public void setTextviewSize(String str, CalendarTextAdapter calendarTextAdapter) {
        ArrayList<View> testViews = calendarTextAdapter.getTestViews();
        int size = testViews.size();
        for (int i = 0; i < size; i++) {
            TextView textView = (TextView) testViews.get(i);
            if (str.equals(textView.getText().toString())) {
                textView.setTextSize((float) this.maxTextSize);
            } else {
                textView.setTextSize((float) this.minTextSize);
            }
        }
    }

    public String getYear() {
        return this.selectYear;
    }

    public String getMonth() {
        return this.selectMonth;
    }

    public String getDay() {
        return this.selectDay;
    }

    public void initData() {
        setDate(getYear(), getMonth(), getDay());
        this.currentDay = "01";
        this.currentMonth = "01";
    }

    public void setDate(String str, String str2, String str3) {
        this.selectYear = str;
        this.selectMonth = str2;
        this.selectDay = str3;
        this.issetdata = true;
        this.currentYear = str;
        this.currentMonth = str2;
        this.currentDay = str3;
        if (str.equals(getYear())) {
            this.month = getMonth();
        } else {
            this.month = "12";
        }
        getDaysByType(str, str2, this.type);
    }

    public void setCalendarType(int i) {
        this.type = i;
    }

    public void isHideYear(boolean z) {
        this.isHideYear = z;
    }

    public int setYear(String str) {
        if (str.equals(getYear())) {
            this.month = getMonth();
        } else {
            this.month = "12";
        }
        int i = this.type;
        int i2 = 0;
        if (i == 0) {
            for (int i3 = this.year0; i3 >= 2000; i3--) {
                if (i3 == Integer.parseInt(str)) {
                    return i2;
                }
                i2++;
            }
        } else if (i == 1) {
            for (int i4 = this.year1; i4 >= 1378; i4--) {
                if (i4 == Integer.parseInt(str)) {
                    return i2;
                }
                i2++;
            }
        } else {
            for (int i5 = this.year2; i5 >= 1420; i5--) {
                if (i5 == Integer.parseInt(str)) {
                    return i2;
                }
                i2++;
            }
        }
        return i2;
    }

    public int setMonth(String str) {
        getDaysByType(this.currentYear, str, this.type);
        int i = 1;
        int i2 = 0;
        while (i < Integer.parseInt(this.month) && Integer.parseInt(str) != i) {
            i2++;
            i++;
        }
        return i2;
    }

    public void calDays(String str, String str2) {
        boolean z = Integer.parseInt(str) % 4 == 0 && Integer.parseInt(str) % 100 != 0;
        switch (Integer.parseInt(str2)) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                this.day = "31";
                return;
            case 2:
                if (z) {
                    this.day = "29";
                    return;
                } else {
                    this.day = "28";
                    return;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                this.day = "30";
                return;
            default:
                return;
        }
    }
}
