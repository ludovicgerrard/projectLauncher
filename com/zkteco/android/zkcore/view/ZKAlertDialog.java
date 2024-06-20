package com.zkteco.android.zkcore.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zkteco.android.zkcore.R;

public class ZKAlertDialog extends Dialog implements View.OnClickListener {
    private setCancelListener cancelListener;
    private Context context;
    private ResultStateListener listener;
    private Button mCancel;
    private CheckBox mCk;
    private View mLine;
    private LinearLayout mLlCk;
    private Button mOk;
    private LinearLayout mTimeLl;
    private TextView mTvClear;
    private TextView mTvTime;
    private TextView mTvTitle;
    private setOkListener okListener;

    public interface ResultStateListener {
        void failure();

        void success();
    }

    public interface setCancelListener {
        void setCancel();
    }

    public interface setOkListener {
        void setOk();
    }

    public ZKAlertDialog(Context context2) {
        super(context2, R.style.WheelViewDialog);
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_alert_dialog);
        initView();
        initListener();
    }

    private void initView() {
        this.mTvTitle = (TextView) findViewById(R.id.core_ad_tv_title);
        this.mLlCk = (LinearLayout) findViewById(R.id.core_ad_ll_ck);
        this.mCk = (CheckBox) findViewById(R.id.core_ad_ck);
        this.mTvClear = (TextView) findViewById(R.id.core_ad_clear);
        this.mCancel = (Button) findViewById(R.id.core_ad_cancel);
        this.mLine = findViewById(R.id.core_ad_line);
        this.mOk = (Button) findViewById(R.id.core_ad_ok);
        this.mTimeLl = (LinearLayout) findViewById(R.id.core_ad_ll_time);
        this.mTvTime = (TextView) findViewById(R.id.core_ad_time_tv);
    }

    public void setText(String str, String str2) {
        this.mCancel.setText(str);
        this.mOk.setText(str2);
    }

    private void initListener() {
        this.mCancel.setOnClickListener(this);
        this.mOk.setOnClickListener(this);
    }

    public void setBtnType(int i) {
        if (i == 0) {
            this.mCancel.setVisibility(0);
            this.mLine.setVisibility(0);
            this.mOk.setVisibility(0);
        } else if (i == 1) {
            this.mOk.setBackgroundResource(R.drawable.dialog_center_1btn_select);
            this.mCancel.setVisibility(8);
            this.mLine.setVisibility(8);
            this.mOk.setVisibility(0);
        }
    }

    public void setTitleType(int i) {
        if (i == 0) {
            this.mLlCk.setVisibility(0);
        } else if (i == 1) {
            this.mLlCk.setVisibility(8);
        }
    }

    public void setTitleMessage(String... strArr) {
        int i;
        int i2;
        int length = strArr.length;
        int length2 = strArr[0].length();
        if (length >= 2) {
            i2 = strArr[1].length() + length2;
            i = length >= 3 ? strArr[2].length() + i2 : 0;
        } else {
            i2 = 0;
            i = 0;
        }
        String str = "";
        for (int i3 = 0; i3 < strArr.length; i3++) {
            str = str + strArr[i3];
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(this.context.getResources().getColor(R.color.clr_2A2A35));
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(this.context.getResources().getColor(R.color.clr_7AC143));
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, length2, 33);
        if (length >= 2) {
            spannableStringBuilder.setSpan(foregroundColorSpan2, length2, i2, 18);
            if (length >= 3) {
                spannableStringBuilder.setSpan(foregroundColorSpan, i2, i, 18);
            }
        }
        this.mTvTitle.setText(spannableStringBuilder);
    }

    public void setTvClear(String str) {
        this.mTvClear.setText(str);
    }

    public void setListener(ResultStateListener resultStateListener) {
        this.listener = resultStateListener;
    }

    public void setOkListener(setOkListener setoklistener) {
        this.okListener = setoklistener;
    }

    public void setCancelListener(setCancelListener setcancellistener) {
        this.cancelListener = setcancellistener;
    }

    public void onClick(View view) {
        if (view == this.mOk) {
            setOkListener setoklistener = this.okListener;
            if (setoklistener != null) {
                setoklistener.setOk();
            }
            ResultStateListener resultStateListener = this.listener;
            if (resultStateListener != null) {
                resultStateListener.success();
            }
            dismiss();
        } else if (view == this.mCancel) {
            setCancelListener setcancellistener = this.cancelListener;
            if (setcancellistener != null) {
                setcancellistener.setCancel();
            }
            dismiss();
        }
    }

    public boolean isCheckedSync() {
        return this.mCk.isChecked();
    }

    public void setTime(String str) {
        this.mLlCk.setVisibility(8);
        this.mTimeLl.setVisibility(0);
        this.mTvTime.setText(str);
    }
}
