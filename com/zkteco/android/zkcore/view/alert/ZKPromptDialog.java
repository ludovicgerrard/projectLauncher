package com.zkteco.android.zkcore.view.alert;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.zkteco.android.zkcore.R;

public class ZKPromptDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private Button mBtnCancel;
    private Button mBtnOk;
    private ResultListener mListener;
    private TextView mTvContent;
    public TextView mTvTitle;
    private View mVLine;

    public interface ResultListener {
        void failure();

        void success();
    }

    public ZKPromptDialog(Context context2) {
        super(context2, R.style.WheelViewDialog);
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_alert_prompt);
        initView();
        initListener();
    }

    private void initView() {
        this.mTvTitle = (TextView) findViewById(R.id.core_ad_tv_title);
        this.mTvContent = (TextView) findViewById(R.id.core_ad_tv_content);
        this.mBtnCancel = (Button) findViewById(R.id.core_ad_btn_cancel);
        this.mVLine = findViewById(R.id.core_ad_v_line);
        this.mBtnOk = (Button) findViewById(R.id.core_ad_btn_ok);
    }

    private void initListener() {
        this.mBtnCancel.setOnClickListener(this);
        this.mBtnOk.setOnClickListener(this);
    }

    public void setType(int i) {
        if (i == 0) {
            this.mBtnCancel.setVisibility(0);
            this.mVLine.setVisibility(0);
            this.mBtnOk.setVisibility(0);
        } else if (i == 1) {
            this.mBtnOk.setBackgroundResource(R.drawable.dialog_center_1btn_select);
            this.mBtnCancel.setVisibility(8);
            this.mVLine.setVisibility(8);
            this.mBtnOk.setVisibility(0);
        }
    }

    public void setTitle(String str) {
        this.mTvTitle.setText(str);
    }

    public void setMessage(String... strArr) {
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
        this.mTvContent.setText(spannableStringBuilder);
    }

    public void onClick(View view) {
        if (view == this.mBtnOk) {
            ResultListener resultListener = this.mListener;
            if (resultListener != null) {
                resultListener.success();
            }
            dismiss();
        } else if (view == this.mBtnCancel) {
            ResultListener resultListener2 = this.mListener;
            if (resultListener2 != null) {
                resultListener2.failure();
            }
            dismiss();
        }
    }

    public void setListener(ResultListener resultListener) {
        this.mListener = resultListener;
    }
}
