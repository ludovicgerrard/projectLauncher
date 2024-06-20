package com.zktechnology.android.verify.dialog.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.dialog.view.adapter.ZKWorkCodeAdapter;
import com.zktechnology.android.verify.dialog.view.util.ZKListenableActionLayout;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.db.orm.tna.WorkCode;
import com.zkteco.android.zkcore.view.ZKToast;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ZKWorkCodeDialog extends ZKTimeOutDialog {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String TAG = "com.zktechnology.android.verify.dialog.view.ZKWorkCodeDialog";
    private static final Comparator<WorkCode> sorter = new Comparator<WorkCode>() {
        public int compare(WorkCode workCode, WorkCode workCode2) {
            int length = workCode.getWork_Code_Num().length();
            int length2 = workCode2.getWork_Code_Num().length();
            if (length > length2) {
                return 1;
            }
            return length < length2 ? -1 : 0;
        }
    };
    /* access modifiers changed from: private */
    public ZKWorkCodeAdapter adapter;
    /* access modifiers changed from: private */
    public boolean bolClean;
    /* access modifiers changed from: private */
    public List<WorkCode> dataList = new ArrayList();
    /* access modifiers changed from: private */
    public String enable = "0";
    /* access modifiers changed from: private */
    public WorkCode selectWc;
    private TextView workCodeDialogCancel;
    private TextView workCodeDialogConfirm;
    private ListView workCodeDialogList;
    /* access modifiers changed from: private */
    public EditText workCodeDialogSearch;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        View inflate = layoutInflater.inflate(R.layout.work_code_dialog, viewGroup);
        setVerifiedInfoTimeOutMsWorkCode();
        resetInactiveTimeout();
        if (inflate instanceof ZKListenableActionLayout) {
            ((ZKListenableActionLayout) inflate).setOnUserInteractiveListener(new ZKListenableActionLayout.OnUserInteractionListener() {
                public void onUserInteraction() {
                    ZKWorkCodeDialog.this.resetInactiveTimeout();
                }
            });
        }
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
        initData();
        initListener();
    }

    private void initView(View view) {
        this.workCodeDialogList = (ListView) view.findViewById(R.id.work_code_dialog_list);
        this.workCodeDialogSearch = (EditText) view.findViewById(R.id.work_code_dialog_search);
        this.workCodeDialogConfirm = (TextView) view.findViewById(R.id.work_code_dialog_confirm);
        this.workCodeDialogCancel = (TextView) view.findViewById(R.id.work_code_dialog_cancel);
    }

    private void initData() {
        this.dataList = new ArrayList();
        ZKWorkCodeAdapter zKWorkCodeAdapter = new ZKWorkCodeAdapter(this.mContext);
        this.adapter = zKWorkCodeAdapter;
        this.workCodeDialogList.setAdapter(zKWorkCodeAdapter);
        this.workCodeDialogList.setChoiceMode(1);
        updateWorkCode("");
    }

    private void initListener() {
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == 4 && keyEvent.getAction() == 0) {
                    ZKWorkCodeDialog.this.dismiss();
                    return true;
                } else if (!"0".equals(ZKWorkCodeDialog.this.enable) || i != 66 || keyEvent.getAction() != 0 || ((ZKWorkCodeDialog.this.dataList != null && ZKWorkCodeDialog.this.dataList.size() > 0) || TextUtils.isEmpty(ZKWorkCodeDialog.this.workCodeDialogSearch.getText()))) {
                    return false;
                } else {
                    int parseInt = Integer.parseInt(ZKWorkCodeDialog.this.workCodeDialogSearch.getText().toString());
                    if (parseInt == 0) {
                        ZKWorkCodeDialog.this.workCodeDialogSearch.setText("");
                        ZKToast.showToast(ZKWorkCodeDialog.this.mContext, ZKWorkCodeDialog.this.getString(R.string.error_work_code));
                        return false;
                    }
                    ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_WORK_CODE, parseInt);
                    ZKVerController.getInstance().changeState(ZKVerConState.STATE_RECORD);
                    boolean unused = ZKWorkCodeDialog.this.bolClean = true;
                    ZKWorkCodeDialog.this.dismiss();
                    return true;
                }
            }
        });
        this.workCodeDialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ZKWorkCodeDialog zKWorkCodeDialog = ZKWorkCodeDialog.this;
                WorkCode unused = zKWorkCodeDialog.selectWc = (WorkCode) zKWorkCodeDialog.dataList.get(i);
                ZKWorkCodeDialog.this.adapter.setSelectWc(ZKWorkCodeDialog.this.selectWc);
                ZKWorkCodeDialog.this.hideSoftKeyboard();
            }
        });
        this.workCodeDialogConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ZKWorkCodeDialog.this.selectWc == null) {
                    ZKToast.showToast(ZKWorkCodeDialog.this.mContext, ZKWorkCodeDialog.this.getString(R.string.please_select_workcode));
                    return;
                }
                ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_WORK_CODE, (int) ZKWorkCodeDialog.this.selectWc.getID());
                ZKVerController.getInstance().changeState(ZKVerConState.STATE_RECORD);
                boolean unused = ZKWorkCodeDialog.this.bolClean = true;
                ZKWorkCodeDialog.this.dismiss();
            }
        });
        this.workCodeDialogCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ZKWorkCodeDialog.this.dismiss();
            }
        });
        this.workCodeDialogSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ZKWorkCodeDialog.this.resetInactiveTimeout();
                ZKWorkCodeDialog.this.updateWorkCode(charSequence);
            }
        });
        this.workCodeDialogSearch.clearFocus();
    }

    /* access modifiers changed from: private */
    public void updateWorkCode(CharSequence charSequence) {
        List arrayList = new ArrayList();
        if (charSequence != null) {
            try {
                arrayList = new WorkCode().getQueryBuilder().where().like("Work_Code_Num", "%" + charSequence + "%").query();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<WorkCode> list = this.dataList;
        if (list == null) {
            this.dataList = new ArrayList();
        } else {
            list.clear();
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            this.dataList.addAll(arrayList);
        }
        Collections.sort(this.dataList, sorter);
        this.adapter.updateData(this.dataList);
        this.adapter.notifyDataSetChanged();
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setSoftInputMode(32);
        }
    }

    public void dismiss() {
        if (!this.bolClean) {
            ZKVerProcessPar.cleanData(9);
            ZKVerProcessPar.cleanView();
        }
        ZKVerProcessPar.cleanBtnWidget();
        super.dismiss();
    }

    /* access modifiers changed from: private */
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method");
        if (getDialog().getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getDialog().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void setEditEnable(String str) {
        this.enable = str;
    }
}
