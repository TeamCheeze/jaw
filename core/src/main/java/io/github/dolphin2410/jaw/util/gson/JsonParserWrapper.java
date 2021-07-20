package io.github.dolphin2410.jaw.util.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.Reader;

public class JsonParserWrapper {
    public static JsonElement parseString(String json) {
        switch (WrapperVersion.getVersion()) {
            case LEGACY -> {
                return new JsonParser().parse(json);
            }
            case NEW -> {
                return JsonParser.parseString(json);
            }
            default -> {
                throw new RuntimeException("");
            }
        }
    }
    public static JsonElement parseReader(Reader json) {
        switch (WrapperVersion.getVersion()) {
            case LEGACY -> {
                return new JsonParser().parse(json);
            }
            case NEW -> {
                return JsonParser.parseReader(json);
            }
            default -> throw new RuntimeException("");
        }
    }
    public static JsonElement parseReader(JsonReader json) {
        switch (WrapperVersion.getVersion()) {
            case LEGACY -> {
                return new JsonParser().parse(json);
            }
            case NEW -> {
                return JsonParser.parseReader(json);
            }
            default -> throw new RuntimeException("");
        }
    }
}
