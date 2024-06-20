package com.zkteco.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

public class YAMLHelper {
    private static Yaml YAML;

    private YAMLHelper() {
    }

    private static Yaml getYAML() {
        if (YAML == null) {
            YAML = new Yaml();
        }
        return YAML;
    }

    public static <T> T getInstanceFromBinary(Class<T> cls, ByteBuffer byteBuffer, String str) throws IOException {
        return getInstanceFromBinary(cls, byteBuffer.array(), str);
    }

    public static void setClassLoader(ClassLoader classLoader) {
        YAML = new Yaml((BaseConstructor) new CustomClassLoaderConstructor(classLoader));
    }

    public static <T> T getInstanceFromBinary(Class<T> cls, byte[] bArr, String str) throws UnsupportedEncodingException {
        return getInstanceFromString(cls, new String(bArr, str));
    }

    public static ByteBuffer getBinaryFromInstance(Object obj, String str) throws UnsupportedEncodingException {
        return ByteBuffer.wrap(getStringFromInstance(obj).getBytes(str));
    }

    public static String getStringFromInstance(Object obj) {
        return getYAML().dump(obj);
    }

    public static <T> T getInstanceFromString(Class<T> cls, String str) {
        return getYAML().loadAs(str, cls);
    }

    public static <T> T getInstanceFromString(String str) {
        return getYAML().load(str);
    }

    public static <T> T getInstanceFromFile(String str) throws IOException {
        FileReader fileReader = new FileReader(str);
        try {
            return getYAML().load((Reader) fileReader);
        } finally {
            fileReader.close();
        }
    }
}
