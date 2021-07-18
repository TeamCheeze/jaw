package io.github.dolphin2410.jaw.util.core;

/**
 * The default boolean isn't allowed in java's switch-case feature. However, enum is, and this class will allow you to to that.
 * @author dolphin2410
 */
public enum EnumBoolean {
    TRUE,
    FALSE;
    public static EnumBoolean of(boolean java_boolean){
        return java_boolean ? TRUE : FALSE;
    }
}
