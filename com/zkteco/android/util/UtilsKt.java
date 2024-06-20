package com.zkteco.android.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000<\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\b\u001a\u0012\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\u001a\u0012\u0010\u000e\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u000b\u001a&\u0010\u0010\u001a\u001e\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00020\u0002\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00130\u00130\u0011*\u00020\u0014\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0015"}, d2 = {"PERMISSIONS_NEED_USER_CONFIRMATION", "", "", "getPERMISSIONS_NEED_USER_CONFIRMATION", "()Ljava/util/Set;", "ignoreErrors", "", "block", "Lkotlin/Function0;", "grantUSBDevicePermission", "", "Landroid/content/Context;", "usbDevice", "Landroid/hardware/usb/UsbDevice;", "isPackageInstalled", "context", "toMap", "", "kotlin.jvm.PlatformType", "", "Landroid/os/Bundle;", "HelpersAndroidUtils_release"}, k = 2, mv = {1, 1, 9})
/* compiled from: Utils.kt */
public final class UtilsKt {
    private static final Set<String> PERMISSIONS_NEED_USER_CONFIRMATION = SetsKt.setOf("android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR", "android.permission.CAMERA", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.GET_ACCOUNTS", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.RECORD_AUDIO", "android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "com.android.voicemail.permission.ADD_VOICEMAIL", "android.permission.USE_SIP", "android.permission.PROCESS_OUTGOING_CALLS", "android.permission.BODY_SENSORS", "android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.RECEIVE_MMS", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE");

    public static final Set<String> getPERMISSIONS_NEED_USER_CONFIRMATION() {
        return PERMISSIONS_NEED_USER_CONFIRMATION;
    }

    public static final void ignoreErrors(Function0<Unit> function0) {
        Intrinsics.checkParameterIsNotNull(function0, "block");
        try {
            function0.invoke();
        } catch (Exception e) {
            Log.e("ignoreErrors", Log.getStackTraceString(e));
        }
    }

    public static final boolean grantUSBDevicePermission(Context context, UsbDevice usbDevice) {
        Intrinsics.checkParameterIsNotNull(context, "$receiver");
        Intrinsics.checkParameterIsNotNull(usbDevice, "usbDevice");
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            Method declaredMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class});
            Intrinsics.checkExpressionValueIsNotNull(declaredMethod, "getServiceMethod");
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke((Object) null, new Object[]{"usb"});
            if (invoke != null) {
                Class<?> cls = Class.forName("android.hardware.usb.IUsbManager");
                Method declaredMethod2 = Class.forName("android.hardware.usb.IUsbManager$Stub").getDeclaredMethod("asInterface", new Class[]{IBinder.class});
                Intrinsics.checkExpressionValueIsNotNull(declaredMethod2, "asInterfaceMethod");
                declaredMethod2.setAccessible(true);
                Object invoke2 = declaredMethod2.invoke((Object) null, new Object[]{(IBinder) invoke});
                Method declaredMethod3 = cls.getDeclaredMethod("grantDevicePermission", new Class[]{UsbDevice.class, JvmClassMappingKt.getJavaPrimitiveType(Reflection.getOrCreateKotlinClass(Integer.TYPE))});
                Intrinsics.checkExpressionValueIsNotNull(declaredMethod3, "grantDevicePermissionMethod");
                declaredMethod3.setAccessible(true);
                declaredMethod3.invoke(invoke2, new Object[]{usbDevice, Integer.valueOf(applicationInfo.uid)});
                return true;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.os.IBinder");
        } catch (Exception e) {
            Log.e("grant USB permission", Log.getStackTraceString(e));
            return false;
        }
    }

    public static final boolean isPackageInstalled(String str, Context context) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        Intrinsics.checkParameterIsNotNull(context, "context");
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static final Map<String, Object> toMap(Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(bundle, "$receiver");
        Set keySet = bundle.keySet();
        Intrinsics.checkExpressionValueIsNotNull(keySet, "keySet()");
        Iterable<String> iterable = keySet;
        Map<String, Object> linkedHashMap = new LinkedHashMap<>(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterable, 10)), 16));
        for (String str : iterable) {
            Pair pair = TuplesKt.to(str, bundle.get(str));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return linkedHashMap;
    }
}
