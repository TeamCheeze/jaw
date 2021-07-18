package io.github.dolphin2410.jaw.util.collection.development;

import java.util.Iterator;
import java.util.List;

/**
 * This simple iterator is an iterator class that you can use while creating your own custom collection.
 * @param <T> The component type of the object.
 *
 * @author dolphin2410
 */
public class SimpleIterator<T> implements Iterator<T> {
    private T[] internal;
    private int currentIndex = -1;
    private T currentElement;
    public SimpleIterator(List<T> list) {
        @SuppressWarnings("unchecked") T[] array = ((T[]) list.toArray(new Object[0]));
        this.internal = array;
    }

    @Override
    public boolean hasNext() {
        return internal.length - 1 != currentIndex;
    }

    @Override
    public T next() {
        currentIndex ++;
        this.currentElement = internal[currentIndex];
        return currentElement;
    }

    public T current() {
        return currentElement;
    }
}
