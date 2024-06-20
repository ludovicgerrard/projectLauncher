package com.zktechnology.android.ver;

import java.util.LinkedHashMap;

public class VerifyMap {
    public static LinkedHashMap<Integer, Boolean> order(int i, int i2) {
        LinkedHashMap<Integer, Boolean> linkedHashMap = new LinkedHashMap<>();
        if (i2 == 0) {
            return put(Integer.valueOf(i));
        } else if (i2 == 1) {
            return put(1);
        } else if (i2 == 2) {
            return put(2);
        } else if (i2 == 3) {
            return put(3);
        } else if (i2 != 4) {
            switch (i2) {
                case 8:
                    if (i == 1) {
                        return put(1, 2);
                    } else if (i != 2) {
                        return put(1, 2);
                    } else {
                        return put(2, 1);
                    }
                case 9:
                    if (i == 1) {
                        return put(1, 3);
                    } else if (i != 3) {
                        return put(3, 1);
                    } else {
                        return put(3, 1);
                    }
                case 10:
                    if (i == 1) {
                        return put(1, 4);
                    } else if (i != 4) {
                        return put(1, 4);
                    } else {
                        return put(4, 1);
                    }
                case 11:
                    if (i == 3) {
                        return put(3, 4);
                    } else if (i != 4) {
                        return put(3, 4);
                    } else {
                        return put(4, 3);
                    }
                case 12:
                    if (i == 1) {
                        return put(1, 3, 4);
                    } else if (i == 3) {
                        return put(3, 4, 1);
                    } else if (i != 4) {
                        return put(1, 3, 4);
                    } else {
                        return put(4, 1, 3);
                    }
                case 13:
                    if (i == 1) {
                        return put(1, 3, 2);
                    } else if (i == 2) {
                        return put(2, 1, 3);
                    } else if (i != 3) {
                        return put(2, 1, 3);
                    } else {
                        return put(3, 2, 1);
                    }
                case 14:
                    if (i == 1) {
                        return put(1, 2);
                    } else if (i == 2) {
                        return put(2, 1);
                    } else if (i != 4) {
                        return put(1, 2);
                    } else {
                        return put(4, 1);
                    }
                case 15:
                    return put(5);
                case 16:
                    if (i == 1) {
                        return put(1, 5);
                    } else if (i != 5) {
                        return put(1, 5);
                    } else {
                        return put(5, 1);
                    }
                case 17:
                    if (i == 3) {
                        return put(3, 5);
                    } else if (i != 5) {
                        return put(5, 3);
                    } else {
                        return put(5, 3);
                    }
                case 18:
                    if (i == 4) {
                        return put(4, 5);
                    } else if (i != 5) {
                        return put(5, 4);
                    } else {
                        return put(5, 4);
                    }
                case 19:
                    if (i == 1) {
                        return put(1, 4, 5);
                    } else if (i == 4) {
                        return put(4, 5, 1);
                    } else if (i != 5) {
                        return put(5, 1, 4);
                    } else {
                        return put(5, 1, 4);
                    }
                case 20:
                    if (i == 1) {
                        return put(1, 3, 5);
                    } else if (i == 3) {
                        return put(3, 5, 1);
                    } else if (i != 5) {
                        return put(5, 1, 3);
                    } else {
                        return put(5, 1, 3);
                    }
                default:
                    return linkedHashMap;
            }
        } else {
            return put(4);
        }
    }

    private static LinkedHashMap<Integer, Boolean> put(Integer... numArr) {
        LinkedHashMap<Integer, Boolean> linkedHashMap = new LinkedHashMap<>();
        if (numArr == null) {
            return linkedHashMap;
        }
        for (Integer num : numArr) {
            if (num.intValue() != -1) {
                linkedHashMap.put(num, false);
            }
        }
        return linkedHashMap;
    }
}
