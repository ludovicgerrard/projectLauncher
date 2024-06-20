package com.zktechnology.android.view.controller;

import android.content.Context;
import android.content.Intent;

public class NavBarController {
    public static void enableNavBar(Context context) {
        context.sendBroadcast(new Intent("enable_nav_bar"));
    }

    public static void disableNavBar(Context context) {
        context.sendBroadcast(new Intent("disable_nav_bar"));
    }
}
