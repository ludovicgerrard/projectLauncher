package com.zkteco.android.core.interfaces;

import java.util.List;

public interface FingerprintAlgorithmInterface {
    public static final String CLEAR = "fp-clear";
    public static final String DELETE = "fp-delete";
    public static final String ENROLL = "fp-enroll";
    public static final String IDENTIFY = "fp-identify";
    public static final String INIT = "fp-init";
    public static final String PARAMETER = "fp-parameter";
    public static final String SAVE = "fp-save";
    public static final String VERIFY = "fp-verify";

    void clear();

    void delete(String str);

    String enroll(String str, List<String> list);

    String identify(String str, int i);

    int init();

    void save(String str, String str2);

    void setParameter(int i);

    boolean verify(String str, String str2, int i);
}
