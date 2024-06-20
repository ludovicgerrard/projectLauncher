package com.zkteco.android.io;

import android.content.Context;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Deprecated
public class FileWriter implements GenericWriter {
    private static final int BUFFER_LIMIT = 50;
    public static final String CODIFICATION = "UTF-8";
    private final Context context;
    private FileOutputStream writer;

    public FileWriter(Context context2) {
        this.context = context2;
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public void writeLine(String str, String str2) throws IOException {
        try {
            this.writer = this.context.openFileOutput(str2, 32768);
            this.writer.write((str + "\n").getBytes());
            close();
        } catch (Exception e) {
            Log.e("writeLine", e.getMessage(), e);
        }
    }

    public void writeLines(List<String> list, String str) throws IOException {
        writeLines(list, str, (String) null);
    }

    public void writeLines(List<String> list, String str, String str2) throws IOException {
        PrintWriter printWriter = new PrintWriter(str, "UTF-8");
        if (str2 != null) {
            try {
                printWriter.print(str2);
            } catch (Throwable th) {
                printWriter.close();
                throw th;
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String append : list) {
            sb.append(append);
            if (i == 50) {
                printWriter.println(sb.toString());
                sb = new StringBuilder();
                i = 0;
            } else {
                sb.append(10);
            }
            i++;
        }
        if (sb.length() > 0) {
            printWriter.print(sb.toString());
        }
        printWriter.close();
    }

    public void writeString(String str, String str2) throws IOException {
        PrintWriter printWriter = new PrintWriter(str2);
        try {
            printWriter.print(str);
        } finally {
            printWriter.close();
        }
    }

    public void writeStringInPrivateFile(String str, String str2) throws IOException {
        try {
            this.writer = this.context.openFileOutput(str2, 0);
            this.writer.write((str + "\n").getBytes());
        } catch (Exception e) {
            Log.e("fileWriter", e.getMessage(), e);
        } catch (Throwable th) {
            close();
            throw th;
        }
        close();
    }
}
