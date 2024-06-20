package com.zkteco.android.core.library;

import android.util.Log;
import com.zkteco.android.util.serialization.Serializer;
import com.zkteco.android.util.serialization.SerializerFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CoreModuleAPIInvoker {
    private static final String TAG = "com.zkteco.android.core.library.CoreModuleAPIInvoker";
    private final Method method;
    private final CoreModule module;
    private final Serializer serializer = SerializerFactory.INSTANCE.getDefaultSerializer();

    CoreModuleAPIInvoker(CoreModule coreModule, Method method2) {
        this.module = coreModule;
        this.method = method2;
    }

    public Object invokeMethod(String[] strArr) throws Exception {
        Object[] objArr;
        Object obj = null;
        if (strArr != null) {
            objArr = new Object[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                if (strArr[i] == null) {
                    objArr[i] = null;
                } else {
                    objArr[i] = this.serializer.getInstanceFromString(this.method.getParameterTypes()[i], strArr[i]);
                }
            }
        } else {
            objArr = null;
        }
        if (Configuration.INSTANCE.isLogInvokeMethodCalls()) {
            Log.d(TAG, "Invoked method '" + this.method.getName() + "'; params: " + Arrays.toString(objArr));
        }
        try {
            obj = this.method.invoke(this.module, objArr);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        if (Configuration.INSTANCE.isLogInvokeMethodCalls()) {
            Log.d(TAG, "Invoked method '" + this.method.getName() + "'; returned value: " + obj);
        }
        return obj;
    }

    public String toString() {
        return String.format("Module: %s, Method: %s", new Object[]{this.module.getClass().getCanonicalName(), this.method.getName()});
    }
}
