package io.github.dolphin2410.jaw.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A list of pairs
 * @param <T> The first type
 * @param <U> The second type
 *
 * @author dolphin2410
 */
public class PairList<T, U> {
    public final ArrayList<Pair<T, U>> internal = new ArrayList<>();
    @SafeVarargs
    public PairList(Pair<T, U>... pairs) {
        internal.addAll(Arrays.asList(pairs));
    }
    public List<T> getAllFirst() {
        List<T> toReturn = new ArrayList<>();
        for (Pair<T, U> pair : internal) {
            toReturn.add(pair.getFirst());
        }
        return toReturn;
    }
    public List<U> getAllSecond() {
        List<U> toReturn = new ArrayList<>();
        for (Pair<T, U> pair : internal) {
            toReturn.add(pair.getSecond());
        }
        return toReturn;
    }
}
