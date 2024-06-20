package com.zkteco.android.util.serialization;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001:\u0001\u0012B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J+\u0010\u0005\u001a\u0004\u0018\u0001H\u0006\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u000bJ\u001d\u0010\u0005\u001a\u0004\u0018\u0001H\u0006\"\u0004\b\u0000\u0010\u00062\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\fJ\u001d\u0010\r\u001a\u00020\n\"\u0004\b\u0000\u0010\u00062\b\u0010\u000e\u001a\u0004\u0018\u0001H\u0006H\u0016¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u0004H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/zkteco/android/util/serialization/BinarySerializer;", "Lcom/zkteco/android/util/serialization/Serializer;", "()V", "classLoader", "Ljava/lang/ClassLoader;", "getInstanceFromString", "T", "clazz", "Ljava/lang/Class;", "string", "", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "(Ljava/lang/String;)Ljava/lang/Object;", "getStringFromInstance", "instance", "(Ljava/lang/Object;)Ljava/lang/String;", "setClassLoader", "", "ZKTecoObjectInputStream", "HelpersAndroidUtils_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: SerializerFactory.kt */
final class BinarySerializer implements Serializer {
    public static final BinarySerializer INSTANCE = new BinarySerializer();
    private static ClassLoader classLoader;

    private BinarySerializer() {
    }

    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u0012\u0012\u0002\b\u0003 \t*\b\u0012\u0002\b\u0003\u0018\u00010\b0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/zkteco/android/util/serialization/BinarySerializer$ZKTecoObjectInputStream;", "Ljava/io/ObjectInputStream;", "classLoader", "Ljava/lang/ClassLoader;", "inputStream", "Ljava/io/InputStream;", "(Ljava/lang/ClassLoader;Ljava/io/InputStream;)V", "resolveClass", "Ljava/lang/Class;", "kotlin.jvm.PlatformType", "desc", "Ljava/io/ObjectStreamClass;", "HelpersAndroidUtils_release"}, k = 1, mv = {1, 1, 9})
    /* compiled from: SerializerFactory.kt */
    private static final class ZKTecoObjectInputStream extends ObjectInputStream {
        private final ClassLoader classLoader;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ZKTecoObjectInputStream(ClassLoader classLoader2, InputStream inputStream) {
            super(inputStream);
            Intrinsics.checkParameterIsNotNull(classLoader2, "classLoader");
            Intrinsics.checkParameterIsNotNull(inputStream, "inputStream");
            this.classLoader = classLoader2;
        }

        /* access modifiers changed from: protected */
        public Class<?> resolveClass(ObjectStreamClass objectStreamClass) {
            Intrinsics.checkParameterIsNotNull(objectStreamClass, "desc");
            try {
                return super.resolveClass(objectStreamClass);
            } catch (ClassNotFoundException unused) {
                return this.classLoader.loadClass(objectStreamClass.getName());
            }
        }
    }

    public void setClassLoader(ClassLoader classLoader2) {
        Intrinsics.checkParameterIsNotNull(classLoader2, "classLoader");
        classLoader = classLoader2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0041, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r4, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0045, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0048, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0049, code lost:
        kotlin.io.CloseableKt.closeFinally(r0, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004c, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.lang.String getStringFromInstance(T r7) {
        /*
            r6 = this;
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = 0
            r2 = r1
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r3 = r0
            java.io.ByteArrayOutputStream r3 = (java.io.ByteArrayOutputStream) r3     // Catch:{ all -> 0x0046 }
            java.io.ObjectOutputStream r4 = new java.io.ObjectOutputStream     // Catch:{ all -> 0x0046 }
            r5 = r3
            java.io.OutputStream r5 = (java.io.OutputStream) r5     // Catch:{ all -> 0x0046 }
            r4.<init>(r5)     // Catch:{ all -> 0x0046 }
            java.io.Closeable r4 = (java.io.Closeable) r4     // Catch:{ all -> 0x0046 }
            java.lang.Throwable r1 = (java.lang.Throwable) r1     // Catch:{ all -> 0x0046 }
            r5 = r4
            java.io.ObjectOutputStream r5 = (java.io.ObjectOutputStream) r5     // Catch:{ all -> 0x003f }
            r5.writeObject(r7)     // Catch:{ all -> 0x003f }
            byte[] r7 = r3.toByteArray()     // Catch:{ all -> 0x003f }
            r3 = 0
            java.lang.String r7 = android.util.Base64.encodeToString(r7, r3)     // Catch:{ all -> 0x003f }
            java.lang.String r3 = "Base64.encodeToString(ar…eArray(), Base64.DEFAULT)"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r7, r3)     // Catch:{ all -> 0x003f }
            kotlin.io.CloseableKt.closeFinally(r4, r1)     // Catch:{ all -> 0x0046 }
            java.lang.String r1 = "ObjectOutputStream(array…EFAULT)\n                }"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r7, r1)     // Catch:{ all -> 0x0046 }
            kotlin.io.CloseableKt.closeFinally(r0, r2)
            java.lang.String r0 = "ByteArrayOutputStream().…          }\n            }"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r7, r0)
            return r7
        L_0x003f:
            r7 = move-exception
            throw r7     // Catch:{ all -> 0x0041 }
        L_0x0041:
            r1 = move-exception
            kotlin.io.CloseableKt.closeFinally(r4, r7)     // Catch:{ all -> 0x0046 }
            throw r1     // Catch:{ all -> 0x0046 }
        L_0x0046:
            r7 = move-exception
            throw r7     // Catch:{ all -> 0x0048 }
        L_0x0048:
            r1 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r7)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.util.serialization.BinarySerializer.getStringFromInstance(java.lang.Object):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0048, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r3, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004c, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004f, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0050, code lost:
        kotlin.io.CloseableKt.closeFinally(r0, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0053, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T getInstanceFromString(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = "string"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r6, r0)
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r1 = 0
            byte[] r6 = android.util.Base64.decode(r6, r1)
            r0.<init>(r6)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r6 = 0
            r1 = r6
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            r2 = r0
            java.io.ByteArrayInputStream r2 = (java.io.ByteArrayInputStream) r2     // Catch:{ all -> 0x004d }
            java.lang.ClassLoader r3 = classLoader     // Catch:{ all -> 0x004d }
            if (r3 == 0) goto L_0x002d
            com.zkteco.android.util.serialization.BinarySerializer$ZKTecoObjectInputStream r3 = new com.zkteco.android.util.serialization.BinarySerializer$ZKTecoObjectInputStream     // Catch:{ all -> 0x004d }
            java.lang.ClassLoader r4 = classLoader     // Catch:{ all -> 0x004d }
            if (r4 != 0) goto L_0x0025
            kotlin.jvm.internal.Intrinsics.throwNpe()     // Catch:{ all -> 0x004d }
        L_0x0025:
            java.io.InputStream r2 = (java.io.InputStream) r2     // Catch:{ all -> 0x004d }
            r3.<init>(r4, r2)     // Catch:{ all -> 0x004d }
            java.io.ObjectInputStream r3 = (java.io.ObjectInputStream) r3     // Catch:{ all -> 0x004d }
            goto L_0x0034
        L_0x002d:
            java.io.ObjectInputStream r3 = new java.io.ObjectInputStream     // Catch:{ all -> 0x004d }
            java.io.InputStream r2 = (java.io.InputStream) r2     // Catch:{ all -> 0x004d }
            r3.<init>(r2)     // Catch:{ all -> 0x004d }
        L_0x0034:
            java.io.Closeable r3 = (java.io.Closeable) r3     // Catch:{ all -> 0x004d }
            java.lang.Throwable r6 = (java.lang.Throwable) r6     // Catch:{ all -> 0x004d }
            r2 = r3
            java.io.ObjectInputStream r2 = (java.io.ObjectInputStream) r2     // Catch:{ all -> 0x0046 }
            java.lang.Object r2 = r2.readObject()     // Catch:{ all -> 0x0046 }
            kotlin.io.CloseableKt.closeFinally(r3, r6)     // Catch:{ all -> 0x004d }
            kotlin.io.CloseableKt.closeFinally(r0, r1)
            return r2
        L_0x0046:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x0048 }
        L_0x0048:
            r1 = move-exception
            kotlin.io.CloseableKt.closeFinally(r3, r6)     // Catch:{ all -> 0x004d }
            throw r1     // Catch:{ all -> 0x004d }
        L_0x004d:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x004f }
        L_0x004f:
            r1 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r6)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.util.serialization.BinarySerializer.getInstanceFromString(java.lang.String):java.lang.Object");
    }

    public <T> T getInstanceFromString(Class<T> cls, String str) {
        Intrinsics.checkParameterIsNotNull(cls, "clazz");
        Intrinsics.checkParameterIsNotNull(str, "string");
        return getInstanceFromString(str);
    }
}
