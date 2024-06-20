package com.zktechnology.android.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.zktechnology.android.clock.ClockAppWidgetUtils;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.zkcore.utils.ZKSharedUtil;

public class ClockView extends FrameLayout {
    private static final String ACTION_CALENDARTYPE_CHANGE = "com.zktechnology.android.state.calendar_type_changed";
    private static final String ACTION_TIME_24_CHANGED = "com.zktechnology.android.state.time_24_changed";
    private static final String ACTION_TIME_FORMATE_CHANGED = "com.zktechnology.android.state.time_formate_changed";
    private static final String KEY_CLOCK_DATA_FORMAT = "clock_date_format";
    TextView am_pm_txt;
    View container;
    TextView date;
    TextView hour_minute_txt;
    Context mContext;
    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                boolean z = false;
                if ("android.intent.action.TIMEZONE_CHANGED".equals(action)) {
                    ClockAppWidgetUtils.getInstance(context).updateCalendar(intent.getStringExtra("time-zone"));
                } else if ("android.intent.action.TIME_SET".equals(action) || "android.intent.action.TIME_TICK".equals(action)) {
                    z = true;
                }
                try {
                    ClockAppWidgetUtils.getInstance(ClockView.this.mContext).updateWidget(z, ClockView.this.getRootView());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private final BroadcastReceiver mIntentReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                ZKSharedUtil zKSharedUtil = new ZKSharedUtil(context);
                action.hashCode();
                if (action.equals(ClockView.ACTION_TIME_24_CHANGED)) {
                    int intExtra = intent.getIntExtra("time_24", ClockAppWidgetUtils.getInstance(ClockView.this.mContext).get24Or12());
                    if (intExtra == 24) {
                        zKSharedUtil.putInt("time_24", 24);
                    } else if (intExtra == 12) {
                        zKSharedUtil.putInt("time_24", 12);
                    }
                } else if (action.equals(ClockView.ACTION_TIME_FORMATE_CHANGED)) {
                    String str = context.getResources().getStringArray(R.array.date_format)[intent.getIntExtra("checkedId", 0)];
                    if (!TextUtils.isEmpty(str)) {
                        zKSharedUtil.putString(ClockView.KEY_CLOCK_DATA_FORMAT, str);
                    }
                }
                try {
                    ClockAppWidgetUtils.getInstance(ClockView.this.mContext).updateWidget(false, ClockView.this.getRootView());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    TextView week;

    public ClockView(Context context) {
        super(context);
        initView(context);
    }

    public ClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        inflate(context, R.layout.clock_layout, this);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.hour_minute_txt = (TextView) findViewById(R.id.hour_minute_txt);
        this.container = findViewById(R.id.container);
        this.am_pm_txt = (TextView) findViewById(R.id.am_pm_txt);
        this.date = (TextView) findViewById(R.id.date);
        this.week = (TextView) findViewById(R.id.week);
        try {
            ClockAppWidgetUtils.getInstance(this.mContext).updateWidget(false, getRootView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.DATE_CHANGED");
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        intentFilter.addAction(ACTION_CALENDARTYPE_CHANGE);
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.setPriority(100);
        this.mContext.registerReceiver(this.mIntentReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(ACTION_TIME_FORMATE_CHANGED);
        intentFilter2.addAction(ACTION_TIME_24_CHANGED);
        this.mContext.registerReceiver(this.mIntentReceiver1, intentFilter2);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mContext.unregisterReceiver(this.mIntentReceiver);
        this.mContext.unregisterReceiver(this.mIntentReceiver1);
    }
}
