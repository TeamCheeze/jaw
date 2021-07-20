package io.github.dolphin2410.jaw.util.gson;

import org.jetbrains.annotations.NotNull;

public enum WrapperVersion {
    NEW,
    LEGACY;
    @NotNull
    private static WrapperVersion version = NEW;
    public static void setVersion(WrapperVersion version) {
        WrapperVersion.version = version;
    }
    @NotNull
    public static WrapperVersion getVersion() {
        return version;
    }
}
