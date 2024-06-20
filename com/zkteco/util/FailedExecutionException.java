package com.zkteco.util;

public class FailedExecutionException extends Exception {
    public FailedExecutionException() {
    }

    public FailedExecutionException(Exception exc) {
        super(exc);
    }

    public FailedExecutionException(String str) {
        super(str);
    }
}
