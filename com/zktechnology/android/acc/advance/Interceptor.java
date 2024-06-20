package com.zktechnology.android.acc.advance;

import java.util.Date;

public interface Interceptor {
    void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date);
}
