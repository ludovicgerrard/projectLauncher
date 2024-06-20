package com.zkteco.android.io;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiConfiguration;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000N\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007\u001a\u0018\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007\u001a\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004\u001a\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004\u001a\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a \u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0016H\u0002\u001a&\u0010\u001b\u001a\n \u001d*\u0004\u0018\u00010\u001c0\u001c2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0016\u001a\u001e\u0010!\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0016¨\u0006\""}, d2 = {"getDeclaredField", "", "obj", "name", "", "getField", "getSecurityType", "Lcom/zkteco/android/io/SecurityType;", "security", "securityLabel", "capabilities", "setDNS", "", "dns", "Ljava/net/InetAddress;", "wifiConf", "Landroid/net/wifi/WifiConfiguration;", "setGateway", "gateway", "setIpAddress", "addr", "prefixLength", "", "setIpAssignment", "strength", "Lcom/zkteco/android/io/Strength;", "level", "strengthIcon", "Landroid/graphics/drawable/Drawable;", "kotlin.jvm.PlatformType", "context", "Landroid/content/Context;", "arrayId", "strengthString", "HelpersAndroidIO_release"}, k = 2, mv = {1, 1, 9})
/* compiled from: WifiUtils.kt */
public final class WifiUtilsKt {
    private static final Strength strength(int i) {
        Strength strength = Strength.POOR;
        if (i >= -50) {
            return Strength.EXCELLENT;
        }
        if (i >= -50 || i < -60) {
            return (i >= -60 || i < -80) ? strength : Strength.FAIR;
        }
        return Strength.GOOD;
    }

    public static final SecurityType getSecurityType(String str) {
        Intrinsics.checkParameterIsNotNull(str, "security");
        CharSequence charSequence = str;
        if (StringsKt.contains$default(charSequence, (CharSequence) "WPA", false, 2, (Object) null)) {
            return SecurityType.WPA;
        }
        if (StringsKt.contains$default(charSequence, (CharSequence) "WEP", false, 2, (Object) null)) {
            return SecurityType.WEP;
        }
        return SecurityType.OPEN;
    }

    public static final String securityLabel(String str) {
        Intrinsics.checkParameterIsNotNull(str, "capabilities");
        CharSequence charSequence = str;
        if (StringsKt.contains$default(charSequence, (CharSequence) "WPA-", false, 2, (Object) null) && StringsKt.contains$default(charSequence, (CharSequence) "WPA2-", false, 2, (Object) null)) {
            return "WPA/WPA2";
        }
        if (StringsKt.contains$default(charSequence, (CharSequence) "WPA-", false, 2, (Object) null)) {
            return "WPA";
        }
        if (StringsKt.contains$default(charSequence, (CharSequence) "WPA2-", false, 2, (Object) null)) {
            return "WPA2";
        }
        if (StringsKt.contains$default(charSequence, (CharSequence) "WEP", false, 2, (Object) null)) {
            return "WEP";
        }
        return "OPEN";
    }

    public static final String strengthString(Context context, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        String str = context.getResources().getStringArray(i2)[strength(i).ordinal()];
        Intrinsics.checkExpressionValueIsNotNull(str, "context.resources.getStr…[strength(level).ordinal]");
        return str;
    }

    public static final Drawable strengthIcon(Context context, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        return context.getResources().obtainTypedArray(i2).getDrawable(strength(i).ordinal());
    }

    public static final void setIpAssignment(WifiConfiguration wifiConfiguration) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        Intrinsics.checkParameterIsNotNull(wifiConfiguration, "wifiConf");
        Field field = wifiConfiguration.getClass().getField("ipAssignment");
        Intrinsics.checkExpressionValueIsNotNull(field, "ipField");
        Class<?> type = field.getType();
        Intrinsics.checkExpressionValueIsNotNull(type, "ipField.type");
        Class<?> cls = Class.forName(type.getName());
        Intrinsics.checkExpressionValueIsNotNull(cls, "clzIpField");
        field.set(wifiConfiguration, cls.getEnumConstants()[0]);
    }

    public static final void setIpAddress(InetAddress inetAddress, int i, WifiConfiguration wifiConfiguration) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(inetAddress, "addr");
        Intrinsics.checkParameterIsNotNull(wifiConfiguration, "wifiConf");
        Object field = getField(wifiConfiguration, "linkProperties");
        Object newInstance = Class.forName("android.net.LinkAddress").getConstructor(new Class[]{InetAddress.class, Integer.TYPE}).newInstance(new Object[]{inetAddress, Integer.valueOf(i)});
        Object declaredField = getDeclaredField(field, "mLinkAddresses");
        if (declaredField != null) {
            ArrayList arrayList = (ArrayList) declaredField;
            arrayList.clear();
            arrayList.add(newInstance);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.util.ArrayList<kotlin.Any>");
    }

    public static final void setGateway(InetAddress inetAddress, WifiConfiguration wifiConfiguration) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(inetAddress, "gateway");
        Intrinsics.checkParameterIsNotNull(wifiConfiguration, "wifiConf");
        Object field = getField(wifiConfiguration, "linkProperties");
        Object newInstance = Class.forName("android.net.RouteInfo").getConstructor(new Class[]{InetAddress.class}).newInstance(new Object[]{inetAddress});
        Object declaredField = getDeclaredField(field, "mRoutes");
        if (declaredField != null) {
            ArrayList arrayList = (ArrayList) declaredField;
            arrayList.clear();
            arrayList.add(newInstance);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.util.ArrayList<kotlin.Any>");
    }

    public static final void setDNS(InetAddress inetAddress, WifiConfiguration wifiConfiguration) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        Intrinsics.checkParameterIsNotNull(inetAddress, "dns");
        Intrinsics.checkParameterIsNotNull(wifiConfiguration, "wifiConf");
        Object declaredField = getDeclaredField(getField(wifiConfiguration, "linkProperties"), "mDnses");
        if (declaredField != null) {
            ArrayList arrayList = (ArrayList) declaredField;
            arrayList.clear();
            arrayList.add(inetAddress);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<kotlin.Any> /* = java.util.ArrayList<kotlin.Any> */");
    }

    public static final Object getField(Object obj, String str) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        Intrinsics.checkParameterIsNotNull(str, "name");
        Object obj2 = obj.getClass().getField(str).get(obj);
        Intrinsics.checkExpressionValueIsNotNull(obj2, "field.get(obj)");
        return obj2;
    }

    public static final Object getDeclaredField(Object obj, String str) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        Intrinsics.checkParameterIsNotNull(str, "name");
        Field declaredField = obj.getClass().getDeclaredField(str);
        Intrinsics.checkExpressionValueIsNotNull(declaredField, "field");
        declaredField.setAccessible(true);
        Object obj2 = declaredField.get(obj);
        Intrinsics.checkExpressionValueIsNotNull(obj2, "field.get(obj)");
        return obj2;
    }
}
