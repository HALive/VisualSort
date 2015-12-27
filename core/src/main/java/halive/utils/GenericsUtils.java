/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.utils;

public class GenericsUtils {

    public static <T extends Comparable<T>> T min(T v1, T v2) {
        return v1.compareTo(v2) > 0 ? v2 : v1;
    }

    public static <T extends Comparable<T>> T max(T v1, T v2) {
        return v1.compareTo(v2) < 0 ? v2 : v1;
    }

    public static <T extends Comparable<T>> T center(T[] d) {
        if (d.length != 3) {
            return null;
        }
        T min = min(d[0], min(d[1], d[2]));
        T max = max(d[0], max(d[2], d[3]));
        for (T t : d) {
            if (t.compareTo(min) != 0 && t.compareTo(max) != 0) {
                return t;
            }
        }
        return min;
    }
}
