package com.zkteco.android.employeemgmt.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;

public class ZKEditNameDialog extends Dialog {
    private Button btnCancel;
    private Button btnOk;
    private Context context;
    private EditText etFirstName;
    private EditText etLastName;
    private String firsetName;
    private String lastName;
    private int maxNameLength = 24;
    /* access modifiers changed from: private */
    public OnClickListener onClickListener;
    /* access modifiers changed from: private */
    public TextView tvFirstName;
    /* access modifiers changed from: private */
    public TextView tvLastName;
    private int type;
    private String userName;
    private View vLastName;

    public interface OnClickListener {
        void onClickCancel();

        void onClickOk();
    }

    public ZKEditNameDialog(Context context2) {
        super(context2, R.style.dia_loading);
        this.context = context2;
    }

    private void initData() {
        if (ZKLauncher.longName == 1) {
            this.maxNameLength = 32;
            this.etFirstName.setHint(R.string.first_name);
            this.etLastName.setVisibility(0);
            this.vLastName.setVisibility(0);
            this.tvLastName.setVisibility(4);
            this.etFirstName.setText(this.firsetName);
            this.etLastName.setText(this.lastName);
        } else {
            this.maxNameLength = 64;
            this.etFirstName.setHint(R.string.zk_staff_sadd_name);
            this.etLastName.setVisibility(8);
            this.vLastName.setVisibility(8);
            this.tvLastName.setVisibility(8);
            this.etFirstName.setText(this.userName);
        }
        this.etFirstName.setInputType(this.type);
        this.etLastName.setInputType(this.type);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_zkedit);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        this.etFirstName = (EditText) findViewById(R.id.ed_first_name);
        this.etLastName = (EditText) findViewById(R.id.ed_last_name);
        this.btnCancel = (Button) findViewById(R.id.btn_cancel);
        this.btnOk = (Button) findViewById(R.id.btn_ok);
        this.tvFirstName = (TextView) findViewById(R.id.tv_first_name);
        this.tvLastName = (TextView) findViewById(R.id.tv_last_name);
        this.vLastName = findViewById(R.id.v_last_name);
    }

    private void setListener() {
        this.etFirstName.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode actionMode) {
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }
        });
        this.etLastName.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode actionMode) {
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }
        });
        this.etFirstName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(this.maxNameLength)});
        this.etLastName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(this.maxNameLength)});
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ZKEditNameDialog.this.onClickListener.onClickCancel();
            }
        });
        this.btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ZKEditNameDialog.this.tvFirstName.setVisibility(4);
                if (ZKLauncher.longName == 1) {
                    ZKEditNameDialog.this.tvLastName.setVisibility(4);
                } else {
                    ZKEditNameDialog.this.tvLastName.setVisibility(8);
                }
                ZKEditNameDialog.this.onClickListener.onClickOk();
            }
        });
    }

    public void setOnClickListener(OnClickListener onClickListener2) {
        this.onClickListener = onClickListener2;
    }

    public void setHint(String str) {
        if (str == null || str.equals("")) {
            this.tvFirstName.setVisibility(4);
            return;
        }
        this.tvFirstName.setVisibility(0);
        this.tvFirstName.setText(str);
        this.tvFirstName.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.anim_shake));
    }

    public void setLastHint(String str) {
        if (str == null || str.equals("")) {
            this.tvLastName.setVisibility(4);
            return;
        }
        this.tvLastName.setVisibility(0);
        this.tvLastName.setText(str);
        this.tvLastName.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.anim_shake));
    }

    public String getInputText() {
        String trim = this.etLastName.getText().toString().trim();
        if (!TextUtils.isEmpty(trim)) {
            return this.etFirstName.getText().toString().trim() + " " + trim;
        }
        return this.etFirstName.getText().toString().trim();
    }

    public String getFirstName() {
        return this.etFirstName.getText().toString().trim();
    }

    public String getLastName() {
        return this.etLastName.getText().toString().trim();
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

    public void setUserName(String str) {
        this.userName = str;
    }

    public void setFirsetName(String str) {
        this.firsetName = str;
    }

    public void setLastName(String str) {
        this.lastName = str;
    }

    public void setInputType(int i) {
        this.type = i;
    }
}
