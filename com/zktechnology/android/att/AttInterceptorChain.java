package com.zktechnology.android.att;

import com.zktechnology.android.att.AttInterceptors.AttInterceptor;
import java.util.ArrayList;
import java.util.List;

public class AttInterceptorChain implements AttInterceptor {
    public List<AttInterceptor> interceptorList = new ArrayList();

    public void addInterceptors(AttInterceptor attInterceptor) {
        this.interceptorList.add(attInterceptor);
    }

    public void interceptor(AttRequest attRequest, AttResponse attResponse) {
        for (AttInterceptor interceptor : this.interceptorList) {
            interceptor.interceptor(attRequest, attResponse);
            if (!attResponse.isCanProceed()) {
                return;
            }
        }
    }
}
