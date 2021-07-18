package io.github.dolphin2410.jaw.reflection;

/**
 * This exception is thrown when an exception occurred while using ConstructorAccessor, FieldAccessor or MethodAccessor.
 * @author dolphin2410
 */
public class ReflectionException extends RuntimeException {
    public final RuntimeException raw;
    public ReflectionException(String message, Throwable exception) {
        super(message);
        raw = new RuntimeException(exception);
    }
}
