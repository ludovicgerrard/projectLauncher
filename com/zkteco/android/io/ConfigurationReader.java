package com.zkteco.android.io;

public interface ConfigurationReader {
    <T> T get(String str, T t);
}
