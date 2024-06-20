package com.zkteco.android.employeemgmt.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;

public class ZKEditPinDialog extends Dialog {
    private Button btnCancel;
    private Button btnOk;
    private Context context;
    /* access modifiers changed from: private */
    public EditText etUserPin;
    private InputFilter[] inputFilters;
    /* access modifiers changed from: private */
    public boolean isSupportABC = false;
    /* access modifiers changed from: private */
    public OnClickListener onClickListener;
    private TextView tvUserPin;
    private int type;
    private String userPin;
    private View vUserPin;

    public interface OnClickListener {
        void onClickCancel();

        void onClickOk();
    }

    public ZKEditPinDialog(Context context2) {
        super(context2, R.style.dia_loading);
        this.context = context2;
    }

    private void initData() {
        this.etUserPin.setInputType(this.type);
        this.etUserPin.setFilters(this.inputFilters);
    }

    public void setSupportABC(boolean z) {
        this.isSupportABC = z;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_zkedit_pin);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        this.btnCancel = (Button) findViewById(R.id.btn_cancel);
        this.btnOk = (Button) findViewById(R.id.btn_ok);
        this.etUserPin = (EditText) findViewById(R.id.ed_user_pin);
        this.tvUserPin = (TextView) findViewById(R.id.tv_user_pin);
        this.vUserPin = findViewById(R.id.v_user_pin);
        this.etUserPin.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String obj = ZKEditPinDialog.this.etUserPin.getText().toString();
                if (!ZKEditPinDialog.this.isSupportABC && obj.startsWith("0")) {
                    ZKEditPinDialog.this.etUserPin.setText(obj.substring(1));
                }
            }
        });
    }

    private void setListener() {
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ZKEditPinDialog.this.onClickListener.onClickCancel();
            }
        });
        this.btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ZKEditPinDialog.this.onClickListener.onClickOk();
            }
        });
    }

    public void setUsePin(String str) {
        this.userPin = str;
    }

    public void setInputType(int i) {
        this.type = i;
    }

    public void setFilters(InputFilter[] inputFilterArr) {
        this.inputFilters = inputFilterArr;
    }

    public void setHint(String str) {
        if (str == null || str.equals("")) {
            this.tvUserPin.setVisibility(4);
            return;
        }
        this.tvUserPin.setVisibility(0);
        this.tvUserPin.setText(str);
        this.tvUserPin.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.anim_shake));
    }

    public void setOnClickListener(OnClickListener onClickListener2) {
        this.onClickListener = onClickListener2;
    }

    public String getUserPinText() {
        return this.etUserPin.getText().toString().trim();
    }

    public String getUserPin() {
        return this.etUserPin.getText().toString().trim();
    }

    private int calculateLength(String str) {
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char c2 = charArray[i2];
            i = ((c2 < 11904 || c2 > 65103) && (c2 < 41279 || c2 > 43584) && c2 < 128) ? i + 1 : i + 2;
        }
        return i;
    }
}
