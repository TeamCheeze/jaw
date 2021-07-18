package io.github.dolphin2410.jaw.util.kotlin;

import io.github.dolphin2410.jaw.util.collection.Pair;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Some essential kotlin's features that are handy to use with java.
 * @author dolphin2410
 */
public class KWrapper {
    @SafeVarargs
    public static<K, V> Map<K, V> mapOf(Pair<K, V>... pairs) {
        HashMap<K, V> map = new HashMap<>();
        for (Pair<K, V> pair : pairs) {
            map.put(pair.getFirst(), pair.getSecond());
        }
        return map;
    }
    @SafeVarargs
    public static<E> List<E> listOf(E... e) {
        return Arrays.asList(e);
    }
    public static <I> I apply(I element, Consumer<I> consumer) {
        consumer.accept(element);
        return element;
    }
    public static String removePrefix(String source, String prefix) {
        if (source.startsWith(prefix)) {
            return source.replaceAll("^" + prefix, "");
        }
        return source;
    }
    public static String removeSuffix(String source, String suffix) {
        if (source.endsWith(suffix)) {
            return source.replaceAll(suffix + "$", "");
        }
        return source;
    }
    public static <T> T[] filter(T[] array, Function<T, Boolean> function) {
        ArrayList<T> internal = new ArrayList<>();
        for (T item : array) {
            if (function.apply(item)) {
                internal.add(item);
            }
        }
        @SuppressWarnings("unchecked") T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), internal.size());
        return internal.toArray(newArray);
    }
    public static <T> ArrayList<T> filter(ArrayList<T> al, Function<T, Boolean> function) {
        ArrayList<T> toReturn = new ArrayList<>();
        for (T item : al) {
            if (function.apply(item)) {
                toReturn.add(item);
            }
        }
        return toReturn;
    }
    public static String print(String message) {
        return apply(message, System.out::print);
    }
    public static String print(Object obj) {
        return print(obj.toString());
    }
    public static String println(String message) {
        return apply(message, System.out::println);
    }
    public String println(Object obj) {
        return println(obj.toString());
    }
}
