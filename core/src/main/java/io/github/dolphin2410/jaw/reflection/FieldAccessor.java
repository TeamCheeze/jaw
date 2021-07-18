package io.github.dolphin2410.jaw.reflection;

import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * This works like a Field class in java, but it has additional features like being able to modify static final types.
 *
 * @param <T> This is the class that you want to get and set the field value of
 * @author dolphin2410
 */
public class FieldAccessor<T> {
    private final Class<T> clazz;
    private final Object instance;
    private final String fieldName;

    public FieldAccessor(Class<T> clazz, @NotNull String fieldName) {
        this.fieldName = fieldName;
        this.instance = null;
        this.clazz = clazz;
    }

    public FieldAccessor(T obj, @NotNull String fieldName) {
        this.fieldName = fieldName;
        @SuppressWarnings("unchecked") Class<T> clazz = (Class<T>) obj.getClass();
        this.clazz = clazz;
        this.instance = obj;
    }

    public Object get() throws ReflectionException {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ReflectionException("There was an error while getting a value using reflection.", e);
        }
    }

    public void set(@NotNull Object value) throws ReflectionException {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                final Unsafe unsafe = (Unsafe) unsafeField.get(null);
                final Object staticFieldBase = unsafe.staticFieldBase(field);
                final long staticFieldOffset = unsafe.staticFieldOffset(field);
                unsafe.putObject(staticFieldBase, staticFieldOffset, value);
            } else {
                field.set(instance, value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ReflectionException("There was an error while setting a value using reflection.", e);
        }
    }

    public FieldAccessor<T> setDeclaringClass(@NotNull Class<?> clazz) {
        new FieldAccessor<FieldAccessor<T>>(this, "clazz").set(clazz);
        return this;
    }
}
