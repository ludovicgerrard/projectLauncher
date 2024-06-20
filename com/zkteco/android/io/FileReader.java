package com.zkteco.android.io;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FileReader implements GenericReader {
    private static final String TAG = "com.zkteco.android.io.FileReader";
    private final FileInputStream inputSt;

    public FileReader(Context context, String str) throws FileNotFoundException {
        this.inputSt = new FileInputStream(new File(str));
    }

    public void close() throws IOException {
        this.inputSt.close();
    }

    public List<String> readAsList() throws IOException {
        ArrayList arrayList = new ArrayList();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.inputSt));
            for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                arrayList.add(readLine);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (Throwable th) {
            this.inputSt.close();
            throw th;
        }
        this.inputSt.close();
        return arrayList;
    }

    /* JADX INFO: finally extract failed */
    public String readFileToString() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.inputSt));
        StringBuilder sb = new StringBuilder();
        try {
            for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                sb.append(readLine).append(10);
            }
            String sb2 = sb.toString();
            this.inputSt.close();
            return sb2;
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            this.inputSt.close();
            return sb.toString();
        } catch (Throwable th) {
            this.inputSt.close();
            throw th;
        }
    }
}
