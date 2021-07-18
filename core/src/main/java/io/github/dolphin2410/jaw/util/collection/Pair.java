package io.github.dolphin2410.jaw.util.collection;

import org.jetbrains.annotations.NotNull;

/**
 * Inspired by the kotlin's Pair class, this allows java developers to use simple pairs.
 *
 * @param <T> The first type
 * @param <U> The second type
 * @author dolphin2410
 */
public class Pair<T, U> {
    private final T first;
    private final U second;
    public static<T, U> Pair<T, U> of(@NotNull T first, U second) {
        return new Pair<>(first, second);
    }
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
    public T getFirst() {
        return first;
    }
    public U getSecond() {
        return second;
    }
}
