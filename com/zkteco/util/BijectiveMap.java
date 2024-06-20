package com.zkteco.util;

import java.util.Map;

public interface BijectiveMap<K, V> extends Map<K, V> {
    K getKey(V v);
}
