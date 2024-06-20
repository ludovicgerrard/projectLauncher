package com.zkteco.android.employeemgmt.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tencent.map.geolocation.util.DateUtils;
import com.zktechnology.android.launcher.R;
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

public class DataTimeDialog extends Dialog implements View.OnClickListener {
    /* access modifiers changed from: private */
    public ArrayList<String> arry_days = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<String> arry_hours = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<String> arry_minute = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<String> arry_months = new ArrayList<>();
    private ArrayList<String> arry_years = new ArrayList<>();
    private TextView btnCancel;
    private TextView btnSure;
    /* access modifiers changed from: private */
    public Context context;
    private String currentDay = "01";
    private String currentHour = "00";
    private String currentMinute = "00";
    private String currentMonth = "01";
    /* access modifiers changed from: private */
    public String currentYear = getYear();
    private String day;
    private String hour;
    private boolean issetdata = false;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mDaydapter;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mHourdapter;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mMinutedapter;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mMonthAdapter;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mYearAdapter;
    /* access modifiers changed from: private */
    public int maxTextSize = 26;
    /* access modifiers changed from: private */
    public int minTextSize = 16;
    private String minute;
    private String month;
    private OnBtnListener onBtnListener;
    /* access modifiers changed from: private */
    public String selectDay;
    /* access modifiers changed from: private */
    public String selectHour;
    /* access modifiers changed from: private */
    public String selectMinute;
    /* access modifiers changed from: private */
    public String selectMonth;
    /* access modifiers changed from: private */
    public String selectYear;
    private String staDay;
    private String staHour;
    private String staMinute;
    private String staMonth;
    private String staYear;
    private TextView tvTitle;
    private View vChangeBirth;
    /* access modifiers changed from: private */
    public WheelView wvDay;
    /* access modifiers changed from: private */
    public WheelView wvHour;
    /* access modifiers changed from: private */
    public WheelView wvMinute;
    /* access modifiers changed from: private */
    public WheelView wvMonth;
    private WheelView wvYear;
    private int yearRange = 3;

    public interface OnBtnListener {
        void onCancelClick();

        void onSureClick(String str, String str2, String str3, String str4, String str5);
    }

    public DataTimeDialog(Context context2) {
        super(context2, R.style.WheelViewDialog);
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_wheel_view_date_all);
        this.wvYear = (WheelView) findViewById(R.id.core_wv_birth_year);
        this.wvMonth = (WheelView) findViewById(R.id.core_wv_birth_month);
        this.wvDay = (WheelView) findViewById(R.id.core_wv_birth_day);
        this.wvHour = (WheelView) findViewById(R.id.core_wv_birth_hour);
        this.wvMinute = (WheelView) findViewById(R.id.core_wv_birth_minute);
        this.tvTitle = (TextView) findViewById(R.id.core_tv_title);
        this.vChangeBirth = findViewById(R.id.core_date);
        this.btnSure = (TextView) findViewById(R.id.core_btn_myinfo_sure);
        this.btnCancel = (TextView) findViewById(R.id.core_btn_myinfo_cancel);
        this.vChangeBirth.setOnClickListener(this);
        this.btnSure.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
        if (!this.issetdata) {
            initData();
        }
        initYears();
        this.mYearAdapter = new CalendarTextAdapter(this.context, this.arry_years, setYear(this.currentYear), this.maxTextSize, this.minTextSize);
        this.wvYear.setVisibleItems(3);
        this.wvYear.setViewAdapter(this.mYearAdapter);
        this.wvYear.setCurrentItem(setYear(this.currentYear));
        initMonths();
        this.mMonthAdapter = new CalendarTextAdapter(this.context, this.arry_months, setMonth(this.currentMonth), this.maxTextSize, this.minTextSize);
        this.wvMonth.setVisibleItems(3);
        this.wvMonth.setViewAdapter(this.mMonthAdapter);
        this.wvMonth.setCurrentItem(setMonth(this.currentMonth));
        initDays();
        this.mDaydapter = new CalendarTextAdapter(this.context, this.arry_days, Integer.parseInt(this.currentDay) - 1, this.maxTextSize, this.minTextSize);
        this.wvDay.setVisibleItems(3);
        this.wvDay.setViewAdapter(this.mDaydapter);
        this.wvDay.setCurrentItem(Integer.parseInt(this.currentDay) - 1);
        initHours();
        this.mHourdapter = new CalendarTextAdapter(this.context, this.arry_hours, Integer.parseInt(this.currentHour), this.maxTextSize, this.minTextSize);
        this.wvHour.setVisibleItems(3);
        this.wvHour.setViewAdapter(this.mHourdapter);
        this.wvHour.setCurrentItem(Integer.parseInt(this.currentHour));
        initMinute();
        this.mMinutedapter = new CalendarTextAdapter(this.context, this.arry_minute, Integer.parseInt(this.currentMinute), this.maxTextSize, this.minTextSize);
        this.wvMinute.setVisibleItems(3);
        this.wvMinute.setViewAdapter(this.mMinutedapter);
        this.wvMinute.setCurrentItem(Integer.parseInt(this.currentMinute));
        setText();
        this.wvYear.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DataTimeDialog.this.mYearAdapter.getItemText(wheelView.getCurrentItem());
                String unused = DataTimeDialog.this.selectYear = str;
                DataTimeDialog dataTimeDialog = DataTimeDialog.this;
                dataTimeDialog.setTextViewSize(str, dataTimeDialog.mYearAdapter);
                String unused2 = DataTimeDialog.this.currentYear = str;
                DataTimeDialog dataTimeDialog2 = DataTimeDialog.this;
                int unused3 = dataTimeDialog2.setYear(dataTimeDialog2.currentYear);
                DataTimeDialog.this.initMonths();
                DataTimeDialog dataTimeDialog3 = DataTimeDialog.this;
                DataTimeDialog dataTimeDialog4 = DataTimeDialog.this;
                CalendarTextAdapter unused4 = dataTimeDialog3.mMonthAdapter = new CalendarTextAdapter(dataTimeDialog4.context, DataTimeDialog.this.arry_months, 0, DataTimeDialog.this.maxTextSize, DataTimeDialog.this.minTextSize);
                DataTimeDialog.this.wvMonth.setVisibleItems(3);
                DataTimeDialog.this.wvMonth.setViewAdapter(DataTimeDialog.this.mMonthAdapter);
                DataTimeDialog.this.wvMonth.setCurrentItem(0);
                DataTimeDialog.this.setText();
            }
        });
        this.wvYear.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DataTimeDialog dataTimeDialog = DataTimeDialog.this;
                dataTimeDialog.setTextViewSize((String) DataTimeDialog.this.mYearAdapter.getItemText(wheelView.getCurrentItem()), dataTimeDialog.mYearAdapter);
            }
        });
        this.wvMonth.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DataTimeDialog.this.mMonthAdapter.getItemText(wheelView.getCurrentItem());
                String unused = DataTimeDialog.this.selectMonth = str;
                DataTimeDialog dataTimeDialog = DataTimeDialog.this;
                dataTimeDialog.setTextViewSize(str, dataTimeDialog.mMonthAdapter);
                int unused2 = DataTimeDialog.this.setMonth(str);
                DataTimeDialog.this.initDays();
                DataTimeDialog dataTimeDialog2 = DataTimeDialog.this;
                DataTimeDialog dataTimeDialog3 = DataTimeDialog.this;
                CalendarTextAdapter unused3 = dataTimeDialog2.mDaydapter = new CalendarTextAdapter(dataTimeDialog3.context, DataTimeDialog.this.arry_days, 0, DataTimeDialog.this.maxTextSize, DataTimeDialog.this.minTextSize);
                DataTimeDialog.this.wvDay.setVisibleItems(3);
                DataTimeDialog.this.wvDay.setViewAdapter(DataTimeDialog.this.mDaydapter);
                DataTimeDialog.this.wvDay.setCurrentItem(0);
                DataTimeDialog.this.setText();
            }
        });
        this.wvMonth.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DataTimeDialog dataTimeDialog = DataTimeDialog.this;
                dataTimeDialog.setTextViewSize((String) DataTimeDialog.this.mMonthAdapter.getItemText(wheelView.getCurrentItem()), dataTimeDialog.mMonthAdapter);
            }
        });
        this.wvDay.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DataTimeDialog.this.mDaydapter.getItemText(wheelView.getCurrentItem());
                String unused = DataTimeDialog.this.selectDay = str;
                DataTimeDialog dataTimeDialog = DataTimeDialog.this;
                dataTimeDialog.setTextViewSize(str, dataTimeDialog.mDaydapter);
                int unused2 = DataTimeDialog.this.setDays(str);
                DataTimeDialog.this.initHours();
                DataTimeDialog dataTimeDialog2 = DataTimeDialog.this;
                DataTimeDialog dataTimeDialog3 = DataTimeDialog.this;
                CalendarTextAdapter unused3 = dataTimeDialog2.mHourdapter = new CalendarTextAdapter(dataTimeDialog3.context, DataTimeDialog.this.arry_hours, 0, DataTimeDialog.this.maxTextSize, DataTimeDialog.this.minTextSize);
                DataTimeDialog.this.wvHour.setVisibleItems(3);
                DataTimeDialog.this.wvHour.setViewAdapter(DataTimeDialog.this.mHourdapter);
                DataTimeDialog.this.wvHour.setCurrentItem(0);
                DataTimeDialog.this.setText();
            }
        });
        this.wvDay.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DataTimeDialog dataTimeDialog = DataTimeDialog.this;
                dataTimeDialog.setTextViewSize((String) DataTimeDialog.this.mDaydapter.getItemText(wheelView.getCurrentItem()), dataTimeDialog.mDaydapter);
            }
        });
        this.wvHour.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DataTimeDialog.this.mHourdapter.getItemText(wheelView.getCurrentItem());
                DataTimeDialog dataTimeDialog = DataTimeDialog.this;
                dataTimeDialog.setTextViewSize(str, dataTimeDialog.mHourdapter);
                String unused = DataTimeDialog.this.selectHour = str;
                int unused2 = DataTimeDialog.this.sethours(str);
                DataTimeDialog.this.initMinute();
                DataTimeDialog dataTimeDialog2 = DataTimeDialog.this;
                DataTimeDialog dataTimeDialog3 = DataTimeDialog.this;
                CalendarTextAdapter unused3 = dataTimeDialog2.mMinutedapter = new CalendarTextAdapter(dataTimeDialog3.context, DataTimeDialog.this.arry_minute, 0, DataTimeDialog.this.maxTextSize, DataTimeDialog.this.minTextSize);
                DataTimeDialog.this.wvMinute.setVisibleItems(3);
                DataTimeDialog.this.wvMinute.setViewAdapter(DataTimeDialog.this.mMinutedapter);
                DataTimeDialog.this.wvMinute.setCurrentItem(0);
                DataTimeDialog.this.setText();
            }
        });
        this.wvHour.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DataTimeDialog dataTimeDialog = DataTimeDialog.this;
                dataTimeDialog.setTextViewSize((String) DataTimeDialog.this.mHourdapter.getItemText(wheelView.getCurrentItem()), dataTimeDialog.mHourdapter);
            }
        });
        this.wvMinute.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DataTimeDialog.this.mMinutedapter.getItemText(wheelView.getCurrentItem());
                DataTimeDialog dataTimeDialog = DataTimeDialog.this;
                dataTimeDialog.setTextViewSize(str, dataTimeDialog.mMinutedapter);
                String unused = DataTimeDialog.this.selectMinute = str;
                DataTimeDialog.this.setText();
            }
        });
        this.wvMinute.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DataTimeDialog dataTimeDialog = DataTimeDialog.this;
                dataTimeDialog.setTextViewSize((String) DataTimeDialog.this.mMinutedapter.getItemText(wheelView.getCurrentItem()), dataTimeDialog.mMinutedapter);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setText() {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this.selectYear + "-" + this.selectMonth + "-" + this.selectDay);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        this.staYear = this.selectYear;
        this.staMonth = this.selectMonth;
        this.staDay = this.selectDay;
        this.staHour = this.selectHour;
        this.staMinute = this.selectMinute;
        this.tvTitle.setText(DateFormat.format(getDateFormat(this.context), date) + " " + this.selectHour + ":" + this.selectMinute);
    }

    private String getDateFormat(Context context2) {
        String string = Settings.System.getString(context2.getContentResolver(), "date_format");
        return TextUtils.isEmpty(string) ? "yyyy-MM-dd" : string;
    }

    private void initYears() {
        for (int parseInt = Integer.parseInt(getYear()) + this.yearRange; parseInt > 1998; parseInt--) {
            this.arry_years.add(parseInt + "");
        }
    }

    /* access modifiers changed from: private */
    public void initMonths() {
        this.arry_months.clear();
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                this.arry_months.add("0" + i);
            } else {
                this.arry_months.add(i + "");
            }
        }
    }

    /* access modifiers changed from: private */
    public void initDays() {
        this.arry_days.clear();
        Calendar instance = Calendar.getInstance();
        instance.set(1, Integer.parseInt(this.selectYear));
        instance.set(2, Integer.parseInt(this.selectMonth));
        instance.set(5, 1);
        Date time = instance.getTime();
        Calendar instance2 = Calendar.getInstance();
        instance2.set(1, Integer.parseInt(this.selectYear));
        instance2.set(2, Integer.parseInt(this.selectMonth));
        instance2.set(5, 1);
        instance2.add(2, -1);
        int time2 = (int) ((time.getTime() - instance2.getTime().getTime()) / DateUtils.ONE_DAY);
        for (int i = 1; i <= time2; i++) {
            if (i < 10) {
                this.arry_days.add("0" + i);
            } else {
                this.arry_days.add(i + "");
            }
        }
    }

    /* access modifiers changed from: private */
    public void initHours() {
        this.arry_hours.clear();
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                this.arry_hours.add("0" + i);
            } else {
                this.arry_hours.add(i + "");
            }
        }
    }

    /* access modifiers changed from: private */
    public void initMinute() {
        this.arry_minute.clear();
        for (int i = 0; i <= 59; i++) {
            if (i < 10) {
                this.arry_minute.add("0" + i);
            } else {
                this.arry_minute.add(i + "");
            }
        }
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        private CalendarTextAdapter(Context context, ArrayList<String> arrayList, int i, int i2, int i3) {
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

    public void setOnBtnListener(OnBtnListener onBtnListener2) {
        this.onBtnListener = onBtnListener2;
    }

    public void onClick(View view) {
        if (view == this.btnSure) {
            if (TextUtils.isEmpty(this.staYear) || TextUtils.isEmpty(this.staMonth) || TextUtils.isEmpty(this.staDay)) {
                ZKToast.showToast(this.context, (int) R.string.zk_core_rec_wv_hint_start_time);
                return;
            }
            OnBtnListener onBtnListener2 = this.onBtnListener;
            if (onBtnListener2 != null) {
                onBtnListener2.onSureClick(this.staYear, this.staMonth, this.staDay, this.staHour, this.staMinute);
            }
            dismiss();
        } else if (view == this.btnCancel) {
            OnBtnListener onBtnListener3 = this.onBtnListener;
            if (onBtnListener3 != null) {
                onBtnListener3.onCancelClick();
            }
            dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void setTextViewSize(String str, CalendarTextAdapter calendarTextAdapter) {
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

    private String getYear() {
        return new SimpleDateFormat("yyyy", Locale.US).format(Calendar.getInstance().getTime());
    }

    private String getMonth() {
        return new SimpleDateFormat("MM", Locale.US).format(Calendar.getInstance().getTime());
    }

    private String getDay() {
        return new SimpleDateFormat("dd", Locale.US).format(Calendar.getInstance().getTime());
    }

    private String getHour() {
        return new SimpleDateFormat("HH", Locale.US).format(Calendar.getInstance().getTime());
    }

    private String getMinute() {
        return new SimpleDateFormat("mm", Locale.US).format(Calendar.getInstance().getTime());
    }

    public void initData() {
        setDate(getYear(), getMonth(), getDay(), getHour(), getMinute());
        this.currentDay = "01";
        this.currentMonth = "01";
        this.currentHour = "00";
        this.currentMinute = "00";
    }

    public void setDate(String str, String str2, String str3, String str4, String str5) {
        Calendar.getInstance();
        this.selectYear = str;
        this.selectMonth = str2;
        this.selectDay = str3;
        this.selectHour = str4;
        this.selectMinute = str5;
        this.issetdata = true;
        this.currentYear = str;
        this.currentMonth = str2;
        this.currentDay = str3;
        this.currentHour = str4;
        this.currentMinute = str5;
        if (str.equals(getYear())) {
            this.month = getMonth();
        } else {
            this.month = "12";
        }
        calDays(str, str2);
        calMinute(str, str2, str3, str4);
    }

    /* access modifiers changed from: private */
    public int setYear(String str) {
        if (str.equals(getYear())) {
            this.month = getMonth();
        } else {
            this.month = "12";
        }
        int parseInt = Integer.parseInt(getYear()) + this.yearRange;
        int parseInt2 = Integer.parseInt(str);
        int i = 0;
        while (parseInt > 1950 && parseInt != parseInt2) {
            i++;
            parseInt--;
        }
        return i;
    }

    /* access modifiers changed from: private */
    public int setMonth(String str) {
        calDays(this.currentYear, str);
        int i = 1;
        int i2 = 0;
        while (i < Integer.parseInt(this.month) && Integer.parseInt(str) != i) {
            i2++;
            i++;
        }
        return i2;
    }

    private void calDays(String str, String str2) {
        boolean z = Integer.parseInt(str) % 4 == 0 && Integer.parseInt(str) % 100 != 0;
        for (int i = 1; i <= 12; i++) {
            switch (Integer.parseInt(str2)) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    this.day = "31";
                    break;
                case 2:
                    if (!z) {
                        this.day = "28";
                        break;
                    } else {
                        this.day = "29";
                        break;
                    }
                case 4:
                case 6:
                case 9:
                case 11:
                    this.day = "30";
                    break;
            }
        }
        if (!str.equals(getYear()) || !str2.equals(getMonth())) {
            this.hour = "23";
            return;
        }
        this.day = getDay();
        this.hour = getHour();
    }

    /* access modifiers changed from: private */
    public int setDays(String str) {
        calhours(this.currentYear, this.currentMonth, str);
        int i = 0;
        int i2 = 0;
        while (i < Integer.parseInt(this.day) && Integer.parseInt(str) != i) {
            i2++;
            i++;
        }
        return i2;
    }

    private void calhours(String str, String str2, String str3) {
        if (!str.equals(getYear()) || !str2.equals(getMonth()) || !str3.equals(getDay())) {
            this.hour = "23";
        } else {
            this.hour = getHour();
        }
    }

    /* access modifiers changed from: private */
    public int sethours(String str) {
        calMinute(this.currentYear, this.currentMonth, this.currentDay, str);
        int i = 0;
        int i2 = 0;
        while (i < Integer.parseInt(this.hour) && Integer.parseInt(str) != i) {
            i2++;
            i++;
        }
        return i2;
    }

    private void calMinute(String str, String str2, String str3, String str4) {
        if (!str.equals(getYear()) || !str2.equals(getMonth()) || !str3.equals(getDay()) || !str4.equals(getHour())) {
            this.minute = "59";
        } else {
            this.minute = getMinute();
        }
    }
}
