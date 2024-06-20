package com.zkteco.android.zkcore.view.wheelview.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public abstract class AbstractWheelTextAdapter extends AbstractWheelAdapter {
    public static final int DEFAULT_TEXT_COLOR = -15724528;
    public static final int DEFAULT_TEXT_SIZE = 24;
    public static final int LABEL_COLOR = -9437072;
    protected static final int NO_RESOURCE = 0;
    public static final int TEXT_VIEW_ITEM_RESOURCE = -1;
    private static int maxsize = 24;
    private static int minsize = 14;
    private ArrayList<View> arrayList;
    protected Context context;
    private int currentIndex;
    protected int emptyItemResourceId;
    protected LayoutInflater inflater;
    protected int itemResourceId;
    protected int itemTextResourceId;
    private int ohterTextColor;
    private int textColor;
    private int textSize;

    /* access modifiers changed from: protected */
    public abstract CharSequence getItemText(int i);

    protected AbstractWheelTextAdapter(Context context2) {
        this(context2, -1);
    }

    protected AbstractWheelTextAdapter(Context context2, int i) {
        this(context2, i, 0, 0, maxsize, minsize);
    }

    protected AbstractWheelTextAdapter(Context context2, int i, int i2, int i3, int i4, int i5) {
        this.textColor = DEFAULT_TEXT_COLOR;
        this.textSize = 24;
        this.ohterTextColor = DEFAULT_TEXT_COLOR;
        this.currentIndex = 0;
        this.arrayList = new ArrayList<>();
        this.context = context2;
        this.itemResourceId = i;
        this.itemTextResourceId = i2;
        this.currentIndex = i3;
        maxsize = i4;
        minsize = i5;
        this.inflater = (LayoutInflater) context2.getSystemService("layout_inflater");
    }

    public ArrayList<View> getTestViews() {
        return this.arrayList;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int i) {
        this.textColor = i;
    }

    public int getTextSize() {
        return this.textSize;
    }

    public void setTextSize(int i) {
        this.textSize = i;
    }

    public int getItemResource() {
        return this.itemResourceId;
    }

    public void setItemResource(int i) {
        this.itemResourceId = i;
    }

    public int getItemTextResource() {
        return this.itemTextResourceId;
    }

    public void setItemTextResource(int i) {
        this.itemTextResourceId = i;
    }

    public int getEmptyItemResource() {
        return this.emptyItemResourceId;
    }

    public void setEmptyItemResource(int i) {
        this.emptyItemResourceId = i;
    }

    public View getItem(int i, View view, ViewGroup viewGroup) {
        if (i < 0 || i >= getItemsCount()) {
            return null;
        }
        if (view == null) {
            view = getView(this.itemResourceId, viewGroup);
        }
        TextView textView = getTextView(view, this.itemTextResourceId);
        if (!this.arrayList.contains(textView)) {
            this.arrayList.add(textView);
        }
        if (textView != null) {
            CharSequence itemText = getItemText(i);
            if (itemText == null) {
                itemText = "";
            }
            textView.setText(itemText);
            if (i == this.currentIndex) {
                textView.setTextSize((float) maxsize);
                textView.setTextColor(this.textColor);
            } else {
                textView.setTextColor(this.ohterTextColor);
                textView.setTextSize((float) minsize);
            }
            if (this.itemResourceId == -1) {
                configureTextView(textView);
            }
        }
        return view;
    }

    public View getEmptyItem(View view, ViewGroup viewGroup) {
        if (view == null) {
            view = getView(this.emptyItemResourceId, viewGroup);
        }
        if (this.emptyItemResourceId == -1 && (view instanceof TextView)) {
            configureTextView((TextView) view);
        }
        return view;
    }

    /* access modifiers changed from: protected */
    public void configureTextView(TextView textView) {
        textView.setTextColor(this.textColor);
        textView.setGravity(17);
        textView.setTextSize((float) this.textSize);
        textView.setLines(1);
        textView.setTypeface(Typeface.SANS_SERIF, 1);
    }

    private TextView getTextView(View view, int i) {
        if (i == 0) {
            try {
                if (view instanceof TextView) {
                    return (TextView) view;
                }
            } catch (ClassCastException e) {
                Log.e("AbstractWheelAdapter", "You must supply a resource ID for a TextView");
                throw new IllegalStateException("AbstractWheelAdapter requires the resource ID to be a TextView", e);
            }
        }
        if (i != 0) {
            return (TextView) view.findViewById(i);
        }
        return null;
    }

    private View getView(int i, ViewGroup viewGroup) {
        if (i == -1) {
            return new TextView(this.context);
        }
        if (i != 0) {
            return this.inflater.inflate(i, viewGroup, false);
        }
        return null;
    }

    public void setOhterTextColor(int i) {
        this.ohterTextColor = i;
    }

    public void setCurrentIndex(int i) {
        this.currentIndex = i;
    }
}
