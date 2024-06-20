package com.zktechnology.android.verify.dialog.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.db.orm.tna.WorkCode;
import java.util.List;

public class ZKWorkCodeAdapter extends BaseAdapter {
    private List<WorkCode> dataList;
    private LayoutInflater layoutInflater;
    private WorkCode selectWc;

    public long getItemId(int i) {
        return (long) i;
    }

    public ZKWorkCodeAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<WorkCode> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        List<WorkCode> list = this.dataList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public Object getItem(int i) {
        return this.dataList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        Hold hold;
        if (view == null) {
            view = this.layoutInflater.inflate(R.layout.work_code_item, (ViewGroup) null);
            hold = new Hold(view);
            view.setTag(hold);
        } else {
            hold = (Hold) view.getTag();
        }
        hold.work_code_item_name.setText(this.dataList.get(i).getWork_Code_Name());
        hold.work_code_item_num.setText(this.dataList.get(i).getWork_Code_Num());
        if (this.selectWc == null || !this.dataList.get(i).equals(this.selectWc)) {
            hold.workCodeBG.setBackgroundResource(R.color.launcher_clr_ffffff);
        } else {
            hold.workCodeBG.setBackgroundResource(R.color.launcher_clr_f3f3f3);
        }
        return view;
    }

    public void setSelectWc(WorkCode workCode) {
        this.selectWc = workCode;
        notifyDataSetChanged();
    }

    class Hold {
        /* access modifiers changed from: private */
        public RelativeLayout workCodeBG;
        /* access modifiers changed from: private */
        public TextView work_code_item_name;
        /* access modifiers changed from: private */
        public TextView work_code_item_num;

        Hold(View view) {
            this.work_code_item_name = (TextView) view.findViewById(R.id.work_code_item_name);
            this.work_code_item_num = (TextView) view.findViewById(R.id.work_code_item_num);
            this.workCodeBG = (RelativeLayout) view.findViewById(R.id.work_code_bg);
        }
    }
}
