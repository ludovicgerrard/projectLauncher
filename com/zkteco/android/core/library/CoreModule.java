package com.zkteco.android.core.library;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.Map;

public abstract class CoreModule {
    private static final String TAG = "CoreModule";
    private final Context context;
    private final Resources resources;

    public CoreModule(Context context2, Resources resources2) {
        this.context = context2;
        this.resources = resources2;
    }

    /* access modifiers changed from: protected */
    public String getString(int i) {
        return this.resources.getString(i);
    }

    /* access modifiers changed from: protected */
    public Bitmap getIcon(int i) {
        return BitmapFactory.decodeResource(this.resources, i);
    }

    /* access modifiers changed from: protected */
    public Context getContext() {
        return this.context;
    }

    /* access modifiers changed from: protected */
    public void broadcast(String str, Bundle bundle) {
        Intent intent = new Intent(str);
        intent.putExtras(bundle);
        getContext().sendStickyBroadcast(intent);
    }

    public void bindFunctions(Map<String, CoreModuleAPIInvoker> map) {
        for (Method method : getClass().getDeclaredMethods()) {
            CoreModuleAPI coreModuleAPI = (CoreModuleAPI) method.getAnnotation(CoreModuleAPI.class);
            if (coreModuleAPI != null) {
                String function = coreModuleAPI.function();
                if (map.containsKey(function)) {
                    String str = TAG;
                    Log.e(str, map.toString());
                    Log.e(str, String.format("Cannot bind function %s, identifier already exists", new Object[]{function}));
                }
                map.put(function, new CoreModuleAPIInvoker(this, method));
                Log.d(TAG, String.format("Module %s > Function %s bound to method %s", new Object[]{this, function, method.getName()}));
            }
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
