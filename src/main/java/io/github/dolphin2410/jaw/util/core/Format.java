package io.github.dolphin2410.jaw.util.core;

import java.util.Iterator;
import java.util.Map;

/**
 * A formatting engine.
 * Usage: "{item} was sold out in {market}.", KWrapper.mapOf(Pair.of("item", "Bananas"), Pair.of("market", "Amazon"))
 *
 * @author dolphin2410
 */
public class Format {
    public static String format(String original, Map<String, String> map){
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        String toReturn = original;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            toReturn = original.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return toReturn;
    }
}
