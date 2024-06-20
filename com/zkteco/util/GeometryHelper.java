package com.zkteco.util;

public class GeometryHelper {

    public static class Point {
        public float x;
        public float y;

        public Point(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        public Point(int[] iArr) {
            this.x = (float) iArr[0];
            this.y = (float) iArr[1];
        }

        public boolean isAbove(Point point) {
            return !isBelow(point);
        }

        public boolean isBelow(Point point) {
            return this.y >= point.y;
        }

        public boolean isLeftOf(Point point) {
            return !isRightOf(point);
        }

        public boolean isRightOf(Point point) {
            return this.x >= point.x;
        }
    }

    public static class Rectangle {
        private final Point downRight;
        private final Point topLeft;

        public Rectangle(Point point, Point point2) {
            this.topLeft = point;
            this.downRight = point2;
        }

        public boolean isPointInside(Point point) {
            return point.isBelow(this.topLeft) && point.isRightOf(this.topLeft) && point.isAbove(this.downRight) && point.isLeftOf(this.downRight);
        }
    }
}
