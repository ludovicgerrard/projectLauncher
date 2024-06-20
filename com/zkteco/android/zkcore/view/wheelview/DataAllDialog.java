package com.zkteco.android.zkcore.view.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zktechnology.android.verify.utils.ZKConstantConfig;
import com.zkteco.android.zkcore.R;
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

public class DataAllDialog extends Dialog implements View.OnClickListener {
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
    /* access modifiers changed from: private */
    public String day;
    private String endDay;
    private String endHour;
    private String endMinute;
    private String endMonth;
    private String endYear;
    /* access modifiers changed from: private */
    public String hour;
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
    /* access modifiers changed from: private */
    public String minute;
    /* access modifiers changed from: private */
    public String month;
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
    /* access modifiers changed from: private */
    public boolean staOrEnd = true;
    private String staYear;
    private TextView tvEnd;
    private TextView tvSta;
    private View vChangeBirth;
    private View vChangeBirthChild;
    /* access modifiers changed from: private */
    public WheelView wvDay;
    /* access modifiers changed from: private */
    public WheelView wvHour;
    /* access modifiers changed from: private */
    public WheelView wvMinute;
    /* access modifiers changed from: private */
    public WheelView wvMonth;
    private WheelView wvYear;

    public interface OnBtnListener {
        void onCancelClick();

        void onSureClick(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10);
    }

    public DataAllDialog(Context context2) {
        super(context2, R.style.WheelViewDialog);
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.context.getResources().getConfiguration().orientation == 2) {
            setContentView(R.layout.layout_wheel_view_date_all);
        } else {
            setContentView(R.layout.layout_wheel_view_date_all1);
        }
        this.wvYear = (WheelView) findViewById(R.id.core_wv_birth_year);
        this.wvMonth = (WheelView) findViewById(R.id.core_wv_birth_month);
        this.wvDay = (WheelView) findViewById(R.id.core_wv_birth_day);
        this.wvHour = (WheelView) findViewById(R.id.core_wv_birth_hour);
        this.wvMinute = (WheelView) findViewById(R.id.core_wv_birth_minute);
        this.tvSta = (TextView) findViewById(R.id.core_tv_sta);
        this.tvEnd = (TextView) findViewById(R.id.core_tv_end);
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
        initDays(this.day);
        this.mDaydapter = new CalendarTextAdapter(this.context, this.arry_days, Integer.parseInt(this.currentDay) - 1, this.maxTextSize, this.minTextSize);
        this.wvDay.setVisibleItems(3);
        this.wvDay.setViewAdapter(this.mDaydapter);
        this.wvDay.setCurrentItem(Integer.parseInt(this.currentDay) - 1);
        initHours(this.hour);
        this.mHourdapter = new CalendarTextAdapter(this.context, this.arry_hours, Integer.parseInt(this.currentHour), this.maxTextSize, this.minTextSize);
        this.wvHour.setVisibleItems(3);
        this.wvHour.setViewAdapter(this.mHourdapter);
        this.wvHour.setCurrentItem(Integer.parseInt(this.currentHour));
        initMinute(this.minute);
        this.mMinutedapter = new CalendarTextAdapter(this.context, this.arry_minute, Integer.parseInt(this.currentMinute), this.maxTextSize, this.minTextSize);
        this.wvMinute.setVisibleItems(3);
        this.wvMinute.setViewAdapter(this.mMinutedapter);
        this.wvMinute.setCurrentItem(Integer.parseInt(this.currentMinute));
        setText();
        this.wvYear.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DataAllDialog.this.mYearAdapter.getItemText(wheelView.getCurrentItem());
                String unused = DataAllDialog.this.selectYear = str;
                DataAllDialog dataAllDialog = DataAllDialog.this;
                dataAllDialog.setTextviewSize(str, dataAllDialog.mYearAdapter);
                String unused2 = DataAllDialog.this.currentYear = str;
                DataAllDialog dataAllDialog2 = DataAllDialog.this;
                dataAllDialog2.setYear(dataAllDialog2.currentYear);
                DataAllDialog dataAllDialog3 = DataAllDialog.this;
                dataAllDialog3.initMonths(dataAllDialog3.month);
                DataAllDialog dataAllDialog4 = DataAllDialog.this;
                DataAllDialog dataAllDialog5 = DataAllDialog.this;
                CalendarTextAdapter unused3 = dataAllDialog4.mMonthAdapter = new CalendarTextAdapter(dataAllDialog5.context, DataAllDialog.this.arry_months, 0, DataAllDialog.this.maxTextSize, DataAllDialog.this.minTextSize);
                DataAllDialog.this.wvMonth.setVisibleItems(3);
                DataAllDialog.this.wvMonth.setViewAdapter(DataAllDialog.this.mMonthAdapter);
                DataAllDialog.this.wvMonth.setCurrentItem(0);
                DataAllDialog.this.setText();
            }
        });
        this.wvYear.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DataAllDialog dataAllDialog = DataAllDialog.this;
                dataAllDialog.setTextviewSize((String) DataAllDialog.this.mYearAdapter.getItemText(wheelView.getCurrentItem()), dataAllDialog.mYearAdapter);
            }
        });
        this.wvMonth.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DataAllDialog.this.mMonthAdapter.getItemText(wheelView.getCurrentItem());
                String unused = DataAllDialog.this.selectMonth = str;
                DataAllDialog dataAllDialog = DataAllDialog.this;
                dataAllDialog.setTextviewSize(str, dataAllDialog.mMonthAdapter);
                DataAllDialog.this.setMonth(str);
                DataAllDialog dataAllDialog2 = DataAllDialog.this;
                dataAllDialog2.initDays(dataAllDialog2.day);
                DataAllDialog dataAllDialog3 = DataAllDialog.this;
                DataAllDialog dataAllDialog4 = DataAllDialog.this;
                CalendarTextAdapter unused2 = dataAllDialog3.mDaydapter = new CalendarTextAdapter(dataAllDialog4.context, DataAllDialog.this.arry_days, 0, DataAllDialog.this.maxTextSize, DataAllDialog.this.minTextSize);
                DataAllDialog.this.wvDay.setVisibleItems(3);
                DataAllDialog.this.wvDay.setViewAdapter(DataAllDialog.this.mDaydapter);
                DataAllDialog.this.wvDay.setCurrentItem(0);
                DataAllDialog.this.setText();
            }
        });
        this.wvMonth.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DataAllDialog dataAllDialog = DataAllDialog.this;
                dataAllDialog.setTextviewSize((String) DataAllDialog.this.mMonthAdapter.getItemText(wheelView.getCurrentItem()), dataAllDialog.mMonthAdapter);
            }
        });
        this.wvDay.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DataAllDialog.this.mDaydapter.getItemText(wheelView.getCurrentItem());
                String unused = DataAllDialog.this.selectDay = str;
                DataAllDialog dataAllDialog = DataAllDialog.this;
                dataAllDialog.setTextviewSize(str, dataAllDialog.mDaydapter);
                DataAllDialog.this.setDays(str);
                DataAllDialog dataAllDialog2 = DataAllDialog.this;
                dataAllDialog2.initHours(dataAllDialog2.hour);
                DataAllDialog dataAllDialog3 = DataAllDialog.this;
                DataAllDialog dataAllDialog4 = DataAllDialog.this;
                CalendarTextAdapter unused2 = dataAllDialog3.mHourdapter = new CalendarTextAdapter(dataAllDialog4.context, DataAllDialog.this.arry_hours, 0, DataAllDialog.this.maxTextSize, DataAllDialog.this.minTextSize);
                DataAllDialog.this.wvHour.setVisibleItems(3);
                DataAllDialog.this.wvHour.setViewAdapter(DataAllDialog.this.mHourdapter);
                DataAllDialog.this.wvHour.setCurrentItem(0);
                DataAllDialog.this.setText();
            }
        });
        this.wvDay.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DataAllDialog dataAllDialog = DataAllDialog.this;
                dataAllDialog.setTextviewSize((String) DataAllDialog.this.mDaydapter.getItemText(wheelView.getCurrentItem()), dataAllDialog.mDaydapter);
            }
        });
        this.wvHour.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DataAllDialog.this.mHourdapter.getItemText(wheelView.getCurrentItem());
                DataAllDialog dataAllDialog = DataAllDialog.this;
                dataAllDialog.setTextviewSize(str, dataAllDialog.mHourdapter);
                String unused = DataAllDialog.this.selectHour = str;
                DataAllDialog.this.sethours(str);
                DataAllDialog dataAllDialog2 = DataAllDialog.this;
                dataAllDialog2.initMinute(dataAllDialog2.minute);
                DataAllDialog dataAllDialog3 = DataAllDialog.this;
                DataAllDialog dataAllDialog4 = DataAllDialog.this;
                CalendarTextAdapter unused2 = dataAllDialog3.mMinutedapter = new CalendarTextAdapter(dataAllDialog4.context, DataAllDialog.this.arry_minute, 0, DataAllDialog.this.maxTextSize, DataAllDialog.this.minTextSize);
                DataAllDialog.this.wvMinute.setVisibleItems(3);
                DataAllDialog.this.wvMinute.setViewAdapter(DataAllDialog.this.mMinutedapter);
                DataAllDialog.this.wvMinute.setCurrentItem(0);
                DataAllDialog.this.setText();
            }
        });
        this.wvHour.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DataAllDialog dataAllDialog = DataAllDialog.this;
                dataAllDialog.setTextviewSize((String) DataAllDialog.this.mHourdapter.getItemText(wheelView.getCurrentItem()), dataAllDialog.mHourdapter);
            }
        });
        this.wvMinute.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DataAllDialog.this.mMinutedapter.getItemText(wheelView.getCurrentItem());
                DataAllDialog dataAllDialog = DataAllDialog.this;
                dataAllDialog.setTextviewSize(str, dataAllDialog.mMinutedapter);
                String unused = DataAllDialog.this.selectMinute = str;
                DataAllDialog.this.setText();
            }
        });
        this.wvMinute.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DataAllDialog dataAllDialog = DataAllDialog.this;
                dataAllDialog.setTextviewSize((String) DataAllDialog.this.mMinutedapter.getItemText(wheelView.getCurrentItem()), dataAllDialog.mMinutedapter);
            }
        });
        this.tvSta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean unused = DataAllDialog.this.staOrEnd = true;
                DataAllDialog.this.checkStaOrEnd();
                DataAllDialog.this.setText();
            }
        });
        this.tvEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean unused = DataAllDialog.this.staOrEnd = false;
                DataAllDialog.this.checkStaOrEnd();
                DataAllDialog.this.setText();
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkStaOrEnd() {
        if (this.staOrEnd) {
            this.tvSta.setTextColor(this.context.getResources().getColor(R.color.clr_7AC143));
            this.tvEnd.setTextColor(this.context.getResources().getColor(R.color.clr_494b4d));
            return;
        }
        this.tvSta.setTextColor(this.context.getResources().getColor(R.color.clr_494b4d));
        this.tvEnd.setTextColor(this.context.getResources().getColor(R.color.clr_7AC143));
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
        this.context.getResources().getStringArray(R.array.zk_core_rec_wv_week);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = instance.get(7) - 1;
        if (this.staOrEnd) {
            this.staYear = this.selectYear;
            this.staMonth = this.selectMonth;
            this.staDay = this.selectDay;
            this.staHour = this.selectHour;
            this.staMinute = this.selectMinute;
            this.tvSta.setText(simpleDateFormat.format(date) + " " + this.selectHour + ":" + this.selectMinute);
            return;
        }
        this.endYear = this.selectYear;
        this.endMonth = this.selectMonth;
        this.endDay = this.selectDay;
        this.endHour = this.selectHour;
        this.endMinute = this.selectMinute;
        this.tvEnd.setText(simpleDateFormat.format(date) + " " + this.selectHour + ":" + this.selectMinute);
    }

    private String getDateFormat(Context context2) {
        String string = Settings.System.getString(context2.getContentResolver(), "date_format");
        return TextUtils.isEmpty(string) ? "yyyy-MM-dd" : string;
    }

    public void initYears() {
        for (int parseInt = Integer.parseInt(getYear()); parseInt > 1998; parseInt--) {
            this.arry_years.add(parseInt + "");
        }
    }

    public void initMonths(String str) {
        this.arry_months.clear();
        for (int i = 1; i <= Integer.parseInt(str); i++) {
            if (i < 10) {
                this.arry_months.add("0" + i);
            } else {
                this.arry_months.add(i + "");
            }
        }
    }

    public void initDays(String str) {
        this.arry_days.clear();
        for (int i = 1; i <= Integer.parseInt(str); i++) {
            if (i < 10) {
                this.arry_days.add("0" + i);
            } else {
                this.arry_days.add(i + "");
            }
        }
    }

    public void initHours(String str) {
        this.arry_hours.clear();
        for (int i = 0; i <= Integer.parseInt(str); i++) {
            if (i < 10) {
                this.arry_hours.add("0" + i);
            } else {
                this.arry_hours.add(i + "");
            }
        }
    }

    public void initMinute(String str) {
        this.arry_minute.clear();
        for (int i = 0; i <= Integer.parseInt(str); i++) {
            if (i < 10) {
                this.arry_minute.add("0" + i);
            } else {
                this.arry_minute.add(i + "");
            }
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

    public void setOnBtnListener(OnBtnListener onBtnListener2) {
        this.onBtnListener = onBtnListener2;
    }

    public void onClick(View view) {
        if (view == this.btnSure) {
            if (TextUtils.isEmpty(this.staYear) || TextUtils.isEmpty(this.staMonth) || TextUtils.isEmpty(this.staDay)) {
                ZKToast.showToast(this.context, R.string.zk_core_rec_wv_hint_start_time);
            } else if (TextUtils.isEmpty(this.endYear) || TextUtils.isEmpty(this.endMonth) || TextUtils.isEmpty(this.endDay)) {
                ZKToast.showToast(this.context, R.string.zk_core_rec_wv_hint_end_time);
            } else if (checkDate()) {
                ZKToast.showToast(this.context, R.string.zk_core_rec_wv_hint_range);
            } else {
                OnBtnListener onBtnListener2 = this.onBtnListener;
                if (onBtnListener2 != null) {
                    onBtnListener2.onSureClick(this.staYear, this.staMonth, this.staDay, this.staHour, this.staMinute, this.endYear, this.endMonth, this.endDay, this.endHour, this.endMinute);
                }
                dismiss();
            }
        } else if (view == this.btnCancel) {
            OnBtnListener onBtnListener3 = this.onBtnListener;
            if (onBtnListener3 != null) {
                onBtnListener3.onCancelClick();
            }
            dismiss();
        }
    }

    private boolean checkDate() {
        try {
            Date parse = new SimpleDateFormat(ZKConstantConfig.DATE_FORMAT_3, Locale.US).parse(this.staYear + "-" + this.staMonth + "-" + this.staDay + " " + this.staHour + ":" + this.staMinute);
            Date parse2 = new SimpleDateFormat(ZKConstantConfig.DATE_FORMAT_3, Locale.US).parse(this.endYear + "-" + this.endMonth + "-" + this.endDay + " " + this.endHour + ":" + this.endMinute);
            if (parse.after(parse2)) {
                return true;
            }
            if (parse.equals(parse2)) {
            }
            return false;
        } catch (ParseException unused) {
            return true;
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
        return new SimpleDateFormat("yyyy", Locale.US).format(Calendar.getInstance().getTime());
    }

    public String getMonth() {
        return new SimpleDateFormat("MM", Locale.US).format(Calendar.getInstance().getTime());
    }

    public String getDay() {
        return new SimpleDateFormat("dd", Locale.US).format(Calendar.getInstance().getTime());
    }

    public String getHour() {
        return new SimpleDateFormat("HH", Locale.US).format(Calendar.getInstance().getTime());
    }

    public String getMinute() {
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

    public int setYear(String str) {
        if (str.equals(getYear())) {
            this.month = getMonth();
        } else {
            this.month = "12";
        }
        int parseInt = Integer.parseInt(getYear());
        int i = 0;
        while (parseInt > 1950 && parseInt != Integer.parseInt(str)) {
            i++;
            parseInt--;
        }
        return i;
    }

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

    public void calDays(String str, String str2) {
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

    public void calhours(String str, String str2, String str3) {
        if (!str.equals(getYear()) || !str2.equals(getMonth()) || !str3.equals(getDay())) {
            this.hour = "23";
        } else {
            this.hour = getHour();
        }
    }

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

    public void calMinute(String str, String str2, String str3, String str4) {
        if (!str.equals(getYear()) || !str2.equals(getMonth()) || !str3.equals(getDay()) || !str4.equals(getHour())) {
            this.minute = "59";
        } else {
            this.minute = getMinute();
        }
    }
}
