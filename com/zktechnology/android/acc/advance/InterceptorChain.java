package com.zktechnology.android.acc.advance;

import com.zktechnology.android.acc.advance.Interceptors.ContinuousVerifyInterceptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InterceptorChain implements Interceptor {
    private static final String TAG = "InterceptorChain";
    public List<Interceptor> interceptorList = new ArrayList();

    public InterceptorChain addInterceptors(Interceptor interceptor) {
        this.interceptorList.add(interceptor);
        return this;
    }

    private Interceptor getContinuousVerifyInterceptor() {
        for (Interceptor next : this.interceptorList) {
            if (next instanceof ContinuousVerifyInterceptor) {
                return next;
            }
        }
        return null;
    }

    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        Interceptor continuousVerifyInterceptor;
        for (Interceptor interceptor : this.interceptorList) {
            interceptor.interceptor(doorAccessRequest, doorAccessResponse, date);
            boolean isCanProceed = doorAccessResponse.isCanProceed();
            if ((doorAccessResponse.isStressFingerAlarmOn() || doorAccessResponse.isStressPasswordAlarmOn()) && (continuousVerifyInterceptor = getContinuousVerifyInterceptor()) != null) {
                continuousVerifyInterceptor.interceptor(doorAccessRequest, doorAccessResponse, date);
                isCanProceed = false;
                continue;
            }
            if (!isCanProceed) {
                return;
            }
        }
    }
}
