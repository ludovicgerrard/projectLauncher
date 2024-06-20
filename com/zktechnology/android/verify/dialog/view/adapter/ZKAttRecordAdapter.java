package com.zktechnology.android.verify.dialog.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.verify.bean.ZKAttRecord;
import java.util.List;

public class ZKAttRecordAdapter extends BaseAdapter {
    private List<ZKAttRecord> dataList;
    private LayoutInflater layoutInflater;

    public long getItemId(int i) {
        return (long) i;
    }

    public ZKAttRecordAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<ZKAttRecord> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        List<ZKAttRecord> list = this.dataList;
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
            view = this.layoutInflater.inflate(R.layout.att_record_item, (ViewGroup) null);
            hold = new Hold(view);
            view.setTag(hold);
        } else {
            hold = (Hold) view.getTag();
        }
        hold.att_name.setText(this.dataList.get(i).getName());
        hold.att_status.setText(this.dataList.get(i).getWork_Code_Name());
        if (this.dataList.get(i).getDescription() == null) {
            String state_Name = this.dataList.get(i).getState_Name();
            state_Name.hashCode();
            char c2 = 65535;
            switch (state_Name.hashCode()) {
                case -892482113:
                    if (state_Name.equals("state0")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case -892482112:
                    if (state_Name.equals("state1")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -892482111:
                    if (state_Name.equals("state2")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -892482110:
                    if (state_Name.equals("state3")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -892482109:
                    if (state_Name.equals("state4")) {
                        c2 = 4;
                        break;
                    }
                    break;
                case -892482108:
                    if (state_Name.equals("state5")) {
                        c2 = 5;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    hold.att_status.setText(getString(R.string.zk_rec_check_in));
                    break;
                case 1:
                    hold.att_status.setText(getString(R.string.zk_rec_check_out));
                    break;
                case 2:
                    hold.att_status.setText(getString(R.string.zk_rec_break_in));
                    break;
                case 3:
                    hold.att_status.setText(getString(R.string.zk_rec_break_out));
                    break;
                case 4:
                    hold.att_status.setText(getString(R.string.zk_rec_overtime_in));
                    break;
                case 5:
                    hold.att_status.setText(getString(R.string.zk_rec_overtime_out));
                    break;
                default:
                    hold.att_status.setText(this.dataList.get(i).getDescription());
                    break;
            }
        } else {
            hold.att_status.setText(this.dataList.get(i).getDescription());
        }
        hold.att_user_num.setText(this.dataList.get(i).getUserPin());
        hold.att_date.setText(this.dataList.get(i).getVerify_Time());
        return view;
    }

    private String getString(int i) {
        return AppUtils.getString(i);
    }

    class Hold {
        /* access modifiers changed from: private */
        public TextView att_date;
        /* access modifiers changed from: private */
        public TextView att_name;
        /* access modifiers changed from: private */
        public TextView att_status;
        /* access modifiers changed from: private */
        public TextView att_user_num;

        Hold(View view) {
            this.att_name = (TextView) view.findViewById(R.id.att_name);
            this.att_status = (TextView) view.findViewById(R.id.att_status);
            this.att_user_num = (TextView) view.findViewById(R.id.att_user_num);
            this.att_date = (TextView) view.findViewById(R.id.att_date);
        }
    }
}
