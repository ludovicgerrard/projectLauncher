package com.zkteco.android.io;

import java.io.IOException;
import java.util.List;

public interface GenericWriter {
    void close() throws IOException;

    void writeLine(String str, String str2) throws IOException;

    void writeLines(List<String> list, String str) throws IOException;

    void writeString(String str, String str2) throws IOException;
}
