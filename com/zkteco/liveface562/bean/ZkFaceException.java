package com.zkteco.liveface562.bean;

public class ZkFaceException extends Exception {
    public ZkFaceException() {
    }

    public ZkFaceException(String str) {
        super(str);
    }

    public ZkFaceException(String str, Throwable th) {
        super(str, th);
    }

    public ZkFaceException(Throwable th) {
        super(th);
    }
}
