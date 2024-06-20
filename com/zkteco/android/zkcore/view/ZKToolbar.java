package com.zkteco.android.zkcore.view;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.zkteco.android.zkcore.R;

public class ZKToolbar extends Toolbar {
    private ImageView mBack;
    private TextView mDate;
    private EditText mEtInput;
    private int mFResource;
    private boolean mImgState;
    private ImageView mIvIconBtn;
    private LinearLayout mLeftTitleBtn;
    private LinearLayout mLlDn;
    private LinearLayout mLlUp;
    private LinearLayout mLlUpDn;
    private ImageView mPop;
    private TextView mRighext;
    private LinearLayout mRightView;
    private LinearLayout mRightViewText;
    private RelativeLayout mRlIpnut;
    private int mTResource;
    private TextView mTitle;

    public ZKToolbar(Context context) {
        this(context, (AttributeSet) null);
    }

    public ZKToolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZKToolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        View view;
        if (getResources().getConfiguration().orientation == 2) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_toolbar, this);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_toolba_port, this);
        }
        this.mLeftTitleBtn = (LinearLayout) view.findViewById(R.id.core_tb_ll_pop);
        this.mTitle = (TextView) view.findViewById(R.id.core_tb_tv_title);
        this.mPop = (ImageView) view.findViewById(R.id.core_tb_iv_pop);
        this.mDate = (TextView) view.findViewById(R.id.core_tb_tv_time);
        this.mBack = (ImageView) view.findViewById(R.id.core_tb_iv_back);
        this.mRightView = (LinearLayout) view.findViewById(R.id.core_tb_rl_icon_input);
        this.mRlIpnut = (RelativeLayout) view.findViewById(R.id.core_tb_rl_input);
        this.mEtInput = (EditText) view.findViewById(R.id.core_tb_ii_et_input);
        this.mIvIconBtn = (ImageView) view.findViewById(R.id.core_tb_iv_ii_icon);
        this.mRightViewText = (LinearLayout) view.findViewById(R.id.core_tb_rl_text);
        this.mRighext = (TextView) view.findViewById(R.id.core_tb_text);
        this.mLlUpDn = (LinearLayout) view.findViewById(R.id.core_tb_ll_up_dn);
        this.mLlUp = (LinearLayout) view.findViewById(R.id.core_tb_ll_up);
        this.mLlDn = (LinearLayout) view.findViewById(R.id.core_tb_ll_dn);
    }

    private void setTitle(String str) {
        if (str.length() > 32) {
            str = str.substring(0, 29) + "...";
        }
        this.mTitle.setText(str);
    }

    public void setLeftView(String str) {
        this.mDate.setVisibility(8);
        this.mPop.setVisibility(8);
        setTitle(str);
        this.mLeftTitleBtn.setOnClickListener((View.OnClickListener) null);
    }

    public void setLeftView(String str, String str2) {
        this.mDate.setVisibility(0);
        this.mDate.setText(str);
        setTitle(str2);
        this.mLeftTitleBtn.setOnClickListener((View.OnClickListener) null);
    }

    public void setLeftView(String str, View.OnClickListener onClickListener) {
        this.mDate.setVisibility(8);
        this.mPop.setVisibility(0);
        setTitle(str);
        this.mLeftTitleBtn.setOnClickListener(onClickListener);
    }

    public void setLeftView(String str, String str2, View.OnClickListener onClickListener) {
        this.mDate.setVisibility(0);
        this.mPop.setVisibility(0);
        this.mDate.setText(str);
        setTitle(str2);
        this.mLeftTitleBtn.setOnClickListener(onClickListener);
    }

    public void setDate(String str) {
        this.mDate.setText(str);
    }

    public void setLeftView(View.OnClickListener onClickListener, String str) {
        this.mDate.setVisibility(8);
        this.mPop.setVisibility(8);
        this.mBack.setVisibility(0);
        setTitle(str);
        this.mBack.setOnClickListener(onClickListener);
        this.mLeftTitleBtn.setOnClickListener((View.OnClickListener) null);
    }

    public void setLeftView(String str, String str2, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        this.mDate.setVisibility(0);
        this.mPop.setVisibility(0);
        this.mBack.setVisibility(0);
        this.mDate.setText(str);
        setTitle(str2);
        this.mLeftTitleBtn.setOnClickListener(onClickListener);
        this.mBack.setOnClickListener(onClickListener2);
    }

    public void setRightView() {
        this.mRightView.setVisibility(8);
        this.mLlUpDn.setVisibility(8);
        this.mRightViewText.setVisibility(8);
    }

    public void setRightView(int i, View.OnClickListener onClickListener) {
        this.mRightView.setVisibility(0);
        this.mLlUpDn.setVisibility(8);
        this.mRightViewText.setVisibility(8);
        this.mRlIpnut.setVisibility(8);
        this.mIvIconBtn.setVisibility(0);
        this.mIvIconBtn.setImageResource(i);
        this.mIvIconBtn.setOnClickListener(onClickListener);
    }

    public void setRightView(int i, int i2, String str, TextWatcher textWatcher) {
        this.mRightView.setVisibility(0);
        this.mLlUpDn.setVisibility(8);
        this.mRightViewText.setVisibility(8);
        this.mRlIpnut.setVisibility(0);
        this.mIvIconBtn.setVisibility(8);
        this.mEtInput.setInputType(i);
        this.mEtInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        this.mEtInput.setHint(str);
        this.mEtInput.addTextChangedListener(textWatcher);
    }

    public void setRightView(int i, int i2, String str, TextWatcher textWatcher, int i3, View.OnClickListener onClickListener) {
        this.mRightView.setVisibility(0);
        this.mLlUpDn.setVisibility(8);
        this.mRightViewText.setVisibility(8);
        this.mRlIpnut.setVisibility(0);
        this.mIvIconBtn.setVisibility(0);
        this.mEtInput.setInputType(i);
        this.mEtInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        this.mEtInput.setHint(str);
        this.mEtInput.addTextChangedListener(textWatcher);
        this.mIvIconBtn.setImageResource(i3);
        this.mIvIconBtn.setOnClickListener(onClickListener);
    }

    public void setRightView(View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        this.mRightView.setVisibility(8);
        this.mLlUpDn.setVisibility(0);
        this.mRightViewText.setVisibility(8);
        this.mLlUp.setOnClickListener(onClickListener);
        this.mLlDn.setOnClickListener(onClickListener2);
    }

    public void setRightView(int i, int i2, View.OnClickListener onClickListener) {
        this.mTResource = i;
        this.mFResource = i2;
        this.mRightView.setVisibility(0);
        this.mLlUpDn.setVisibility(8);
        this.mRightViewText.setVisibility(8);
        this.mRlIpnut.setVisibility(8);
        this.mIvIconBtn.setVisibility(0);
        this.mIvIconBtn.setOnClickListener(onClickListener);
        setImgState();
    }

    public void setRightView(int i, int i2, String str, TextWatcher textWatcher, int i3, int i4, View.OnClickListener onClickListener) {
        this.mTResource = i3;
        this.mFResource = i4;
        this.mRightView.setVisibility(0);
        this.mLlUpDn.setVisibility(8);
        this.mRightViewText.setVisibility(8);
        this.mRlIpnut.setVisibility(0);
        this.mIvIconBtn.setVisibility(0);
        this.mEtInput.setInputType(i);
        this.mEtInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        this.mEtInput.setHint(str);
        this.mEtInput.addTextChangedListener(textWatcher);
        this.mIvIconBtn.setOnClickListener(onClickListener);
        setImgState();
    }

    public void setRightView(String str, View.OnClickListener onClickListener) {
        this.mRightView.setVisibility(8);
        this.mLlUpDn.setVisibility(8);
        this.mRightViewText.setVisibility(0);
        if (str.length() > 8) {
            this.mRighext.setText(str.substring(0, 5) + "...");
        } else {
            this.mRighext.setText(str);
        }
        this.mRighext.setOnClickListener(onClickListener);
    }

    public boolean isImgState() {
        return this.mImgState;
    }

    public void setImgState(boolean z) {
        this.mImgState = z;
        setImgState();
    }

    public void setImgState() {
        if (this.mImgState) {
            this.mIvIconBtn.setImageResource(this.mFResource);
        } else {
            this.mIvIconBtn.setImageResource(this.mTResource);
        }
    }

    public void setmEtInputNull() {
        this.mEtInput.setText((CharSequence) null);
    }

    public EditText getmEtInput() {
        return this.mEtInput;
    }

    public void setPopupArrowVisibility(int i) {
        ImageView imageView = this.mPop;
        if (imageView != null) {
            imageView.setVisibility(i);
        }
    }
}
