package com.zkteco.android.employeemgmt.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.zktechnology.android.launcher.R;
import java.util.List;

public class ZKSpinerPopupWindow<T> extends PopupWindow {
    private Activity activity;
    /* access modifiers changed from: private */
    public Context context;
    private int firstPosition;
    /* access modifiers changed from: private */
    public LayoutInflater inflater;
    /* access modifiers changed from: private */
    public List<T> list;
    private ZKSpinerPopupWindow<T>.MyAdapter mAdapter;

    public ZKSpinerPopupWindow(Context context2, Activity activity2, List<T> list2, int i, AdapterView.OnItemClickListener onItemClickListener) {
        super(context2);
        this.activity = activity2;
        this.context = context2;
        this.inflater = LayoutInflater.from(context2);
        this.list = list2;
        this.firstPosition = i;
        init(onItemClickListener);
    }

    private void init(AdapterView.OnItemClickListener onItemClickListener) {
        View inflate = this.inflater.inflate(R.layout.spiner_window_layout, (ViewGroup) null);
        setContentView(inflate);
        setWidth(-2);
        setHeight(-2);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        ListView listView = (ListView) inflate.findViewById(R.id.listview);
        ZKSpinerPopupWindow<T>.MyAdapter myAdapter = new MyAdapter(this.firstPosition);
        this.mAdapter = myAdapter;
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(onItemClickListener);
        this.mAdapter.notifyDataSetChanged();
    }

    public void setBackgroundAlpha(float f) {
        WindowManager.LayoutParams attributes = this.activity.getWindow().getAttributes();
        attributes.alpha = f;
        if (f == 1.0f) {
            this.activity.getWindow().clearFlags(2);
        } else {
            this.activity.getWindow().addFlags(2);
        }
        this.activity.getWindow().setAttributes(attributes);
    }

    private class MyAdapter extends BaseAdapter {
        private int nowPos;

        public long getItemId(int i) {
            return (long) i;
        }

        private MyAdapter(int i) {
            this.nowPos = i;
        }

        public int getCount() {
            return ZKSpinerPopupWindow.this.list.size();
        }

        public Object getItem(int i) {
            return ZKSpinerPopupWindow.this.list.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2;
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view2 = ZKSpinerPopupWindow.this.inflater.inflate(R.layout.item_simple_spinner_dropdown, (ViewGroup) null);
                TextView unused = viewHolder.tvKind = (TextView) view2.findViewById(R.id.item_tv);
                view2.setTag(viewHolder);
            } else {
                view2 = view;
                viewHolder = (ViewHolder) view.getTag();
            }
            if (this.nowPos == i) {
                viewHolder.tvKind.setText(getItem(i).toString());
                if (Build.VERSION.SDK_INT >= 23) {
                    viewHolder.tvKind.setTextColor(ZKSpinerPopupWindow.this.context.getColor(R.color.clr_7AC143));
                } else if (ZKSpinerPopupWindow.this.context.getResources().getConfiguration().orientation == 1) {
                    viewHolder.tvKind.setTextColor(ContextCompat.getColor(ZKSpinerPopupWindow.this.context, R.color.clr_7AC143));
                }
            } else {
                viewHolder.tvKind.setText(getItem(i).toString());
                if (Build.VERSION.SDK_INT >= 23) {
                    viewHolder.tvKind.setTextColor(ZKSpinerPopupWindow.this.context.getColor(R.color.clr_000000));
                } else if (ZKSpinerPopupWindow.this.context.getResources().getConfiguration().orientation == 1) {
                    viewHolder.tvKind.setTextColor(ContextCompat.getColor(ZKSpinerPopupWindow.this.context, R.color.clr_000000));
                }
            }
            return view2;
        }

        /* access modifiers changed from: private */
        public void setNowPos(int i) {
            this.nowPos = i;
        }
    }

    public class ViewHolder {
        /* access modifiers changed from: private */
        public TextView tvKind;

        public ViewHolder() {
        }
    }

    public void setNowPosition(int i) {
        this.mAdapter.setNowPos(i);
    }
}
