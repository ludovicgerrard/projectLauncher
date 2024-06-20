package com.zkteco.android.core.library;

import java.io.Closeable;

public interface Provider extends Closeable {
    Object invoke(String str, Object... objArr);
}
