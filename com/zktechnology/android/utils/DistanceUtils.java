package com.zktechnology.android.utils;

public class DistanceUtils {
    private static final double EARTH_RADIUS = 6378.137d;

    private static double rad(double d) {
        return (d * 3.141592653589793d) / 180.0d;
    }

    public static double getDistance(double d, double d2, double d3, double d4) {
        double rad = rad(d);
        double rad2 = rad(d3);
        return (((double) Math.round(((Math.asin(Math.sqrt(Math.pow(Math.sin((rad - rad2) / 2.0d), 2.0d) + ((Math.cos(rad) * Math.cos(rad2)) * Math.pow(Math.sin((rad(d2) - rad(d4)) / 2.0d), 2.0d)))) * 2.0d) * EARTH_RADIUS) * 10000.0d)) / 10000.0d) * 1000.0d;
    }
}
