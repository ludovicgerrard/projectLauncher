package com.zkteco.android.zkcore.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ZKViewHolder {
    private View convertView;
    private int position;
    private SparseArray<View> views = new SparseArray<>();

    public ZKViewHolder setImageURI(int i, String str) {
        return this;
    }

    public ZKViewHolder(Context context, ViewGroup viewGroup, int i, int i2) {
        this.position = i2;
        View inflate = LayoutInflater.from(context).inflate(i, viewGroup, false);
        this.convertView = inflate;
        inflate.setTag(this);
    }

    public static ZKViewHolder get(Context context, View view, ViewGroup viewGroup, int i, int i2) {
        if (view == null) {
            return new ZKViewHolder(context, viewGroup, i, i2);
        }
        ZKViewHolder zKViewHolder = (ZKViewHolder) view.getTag();
        zKViewHolder.position = i2;
        return zKViewHolder;
    }

    public <T extends View> T getView(int i) {
        T t = (View) this.views.get(i);
        if (t != null) {
            return t;
        }
        T findViewById = this.convertView.findViewById(i);
        this.views.put(i, findViewById);
        return findViewById;
    }

    public View getConvertView() {
        return this.convertView;
    }

    public int getPosition() {
        return this.position;
    }

    public ZKViewHolder setText(int i, String str) {
        ((TextView) getView(i)).setText(str);
        return this;
    }

    public ZKViewHolder setImageResource(int i, int i2) {
        ((ImageView) getView(i)).setImageResource(i2);
        return this;
    }

    public ZKViewHolder setImageBitmap(int i, Bitmap bitmap) {
        ((ImageView) getView(i)).setImageBitmap(bitmap);
        return this;
    }
}
