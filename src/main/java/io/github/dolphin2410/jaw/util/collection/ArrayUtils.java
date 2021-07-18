package io.github.dolphin2410.jaw.util.collection;

import java.lang.reflect.Array;

/**
 * Array utilities that is most likely to be cumbersome to create every time.
 *
 * @author dolphin2410
 */
public class ArrayUtils {
    public static <T> T[] concentrate(T[] a, T[] b) {
        @SuppressWarnings("unchecked")
        T[] newArray = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length + b.length);
        System.arraycopy(a, 0, newArray, 0, a.length);
        System.arraycopy(b, 0, newArray, a.length, b.length);
        return newArray;
    }
    public static <T> T[] emptyArray(Class<? extends T> clazz) {
        @SuppressWarnings("unchecked") T[] toReturn = (T[]) Array.newInstance(clazz, 0);
        return toReturn;
    }
}
