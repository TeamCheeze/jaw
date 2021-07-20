package io.github.dolphin2410.jaw.util.gson;

import com.google.gson.JsonParser;
import io.github.dolphin2410.jaw.reflection.MethodAccessor;
import io.github.dolphin2410.jaw.reflection.ReflectionException;
import io.github.dolphin2410.jaw.util.kotlin.KWrapper;
import org.jetbrains.annotations.NotNull;

public enum WrapperVersion {
    NEW,
    LEGACY;
    @NotNull
    private static WrapperVersion version = NEW;
    public static void setVersion(@NotNull WrapperVersion version) {
        WrapperVersion.version = version;
    }
    @NotNull
    public static WrapperVersion getVersion() {
        return version;
    }

    /**
     * Automatically infers Gson wrapper version and sets it.
     * @return The version
     */
    public WrapperVersion inferVersion() {
        return KWrapper.apply(detectVersion(), WrapperVersion::setVersion);
    }

    /**
     * Automatically detects Gson wrapper
     * @return The version
     */
    public WrapperVersion detectVersion() {
        try {
            new MethodAccessor<>(JsonParser.class, "parseString").invoke("{}");
            return WrapperVersion.NEW;
        } catch (ReflectionException e) {
            return WrapperVersion.LEGACY;
        }
    }
}
