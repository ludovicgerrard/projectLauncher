package com.zkteco.android.core.library;

public class CoreServiceException extends RuntimeException {
    public CoreServiceException(String str) {
        super(str);
    }

    public CoreServiceException(Exception exc) {
        super(exc);
    }
}
