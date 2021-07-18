package io.github.dolphin2410.jaw.util.kotlin;

import java.util.AbstractList;

/**
 * IntRange can be used to use in the for loop for looping N times, or pass the range of an Integer like kotlin.
 */
public class IntRange extends AbstractList<Integer> {
    private final int first;
    private final int second;
    public IntRange(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public Integer get(int index) {
        return index;
    }

    @Override
    public int size() {
        return second;
    }
}
