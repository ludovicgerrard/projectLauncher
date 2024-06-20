package com.zkteco.android.util.serialization;

import kotlin.Metadata;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0004J\u0006\u0010\u0006\u001a\u00020\u0004¨\u0006\u0007"}, d2 = {"Lcom/zkteco/android/util/serialization/SerializerFactory;", "", "()V", "getBinarySerializer", "Lcom/zkteco/android/util/serialization/Serializer;", "getDefaultSerializer", "getYAMLSerializer", "HelpersAndroidUtils_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: SerializerFactory.kt */
public final class SerializerFactory {
    public static final SerializerFactory INSTANCE = new SerializerFactory();

    private SerializerFactory() {
    }

    public final Serializer getDefaultSerializer() {
        return getBinarySerializer();
    }

    public final Serializer getYAMLSerializer() {
        return YAMLSerializer.INSTANCE;
    }

    public final Serializer getBinarySerializer() {
        return BinarySerializer.INSTANCE;
    }
}
