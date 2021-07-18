package io.github.dolphin2410.jaw.util.core;

import io.github.dolphin2410.jaw.util.collection.Pair;
import io.github.dolphin2410.jaw.util.kotlin.KWrapper;

import java.util.ArrayList;

/**
 * A global named variable. Once you created an instance, the object goes in the static field 'internal' in the Named class.
 * @author dolphin2410
 */
public class Named {
    private static final ArrayList<Pair<String, Object>> internal = new ArrayList<>();
    public Named(String name, Object obj) {
        if (KWrapper.filter(internal, (i)->i.getFirst().equals(name)).size() == 0 || internal.size() == 0) {
            internal.add(Pair.of(name, obj));
        } else {
            throw new RuntimeException("Element already exists");
        }
    }
}
