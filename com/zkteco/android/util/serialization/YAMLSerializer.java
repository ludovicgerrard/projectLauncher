package com.zkteco.android.util.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J+\u0010\u0005\u001a\u0004\u0018\u0001H\u0006\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u000bJ\u001d\u0010\u0005\u001a\u0004\u0018\u0001H\u0006\"\u0004\b\u0000\u0010\u00062\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\fJ%\u0010\r\u001a\n \u000e*\u0004\u0018\u00010\n0\n\"\u0004\b\u0000\u0010\u00062\b\u0010\u000f\u001a\u0004\u0018\u0001H\u0006H\u0016¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/zkteco/android/util/serialization/YAMLSerializer;", "Lcom/zkteco/android/util/serialization/Serializer;", "()V", "yaml", "Lorg/yaml/snakeyaml/Yaml;", "getInstanceFromString", "T", "clazz", "Ljava/lang/Class;", "string", "", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "(Ljava/lang/String;)Ljava/lang/Object;", "getStringFromInstance", "kotlin.jvm.PlatformType", "instance", "(Ljava/lang/Object;)Ljava/lang/String;", "setClassLoader", "", "classLoader", "Ljava/lang/ClassLoader;", "HelpersAndroidUtils_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: SerializerFactory.kt */
final class YAMLSerializer implements Serializer {
    public static final YAMLSerializer INSTANCE = new YAMLSerializer();
    private static Yaml yaml = new Yaml();

    private YAMLSerializer() {
    }

    public void setClassLoader(ClassLoader classLoader) {
        Intrinsics.checkParameterIsNotNull(classLoader, "classLoader");
        yaml = new Yaml((BaseConstructor) new CustomClassLoaderConstructor(classLoader));
    }

    public <T> String getStringFromInstance(T t) {
        return yaml.dump(t);
    }

    public <T> T getInstanceFromString(String str) {
        Intrinsics.checkParameterIsNotNull(str, "string");
        return yaml.load(str);
    }

    public <T> T getInstanceFromString(Class<T> cls, String str) {
        Intrinsics.checkParameterIsNotNull(cls, "clazz");
        Intrinsics.checkParameterIsNotNull(str, "string");
        return yaml.loadAs(str, cls);
    }
}
