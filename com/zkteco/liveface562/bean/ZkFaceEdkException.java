package com.zkteco.liveface562.bean;

public class ZkFaceEdkException extends Exception {
    private int errorCode;

    public static String getErrorDetail(int i) {
        return i != -804 ? i != -801 ? "unknow error code" : "Edk service not connected" : "Face Algorithm is not init";
    }

    public ZkFaceEdkException(int i) {
        super(getErrorDetail(i));
        this.errorCode = i;
    }

    public ZkFaceEdkException(String str, int i) {
        super(str);
        this.errorCode = i;
    }

    public ZkFaceEdkException(String str, Throwable th, int i) {
        this.errorCode = i;
    }

    public ZkFaceEdkException(Throwable th, int i) {
        this.errorCode = i;
    }

    public ZkFaceEdkException(String str, Throwable th, boolean z, boolean z2, int i) {
        this.errorCode = i;
    }

    public void printStackTrace() {
        super.printStackTrace();
    }
}
