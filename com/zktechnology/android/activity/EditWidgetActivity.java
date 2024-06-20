package com.zktechnology.android.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.zktechnology.android.bean.WidgetInfo;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.view.DragMainView;
import com.zktechnology.android.view.adapter.DragAdapter;
import com.zkteco.android.core.model.ButtonWidgetInfo;
import com.zkteco.android.core.sdk.BtnWidgetManager;
import java.util.ArrayList;
import java.util.List;

public class EditWidgetActivity extends BaseActivity {
    /* access modifiers changed from: private */
    public MyAdapter bottomAdapter;
    private List<ButtonWidgetInfo> bottomList = new ArrayList();
    ImageView close;
    ImageView commit;
    DragMainView drag_main;
    /* access modifiers changed from: private */
    public MyAdapter topAdapter;
    private List<ButtonWidgetInfo> topList = new ArrayList();
    /* access modifiers changed from: private */
    public BtnWidgetManager widgetManager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_edit_widget);
        this.drag_main = (DragMainView) findViewById(R.id.drag_main);
        this.commit = (ImageView) findViewById(R.id.commit);
        this.close = (ImageView) findViewById(R.id.close);
        BtnWidgetManager btnWidgetManager = new BtnWidgetManager(this);
        this.widgetManager = btnWidgetManager;
        this.topList = btnWidgetManager.getBtnWidgets(0);
        this.bottomList = this.widgetManager.getBtnWidgets(1);
        this.topAdapter = new MyAdapter(this.topList);
        this.bottomAdapter = new MyAdapter(this.bottomList);
        this.drag_main.setTopAdapter(this.topAdapter);
        this.drag_main.setBottomAdapter(this.bottomAdapter);
        this.commit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditWidgetActivity.this.widgetManager.saveBtnWidgets(0, EditWidgetActivity.this.topAdapter.getWidgetInfoList());
                EditWidgetActivity.this.widgetManager.saveBtnWidgets(1, EditWidgetActivity.this.bottomAdapter.getWidgetInfoList());
                EditWidgetActivity.this.finish();
            }
        });
        this.close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditWidgetActivity.this.setResult(0);
                EditWidgetActivity.this.finish();
            }
        });
    }

    class MyAdapter extends DragAdapter {
        List<ButtonWidgetInfo> list;

        public long getItemId(int i) {
            return 0;
        }

        private MyAdapter(List<ButtonWidgetInfo> list2) {
            this.list = list2;
        }

        public void onDataModelMove(int i, int i2) {
            this.list.add(i2, this.list.remove(i));
        }

        public Object getSwapData(int i) {
            return getItem(i);
        }

        public void removeData(int i) {
            this.list.remove(i);
        }

        public void addNewData(Object obj) {
            ButtonWidgetInfo buttonWidgetInfo = (ButtonWidgetInfo) obj;
            if (buttonWidgetInfo != null) {
                this.list.add(buttonWidgetInfo);
            }
        }

        public int getCount() {
            return this.list.size();
        }

        public ButtonWidgetInfo getItem(int i) {
            try {
                return this.list.get(i);
            } catch (Exception e) {
                e.printStackTrace();
                if (this.list.size() <= 0) {
                    return null;
                }
                List<ButtonWidgetInfo> list2 = this.list;
                return list2.get(list2.size() - 1);
            }
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(EditWidgetActivity.this, R.layout.item_widget, (ViewGroup) null);
            }
            ButtonWidgetInfo buttonWidgetInfo = this.list.get(i);
            ((ImageView) view.findViewById(R.id.img)).setBackgroundResource(WidgetInfo.valueOf(buttonWidgetInfo.getName()).unselectedImageId);
            ((TextView) view.findViewById(R.id.text)).setText(WidgetInfo.valueOf(buttonWidgetInfo.getName()).nameId);
            return view;
        }

        public List<ButtonWidgetInfo> getWidgetInfoList() {
            return this.list;
        }
    }
}
