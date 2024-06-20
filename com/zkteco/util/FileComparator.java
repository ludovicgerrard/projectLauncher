package com.zkteco.util;

import java.io.File;
import java.util.Comparator;

public class FileComparator implements Comparator<File> {
    public static final int ALPHA_ASC = 0;
    public static final int ALPHA_DESC = 1;
    public static final int DATE_ASC = 2;
    public static final int DATE_DESC = 3;
    private static final int[] REVERSE_MODES = {1, 0, 3, 2};
    private static final String TAG = FileComparator.class.getName();
    private final int mode;

    public static boolean isAlphaOrdering(int i) {
        return i == 0 || i == 1;
    }

    public static boolean isDateOrdering(int i) {
        return i == 2 || i == 3;
    }

    public static int switchMode(int i) {
        return REVERSE_MODES[i];
    }

    public FileComparator(int i) {
        this.mode = i;
    }

    public int compare(File file, File file2) {
        int i = this.mode;
        if (i == 0) {
            return compareAlphaAsc(file, file2);
        }
        if (i == 1) {
            return compareAlphaDesc(file, file2);
        }
        if (i == 2) {
            return compareDateAsc(file, file2);
        }
        if (i == 3) {
            return compareDateDesc(file, file2);
        }
        throw new RuntimeException("Unknown comparison mode");
    }

    private int compareAlphaAsc(File file, File file2) {
        if (file.isDirectory()) {
            if (file2.isDirectory()) {
                return file.getName().compareToIgnoreCase(file2.getName());
            }
            return -1;
        } else if (file2.isDirectory()) {
            return 1;
        } else {
            return file2.getName().compareToIgnoreCase(file.getName());
        }
    }

    private int compareAlphaDesc(File file, File file2) {
        return -compareAlphaAsc(file, file2);
    }

    private int compareDateAsc(File file, File file2) {
        return Long.valueOf(file.lastModified()).compareTo(Long.valueOf(file2.lastModified()));
    }

    private int compareDateDesc(File file, File file2) {
        return -compareDateAsc(file, file2);
    }
}
