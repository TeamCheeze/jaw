package io.github.dolphin2410.jaw.util.core;

/**
 * This is nothing. Just Void.
 *
 * @author dolphin2410
 */
public final class Nothing {
    public static Nothing INSTANCE;
    static {
        new Nothing();
    }
    private Nothing() {
        if (INSTANCE != null) {
            throw new RuntimeException("This can't be called. Use Nothing#INSTANCE instead");
        }
        INSTANCE = this;
    }
}
