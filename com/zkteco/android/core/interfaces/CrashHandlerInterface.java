package com.zkteco.android.core.interfaces;

public interface CrashHandlerInterface {
    public static final String CRASHHANDLER_SAVEEXCEPTION = "crashhandler-saveexception";

    void saveException(Throwable th);
}
