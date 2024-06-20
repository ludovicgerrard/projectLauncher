package com.zkteco.android.util;

import android.graphics.Point;

public class Area {
    private final Point lowerRight;
    private final Point upperLeft;

    public Area(Point point, Point point2) {
        this.upperLeft = point;
        this.lowerRight = point2;
    }

    public Point getUpperLeft() {
        return this.upperLeft;
    }

    public Point getLowerRight() {
        return this.lowerRight;
    }

    public String toString() {
        return "Area{upperLeft=" + this.upperLeft + ", lowerRight=" + this.lowerRight + '}';
    }
}
