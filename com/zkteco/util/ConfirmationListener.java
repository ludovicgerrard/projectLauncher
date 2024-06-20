package com.zkteco.util;

import java.util.Map;

public interface ConfirmationListener {
    void onCancel();

    void onOk(Map<String, Object> map);
}
