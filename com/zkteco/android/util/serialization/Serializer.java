package com.zkteco.android.util.serialization;

import kotlin.Metadata;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J+\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\u0004\b\u0000\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¢\u0006\u0002\u0010\bJ\u001d\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0006\u001a\u00020\u0007H&¢\u0006\u0002\u0010\tJ\u001d\u0010\n\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u00032\b\u0010\u000b\u001a\u0004\u0018\u0001H\u0003H&¢\u0006\u0002\u0010\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&¨\u0006\u0011"}, d2 = {"Lcom/zkteco/android/util/serialization/Serializer;", "", "getInstanceFromString", "T", "clazz", "Ljava/lang/Class;", "string", "", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "(Ljava/lang/String;)Ljava/lang/Object;", "getStringFromInstance", "instance", "(Ljava/lang/Object;)Ljava/lang/String;", "setClassLoader", "", "classLoader", "Ljava/lang/ClassLoader;", "HelpersAndroidUtils_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: Serializer.kt */
public interface Serializer {
    <T> T getInstanceFromString(Class<T> cls, String str);

    <T> T getInstanceFromString(String str);

    <T> String getStringFromInstance(T t);

    void setClassLoader(ClassLoader classLoader);
}
