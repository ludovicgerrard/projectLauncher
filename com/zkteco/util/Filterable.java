package com.zkteco.util;

import java.lang.Comparable;

public interface Filterable<E extends Comparable<? super E>> extends Comparable<E> {
    String getFilterString();
}
