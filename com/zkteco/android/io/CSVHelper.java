package com.zkteco.android.io;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper extends GenericFormattedFileWritter {
    public static final String CSV_FILTER = "^.*\\.csv$";
    public static final String EXTENSION = ".csv";
    public static final String HEADER_CSV = "﻿";
    public static final char SEPARATOR = ',';
    private static final String TAG = "com.zkteco.android.io.CSVHelper";

    public String getFormatHeader() {
        return HEADER_CSV;
    }

    public char getSeparator() {
        return SEPARATOR;
    }

    private String arrayToCSVRow(String[] strArr) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            if (sb.length() > 0) {
                sb.append(SEPARATOR);
            }
            sb.append(str);
        }
        return sb.toString();
    }

    public boolean generateDefaultReportFile(Context context, String str, String[] strArr, String[] strArr2, String[][] strArr3) {
        try {
            ArrayList arrayList = new ArrayList();
            String arrayToCSVRow = arrayToCSVRow(strArr);
            String arrayToCSVRow2 = arrayToCSVRow(strArr2);
            arrayList.add(arrayToCSVRow);
            arrayList.add(arrayToCSVRow2);
            for (String[] arrayToCSVRow3 : strArr3) {
                arrayList.add(arrayToCSVRow(arrayToCSVRow3));
            }
            new FileWriter(context).writeLines(arrayList, str);
            return true;
        } catch (Exception e) {
            String str2 = TAG;
            Log.e(str2, "There was an error reading the cursor and exporting it");
            Log.e(str2, Log.getStackTraceString(e));
            return false;
        }
    }

    public List<String> readCSVFromCursor(Context context, String str) throws IOException {
        return (ArrayList) new FileReader(context, str).readAsList();
    }
}
