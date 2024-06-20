package com.zkteco.android.zkcore.view.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zkteco.android.zkcore.R;
import com.zkteco.android.zkcore.view.wheelview.adapters.AbstractWheelTextAdapter;
import com.zkteco.android.zkcore.view.wheelview.views.OnWheelChangedListener;
import com.zkteco.android.zkcore.view.wheelview.views.OnWheelScrollListener;
import com.zkteco.android.zkcore.view.wheelview.views.WheelView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeDialog extends Dialog implements View.OnClickListener {
    private ArrayList<String> arry_aa = new ArrayList<>();
    private ArrayList<String> arry_hour = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<String> arry_minute = new ArrayList<>();
    private TextView btnCancel;
    private TextView btnSure;
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public int currentAa = -1;
    /* access modifiers changed from: private */
    public String currentHour = getHour();
    private String currentMinute = "00";
    private boolean is24 = true;
    private boolean issetdata = false;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mAaAdapter;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mHourAdapter;
    /* access modifiers changed from: private */
    public CalendarTextAdapter mMinuteAdapter;
    private OnCancelListener mOnCancelListener;
    /* access modifiers changed from: private */
    public int maxTextSize = 26;
    /* access modifiers changed from: private */
    public int minTextSize = 16;
    private OnTimeListener onTimeListener;
    /* access modifiers changed from: private */
    public String selectAa = "";
    /* access modifiers changed from: private */
    public String selectHour;
    /* access modifiers changed from: private */
    public String selectMinute;
    private String tempAMaa = "";
    private String tempPMaa = "";
    private TextView tvTitle;
    private View vChange;
    private View vChangeChild;
    private WheelView wvAa;
    private WheelView wvHour;
    /* access modifiers changed from: private */
    public WheelView wvMinute;

    public interface OnCancelListener {
        void cancelClick();
    }

    public interface OnTimeListener {
        void onClick(int i, String str, String str2);
    }

    public TimeDialog(Context context2) {
        super(context2, R.style.WheelViewDialog);
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.context.getResources().getConfiguration().orientation == 2) {
            setContentView(R.layout.layout_wheel_view_time);
        } else {
            setContentView(R.layout.layout_wheel_view_time_port);
        }
        this.wvAa = (WheelView) findViewById(R.id.core_wv_time_aa);
        this.wvHour = (WheelView) findViewById(R.id.core_wv_time_hour);
        this.wvMinute = (WheelView) findViewById(R.id.core_wv_time_minute);
        this.tvTitle = (TextView) findViewById(R.id.core_time_tv_title);
        this.vChange = findViewById(R.id.core_date);
        this.vChangeChild = findViewById(R.id.core_ll_date);
        this.btnSure = (TextView) findViewById(R.id.core_btn_myinfo_sure);
        this.btnCancel = (TextView) findViewById(R.id.core_btn_myinfo_cancel);
        this.vChange.setOnClickListener(this);
        this.vChangeChild.setOnClickListener(this);
        this.btnSure.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
        if (!this.issetdata) {
            initData();
        }
        if (this.currentAa != -1) {
            initAa();
            this.mAaAdapter = new CalendarTextAdapter(this.context, this.arry_aa, setAa(this.selectAa), this.maxTextSize, this.minTextSize);
            this.wvAa.setVisibleItems(2);
            this.wvAa.setViewAdapter(this.mAaAdapter);
            this.wvAa.setCurrentItem(setAa(this.selectAa));
        } else {
            this.wvAa.setVisibility(8);
        }
        initHour();
        this.mHourAdapter = new CalendarTextAdapter(this.context, this.arry_hour, setHour(this.currentHour), this.maxTextSize, this.minTextSize);
        this.wvHour.setVisibleItems(3);
        this.wvHour.setViewAdapter(this.mHourAdapter);
        this.wvHour.setCurrentItem(setHour(this.currentHour));
        initMinute();
        this.mMinuteAdapter = new CalendarTextAdapter(this.context, this.arry_minute, setMonth(this.currentMinute), this.maxTextSize, this.minTextSize);
        this.wvMinute.setVisibleItems(3);
        this.wvMinute.setViewAdapter(this.mMinuteAdapter);
        this.wvMinute.setCurrentItem(setMonth(this.currentMinute));
        setText();
        this.wvAa.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) TimeDialog.this.mAaAdapter.getItemText(wheelView.getCurrentItem());
                String unused = TimeDialog.this.selectAa = str;
                TimeDialog timeDialog = TimeDialog.this;
                timeDialog.setTextViewSize(str, timeDialog.mAaAdapter);
                TimeDialog timeDialog2 = TimeDialog.this;
                int unused2 = timeDialog2.currentAa = timeDialog2.setAa(timeDialog2.selectAa);
                TimeDialog.this.setText();
            }
        });
        this.wvAa.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                TimeDialog timeDialog = TimeDialog.this;
                timeDialog.setTextViewSize((String) TimeDialog.this.mAaAdapter.getItemText(wheelView.getCurrentItem()), timeDialog.mAaAdapter);
            }
        });
        this.wvHour.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) TimeDialog.this.mHourAdapter.getItemText(wheelView.getCurrentItem());
                String unused = TimeDialog.this.selectHour = str;
                TimeDialog timeDialog = TimeDialog.this;
                timeDialog.setTextViewSize(str, timeDialog.mHourAdapter);
                String unused2 = TimeDialog.this.currentHour = str;
                TimeDialog timeDialog2 = TimeDialog.this;
                timeDialog2.setHour(timeDialog2.currentHour);
                TimeDialog.this.initMinute();
                TimeDialog timeDialog3 = TimeDialog.this;
                TimeDialog timeDialog4 = TimeDialog.this;
                CalendarTextAdapter unused3 = timeDialog3.mMinuteAdapter = new CalendarTextAdapter(timeDialog4.context, TimeDialog.this.arry_minute, 0, TimeDialog.this.maxTextSize, TimeDialog.this.minTextSize);
                TimeDialog.this.wvMinute.setVisibleItems(3);
                TimeDialog.this.wvMinute.setViewAdapter(TimeDialog.this.mMinuteAdapter);
                TimeDialog.this.wvMinute.setCurrentItem(0);
                TimeDialog.this.setText();
            }
        });
        this.wvHour.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                TimeDialog timeDialog = TimeDialog.this;
                timeDialog.setTextViewSize((String) TimeDialog.this.mHourAdapter.getItemText(wheelView.getCurrentItem()), timeDialog.mHourAdapter);
            }
        });
        this.wvMinute.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                String str = (String) TimeDialog.this.mMinuteAdapter.getItemText(wheelView.getCurrentItem());
                String unused = TimeDialog.this.selectMinute = str;
                TimeDialog timeDialog = TimeDialog.this;
                timeDialog.setTextViewSize(str, timeDialog.mMinuteAdapter);
                TimeDialog.this.setMonth(str);
                TimeDialog.this.setText();
            }
        });
        this.wvMinute.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheelView) {
            }

            public void onScrollingFinished(WheelView wheelView) {
                TimeDialog timeDialog = TimeDialog.this;
                timeDialog.setTextViewSize((String) TimeDialog.this.mMinuteAdapter.getItemText(wheelView.getCurrentItem()), timeDialog.mMinuteAdapter);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setText() {
        this.tvTitle.setText(this.selectAa + "\t‭" + this.selectHour + " : " + this.selectMinute + "‬");
    }

    public void initAa() {
        this.arry_aa.clear();
        this.tempAMaa = new SimpleDateFormat("aa", Locale.US).format(new Date(getStartTime().longValue()));
        this.tempPMaa = new SimpleDateFormat("aa", Locale.US).format(new Date(getEndTime().longValue()));
        this.arry_aa.add(this.tempAMaa);
        this.arry_aa.add(this.tempPMaa);
    }

    private Long getStartTime() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 1);
        return Long.valueOf(instance.getTime().getTime());
    }

    private Long getEndTime() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 23);
        instance.set(12, 59);
        instance.set(13, 59);
        instance.set(14, 999);
        return Long.valueOf(instance.getTime().getTime());
    }

    public void initHour() {
        this.arry_hour.clear();
        boolean z = this.is24;
        int i = z ? 24 : 13;
        for (int i2 = !z; i2 < i; i2++) {
            if (i2 < 10) {
                this.arry_hour.add("0" + i2);
            } else {
                this.arry_hour.add(i2 + "");
            }
        }
    }

    public void initMinute() {
        this.arry_minute.clear();
        for (int i = 0; i < 60; i++) {
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

    public void onClick(View view) {
        if (view == this.btnSure) {
            if (!TextUtils.isEmpty(this.selectHour) && !TextUtils.isEmpty(this.selectMinute)) {
                OnTimeListener onTimeListener2 = this.onTimeListener;
                if (onTimeListener2 != null) {
                    onTimeListener2.onClick(this.currentAa, this.selectHour, this.selectMinute);
                }
                dismiss();
            }
        } else if (view == this.btnCancel) {
            OnCancelListener onCancelListener = this.mOnCancelListener;
            if (onCancelListener != null) {
                onCancelListener.cancelClick();
            }
            dismiss();
        }
    }

    public void setOnTimeListener(OnTimeListener onTimeListener2) {
        this.onTimeListener = onTimeListener2;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;
    }

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

    private String getAa() {
        return new SimpleDateFormat("aa", Locale.US).format(Calendar.getInstance().getTime());
    }

    private String getHour() {
        return new SimpleDateFormat(this.is24 ? "HH" : "hh", Locale.US).format(Calendar.getInstance().getTime());
    }

    private String getMinute() {
        return new SimpleDateFormat("mm", Locale.US).format(Calendar.getInstance().getTime());
    }

    public void initData() {
        setTime(getHour(), getMinute(), true);
        this.currentMinute = "00";
    }

    public void setTime(String str, String str2, boolean z) {
        if (str.length() < 2) {
            str = "0" + str;
        }
        if (str2.length() < 2) {
            str2 = "0" + str2;
        }
        this.selectHour = str;
        this.selectMinute = str2;
        if (z) {
            boolean is24HourFormat = DateFormat.is24HourFormat(this.context);
            this.is24 = is24HourFormat;
            if (!is24HourFormat) {
                this.selectAa = getAa();
                this.currentAa = setAa(getAa());
            }
        }
        this.issetdata = true;
        this.currentHour = str;
        this.currentMinute = str2;
    }

    public int setAa(String str) {
        return str.equals(new SimpleDateFormat("aa", Locale.US).format(getStartTime())) ? 0 : 1;
    }

    public int setHour(String str) {
        boolean z = this.is24;
        int i = !z;
        int i2 = z ? 24 : 13;
        int i3 = 0;
        while (i < i2 && i != Integer.parseInt(str)) {
            i3++;
            i++;
        }
        return i3;
    }

    public int setMonth(String str) {
        int i = 0;
        int i2 = 0;
        while (i < 60 && i != Integer.parseInt(str)) {
            i2++;
            i++;
        }
        return i2;
    }
}
