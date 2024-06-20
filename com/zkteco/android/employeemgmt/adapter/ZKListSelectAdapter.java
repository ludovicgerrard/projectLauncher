package com.zkteco.android.employeemgmt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.employeemgmt.model.ZKListSelectModel;
import java.util.ArrayList;

public class ZKListSelectAdapter extends BaseAdapter {
    ArrayList<ZKListSelectModel> datas;
    Listener listener;
    private Context mContext;

    public interface Listener {
        void callBack(int i, int i2);
    }

    public long getItemId(int i) {
        return 0;
    }

    public ZKListSelectAdapter(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        ArrayList<ZKListSelectModel> arrayList = this.datas;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public void setDatas(ArrayList<ZKListSelectModel> arrayList) {
        this.datas = arrayList;
        notifyDataSetChanged();
    }

    public Object getItem(int i) {
        return this.datas.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(this.mContext, R.layout.item_select, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.tv_text = (TextView) view.findViewById(R.id.tv_text);
            viewHolder.check_iv = (ImageView) view.findViewById(R.id.check_iv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ZKListSelectModel zKListSelectModel = this.datas.get(i);
        viewHolder.tv_text.setText(zKListSelectModel.getContent());
        if (zKListSelectModel.isSelectStatus()) {
            viewHolder.check_iv.setImageResource(R.mipmap.time_rule_select);
        } else {
            viewHolder.check_iv.setImageResource(R.mipmap.time_rule_nocheck);
        }
        return view;
    }

    private class ViewHolder {
        ImageView check_iv;
        TextView tv_text;

        private ViewHolder() {
        }
    }

    public void setListener(Listener listener2) {
        this.listener = listener2;
    }
}
