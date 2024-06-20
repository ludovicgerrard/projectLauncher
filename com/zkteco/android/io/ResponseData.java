package com.zkteco.android.io;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J!\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\u0015"}, d2 = {"Lcom/zkteco/android/io/ResponseData;", "", "data", "", "error", "(Ljava/lang/String;Ljava/lang/String;)V", "getData", "()Ljava/lang/String;", "setData", "(Ljava/lang/String;)V", "getError", "setError", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "HelpersAndroidIO_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: NetworkUtils.kt */
public final class ResponseData {
    private String data;
    private String error;

    public static /* bridge */ /* synthetic */ ResponseData copy$default(ResponseData responseData, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = responseData.data;
        }
        if ((i & 2) != 0) {
            str2 = responseData.error;
        }
        return responseData.copy(str, str2);
    }

    public final String component1() {
        return this.data;
    }

    public final String component2() {
        return this.error;
    }

    public final ResponseData copy(String str, String str2) {
        return new ResponseData(str, str2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResponseData)) {
            return false;
        }
        ResponseData responseData = (ResponseData) obj;
        return Intrinsics.areEqual((Object) this.data, (Object) responseData.data) && Intrinsics.areEqual((Object) this.error, (Object) responseData.error);
    }

    public int hashCode() {
        String str = this.data;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.error;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "ResponseData(data=" + this.data + ", error=" + this.error + ")";
    }

    public ResponseData(String str, String str2) {
        this.data = str;
        this.error = str2;
    }

    public final String getData() {
        return this.data;
    }

    public final String getError() {
        return this.error;
    }

    public final void setData(String str) {
        this.data = str;
    }

    public final void setError(String str) {
        this.error = str;
    }
}
