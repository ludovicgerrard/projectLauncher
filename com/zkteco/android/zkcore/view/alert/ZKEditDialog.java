package com.zkteco.android.zkcore.view.alert;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zkteco.android.zkcore.R;
import java.util.Locale;

public class ZKEditDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private boolean isShowPassWord;
    private ImageView isShowPwd;
    private Button mBtnCancel;
    private Button mBtnOk;
    private CheckBox mCk;
    private TextView mCkText;
    private EditText mEtConf;
    private EditText mEtText;
    private ResultListener mListener;
    private LinearLayout mLlCb;
    private LinearLayout mLlInput;
    private LinearLayout mLlInputConf;
    private TextView mTvConf;
    private TextView mTvContent;
    private TextView mTvHint;
    private View mVLine;

    public interface ResultListener {
        void failure();

        void success();
    }

    public ZKEditDialog(Context context2) {
        super(context2, R.style.WheelViewDialog);
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_alert_edit);
        initView();
        initListener();
    }

    private void initView() {
        this.mTvContent = (TextView) findViewById(R.id.core_ad_tv_content);
        this.mLlInput = (LinearLayout) findViewById(R.id.core_ad_ll_input);
        this.mEtText = (EditText) findViewById(R.id.core_ad_et_text);
        this.mTvHint = (TextView) findViewById(R.id.core_ad_tv_hint);
        this.mLlCb = (LinearLayout) findViewById(R.id.core_ad_ll_cb);
        this.mCk = (CheckBox) findViewById(R.id.core_ad_ck);
        this.mCkText = (TextView) findViewById(R.id.core_ad_ck_text);
        this.mBtnCancel = (Button) findViewById(R.id.core_ad_btn_cancel);
        this.mVLine = findViewById(R.id.core_ad_v_line);
        this.mBtnOk = (Button) findViewById(R.id.core_ad_btn_ok);
        this.mLlInputConf = (LinearLayout) findViewById(R.id.core_ad_ll_input_conf);
        this.mEtConf = (EditText) findViewById(R.id.core_ad_et_text__conf);
        this.mTvConf = (TextView) findViewById(R.id.core_ad_tv_hintv_conf);
        this.isShowPwd = (ImageView) findViewById(R.id.iv_showpwd);
    }

    private void initListener() {
        this.mBtnCancel.setOnClickListener(this);
        this.mBtnOk.setOnClickListener(this);
        this.isShowPwd.setOnClickListener(this);
        this.mEtText.setOnEditorActionListener(getL());
        this.mEtConf.setOnEditorActionListener(getL());
    }

    /* access modifiers changed from: protected */
    public TextView.OnEditorActionListener getL() {
        return new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return keyEvent != null && keyEvent.getKeyCode() == 66;
            }
        };
    }

    public void setBtnType(int i, String str, String str2) {
        if (i == 1) {
            this.mBtnCancel.setVisibility(8);
            this.mVLine.setVisibility(8);
            this.mBtnOk.setVisibility(0);
            this.mBtnOk.setText(str2);
        } else if (i == 2) {
            this.mBtnCancel.setVisibility(0);
            this.mVLine.setVisibility(0);
            this.mBtnOk.setVisibility(0);
            this.mBtnCancel.setText(str);
            this.mBtnOk.setText(str2);
        }
    }

    public void setContentType(int i, String str) {
        if (i == 1) {
            this.mLlInput.setVisibility(0);
            this.mLlCb.setVisibility(8);
        } else if (i == 2) {
            this.mLlInput.setVisibility(8);
            this.mLlCb.setVisibility(0);
            this.mCkText.setText(str);
        } else if (i == 3) {
            this.mLlInput.setVisibility(0);
            this.mLlInputConf.setVisibility(0);
            this.mLlCb.setVisibility(8);
        }
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
        if (view == this.mBtnCancel) {
            ResultListener resultListener = this.mListener;
            if (resultListener != null) {
                resultListener.failure();
            }
            dismiss();
        } else if (view == this.mBtnOk) {
            ResultListener resultListener2 = this.mListener;
            if (resultListener2 != null) {
                resultListener2.success();
            }
        } else {
            ImageView imageView = this.isShowPwd;
            if (view == imageView) {
                if (this.isShowPassWord) {
                    this.isShowPassWord = false;
                    imageView.setImageResource(R.mipmap.pwd_hide);
                    this.mEtText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    this.mEtConf.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    EditText editText = this.mEtText;
                    editText.setSelection(editText.getText().toString().trim().length());
                    EditText editText2 = this.mEtConf;
                    editText2.setSelection(editText2.getText().toString().trim().length());
                } else {
                    this.isShowPassWord = true;
                    imageView.setImageResource(R.mipmap.pwd_show);
                    this.mEtText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    this.mEtConf.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    EditText editText3 = this.mEtText;
                    editText3.setSelection(editText3.getText().toString().trim().length());
                    EditText editText4 = this.mEtConf;
                    editText4.setSelection(editText4.getText().toString().trim().length());
                }
                updateViewFromRTL();
            }
        }
    }

    public void showEye() {
        this.isShowPwd.setVisibility(0);
        this.isShowPassWord = false;
        this.mEtText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.mEtConf.setTransformationMethod(PasswordTransformationMethod.getInstance());
        updateViewFromRTL();
    }

    private void updateViewFromRTL() {
        boolean z = true;
        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) != 1) {
            z = false;
        }
        int i = 5;
        this.mEtText.setGravity(z ? 5 : 3);
        EditText editText = this.mEtConf;
        if (!z) {
            i = 3;
        }
        editText.setGravity(i);
    }

    public boolean getCheckBoxState() {
        return this.mCk.isChecked();
    }

    public String getInputText() {
        return this.mEtText.getText().toString();
    }

    public String getInputTextConf() {
        return this.mEtConf.getText().toString();
    }

    public void setEditText(int i, int i2, String str) {
        this.mEtText.setInputType(i);
        this.mEtText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        this.mEtText.setText(str);
        if (i2 < str.length()) {
            this.mEtText.setSelection(i2);
        } else {
            this.mEtText.setSelection(str.length());
        }
    }

    public void setEditText(int i, String str) {
        this.mEtText.setInputType(i);
        setEditText(i, (str == null || str.length() <= 0) ? 0 : str.length(), str);
    }

    public void setEtTop(int i, int i2, String str) {
        this.mEtText.setInputType(i);
        this.mEtText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        this.mEtText.setHint(str);
    }

    public void setEtConf(int i, int i2, String str) {
        this.mEtConf.setInputType(i);
        this.mEtConf.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        this.mEtConf.setHint(str);
    }

    public void setHint(String str) {
        if (str == null || str.equals("")) {
            this.mTvHint.setVisibility(4);
            return;
        }
        this.mTvHint.setVisibility(0);
        this.mTvConf.setVisibility(4);
        this.mTvHint.setText(str);
        this.mTvHint.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.anim_shake));
    }

    public void setHintConf(String str) {
        if (str == null || str.equals("")) {
            this.mTvConf.setVisibility(4);
            return;
        }
        this.mTvHint.setVisibility(4);
        this.mTvConf.setVisibility(0);
        this.mTvConf.setText(str);
        this.mTvConf.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.anim_shake));
    }

    public EditText getmEtText() {
        return this.mEtText;
    }

    public void setListener(ResultListener resultListener) {
        this.mListener = resultListener;
    }
}
