package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.SharedPreferencesProvider;
import com.zkteco.android.core.library.CoreProvider;
import com.zkteco.android.core.model.ButtonWidgetInfo;
import com.zkteco.util.YAMLHelper;
import java.util.List;

public class BtnWidgetManager {
    private final String POSITION = "_position";
    private final String PRESSED = "_pressed";
    private SharedPreferencesProvider provider;

    public BtnWidgetManager(Context context) {
        this.provider = SharedPreferencesProvider.getInstance(new CoreProvider(context));
    }

    public void savePressedWidgetId(int i) {
        this.provider.set("_pressed", String.valueOf(i));
        this.provider.set("_position", "0");
    }

    public void savePressedWidgetId(int i, int i2) {
        String valueOf = String.valueOf(i);
        String valueOf2 = String.valueOf(i2);
        this.provider.set("_pressed", valueOf);
        this.provider.set("_position", valueOf2);
    }

    public int getPressedWidgetId() {
        return Integer.valueOf(this.provider.get("_pressed", "-1")).intValue();
    }

    public int getPressedPosition() {
        return Integer.valueOf(this.provider.get("_position", "-1")).intValue();
    }

    public int removePressedId() {
        String str = this.provider.get("_pressed", "-1");
        this.provider.remove("_pressed");
        this.provider.remove("_position");
        return Integer.valueOf(str).intValue();
    }

    public void saveBtnWidgets(int i, List<ButtonWidgetInfo> list) {
        this.provider.set(String.valueOf(i), YAMLHelper.getStringFromInstance(list));
    }

    public List<ButtonWidgetInfo> getBtnWidgets(int i) {
        return (List) YAMLHelper.getInstanceFromString(this.provider.get(String.valueOf(i), ""));
    }

    public void removeBtnWidget(int i) {
        this.provider.remove(String.valueOf(i));
    }
}
