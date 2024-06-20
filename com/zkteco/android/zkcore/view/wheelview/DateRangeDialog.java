package com.zkteco.android.zkcore.view.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
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

public class DateRangeDialog extends Dialog implements View.OnClickListener {
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
    /* access modifiers changed from: private */
    public String currentMonth = "01";
    /* access modifiers changed from: private */
    public String currentYear = getYear();
    private String day;
    private String endDay;
    private String endMonth;
    private String endYear;
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
    private OnBtnListener onBtnListener;
    /* access modifiers changed from: private */
    public String selectDay;
    /* access modifiers changed from: private */
    public String selectMonth;
    /* access modifiers changed from: private */
    public String selectYear;
    private String staDay;
    private String staMonth;
    /* access modifiers changed from: private */
    public boolean staOrEnd = true;
    private String staYear;
    private TextView tvEnd;
    private TextView tvSta;
    /* access modifiers changed from: private */
    public int type = 0;
    private View vChangeBirth;
    private View vChangeBirthChild;
    /* access modifiers changed from: private */
    public WheelView wvDay;
    /* access modifiers changed from: private */
    public WheelView wvMonth;
    private WheelView wvYear;

    public interface OnBtnListener {
        void onCancelClick();

        void onSureClick(String str, String str2, String str3, String str4, String str5, String str6);
    }

    public DateRangeDialog(Context context2) {
        super(context2, R.style.WheelViewDialog);
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.context.getResources().getConfiguration().orientation == 2) {
            setContentView(R.layout.layout_wheel_view_date_range);
        } else {
            setContentView(R.layout.layout_wheel_view_date_range_port);
        }
        this.wvYear = (WheelView) findViewById(R.id.core_wv_birth_year);
        this.wvMonth = (WheelView) findViewById(R.id.core_wv_birth_month);
        this.wvDay = (WheelView) findViewById(R.id.core_wv_birth_day);
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
        initDays(this.currentYear, this.currentMonth, this.type);
        this.mDaydapter = new CalendarTextAdapter(this.context, this.arry_days, Integer.parseInt(this.currentDay) - 1, this.maxTextSize, this.minTextSize);
        this.wvDay.setVisibleItems(3);
        this.wvDay.setViewAdapter(this.mDaydapter);
        this.wvDay.setCurrentItem(Integer.parseInt(this.currentDay) - 1);
        setText();
        this.wvYear.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DateRangeDialog.this.mYearAdapter.getItemText(wheelView.getCurrentItem());
                String unused = DateRangeDialog.this.selectYear = str;
                DateRangeDialog dateRangeDialog = DateRangeDialog.this;
                dateRangeDialog.setTextviewSize(str, dateRangeDialog.mYearAdapter);
                String unused2 = DateRangeDialog.this.currentYear = str;
                DateRangeDialog dateRangeDialog2 = DateRangeDialog.this;
                dateRangeDialog2.setYear(dateRangeDialog2.currentYear);
                DateRangeDialog dateRangeDialog3 = DateRangeDialog.this;
                dateRangeDialog3.initMonths(dateRangeDialog3.month);
                DateRangeDialog dateRangeDialog4 = DateRangeDialog.this;
                dateRangeDialog4.calDays(dateRangeDialog4.currentYear, DateRangeDialog.this.month);
                DateRangeDialog dateRangeDialog5 = DateRangeDialog.this;
                dateRangeDialog5.initDays(dateRangeDialog5.currentYear, DateRangeDialog.this.currentMonth, DateRangeDialog.this.type);
                DateRangeDialog dateRangeDialog6 = DateRangeDialog.this;
                DateRangeDialog dateRangeDialog7 = DateRangeDialog.this;
                CalendarTextAdapter unused3 = dateRangeDialog6.mDaydapter = new CalendarTextAdapter(dateRangeDialog7.context, DateRangeDialog.this.arry_days, 0, DateRangeDialog.this.maxTextSize, DateRangeDialog.this.minTextSize);
                DateRangeDialog.this.wvDay.setVisibleItems(3);
                DateRangeDialog.this.wvDay.setViewAdapter(DateRangeDialog.this.mDaydapter);
                DateRangeDialog.this.wvDay.setCurrentItem(0);
                DateRangeDialog dateRangeDialog8 = DateRangeDialog.this;
                DateRangeDialog dateRangeDialog9 = DateRangeDialog.this;
                CalendarTextAdapter unused4 = dateRangeDialog8.mMonthAdapter = new CalendarTextAdapter(dateRangeDialog9.context, DateRangeDialog.this.arry_months, 0, DateRangeDialog.this.maxTextSize, DateRangeDialog.this.minTextSize);
                DateRangeDialog.this.wvMonth.setVisibleItems(3);
                DateRangeDialog.this.wvMonth.setViewAdapter(DateRangeDialog.this.mMonthAdapter);
                DateRangeDialog.this.wvMonth.setCurrentItem(0);
                DateRangeDialog.this.setText();
            }
        });
        this.wvYear.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DateRangeDialog dateRangeDialog = DateRangeDialog.this;
                dateRangeDialog.setTextviewSize((String) DateRangeDialog.this.mYearAdapter.getItemText(wheelView.getCurrentItem()), dateRangeDialog.mYearAdapter);
            }
        });
        this.wvMonth.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DateRangeDialog.this.mMonthAdapter.getItemText(wheelView.getCurrentItem());
                String unused = DateRangeDialog.this.selectMonth = str;
                DateRangeDialog dateRangeDialog = DateRangeDialog.this;
                dateRangeDialog.setTextviewSize(str, dateRangeDialog.mMonthAdapter);
                String unused2 = DateRangeDialog.this.currentMonth = str;
                DateRangeDialog.this.setMonth(str);
                DateRangeDialog dateRangeDialog2 = DateRangeDialog.this;
                dateRangeDialog2.calDays(dateRangeDialog2.currentYear, DateRangeDialog.this.currentMonth);
                DateRangeDialog dateRangeDialog3 = DateRangeDialog.this;
                dateRangeDialog3.initDays(dateRangeDialog3.currentYear, DateRangeDialog.this.currentMonth, DateRangeDialog.this.type);
                DateRangeDialog dateRangeDialog4 = DateRangeDialog.this;
                DateRangeDialog dateRangeDialog5 = DateRangeDialog.this;
                CalendarTextAdapter unused3 = dateRangeDialog4.mDaydapter = new CalendarTextAdapter(dateRangeDialog5.context, DateRangeDialog.this.arry_days, 0, DateRangeDialog.this.maxTextSize, DateRangeDialog.this.minTextSize);
                DateRangeDialog.this.wvDay.setVisibleItems(3);
                DateRangeDialog.this.wvDay.setViewAdapter(DateRangeDialog.this.mDaydapter);
                DateRangeDialog.this.wvDay.setCurrentItem(0);
                DateRangeDialog.this.setText();
            }
        });
        this.wvMonth.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DateRangeDialog dateRangeDialog = DateRangeDialog.this;
                dateRangeDialog.setTextviewSize((String) DateRangeDialog.this.mMonthAdapter.getItemText(wheelView.getCurrentItem()), dateRangeDialog.mMonthAdapter);
            }
        });
        this.wvDay.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) DateRangeDialog.this.mDaydapter.getItemText(wheelView.getCurrentItem());
                DateRangeDialog dateRangeDialog = DateRangeDialog.this;
                dateRangeDialog.setTextviewSize(str, dateRangeDialog.mDaydapter);
                String unused = DateRangeDialog.this.selectDay = str;
                DateRangeDialog.this.setText();
            }
        });
        this.wvDay.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                DateRangeDialog dateRangeDialog = DateRangeDialog.this;
                dateRangeDialog.setTextviewSize((String) DateRangeDialog.this.mDaydapter.getItemText(wheelView.getCurrentItem()), dateRangeDialog.mDaydapter);
            }
        });
        this.tvSta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean unused = DateRangeDialog.this.staOrEnd = true;
                DateRangeDialog.this.checkStaOrEnd();
                DateRangeDialog.this.setText();
            }
        });
        this.tvEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean unused = DateRangeDialog.this.staOrEnd = false;
                DateRangeDialog.this.checkStaOrEnd();
                DateRangeDialog.this.setText();
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkStaOrEnd() {
        if (this.staOrEnd) {
            this.tvSta.setTextColor(this.context.getResources().getColor(R.color.clr_7AC143));
            this.tvEnd.setTextColor(this.context.getResources().getColor(R.color.clr_000000));
            return;
        }
        this.tvSta.setTextColor(this.context.getResources().getColor(R.color.clr_000000));
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
        String[] stringArray = this.context.getResources().getStringArray(R.array.zk_core_rec_wv_week);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = instance.get(7) - 1;
        if (i < 0) {
            i = 0;
        }
        String str = "";
        if (this.staOrEnd) {
            this.staYear = this.selectYear;
            this.staMonth = this.selectMonth;
            this.staDay = this.selectDay;
            TextView textView = this.tvSta;
            StringBuilder append = new StringBuilder().append(simpleDateFormat.format(date));
            if (this.type == 0) {
                str = " " + stringArray[i];
            }
            textView.setText(append.append(str).toString());
            return;
        }
        this.endYear = this.selectYear;
        this.endMonth = this.selectMonth;
        this.endDay = this.selectDay;
        TextView textView2 = this.tvEnd;
        StringBuilder append2 = new StringBuilder().append(simpleDateFormat.format(date));
        if (this.type == 0) {
            str = " " + stringArray[i];
        }
        textView2.setText(append2.append(str).toString());
    }

    private String getDateFormat(Context context2) {
        String string = Settings.System.getString(context2.getContentResolver(), "date_format");
        return TextUtils.isEmpty(string) ? "yyyy-MM-dd" : string;
    }

    public void initYears() {
        int parseInt = Integer.parseInt(getYear());
        int i = this.type;
        if (i == 0) {
            while (parseInt >= 2000) {
                this.arry_years.add(parseInt + "");
                parseInt--;
            }
        } else if (i == 1) {
            while (parseInt >= 1378) {
                this.arry_years.add(parseInt + "");
                parseInt--;
            }
        } else {
            while (parseInt >= 1420) {
                this.arry_years.add(parseInt + "");
                parseInt--;
            }
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

    public void initDays(String str, String str2, int i) {
        this.arry_days.clear();
        getDaysByType(str, str2, i);
        for (int i2 = 1; i2 <= Integer.parseInt(this.day); i2++) {
            if (i2 < 10) {
                this.arry_days.add("0" + i2);
            } else {
                this.arry_days.add(i2 + "");
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
                    onBtnListener2.onSureClick(this.staYear, this.staMonth, this.staDay, this.endYear, this.endMonth, this.endDay);
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
            Date parse = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this.staYear + "-" + this.staMonth + "-" + this.staDay);
            Date parse2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this.endYear + "-" + this.endMonth + "-" + this.endDay);
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
        Calendar instance = Calendar.getInstance();
        int i = this.type;
        if (i == 1) {
            instance = ZKPersiaCalendar.GregorianToPersianCalendar(instance);
        } else if (i == 2) {
            instance = ZKPersiaCalendar.GregorianToIslamicCalendar(instance);
        }
        return new SimpleDateFormat("yyyy", Locale.US).format(instance.getTime());
    }

    public String getMonth() {
        Calendar instance = Calendar.getInstance();
        int i = this.type;
        if (i == 1) {
            instance = ZKPersiaCalendar.GregorianToPersianCalendar(instance);
        } else if (i == 2) {
            instance = ZKPersiaCalendar.GregorianToIslamicCalendar(instance);
        }
        return new SimpleDateFormat("MM", Locale.US).format(instance.getTime());
    }

    public String getDay() {
        Calendar instance = Calendar.getInstance();
        int i = this.type;
        if (i == 1) {
            instance = ZKPersiaCalendar.GregorianToPersianCalendar(instance);
        } else if (i == 2) {
            instance = ZKPersiaCalendar.GregorianToIslamicCalendar(instance);
        }
        return new SimpleDateFormat("dd", Locale.US).format(instance.getTime());
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
        calDays(str, str2);
    }

    public int setYear(String str) {
        if (str.equals(getYear())) {
            this.month = getMonth();
        } else {
            this.month = "12";
        }
        int parseInt = Integer.parseInt(getYear());
        int i = this.type;
        int i2 = 0;
        if (i == 0) {
            while (parseInt >= 2000) {
                if (parseInt == Integer.parseInt(str)) {
                    return i2;
                }
                i2++;
                parseInt--;
            }
        } else if (i == 1) {
            while (parseInt >= 1378) {
                if (parseInt == Integer.parseInt(str)) {
                    return i2;
                }
                i2++;
                parseInt--;
            }
        } else {
            while (parseInt >= 1420) {
                if (parseInt == Integer.parseInt(str)) {
                    return i2;
                }
                i2++;
                parseInt--;
            }
        }
        return i2;
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
        if (str.equals(getYear()) && str2.equals(getMonth())) {
            this.day = getDay();
        }
    }

    public void setCalendarType(int i) {
        this.type = i;
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
}
