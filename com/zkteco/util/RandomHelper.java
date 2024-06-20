package com.zkteco.util;

import java.util.Random;

public final class RandomHelper {
    private static final Random RND = new Random();

    private RandomHelper() {
    }

    public static long getUUID() {
        return RND.nextLong();
    }

    public static boolean getByProbability(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Negative probability not supported");
        } else if (i == 0) {
            return false;
        } else {
            if (i == 100) {
                return true;
            }
            if (RND.nextInt(100) >= i) {
                return true;
            }
            return false;
        }
    }

    public static int getRandomInteger() {
        return RND.nextInt();
    }

    public static int getRandomPositiveInteger() {
        return RND.nextInt(Integer.MAX_VALUE);
    }
}
