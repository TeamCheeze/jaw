package io.github.dolphin2410.jaw.util.collection;

import io.github.dolphin2410.jaw.util.collection.development.SimpleIterator;

import java.util.*;
import java.util.function.Consumer;

/**
 * Just as the name says, this supports creating indexes for non-indexed list.
 * @param <T> The component type of the non-indexed list
 * @author dolphin2410
 */
public class IndexedList<T> extends AbstractList<Pair<Integer, T>> {
    public final ArrayList<Pair<Integer, T>> internalArray = new ArrayList<>();
    public IndexedList(){}

    @Override
    public Pair<Integer, T> get(int index) {
        return internalArray.get(index);
    }

    @Override
    public void forEach(Consumer<? super Pair<Integer, T>> action) {
        internalArray.forEach(action);
    }

    @Override
    public Iterator<Pair<Integer, T>> iterator() {
        return new SimpleIterator<>(internalArray.stream().toList());
    }

    @Override
    public int size() {
        return internalArray.size();
    }

    public static <T> IndexedList<T> of(T[] array) {
        return IndexedList.of(Arrays.asList(array));
    }
    public static <T> IndexedList<T> of(List<T> list) {
        IndexedList<T> newIndexedList = new IndexedList<>();
        int index = 0;
        for (T element : list) {
            newIndexedList.internalArray.add(new Pair<>(index, element));
            index++;
        }
        return newIndexedList;
    }
}
