package com.zkteco.android.employeemgmt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean;
import java.util.List;

public class ZKStaffVertificationAdapter extends BaseAdapter {
    private Context context;
    private boolean hasSelectStatus = true;
    private final LayoutInflater inflater;
    private List<ZKStaffVerifyBean> mData;
    private int selectPosition;

    public long getItemId(int i) {
        return (long) i;
    }

    public ZKStaffVertificationAdapter(Context context2, List<ZKStaffVerifyBean> list) {
        this.context = context2;
        this.mData = list;
        this.inflater = LayoutInflater.from(context2);
    }

    public void setmData(List<ZKStaffVerifyBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public List<ZKStaffVerifyBean> getmData() {
        return this.mData;
    }

    public int getCount() {
        return this.mData.size();
    }

    public ZKStaffVerifyBean getItem(int i) {
        return this.mData.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = this.inflater.inflate(R.layout.item_staff_vertification, (ViewGroup) null);
            viewHolder.imageView = (ImageView) view2.findViewById(R.id.iv_verify);
            viewHolder.textView = (TextView) view2.findViewById(R.id.tv_verify);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(this.mData.get(i).getString());
        if (!this.hasSelectStatus) {
            viewHolder.imageView.setImageResource(R.mipmap.ic_unlight);
        } else if (i == this.selectPosition) {
            viewHolder.imageView.setImageResource(R.mipmap.ic_light);
        } else {
            viewHolder.imageView.setImageResource(R.mipmap.ic_unlight);
        }
        return view2;
    }

    public void setSelected(int i) {
        this.selectPosition = i;
    }

    public void setHasSelectStatus(boolean z) {
        this.hasSelectStatus = z;
    }

    public boolean getHasSelectStatus() {
        return this.hasSelectStatus;
    }

    public int getSelectPosition() {
        return this.selectPosition;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;

        ViewHolder() {
        }
    }
}
