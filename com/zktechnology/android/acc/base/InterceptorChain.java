package com.zktechnology.android.acc.base;

import com.zktechnology.android.utils.LogUtils;
import java.util.ArrayList;
import java.util.List;

public class InterceptorChain implements Interceptor {
    private List<Interceptor> interceptorList = new ArrayList();

    public InterceptorChain addInterceptors(Interceptor interceptor) {
        LogUtils.i("AccDoor", "InterceptorChain addInterceptors: " + interceptor.getClass().getSimpleName());
        this.interceptorList.add(interceptor);
        return this;
    }

    public void clearInterceptors() {
        this.interceptorList.clear();
    }

    public int getInterceptorCount() {
        return this.interceptorList.size();
    }

    public void interceptor(Request request, Response response) {
        try {
            for (Interceptor interceptor : this.interceptorList) {
                interceptor.interceptor(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
