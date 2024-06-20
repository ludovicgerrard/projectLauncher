package com.zkteco.android.io;

import java.io.IOException;
import java.util.List;

public interface GenericReader {
    void close() throws IOException;

    List<String> readAsList() throws IOException;

    String readFileToString() throws IOException;
}
