package com.zktechnology.android.att.AttInterceptors;

import com.zktechnology.android.att.AttRequest;
import com.zktechnology.android.att.AttResponse;

public interface AttInterceptor {
    void interceptor(AttRequest attRequest, AttResponse attResponse);
}
