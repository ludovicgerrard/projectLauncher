package com.zkteco.util;

public interface GenericDataListener<K, V> {
    V onReceive(K k);
}
